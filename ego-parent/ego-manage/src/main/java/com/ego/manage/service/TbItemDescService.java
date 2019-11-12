package com.ego.manage.service;

import com.ego.commons.pojo.EgoResult;

public interface TbItemDescService {
	/**
	 * 获取商品描述
	 * @param itemId
	 * @return
	 */
	EgoResult selItemDesc(long itemId);
}
