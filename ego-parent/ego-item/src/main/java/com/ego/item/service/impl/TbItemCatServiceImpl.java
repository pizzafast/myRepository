package com.ego.item.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.TbItemChild;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.item.pojo.PortalMenu;
import com.ego.item.pojo.PortalMenuNode;
import com.ego.item.service.TbItemCatService;
import com.ego.pojo.TbItemCat;
import com.ego.redis.dao.JedisDao;

@Service
public class TbItemCatServiceImpl implements TbItemCatService{
	@Reference
	private TbItemCatDubboService tbItemCatDubboServiceImpl;//对商品类目操作  服务中已经存在
	@Resource
	private JedisDao jedisDaoImpl;
	@Value("${item.cat.key}")
	private String cat_key;
	@Override
	public PortalMenu selAllCat() {
		//如果redis存在key就直接取
		if(jedisDaoImpl.exist(cat_key)){
			String cats = jedisDaoImpl.get(cat_key);
			if(cats!=null&&!cats.equals("")){
				return JsonUtils.jsonToPojo(cats, PortalMenu.class);
			}
		}
		//redis不存在就从数据库取  并且将查出来的放入redis中
		PortalMenu menu=new PortalMenu();
		//查询出所有的父类目
		List<TbItemCat> itemCats = tbItemCatDubboServiceImpl.selItemCat(0);
		menu.setData(selChildCat(itemCats));
		jedisDaoImpl.set(cat_key, JsonUtils.objectToJson(menu));
		return menu;
	}
	
	private List<Object> selChildCat(List<TbItemCat> cats){
		List<Object> nodes=new ArrayList<Object>();
		for (TbItemCat cat : cats) {
			//如果此节点时父节点  就递归查询
			if(cat.getIsParent()){
				PortalMenuNode node=new PortalMenuNode();
				node.setU("/products/"+cat.getId()+".html");
				node.setN("<a href='/products/"+cat.getId()+".html'>"+cat.getName()+"</a>");
				node.setI(selChildCat(tbItemCatDubboServiceImpl.selItemCat(cat.getId())));
				nodes.add(node);
			}else{//不是父节点就直接添加
				nodes.add("/products/"+cat.getId()+".html|"+cat.getName());
			}
		}
		return nodes;
	}

}
