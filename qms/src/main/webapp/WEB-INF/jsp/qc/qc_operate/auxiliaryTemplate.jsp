<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>辅助表模板</title>
<script type="text/javascript">
var qcTypeTree;
$(document).ready(function(){
	$("body").layout({
		resizerTip:"可调整大小",
		togglerTip_open:"关闭",
		togglerTip_closed:"打开",
		west__size: 570,
		center__onresize: resizeTabPanel
	});
	qcTypeInit();
	$(".accordion").each(function() {
		$(this).accordion({
	    	collapsible: true,
	    	heightStyle: "content"
	    });
	});
});


function qcTypeInit() {
	var setting = {
		view: {
			addHoverDom: addHoverDom,
			removeHoverDom: removeHoverDom,
			selectedMulti: false
		},
	
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			beforeEditName: beforeEditName,
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
	if (treeNode.editNameFlag || $("#editBtn_"+treeNode.tId).length>0) return;
	var addStr = "<span class='button add' id='editBtn_" + treeNode.tId + "' title='配置' onfocus='this.blur();'></span>";
	sObj.after(addStr);
	var btn = $("#editBtn_"+treeNode.tId);
	if (btn) btn.bind("click", function(){
		
		$("#authFrame").attr("src", "qc/operateQcBill/" + treeNode.id + "/authTemplate");
	});
};

function removeHoverDom(treeId, treeNode) {
	$("#editBtn_"+treeNode.tId).unbind().remove();
};

function beforeEditName(treeId, treeNode) {
	qcTypeTree.selectNode(treeNode);
	return false; // 不进入编辑状态
}



function beforeDrag(treeId, treeNodes) {
	return false;
}
function resizeTabPanel() {
	$(".tabpanel").width('100%');
	$(".tabpanel_tab_content").width('100%');
	$(".tabpanel_move_content").width('100%');
	$(".tabpanel_content").width('100%');
}


</script>
</head>

<body>
<div class="ui-layout-west" style="overflow: auto;">
	<ul id="qcTypeTree" class="ztree" style="overflow:auto;"></ul>
</div>
<div class="ui-layout-center">
	<iframe id="authFrame" width="100%" height="100%" frameborder="0" scrolling="auto"></iframe>
</div>

</body>
</html>
