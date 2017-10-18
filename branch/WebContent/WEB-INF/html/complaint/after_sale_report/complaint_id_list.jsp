<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人交接投诉单列表</title>

</HEAD>
<body>
	<c:forEach items="${complaintList}" var="complaintId">
			${complaintId } </br>
	</c:forEach>
</body>
</HTML>
