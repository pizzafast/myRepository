package com.ego.manage.pojo;

import com.ego.pojo.TbItemParam;
/**
 * 返回前端的数据中缺少itemCatName这个属性
 * 因为TbItemParam中的数据不够完全     所以利用多态  在新建一个类
 * @author pizzafast
 *
 */
public class TbItemParamChild extends TbItemParam{
	//所在类型的名称
	private String itemCatName;

	public String getItemCatName() {
		return itemCatName;
	}

	public void setItemCatName(String itemCatName) {
		this.itemCatName = itemCatName;
	}
	
}
