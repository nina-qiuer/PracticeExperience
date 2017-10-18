<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>出团通知超时订单列表</title>
<script type="text/javascript">
$(function(){
	$('#accordion').accordion({
    	collapsible: true,
    	heightStyle: "content"
    });
    
    $('#destCateSet').buttonset();
	$('#destCateSet').click(function() {
		searchResetPage();
	});
});

function search() {
	$("#ntoForm").attr("action", "qs/substdOrderSnapshot/listNoticeTimeout");
	$("#ntoForm").submit();
}

function resetForm() {
	$("input[type=text]", "#ntoForm").val('');
	$("#pageNo").val('1');
}
</script>
</head>
<body>
<form id="ntoForm" method="post" action="qs/substdOrderSnapshot/listNoticeTimeout">
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
<div id="accordion">
<h3>出团通知超时订单列表</h3>
<div align="right">
<table width="95%">
	<tr>
		<td>　订单ID：<form:input path="dto.ordId" size="10"/></td>
		<td>　　产品ID：<form:input path="dto.prdId" size="10"/></td>
		<td>　事业部：<form:input path="dto.businessUnit" size="10"/></td>
		<td>　产品部：<form:input path="dto.prdDep" size="10"/></td>
		<td>　产品组：<form:input path="dto.prdTeam" size="10"/></td>
	</tr>
	<tr>
		<td>
			出游日期：<form:input path="dto.departDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly" size="10"/>
		</td>
		<td>　产品经理：<form:input path="dto.prdManager" size="10"/></td>
		<td>产品专员：<form:input path="dto.producter" size="10"/></td>
		<td>客服经理：<form:input path="dto.customerManager" size="10"/></td>
		<td>客服专员：<form:input path="dto.customer" size="10"/></td>
	</tr>
	<tr>
		<td>
			统计日期：<form:input path="dto.statisticDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly" size="10"/>
		</td>
		<td colspan="4">
			<span id="destCateSet">目的地大类：
				<form:radiobutton path="dto.destCate" value="全部" label="全部"/>
				<form:radiobuttons path="dto.destCate" items="${destStdList}" itemValue="destCate" itemLabel="destCate"/>
			</span>　　　　　
			<input type="button" class="blue" value="查询" onclick="searchResetPage()">
			<input type="button" class="blue" value="重置" onclick="resetForm()">
		</td>
	</tr>
</table>
</div>
</div>
<table class="listtable">
	<tr>
		<th>订单ID</th>
		<th>产品ID</th>
		<th>产品名称</th>
		<th>事业部</th>
		<th>产品部</th>
		<th>产品组</th>
		<th>产品经理</th>
		<th>产品专员</th>
		<th>目的地大类</th>
		<th>统计日期</th>
		<th>出游团期</th>
		<th>出团通知<br>发送日期</th>
		<th>超时天数</th>
	</tr>
	<c:forEach items="${dto.dataList}" var="ord">
	<tr>
		<td class="orderId">${ord.ordId}</td>
		<td class="prdId">${ord.prdId}</td>
		<td class="shorten10">${ord.prdName}</td>
		<td>${ord.businessUnit}</td>
		<td>${ord.prdDep}</td>
		<td>${ord.prdTeam}</td>
		<td>${ord.prdManager}</td>
		<td>${ord.producter}</td>
		<td>${ord.destCate}</td>
		<td>${ord.statisticDate}</td>
		<td>${ord.departDate}</td>
		<td>${ord.noticeSendDate}</td>
		<td>${ord.timeOutDays}</td>
	</tr>
	</c:forEach>
</table>
<%@ include file="/WEB-INF/jsp/common/pager.jsp" %>
</form>
</body>
</html>
