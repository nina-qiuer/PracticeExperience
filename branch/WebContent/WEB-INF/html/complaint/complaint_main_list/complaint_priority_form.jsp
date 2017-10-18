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


function updatePriority(input){
	
	var complaintId = $('#complaintId').val();
	var priorityContent = $('#priorityContent').val();
	
	var priority = $("#priority").find("option:selected").val();  
	  // 禁用提交按钮
    disableButton(input);
    	$.ajax( {
    		url : 'complaint-updatePriority',
    		data : {
    			priority: priority,
    			complaintId :complaintId,
    			priorityContent :priorityContent
    		},
    		type : 'post',
    		dataType:'json',
    		cache : false,
    		success : function(result) {
    			
    			if(result)
    			{
    		    	if(result.retObj == "success")
    				{
    		    		
    		    		alert("提交成功");
    		    		parent.location.replace(parent.location.href);
    		    		
    				}else{
    					
    					enableButton(input);
    					alert(result.resMsg);
    				}
    		     }
    		 },
    		 error: function() {
                 // 解除禁用
                 enableButton(input);
             }
    	    });

}


</script>
</head>
<body>
	<div class="common-box">
		<form name="form" id="form" method="post"  enctype="multipart/form-data" action="">
			<input type="hidden" name="complaintId" id="complaintId" value="${complaint.id}" />
		
			<table width="100%" style="TABLE-LAYOUT: fixed;" class="datatable">
			
				<tr>
					<th width="100" rows="4">优先级：</th>
					<td >
					<select class="mr10" name="priority" id="priority" >
					
						<option value="3" <c:if test="${complaint.priority== 3}">selected</c:if> >普通</option>
						<option value="1" <c:if test="${complaint.priority== 1}">selected</c:if> >紧急</option>
						<option value="2" <c:if test="${complaint.priority== 2}">selected</c:if> >重要</option>
						<option value="4" <c:if test="${complaint.priority== 4}">selected</c:if> >一般</option>
					</select>
				</td>
				</tr>
				<tr>
					<th width="100" rows="4">备注：</th>
					<td>
					    <textarea id="priorityContent" name="priorityContent" style="width:400px;height:100px">${complaint.priorityContent }</textarea>
					</td>
				</tr>
				<tr>
					<th width="100" ></th>
					<td><input class="pd5" type="button" name="submitBtn" value="提交" onclick="updatePriority(this)" />
				    </td>
				    <td></td>
				</tr>
			</table>
		</form>
	</div>
	