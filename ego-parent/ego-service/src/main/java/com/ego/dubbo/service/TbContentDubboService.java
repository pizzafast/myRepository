package com.ego.dubbo.service;

import java.util.List;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.pojo.TbContent;

public interface TbContentDubboService {
	/**
	 * 根据TbContentCategory的id查询数据
	 * @param categoryId
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUIDataGrid selByPage(long categoryId,int page,int rows);
	
	/**
	 * 查询出最近的前 n 个 
	 * @param count   需要的数据量
	 * @param isSort  是否排序
	 * @return
	 */
	List<TbContent> selByCount(int count,boolean isSort);
	
	/**
	 * 根据category的id插入内容信息
	 * @param tbContent
	 * @return
	 */
	int insCate(TbContent tbContent);
}
