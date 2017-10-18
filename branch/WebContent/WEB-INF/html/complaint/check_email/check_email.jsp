<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp"%>
<script type="text/javascript" src="${CONFIG.res_url}/script/jquery/plugin/jquery.form.js"></script>
<script type="text/javascript">
$(document).ready(function() { 
    var options = { 
        beforeSubmit:  check_mail_form,  // pre-submit callback 
        success:       success_function,  // post-submit callback 
    }; 
    // bind form using 'ajaxForm' 
    $('#check_mail').ajaxForm(options); 
});

function check_mail_form(){
	var reg = /^([a-zA-Z0-9]+[_|\_|\.|\-]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.|\-]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	var address = $('#address').val();
	var cc = $('#cc').val();
	var title = $('#title').val();
	var content = $('#content').val();
	
	var addressMail = address.substr(0, address.length-1).split(";");
	//alert(addressMail)
	for ( var i = 0; i < addressMail.length; i++) {
		reg.lastIndex = 0; 
		if(addressMail[i] != '' && !reg.test(addressMail[i])){
			alert('请输入有效的收件人邮箱地址');
			return false;
		}
	}
	
	var ccMail = cc.split(";");
	for ( var i = 0; i < ccMail.length; i++) {
		reg.lastIndex = 0; 
		if(ccMail[i] != '' && !reg.test(ccMail[i])){
			alert('请输入有效的抄送人邮箱地址');
			return false;
		}
	}
	
	if(title == ''){
		alert('核实请求主题不能为空');
		return false;
	}
	if(title.length > 30){
		alert('核实请求主题不能超过30个字');
		return false;
	}
	if(content == ''){
		alert('核实请求内容不能为空');
		return false;
	}
	
	$('#button').attr('disabled' , 'true');
	$("#isSubmit").val(1);
	return true;
	
}

function success_function(){
	alert('提交成功');
	self.parent.easyDialog.close();
	parent.location.replace(parent.location.href);
}


function setAddress(select){
	alert(select.value);
	var address = $('#address').val();
	var newAddress = "";
	if(address.indexOf(select.value) ==-1){
		newAddress = address + select.value + ";";
		$('#address').val(newAddress);
	}
}

function onSaveInfoClicked() {
		
   $('#check_mail').attr("action", "check_email-save");
   $('#check_mail').submit();

}

</script>

</HEAD>
<BODY>
<form id="check_mail" method="post" enctype="multipart/form-data">
<input type="hidden"  id="isSubmit" value="0" />
	<input name="entity.complaintId" type="hidden" value="${complaintId}"/>
	<input name="id" type="hidden" value="${id}"/>
	<input name="entity.mark" type="hidden" value="${mark}"/>
	<input name="orderId" type="hidden" value="${orderId}"/>
	<table class="datatable" width="100%">
		<tr>
			<th width="100" align="right">收件人：</th>
			<td>
				<input name="entity.address" type="text" id="address" size="54" value="${emails}"/>
				
			</td>

		</tr>
		<tr>
			<th align="right">抄送人：</th>
			<td>
				<input name="entity.cc" type="text" id="cc" size="54" />
			</td>
		</tr>
		<tr>
			<th align="right">主题：</th>
			<td>
				<input name="entity.title" type="text" id="title" size="54" />
				</td>

		</tr>
		<tr>
			<th align="right">内容：</th>
			<td>
				<textarea name="entity.content" id="content" cols="45" rows="5">${send_content }</textarea>
			</td>
		</tr>
		<tr>
			<th>&nbsp;</th>
			<td>
				<input class="pd5" type="button" name="button" id="button" value="发送" onclick="onSaveInfoClicked()"/> 
				<input onclick="self.parent.easyDialog.close();" type="button" name="button2" class="pd5" id="button2" value="取消" />
			</td>
		</tr>
	</table>
</form>
	<%@include file="/WEB-INF/html/foot.jsp"%>