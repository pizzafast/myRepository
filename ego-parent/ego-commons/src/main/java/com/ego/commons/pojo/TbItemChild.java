package com.ego.commons.pojo;

import com.ego.pojo.TbItem;

/**
 * 前台需要将图片以集合形式发送过去
 * @author pizzafast
 *
 */
public class TbItemChild extends TbItem{
	private String [] images;

	public String[] getImages() {
		return images;
	}

	public void setImages(String[] images) {
		this.images = images;
	}
	
}
