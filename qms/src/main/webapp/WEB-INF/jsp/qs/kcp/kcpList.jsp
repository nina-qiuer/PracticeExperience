<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>KCP列表</title>
<script type="text/javascript">
$(function(){
	$('#accordion').accordion({
    	collapsible: true,
    	heightStyle: "content"
    });
    
    $('#stateSet').buttonset();
	$('#stateSet').click(function() {
		searchResetPage();
	});
});

function search() {
	$("#kcpForm").attr("action", "qs/kcp/list");
	$("#kcpForm").submit();
}

function resetForm() {
	$("input[type=text]", "#kcpForm").val('');
	$("select", "#kcpForm").val('0');
	$("#pageNo").val('1');
}
</script>
</head>
<body>
<form id="kcpForm" method="post" action="qs/kcp/list">
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
<div id="accordion">
<h3>KCP列表</h3>
<div align="right">
<table width="95%">
	<tr>
		<td align="right">KCP编号：</td>
		<td><form:input path="dto.id" size="10"/></td>
		<td align="right">类型：</td>
		<td>
			<form:select path="dto.kcpTypeId">
				<form:option value="0">全部</form:option>
				<form:options items="${kcpTypeList}" itemValue="id" itemLabel="name"/>
			</form:select>
		</td>
		<td align="right">初审人：</td>
		<td><form:input path="dto.audit1Name" size="10"/></td>
		<td align="right">申请日期：</td>
		<td>
			<form:input path="dto.applyDateBgn" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly" size="10"/> - 
			<form:input path="dto.applyDateEnd" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly" size="10"/>
		</td>
	</tr>
	<tr>
		<td align="right">重要程度：</td>
		<td>
			<form:select path="dto.importantFlag">
				<form:option value="0">全部</form:option>
				<form:option value="1">普通</form:option>
				<form:option value="2">重要</form:option>
				<form:option value="3">非常重要</form:option>
			</form:select>
		</td>
		<td align="right">来源：</td>
		<td>
			<form:select path="dto.kcpSourceId">
				<form:option value="0">全部</form:option>
				<form:options items="${kcpSourceList}" itemValue="id" itemLabel="name"/>
			</form:select>
		</td>
		<td align="right">终审人：</td>
		<td><form:input path="dto.audit2Name" size="10"/></td>
		<td align="right"><c:if test="${dto.state == 3}">审核日期：</c:if></td>
		<td>
			<c:if test="${dto.state == 3}">
			<form:input path="dto.auditDateBgn" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly" size="10"/> - 
			<form:input path="dto.auditDateEnd" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly" size="10"/>
			</c:if>
		</td>
	</tr>
	<tr>
		<td align="right">状态：</td>
		<td colspan="3">
			<div id="stateSet">
				<form:radiobutton path="dto.state" value="1" label="待初审"/>
				<form:radiobutton path="dto.state" value="2" label="待终审"/>
				<form:radiobutton path="dto.state" value="3" label="审核通过"/>
			</div>
		</td>
		<td></td><td></td>
		<td align="center" colspan="2">
			<input type="button" class="blue" value="查询" onclick="searchResetPage()">
			<input type="button" class="blue" value="重置" onclick="resetForm()">
		</td>
	</tr>
</table>
</div>
</div>
<table class="listtable">
	<tr>
		<th width="50px">编号</th>
		<th>名称</th>
		<th>类型</th>
		<th>来源</th>
		<th>重要程度</th>
		<th>状态</th>
		<th>申请人</th>
		<th>申请时间</th>
		<th>初审人</th>
		<th>终审人</th>
		<c:if test="${dto.state == 3}">
		<th>审核通过时间</th>
		</c:if>
		<c:if test="${(1 == dto.state && fn:contains(loginUser_WCS,'KCP_FIRST_AUDIT')) || (2 == dto.state && fn:contains(loginUser_WCS,'KCP_LAST_AUDIT'))}">
		<th width="70px">操作</th>
		</c:if>
	</tr>
	<c:forEach items="${dto.dataList}" var="kcp">
	<tr>
		<td>
		  <a href="javascript:void(0)" onclick="openWin('KCP明细','qs/kcp/${kcp.id}/toShow',800, 500)" >${kcp.id}</a>
		</td>
		<td class="shorten20">${kcp.name}</td>
		<td>${kcp.kcpTypeName}</td>
		<td>${kcp.kcpSourceName}</td>
		<td>
			<c:if test="${kcp.importantFlag == 1}">普通</c:if>
			<c:if test="${kcp.importantFlag == 2}"><span style="color: orangered;">重要</span></c:if>
			<c:if test="${kcp.importantFlag == 3}"><span style="color: red;">非常重要</span></c:if>
		</td>
		<td>
			<c:if test="${kcp.state == 0}">发起中</c:if>
			<c:if test="${kcp.state == 1}">待初审</c:if>
			<c:if test="${kcp.state == 2}">待复审</c:if>
			<c:if test="${kcp.state == 3}">审核通过</c:if>
		</td>
		<td>${kcp.addPerson}</td>
		<td><fmt:formatDate value="${kcp.addTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		<td>${kcp.audit1Name}</td>
		<td>${kcp.audit2Name}</td>
		<c:if test="${dto.state == 3}">
		<td><fmt:formatDate value="${kcp.auditTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</c:if>
		<c:if test="${(1 == dto.state && fn:contains(loginUser_WCS,'KCP_FIRST_AUDIT')) || (2 == dto.state && fn:contains(loginUser_WCS,'KCP_LAST_AUDIT'))}">
		<td>
			<input type="button" class="blue" value="审核" onclick="openWin('KCP审核', 'qs/kcp/${kcp.id}/toAudit', 800, 500)">
		</td>
		</c:if>
	</tr>
	</c:forEach>
</table>
<%@ include file="/WEB-INF/jsp/common/pager.jsp" %>
</form>
</body>
</html>
