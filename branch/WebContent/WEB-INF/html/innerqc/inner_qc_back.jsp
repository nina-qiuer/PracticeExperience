<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<script type="text/javascript" src="${CONFIG.res_url}/script/jquery/plugin/jquery.form.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}script/jquery/plugin/KindEditor/kindeditor-min.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}script/jquery/plugin/KindEditor/lang/zh_CN.js"></script>
<link rel="stylesheet" type="text/css" href="${CONFIG.res_url}script/jquery/plugin/KindEditor/css/default.css"/>

<script type="text/javascript">
<!--
var editor1;
KindEditor.ready(function(K) {
	editor1 = K.create('#remark', {
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
	
}

function success_function() {
	self.parent.closeWindow('${entity.id}');
}

function doSubmit() {
	editor1.sync();
	$("#form").submit();
}
//-->
</script>
</HEAD>
<BODY>
<form name="form" id="form" method="post" action="inner_qc-back" enctype="multipart/form-data">
<input type="hidden" name="entity.id" value="${entity.id }">
<input type="hidden" id="state" name="entity.state" value="0">
<input type="hidden" id="state" name="entity.addPersonId" value="${entity.addPersonId}">
<input type="hidden" id="state" name="entity.qcEventSummary" value="${entity.qcEventSummary}">
<table width="100%" class="datatable">
	<tr>
		<th align="right" width="80" height="30">备注：</th>
		<td>
			<textarea id="remark" name="entity.remark" style="width:600px;height:100px;visibility:hidden;">${entity.remark}</textarea>
		</td>
	</tr>
	<tr>
		<th align="right" height="30"></th>
		<td>　
			<input type="button" class="pd5" value="退回" onclick="doSubmit()">
		</td>
	</tr>
</table>
</form>
</BODY>
