<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<script type="text/javascript" src="${CONFIG.res_url}/script/jquery/plugin/jquery.form.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}script/DatePickerNew/WdatePicker.js" ></script> 
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
	$('#isSubmit').val(1);
	return true;
}

function success_function(){
	if($("#sendSms:checked").length > 0){
		return false;
	}
	self.parent.easyDialog.close();
	parent.myrefresh();
}

function sendSMS(){
	var ids = $('#ids').val();
	var smsContent = $('#smsContent').val().replace("/\r\n|\r|\n|\n\r|\t|\t\r|\t\n|\n\t|\r\t|\t\r\n|\t\n\r|\r\t\n|\n\t\r|\n\r\t|\r\n\t/", "");
	if(!confirm("是否要发送短信？")){ 
		return false; 
	}
    var reg = /^0?1[3|4|5|8][0-9]\d{8}$/;
	if(smsContent == ''){
		alert("短信内容不能为空");
		return false;
	}
	if(smsContent.length>250){
		alert("短信内容不能超过250个字");
		return false;
	}
	var param = {"ids":ids,"smsContent":smsContent};
	$.ajax({
	type: "POST",
	async:false,
	url: "callloss-sendSMS",
	data: param,
	success: function(data){
		var json=jQuery.parseJSON(data);
		if(json.num > 0){
			alert("短信发送成功("+json.num+"条)");
			self.parent.easyDialog.close();
			parent.myrefresh();
		}
		if(json.num <= 0){
			if(json.num == -1){
				alert("两天内不能重复发送相同内容");
			}else{
				alert("短信发送失败，请确认后发送");
			}
			
		}
     }
   });
}

function isSendSms(){
	if($("#sendSms:checked").length > 0){
		$("#isSand").show();
	}else{
		$("#isSand").hide();
	}
}
</script>
</HEAD>
<BODY>
	<h2>${request.formTitle}</h2>
	<form name="form" id="form" method="post" enctype="multipart/form-data"
		action="follow_up_record-save">
		<input type="hidden" id="isSubmit" value="0" />
		<input type="hidden" id="ids" name="ids" value="${ids}" />
		<table class="datatable" width="100%">
			<tr>
				<th align="right">短信内容：</th>
				<td>
					<textarea id="smsContent" cols="45" rows="3" >${entity.smsContent}</textarea>
				</td>
			</tr>
			<tr>
				<th>&nbsp;</th>
				<td><input class="pd5" type="button" name="button" onclick="sendSMS()" value="发送短信" /></td>
			</tr>
		</table>
	</form>
	<%@include file="/WEB-INF/html/foot.jsp"%>