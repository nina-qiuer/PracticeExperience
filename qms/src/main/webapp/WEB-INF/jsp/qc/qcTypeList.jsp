<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>资源管理</title>
<script type="text/javascript">
var qcTypeTree;

$(document).ready(function(){
	qcTypeInit();
});

function qcTypeInit() {
	var setting = {
		view: {
			addHoverDom: addHoverDom,
			removeHoverDom: removeHoverDom,
			selectedMulti: false
		},
		edit: {
			enable: true,
			showRenameBtn: true,
			renameTitle: "编辑节点",
			showRemoveBtn: true,
			removeTitle: "删除节点"
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			beforeEditName: beforeEditName,
			beforeRemove: beforeRemove,
			beforeDrag: beforeDrag
		}
	};
	
	var zNodes = new Array();
	<c:forEach items="${qcTypeList}" var="qcType">
		zNodes.push({id:'${qcType.id}', pId:'${qcType.pid}', name:'${qcType.name}', open:true});
	</c:forEach>
	
	qcTypeTree = $.fn.zTree.init($("#qcTypeTree"), setting, zNodes);
}

var newCount = 1;
function addHoverDom(treeId, treeNode) {
	var sObj = $("#" + treeNode.tId + "_span");
	if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
	var addStr = "<span class='button add' id='addBtn_" + treeNode.tId + "' title='添加节点' onfocus='this.blur();'></span>";
	sObj.after(addStr);
	var btn = $("#addBtn_"+treeNode.tId);
	if (btn) btn.bind("click", function(){
		openWin('添加质检类型' + ' - [' + treeNode.name + ']', 'qc/qcType/' + treeNode.id + '/toAddChild', 410, 117);
	});
};

function removeHoverDom(treeId, treeNode) {
	$("#addBtn_"+treeNode.tId).unbind().remove();
};

function beforeEditName(treeId, treeNode) {
	qcTypeTree.selectNode(treeNode);
	openWin('编辑质检类型' + ' - [' + treeNode.name + ']', 'qc/qcType/' + treeNode.id + '/toUpdate', 410, 117);
	return false; // 不进入编辑状态
}

function beforeRemove(treeId, treeNode) {
	qcTypeTree.selectNode(treeNode);
	if (0 == treeNode.level) { // 根节点
		layer.alert("不允许删除根节点！~", {icon: 2});
		return false;
	}
	if (treeNode.isParent) { // 如果是父节点，则提示先删除子节点
		layer.alert("不允许直接删除父节点，请先删除子节点！~", {icon: 2});
		return false;
	}
	
	var msg = "确认删除节点 [" + treeNode.name + "] 吗？";
	layer.confirm(msg, {icon: 3}, function(index){
		layer.close(index);
	    $.ajax({
			type: "GET",
			url: "qc/qcType/" + treeNode.id + "/delete",
			async: false,
			success: function(data) {
				if ("Success" == data) {
					qcTypeTree.removeNode(treeNode);
				}
			}
		});
	});
	
	return false;
}

function beforeDrag(treeId, treeNodes) {
	return false;
}
</script>
</head>

<body>
	<ul id="qcTypeTree" class="ztree" style="overflow:auto;"></ul>
</body>
</html>
