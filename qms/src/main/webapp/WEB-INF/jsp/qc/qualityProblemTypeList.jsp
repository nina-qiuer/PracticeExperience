<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>质量问题类型配置</title>
<script type="text/javascript">
var qpTypeTree;

$(document).ready(function(){
	qpTypeInit();
});

function qpTypeInit() {
	var setting = {
		view: {
			addHoverDom: addHoverDom,
			removeHoverDom: removeHoverDom,
			fontCss: getFont,
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
	<c:forEach items="${qpTypeList}" var="qpType">
		<c:if test="${0 == qpType.touchRedFlag}">
			zNodes.push({id:'${qpType.id}', pId:'${qpType.pid}', name:'${qpType.name}', open:true});
		</c:if>
		<c:if test="${1 == qpType.touchRedFlag}">
			zNodes.push({id:'${qpType.id}', pId:'${qpType.pid}', name:'${qpType.name}', font:{'color':'red'}, open:true});
		</c:if>
	</c:forEach>
	
	qpTypeTree = $.fn.zTree.init($("#qpTypeTree"), setting, zNodes);
}

function getFont(treeId, node) {
	return node.font ? node.font : {};
}

var newCount = 1;
function addHoverDom(treeId, treeNode) {
	var sObj = $("#" + treeNode.tId + "_span");
	if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
	var addStr = "<span class='button add' id='addBtn_" + treeNode.tId + "' title='添加节点' onfocus='this.blur();'></span>";
	sObj.after(addStr);
	var btn = $("#addBtn_"+treeNode.tId);
	if (btn) btn.bind("click", function(){
		openWin('添加质量问题类型' + ' - [' + treeNode.name + ']', 'qc/qualityProblemType/' + treeNode.id + '/toAddChild', 450, 172);
	});
};

function removeHoverDom(treeId, treeNode) {
	$("#addBtn_"+treeNode.tId).unbind().remove();
};

function beforeEditName(treeId, treeNode) {
	qpTypeTree.selectNode(treeNode);
	openWin('编辑质量问题类型' + ' - [' + treeNode.name + ']', 'qc/qualityProblemType/' + treeNode.id + '/toUpdate', 450, 172);
	return false; // 不进入编辑状态
}

function beforeRemove(treeId, treeNode) {
	qpTypeTree.selectNode(treeNode);
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
			url: "qc/qualityProblemType/" + treeNode.id + "/delete",
			async: false,
			success: function(data) {
				if ("Success" == data) {
					qpTypeTree.removeNode(treeNode);
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
	<span style="color: gray;">说明：C-投诉质检使用、O-运营质检使用、D-研发质检使用、I-内部质检使用</span>
	<ul id="qpTypeTree" class="ztree" style="overflow:auto;"></ul>
</body>
</html>
