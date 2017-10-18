<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>研发质检员对接部门设置</title>
<script type="text/javascript">
var projectTree;

$(document).ready(function(){
	projectInit();
	checkNodes();
});

function projectInit() {
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
	<c:forEach items="${projectList}" var="project">
		zNodes.push({id:'${project.projectId}', pId:'', name:'${project.projectName}'});
	</c:forEach>
	
	projectTree = $.fn.zTree.init($("#projectTree"), setting, zNodes);
}

function checkNodes() {
	var qcPersonId = "${qcPerson.id}";
	<c:forEach items="${doProjectList}" var="doProject">
		var node = projectTree.getNodeByParam("id", "${doProject.projectId}", null);
		if(null!=node){
			var qcPersonIds = "${doProject.qcPersonId}";
			var qcPersonNames = "${doProject.qcPersonName}";
			if (qcPersonIds.indexOf(qcPersonId) != -1) {
				projectTree.updateNode(node);
				projectTree.checkNode(node, true, false);
				projectTree.expandNode(node, true);
			} else {
				var nodeChildrens = node.children;
				if(!nodeChildrens) {
					node.nocheck = true;
					node.name += " - [" + qcPersonNames + "]";
					projectTree.updateNode(node);
				}
			}
		}
		
	</c:forEach>
}

function saveDeploy() {
	var nodes = projectTree.getCheckedNodes(true);
	var projectIds = new Array();
	for (var i=0; i<nodes.length; i++) {
		projectIds.push(nodes[i].id);
	}

	$.ajax({
		
		type: "post",
		url: "qc/assignConfigDev/savePrjectDeploy",
		data: {
			qcPersonId: $('#qcPersonId').val(),
			qcPersonName: $('#qcPersonName').val(),
			projectIds :projectIds
		},
		traditional: true,
		cache: false,
		success : function(result) {
			if(result)
			{
		    	if(result.retCode == "0")
				{
		    		layer.alert("保存成功", {icon: 6});
		    		
				 }else{
					
					layer.alert(result.resMsg, {icon: 2});
				}
		     }
		 }
	});
}
</script>
</head>

<body>
<form name="deployForm" id="deployForm" method="post" action="" >
<input type="hidden" name="qcPersonId"  id="qcPersonId" value="${qcPerson.id}">
<input type="hidden" name="qcPersonName"  id="qcPersonName" value="${qcPerson.realName}">

	<span style="color: green;">质检员：${qcPerson.realName}</span>
	<ul id="projectTree" class="ztree" style="overflow:auto;"></ul>
	<div align="center">
		<input type="button" class="blue" value="保存" onclick="saveDeploy()"/>　
		<input type="button" class="blue" value="重置" onclick="window.location.reload();"/>
	</div>
	
</form>
</body>
</html>
