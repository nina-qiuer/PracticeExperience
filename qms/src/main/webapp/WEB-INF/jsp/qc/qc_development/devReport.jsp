<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<link rel="stylesheet" type="text/css" href="res/plugins/KindEditor/css/default.css"/>
<script type="text/javascript" src="res/plugins/KindEditor/kindeditor-min.js"></script>
<script type="text/javascript" src="res/plugins/KindEditor/lang/zh_CN.js"></script>
<title>研发质检单信息</title><style type="text/css">
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

.listtable  .noteContent {
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
var realNameArr = new Array();
var textArea="";
$(document).ready(function(){
	userAutoComplete();
	$(".accordion").each(function() {
		$(this).accordion({
	    	collapsible: true,
	    	heightStyle: "content"
	    });
	});
	$('.selfContent')
	.dblclick(
			updateNote
			);
	$("#revoke_form").validate({
		rules:{
			remark:{
                  required:true,
                  rangelength:[1,1000]
			}
		
        },
        messages:{
        	
        	remark:{required:"请输入撤销备注"}
        }
		
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
	if($('#revoke_form').valid()){
		
		var msg = "您确定撤销编号[" + qcId + "]的质检单吗？";
		layer.confirm(msg, {icon: 3}, function(index){
	    	disableButton(input);
	    	 layer.close(index);
				$.ajax( {
					url : 'qc/resDevQcBill/revokeDevBill',
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
function assign(input){
	var isExists = false;
	var devProPeople =$('#devProPeople').val();
	var qcId =$('#qcBillId').val();
	if($.trim(devProPeople)==''){
		
		 layer.alert("请输入分配人!",{icon: 2});
		return  false; 
		
	}
	for(var i=0;i<realNameArr.length;i++){
		
		if(devProPeople == realNameArr[i]){
			
			isExists = true;
		}
	}
	if(isExists ==false){
		
		layer.alert("分配人不存在!",{icon: 2});
		return  false; 
	}
	disableButton(input);
	$.ajax( {
		url : 'qc/resDevQcBill/assignPerson',
		data : {
			
			qcId : qcId,
			devProPeople : devProPeople
			
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
		    		 layer.alert("分配成功!",{icon: 1});
		    	
				 }else{
					
					 layer.alert(result.resMsg,{icon: 2});
				}
		     }
		 }
	    });
}
function userAutoComplete() {
	var userArr = new Array();
	<c:forEach items="${userNames}" var="userMap">
		userArr.push({
			label: '${userMap.label}',
			value: '${userMap.realName}'
		});
		realNameArr.push('${userMap.realName}');
	</c:forEach>
if('${loginUser_WCS}'.indexOf('DEV_REPORT') !=-1){	
	$("#devProPeople").autocomplete({
	    source: userArr,
	    autoFocus : true
	}).data( "ui-autocomplete" )._renderItem = function( ul, item ) {
		return $( "<li>" )
        .append( "<a>" + item.value + "</a>" )
        .appendTo( ul );
	};
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

function sendEmail(input){
	
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
	var msg = "您确定发送该质检报告吗？";
	layer.confirm(msg, {icon: 3}, function(index){
		 disableButton(input);
		 layer.close(index);
	$.ajax({
		type:'post',
		url:'qc/resDevQcBill/'+id+'/sendEmail',
		data:{'title':title.value,'reEmails':reEmails.value,'ccEmails':ccEmails.value},
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
}

function deleteSystem(id){
	
	$.ajax( {
		url : 'qc/resDevQcBill/deleteSystem',
		data : 	{id:id} ,
		type : 'post',
		dataType:'json',
		cache : false,
		success : function(result) {
			if(result)
			{
		    	if(result.retCode == "0")
				{
		    		 //location.reload();
		    		window.location.reload();
		    		// parent.frames['qualityProblem'].location.reload();
				 }else{
					
					layer.alert(result.resMsg, {icon: 2});
				}
		     }
		 }
	    });
	
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
//	$('#cancelVBtn').hide();
}



function updateImportantFlag(id,importantFlag){
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
}

</script>
</head>
<body >
<input type="hidden" name="qcBillId" id="qcBillId" value="${qcBill.id}">
<input type="hidden"  id ="remark" name="remark" value="${qcBill.remark}">
<div id="qcReport">
<form name="revoke_form" id="revoke_form" method="post" action="" >
	<div class="accordion">
	  <h3>研发质检单信息</h3>
	  <div>
	  <table class="datatable">
		<tr>
			<th align="left" width="50">质检单号：</th>
			<td  width="85" height="30px">
			 ${qcBill.id}
			</td>
			<th align="left" width="50">影响时长：</th>
			<td  width="85">
			 ${qcBill.influenceTime}分钟
			</td>
			<th align="left" width="50">影响结果：</th>
			<td  width="85">
			 ${qcBill.influenceResult}
			</td>
		</tr>
		<tr>
		<th align="left" width="50">质检员：</th>
			<td  width="85">
				${qcBill.qcPerson}
			</td>
		<th align="left" width="50">质检状态：</th>
			<td  width="85"  height="30px">
			  <c:if test= "${qcBill.state==3}">
			    	 质检中
			  </c:if>
			  <c:if test= "${qcBill.state==4}">
			   	  	已完成
			  </c:if>
			  <c:if test= "${qcBill.state==5}">
			   	 	 已撤销
			  </c:if>
			</td>
			<th align="left" width="50">质量问题等级：</th>
			 <c:if test="${qcBill.qualityEventClass =='S'}">
		   	    <td width="85" style="color:red ;font-weight: bold">${qcBill.qualityEventClass }级  </td>
		   	 </c:if>
		    <c:if test="${qcBill.qualityEventClass !='S'}">
		   		 <td width="85" style="font-weight: bold">${qcBill.qualityEventClass }级  </td>
		    </c:if>
		  </tr>
		<tr>
			<th align="left" width="50">分配时间：</th>
			<td  width="85">
				 <fmt:formatDate value="${qcBill.distribTime}" pattern="yyyy-MM-dd HH:mm:ss" /> 
			</td>
			<th align="left" width="50">故障发生时间：</th>
			<td  width="85">${qcBill.faultHappenTime}</td>
			<th align="left" width="50">故障完成时间：</th>
			<td  width="85">${qcBill.faultFinishTime}</td>
		</tr>
		<tr>
			<th align="left" width="50">故障来源：</th>
			<td  width="85">${qcBill.faultSourceName}</td>
		  	<th  align="left" width="50">影响系统</th>
		  	<td colspan="3">
		     <table>
				<tr>
				  <c:forEach items="${qcBill.influenceSystem}" var="influence" >
				   <td>
				      ${influence.influenceSystem}
				       <c:if test= "${fn:contains(loginUser_WCS,'DEV_REPORT')}">
				       <input type="image" src="res/plugins/validation/images/unchecked.gif" onclick="deleteSystem(${influence.id});return false;" >
				       </c:if>
				    </td>
				   </c:forEach>
		   	     <c:if test= "${fn:contains(loginUser_WCS,'DEV_REPORT')}">
				 <td><input type="button" name="addbtn" class="blue" value="添加影响系统" onclick="openWin('添加影响系统','qc/resDevQcBill/${qcBill.id}/toAddSystem','450','200')"></td>
				</c:if>
				 </tr>
			 </table>
		  </td>
		</tr>
		<tr>
		    <th align="left" width="50">备注：</th>
			<td colspan="5" class="shorten30" width="150" title=" ${qcBill.remark}" >
				 ${qcBill.remark}		
			</td>
		</tr>
		<tr>
			<th>故障描述：</th>
			<td colspan="5">${qcBill.qcAffairDesc}</td>
		</tr>
		<tr>
		<th align="left">核实情况：</th>
		<td colspan=10>
		  <textarea id="verification" name="verification" style="width:700px;height:180px;visibility:hidden;" >${qcBill.verification}</textarea>
		  <c:if test= "${fn:contains(loginUser_WCS,'DEV_REPORT')}">
		  <input type="button" class="blue" id="updateVBtn" name="updateVBtn" value="修改" onclick="upVerfic()">
		  <input type="button" class="blue" id="confVBtn" name="confVBtn"   value="确认"  style="display:none" onclick="confVerfic()">
		  <!-- <input type="button" class="blue" id="cancelVBtn" name="cancelVBtn"  value="取消"  style="display:none" onclick="cancelVerfic()"> -->
		  </c:if>
		</td>
		</tr>
		 <c:if test= "${qcBill.state==3 &&fn:contains(loginUser_WCS,'DEV_REPORT')}">
			<tr>
				<th align="left" width="50">操作：</th>
					<td colspan="7">
					  <input type="text" id="devProPeople" name="devProPeople" value=""/>
					  <input type="button" value="分配研发处理人" name="assginBtn" id="assginBtn" class="blue"  onclick="assign(this)">&nbsp;&nbsp;&nbsp;&nbsp;
					    <input  type="button" value="修改质检单" name="qcBtn" id="qcBtn" class="blue" style="width:100px" onclick="openWin('修改质检单','qc/resDevQcBill/${qcBill.id}/updateQcBill','850','450')"/>&nbsp;&nbsp;&nbsp;&nbsp;
					  <input  type="button" value="撤销" name="revokeBtn" id="revokeBtn" class="blue" style="width:100px"  onclick="revokeQcBill(this)"/>
					<img  id="markImportant" class="img_btn" alt="标记为重要" title="标记为重要" src="res/img/not_important.png" width="20px" height="20px"  <c:if test="${qcBill.importantFlag ==1 }">style="display:none"</c:if>  onclick="updateImportantFlag(${qcBill.id},1)"/>
					<img  id="cancelImportant" class="img_btn" alt="取消重要标记" title="取消重要标记" src="res/img/important.png" width="20px" height="20px"  <c:if test="${qcBill.importantFlag ==0 }">style="display:none"</c:if>  onclick="updateImportantFlag(${qcBill.id},0)"/>
					</td>
			</tr>
		</c:if>
		
	</table>
	</div>
	</div>
	</form>
	<div class="accordion">
		 <h3>辅助信息</h3>
		 <div>
		 <iframe name="auxiliaryInfo" src="qc/resDevQcBill/toAuxiliaryInfo?qcBillId=${qcBill.id}" width="100%" id="iframepage" scrolling="no" frameborder="0"  onload="iFrameHeight(this)"></iframe>
		 </div>
	</div>
	<div class="accordion">
		 <h3>故障单</h3>
		 <div>
		 <iframe name="devFault" src="qc/devFault/list?qcBillId=${qcBill.id}" width="100%"  scrolling="no" frameborder="0" id="qualityProblem" onload="iFrameHeight(this)"></iframe>
		 </div>
	</div>
 <c:if test= "${fn:contains(loginUser_WCS,'DEV_REPORT')}">
	<div class="accordion">
		 <h3>处罚单</h3>
		 <div>
		 <iframe name="punishBill" src="qc/resDevQcBill/toPunishBill?qcBillId=${qcBill.id}" width="100%"  scrolling="no" frameborder="0" id="punishBill" onload="iFrameHeight(this)"></iframe>
		 </div>
	</div>
	<div class="accordion">
			<h3>发送质检报告</h3>
			<div>
			<form name="emailForm" id="emailForm" method="post" action="">
			<table width="100%" class="datatable">
			<tr>
			<th align="right" width="80">邮件主题：</th>
				<td colspan=4><input style="font-size: 14px;width:700px;height:30px" type="text" name="title" id="title"  name ="title"  value="${emailTitle}"/></td>
			</tr>
			<tr>
				<th align="right" width="80">收件人：</th>
				<td>
					<textarea name="reEmails" id="reEmails" style="font-size: 14px;" rows="3" cols="60">${reEmails }</textarea>
				</td>
				<th align="right" width="80">抄送人：</th>
				<td>
					<textarea name="ccEmails" id="ccEmails" style="font-size: 14px;" rows="3" cols="60">${ccEmails}</textarea>
				</td>
				<td align="center"><input type="button" class="blue"  value="选择收件人和抄送人" onclick="openWin('选择质检邮件模板', 'qc/mailConfig/${qcBill.addPersonId}/toChoose', 850, 400)">
				<input type="button" value="发送" id='sendButton' name="sendButton" class="blue" onclick="sendEmail(this)"></td>
			</tr>
			</table>
			</form>
		</div>
</div>
</c:if>
</div>
</body>
</html>
