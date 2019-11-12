package com.ego.dubbo.service;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.pojo.TbItemParam;

public interface TbItemParamDubboService {
	/**
	 * 分页查询 规格参数
	 * @param page 显示第几页
	 * @param rows 一页的数据量
	 * @return
	 */
	EasyUIDataGrid seBylPage(int page,int rows);
	/**
	 * 根据类型的id查找规格参数的模板   因为一个类型只能存在一个规格参数模板
	 * @param itemCatId
	 * @return
	 */
	TbItemParam selByCatID(long itemCatId);
	
	/**
	 * 新增规格参数模板
	 * @param tbItemParam
	 * @return
	 */
	int insParam(TbItemParam tbItemParam);
	
	/**
	 * 批量删除规格参数
	 * @param ids 规格参数的id  多个会被逗号隔开
	 * @return
	 */
	int delById(String ids)throws Exception;
}
