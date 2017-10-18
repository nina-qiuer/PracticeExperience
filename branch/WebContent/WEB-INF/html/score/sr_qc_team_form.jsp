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
    	$('#form').attr("action", "sr_qc_team-update");
    } else {
    	$('#form').attr("action", "sr_qc_team-add");
    }
});

function checkSubmit() {
	var teamName = $("#teamName").val();
	if (teamName == '') {
		alert("组名不能为空!~");
		$("#teamName").focus();
		return false;
	}

	var persons = $("#persons").val();
	if (persons == '') {
		alert("成员不能为空!~");
		$("#persons").focus();
		return false;
	}
	
	$("#submitBtn").attr("disabled", true);
}

function success_function() {
	self.parent.location.href="sr_qc_team";
}
//-->
</script>
</HEAD>
<BODY>
<form name="form" id="form" method="post" action="sr_qc_team-add" enctype="multipart/form-data">
<input type="hidden" name="entity.id" value="${entity.id}">
<table width="100%" class="datatable">
	<tr>
		<th align="right" width="50" height="30">组名：</th>
		<td>
			<input type="text" name="entity.teamName" id="teamName" value="${entity.teamName}">
		</td>
	</tr>
	<tr>
		<th align="right" width="50" height="30">成员：</th>
		<td>
			<span style="color: green;">格式：张三,李四,王五,赵六（英文逗号间隔）</span>
			<textarea rows="3" cols="30" name="entity.persons" id="persons">${entity.persons}</textarea>
		</td>
	</tr>
	<tr>
		<th align="right" width="50" height="30"></th>
		<td>
			<input type="submit" value="提交" id="submitBtn">
		</td>
	</tr>
</table>
</form>
</BODY>
