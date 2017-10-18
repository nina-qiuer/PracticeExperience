<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title></title>
<script type="text/javascript">
$(document).ready(function(){
	$("#psForm").validate({
		rules:{
			description: "required",
            economicPunish:{
                number:true,
                min:0
            },
            scorePunish:{
                digits:true,
                min:0
            },
            source: "required"
        },
		submitHandler:function(form){
		    addOrUpdate();
		}
	});
});

function addOrUpdate() {
	var url = "qc/punishStandard/add";
	if ("${ps.id}" > 0) {
		url = "qc/punishStandard/update";
	}

	$.ajax({
		type: "POST",
		url: url,
		data: $("#psForm").serialize(),
		dataType: "json",
		async: false,
		success: function(data) {
			parent.search();
    		parent.layer.closeAll();
		}
	});
}
</script>
</head>
<body>
<form:form  id="psForm"  method="post"  commandName="ps">
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
<form:hidden path="id"/>
<table class="datatable">
  	<tr>
  		<th>处罚等级：</th>
  		<td><form:input path="level"/></td>
  	</tr>
  	<tr>
  		<th>红线标识：</th>
  		<td>
  			<form:radiobutton path="redLineFlag" value="0" label="非红线"/>　
  			<form:radiobutton path="redLineFlag" value="1" label="红线"/>
  		</td>
  	</tr>
  	<tr>
  		<th>分级标准：</th>
  		<td><form:textarea path="description" cols="40" rows="3"/></td>
  	</tr>
  	<tr>
  		<th>处罚对象：</th>
  		<td>
  			<form:radiobutton path="punishObj" value="1" label="内部员工"/>　
  			<form:radiobutton path="punishObj" value="2" label="供应商"/>
  		</td>
  	</tr>
  	<tr>
  		<th>经济处罚(元)：</th>
  		<td><form:input path="economicPunish"/></td>
  	</tr>
  	<tr>
  		<th>记分处罚(分)：</th>
  		<td><form:input path="scorePunish"/></td>
  	</tr>
  	<tr>
  		<th>出处：</th>
  		<td><form:textarea path="source" cols="40" rows="2"/></td>
  	</tr>
  	<tr>
  		<th>使用方：</th>
  		<td>
  			<form:checkbox path="cmpQcUse" value="1" label="投诉质检"/>　
  			<form:checkbox path="operQcUse" value="1" label="运营质检"/>　
  			<form:checkbox path="devQcUse" value="1" label="研发质检"/>
  			<form:checkbox path="innerQcUse" value="1" label="内部质检"/>
  		</td>
  	</tr>
  	<tr>
  		<th>操作：</th>
  		<td><input type="submit" class="blue" value="提交"></td>
  	</tr>
</table>
</form:form>
</body>
</html>
