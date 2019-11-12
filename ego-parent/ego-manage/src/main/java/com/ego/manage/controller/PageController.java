package com.ego.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
	
	/**
	 * 通过不输入直接访问首页
	 * @return
	 */
	@RequestMapping("/")
	public String welcome() {
		return "index";
	}
	/**
	 * 通过使用rest风格  将用户输入的url进行获取  然后找相应的控制器执行   
	 * @param url
	 * @return
	 */
	@RequestMapping("{url}")//@PathVariable获取rest风格的值
	public String page(@PathVariable String url) {
		return url;
	}

}
