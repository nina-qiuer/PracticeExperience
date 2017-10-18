<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>授权管理</title>
<style type="text/css">
/*搜索框表格样式*/
.search {
	font-size: 12px;
	font-family: Microsoft YaHei;
}

.search td:nth-child(even) {
	text-align: left;
}

.search td:nth-child(odd) {
	text-align: right;
}

.search td input[type=text] {
	width: 100px;
}

</style>
<script type="text/javascript">
var realNameArr = new Array();
var userArr = new Array();
$(document).ready(function(){
	$(".accordion").each(function() {
		$(this).accordion({
	    	collapsible: true,
	    	heightStyle: "content"
	    });
	});
});

function search() {
	$("#searchForm").attr("action", "common/user/authManagement");
	$("#searchForm").submit();
}
function userAutoComplete() {
	if (userArr.length > 0) {
		return;
	} else {
		$.ajax({
			type : "POST",
			url : "qc/qcBill/getUserNamesInJSON",
			success : function(data) {
				for (var i = 0; i < data.length; i++) {
					userArr.push({
						label : data[i].label,
						value : data[i].realName
					});
					realNameArr.push(data[i].realName);
				}
			}
		});
	
	$("#realName").autocomplete({
	    source: userArr,
	    autoFocus : true
	}).data( "ui-autocomplete" )._renderItem = function( ul, item ) {
		return $( "<li>" )
        .append( "<a>" + item.value + "</a>" )
        .appendTo( ul );
	};
	}
}
</script>
</head>

<body>
<form name="form" id="searchForm" method="post" action="">
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
<div class="accordion">
	<h3>用户列表</h3>
	<div align="right">
	   <table width="85%" class="search">
			<tr>
			    <td>工号：</td>
		    	<td><form:input path="dto.workNum"/></td>
		    	<td>姓名：</td>
		    	<td><form:input path="dto.realName" onfocus="userAutoComplete()"/></td>
				<td>角色：</td>
				<td>
					<form:select id="roleId" path="dto.roleId" onchange="searchResetPage()">
						<option value="">全部</option>
						<form:options items="${roleList}" itemValue="id" itemLabel="name"/>
					</form:select>
				</td>
				<td>角色类型：</td>
				<td>
					<form:select id="roleType" path="dto.roleType" onchange="searchResetPage()">
						<form:option value="" label="全部" />
						<form:option value="1" label="基础员工" />
						<form:option value="2" label="经理" />
						<form:option value="3" label="管理人员" />
					</form:select>
				</td>
				<td>
				<input type="button"   class="blue" value ="查询" onclick="searchResetPage()">
				<input type="button"   class="blue"  value ="授权" onclick="openWin('授权', 'common/user/toAssignRole', 1000, 500)">
			    </td>
			</tr>
		</table>
	
		
	</div>
</div>
<table class="listtable" width="100%">
	<tr>
		<th>ID</th>
		<th>工号</th>
		<th>姓名</th>
		<th>角色名称</th>
		<th>角色类型</th>
		<th>用户名</th>
		<th>邮箱地址</th>
		<th>分机号</th>
	</tr>
	<c:forEach items="${dto.dataList}" var="user">
	<tr align="center">
		<td>${user.id}</td>
		<td>${user.workNum}</td>
		<td>${user.realName}</td>
		<td>${user.role.name}</td>
		<td>
			<c:if test="${user.role.type == 1}">基础员工</c:if>
			<c:if test="${user.role.type == 2}"><span style="color: green;">经理</span></c:if>
			<c:if test="${user.role.type == 3}"><span style="color: red;">管理员</span></c:if>
		</td>
		<td>${user.userName}</td>
		<td>${user.email}</td>
		<td>${user.extension}</td>
	</tr>
	</c:forEach>
</table>
<%@include file="/WEB-INF/jsp/common/pager.jsp" %>
</form>
</body>
</html>
