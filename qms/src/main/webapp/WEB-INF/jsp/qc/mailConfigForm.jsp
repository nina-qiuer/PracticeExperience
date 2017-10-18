<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title></title>
<script type="text/javascript">
$(document).ready(function(){
	$("#mcForm").validate({
		rules:{
			respType: "required",
		},
		submitHandler:function(form){
		    addOrUpdate();
		}
	});
});

function addOrUpdate() {
	var url = "qc/mailConfig/add";
	if ("${mc.id}" > 0) {
		url = "qc/mailConfig/update";
	}

	$.ajax({
		type: "POST",
		url: url,
		data: $("#mcForm").serialize(),
		dataType: "json",
		async: false,
		success: function(data) {
			window.parent.location.href = "qc/mailConfig/list";
		}
	});
}
</script>
</head>
<body>
<form:form id="mcForm" method="post" commandName="mc">
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
<form:hidden path="id"/>
<table class="datatable">
  	<tr>
  		<th>责任归属类型：</th>
  		<td><form:input path="respType"/></td>
  	</tr>
  	<tr>
  		<th>投诉等级：</th>
  		<td><form:input path="cmpLevel"/></td>
  	</tr>
  	<tr>
  		<th>发送人：</th>
  		<td>
  		<p style="color:red">多个邮件用“;”进行分隔</p>
  		<form:textarea path="reAddrs" cols="40" rows="3"/></td>
  	</tr>
    <tr>
      <th>抄送人：</th>
      <td><form:textarea path="ccAddrs" cols="40" rows="3"/></td>
    </tr>
  	<tr>
  		<th>操作：</th>
  		<td><input type="submit" class="blue" value="提交">
  		</td>
  	</tr>
</table>
</form:form>
</body>
</html>
