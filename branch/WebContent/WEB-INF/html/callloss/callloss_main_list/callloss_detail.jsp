<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<link href="${CONFIG.res_url}css/thickbox.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${CONFIG.res_url}script/jquery/plugin/auto_complete/jquery.autocomplete.css"/>
<script type="text/javascript" src="${CONFIG.res_url}script/thickbox-compressed.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}script/jquery/plugin/auto_complete/jquery.autocomplete.js"></script>  
<script type="text/javascript" src="${CONFIG.res_url}script/jquery/jquery.1.4.2.js"></script>
<style type="text/css">
<!-- tr变黄色 -->
.trbg td{background:#ff9;}

.listtable tr.yellowbg td{background:#FFFF99}

.listtable tr td.orange{background:orange;}
</style>
</HEAD>
<BODY>
<div class="top_crumbs">您当前所在的位置：<a href="#">呼损查询</a>&gt;&gt;<span class="top_crumbs_txt">呼损列表</span></div>
<table class="listtable" width="98%">
	<thead>
		<th>客人主叫号</th>
		<th>客服ID</th>
		<th>通话情况</th>
		<th>状态</th>
		<th>创建时间</th>
	</thead>
	<tbody>
		<c:forEach items="${callLossDetailEntitys}" var="v"  varStatus="st"> 
		<tr align="center" class="trbg" >
		    <td>${v.callLossCallingId}</td>
			<c:if test="${v.salerId==0}">
			<td></td>
			</c:if>
			<c:if test="${v.salerId!=0}">
			<td>${v.salerName}(${v.salerId})</td>
			</c:if>
			<td>${v.content}</td>
			<c:if test="${v.success == 0}">
				<td>待处理</td>
			</c:if>
			<c:if test="${v.success == 1}">
				<td>已处理</td>
			</c:if>
			<td><fmt:formatDate value="${v.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
		</tr>
		</c:forEach>
	</tbody>
</table>
<%@include file="/WEB-INF/html/pagerNew.jsp" %>
<br>
<script type="text/javascript">
$(function(){
	/*移除thickbox默认样式*/
	$('.thickbox').click(function(){
		$('#TB_closeAjaxWindow').html("<a href='javascript:void(0)' id='TB_closeWindowButton' title='Close'>×</a>");
		$('#TB_closeWindowButton').live('click',function(){
			tb_remove();
		});
	});
});
</script>
<%@include file="/WEB-INF/html/foot.jsp" %>
