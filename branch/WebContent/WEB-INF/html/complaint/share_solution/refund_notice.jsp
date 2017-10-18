<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp"%>
<style type="text/css">
table {
	border-collapse:collapse;
	font-size:14px;
}
table caption {
	font: 25px/25px simhei;
	padding:20px 0;
}
table td, table th {
	border:#000 solid 1px;
}
</style>
</head>
<body>
<div style="text-align:left;"><a href="share_solution-refundNoticeDownload?complaintId=${complaintId}&agencyId=${agencyId}"><span>下载供应商偿通知单</span></a></div>
<table width="650" align="center" border="0" cellpadding="5" cellspacing="0">
  <caption>供应商赔偿通知单</caption>
  <tbody>
  <tr>
    <th width="77">订单号</th>
    <td width="121">${payoutDetailMap['orderId']}</td>
    <th width="85">客户姓名</th>
    <td width="122">${payoutDetailMap['guestName']}</td>
    <th width="73">人数</th>
    <td width="112">${payoutDetailMap['guestNum']}</td>
  </tr>
  <tr>
    <th>出游日期</th>
    <td>${payoutDetailMap['startDate']}</td>
    <th>售前客服</th>
    <td>${payoutDetailMap['customer']}</td>
    <th>产品经理</th>
    <td>${payoutDetailMap['productLeader']}</td>
  </tr>
  <tr>
    <th>出发地</th>
    <td>${payoutDetailMap['startCity']}</td>
    <td>&nbsp;</td>
    <th>航线</th>
    <td colspan="2">${payoutDetailMap['routeName']}</td>
  </tr>
  <c:forEach items="${payoutDetailMap['cpList'] }" var="cp" varStatus="st">
    <tr>
		<th width="65" align="right" style="font-size: 9pt;padding-right: 5px">投诉事宜${st.index+1 }</th>
		<td colspan="5" style="font-size: 9pt;padding-left: 5px">${cp['complaintInfo'] }</td>
	</tr>
	<tr>
		<th width="65" align="right" style="font-size: 9pt;padding-right: 5px">赔付理据${st.index+1 }</th>
		<td colspan="5" style="font-size: 9pt;padding-left: 5px">${cp['payBase'] }</td>
	</tr>
  </c:forEach>
  <tr>
    <th>供应商</th>
    <td>${payoutDetailMap['agencyName']}</td>
    <th>承担金额</th>
    <td>${payoutDetailMap['localCurrencyAmount']} 元
    	<c:if test="${payoutDetailMap['foreignCurrencyNumber']}">
	    	（${payoutDetailMap['foreignCurrencyNumber']} ${payoutDetailMap['foreignCurrencyName']}）
	    </c:if>
    </td>
    <th>盖章</th>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <th>受理人</th>
    <td colspan="2">${payoutDetailMap['dealName']}</td>
    <th>确认到期时间</th>
    <td colspan="2">${payoutDetailMap['finishTime']}</td>
  </tr>
  <tr>
    <th>备注</th>
    <td colspan="5" height="150">
    	<br>1.请在5个工作日内盖章确认赔款金额，并尽快回传025-86853999，若有异议可向我司供应商部门反馈（邮箱supplier@tuniu.com）。</br>
        <br>2.若5个工作日内未回传且未与供应商部门反馈，我司默认您已认可以上赔款金额，届时我司结算部门会直接扣款。谢谢配合。</br>
    </td>
  </tr>
</tbody></table>
</body>
</html>