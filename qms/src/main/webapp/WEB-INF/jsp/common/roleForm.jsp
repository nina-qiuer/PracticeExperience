<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>

<html>
<head>
<script type="text/javascript">
$(document).ready(function(){
	jQuery.validator.addMethod("roleSame", function(value, element) {
		var roleId = "${role.id}";
		var roleName = "${role.name}";
		if (roleId > 0 && roleName == value) {
			return true;
		} else {
			var isNotSame = false;
			$.ajax({
				type : "POST",
				url : "common/role/checkRoleSame",
				async: false,
				dataType: "json",
				data : {"name" : value},
				success: function(data) {
					isNotSame = data;
				}
			});
			return isNotSame;
		}
	}, "角色名称已存在！");
	
	$("#roleForm").validate({
		submitHandler:function(form){
		    addOrUpdate();
		}
	});
});

function addOrUpdate() {
	var url = "common/role/add";
	if ("${role.id}" > 0) {
		url = "common/role/update";
	}

	$.ajax({
		type: "POST",
		url: url,
		data: $("#roleForm").serialize(),
		dataType: "json",
		async: false,
		success: function(data) {
			window.parent.location.href = "common/role/management";
		}
	});
}
</script>
</head>
<body>
<form id="roleForm" method="post">
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
<form:hidden path="role.id"/>
<table class="datatable">
	<tr>
		<th align="right" width="85"><span style="color: red">*</span>&nbsp;角色名称：</th>
		<td><form:input id="roleName" path="role.name" cssClass="required roleSame"/></td>
	</tr>
	<tr>
		<th align="right"><span style="color: red">*</span>&nbsp;角色类型：</th>
		<td>
			<form:select path="role.type">
				<form:option value="1">基础员工</form:option>
				<form:option value="2">经理</form:option>
				<form:option value="3">管理员</form:option>
			</form:select>
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
