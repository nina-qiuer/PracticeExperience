<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<link href="${CONFIG.res_url}css/thickbox.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${CONFIG.res_url}script/jquery/plugin/auto_complete/jquery.autocomplete.css"/>
<script type="text/javascript" src="${CONFIG.res_url}script/thickbox-compressed.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}script/jquery/plugin/auto_complete/jquery.autocomplete.js"></script>  
<script type="text/javascript" src="http://crm.nb.tuniu.org/js/util.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}script/DatePickerNew/WdatePicker.js" ></script>  
<script type="text/javascript" src="${CONFIG.res_url}script/jquery/jquery.1.4.2.js"></script>
<script>
function onSearchClicked() {
	if(formCheck()){
		$('#callloss_form').attr("action", "calllossPeriod-addPeriod");
		$('#callloss_form').submit();
	}
}

function onReset(){
	$('#start_time').val("");
	$('#end_time').val("");
}

function formCheck(){
	if($('#start_time').val()==""){
		alert("同步开始时间不能为空");
		return false;
	}
	if($('#end_time').val()==""){
		alert("同步结束时间不能为空");
		return false;
	}
	if($('#start_time').val()>$('#end_time').val()){
		alert("同步结束时间不能早于同步开始时间");
		return false;
	}
	return true;
}

function delPeriod(id){
	if(confirm("确定删除？")){
		$.ajax({
			type: "POST",
			url: "calllossPeriod-delPeriod",
			data: "id=" + id ,
			success: function(data) {				
				$('#callloss_form').attr("action", "calllossPeriod");
				$('#callloss_form').submit();
			},
		})
		
		
		
	}
}
</script>
</HEAD>
<BODY>
<div class="top_crumbs">您当前所在的位置：<a href="#">呼损查询</a>&gt;&gt;<span class="top_crumbs_txt">呼损同步时间设置</span></div>
	<form name="form" id="callloss_form" method="post">

		<div class="pici_search pd5">

			<div class="pici_search pd5 mb10">
			<label> 记录同步时间段： 从<input type="text" size="20" name="startTime" id="start_time"	value="${startTime }" onclick="WdatePicker({dateFmt:'HH:mm'})" readOnly="readonly"/> </label> 
			<label class="mr10">至 <input type="text" size="20" name="endTime" id="end_time" value="${endTime }" onclick="WdatePicker({dateFmt:'HH:mm'})" readOnly="readonly"/> </label> 
			<input type="button" id="queryCall" value="添加" class="blue" name="" onclick="onSearchClicked();"/> 
			<input type="button" value="重置" class="blue" onclick="onReset();"/> 
			</div>
		</div>
	</form>
	
<table class="listtable" width="98%">
	<thead>
		<th>同步开始时间</th>
		<th>同步结束时间</th>
		<th>操作</th>
	</thead>
	<tbody>
		<c:forEach items="${dataList}" var="v"  varStatus="st"> 
		<tr  align="center" class="trbg" >
			<td>${v.startTime}</td>
			<td>${v.endTime}</td>
			<td><a href='javascript:delPeriod(${v.id})'>删除</a></td>
		</tr>
		</c:forEach>
	</tbody>
</table>
<%@include file="/WEB-INF/html/pager.jsp" %>
<br>
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


</script>
<%@include file="/WEB-INF/html/foot.jsp" %>
