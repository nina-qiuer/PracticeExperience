<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>运营质检模板</title>
<script type="text/javascript">
	
	function chooseConfig(obj){
		
		 var content  =  $(obj).parent().next().next();
		 parent.editor1.html(content.html());
		 var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
		 parent.layer.close(index); //再执行关闭   
	}

</script>
<style type="text/css">
</style>
</head>
<body>
<table class="listtable">
	<tr>
		<th style="width:10%">选择</th>
		<th style="width:10%">话术类型</th>
		<th style="width:80%">话术详情</th>
	</tr>
	<c:forEach items="${dataList}" var="talk">
	<tr>
		<td style="width:10%"><input type="radio"  name="choosedConfig"  value="${talk.id}"  title="点选后自动关闭并填充"  onclick="chooseConfig(this)"/></td>
		<td style="width:10%">${talk.type}</td>
		<td style="text-align: left;width:80%"  >${talk.content}</td>
	</tr>
	</c:forEach>
</table>
</body>
</html>
