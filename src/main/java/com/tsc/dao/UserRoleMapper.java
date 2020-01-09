package com.tsc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tsc.entity.Role;

@Mapper
public interface UserRoleMapper {
	List<Role> findByUserName(String userName);
}
