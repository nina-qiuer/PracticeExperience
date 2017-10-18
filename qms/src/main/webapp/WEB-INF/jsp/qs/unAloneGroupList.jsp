<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>牛人专线组团形式列表</title>
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
	$("#uagForm").attr("action", "qs/substdProductSnapshot/listUnAloneGroup");
	$("#uagForm").submit();
}

function resetForm() {
	$("input[type=text]", "#uagForm").val('');
	$("#pageNo").val('1');
}
</script>
</head>
<body>
<form id="uagForm" method="post" action="qs/substdProductSnapshot/listUnAloneGroup">
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
<div id="accordion">
<h3>牛人专线组团形式列表</h3>
<div>
<table width="100%">
	<tr>
		<td>　产品ID：<form:input path="dto.prdId" size="10"/></td>
		<td>事业部：<form:input path="dto.businessUnit" size="10"/></td>
		<td>产品部：<form:input path="dto.prdDep" size="10"/></td>
		<td>产品组：<form:input path="dto.prdTeam" size="10"/></td>
		<td>产品经理：<form:input path="dto.prdManager" size="10"/></td>
		<td>产品专员：<form:input path="dto.producter" size="10"/></td>
	</tr>
	<tr>
		<td>
			统计日期：<form:input path="dto.statisticDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly" size="10"/>
		</td>
		<td colspan="4">
			<div id="destCateSet">目的地大类：
				<form:radiobutton path="dto.destCate" value="全部" label="全部"/>
				<form:radiobuttons path="dto.destCate" items="${destStdList}" itemValue="destCate" itemLabel="destCate"/>
			</div>
		</td>
		<td align="center">
			<input type="button" class="blue" value="查询" onclick="searchResetPage()">
			<input type="button" class="blue" value="重置" onclick="resetForm()">
		</td>
	</tr>
</table>
</div>
</div>
<table class="listtable">
	<tr>
		<th>产品ID</th>
		<th>产品名称</th>
		<th>事业部</th>
		<th>产品部</th>
		<th>产品组</th>
		<th>产品经理</th>
		<th>产品专员</th>
		<th>目的地大类</th>
		<th>统计日期</th>
	</tr>
	<c:forEach items="${dto.dataList}" var="uag">
	<tr>
		<td>${uag.prdId}</td>
		<td class="shorten16">${uag.prdName}</td>
		<td>${uag.businessUnit}</td>
		<td>${uag.prdDep}</td>
		<td>${uag.prdTeam}</td>
		<td>${uag.prdManager}</td>
		<td>${uag.producter}</td>
		<td>${uag.destCate}</td>
		<td>${uag.statisticDate}</td>
	</tr>
	</c:forEach>
</table>
<%@ include file="/WEB-INF/jsp/common/pager.jsp" %>
</form>
</body>
</html>
