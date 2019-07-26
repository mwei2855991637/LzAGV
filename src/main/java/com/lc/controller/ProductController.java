package com.lc.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lc.bean.Product;
import com.lc.bean.TransportPlan;
import com.lc.service.ProductService;
import com.lc.service.TransPlanService;

@Controller
public class ProductController {
	@Autowired
	private ProductService service;
	@Autowired
	private TransPlanService transPlanService;
	/**
	 *	 根据id查询货物信息
	 * @param id
	 * @param carId
	 * @param model
	 * @return
	 */
	@RequestMapping("product")
	public String findById(@RequestParam(value = "id", defaultValue = "0")Integer id,String carId,Model model) {
		if(id==0) {
			model.addAttribute("product", -1);
			return "plan/productDetails";
		}
		Product product=service.findById(id);
		model.addAttribute("carId", carId);
		System.out.println(product);
		model.addAttribute("product", product);
		return "plan/productDetails";
	}
	@RequestMapping("cargo")
	public String findByIdAndTime(@RequestParam(value = "id", defaultValue = "-1")String id,Model model) {
		System.out.println("获取编号id："+id);
		if(id.equals("-1")) {
			model.addAttribute("product", -1);
			return "plan/productDetails";
		}
		Integer pid=transPlanService.findByIdAndTime(id);
		System.out.println("获取货物id:"+pid);
		Product product=service.findById(pid);
		if(product==null) {
			model.addAttribute("product", -1);
			return "plan/productDetails";
		}
		model.addAttribute("carId", id);
		System.out.println(product);
		model.addAttribute("product", product);
		return "plan/productDetails";
	}
}
