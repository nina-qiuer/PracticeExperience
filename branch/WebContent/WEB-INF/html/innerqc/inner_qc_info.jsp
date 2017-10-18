<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
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
</HEAD>
<BODY>
<div class="common-box">
<table width="100%" class="datatable">
	<tr>
		<th align="right" width="100" height="30">关联单据类型：</th>
		<td>
			<c:if test="${0 == entity.relBillType}">无关联</c:if>
			<c:if test="${1 == entity.relBillType}">订单</c:if>
			<c:if test="${2 == entity.relBillType}">产品</c:if>
			<c:if test="${3 == entity.relBillType}">Jira</c:if>
		</td>
		<th align="right" width="100" height="30">关联单据编号：</th>
		<td>
			<c:if test="${1 == entity.relBillType}">
				<a href="#" onclick="showOrder(${user.id},'${user.realName}',${entity.relBillNum})">${entity.relBillNum}</a>
			</c:if>
			<c:if test="${2 == entity.relBillType}">${entity.relBillNum}</c:if>
			<c:if test="${3 == entity.relBillType}">
				<a href="http://jira.tuniu.org/browse/${entity.relBillNum}" target="_blank">${entity.relBillNum}</a>
			</c:if>
		</td>
		<th align="right" width="100" height="30">公司损失：</th>
		<td>${entity.lossAmount} 元</td>
	</tr>
	<tr>
		<th align="right" width="100" height="30">质检事宜类型：</th>
		<td>${entity.typeName}</td>
		<th align="right" width="100" height="30">质检事宜概述：</th>
		<td colspan="3">${entity.qcEventSummary}</td>
	</tr>
	<tr>
		<th align="right" width="100" height="30">质检事宜详述：</th>
		<td colspan="5">${entity.qcEventDesc}</td>
	</tr>
	<tr>
		<th align="right" width="100" height="30">备注：</th>
		<td colspan="5">${entity.remark}</td>
	</tr>
	<tr>
		<th align="right" width="100" height="30">申请人：</th>
		<td>${entity.addPersonName}</td>
		<th align="right" width="100" height="30">申请时间：</th>
		<td><fmt:formatDate value="${entity.addTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		<th align="right" width="100" height="30">质检员：</th>
		<td><c:if test="${entity.dealPersonId > 0}">${entity.dealPersonName}</c:if></td>
	</tr>
	<tr>
		<th align="right" width="100" height="30">附件：</th>
		<td colspan="5">
		<c:forEach items="${entity.attachList}" var="attach">
			<a href="${attach.path}">${attach.name}</a>　　
		</c:forEach>
		</td>
	</tr>
</table>
</div>
</BODY>
