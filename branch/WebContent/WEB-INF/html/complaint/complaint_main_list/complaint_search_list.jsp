<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<link href="${CONFIG.res_url}css/thickbox.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${CONFIG.res_url}script/jquery/plugin/auto_complete/jquery.autocomplete.css"/>

<script type="text/javascript" src="${CONFIG.res_url}script/thickbox-compressed.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}script/DatePickerNew/WdatePicker.js" ></script>  
<script type="text/javascript" src="${CONFIG.res_url}script/jquery/plugin/auto_complete/jquery.autocomplete.js"></script>
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
	alert(1);
	$('#complaint_form').attr("action", "complaintSearch");
	$('#complaint_form').submit();
}

function searchTab(flag){ 
	//$('#complaint_form').attr("action", "${manageUrl}");
	$('#tab_flag').val(flag);
	$('#searchTab li').removeClass("menu_on");
	$('#complaint_form').attr("action", "complaintSearch");
	$('#complaint_form').submit();
}

function check_field(){
	var flag = 0;
	var orderId = $("input[name='search.orderId']").val();
	if(orderId==null||orderId==""||orderId=="undefined"){
		flag ++;
	}
	var id = $("input[name='search.id']").val();
	if(id=null||id==""||id=="undefined"){
		flag ++;
	}
	if(flag>=2){
		alert("订单号和订单号至少填写一个");
	}
	return flag;
}

function onSearchClicked() { 
	if(check_field()<2){
		$('#complaint_form').attr("action", "complaintSearch");
		$('#complaint_form').submit();
	}
}


</script>
</HEAD>
<BODY>
<div class="top_crumbs">您当前所在的位置：<a href="#">投诉质检管理</a>&gt;&gt;<span class="top_crumbs_txt">投诉单查询</span></div>
	<form name="form" id="complaint_form" method="post"  enctype="multipart/form-data" action="">
		<div id="pici_tab" class="clear">
			<ul>
				<li class="menu_on" onclick="searchTable(this.id)" id="menu_1">
					<s class="rc-l"></s><s class="rc-r"></s><a href="javascript:void(0)">投诉单查询</a>
				</li>
			</ul>
		</div>

		<div class="pici_search pd5">
			<div class="pici_search pd5 mb10">
				<label class="mr10">订&nbsp;&nbsp;单&nbsp;&nbsp;号： <input type="text" size="10" name="search.orderId" value="${search.orderId}" /> </label>
				<label class="mr10">投诉单号： <input type="text" size="10" name="search.id" value="${search.id}" /> </label>
				
				<input type="button" value="查询" class="blue" name="" onclick="onSearchClicked();"/>
			</div>
		</div>


<table class="listtable" width="100%">
	<thead>
		<th>投诉号</th>
		<th>关联订单id</th>
		<th>客人姓名</th>
		<th>客人等级</th>
		<th>特殊会员</th>
		<th>出发地/线路</th>
		<th>出发时间</th>
		<th>投诉时间</th>
		<th>最近重复投诉时间</th>
		<th>客服经理</th>
		<th>产品经理</th>
		<th>订单状态</th>
		<th>处理岗</th>
		<th>投诉来源</th>
		<th>投诉等级</th>
		<th>处理人</th>
		<th>投诉处理状态</th>
		<th>供应商名称</th>
		<th>供应商电话</th>
	</thead>
	<tbody>
		<c:forEach items="${dataList}" var="v"  varStatus="st"> 
		<tr align="center"  class="trbg" >
			<td>
				<a href="complaintSearch-toBill?id=${v.id}" target="_blank" id="td_${v.id}">${v.id}</a>
			</td>
			<td>
			<a href="#" onclick="showOrder(${user.id},'${user.realName}',${v.orderId})">${v.orderId}</a>
			</td> 
			<td>${v.guestName}</td>
			<td>${v.guestLevel}</td>
			<td style="color: red">
				<c:forEach items="${tagList}" var="tag"  varStatus="st"> 
					<c:if test="${v.custId==tag.cust_id}">${tag.tag_values}</c:if>
				</c:forEach>
			</td>
			<td>${v.startCity}-${v.routeId}</td> 
			<td><fmt:formatDate value="${v.startTime}" pattern="yyyy-MM-dd" /></td> 
			
			<td><fmt:formatDate value="${v.buildDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td> 
			<td><fmt:formatDate value="${v.repeateTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td> 
			<td>${v.customerLeader}</td> 
			<td>${v.productLeader}</td> 
			<td>${v.orderState}</td> 
			<td>${v.dealDepart}</td>
			<td>${v.comeFrom}</td> 
			<td>${v.level}</td>
			<td>${v.dealName}</td>
			<td><c:if test="${v.state==1}">投诉待处理</c:if>
				<c:if test="${v.state==2}">投诉处理中</c:if>
				<c:if test="${v.state==3}">投诉已待结</c:if>
				<c:if test="${v.state==4}">投诉已完成</c:if>
				<c:if test="${v.state==5}">投诉待指定（升级售后）</c:if>
				<c:if test="${v.state==6}">投诉待指定（提交售后填写分担方案）</c:if>
				<c:if test="${v.state==7}">投诉待指定（升级售前）</c:if>
				<c:if test="${v.state==9}">已撤销</c:if>
				<c:if test="${v.state==10}">投诉待指定（升级客服中心售后）</c:if>
			</td> 
			<td>${v.agencyName}</td>
			<td>${v.agencyTel}</td>
		</tr>
		</c:forEach>
	</tbody>
</table>
</form>



<script type="text/javascript">
$(document).ready(function(){
	
	$(document).keydown(function(event) {  //屏蔽回车提交
		  if (event.keyCode == 13) {
		    $('form').each(function() {
		      event.preventDefault();
		    });
		  }
		}); 
	
});

</script>

<%@include file="/WEB-INF/html/pager.jsp" %>
<%@include file="/WEB-INF/html/foot.jsp" %>
