<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/base_resource.jsp" %>
<link rel="stylesheet" href="${basePath}assets/multiselect-master/css/style.css" />
<script type="text/javascript" src="${basePath}assets/js/iframe.js"></script>
<script type="text/javascript" src="${basePath}assets/js/authority/query_authority.js"></script>
<script type="text/javascript" src="${basePath}assets/multiselect-master/js/multiselect.js"></script>
<div class="loc">
	<h3>权限管理</h3>
	<ul class="loc_loc"><li> 当前位置：系统管理 > 权限管理</li>
	</ul>
	
</div>
<div class="list_con_table">
	 
   <div class="search_table">
   <input type="hidden" id="basePath" value="${basePath }"/>
   <form id="queryForm" style="width: 100%">
   	<input type="hidden" name="currentPage" value="1"/>
    <ul>
   
    <li class="Label_1">权限编号：&nbsp;</li>
    <li class="Label_2"><input class="input_box" type="text" name="authorityCode"/></li>
    <li class="Label_1">权限名称：&nbsp;</li>
    <li class="Label_2"><input class="input_box" type="text" name="authorityName" /></li>
    
    <li class="Label_5">
     <a onclick='layerCreateAuthority()'>新增</a>
     <a id="queryBtn" onclick="queryBtnClick()">查询</a>
    </li>
    </ul>
    </form>
   </div>
  
    
</div>
 <table style="width:934px; float:left; border-collapse:collapse;" id="query_show_table">
      <thead>
	      <tr>
	      	  <td class="tdLabel_4">权限编号</td>
		      <td class="tdLabel_4">权限名称</td>
		      <td class="tdLabel_4">权限描述</td>
		      <td class="tdLabel_4">操作</td>
	      </tr>
      </thead>
      <tbody>
      	 <tr hidden="hidden" id="template_tr">
      	  <td class="tdLabel_5"><span class="authority_code"></span></td>
	      <td class="tdLabel_5"><span class="authority_name"></span></td>
	      <td class="tdLabel_5"><span class="authority_desc"></span></td>
	      <td class="tdLabel_5">
	      	<a class="operate_pigeonhole" onclick="layerUpdateAuthority(this)" title="编辑">编辑&nbsp;</a>
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


<div hidden="hidden" id="create_authority_div">
	<form>
		<div class="search_table" style="width: 380px;">
		 <ul>
   
		    <li class="Label_1" style="width: 40%">权限编号：&nbsp;</li>
		    <li class="Label_2" style="width: 60%"><input class="input_box" type="text" name="authorityCode" required/></li>
		    <li class="Label_1" style="width: 40%">权限名称：&nbsp;</li>
		    <li class="Label_2" style="width: 60%"><input class="input_box" type="text" name="authorityName" required/></li>
		    <li class="Label_1" style="width: 40%">权限描述：&nbsp;</li>
		    <li class="Label_2" style="width: 60%"><input class="input_box" type="text" name="authorityDesc" required/></li>
		    <li class="Label_5">
		   
		     <a onclick="createAuthorityFormSubmit()">新增</a>
		    </li>
	    </ul>
	    </div>
	</form>
</div>


<div hidden="hidden" id="update_authority_div">
	<form>
		<input type="hidden" name="id"/>
		<input type="hidden" name="version"/>
		<div class="search_table" style="width: 380px;">
		 <ul>
   
		    <li class="Label_1" style="width: 40%">权限编号：&nbsp;</li>
		    <li class="Label_2" style="width: 60%"><input class="input_box" type="text" name="authorityCode" required/></li>
		    <li class="Label_1" style="width: 40%">权限名称：&nbsp;</li>
		    <li class="Label_2" style="width: 60%"><input class="input_box" type="text" name="authorityName" required/></li>
		    <li class="Label_1" style="width: 40%">权限描述：&nbsp;</li>
		    <li class="Label_2" style="width: 60%"><input class="input_box" type="text" name="authorityDesc" required/></li>
		    <li class="Label_5">
		   
		     <a onclick="updateAuthorityFormSubmit()">保存</a>
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
