package com.ego.commons.pojo;

/**
 * 将规格参数以对象形式存储 方便前端获取数据
 * @author pizzafast
 *
 */
public class ParamNode {
	//建  即属性名称
	private String k;
	//值 属性的值
	private String v;
	public String getK() {
		return k;
	}
	public void setK(String k) {
		this.k = k;
	}
	public String getV() {
		return v;
	}
	public void setV(String v) {
		this.v = v;
	}
	@Override
	public String toString() {
		return "ParamNode [k=" + k + ", v=" + v + "]";
	}
	
}
