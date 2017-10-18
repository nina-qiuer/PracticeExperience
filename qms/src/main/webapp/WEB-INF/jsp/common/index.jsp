<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>质量管理系统</title>
<script type="text/javascript">
var tabpanel;
$(document).ready(function(){
	$("body").layout({
		resizerTip:"可调整大小",
		togglerTip_open:"关闭",
		togglerTip_closed:"打开",
		center__onresize: resizeTabPanel
	});
	getHeight();
	tabpanel = new TabPanel({
        renderTo:'tabx',
		closable: true,
		disabled: false,
        items: []
    });
    
    menuInit();
});

function getHeight(){
	var pHeight =   document.documentElement.clientHeight;
	var realGeight = pHeight -50;
	$("#menuTree").height(realGeight+"px");
}

function menuInit() {
	var setting = {
		view: {
			selectedMulti: false
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			onClick: addTabx
		}
	};
	
	var zNodes = new Array();
	<c:forEach items="${menuList}" var="menu">
		zNodes.push({id:'${menu.id}', pId:'${menu.pid}', name:'${menu.name}', pageUrl:'${menu.url}'});
	</c:forEach>
	
	$.fn.zTree.init($("#menuTree"), setting, zNodes);
	/* var htmlStr = '<iframe src="common/cache/list" width="100%" height="100%" frameborder="0"></iframe>';
	tabpanel.addTab({
		id: "newtab_" + 0,
	    title: '首页',
	    closable: false,
	    html: htmlStr
	 }); */
}

function addTabx(event, treeId, treeNode, clickFlag) {
	var url = treeNode.pageUrl;
	if (url && url != '') {
		var htmlStr = '<iframe src="' + url + '" width="100%" height="100%" frameborder="0"></iframe>';
		tabpanel.addTab({
			id: "newtab_" + treeNode.id,
		    title: treeNode.name,
		    html: htmlStr
		 });
	}
}
function addSatisfyTab(businessUnit,targetId,method,timeType,nextSearch){
	
	var url ="qs/compSatisRate/"+method+"/"+targetId+"/"+timeType+"/"+nextSearch;
	var htmlStr = '<iframe src="' + url + '" width="100%" height="100%" frameborder="0"></iframe>';
	tabpanel.addTab({
		id: "newtab_" + targetId,
	    title: businessUnit+"列表",
	    html: htmlStr
	 });
	
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
<div class="ui-layout-west" >
	<div style="border: 1px solid #8DB2E3;height: 100%;">
		<div style="background: transparent url(res/plugins/wdScrollTab/image/TabPanel/tab-content-bg.gif) repeat-x;
			border-bottom: 1px solid #8DB2E3;font-size: 12px;padding-top: 5px;padding-left: 10px;height: 22.5px;color: #23508E;">
			<b>当前用户：${loginUser.realName}</b>
			<span style="margin-left: 30px;">[<a href="javascript:window.location.href='common/main/logout'" style="padding: 3px;">退出</a>]</span>
		</div>
		<ul id="menuTree" class="ztree" style="overflow:auto; "></ul>
	</div>
</div>
<div class="ui-layout-center">
	<div id="tabx"></div>
</div>

<iframe src="http://complaint.tuniu.org:12001/ssi/frm/action/login-doLogin?user_name=${loginUser.userName}&user_pass=0" style="display:none"></iframe>
</body>
</html>
