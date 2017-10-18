<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<link type="text/css" href="${CONFIG.res_url}css/bui.css" rel="stylesheet" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
<title>订单投诉详情</title>
</head>
<body>
<div class="common-box">
<table width="100%" class="listtable" style="TABLE-LAYOUT: fixed;">
	<tr align="center">
		<th width="60">投诉单号</th>
		<th>投诉事宜</th>
		<th width="50">赔偿<br>现金</th>
		<th width="50">赔偿<br>旅游券</th>
		<th width="50">赔偿<br>抵用券</th>
		<th width="50">赔偿<br>礼品总额</th>
		<th width="50">处理人</th>
	</tr>
	<c:forEach items="${complaintDetailList }" var="v">
	<tr align="center">
		<td>
			<a href="complaint-toBill?id=${v.id}" target="_blank" id="td_${v.id}"
				 <c:choose>
						<c:when test="${v.compSolution.id > 0}">
							style="color:red" title="已有对客解决方案"
						</c:when>
						<c:when test="${v.state==2 && v.inProcessState==2}">
							style="color:purple" title="已有跟进记录"
						</c:when>
						<c:when test="${v.deal>0}">
							style="color:green" title="已分配处理人"
						</c:when>
					</c:choose>
			>${v.id}
			</a>
		</td>
		<td style="padding: 0px;">
			<table width="100%" style="border-collapse: collapse;">
			<c:forEach items="${v.reasonList }" var="reason" varStatus="st">
				<tr>
					<td title="${reason.type }" width="60" style="<c:if test='${st.count == v.reasonList.size()}'>border-bottom: 0px;</c:if>border-left: 0px;border-top: 0px;margin: 0px;">
					<c:choose>
						<c:when test="${fn:length(reason.type) > 8}">
							<c:out value="${fn:substring(reason.type, 0, 7)}..." />
						</c:when>
						<c:otherwise>
							<c:out value="${reason.type}" />
						</c:otherwise>
					</c:choose>
					</td>
					<td title="${reason.secondType }" width="70" style="<c:if test='${st.count == v.reasonList.size()}'>border-bottom: 0px;</c:if>border-left: 0px;border-top: 0px;margin: 0px;">
					<c:choose>
						<c:when test="${fn:length(reason.secondType) > 10}">
							<c:out value="${fn:substring(reason.secondType, 0, 9)}..." />
						</c:when>
						<c:otherwise>
							<c:out value="${reason.secondType}" />
						</c:otherwise>
					</c:choose>
					</td>
					<td title="${reason.typeDescript }" style="<c:if test='${st.count == v.reasonList.size()}'>border-bottom: 0px;</c:if>border-left: 0px;border-top: 0px;margin: 0px;">
					<c:choose>
						<c:when test="${fn:length(reason.typeDescript) > 70}">
							<c:out value="${fn:substring(reason.typeDescript, 0, 69)}..." />
						</c:when>
						<c:otherwise>
							<c:out value="${reason.typeDescript}" />
						</c:otherwise>
					</c:choose>
					</td>
					<td title="${reason.descript }" width="100" style="<c:if test='${st.count == v.reasonList.size()}'>border-bottom: 0px;</c:if>border-left: 0px;border-top: 0px;border-right: 0px;margin: 0px;">
					<c:choose>
						<c:when test="${fn:length(reason.descript) > 16}">
							<c:out value="${fn:substring(reason.descript, 0, 15)}..." />
						</c:when>
						<c:otherwise>
							<c:out value="${reason.descript}" />
						</c:otherwise>
					</c:choose>
					</td>
				</tr>
			</c:forEach>
			</table>
		</td>
		<td>${v.compSolution.cash }</td>
		<td>${v.compSolution.touristBook }</td>
		<td>${v.compSolution.replaceBook }</td>
		<td>${v.compSolution.gift }</td>
		<td>${v.dealName }</td>
	</tr>
	</c:forEach>
</table>
<br/>
<b>颜色说明：</b><br/>
<p style="color:red">已有对客解决方案</p>
<p style="color:purple">已有跟进记录</p>
<p style="color:green">已分配处理人</p>
<p style="color:#3E649D">其他</p>

</div>
</body>
