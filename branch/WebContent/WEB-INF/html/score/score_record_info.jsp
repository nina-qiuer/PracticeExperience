<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
</HEAD>
<BODY>
<table width="100%" class="datatable">
	<tr>
		<th align="right" width="80" height="30">质检日期</th>
		<td>${entity.qcDate }</td>
		<th align="right" width="80" height="30">质检专员</th>
		<td colspan="3">${entity.qcPerson }</td>
	</tr>
	<tr>
		<th align="right" width="80" height="30">订单号</th>
		<td>
		<c:if test="${entity.orderId > 0}">
			<a href="#" onclick="showOrder(${user.id},'${user.realName}',${entity.orderId})">${entity.orderId}</a>
		</c:if>
		</td>
		<th align="right" width="80" height="30">线路号</th>
		<td>
			<c:if test="${entity.routeId > 0}">${entity.routeId}</c:if>
		</td>
		<th align="right" width="80" height="30">Jira号</th>
		<td>
		<c:if test="${entity.jiraNum != ''}">
			<a href="http://jira.tuniu.org/browse/${entity.jiraNum }" target="_blank">${entity.jiraNum }</a>
		</c:if>
		</td>
	</tr>
	<c:if test="${user.depId==973 || user.id==5175 || user.id==7167 || user.id==4405}">
	<tr>
		<th align="right" width="80" height="30">投诉单号</th>
		<td>${entity.complaintId }</td>
		<th align="right" width="80" height="30">问题类型一</th>
		<td>${entity.bigClassName }</td>
		<th align="right" width="80" height="30">问题类型二</th>
		<td>${entity.smallClassName }</td>
	</tr>
	<tr>
		<th align="right" width="80" height="30">责任人</th>
		<td>${entity.responsiblePerson}</td>
		<th align="right" width="80" height="30">改进人</th>
		<td colspan="3">${entity.improver}</td>
	</tr>
	</c:if>
	<tr>
		<th align="right" width="80" height="30">一级部门</th>
		<td>${entity.depName1}</td>
		<th align="right" width="80" height="30">二级部门</th>
		<td>${entity.depName2}</td>
		<th align="right" width="80" height="30">岗位</th>
		<td>${entity.positionName}</td>
	</tr>
	<tr>
		<th align="right" width="80" height="30">记分对象1</th>
		<td>${entity.scoreTarget1 }</td>
		<th align="right" width="80" height="30">记分值1</th>
		<td>${entity.scoreValue1 }</td>
		<th align="right" width="80" rowspan="2">总记分</th>
		<td id="totalScore" rowspan="2">${entity.scoreValue1 + entity.scoreValue2}</td>
	</tr>
	<tr>
		<th align="right" width="80" height="30">记分对象2</th>
		<td>${entity.scoreTarget2 }</td>
		<th align="right" width="80" height="30">记分值2</th>
		<td>${entity.scoreValue2 }</td>
	</tr>
	<tr>
		<th align="right" width="80" height="30">记分性质</th>
		<td>${entity.scoreTypeName1 }</td>
		<th align="right" width="80" height="30">记分类型</th>
		<td colspan="3">${entity.scoreTypeName2 }</td>
	</tr>
	<tr>
		<th align="right" width="80">问题描述</th>
		<td colspan="7">${entity.description }</td>
	</tr>
</table>
</BODY>
