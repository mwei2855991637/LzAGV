package com.lc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class PageController {
	
	@RequestMapping("/")
	public String login() {			
		return "login";
	}
	/**
	 * 	Test
	 * @return 数据访问界面
	 */
	@RequestMapping("/send")
	public String send() {			
		return "plan/testSocket";
	}
	@RequestMapping("/layui")
	public String layui() {
		return "plan/layui";
	}
	
	@RequestMapping("/left_info")
	public String left_info() {
		return "plan/left_info";
	}
	
	@RequestMapping("/statistical")
	public String statistical() {
		return "plan/statistical";
	}
	
	@RequestMapping("/statistical_admin")
	public String statistical_admin() {
		return "admin/data";
	}
	
	@RequestMapping("/storageTest")
	public String storageTest() {
		return "plan/storageTest";
	}
	
	
}
