package com.kurumi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kurumi.config.MyConfig;
import com.kurumi.pojo.RespondResult;
import com.kurumi.pojo.User;
import com.kurumi.query.UserQuery;
import com.kurumi.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	private MyConfig myConfig;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("query_user")
	public String queryUser(){
		return "user/query_user";
	}
	
	@GetMapping("person_user")
	public String personUser(HttpServletRequest request,Model model){
		HttpSession session = request.getSession();
		@SuppressWarnings("unchecked")
		Map<String, Object> currentUser = (Map<String, Object>)session.getAttribute("currentUser");
		String userCode = (String)currentUser.get("user_code");
		List<Map<String, Object>> users = userService.getUserByUserCode(userCode);
		model.addAttribute("user",users.get(0));
		return "user/person_user";
	}
	
	
	@GetMapping("/ajax_query_user")
	@ResponseBody
	public RespondResult ajaxQueryUser(UserQuery params,HttpServletRequest request){
		RespondResult respondResult = null;
		try{
			List<Map<String, Object>> users = new ArrayList<Map<String, Object>>();
			int count = 0;
			if(!params.isEmpty()){
				users = userService.getUserByQueryUser(params);
				count = userService.getUserCountByQueryUser(params);
			}
			params.setTotalCounts(count);
			params.setQueryDatas(users);
			respondResult = new RespondResult(true, RespondResult.successCode, "查询成功", params);
		}
		catch (Exception e) {
			// TODO: handle exception
			respondResult = new RespondResult(false, RespondResult.errorCode, e.getMessage(),e.getMessage());
		}
		return respondResult;
	}
	
	
	@GetMapping("/ajax_user_role_init")
	@ResponseBody
	public RespondResult ajaxUserRoleInit(String userCode,HttpServletRequest request){
		RespondResult respondResult = null;
		try{
			Map<String, List<Map<String, Object>>> datas = userService.getUserRoleInit(userCode);
			respondResult = new RespondResult(true, RespondResult.successCode, "查询", datas);
		}
		catch (Exception e) {
			// TODO: handle exception
			respondResult = new RespondResult(false, RespondResult.errorCode, e.getMessage(),e.getMessage());
		}
		return respondResult;
	}
	
	@PostMapping("/ajax_create_user")
	@ResponseBody
	public RespondResult ajaxCreateUser(User user,HttpServletRequest request){
		RespondResult respondResult = null;
		try{
			int count = userService.insert(user);
			
			if(count == 1){
				user = userService.selectByPrimaryKey(user.getId());
				respondResult = new RespondResult(true, RespondResult.successCode, "新建用户成功", user);
				
			}else if(count == 2){
				respondResult = new RespondResult(true, RespondResult.repeatCode, "用户编号或名称已存在", user);
				
			}else{
				respondResult = new RespondResult(true, RespondResult.errorCode, "新建用户失败", "新建用户失败");
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			respondResult = new RespondResult(false, RespondResult.errorCode, e.getMessage(),e.getMessage());
		}
		
		return respondResult;
	}
	
	
	@PostMapping("/ajax_update_user")
	@ResponseBody
	public RespondResult ajaxUpdateUser(User user,HttpServletRequest request){
		RespondResult respondResult = null;
		try{
			int count = userService.update(user);
			
			if(count == 1){
				user = userService.selectByPrimaryKey(user.getId());
				respondResult = new RespondResult(true, RespondResult.successCode, "修改用户成功", user);
				
			}else if(count == 2){
				respondResult = new RespondResult(true, RespondResult.repeatCode, "用户编号或名称已存在", user);
				
			}else{
				respondResult = new RespondResult(true, RespondResult.errorCode, "修改用户失败", "修改用户失败");
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			respondResult = new RespondResult(false, RespondResult.errorCode, e.getMessage(),e.getMessage());
		}
		
		return respondResult;
	}
	
	@PostMapping("/ajax_update_user_role")
	@ResponseBody
	public RespondResult ajaxUpdateUserRole(String userCode,String[] roleCodes, HttpServletRequest request){
		RespondResult respondResult = null;
		try{
			int count = userService.updateUserRole(userCode, roleCodes);
			if(count == 1){
				respondResult = new RespondResult(true, RespondResult.successCode, "设置角色成功", "设置角色成功");
			}else{
				respondResult = new RespondResult(true, RespondResult.errorCode, "设置角色失败", "设置角色失败");
			}
			
			
		}
		catch (Exception e) {
			// TODO: handle exception
			respondResult = new RespondResult(false, RespondResult.errorCode, e.getMessage(),e.getMessage());
		}
		
		return respondResult;
	}
	
	@PostMapping("/ajax_update_person_user")
	@ResponseBody
	public RespondResult ajaxUpdatePersonUser(User user,HttpServletRequest request){
		RespondResult respondResult = null;
		try{
			int count = userService.update(user);
			
			if(count == 1){
				user = userService.selectByPrimaryKey(user.getId());
				List<Map<String, Object>> users = userService.getUserByUserCode(user.getUserCode());
				HttpSession session = request.getSession();
				session.setAttribute("currentUser", users.get(0));
				respondResult = new RespondResult(true, RespondResult.successCode, "修改用户成功", user);
				
			}else if(count == 2){
				respondResult = new RespondResult(true, RespondResult.repeatCode, "用户编号或名称已存在", user);
				
			}else{
				respondResult = new RespondResult(true, RespondResult.errorCode, "修改用户失败", "修改用户失败");
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			respondResult = new RespondResult(false, RespondResult.errorCode, e.getMessage(),e.getMessage());
		}
		
		return respondResult;
	}
	
	
	@PostMapping("/ajax_update_password")
	@ResponseBody
	public RespondResult ajaxUpdatePassword(Integer id,String oldPassword,String newPassword,HttpServletRequest request){
		RespondResult respondResult = null;
		try{
			int count = userService.updatePassword(id, oldPassword, newPassword);
			
			if(count == 1){
				respondResult = new RespondResult(true, RespondResult.successCode, "修改密码成功", "");
			}else{
				respondResult = new RespondResult(true, RespondResult.errorCode, "修改密码失败", "修改密码失败");
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			respondResult = new RespondResult(false, RespondResult.errorCode, e.getMessage(),e.getMessage());
		}
		
		return respondResult;
	}
	
	
	@PostMapping("/ajax_init_password")
	@ResponseBody
	public RespondResult ajaxInitPassword(Integer id,HttpServletRequest request){
		RespondResult respondResult = null;
		try{
			int count = userService.initPassword(id, myConfig.getInitUserPassword());
			
			if(count == 1){
				respondResult = new RespondResult(true, RespondResult.successCode, "初始化密码成功", "");
			}else{
				respondResult = new RespondResult(true, RespondResult.errorCode, "初始化密码失败", "初始化改密码失败");
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			respondResult = new RespondResult(false, RespondResult.errorCode, e.getMessage(),e.getMessage());
		}
		
		return respondResult;
	}
	
}
