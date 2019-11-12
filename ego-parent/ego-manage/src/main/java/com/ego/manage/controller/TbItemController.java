package com.ego.manage.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbItemService;
import com.ego.pojo.TbItem;

@Controller
public class TbItemController {
	
	@Resource
	private TbItemService tbItemServiceImpl;
	
	@RequestMapping("item/list")
	@ResponseBody
	public EasyUIDataGrid show(int page,int rows) {
		return tbItemServiceImpl.show(page, rows);
	}
	
	/**
	 * 显示商品修改信息
	 * @return
	 */
	@RequestMapping("rest/page/item-edit")
	public String showItemEdit(){
		return "item-edit";
	}
	
	/**
	 * 商品删除  将状态设置为3  不是真正的删除   逻辑删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("rest/item/delete")
	@ResponseBody
	public EgoResult delete(String ids){
		EgoResult er = new EgoResult();
		int index = tbItemServiceImpl.update(ids, (byte)3);
		if(index==1){
			er.setStatus(200);
		}
		return er;
	}
	/**
	 * 商品下架  将状态设置为2
	 * @param ids
	 * @return
	 */
	@RequestMapping("rest/item/instock")
	@ResponseBody
	public EgoResult instock(String ids){
		EgoResult er = new EgoResult();
		int index = tbItemServiceImpl.update(ids, (byte)2);
		if(index==1){
			er.setStatus(200);
		}
		return er;
	}
	/**
	 * 商品上架  将状态设置为1
	 * @param ids
	 * @return
	 */
	@RequestMapping("rest/item/reshelf")
	@ResponseBody
	public EgoResult reshelf(String ids){
		EgoResult er = new EgoResult();
		int index = tbItemServiceImpl.update(ids, (byte)1);
		if(index==1){
			er.setStatus(200);
		}
		return er;
	}
	
	/**
	 * 插入商品
	 * @param tbItem
	 * @param desc  jsp中接收描述的参数名
	 * @return
	 */
	@RequestMapping("item/save")
	@ResponseBody
	public EgoResult insItem(TbItem tbItem,String desc,String itemParams){
		EgoResult result = new EgoResult();
		try {
			int index = tbItemServiceImpl.insTbItem(tbItem, desc,itemParams);
			if(index>0){
				result.setStatus(200);
			}
		} catch (Exception e) {
//			e.printStackTrace();
			result.setData(e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 编辑商品 修改商品信息  包括描述信息以及参数规格
	 * @param tbItem
	 * @param desc
	 * @param itemParams
	 * @param itemParamId
	 * @return
	 */
	@RequestMapping("rest/item/update")
	@ResponseBody
	public EgoResult updateItem(TbItem tbItem,String desc,String itemParams,long itemParamId) {
		EgoResult ego = new EgoResult();
		try {
			int index = tbItemServiceImpl.updateItem(tbItem, desc, itemParams, itemParamId);
			if(index>0){
				ego.setStatus(200);
			}
		} catch (Exception e) {
			//e.printStackTrace();
			ego.setData(e.getMessage());
		}
		return ego;
	}
}
