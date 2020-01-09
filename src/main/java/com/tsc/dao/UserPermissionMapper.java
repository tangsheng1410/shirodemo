package com.tsc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tsc.entity.Permission;

@Mapper
public interface UserPermissionMapper {
	List<Permission> findByUserName(String userName);
}
