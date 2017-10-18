<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>角色管理</title>
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

function authConfig(id) {
	$("#authFrame").attr("src", "common/role/" + id + "/authConfig");
}

function deleteRole(id, name) {
	var msg = "确定要删除角色[" + name + "]吗？";
	layer.confirm(msg, {icon: 3}, function(){
	    $.ajax({
			type: "GET",
			url: "common/role/" + id + "/delete",
			async: false,
			dataType: "json",
			success: function(data) {
				// 校验关联用户, 成功则刷新
				if ("Success" == data) {
					window.location.href = "common/role/management";
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
	<h3>角色列表</h3>
	<div align="right">
		<img src="res/img/add.png" class="img_btn" title="添加" onclick="openWin('添加角色', 'common/role/toAdd', 400, 148)">
	</div>
</div>
<table class="listtable" width="100%">
	<tr>
		<th>角色名称</th>
		<th>角色类型</th>
		<th>添加人</th>
		<th>添加时间</th>
		<th width="100">操作</th>
	</tr>
	<c:forEach items="${roleList}" var="role">
	<tr align="center">
		<td>${role.name}</td>
		<td>
			<c:if test="${role.type == 1}">基础员工</c:if>
			<c:if test="${role.type == 2}"><span style="color: green;">经理</span></c:if>
			<c:if test="${role.type == 3}"><span style="color: red;">管理员</span></c:if>
		</td>
		<td>${role.addPerson}</td>
		<td><fmt:formatDate value="${role.addTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		<td>
			<img src="res/img/modify.png" class="img_btn" title="修改" onclick="openWin('修改角色', 'common/role/${role.id}/toUpdate', 400, 148)">
			<img src="res/img/close.png" class="img_btn" title="删除" onclick="deleteRole('${role.id}', '${role.name}')">
			<img src="res/img/edit.gif" class="img_btn" title="权限配置" onclick="authConfig('${role.id}')">
		</td>
	</tr>
	</c:forEach>
</table>
</div>
<div class="ui-layout-center">
	<iframe id="authFrame" width="100%" height="100%" frameborder="0" scrolling="auto"></iframe>
</div>

</body>
</html>
