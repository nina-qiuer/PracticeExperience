<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
</head>

<body class="login">
<div id="login"><h1><a href="${CONFIG.web_url}" title="${CONFIG.page_title}">${CONFIG.page_title}测试</a></h1>
<div id="error">${errorMsg}</div>
<form name="loginform" id="loginform" method="post" action="${CONFIG.app_url}frm/action/login-doLogin"> 
<input type="hidden" name="refer_to" id="refer_to" value="{refer_to}" /> 
<input type="hidden" name="a" id="a" value="doLogin" />
	<label><p>用户名：</p><input type="text" tabindex="1" name="user.userName"></label>
	<label><p>密码：</p><input type="password" tabindex="2" name="user.password"></label>
	<!-- 
	<p class="forgetmenot"><label><input type="checkbox" value="1" tabindex="4" name="autologin" id="autologin" />  记住我的登录信息</label></p>
	-->
	<p class="submit">
		<input type="submit" tabindex="5" value="登录 »" name="login" />	
	</p>
</form>
<p id="nav"> <a href="#" title="找回密码">忘记密码？</a></p>
<p id="backtoblog"><a href="http://crm.tuniu.com" title="不知道自己在哪？">← 返回 BOSS</a></p>
</div>
</body>
</html>
