<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>投诉处理数据报表</title>
<link rel="stylesheet"
	href="${CONFIG.res_url}css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript"
	src="${CONFIG.res_url}script/ztree/jquery.ztree.core-3.1.js"></script>

<script type="text/javascript">
	$(function() {
	});

	function commitPage(currentPage, pageSize) {
		$('#laun_list_form').attr(
				"action",
				"complaint_report-getLanchList?page.currentPage=" + currentPage
						+ "&page.pageSize=" + pageSize);
		$('#laun_list_form').submit();
	}
</script>

<style type="text/css">
.td_right {
	text-align: right;
}

.td_center {
	text-align: center;
}
</style>
</HEAD>
<body style="min-width: 1280px">
	<form id="laun_list_form" method="post"
		action="complaint_report-getLanchList">
		<div class="pici_search pd5">
			<label class="mr10 ">产品品类： <select class="mr10"
				name="routeType">
					<option value="">全部</option>
					<option value="跟团游"
						<c:if test="${'跟团游'.equals(routeType)}">selected</c:if>>跟团游</option>
					<option value="自助游"
						<c:if test="${'自助游'.equals(routeType)}">selected</c:if>>自助游</option>
					<option value="团队游"
						<c:if test="${'团队游'.equals(routeType)}">selected</c:if>>团队游</option>
					<option value="邮轮"
						<c:if test="${'邮轮'.equals(routeType)}">selected</c:if>>邮轮</option>
					<option value="自驾游 "
						<c:if test="${'自驾游'.equals(routeType)}">selected</c:if>>自驾游</option>
					<option value="门票"
						<c:if test="${'门票'.equals(routeType)}">selected</c:if>>门票</option>
					<option value="酒店"
						<c:if test="${'酒店'.equals(routeType)}">selected</c:if>>酒店</option>
					<option value="火车票"
						<c:if test="${'火车票'.equals(routeType)}">selected</c:if>>火车票</option>
					<option value="汽车票"
						<c:if test="${'汽车票'.equals(routeType)}">selected</c:if>>汽车票</option>
					<option value="签证"
						<c:if test="${'签证'.equals(routeType)}">selected</c:if>>签证</option>
					<option value="用车服务"
						<c:if test="${'用车服务'.equals(routeType)}">selected</c:if>>用车服务</option>
					<option value="票务"
						<c:if test="${'票务'.equals(routeType)}">selected</c:if>>票务</option>
					<option value="目的地服务"
						<c:if test="${'目的地服务'.equals(routeType)}">selected</c:if>>目的地服务</option>
					<option value="通信"
						<c:if test="${'通信'.equals(routeType)}">selected</c:if>>通信</option>
			</select><label class="mr10 ">完成时间：</label> <input type="text"
				id="finishTimeBgn" name="finishTimeBgn" value="${finishTimeBgn }"
				onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'finishTimeEnd\')}',minDate:'#F{$dp.$D(\'finishTimeEnd\',{M:-24})}'})"
				readOnly="readonly" class="Wdate">
				&nbsp;&nbsp;--&nbsp;&nbsp; <input type="text" id="finishTimeEnd"
				name="finishTimeEnd" value="${finishTimeEnd }"
				onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'finishTimeBgn\')}',maxDate:'#F{$dp.$D(\'finishTimeBgn\',{M:24})}'})"
				readOnly="readonly" class="Wdate"> 
				<label class="mr10 ">二级部门： <input type="text"
				name="dep_second" id="dep_second" value="${dep_second }" /></label>
				<label class="mr10 ">三级部门： <input type="text"
				name="dep_third" id="dep_third" value="${dep_third }" /></label>
				<label class="mr10 ">四级部门： <input type="text"
				name="dep_fourth" id="dep_fourth" value="${dep_fourth }" /></label>
				<input type="submit"
				value="查询" />
		</div>
	</form>
	<table class="listtable" width="98%">
		<thead>
			<th width="5%">投诉单号</th>
			<th width="5%">产品类型</th>
			<th width="5%">发起人</th>
			<th width="5%">一级部门</th>
			<th width="5%">二级部门</th>
			<th width="5%">三级部门</th>
			<th width="5%">四级部门</th>
		</thead>
		<tbody>
			<c:forEach items="${complaintLaunchList}" var="complaint_launch">
				<tr>
					<td width="5%" class="td_center">${complaint_launch.complaint_id}</td>
					<td width="5%" class="td_center">${complaint_launch.route_type}</td>
					<td width="5%" class="td_center">${complaint_launch.launcher_name}</td>
					<td width="5%" class="td_left">${complaint_launch.depname_first}</td>
					<td width="5%" class="td_left">${complaint_launch.depname_second}</td>
					<td width="5%" class="td_left">${complaint_launch.depname_third}</td>
					<td width="5%" class="td_left">${complaint_launch.depname_fourth}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</HTML>

<%@include file="/WEB-INF/html/pagerCommon.jsp"%>
