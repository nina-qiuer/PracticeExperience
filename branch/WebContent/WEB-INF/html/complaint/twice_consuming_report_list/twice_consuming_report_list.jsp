<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<link href="${CONFIG.res_url}css/thickbox.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${CONFIG.res_url}script/jquery/plugin/auto_complete/jquery.autocomplete.css"/>
<link rel="stylesheet" type="text/css" href="${CONFIG.res_url}script/jquery/showLoading/css/showLoading.css"/>

<script type="text/javascript" src="${CONFIG.res_url}script/thickbox-compressed.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}script/DatePickerNew/WdatePicker.js" ></script>  
<script type="text/javascript" src="${CONFIG.res_url}script/jquery/plugin/auto_complete/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}script/jquery/showLoading/js/jquery.showLoading.min.js"></script>
<style type="text/css">
<!-- tr变黄色 -->
.trbg td{background:#ff9;}

.listtable tr.yellowbg td{background:#FFFF99}
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

function checkField(){
	if($("#complaint_date_begin").val()==""){
		alert("请选择投诉开始时间！");
		$("#complaintDateBegin").click();
		return false;
	}
	if($("#complaint_date_end").val()==""){
		alert("请选择投诉结束时间！");
		$("#complaintDateEnd").click();
		return false;
	}
	if($("#complaint_date_end").val()<$("#complaint_date_begin").val()){
		alert("请选择投诉结束时间不能早于开始时间！");
		$("#complaintDateEnd").click();
		return false;
	}
	if($("#twice_consuming_date_begin").val()==""){
		alert("请选择再次消费开始时间！");
		$("#twiceConsumingDateBegin").click();
		return false;
	}
	if($("#twice_consuming_date_begin").val()<$("#complaint_date_end").val()){
		alert("再次消费开始时间不能早于投诉结束时间！");
		$("#twiceConsumingDateBegin").click();
		return false;
	}
	if($("#twice_consuming_date_end").val()==""){
		alert("请选择再次消费结束时间！");
		$("#twiceConsumingDateEnd").click();
		return false;
	}
	if($("#twice_consuming_date_end").val()<$("#twice_consuming_date_begin").val()){
		alert("再次消费结束时间不能早于开始时间！");
		$("#twiceConsumingDateEnd").click();
		return false;
	}
	return true;
}


function onSearchClicked() {
	if(checkField()){
		if(confirm("日期间隔过大可能造成数据加载慢，是否继续?")){
			jQuery('#wholePage').showLoading();
			$('#twiceConsumingReport_form').attr("action", "twiceConsumingReport");
			$('#twiceConsumingReport_form').submit();
		}
	}
}
</script>
</HEAD>	
<BODY>
<div class="top_crumbs">您当前所在的位置：<a href="#">统计报表</a>&gt;&gt;<span class="top_crumbs_txt">老客户消费率</span></div>
<div id="wholePage">
	<form name="form" id="twiceConsumingReport_form" method="post" onSubmit=""
		enctype="multipart/form-data" action="twiceConsumingReport-execute">

		<div class="pici_search pd5">

			<div class="pici_search pd5 mb10">
			<label id="complaintDateBegin"> 投诉日期： <input type="text" size="20" name="search.startBuildDate" id="complaint_date_begin"	value="${search.startBuildDate }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd 00:00:00'})" readOnly="readonly"/> </label> 
			<label id="complaintDateEnd"  class="mr10">至 <input type="text" size="20" name="search.endBuildDate" id="complaint_date_end"value="${search.endBuildDate }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd 23:59:59'})" readOnly="readonly"/> </label> 
				
			<label id="twiceConsumingDateBegin"> 再次消费日期： <input type="text" size="20" name="search.twiceConsumingDateBegin" id="twice_consuming_date_begin"	value="${search.twiceConsumingDateBegin }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd 00:00:00'})" readOnly="readonly"/> </label> 
			<label id="twiceConsumingDateEnd"  class="mr10">至 <input type="text" size="20" name="search.twiceConsumingDateEnd" id="twice_consuming_date_end"value="${search.twiceConsumingDateEnd }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd 23:59:59'})" readOnly="readonly"/> </label> 
				 
			<input type="button" value="查询" class="blue" name="" onclick="onSearchClicked();"/> 
			<!-- <input type="button" value="导出" class="blue" name="" onclick="onSearchExport();"/> -->
			</div>
		</div>
	</form>
	<!-- 分配负责人或者交接工作表单 -->

<table class="listtable" width="100%">
	<thead>
	<tr>
		<th rowspan="2">投诉会员总数</th>
		<th colspan="3">会员再次消费总数</th>
		<th colspan="3">老客户回头率</th>
		<th colspan="3">客人总数</th>
	</tr>
	<tr>
		<th>常规订单消费数</th>
		<th>酒店门票订单消费数</th>
		<th>总计</th>
		<th>常规订单回头率</th>
		<th>酒店门票订单回头率</th>
		<th>总计</th>
		<th>常规订单出游人数</th>
		<th>酒店门票订单出游人数</th>
		<th>总计</th>
	</tr>
	</thead>
	
	<tbody>
		<tr align="center"  class="trbg" >
			
			<td>
				<c:if test="${complaintCustTotalCount==null}">0</c:if>
				<c:if test="${complaintCustTotalCount!=null}">${complaintCustTotalCount}</c:if>
			</td>
			
			<td>
				<c:if test="${custTwiceConsumingCountNormal==null}">0</c:if>
				<c:if test="${custTwiceConsumingCountNormal!=null}">${custTwiceConsumingCountNormal}</c:if>
			</td>
			<td>
				<c:if test="${custTwiceConsumingCountTicket==null}">0</c:if>
				<c:if test="${custTwiceConsumingCountTicket!=null}">${custTwiceConsumingCountTicket}</c:if>
			</td>
			<td>
				<c:if test="${custTwiceConsumingCountTotal==null}">0</c:if>
				<c:if test="${custTwiceConsumingCountTotal!=null}">${custTwiceConsumingCountTotal}</c:if>
			</td>
			
			<td>
				<c:if test="${percentNormal==null}">0</c:if>
				<c:if test="${percentNormal!=null}">${percentNormal}</c:if>
			</td>
			<td>
				<c:if test="${percentTicket==null}">0</c:if>
				<c:if test="${percentTicket!=null}">${percentTicket}</c:if>
			</td>
			<td>
				<c:if test="${percentTotal==null}">0</c:if>
				<c:if test="${percentTotal!=null}">${percentTotal}</c:if>
			</td>
			
			
			<td>
				<c:if test="${peopleNumNormal==null}">0</c:if>
				<c:if test="${peopleNumNormal=='null'}">0</c:if>
				<c:if test="${peopleNumNormal!=null&&peopleNumNormal!='null'}">${peopleNumNormal}</c:if>
			</td>
			<td>
				<c:if test="${peopleNumTicket==null}">0</c:if>
				<c:if test="${peopleNumTicket=='null'}">0</c:if>
				<c:if test="${peopleNumTicket!=null&&peopleNumTicket!='null'}">${peopleNumTicket}</c:if>
			</td>
			<td>
				<c:if test="${peopleNumTotal==null}">0</c:if>
				<c:if test="${peopleNumTotal=='null'}">0</c:if>
				<c:if test="${peopleNumTotal!=null&&peopleNumTotal!='null'}">${peopleNumTotal}</c:if>
			</td>
		</tr>
	</tbody>
</table>
</div>


<script type="text/javascript">
$(document).ready(function(){
	
	
	$(document).keydown(function(event) {  //屏蔽回车提交
		  if (event.keyCode == 13) {
		    $('form').each(function() {
		      event.preventDefault();
		    });
		  }
		}); 
	
	textAutocomplete();
});

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

<%@include file="/WEB-INF/html/foot.jsp" %>
