
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

var layerUpdateUserIndex
function layerUpdateUser(obj){
	
	var add_content=$("#update_user_div").clone();
	add_content.find("form").attr("id","update_user_form");
	layerUpdateUserIndex = layer.open({
		  type: 1,
		  title:'编辑用户',
		  skin: 'layui-layer-rim', //加上边框
		  area: ['420px', '350px'], //宽高
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
			  $("#update_user_form input[name='status']").val(status);
			  
		  }
		});
}

function updateUserFormSubmit(){
	layer.load(1);
	var basePath = $("#basePath").val();
	var submitData = $('#update_user_form').serialize();
	$.ajax({
		url: basePath + "user/ajax_update_person_user",
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
	
}
var layerUpdatePasswordIndex;
function layerUpdatePassword(obj){
	var basePath = $("#basePath").val();
	//页面层
	var add_content=$("#update_password_div").clone();
	
	var content = $(obj).parent().parent();
	
	
	add_content.find("form").attr("id","update_password_form");
	
	
	layerUpdatePasswordIndex = layer.open({
	  type: 1,
	  title:'修改密码',
	  skin: 'layui-layer-rim', //加上边框
	  area: ['420px', '450px'], //宽高
	  content: add_content.html(),
	  success: function(layero, index){
		  $("#update_password_form input").each(function() {
			  $(this).attr("id",$(this).attr("name"));
		  })
		  var content = $(obj).parent().parent();
		  $("#update_password_form input[name='id']").val(content.attr("id"));
		  var validator = $("#update_password_form").validate({
				errorElement: "title",
				submitHandler : function(){
					var basePath = $("#basePath").val();
					layer.load(1);
	    			var submitData = $('#update_password_form').serialize();
	    			$.ajax({
	    				url: basePath + "user/ajax_update_password",
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
	    							layer.close(layerUpdatePasswordIndex);
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
					newConfirmPassword: {
						equalTo: "#newPassword"
					}
				},
				messages: {
					oldPassword: {
						required: " (必需字段)"
					},
					newPassword: {
						required: " (必需字段)"
					}
					,newConfirmPassword: {
						equalTo: "密码不一致"
					}
				}
			});
		
	  }
	});
}

function updatePasswordFormSubmit(){
	$("#update_password_form").submit();
}
