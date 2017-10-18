<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>质检邮件发送范围模板</title>
<script type="text/javascript">
$(function(){
	$('#accordion').accordion({
    	collapsible: true,
    	heightStyle: "content"
    });

});

	function deleteRM(id) {
		var msg = "确定要删除此提醒吗？";
		layer.confirm(msg, {
			icon : 3
		}, function() {
			$.ajax({
				type : "GET",
				url : "common/rtxRemind/" + id + "/delete",
				async : false,
				dataType : "json",
				success : function(data) {
					layer.alert('删除成功');
					window.location.reload();
				}
			});
		});
	}
</script>
<style type="text/css">
</style>
</head>
<body>
<div id="accordion">
<h3>RTX提醒</h3>
<div align="right">
	<input type="button" class="blue" value="添加" onclick="openWin('添加rtx提醒', 'common/rtxRemind/toAdd', 480, 225)">
</div>
</div>

<table class="listtable">
	<tr>
		<th>提醒时间</th>
		<th>标题</th>
		<th>内容</th>
		<th>操作</th>
	</tr>
	<c:forEach items="${dataList}" var="rtxRemind">
	<tr>
		<td><fmt:formatDate value="${rtxRemind.sendTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		<td class="left">${rtxRemind.title}</td>
		<td class="shorten70">${rtxRemind.content}</td>
		<td>
			<input type="button" class="blue" value="修改" onclick="openWin('修改rtx提醒', 'common/rtxRemind/${rtxRemind.id}/toUpdate', 480,296)">
			<input type="button" class="blue" value="删除" onclick="deleteRM('${rtxRemind.id}')">
		</td>
	</tr>
	</c:forEach>
</table>
</body>
</html>
