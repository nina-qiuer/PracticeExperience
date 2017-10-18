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

function revokeQcBill(input){
	var remark =$('#remark').val();
	var qcId =$('#qcBillId').val();
	if($('#operate_form').valid()){
		
		var msg = "您确定撤销编号[" + qcId + "]的质检单吗？";
		layer.confirm(msg, {icon: 3}, function(index){
	    	disableButton(input);
	    	 layer.close(index);
				$.ajax( {
					url : 'qc/operateQcBill/revokeOperateBill',
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

					    		layer.alert('撤销成功',{icon: 6,closeBtn: 0},function(){
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


function openWinow(title, url, width, height,top,left) {
	layer.open({
        type: 2,
        shade: false,
        maxmin: true,
        title: title,
        content: url,
        offset : [top ,left],
        area: [width+'px', height+'px']
    });
}
function upVerfic(){
	
	editor1.readonly(false);
	 $('#updateVBtn').hide();
	 $('#confVBtn').show();
	
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
	$('#updateVBtn').show();
    $('#confVBtn').hide();
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

function commitBill(input){
	
	if(editor1.text()==""){
		
		layer.alert("核实情况不能为空", {icon: 2});
		return false;
	}
	var qcId = $('#qcBillId').val();
	var msg = "您确定提交该质检单吗？";
	layer.confirm(msg, {icon: 3}, function(index){
		 disableButton(input);
		 layer.close(index);
	$.ajax({
		type:'post',
		url:'qc/operateQcBill/commitBill',
		data:{
			
			'qcId':qcId,
		},
		cache : false,
		success:function(result){
			enableButton(input);
			if(result)
			{
		    	if(result.retCode == "0")
				{
		    		layer.alert('提交成功',{icon: 6,closeBtn: 0},function(){
						
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

</script>
</head>
<body>

<div id="qcReport">
<div class="accordion">
<h3>质检单信息</h3>
<div>
<form name="operate_form" id="operate_form" method="post" action="">
<input type="hidden" name="qcBillId" id="qcBillId" value="${qcBill.id}">
<input type="hidden"  id ="oldRemark" name="oldRemark" value="${qcBill.remark}">
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
	<table class="datatable">
	<tr>
		<th width="80">质检单号：</th>
		<td width="70">${qcBill.id}</td>
		<th width="80">产品编号：</th>
		<td>${qcBill.prdId}</td>
		<th width="80">团期：</th>
		<td>${qcBill.groupDate}</td>
		<th width="80">订单号：</th>
		<td>${qcBill.ordId}</td>
		<th width="80">质检状态：</th>
		<td>
		  <c:if test= "${qcBill.state==1}">发起中</c:if>
		  <c:if test= "${qcBill.state==2}">待分配</c:if>
		  <c:if test= "${qcBill.state==3}">质检中</c:if>
		  <c:if test= "${qcBill.state==4}">已完成</c:if>
		  <c:if test= "${qcBill.state==5}">已撤销</c:if>
		</td>
	</tr>
	<tr>
		<th width="80">质检员：</th>
		<td>${qcBill.qcPerson}</td>
		<th width="80">添加时间：</th>
		<td><fmt:formatDate value="${qcBill.addTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
		<th width="80">分配时间：</th>
		<td><fmt:formatDate value="${qcBill.distribTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
		<th width="80">完成时间：</th>
		<td colspan="3"><fmt:formatDate value="${qcBill.finishTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	</tr>
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
	<input  type="button" value="撤销" name="revokeBtn" id="revokeBtn"
	        class="blue" style="width:100px"  onclick="revokeQcBill(this)"/>
	</td>
	</tr>
	</c:if>
	</table>
</form>
</div>
</div>
<div class="accordion">
	 <h3>辅助信息</h3>
	 <div>
	 <iframe name="auxiliaryInfo" src="qc/operateQcBill/${qcBill.id}/toAuxiliaryInfo" width="100%" id="iframepage" scrolling="no" frameborder="0" onload="iFrameHeight(this)"></iframe>
	 </div>
</div>
<c:if test="${null!=template}">
<div class="accordion">
	 <h3>辅助表信息</h3>
	 <div style="overflow-y:hidden">
	 <iframe name="auxiliaryBill" src="qc/operateQcBill/${template.id}/${qcBill.id}/toAuxiliaryBill" width="100%" id="auxiliaryBill"  scrolling="no" frameborder="0" onload="iFrameHeight(this)"></iframe>
	 </div>
</div>
</c:if>
<div class="accordion">
	 <h3>质量问题单</h3>
	 <div>
	 <iframe name="qualityProblem" src="qc/qualityProblem/${qcBill.id}/toOperQuestion" width="100%" scrolling="no" frameborder="0" id="qualityProblem" onload="iFrameHeight(this)"></iframe>
	 </div>
</div>
<div class="accordion">
	 <h3>处罚单</h3>
	 <div>
	 <iframe name="punishBill" src="qc/qualityProblem/${qcBill.id}/toOperPunishBill" width="100%" scrolling="no" frameborder="0" id="punishBill" onload="iFrameHeight(this)"></iframe>
	 </div>
</div>
<br>
<div>
<table width="100%">
<tr>
<td   align="right">
 	<input type="button" class="green" name="submitbtn" id="submitbtn" value="提交" style="height:40px;" 
		 onclick="commitBill(this)">
		  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</td>
	 </tr>
 </table> 
</div>
</div>
</body>
</html>
