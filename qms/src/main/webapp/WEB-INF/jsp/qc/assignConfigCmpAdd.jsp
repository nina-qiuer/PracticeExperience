<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>

<html>
<head>
<script type="text/javascript">
var userArr = new Array();
var realNameArr = new Array();

$(document).ready(function(){
	<c:forEach items="${userNames}" var="userMap">
		userArr.push({
			label: '${userMap.label}',
			value: '${userMap.realName}'
		});
		realNameArr.push('${userMap.realName}');
	</c:forEach>
	
	userAutoComplete();
	
	jQuery.validator.addMethod("userExists", function(value, element) {
		var isExists = false;
		
		for (var i=0; i<realNameArr.length; i++) {
			if (value == realNameArr[i]) {
				isExists = true;
			}
		}
		return isExists;
	}, "此员工不存在！");
	jQuery.validator.addMethod("personSame", function(value, element) {
		var isNotSame = false;
		$.ajax({
			type : "POST",
			url : "qc/assignConfigCmp/checkPersonSame",
			async: false,
			dataType: "json",
			data : {"qcPersonName" : value},
			success: function(data) {
				isNotSame = data;
			}
		});
		return isNotSame;
	}, "质检员已存在！");
	$("#acForm").validate({
		submitHandler:function(form){
		    add();
		}
	});
});

function userAutoComplete() {
	$("#qcPersonName").autocomplete({
		autoFocus: true,
	    source: userArr
	}).data( "ui-autocomplete" )._renderItem = function( ul, item ) {
		return $( "<li>" )
        .append( "<a>" + item.value + "</a>" )
        .appendTo( ul );
	};
}

function add() {
	$.ajax({
		type: "POST",
		url: "qc/assignConfigCmp/add",
		data: $("#acForm").serialize(),
		dataType: "json",
		async: false,
		success: function(data) {
			window.parent.location.href = "qc/assignConfigCmp/list";
		}
	});
}
</script>
</head>
<body>
<form id="acForm" method="post">
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
<table class="datatable">
	<tr>
		<th align="right" width="85"><span style="color: red">*</span>&nbsp;质检员：</th>
		<td><form:input id="qcPersonName" path="ac.qcPersonName" cssClass="required userExists personSame"/></td>
	</tr>
	<tr>
		<th align="right">无订单质检：</th>
		<td>
			<form:radiobutton path="ac.noOrderFlag" value="0" label="不处理"/>　
			<form:radiobutton path="ac.noOrderFlag" value="1" label="处理"/>
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
