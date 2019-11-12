package com.ego.item.pojo;

import java.util.List;

/**
 * 存储节点信息   主要是类目信息
 * 例如{"data":[{"u":"/products/1.html","n":"<a href='/products/1.html'>图书、音像、电子书刊</a>",
 * "i":[{"u":"/products/2.html","n":"电子书刊",
 * "i":["/products/3.html|电子书","/products/4.html|网络原创","/products/5.html|数字杂志","/products/6.html|多媒体图书"]},{"u":"/products/7.html","n":"音像","i":["/products/8.html|音乐","/products/9.html|影视","/products/10.html|教育音像"]},{"u":"/products/11.html","n":"英文原版","i":["/products/12.html|少儿","/products/13.html|商务投资","/products/14.html|英语学习与考试","/products/15.html|小说","/products/16.html|传记","/products/17.html|励志"]},{"u":"/products/18.html","n":"文艺","i":["/products/19.html|小说","/products/20.html|文学","/products/21.html|青春文学","/products/22.html|传记","/products/23.html|艺术"]},{"u":"/products/24.html","n":"少儿","i":["/products/25.html|少儿","/products/26.html|0-2岁","/products/27.html|3-6岁","/products/28.html|7-10岁","/products/29.html|11-14岁"]},{"u":"/products/30.html","n":"人文社科","i":["/products/31.html|历史","/products/32.html|哲学","/products/33.html|国学","/products/34.html|政治/军事","/products/35.html|法律","/products/36.html|宗教","/products/37.html|心理学","/products/38.html|文化","/products/39.html|社会科学"]}
 * @author pizzafast
 *
 */
public class PortalMenuNode {
	/**
	 * 父节点点击跳转
	 */
	private String u;
	/**
	 * 类目名称
	 */
	private String n;
	/**
	 * 子节点信息
	 */
	private List<Object> i;
	public String getU() {
		return u;
	}
	public void setU(String u) {
		this.u = u;
	}
	public String getN() {
		return n;
	}
	public void setN(String n) {
		this.n = n;
	}
	public List<Object> getI() {
		return i;
	}
	public void setI(List<Object> i) {
		this.i = i;
	}
	
	
}
