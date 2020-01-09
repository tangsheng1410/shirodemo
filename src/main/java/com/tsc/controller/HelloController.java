package com.tsc.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tsc.entity.User;

@Controller
public class HelloController {
	
	@RequiresPermissions("user:user")
	@RequestMapping("/hello")
	public String toHello(HttpServletRequest req) {
		Subject subject=SecurityUtils.getSubject();
		User user=(User) subject.getPrincipal();
		req.setAttribute("user",user.getUserName() );
		return "hello";
		
		
	}
	
}
