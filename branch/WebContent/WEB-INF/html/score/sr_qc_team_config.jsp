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
	$('.thickbox').click(function() {
		$('#TB_closeAjaxWindow').html("<a href='javascript:void(0)' id='TB_closeWindowButton' title='Close'>×</a>");
		$('#TB_closeWindowButton').live('click',function(){
			tb_remove();
		});
	});
});

function delTeam(id, teamName) {
	if (confirm("确定要删除[" + teamName + "]吗？")) {
		$.ajax({
			type: "POST",
			url: "sr_qc_team-delete",
			data: {"entity.id":id},
			async: false,
			success: function(data) {
				location.href="sr_qc_team";
			} 
		});
	}
}
</script>
</HEAD>
<BODY>
<div class="top_crumbs">您当前所在的位置：<a href="#">记分管理系统</a>&gt;&gt;<span class="top_crumbs_txt">质检组配置</span></div>
<table class="listtable" width="700">
<thead>
	<th>组名</th>
	<th>成员</th>
	<th width="120">
		<input type="button" value="添加" title="添加质检组" class="thickbox blue" style="cursor: pointer;"
		alt="sr_qc_team-toAdd?placeValuesBeforeTB_=savedValues&TB_iframe=true&height=180&width=350&modal=false">
	</th>
</thead>
<tbody>
<c:forEach items="${teamList}" var="team">
	<tr>
		<td>${team.teamName}</td>
		<td>${team.persons}</td>
		<td align="center">
			<input type="button" value="修改" title="修改质检组" class="thickbox" style="cursor: pointer;"
			alt="sr_qc_team-toUpdate?entity.id=${team.id}&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=180&width=350&modal=false">　
			<input type="button" value="删除" onclick="delTeam('${team.id}', '${team.teamName}')">
		</td>
	</tr>
</c:forEach>
</tbody>
</table>
</BODY>
