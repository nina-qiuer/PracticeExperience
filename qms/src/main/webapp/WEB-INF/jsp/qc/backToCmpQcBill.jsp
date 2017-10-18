<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>
<html>
<head>
<script type="text/javascript">
function doSubmit(input) {
	var relationId = $('#relationId').val();
	var remark = $("#remark").val();
	
   	disableButton(input);
	$.ajax( {
		url : 'qc/qcBillRelation/submitBack',
		data : {
			"id" : relationId,
			"remark" : remark		
		}, 
		type : 'post',
		dataType:'json',
		cache : false,
		success : function(result) {
			if(result != null){
		    	if(result.retCode == "0"){
		    		parent.search();
		    		parent.layer.closeAll();
				 }else{
					enableButton(input);
					layer.alert(result.resMsg, {icon: 2});
				}
		     }
		 }
	  });
}
</script>
<title>返回投诉质检</title>
</head>
<body>
<form name="qc_form" id="qc_form" method="post" action="" >
	<input type="hidden" id="relationId" value="${relationId}">
	<table class="datatable" >
		<tr>
			<th align="right" style="width:100px" height="30">备注：</th>
		 	<td>
		 		<textarea id="remark" name="remark" style="width:385px;height:125px;"></textarea>
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