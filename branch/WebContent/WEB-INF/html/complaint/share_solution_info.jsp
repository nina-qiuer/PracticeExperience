<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>

<link href="${CONFIG.res_url}css/thickbox.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${CONFIG.res_url}/script/thickbox-compressed.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}/script/jquery/plugin/jquery.form.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}/script/DatePickerNew/WdatePicker.js" ></script>
<style type="text/css">
.common-box {
	border: 1px solid #8CBFDE;
	margin:0 0 10px 0;
}
</style>

<script type="text/javascript">
$(document).ready(function() {
	var succFlag = ${succFlag};
    if (1 == succFlag) {
    	alert('提交成功！');
    	parent.location.replace(parent.location.href);
    }
});

/**
 * 跳转至供应商赔款通知模板查看下载页面
 */
function supply_template(complaintId, agencyId) {
	var url = "share_solution-refundNoticeView?complaintId=" + complaintId + "&agencyId=" + agencyId;
	console.debug(url);
	openWindow(url);
}

function doSubmit(flag) {
	$("#saveOrSubmit").val(flag);
	$("#share_solution_form").submit();
}

</script>

</HEAD>
<BODY>
<c:if test="${customerTotal > 0}">
<div class="common-box">
	<span style="color: navy;">对客解决方案已提交，对客赔偿总额为：${customerTotal } 元</span>
</div>
</c:if>
<div class="common-box">
<table class="datatable" width="100%">
	<tr>
		<th width="156" align="right">分担总额：</th>
		<td><span id="share_total">${entity.total }</span> 元</td>
	</tr>
	<tr>
	    <th width="156" align="right">供应商承担赔偿金额：</th>
	    <td id="agencyTable">
	    <c:forEach items="${entity.supportShareList }" var="supportShare">
	    <c:if test="${null != supportShare}">
	    <table width="100%" border="1">
			<tr>
				<td colspan="2">
					${supportShare.name }　　${supportShare.code }　　
		        	 <c:if test="${supportShare.confirmState == 0 }">
		                 <span id="confirmStateText" style="color: blue;">未确认</span>
		             </c:if>
		             <c:if test="${supportShare.confirmState == 1 }">
		                 <span id="confirmStateText" style="color: green;">已确认</span>
		             </c:if>
		             <c:if test="${supportShare.confirmState == 2 }">
		                 <span id="confirmStateText" style="color: green;">到期默认</span>
		             </c:if>
		             <c:if test="${supportShare.confirmState == 3 }">
		                 <span id="confirmStateText" style="color: red;">已申诉</span>
		             </c:if>
		             <c:if test="${supportShare.confirmState == -1 && 1 == supportShare.nbFlag}">
		                 <span id="nbFlagText" style="color: green;">[NB供应商赔付确认]</span>
		             </c:if>
		        </td>
				<td><input type="button" onclick="supply_template('${entity.complaintId }','${supportShare.code }')" value="供应商赔偿通知单"></td>
			</tr>
			<tr>
				<th>投诉详情</th>
				<th>赔付理据</th>
				<th>承担金额</th>
			</tr>
			<c:forEach items="${supportShare.agencyPayoutList }" var="payout">
			<c:if test="${null != payout}">
			<tr>
			    <td>${payout.complaintInfo }</td>
			    <td class="payoutCl">${payout.payoutBase }</td>
			    <td>
			    	${payout.payoutNum} 元
				    <c:if test="${payout.foreignCurrencyNumber > 0}">
				    	（${payout.foreignCurrencyNumber} ${supportShare.foreignCurrencyName}）
				    </c:if>
			    </td>
		    </tr>
			</c:if>
			</c:forEach>
		 	<tr>
		 	    <th>备注</th>
			    <td colspan="2">${supportShare.remark }</td>
		    </tr>
		</table>
	    </c:if>
	    </c:forEach>
		</td>
	</tr>
	<tr>
		<th align="right">订单利润承担赔偿金额：</th>
		<td>${entity.orderGains } 元</td>
	</tr>
	<tr>
		<th align="right">员工承担赔偿金额：</th>
		<td>
		<c:forEach items="${entity.employeeShareList }" var="empShare">
		<table border="0" cellpadding="0" cellspacing="0" id="employee_table">
			<tr id="employee_row">
				<td>${empShare.name }</td>
				<td width="100px"></td>
				<td>${empShare.number } 元</td>
				<td align="right"></td>
			</tr>
		</table>
		</c:forEach>
		</td>
	</tr>
	<tr>
		<th align="right">公司承担：</th>
		<td>${entity.special } 元</td>
	</tr>
	<tr>
		<th align="right">成本类型：</th>
		<td>
			<table border="0" cellpadding="0" cellspacing="0" id="quality_tool_table">
			<c:forEach items="${entity.qualityToolList }" var="quality">
			<tr>
				<td>${quality.toolName }</td>
				<td>${quality.total } 元</td>
				<td></td>
			</tr>
			</c:forEach>
			</table>
		</td>
	</tr>
	<tr>
		<th align="right">退转赔：</th>
		<td>${entity.refundToIndemnity } 元</td>
	</tr>
	<c:if test="${'submitConfirm' == pageInfo}">
	<tr>
		<th>&nbsp;</th>
		<td>
			<form action="share_solution-submitShare">
				<input type="hidden" name="entity.id" value="${entity.id }">
				<input type="hidden" name="entity.complaintId" value="${entity.complaintId }">
				<input type="hidden" name="entity.submitFlag" value="1">
				<c:choose>
					<c:when test="${entity.total > 0}">
						<input type="hidden" name="entity.auditFlag" value="0">
					</c:when>
					<c:otherwise>
						<input type="hidden" name="entity.auditFlag" value="-1">
					</c:otherwise>
				</c:choose>
				<input class="pd5" type="submit" value="确认提交">　
				<input class="pd5" type="button" value="取消" onclick="parent.location.replace(parent.location.href)">
			</form>
		</td>
	</tr>
	</c:if>
</table>
</div>
</BODY>
