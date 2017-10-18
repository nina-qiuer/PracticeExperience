<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<%@ taglib prefix="s" uri="/struts-tags" %> 

<style type="text/css">
<!-- tr变黄色 -->
.trbg td{background:#ff9;}

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

	$('#chkAll').click(function() {
		if($(this).attr("checked")) {
			$(this).parents('table').find(":checkbox[name='ids']").attr("checked", true);
		} else {
			$(this).parents('table').find(":checkbox[name='ids']").attr("checked", false);
		}
	});
	
});

$(function(){
	change_menu('${request.type}');
	//change_menu(2);
	$("#deal_form").submit(function(){
	   //alert($("#assignFlag").val());
	   //return check_select_people();
	}); 
});
//标签转换时更新标签样式
function change_menu(value) {
	$("li[id^='menu_']").each(function(){
		$(this).removeClass("menu_on");
	});
	$('#menu_'+value).addClass("menu_on");	
}
//列表标签表单提交
function searchTable(menuId){ 
	$('#complaint_form').attr("action", "taskReminder");
	$('#c_type').attr("value",menuId);
	$('#complaint_form').submit();
}

function onSearchClicked() { 
	$('#complaint_form').attr("action", "taskReminder");
	$('#complaint_form').submit();
}

function commitPage(currentPage,pageSize){
	$('#complaint_form').attr("action","taskReminder?1=1&page.currentPage="+currentPage+"&page.pageSize="+pageSize);
	$('#complaint_form').submit();
}

function onResetClicked() {
    $(':input','#complaint_form').not(':button, :submit, :reset, :hidden')  
    .val('').removeAttr('checked').removeAttr('selected');
}

</script>
</HEAD>
<BODY>
<div class="top_crumbs">您当前所在的位置：<a href="#">投诉质检管理</a>&gt;&gt;<span class="top_crumbs_txt">任务提醒列表</span></div>
	<form name="form" id="complaint_form" method="post" action="taskReminder">
		<input type="hidden" name="c_type" id="c_type" value="menu_${type}">
		<div id="pici_tab" class="clear">
			<ul>
				<li class="menu_on" onclick="searchTable(this.id)" id="menu_1">
					<s class="rc-l"></s><s class="rc-r"></s><a href="javascript:void(0)">待回呼</a>
				</li>
			</ul>
		</div>
		<div class="pici_search pd5">
			<div class="pici_search pd5 mb10">
			<label class="mr10">订&nbsp;&nbsp;单&nbsp;&nbsp;号： <input type="text" size="10" name="orderId" value="${orderId}" /> </label>
			<label class="mr10">投诉单号： <input type="text" size="10" name="cmpId" value="${cmpId}" /> </label>
			投诉等级：
			<select class="mr10" name="level">
					<option value="">全部</option>
					<option value="3" <c:if test="${level==3}">selected</c:if> >3级</option>
					<option value="2" <c:if test="${level==2}">selected</c:if> >2级</option>
					<option value="1" <c:if test="${level==1}">selected</c:if> >1级</option>
			</select>
				
				<label> 出游时间： <input type="text" size="20" name="startTimeBegin" id="startTimeBegin"	value="${startTimeBegin }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readOnly="readonly"/> </label> 
				<label class="mr10">至 <input type="text" size="20" name="startTimeEnd" id="startTimeEnd"value="${startTimeEnd }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readOnly="readonly"/> </label> 
			    
			    <label class="mr10">客服经理：<input type="text" size="10" name="customerLeader" value="${customerLeader}"/></label> 
				<label class="mr10">
				处理人： <input	type="text" size="10" name="dealName" id="dealName" value="${dealName}"/>
				</label>
				处理岗：<s:select list="dealDepartments" headerKey="" headerValue="请选择" name="dealDepart"/>
				
				<input type="button" value="查询" class="blue" onclick="onSearchClicked();"/>　
				<input type="button" value="重置" class="blue" onclick="onResetClicked();"/> 
			</div>
		</div>

<table class="listtable" width="98%">
	<thead>
		<th>投诉单号</th>
		<th>订单号</th>
		<th>客人姓名</th>
		<th>客人等级</th>
		<th>要求回呼时间</th>
		<th>出发时间</th>
		<th>投诉时间</th>
		<th>最近重复投诉时间</th>
		<th>客服经理</th>
		<th>订单状态</th>
		<th>处理岗</th>
		<th>投诉等级</th>
		<th>处理人</th>
		<th>投诉处理状态</th>
	</thead>
	<tbody>
		<c:forEach items="${taskList}" var="v"  varStatus="st"> 
		<tr align="center" class="trbg" >
			<td>
				<a href="complaint-toBill?id=${v.cmpId}" target="_blank" id="td_${v.cmpId}">${v.cmpId}</a>
			</td>
			<td>
			<c:if test="${v.orderId > 0}">
				<a href="#" onclick="showOrder(${user.id},'${user.realName}',${v.orderId})">${v.orderId}</a>
			</c:if>
			</td>
			<td>${v.guestName}</td>
			<td>${v.guestLevel}</td>
			<td>${v.callBackTime}</td> 
			<td><fmt:formatDate value="${v.startTime}" pattern="yyyy-MM-dd" /></td> 
			<td><fmt:formatDate value="${v.buildDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td> 
			<td><fmt:formatDate value="${v.repeateTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td> 
			<td>${v.customerLeader}</td> 
			<td>${v.orderState}</td> 
			<td>${v.dealDepart}</td>
			<td>${v.level}</td>
			<td>${v.dealName}</td>
			<td><c:if test="${v.cmpState==1}">投诉待处理</c:if>
				<c:if test="${v.cmpState==2}">投诉处理中</c:if>
				<c:if test="${v.cmpState==3}">投诉已待结</c:if>
				<c:if test="${v.cmpState==4}">投诉已完成</c:if>
				<c:if test="${v.cmpState==5}">投诉待指定（升级售后）</c:if>
				<c:if test="${v.cmpState==6}">投诉待指定（提交售后填写分担方案）</c:if>
				<c:if test="${v.cmpState==7}">投诉待指定（升级售前）</c:if>
				<c:if test="${v.cmpState==9}">已撤销</c:if>
				<c:if test="${v.cmpState==10}">投诉待指定（升级客服中心售后）</c:if>
			</td> 
		</tr>
		</c:forEach>
	</tbody>
</table>
</form>

<%@include file="/WEB-INF/html/pagerCommon.jsp" %>