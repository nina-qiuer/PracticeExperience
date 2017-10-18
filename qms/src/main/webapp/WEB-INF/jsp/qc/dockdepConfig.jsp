<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>投诉质检员对接部门设置</title>
<script type="text/javascript">
var depTree;

$(document).ready(function(){
	depInit();
	checkNodes();
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
		zNodes.push({id:'${dep.id}', pId:'${dep.pid}', name:'${dep.name}'});
	</c:forEach>
	
	depTree = $.fn.zTree.init($("#depTree"), setting, zNodes);
}

function checkNodes() {
	var qcPersonId = "${qcPerson.id}";
	<c:forEach items="${dockdepList}" var="dockdep">
		var node = depTree.getNodeByParam("id", "${dockdep.dockdepId}", null);
		if (null != node) {
			var qcPersonIds = "${dockdep.qcPersonIds}";
			var qcPersonNames = "${dockdep.qcPersonNames}";
			if (qcPersonIds.indexOf(qcPersonId) != -1) {
				depTree.updateNode(node);
				depTree.checkNode(node, true, false);
				depTree.expandNode(node, true);
			} else {
				var nodeChildrens = node.children;
				if(!nodeChildrens) {
					node.nocheck = true;
					node.name += " - [" + qcPersonNames + "]";
					depTree.updateNode(node);
				}
			}
		}
	</c:forEach>
}

function saveDockdeps() {
	var nodes = depTree.getCheckedNodes(true);
	var dockdepIds = new Array();
	for (var i=0; i<nodes.length; i++) {
		dockdepIds.push(nodes[i].id);
	}
	$.ajax({
		type: "POST",
		url: "qc/assignConfigCmp/saveDockdeps",
		data: {"qcPersonId":'${qcPerson.id}',"qcPersonName":'${qcPerson.realName}',"dockdepIds":dockdepIds},
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
	<span style="color: green;">质检员：${qcPerson.realName}</span>
	<ul id="depTree" class="ztree" style="overflow:auto;"></ul>
	<div align="center">
		<input type="button" class="blue" value="保存" onclick="saveDockdeps()"/>　
		<input type="button" class="blue" value="重置" onclick="window.location.reload();"/>
	</div>
</body>
</html>
