<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<style type="text/css">
	.subTitle {
	    margin-top: 10px;
	    padding-left: 10px;
	    background: #C6E3F1 none repeat scroll 0% 0%;
	    color: #005590;
	    font-size: 14px;
	    font-weight: bold;
	    height: 25px;
	    line-height: 25px;
	    border-bottom: 1px solid #8CBFDE;
	    position: relative;
	}
</style>
<title>投诉改进报告</title>
</head>
<body>
<div class="main_div">
<c:if test="${cmpImproveList != null && fn:length(cmpImproveList)>0}">
<c:forEach items="${cmpImproveList}" var="cmpImprove">
	<table class="datatable"  style="border:1px solid lightblue">
		<tr>
			<td colspan="10"><div class="subTitle">改进报告信息</div></td>
		</tr>
		<tr>
			<th style="width:12%;color:red">改进报告单号：</th>
			<td style="width:10%">${cmpImprove.id}</td>
			<th style="width:8%">投诉单号：</th>
			<td style="width:8%">${cmpImprove.cmpId}</td>
			<th style="width:10%">订单号：</th>
			<td style="width:10%">
				<c:if test="${cmpImprove.ordId > 0}">
					${cmpImprove.ordId}
				</c:if>
			</td>
			<th style="width:12%">产品单号：</th>
			<td style="width:12%">
				<c:if test="${cmpImprove.prdId > 0}">
					${cmpImprove.prdId}
				</c:if>
			</td>
		</tr>
		<tr>
			<th>责任人：</th>
			<td>${cmpImprove.impPerson}</td>
			<th>处理人：</th>
			<td>${cmpImprove.handlePerson}</td>
			<th>报告状态：</th>
			<td>${cmpImprove.stateStr}</td>
			<th>流程处理到期时间：</th>
			<c:set var="nowDate" value="<%=System.currentTimeMillis()%>"></c:set>
			<c:choose>
				<c:when test="${impBill.state == 2 && nowDate - cmpImprove.handleEndTime.time >= 0 }">
					<td style="color:red"><fmt:formatDate value="${cmpImprove.handleEndTime}" pattern="yyyy-MM-dd"/></td>
				</c:when>
				<c:when test=""></c:when>
				<c:when test="${nowDate - cmpImprove.handleEndTime.time < 0}">
		  			<td><fmt:formatDate value="${cmpImprove.handleEndTime}" pattern="yyyy-MM-dd"/></td>
		  		</c:when>
		  		<c:otherwise>
		  			<td colspan="9"></td>
		  		</c:otherwise>
			</c:choose>
		</tr>
		<tr>
			<th>投诉事宜：</th>
			<td colspan="7"><p>${cmpImprove.cmpAffair}</p></td>
		</tr>
		<tr>
			<th>改进点：</th>
			<td colspan="7"><p>${cmpImprove.improvePoint}</p></td>
		</tr>
		<tr>
			<th>附件：</th>
			<td colspan="7">
				<c:forEach items="${cmpImprove.attachList}" var="upload" >
					<a href="${upload.path}" target="_blank">${upload.name}</a>
				</c:forEach>
			</td>
		</tr>
		<tr>
			<th>是否有责：</th>
			<td>
				<c:if test="${cmpImprove.isRespFlag == 0 }">
					有责
				</c:if>
				<c:if test="${cmpImprove.isRespFlag == 1 }">
					无责
				</c:if>
			</td>
			<th>其他责任人：</th>
			<td>${cmpImprove.otherPerson}</td>
			<th>责任供应商：</th>
			<td>${cmpImprove.otherAgencyName}</td>
			<th>预计改进完成时间：</th>
			<td><p><fmt:formatDate value="${cmpImprove.improveFinTime}" pattern="yyyy-MM-dd"/></p></td>
		</tr>
		<tr>
			<th>改进措施：</th>
			<td colspan="7"><p>${cmpImprove.improveMethod}</p></td>
		</tr>
		<tr>
			<th>预计改进结果：</th>
			<td colspan="7"><p>${cmpImprove.improveResult}</p></td>
		</tr>
		<tr>
			<th>备注：</th>
			<td colspan="7"><p>${cmpImprove.remark}</p></td>
		</tr>
	</table>
</c:forEach>
</c:if>
<c:if test="${cmpImproveList == null || fn:length(cmpImproveList) == 0}">
	<h2>该投诉单暂没有改进报告！</h2>
</c:if>
</div>
</body>
</html>