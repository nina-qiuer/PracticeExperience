<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>
<html>
<head>
<link rel="stylesheet" type="text/css" href="res/plugins/KindEditor/css/default.css"/>
<script type="text/javascript" src="res/plugins/KindEditor/kindeditor-min.js"></script>
<script type="text/javascript" src="res/plugins/KindEditor/lang/zh_CN.js"></script>

<script type="text/javascript">
$(document).ready(function(){
	
	$("#qc_form").validate({
		rules:{
		     qcAffairSummary:{
		    	 required:true,
		    	 rangelength:[1,25]
		       },
		       ordId:{
	                digits:true,
	                min:0
				},
				lossAmount:{
				    required:true,
					priceExists:true,
		            min:0
				},
				prdId:{
	                digits:true,
	                min:0
				}
		       
        },
        messages:{
        	qcAffairSummary:{required:"请输入质检事宜概述",rangelength:"字数不超过25"},
        	ordId:{digits:"请输入整数"},
        	prdId:{digits:"请输入整数"},
        	lossAmount:{priceExists:"请输入整数或者最多两位小数",required:"请输入公司损失金额"}
        
        }
		
	});
	jQuery.validator.addMethod("priceExists", function(value, element) {
		var isExists = false;
		if($.trim(value)!=''){
			var compar = new RegExp("^0{1}([.]\\d{1,2})?$|^[1-9]\\d*([.]{1}[0-9]{1,2})?$");
			if(compar.test(value)){
		      
				isExists =true;
			}
			return isExists;
		
		}else{
			
		 return true;
		}
	}, "");
});




function iFrameHeight(iframe) {   
	var ifm= document.getElementById(iframe.id);   
	var subWeb = document.frames ? document.frames[iframe.id].document : ifm.contentDocument;   
	if(ifm != null && subWeb != null) {
	   ifm.height = subWeb.body.scrollHeight;
	   ifm.width = subWeb.body.scrollWidth;
	}   
}  
var editor1;
KindEditor.ready(function(K) {

	editor1 = K.create('#qcAffairDesc', {
		resizeType : 1,
		allowPreviewEmoticons : false,
		allowImageUpload : false,
		items : ['fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', 
				 'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', '|', 'hr', 'fullscreen']
	});
	

});


function doSubmit(input,flag) {
	editor1.sync();
	url ="";
	if(flag ==0){
		
		url ="qc/innerQcBill/addInnerQc";
	}else{
		
		url ="qc/innerQcBill/submitInnerQc";
	}
    if($('#qc_form').valid()){
    	disableButton(input);
		$.ajax( {
			url : url,
			data : 	$('#qc_form').serialize(),
			type : 'post',
			dataType:'json',
			cache : false,
			success : function(result) {
				enableButton(input);
				if(result)
				{
			    	if(result.retCode == "0")
					{
			    		parent.search();
			    		parent.layer.closeAll();
			    		
					 }else{
						
						layer.alert(result.resMsg, {icon: 2});
					}
			     }
			 }
		    });
			    			
	 }    			
	
}
function deleteUpLoad(upId){
	
	$.ajax( {
		url : 'qc/upload/deleteQcUpLoad',
		data : 	{upId:upId} ,
		type : 'post',
		dataType:'json',
		cache : false,
		success : function(result) {
			if(result)
			{
		    	if(result.retCode == "0")
				{
		    		 parent.location.reload();
				 }else{
					
					layer.alert(result.resMsg, {icon: 2});
				}
		     }
		 }
	    });
	
}
</script>
</head>
<body>
<form name="qc_form" id="qc_form" method="post" action="" >
<form:hidden path="qcBill.id"/>
<table class="datatable" >
<tr>
 <th align="right" style="width:100px" >订单号：</th>
 <td >
   <input type="text" name="ordId" id="ordId" value="${qcBill.ordId}" />
 </td>
  <th align="right" style="width:100px" >产品号：</th>
 <td>
   <input type="text" name="prdId" id="prdId" value="${qcBill.prdId}" />
 </td>
</tr>
<tr>
 <th align="right" style="width:100px" >公司损失：</th>
<td >
   <input type="text" name="lossAmount" id="lossAmount" value="${qcBill.lossAmount}" />元
 </td>
 <th align="right" style="width:120px" height="30">质检类型：</th>
		<td >
			<form:select path="qcBill.qcTypeId" id="qcTypeId"   style="width:100px">
		     	<form:options items="${qcTypeList}" itemValue="id" itemLabel="name"/>
			</form:select>
		</td>
</tr>
<tr>
		<th align="right" style="width:100px" height="30">质检事宜概述：</th>
		<td colspan="3">
			<textarea id="qcAffairSummary" name="qcAffairSummary" style="width:700px;height:50px;">${qcBill.qcAffairSummary}</textarea>
		</td>
	</tr>
	<tr>
		<th align="right" width="100" height="30">质检事宜详述：</th>
		<td colspan="3">
			<textarea id="qcAffairDesc" name="qcAffairDesc" style="width:700px;height:100px;visibility:hidden;">${qcBill.qcAffairDesc}</textarea>
		</td>
	</tr>
	<tr>
		<th align="right" width="100">附件：</th>
		<td colspan="3">
		<div>
			<iframe name="attachMain" src="qc/innerQcBill/${qcBill.id}/toShowAttach" width="100%"  scrolling="no" frameborder="0" id="attachMain" onload="iFrameHeight(this)"></iframe>
		</div>
		</td>
	</tr>
	
	<tr>
		<th align="right" width="100" height="30">操作</th>
		<td colspan="3">
			<input type="button" class="blue" value="保存" name="saveBtn" id="saveBtn" onclick="doSubmit(this,0)">
			<input type="button" class="blue" value="提交" name="submitBtn" id="submitBtn" onclick="doSubmit(this,1)">
		</td>
	</tr>
</table>
</form>
</body>
</html>