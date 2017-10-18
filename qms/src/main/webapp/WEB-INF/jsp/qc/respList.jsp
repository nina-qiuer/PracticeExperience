<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>责任单信息</title>
<script type="text/javascript">
function openWinow(title, url, width, height,top,left) {
	parent.layer.open({
        type: 2,
        shade: false,
        maxmin: true,
        title: title,
        content: url,
       offset : [top ,left],
        area: [width+'px', height+'px']
    });
}

function delInnerResp(innerId){
	var msg = "您确定删除编号["+innerId+"]的内部责任单吗？";
	layer.confirm(msg, {icon: 3}, function(index){
	$.ajax( {
			url : 'qc/innerResp/deleteInnerResp',
			data : 	{innerId:innerId} ,
			type : 'post',
			dataType:'json',
			cache : false,
			success : function(result) {
				if(result)
				{
			    	if(result.retCode == "0")
					{
			    		parent.parent.location.reload();
					 }else{
						layer.alert(result.resMsg, {icon: 2});
					}
			     }
			 }
		    });
	 });
}
function delOutResp(outerId){
	var msg = "您确定删除编号["+outerId+"]的外部责任单吗？";
	layer.confirm(msg, {icon: 3}, function(index){
	$.ajax( {
			url : 'qc/outerResp/deleteOuterResp',
			data : 	{outerId:outerId} ,
			type : 'post',
			dataType:'json',
			cache : false,
			success : function(result) {
				if(result)
				{
			    	if(result.retCode == "0")
					{
			    		parent.parent.location.reload();
					 }else{
						layer.alert(result.resMsg, {icon: 2});
					}
			     }
			 }
		    });
	});
}
</script>
</head>
<body style="margin: 0px;">
	<div class="pici_search pd5" align="right">
		<input type="button" value="添加内部责任单" title="添加内部责任单" class="blue" style="cursor: pointer;"
			onclick="parent.openWin('添加内部责任单', 'qc/innerResp/${qpId}/${qcId}/toAddInner', 600, 440)">
	</div>
	<table class="listtable" width="100%">
	<tr>
		<th>内部<br>责任单号</th>
		<th>责任人</th>
		<th>责任部门</th>
		<th>责任岗位</th>
		<th>改进人</th>
		<c:if test="${userFlag == 1}">
			<th>改进人岗位</th>
			<th>异议<br>提出人</th>
			<th>异议<br>提出人岗位</th>
		</c:if>
		<th>责任经理</th>
		<th>责任<br>经理岗位</th>
		<th>责任总监</th>
		<th>责任<br>总监岗位</th>
		<th width="120px">操作</th>
	</tr>
	<c:forEach items="${innerList}" var="inner" varStatus="st"> 
	<tr>
		<td>${inner.id}</td>
		<td>${inner.respPersonName}</td>
		<td class="left">${inner.depName}</td>
		<td>${inner.jobName}</td>
		<td>${inner.impPersonName}</td>
		<c:if test="${userFlag == 1}">
			<td>${inner.impJobName}</td>
			<td>${inner.appealPersonName}</td>
			<td>${inner.appealJobName}</td>
		</c:if>
		<td>${inner.respManagerName}</td>
		<td>${inner.managerJobName}</td>
		<td>${inner.respGeneralName}</td>
		<td>${inner.generalJobName}</td>
		<td>
			<input type="button" class="blue" value="编辑" 
			 onclick="parent.openWin('修改内部责任单', 'qc/innerResp/${inner.id}/updateInner', 600, 440)">
			<input type="button" class="blue" value="删除" onclick="delInnerResp('${inner.id}')">
		</td>
	</tr>
	</c:forEach>
	</table>
	<div class="pici_search pd5" align="right">
		<input type="button" value="添加外部责任单" title="添加外部责任单" class="blue" style="cursor: pointer;"
			onclick="parent.openWin('添加外部责任单', 'qc/outerResp/${qpId}/${qcId}/toAddOuter', 600, 440)">
	</div>
	<table class="listtable" width="100%">
	<tr>
		<th>外部<br>责任单号</th>
		<th>供应商ID</th>
		<th>供应商名称</th>
		<th>责任人</th>
		<th>责任部门</th>
		<th>改进人</th>
		<c:if test="${userFlag == 1}">
			<th>异议<br>提出人</th>
		</c:if>
		<th>责任经理</th>
		<th>责任<br>经理岗位</th>
		<th>责任总监</th>
		<th>责任<br>总监岗位</th>
		<th width="120px">操作</th>
	</tr>
	<c:forEach items="${outerList}" var="outer" varStatus="st">
	<tr>
		<td>${outer.id}</td>
		<td>${outer.agencyId}</td>
		<td class="left">${outer.agencyName}</td>
		<td>${outer.respPersonName}</td>
		<td>${outer.depName}</td>
		<td>${outer.impPersonName}</td>
		<c:if test="${userFlag == 1}">
			<td>${outer.appealPersonName}</td>
		</c:if>
		<td>${outer.respManagerName}</td>
		<td>${outer.managerJobName}</td>
		<td>${outer.respGeneralName}</td>
		<td>${outer.generalJobName}</td>
		<td>
			<input type="button" class="blue" value="编辑" title="编辑" style="cursor: pointer;"
			 onclick="parent.openWin('修改外部责任单', 'qc/outerResp/${outer.id}/updateOuter', 600, 440)">
			<input type="button" class="blue" value="删除" onclick="delOutResp('${outer.id}')">
		</td>
	</tr>
	</c:forEach>
	</table>
</body>
</html>
