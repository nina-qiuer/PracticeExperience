<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<link rel="stylesheet" type="text/css" href="res/plugins/KindEditor/css/default.css"/>
<script type="text/javascript" src="res/plugins/KindEditor/kindeditor-min.js"></script>
<script type="text/javascript" src="res/plugins/KindEditor/lang/zh_CN.js"></script>
<title>质检单信息</title>
<style type="text/css">
a:hover{
	color:blue;
}
.left {
	width: 65%;
}

.right {
	width: 30%;
	position: absolute;
	top: 4px;
	right: 15px;
}

.right textarea {
	display: block;
	margin: 5px 0;
	width: 100%;
	height: 150px;
	resize: none;
}

.listtable .noteContent {
	text-align: left;
}

.noteContent p {
	text-indent: 2em;
}

#editArea {
	width: 100%;
}
</style>
<script type="text/javascript">
var textArea="";
$(document).ready(function(){
	$(".accordion").each(function() {
		$(this).accordion({
	    	collapsible: true,
	    	heightStyle: "content"
	    });
	});
});
var editor1;
KindEditor.ready(function(K) {

	editor1 = K.create('#verification', {
		resizeType : 1,
		readonlyMode : true,
		allowPreviewEmoticons : false,
		allowImageUpload : false,
		items : ['fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', 
				 'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', '|', 'hr']
	});
	

});
function returnQcBill(input){
	var remark = $('#remark').val();
	var qcId =$('#qcBillId').val();
	if($.trim(remark)==''){
		
		 layer.alert("备注不能为空!",{icon: 2});
		 return false;
	}
	if($('#return_form').valid()){
		
		var msg = "您确定退回编号[" + qcId + "]的质检单吗？";
		layer.confirm(msg, {icon: 3}, function(index){
			 layer.close(index);
	    	disableButton(input);
				$.ajax( {
					url : 'qc/innerQcBill/returnQcBill',
					data : {
						qcId:qcId,
						remark:remark
					},
					type : 'post',
					dataType:'json',
					cache : false,
					success : function(result) {
						
						if(result)
						{
							enableButton(input);
					    	if(result.retCode == "0")
							{
					    		layer.alert('退回成功',{icon: 6,closeBtn: 0},function(){
									
					    			 window.parent.opener.search();
						    		 window.top.close();    
								});
					    	
					    	
							 }else{
								
								 layer.alert(result.resMsg,{icon: 2});
							}
					     }
					 }
				    });
				
			});
    }
}

function iFrameHeight(iframe) {   
	var ifm= document.getElementById(iframe.id);   
	var subWeb = document.frames ? document.frames[iframe.id].document : ifm.contentDocument;   
	if(ifm != null && subWeb != null) {
	   ifm.height = subWeb.body.scrollHeight;
	   ifm.width = subWeb.body.scrollWidth;
	}   
}   


function upRemark(){
	
	 $("#remark").attr("disabled",false);
	 $('#updateBtn').hide();
	 $('#confBtn').show();
	 $('#cancelBtn').show();
	
}
function cancelRemark(){
	
	var oldRemark = $('#oldRemark').val();
	$("#remark").val(oldRemark);
    $("#remark").attr("disabled",true);
    $('#updateBtn').show();
    $('#confBtn').hide();
	$('#cancelBtn').hide();
}
function confRemark(){
	var qcId = $('#qcBillId').val();
	var remark = $('#remark').val();
	if(remark == $('#oldRemark').val()){
		$("#remark").attr("disabled",true);
		$('#updateBtn').show();
	    $('#confBtn').hide();
		$('#cancelBtn').hide();
		return false;
	}
	$.ajax({
		type : "POST",
			url : "qc/qcBill/updateRemark",
			data:{
				remark:remark,
				qcId:qcId
			},
			dataType:'json',
			cache : false,
			success : function(result) {
				
				if(result)
				{
			    	if(result.retCode == "0")
					{
			    		$('#remark').val(remark);
			    		$('#oldRemark').val(remark);
					 }else{
						 layer.alert(result.resMsg,{icon: 2});
					}
			     }
				
			}
	});
	$("#remark").attr("disabled",true);
	$('#updateBtn').show();
    $('#confBtn').hide();
	$('#cancelBtn').hide();
}
function upVerfic(){
	
	editor1.readonly(false);
	// $("#verification").attr("disabled",false);
	 $('#updateVBtn').hide();
	 $('#confVBtn').show();
	// $('#cancelVBtn').show();
	
}
function confVerfic(){
	
	editor1.sync();
	var qcId = $('#qcBillId').val();
	var verification = $('#verification').val();
	if(editor1.text()==""){
		
		layer.alert("核实情况不能为空", {icon: 2});
		return false;
	}
	$.ajax({
		type : "POST",
			url : "qc/qcBill/updateVerific",
			data:{
				verification : verification,
				qcId:qcId
				},
			dataType:'json',
			cache : false,
			success : function(result) {
				
				if(result)
				{
			    	if(result.retCode == "0")
					{
			    		$('#verification').val(verification);
			    		 layer.alert("保存成功",{icon: 6});
					 }else{
						 
						 layer.alert(result.resMsg,{icon: 2});
					}
			     }
				
			}
	});
	 editor1.readonly();
	//$("#verification").attr("disabled",true);
	$('#updateVBtn').show();
    $('#confVBtn').hide();
	//$('#cancelVBtn').hide();
}


function openWinow(title, url, width, height,top,left) {
	layer.open({
        type: 2,
        shade : [0.5 , '#000' , true],
        maxmin: true,
        title: title,
        content: url,
        offset : [top ,left],
        area: [width+'px', height+'px']
    });
}

function sendQcReport(){
	var qcBillId = $("#qcBillId").val();
	
	$.ajax({
		url: "qc/qcBill/checkPunishRelation",
		type: "post",
		data: {
			"qcBillId": qcBillId
		},
		dataType: "json",                
		success: function(result){
			if(result != null ){
				if(result.retCode == 0){
					openWinow('质检报告预览', 'qc/innerQcBill/${qcBill.id}/sendPreviewEmail', 1250, 650,'15%','25%');
				}else{
					layer.alert(result.resMsg, {icon: 2});
				}
			}else{
				layer.alert("发送失败，请刷新页面重新操作！", {icon: 2});
			}
		},
		error: function(){
			layer.alert("发送失败，请刷新页面重新操作！", {icon: 2});
		}
	});
}

</script>
</head>
<body>

<div id="qcReport">
<div class="accordion">
<h3>质检单信息</h3>
<div>
<form name="return_form" id="return_form" method="post" action="">
<input type="hidden" name="qcBillId" id="qcBillId" value="${qcBill.id}">
<input type="hidden"  id ="oldRemark" name="oldRemark" value="${qcBill.remark}">
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
	<table class="datatable">
	<tr>
		<th width="80">质检单号：</th>
		<td >${qcBill.id}</td>
		<th width="80">产品编号：</th>
		<td class="prdId">${qcBill.prdId}</td>
		<th width="80">订单号：</th>
		<td class="orderId">${qcBill.ordId}</td>
		<th width="80">公司损失：</th>
		<td>${qcBill.lossAmount}</td>
		<th width="80">申请人：</th>
		<td>${qcBill.addPerson}</td>
	</tr>
	<tr>
	<th width="80">质检状态：</th>
		<td>
		  <c:if test= "${qcBill.state==1}">发起中</c:if>
		  <c:if test= "${qcBill.state==2}">待分配</c:if>
		  <c:if test= "${qcBill.state==3}">质检中</c:if>
		  <c:if test= "${qcBill.state==4}">已完成</c:if>
		  <c:if test= "${qcBill.state==5}">已撤销</c:if>
		</td>
    	<th width="80">质检类型：</th>
		<td>${qcBill.qcTypeName}</td>
		<th width="80">质检员：</th>
		<td>${qcBill.qcPerson}</td>
		<th width="80">提交时间：</th>
		<td><fmt:formatDate value="${qcBill.submitTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
		<th width="80">分配时间：</th>
		<td><fmt:formatDate value="${qcBill.distribTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	</tr>
	<tr>
	
		<th width="100">质检事宜概述：</th>
		<td colspan="9">${qcBill.qcAffairSummary}</td>
	</tr>
	<tr>
		<th width="100">质检事宜详述：</th>
		<td colspan="9">${qcBill.qcAffairDesc}</td>
	</tr>
	<c:if test="${attachList!=null&& fn:length(attachList)>0}">
	<tr>
	<th>附件：</th>
	<td colspan="9">
		<table>
			<tr>
				<c:forEach items="${attachList}" var="upload" >
				  <td>
				     <a href="${upload.path}" target="_blank">${upload.name}</a>
				   </td>
			      </c:forEach>
			   </tr>
		 </table>
	  </td>
	</tr>
	</c:if>
	<tr>
	<th align="left">核实情况：</th>
	<td colspan=10>
	  <textarea id="verification" name="verification" style="width:700px;height:180px;visibility:hidden;">${qcBill.verification}</textarea>
	  <input type="button" class="blue" id="updateVBtn" name="updateVBtn" value="修改" onclick="upVerfic()">
	  <input type="button" class="blue" id="confVBtn" name="confVBtn"   value="确认"  style="display:none" onclick="confVerfic()">
	</td>
	</tr>
	<tr>
	<th align="left">备注：</th>
	<td colspan=10>
	  <textarea id="remark" name="remark" disabled="disabled" rows="2" cols="100px" >${qcBill.remark}</textarea><br>
	  <input type="button" class="blue" id="updateBtn" name="updateBtn" value="修改" onclick="upRemark()">
	  <input type="button" class="blue" id="confBtn" name="confBtn"   value="确认"  style="display:none" onclick="confRemark()">
	  <input type="button" class="blue" id="cancelBtn" name="cancelBtn"  value="取消"  style="display:none" onclick="cancelRemark()">
	</td>
	</tr>
	 <c:if test= "${qcBill.state==3}">
	<tr>
	<th width="80">操作：</th>
	<td colspan="10">
	<input type="button" value="退回" name="returnBtn" id="returnBtn" class="blue" style="width:100px"  
	    onclick="returnQcBill(this)"/>
	</td>
	</tr>
	</c:if>
	</table>
</form>
</div>
</div>
<div class="accordion">
	 <h3>质量问题单</h3>
	 <div>
	 <iframe name="qualityProblem" src="qc/qualityProblem/4/${qcBill.id}/toQuestion" width="100%" scrolling="no" frameborder="0" id="qualityProblem" onload="iFrameHeight(this)"></iframe>
	 </div>
</div>
<div class="accordion">
	 <h3>处罚单</h3>
	 <div>
	 <iframe name="punishBill" src="qc/qualityProblem/${qcBill.id}/toPunishBill" width="100%" scrolling="no" frameborder="0" id="punishBill" onload="iFrameHeight(this)"></iframe>
	 </div>
</div>
<br>
<div>
<table width="100%">
<tr>
	<td   align="right">
		<input type="button" class="green" value="发送质检报告" style="height:40px;" onclick="sendQcReport()"/>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	</td>
</tr>
 </table> 
</div>
</div>
</body>
</html>
