package com.ego.dubbo.service;

import java.util.List;

import com.ego.pojo.TbItemCat;

public interface TbItemCatDubboService {
	/**
	 * 根据父类目 id 查询所有子类目 
	 * @return
	 */
	List<TbItemCat> selItemCat(long pid);
	/**
	 * 根据主键查找 相应的类型
	 * @param id
	 * @return
	 */
	TbItemCat selById(long id);
}
