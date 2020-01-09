package com.tsc.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tsc.realm.MyRealm;

@Configuration
public class ShiroConfig {

	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
		ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
		bean.setSecurityManager(securityManager);
		Map<String, String> filterChainDefinitionMap = new HashMap<String, String>();
		bean.setLoginUrl("/login");
		bean.setUnauthorizedUrl("/403");
		filterChainDefinitionMap.put("/logout", "logout");
		filterChainDefinitionMap.put("/css/**", "anon");
		filterChainDefinitionMap.put("/js/**", "anon");
		filterChainDefinitionMap.put("/fonts/**", "anon");
		filterChainDefinitionMap.put("/img/**", "anon");
		filterChainDefinitionMap.put("/druid/**", "anon");
		filterChainDefinitionMap.put("/**", "user");
//		filterChainDefinitionMap.put("/admin", "perms[user:add]");
//		filterChainDefinitionMap.put("/hello", "perms[user:user]");

		bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return bean;
	}

	/**
	 * 开启aop
	 */
	@Bean
	public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		advisorAutoProxyCreator.setProxyTargetClass(true);
		return advisorAutoProxyCreator;
	}

	/**
	 * 开启shiro aop注解支持. 使用代理方式;所以需要开启代码支持;
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

	@Bean
	public SecurityManager securityManager(MyRealm myRealm) {
		DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
		defaultWebSecurityManager.setRememberMeManager(cookieRememberMeManager());
		defaultWebSecurityManager.setRealm(myRealm);
		return defaultWebSecurityManager;
	}

	@Bean
	public MyRealm myRealm() {
		MyRealm myRealm = new MyRealm();
		return myRealm;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor advisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor attributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		attributeSourceAdvisor.setSecurityManager(securityManager);
		return attributeSourceAdvisor;

	}

	public SimpleCookie rememberMeCookie() {
		// 对应页面标签上的name名称
		SimpleCookie cookie = new SimpleCookie("rememberMe");
		cookie.setMaxAge(86400);
		return cookie;
	}

	public CookieRememberMeManager cookieRememberMeManager() {
		CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
		cookieRememberMeManager.setCookie(rememberMeCookie());
		System.out.println("----->记住密码成功");
		cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
		return cookieRememberMeManager;
	}

}
