<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>权限设置</title>
<script type="text/javascript">
var resourceTree;

$(document).ready(function(){
	resourceInit();
	checkNodes();
});

function resourceInit() {
	var setting = {
		check: {
			enable: true
		},
		data: {
			simpleData: {
				enable: true
			}
		}
	};
	
	var zNodes = new Array();
	<c:forEach items="${resourceList}" var="resource">
		zNodes.push({id:'${resource.id}', pId:'${resource.pid}', name:'${resource.name}', open:true});
	</c:forEach>
	
	resourceTree = $.fn.zTree.init($("#resourceTree"), setting, zNodes);
}

function checkNodes() {
	<c:forEach items="${roleResources}" var="resource">
		var node = resourceTree.getNodeByParam("id", "${resource.id}", null);
		resourceTree.checkNode(node, true, false);
	</c:forEach>
}

function saveAuth() {
	var nodes = resourceTree.getCheckedNodes(true);
	var resIds = new Array();
	for (var i=0; i<nodes.length; i++) {
		resIds.push(nodes[i].id);
	}
	$.ajax({
		type: "POST",
		url: "common/role/" + "${role.id}" + "/saveAuth",
		data: {"resIds":resIds},
		traditional: true,
		async: false,
		success: function(data) {
			layer.alert("保存成功！", {icon: 6});
		}
	});
}
</script>
</head>

<body>
	<span style="color: green;">角色名称：${role.name}</span>
	<ul id="resourceTree" class="ztree" style="overflow:auto;"></ul>
	<div align="center">
		<input type="button" class="blue" value="保存" onclick="saveAuth()"/>　
		<input type="button" class="blue" value="重置" onclick="window.location.reload();"/>
	</div>
</body>
</html>
