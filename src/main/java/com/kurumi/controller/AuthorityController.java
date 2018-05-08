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

import com.kurumi.pojo.Authority;
import com.kurumi.pojo.RespondResult;
import com.kurumi.query.AuthorityQuery;
import com.kurumi.service.AuthorityService;

@Controller
@RequestMapping("authority")
public class AuthorityController {

	
	@Autowired
	private AuthorityService authorityService;
	
	@GetMapping("query_authority")
	public String queryAuthority(){
		return "authority/query_authority";
	}
	
	
	@GetMapping("/ajax_query_authority")
	@ResponseBody
	public RespondResult ajaxQueryAuthority(AuthorityQuery params,HttpServletRequest request){
		RespondResult respondResult = null;
		try{
			List<Map<String, Object>> roles = new ArrayList<Map<String, Object>>();
			int count = 0;
			if(!params.isEmpty()){
				roles = authorityService.getAuthorityByQueryAuthority(params);
				count = authorityService.getAuthorityCountByQueryAuthority(params);
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
	
	
	
	@PostMapping("/ajax_create_authority")
	@ResponseBody
	public RespondResult ajaxCreateAuthority(Authority authority,HttpServletRequest request){
		RespondResult respondResult = null;
		try{
			int count = authorityService.insert(authority);
			if(count == 1){
				respondResult = new RespondResult(true, RespondResult.successCode, "新建权限成功", authority);
			}else if(count == 2){
				respondResult = new RespondResult(true, RespondResult.repeatCode, "权限编号或名称已存在", authority);
			}
			else{
				respondResult = new RespondResult(true, RespondResult.errorCode, "新建权限失败", "新建权限失败");
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			respondResult = new RespondResult(false, RespondResult.errorCode, e.getMessage(),e.getMessage());
		}
		
		return respondResult;
	}
	
	
	@PostMapping("/ajax_update_authority")
	@ResponseBody
	public RespondResult ajaxUpdateAuthority(Authority authority,HttpServletRequest request){
		RespondResult respondResult = null;
		try{
			int count = authorityService.update(authority);
			if(count == 1){
				authority = authorityService.selectByPrimaryKey(authority.getId());
				respondResult = new RespondResult(true, RespondResult.successCode, "编辑角色成功", authority);
			}else if(count == 2){
				respondResult = new RespondResult(true, RespondResult.repeatCode, "角色编号或名称已存在", authority);
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
