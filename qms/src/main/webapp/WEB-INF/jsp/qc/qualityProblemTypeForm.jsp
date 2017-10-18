<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>

<html>
<head>
<script type="text/javascript">
$(document).ready(function(){
	$("#resourceForm").validate({
		submitHandler:function(form){
		    addOrUpdate();
		}
	});
});

function addOrUpdate() {
	var url = "qc/qualityProblemType/addChild";
	if ("${qpType.id}" > 0) {
		url = "qc/qualityProblemType/update";
	}

	$.ajax({
		type: "POST",
		url: url,
		data: $("#resourceForm").serialize(),
		dataType: "json",
		async: false,
		success: function(data) {
			window.parent.location.href = "qc/qualityProblemType/list";
		}
	});
}
</script>
</head>
<body>
<form id="resourceForm" method="post">
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
<form:hidden path="qpType.id"/>
<form:hidden path="qpType.pid"/>
<table class="datatable">
	<tr>
		<th align="right" width="100"><span style="color: red">*</span>&nbsp;问题类型名称：</th>
		<td><form:input path="qpType.name" cssClass="required"/></td>
	</tr>
	<tr>
		<th align="right">触红标识：</th>
		<td>
			<form:radiobutton path="qpType.touchRedFlag" value="0" label="未触红"/>　
			<form:radiobutton path="qpType.touchRedFlag" value="1" label="触红"/>
		</td>
	</tr>
	<tr>
		<th align="right">使用方：</th>
		<td>
			<form:checkbox path="qpType.cmpQcUse" value="1" label="投诉质检"/>　
			<form:checkbox path="qpType.operQcUse" value="1" label="运营质检"/>　
			<form:checkbox path="qpType.devQcUse" value="1" label="研发质检"/>
			<form:checkbox path="qpType.innerQcUse" value="1" label="内部质检"/>
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
