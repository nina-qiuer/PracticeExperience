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
<script type="text/javascript" src="${CONFIG.res_url}/script/jquery/plugin/jquery.form.js"></script>
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
        success:   success_function,  // post-submit callback 
    }; 
 
    $('#form').ajaxForm(options); 
});

function success_function(){
	alert('提交成功');
	parent.location.replace(parent.location.href);
}

</script>
</head>
<body>

<div class="common-box">
		
		<form name="form" id ="form" method="post" enctype="multipart/form-data"
		action="complaint-changeComplaintComeFrom" onSubmit="">
				<input type="hidden" name="id" id="id" value="${request.complaint_id }" />
					
				<table width="100%" style="TABLE-LAYOUT: fixed;" class="datatable">
					<tr>
			            <th width="130" rows="4" >投诉来源：</th>
			            <td >
				            <select name="comeFrom" id="come_from">
								<option value="0">投诉来源</option>
								<option value="网站" <c:if test="${'网站' == comeFrom}">selected</c:if>> 网站</option>
								<option value="门市" <c:if test="${'门市' == comeFrom }">selected</c:if>>门市</option>
								<option value="当地质检" <c:if test="${'当地质检' == comeFrom}">selected</c:if>>当地质检</option>
								<option value="来电投诉" <c:if test="${'来电投诉'== comeFrom }">selected</c:if>>来电投诉</option>
								<option value="CS邮箱" <c:if test="${'CS邮箱' == comeFrom }">selected</c:if>>CS邮箱</option>
								<option value="回访" <c:if test="${'回访'== comeFrom }">selected</c:if>>回访</option>
								<option value="点评" <c:if test="${'点评'== comeFrom }">selected</c:if>>点评</option>
								<option value="旅游局" <c:if test="${'旅游局' == comeFrom}">selected</c:if>>旅游局</option>
								<option value="微博" <c:if test="${'微博' == comeFrom}">selected</c:if>>微博</option>
								<option value="APP" <c:if test="${'APP' == comeFrom}">selected</c:if>>APP</option>
								<option value="其他" <c:if test="${'其他'== comeFrom}">selected</c:if>>其他</option>
							</select> 
						</td>
		          	</tr>
		          	<tr>
		          	    <th width="130"></th>
		          	    <td><input class="pd5" type="submit" name="save" value="确定" id="saveInfo" /></td>
		          	</tr>
		        </table>
	        
        </form>
	</div>
	
<%@include file="/WEB-INF/html/foot.jsp" %> 
