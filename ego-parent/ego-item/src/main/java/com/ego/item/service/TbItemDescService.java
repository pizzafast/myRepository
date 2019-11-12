package com.ego.item.service;

public interface TbItemDescService {
	/**
	 * 根据商品id查找商品描述
	 * @param itemId
	 * @return
	 */
	String itemDesc(long itemId);
}
