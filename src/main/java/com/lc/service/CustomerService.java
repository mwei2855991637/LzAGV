package com.lc.service;

import org.springframework.stereotype.Service;

import com.lc.bean.Customer;
@Service
public interface CustomerService {
	//登录
	Customer login(String username,String password);
	//注册
	void register(Customer customer);
	//忘记密码->(验证用户，查看是否存在该用户);
	Customer forgetPass(String username);
	//更改密码
	void updatePassword(String username,String password);	
}
