package com.lc.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lc.bean.CarStatus;
import com.lc.service.CarService;

@Controller
public class CarController {
	@Autowired
	private CarService service;
	/**
	 *	 根据id查询货物信息
	 * @param id
	 * @param carId
	 * @param model
	 * @return
	 */
	@RequestMapping("carstatus")
	public String findById(@RequestParam(value = "id", defaultValue = "-1")String id,@RequestParam(value = "status", defaultValue = "-1")Integer status,Model model) {
		System.out.println("获取id："+id);
		if(id.equals("-1")) {
			model.addAttribute("carStatus", -1);
			return "plan/left_info";
		}
		CarStatus carStatus=service.findByCarId(id);
		if(carStatus==null) {
			model.addAttribute("carStatus", -1);
			return "plan/left_info";
		}
		model.addAttribute("carId", id);
		model.addAttribute("status", status);
		model.addAttribute("carStatus", carStatus);
		return "plan/left_info";
	}
	@ResponseBody
	@RequestMapping("carinfo")
	public Map<String, Object> carinfo(@RequestParam(value = "id", defaultValue = "1")String id) {
		System.out.println("获取id："+id);
		Map<String, Object> map=new HashMap<String, Object>();
		CarStatus carStatus=service.findByCarId(id);
		map.put("code", 0);
		map.put("mapCar", carStatus);
		System.out.println("得到map："+map.toString());
		return map;
	}
}
