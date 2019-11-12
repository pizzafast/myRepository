package com.ego.manage.service;

import java.util.List;

import com.ego.commons.pojo.EasyUiTree;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbContentCategory;

public interface TbContentCategoryService {
	/**
	 * 查找所有内容分类类目
	 * @return
	 */
	List<EasyUiTree> selAllCategroy(long pid);
	
	/**
	 * 新增内容类目   并将其点击的类目设置为父类目
	 * @param category
	 * @return
	 */
	EgoResult insCategory(TbContentCategory category)throws Exception;
	
	/**
	 * 重命名内容类目
	 * @param category
	 * @return
	 */
	EgoResult updCategry(TbContentCategory category);
	/**
	 * 逻辑删除  不是真正的删除   实际是更新操作  将status设置为0
	 * @param id
	 * @return
	 */
	int del(TbContentCategory category) throws Exception;
}
