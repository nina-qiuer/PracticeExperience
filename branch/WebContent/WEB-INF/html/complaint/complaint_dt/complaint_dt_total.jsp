<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="${CONFIG.res_url}css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript"
	src="${CONFIG.res_url}script/ztree/jquery.ztree.core-3.1.js"></script>

<script type="text/javascript">
	$(function() {
	});
</script>

<style type="text/css">
.datatable td {
	text-align: center;
}

.hidden {
	display: none;
}

img {
	vertical-align: middle;
}

img:hover {
	cursor: pointer;
}
</style>
</HEAD>
<body>
	<table width="100%" class="datatable">
		<tr>
			<th>
				<table width="100%" class="datatable">
					<tr>
						<th width="7%">二级部门</th>
						<th width="5%">理赔单数</th>
						<th width="5%">填写理论赔付数</th>
						<th width="7%">公司理论赔付</th>
						<th width="7%">实际赔付总额</th>
						<th width="7%">分担总额</th>
						<th width="7%">供应商承担金额</th>
						<th width="7%">公司承担金额</th>
						<th width="7%">质量工具承担金额</th>
						<th width="7%">订单利润承担金额</th>
						<th width="7%">质量成本实际使用额</th>
						<th width="7%">实际收益</th>
						<th width="7%">理论收益</th>
					</tr>
					<c:forEach items="${reportData }" var="complaintDtReportVo">
						<tr>
							<td width="7%"><c:choose>
									<c:when test="${complaintDtReportVo.dept_second=='售后投诉组'}">
										分销客服部
									</c:when>
									<c:otherwise>
										${complaintDtReportVo.dept_second}
									</c:otherwise>
								</c:choose></td>
							<td width="5%" class="cmpCountItem"><c:if
									test="${complaintDtReportVo.payout_num!=0}">
																			${complaintDtReportVo.payout_num}
																</c:if></td>
							<td width="5%" class="cmpCountItem">${complaintDtReportVo.theory_num}</td>
							<td width="7%" class="cmpCountItem"><c:if
									test="${complaintDtReportVo.theoryPayout!=0}">
																			${complaintDtReportVo.theoryPayout}
																</c:if></td>
							<td width="7%" class="cmpCountItem"><c:if
									test="${complaintDtReportVo.realPayout!=0}">
																		${complaintDtReportVo.realPayout}
														</c:if></td>
							<td width="7%" class="cmpCountItem"><c:if
									test="${complaintDtReportVo.share_total!=0}">
																			${complaintDtReportVo.share_total}
																</c:if></td>
							<td width="7%" class="cmpCountItem"><c:if
									test="${complaintDtReportVo.supplier_total!=0}">
																			${complaintDtReportVo.supplier_total}
																</c:if></td>
							<td width="7%" class="cmpCountItem"><c:if
									test="${complaintDtReportVo.company_total!=0}">
																		${complaintDtReportVo.company_total}
														</c:if></td>
							<td width="7%" class="cmpCountItem"><c:if
									test="${complaintDtReportVo.quality_tool_total!=0}">
																		${complaintDtReportVo.quality_tool_total}
														</c:if></td>
							<td width="7%" class="cmpCountItem"><c:if
									test="${complaintDtReportVo.order_gains!=0}">
																		${complaintDtReportVo.order_gains}
														</c:if></td>
							<td width="7%" class="cmpCountItem"><c:if
									test="${complaintDtReportVo.savePayout!=0}">
																		${complaintDtReportVo.savePayout}
														</c:if></td>
							<td width="7%" class="cmpCountItem"><c:if
									test="${complaintDtReportVo.realGain!=0}">
																		${complaintDtReportVo.realGain}
															</c:if></td>
							<td width="7%" class="cmpCountItem"><c:if
									test="${complaintDtReportVo.theoryGain!=0}">
																		${complaintDtReportVo.theoryGain}
															</c:if></td>
						</tr>
					</c:forEach>
				</table>
			</th>
		</tr>
	</table>
</body>
</HTML>
