package com.ego.manage.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EgoResult;
import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.manage.service.TbItemDescService;
import com.ego.pojo.TbItemDesc;
@Service
public class TbItemDescServiceImpl implements TbItemDescService{
	@Reference
	private TbItemDescDubboService tbItemDescDubboServiceImpl;
	@Override
	public EgoResult selItemDesc(long itemId) {
		TbItemDesc desc = tbItemDescDubboServiceImpl.selByItemId(itemId);
		EgoResult ego = new EgoResult();
		if(desc!=null){
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("itemDesc", desc.getItemDesc());
			ego.setData(map);
			ego.setStatus(200);
		}
		return ego;
	}

}
