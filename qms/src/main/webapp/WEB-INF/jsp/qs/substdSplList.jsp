<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>销售期长不合格产品列表</title>
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
	$("#splForm").attr("action", "qs/substdProductSnapshot/listSubstdSpl");
	$("#splForm").submit();
}

function resetForm() {
	$("input[type=text]", "#splForm").val('');
	$("#pageNo").val('1');
}
</script>
</head>
<body>
<form id="splForm" method="post" action="qs/substdProductSnapshot/listSubstdSpl">
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
<div id="accordion">
<h3>销售期长不合格产品列表</h3>
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
		<th>最远团期</th>
		<th>销售期长<br>（天）</th>
		<th>标准销售期长<br>（天）</th>
		<th>差值<br>（天）</th>
	</tr>
	<c:forEach items="${dto.dataList}" var="spl">
	<tr>
		<td>${spl.prdId}</td>
		<td class="shorten10">${spl.prdName}</td>
		<td>${spl.businessUnit}</td>
		<td>${spl.prdDep}</td>
		<td>${spl.prdTeam}</td>
		<td>${spl.prdManager}</td>
		<td>${spl.producter}</td>
		<td>${spl.destCate}</td>
		<td>${spl.statisticDate}</td>
		<td>${spl.furthestGroupDate}</td>
		<td>${spl.salesPeriodLength}</td>
		<td>${spl.stdSalesPeriodLength}</td>
		<td>${spl.splDifValue}</td>
	</tr>
	</c:forEach>
</table>
<%@ include file="/WEB-INF/jsp/common/pager.jsp" %>
</form>
</body>
</html>
