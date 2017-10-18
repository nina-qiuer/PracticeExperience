<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title></title>
<script type="text/javascript">
$(document).ready(function(){
	$("#rtxRemindForm").validate({
		rules:{
			sendTime:"required",
			title: "required",
			content:"required"
		},
		submitHandler:function(form){
		    addOrUpdate();
		}
	});
});


function addOrUpdate() {
	var url = "common/rtxRemind/add";
	if ("${rtxRemind.id}" > 0) {
		url = "common/rtxRemind/update";
	}
	
	$('#content').escape();
	
	   $.ajax({
		type: "POST",
		url: url,
		data: $("#rtxRemindForm").serialize(),
		dataType: "json",
		async: false,
		success: function(data) {
			layer.alert('提交成功');
			parent.location.reload();
		}
	});  
}
</script>
</head>
<body>
<form id="rtxRemindForm" method="post">
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
<form:hidden path="rtxRemind.id"/>
<table class="datatable">
  	<tr>
  	 <th>提醒时间：</th>
  		<td>
  			<input id="sendTime" name="sendTime" type="text" class="Wdate"
						value='<fmt:formatDate value="${rtxRemind.sendTime }" pattern="yyyy-MM-dd HH:mm:ss"/>'
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"></input>
		</td>
  		 
  	</tr>
  	<tr>
  		<th width="18%">标题：</th>
  		<td><form:input path="rtxRemind.title"/></td>
  	</tr>
  	<tr>
  		<th>内容：</th>
  		<td><form:textarea path="rtxRemind.content" cols="31" rows="3"/></td>
  	</tr>
  	<tr>
  		<th>操作：</th>
  		<td><input type="submit" class="blue" value="提交"></td>
  	</tr>
</table>
</form>
</body>
</html>
