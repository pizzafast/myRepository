package com.ego.manage.service.impl;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EgoResult;
import com.ego.dubbo.service.TbItemParamItemDubboService;
import com.ego.manage.service.TbItemParamItemService;
import com.ego.pojo.TbItemParamItem;
@Service
public class TbItemParamItemServiceImpl implements TbItemParamItemService{
	@Reference
	private TbItemParamItemDubboService tbItemParamItemDubboServiceImpl;
	@Override
	public EgoResult selParamItemByItemId(long itemId) {
		EgoResult ego = new EgoResult();
		TbItemParamItem paramItem = tbItemParamItemDubboServiceImpl.selByItemId(itemId);
		if(paramItem!=null){
			ego.setStatus(200);
			//因为前台需要的数据TbItemParamItem都包含  所以直接返回对象即可
			ego.setData(paramItem);
		}
		return ego;
	}

}
