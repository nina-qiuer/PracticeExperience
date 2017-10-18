<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>事业部投诉质检量统计</title>
<script type="text/javascript">
$(function(){
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
	$("#cmpQcBUStatForm").attr("action", "report/cmpQcBUStat/list");
	$("#cmpQcBUStatForm").submit();
}

function resetForm() {
	$("input[type=text]", "#cmpQcBUStatForm").val('');
}
</script>
</head>
<body>
<form id="cmpQcBUStatForm" method="post" action="report/cmpQcBUStat/list">
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
<div id="accordion">
<h3>事业部投诉质检量统计列表</h3>
<div align="right">
<table width="95%">
	<tr>
		<td>
			统计日期：<form:input path="dto.statDateBgn" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly" size="10"/> - 
			<form:input path="dto.statDateEnd" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly" size="10"/>　
			<input type="button" class="blue" value="查询" onclick="search()">
			<input type="button" class="blue" value="重置" onclick="resetForm()">
		</td>
	</tr>
</table>
</div>
</div>
<table class="listtable">
	<tr>
		<th>一级部门</th>
		<th>质检单数</th>
		<th>质检中单数</th>
		<th>质检完成单数</th>
		<th>撤销单数</th>
		<th>总完成单数</th>
		<th>撤销率</th>
		<th>来电投诉</th>
		<th>其他</th>
		<th>回访</th>
		<th>门市</th>
		<th>旅游局</th>
		<th>微博</th>
		<th>CS邮箱</th>
		<th>点评</th>
		<th>网站</th>
		<th>当地质检</th>
		<th>变更</th>
		<th>APP</th>
	</tr>
	<c:forEach items="${dataList}" var="data">
		<tr class="zeroHide">
			<td>${data.businessUnit}</td>
			<td>${data.qcNum}</td>
			<td>${data.qcIngNum}</td>
			<td>${data.qcDoneNum}</td>
			<td>${data.cancelNum}</td>
			<td>${data.doneTotalNum}</td>
			<td><fmt:formatNumber value="${data.cancelRate}" type="percent" pattern="#0.0%"/></td>
			<td>${data.comeFrom1}</td>
			<td>${data.comeFrom2}</td>
			<td>${data.comeFrom3}</td>
			<td>${data.comeFrom4}</td>
			<td>${data.comeFrom5}</td>
			<td>${data.comeFrom6}</td>
			<td>${data.comeFrom7}</td>
			<td>${data.comeFrom8}</td>
			<td>${data.comeFrom9}</td>
			<td>${data.comeFrom10}</td>
			<td>${data.comeFrom11}</td>
			<td>${data.comeFrom12}</td>
		</tr>
	</c:forEach>
	<c:if test="${dataList.size() > 0}"> 
		<tr class="zeroHide">
			<td>${departmentTotal.businessUnit}</td>
			<td>${departmentTotal.qcNum}</td>
			<td>${departmentTotal.qcIngNum}</td>
			<td>${departmentTotal.qcDoneNum}</td>
			<td>${departmentTotal.cancelNum}</td>
			<td>${departmentTotal.doneTotalNum}</td>
			<td><fmt:formatNumber value="${departmentTotal.cancelRate}" type="percent" pattern="#0.0%"/></td>
			<td>${departmentTotal.comeFrom1}</td>
			<td>${departmentTotal.comeFrom2}</td>
			<td>${departmentTotal.comeFrom3}</td>
			<td>${departmentTotal.comeFrom4}</td>
			<td>${departmentTotal.comeFrom5}</td>
			<td>${departmentTotal.comeFrom6}</td>
			<td>${departmentTotal.comeFrom7}</td>
			<td>${departmentTotal.comeFrom8}</td>
			<td>${departmentTotal.comeFrom9}</td>
			<td>${departmentTotal.comeFrom10}</td>
			<td>${departmentTotal.comeFrom11}</td>
			<td>${departmentTotal.comeFrom12}</td>
		</tr>
	</c:if> 
</table>
</form>
</body>
</html>
