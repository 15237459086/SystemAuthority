package com.kurumi.shiro;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import com.kurumi.mapper.UserMapper;

public class MyShiroRealm extends AuthorizingRealm {

	@Autowired
	private UserMapper userMapper;
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		// TODO Auto-generated method stub
	 	/*User user = (User)SecurityUtils.getSubject().getPrincipal();*/
        SimpleAuthorizationInfo info =  new SimpleAuthorizationInfo();
        Subject subject=SecurityUtils.getSubject();
		Session session = subject.getSession();
		String currentRole =(String) session.getAttribute("currentRole");
		//获取用户角色
        Set<String> roleSet = new HashSet<String>();
      //获取用户权限
        Set<String> permissionSet = new HashSet<String>();
		if(currentRole != null){
			/*roleSet.add("admin");*/
			roleSet.add(currentRole);
	        
	        /*permissionSet.add("admin");*/
	        permissionSet.add(currentRole);
	        
		}
        
		info.setRoles(roleSet);

		info.setStringPermissions(permissionSet);
       
        return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		// TODO Auto-generated method stub
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        String loginName = token.getUsername();
        String password = String.valueOf(token.getPassword());
       
        List<Map<String, Object>> users = userMapper.getSupperAdminByLoginNameAndPassword(loginName, password);
        Subject subject=SecurityUtils.getSubject();
		Session session = subject.getSession();
        if(!users.isEmpty()){
    		session.setAttribute("currentRole", "admin");
		}else{
			
			users = userMapper.getUserByLoginNameAndPassword(loginName, password);
			if(!users.isEmpty()){
				session.setAttribute("currentRole", "user");
			}else{
				return null;
			}
			
		}
        
		Map<String, Object> currentUser = users.get(0);
		session.setAttribute("currentUser",currentUser);

	    return new SimpleAuthenticationInfo(loginName, password, getName());
	}

}
