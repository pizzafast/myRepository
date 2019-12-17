package com.bjsxt.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bjsxt.pojo.User;
import com.bjsxt.service.UserSerevice;

/**
 * @author la
 */

@Controller
public class UserController {
    @Resource
    private UserSerevice userSereviceImpl;

    @RequestMapping("/find")
    public String find(Model model){
        System.out.println("controller");
        List<User> list = userSereviceImpl.findAll();
        model.addAttribute("list", list);
        return "showUsers";
    }
}
