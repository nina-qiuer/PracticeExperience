<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>通用类型配置</title>
<script type="text/javascript">
	var typeTree;
	
	$(document).ready(function(){
		initTree();
	});
	
	function initTree(){
		//ztree 参数初设置
		var setting ={
				view : {
					addHoverDom : addHoverDom,
					removeHoverDom : removeHoverDom,
					selectedMulti: false
				},
				edit: {
					enable : true,
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
		//数据格式
		var zNodes = new Array();
		<c:forEach items="${dataList}" var="type">
			zNodes.push({id:'${type.id}', pId:'${type.pid}', name:'${type.name}', open:true});
		</c:forEach>
		
		typeTree = $.fn.zTree.init($("#typeTree"), setting, zNodes);
	}
	
	function addHoverDom(treeId, treeNode){
		var sObj = $("#" + treeNode.tId + "_span");
		if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
		var addStr = "<span class='button add' id='addBtn_" + treeNode.tId + "' title='添加节点' onfocus='this.blur();'></span>";
		sObj.after(addStr);
		var btn = $("#addBtn_"+treeNode.tId);
		if (btn) btn.bind("click", function(){
			openWin('添加类型' + ' - 【' + treeNode.name + '】', 'common/cmType/' + treeNode.id + '/toAddChild', 350, 230);
		});
	}
	
	function removeHoverDom(treeId, treeNode){
		$("#addBtn_"+treeNode.tId).unbind().remove();
	}
	
	function beforeEditName(treeId, treeNode){
		typeTree.selectNode(treeNode);
		openWin('编辑类型' + ' - 【' + treeNode.name + '】', 'common/cmType/' + treeNode.id + '/toUpdate', 350, 230);
		return false; // 不进入编辑状态
	}
	
	function beforeRemove(treeId, treeNode){
		typeTree.selectNode(treeNode);
		if (0 == treeNode.level) { 
			// 根节点
			layer.alert("不允许删除根节点！~", {icon: 2});
			return false;
		}
		if (treeNode.isParent) {
			 // 如果是父节点，则提示先删除子节点
			layer.alert("不允许直接删除父节点，请先删除子节点！~", {icon: 2});
			return false;
		}
		
		var msg = "确认删除节点 【" + treeNode.name + "】 吗？";
		layer.confirm(msg, {icon: 3}, function(index){
			layer.close(index);
		    $.ajax({
				type: "GET",
				url: "common/cmType/" + treeNode.id + "/delete",
				async: false,
				success: function(data) {
					if ("Success" == data) {
						typeTree.removeNode(treeNode);
					}
				}
			});
		});
		
		return false;
	}
	
	function beforeDrag(treeId, treeNode){
		return false;
	}
</script>
</head>
<body>
	<span style="color: gray;">说明：CR：质检关联关闭原因类型  OT：其他 </span>
	<ul id="typeTree" class="ztree" style="overflow:auto;"></ul>
</body>
</html>
