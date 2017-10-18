<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<script type="text/javascript" src="${CONFIG.res_url}/script/jquery/plugin/jquery.form.js"></script>
<script type="text/javascript">
$(document).ready(function() { 
    var options = { 
        beforeSubmit:  form_submit,  // pre-submit callback 
        success:       success_function,  // post-submit callback 
    }; 
    // bind form using 'ajaxForm' 
    $('#form').ajaxForm(options); 
});
function form_submit(){
	
	var express = $('#express').val();
	
	if (express == 2) {
		var receiver = $('#receiver').val();
		var address = $('#address').val();
		var phone = $('#phone').val();
		
		if(receiver == ''){
			alert('收件人不能为空');
			return false;
		}
		if(phone.match(/^[0-9]+$/) == null){
			alert('联系电话必须为数字');
			return false;
		}
		if(address == ''){
			alert('收件人地址不能为空');
			return false;
		}
		
	}
	
	
	if(confirm("确定申请礼品？")){
		$('#saveInfo').attr('disabled' , 'true');
		return true;
	}
	else {
		return false;
	}
}

function success_function(){
	$("#isSubmit").val(1);
	alert('提交成功');
	self.parent.easyDialog.close();
	parent.location.replace(parent.location.href);
}

function express_changed(type){
	$('#tr_receiver').toggle();
	$('#tr_address').toggle();
	$('#tr_remark').toggle();
}

</script>

</HEAD>
<BODY>
	<h2>${request.formTitle}</h2>
	<form name="form" id="form" method="post" enctype="multipart/form-data"
		action="gift_note-addGiftNote" onSubmit="">
		<input type="hidden"  id="isSubmit" value="0" />
		<input type="hidden" name="entity.id" id="id" value="${entity.id}" />
		<input type="hidden" name="refer_to" id="refer_to" value="" />
		<table width="100%" class="datatable">
			<tr>
				<th width="100" align="right">订单号：</th>
				<td><input type="text" name="entity.orderId.tmp" id="order_id.tmp"
					value="${orderId}" disabled="disabled" style="background: #fff;"
					size="15" />
					<input type="hidden" name="entity.orderId" id="order_id" value="${orderId}">
				<th width="100" align="right">投诉号：</th>
				<td><input type="text" name="entity.complaintId.tmp"
					id="complaint_id.tmp" value="${complaintId}" disabled="disabled"
					style="background: #fff;" size="15" />
					<input type="hidden" name="entity.complaintId" id="complaint_id" value="${complaintId}">
				</td>
			</tr>
			
			<tr>
				<th align="right">赠送礼品：</th>

				<td colspan="3"></td>
			</tr>
			<tr>
				<th align="right">&nbsp;</th>
				<td colspan="3">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr><th>礼品分类</th><th>礼品名称</th><th>单价(元)</th><th>数量</th><th>备注</th></tr>
						<c:forEach items="${giftList}" var="v" varStatus="st">
							<tr align="center">
								<td width="100">${v.type }</td>
								<td width="100">${v.name }</td>
								<td width="100">${v.price }</td>
								<td width="100">${v.number }个</td>
								<td width="100">${v.remark }</td>
							</tr>
						</c:forEach>
					</table>
				</td>
			</tr>
			
			<tr>
				<th align="right">领取方式</th>
				<td colspan="3">
					<select name="entity.express" id="express" onchange="express_changed(this)">
						<option value="2" <c:if test="${entity.express==2}">selected</c:if>>快递领取</option>
						<option value="1" <c:if test="${entity.express==1}">selected</c:if>>本人领取</option>
					</select>
				</td>
			</tr>
			
			<tr id="tr_receiver">
				<th align="right">收件人：</th>
				<td><input type="text" name="entity.receiver"
					id="receiver" value="${entity.receiver}" size="15" /></td>
				<th align="right">联系电话：</th>
				<td><input type="text" name="entity.phone" id="phone"
					value="${entity.phone}" size="15" /></td>
			</tr>

			<tr id="tr_address">
				<th align="right">收件人地址：</th>
				<td colspan="3"><input type="text" name="entity.address"
					id="address" value="${entity.address}" size="59" /></td>
			</tr>

			<tr id="tr_remark">
				<th align="right">备注说明：</th>

				<td colspan="3"><textarea name="entity.remark" id="remark"
						cols="49" rows="5">${entity.remark }</textarea></td>
			</tr>
			<tr>
				<th align="right">申请人：</th>
				<td>${userName}<input type="hidden" name="entity.userName" id="userName" value="${userName}" />
							   <input type="hidden" name="entity.userId" id="userId" value="${userId}" /></td>
				<th align="right">部门：</th>
				<td>${departmentName}<input type="hidden" name="entity.departmentName" id="department_name" value="${departmentName}" />
									 <input type="hidden" name="entity.departmentId" id="department_id" value="${departmentId}" />
				</td>
			</tr>
			<tr>
				<th>&nbsp;</th>
				<td colspan="3"><input class="pd5" name="input" type="submit" id="saveInfo"
					value="确认" /> <input class="pd5" name="input" type="button"
					value="取消" onclick="self.parent.easyDialog.close();" /></td>
			</tr>
		</table>
	</form>
<%@include file="/WEB-INF/html/foot.jsp"%>