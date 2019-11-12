package com.ego.manage.service;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbContent;

public interface TbContentService {
	/**
	 * 根据category的id查询内容
	 * @param categoryId
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUIDataGrid showByPage(long categoryId,int page,int rows);
	
	/**
	 * 插入内容信息
	 * @param tbContent
	 * @return
	 */
	EgoResult insContent(TbContent tbContent);
}
