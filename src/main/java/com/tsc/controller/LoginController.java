package com.tsc.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tsc.util.MD5Utils;
import com.tsc.util.ResponseBo;

@Controller
public class LoginController {
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	
	@PostMapping("/login")
	@ResponseBody
	public ResponseBo login(String username, String password,boolean rememberMe, HttpServletRequest req) {
		
		password = MD5Utils.encrty(username, password);
		System.out.println("----->"+rememberMe);
		UsernamePasswordToken token = new UsernamePasswordToken(username, password,rememberMe);

		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
			System.out.println("登录成功");
			return ResponseBo.ok();
		} catch (UnknownAccountException e) {
			req.setAttribute("msg", username);
			System.out.println("用户名错误");
			return ResponseBo.error(e.getMessage());
		}catch (IncorrectCredentialsException e) {
			System.out.println("密码错误");
			return ResponseBo.error(e.getMessage());
		} catch (LockedAccountException e) {
			System.out.println("账户锁定");
			return ResponseBo.error(e.getMessage());
		}catch (AuthenticationException e) {
			return ResponseBo.error("认证失败");
		}

		
	}
}
