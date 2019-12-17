package com.bjsxt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bjsxt.mapper.UserMapper;
import com.bjsxt.pojo.User;
import com.bjsxt.pojo.UserExample;
import com.bjsxt.service.UserSerevice;

/**
 * @author la
 */

@Service
public class UserServiceImpl implements UserSerevice {
    @Resource
    private UserMapper userMapper;
    @Override
    public List<User> findAll() {
        return userMapper.selectByExample(new UserExample());
    }
}
