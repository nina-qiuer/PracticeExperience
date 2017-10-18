<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>
<html>
<head>
<script type="text/javascript">
function doSubmit(input) {
	var qcIds = $('#qcIds').val();
	var qcLevel = $("#importantFlag").val();
	
   	disableButton(input);
	$.ajax( {
		url : 'qc/qcBill/alterQcLevel',
		data : {
			"qcBillIds" : qcIds,
			"importantFlag"	: qcLevel		
		}, 
		type : 'post',
		dataType:'json',
		cache : false,
		success : function(result) {
			if(result != null){
		    	if(result.retCode == "0"){
		    		parent.successCallBack(qcLevel);
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
</head>
<body>
<form name="qc_form" id="qc_form" method="post" action="" >
	<input type="hidden" id="qcIds" value="${qcIds}">
	<table class="datatable" >
		<tr>
			<th align="right" style="width:100px" height="30"><span style="color: red">*</span>质检等级：</th>
		 	<td>
			  	<select id="importantFlag" style="width: 100px;" onchange="searchResetPage()">
					<option value="0">零级</option>
					<option value="1">一级</option>
					<c:if test="${fn:contains(loginUser_WCS,'QC_ALTER_SECOND_LEVEL')}">
						<option value="2">二级</option>
					</c:if>
				</select>
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