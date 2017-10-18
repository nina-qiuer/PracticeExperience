<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>
<html>
<head>
<script type="text/javascript">
function doSubmit(input) {
	var relationId = $('#relationId').val();
	var closeType = $("#remark").find("option:selected").val();
	var remark = $("#remark").find("option:selected").text();
	
   	disableButton(input);
	$.ajax( {
		url : 'qc/qcBillRelation/closeRelation',
		data : {
			"id" : relationId,
			"closeType" : closeType,
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
<title>关闭关联关系</title>
</head>
<body>
<form name="qc_form" id="qc_form" method="post" action="" >
	<input type="hidden" id="relationId" value="${relationId}">
	<table class="datatable" >
		<tr>
			<th align="right" style="width:70px" height="30"><span style="color: red">*</span>备注：</th>
		 	<td>
		 		<select id="remark" name="remark" style="width: 210px;">
		 			<c:forEach var="type" items="${cmTypeList}">
		 				<option value="${type.id}">${type.name}</option>
		 			</c:forEach>
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