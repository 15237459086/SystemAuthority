package com.kurumi.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kurumi.pojo.Authority;
import com.kurumi.query.AuthorityQuery;

public interface AuthorityMapper {
    

   
    int deleteByPrimaryKey(Integer id);

    int insert(Authority record);

    int insertSelective(Authority record);

    Authority selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Authority record);

    int updateByPrimaryKey(Authority record);
    
    List<Map<String, Object>> getAuthorityByQueryAuthority(AuthorityQuery record);
    
    int getAuthorityCountByQueryAuthority(AuthorityQuery record);
    
    int getAuthorityCountByCodeOrName(@Param("authorityCode") String authorityCode,@Param("authorityName") String authorityName);
    
    int getAuthorityCountByCodeOrNameAndId(@Param("authorityCode") String authorityCode,@Param("authorityName") String authorityName, @Param("id") Integer id);
    
}