package com.ego.item.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.item.service.TbItemDescService;

@Controller
public class TbItemDescController {
	@Resource
	private TbItemDescService tbItemDescServiceImpl;
	
	
	/**
	 * 显示商品描述
	 *  使用 js 的 setTimeout 延迟 1 秒加载,提升商品详细信息
	 *  以后项目中如果页面数据过大,可以考虑一些不是立即看见的内容选择延迟加载.
	 * @param id
	 * @return
	 */
	//返回json数据时 如果返回值为字符串为了防止中文乱码需要添加属性produces
	@RequestMapping(value="item/desc/{id}.html",produces="text/html;charset=utf-8") 
	@ResponseBody
	public String showItemDesc(@PathVariable long id) {
		return tbItemDescServiceImpl.itemDesc(id);
	}
}
