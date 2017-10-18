<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>

<html>
<head>
<script type="text/javascript">
$(document).ready(function(){
	$("#qcTypeForm").validate({
		submitHandler:function(form){
		    addOrUpdate();
		}
	});
});

function addOrUpdate() {
	var url = "qc/qcType/addChild";
	if ("${qcType.id}" > 0) {
		url = "qc/qcType/update";
	}

	$.ajax({
		type: "POST",
		url: url,
		data: $("#qcTypeForm").serialize(),
		dataType: "json",
		async: false,
		success: function(data) {
			window.parent.location.href = "qc/qcType/list";
		}
	});
}
</script>
</head>
<body>
<form id="qcTypeForm" method="post">
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
<form:hidden path="qcType.id"/>
<form:hidden path="qcType.pid"/>
<table class="datatable">
	<tr>
		<th align="right" width="100"><span style="color: red">*</span>&nbsp;质检类型名称：</th>
		<td><form:input path="qcType.name" cssClass="required"/></td>
	</tr>
	<tr>
		<th align="right">操作：</th>
		<td><input type="submit" class="blue" value="提交"></td>
	</tr>
</table>
</form>
</body>
</html>
