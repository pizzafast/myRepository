package com.ego.manage.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbContentService;
import com.ego.pojo.TbContent;

@Controller
public class TbContentController {
	@Resource
	private TbContentService tbContentServiceImpl;
	
	/**
	 * 显示内容分类的信息
	 * @param categoryId
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("content/query/list")
	@ResponseBody
	public EasyUIDataGrid showContent(long categoryId, int page, int rows) {
		return tbContentServiceImpl.showByPage(categoryId, page, rows);
	}
	
	/**
	 * 新增内容信息
	 * @param tbContent
	 * @return
	 */
	@RequestMapping("content/save")
	@ResponseBody
	public EgoResult insert(TbContent tbContent) {
		return tbContentServiceImpl.insContent(tbContent);
	}
}

