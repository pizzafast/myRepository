package com.ego.commons.pojo;

import java.util.List;

/**
 * 将规格参数以对象形式存储 方便前端获取数据
 * @author pizzafast
 *
 */
public class ParamItem {
	//规格参数的group名称
	private String group; 
	//group中的属性键值对
	private List<ParamNode> params;
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public List<ParamNode> getParams() {
		return params;
	}
	public void setParams(List<ParamNode> params) {
		this.params = params;
	}
	@Override
	public String toString() {
		return "ParamItem [group=" + group + ", params=" + params + "]";
	}
	
	
}
