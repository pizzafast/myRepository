package com.ego.item.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.item.service.TbItemDescService;
import com.ego.pojo.TbItemDesc;
import com.ego.redis.dao.JedisDao;
@Service
public class TbItemDescServiceImpl implements TbItemDescService{
	@Reference
	private TbItemDescDubboService tbItemDescDubboServiceImpl;
	@Value("${redis.desc.key}")
	private String itemDescKey;//redis中商品描述key
	@Resource
	private JedisDao jedisDaoImpl;
	@Override
	public String itemDesc(long itemId) {
		String key=itemDescKey+itemId;
		//从redis中取
		if(jedisDaoImpl.exist(key)){
			String desc = jedisDaoImpl.get(key);
			if(desc!=null&&!desc.equals("")){
				return desc;
			}
		}
		TbItemDesc itemDesc = tbItemDescDubboServiceImpl.selByItemId(itemId);
		//录入到redis中
		jedisDaoImpl.set(key, itemDesc.getItemDesc());
		return itemDesc.getItemDesc();
	}

}
