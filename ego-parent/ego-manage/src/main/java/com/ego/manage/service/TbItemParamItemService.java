package com.ego.manage.service;

import com.ego.commons.pojo.EgoResult;

public interface TbItemParamItemService {
	/**
	 * 通过商品id查询商品规格参数
	 * @param itemId
	 * @return
	 */
	EgoResult selParamItemByItemId(long itemId);
}
