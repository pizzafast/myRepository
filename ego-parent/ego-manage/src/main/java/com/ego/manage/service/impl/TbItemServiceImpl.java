package com.ego.manage.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.TbItemChild;
import com.ego.commons.utils.HttpClientUtil;
import com.ego.commons.utils.IDUtils;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.manage.service.TbItemService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParamItem;
import com.ego.redis.dao.JedisDao;
@Service
public class TbItemServiceImpl implements TbItemService{
	@Reference
	private TbItemDubboService tbItemDubboServiceImpl;
	@Reference
	private TbItemDescDubboService tbItemDescDubboServiceImpl;
	
	@Value("${search.addurl}")
	private String addurl;//添加solr的控制器
	@Value("${search.deleteurl}")
	private String deleteurl;//删除solr中的数据的控制器
	
	@Value("${redis.item.key}")
	private String itemKey;//redis中商品的key
	@Value("${redis.desc.key}")
	private String itemDescKey;//redis中商品描述key
	
	@Resource
	private JedisDao jedisDaoImpl;
	@Override
	public EasyUIDataGrid show(int page, int rows) {
		return tbItemDubboServiceImpl.show(page, rows);
	}
	@Override
	public int update(String ids, byte status) {
		String[] idsstr = ids.split(",");
		TbItem tbItem = new TbItem();
		Date date = new Date();
		int index=0;
		for (String id : idsstr) {
			tbItem.setId(Long.parseLong(id));
			tbItem.setStatus(status);
			tbItem.setUpdated(date);
			index+=tbItemDubboServiceImpl.updItemStatus(tbItem);
		}
		if(index==idsstr.length){
			//数据同步到solr
			//将状态设置为2或3时直接从solr中删除数据
			if(status==(byte)2||status==(byte)3){
				for (String id : idsstr) {
					String key=itemKey+id;
					//判断redis中是否存在key
					if(jedisDaoImpl.exist(key)){
						//存在就删除
						jedisDaoImpl.del(key);
						jedisDaoImpl.del(itemDescKey+id);
					}
					//匿名内部类访问局部变量  局部变量必须是final修饰的   因为匿名内部类的生命周期可能长于局部变量   
					final String idFinal = id; 
					new Thread(){
						@Override
						public void run() {
							//使用 java 代码调用其他项目的控制器  
							//只能传一个值  只能通过map或者对象
							HttpClientUtil.doPostJson(deleteurl, idFinal);
						}
					}.start();
				}
			}else {
				//上架商品时将商品录入到solr
				for (String id : idsstr) {
					//匿名内部类访问局部变量  局部变量必须是final修饰的   因为匿名内部类的生命周期可能长于局部变量   
					final TbItem itemFinal = tbItemDubboServiceImpl.selById(Long.parseLong(id)); 
					final String descFinal = tbItemDescDubboServiceImpl.selByItemId(Long.parseLong(id)).getItemDesc();
					new Thread(){
						@Override
						public void run() {
							Map<String, Object> map=new HashMap<String, Object>();
							map.put("item", itemFinal);
							map.put("desc", descFinal);
							//使用 java 代码调用其他项目的控制器  
							//只能传一个值  只能通过map或者对象
							HttpClientUtil.doPostJson(addurl, JsonUtils.objectToJson(map));
						}
					}.start();
				}
			}
			return 1;
		}
		return 0;
	}
	@Override
	public int insTbItem(TbItem tbItem, String desc,String itemParams) throws Exception {
		// 不考虑事务回滚
		// long id = IDUtils.genItemId();
		// item.setId(id);
		// Date date = new Date();
		// item.setCreated(date);
		// item.setUpdated(date);
		// item.setStatus((byte)1);
		// int index = tbItemDubboServiceImpl.insTbItem(item);
		// if(index>0){
		// TbItemDesc itemDesc = new TbItemDesc();
		// itemDesc.setItemDesc(desc);
		// itemDesc.setItemId(id);
		// itemDesc.setCreated(date);
		// itemDesc.setUpdated(date);
		// index+=tbItemDescDubboService.insDesc(itemDesc);
		// }
		// if(index==2){
		// return 1;
		// }
		
		//考虑事务回滚
		long id = IDUtils.genItemId();
		Date date = new Date();
		tbItem.setId(id);
		//设置商品状态  刚插入的状态为上架即为1
		tbItem.setStatus((byte)1);
		tbItem.setCreated(date);
		tbItem.setUpdated(date);
		
		TbItemDesc tbItemDesc=new TbItemDesc();
		tbItemDesc.setItemId(id);
		tbItemDesc.setCreated(date);
		tbItemDesc.setUpdated(date);
		tbItemDesc.setItemDesc(desc);
		
		//TbItemParam表的id自增可以不设置
		TbItemParamItem tbItemParamItem = new TbItemParamItem();
		tbItemParamItem.setItemId(id);
		tbItemParamItem.setCreated(date);
		tbItemParamItem.setParamData(itemParams);
		tbItemParamItem.setUpdated(date);
		int index=0;
		index=tbItemDubboServiceImpl.insItem(tbItem, tbItemDesc, tbItemParamItem);
		//因为匿名内部类需要让属性为final
		if(index>0){
			//solr数据同步
			final TbItem itemFinal = tbItem; 
			final String descFinal = desc;
			new Thread(){
				@Override
				public void run() {
					Map<String, Object> map=new HashMap<String, Object>();
					map.put("item", itemFinal);
					map.put("desc", descFinal);
					//使用 java 代码调用其他项目的控制器  
					//只能传一个值  只能通过map或者对象
					HttpClientUtil.doPostJson(addurl, JsonUtils.objectToJson(map));
				}
			}.start();
		}
		return index;
	}
	@Override
	public int updateItem(TbItem tbItem, String desc, String itemParams, long itemParamId) throws Exception {
		Date date = new Date();
		//设置商品状态  刚修改状态为上架即为1
		tbItem.setStatus((byte)1);
		tbItem.setUpdated(date);
		
		TbItemDesc tbItemDesc=new TbItemDesc();
		tbItemDesc.setItemId(tbItem.getId());
		tbItemDesc.setUpdated(date);
		tbItemDesc.setItemDesc(desc);
		
		TbItemParamItem tbItemParamItem = new TbItemParamItem();
		tbItemParamItem.setId(itemParamId);;
		tbItemParamItem.setParamData(itemParams);
		tbItemParamItem.setUpdated(date);
		int index=tbItemDubboServiceImpl.updateItem(tbItem, tbItemDesc, tbItemParamItem);
		if(index>0){
			TbItem ti = tbItemDubboServiceImpl.selById(tbItem.getId());
			String key=itemKey+ti.getId();
			//判断redis中是否存在商品key    redis数据同步
			if (jedisDaoImpl.exist(key)) {
				TbItemChild child = new TbItemChild();
				child.setId(ti.getId());
				child.setTitle(ti.getTitle());
				child.setPrice(ti.getPrice());
				child.setSellPoint(ti.getSellPoint());
				child.setImages(ti.getImage() != null && !ti.equals("") ? ti.getImage().split(",") : new String[1]);
				// 存在就更新到redis中
				jedisDaoImpl.set(key, JsonUtils.objectToJson(child));
				jedisDaoImpl.set(itemDescKey+ti.getId(), desc);
			}
//			//判断redis中是否存在商品描述key    redis数据同步
//			if (jedisDaoImpl.exist(itemDescKey+ti.getId())) {
//				// 存在就更新到redis中
//			}
			//solr数据同步
			final TbItem itemFinal = ti; 
			final String descFinal = desc;
			new Thread(){
				@Override
				public void run() {
					Map<String, Object> map=new HashMap<String, Object>();
					map.put("item", itemFinal);
					map.put("desc", descFinal);
					//使用 java 代码调用其他项目的控制器  
					//只能传一个值  只能通过map或者对象
					HttpClientUtil.doPostJson(addurl, JsonUtils.objectToJson(map));
				}
			}.start();
		}
		return index;
	}

}
