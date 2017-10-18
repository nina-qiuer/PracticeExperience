<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<script type="text/javascript" src="${CONFIG.res_url}script/DatePickerNew/WdatePicker.js" ></script>
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

function onResetClicked() {
    $(':input','#search_form').not(':button, :submit, :reset, :hidden')  
    .val('').removeAttr('checked').removeAttr('selected');
    $("#pageNo").val(1);
}

function search() {
	$("#search_form").submit();
}

function onSearchClicked() {
	$("#pageNo").val(1);
	search();
}
</script>
</HEAD>
<BODY>
<div class="top_crumbs">您当前所在的位置：<a href="#">记分管理系统</a>&gt;&gt;<span class="top_crumbs_txt">我的记分</span></div>
<form name="form" id="search_form" method="post" action="score_record-toMyScoreRecord">
<div class="pici_search pd5">
	<div class="pici_search pd5 mb10">
	质检日期：<input type="text" size="10" name="page.qcDateBgn" id="qcDateBgn" value="${page.qcDateBgn}" 
		   onclick="var qcDateEnd=$dp.$('qcDateEnd');WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,onpicked:function(){qcDateEnd.click();},maxDate:'#F{$dp.$D(\'qcDateEnd\')}'})" readonly="readonly">&nbsp;至&nbsp;
		  <input type="text" size="10" name="page.qcDateEnd" id="qcDateEnd" value="${page.qcDateEnd}" 
		   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'qcDateBgn\')}'})" readonly="readonly" />
	<label class="mr10">　记分单号： <input type="text" size="10" id="srId" name="page.srId" value="${page.srId}"> </label>
	<label class="mr10">订单号： <input type="text" size="10" id="orderId" name="page.orderId" value="${page.orderId}" /> </label>
	<label class="mr10">线路号： <input type="text" size="10" id="routeId" name="page.routeId" value="${page.routeId}" /> </label>
	<label class="mr10">Jira号： <input type="text" size="10" id="jiraNum" name="page.jiraNum" value="${page.jiraNum}" /> </label>
	<label class="mr10"><input type="button" value="查询" class="blue" onclick="onSearchClicked()" style="cursor: pointer;"> </label>
	<label class="mr10"><input type="button" value="重置" class="blue" onclick="onResetClicked()" style="cursor: pointer;"></label>
	</div>
</div>
<table class="listtable" width="100%">
<thead>
	<th>记分单号</th>
	<th>订单号 /线路号/jira号</th>
	<th>一级部门</th>
	<th>二级部门</th>
	<th>记分性质</th>
	<th>记分类型</th>
	<th>记分对象1</th>
	<th>记分值1</th>
	<th>记分对象2</th>
	<th>记分值2</th>
	<th>总分</th>
	<th>质检人</th>
	<th>质检日期</th>
</thead>
<tbody>
	<c:forEach items="${page.srList}" var="v"  varStatus="st"> 
	<tr align="center">
		<td>
		<a href="score_record-toInfo?entity.id=${v.id}&keepThis=true&TB_iframe=true&height=300&width=600" class="thickbox" title="记分单[${v.id}]">
		${v.id }</a>
		</td>
		<td>
			<c:choose>
				<c:when test="${v.orderId > 0}">
					<a href="#" onclick="showOrder(${user.id},'${user.realName}',${v.orderId})">${v.orderId}</a>
				</c:when>
				<c:when test="${v.routeId > 0}">${v.routeId}</c:when>
				<c:otherwise>
					<a href="http://jira.tuniu.org/browse/${v.jiraNum}" target="_blank">${v.jiraNum}</a>
				</c:otherwise>
			</c:choose>
		</td>
		<td>${v.depName1 }</td>
		<td>${v.depName2 }</td>
		<td>${v.scoreTypeName1}</td>
		<td>${v.scoreTypeName2}</td>
		<td>${v.scoreTarget1}</td>
		<td>${v.scoreValue1}</td>
		<td>${v.scoreTarget2}</td>
		<td>${v.scoreValue2}</td>
		<td>${v.scoreValue1 + v.scoreValue2}</td>
		<td>${v.qcPerson}</td>
		<td><fmt:formatDate value="${v.qcDate}" pattern="yyyy-MM-dd"/></td>
	</tr>
	</c:forEach>
</tbody>
</table>
<table width="100%">
<tr>
<td><%@include file="/WEB-INF/html/pager2.jsp" %></td>
<td align="right" valign="bottom"">当前总分为：${page.totalValue}　　</td>
</tr>
</table>
</form>
</BODY>
