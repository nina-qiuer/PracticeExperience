<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<link href="${CONFIG.res_url}css/thickbox.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${CONFIG.res_url}script/jquery/plugin/auto_complete/jquery.autocomplete.css"/>

<script type="text/javascript" src="${CONFIG.res_url}script/thickbox-compressed.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}script/DatePickerNew/WdatePicker.js" ></script>  
<script type="text/javascript" src="${CONFIG.res_url}script/jquery/plugin/auto_complete/jquery.autocomplete.js"></script>

<script>
function onSearchClicked() {
	$('#complaint_form').attr("action", "qcwork-execute");
	$('#complaint_form').submit();
}

function onSearchExport() { 
	$('#complaint_form').attr("action", "qcwork-export");
	$('#complaint_form').submit();
}

function showDetail(person,tpye,count) {
	if(count == 0){
		return;
	}
	if(person == 0){
		return;
	}
	
	$('#timeoutflag').val("");
	$('#questions').val("");
	$('#nametype').val(tpye); 
	if(tpye == 1){//导出总完成质检单
		$('#status').val(2);
	}else if(tpye == 2){//导出应完成质检单
		$('#status').val(0);
	}else if(tpye == 3){//导出及时完成的质检单
		$('#status').val(2);
		$('#timeoutflag').val(0);
	}else if(tpye == 4){//执行问题
		$('#status').val(2);
		$('#questions').val(1);
	}else if(tpye == 5){//供应商问题
		$('#status').val(2); 
		$('#questions').val(2);
	}else if(tpye == 6){//系统、流程问题
		$('#status').val(2);
		$('#questions').val(3);
	}else if(tpye == 8){//低满意度
		$('#status').val(2);
		$('#questions').val(5);
	}else if(tpye == 7){//导出未及时完成的质检单
		$('#status').val(2);
		$('#timeoutflag').val(1);
	}

	else{
		return;
	}
	$('#qcPerson').val(person);
	$('#complaint_form').attr("action", "qcwork-showDetail");
	$('#complaint_form').submit();
}

function showInputDay(person){
	if(person == 0){
		return;
	}
	$('#workday_' + person).hide();
	$('#inputworkday_' + person).show();
}

function changeday(person){
	var newdays = $('#inputworkday_' + person).val();
	if(null == newdays || newdays.trim() == ""){
		$('#workday_' + person).show();
		$('#inputworkday_' + person).hide();
		return;
	}
	
	if(parseInt(newdays) != newdays || newdays <= 0){
		alert("输入有误");
		return;
	}
	var olddays = $('#workday_' + person).html();
	if(newdays == olddays){
		$('#workday_' + person).show();
		$('#inputworkday_' + person).hide();
		return;
	}
	$.ajax({
		"type":"GET",
		"url":"qcwork-changeWorkDay",
		data: "personId=" + person + "&newdays=" + newdays + "&startTime=" + $('#start_time_begin').val() + "&endTime=" + $('#start_time_end').val(),
		success: function(data) {				
			var json_data = eval('('+data+')');
			$('#workday_' + person).show();
			$('#inputworkday_' + person).hide();
			$('#workday_' + person).html(newdays);
			$('#everydaycount_' + person).html(json_data[0].everydaycount);
			$('#everydayqccount_' + person).html(json_data[0].everydayqccount);
			$('#workday_0').html(json_data[1].workday);
			$('#everydaycount_0').html(json_data[1].everydaycount);
			$('#everydayqccount_0').html(json_data[1].everydayqccount);
		},
		error:function() {
			alert("error");
		}
	});
}
</script>
<BODY>
<div class="top_crumbs">您当前所在的位置：<a href="#">投诉质检管理</a>&gt;&gt;<span class="top_crumbs_txt">质检工作量统计</span></div>
	<form name="form" id="complaint_form" method="post" enctype="multipart/form-data" action="qcwork-execute">
		<input type="text" style="display: none;" name="search.qcPerson" id="qcPerson" value="${search.qcPerson }"/>
		<input type="text" style="display: none;" name="search.questions" id="questions" value="${search.questions }"/>
		<input type="text" style="display: none;" name="search.timeoutflag" id="timeoutflag" value="${search.timeoutflag }"/>
		<input type="text" style="display: none;" name="search.status" id="status" value="${search.status }"/>
		<input type="text" style="display: none;" name="search.nametype" id="nametype" value="${search.nametype }"/>
		<div class="pici_search pd5">
			<div class="pici_search pd5 mb10">
				<label> 时间： <input type="text" size="20" name="search.startTime" id="start_time_begin"	value="${search.startTime }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readOnly="readonly"/> </label> 
				<label class="mr10">至 <input type="text" size="20" name="search.endTime" id="start_time_end"value="${search.endTime }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readOnly="readonly"/> </label> 
				<input type="button" value="查询" class="blue" name="" onclick="onSearchClicked();"/> 
				<input type="button" value="导出" class="blue" name="" onclick="onSearchExport();"/>
			</div>
		</div>
	</form>
	<table class="listtable" width="98%">
	<thead>
		<th>人员</th>
		<th>总完成单数</th>
		<th>应完成单数</th>
		<th>及时完成单数</th>
		<th>超时完成单数</th>
		<th>执行问题</th>
		<th>供应商问题</th>
		<th>系统、流程问题</th>
		<th>低满意度问题</th>
		<th>上班天数</th>
		<th>日均工作量（单）</th>
		<th>及时率</th>
		<th>完成率</th>
		<th>日均发现问题点数</th>
	</thead>
	<tbody>
		<c:forEach items="${resultList}" var="v" varStatus="st">
			<tr align="center">
				<td>${v.pername}</td>
				<td><a href="javascript:showDetail(${v.person},1,${v.alldoaccount})" title="点击导出详细数据" id="alldoaccount_${v.person}">${v.alldoaccount}</a></td>
				<td><a href="javascript:showDetail(${v.person},2,${v.allaccount})" title="点击导出详细数据" id="allaccount_${v.person}">${v.allaccount}</a></td>
				<td><a href="javascript:showDetail(${v.person},3,${v.timeaccount})" title="点击导出详细数据" id="timeaccount_${v.person}">${v.timeaccount}</a></td>
				<td><a href="javascript:showDetail(${v.person},7,${v.timeoutdocount})" title="点击导出详细数据" id="timeoutdocount_${v.person}">${v.timeoutdocount}</a></td>
				<td><a href="javascript:showDetail(${v.person},4,${v.qesa})" title="点击导出详细数据" id="qesa_${v.person}">${v.qesa}</a></td>
				<td><a href="javascript:showDetail(${v.person},5,${v.qesb})" title="点击导出详细数据" id="qesb_${v.person}">${v.qesb}</a></td>
				<td><a href="javascript:showDetail(${v.person},6,${v.qesc})" title="点击导出详细数据" id="qesc_${v.person}">${v.qesc}</a></td>
				<td><a href="javascript:showDetail(${v.person},8,${v.qesd})" title="点击导出详细数据" id="qesd_${v.person}">${v.qesd}</a></td>
				<td>
					<a href="javascript:showInputDay(${v.person})" title="点击修正工作天数" id="workday_${v.person}">${v.workday}</a>
					<input id="inputworkday_${v.person}" type="text" onblur="changeday(${v.person})" style="display: none;width: 50px;"/>
				</td>
				<td id="everydaycount_${v.person}">${v.everydaycount}</td>
				<td>${v.timely}</td>
				<td>${v.finishly}</td>
				<td id="everydayqccount_${v.person}">${v.everydayqccount}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
</form>
</BODY>
