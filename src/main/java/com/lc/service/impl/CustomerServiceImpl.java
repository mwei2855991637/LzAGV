package com.lc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lc.bean.Customer;
import com.lc.dao.CustomerDao;
import com.lc.service.CustomerService;
@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerDao dao;
	//登录
	@Override
	public Customer login(String username, String password) {
		return dao.login(username, password);
	}
	//注册
	@Override
	public void register(Customer customer) {
		dao.save(customer);
	}
	//根据用户名查找用户信息
	@Override
	public Customer forgetPass(String username) {
		return dao.findByUsername(username);
	}
	//更改用户密码
	@Override
	public void updatePassword(String username,String password) {

	}

}
