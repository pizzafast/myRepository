package com.ego.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbContentDubboService;
import com.ego.pojo.TbContent;
import com.ego.portal.service.TbContentService;
import com.ego.redis.dao.JedisDao;

@Service
public class TbContentServiceImpl implements TbContentService {
	@Reference
	private TbContentDubboService tbContentDubboServiceImpl;

	@Resource
	private JedisDao jedisDaoImpl;
	@Value("${content.big.pic.key}")
	private String bigPic_key;

	@Override
	public String selByCount() {
		// 如果redis中存在及直接取
		if (jedisDaoImpl.exist(bigPic_key)) {
			String value = jedisDaoImpl.get(bigPic_key);
			if (value != null && !value.equals("")) {
				return value;
			}
		}
		// 不存在就在数据库中取
		List<TbContent> list = tbContentDubboServiceImpl.selByCount(6, true);
		List<Map<String, Object>> listResult = new ArrayList<>();
		for (TbContent tc : list) {
			Map<String, Object> map = new HashMap<>();
			map.put("srcB", tc.getPic2());
			map.put("height", 240);
			map.put("alt", "对不起,加载图片失败");
			map.put("width", 670);
			map.put("src", tc.getPic());
			map.put("widthB", 550);
			map.put("href", tc.getUrl());
			map.put("heightB", 240);
			listResult.add(map);
		}
		//取出后再将从数据库中获取的数据存储到redis中
		String listJson = JsonUtils.objectToJson(listResult);
		jedisDaoImpl.set(bigPic_key, listJson);
		return listJson;

	}

}
