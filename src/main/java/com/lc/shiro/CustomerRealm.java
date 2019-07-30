package com.lc.shiro;


import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.lc.bean.Customer;
import com.lc.service.CustomerService;

public class CustomerRealm extends AuthorizingRealm {
	@Autowired
	private CustomerService customerService;
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("执行授权认证");
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("执行认证逻辑");
		UsernamePasswordToken token1=(UsernamePasswordToken)token;
		Customer customer=customerService.login(token1.getUsername());
		//编辑判断逻辑
		if(customer==null) {
			//用户名不存在
			System.out.println("用户名不存在"+token1.getUsername());
			return null;
		}
//		Object principal=token1.getUsername();
//        Object credentials=customer.getPassword();
//        String realmName=this.getName();
//        //设置盐值
//        ByteSource salt=ByteSource.Util.bytes(token1.getUsername());
//        
//        //SimpleHash sh=new SimpleHash(algorithmName, source, salt, iterations); 
//                                      //   加密类型                       加密资源        盐值加密      加密次数
//        //给从数据库中拿到的密码做MD5的加密
//        SimpleHash sh=new SimpleHash("MD5", credentials, salt, 1024);
//        //info = new SimpleAuthenticationInfo(principal, credentials, realmName);
//        //info = new SimpleAuthenticationInfo(principal, sh, realmName);
//        //通过关于盐值的构造器，将前端传入的密码在加密时再加入盐值
//        return new SimpleAuthenticationInfo(principal, sh, salt, realmName);
//		
		
		
		return new SimpleAuthenticationInfo(customer, customer.getPassword(), "");
	}

}
