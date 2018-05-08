package com.kurumi.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kurumi.mapper.RoleMapper;
import com.kurumi.pojo.Role;
import com.kurumi.query.RoleQuery;
import com.kurumi.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleMapper;
	
	@Transactional(readOnly=true)
	@Override
	public Role selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return roleMapper.selectByPrimaryKey(id);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public int insert(Role record) {
		// TODO Auto-generated method stub
		int count = roleMapper.getRoleCountByCodeOrName(record.getRoleCode(), record.getRoleName());
		if(count > 0){
			return 2;
		}
		return roleMapper.insert(record);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public int update(Role record) {
		// TODO Auto-generated method stub
		int count = roleMapper.getRoleCountByCodeOrNameAndId(record.getRoleCode(), record.getRoleName(),record.getId());
		if(count > 0){
			return 2;
		}
		count = roleMapper.updateByPrimaryKey(record);
		return count;
	}

	@Override
	public List<Map<String, Object>> getRoleByQueryRole(RoleQuery record) {
		// TODO Auto-generated method stub
		return roleMapper.getRoleByQueryRole(record);
	}

	@Override
	public int getRoleCountByQueryRole(RoleQuery record) {
		// TODO Auto-generated method stub
		return roleMapper.getRoleCountByQueryRole(record);
	}

	
	

}
