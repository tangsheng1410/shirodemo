package com.tsc;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tsc.dao.UserPermissionMapper;
import com.tsc.entity.Permission;

@SpringBootTest
public class TestMybatis {
	@Autowired
	UserPermissionMapper userPermissionMapper;
	@Test
	public void test1() {
		List<Permission> permission=(List<Permission>) userPermissionMapper.findByUserName("test");
		System.out.println(permission);
	}
}
