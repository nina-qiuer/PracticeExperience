<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>通用类型</title>
<script type="text/javascript">
	$(document).ready(function(){
		$("#typeForm").validate({
			submitHandler:function(form){
			    addOrUpdate();
			}
		});
	});
	
	function addOrUpdate(){
		var url = "common/cmType/addChild";
		if($("#id").val() != null && $("#id").val() != 0){
			url = "common/cmType/updateType";
		}
		
		$.ajax({
			url: url,
			type: "POST",
			data: $("#typeForm").serialize(),
			dataType: "json",
			success: function(data){
				if(data == "Success"){
					window.parent.location.href = "common/cmType/list";
				}
			},
			error : function(){
				layer.alert("提交失败！");
			}
		});
	}
</script>
</head>
<body>
	<form id="typeForm" method="post">
		<form:hidden id="pid" path="cmType.pid"/>
		<form:hidden id="id" path="cmType.id"/>
		<table class="datatable">
			<tr>
				<th align="right" width="100"><span style="color: red">*</span>&nbsp;通用类型：</th>
				<td><form:input path="cmType.name" style="width:120" cssClass="required"/></td>
			</tr>
			<tr>
				<th align="right">类型标识：</th>
				<td>
					<form:radiobutton path="cmType.typeFlag" value="1" label="关联关闭原因"/>　
					<form:radiobutton path="cmType.typeFlag" value="2" label="其他"/>
				</td>
			</tr>
			<tr>
				<th align="right">操作：</th>
				<td><input type="submit" class="blue" value="提交" /></td>
			</tr>
		</table>
	</form>
</body>
</html>
