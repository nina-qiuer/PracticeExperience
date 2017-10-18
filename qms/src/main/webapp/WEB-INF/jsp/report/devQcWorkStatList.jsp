<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>研发质检工作量统计</title>
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
	$("#devQcWorkStatForm").attr("action", "report/devQcWorkStat/list");
	$("#devQcWorkStatForm").submit();
}

function resetForm() {
	$("input[type=text]", "#devQcWorkStatForm").val('');
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
<form id="devQcWorkStatForm" method="post" action="">
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
<div id="accordion">
<h3>研发质检工作量统计报表</h3>
<div align="right">
<table width="95%">
	<tr>
		<td>
			统计日期：<form:input path="dto.statDateBgn" onfocus="setMaxDate('statDateEnd')"  readonly="readonly" size="10"/> - 
			<form:input path="dto.statDateEnd" onfocus="setMinDate('statDateBgn')"  readonly="readonly" size="10"/>　
			<input type="button" class="blue" value="查询" onclick="search()">
			<input type="button" class="blue" value="重置" onclick="resetForm()">
		</td>
	</tr>
</table>
</div>
</div>
<table class="listtable">
	<tr>
		<th>质检员</th>
		<th>分配单数</th>
		<th>完成单数</th>
		<th>总完成单数</th>
		<th>撤销单数</th>
		<th>撤销率</th>
		<th>研发故障单<br>完成总数</th>
		<th>故障率</th>
		<th>上班天数</th>
		<th>日均<br>完成单数</th>
		<th>日均完成<br>研发故障单数</th>
		<th>故障S<br>问题个数</th>
		<th>故障A<br>问题个数</th>
		<th>故障B<br>问题个数</th>
		<th>故障C<br>问题个数</th>
		<th>非研发故障<br>问题个数</th>
		<th>及时<br>完成单数</th>
		<th>到期单数</th>
		<th>及时率</th>
		<th>完成率</th>
	</tr>
	<c:forEach items="${dataList}" var="data">
	<tr class="zeroHide">
		<td>${data.qcPerson}</td>
		<td>${data.distribNum}</td>
		<td>${data.doneNum}</td>
	    <td>${data.doneTotalNum}</td>
		<td>${data.cancelNum}</td>
		<td><fmt:formatNumber value="${data.cancelRate}" type="percent" pattern="#0.0%"/></td>
		<td>${data.problemTotalNum}</td>
		<td><fmt:formatNumber value="${data.problemRate}" type="percent" pattern="#0.0%"/></td>
		<td>${data.workDayNum}</td>
		<td><fmt:formatNumber value="${data.avgDailyDoneNum}" type="number" pattern="0.0"/></td>
		<td><fmt:formatNumber value="${data.avgDailyProblemNum}" type="number" pattern="0.0"/></td>
		<td>${data.sDevNum}</td>
		<td>${data.aDevNum}</td>
		<td>${data.bDevNum}</td>
		<td>${data.cDevNum}</td>
		<td>${data.noDevNum}</td>
		<td>${data.timelyDoneNum}</td>
		<td>${data.expireNum}</td>
		<td><fmt:formatNumber value="${data.timelyRate}" type="percent" pattern="#0.0%"/></td>
		<td><fmt:formatNumber value="${data.doneRate}" type="percent" pattern="#0.0%"/></td>
	</tr>
	</c:forEach>
	<tr class="zeroHide">
		<td>${workPro.qcPerson}</td>
		<td>${workPro.distribNum}</td>
		<td>${workPro.doneNum}</td>
		<td>${workPro.doneTotalNum}</td>
		<td>${workPro.cancelNum}</td>
		<td><fmt:formatNumber value="${workPro.cancelRate}" type="percent" pattern="#0.0%"/></td>
		<td>${workPro.problemTotalNum}</td>
		<td><fmt:formatNumber value="${workPro.problemRate}" type="percent" pattern="#0.0%"/></td>
		<td>${workPro.workDayNum}</td>
		<td>${workPro.avgDailyDoneNum}</td>
		<td>${workPro.avgDailyProblemNum}</td>
		<td>${workPro.sDevNum}</td>
		<td>${workPro.aDevNum}</td>
		<td>${workPro.bDevNum}</td>
		<td>${workPro.cDevNum}</td>
		<td>${workPro.noDevNum}</td>
		<td>${workPro.timelyDoneNum}</td>
		<td>${workPro.expireNum}</td>
		<td><fmt:formatNumber value="${workPro.timelyRate}" type="percent" pattern="#0.0%"/></td>
		<td><fmt:formatNumber value="${workPro.doneRate}" type="percent" pattern="#0.0%"/></td>
	</tr>
</table>
<br>
<b>名词解释：</b><br>
　　<b> 1. 统计期：</b>界面选取的时间范围筛选条件；<br>
　　<b> 2. 完成单数：</b>研发故障S +研发故障A +研发故障B +研发故障C +非研发故障完成单数；<br>
　　<b> 3. 总完成单数 ：</b>包含已完成+已撤销；<br>
　　<b> 4. 撤销率：</b>撤销单数/总完成单数；<br>
　　<b> 5. 研发故障单总数：</b>研发故障S +研发故障A +研发故障B +研发故障C；<br>
　　<b> 6. 故障率： </b>研发故障单总数/总完成单数；<br>
　　<b> 7. 及时率：</b>及时完成单数/到期单数(不包含撤销单数)；<br>
　　<b> 8. 及时完成单数：</b>质检到期时间在统计期内的所有质检单中 --- 在质检到期时间之前完成的质检单数(质检到期时间为分配时间+6个工作日
不包含撤销单数
)；<br>
　　<b> 9. 到期单数：</b>质检到期时间在统计期内的质检单数(不包含撤销单数)；<br>
　　<b>10. 完成率：</b>统计期内完成单数/统计期内分配的总单数(不包含撤销单数)；<br>
</form>
</body>
</html>
