package com.kurumi.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kurumi.mapper.UserMapper;
import com.kurumi.pojo.User;
import com.kurumi.query.UserQuery;
import com.kurumi.service.UserService;
import com.kurumi.util.MD5Util;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	
	
	@Transactional(readOnly=true)
	@Override
	public List<Map<String, Object>> getSupperAdminByLoginNameAndPassword(String loginName, String password) {
		// TODO Auto-generated method stub
		return userMapper.getSupperAdminByLoginNameAndPassword(loginName, password);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public int insert(User record) {
		// TODO Auto-generated method stub
		int count = userMapper.getUserByCodeOrName(record.getUserCode(), record.getUserName());
		if(count > 0){
			return 2;
		}
		record.setPassword(MD5Util.getMD5(record.getPassword()));
		return userMapper.insert(record);
	}

	@Transactional(readOnly=true)
	@Override
	public List<Map<String, Object>> getUserByQueryUser(UserQuery record) {
		// TODO Auto-generated method stub
		return userMapper.getUserByQueryUser(record);
	}

	@Transactional(readOnly=true)
	@Override
	public int getUserCountByQueryUser(UserQuery record) {
		// TODO Auto-generated method stub
		return userMapper.getUserCountByQueryUser(record);
	}

	@Transactional(readOnly=true)
	@Override
	public User selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return userMapper.selectByPrimaryKey(id);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public int update(User record) {
		// TODO Auto-generated method stub
		int count = userMapper.getUserByCodeOrNameAndId(record.getUserCode(), record.getUserName(), record.getId());
		if(count > 0){
			return 2;
		}
		return userMapper.updateByPrimaryKey(record);
	}

	@Override
	public Map<String, List<Map<String, Object>>> getUserRoleInit(String userCode) {
		// TODO Auto-generated method stub
		Map<String, List<Map<String, Object>>> datas = new HashMap<String, List<Map<String, Object>>>();
		List<Map<String, Object>> selectedRoles = userMapper.getSelectedRoleByUserCode(userCode);
		datas.put("selectedRoles", selectedRoles);
		List<Map<String, Object>> unSelectedRoles = userMapper.getUnSelectedRoleByUserCode(userCode);
		datas.put("unSelectedRoles", unSelectedRoles);
		return datas;
	}

	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public int updateUserRole(String userCode, String[] roleCodes) {
		// TODO Auto-generated method stub
		userMapper.deleteUserRoleByUserCode(userCode);
		if(roleCodes != null){
			for (String roleCode : roleCodes) {
				userMapper.insertUserRole(userCode, roleCode);
			}
		}
		return 1;
	}

	@Transactional(readOnly=true)
	@Override
	public List<Map<String, Object>> getUserByLoginNameAndPassword(String loginName, String password) {
		// TODO Auto-generated method stub
		return userMapper.getUserByLoginNameAndPassword(loginName, password);
	}

	@Transactional(readOnly=true)
	@Override
	public Map<String, Object> remoteLoginCheck(String loginName, String password) {
		// TODO Auto-generated method stub
		Map<String, Object> datas = new HashMap<String, Object>();
		List<Map<String, Object>> users = userMapper.getUserByLoginNameAndPassword(loginName, password);
		if(users.isEmpty()){
			return null;
		}
		datas.put("currentUser", users.get(0));
		List<Map<String, Object>> roles = userMapper.getSelectedRoleByUserCode(loginName);
		datas.put("roles", roles);
		List<Map<String, Object>> authoritys = userMapper.getSelectedAuthorityByUserCode(loginName);
		datas.put("authoritys", authoritys);
		return datas;
	}

	@Transactional(readOnly=true)
	@Override
	public List<Map<String, Object>> getUserByUserCode(String userCode) {
		// TODO Auto-generated method stub
		return userMapper.getUserByUserCode(userCode);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public int updatePassword(Integer id, String oldPassword, String newPassword) {
		// TODO Auto-generated method stub
		return userMapper.updatePassword(id, MD5Util.getMD5(oldPassword), MD5Util.getMD5(newPassword));
	}

	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public int initPassword(Integer id, String password) {
		// TODO Auto-generated method stub
		return userMapper.initPassword(id, MD5Util.getMD5(password));
	}

	

}
