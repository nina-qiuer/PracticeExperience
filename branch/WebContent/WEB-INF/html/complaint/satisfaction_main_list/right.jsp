<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${CONFIG.res_url}css/thickbox.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${CONFIG.res_url}script/thickbox-compressed.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}script/DatePickerNew/WdatePicker.js" ></script>  
<title>Insert title here</title>
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

function onSearchClicked() {
	$('#satisfaction_form').attr("action", "preSaleSatisfaction-toRight");
	$('#satisfaction_form').submit();
}

function onSearchExport() { 
	$('#satisfaction_form').attr("action", "preSaleSatisfaction-exports");
	$('#satisfaction_form').submit();
}

function onSearchExportToday() { 
	$('#satisfaction_form').attr("action", "preSaleSatisfaction-exportsToday");
	$('#satisfaction_form').submit();
}

function onResetClicked() {
    $(':input','#satisfaction_form').not(':button, :submit, :reset, :hidden')  
    .val('').removeAttr('checked').removeAttr('selected');
}
</script>
</head>
<body>
<form id="satisfaction_form" method="post" enctype="multipart/form-data" action="preSaleSatisfaction-execute">
<div class="pici_search pd5">
	<div class="pici_search pd5 mb10">
	<label class="mr10">月份：<input type="text" size="10" name="search.yearMonth" id="yearMonth"	value="${search.yearMonth }" onclick="WdatePicker({dateFmt:'yyyy-MM'})" readOnly="readonly"> </label>
	<label class="mr10">客服组：
	<select name="search.depId">
		<option value="">请选择</option>
		<c:forEach items="${departmentList}" var="v">
			<option value="${v.currentId}" <c:if test="${search.depId == v.currentId}">selected</c:if>>${v.selectedDeptName}</option>
		</c:forEach>
	</select> </label>
	<label class="mr10">客服经理： <input type="text" size="10" name="search.salermanagerName" value="${search.salermanagerName}" /> </label>
	<label class="mr10">售前客服： <input type="text" size="10" name="search.salerName" value="${search.salerName}" /> </label>
	<label class="mr10">订单号： <input type="text" size="10" name="search.orderId" value="${search.orderId}" /></label>
	<label class="mr10">客人星级：
	<select name="search.guestLevel">
		<option value="">请选择</option>
		<option value="0" <c:if test="${search.guestLevel == '0'}">selected</c:if>>注册会员</option>
		<option value="1" <c:if test="${search.guestLevel == '1'}">selected</c:if>>一星会员 </option>
		<option value="2" <c:if test="${search.guestLevel == '2'}">selected</c:if>>二星会员</option>
		<option value="3" <c:if test="${search.guestLevel == '3'}">selected</c:if>>三星会员</option>
		<option value="4" <c:if test="${search.guestLevel == '4'}">selected</c:if>>四星会员</option>
		<option value="5" <c:if test="${search.guestLevel == '5'}">selected</c:if>>五星会员</option>
		<option value="6" <c:if test="${search.guestLevel == '6'}">selected</c:if>>白金会员</option>
		<option value="7" <c:if test="${search.guestLevel == '7'}">selected</c:if>>钻石会员</option>
		<option value="-1" <c:if test="${search.guestLevel == '-1'}">selected</c:if>>未知</option>
	</select> </label>
	<br>
	<label class="mr10">订单联系人：<input type="text" size="10" name="search.contactName" id="customName" value="${search.contactName}"/>　</label>
	<label>出游日期：<input type="text" size="10" name="search.outDateBegin" id="out_date_begin"	value="${search.outDateBegin }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/> </label> 
	<label class="mr10">至 <input type="text" size="10" name="search.outDateEnd" id="out_date_end"value="${search.outDateEnd }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/> </label> 
	<label class="mr10">提前天数:
	<select name="search.dayNums">
		<option value="">请选择</option>
		<option value="1" <c:if test="${search.dayNums == '1'}">selected</c:if>>提前一天 </option>
		<option value="2" <c:if test="${search.dayNums == '2'}">selected</c:if>>提前两天</option>
		<option value="3" <c:if test="${search.dayNums == '3'}">selected</c:if>>提前三天</option>
		<option value="4" <c:if test="${search.dayNums == '4'}">selected</c:if>>提前四天</option>
		<option value="5" <c:if test="${search.dayNums == '5'}">selected</c:if>>提前五天</option>
	</select> </label>
	<label class="mr10">回访状态：
	<select name="search.commentStatus">
		<option value="">请选择</option>
		<option value="0" <c:if test="${search.commentStatus == '0'}">selected</c:if>>待点评</option>
		<option value="1" <c:if test="${search.commentStatus == '1'}">selected</c:if>>点评完成 </option>
		<option value="2" <c:if test="${search.commentStatus == '2'}">selected</c:if>>待再次回访</option>
		<option value="3" <c:if test="${search.commentStatus == '3'}">selected</c:if>>点评失败</option>
	</select> </label>
	<input type="button" value="查询" class="blue" name="" onclick="onSearchClicked();">　
	<input type="button" value="重置" class="blue" onclick="onResetClicked();">　
	<input type="button" value="导出" class="blue" name="" onclick="onSearchExport();"/>　
	<input type="button" value="今日工作导出" class="blue" name="" onclick="onSearchExportToday();"/>
	<input id="parent_salerName" type="hidden" value="${search.salerName }"/>
	<input id="parent_deptId" type="hidden" value="${parent_deptId }"/>
	</div>
</div>
<table id="tableDetails" class="listtable" width="100%">
	<thead>
		<th>订单号</th>
		<th>联系人姓名</th>
		<th>联系人电话</th>
		<th>客人星级</th>
		<th>电话呼出量</th>
		<th>线路名称</th>
		<th>售前客服</th>
		<th>客服经理</th>
		<th>出游日期</th>
		<th>满意度</th>
		<th>回访状态</th>
		<th>操作</th>
	</thead>
	<tbody>
		<c:forEach items="${dataList}" var="v"  varStatus="st"> 
		<tr align="center"  class="trbg" >
			<td><a href="#" onclick="showOrder(${user.id},'${user.realName}',${v.orderId})">${v.orderId}</a></td>
			<td>${v.contactName}</td> 
			<td>${v.contactTel}</td> 
			<td>
				<c:if test="${v.star_level=='-1'}">未知</c:if>
				<c:if test="${v.star_level=='0'}">注册会员</c:if>
				<c:if test="${v.star_level=='1'}">一星会员</c:if>
				<c:if test="${v.star_level=='2'}">二星会员</c:if>
				<c:if test="${v.star_level=='3'}">三星会员</c:if>
				<c:if test="${v.star_level=='4'}">四星会员</c:if>
				<c:if test="${v.star_level=='5'}">五星会员</c:if>
				<c:if test="${v.star_level=='6'}">白金会员</c:if>
				<c:if test="${v.star_level=='7'}">钻石会员</c:if>
			</td> 
			<td>${v.telCnt}</td> 
			<td title="${v.routeName }" align="left">
		    <c:choose>
				<c:when test="${fn:length(v.routeName) > 15}">
					<c:out value="${fn:substring(v.routeName, 0, 13)}......" />
				</c:when>
				<c:otherwise>
					<c:out value="${v.routeName}" />
				</c:otherwise>
			</c:choose>
			</td>
			<td>${v.salerName}</td> 
			<td>${v.salermanagerName}</td>
			<td><fmt:formatDate value="${v.startTime}" pattern="yyyy-MM-dd" /></td> 
			<td>${v.preSaleSatisfaction}</td>  
			<td>
				<c:if test="${v.commentStatus=='0'}">待点评</c:if>
				<c:if test="${v.commentStatus=='1'}">点评完成</c:if>
				<c:if test="${v.commentStatus=='2'}">待再次回访</c:if>
				<c:if test="${v.commentStatus=='3'}">点评失败</c:if>
			</td> 
			<td>
			<c:if test="${v.commentStatus!=1 || user.id==5148}">
				<a href="preSaleSatisfaction-operation?id=${v.id}&parent_salerName=${search.salerName }&parent_deptId=${parent_deptId }&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=485&width=1000&modal=false" 
				   title="售前客服满意度调查评分表（打分标准：4分，3分，2分，0分）" class="thickbox pd5 mr10" class="pd5 mr10">售前客服评分</a>
			</c:if>
			</td>
		</tr>
		</c:forEach>
	</tbody>
</table>
</form>
<%@include file="/WEB-INF/html/pager.jsp" %>
</body>
</html>