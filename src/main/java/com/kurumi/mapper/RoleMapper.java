package com.kurumi.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kurumi.pojo.Role;
import com.kurumi.query.RoleQuery;

public interface RoleMapper {
   

    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);


    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
    
    
    int getRoleCountByCodeOrName(@Param("roleCode") String roleCode,@Param("roleName") String roleName);
    
    int getRoleCountByCodeOrNameAndId(@Param("roleCode") String roleCode,@Param("roleName") String roleName, @Param("id") Integer id);
    
    
    List<Map<String, Object>> getRoleByQueryRole(RoleQuery record);
    
    int getRoleCountByQueryRole(RoleQuery record);
    
    List<Map<String, Object>> getRoles();
}