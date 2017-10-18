<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>

<html>
<head>
<link rel="stylesheet" type="text/css" href="res/plugins/KindEditor/css/default.css"/>
<script type="text/javascript" src="res/plugins/KindEditor/kindeditor-min.js"></script>
<script type="text/javascript" src="res/plugins/KindEditor/lang/zh_CN.js"></script>

<script type="text/javascript">
var editor1, editor2;
KindEditor.ready(function(K) {
	editor1 = K.create('#description', {
		resizeType : 1,
		allowPreviewEmoticons : false,
		allowImageUpload : false,
		items : ['fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', 
				 'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', '|', 'hr', 'fullscreen']
	});
	
	editor2 = K.create('#example', {
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
		    addOrUpdate();
		}
	});
});

function addOrUpdate() {
	editor1.sync();
	editor2.sync();
	
	if(editor1.text() == "") {
		layer.alert("问题描述不能为空!", {icon: 5});
		return false;
	}
	
	var url = "qs/kcp/add";
	if ("${kcp.id}" > 0) {
		url = "qs/kcp/update";
	}

	$.ajax({
		type: "POST",
		url: url,
		data: $("#kcpForm").serialize(),
		dataType: "json",
		async: false,
		success: function(data) {
			window.parent.search();
		}
	});
}

function saveOrSubmit(flag) {
	$("#state").val(flag);
	$("#kcpForm").submit();
}
</script>
</head>
<body>
<form id="kcpForm" method="post">
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
<form:hidden path="kcp.id"/>
<form:hidden path="kcp.state"/>
<table class="datatable">
	<tr>
		<th align="right" width="86px"><span style="color: red">*</span>&nbsp;名称：</th>
		<td><form:input path="kcp.name" cssClass="required" size="80"/></td>
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
		<th align="right"><span style="color: red">*</span>&nbsp;问题描述：</th>
		<td>
			<form:textarea path="kcp.description" style="height:180px;visibility:hidden;" cssClass="required"/>
		</td>
	</tr>
	<tr>
		<th align="right">举例说明：</th>
		<td>
			<form:textarea path="kcp.example" style="height:180px;visibility:hidden;"/>
		</td>
	</tr>
	<c:if test="${null != kcp.analysis && '' != kcp.analysis}">
	<tr>
		<th align="right">问题分析：</th><td>${kcp.analysis}</td>
	</tr>
	</c:if>
	<tr>
		<th align="right">操作：</th>
		<td>　
			<input type="button" class="blue" value="保存" onclick="saveOrSubmit(0)">　
			<input type="button" class="blue" value="提交" onclick="saveOrSubmit(1)">
		</td>
	</tr>
</table>
</form>
</body>
</html>
