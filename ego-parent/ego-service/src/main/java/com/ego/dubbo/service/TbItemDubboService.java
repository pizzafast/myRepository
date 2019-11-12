package com.ego.dubbo.service;

import java.util.List;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParamItem;

public interface TbItemDubboService {
	/**
	 * 分页查询
	 * @param page 查第几页
	 * @param rows 一页的数据量
	 * @return
	 */
	EasyUIDataGrid show(int page,int rows);
	
	/**
	 * 根据主键查找
	 * @param id
	 * @return
	 */
	TbItem selById(long id);
	
	/**
	 * 根据商品状态查询商品
	 * @param status
	 * @return
	 */
	List<TbItem> selByStatus(byte status);
	/**
	 * 新增 商品  包括他的基本信息   描述信息   以及规格参数信息
	 * @param tbItem
	 * @param tbItemDesc
	 * @param tbItemParamItem
	 * @return
	 * @throws Exception
	 */
	int insItem(TbItem tbItem,TbItemDesc tbItemDesc,TbItemParamItem tbItemParamItem)throws Exception;
	/**
	 * 根据id修改状态
	 * @param id
	 * @param status
	 * @return
	 */
	int updItemStatus(TbItem tbItem);
	/**
	 * 编辑商品  修改商品信息及描述信息  以及规格参数
	 * @param tbItem
	 * @param tbItemDesc
	 * @param tbItemParamItem
	 * @return
	 */
	int updateItem(TbItem tbItem,TbItemDesc tbItemDesc,TbItemParamItem tbItemParamItem) throws Exception;
}
