package com.lc.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lc.bean.CurrStatus;
import com.lc.service.OtherService;
import com.lc.util.EmailHelper;


@Controller
public class OtherController {
	@Autowired
	private OtherService otherService;
	@RequestMapping("email")
	public String emailIndex() {
		return "plan/sendEmail";
	}
	
	@RequestMapping("sendEmail")
	public String email(HttpServletRequest req,String name,String title,String context,Model model) {
	    int flag=EmailHelper.sendEmail(name, title, context);
		model.addAttribute("flag", flag);
		return "plan/sendEmail";
	}
	@RequestMapping("findStatus")
	public String findStatus(Model model) {
		List<CurrStatus> currStatus=otherService.findAll();
		System.out.println("控制层："+currStatus.get(0).getCurrName());
		if(currStatus.get(0).getCurrName().equals("自动")) {
			model.addAttribute("currStatus", 1);
		}else {
			model.addAttribute("currStatus", 2);
		}
		
		return "plan/storage";
	}
	@ResponseBody
	@RequestMapping("updateStatus")
	public Map<String, Object> updateStatus(Integer status) {
		System.out.println("执行更改状态："+status);
		if(status==1) {
			otherService.updateStatus("手动", 1);
		}else {
			otherService.updateStatus("自动", 1);
		}
		Map<String, Object> map=new HashMap<>();
		map.put("code", 0);
		map.put("data", "ok");
		return map;
	}
}
