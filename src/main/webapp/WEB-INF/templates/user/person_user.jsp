<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/base_resource.jsp" %>
<link rel="stylesheet" href="${basePath}assets/multiselect-master/css/style.css" />
<script type="text/javascript" src="${basePath}assets/js/iframe.js"></script>
<script type="text/javascript" src="${basePath}assets/js/user/person_user.js"></script>
<script type="text/javascript" src="${basePath}assets/multiselect-master/js/multiselect.js"></script>
<div class="loc">
	<h3>个人管理</h3>
	<ul class="loc_loc"><li> 当前位置：系统管理 > 个人管理</li>
	</ul>
	<input type="hidden" id="basePath" value="${basePath }"/>
</div>

 <table style="width:934px; float:left; border-collapse:collapse;" id="query_show_table">
      <thead>
	      <tr>
	      	  <td class="tdLabel_4">用户编号</td>
		      <td class="tdLabel_4">用户名</td>
		      <td class="tdLabel_4">用户昵称</td>
		      <td class="tdLabel_4">身份证</td>
		      <td class="tdLabel_4">电话号码</td>
		      <td class="tdLabel_4">用户所属标识</td>
		      <td class="tdLabel_4">操作</td>
	      </tr>
      </thead>
      <tbody>
      	 <tr id="${user.id}" version = "${user.version}" status = "${user.status}">
      	  <td class="tdLabel_5"><span class="user_code">${user.user_code}</span></td>
	      <td class="tdLabel_5"><span class="user_name">${user.user_name}</span></td>
	      <td class="tdLabel_5"><span class="user_nick_name">${user.user_nick_name}</span></td>
	      <td class="tdLabel_5"><span class="id_number">${user.id_number}</span></td>
	      <td class="tdLabel_5"><span class="tele_phone">${user.tele_phone}</span></td>
	      <td class="tdLabel_5"><span class="user_desc">${user.user_desc}</span></td>
	      <td class="tdLabel_5">
	      	<a class="operate_pigeonhole" onclick="layerUpdateUser(this)" title="编辑">编辑&nbsp;</a>
	      	&nbsp;&nbsp;<a class="operate_pigeonhole" onclick="layerUpdatePassword(this)" title="修改密码">修改密码&nbsp;</a>
	      </td>
	     </tr>
      	
      </tbody>
  </table>

<div hidden="hidden" id="update_user_div">
	<form>
		<input type="hidden" name="id"/>
		<input type="hidden" name="version"/>
		<input type="hidden" name="status"/>
		<div class="search_table" style="width: 380px;">
		 <ul>
   
		    <li class="Label_1" style="width: 40%">用户编号：&nbsp;</li>
		    <li class="Label_2" style="width: 60%"><input class="input_box" readonly="readonly" type="text" name="userCode"/></li>
		    <li class="Label_1" style="width: 40%">用户名：&nbsp;</li>
		    <li class="Label_2" style="width: 60%"><input class="input_box" type="text" name="userName" /></li>
		    <li class="Label_1" style="width: 40%">用户昵称：&nbsp;</li>
		    <li class="Label_2" style="width: 60%"><input class="input_box" type="text" name="userNickName" /></li>
		    <li class="Label_1" style="width: 40%">身份证号：&nbsp;</li>
		    <li class="Label_2" style="width: 60%"><input class="input_box" type="text" name="idNumber"/></li>
		    <li class="Label_1" style="width: 40%">电话号码：&nbsp;</li>
		    <li class="Label_2" style="width: 60%">
		    	<input class="input_box" type="text" name="telePhone"/>
		    </li>
		     <li class="Label_1" style="width: 40%">用户所属标识：&nbsp;</li>
		    <li class="Label_2" style="width: 60%">
		    	<input class="input_box" type="text" name="userDesc"/>
		    </li>
		   
		    <li class="Label_5">
		   
		     <a onclick="updateUserFormSubmit()">保存</a>
		    </li>
	    </ul>
	    </div>
	</form>
</div>
<div hidden="hidden" id="update_password_div">
	<form>
		<input type="hidden" name="id"/>
		<div class="search_table" style="width: 380px;">
		 <ul>
   
		    <li class="Label_1" style="width: 40%">旧密码：&nbsp;</li>
		    <li class="Label_2" style="width: 60%"><input class="input_box" required="required" type="password" name="oldPassword"/></li>
		    <li class="Label_1" style="width: 40%">新密码：&nbsp;</li>
		    <li class="Label_2" style="width: 60%"><input class="input_box" required="required" type="password" name="newPassword" /></li>
		     <li class="Label_1" style="width: 40%">确认新密码：&nbsp;</li>
		    <li class="Label_2" style="width: 60%"><input class="input_box" required="required" type="password" name="newConfirmPassword" /></li>
		   
		   
		    <li class="Label_5">
		   
		     <a onclick="updatePasswordFormSubmit()">保存</a>
		    </li>
	    </ul>
	    </div>
	</form>
</div>
