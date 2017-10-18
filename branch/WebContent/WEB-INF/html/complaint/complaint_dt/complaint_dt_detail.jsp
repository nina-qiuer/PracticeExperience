<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp"%>
<link type="text/css" href="${CONFIG.res_url}css/bui.css"
	rel="stylesheet" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
.common-box {
	border: 1px solid #8CBFDE;
	margin: 0 0 10px 0;
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
<title>订单投诉详情</title>
</head>
<body>
	<div class="common-box">
		<table width="100%" class="listtable" style="TABLE-LAYOUT: fixed;">
			<tr align="center">
				<th width="50">投诉单号</th>
				<th width="60">订单号</th>
				<th width="50">公司理论赔付</th>
				<th width="50">实际赔付总额</th>
				<th width="50">分担总额</th>
				<th width="50">供应商承担金额</th>
				<th width="50">公司承担金额</th>
				<th width="50">质量工具承担金额</th>
				<th width="50">订单利润承担金额</th>
				<th width="50">质量成本实际使用额</th>
				<th width="50">实际收益</th>
				<th width="50">理论收益</th>
				<!-- <th width="50">节约金额</th>
				<th width="50">超额赔付率</th> -->
			</tr>
			<c:forEach items="${complaintDetailList }" var="dtDetail">
				<tr align="center">
					<td><a href="complaint-toBill?id=${dtDetail.id }"
						target="_blank" id="td_${dtDetail.id }">${dtDetail.id } </a></td>
					<td><a href="#" onclick="showOrder(${user.id},'${user.realName}',${dtDetail.order_id })">${dtDetail.order_id }</a></td>
					<td>${dtDetail.theoryPayout }</td>
					<td>${dtDetail.realPayout }</td>
					<td>${dtDetail.share_total }</td>
					<td>${dtDetail.supplier_total }</td>
					<td>${dtDetail.company_total }</td>
					<td>${dtDetail.quality_tool_total }</td>
					<td>${dtDetail.order_gains }</td>
					<td>${dtDetail.savePayout }</td>
					<td>${dtDetail.realGain }</td>
					<td>${dtDetail.theoryGain }</td>
					<%-- <td>${dtDetail.savePayout }</td>
					<td><c:if test="${dtDetail.above_scale!=null}">${dtDetail.above_scale }%</c:if></td> --%>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
