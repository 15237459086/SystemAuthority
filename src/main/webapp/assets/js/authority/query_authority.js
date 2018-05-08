
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

var addFormIndex;
var pigeonholeFormIndex;

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
		url: basePath + "authority/ajax_query_authority",
		dataType: "json",
		data:submitData,
		success: function( data ) {
			var success = data['success'];
			if(success){
				
				var params = data['data'];
				var totalCounts = params['totalCounts'];
				if(totalCounts > 0){
					layer.msg("查询成功");
					var authoritys = params['queryDatas'];
					addRows(authoritys);
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
function addRows(authoritys){
	for(var index in authoritys){
		var authority = authoritys[index];
		var add_content=$("#template_tr").clone();
		add_content.removeAttr("hidden");
		add_content.attr("id",authority.id);
		add_content.attr("version",authority.version);
		add_content.find("span[class='authority_code']").html(authority.authority_code);
		
		add_content.find("span[class='authority_name']").html(authority.authority_name);
		add_content.find("span[class='authority_desc']").html(authority.authority_desc);
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


var layerCreateAuthorityIndex
function layerCreateAuthority(){
	
	var add_content=$("#create_authority_div").clone();
	add_content.find("form").attr("id","crate_authority_form");
	layerCreateAuthorityIndex = layer.open({
		  type: 1,
		  title:'新建权限',
		  skin: 'layui-layer-rim', //加上边框
		  area: ['420px', '250px'], //宽高
		  content: add_content.html(),
		  success: function(layero, index){
			  $("#crate_authority_form input").each(function() {
				  $(this).attr("id",$(this).attr("name")+"_1");
			  });
			  var validator = $("#crate_authority_form").validate({
				errorElement: "title",
				submitHandler : function(){
					layer.load(1);
					var basePath = $("#basePath").val();
					var submitData = $('#crate_authority_form').serialize();
					$.ajax({
						url: basePath + "authority/ajax_create_authority",
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
									var authority = data['data'];
									layer.close(layerCreateAuthorityIndex);
									addRow(authority);
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
				messages: {
					authorityCode_1: {
						required: " (必需字段)"
					},authorityName_1: {
						required: " (必需字段)"
					},authorityDesc_1: {
						required: " (必需字段)",
					}
				}
			});		  
		}
	});
}



function createAuthorityFormSubmit(){
	$("#crate_authority_form").submit();
	/**/
}


/*添加列表行*/
function addRow(authority){
	var add_content=$("#template_tr").clone();
	add_content.removeAttr("hidden");
	add_content.attr("id",authority.id);
	add_content.attr("version",authority.version);
	add_content.find("span[class='authority_code']").html(authority.authorityCode);
	
	add_content.find("span[class='authority_name']").html(authority.authorityName);
	add_content.find("span[class='authority_desc']").html(authority.authorityDesc);
	$("#query_show_table tbody").append(add_content);
}

var layerUpdateAuthorityIndex
function layerUpdateAuthority(obj){
	
	var add_content=$("#update_authority_div").clone();
	add_content.find("form").attr("id","update_authority_form");
	layerUpdateAuthorityIndex = layer.open({
		  type: 1,
		  title:'编辑用户',
		  skin: 'layui-layer-rim', //加上边框
		  area: ['420px', '250px'], //宽高
		  content: add_content.html(),
		  success: function(layero, index){
			  
			  var content = $(obj).parent().parent();
			  $("#update_authority_form input[name='id']").val(content.attr("id"));
			  $("#update_authority_form input[name='version']").val(content.attr("version"));
			  $("#update_authority_form input[name='authorityCode']").val(content.find(".authority_code").html());
			  $("#update_authority_form input[name='authorityName']").val(content.find(".authority_name").html());
			  $("#update_authority_form input[name='authorityDesc']").val(content.find(".authority_desc").html());
			  $("#update_authority_form input").each(function() {
				  $(this).attr("id",$(this).attr("name")+"_1");
			  });
			  
			  var validator = $("#update_authority_form").validate({
					errorElement: "title",
					submitHandler : function(){
						layer.load(1);
						var basePath = $("#basePath").val();
						var submitData = $('#update_authority_form').serialize();
						$.ajax({
							url: basePath + "authority/ajax_update_authority",
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
										var authority = data['data'];
										layer.close(layerCreateAuthorityIndex);
										updateRow(authority);
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
					messages: {
						authorityCode_1: {
							required: " (必需字段)"
						},authorityName_1: {
							required: " (必需字段)"
						},authorityDesc_1: {
							required: " (必需字段)",
						}
					}
				});	
		  }
		});
}

function updateAuthorityFormSubmit(){
	$("#update_authority_form").submit();
}


/*修改列表行*/
function updateRow(authority){
	var add_content=$("#query_show_table tr[id='"+authority.id+"']");
	add_content.attr("id",authority.id);
	add_content.attr("version",authority.version);
	
	add_content.find("span[class='authority_code']").html(authority.authorityCode);
	
	add_content.find("span[class='authority_name']").html(authority.authorityName);
	add_content.find("span[class='authority_desc']").html(authority.authorityDesc);
}

