package com.ego.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.ego.dubbo.service.TbItemParamItemDubboService;
import com.ego.mapper.TbItemParamItemMapper;
import com.ego.pojo.TbItemParamItem;
import com.ego.pojo.TbItemParamItemExample;

public class TbItemParamItemDubboServiceImpl implements TbItemParamItemDubboService{
	@Resource
	private TbItemParamItemMapper tbItemParamItemMapper;
	@Override
	public TbItemParamItem selByItemId(long itemId) {
		TbItemParamItemExample example = new TbItemParamItemExample();
		example.createCriteria().andItemIdEqualTo(itemId);
		List<TbItemParamItem> paramItems = tbItemParamItemMapper.selectByExampleWithBLOBs(example);
		if(paramItems!=null&&paramItems.size()>0){
			return paramItems.get(0);
		}
		return null;
	}

}
