<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>金额概览</title>
<style type="text/css">
.common-box {
	border: 1px solid #8CBFDE;
	margin:0 0 10px 0;
}
.common-box-hd {
	margin-top: 1px;
	padding-left: 10px;
	background: #C6E3F1;
	color: #005590;
	font-size: 14px;
	font-weight: bold;
	height: 25px;
	line-height: 25px;
	border-bottom: 1px solid #8CBFDE;
	position: relative;
}
.common-box-hd span.title2 {
	cursor: pointer;
	display: inline-block;
	line-height: 25px;
	margin-right: 15px;
}
</style>
</HEAD>
<body>
<div class="common-box">
	<table width="100%" class="datatable">
		<tr>
			<th width="150" align="right">对客赔偿总额：</th>
			<td colspan="11">${complaintSolutionEntity.cash+complaintSolutionEntity.touristBook}　　（包括：现金${complaintSolutionEntity.cash}元 + 抵用券${complaintSolutionEntity.replaceBook}元 + 旅游券${complaintSolutionEntity.touristBook}元 + 礼品${complaintSolutionEntity.gift}元，<font color="red">赔偿总额 = 现金 + 旅游券</font>）</td>
		</tr>
		<tr>
			<th width="150" align="right">供应商承担总额：</th>
			<td colspan="11">
			${sum}</td>
		</tr>
		<tr>
			<th width="150" align="right">订单利润承担总额：</th>
			<td colspan="11">${shareSolutionEntity.orderGains }</td>
		</tr>
		<tr>
			<th width="150" align="right">员工承担总额：</th>
			<td colspan="11"><c:forEach items="${shareSolutionEntity.employeeShareList }" var="empShare">
					<table border="0" cellpadding="0" cellspacing="0" id="employee_table">
						<tr id="employee_row">
							<td>${empShare.name }</td>
							<td width="100px"></td>
							<td>${empShare.number } 元</td>
							<td align="right"></td>
						</tr>
					</table>
		</c:forEach></td>
		</tr>
		<tr>
			<th width="150" align="right">公司承担总额：</th>
			<td colspan="11">${shareSolutionEntity.special }</td>
		</tr>
		<tr>
			<th width="150" align="right">成本类型承担总额：</th>
			<td colspan="11"> <table border="0" cellpadding="0" cellspacing="0" id="quality_tool_table">
					<c:forEach items="${shareSolutionEntity.qualityToolList }" var="quality">
						<tr>
							<td>${quality.toolName }</td>
							<td>${quality.total } 元</td>
							<td></td>
						</tr>
					</c:forEach>
				</table></td>
		</tr>
		<tr>
			<th width="150" align="right">退转陪：</th>
			<td colspan="11">${shareSolutionEntity.refundToIndemnity}</td>
		</tr>
	</table>
</div>
</body>
</HTML>
<%@include file="/WEB-INF/html/foot.jsp"%>
