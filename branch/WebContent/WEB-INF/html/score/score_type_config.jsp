<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<script type="text/javascript" src="${CONFIG.res_url}script/thickbox-compressed.js"></script>
<link href="${CONFIG.res_url}css/thickbox.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!-- tr变黄色 -->
.trbg td{background:#ff9;}
.trbg1 td{background:#87CEFF;}
.listtable tr.yellowbg td{background:#FFFF99}
.listtable tr td.orange{background:orange;}
</style>
<script>
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
</HEAD>
<BODY>
<div class="top_crumbs">您当前所在的位置：<a href="#">记分管理系统</a>&gt;&gt;<span class="top_crumbs_txt">记分类型配置</span></div>
<table class="listtable" width="500">
<thead>
	<th>记分性质</th>
	<th>记分类型</th>
	<th width="50">
		<input type="button" value="添加" title="添加记分类型" class="thickbox blue" style="cursor: pointer;"
		alt="score_type-toAdd?placeValuesBeforeTB_=savedValues&TB_iframe=true&height=112&width=270&modal=false">
	</th>
</thead>
<tbody>
	<c:forEach items="${scoreTypeList}" var="scMap">
		<c:forEach items="${scMap}" var="m">
		<tr>
			<td rowspan="${m.value.size()}">${m.key.name}</td>
			<c:forEach items="${m.value}" var="v" varStatus="st">
				<c:if test="${st.count > 1}"><tr></c:if>
				<td>${v.name}</td>
				<td align="center">
					<input type="button" value="编辑" title="修改记分类型" class="thickbox" style="cursor: pointer;"
					alt="score_type-toUpdate?entity.id=${v.id}&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=112&width=270&modal=false">
				</td>
				</tr>
			</c:forEach>
		</c:forEach>
	</c:forEach>
</tbody>
</table>
</BODY>
