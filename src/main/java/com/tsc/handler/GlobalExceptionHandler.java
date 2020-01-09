package com.tsc.handler;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 对页面会被重定向到/403进行全局异常捕获
 * 
 * @author tsc
 *
 */

@ControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {
	@ExceptionHandler(value = AuthorizationException.class)
	public String handleAuthorizationException() {
		return "403";
	}
}
