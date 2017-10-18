<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<link href="${CONFIG.res_url}css/thickbox.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${CONFIG.res_url}script/thickbox-compressed.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}script/DatePickerNew/WdatePicker.js" ></script>  
<style type="text/css">
<!-- tr变黄色 -->
.trbg td{background:#ff9;}
.listtable tr.yellowbg td{background:#FFFF99}
.listtable tr td.orange{background:orange;}
</style>
<script>
$(function() {
	/*移除thickbox默认样式*/
	$('.thickbox').click(function() {
		$('#TB_closeAjaxWindow').html("<a href='javascript:void(0)' id='TB_closeWindowButton' title='Close'>×</a>");
		$('#TB_closeWindowButton').live('click',function(){
			tb_remove();
		});
	});
});

function onSearchClicked() {
	//$('#complaint_form').attr("action", "${manageUrl}");
	$('#warning_form').attr("action", "early_warning");
	$('#warning_form').submit();
}

function onResetClicked() {
    $(':input','#warning_form').not(':button, :submit, :reset, :hidden')  
    .val('').removeAttr('checked').removeAttr('selected');
}

function closeWindow() {
	tb_remove();
	onSearchClicked();
}
</script>
</HEAD>
<BODY>
<div class="top_crumbs">您当前所在的位置：<a href="#">预警系统</a>&gt;&gt;<span class="top_crumbs_txt">预警处理</span></div>
<form name="form" id="warning_form" method="post" enctype="multipart/form-data" action="early_warning">
<div class="pici_search pd5">
	<div class="pici_search pd5 mb10">
	<label class="mr10">预警时间：<input type="text" size="20" name="search.ewTimeBegin" value="${search.ewTimeBegin }" onclick="WdatePicker()" readOnly="readonly"/></label>
	<label class="mr10">至　<input type="text" size="20" name="search.ewTimeEnd" value="${search.ewTimeEnd }" onclick="WdatePicker()" readOnly="readonly"/> </label>
	<label class="mr10">预警等级：
	<select class="mr10" name="search.warningLv">
		<option value="" >全部</option>
		<option value="1" <c:if test ="${search.warningLv == 1}">selected="selected"</c:if>>红色预警</option>
		<option value="2" <c:if test ="${search.warningLv == 2}">selected="selected"</c:if>>橙色预警</option>
		<option value="3" <c:if test ="${search.warningLv == 3}">selected="selected"</c:if>>黄色预警</option>
		<option value="4" <c:if test ="${search.warningLv == 4}">selected="selected"</c:if>>蓝色预警</option>
	</select>
	</label>
	<label class="mr10">预警类型：
	<select class="mr10" name="search.warningType">
		<option value="" >全部</option>
		<option value="1" <c:if test ="${search.warningType == 1}">selected="selected"</c:if>>天气预警</option>
		<option value="2" <c:if test ="${search.warningType == 2}">selected="selected"</c:if>>突发事件</option>
		<option value="99" <c:if test ="${search.warningType == 99}">selected="selected"</c:if>>其他</option>
	</select>
	</label>
	
	<input type="button" value="查询" class="blue" onclick="onSearchClicked();">　
	<input type="button" value="重置" class="blue" onclick="onResetClicked();">　
	</div>
</div>
</form>
<table class="listtable" width="100%">
<tr>
	<th>预警等级</th>
	<th>预警编号</th>
	<th>预警类型</th>
	<th>预警内容</th>
	<th>添加人</th>
	<th>预警时间</th>
	<th>操作</th>
</tr>
<c:forEach items="${dataList }" var="v"  varStatus="st">
<tr align="center" class="trbg">
	<c:if test="${v.warningLv == 1}">
		<td style="background-color: red;">【红】</td>
	</c:if>
	<c:if test="${v.warningLv == 2}">
		<td style="background-color: darkorange;">【橙】</td>
	</c:if>
	<c:if test="${v.warningLv == 3}">
		<td style="background-color: yellow;">【黄】</td>
	</c:if>
	<c:if test="${v.warningLv == 4}">
		<td style="background-color: deepskyblue;">【蓝】</td>
	</c:if>
	<td>${v.id}</td>
	<td>
		<c:if test="${v.warningType == 1}">天气预警</c:if>
		<c:if test="${v.warningType == 2}">突发事件</c:if>
		<c:if test="${v.warningType == 99}">其他</c:if>
	</td>
	<td align="left" title="${v.content}">
		<c:choose>
			<c:when test="${fn:length(v.content) > 30}">
				<c:out value="${fn:substring(v.content, 0, 28)}......" />
			</c:when>
			<c:otherwise>
				<c:out value="${v.content}" />
			</c:otherwise>
		</c:choose>
	</td>
	<td>${v.addPerson}</td>
	<td><fmt:formatDate value="${v.addTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	<td>
		<form action="ew_order" method="post" target="ew_${v.id}">
			<input type="hidden" name="page.ewId" value="${v.id}">
			<input type="hidden" name="page.warningType" value="${v.warningType}">
			<input type="hidden" name="page.warningLv" value="${v.warningLv}">
			<input type="hidden" name="page.warningContent" value="${v.content}">
			<input type="button" value="修改" title="预警单[${v.id}]修改" class="thickbox" style="cursor: pointer;"
			alt="early_warning-toUpdateEarlyWarning?entity.id=${v.id}&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=200&width=400&modal=false">
			<input type="submit" value="处理" style="cursor: pointer;">
		</form>
	</td>
</tr>
</c:forEach>
</table>
<%@include file="/WEB-INF/html/pager.jsp" %>
