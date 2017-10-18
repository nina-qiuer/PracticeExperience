<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>

<html>
<head>
<link rel="stylesheet" type="text/css" href="res/plugins/KindEditor/css/default.css"/>
<script type="text/javascript" src="res/plugins/KindEditor/kindeditor-min.js"></script>
<script type="text/javascript" src="res/plugins/KindEditor/lang/zh_CN.js"></script>

<script type="text/javascript">
var editor1;
KindEditor.ready(function(K) {
	editor1 = K.create('#analysis', {
		resizeType : 1,
		allowPreviewEmoticons : false,
		allowImageUpload : false,
		items : ['fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', 
				 'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', '|', 'hr', 'fullscreen']
	});
});

$(document).ready(function(){
	$("#kcpForm").validate({
		submitHandler:function(form){
		    audit();
		}
	});
});

function audit() {
	editor1.sync();
	if(editor1.text() == "") {
		layer.alert("问题分析不能为空!", {icon: 5});
		return false;
	}
	
	$.ajax({
		type: "POST",
		url: "qs/kcp/audit",
		data: $("#kcpForm").serialize(),
		dataType: "json",
		async: false,
		success: function(data) {
			window.parent.search();
		}
	});
}

function passOrReturn(auditFlag) {
	$("#auditFlag").val(auditFlag);
	$("#kcpForm").submit();
}
</script>
</head>
<body>
<form id="kcpForm" method="post">
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
<input type="hidden" id="auditFlag" name="auditFlag">
<form:hidden path="kcp.id"/>
<form:hidden path="kcp.state"/>
<table class="datatable">
	<tr>
		<th align="right" width="86px">名称：</th><td>${kcp.name}</td>
	</tr>
	<tr>
		<th align="right">问题描述：</th><td>${kcp.description}</td>
	</tr>
	<tr>
		<th align="right">举例说明：</th><td>${kcp.example}</td>
	</tr>
	<tr>
		<th align="right"><span style="color: red">*</span>&nbsp;重要程度：</th>
		<td>
			<form:select path="kcp.importantFlag">
				<form:option value="1">普通</form:option>
				<form:option value="2">重要</form:option>
				<form:option value="3">非常重要</form:option>
			</form:select>
		</td>
	</tr>
	<tr>
		<th align="right"><span style="color: red">*</span>&nbsp;类型：</th>
		<td>
			<form:select path="kcp.kcpTypeId">
				<form:options items="${kcpTypeList}" itemValue="id" itemLabel="name"/>
			</form:select>
		</td>
	</tr>
	<tr>
		<th align="right"><span style="color: red">*</span>&nbsp;来源：</th>
		<td>
			<form:select path="kcp.kcpSourceId">
				<form:options items="${kcpSourceList}" itemValue="id" itemLabel="name"/>
			</form:select>
		</td>
	</tr>
	<tr>
		<th align="right"><span style="color: red">*</span>&nbsp;问题分析：</th>
		<td>
			<form:textarea path="kcp.analysis" style="height:214px;visibility:hidden;" cssClass="required"/>
		</td>
	</tr>
	<tr>
		<th align="right">操作：</th>
		<td>　
			<input type="button" class="blue" value="审核通过" onclick="passOrReturn(1)">　
			<input type="button" class="blue" value="退回" onclick="passOrReturn(0)">
		</td>
	</tr>
</table>
</form>
</body>
</html>
