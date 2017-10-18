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
		data: {
			simpleData: {
				enable: true
			}
		}
	};
	
	var zNodes = new Array();
	<c:forEach items="${depList}" var="dep">
		zNodes.push({id:'${dep.id}', pId:'${dep.pid}', name:'${dep.name}',open:true});
	</c:forEach>
	
	depTree = $.fn.zTree.init($("#depTree"), setting, zNodes);
}

function checkNodes() {
	<c:forEach items="${dockdepList}" var="dockdep">
		var node = depTree.getNodeByParam("id", "${dockdep.dockdepId}", null);
		if (null != node) {
			    var qcPersonNames = "${dockdep.qcPersonNames}";
				var nodeChildrens = node.children;
				if(!nodeChildrens) {
					node.nocheck = true;
					node.name += " - [" + qcPersonNames + "]";
					depTree.updateNode(node);
				}
			
		}
	</c:forEach>
	
}


</script>
</head>

<body>
	<ul id="depTree" class="ztree" style="overflow:auto;"></ul>
</body>
</html>
