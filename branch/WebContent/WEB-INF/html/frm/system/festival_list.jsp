<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>

<HEAD>
<link href="${CONFIG.res_url}css/thickbox.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${CONFIG.res_url}script/thickbox-compressed.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}script/DatePickerNew/WdatePicker.js" ></script>
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

$(document).ready(function(){
	var delFlag = "${delFlag }";
	if ('Success' == delFlag) {
		search();
	}
});

function search() {
	$('#fesForm').attr("action", "${manageUrl}");
	$('#fesForm').submit();
}

function delFes(fesId) {
	$('#fesId').attr("value", fesId);
	$('#fesForm').attr("action", "${manageUrl}-delFes");
	$('#fesForm').submit();
}
</script>
</HEAD>
<BODY>
<div class="top_crumbs">您当前所在的位置：<a href="#">投诉质检系统</a> >> <span class="top_crumbs_txt">法定假日及补工日期</span></div>
<div id="pici_tab" class="clear">
  <ul>
    <li class="menu_on"><s class="rc-l"></s><s class="rc-r"></s><a href="#">法定假日及补工日期</a></li>
  </ul>
</div>
<div class="pici_search pd5 mb10">
<form id="fesForm" method="post">
<input type="hidden" id="fesId" name="fesId">
年份：
<select class="mr10" name="fYear" onchange="search()">
    <c:forEach items="${fYearList }" var="y" varStatus="st">
    	<option value="${y }" <c:if test="${y.equals(requestScope.fYear) }">selected</c:if>>${y }</option>
    </c:forEach>
</select>
<input title="新增" name="addFestival" type="button" class="thickbox pd5 mr10 blue" value="新增" 
	alt="festival-toAddFestival?&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=200&width=660&modal=false">
</form>
</div>
<table width="100%" class="listtable mb10">
	<tr>
		<th>节日名称</th>
		<th>放假日期</th>
		<th>补工日期</th>
		<th>操作</th>
	</tr>
	<c:forEach items="${fesVoList }" var="v" varStatus="st">
		<tr align="center">
			<td>${v.festivalName }</td>
			<td>${v.fesDateStr }</td>
			<td>${v.offsetDateStr }</td>
			<td><a href="javascript:delFes(${v.fesId })">删除</a></td>
		</tr>
	</c:forEach>
</table>
</BODY>
<%@include file="/WEB-INF/html/foot.jsp" %>
