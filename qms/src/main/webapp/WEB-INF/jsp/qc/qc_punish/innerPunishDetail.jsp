<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>
<html>
<head>

</head>
<body>
<table class="datatable" >
<tr>
<th align="right" width="100" height="30">处罚人名字：</th>
<td>
  ${punishBill.punishPersonName}
</td>
<th align="right" width="100" height="30">处罚人部门：</th>
<td>
 ${punishBill.depName}
</td>
<th align="right" width="100" height="30">处罚人岗位：</th>
<td>
${punishBill.jobName}
</td>
</tr>
<tr>
<th align="right" width="100" height="30">记分处罚：</th>
<td>
  ${punishBill.scorePunish}
</td>
<th align="right" width="100" height="30">经济处罚：</th>
<td colspan="3">
 ${punishBill.economicPunish}
</td>
</tr>
<c:if test="${useFlag==3}">
<tr>
		<th align="right" width="100" height="30">处罚事由</th>
		<td colspan="5">
			${punishBill.punishReason}
		</td>
</tr>
</c:if>
</table>
<table  class="listtable" >
	<tr>
	    <th>内部处罚单编号</th>
		<th>处罚依据编号</th>
		<th>处罚等级</th>
		<th>分级标准描述</th>
		<th>经济处罚</th>
		<th>记分处罚</th>
		<th>执行标记</th>
</tr>
	<c:forEach items="${basisList}" var="list" > 
	<c:if test="${list.execFlag ==1}">
		<tr align="center" style="color: red">
			<td>${list.ipbId}</td>
			<td>${list.psId}</td>
			<td>${list.punishStandard.level}</td>
			<td title="${list.punishStandard.description}">${list.punishStandard.description}</td>
			<td>${list.punishStandard.economicPunish}</td>
			<td>${list.punishStandard.scorePunish}</td>
			<td>执行</td>
		</tr>
	</c:if>
	<c:if test="${list.execFlag ==0}">
		<tr align="center">
			<td>${list.ipbId}</td>
			<td>${list.psId}</td>
			<td>${list.punishStandard.level}</td>
			<td title="${list.punishStandard.description}">${list.punishStandard.description}</td>
			<td>${list.punishStandard.economicPunish}</td>
			<td>${list.punishStandard.scorePunish}</td>
			<td>未执行</td>
		</tr>
	</c:if>
	</c:forEach>
</table>
</body>
</html>