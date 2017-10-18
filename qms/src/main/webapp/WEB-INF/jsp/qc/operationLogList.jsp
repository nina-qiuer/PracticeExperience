<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script  type="text/javascript">
$(document).ready(function() {
	$(".accordion").each(function() {
		$(this).accordion({
	    	collapsible: true,
//	    	active:false, //默认折叠
	    	heightStyle: "content"
	    });
	});
	
});


</script>

<title>业务操作日志列表</title>
</head>
<body >
<form name="searchForm" id="searchForm" method="post" action="" >
<div class="accordion">
<h3>业务操作日志列表</h3> 
</div>
<table class="listtable" style="width:100%">
  <tr>
  		<th class="addTime" style="width:200px">添加时间</th>
 		<th class="dealPeopleName" style="width:100px">操作人</th>
		<th class="dealDepart" style="width:100px">角色</th>
		<th class="flowName" style="width:200px">事件</th>
		<th class="content" >备注</th>
  </tr>
 <c:forEach items="${list}" var="log">
 
 	 <tr>
 	         <td style="width:200px" class="addTime" ><fmt:formatDate value="${log.addTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	 	     <td style="width:100px" class="dealPeopleName"> ${log.dealPeopleName}</td>
	  		 <td style="width:100px" class="dealDepart">${log.dealDepart}</td>
	  		 <td style="width:200px" class="flowName">${log.flowName}</td>
	  		 <td  class="content left">${log.content}</td>
 	</tr>
 </c:forEach>
</table>
</form>
</body>
</html>
