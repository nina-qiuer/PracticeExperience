<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>常规质检任务</title>
<script type="text/javascript">
$(function(){
	$('#accordion').accordion({
    	collapsible: true,
    	heightStyle: "content"
    });
    
    $('#jobTypeSet').buttonset();
	$('#jobTypeSet').click(function() {
		search();
	});
});

function search() {
	$("#searchForm").attr("action", "qc/qcBill/listNormalQcJob");
	$("#searchForm").submit();
}
</script>
</head>
<body>
<form id="searchForm" method="post" action="qc/qcBill/listNormalQcJob">
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
<div id="accordion">
<h3>质检处理列表</h3>
<div align="center">
	<div id="jobTypeSet">
		<form:radiobutton path="dto.qcJobType" value="qcPoint" label="售后发起"/>
		<form:radiobutton path="dto.qcJobType" value="companyPay" label="公司承担赔付"/>
		<form:radiobutton path="dto.qcJobType" value="agencyPay" label="供应商承担赔付"/>
		<form:radiobutton path="dto.qcJobType" value="oneTwoLevelCmp" label="一二级投诉"/>
		<form:radiobutton path="dto.qcJobType" value="repVoucherGift" label="赠送礼品或抵用券"/>
	</div>
</div>
</div>
</form>
<table class="listtable">
	<tr>
		<th>质检单号</th>
		<th>质检员</th>
		<th>产品编号</th>
		<th>团期</th>
		<th>产品品牌</th>
		<th>产品线目的地</th>
		<th>投诉时间</th>
		<th>投诉处理完成时间</th>
		<th>对客赔偿总额</th>
		<th>供应商赔付总额</th>
		<th>投诉级别</th>
		<th>一级部门</th>
		<th>产品经理</th>
		<th>客服经理</th>
		<th>操作</th>
	</tr>
	<c:forEach items="${dto.dataList}" var="qc">
	<tr>
		<td>${qc.id}</td>
		<td>${qc.qcPerson}</td>
		<td>${qc.prdId}</td>
		<td>${qc.groupDate}</td>
		<td>${qc.product.brandName}</td>
		<td>${qc.product.prdLineDestName}</td>
		<td><fmt:formatDate value="${qc.complaintBill.addTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		<td><fmt:formatDate value="${qc.complaintBill.finishTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		<td>${qc.complaintBill.indemnifyAmount}</td>
		<td>${qc.complaintBill.claimAmount}</td>
		<td>${qc.complaintBill.cmpLevel}</td>
		<td>${qc.product.businessUnitName}</td>
		<td>${qc.product.prdManager}</td>
		<td>${qc.orderBill.salerManagerName}</td>
		<td>
			<a href="javascript:void(0)" onclick="window.open('qc/qcBill/${qc.id}/toBill','_blank')">质检</a>
		</td>
	</tr>
	</c:forEach>
</table>
</body>
</html>
