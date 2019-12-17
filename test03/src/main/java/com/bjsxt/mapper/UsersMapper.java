package com.bjsxt.mapper;

import java.util.List;

import com.bjsxt.pojo.Users;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UsersMapper {
	
	void insertUser(Users users);
	
	List<Users> selectUsersAll();
}
