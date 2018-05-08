package com.kurumi.service;

import java.util.List;
import java.util.Map;

import com.kurumi.pojo.User;
import com.kurumi.query.UserQuery;


public interface UserService {
	
	User selectByPrimaryKey(Integer id);

	List<Map<String, Object>> getSupperAdminByLoginNameAndPassword(String loginName, String password);
	
	int insert(User record);
	
	int update(User record);
	
	List<Map<String, Object>> getUserByQueryUser(UserQuery record);
	
	int getUserCountByQueryUser(UserQuery record);
	
	Map<String,List<Map<String, Object>>> getUserRoleInit(String userCode);

	int updateUserRole(String userCode,String[] roleCodes);
}
