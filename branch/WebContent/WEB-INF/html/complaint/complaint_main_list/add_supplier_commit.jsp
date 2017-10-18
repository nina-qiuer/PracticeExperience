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
	<script type="javascript" src="${CONFIG.res_url}script/complaint/complaint/follow_time/follow_time.js" ></script> 
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

$(document).ready(function(){
	
	var flag = $('#upflag').val();

	if(flag == '1'){
		alert("上传失败");
		parent.location.replace(parent.location.href);
	}
	if(flag == '2'){
		alert("没有权限进入房间");
		parent.location.replace(parent.location.href);
	}
	if(flag == '0'){
		alert("上传成功");
		parent.location.replace(parent.location.href);
	}
	
});

function addCommit(input){
	
	var commitDetail = $('#commitDetail').val();
	var complaintId =  $('#complaintId').val();
	var roomId =  $('#roomId').val();
	 var obj  = document.getElementsByName('checkType');
	 for(var i=0;i<obj.length;i++){
		   if(obj[i].checked==true){
			   if(obj[i].value=='1'){
				   
				   if(commitDetail==""){ 
						alert("请填写沟通内容");
						return false;
					}
					if(commitDetail.length<6){
						
						alert("沟通内容不少于6个字");
						return false;
					}
			   }
	  }
	 }
	  // 禁用提交按钮
    disableButton(input);
	$.ajax( {
		url : 'complaint-addSupplierCommit',
		data : {
			complaintId:complaintId,
			commitDetail :commitDetail,
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
		    		parent.location.replace(parent.location.href);
				}else{
					
					alert(result.resMsg);
					//window.parent.parent.location.replace(parent.parent.location.href);
					//parent.location.replace(parent.location.href);
				}
		     }
		 }, error: function() {
             // 解除禁用
             enableButton(input);
         }
	    });
}

function to_change(){
	
	
	 var obj  = document.getElementsByName('checkType');
	 for(var i=0;i<obj.length;i++){
		   if(obj[i].checked==true){
			   
			   if(obj[i].value=='1'){
				   
				   $("#tr_tool_word").show();
				   $("#tr_tool_sumbit").show();
				   $("#tr_tool_file").css('display', 'none'); 
			   }else{
				   
				   $("#tr_tool_word").hide();
				   $("#tr_tool_sumbit").hide();
				   $("#tr_tool_file").css('display', ''); 
			   }
			   
		   }
	 }
}


function getFileSize(obj)
{ 
	var fileSize = obj.files[0].size;
	if(fileSize>20971520){
		alert("请上传小于20M的文件！");
		obj.parentNode.parentNode.remove();
	}
}
</script>
</head>
<body>
	<div class="common-box">
		<form name="form" id="form" method="post"  enctype="multipart/form-data" action="complaint-commit_upload">
			<table width="100%" style="TABLE-LAYOUT: fixed;" class="datatable">
				<input type="hidden" name="complaintId" id="complaintId" value="${complaintId }" />
				<input type="hidden" name="roomId" id="roomId" value="${roomId }" />
				<input type="hidden" name="upflag" id="upflag" value="${upflag }" />
				<tr>
					<th width="100" rowspan="2" >沟通内容：</th>
					<td ><input  type="radio" name ="checkType" value="1" checked="checked" onclick="to_change()">文字  &nbsp&nbsp&nbsp&nbsp<input  type="radio" name ="checkType" value="2" onclick="to_change()"/>图片</td>
				</tr>
				<tr id="tr_tool_word"><td><textarea id="commitDetail" name="commitDetail" rows="5" cols="50"></textarea></td></tr>
				<tr id="tr_tool_file" style="display: none">
					<td ><input type="file" name="toolFile"  onchange="getFileSize(this);"/>&nbsp&nbsp&nbsp&nbsp
						<input type="submit" value="上传">
					</td>
				</tr>
				<tr id="tr_tool_sumbit">
					<th width="100" rowspan="2"></th>
					<td>
						<input class="pd5" type="button" name="save" value="确认" onclick="addCommit(this)" />
					</td>
				</tr>
			</table>
		</form>
	</div>
	