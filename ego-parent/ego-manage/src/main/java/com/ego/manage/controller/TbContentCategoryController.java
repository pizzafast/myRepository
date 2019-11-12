package com.ego.manage.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.EasyUiTree;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbContentCategoryService;
import com.ego.pojo.TbContentCategory;

@Controller
public class TbContentCategoryController {
	@Resource
	private TbContentCategoryService tbContentCategoryServiceImpl;
	
	/**
	 * 显示内容分类的类目
	 * @param id
	 * @return
	 */
	@RequestMapping("content/category/list")
	@ResponseBody
	public List<EasyUiTree> showALL(@RequestParam(defaultValue="0") long id) {
		return tbContentCategoryServiceImpl.selAllCategroy(id);
	}
	
	/**
	 * 插入内容类目
	 * @param category
	 * @return
	 */
	@RequestMapping("content/category/create")
	@ResponseBody
	public EgoResult insCate(TbContentCategory category) {
		EgoResult ego=new EgoResult();
		try {
				ego= tbContentCategoryServiceImpl.insCategory(category);
		} catch (Exception e) {
			//e.printStackTrace();
			ego.setData(e.getMessage());;
		}
		return ego;
	}
	
	/**
	 * 重命名
	 * @param category
	 * @return
	 */
	@RequestMapping("content/category/update")
	@ResponseBody
	public EgoResult update(TbContentCategory category) {
		return tbContentCategoryServiceImpl.updCategry(category);
	}
	
	/**
	 * 逻辑删除
	 * @param category
	 * @return
	 */
	@RequestMapping("content/category/delete")
	@ResponseBody
	public EgoResult delete(TbContentCategory category) {
		EgoResult egoResult = new EgoResult();
		try {
			int index = tbContentCategoryServiceImpl.del(category);
			if (index>0) {
				egoResult.setStatus(200);
			}
		} catch (Exception e) {
			//e.printStackTrace();
			egoResult.setData(e.getMessage());
		}
		return egoResult;
		
	}
}
