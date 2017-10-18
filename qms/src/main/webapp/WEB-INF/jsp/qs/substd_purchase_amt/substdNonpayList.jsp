<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>非现付采购单列表</title>
<script type="text/javascript">
$(function(){
	$('#accordion').accordion({
    	collapsible: true,
    	heightStyle: "content"
    });
});

function search() {
	$("#substdNonpayForm").attr("action", "qs/substdPurchaseAmt/substdNonpayList");
	$("#substdNonpayForm").submit();
}

function resetForm() {
	$("input[type=text]", "#substdNonpayForm").val('');
	$("#pageNo").val('1');
}
</script>
</head>
<body>
<form id="substdNonpayForm" method="post" action="qs/substdPurchaseAmt/substdNonpayList">
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
<div id="accordion">
<h3>非现付采购单列表</h3>
<div align="right">
<table width="95%">
	<tr>
		<td>事业部：<form:input path="dto.businessUnit" size="10"/></td>
		<td>　产品部：<form:input path="dto.prdDep" size="10"/></td>
		<td>产品组：<form:input path="dto.prdTeam" size="10"/></td>
		<td>产品经理：<form:input path="dto.prdManager" size="10"/></td>
		<td>产品专员：<form:input path="dto.producter" size="10"/></td>
	</tr>
	<tr>
		<td>订单号：<form:input path="dto.ordId" size="10"/></td>
		<td colspan="3">
			添加日期：<form:input path="dto.addDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly" size="10"/>
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
		<th>采购单<br>编号</th>
		<th>采购单<br>类型</th>
		<th>采购单<br>添加日期</th>
		<th>采购单<br>金额(元)</th>
		<th>采购单<br>添加人</th>
		<th>订单号</th>
		<th>事业部</th>
		<th>产品部</th>
		<th>产品组</th>
		<th>产品经理</th>
		<th>产品专员</th>
	</tr>
	<c:forEach items="${dto.dataList}" var="purchase">
	<tr>
		<td>${purchase.purchaseId}</td>
		<td>${purchase.type}</td>
		<td>${purchase.addDate}</td>
		<td>${purchase.price}</td>
		<td>${purchase.addPerson}</td>
		<td>${purchase.ordId}</td>
		<td>${purchase.businessUnit}</td>
		<td>${purchase.prdDep}</td>
		<td>${purchase.prdTeam}</td>
		<td>${purchase.prdManager}</td>
		<td>${purchase.producter}</td>
	</tr>
	</c:forEach>
</table>
<%@ include file="/WEB-INF/jsp/common/pager.jsp" %>
</form>
</body>
</html>
