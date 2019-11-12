package com.ego.search.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.search.service.TbItemService;

@Controller
public class TbItemController {
	@Resource
	private TbItemService tbItemServiceImpl;

	/**
	 * 数据初始化 将商品数据全部插入到solr中
	 * 
	 * @return
	 */
	// produces为了防止中文乱码
	@RequestMapping(value = "solr/init", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String init() {
		long start = System.currentTimeMillis();
		try {
			tbItemServiceImpl.init();
			long end = System.currentTimeMillis();
			return "初始化总时间:" + (end - start) / 1000 + "秒";
		} catch (Exception e) {
			e.printStackTrace();
			return "初始化失败";
		}
	}

	/**
	 * 根据搜索中输入的内容从solr中查询数据
	 * @param model
	 * @param q
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("search.html")
	public String search(Model model, String q, @RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "12") int rows) {
		try {
			// 防止请求地址中文乱码
			q = new String(q.getBytes("iso-8859-1"), "utf-8");
			Map<String, Object> map = tbItemServiceImpl.selByQuery(q, rows, page);
			model.addAttribute("query", q);
			model.addAttribute("itemList", map.get("itemList"));
			model.addAttribute("totalPages", map.get("totalPages"));
			model.addAttribute("page", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "search";
	}
	
	/**
	 * solr同步后台新增商品数据   后台修改数据也是访问这个url  因为solr修改和新增差不多  只要id不变
	 * @RequestBody 把请求体中流数据转换为指定类型
	 * @param tbItem
	 * @param desc
	 * @return
	 */
	@RequestMapping("solr/add")
	@ResponseBody
	public int add(@RequestBody Map<String,Object> map) {
		try {
			//java.util.LinkedHashMap cannot be cast to com.ego.pojo.TbItem
			return  tbItemServiceImpl.addToSolr((LinkedHashMap)map.get("item"), map.get("desc").toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@RequestMapping("solr/delete")
	@ResponseBody
	public int delete(@RequestBody String id) {
		try {
			//java.util.LinkedHashMap cannot be cast to com.ego.pojo.TbItemg());
			return  tbItemServiceImpl.deleteFromSolr(Long.parseLong(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
