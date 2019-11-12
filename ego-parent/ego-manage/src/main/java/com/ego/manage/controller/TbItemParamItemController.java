package com.ego.manage.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbItemParamItemService;

@Controller
public class TbItemParamItemController {
	@Resource
	private TbItemParamItemService tbItemParamItemServiceImpl;
	
	/**
	 * 通过商品id获取规格参数
	 * @param id
	 * @return
	 */
	@RequestMapping("rest/item/param/item/query/{id}")
	@ResponseBody
	public EgoResult showParamItem(@PathVariable long id) {
		return tbItemParamItemServiceImpl.selParamItemByItemId(id);
	}
}
