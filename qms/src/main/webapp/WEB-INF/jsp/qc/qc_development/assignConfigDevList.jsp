<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>研发质检员配置</title>
<script type="text/javascript">
$(document).ready(function(){
	$("body").layout({
		resizerTip:"可调整大小",
		togglerTip_open:"关闭",
		togglerTip_closed:"打开",
		west__size: 570,
		center__onresize: resizeTabPanel
	});
	
	$(".accordion").each(function() {
		$(this).accordion({
	    	collapsible: true,
	    	heightStyle: "content"
	    });
	});
});

function resizeTabPanel() {
	$(".tabpanel").width('100%');
	$(".tabpanel_tab_content").width('100%');
	$(".tabpanel_move_content").width('100%');
	$(".tabpanel_content").width('100%');
}

//function depConfig() {
//	$("#doProjectFrame").attr("src", "qc/assignConfigCmp/depConfig");
//}

function doProjectConfig(id) {
	$("#doProjectFrame").attr("src", "qc/assignConfigDev/"+id+"/doProjectConfig");
}

function deletePerson(id, qcPersonName) {
	var msg = "确定要删除质检员[" + qcPersonName + "]吗？";
	layer.confirm(msg, {icon: 3}, function(){
	    $.ajax({
			type: "post",
			url: "qc/assignConfigDev/deleteQc",
			data:{
				
				id :id
			},
			cache : false,
			dataType: "json",
			success : function(result) {
				if(result)
				{
			    	if(result.retCode == "0")
					{
			    		window.location.href = "qc/assignConfigDev/list";
			    		
					 }else{
						
						layer.alert(result.resMsg, {icon: 2});
					}
			     }
			 }
		});
	});
}
</script>
</head>

<body>
<div class="ui-layout-west" style="overflow: auto;">
<div class="accordion">
	<h3>研发质检员配置列表</h3>
	<div align="right">
		<input type="button" class="blue" value="添加质检员" onclick="openWin('添加质检员', 'qc/assignConfigDev/toAdd', 400, 144)">
	</div>
</div>
<table class="listtable" width="100%">
	<tr>
		<th>质检员ID</th>
		<th>质检员</th>
		<th width="100">操作</th>
	</tr>
	<c:forEach items="${list}" var="ac">
	<tr align="center">
		<td>${ac.qcPersonId}</td>
		<td>${ac.qcPersonName}</td>
		<td>
			<img src="res/img/close.png" class="img_btn" title="删除" onclick="deletePerson('${ac.id}', '${ac.qcPersonName}')">
			<img src="res/img/edit.gif" class="img_btn" title="对接部门配置" onclick="doProjectConfig('${ac.qcPersonId}')">
		</td>
	</tr>
	</c:forEach>
</table>
</div>
<div class="ui-layout-center">
	<iframe id="doProjectFrame" width="100%" height="100%" frameborder="0" scrolling="auto"></iframe>
</div>

</body>
</html>
