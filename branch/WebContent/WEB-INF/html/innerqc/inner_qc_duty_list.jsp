<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<script language="javascript" src="${CONFIG.res_url}script/jquery/jquery-ui.min.js"></script>
<link rel="stylesheet" href="${CONFIG.res_url}script/jquery/css/jquery-ui.min.css"/>
<style type="text/css">
<!-- tr变黄色 -->
.trbg td{background:#ff9;}
.trbg1 td{background:#87CEFF;}
.listtable tr.yellowbg td{background:#FFFF99}
.listtable tr td.orange{background:orange;}
</style>
<script>
function openAddWindow(questionId) {
	parent.parent.openWindow('添加责任单', 'inner_qc_duty-toAdd?entity.questionId='+questionId, 380, 257);
}

function openEditWindow(id) {
	parent.parent.openWindow('修改责任单', 'inner_qc_duty-toUpdate?entity.id='+id, 380, 257);
}

function delInnerQcDuty(id) {
	if (confirm("确定要删除责任单[" + id + "]吗？")) {
		$.ajax({
			type: "POST",
			url: "inner_qc_duty-delete",
			data: {"entity.id":id},
			async: false,
			success: function(data) {
				location.reload();
			} 
		});
	}
}
</script>
</HEAD>
<BODY>
<div class="pici_search pd5" align="right">
	<input type="button" value="添加责任单" title="添加责任单" class="blue" style="cursor: pointer;"
		onclick="openAddWindow('${entity.questionId}')">
</div>
<table class="listtable" width="100%">
<tr>
	<th>责任单号</th>
	<th>一级责任部门</th>
	<th>二级责任部门</th>
	<th>责任岗位</th>
	<th>责任人</th>
	<th>记分值</th>
	<th>罚款金额</th>
	<th>操作</th>
</tr>
<c:forEach items="${dutyList}" var="v" varStatus="st"> 
<tr align="center">
	<td>${v.id}
		<!-- <a href="inner_qc_duty-toBill?entity.id=${v.id}" target="_blank" title="责任单[${v.id}]">${v.id}</a> -->
	</td>
	<td>${v.depName1}</td>
	<td>${v.depName2}</td>
	<td>${v.positionName}</td>
	<td>${v.respPersonName}</td>
	<td>${v.scoreValue}</td>
	<td>${v.fineAmount}</td>
	<td>
		<input type="button" class="pd5" value="编辑" title="编辑" style="cursor: pointer;"
			onclick="openEditWindow('${v.id}')">　
		<input type="button" class="pd5" value="删除" onclick="delInnerQcDuty('${v.id}')">
	</td>
</tr>
</c:forEach>
</table>
</BODY>
