<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>

<html>
<head>
<script type="text/javascript">

function updateLevel(input) {
	disableButton(input);
	$.ajax( {
		url : 'qc/qcBill/updateLevel',
		data :  $("#qcLevelForm").serialize(),
		type : 'post',
		dataType:'json',
		cache : false,
		success : function(result) {
			if(result)
			{
				enableButton(input);
		    	if(result.retCode == "0")
				{
		    		 layer.alert("修改成功", {icon: 1});
		    		 window.parent.location.reload();
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
<form id="qcLevelForm" name="qcLevelForm" method="post" action="" >
<form:hidden path="qcBill.id"/>
<form:hidden path="qcBill.cmpId"/>
<table class="datatable">
	<tr>
		<th align="right" width="50"  ><span style="color: red">*</span>&nbsp;投诉类型：</th>
		<td width="85"  >
			<form:select path="qcBill.cmpLevel" >
				<form:option value="0">0级</form:option>
				<form:option value="1">1级</form:option>
				<form:option value="2">2级</form:option>
				<form:option value="3">3级</form:option>
			</form:select>
		</td>
	</tr>
	<tr>
		<th align="right">操作：</th>
		<td><input type="button" class="blue" name="submitBtn" value="提交" onclick="updateLevel(this)"></td>
	</tr>
</table>
</form>
</body>
</html>
