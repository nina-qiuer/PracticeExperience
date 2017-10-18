<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>部门配置</title>
<script type="text/javascript">
var depTree;

$(document).ready(function(){
	depInit();
});

function depInit() {
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
	<c:forEach items="${depList}" var="dep">
		<c:if test="${0 == dep.cmpQcUseFlag}">
			zNodes.push({id:'${dep.id}', pId:'${dep.pid}', name:'${dep.name}'});
		</c:if>
		<c:if test="${1 == dep.cmpQcUseFlag}">
			zNodes.push({id:'${dep.id}', pId:'${dep.pid}', name:'${dep.name}', checked:true});
		</c:if>
	</c:forEach>
	
	depTree = $.fn.zTree.init($("#depTree"), setting, zNodes);
}

function saveDepConfig() {
	var nodes = depTree.getChangeCheckedNodes();
	var checkedIds = new Array();
	var unCheckedIds = new Array();
	for (var i=0; i<nodes.length; i++) {
		var node = nodes[i];
		if (node.checked) {
			checkedIds.push(node.id);
		} else {
			unCheckedIds.push(node.id);
		}
	}
	
	$.ajax({
		type: "POST",
		url: "qc/assignConfigCmp/saveDepConfig",
		data: {"checkedIds":checkedIds,"unCheckedIds":unCheckedIds},
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
	<span style="color: blue;">部门配置</span>
	<ul id="depTree" class="ztree" style="overflow:auto;"></ul>
	<div align="center">
		<input type="button" class="blue" value="保存" onclick="saveDepConfig()"/>　
		<input type="button" class="blue" value="重置" onclick="window.location.reload();"/>
	</div>
</body>
</html>
