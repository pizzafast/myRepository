package com.ego.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
	/**
	 * 访问前台首页   因为xml中配置了视图解析器  所以必须要添加forward才能转发到另一个控制器
	 * @return
	 */
	@RequestMapping("/")
	public String welcome() {
		return "forward:/showBigPic";
	}
}
