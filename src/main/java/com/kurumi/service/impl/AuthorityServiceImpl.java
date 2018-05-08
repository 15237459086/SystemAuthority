package com.kurumi.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kurumi.mapper.AuthorityMapper;
import com.kurumi.pojo.Authority;
import com.kurumi.query.AuthorityQuery;
import com.kurumi.service.AuthorityService;

@Service
public class AuthorityServiceImpl implements AuthorityService {

	@Autowired
	private AuthorityMapper authorityMapper;
	
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public int insert(Authority record) {
		// TODO Auto-generated method stub
		int count = authorityMapper.getAuthorityCountByCodeOrName(record.getAuthorityCode(), record.getAuthorityName());
		if(count > 0){
			return 2;
		}
		return authorityMapper.insert(record);
	}

	@Transactional(readOnly=true)
	@Override
	public List<Map<String, Object>> getAuthorityByQueryAuthority(AuthorityQuery record) {
		// TODO Auto-generated method stub
		return authorityMapper.getAuthorityByQueryAuthority(record);
	}

	@Transactional(readOnly=true)
	@Override
	public int getAuthorityCountByQueryAuthority(AuthorityQuery record) {
		// TODO Auto-generated method stub
		return authorityMapper.getAuthorityCountByQueryAuthority(record);
	}

	@Transactional(readOnly=true)
	@Override
	public Authority selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return authorityMapper.selectByPrimaryKey(id);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public int update(Authority record) {
		// TODO Auto-generated method stub
		int count = authorityMapper.getAuthorityCountByCodeOrNameAndId(record.getAuthorityCode(), record.getAuthorityName(),record.getId());
		if(count > 0){
			return 2;
		}
		return authorityMapper.updateByPrimaryKey(record);
	}

}
