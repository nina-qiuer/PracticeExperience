<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>赔偿通知单</title>
<style type="text/css">
.common-box {
	border: 2px solid #8CBFDE;
	margin:0 0 10px 0;
}
</style>
</HEAD>
<body>
<div class="common-box">
	<table width="100%" class="datatable">
		<tr>
			<th width="65" align="right" style="font-size: 9pt;padding-right: 5px;">订单号</th>
			<td style="font-size: 9pt;padding-left: 5px">${agencyPayoutBill['orderId'] }</td>
			<th width="65" align="right" style="font-size: 9pt;padding-right: 5px">客户姓名</th>
			<td style="font-size: 9pt;padding-left: 5px">${agencyPayoutBill['guestName'] }</td>
			<th width="65" align="right" style="font-size: 9pt;padding-right: 5px">出游人数</th>
			<td style="font-size: 9pt;padding-left: 5px">${agencyPayoutBill['guestNum'] }</td>
		</tr>
		<tr>
			<th width="65" align="right" style="font-size: 9pt;padding-right: 5px">出游日期</th>
			<td style="font-size: 9pt;padding-left: 5px">${agencyPayoutBill['startDate'] }</td>
			<th width="65" align="right" style="font-size: 9pt;padding-right: 5px">售前客服</th>
			<td style="font-size: 9pt;padding-left: 5px">${agencyPayoutBill['customer'] }</td>
			<th width="65" align="right" style="font-size: 9pt;padding-right: 5px">产品经理</th>
			<td style="font-size: 9pt;padding-left: 5px">${agencyPayoutBill['productLeader'] }</td>
		</tr>
		<tr>
			<th width="65" align="right" style="font-size: 9pt;padding-right: 5px">出发地</th>
			<td style="font-size: 9pt;padding-left: 5px">${agencyPayoutBill['startCity'] }</td>
			<th width="65" align="right" style="font-size: 9pt;padding-right: 5px">线路</th>
			<td colspan="3" style="font-size: 9pt;padding-left: 5px">${agencyPayoutBill['routeName'] }</td>
		</tr>
		<tr>
			<th width="65" align="right" style="font-size: 9pt;padding-right: 5px">供应商</th>
			<td style="font-size: 9pt;padding-left: 5px">${agencyPayoutBill['agencyName'] }</td>
			<th width="65" align="right" style="font-size: 9pt;padding-right: 5px">承担金额</th>
			<td colspan="3" style="font-size: 9pt;padding-left: 5px">
				人民币： ${agencyPayoutBill['localCurrencyAmount'] }
				<c:if test="${agencyPayoutBill['foreignCurrencyAmount'] > 0}">
				    <span>, ${agencyPayoutBill['foreignCurrencyName'] }: ${agencyPayoutBill['foreignCurrencyAmount'] }</span>
				</c:if>
			</td>
		</tr>
		<tr>
			<th width="65" align="right" style="font-size: 9pt;padding-right: 5px">受理人</th>
			<td style="font-size: 9pt;padding-left: 5px">${agencyPayoutBill['dealName'] }</td>
			<th width="65" align="right" style="font-size: 9pt;padding-right: 5px">赔付确认<br>到期时间</th>
			<td colspan="3" style="font-size: 9pt;padding-left: 5px">${agencyPayoutBill['finishTime'] }</td>
		</tr>
		<c:forEach items="${agencyPayoutBill['cpList'] }" var="cp" varStatus="st">
		    <tr>
				<th width="65" align="right" style="font-size: 9pt;padding-right: 5px">投诉事宜${st.index+1 }</th>
				<td colspan="5" style="font-size: 9pt;padding-left: 5px">${cp['complaintInfo'] }</td>
			</tr>
			<tr>
				<th width="65" align="right" style="font-size: 9pt;padding-right: 5px">赔付理据${st.index+1 }</th>
				<td colspan="5" style="font-size: 9pt;padding-left: 5px">${cp['payBase'] }</td>
			</tr>
		</c:forEach>
	</table>
</div>
</body>
</HTML>
<%@include file="/WEB-INF/html/foot.jsp"%>
