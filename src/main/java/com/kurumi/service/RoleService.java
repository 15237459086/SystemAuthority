package com.kurumi.service;

import java.util.List;
import java.util.Map;

import com.kurumi.pojo.Role;
import com.kurumi.query.RoleQuery;

public interface RoleService {

	Role selectByPrimaryKey(Integer id);
	
	int insert(Role record);
	
	int update(Role record);
	
	List<Map<String, Object>> getRoleByQueryRole(RoleQuery record);
	    
	int getRoleCountByQueryRole(RoleQuery record);
	
	Map<String,List<Map<String, Object>>> getRoleAuthorityInit(String roleCode);
	
	int updateRoleAuthority(String roleCode,String[] authorityCodes);
}
