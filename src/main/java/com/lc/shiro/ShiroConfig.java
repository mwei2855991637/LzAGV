package com.lc.shiro;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.util.SavedRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {
	/**
	 * 创建ShrioFilterFactoryBean
	 * 
	 */
	@Bean
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(
			@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
		System.out.println("执行过滤器");
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		//设置安全管理器
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		//添加shrio内置过滤器
		Map<String, String> filterMap=new LinkedHashMap<String, String>();
		filterMap.put("/plan/*", "authc");
		filterMap.put("/findStatus", "authc");
		filterMap.put("/statistical", "authc");
		filterMap.put("/carstatus", "authc");
		filterMap.put("/email", "authc");
		filterMap.put("/cargo", "authc");
		filterMap.put("/cargo", "authc");
		filterMap.put("/statistical_admin", "authc");
		filterMap.put("/logout", "logout");//安全退出
		shiroFilterFactoryBean.setLoginUrl("/");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
		return shiroFilterFactoryBean;
	}

	/**
	 * 创建DefaultwebSecurityManager
	 * 
	 */
	@Bean(name="securityManager")
	public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("customerRealm") CustomerRealm customerRealm) {
		DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
		//将自定义的realm交给SecurityManager管理
		defaultWebSecurityManager.setRealm(customerRealm);
		// 使用记住我
		defaultWebSecurityManager.setRememberMeManager(rememberMeManager());
		return defaultWebSecurityManager;
	}

	/**
	 * 创建realm
	 */
	@Bean(name="customerRealm")
	public CustomerRealm getRealm() {
		return new CustomerRealm();
	}
	
	/**
     * cookie对象;
     * @return
     */
    public SimpleCookie rememberMeCookie(){
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //cookie生效时间30天,单位秒;
        simpleCookie.setMaxAge(2592000);
        return simpleCookie;
    }

    /**
     * cookie管理对象;记住我功能
     * @return
     */
    public CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        System.out.println("执行cookie");
        // cookieRememberMeManager.setCipherKey用来设置加密的Key,参数类型byte[],字节数组长度要求16
        // cookieRememberMeManager.setCipherKey(Base64.decode("3AvVhmFLUs0KTA3Kprsdag=="));
       cookieRememberMeManager.setCipherKey("LeChangeSystemMW".getBytes());
        return cookieRememberMeManager;
    }
	
}
