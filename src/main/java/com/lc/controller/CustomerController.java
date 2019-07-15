package com.lc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lc.bean.Customer;
import com.lc.service.CustomerService;

@Controller
public class CustomerController {
	private static final String SUCCESS="success";
	private static final String ERROR="error";
	private static final String FAIL="fail";
	
	@Autowired
	private CustomerService customerService;
	/**
	 * 	用户登录
	 * @return
	 */
	@RequestMapping("login")
	public String login(@RequestParam(value = "username", defaultValue = "-1")String username,@RequestParam(value = "password")String password,Model model) {
		if(username.equals("-1")) {
			//用户名为空
			model.addAttribute("flag", FAIL);
			return "login";
		}else {
			Customer customer=customerService.login(username, password);
			if(customer!=null) {
				//登录成功
				model.addAttribute("flag", SUCCESS);
				return "index";	
			}else {
				//用户名或密码错误
				model.addAttribute("flag", ERROR);
				return "login";	
			}
		}
		
	}
}
