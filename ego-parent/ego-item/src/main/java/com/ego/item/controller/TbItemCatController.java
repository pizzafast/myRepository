package com.ego.item.controller;

import javax.annotation.Resource;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.item.service.TbItemCatService;

@Controller
public class TbItemCatController {
	@Resource
	private TbItemCatService itemServiceImpl;
	
	/**
	 * 查询所有的类目信息  另一个项目需要访问此控制器  需要使用jsonp格式传输
	 * @return
	 */
	@RequestMapping("rest/itemcat/all")
	@ResponseBody
	public MappingJacksonValue showCat(String callback) {
		//MappingJacksonValue为springmvc提供了的一种封装返回jsonp格式的方法
		//把构造方法参数转换为json字符串并当作最终返回值函数的参数
		MappingJacksonValue mjv = new MappingJacksonValue(itemServiceImpl.selAllCat());
		mjv.setJsonpFunction(callback);//callback为js需要调用的方法名的参数名   对应js中jsonp的值为传入控制器的参数名   即 callback 类似于getParameter("参数名");
		//jsonpCallback的值为传入控制器的参数的值
		return mjv;
	}
}
