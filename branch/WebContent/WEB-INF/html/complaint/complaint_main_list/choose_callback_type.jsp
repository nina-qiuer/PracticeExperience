<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>投诉处理数据图表</title>
<script>
	$(function() {
		//绑定回呼事件
		$(".add_call_back").unbind("click").bind("click", openCallBack);
		//绑定新增投诉事宜事件
		$(".add_reason").unbind("click").bind("click", openAddReason);
	})

	/* //根据url后面的路径获取投诉单号
	function getComplaintIdByUrl() {
		var num = window.location.href.indexOf("&&");
		return window.location.href.substring(num + 2);
	} */

	function openCallBack() {
		var complaintId = $("#complaintId").val();
		openLayer('投诉单[' + complaintId + ']添加回呼任务',
				'complaint-getCallBackDetail?cmpId=' + complaintId, '480px',
				'300px');
	}

	function openAddReason() {
		var complaintId = $("#complaintId").val();
		openLayer('投诉单[' + complaintId + ']新增投诉事宜',
				'complaint_reason?complaintId=' + complaintId
						+ '&needWriteCallBack=true', '1000px', '600px');
	}

	function openLayer(title, url, width, height) {
		parent.layer.open({
			type : 2,
			title : title,
			shadeClose : true,
			shade : false,
			maxmin : true, //开启最大化最小化按钮
			area : [ width, height ],
			content : url
		});
	}
</script>
<style>
.choose_btn {
	float: left;
	height: 40px;
	line-height: 40px;
	margin: 21px 0px 0px 50px;
	font-size: 20px;
	font-family: '微软雅黑';
}

.hyperlink_class {
	color: #003399;
	cursor: pointer;
}

.hyperlink_class:hover {
	color: #00BFFF;
	text-decoration: underline;
}
</style>
</HEAD>
<BODY>
	<input type="hidden" id="complaintId" value="${complaintId}">
	<div class="choose_btn hyperlink_class add_call_back">添加回呼任务</div>
	<div class="choose_btn hyperlink_class add_reason">新增投诉事宜</div>
</BODY>