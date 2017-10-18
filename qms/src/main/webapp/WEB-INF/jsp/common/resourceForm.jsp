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
	
	$('#resourceName').keydown(function(e){
	   if(e.ctrlKey==1 && e.shiftKey==1 && e.keyCode == 123){
		   parent.openWin('技术支持', 'ts/tecksupport/index', 800, 300);
	   }
	});
});

function addOrUpdate() {
	var url = "common/resource/addChild";
	if ("${resource.id}" > 0) {
		url = "common/resource/update";
	}
	
	$.ajax({
		type: "POST",
		url: url,
		data: $("#resourceForm").serialize(),
		dataType: "json",
		async: false,
		success: function(data) {
			window.parent.location.href = "common/resource/management";
		}
	});
}

</script>
</head>

<body>
<form id="resourceForm" method="post">
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
<form:hidden path="resource.id"/>
<form:hidden path="resource.pid"/>
<table class="datatable">
	<tr>
		<th align="right" width="100"><span style="color: red">*</span>&nbsp;资源名称：</th>
		<td><form:input id="resourceName"  path="resource.name" cssClass="required"/></td>
	</tr>
	<tr>
		<th align="right">界面控件编码：</th>
		<td><form:input path="resource.widgetCode"/></td>
	</tr>
	<tr>
		<th align="right">关联访问地址：</th>
		<td><form:input path="resource.url"/></td>
	</tr>
	<tr>
		<th align="right">菜单标识：</th>
		<td>
			<form:radiobutton path="resource.menuFlag" value="0" label="否"/>　
			<form:radiobutton path="resource.menuFlag" value="1" label="是"/>
		</td>
	</tr>
	<tr>
		<th align="right">操作类型：</th>
		<td>
			<form:radiobutton path="resource.operType" value="0" label="查询"/>　
			<form:radiobutton path="resource.operType" value="1" label="增删改"/>
		</td>
	</tr>
	<tr>
		<th align="right">鉴权标识：</th>
		<td>
			<form:radiobutton path="resource.chkAuthFlag" value="0" label="否"/>　
			<form:radiobutton path="resource.chkAuthFlag" value="1" label="是"/>
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
