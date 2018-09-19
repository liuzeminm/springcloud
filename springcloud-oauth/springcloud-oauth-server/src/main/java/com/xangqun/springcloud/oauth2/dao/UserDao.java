package com.xangqun.springcloud.oauth2.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface UserDao {

	@Select("select * from sys_user t where t.username = #{username}")
	Map getUser(String username);
}
