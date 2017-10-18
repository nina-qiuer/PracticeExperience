<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<style type="text/css">

</style>
</head>
<body>
<table class="listtable">
	<tr>
		<th style="width:10%">投诉单号</th>
		<th style="width:10%">分配时间</th>
		<th style="width:10%">完成时间</th>
		<th>投诉事宜</th>
		<th style="width:30%">对客方案描述</th>
	</tr>
	<c:forEach items="${compDetailList }" var="v">
	<tr>
		<td style="text-align:center">
			<a href="${CONFIG.COMPLAINT_DETAIL_URL}${v.id}" target="_blank" id="td_${v.id}">${v.id}</a>
		</td>
		<td style="text-align:center"><fmt:formatDate value= "${v.assignTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		<td style="text-align:center"><fmt:formatDate value= "${v.finishDate }" pattern="yyyy-MM-dd"/></td>
		<td>
			<table style="width:100%">
			<c:forEach items="${v.reasonList }" var="reason" varStatus="st">
				<tr>
					<td class="shorten7" style="width:20%">	${reason.type}</td>
					<td class="shorten9" style="width:20%">${reason.secondType}</td>
					<td class="shorten69" style="width:60%">${reason.typeDescript}</td>
				</tr>
			</c:forEach>
			</table>
		</td>
		<td style="width:30%" class="shorten30">${v.compSolution.descript }</td>
	</tr>
	</c:forEach>
</table>
</body>
