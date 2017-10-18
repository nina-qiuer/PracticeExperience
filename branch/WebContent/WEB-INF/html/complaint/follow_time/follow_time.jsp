<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<script type="text/javascript" src="${CONFIG.res_url}script/DatePickerNew/WdatePicker.js" ></script> 
<script type="text/javascript" src="${CONFIG.res_url}/script/jquery/plugin/jquery.form.js"></script>
<script type="text/javascript">
$(document).ready(function() { 
    var options = { 
        beforeSubmit:  check_follow_form,  // pre-submit callback 
        success:       success_function,  // post-submit callback 
    }; 
    // bind form using 'ajaxForm' 
    $('#form').ajaxForm(options); 
});

function check_follow_form(){
	var time = $('#time').val();
	if(time == ''){
		alert('下次跟进时间不能为空');
		return false;
	}
	
	$('#button').attr('disabled' , 'true');
	$("#isSubmit").val(1);
	return true;
}

function success_function(){
	parent.location.replace(parent.location.href);
}

</script>
</HEAD>
<BODY>
	<h2>${request.formTitle}</h2>
	<form name="form" id="form" method="post" enctype="multipart/form-data"
		action="follow_time-save">
		<input type="hidden"  id="isSubmit" value="0" />
		<input type="hidden" name="entity.complaintId" id="id" value="${entity.complaintId}" />
		<input type="hidden" name="entity.orderId" id="refer_to" value="${entity.orderId}" />
		<table class="datatable" width="100%">
			<tr>
				<th width="100" align="right">下次跟进时间：</th>
				<td>
					<input type="text" name="entity.time" id="time" onFocus="WdatePicker()" readonly value="" />
				</td>
			</tr>
			<tr>
				<th align="right">备注内容：</th>
				<td>
					<textarea name="entity.note" id="note" cols="45" rows="5"></textarea>
				</td>
			</tr>
			<tr>
				<th>&nbsp;</th>
				<td>
					<input class="pd5" type="submit" name="button" id="button"	value="确定" /> 
				</td>
			</tr>
		</table>
	</form>
	<%@include file="/WEB-INF/html/foot.jsp"%>