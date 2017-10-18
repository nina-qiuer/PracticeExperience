<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>
<html>
<head>
<link rel="stylesheet" type="text/css" href="res/plugins/KindEditor/css/default.css"/>
<script type="text/javascript" src="res/plugins/KindEditor/kindeditor-min.js"></script>
<script type="text/javascript" src="res/plugins/KindEditor/lang/zh_CN.js"></script>

<script type="text/javascript">
$(document).ready(function(){
	$("#jira_form").validate({
		rules:{
			qcId:{
				        required:true,
				        digits:true,
				        min:1
		          }
		 
        },
        messages:{
        	
        	qcId:{required:"请输入单号",digits:"请输入整数",min:"单号不能为0"}
        	
        }
		
	});
	
});


function doSubmit(input) {
	var jiraId = $('#jiraIds').val();
	var qcId = $('#qcId').val();
	
    if($('#jira_form').valid()){
    	disableButton(input);
		$.ajax( {
			url : 'qc/jiraRelation/updateQcId',
			data : {
				
				jiraId : jiraId,
				qcId : qcId
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
<form name="jira_form" id="jira_form" method="post" action="" >
<input type="hidden" name="jiraIds"  id="jiraIds" value="${jiraIds}">
<table class="datatable" >
<tr>
<th align="right" style="width:100px" height="30"><span style="color: red">*</span>研发质检单号：</th>
 <td>
   <input type="text" name="qcId" id="qcId" value="" />
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