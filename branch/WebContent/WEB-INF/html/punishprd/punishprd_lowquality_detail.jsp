<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp"%>
<script type="text/javascript"
	src="${CONFIG.res_url}script/DatePickerNew/WdatePicker.js"></script>
<script type="text/javascript"
	src="${CONFIG.res_url}script/thickbox-compressed.js"></script>
<link href="${CONFIG.res_url}css/thickbox.css" rel="stylesheet"
	type="text/css" />
<style type="text/css">
<!--
tr变黄色 -->.trbg td {
	background: #ff9;
}

.trbg1 td {
	background: #87CEFF;
}

.listtable tr.yellowbg td {
	background: #FFFF99
}

.listtable tr td.orange {
	background: orange;
}
</style>

</HEAD>
<BODY>
	<div style="width: 800px; display: block;">
		<table
			style="width: 100%; border-collapse: collapse; border: 2px solid #99bbe8; margin: auto; table-layout: fixed;">
			<caption>低质量产品详情</caption>
			<tr>
				<td
					style="width: 10%; border: 2px solid #99bbe8; text-align: left; font-weight: bold;">产品编号:</td>
				<td style="width: 15%; border: 2px solid #99bbe8; text-align: left;">${vo.routeId
					}</td>
				<td
					style="width: 10%; border: 2px solid #99bbe8; text-align: left; font-weight: bold;">统计周期:</td>
				<td style="width: 15%; border: 2px solid #99bbe8; text-align: left;">${vo.month
					}月</td>
				<td
					style="width: 15%; border: 2px solid #99bbe8; text-align: left; font-weight: bold;">触红次数:</td>
				<td style="border: 2px solid #99bbe8; text-align: left;">${vo.touchRedCount==null?0:vo.touchRedCount
					}</td>
			</tr>
			<tr>
				<td
					style="width: 10%; borde: 2px solid #99bbe8; text-align: left; font-weight: bold;">线路名称:</td>
				<td colspan="5" style="border: 2px solid #99bbe8; text-align: left;">${vo.routeName
					}</td>
			</tr>
			<tr>
				<td
					style="width: 10%; border: 2px solid #99bbe8; text-align: left; font-weight: bold;">满意度:</td>
				<td style="width: 15%; border: 2px solid #99bbe8; text-align: left;">${vo.satisfation }%</td>
				<td
					style="width: 10%; border: 2px solid #99bbe8; text-align: left; font-weight: bold;">点评次数:</td>
				<td style="width: 15%; border: 2px solid #99bbe8; text-align: left;">${vo.remarkAmount
					}</td>
				<td
					style="width: 15%; border: 2px solid #99bbe8; text-align: left; font-weight: bold;">满意度排名</td>
				<td style="border: 2px solid #99bbe8; text-align: left;">当前排名（${vo.scoreRank
					}）/${vo.prdArea }方向后5%（${vo.areaPrdCnt }）</td>
			</tr>
			<tr>
				<td
					style="width: 10%; border: 2px solid #99bbe8; text-align: left; font-weight: bold;">产品部门:</td>
				<td style="width: 15%; border: 2px solid #99bbe8; text-align: left;">${vo.prdDepartment
					}</td>
				<td
					style="width: 10%; border: 2px solid #99bbe8; text-align: left; font-weight: bold;">产品经理:</td>
				<td style="width: 15%; border: 2px solid #99bbe8; text-align: left;">${vo.prdManager
					}</td>
				<td
					style="width: 15%; border: 2px solid #99bbe8; text-align: left; font-weight: bold;">产品专员</td>
				<td style="border: 2px solid #99bbe8; text-align: left;">${vo.prdOfficer
					}</td>
			</tr>
		</table>
		<h1>历史质量事件记录</h1>
		<c:forEach items="${vo.historyDateList}" var="historyDateList">
			<c:if test="${historyDateList.key ==1 }">
				<c:forEach items="${historyDateList.value}" var="touchRed">
					<p>
						<fmt:formatDate value="${touchRed}" pattern="yyyy年MM月dd日" />
						因触红投诉被下线
					</p>
				</c:forEach>
			</c:if>

			<c:if test="${historyDateList.key ==2 }">
				<c:forEach items="${historyDateList.value}" var="lowSatisfaction">
					<p>
						<fmt:formatDate value="${lowSatisfaction}" pattern="yyyy年MM月dd日" />
						因低满意度被下线
					</p>
				</c:forEach>
			</c:if>

			<c:if test="${historyDateList.key ==3 }">
				<c:forEach items="${historyDateList.value}" var="lowQuality">
					<p>
						<fmt:formatDate value="${lowQuality}" pattern="yyyy年MM月dd日" />
						因低质量被下线
					</p>
				</c:forEach>
			</c:if>
		</c:forEach>
	</div>
</BODY>