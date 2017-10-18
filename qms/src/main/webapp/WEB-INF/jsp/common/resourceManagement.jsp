<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>资源管理</title>
<script type="text/javascript">
var resourceTree;

$(document).ready(function(){
	resourceInit();
});

function resourceInit() {
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
	<c:forEach items="${resourceList}" var="resource">
		zNodes.push({id:'${resource.id}', pId:'${resource.pid}', name:'${resource.name}', open:true});
	</c:forEach>
	
	resourceTree = $.fn.zTree.init($("#resourceTree"), setting, zNodes);
}

var newCount = 1;
function addHoverDom(treeId, treeNode) {
	var sObj = $("#" + treeNode.tId + "_span");
	if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
	var addStr = "<span class='button add' id='addBtn_" + treeNode.tId + "' title='添加子节点' onfocus='this.blur();'></span>";
	sObj.after(addStr);
	var btn = $("#addBtn_"+treeNode.tId);
	if (btn) btn.bind("click", function(){
		openWin('添加资源' + ' - [' + treeNode.name + ']', 'common/resource/' + treeNode.id + '/toAddChild', 410, 258);
	});
};

function removeHoverDom(treeId, treeNode) {
	$("#addBtn_"+treeNode.tId).unbind().remove();
};

function beforeEditName(treeId, treeNode) {
	resourceTree.selectNode(treeNode);
	openWin('编辑资源' + ' - [' + treeNode.name + ']', 'common/resource/' + treeNode.id + '/toUpdate', 410, 258);
	return false; // 不进入编辑状态
}

function beforeRemove(treeId, treeNode) {
	resourceTree.selectNode(treeNode);
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
			url: "common/resource/" + treeNode.id + "/delete",
			async: false,
			success: function(data) {
				if ("Success" == data) {
					resourceTree.removeNode(treeNode);
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
	<ul id="resourceTree" class="ztree" style="overflow:auto;"></ul>
</body>
</html>
