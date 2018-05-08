package com.kurumi.service;

import java.util.List;
import java.util.Map;

import com.kurumi.pojo.Authority;
import com.kurumi.query.AuthorityQuery;

public interface AuthorityService {

	int insert(Authority record);
	
	List<Map<String, Object>> getAuthorityByQueryAuthority(AuthorityQuery record);
	    
	int getAuthorityCountByQueryAuthority(AuthorityQuery record);
	
	Authority selectByPrimaryKey(Integer id);
	
	
	int update(Authority record);
}
