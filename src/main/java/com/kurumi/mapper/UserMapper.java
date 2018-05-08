package com.kurumi.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kurumi.pojo.User;
import com.kurumi.query.UserQuery;

public interface UserMapper {
   
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    List<Map<String, Object>> getSupperAdminByLoginNameAndPassword(@Param("loginName") String loginName,@Param("password") String password);
    
    int getUserByCodeOrName(@Param("userCode") String userCode,@Param("userName") String userName);
    
    int getUserByCodeOrNameAndId(@Param("userCode") String userCode,@Param("userName") String userName,@Param("id") Integer id);
    
    List<Map<String, Object>> getUserByQueryUser(UserQuery record);
    
    int getUserCountByQueryUser(UserQuery record);
    
    
    List<Map<String, Object>> getSelectedRoleByUserCode(@Param("userCode") String userCode);
    
    List<Map<String, Object>> getUnSelectedRoleByUserCode(@Param("userCode") String userCode);
    
    
    int deleteUserRoleByUserCode(@Param("userCode") String userCode);
    
    int insertUserRole(@Param("userCode") String userCode,@Param("roleCode") String roleCode);
}