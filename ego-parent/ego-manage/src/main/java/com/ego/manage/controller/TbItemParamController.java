package com.ego.manage.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbItemParamService;
import com.ego.pojo.TbItemParam;

@Controller
public class TbItemParamController {
	@Resource
	private TbItemParamService tbItemParamServiceImpl;
	
	/**
	 * 分页显示规格参数
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("item/param/list")
	@ResponseBody
	public EasyUIDataGrid showParams(int page,int rows) {
		return tbItemParamServiceImpl.showPage(page, rows);
	}
	/**
	 * 删除相应的规格参数
	 * @param ids  id
	 * @return
	 */
	@RequestMapping("item/param/delete")
	@ResponseBody
	public EgoResult del(String ids) {
		EgoResult ego = new EgoResult();
		try {
			int index = tbItemParamServiceImpl.delParam(ids);
			if(index>0){
				ego.setStatus(200);
			}
		} catch (Exception e) {
//			e.printStackTrace();
			ego.setData(e.getMessage());
		}
		return ego;
	}
	/**
	 * 根据类目id查找规格参数模板
	 * @param catid
	 * @return
	 */
	@RequestMapping("item/param/query/itemcatid/{catid}")
	@ResponseBody
	public EgoResult showParam(@PathVariable long catid) {//使用restful风格要使用注解@PathVariable
		return tbItemParamServiceImpl.selByCatId(catid);
	}
	
	/**
	 * 新增类目的规格参数模板   前端已经实现基本逻辑  即有类目拥有规格参数模板的不再允许添加
	 * @param tbItemParam
	 * @param catid  类目的id
	 * @return
	 */
	@RequestMapping("item/param/save/{catid}")
	@ResponseBody
	public EgoResult insert(TbItemParam tbItemParam,@PathVariable long catid) {
		tbItemParam.setItemCatId(catid);
		return tbItemParamServiceImpl.insParam(tbItemParam);
		
	}
}
