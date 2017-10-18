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
function revokeQcBill(qptName){
	var remark = qptName;
	var qcId =$('#qcBillId').val();
	if($.trim(remark)==''){
		
		 layer.alert("备注不能为空!",{icon: 2});
		 return false;
	}
	if($('#revoke_form').valid()){
		
		var msg = "您确定撤销编号[" + qcId + "]的质检单吗？";
		layer.confirm(msg, {icon: 3}, function(index){
			 layer.close(index);
	    //	disableButton(input);
				$.ajax( {
					url : 'qc/qcBill/revokeQcBill',
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
							//enableButton(input);
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

/* function updateImportantFlag(id,importantFlag){
	$.ajax({
		type:'post',
		url:'qc/qcBill/'+id+'/updateImportantFlag',
		data:{importantFlag:importantFlag},
		success:function(data){
			if(importantFlag == 1) {
				$('#markImportant').hide();
				$('#cancelImportant').show();
			}else {
				$('#cancelImportant').hide();
				$('#markImportant').show();
			}
		}
	});
} */

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
/* function cancelVerfic(){
	
	var oldVerfic = $('#oldVerfic').val();
	$("#verification").val(oldVerfic);
   // $("#verification").attr("disabled",true);
   editor1.readonly();
    $('#updateVBtn').show();
    $('#confVBtn').hide();
	$('#cancelVBtn').hide();
} */
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
/* 
function sendEmail(input){
	
	if(editor1.text()==""){
		
		layer.alert("核实情况不能为空", {icon: 2});
		return false;
	}
	
	var id = '${qcBill.id}';
	var reEmail  = $('#reEmails').val();
	var ccEmail  = $('#ccEmails').val();
	if($.trim(reEmail)==''||$.trim(ccEmail)==''){
		
		layer.alert("收件人、抄送人不能为空", {icon: 2});
		return false;
	}
	var  reEmailT = reEmail.split(";");//去除最后一个分号
	var  ccEmailT = ccEmail.split(";");//去除最后一个分号
	var  compar = new RegExp("^(\\w)+([-+.]\\w+)*@tuniu\.com$");//判断tuniu.com邮箱正则表达式
	//对收件人邮箱进去判断
		for(var i=0;i<reEmailT.length;i++){
		if(!compar.test(reEmailT[i])){
			layer.alert("收件人中"+reEmailT[i]+"不符合要求!", {icon: 2});
			return false;
			break;
		}
	}
	//对抄送人邮箱进去判断
	for(var i=0;i<ccEmailT.length;i++){
		if(!compar.test(ccEmailT[i])){
			layer.alert("抄送人中"+ccEmailT[i]+"不符合要求!",{icon: 2});
			return false;
			break;
		}
	}
	var str = document.getElementById("qcState");
	var qcState = 0;
	if(str.checked){
		
		qcState = str.value;
	 }
	var msg = "您确定发送该质检报告吗？";
	layer.confirm(msg, {icon: 3}, function(index){
		 disableButton(input);
		 layer.close(index);
	$.ajax({
		type:'post',
		url:'qc/qcBill/'+id+'/sendEmail',
		data:{'title':title.value,'reEmails':reEmails.value,'ccEmails':ccEmails.value,qcState:qcState},
		cache : false,
		success:function(result){
			enableButton(input);
			if(result)
			{
		    	if(result.retCode == "0")
				{
		    		layer.alert('发送成功',{icon: 6,closeBtn: 0},function(){
						
						 window.parent.opener.search();
						//window.parent.opener.location.href=window.parent.opener.location.href;
			    		 window.top.close();  
					});
		    		
				 }else{
					 
					 layer.alert(result.resMsg,{icon: 2});
				}
		     }
			
			
		}
		});
	});
} */

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

function searchReaload(){//重新加载辅助信息
	window.frames['auxiliaryInfo'].location.reload();
}

//修改投诉等级
function alterQcLevel(qcBillId){
	var level = $("#importantFlag").html();
	var flag = $("#alterSecondLevel").val();
	if (level == 2 && "false" == flag) {
		layer.alert("无权限修改，经理已修改到二级！", {
			icon : 2
		});
		return false;
	}else{
		var url = 'qc/qcBill/' + qcBillId + '/toAlterQcLevel';
		openWin('修改质检等级', url, 400, 200);
	}
}

//修改质检等级回调函数
function successCallBack(level){
	$("#importantFlag").html(level);
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
					openWinow('质检报告预览', 'qc/qcBill/${qcBill.id}/sendPreviewEmail', 1250, 650,'15%','25%');
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
<form name="revoke_form" id="revoke_form" method="post" action="">
<input type="hidden" name="qcBillId" id="qcBillId" value="${qcBill.id}">
<input type="hidden"  id ="oldRemark" name="oldRemark" value="${qcBill.remark}">
<input type="hidden"  id ="alterSecondLevel" name="alterSecondLevel" value="${fn:contains(loginUser_WCS,'QC_ALTER_SECOND_LEVEL')}">
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
		<c:if test="${qcBill.cmpId > 0}">
			<th width="80">订单号：</th>
			<td>${qcBill.ordId}</td>
			<th width="80">投诉单号：</th>
			<td>${qcBill.cmpId}</td>
		</c:if>
		<c:if test="${qcBill.cmpId == 0}">
			<th width="80">订单号：</th>
			<td colspan="3">${qcBill.ordId}</td>
		</c:if>
	</tr>
	<tr>
	<th width="80">质检状态：</th>
		<td>
		  <c:if test= "${qcBill.state==1}">发起中</c:if>
		  <c:if test= "${qcBill.state==2}">待分配</c:if>
		  <c:if test= "${qcBill.state==3}">质检中</c:if>
		  <c:if test= "${qcBill.state==4}">已完成</c:if>
		  <c:if test= "${qcBill.state==5}">已撤销</c:if>
		  <c:if test= "${qcBill.state==6}">已待结</c:if>
		  <c:if test= "${qcBill.state==7}">审核中</c:if>
		</td>
		<th width="80">质检员：</th>
		<td>${qcBill.qcPerson}</td>
		<th width="80">添加时间：</th>
		<td><fmt:formatDate value="${qcBill.addTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
		<th width="80">分配时间：</th>
		<td><fmt:formatDate value="${qcBill.distribTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
		<th width="80">完成时间：</th>
		<td  width="200"><fmt:formatDate value="${qcBill.finishTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	</tr>
	<tr>
		<th width="80">申请人：</th>
		<td>${qcBill.addPerson}</td>
		<th width="80">质检等级：</th>
		<td id="importantFlag">${qcBill.importantFlag}</td>
		<th width="80">质检事宜：</th>
		<td colspan="6">${qcBill.qcAffairDesc}</td>
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
	  <!--<input type="button" class="blue" id="cancelVBtn" name="cancelVBtn"  value="取消"  style="display:none" onclick="cancelVerfic()">  -->
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
	 <c:if test= "${qcBill.state==3||qcBill.state==6}">
	<tr>
	<th width="80">操作：</th>
	<td colspan="10">
		<c:if test= "${qcBill.state==3}">
			<input type="button" value="一键复制" name="copyBtn" id="copyBtn" class="blue" style="width:100px"  
			    onclick="openWin('匹配质检单', 'qc/qcBill/${qcBill.id}/getCopyQcBill',  450, 250)"/>
		 </c:if>
		<input type="button" value="撤销" name="revokeBtn" id="revokeBtn" class="blue" style="width:100px"  
		   onclick="openWin('问题类型', 'qc/qualityProblem/1/getNoQpType',  600, 400)"/>
		<input type="button" class="blue" value="修改质检等级" onclick="alterQcLevel(${qcBill.id})"/>
		<%-- <img id="markImportant" class="img_btn" alt="标记为重要" title="标记为重要" src="res/img/not_important.png" width="20px" height="20px"  <c:if test="${qcBill.importantFlag ==1 }">style="display:none"</c:if>  onclick="updateImportantFlag(${qcBill.id},1)"/>
			<img  id="cancelImportant" class="img_btn" alt="取消重要标记" title="取消重要标记" src="res/img/important.png" width="20px" height="20px"  <c:if test="${qcBill.importantFlag ==0 }">style="display:none"</c:if>  onclick="updateImportantFlag(${qcBill.id},0)"/>
		--%>
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
	 <iframe name="auxiliaryInfo" src="qc/qcBill/${qcBill.id}/toAuxiliaryInfo" width="100%" id="iframepage" scrolling="no" frameborder="0" onload="iFrameHeight(this)"></iframe>
	 </div>
</div>
<div class="accordion">
	 <h3>质量问题单</h3>
	 <div>
	 <iframe name="qualityProblem" src="qc/qualityProblem/1/${qcBill.id}/toQuestion" width="100%" scrolling="no" frameborder="0" id="qualityProblem" onload="iFrameHeight(this)"></iframe>
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
	<td align="right">
	 	<input type="button" class="green" value="发送质检报告" style="height:40px;" onclick="sendQcReport()"/>
	    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	</td>
	 </tr>
 </table> 
</div>
</div>
</body>
</html>
