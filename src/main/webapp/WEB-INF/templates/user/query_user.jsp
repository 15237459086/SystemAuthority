<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/base_resource.jsp" %>
<link rel="stylesheet" href="${basePath}assets/multiselect-master/css/style.css" />
<script type="text/javascript" src="${basePath}assets/js/iframe.js"></script>
<script type="text/javascript" src="${basePath}assets/js/user/query_user.js"></script>
<script type="text/javascript" src="${basePath}assets/multiselect-master/js/multiselect.js"></script>
<div class="loc">
	<h3>用户管理</h3>
	<ul class="loc_loc"><li> 当前位置：系统管理 > 用户管理</li>
	</ul>
	
</div>
<div class="list_con_table">
	 
   <div class="search_table">
   <input type="hidden" id="basePath" value="${basePath }"/>
   <form id="queryForm">
   	<input type="hidden" name="currentPage" value="1"/>
    <ul>
   
    <li class="Label_1">用户编号：&nbsp;</li>
    <li class="Label_2"><input class="input_box" type="text" name="userCode"/></li>
    <li class="Label_1">用户名：&nbsp;</li>
    <li class="Label_2"><input class="input_box" type="text" name="userName" /></li>
    <li class="Label_1">身份证号：&nbsp;</li>
    <li class="Label_2"><input class="input_box" type="text" name="idNumber"/></li>
    <li class="Label_1">电话号码：&nbsp;</li>
    <li class="Label_2">
    	<input class="input_box" type="text" name="telePhone"/>
    </li>
    
    <li class="Label_5">
     <!-- <a data-info='1231231' onclick='Pigeonhole(this)'>新增</a> -->
     <a onclick='layerCreateUser()'>新增</a>
     <a id="queryBtn" onclick="queryBtnClick()">查询</a>
    </li>
    </ul>
    </form>
   </div>
  
    
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
		      <td class="tdLabel_4">状态</td>
		      <td class="tdLabel_4">操作</td>
	      </tr>
      </thead>
      <tbody>
      	 <tr hidden="hidden" id="template_tr">
      	  <td class="tdLabel_5"><span class="user_code"></span></td>
	      <td class="tdLabel_5"><span class="user_name"></span></td>
	      <td class="tdLabel_5"><span class="user_nick_name"></span></td>
	      <td class="tdLabel_5"><span class="id_number"></span></td>
	      <td class="tdLabel_5"><span class="tele_phone"></span></td>
	      <td class="tdLabel_5"><span class="user_desc"></span></td>
	      <td class="tdLabel_5"><span class="status"></span></td> 
	      <td class="tdLabel_5">
	      	<a class="operate_pigeonhole" onclick="layerUpdateUser(this)" title="编辑">编辑&nbsp;</a>
	      	&nbsp;&nbsp;<a class="operate_pigeonhole" onclick="layerUserRole(this)" title="设置角色">设置角色&nbsp;</a>
	      	&nbsp;&nbsp;<a class="operate_pigeonhole" onclick="initPassword(this)" title="初始化密码">初始化密码&nbsp;</a>
	      </td>
	     </tr>
      	
      </tbody>
     </table>
 <div id="pageList" class="pageList">
    <ul class="pagination clearfix" id="page_plus"></ul>
    <div class="pagination">
        <div>总共：<b id="totalPage">0</b> 条信息    当前页是第 <b id="currentPage">0/0</b>　页</div>
    </div>
</div>


<div hidden="hidden" id="create_user_div">
	<form>
		<div class="search_table" style="width: 380px;">
		 <ul>
   
		    <li class="Label_1" style="width: 40%">用户编号：&nbsp;</li>
		    <li class="Label_2" style="width: 60%"><input class="input_box" type="text" name="userCode" required/></li>
		    <li class="Label_1" style="width: 40%">密码&nbsp;</li>
		    <li class="Label_2" style="width: 60%"><input class="input_box" type="password" name="password" required/></li>
		    <li class="Label_1" style="width: 40%">用户名：&nbsp;</li>
		    <li class="Label_2" style="width: 60%"><input class="input_box" type="text" name="userName" required/></li>
		    <li class="Label_1" style="width: 40%">用户昵称：&nbsp;</li>
		    <li class="Label_2" style="width: 60%"><input class="input_box" type="text" name="userNickName" required/></li>
		    <li class="Label_1" style="width: 40%">身份证号：&nbsp;</li>
		    <li class="Label_2" style="width: 60%"><input class="input_box" type="text" name="idNumber" required/></li>
		    <li class="Label_1" style="width: 40%">电话号码：&nbsp;</li>
		    <li class="Label_2" style="width: 60%">
		    	<input class="input_box" type="text" name="telePhone" required/>
		    </li>
		     <li class="Label_1" style="width: 40%">用户所属标识：&nbsp;</li>
		    <li class="Label_2" style="width: 60%">
		    	<input class="input_box" type="text" name="userDesc"/>
		    </li>
		    <li class="Label_5">
		   
		     <a class="ceate_user_btn">新增</a>
		    </li>
	    </ul>
	    </div>
	</form>
</div>


<div hidden="hidden" id="update_user_div">
	<form>
		<input type="hidden" name="id"/>
		<input type="hidden" name="version"/>
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
		    <li class="Label_1" style="width: 40%">状态：&nbsp;</li>
		    <li class="Label_2" style="width: 60%">
		    	<input type="radio" name="status" class="able" value="1"/> 启用
		    	<input type="radio" name="status" class="disable" value="0"/> 禁用
		    </li>
		    <li class="Label_5">
		   
		     <a onclick="updateUserFormSubmit()">保存</a>
		    </li>
	    </ul>
	    </div>
	</form>
</div>
<div hidden="hidden" id="user_role_div">
	<form action="">
	
	<div class="search_table" style="width: 380px; margin-left: 10px;">
		<ul>
   
		    <li class="Label_1" style="width: 40%">用户编号：&nbsp;</li>
		    <li class="Label_2" style="width: 60%"><input class="input_box" type="text" readonly="readonly" name="userCode"/></li>
		    <li class="Label_1" style="width: 40%">用户名：&nbsp;</li>
		    <li class="Label_2" style="width: 60%"><input class="input_box" type="text" disabled="disabled" name="userName" /></li>
	    </ul>
    </div>
	<div class="row" style="width: 400px; margin-left: 5px;">
		<div class="col-xs-12">
	       	 已选角色
	    </div>
	    <div class="col-xs-5">
	        <select id="multiselect1" class="form-control" multiple="multiple">
	            
	        </select>
	    </div>
	    
	    <div class="col-xs-2">
	        <button type="button" id="multiselect1_rightAll" class="btn btn-block"><i class="glyphicon glyphicon-forward"></i></button>
	        <button type="button" id="multiselect1_rightSelected" class="btn btn-block"><i class="glyphicon glyphicon-chevron-right"></i></button>
	        <button type="button" id="multiselect1_leftSelected" class="btn btn-block"><i class="glyphicon glyphicon-chevron-left"></i></button>
	        <button type="button" id="multiselect1_leftAll" class="btn btn-block"><i class="glyphicon glyphicon-backward"></i></button>
	    </div>
	    
	    <div class="col-xs-5">
	        <select id="multiselect1_to" class="form-control" multiple="multiple"></select>
	    </div>
	    
	</div>
	<br/>
	<div class="search_table" style="width: 380px;margin-left: 10px;">
		 <ul>
		 	<li class="Label_5">
		   
		     <a onclick="updateUserRoleFormSubmit()">保存</a>
		    </li>
		 </ul>
	</div>
	</form>
</div>
