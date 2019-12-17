package com.bjsxt.service;

import java.util.List;

import com.bjsxt.pojo.Users;

public interface UsersService {
	
	void addUser(Users users);
	List<Users> findUserAll();
} 
