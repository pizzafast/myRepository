package com.ego.item.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.item.service.TbItemParamItemService;

@Controller
public class TbItemParamItemController {
	@Resource
	private TbItemParamItemService tbItemParamItemServiceImpl;
	
	
	/**
	 * 显示商品规格参数
	 * @param id
	 * @return
	 */
	//返回json数据时 如果返回值为字符串为了防止中文乱码需要添加属性produces
	@RequestMapping(value="item/param/{id}.html",produces="text/html;charset=utf-8")
	@ResponseBody
	public String showParamItem(@PathVariable long id) {
		return tbItemParamItemServiceImpl.showItemParam(id);
	}
}
