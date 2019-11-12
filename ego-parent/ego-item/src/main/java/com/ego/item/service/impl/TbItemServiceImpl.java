package com.ego.item.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.TbItemChild;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.item.service.TbItemService;
import com.ego.pojo.TbItem;
import com.ego.redis.dao.JedisDao;

@Service
public class TbItemServiceImpl implements TbItemService {
	@Reference
	private TbItemDubboService tbItemDubboServiceImpl;

	@Value("${redis.item.key}")
	private String itemKey;
	@Resource
	private JedisDao jedisDaoImpl;
	@Override
	public TbItemChild showTbItem(long id) {
		String key=itemKey+id;
		//因为后台中同步了solr数据  所以后台就不需要同步redis中的商品  因为
		//判断redis中是否存在
		if(jedisDaoImpl.exist(key)){
			String child = jedisDaoImpl.get(key);
			if(child!=null&&!child.equals("")){
				return JsonUtils.jsonToPojo(child, TbItemChild.class);
			}
		}
		TbItem tbItem = tbItemDubboServiceImpl.selById(id);
		TbItemChild child = new TbItemChild();
		if (tbItem != null) {
			child.setId(tbItem.getId());
			child.setTitle(tbItem.getTitle());
			child.setPrice(tbItem.getPrice());
			child.setSellPoint(tbItem.getSellPoint());
			child.setImages(tbItem.getImage() != null && !tbItem.equals("") ? tbItem.getImage().split(",") : new String[1]);
			//存入到redis中
			jedisDaoImpl.set(key, JsonUtils.objectToJson(child));
		}
		return child;
	}

}
