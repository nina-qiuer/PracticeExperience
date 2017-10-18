<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<script type="text/javascript" src="${CONFIG.res_url}script/jquery/plugin/KindEditor/kindeditor-min.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}script/jquery/plugin/KindEditor/lang/zh_CN.js"></script>
<link rel="stylesheet" type="text/css" href="${CONFIG.res_url}script/jquery/plugin/KindEditor/css/default.css"/>

<script type="text/javascript">
<!--
var editor1, editor2, editor3;
KindEditor.ready(function(K) {
	editor1 = K.create('#description', {
		resizeType : 1,
		allowPreviewEmoticons : false,
		allowImageUpload : false,
		items : ['fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', 
				 'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', '|', 'hr', 'fullscreen']
	});
	editor2 = K.create('#verifyBasis', {
		resizeType : 1,
		allowPreviewEmoticons : false,
		allowImageUpload : false,
		items : ['fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', 
				 'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', '|', 'hr', 'fullscreen']
	});
	editor3 = K.create('#conclusion', {
		resizeType : 1,
		allowPreviewEmoticons : false,
		allowImageUpload : false,
		items : ['fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', 
				 'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', '|', 'hr', 'fullscreen']
	});
});

$(document).ready(function(){
	var resultInfo = '${resultInfo}';
	if ("Success" == resultInfo) {
		self.parent.refreshIfm2();
		self.parent.easyDialog.close();
	}
	
    $("#form").validate();
});

function changeClassTwo(pid) {
	changeTwo(pid, "#classTwo option", ".cla_");
}

function changeTwo(pid, optStr, claStr) {
	$(optStr).each(function() {
		var value = $(this).val();
		if (value == '') {
			value = 0;
		}
		
		if (value > 0) {
			$(this).hide();
			$(this).attr("disabled", true);
		} else {
			$(this).attr("selected", "selected");
		}
	 });
	if (pid > 0) {
		$(claStr + pid).each(function() {
			$(this).show();
			$(this).attr("disabled", false);
		 });
	}
}

function doSubmit(id) {
	editor1.sync();
	editor2.sync();
	editor3.sync();
	if (id > 0) {
		$('#form').attr("action", "inner_qc_question-update");
		$('#form').submit();
	} else {
		$('#form').attr("action", "inner_qc_question-add");
		$('#form').submit();
	}
}
//-->
</script>
</HEAD>
<BODY>
<form name="form" id="form" method="post" action="" enctype="multipart/form-data">
<input type="hidden" name="entity.id" value="${entity.id }">
<input type="hidden" name="entity.iqcId" value="${entity.iqcId }">
<table width="100%" class="datatable">
	<tr>
		<th align="right" width="100" height="30"><span style="color: red">*</span>&nbsp;问题等级：</th>
		<td>
			<select name="entity.questionLevel" class="required">
				<option value="" <c:if test="${0 == entity.questionLevel}">selected</c:if>>请选择</option>
				<option value="1" <c:if test="${1 == entity.questionLevel}">selected</c:if>>1</option>
				<option value="2" <c:if test="${2 == entity.questionLevel}">selected</c:if>>2</option>
				<option value="3" <c:if test="${3 == entity.questionLevel}">selected</c:if>>3</option>
			</select>
		</td>
		<th align="right" width="100" height="30"><span style="color: red">*</span>&nbsp;问题类型：</th>
		<td>
			<select onchange="changeClassTwo(this.value)">
			<option value="0">请选择一级分类</option>
			<c:forEach items="${questionClasses}" var="bigClass">
				<c:if test="${bigClass.childClasses != null}">
					<option value="${bigClass.id}" <c:if test="${entity.bigClassName == bigClass.description}">selected</c:if>>${bigClass.description}</option>
				</c:if>
			</c:forEach>
			</select>
			&nbsp;—&nbsp;
			<select name="entity.questionTypeId" class="required" id="classTwo">
			<option value="">请选择二级分类</option>
			<c:forEach items="${questionClasses}" var="smallClass">
				<c:if test="${smallClass.childClasses == null}">
					<c:choose>
						<c:when test="${smallClass.parentId == entity.questionClassId1}">
							<option value="${smallClass.id}" class="cla_${smallClass.parentId}" <c:if test="${smallClass.id == entity.questionTypeId}">selected</c:if>>${smallClass.description}</option>
						</c:when>
						<c:otherwise>
							<option value="${smallClass.id}" class="cla_${smallClass.parentId}" style="display: none;" disabled="disabled">${smallClass.description}</option>
						</c:otherwise>
					</c:choose>
				</c:if>
			</c:forEach>
			</select>
		</td>
	</tr>
	<tr>
		<th align="right" width="100" height="30">问题描述：</th>
		<td colspan="3">
			<textarea id="description" name="entity.description" style="width:700px;height:200px;visibility:hidden;">${entity.description}</textarea>
		</td>
	</tr>
	<tr>
		<th align="right" width="100" height="30">核实依据：</th>
		<td colspan="3">
			<textarea id="verifyBasis" name="entity.verifyBasis" style="width:700px;height:200px;visibility:hidden;">${entity.verifyBasis}</textarea>
		</td>
	</tr>
	<tr>
		<th align="right" width="100" height="30">质检结论：</th>
		<td colspan="3">
			<textarea id="conclusion" name="entity.conclusion" style="width:700px;height:200px;visibility:hidden;">${entity.conclusion}</textarea>
		</td>
	</tr>
	<tr>
		<th align="right" width="100" height="30"></th>
		<td colspan="3">
			<input type="button" class="pd5" value="提交" id="submitBtn" onclick="doSubmit('${entity.id}')">
		</td>
	</tr>
</table>
</form>
</BODY>
