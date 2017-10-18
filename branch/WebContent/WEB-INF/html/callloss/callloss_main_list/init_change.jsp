<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${CONFIG.res_url}css/thickbox.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${CONFIG.res_url}/script/thickbox-compressed.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}script/DatePickerNew/WdatePicker.js" ></script>
<script type="text/javascript" src="${CONFIG.res_url}/script/jquery/plugin/jquery.form.js"></script>
<script>

function onSubmit(){
	if($("input[name='status']:checked").length <= 0){
		alert("至少选择一条记录");
		return false;
	}
	if($("input[name='status']:checked").val()== 3){
		alert("当前状态为处理中，请选择正确的处理结果状态");
		return false;
	}
	$('#callloss_form').attr("action","callloss-changeCallStatus?a="+new Date());
	$('#callloss_form').submit();
}


</script>

</HEAD>
<BODY>
<div class="top_crumbs">您当前所在的位置：<a href="#">呼损查询</a>&gt;&gt;<span class="top_crumbs_txt">更改状态</span></div>
	<form name="form" id="callloss_form" method="post">
		<input type="hidden" id="callingId" name="callingId" value="${callingId}"/>
		<span class="top_crumbs_txt">客户号码：</span>${callingId}<br/>
		<input type="hidden" name="calling" value="calling"/>
			<span class="top_crumbs_txt">状态：</span>
				<input type="radio" name="status" value="0">待处理
				<input type="radio" name="status" value="3" checked="checked">处理中
				<input type="radio" name="status" value="1">已处理
				<input type="radio" name="status" value="2">已关闭
				<br>
				<span class="top_crumbs_txt">通话情况说明：</span>
				<br>
				<textarea rows="6" cols="30" name="content"></textarea>
				<input type="button" value="更改状态" class="blue" name="" onclick="onSubmit();"/> 
	</form>

