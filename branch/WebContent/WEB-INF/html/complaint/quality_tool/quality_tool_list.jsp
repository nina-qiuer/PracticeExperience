<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<link href="${CONFIG.res_url}css/thickbox.css" rel="stylesheet" type="text/css" />
<title>基础配置</title>
<script type="text/javascript" src="${CONFIG.res_url}script/thickbox-compressed.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}script/jquery/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}script/complaint/complaint/quality_tool.js"></script>
<script type="text/javascript">
//标签页控制
function tabFuncdd(showId,navObj){ 
	$(".tab_part").hide();
	$("#pici_tab .menu_on").removeClass("menu_on");
	$(navObj).addClass("menu_on");
	$(showId).show();
	return false;
}
</script>
	<script type="text/javascript">
	//thinkbox弹出框控制js
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
	<style type="text/css">
		.datatable td table{border-collapse:collapse;}
		.datatable td td{border:0 none;}
	</style>

</HEAD>
<BODY>
	<div id="pici_tab" class="clear">
		<ul>
			<li class="menu_on">
			<s class="rc-l"></s><s class="rc-r"></s><a href="quality_tool">成本类型</a>
			</li>
		</ul>
	</div>
	<div id="001" class="tab_part">
		<div align="right" style="width: 770px;">
			<input class="thickbox pd5 mr10 blue" type="button" value="新增" alt=quality_tool-add?placeValuesBeforeTB_=savedValues&TB_iframe=true&height=280&width=400&modal=false />
		</div>
		<table class="datatable">
			<tr>
				<th width="50px;">ID</th>
				<th width="100px;">成本类型级别</th>
				<th width="100px;">成本类型类别</th>
				<th width="120px;">成本类型名称</th>
				<th width="60px;">状态</th>
				<th width="250px;">备注</th>
				<th width="50px;">操作栏</th>
			</tr>
			<c:forEach items="${toolList}" var="v">
			<tr align="center">
				<td>${v.id}</td>
				<td>
					<c:if test="${v.level==1}">公司级</c:if>
					<c:if test="${v.level==2}">部门级</c:if>
				<td>
					<c:if test="${v.type==1}">成本增加类</c:if>
					<c:if test="${v.type==2}">收入扣减类</c:if>
				</td>
				<td>${v.name}</td>
				<td><c:if test="${v.useFlag==1}">启用</c:if><c:if test="${v.useFlag==0}">废弃</c:if></td>
				<td>${v.remark}</td>
				<td>
					<a title="成本类型维护" href="quality_tool-add?id=${v.id}&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=320&width=400&modal=false" class="thickbox">
						<img src="${CONFIG.res_url}images/icon/default/reg_editbtn.png">
					</a>
				</td>
			</tr>
			</c:forEach>
		</table>
	</div>
	<div id="002" class="tab_part">
		
	</div>
	<%@include file="/WEB-INF/html/foot.jsp" %>
