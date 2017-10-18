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
	//$('#complaint_form').attr("action", "${manageUrl}");
	$('#satisfaction_form').attr("action", "signSatisfaction-fixSignSatisfaction");
	$('#satisfaction_form').submit();
}

</script>
</HEAD>	
<BODY>
<div class="top_crumbs">您当前所在的位置：<a href="#">投诉单custId数据修补</a>&gt;&gt;<span class="top_crumbs_txt">签约满意度数据修补</span></div>
	<form name="form" id="satisfaction_form" method="post" onSubmit=""
		enctype="multipart/form-data" action="signSatisfaction-fixSignSatisfaction">

		<div class="pici_search pd5">

			<div class="pici_search pd5 mb10">
			
			           
				<input type="button" value="提交" class="blue" name="" onclick="onSearchClicked();"/> 
			</div>
		</div>
	</form>

<%@include file="/WEB-INF/html/pager.jsp" %>
<%@include file="/WEB-INF/html/foot.jsp" %>
