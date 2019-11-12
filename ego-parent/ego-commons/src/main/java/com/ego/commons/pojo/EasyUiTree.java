package com.ego.commons.pojo;

/**
 * 查询类目时使用  一层一层查询
 * @author pizzafast
 *
 */
public class EasyUiTree {
	private long id;
	private String text; 
	private String state;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
}
