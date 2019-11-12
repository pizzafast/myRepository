package com.ego.item.service;

import com.ego.commons.pojo.TbItemChild;

public interface TbItemService {
	/**
	 * 通过主键查找商品信息
	 * @param id
	 * @return
	 */
	TbItemChild showTbItem(long id);
}
