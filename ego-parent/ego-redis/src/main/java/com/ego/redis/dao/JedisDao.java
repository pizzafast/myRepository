package com.ego.redis.dao;

public interface JedisDao {
	/**
	 * 判断redis是否存在key
	 * @param key
	 * @return
	 */
	boolean exist(String key);
	/**
	 * 设置key value
	 * @param key
	 * @param value
	 * @return
	 */
	String set(String key,String value);
	/**
	 * 获取key
	 * @param key
	 * @return
	 */
	String get(String key);
	/**
	 * 删除redis中key
	 * @param key
	 * @return
	 */
	Long del(String key);
	
}
