package com.kurumi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kurumi.pojo.RespondResult;
import com.kurumi.pojo.Role;
import com.kurumi.query.RoleQuery;
import com.kurumi.service.RoleService;

@Controller
@RequestMapping("role")
public class RoleController {

	
	@Autowired
	private RoleService roleService;
	
	@GetMapping("query_role")
	public String queryUser(){
		return "role/query_role";
	}
	
	
	@GetMapping("/ajax_query_role")
	@ResponseBody
	public RespondResult ajaxQueryRole(RoleQuery params,HttpServletRequest request){
		RespondResult respondResult = null;
		try{
			List<Map<String, Object>> roles = new ArrayList<Map<String, Object>>();
			int count = 0;
			if(!params.isEmpty()){
				roles = roleService.getRoleByQueryRole(params);
				count = roleService.getRoleCountByQueryRole(params);
			}
			params.setTotalCounts(count);
			params.setQueryDatas(roles);
			respondResult = new RespondResult(true, RespondResult.successCode, "查询成功", params);
		}
		catch (Exception e) {
			// TODO: handle exception
			respondResult = new RespondResult(false, RespondResult.errorCode, e.getMessage(),e.getMessage());
		}
		return respondResult;
	}
	
	
	
	@PostMapping("/ajax_create_role")
	@ResponseBody
	public RespondResult ajaxCreateRole(Role role,HttpServletRequest request){
		RespondResult respondResult = null;
		try{
			int count = roleService.insert(role);
			if(count == 1){
				respondResult = new RespondResult(true, RespondResult.successCode, "新建角色成功", role);
			}else if(count == 2){
				respondResult = new RespondResult(true, RespondResult.repeatCode, "角色编号或名称已存在", role);
			}
			else{
				respondResult = new RespondResult(true, RespondResult.errorCode, "新建角色失败", "新建角色失败");
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			respondResult = new RespondResult(false, RespondResult.errorCode, e.getMessage(),e.getMessage());
		}
		
		return respondResult;
	}
	
	
	@PostMapping("/ajax_update_role")
	@ResponseBody
	public RespondResult ajaxUpdateRole(Role role,HttpServletRequest request){
		RespondResult respondResult = null;
		try{
			int count = roleService.update(role);
			if(count == 1){
				role = roleService.selectByPrimaryKey(role.getId());
				respondResult = new RespondResult(true, RespondResult.successCode, "编辑角色成功", role);
			}else if(count == 2){
				respondResult = new RespondResult(true, RespondResult.repeatCode, "角色编号或名称已存在", role);
			}
			else{
				respondResult = new RespondResult(true, RespondResult.errorCode, "编辑角色失败", "编辑角色失败");
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			respondResult = new RespondResult(false, RespondResult.errorCode, e.getMessage(),e.getMessage());
		}
		
		return respondResult;
	}
}
