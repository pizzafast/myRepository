package com.ego.manage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbContentDubboService;
import com.ego.manage.service.TbContentService;
import com.ego.pojo.TbContent;
import com.ego.redis.dao.JedisDao;

@Service
public class TbContentServiceImpl implements TbContentService{
	@Reference
	private TbContentDubboService tbContentDubboServiceImpl;
	@Resource
	private JedisDao jedisDaoImpl;
	
	@Value("${content.big.pic.key}")
	private String bigPic;
	@Override
	public EasyUIDataGrid showByPage(long categoryId, int page, int rows) {
		EasyUIDataGrid uid = tbContentDubboServiceImpl.selByPage(categoryId, page, rows);
		//categoryId为89就是大广告的信息
		if(categoryId==89){
			//如果从数据库中查出没有值就不设入redis
			List<TbContent> list = (List<TbContent>) uid.getRows();
			if(list!=null&&list.size()>0){
				if(!jedisDaoImpl.exist(bigPic)){
					//前台需要这种格式
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
					//大于6时就截取前面6个
					if(list.size()>6){
						jedisDaoImpl.set(bigPic, JsonUtils.objectToJson(listResult.subList(0, 6)));
					}else {
						jedisDaoImpl.set(bigPic, JsonUtils.objectToJson(listResult));
					}
				}
			}
		}
		return uid;
	}

	@Override
	public EgoResult insContent(TbContent tbContent) {
		EgoResult ego=new EgoResult();
		Date date = new Date();
		tbContent.setCreated(date);
		tbContent.setUpdated(date);
		int index = tbContentDubboServiceImpl.insCate(tbContent);
		if(index>0){
			//判断redis中是否存在key
			if(jedisDaoImpl.exist(bigPic)){
				String value = jedisDaoImpl.get(bigPic);
				if(value!=null&&!value.equals("")){
					List<HashMap> listMap = JsonUtils.jsonToList(value, HashMap.class);
					HashMap<String, Object> map = new HashMap<>();
					map.put("srcB", tbContent.getPic2());
					map.put("height", 240);
					map.put("alt", "对不起,加载图片失败");
					map.put("width", 670);
					map.put("src", tbContent.getPic());
					map.put("widthB", 550);
					map.put("href", tbContent.getUrl());
					map.put("heightB", 240);
					//这个数组中只能存在6个
					if(listMap.size()==6){
						//移除最后一个
						listMap.remove(5);
					}
					//将新添加的加入到第一个
					listMap.add(0, map);
					//将更新之后的集合重新设入redis中
					jedisDaoImpl.set(bigPic, JsonUtils.objectToJson(listMap));
				}
			}
			ego.setStatus(200);
		}
		return ego;
	}

}
