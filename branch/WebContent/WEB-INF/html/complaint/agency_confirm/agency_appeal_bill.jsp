<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>供应商申诉处理</title>
<style type="text/css">
.common-box {
	border: 1px solid #8CBFDE;
	margin:0 0 10px 0;
}
</style>
<script type="text/javascript">
$(document).ready(function() {
	var succFlag = '${succFlag}';
    if (1 == succFlag) {
    	alert('操作成功！');
    	self.parent.tb_remove();
		parent.searchTable('menu_2');
    }
});

function doSubmit(flag) {
	$("#resultFlag").attr("value", flag);
	$("#agency_appeal_form").submit();
}
</script>
</HEAD>
<body>
<div class="common-box">
<form id="agency_appeal_form" method="post" action="agency_confirm-processAppeal">
<input type="hidden" name="appealInfo.id" value="${appealInfo.id }">
<input type="hidden" name="appealInfo.complaintId" value="${appealInfo.complaintId }">
<input type="hidden" name="appealInfo.agencyId" value="${appealInfo.agencyId }">
<input type="hidden" id="resultFlag" name="appealInfo.resultFlag">
<table width="100%" class="datatable">
    <tr>
		<th width="70" align="right" style="font-size: 9pt;padding-right: 5px;">订单号</th>
		<td style="font-size: 9pt;padding-left: 5px">${appealInfo.orderId }</td>
	</tr>
	<tr>
		<th align="right" style="font-size: 9pt;padding-right: 5px;">投诉单号</th>
		<td style="font-size: 9pt;padding-left: 5px">${appealInfo.complaintId }</td>
	</tr>
	<tr>
		<th align="right" style="font-size: 9pt;padding-right: 5px;">供应商ID</th>
		<td style="font-size: 9pt;padding-left: 5px">${appealInfo.agencyId }</td>
	</tr>
	<tr>
		<th align="right" style="font-size: 9pt;padding-right: 5px">申诉人</th>
		<td style="font-size: 9pt;padding-left: 5px">${appealInfo.appealer }</td>
	</tr>
	<tr>
		<th align="right" style="font-size: 9pt;padding-right: 5px">联系电话</th>
		<td style="font-size: 9pt;padding-left: 5px">${appealInfo.contactPhone }</td>
	</tr>
	<tr>
		<th align="right" style="font-size: 9pt;padding-right: 5px">联系邮箱</th>
		<td style="font-size: 9pt;padding-left: 5px">${appealInfo.contactEmail }</td>
	</tr>
	<tr>
		<th align="right" style="font-size: 9pt;padding-right: 5px">联系QQ</th>
		<td style="font-size: 9pt;padding-left: 5px">${appealInfo.contactQQ }</td>
	</tr>
	<tr>
		<th align="right" style="font-size: 9pt;padding-right: 5px">申诉内容</th>
		<td style="font-size: 9pt;padding-left: 5px">${appealInfo.appealContent }</td>
	</tr>
	<c:if test="${appealInfo.resultFlag != 0}">
	<tr>
		<th align="right" style="font-size: 9pt;padding-right: 5px">处理信息</th>
		<td style="font-size: 9pt;padding-left: 5px">${appealInfo.processInfo}</td>
	</tr>
	<tr>
		<th align="right" style="font-size: 9pt;padding-right: 5px">处理结果</th>
		<td style="font-size: 9pt;padding-left: 5px">
			<c:if test="${appealInfo.resultFlag == 1}"><span style="color: green;">受理</span></c:if>
			<c:if test="${appealInfo.resultFlag == -1}"><span style="color: red;">驳回</span></c:if>
		</td>
	</tr>
	</c:if>
</table>
</form>
</div>
</body>
</HTML>
