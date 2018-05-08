<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("basePath", basePath);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript">
	function loadContent(url) {
		$("#mainContent").attr("src", url);
	}
</script>
<div id="list_main">
	<div id="list_DH">
	  <div class="list_menu2">
	   <h3>系统管理</h3>
	   <shiro:hasRole name="admin">  
	   	<h4 class="li_t"><a onclick="loadContent('${basePath }user/query_user')">用户管理</a></h4>
	  
		</shiro:hasRole>   
	   
	   <h4 class="li_t"><a onclick="loadContent('${basePath }role/query_role')">角色管理</a></h4>
	  
	  <h4 class="li_t"><a onclick="loadContent('${basePath }authority/query_authority')">权限管理</a></h4>
	  
	  
	  </div>
	 </div>
	<div id="list_NR">
		<iframe id="mainContent" style="border: 0px; overflow-x: hidden; min-height: 500px;" width="100%"  scrolling="no" src="">
				
		</iframe>
	</div>
</div>
