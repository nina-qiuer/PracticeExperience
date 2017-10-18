
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<script type="text/javascript" src="${CONFIG.res_url}/script/jquery/plugin/jquery.form.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}script/DatePickerNew/WdatePicker.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}script/jquery/plugin/KindEditor/kindeditor-min.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}script/jquery/plugin/KindEditor/lang/zh_CN.js"></script>
<link rel="stylesheet" type="text/css" href="${CONFIG.res_url}script/jquery/plugin/KindEditor/css/default.css"/>

<script type="text/javascript">
<!--
var editor1, editor2;
KindEditor.ready(function(K) {
	editor1 = K.create('#qcEventDesc', {
		resizeType : 1,
		allowPreviewEmoticons : false,
		allowImageUpload : false,
		items : ['fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', 
				 'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', '|', 'hr', 'fullscreen']
	});
	editor2 = K.create('#remark', {
		resizeType : 1,
		allowPreviewEmoticons : false,
		allowImageUpload : false,
		items : ['fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', 
				 'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', '|', 'hr', 'fullscreen']
	});
});

$(document).ready(function(){
	var options = {
	    beforeSubmit:  checkSubmit,  // pre-submit callback 
	    success:       success_function  // post-submit callback 
	}; 
    $('#form').ajaxForm(options);
});

function checkSubmit() {
	var typeId = $("#typeId").val();
	if ('' == typeId) {
		alert("请选择质检事宜类型！~");
		return false;
	}
}

function success_function() {
	self.parent.closeWindow('${entity.id}');
}

function doSubmit(id, state) {
	editor1.sync();
	editor2.sync();
	var lossAmount = $("#lossAmount").val();
	if ('' == lossAmount) {
		$("#lossAmount").val(0);
	}
	$("#state").val(state);
	
	var relBillType = $("select[name=entity.relBillType] option:selected").val();
	if((relBillType==1 || relBillType==2) && $("#relBillNum").val()==""){
		alert("关联单据类型为订单或产品时，关联单据编号不许为空！");
		return false;
	}
	
	if (id > 0) {
		$('#form').attr("action", "inner_qc-update");
		$('#form').submit();
	} else {
		$('#form').attr("action", "inner_qc-add");
		$('#form').submit();
	}
}

function addRow_attach(){
	var data_json = [ {} ];
	$.tmpl.add_row("tr_attach", data_json);
}

function getFileSize(obj)
{ 
	var fileSize = obj.files[0].size;
	if(fileSize>20971520){
		alert("请上传小于20M的文件！");
		obj.parentNode.parentNode.remove();
	}
}

function deleteAttach(obj, attachId) {
	$(obj).parent().parent().remove();
	var attachDelIds = "<input type='hidden' name='attachDelIds' value='" + attachId + "'>";
	$("#addButton").after(attachDelIds);
}
//-->
</script>
</HEAD>
<BODY>
<form name="form" id="form" method="post" action="" enctype="multipart/form-data">
<input type="hidden" name="entity.id" value="${entity.id }">
<input type="hidden" id="state" name="entity.state">
<table width="100%" class="datatable">
	<tr>
		<th align="right" width="100" height="30">关联单据类型：</th>
		<td>
			<select name="entity.relBillType">
				<option value="1" <c:if test="${1 == entity.relBillType}">selected</c:if>>订单</option>
				<option value="2" <c:if test="${2 == entity.relBillType}">selected</c:if>>产品</option>
				<option value="3" <c:if test="${3 == entity.relBillType}">selected</c:if>>Jira</option>
				<option value="0" <c:if test="${0 == entity.relBillType}">selected</c:if>>无关联</option>
			</select> 
		</td>
		<th align="right" width="100" height="30">关联单据编号：</th>
		<td><input type="text" name="entity.relBillNum" id="relBillNum" value="${entity.relBillNum}"></td>
		
	</tr>
	<tr>
		<th align="right" width="100" height="30">公司损失：</th>
		<td><input type="text" name="entity.lossAmount" id="lossAmount" value="${entity.lossAmount}"> 元</td>
		<th align="right" width="100" height="30">质检事宜类型：</th>
		<td>
			<select name="entity.typeId" id="typeId">
				<option value="">全部</option>
				<c:forEach items="${typeList}" var="type">
					<option value="${type.id}" <c:if test="${type.id == entity.typeId}">selected</c:if>>${type.name}</option>
				</c:forEach>
			</select>
		</td>
	</tr>
	<tr>
		<th align="right" width="100" height="30">质检事宜概述：</th>
		<td colspan="3">
			<input type="text" name="entity.qcEventSummary" id="qcEventSummary" value="${entity.qcEventSummary}" size="93">
		</td>
	</tr>
	<tr>
		<th align="right" width="100" height="30">质检事宜详述：</th>
		<td colspan="3">
			<textarea id="qcEventDesc" name="entity.qcEventDesc" style="width:600px;height:150px;visibility:hidden;">${entity.qcEventDesc}</textarea>
		</td>
	</tr>
	<tr>
		<th align="right" width="100" height="30">备注：</th>
		<td colspan="3">
			<textarea id="remark" name="entity.remark" style="width:600px;height:100px;visibility:hidden;">${entity.remark}</textarea>
		</td>
	</tr>
	<tr>
		<th align="right" width="100" height="30">附件：</th>
		<td colspan="3"><input type="button" class="blue" value="添加" id="addButton" onclick="addRow_attach()">
		<table class="listtable">
		<c:forEach items="${entity.attachList}" var="attach">
		<tr>
			<td><a href="${attach.path}">${attach.name}</a></td>
			<td>
				<input type="button" onclick="deleteAttach(this, '${attach.id}')" value="删除">
			</td>
		</tr>
		</c:forEach>
		<tr id="tr_attach" style="display: none">
			<td><input type="file" name="iqcAttach" onchange="getFileSize(this);"></td>
			<td>
				<input type="button" onclick="$(this).parent().parent().remove();" value="删除">
			</td>
		</tr>
		</table>
		</td>
	</tr>
	<tr>
		<th align="right" width="100" height="30"></th>
		<td colspan="3">
			<input type="button" class="pd5" value="保存" id="submitBtn" onclick="doSubmit('${entity.id}', 0)">　
			<c:if test="${entity.dealPersonId == null || entity.dealPersonId == 0}">
				<input type="button" class="pd5" value="提交" id="submitBtn" onclick="doSubmit('${entity.id}', 1)">
			</c:if>
			<c:if test="${entity.dealPersonId > 0}">
				<input type="button" class="pd5" value="提交" id="submitBtn" onclick="doSubmit('${entity.id}', 2)">
			</c:if>
		</td>
	</tr>
</table>
</form>
</BODY>
