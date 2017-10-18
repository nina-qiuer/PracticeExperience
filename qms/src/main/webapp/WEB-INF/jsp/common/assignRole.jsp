<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>授权</title>
<script type="text/javascript">
$(document).ready(function(){
	$(".accordion").each(function() {
		$(this).accordion({
	    	collapsible: true,
	    	heightStyle: "content"
	    });
	});
	
	userAutoComplete();
	depAutoComplete();
	jobAutoComplete();
	
	changeSearchItem("${dto.searchType}");
	
	// chkAll全选事件
	$("#chkAll").click(function(){
		var flag = this.checked;
        $("[name='userIds']:checkbox").each(function(){
        	this.checked = flag;
        });
    });
});

function userAutoComplete() {
	var userArr = new Array();
	<c:forEach items="${userNames}" var="userMap">
		userArr.push({
			label: '${userMap.label}',
			value: '${userMap.realName}'
		});
	</c:forEach>
	
	$("#realName").autocomplete({
	    source: userArr
	}).data( "ui-autocomplete" )._renderItem = function( ul, item ) {
		return $( "<li>" )
        .append( "<a>" + item.value + "</a>" )
        .appendTo( ul );
	};
}

function depAutoComplete() {
	var depArr = new Array();
	<c:forEach items="${depNames}" var="depName">
		depArr.push('${depName}');
	</c:forEach>
	
    $("#depName").autocomplete({
	    source: depArr
	});
}

function jobAutoComplete() {
	var jobArr = new Array();
	<c:forEach items="${jobNames}" var="jobName">
		jobArr.push('${jobName}');
	</c:forEach>
	
    $("#jobName").autocomplete({
	    source: jobArr
	});
}

function search() {
	$("#searchForm").attr("action", "common/user/toAssignRole");
	$("#searchForm").submit();
}

function assignRole() {
	if($("input[name='userIds']:checked").length <= 0) {
		layer.alert("请至少选择一个用户！", {icon: 2});
		return false;
	}
	if ($("#roleId").val() == '') {
		layer.alert("请选择角色！", {icon: 2});
		return false;
	}
	
	$.ajax({
		type: "POST",
		url: "common/user/assignRole",
		data: $("#searchForm").serialize(),
		dataType: "json",
		async: false,
		success: function(data) {
			parent.search();
		}
	});
}

function changeSearchItem(searchType) {
	if (0 == searchType || 1 == searchType) {
		$("#workNumLabel").show();
		$("#realNameLabel").show();
		$("#depNameLabel").hide();
		$("#jobNameLabel").hide();
	} else if (2 == searchType) {
		$("#workNumLabel").hide();
		$("#realNameLabel").hide();
		$("#depNameLabel").show();
		$("#jobNameLabel").hide();
	} else if (3 == searchType) {
		$("#workNumLabel").hide();
		$("#realNameLabel").hide();
		$("#depNameLabel").hide();
		$("#jobNameLabel").show();
	}
}
</script>
</head>

<body>
<form id="searchForm" method="post" action="common/user/toAssignRole">
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
<div class="accordion">
	<h3>人员列表</h3>
	<div align="right">
	<table width="100%">
		<tr>
			<td>
				<form:radiobutton path="dto.searchType" value="1" onchange="changeSearchItem(1)" label="按单人查询"/><br>
				<form:radiobutton path="dto.searchType" value="2" onchange="changeSearchItem(2)" label="按部门查询"/><br>
				<form:radiobutton path="dto.searchType" value="3" onchange="changeSearchItem(3)" label="按职务查询"/>
			</td>
			<td>
				<label id="workNumLabel" style="display: none;">工号：<form:input path="dto.workNum" size="10"/>　　</label>
				<label id="realNameLabel" style="display: none;">姓名：<form:input id="realName" path="dto.realName" size="10"/></label>
				<label id="depNameLabel" style="display: none;">部门：<form:input id="depName" path="dto.depName" size="45"/></label>
				<label id="jobNameLabel" style="display: none;">职务：<form:input id="jobName" path="dto.jobName" size="30"/></label>
			</td>
			<td><img src="res/img/search.png" class="img_btn" title="查询" onclick="search()"></td>
		</tr>
	</table>
	</div>
</div>
<table class="listtable" width="100%">
	<tr>
		<th><input type="checkbox" id="chkAll" title="全选"></th>
		<th>ID</th>
		<th>工号</th>
		<th>姓名</th>
		<th>分机号</th>
	</tr>
	<c:forEach items="${userList}" var="user">
	<tr align="center">
		<td><form:checkbox path="dto.userIds" value="${user.id}"/></td>
		<td>${user.id}</td>
		<td>${user.workNum}</td>
		<td>${user.realName}</td>
		<td>${user.extension}</td>
	</tr>
	</c:forEach>
</table>
<c:if test="${(fn:length(userList)) > 0}">
<div align="center">
	<form:select id="roleId" path="dto.roleId">
		<option value="">请选择角色</option>
		<form:options items="${roleList}" itemValue="id" itemLabel="name"/>
	</form:select>　
	<input type="button" class="blue" value="分配" onclick="assignRole()">
</div>
</c:if>
</form>
</body>
</html>
