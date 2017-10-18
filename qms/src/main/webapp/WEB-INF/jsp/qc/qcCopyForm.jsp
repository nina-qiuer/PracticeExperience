<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>
<html>
<head>
<script type="text/javascript">
$(document).ready(function() {
	$("#cpoy_form").validate({
		rules:{
			
		        copyId:{
		      	       required:true,
		               digits:true,
		               maxlength:10
		            }
        },
        messages:{
        	copyId:{digits:"请输入整数",required:"请输入质检单号",maxlength:"不能超过10位数"}
        }
	   });
	
});

function doSubmit(input) {
	if(!$('#cpoy_form').valid()){
		return false;
	}
	
	var id =  $('#id').val();
	var copyId =  $('#copyId').val();
	if(id == copyId){
	 layer.alert("不能复制本身!", {icon: 2});
	 return false;
	}
	
	layer.confirm("复制后,会覆盖原来所有内容!", {icon: 7}, function(index){
		 disableButton(input);
		 
		 checkPunishRelation(id, copyId, input);
		 
		 layer.close(index);
	});
}

function checkPunishRelation(id, copyId, input){
	$.ajax({
		url: "qc/qcBill/checkPunishRelation",
		type: "post",
		data: {
			"qcBillId": copyId
		},
		dataType: "json",                
		success: function(result){
			if(result != null ){
				enableButton(input);
				
				if(result.retCode == 0){
					copyQcBill(id, copyId, input);
				}else{
					layer.alert(result.resMsg, {icon: 2});
				}
			}else{
				layer.alert("复制失败，请刷新页面重新操作！", {icon: 2});
			}
		},
		error: function(){
			layer.alert("复制失败，请刷新页面重新操作！", {icon: 2});
		}
	});
}

function copyQcBill(id, copyId, input){
	$.ajax( {
		url : 'qc/qcBill/copyQcBill',
		data : {
			"id": id,
			"copyId": copyId
		},
		type : 'post',
		dataType:'json',
		cache : false,
		success : function(result) {
			if(result){
				enableButton(input);
		    	if(result.retCode == "0"){
		    		
		    		layer.alert("复制成功",{icon: 1,closeBtn: 0},function(){
		    			parent.location.reload();
	    		    	parent.layer.closeAll();
					});
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
<form name="cpoy_form" id="cpoy_form" method="post" action="" >
    <input type="hidden" name="id" id="id" value="${id}">
<table class="datatable">
	<tr>
		<th align="right" width="100"><span style="color: red">*</span>质检单号：</th>
		<td>
		    <input type="text" name="copyId" id="copyId" value="">
		</td>
	</tr>
	<tr>
		<th align="right" width="100">操作：</th>
		<td>
			<input type="button" name="submitBtn" class="blue" style="width:100px" value="提交" id="submitBtn" onclick="doSubmit(this)">
		</td>
	</tr>
</table>
</form>
</body>
</html>