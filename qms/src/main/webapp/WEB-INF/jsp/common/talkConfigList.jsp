<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>话术配置</title>
<script type="text/javascript">
$(function(){
	$('#accordion').accordion({
    	collapsible: true,
    	heightStyle: "content"
    });
	

	});

</script>
<style type="text/css">
</style>
</head>
<body>
<c:if test="${loginUser.role.type==3}">
<div id="accordion">
<h3>话术配置</h3>
<div align="right">
	<input type="button" class="blue" value="添加" onclick="openWin('添加话术', 'common/talkConfig/toAdd', 750, 380)">
</div>
</div>
</c:if>

<table class="listtable">
	<tr>
		<th style="width:10%">类型</th>
		<th style="width:80%">内容</th>
		<th style="width:10%">操作</th>
	</tr>
	<c:forEach items="${dataList}" var="talk">
	<tr>
		
		<td style="text-align: center;width:10%">	${talk.type}</td>
		<td style="text-align: left;width:80%" class="shorten130" >	${talk.content}</td>
		<td style="width:10%">
			<input type="button" class="blue" value="修改" onclick="openWin('修改话术', 'common/talkConfig/${talk.id}/toUpdate', 750, 380)">
		</td>
	</tr>
	</c:forEach>
</table>
</body>
</html>
