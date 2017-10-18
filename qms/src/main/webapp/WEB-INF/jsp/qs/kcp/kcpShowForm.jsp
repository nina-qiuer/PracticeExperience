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
	 editor1.readonly();
});

</script>
</head>
<body>
<form id="kcpForm" method="post">
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
			<form:select path="kcp.importantFlag" disabled="true">
				<form:option value="1">普通</form:option>
				<form:option value="2">重要</form:option>
				<form:option value="3">非常重要</form:option>
			</form:select>
		</td>
	</tr>
	<tr>
		<th align="right"><span style="color: red">*</span>&nbsp;类型：</th>
		<td>
			<form:select path="kcp.kcpTypeId" disabled="true">
				<form:options items="${kcpTypeList}" itemValue="id" itemLabel="name"/>
			</form:select>
		</td>
	</tr>
	<tr>
		<th align="right"><span style="color: red">*</span>&nbsp;来源：</th>
		<td>
			<form:select path="kcp.kcpSourceId" disabled="true">
				<form:options items="${kcpSourceList}" itemValue="id" itemLabel="name"/>
			</form:select>
		</td>
	</tr>
	<tr>
		<th align="right"><span style="color: red">*</span>&nbsp;问题分析：</th>
		<td>
			<form:textarea path="kcp.analysis" style="height:214px;visibility:hidden;" cssClass="required"  />
		</td>
	</tr>
</table>
</form>
</body>
</html>
