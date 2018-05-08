
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
		url: basePath + "role/ajax_query_role",
		dataType: "json",
		data:submitData,
		success: function( data ) {
			var success = data['success'];
			if(success){
				
				var params = data['data'];
				var totalCounts = params['totalCounts'];
				if(totalCounts > 0){
					layer.msg("查询成功");
					var roles = params['queryDatas'];
					addRows(roles);
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
function addRows(roles){
	for(var index in roles){
		var role = roles[index];
		var add_content=$("#template_tr").clone();
		add_content.removeAttr("hidden");
		add_content.attr("id",role.id);
		add_content.attr("version",role.version);
		add_content.find("span[class='role_code']").html(role.role_code);
		add_content.find("span[class='role_name']").html(role.role_name);
		add_content.find("span[class='role_desc']").html(role.role_desc);
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



//显示新建角色Layer
var layerCreateRoleIndex
function layerCreateRole(){
	
	var add_content=$("#create_role_div").clone();
	add_content.find("form").attr("id","crate_role_form");
	layerCreateRoleIndex = layer.open({
		  type: 1,
		  title:'新建角色',
		  skin: 'layui-layer-rim', //加上边框
		  area: ['420px', '250px'], //宽高
		  content: add_content.html(),
		  success: function(layero, index){
			  var createUserBtnOjbs = $("#crate_role_form .ceate_role_btn");
			  createUserBtnOjbs.each(function(){
				  var obj = $(this);
				  $(this).click(function() {
					 createRoleFormSubmit();
				  });
				
				  
			  });
			  
		  }
		});
}

//提交新建角色
function createRoleFormSubmit(){
	layer.load(1);
	var basePath = $("#basePath").val();
	var submitData = $('#crate_role_form').serialize();
	$.ajax({
		url: basePath + "role/ajax_create_role",
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
					layer.close(layerCreateRoleIndex);
					var role = data['data'];
					addRow(role);
				}else if("2" == stateCode){
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
}

/*添加列表行*/
function addRow(role){
	var add_content=$("#template_tr").clone();
	add_content.removeAttr("hidden");
	add_content.attr("id",role.id);
	add_content.attr("version",role.version);
	add_content.find("span[class='role_code']").html(role.roleCode);
	add_content.find("span[class='role_name']").html(role.roleName);
	add_content.find("span[class='role_desc']").html(role.roleDesc);
	$("#query_show_table tbody").append(add_content);
}


//显示编辑角色Layer
var layerUpdateUserIndex
function layerUpdateRole(obj){
	
	var add_content=$("#update_role_div").clone();
	add_content.find("form").attr("id","update_role_form");
	layerUpdateUserIndex = layer.open({
		  type: 1,
		  title:'编辑角色',
		  skin: 'layui-layer-rim', //加上边框
		  area: ['420px', '250px'], //宽高
		  content: add_content.html(),
		  success: function(layero, index){
			  var content = $(obj).parent().parent();
			  $("#update_role_form input[name='id']").val(content.attr("id"));
			  $("#update_role_form input[name='version']").val(content.attr("version"));
			  $("#update_role_form input[name='roleCode']").val(content.find(".role_code").html());
			  $("#update_role_form input[name='roleName']").val(content.find(".role_name").html());
			  $("#update_role_form input[name='roleDesc']").val(content.find(".role_desc").html());
			  
		  }
		});
}

function updateRoleFormSubmit(){
	layer.load(1);
	var basePath = $("#basePath").val();
	var submitData = $('#update_role_form').serialize();
	$.ajax({
		url: basePath + "role/ajax_update_role",
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
					layer.close(layerUpdateUserIndex);
					var role = data['data'];
					updateRow(role);
				}else if("2" == stateCode){
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
}

/修改列表行*/
function updateRow(role){
	var add_content=$("#query_show_table tr[id='"+role.id+"']");
	add_content.attr("version",role.version);
	add_content.find("span[class='role_code']").html(role.roleCode);
	add_content.find("span[class='role_name']").html(role.roleName);
	add_content.find("span[class='role_desc']").html(role.roleDesc);
}

var layerRoleAuthorityIndex
function layerRoleAuthority(obj){
	
	var basePath = $("#basePath").val();
	//页面层
	var add_content=$("#user_role_div").clone();
	add_content.find("select[id^='multiselect1'],button[id^='multiselect1']").each(function(){
		var orignalId = $(this).attr("id");
		$(this).attr("id",orignalId.replace("multiselect1", "multiselect1_1"));
       
	});
	var content = $(obj).parent().parent();
	
	
	add_content.find("form").attr("id","update_role_authority_form");
	/*add_content.find("form").attr("id","addForm");
	*/
	layerRoleAuthorityIndex = layer.open({
	  type: 1,
	  title:'设置权限',
	  skin: 'layui-layer-rim', //加上边框
	  area: ['420px', '450px'], //宽高
	  content: add_content.html(),
	  success: function(layero, index){
		  var roleCode = content.find(".role_code").html();
		  $("#update_role_authority_form input[name='roleCode']").val(roleCode);
		  var roleName = content.find(".role_name").html();
		  $("#update_role_authority_form input[name='roleName']").val(roleName);
		  $.ajax({
				url: basePath + "role/ajax_role_authority_init",
				dataType: "json",
				data:{roleCode:roleCode},
				success: function( data ) {
					var datas = data['data'];
					var selectedAuthoritys = datas['selectedAuthoritys'];
					var unSelectedAuthoritys = datas['unSelectedAuthoritys'];
					$("#multiselect1_1_to").each(function(){
						var options = "";
						for(var index in unSelectedAuthoritys){
							var unSelectedAuthority = unSelectedAuthoritys[index];
					    	options+="<option value='"+unSelectedAuthority.authority_code+"' >"+unSelectedAuthority.authority_name+"</option>"
					    } 
				       $(this).append(options);
				       
					});
					
					$("#multiselect1_1").each(function(){
						var options = "";
						for(var index in selectedAuthoritys){
							var selectedAuthority = selectedAuthoritys[index];
					    	options+="<option value='"+selectedAuthority.authority_code+"'>"+selectedAuthority.authority_name+"</option>"
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

function updateRoleAuthorityFormSubmit(){
	layer.load(1);
	var basePath = $("#basePath").val();
	var submitData = $('#update_role_authority_form').serialize();
	var authorityOptions = $("#multiselect1_1 option");
	var athorityCodes ="";
	authorityOptions.each(function(){
		athorityCodes += "&authorityCodes="+$(this).val();
       
	});
	submitData +=athorityCodes;
	$.ajax({
		url: basePath + "role/ajax_update_role_authority",
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
					layer.close(layerRoleAuthorityIndex);
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
