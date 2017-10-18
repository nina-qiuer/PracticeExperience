<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>跨月添加合同增补单列表</title>
<script type="text/javascript">
$(function(){
	$('#accordion').accordion({
    	collapsible: true,
    	heightStyle: "content"
    });
});

function search() {
	$("#addDiffMonthForm").attr("action", "qs/substdContractAppendAmt/addDiffMonthList");
	$("#addDiffMonthForm").submit();
}

function resetForm() {
	$("input[type=text]", "#addDiffMonthForm").val('');
	$("#pageNo").val('1');
}
</script>
</head>
<body>
<form id="addDiffMonthForm" method="post" action="qs/substdContractAppendAmt/addDiffMonthList">
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
<div id="accordion">
<h3>跨月添加合同增补单列表</h3>
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
		<td>
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
		<th>合同增补单<br>编号</th>
		<th>订单<br>签约日期</th>
		<th>合同增补单<br>添加日期</th>
		<th>合同增补单<br>金额(元)</th>
		<th>合同增补单<br>添加人</th>
		<th>当月添加<br>合同增补单总额</th>
		<th>订单号</th>
		<th>事业部</th>
		<th>产品部</th>
		<th>产品组</th>
		<th>产品经理</th>
		<th>产品专员</th>
		<th>客服经理</th>
		<th>客服专员</th>
	</tr>
	<c:forEach items="${dto.dataList}" var="ca">
	<tr>
		<td>${ca.caId}</td>
		<td>${ca.signDate}</td>
		<td>${ca.addDate}</td>
		<td>${ca.price}</td>
		<td>${ca.addPerson}</td>
		<td>${ca.monthTotalAmount}</td>
		<td class="orderId">${ca.ordId}</td>
		<td>${ca.businessUnit}</td>
		<td>${ca.prdDep}</td>
		<td>${ca.prdTeam}</td>
		<td>${ca.prdManager}</td>
		<td>${ca.producter}</td>
		<td>${ca.customerManager}</td>
		<td>${ca.customer}</td>
	</tr>
	</c:forEach>
</table>
<%@ include file="/WEB-INF/jsp/common/pager.jsp" %>
</form>
</body>
</html>
