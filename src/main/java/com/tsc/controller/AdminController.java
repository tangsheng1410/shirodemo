package com.tsc.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {
	
	@RequiresPermissions("user:add")
	@RequestMapping("/admin")
	public String adminPage() {
		return "admin";
	}
	
	@RequestMapping("/403")
	public String unauthorizedUrl() {
		return "403";
	}
	
	
}
