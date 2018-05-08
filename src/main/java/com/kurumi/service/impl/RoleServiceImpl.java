package com.kurumi.service.impl;

import java.util.HashMap;
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

	@Transactional(readOnly=true)
	@Override
	public List<Map<String, Object>> getRoleByQueryRole(RoleQuery record) {
		// TODO Auto-generated method stub
		return roleMapper.getRoleByQueryRole(record);
	}

	@Transactional(readOnly=true)
	@Override
	public int getRoleCountByQueryRole(RoleQuery record) {
		// TODO Auto-generated method stub
		return roleMapper.getRoleCountByQueryRole(record);
	}

	@Transactional(readOnly=true)
	@Override
	public Map<String, List<Map<String, Object>>> getRoleAuthorityInit(String roleCode) {
		// TODO Auto-generated method stub
		Map<String, List<Map<String, Object>>> datas = new HashMap<String, List<Map<String, Object>>>();
		List<Map<String, Object>> selectedAuthoritys = roleMapper.getSelectedAuthorityByRoleCode(roleCode);
		datas.put("selectedAuthoritys", selectedAuthoritys);
		List<Map<String, Object>> unSelectedAuthoritys = roleMapper.getUnSelectedAuthorityByRoleCode(roleCode);
		datas.put("unSelectedAuthoritys", unSelectedAuthoritys);
		return datas;
	}

	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public int updateRoleAuthority(String roleCode, String[] authorityCodes) {
		// TODO Auto-generated method stub
		roleMapper.deleteRoleAuthorityByRoleCode(roleCode);
		if(authorityCodes != null){
			for (String authorityCode : authorityCodes) {
				roleMapper.insertRoleAuthority(roleCode, authorityCode);
			}
		}
		return 1;
	}

	
	

}
