package com.kurumi.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kurumi.pojo.RespondResult;
import com.kurumi.service.UserService;
import com.kurumi.util.MD5Util;

@Controller
public class HomeController {

	
	@GetMapping("/")
	public String home(){
		
		
		return "index.default";
	}
	
	@RequiresRoles("admin")
	@GetMapping("/index")
	public String index(){
		return "index.default";
	}
	
	@GetMapping("/login")
	public String login(){
		return "login";
	}
	
	
	@PostMapping("/login_check")
	@ResponseBody
	public RespondResult loginCheck(String loginName,String password,String verificationCode,HttpServletRequest request){
		RespondResult respondResult = null;
		try{
			
			String sessionVerificationCode = (String) request.getSession().getAttribute("verificationCode");
			if(verificationCode !=null){
				boolean result = verificationCode.equalsIgnoreCase(sessionVerificationCode);
				if(!result){
					respondResult = new RespondResult(true, RespondResult.errorCode, "验证码输入错误", "验证码输入错误");
				}else{
					UsernamePasswordToken token = new UsernamePasswordToken(loginName, MD5Util.getMD5(password));
					SecurityUtils.getSubject().login(token);
					respondResult = new RespondResult(true, RespondResult.successCode, "登陆成功", "");
				}
				
			}else{
				respondResult = new RespondResult(true, RespondResult.errorCode, "验证码不能为空", "验证码不能为空");
			}
			
		}
		catch (Exception e) {
			// TODO: handle exception
			if(e instanceof UnknownAccountException){
				respondResult = new RespondResult(true, RespondResult.errorCode, "用户名或密码错误", "用户名或密码错误");
			}else{
				respondResult = new RespondResult(false, RespondResult.errorCode, e.getMessage(),e.getMessage());
			}
			
			
		}
		
		return respondResult;
		
		
	}
	
	
	@PostMapping("/login_out")
	@ResponseBody
	public RespondResult loginOut(HttpServletRequest request){
		RespondResult respondResult = null;
		try{
			request.getSession().removeAttribute("currentUser");
			
			respondResult = new RespondResult(true, RespondResult.successCode, "退出登录成功", "");
					
		}
		catch (Exception e) {
			// TODO: handle exception
			respondResult = new RespondResult(false, RespondResult.errorCode, e.getMessage(),e.getMessage());
		}
		
		return respondResult;
		
		
	}
	
}
