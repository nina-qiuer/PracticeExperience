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


function submitReview(input){
	
	var complaintId = $('#complaintId').val();
	var qcAffairDesc = $('#qcAffairDesc').val();
	
	  // 禁用提交按钮
    disableButton(input);
    	$.ajax( {
    		url : 'complaint-submitReview',
    		data : {
    			complaintId :complaintId,
    			qcAffairDesc :qcAffairDesc
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
    		    		parent.layer.closeAll();
    		    		
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
			<input type="hidden" name="complaintId" id="complaintId" value="${complaintId}" />
		
			<table width="100%" style="TABLE-LAYOUT: fixed;" class="datatable">
			
				<tr>
					<th width="100" rows="4">质检事宜详述：</th>
					<td>
					    <textarea id="qcAffairDesc" name="qcAffairDesc" style="width:400px;height:100px"></textarea>
					</td>
				</tr>
				<tr>
					<th width="100" ></th>
					<td><input class="pd5" type="button" name="submitBtn" value="提交" onclick="submitReview(this)" />
				    </td>
				    <td></td>
				</tr>
			</table>
		</form>
	</div>
	