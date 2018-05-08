package com.kurumi.query;

import com.kurumi.util.StringUtil;

public class AuthorityQuery extends PageQuery {
	

	
	private String authorityCode;

	private String authorityName;
	 
	//查询结果集
	private Object queryDatas;

	
	
	public String getAuthorityCode() {
		return StringUtil.meaningStr(authorityCode);
	}

	public void setAuthorityCode(String authorityCode) {
		this.authorityCode = authorityCode;
	}

	public String getAuthorityName() {
		return StringUtil.meaningStr(authorityName);
	}

	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}

	public Object getQueryDatas() {
		return queryDatas;
	}

	public void setQueryDatas(Object queryDatas) {
		this.queryDatas = queryDatas;
	}
	
	
	public boolean isEmpty(){
    	if(this.getAuthorityCode() == null && this.getAuthorityName() == null ){
    		return true;
    	}
    	return false;
    }
	
	 
	 
}
