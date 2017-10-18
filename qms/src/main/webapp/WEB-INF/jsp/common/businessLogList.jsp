<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>业务操作日志</title>
<script type="text/javascript">
$(document).ready(function(){
	$(".accordion").each(function() {
		$(this).accordion({
	    	collapsible: true,
	    	heightStyle: "content"
	    });
	});
});

function search() {
	$("#searchForm").attr("action", "common/businessLog/list");
	$("#searchForm").submit();
}
</script>
</head>

<body>
<form name="form" id="searchForm" method="post" action="common/businessLog/list">
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
<div class="accordion">
	<h3>业务操作日志列表</h3>
	<div align="right">
		<img src="res/img/search.png" class="img_btn" title="查询" onclick="search()">
	</div>
</div>
<table class="listtable" width="100%">
	<tr>
		<th>操作时间</th>
		<th>客户端IP</th>
		<th>操作人</th>
		<th>资源名称</th>
		<th>访问地址</th>
		<th>访问参数</th>
	</tr>
	<c:forEach items="${dto.dataList}" var="log">
	<tr align="center">
		<td><fmt:formatDate value="${log.addTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		<td>${log.clientIP}</td>
		<td>${log.operatorName}</td>
		<td>${log.resName}</td>
		<td>${log.requestUri}</td>
		<td>${log.requestArgs}</td>
	</tr>
	</c:forEach>
</table>
<%@include file="/WEB-INF/jsp/common/pager.jsp" %>
</form>
</body>
</html>
