<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<script type="text/javascript" src="${CONFIG.res_url}/script/jquery/plugin/jquery.form.js"></script>
<script type="text/javascript">
<!--
$(document).ready(function(){
	var options = {
	    beforeSubmit:  checkSubmit,  // pre-submit callback 
	    success:       success_function  // post-submit callback 
	}; 
    $('#form').ajaxForm(options);

    var id = "${entity.id}";
    if (id > 0) {
    	$('#form').attr("action", "score_type-update");
    } else {
    	$('#form').attr("action", "score_type-add");
    }
});

function checkSubmit() {
	var scoreTypeName = $("#scoreTypeName").val();
	if (scoreTypeName == '') {
		alert("记分类型不能为空!~");
		$("#scoreTypeName").focus();
		return false;
	}
	$("#submitBtn").attr("disabled", true);
}

function success_function() {
	self.parent.location.href="score_type";
}
//-->
</script>
</HEAD>
<BODY>
<form name="form" id="form" method="post" action="score_type-add" enctype="multipart/form-data">
<input type="hidden" name="entity.id" value="${entity.id}">
<table width="100%" class="datatable">
	<tr>
		<th align="right" width="80" height="30">记分性质：</th>
		<td>
			<select name="entity.parentId">
			<c:forEach items="${scoreTypes}" var="type">
				<option value="${type.id}" <c:if test='${type.id == entity.parentId}'>selected</c:if>>${type.name}</option>
			</c:forEach>
			</select>
		</td>
	</tr>
	<tr>
		<th align="right" width="80" height="30">记分类型：</th>
		<td>
			<input type="text" name="entity.name" id="scoreTypeName" value="${entity.name}">
		</td>
	</tr>
	<tr>
		<th align="right" width="80" height="30"></th>
		<td>
			<input type="submit" value="提交" id="submitBtn">
		</td>
	</tr>
</table>
</form>
</BODY>
