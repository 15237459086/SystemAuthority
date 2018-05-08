package com.kurumi.query;

import com.kurumi.util.StringUtil;

public class RoleQuery extends PageQuery {
	

	
	private String roleCode;

	private String roleName;
	 
	//查询结果集
	private Object queryDatas;

	public String getRoleCode() {
		return StringUtil.meaningStr(roleCode);
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return StringUtil.meaningStr(roleName);
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Object getQueryDatas() {
		return queryDatas;
	}

	public void setQueryDatas(Object queryDatas) {
		this.queryDatas = queryDatas;
	}
	
	
	public boolean isEmpty(){
    	if(this.getRoleCode() == null && this.getRoleName() == null ){
    		return true;
    	}
    	return false;
    }
	
	 
	 
}
