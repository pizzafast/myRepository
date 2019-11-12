package com.ego.search.service;

import java.util.Map;

public interface TbItemService {
	
	/**
	 * 将solr初始化   将数据库中的数据录入到solr中
	 */
	void init()throws Exception;
	
	/**
	 * 根据前台搜索框中输入的从solr中查询  分页显示
	 * @param query
	 * @param rows
	 * @param page
	 * @return
	 */
	Map<String, Object> selByQuery(String query,int rows,int page) throws Exception;
	
	/**
	 * 当后台新增商品时  同时录入到solr中 
	 * @param tbItem java.util.LinkedHashMap cannot be cast to com.ego.pojo.TbItem
	 * @param desc
	 * @return
	 */
	int addToSolr(Map<String, Object> map,String desc) throws Exception ;
	
	/**
	 * 根据solr中的id删除数据
	 * @param id
	 * @return
	 */
	int deleteFromSolr(long id)throws Exception;
}
