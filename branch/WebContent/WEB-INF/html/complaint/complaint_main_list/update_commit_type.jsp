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

function closeParent(){
	self.parent.easyDialog.close();
	
}


function updateType(input){
	
	var complaintId =$('#complaintId').val(); 
	var roomId =$('#roomId').val(); 
	var complaintType =$('#complaintType').val(); 
	if(complaintType == $('#typeN').val()){
		
		alert("类型未修改!");
		return false;
	}
	 disableButton(input);
	$.ajax( {
		url : 'complaint-updateCommit',
		data : {
			complaintId:complaintId,
			complaintType:complaintType,
			roomId:roomId
		},
		type : 'post',
		dataType:'json',
		cache : false,
		success : function(result) {
			if(result)
			{
				 enableButton(input);
		    	if(result.retObj == "success")
				{
		    		alert("发送成功");
		    		self.parent.easyDialog.close();
		    		self.parent.parent.easyDialog.close();
		    		window.parent.parent.location.replace(parent.parent.location.href);
				}else{
					
					alert(result.resMsg);
					//self.parent.easyDialog.close();
		    		//parent.location.replace(parent.location.href);
				}
		     }
		 },error: function() {
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
			<table width="100%" style="TABLE-LAYOUT: fixed;" class="datatable">
				<input type="hidden" name="complaintId" id="complaintId" value="${chat.complaintId }" />
					<input type="hidden" name="typeN" id="typeN" value="${chat.typeNum }" />
						<input type="hidden" name="roomId" id="roomId" value="${chat.roomId }" />
				<tr>
					<th width="100" rows="4">类型：</th>
					<td>
					<select class="mr10" id="complaintType" name="complaintType" style="width: 100px;">
						<option value="1"  <c:if test="${chat.typeNum==1}">selected</c:if> >咨询</option>
						<option value="2"  <c:if test="${chat.typeNum==2}">selected</c:if> >投诉</option>
					</select>
					</td>
				</tr>
				<tr>
					<th width="100"></th>
					<td><input class="pd5" type="button" name="save" value="确认" onclick="updateType(this)" /> &nbsp&nbsp&nbsp&nbsp
				    <input class="pd5" type="button" name="close" value="关闭" onclick="closeParent()" /></td>
				</tr>
			</table>
		</form>
	</div>
	