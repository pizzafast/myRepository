package com.ego.dubbo.service;

import com.ego.pojo.TbItemDesc;

public interface TbItemDescDubboService {
	/**
	 * 根据商品id查询商品描述
	 * @param id
	 * @return
	 */
	TbItemDesc selByItemId(long itemId);
}
