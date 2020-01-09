package com.tsc.realm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.tsc.dao.UserMapper;
import com.tsc.dao.UserPermissionMapper;
import com.tsc.dao.UserRoleMapper;
import com.tsc.entity.Permission;
import com.tsc.entity.Role;
import com.tsc.entity.User;

public class MyRealm extends AuthorizingRealm{
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserRoleMapper userRoleMapper;
	
	
	@Autowired 
	private UserPermissionMapper userPermissionMapper;
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		System.out.println("--->授权");
		SimpleAuthorizationInfo authorizationInfo=new SimpleAuthorizationInfo();
		User user=(User)SecurityUtils.getSubject().getPrincipal();
		String username=user.getUserName();
		System.out.println("用户"+username+"获取的权限--------shiro.doGetAuthorizationInfo");
		//获取的用户角色集
		List<Role> roleList=userRoleMapper.findByUserName(username);
		Set<String> roleSet=new HashSet<String>();
		for(Role r:roleList) {
			roleSet.add(r.getName());
		}
		authorizationInfo.setRoles(roleSet);
		//获取用户权集
		List<Permission> permissionsList=userPermissionMapper.findByUserName(username);
		Set<String> permissionSet=new HashSet<String>();
		for(Permission p:permissionsList) {
			permissionSet.add(p.getName());
		}
		authorizationInfo.setStringPermissions(permissionSet);
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username=(String)token.getPrincipal();
		String password=new String((char[])token.getCredentials());
		System.out.println("----->登录认证"+username+password);
		if(username==null) {
			throw new UnknownAccountException("未知账号异常");
		}
		User dpPassword=userMapper.findByUserName(username);
		if(!password.equals(dpPassword.getPassword())) {
			throw new IncorrectCredentialsException("用户名或者密码错误");
		}
		if("0".equals(dpPassword.getStatus())) {
			throw new LockedAccountException("账户已经锁定,请联系管理员");
		}
		return new SimpleAuthenticationInfo(dpPassword, dpPassword.getPassword(), getName());
	}

	



}
