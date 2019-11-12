package com.ego.commons.pojo;

/**
 * 增删改时使用
 * @author pizzafast
 *
 */
public class EgoResult {
	private int status;
	private Object data;
	

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
