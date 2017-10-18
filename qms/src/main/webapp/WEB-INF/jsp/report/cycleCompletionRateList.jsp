<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<style type="text/css">

</style>
<title>周期完成率统计</title>
<script type="text/javascript">
$(function(){
	WdatePicker('${dto.qcPeriodTimeBgn}');
	WdatePicker('${dto.qcPeriodTimeEnd}');
	$('#accordion').accordion({
    	collapsible: true,
    	heightStyle: "content"
    });
    
    $(".zeroHide td").each(function() {
    	var value = $(this).text();
    	if (0 == value) {
    		$(this).text('');
    	}
    });
});

function search() {
	$("#completionRateForm").attr("action", "report/completionRate/list");
	$("#completionRateForm").submit();
}
function resetSearchTable() {
	$('.search :text').val('');
}
function setMaxDate(id){
	WdatePicker({maxDate:'#F{$dp.$D(\''+id+'\')}'});
}

function setMinDate(id){
	WdatePicker({minDate:'#F{$dp.$D(\''+id+'\')}'});
}

</script>
</head>
<body>
<form id="completionRateForm" method="post" action="">
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
<div id="accordion">
<h3>周期完成率统计</h3>
<div align="right">
<table width="95%" class="search">
	<tr>
		<td>
			质检期开始日期：	<form:input path="dto.qcPeriodTimeBgn" onfocus="setMaxDate('qcPeriodTimeEnd')" />-
			<form:input path="dto.qcPeriodTimeEnd" onfocus="setMinDate('qcPeriodTimeBgn')" />
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			统计日期：<form:input path="dto.statisticDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" /> 
			<input type="button" class="blue" value="查询" onclick="search()">
			<input type="button" class="blue" value="重置" onclick="resetSearchTable()">
		</td>
	</tr>
</table>
</div>
</div>
<table class="listtable">
	<tr>
		<th>质检员</th>
		<th>质检期开始<br>已完成单数</th>
		<th>质检期开始<br>总单数</th>
		<th>完成率</th>
	</tr>
	<c:forEach items="${dataList}" var="data">
	<tr class="zeroHide">
		<td>${data.qcPerson}</td>
		<td>${data.qcPeriodBgnDoneNum}</td>
		<td>${data.qcPeriodBgnNum}</td>
		<td><fmt:formatNumber value="${data.doneRate}" type="percent" pattern="#0.0%"/></td>
	</tr>
	</c:forEach>
</table>
</form>
</body>
</html>
