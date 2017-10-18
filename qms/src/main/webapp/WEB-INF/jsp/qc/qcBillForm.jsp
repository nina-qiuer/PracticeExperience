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
			cmpId:{
				        required:true,
				        digits:true,
				        min:1
		          }
		 
        },
        messages:{
        	
        	cmpId:{required:"请输入单号",digits:"请输入整数",min:"单号不能为0"}
        	
        }
		
	});
	
});


function doSubmit(input) {
	var cmpId = $('#cmpId').val();
	
    if($('#qc_form').valid()){
    	disableButton(input);
		$.ajax( {
			url : 'qc/qcBill/saveQc',
			data : {
				
				cmpId : cmpId
			},
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
</script>
</head>
<body>
<form name="qc_form" id="qc_form" method="post" action="" >
<table class="datatable" >
<tr>
<th align="right" style="width:100px" height="30"><span style="color: red">*</span>投诉单号：</th>
 <td>
   <input type="text" name="cmpId" id="cmpId" value="" />
 </td>
</tr>

	<tr>
		<th align="right" width="100" height="30">操作</th>
		<td >
			<input type="button" class="blue" value="提交" name="submitBtn" id="submitBtn" onclick="doSubmit(this)">
		</td>
	</tr>
</table>
</form>
</body>
</html>