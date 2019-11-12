package com.ego.manage.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbItemDescService;

@Controller
public class TbItemDescController {
	@Resource
	private TbItemDescService tbItemDescServiceImpl;
	
	/**
	 * 根据商品id查询商品描述
	 * @param id
	 * @return
	 */
	@RequestMapping("rest/item/query/item/desc/{id}")
	@ResponseBody
	public EgoResult getItemDesc(@PathVariable long id) {
		return tbItemDescServiceImpl.selItemDesc(id);
	}
}
