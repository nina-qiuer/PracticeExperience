<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>

<html>
<head>
<script type="text/javascript">
$(document).ready(function(){
	$("#acForm").validate({
		submitHandler:function(form){
		    update();
		}
	});
});

function update() {
	$.ajax({
		type: "POST",
		url: "qc/assignConfigCmp/update",
		data: $("#acForm").serialize(),
		dataType: "json",
		async: false,
		success: function(data) {
			window.parent.location.href = "qc/assignConfigCmp/list";
		}
	});
}
</script>
</head>
<body>
<form id="acForm" method="post">
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
<form:hidden path="ac.id"/>
<table class="datatable">
	<tr>
		<th align="right" width="85"><span style="color: red">*</span>&nbsp;质检员：</th>
		<td>&nbsp;${ac.qcPersonName}</td>
	</tr>
	<tr>
		<th align="right">无订单质检：</th>
		<td>
			<form:radiobutton path="ac.noOrderFlag" value="0" label="不处理"/>　
			<form:radiobutton path="ac.noOrderFlag" value="1" label="处理"/>
		</td>
	</tr>
	<tr>
		<th align="right">操作：</th>
		<td><input type="submit" class="blue" value="提交"></td>
	</tr>
</table>
</form>
</body>
</html>
