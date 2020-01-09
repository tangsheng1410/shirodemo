package com.tsc.dao;

import org.apache.ibatis.annotations.Mapper;

import com.tsc.entity.User;


@Mapper
public interface UserMapper {
	User findByUserName(String userName);
	
}
