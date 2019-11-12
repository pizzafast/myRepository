package com.ego.manage.service;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbItemParam;

public interface TbItemParamService {
	/**
	 * 分页查询规格参数 
	 * @param page 查询第几页
	 * @param rows  一页的数据量
	 * @return
	 */
	EasyUIDataGrid showPage(int page,int rows);
	
	/**
	 * 根据id删除规格参数
	 * @param ids
	 * @return
	 */
	int delParam(String ids)throws Exception;
	/**
	 * 新增规格参数
	 * @param tbItemParam
	 * @return
	 */
	EgoResult insParam(TbItemParam tbItemParam);
	/**
	 * 根据类目id查找规格参数模板
	 * @param cid 类目id
	 * @return
	 */
	EgoResult selByCatId(long cid);
}
