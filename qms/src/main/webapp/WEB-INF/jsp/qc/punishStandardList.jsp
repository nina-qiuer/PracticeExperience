<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>处罚标准列表</title>
<script type="text/javascript">
$(function(){
	$('#accordion').accordion({
    	collapsible: true,
    	heightStyle: "content"
    });
    
	$('#redLineFlagSet').buttonset();
	$('#redLineFlagSet').click(function() {
		search();
	});
	$('#punishObjSet').buttonset();
	$('#punishObjSet').click(function() {
		search();
	});
});

function search() {
	$("#searchForm").attr("action", "qc/punishStandard/list");
	$("#searchForm").submit();
}

function deletePs(id){
	var msg = "确定要删除此标准吗？";
	layer.confirm(msg, {icon: 3}, function(){
		$.ajax({
			type: "GET",
			url: "qc/punishStandard/"+id+"/delete",
			async: false,
			dataType: "json",
			success: function(data) {
				search();
			}
		});
	});
}
</script>
</head>
<body>
<form id="searchForm" method="post" action="qc/punishStandard/list">
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
<div id="accordion">
<h3>处罚标准列表</h3>
<div align="right">
<table width="90%">
<tr>
	<td align="right">处罚等级：</td>
	<td>
		<form:select path="dto.level" onchange="search()">
			<form:option value="">全部</form:option>
			<form:options items="${levelList}"/>
		</form:select>
	</td>
	<td align="right">红线标识：</td>
	<td>
		<div id="redLineFlagSet">
			<form:radiobutton path="dto.redLineFlag" value="-1" label="全部"/>
			<form:radiobutton path="dto.redLineFlag" value="1" label="红线"/>
			<form:radiobutton path="dto.redLineFlag" value="0" label="非红线"/>
		</div>
	</td>
	<td align="right">处理岗：</td>
	<td>
		<form:select path="dto.qcFlag" onchange="search()">
						<form:option value="-1" label="全部" />
						<form:option value="1" label="投诉质检" />
						<form:option value="2" label="研发质检" />
						<form:option value="3" label="运营质检" />
						<form:option value="4" label="内部质检" />
		</form:select>
	</td>
	<td align="right">处罚对象：</td>
	<td>
		<div id="punishObjSet">
			<form:radiobutton path="dto.punishObj" value="-1" label="全部"/>
			<form:radiobutton path="dto.punishObj" value="1" label="内部员工"/>
			<form:radiobutton path="dto.punishObj" value="2" label="供应商"/>
		</div>
	</td>
	<td>
		<input type="button" class="blue" value="添加" onclick="openWin('添加处罚标准', 'qc/punishStandard/toAdd', 600, 404)">
	</td>
</tr>
</table>
</div>
</div>
</form>

<table class="listtable">
	<tr>
		<th width="67">处罚等级</th>
		<th width="67">红线标识</th>
		<th>分级标准</th>
		<th width="67">处罚对象</th>
		<th width="77">经济处罚<br>（元）</th>
		<th width="77">记分处罚<br>（分）</th>
		<th>出处</th>
		<th width="67">使用方</th>
		<th width="110">操作</th>
	</tr>
	<c:forEach items="${psList}" var="ps">
	<tr>
		<td>${ps.level}</td>
		<td>
			<c:if test="${0 == ps.redLineFlag}">非红线</c:if>
			<c:if test="${1 == ps.redLineFlag}"><span style="color: red;">红线</span></c:if>
		</td>
		<td style="text-align: left;">${ps.description}</td>
		<td>
			<c:if test="${1 == ps.punishObj}">内部员工</c:if>
			<c:if test="${2 == ps.punishObj}"><span style="color: green;">供应商</span></c:if>
		</td>
		<td>${ps.economicPunish}</td>
		<td>${ps.scorePunish}</td>
		<td style="text-align: left;">${ps.source}</td>
		<td>
			<c:if test="${1 == ps.cmpQcUse}"><span style="color: blue;">投诉质检<br></span></c:if>
			<c:if test="${1 == ps.operQcUse}"><span style="color: blue;">运营质检<br></span></c:if>
			<c:if test="${1 == ps.devQcUse}"><span style="color: blue;">研发质检<br></span></c:if>
			<c:if test="${1 == ps.innerQcUse}"><span style="color: blue;">内部质检<br></span></c:if>
		</td>
		<td>
			<input type="button" class="blue" value="修改" onclick="openWin('修改处罚标准', 'qc/punishStandard/${ps.id}/toUpdate', 600, 404)">
			<input type="button" class="blue" value="删除" onclick="deletePs('${ps.id}')">
		</td>
	</tr>
	</c:forEach>
</table>
</body>
</html>
