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
		$(".pici_search span").unbind("click").bind("click", function() {
			$(this).toggleClass("active");
			$(this).find("span").toggleClass("tempactive");
		})
	});

	function commitPage(currentPage, pageSize) {
		$('#analyseList_form').attr(
				"action",
				"complaint_dt_report-getDtAnalyseList?page.currentPage="
						+ currentPage + "&page.pageSize=" + pageSize);
		$('#analyseList_form').submit();
	}

	function checkIsTheoryPayout() {
		var s = $("#isTheoryPayout").attr("checked");
		if (s == undefined) {
			$("#isTheoryPayout").val(0);
		} else {
			$("#isTheoryPayout").val(1);
		}
	}

	function checkIsRealPayout() {
		var s = $("#isRealPayout").attr("checked");
		if (s == undefined) {
			$("#isRealPayout").val(0);
		} else {
			$("#isRealPayout").val(1);
		}
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
	<form id="analyseList_form" method="post"
		action="complaint_dt_report-getDtAnalyseList">
		<div class="pici_search pd5">
			<label class="mr10 ">供应商名称： <input type="text"
				name="agencyName" id="agencyName" value="${agencyName}" /></label> </label> <label
				class="mr10 ">出发地： <input type="text" name="startCity"
				id="startCity" value="${startCity}" /></label> <label class="mr10 ">目的地：
				<input type="text" name="endCity" id="endCity" value="${endCity}" />
			</label> <label class="mr10 ">签约城市： <input type="text"
				name="signCity" id="signCity" value="${signCity}" /></label><label
				class="mr10 ">处理岗： <select class="mr10" name="dealDepart">
					<option value="">全部</option>
					<option value="售前组"
						<c:if test="${'售前组'.equals(dealDepart)}">selected</c:if>>售前组</option>
					<option value="售后组"
						<c:if test="${'售后组'.equals(dealDepart)}">selected</c:if>>售后组</option>
					<option value="资深组"
						<c:if test="${'资深组'.equals(dealDepart)}">selected</c:if>>资深组</option>
					<option value="出游前客户服务"
						<c:if test="${'出游前客户服务'.equals(dealDepart)}">selected</c:if>>出游前客户服务</option>
					<option value="机票组 "
						<c:if test="${'机票组'.equals(dealDepart)}">selected</c:if>>机票组</option>
					<option value="酒店组"
						<c:if test="${'酒店组'.equals(dealDepart)}">selected</c:if>>酒店组</option>
					<option value="分销组"
						<c:if test="${'分销组'.equals(dealDepart)}">selected</c:if>>分销组</option>
					<option value="交通组"
						<c:if test="${'交通组'.equals(dealDepart)}">selected</c:if>>交通组</option>
			</select></label> </br> <label class="mr10 ">产品品类： <select class="mr10"
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
			</select>
			</label> <label class="mr10 ">产品经理： <input type="text"
				name="productLeder" id="productLeder" value="${productLeder}" /></label> <label
				class="mr10 ">归属部门： <input type="text" name="belongDept"
				id="belongDept" value="${belongDept}" /></label> <label class="mr10 ">线路编号：
				<input type="text" name="routeId" id="routeId" value="${routeId}" />
			</label> <label class="mr10 "><input type="checkbox"
				id="isTheoryPayout" name="isTheoryPayout"
				<c:if test="${isTheoryPayout==1}" >checked="checked" value="1"</c:if>
				value="0" onclick="checkIsTheoryPayout()">理论赔款单</label> <label
				class="mr10 "><input type="checkbox" id="isRealPayout"
				name="isRealPayout"
				<c:if test="${isRealPayout==1}" >checked="checked" value="1"</c:if>
				value="0" onclick="checkIsRealPayout()">实际赔款单</label></br> <label
				class="mr10 ">订单编号： <input type="text" name="orderId"
				id="orderId" value="${orderId}" />
			</label> <label class="mr10 ">投诉单号： <input type="text"
				name="complaintId" id="complaintId" value="${complaintId}" /></label> <label
				class="mr10 ">完成时间：</label> <input type="text" id="assignTimeBgn"
				name="assignTimeBgn" value="${assignTimeBgn }"
				onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'assignTimeEnd\')}',minDate:'#F{$dp.$D(\'assignTimeEnd\',{M:-24})}'})"
				readOnly="readonly" class="Wdate">
			&nbsp;&nbsp;--&nbsp;&nbsp; <input type="text" id="assignTimeEnd"
				name="assignTimeEnd" value="${assignTimeEnd }"
				onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'assignTimeBgn\')}',maxDate:'#F{$dp.$D(\'assignTimeBgn\',{M:24})}'})"
				readOnly="readonly" class="Wdate"> <input type="submit"
				value="查询" />
		</div>
	</form>
	<table class="listtable" width="98%">
		<thead>
			<th width="5%">投诉单号</th>
			<th width="5%">订单号</th>
			<th width="5%">理论赔付总额</th>
			<th width="5%">实际赔付总额</th>
			<th width="5%">分担总额</th>
			<th width="5%">供应商承担金额</th>
			<th width="5%">公司承担金额</th>
			<th width="6%">质量工具承担金额</th>
			<th width="6%">订单利润赔付金额</th>
			<th width="5%">线路编号</th>
			<th width="6%">线路名称</th>
			<th width="5%">供应商名称</th>
			<th width="5%">产品经理</th>
			<th width="5%">归属部门</th>
			<th width="5%">产品品类</th>
			<th width="4%">出发地</th>
			<th width="4%">目的地</th>
			<th width="4%">签约城市</th>
			<th width="5%">完成时间</th>
		</thead>
		<tbody>
			<c:forEach items="${reportData}" var="complaint_dt_analyse">
				<tr>
					<td width="5%" class="td_center">${complaint_dt_analyse.complaint_id}</td>
					<td width="5%" class="td_center">${complaint_dt_analyse.order_id}</td>
					<td width="5%" class="td_right">${complaint_dt_analyse.theoryPayout}</td>
					<td width="5%" class="td_right">${complaint_dt_analyse.realPayout}</td>
					<td width="5%" class="td_right">${complaint_dt_analyse.share_total}</td>
					<td width="5%" class="td_right">${complaint_dt_analyse.supplier_total}</td>
					<td width="5%" class="td_right">${complaint_dt_analyse.company_total}</td>
					<td width="6%" class="td_right">${complaint_dt_analyse.quality_tool_total}</td>
					<td width="6%" class="td_right">${complaint_dt_analyse.order_gains}</td>
					<td width="5%" class="td_center">${complaint_dt_analyse.route_id}</td>
					<td width="6%">${complaint_dt_analyse.route}</td>
					<td width="5%">${complaint_dt_analyse.agency_name}</td>
					<td width="5%" class="td_center">${complaint_dt_analyse.product_leader}</td>
					<td width="5%">${complaint_dt_analyse.belong_dept}</td>
					<td width="5%" class="td_center">${complaint_dt_analyse.route_type}</td>
					<td width="4%" class="td_center">${complaint_dt_analyse.start_city}</td>
					<td width="4%" class="td_center">${complaint_dt_analyse.end_city}</td>
					<td width="4%" class="td_center">${complaint_dt_analyse.sign_city}</td>
					<td width="5%" class="td_center"><fmt:formatDate
							value="${complaint_dt_analyse.finish_date}" pattern="yyyy-MM-dd" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</HTML>

<%@include file="/WEB-INF/html/pagerCommon.jsp"%>
