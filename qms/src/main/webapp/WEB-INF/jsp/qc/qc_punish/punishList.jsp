<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>处罚单信息</title>
<script type="text/javascript">
function delInnerPunish(innerId){
	var msg = "您确定删除编号["+innerId+"]的内部处罚单吗？";
	layer.confirm(msg, {icon: 3}, function(index){
	$.ajax( {
			url : 'qc/innerPunish/deleteInnerPunish',
			data : 	{innerId:innerId} ,
			type : 'post',
			dataType:'json',
			cache : false,
			success : function(result) {
				if(result)
				{
			    	if(result.retCode == "0")
					{
			    		parent.location.reload();
					 }else{
						layer.alert(result.resMsg, {icon: 2});
					}
			     }
			 }
		    });
	  });
}

function delOutPunish(outerId){
	var msg = "您确定删除编号["+outerId+"]的外部处罚单吗？";
	layer.confirm(msg, {icon: 3}, function(index){
	$.ajax( {
			url : 'qc/outerPunish/deleteOuterPunish',
			data : 	{outerId:outerId} ,
			type : 'post',
			dataType:'json',
			cache : false,
			success : function(result) {
				if(result)
				{
			    	if(result.retCode == "0")
					{
			    		parent.location.reload();
					 }else{
						layer.alert(result.resMsg, {icon: 2});
					}
			     }
			 }
		    });
	   });
}
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
</script>
</head>
<body style="margin: 0px;">
<div class="pici_search pd5" align="right">
	<input type="button" value="添加内部处罚单" title="添加内部处罚单" class="blue" style="cursor: pointer;"
		onclick="parent.openWin('添加内部处罚单', 'qc/innerPunish/${qcId}/${userFlag}/toAdd', 800, 490)">
</div>
<c:forEach items="${innerList}" var="inner" varStatus="st">
<div class="common-box">
<div class="common-box-hd">
	<span class="title2">内部处罚单 ${st.count}</span>
</div>
<table class="datatable" width="100%">
	<tr>
		<th width="90">处罚单号：</th><td>${inner.id}</td>
		<th width="90">被处罚人：</th><td>${inner.punishPersonName}</td>
		<th width="90">关联部门：</th><td class="left">${inner.depName}</td>
		<th width="90">关联责任单ID：</th><td>${inner.respId}</td>
		<th width="100">关联责任单类型：</th>
		<td>
			<c:if test="${inner.respType == 1}">内部责任单</c:if>
			<c:if test="${inner.respType == 2}">外部责任单</c:if>
		</td>
	</tr>
	<tr>
		<th width="90">关联岗位：</th><td>${inner.jobName}</td>
		<th width="90">连带责任：</th>
		<td>
			<c:if test="${inner.relatedFlag == 1}"><span style="color: green;">是</span></c:if>
			<c:if test="${inner.relatedFlag == 0}">否</c:if>
		</td>
		<th width="90">记分处罚：</th><td>${inner.scorePunish} 分</td>
		<th width="90">经济处罚：</th><td>${inner.economicPunish} 元</td>
		<th width="80">操作：</th>
		<td style="text-align: center;" width="130">
			<input type="button" class="blue" value="修改" 
			 onclick="parent.openWin('修改内部处罚单', 'qc/innerPunish/${inner.id}/${userFlag}/updateInner', 800, 490)">
			<input type="button" class="blue" value="删除" onclick="delInnerPunish('${inner.id}')">
		</td>
	</tr>
	<tr>
		<th width="90">处罚依据：</th>
		<td colspan="9">
		<table class="listtable" width="100%">
			<tr>
			    <th style="text-align: center; width: 100px;">处罚等级</th>
				<th style="text-align: center;">分级标准</th>
				<th style="text-align: center; width: 100px;">记分处罚</th>
				<th style="text-align: center; width: 100px;">经济处罚</th>
				<th style="text-align: center;">出处</th>
				<th style="text-align: center; width: 30px;">执行</th>
			</tr>
			<c:forEach items="${inner.ipbList}" var="ipb" varStatus="st">
			<tr>
				<td <c:if test="${ipb.punishStandard.redLineFlag == 1}">style='color: red;'</c:if>>${ipb.punishStandard.level}</td>
				<td class="left">${ipb.punishStandard.description}</td>
				<td>${ipb.punishStandard.scorePunish} 分</td>
				<td>${ipb.punishStandard.economicPunish} 元</td>
				<td class="left">${ipb.punishStandard.source}</td>
				<td>
					<c:if test="${ipb.execFlag == 1}"><span style="color: green;">是</span></c:if>
					<c:if test="${ipb.execFlag == 0}">否</c:if>
				</td>
			</tr>
			</c:forEach>
		</table>
		</td>
	</tr>
</table>
</div>
</c:forEach>

<div class="pici_search pd5" align="right">
	<input type="button" value="添加外部处罚单" title="添加外部处罚单" class="blue" style="cursor: pointer;"
		onclick="parent.openWin('添加外部处罚单', 'qc/outerPunish/${qcId}/${userFlag}/toAdd', 800, 490)">
</div>
<c:forEach items="${outerList}" var="outer" varStatus="st">
<div class="common-box">
<div class="common-box-hd">
	<span class="title2">外部处罚单 ${st.count}</span>
</div>
<table class="datatable" width="100%">
	<tr>
		<th width="80">处罚单号：</th><td>${outer.id}</td>
		<th width="90">供应商ID：</th><td>${outer.agencyId}</td>
		<th width="90">供应商名称：</th><td class="left">${outer.agencyName}</td>
		<th width="90">记分处罚：</th><td>${outer.scorePunish} 分</td>
	</tr>
	<tr>
		<%-- <th width="90">经济处罚：</th><td>${outer.economicPunish} 元</td> --%>
		<th width="90">关联责任单ID：</th><td>${outer.respId}</td>
		<th width="100">关联责任单类型：</th>
		<td>
			<c:if test="${outer.respType == 1}">内部责任单</c:if>
			<c:if test="${outer.respType == 2}">外部责任单</c:if>
		</td>
		<th width="80">操作：</th>
		<td style="text-align: center;" colspan="3">
			<input type="button" class="blue" value="修改"
			 onclick="parent.openWin('修改外部处罚单', 'qc/outerPunish/${outer.id}/${userFlag}/updateOuter', 800, 490)">
			<input type="button" class="blue" value="删除" onclick="delOutPunish('${outer.id}')">
		</td>
	</tr>
	<tr>
		<th width="90">处罚依据：</th>
		<td colspan="9">
		<table class="listtable" width="100%">
			<tr>
			    <th style="text-align: center; width: 100px;">处罚等级</th>
				<th style="text-align: center;">分级标准</th>
				<th style="text-align: center; width: 100px;">记分处罚</th>
				<th style="text-align: center; width: 100px;">经济处罚</th>
				<th style="text-align: center;">出处</th>
				<th style="text-align: center; width: 30px;">执行</th>
			</tr>
			<c:forEach items="${outer.opbList}" var="opb" varStatus="st">
			<tr>
				<td <c:if test="${opb.punishStandard.redLineFlag == 1}">style='color: red;'</c:if>>${opb.punishStandard.level}</td>
				<td class="left">${opb.punishStandard.description}</td>
				<td>${opb.punishStandard.scorePunish} 分</td>
				<td>${opb.punishStandard.economicPunish} 元</td>
				<td class="left">${opb.punishStandard.source}</td>
				<td>
					<c:if test="${opb.execFlag == 1}"><span style="color: green;">是</span></c:if>
					<c:if test="${opb.execFlag == 0}">否</c:if>
				</td>
			</tr>
			</c:forEach>
		</table>
		</td>
	</tr>
</table>
</div>
</c:forEach>
</body>
