<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/html/head.jsp"%>
<!-- HTML head 元素已经在head.jsp中包含 -->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>质检报告</title>
<link type="text/css" href="${CONFIG.res_url}css/bui.css"
	rel="stylesheet" />
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
</head>
<body>
	<div class="top_crumbs">
		您当前所在的位置：<a href="#">投诉质检管理</a>>><span class="top_crumbs_txt">质检报告</span>
	</div>
	<div class="common-box">
		<div class="common-box-hd">
			<span class="title2">投诉情况说明单</span>
		</div>
		<table width="100%" class="datatable">
			<tr>
				<th width="100" align="right">订单号：</th>
				<td>${qc.orderId}</td>
				<th width="100" align="right">客户姓名：</th>
				<td>${qc.complaint.guestName}</td>
				<th width="100" align="right">人数：</th>
				<td>${qc.complaint.guestNum}</td>
			</tr>
			<tr>
				<th align="right">出发地：</th>
				<td>${qc.complaint.startCity}</td>
				<th align="right">出发时间：</th>
				<td colspan="3"><fmt:formatDate value="${qc.complaint.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
			<tr>
				<th align="right">线路：</th>
				<td colspan="5">${qc.complaint.route}</td>
			</tr>
			<tr>
				<th align="right">满意度：</th>
				<td colspan="5">${qc.complaint.score}%</td>
			</tr>
			<tr>
				<th height="36" align="right">售前客服：</th>
				<td>${qc.complaint.customer}</td>
				<th align="right">客服经理：</th>
				<td>${qc.complaint.customerLeader}</td>
				<th align="right">高级客服经理：</th>
				<td>${qc.complaint.serviceManager}</td>
			</tr>
			<tr>
				<th align="right">产品专员：</th>
				<td>${qc.complaint.producter}</td>
				<th align="right">产品经理：</th>
				<td>${qc.complaint.productLeader}</td>
				<th align="right">高级产品经理：</th>
				<td>${qc.complaint.seniorManager}</td>
			</tr>
			<tr>
					<th align="right">导游编号：</th>
					<td>${qc.complaint.guideId}</td>
					<th align="right">导游姓名：</th>
					<td>${qc.complaint.guideName}</td>
					<th align="right">导游电话：</th>
					<td>${qc.complaint.guideCall}</td>
				</tr>
			<tr>
				<th align="right">投诉级别：</th>
				<td colspan="5">${qc.complaint.level}级</td>
			</tr>
			<tr>
				<th align="right">投诉说明：</th>
				<td colspan="5">${qc.complaint.descript}</td>
			</tr>
			<tr>
				<th align="right">客户要求：</th>
				<td colspan="5">${qc.complaint.requirement}</td>
			</tr>
			<tr>
				<th align="right">受理人：</th>
				<td>${qc.complaint.dealName}</td>
				<th align="right">受理时间：</th>
				<td colspan="3"><fmt:formatDate
						value="${qc.complaint.buildDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
			<tr>
				<th align="right">相关供应商：</th>
				<td colspan="5">${qc.complaint.agencyName}</td>
			</tr>
		</table>
	</div>
	<div class="common-box">
		<div class="common-box-hd">
			<span class="title2">质检报告</span>
		</div>
		<table width="100%" class="datatable">
			<tr>
				<th width="100" align="right">问题描述：</th>
				<td colspan="7">
					<table style="border-collapse: collapse;">
						<c:forEach items="${complaintReasons }" var="reason">
						<tr>
							<td width="130">${reason.type}&nbsp;-&nbsp;${reason.secondType}</td>
							<td>${reason.typeDescript}</td>
						</tr>
						</c:forEach>
					</table>
				</td>
			</tr>
			<tr>
				<th width="100" align="right">核实情况：</th>
				<td colspan="7">${qc.verify}</td>
			</tr>
			<c:forEach items="${qcQuestions}" var="question" varStatus="st">
			<tr>
				<td colspan="8"><b>质检结论${st.count}</b></td>
			</tr>
			<tr>
				<th width="100" align="right">问题类型：</th><td>${question.bigClassName}—${question.smallClassName}</td>
				<th width="100" align="right">投诉问题等级：</th>
				<td colspan="5">
					<c:if test="${question.compLevel != -1}">${question.compLevel}&nbsp;级</c:if>
				</td>
			</tr>
			<tr>
				<th width="100" align="right">质检结论：</th>
				<td colspan="7">${question.conclusion}</td>
			</tr>
			<tr>
				<th width="100" align="right">记分等级：</th><td>${question.scoreLevel}</td>
				<th align="right">记分值：</th>
				<td>
					<c:choose>
						<c:when test="${question.scoreValue == 0}">无</c:when>
						<c:otherwise>${question.scoreValue}</c:otherwise>
					</c:choose>
				</td>
				<th align="right">记分对象1：</th><td>
                    <c:choose>
						<c:when test="${question.scoreTarget1 == ''}">无</c:when>
						<c:otherwise>${question.scoreTarget1}</c:otherwise>
					</c:choose>
				</td>
				<th align="right">记分对象2：</th><td>
				<c:choose>
						<c:when test="${question.scoreTarget2 == ''}">无</c:when>
						<c:otherwise>${question.scoreTarget2}</c:otherwise>
				</c:choose>
				</td>
			</tr>
			<c:if test="${question.scoreLevel == '一级乙等-红线'}">
			<tr>
				<th width="100" align="right">出发地：</th><td>${question.startCity}</td>
				<th align="right">目的地：</th><td>${question.endCity}</td>
				<th align="right">机票价格（元）：</th><td>${question.airfare}</td>
				<th align="right">产品价格（元）：</th><td>${question.productPrice}</td>
			</tr>
			</c:if>
			<c:forEach items="${question.trackers}" var="tracker" varStatus="vt">
			<tr>
				<th width="100" align="right">责任归属${vt.count}：</th><td>${tracker.responsibility}</td>
				<th align="right">执行岗位：</th><td>${tracker.position}</td>
				<th align="right">责任人：</th><td>
				<c:choose>
						<c:when test="${tracker.responsiblePerson == ''}">无</c:when>
						<c:otherwise>${tracker.responsiblePerson}</c:otherwise>
				</c:choose>				
				</td>
				<th align="right">改进人：</th><td>
				<c:choose>
						<c:when test="${tracker.improver == ''}">无</c:when>
						<c:otherwise>${tracker.improver}</c:otherwise>
				</c:choose>	
				</td>
			</tr>
			</c:forEach>
   			</c:forEach>
		</table>
	</div>
</body>
</html>
