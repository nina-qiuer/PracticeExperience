<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<link rel="stylesheet" type="text/css" href="res/plugins/KindEditor/css/default.css"/>
<script type="text/javascript" src="res/plugins/KindEditor/kindeditor-min.js"></script>
<script type="text/javascript" src="res/plugins/KindEditor/lang/zh_CN.js"></script>
<title></title>
<script type="text/javascript">
$(document).ready(function(){
	$("#talkForm").validate({
		rules:{
			respType: "required",
		},
		submitHandler:function(form){
		    addOrUpdate();
		}
	});
});

function addOrUpdate() {
	
	editor1.sync();
	var url = "common/talkConfig/add";
	if ("${talkConfig.id}" > 0) {
		url = "common/talkConfig/update";
	}

	$.ajax({
		type: "POST",
		url: url,
		data: $("#talkForm").serialize(),
		dataType: "json",
		async: false,
		success: function(data) {
			window.parent.location.href = "common/talkConfig/list";
		}
	});
}
var editor1;
KindEditor.ready(function(K) {
	editor1 = K.create('#content', {
		resizeType : 1,
		allowPreviewEmoticons : false,
		allowImageUpload : false,
		items : ['fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', 
				 'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', '|', 'hr', 'fullscreen']
	});

	
});
</script>
</head>
<body>
<form:form id="talkForm" method="post" commandName="talkConfig">
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
<form:hidden path="id"/>
<table class="datatable">
  	<tr>
  		<th>类型：</th>
  		<c:choose>
			<c:when test="${loginUser.role.type!=3}">
  				<td><form:input path="type" readonly="true"/></td>
			</c:when>
			<c:otherwise>
  				<td><form:input path="type"/></td>
			</c:otherwise>
		</c:choose>
  	</tr>
  	<tr>
  		<th>内容：</th>
  		<td>
  		  <textarea id="content" name="content" style="width:400px;height:220px;visibility:hidden;">${talkConfig.content}</textarea>
  		  </td>
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
