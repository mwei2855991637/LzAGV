package com.lc.controller;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lc.bean.Customer;
import com.lc.service.CustomerService;
import com.lc.util.EmailHelper;
import com.lc.util.SaltMd5;

@Controller
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	private static Customer currCustomer;
	/**
	 * 	用户登录
	 * @return
	 */
	@RequestMapping("login")
	public String login(HttpServletRequest request,HttpSession session,@RequestParam(value = "username", defaultValue = "-1")String username,@RequestParam(value = "password", defaultValue = "-1")String password,@RequestParam(value = "rememberMe", defaultValue = "-1")String rememberMe,Model model) {
		System.out.println("当前rememberMe："+rememberMe);
		//获得subject
		Subject subject=SecurityUtils.getSubject();
		System.out.println("是否记住"+subject.isRemembered());
		//获取加密后内容
		String saltMd5=SaltMd5.md5(password, username);
		//封装用户数据
		UsernamePasswordToken token=new UsernamePasswordToken(username, saltMd5);
		//执行登录方法
		try {
			token.setRememberMe(rememberMe.equals("1")?true:false);
			subject.login(token);
			//获取已登录的用户信息
	        Customer customer = (Customer) subject.getPrincipal();
	        
	        session.setAttribute("customer", customer);
	        System.out.println("登录成功！");
	        SavedRequest savedRequest=WebUtils.getSavedRequest(request);//通过shiro获取登录之前的路径
	        if(null!=savedRequest){
	            return "redirect:" + savedRequest.getRequestUrl();
	        }else {
	        	return "redirect:/plan/index";
	        }
			//登录成功
		}catch(UnknownAccountException e) {
			 System.out.println("用户名不存在！");
			//登录失败
			model.addAttribute("msg", "用户名不存在");
		}catch (IncorrectCredentialsException e) {
			 System.out.println("密码错误！");
			model.addAttribute("msg", "密码错误");
		}
		return "login";	
	}
	/**
	 * 修改密码
	 */
	@RequestMapping("updatePassword")
	@ResponseBody
	public Map<String, Object> updatePassword(String username,String oldPassword,String newPassword) {
		Map<String, Object> map=new HashMap<String, Object>();
		//访问数据库
		Customer customer = customerService.forgetPass(username);
		if(customer.getPassword().equals(SaltMd5.md5(oldPassword, username))) {
			//原密码有误
		}else {
			//修改成功
			customerService.updatePassword(username, SaltMd5.md5(newPassword, username));
		}
		map.put("code", 0);
		map.put("flag", 1);
		
		return map;
	}
	/**
	 * 忘记密码界面
	 */
	@RequestMapping("judgeVerificationIndex")
	public String judgeVerificationIndex(Model model) {
		model.addAttribute("msg", "请输入用户名完成验证");
		return "/judgeVerification";
	}
	/**
	 * 忘记密码->发送邮箱验证码
	 */
	@RequestMapping("sendVerification")
	@ResponseBody
	public Map<String, Object> sendVerification(HttpSession session,Model model,String username) {
		Map<String, Object> map=new HashMap<String, Object>();
		Integer verificationCode=(int)((Math.random()*9+1)*100000);
		currCustomer=customerService.forgetPass(username);
		if(currCustomer==null) {
			//用户名不存在
			map.put("code", 0);
			map.put("msg", -1);
			return map;
		}else {
			int flag=EmailHelper.sendEmail(currCustomer.getEmail(), "【深圳乐创信息通讯技术有限公司】验证码", "您好！尊敬的 "+currCustomer.getCompany()+" 验证码为 "+verificationCode+"。 您正在使用重置密码功能，该验证码10分钟内有效,若非本人操作请忽略！");
			//验证码定时任务
			final Timer timer=new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					if(session.getAttribute("verificationCode")!=null) {
						session.removeAttribute("verificationCode");
					}
					System.out.println("验证码已经删除");
					//终止定时器---->(可能存在bug)
					timer.cancel();
				}
			},10*60*1000);
			map.put("code", 0);
			if(flag==1) {
				session.setAttribute("verificationCode", verificationCode);
				map.put("msg", "验证码以成功发送至邮箱！请注意查收！");
			}else {
				map.put("msg", "邮件发送失败！（请保持网络畅通，收件人地址是否有误！）");
			}
			return map;
		}
	}
	/**
	 * 判断验证码是否正确
	 * @param session
	 * @return
	 */
	@RequestMapping("judgeVerification")
	public String judgeVerification(HttpSession session,Model model,@RequestParam(value = "code", defaultValue = "-1")Integer code) {
		Integer verificationCode=(Integer) session.getAttribute("verificationCode");
		if(session.getAttribute("verificationCode")==null){
			model.addAttribute("currCust", currCustomer.getUsername());
			model.addAttribute("msg", "您的验证码已失效！请重新发送邮箱！");
			return "/judgeVerification";
		}else {
			if(verificationCode.equals(code)) {
				//验证通过,调转重置界面
				return "/restartPassword";
			}else {
				model.addAttribute("currCust", currCustomer.getUsername());
				model.addAttribute("msg", "您的验证码有误！请重新输入！");
				return "/judgeVerification";
			}
		}
	}
	
	/**
	 * 重新设置密码->重设密码
	 */
	@RequestMapping("restartPassword")
	@ResponseBody
	public Map<String, Object> restartPassword(String password) {
		Map<String, Object> map=new HashMap<String, Object>();
		//访问数据库
		customerService.updatePassword(currCustomer.getUsername(), SaltMd5.md5(password, currCustomer.getUsername()));
		map.put("code", 0);
		map.put("flag", 1);
		return map;
	}
}
