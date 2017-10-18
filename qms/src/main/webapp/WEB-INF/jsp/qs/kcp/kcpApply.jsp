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
});

function search() {
	$("#kcpForm").attr("action", "qs/kcp/apply");
	$("#kcpForm").submit();
}

function resetForm() {
	$("input[type=text]", "#kcpForm").val('');
	$("#pageNo").val('1');
}

function deleteKCP(id){
	var msg = "确定要删除此标准吗？";
	layer.confirm(msg, {icon: 3}, function(){
		$.ajax({
			type: "GET",
			url: "qs/kcp/"+id+"/delete",
			async: false,
			dataType: "json",
			success: function(data) {
				search();
			}
		});
	});
}
</script>
</head>
<body>
<form id="kcpForm" method="post" action="qs/kcp/apply">
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
<div id="accordion">
<h3>KCP列表</h3>
<div align="right">
<input type="button" class="blue" value="KCP申请" onclick="openWin('KCP申请', 'qs/kcp/toAdd', 800, 500)">
<input type="button" class="blue" value="刷新" onclick="search()">
<!-- <input type="button" class="blue" value="重置" onclick="resetForm()"> -->
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
		<th>审核通过时间</th>
		<th width="120px">操作</th>
	</tr>
	<c:forEach items="${dto.dataList}" var="kcp">
	<tr>
		<td>${kcp.id}</td>
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
		<td><fmt:formatDate value="${kcp.auditTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		<td>
			<c:if test="${kcp.state == 0}">
				<input type="button" class="blue" value="修改" onclick="openWin('KCP申请', 'qs/kcp/${kcp.id}/toUpdate', 800, 500)">
				<input type="button" class="blue" value="删除" onclick="deleteKCP('${kcp.id}')">
			</c:if>
		</td>
	</tr>
	</c:forEach>
</table>
<%@ include file="/WEB-INF/jsp/common/pager.jsp" %>
</form>
</body>
</html>
