package com.ego.manage.service;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.pojo.TbItem;

public interface TbItemService {
	/**
	 * 分页查询
	 * @param page 查第几页
	 * @param rows 一页的数据量
	 * @return
	 */
	EasyUIDataGrid show(int page,int rows);
	int insTbItem(TbItem tbItem,String desc,String itemParams) throws Exception;
	/**
	 * 批量修改商品状态
	 * @param ids  需要修改商品的id
	 * @param status  商品修改的状态值 1上架 2下架  3删除
	 * @return
	 */
	int update(String ids,byte status);
	
	/**
	 * 编辑商品  更新商品 以及 描述  以及规格参数
	 * @param tbItem
	 * @param desc
	 * @param itemParams
	 * @param itemParamId
	 * @return
	 */
	int updateItem(TbItem tbItem,String desc,String itemParams,long itemParamId)throws Exception;
}
