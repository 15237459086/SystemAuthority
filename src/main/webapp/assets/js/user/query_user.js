
$(function(){
	/*$("select").each(function(){
		$(this).append("<option value=''>---请选择---</option>");
	});
*/
	var basePath = $("#basePath").val();
	
	/*$.ajax({
		url: basePath + "base_info/ajax_un_pigeonhole_base_data",
		type: "GET",
		dataType: "json",
		success: function( datas ) {
			console.log(datas);
			init(datas);
			
		}
	});*/
});

function init(baseInfo){
	var medicalDepts = baseInfo['medicalDepts'];
	$("select[name='outHospitalDeptCode'],[name='outDeptCode']").each(function(){
		var options = "";
		for(var index in medicalDepts){
			var medicalDept = medicalDepts[index];
	    	options+="<option value='"+medicalDept.uniq_code+"'>"+medicalDept.label+"</option>"
	    } 
       $(this).append(options);
       
	});
	
	var outHospitalTypes = baseInfo['outHospitalTypes'];
	$("select[name='outHospitalTypeCode']").each(function(){
		var options = "";
		for(var index in outHospitalTypes){
			var outHospitalType = outHospitalTypes[index];
	    	options+="<option value='"+outHospitalType.uniq_code+"'>"+outHospitalType.label+"</option>"
	    } 
       $(this).append(options);
	});
}


/*点击查询按钮*/
function queryBtnClick(){
	$("input[name='currentPage']").val(1);
	queryFormSubmit();
}

/*提交查询*/
function queryFormSubmit(){
	var submitData = $('#queryForm').serialize();
	var basePath = $("#basePath").val();
	layer.load(1);
	
	clearPage();
	$.ajax({
		url: basePath + "user/ajax_query_user",
		dataType: "json",
		data:submitData,
		success: function( data ) {
			var success = data['success'];
			if(success){
				
				var params = data['data'];
				var totalCounts = params['totalCounts'];
				if(totalCounts > 0){
					layer.msg("查询成功");
					var users = params['queryDatas'];
					addRows(users);
					var pageSize = params['pageSize'];
					var currentPage = params['currentPage'];
					initPage(parseInt(totalCounts),parseInt(pageSize),parseInt(currentPage));
				}else{
					layer.msg("查询结果为空");
				}
				
			}else{
				layer.msg("查询失败");
			}
			console.log(data);
			
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			layer.msg("未知错误，请联系管理员");
		},
		complete:function(XMLHttpRequest, textStatus){
			layer.closeAll('loading');
		}
	});
	
};

/*添加列表行*/
function addRows(users){
	for(var index in users){
		var user = users[index];
		var add_content=$("#template_tr").clone();
		add_content.removeAttr("hidden");
		add_content.attr("id",user.id);
		add_content.attr("version",user.version);
		add_content.attr("status",user.status);
		add_content.find("span[class='user_code']").html(user.user_code);
		
		add_content.find("span[class='user_name']").html(user.user_name);
		add_content.find("span[class='user_nick_name']").html(user.user_nick_name);
		add_content.find("span[class='id_number']").html(user.id_number);
		add_content.find("span[class='tele_phone']").html(user.tele_phone);
		add_content.find("span[class='user_desc']").html(user.user_desc);
		if(user.status == '1'){
			add_content.find("span[class='status']").html("已启用");
		}else{
			add_content.find("span[class='status']").html("已禁用");
		}
		
		$("#query_show_table tbody").append(add_content);
    } 
}

/*清空列表和分页控件*/
function clearPage(){
	$("#template_tr").siblings("tr").remove();
	$("#page_plus").html("");
	$("#totalPage").html("0");
    $("#currentPage").html("0/0");
}

/*初始化分页控件*/
function initPage(totalCounts,pageSize,currentPage){
	 var visiblePages = 5;
	 var totalPages =  totalCounts%pageSize==0?(totalCounts/pageSize):(parseInt(totalCounts/pageSize)+1);
	 if(totalCounts < 1){
		$("#page_plus").html("");
		$("#totalPage").html("0");
        $("#currentPage").html("0/0");
	 }else{
		 $('#page_plus').jqPaginator({

	        totalCounts: totalCounts,
	        pageSize:pageSize,
	        visiblePages: visiblePages,
	        currentPage: currentPage,

	        first:'<li class="first"><a href="javascript:void(0);">首页</a></li>',
	        prev: '<li class="prev"><a href="javascript:void(0);">上一页</a></li>',
	        next: '<li class="next"><a href="javascript:void(0);">下一页</a></li>',
	        last: '<li class="last"><a href="javascript:void(0);">末页</a></li>',
	        page: '<li class="page"><a href="javascript:void(0);">{{page}}</a></li>',
	        onPageChange: function (num,type) {

	     		if(type !='init'){
	     			$("input[name='currentPage']").val(num);
	     			queryFormSubmit();
				}
	            $("#totalPage").html(totalCounts);
	            $("#currentPage").html(num+"/"+totalPages);
	        }
	    });
	 }
}


var layerCreateUserIndex
function layerCreateUser(){
	
	var add_content=$("#create_user_div").clone();
	add_content.find("form").attr("id","crate_user_form");
	layerCreateUserIndex = layer.open({
		  type: 1,
		  title:'新建用户',
		  skin: 'layui-layer-rim', //加上边框
		  area: ['420px', '520px'], //宽高
		  content: add_content.html(),
		  success: function(layero, index){
			  $("#crate_user_form input").each(function() {
				  $(this).attr("id",$(this).attr("name"));
			  })
			  var validator = $("#crate_user_form").validate({
				errorElement: "title",
				submitHandler : function(){
					layer.load(1);
					var basePath = $("#basePath").val();
					var submitData = $('#crate_user_form').serialize();
					$.ajax({
						url: basePath + "user/ajax_create_user",
						type: "POST",
						dataType: "json",
						data:submitData,
						success: function( data ) {
							var success = data['success'];
							if(success){
								var stateCode = data['stateCode'];
								var stateMessage = data['stateMessage'];
								if("1" == stateCode){
									layer.msg(stateMessage);
									var user = data['data'];
									layer.close(layerCreateUserIndex);
									addRow(user);
								}else{
									layer.msg(stateMessage);
								}
							}else{
								layer.msg("操作错误，请重试！");
							}
							console.log(data);
							
						},
						error:function(XMLHttpRequest, textStatus, errorThrown){
							layer.msg("未知错误，请联系管理员");
						},
						complete:function(XMLHttpRequest, textStatus){
							layer.closeAll('loading');
						}
					});
				},
				rules:{
					confirmPassword: {
						equalTo: "#password"
					}
				},
				messages: {
					userCode: {
						required: " (必需字段)"
					},password: {
						required: " (必需字段)",
					},confirmPassword: {
						equalTo: "密码不一致"
					}
					,userName: {
						required: " (必需字段)",
					},userNickName: {
						required: " (必需字段)"
					},idNumber: {
						required: " (必需字段)",
					},telePhone: {
						required: " (必需字段)"
					}
				}
			});
			  var createUserBtnOjbs = $("#crate_user_form .ceate_user_btn");
			  createUserBtnOjbs.each(function(){
				  var obj = $(this);
				  $(this).click(function() {
					  createUserFormSubmit();
				  });
				
			  });
			  
		  }
		});
}



function createUserFormSubmit(){
	$("#crate_user_form").submit();
	/**/
}


/*添加列表行*/
function addRow(user){
	var add_content=$("#template_tr").clone();
	add_content.removeAttr("hidden");
	add_content.attr("id",user.id);
	add_content.attr("version",user.version);
	add_content.attr("status",user.status);
	add_content.find("span[class='user_code']").html(user.userCode);
	add_content.find("span[class='user_name']").html(user.userName);
	add_content.find("span[class='user_nick_name']").html(user.userNickName);
	
	add_content.find("span[class='id_number']").html(user.idNumber);
	add_content.find("span[class='tele_phone']").html(user.telePhone);
	add_content.find("span[class='user_desc']").html(user.userDesc);
	
	if(user.status == '1'){
		add_content.find("span[class='status']").html("已启用");
	}else{
		add_content.find("span[class='status']").html("已禁用");
	}
	
	$("#query_show_table tbody").append(add_content);
}

var layerUpdateUserIndex
function layerUpdateUser(obj){
	
	var add_content=$("#update_user_div").clone();
	add_content.find("form").attr("id","update_user_form");
	layerUpdateUserIndex = layer.open({
		  type: 1,
		  title:'编辑用户',
		  skin: 'layui-layer-rim', //加上边框
		  area: ['420px', '520px'], //宽高
		  content: add_content.html(),
		  success: function(layero, index){
			  var content = $(obj).parent().parent();
			  var status = content.attr("status");
			  $("#update_user_form input[name='id']").val(content.attr("id"));
			  $("#update_user_form input[name='version']").val(content.attr("version"));
			  $("#update_user_form input[name='userCode']").val(content.find(".user_code").html());
			  $("#update_user_form input[name='userName']").val(content.find(".user_name").html());
			  $("#update_user_form input[name='userNickName']").val(content.find(".user_nick_name").html());
			  $("#update_user_form input[name='idNumber']").val(content.find(".id_number").html());
			  $("#update_user_form input[name='telePhone']").val(content.find(".tele_phone").html());
			  $("#update_user_form input[name='userDesc']").val(content.find(".user_desc").html());
			  if(status == "1"){
				  $("#update_user_form .able").attr("checked",true);
			  }else{
				  $("#update_user_form .disable").attr("checked",true);
			  }
		  }
		});
}

function updateUserFormSubmit(){
	layer.load(1);
	var basePath = $("#basePath").val();
	var submitData = $('#update_user_form').serialize();
	$.ajax({
		url: basePath + "user/ajax_update_user",
		type: "POST",
		dataType: "json",
		data:submitData,
		success: function( data ) {
			var success = data['success'];
			if(success){
				var stateCode = data['stateCode'];
				var stateMessage = data['stateMessage'];
				if("1" == stateCode){
					layer.msg(stateMessage);
					var user = data['data'];
					layer.close(layerUpdateUserIndex);
					updateRow(user);
				}else{
					layer.msg(stateMessage);
				}
			}else{
				layer.msg("操作错误，请重试！");
			}
			console.log(data);
			
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			layer.msg("未知错误，请联系管理员");
		},
		complete:function(XMLHttpRequest, textStatus){
			layer.closeAll('loading');
		}
	});
}


/*修改列表行*/
function updateRow(user){
	var add_content=$("#query_show_table tr[id='"+user.id+"']");
	add_content.attr("id",user.id);
	add_content.attr("version",user.version);
	add_content.attr("status",user.status);
	add_content.find("span[class='user_code']").html(user.userCode);
	add_content.find("span[class='user_name']").html(user.userName);
	add_content.find("span[class='user_nick_name']").html(user.userNickName);
	
	add_content.find("span[class='id_number']").html(user.idNumber);
	add_content.find("span[class='tele_phone']").html(user.telePhone);
	add_content.find("span[class='user_desc']").html(user.userDesc);
	
	if(user.status == '1'){
		add_content.find("span[class='status']").html("已启用");
	}else{
		add_content.find("span[class='status']").html("已禁用");
	}
	
}
var layerUserRoleIndex;
function layerUserRole(obj){
	var basePath = $("#basePath").val();
	//页面层
	var add_content=$("#user_role_div").clone();
	add_content.find("select[id^='multiselect1'],button[id^='multiselect1']").each(function(){
		var orignalId = $(this).attr("id");
		$(this).attr("id",orignalId.replace("multiselect1", "multiselect1_1"));
       
	});
	var content = $(obj).parent().parent();
	
	
	add_content.find("form").attr("id","update_user_role_form");
	
	
	layerUserRoleIndex = layer.open({
	  type: 1,
	  title:'设置角色',
	  skin: 'layui-layer-rim', //加上边框
	  area: ['420px', '450px'], //宽高
	  content: add_content.html(),
	  success: function(layero, index){
		  var userCode = content.find(".user_code").html();
		  $("#update_user_role_form input[name='userCode']").val(userCode);
		  var userName = content.find(".user_name").html();
		  $("#update_user_role_form input[name='userName']").val(userName);
		  $.ajax({
				url: basePath + "user/ajax_user_role_init",
				dataType: "json",
				data:{userCode:userCode},
				success: function( data ) {
					var datas = data['data'];
					var selectedRoles = datas['selectedRoles'];
					var unSelectedRoles = datas['unSelectedRoles'];
					$("#multiselect1_1_to").each(function(){
						var options = "";
						for(var index in unSelectedRoles){
							var unSelectedRole = unSelectedRoles[index];
					    	options+="<option value='"+unSelectedRole.role_code+"' >"+unSelectedRole.role_name+"</option>"
					    } 
				       $(this).append(options);
				       
					});
					
					$("#multiselect1_1").each(function(){
						var options = "";
						for(var index in selectedRoles){
							var selectedRole = selectedRoles[index];
					    	options+="<option value='"+selectedRole.role_code+"'>"+selectedRole.role_name+"</option>"
					    } 
				       $(this).append(options);
				       
					});
					console.log(data);
					
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
					layer.msg("未知错误，请联系管理员");
				},
				complete:function(XMLHttpRequest, textStatus){
					layer.closeAll('loading');
				}
			});
		  $('#multiselect1_1').multiselect();
		  
		
	  }
	});
}

function updateUserRoleFormSubmit(){
	layer.load(1);
	var basePath = $("#basePath").val();
	var submitData = $('#update_user_role_form').serialize();
	var roleOptions = $("#multiselect1_1 option");
	var userRoleCodes ="";
	roleOptions.each(function(){
		userRoleCodes += "&roleCodes="+$(this).val();
       
	});
	submitData +=userRoleCodes;
	$.ajax({
		url: basePath + "user/ajax_update_user_role",
		type: "POST",
		dataType: "json",
		data:submitData,
		success: function( data ) {
			var success = data['success'];
			if(success){
				var stateCode = data['stateCode'];
				var stateMessage = data['stateMessage'];
				if("1" == stateCode){
					layer.msg(stateMessage);
					layer.close(layerUserRoleIndex);
				}else{
					layer.msg(stateMessage);
				}
			}else{
				layer.msg("操作错误，请重试！");
			}
			console.log(data);
			
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			layer.msg("未知错误，请联系管理员");
		},
		complete:function(XMLHttpRequest, textStatus){
			layer.closeAll('loading');
		}
	});
}

function initPassword(obj){
	
	if(window.confirm('您确认初始化密码？')){
		var basePath = $("#basePath").val();
		layer.load(1);
		var content = $(obj).parent().parent();
		var id = content.attr("id");
		var submitData = {id:id};
        $.ajax({
    		url: basePath + "user/ajax_init_password",
    		type: "POST",
    		dataType: "json",
    		data:submitData,
    		success: function( data ) {
    			var success = data['success'];
    			if(success){
    				var stateCode = data['stateCode'];
    				var stateMessage = data['stateMessage'];
    				if("1" == stateCode){
    					layer.msg(stateMessage);
    				}else{
    					layer.msg(stateMessage);
    				}
    			}else{
    				layer.msg("操作错误，请重试！");
    			}
    			console.log(data);
    			
    		},
    		error:function(XMLHttpRequest, textStatus, errorThrown){
    			layer.msg("未知错误，请联系管理员");
    		},
    		complete:function(XMLHttpRequest, textStatus){
    			layer.closeAll('loading');
    		}
    	});
     }else{
        //alert("取消");
        return false;
    }
}
