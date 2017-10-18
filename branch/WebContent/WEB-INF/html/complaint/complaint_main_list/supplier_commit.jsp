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

/* $(document).ready(function() { 
    var options = { 
    	beforeSubmit:  check_form,
        success:   success_function,  // post-submit callback 
    }; 
    $('#form').ajaxForm(options); 
}); */
/* 
function check_form(){
	var agencyName = $('#agencyName').val();
	var complaintType = $('#complaintType').val();
	var commitDetail = $('#commitDetail').val();
	if(agencyName == ''){
		alert('请填写供应商名称');
		return false;
	}
	if(commitDetail==""){ 
		alert("请填写沟通内容");
		return false;
	}
	if(commitDetail.length<6){
		
		alert("沟通内容不少于6个字");
		return false;
	}
	return true;
} */

/* function success_function(){
	alert('提交成功');
	parent.location.replace(parent.location.href);
} */

function agencyChange(){
	
	var agencyId=$("#agency").val(); 
	if(agencyId==0){
				   
	  $("#agencyOtherName").show();
	}else{
		
	  $('#agencyOtherName').attr("value",'');   
      $("#agencyOtherName").css('display', 'none'); 
	   
	}
			   
}

function saveChat(input){
	var agencyName ="";
	var agencyId="";
	var supplierCount =$('#supplierCount').val();
	if(supplierCount==0){
		
		 agencyName=$("#agencyName").val();  
		 
	}else{
		
		agencyName = $("#agency").find("option:selected").text();  
		agencyId = $("#agency").val();  
		if(agencyId==0){
			agencyId ="";
			agencyName =$('#agencyOtherName').val();
		}
	}
	var orderId = $('#orderId').val();
	var complaintType = $('#complaintType').val();
	var commitDetail = $('#commitDetail').val();
	var complaintId = $('#complaintId').val();
	var route =$('#route').val();
	var routeId =$('#routeId').val();
	if(agencyName == ''){
		alert('请填写供应商名称');
		return false;
	}
	if(commitDetail==""){ 
		alert("请填写沟通内容");
		return false;
	}
	if(commitDetail.length<6){
		
		alert("沟通内容不少于6个字");
		return false;
	}
	  // 禁用提交按钮
    disableButton(input);
	$.ajax( {
		url : 'complaint-saveSupplierCommit',
		data : {
			complaintId :complaintId,
			complaintType :complaintType,
			agencyId :agencyId,
			agencyName :agencyName,
			orderId :orderId,
			route :route,
			routeId :routeId,
			commitDetail :commitDetail
		},
		type : 'post',
		dataType:'json',
		cache : false,
		success : function(result) {
			if(result)
			{
		    	if(result.retObj == "success")
				{
		    		enableButton(input);
		    		alert("发送成功");
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
			<table width="100%" style="TABLE-LAYOUT: fixed;" class="datatable">
				<input type="hidden" name="complaintId" id="complaintId" value="${complaint.id }" />
				<input type="hidden" name="orderId" id="orderId" value="${complaint.orderId }" />
				<input type="hidden" name="route" id="route" value="${complaint.route }" />
				<input type="hidden" name="routeId" id="routeId" value="${complaint.routeId }" />
				<input type="hidden" name="supplierCount" id="supplierCount" value="${supplierCount}" />
				<tr>
					<th width="100" rows="4">供应商：</th>
					<td>
					<c:if test="${supplierList != null && supplierList.size() > 0}">
						<select class="mr10" name="agency" id="agency" onchange="agencyChange()">
							<c:forEach items="${supplierList}" var="v">
								<option value="${v.code }">${v.name }</option>
							</c:forEach>
							<option value="0">其他</option>
						</select> 
					</c:if>
					<c:if test="${supplierList == null || supplierList.size() == 0}">
					  <input type="text" name ="agencyName" id="agencyName" value="${complaint.agencyName}" />
					</c:if>
					</td>
					<td>
					  <input type="text" name ="agencyOtherName" id="agencyOtherName" value=""  style="display: none"/>
					</td>
				</tr>
				<tr>
					<th width="100" rows="4">类型：</th>
					<td>
					<select class="mr10" id="complaintType" name="complaintType" style="width: 100px;">
						<option value="1"  selected="selected">咨询</option>
						<option value="2"  >投诉</option>
					</select>
					</td>
					<td></td>
				</tr>
				<tr>
					<th width="100" rows="4">沟通内容：</th>
					<td><textarea id="commitDetail" name="commitDetail" rows="5" cols="50"></textarea></td>
						<td></td>
				</tr>
				<tr>
					<th width="100" ></th>
					<td>
						<input class="pd5" type="button" name="save" value="确认" onclick="saveChat(this)" />
					</td>
				    <td></td>
				</tr>
			</table>
		</form>
	</div>
	