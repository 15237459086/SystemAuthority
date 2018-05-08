<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("basePath", basePath);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div id="header_top">
	<div class="header_top_ul">
		<img src="${basePath }assets/images/logo.png" />
		<div class="h_right">
			<ul>
				<li><a>首页</a>&nbsp;|&nbsp;<a>平台主页</a>&nbsp;|&nbsp;<a>友情链接</a>&nbsp;|&nbsp;<a>联网机构</a>&nbsp;|&nbsp;<a>退出登陆</a></li>
				<li>
					<form name="formsearch" action="">
						<input type="hidden" name="kwtype" value="1" /> <input name="q"
							type="text" class="search-keyword" id="search-keyword" value=""
							onFocus="if(this.value==''){this.value='';}"
							onblur="if(this.value==''){this.value='';}" />
						<button type="submit" class="search-submit"></button>
					</form>
				</li>
			</ul>
		</div>
	</div>
</div>

<!------导航条--------->
<div id="nav">
 <ul class="multiUl">
  <li><a href="${basePath }">首页</a></li>
 </ul>
</div>