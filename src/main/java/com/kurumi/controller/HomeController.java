package com.kurumi.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kurumi.pojo.RespondResult;
import com.kurumi.service.UserService;
import com.kurumi.util.JsonUtil;
import com.kurumi.util.MD5Util;

/**
 * 默认控制器
 * @author lyh
 * @version 1.1
 */
@Controller
public class HomeController {

	@Autowired
	private UserService userService;
	
	/**
	 * 默认首页
	 * @return
	 */
	@GetMapping("/")
	public String home(){
		
		
		return "index.default";
	}
	
	/*@RequiresRoles("admin")*/
	/**
	 * 首页
	 * @return
	 */
	@GetMapping("/index")
	public String index(){
		return "index.default";
	}
	
	/**
	 * 登陆页
	 * @return
	 */
	@GetMapping("/login")
	public String login(){
		return "login";
	}
	
	/**
	 * 退出
	 * @return
	 */
	@GetMapping("/login_out")
	public String loginOut(){
		Subject subject=SecurityUtils.getSubject();
		Session session = subject.getSession();
		session.removeAttribute("currentRole");
		session.removeAttribute("currentUser");
		SecurityUtils.getSubject().logout();
		
		return "redirect:/login"; 
	}
	
	/**
	 * 本地登录
	 * @param loginName
	 * @param password
	 * @param verificationCode
	 * @param request
	 * @return
	 */
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
	
	/**
	 * 远程登陆
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/romote_login_check")
	@ResponseBody
	public RespondResult remoteLoginCheck(HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		RespondResult respondResult = null;
		try{
			
			BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));  
			StringBuffer sb = new StringBuffer("");  
			String temp;  
			while ((temp = br.readLine()) != null) {  
				sb.append(temp);  
			}  
			br.close();
			String postDataJson = sb.toString();
			Map<String, Object> postData = JsonUtil.jsonToPojo(postDataJson, Map.class);
			String loginName =(String)postData.get("loginName");
			String password =(String)postData.get("password");
			Map<String, Object>  datas = userService.remoteLoginCheck(loginName, password);
			if(datas != null){
				respondResult = new RespondResult(true, RespondResult.successCode, "登陆成功", datas);
			}else{
				respondResult = new RespondResult(true, RespondResult.errorCode, "登录名或密码错误", "");
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
	
	
	@SuppressWarnings("unchecked")
	@PostMapping("/romote_admin_login_check")
	@ResponseBody
	public RespondResult remoteAdminLoginCheck(HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		RespondResult respondResult = null;
		try{
			
			BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));  
			StringBuffer sb = new StringBuffer("");  
			String temp;  
			while ((temp = br.readLine()) != null) {  
				sb.append(temp);  
			}  
			br.close();
			String postDataJson = sb.toString();
			Map<String, Object> postData = JsonUtil.jsonToPojo(postDataJson, Map.class);
			String loginName =(String)postData.get("loginName");
			String password =(String)postData.get("password");
			List<Map<String, Object>>  datas = userService.getSupperAdminByLoginNameAndPassword(loginName, password);
			if(!datas.isEmpty()){
				respondResult = new RespondResult(true, RespondResult.successCode, "登陆成功", datas.get(0));
			}else{
				respondResult = new RespondResult(true, RespondResult.errorCode, "登录名或密码错误", "");
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
