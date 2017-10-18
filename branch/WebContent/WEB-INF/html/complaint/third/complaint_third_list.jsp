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
	$('#third_form').attr("action", "complaint-toThirdList");
	$('#third_form').submit();
}

function onResetClicked() {
    $(':input','#third_form').not(':button, :submit, :reset, :hidden')  
    .val('').removeAttr('checked').removeAttr('selected');
}

function addCompTh() {
	var compId = $("#compIdText").val();
	if (compId == '') {
		alert("投诉单号不能为空!");
		$("#compIdText").focus();
		return false;
	}

	var compCity = $("#compCityText").val();
	if (compCity == '') {
		alert("第三方城市不能为空!");
		$("#compCityText").focus();
		return false;
	}

	$.ajax({
		type: "POST",
		url: "complaint-addCompTh",
		data: $('#addCompTh_form').serialize(),
		async: false,
		success: function(data) {
			if (0 == data) {
				onResetClicked();
				onSearchClicked();
			} else {
				alert(data);
			}
		}
	});
}

function cancelCompTh(complaintId) {
	var msg = "确定要撤销投诉单[" + complaintId + "]的第三方信息吗？";
	if (confirm(msg)) {
		$.ajax({
			type: "POST",
			url: "complaint-cancelCompTh",
			data: {"complaintId":complaintId},
			async: false,
			success: function(data) {
				onSearchClicked();
			}
		});
	}
}
</script>
</HEAD>
<BODY>
<div class="top_crumbs">您当前所在的位置：<a href="#">投诉质检管理</a>&gt;&gt;<span class="top_crumbs_txt">第三方投诉查询管理</span></div>
<div class="pici_search pd5">
	<div class="pici_search pd5 mb10">
	<form name="form" id="third_form" method="post" enctype="multipart/form-data" action="complaint-toThirdList">
	<label class="mr10">订单号： <input type="text" size="10" name="search.orderId" value="${search.orderId}" /> </label>
	<label class="mr10">投诉单号： <input type="text" size="10" name="search.id" value="${search.id}" /> </label>
	出游日期：<input type="text" size="12" name="search.startTimeBegin" id="startTimeBegin" value="${search.startTimeBegin}" 
		         onclick="var startTimeEnd=$dp.$('startTimeEnd');WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,onpicked:function(){startTimeEnd.click();},maxDate:'#F{$dp.$D(\'startTimeEnd\')}'})" readonly="readonly" /> 至 
		  <input type="text" size="12" name="search.startTimeEnd" id="startTimeEnd" value="${search.startTimeEnd}" 
		         onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'startTimeBegin\')}'})" readonly="readonly" />　
	<label class="mr10">处理人： <input type="text" size="10" name="search.dealName" value="${search.dealName}" /> </label>
	<label class="mr10">投诉来源城市： <input type="text" size="10" name="search.compCity" value="${search.compCity}" /></label>
	<br>
	<label class="mr10">第三方投诉时间：<input type="text" size="20" name="search.compTimeThBgn" value="${search.compTimeThBgn}" onclick="WdatePicker()" readOnly="readonly"/></label>
	<label class="mr10">至　<input type="text" size="20" name="search.compTimeThEnd" value="${search.compTimeThEnd }" onclick="WdatePicker()" readOnly="readonly"/> </label>
	<input type="button" value="查询" class="blue" onclick="onSearchClicked();">　
	<input type="button" value="重置" class="blue" onclick="onResetClicked();">　
	<input type="button" value="补单" class="blue" onclick="$('#addCompThDiv').toggle()">
	</form>
	</div>
	<div id="addCompThDiv" class="pici_search pd5 mb10" style="display: none;">
	<form id="addCompTh_form" method="post" enctype="multipart/form-data" action="complaint-addCompTh">
	<label class="mr10">投诉单号：<input type="text" size="10" id="compIdText" name="complaintId">　</label>
	<label class="mr10">来源第三方城市：<input type="text" size="10" id="compCityText" name="compCity">　</label>
	<input type="button" id="sendButton" value="确认补单" class="blue" onclick="addCompTh()">
	</form>
	</div>
</div>
<table class="listtable" width="100%">
	<tr>
		<th>投诉单号</th>
		<th>订单号</th>
		<th>客人姓名</th>
		<th>出游日期</th>
		<th>第三方投诉时间</th>
		<th>投诉来源城市</th>
		<th>订单状态</th>
		<th>处理岗</th>
		<th>处理人</th>
		<th>投诉处理状态</th>
		<th>操作</th>
	</tr>
	<c:forEach items="${dataList }" var="v" varStatus="st">
	<tr align="center" class="trbg">
		<td>
			<c:if test="${v.thFinishFlag == 0}">
				<span style="color: red;">★</span>&nbsp;
			</c:if>
			<a href="complaint-toBill?id=${v.id}" target="_blank" id="td_${v.id}">${v.id}</a>
		</td>
		<td>
			<a href="#" onclick="showOrder(${user.id},'${user.realName}',${v.orderId})">${v.orderId}</a>
		</td>
		<td>${v.guestName}</td>
		<td><fmt:formatDate value="${v.startTime}" pattern="yyyy-MM-dd"/></td>
		<td><fmt:formatDate value="${v.compTimeTh}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		<td>${v.compCity}</td>
		<td>${v.orderState}</td>
		<td>${v.dealDepart}</td>
		<td>${v.dealName}</td>
		<td>
			<c:if test="${v.state==1}">投诉待处理</c:if>
			<c:if test="${v.state==2}">投诉处理中</c:if>
			<c:if test="${v.state==3}">投诉已待结</c:if>
			<c:if test="${v.state==4}">投诉已完成</c:if>
			<c:if test="${v.state==5}">投诉待指定（升级售后）</c:if>
			<c:if test="${v.state==6}">投诉待指定（提交售后填写分担方案）</c:if>
			<c:if test="${v.state==7}">投诉待指定（升级售前）</c:if>
			<c:if test="${v.state==9}">已撤销</c:if>
			<c:if test="${v.state==10}">投诉待指定（升级客服中心售后）</c:if>
		</td>
		<td>
			<a href="complaint-toThirdDeal?complaintId=${v.id }&keepThis=true&TB_iframe=true&height=350&width=1000" class="thickbox" title="处理">处理</a>　
			<a href="javascript:cancelCompTh(${v.id })">撤销</a>
		</td>
	</tr>
	</c:forEach>
</table>
<%@include file="/WEB-INF/html/pager.jsp" %>
