<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>订单收款超时列表</title>
<script type="text/javascript">
$(function(){
	$('#accordion').accordion({
    	collapsible: true,
    	heightStyle: "content"
    });
});

function search() {
	$("#collectTimeoutForm").attr("action", "qs/substdOrderAmt/collectTimeoutList");
	$("#collectTimeoutForm").submit();
}

function resetForm() {
	$("input[type=text]", "#collectTimeoutForm").val('');
	$("#pageNo").val('1');
}
</script>
</head>
<body>
<form id="collectTimeoutForm" method="post" action="qs/substdOrderAmt/collectTimeoutList">
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
<div id="accordion">
<h3>订单收款超时列表</h3>
<div align="right">
<table width="95%">
	<tr>
		<td>　事业部：<form:input path="dto.businessUnit" size="10"/></td>
		<td>　产品部：<form:input path="dto.prdDep" size="10"/></td>
		<td>产品组：<form:input path="dto.prdTeam" size="10"/></td>
		<td>产品经理：<form:input path="dto.prdManager" size="10"/></td>
		<td>产品专员：<form:input path="dto.producter" size="10"/></td>
	</tr>
	<tr>
		<td>客服经理：<form:input path="dto.customerManager" size="10"/></td>
		<td>客服专员：<form:input path="dto.customer" size="10"/></td>
		<td>订单号：<form:input path="dto.ordId" size="10"/></td>
		<td>线路编号：<form:input path="dto.routeId" size="10"/></td>
		<td>
			统计日期：<form:input path="dto.statisticDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly" size="10"/>
		</td>
		<td style="text-align: left;">　　
			<input type="button" class="blue" value="查询" onclick="searchResetPage()">
			<input type="button" class="blue" value="重置" onclick="resetForm()">
		</td>
	</tr>
</table>
</div>
</div>
<table class="listtable">
	<tr>
		<th>订单号</th>
		<th>出游日期</th>
		<th>线路编号</th>
		<th>统计日期</th>
		<th>实收总额(元)</th>
		<th>应收总额(元)</th>
		<th>未收总额(元)</th>
		<th>事业部</th>
		<th>产品部</th>
		<th>产品组</th>
		<th>产品经理</th>
		<th>产品专员</th>
		<th>客服经理</th>
		<th>客服专员</th>
	</tr>
	<c:forEach items="${dto.dataList}" var="ord">
	<tr>
		<td>${ord.ordId}</td>
		<td>${ord.backDate}</td>
		<td>${ord.routeId}</td>
		<td>${ord.statisticDate}</td>
		<td>${ord.collectionAmount}</td>
		<td>${ord.validAmount}</td>
		<td>${ord.nonCollectionAmount}</td>
		<td>${ord.businessUnit}</td>
		<td>${ord.prdDep}</td>
		<td>${ord.prdTeam}</td>
		<td>${ord.prdManager}</td>
		<td>${ord.producter}</td>
		<td>${ord.customerManager}</td>
		<td>${ord.customer}</td>
	</tr>
	</c:forEach>
</table>
<%@ include file="/WEB-INF/jsp/common/pager.jsp" %>
</form>
</body>
</html>
