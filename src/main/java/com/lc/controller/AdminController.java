package com.lc.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lc.bean.Customer;
import com.lc.service.HisTransportService;
import com.lc.service.TransPlanService;
import com.lc.util.GetTimestamp;
import com.lc.util.LogUtil;

/** 
* @author MWEI E-mail: 574185505@qq.com
* @version 2019年7月30日 上午10:50:39 
* Class Explain: 后台数据类
*/
@Controller
@ResponseBody
public class AdminController {
	@Autowired
	private HisTransportService hisTransportService;
	@Autowired
	private TransPlanService transPlanService;
	//记录管理员删除数据的操作
	private String logpath="E:/del.log";
	//删除选中数据
	@RequestMapping("delSel")
	public Map<String, Object> delSel(HttpSession session,String ids){
		Customer customer =(Customer)session.getAttribute("customer");
		Map<String, Object> map=new HashMap<String, Object>();
		hisTransportService.delSel(ids);
		transPlanService.delSel(ids);
		String content = LogUtil.getCurrentYYYYMMDDHHMMSS() + " ***执行删除选中记录操作开始***";
		LogUtil.writeFile(logpath, content);
		content="管理员:"+customer.getUsername()+"操作，删除选中数据,id分别为"+ids;
		LogUtil.writeFile(logpath, content);
		map.put("code", 0);
		return map;
	}
	//删除当日数据
	@RequestMapping("delDay")
	public Map<String, Object> delDay(HttpSession session){
		Customer customer =(Customer)session.getAttribute("customer");
		Map<String, Object> map=new HashMap<String, Object>();
		String currDay=GetTimestamp.getTimestamp();//获取当前年月日
		transPlanService.delDay(currDay+" 00:00:00",currDay+" 23:59:59");
		String content = LogUtil.getCurrentYYYYMMDDHHMMSS() + " ***执行删除今日数据操作开始***";
		LogUtil.writeFile(logpath, content);
		content="管理员:"+customer.getUsername()+"操作，删除今日所有数据";
		LogUtil.writeFile(logpath, content);
		map.put("code", 0);
		return map;
	}
	//删除所有数据
	@RequestMapping("delAll")
	public Map<String, Object> delAll(HttpSession session){
		Customer customer =(Customer)session.getAttribute("customer");
		Map<String, Object> map=new HashMap<String, Object>();
		hisTransportService.delAll();
		transPlanService.delAll();
		String content = LogUtil.getCurrentYYYYMMDDHHMMSS() + " ————————————***执行删除今日数据操作开始***————————————";
		LogUtil.writeFile(logpath, content);
		content="管理员:"+customer.getUsername()+"操作，删除所有数据";
		LogUtil.writeFile(logpath, content);
		map.put("code", 0);
		return map;
	}
}
