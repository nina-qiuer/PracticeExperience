<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>投诉质检员配置</title>
<script type="text/javascript">
$(document).ready(function(){
	$("body").layout({
		resizerTip:"可调整大小",
		togglerTip_open:"关闭",
		togglerTip_closed:"打开",
		west__size: 570,
		center__onresize: resizeTabPanel
	});
	
	$(".accordion").each(function() {
		$(this).accordion({
	    	collapsible: true,
	    	heightStyle: "content"
	    });
	});
});

function resizeTabPanel() {
	$(".tabpanel").width('100%');
	$(".tabpanel_tab_content").width('100%');
	$(".tabpanel_move_content").width('100%');
	$(".tabpanel_content").width('100%');
}

function depConfig() {
	$("#dockdepFrame").attr("src", "qc/assignConfigCmp/depConfig");
}

function dockdepConfig(id) {
	$("#dockdepFrame").attr("src", "qc/assignConfigCmp/" + id + "/dockdepConfig");
}

function dockdepConfigShow() {
	$("#dockdepFrame").attr("src", "qc/assignConfigCmp/dockdepConfigShow");
}


function deletePerson(id, qcPersonName) {
	var msg = "确定要删除质检员[" + qcPersonName + "]吗？";
	layer.confirm(msg, {icon: 3}, function(){
	    $.ajax({
			type: "GET",
			url: "qc/assignConfigCmp/" + id + "/delete",
			async: false,
			dataType: "json",
			success: function(data) {
				// 校验关联用户, 成功则刷新
				if ("Success" == data) {
					window.location.href = "qc/assignConfigCmp/list";
				}
			}
		});
	});
}
</script>
</head>

<body>
<div class="ui-layout-west" style="overflow: auto;">
<div class="accordion">
	<h3>投诉质检员配置列表</h3>
	<div align="right">
		<img src="res/img/add.png" class="img_btn" title="添加" onclick="openWin('添加质检员', 'qc/assignConfigCmp/toAdd', 400, 144)">
		<!-- <input type="button" class="blue" value="部门配置" onclick="depConfig()"> -->
		<input type="button" class="blue" value="对接部门配置总览" onclick="dockdepConfigShow()">
	</div>
</div>
<table class="listtable" width="100%">
	<tr>
		<th>质检员ID</th>
		<th>质检员</th>
		<th>是否处理<br>无订单质检</th>
		<th width="100">操作</th>
	</tr>
	<c:forEach items="${acList}" var="ac">
	<tr align="center">
		<td>${ac.qcPersonId}</td>
		<td>${ac.qcPersonName}</td>
		<td>
			<c:if test="${ac.noOrderFlag == 0}">不处理</c:if>
			<c:if test="${ac.noOrderFlag == 1}"><span style="color: green;">处理</span></c:if>
		</td>
		<td>
			<img src="res/img/modify.png" class="img_btn" title="修改" onclick="openWin('修改质检员配置', 'qc/assignConfigCmp/${ac.id}/toUpdate', 400, 144)">
			<img src="res/img/close.png" class="img_btn" title="删除" onclick="deletePerson('${ac.id}', '${ac.qcPersonName}')">
			<img src="res/img/edit.gif" class="img_btn" title="对接部门配置" onclick="dockdepConfig('${ac.qcPersonId}')">
		</td>
	</tr>
	</c:forEach>
</table>
</div>
<div class="ui-layout-center">
	<iframe id="dockdepFrame" width="100%" height="100%" frameborder="0" scrolling="auto"></iframe>
</div>

</body>
</html>
