package com.bjsxt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bjsxt.pojo.Users;
import com.bjsxt.service.UsersService;

@Controller
//@RequestMapping("/users")
public class UsersController {

	@Autowired
	private UsersService usersService;
	
	/**
	 * 页面跳转
	 */
	@RequestMapping("/{page}")
	public String showPage(@PathVariable String page){
		return page;
	}
	
	/**
	 * 添加用户
	 */
	@RequestMapping("/addUser")
	public String addUser(Users users){
		this.usersService.addUser(users);
		return "ok";
	}
	
	/**
	 * 查询全部用户
	 */
	@RequestMapping("/findUserAll")
	public String findUserAll(Model model){
		List<Users> list = this.usersService.findUserAll();
		model.addAttribute("list", list);
		return "showUsers";
	}
	
	
}
