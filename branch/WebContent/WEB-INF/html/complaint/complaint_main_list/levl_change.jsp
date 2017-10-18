<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp"%>
<link href="${CONFIG.res_url}css/thickbox.css" rel="stylesheet"
	type="text/css" />
<link type="text/css" href="${CONFIG.res_url}css/bui.css"
	rel="stylesheet" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript"
	src="${CONFIG.res_url}/script/thickbox-compressed.js"></script>
<script type="text/javascript"
	src="${CONFIG.res_url}/script/jquery/plugin/jquery.form.js"></script>
<style type="text/css">
.common-box {
	border: 1px solid #8CBFDE;
	margin: 0 0 10px 0;
}

.common-box-hd {
	margin-top: 1px;
	padding-left: 10px;
	background: #C6E3F1;
	color: #005590;
	font-size: 14px;
	font-weight: bold;
	height: 25px;
	line-height: 25px;
	border-bottom: 1px solid #8CBFDE;
	position: relative;
}

.common-box-hd span.title2 {
	cursor: pointer;
	display: inline-block;
	line-height: 25px;
	margin-right: 15px;
}
</style>


<script type="text/javascript">
$(document).ready(function() { 
    var options = { 
    	beforeSubmit:  check_follow_form,
        success:   success_function,  // post-submit callback 
    }; 
    $('#form').ajaxForm(options); 
});

function check_follow_form(){
	var oldLevl = $('#oldLevl').val();
	var newLevl = $('#newLevl').val();alert(newLevl);
	var changeReason = $('changeReason').val();
	if(changeReason == ''){
		alert('修改原因必须填写');
		return false;
	}
	if(newLevl == oldLevl){
		alert('等级没变化');
		return false;
	}
	if(!newLevl.match(/^[1-3]{1}$/)){ 
		alert("只能填写正整数1,2,3");
		return false;
	}
	return true;
}

function success_function(){
	alert('提交成功');
	self.parent.easyDialog.close();
	parent.location.replace(parent.location.href);
}
</script>
</head>
<body>
	<div class="common-box">
		<form name="form" id="form" method="post"
			enctype="multipart/form-data" action="complaint-changeComplaintLevl">
			<table width="100%" style="TABLE-LAYOUT: fixed;" class="datatable">
				<input type="hidden" name="complaintId" id="id" value="${request.complaint_id }" />
				<input type="hidden" name="oldLevl" id="oldLevl" value="${oldLevl }" />
				<tr>
					<th width="100" rows="4">投诉等级：</th>
					<td>
					<select class="mr10" id="newLevl" name="complaintLevl">
						<option value="1" <c:if test="${complaintLevl=='1'}">selected</c:if> >1级</option>
						<option value="2" <c:if test="${complaintLevl=='2'}">selected</c:if> >2级</option>
						<option value="3" <c:if test="${complaintLevl=='3'}">selected</c:if> >3级</option>
					</select>
					</td>
				</tr>
				<tr>
					<th width="100" rows="4">修改原因：</th>
					<td><textarea id="changeReason" name="changeReason" rows="5" cols="70"></textarea></td>
				</tr>
				<tr>
					<th width="100" rows="4">是否发送邮件：</th>
					<td><input type="checkbox" name="isEmail" value="1">是否发送邮件</td>
				</tr>
				<tr>
					<th width="100" rows="4">情况说明：</th>
					<td><textarea id="changeDesc" name="changeDesc" rows="5" cols="70"></textarea></td>
				</tr>
				<tr>
					<th width="130"></th>
					<td><input class="pd5" type="submit" name="save" value="确定"
						id="saveInfo" /></td>
				</tr>
			</table>
		</form>
	</div>
	<%@include file="/WEB-INF/html/foot.jsp"%>