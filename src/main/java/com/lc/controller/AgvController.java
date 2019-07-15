package com.lc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lc.bean.Agv;
import com.lc.service.AgvService;

@Controller
public class AgvController {
	@Autowired
	private AgvService agvService;
	@ResponseBody
	@RequestMapping("agvFindAll")
	public List<Agv> agvFindAll(){
		return agvService.findAll();
	}
}
