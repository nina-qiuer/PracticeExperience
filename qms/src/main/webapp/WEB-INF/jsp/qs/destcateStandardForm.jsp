<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title></title>
<script type="text/javascript">
$(document).ready(function(){
	$("#dcsForm").validate({
		rules:{
			destCate: "required",
            groupRichness:{
            	required:true,
                number:true,
                range:[0, 1]
            },
            noticeTimeLimit:{
            	required:true,
                digits:true,
                min:0
            }
        },
		submitHandler:function(form){
		    addOrUpdate();
		}
	});
});

function addOrUpdate() {
	var url = "qs/destcateStandard/add";
	if ("${dcs.id}" > 0) {
		url = "qs/destcateStandard/update";
	}

	$.ajax({
		type: "POST",
		url: url,
		data: $("#dcsForm").serialize(),
		dataType: "json",
		async: false,
		success: function(data) {
			window.parent.location.href = "qs/destcateStandard/list";
		}
	});
}
</script>
</head>
<body>
<form id="dcsForm" method="post">
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
<form:hidden path="dcs.id"/>
<table class="datatable">
  	<tr>
  		<th width="130">目的地大类：</th>
  		<td><form:input path="dcs.destCate"/></td>
  	</tr>
  	<tr>
  		<th>团期丰富度：</th>
  		<td><form:input path="dcs.groupRichness"/></td>
  	</tr>
  	<tr>
  		<th>出团通知时限（天）：</th>
  		<td><form:input path="dcs.noticeTimeLimit"/></td>
  	</tr>
  	<tr>
  		<th>操作：</th>
  		<td><input type="submit" class="blue" value="提交"></td>
  	</tr>
</table>
</form>
</body>
</html>
