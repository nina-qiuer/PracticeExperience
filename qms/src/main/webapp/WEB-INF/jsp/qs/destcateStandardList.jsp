<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>监控标准列表</title>
<script type="text/javascript">
$(function(){
	$('#accordion').accordion({
    	collapsible: true,
    	heightStyle: "content"
    });
});

function deleteDcs(id){
	var msg = "确定要删除此标准吗？";
	layer.confirm(msg, {icon: 3}, function(){
		$.ajax({
			type: "GET",
			url: "qs/destcateStandard/"+id+"/delete",
			async: false,
			dataType: "json",
			success: function(data) {
				window.location.reload();
			}
		});
	});
}
</script>
</head>
<body>
<div id="accordion">
<h3>目的地大类相关监控标准列表</h3>
<div align="right">
	<input type="button" class="blue" value="添加" onclick="openWin('添加监控标准', 'qs/destcateStandard/toAdd', 500, 170)">
</div>
</div>

<table class="listtable">
	<tr>
		<th>目的地大类</th>
		<th>团期丰富度</th>
		<th>出团通知时限<br>（天）</th>
		<th>操作</th>
	</tr>
	<c:forEach items="${dcsList}" var="dcs">
	<tr>
		<td>${dcs.destCate}</td>
		<td><fmt:formatNumber value="${dcs.groupRichness}" type="percent"/></td>
		<td>${dcs.noticeTimeLimit}</td>
		<td>
			<input type="button" class="blue" value="修改" onclick="openWin('修改监控标准', 'qs/destcateStandard/${dcs.id}/toUpdate', 500, 170)">
			<input type="button" class="blue" value="删除" onclick="deleteDcs('${dcs.id}')">
		</td>
	</tr>
	</c:forEach>
</table>
</body>
</html>
