package com.ego.dubbo.service;

import java.util.List;

import com.ego.pojo.TbContentCategory;

/**
 * 内容分类管理
 * @author pizzafast
 *
 */
public interface TbContentCategoryDubboService {
	/**
	 * 根据父类id查找内容分类类目
	 * @param pid
	 * @return
	 */
	List<TbContentCategory> selByPid(long pid);
	
	/**
	 * 新增内容类目
	 * @param category
	 * @return
	 */
	int insCategory(TbContentCategory category,TbContentCategory categoryParent)throws Exception;
	
	/**
	 * 根据id查询内容类目信息
	 * @param id
	 * @return
	 */
	TbContentCategory selById(long id);
	
	/**
	 * 重命名内容类目
	 * @param category
	 * @return
	 */
	int updCategory(TbContentCategory category);
	
	/**
	 * 逻辑删除  不是真正的删除   实际是更新操作  将status设置为0
	 * @param id
	 * @return
	 */
	int del(TbContentCategory category)throws Exception ;
	
}
