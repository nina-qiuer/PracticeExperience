<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<style type="text/css">
/*搜索框表格样式*/
<style type="text/css">
/*basic*/
.clearfix:after {
	content: ".";
	display: block;
	height: 0;
	clear: both;
}

/*覆盖插件默认样式*/
.ui-autocomplete {
	max-height: 100px;
	overflow-y: auto;
	/* prevent horizontal scrollbar */
	overflow-x: hidden;
}

.ui-widget {
	font-family: Microsoft YaHei;
}

/*搜索框表格样式*/
.search {
	font-size: 12px;
	font-family: Microsoft YaHei;
}

/* .search td:nth-child(even) {
	text-align: left;
}

.search td:nth-child(odd) {
	text-align: right;
} */

.search td input[type=text] {
	width: 100px;
}

.listtable .orderable {
	background: #DFEAFB url('res/img/line.png') no-repeat right 5px  center;
	text-align: right;
	padding-right: 26px;
}
</style>
<title>综合满意度监控报表</title>
<script type="text/javascript">
var depArr = new Array();
$(function(){
	$('#accordion').accordion({
    	collapsible: true,
    	heightStyle: "content"
    });
});

</script>
</head>
<body>
<form id="dataForm" method="post" action="">
<%-- <input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}"> --%>
  <div id="accordion">
<h3>综合满意度达成率监控列表</h3>
<div align="right">
</div>
</div>
<table class="listtable">
	<tr>
		<th width="50px">排名</th>
		<th width="50px">事业部</th>
		<th width="50px">产品部</th>
		<th width="50px">产品组</th>
		<th width="50px">产品经理</th>
		<th width="50px">产品专员</th>
		<th width="50px">目标</th>
		<th width="50px">投诉订单数</th>
		<th width="50px">点评量</th>
		<th width="50px">投诉率</th>
		<th width="50px">满意度</th>
		<th width="50px" >综合满意度</th>
		<th width="50px" >达成率</th>
	</tr>
	<c:forEach items="${dataList}" var="satisfy" varStatus="statis">
	<tr>
		<td >${statis.index+1}</td>
	    <td >${satisfy.businessUnit}</td>
	    <td >${satisfy.prdDep}</td>
	    <td >${satisfy.prdTeam}</td>
	    <td >${satisfy.prdManager}</td>
	    <td >${satisfy.prdSpecialist}</td>
	    <td >${satisfy.targetValue}</td>
		<td >${satisfy.cmpNum}</td>
		<td >${satisfy.commentNum}</td>
		<td ><fmt:formatNumber value="${satisfy.cmpRate}" type="percent" pattern="#0.00%"/></td>
		<td ><fmt:formatNumber value="${satisfy.satisfaction}" type="percent" pattern="#0.00%"/></td>
		<td ><fmt:formatNumber value="${satisfy.compSatisfaction}" type="percent" pattern="#0.00%"/></td>
		<td ><fmt:formatNumber value="${satisfy.reacheRate}" type="percent" pattern="#0.00%"/></td>
	</tr>
	</c:forEach>
</table>
</form>
</body>
</html>
