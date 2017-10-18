<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>模板信息</title>

<script type="text/javascript">
$(document).ready(function(){
	$(".accordion").each(function() {
		$(this).accordion({
	    	collapsible: true,
	    	heightStyle: "content"
	    });
	});
});

function doSubmit(input){
	
	var url ="";
	var qcType =$('#qcType').val();
	var qcTypeId = $('#qcTypeId').val();
	if(qcType==""||null==qcType){
		
		$('#qcType').val(qcTypeId);
	}
 	var id = $('#id').val();
    if(id!="" && id!=null){
 		
    	url ="qc/operateQcBill/updateTemplate";
 	}else{
 		
 		url = "qc/operateQcBill/saveTemplate";
 	}
	disableButton(input);
		$.ajax( {
  			url : url,
  			data : 	$('#operate_form').serialize(),
  			type : 'post',
  			dataType:'json',
  			cache : false,
  			success : function(result) {
  				enableButton(input);
  				if(result)
  				{
  					enableButton(input);
  			    	if(result.retCode == "0")
  					{
  			    		layer.alert('保存成功',{icon: 6});
  			  		     location.reload();
  			  		   
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

<div id="template">
<div class="accordion">
<h3>${qtFullName}</h3>
</div>
<div>
<form name="operate_form" id="operate_form" method="post" action="">
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
<input type="hidden" name="id" id="id" value="${qcTemplate.id}">
<input type="hidden" name="qcType" id="qcType" value="${qcTemplate.qcType}">
<input type="hidden" name="qcTypeId" id="qcTypeId" value="${qcTypeId}">
	<table class="datatable">
	<tr>
		<th width="20">字段名称1：</th>
		<td width="70"><input type="text" name="fieldName1" value="${qcTemplate.fieldName1}"></td>
		<th width="20">字段名称2：</th>
		<td width="70"><input type="text" name="fieldName2" value="${qcTemplate.fieldName2}"></td>
	</tr>
	<tr>
		<th width="20">字段名称3：</th>
		<td width="70"><input type="text" name="fieldName3" value="${qcTemplate.fieldName3}"></td>
		<th width="20">字段名称4：</th>
		<td width="70"><input type="text" name="fieldName4" value="${qcTemplate.fieldName4}"></td>
	</tr>
	<tr>
		<th width="20">字段名称5：</th>
		<td width="70"><input type="text" name="fieldName5" value="${qcTemplate.fieldName5}"></td>
		<th width="20">字段名称6：</th>
		<td width="70"><input type="text" name="fieldName6" value="${qcTemplate.fieldName6}"></td>
	</tr>
	<tr>
		<th width="20">字段名称7：</th>
		<td width="70"><input type="text" name="fieldName7" value="${qcTemplate.fieldName7}"></td>
		<th width="20">字段名称8：</th>
		<td width="70"><input type="text" name="fieldName8" value="${qcTemplate.fieldName8}"></td>
	</tr>
	<tr>
		<th width="20">字段名称9：</th>
		<td width="70"><input type="text" name="fieldName9" value="${qcTemplate.fieldName9}"></td>
		<th width="20">字段名称10：</th>
		<td width="70"><input type="text" name="fieldName10" value="${qcTemplate.fieldName10}"></td>
	</tr>
		<tr>
		<th width="20">字段名称11：</th>
		<td width="70"><input type="text" name="fieldName11" value="${qcTemplate.fieldName11}"></td>
		<th width="20">字段名称12：</th>
		<td width="70"><input type="text" name="fieldName12" value="${qcTemplate.fieldName12}"></td>
	</tr>
	<tr>
		<th width="20">字段名称13：</th>
		<td width="70"><input type="text" name="fieldName13" value="${qcTemplate.fieldName13}"></td>
		<th width="20">字段名称14：</th>
		<td width="70"><input type="text" name="fieldName14" value="${qcTemplate.fieldName14}"></td>
	</tr>
	<tr>
		<th width="20">字段名称15：</th>
		<td width="70"><input type="text" name="fieldName15" value="${qcTemplate.fieldName15}"></td>
		<th width="20">字段名称16：</th>
		<td width="70"><input type="text" name="fieldName16" value="${qcTemplate.fieldName16}"></td>
	</tr>
	<tr>
		<th width="20">字段名称17：</th>
		<td width="70"><input type="text" name="fieldName17" value="${qcTemplate.fieldName17}"></td>
		<th width="20">字段名称18：</th>
		<td width="70"><input type="text" name="fieldName18" value="${qcTemplate.fieldName18}"></td>
	</tr>
	<tr>
		<th width="20">字段名称19：</th>
		<td width="70"><input type="text" name="fieldName19" value="${qcTemplate.fieldName19}"></td>
		<th width="20">字段名称20：</th>
		<td width="70"><input type="text" name="fieldName20" value="${qcTemplate.fieldName20}"></td>
	</tr>
		<tr>
		<th width="20">字段名称21：</th>
		<td width="70"><input type="text" name="fieldName21" value="${qcTemplate.fieldName21}"></td>
		<th width="20">字段名称22：</th>
		<td width="70"><input type="text" name="fieldName22" value="${qcTemplate.fieldName22}"></td>
	</tr>
	<tr>
		<th width="20">字段名称23：</th>
		<td width="70"><input type="text" name="fieldName23" value="${qcTemplate.fieldName23}"></td>
		<th width="20">字段名称24：</th>
		<td width="70"><input type="text" name="fieldName24" value="${qcTemplate.fieldName24}"></td>
	</tr>
	<tr>
		<th width="20">字段名称25：</th>
		<td width="70"><input type="text" name="fieldName25" value="${qcTemplate.fieldName25}"></td>
		<th width="20">字段名称26：</th>
		<td width="70"><input type="text" name="fieldName26" value="${qcTemplate.fieldName26}"></td>
	</tr>
	<tr>
		<th width="20">字段名称27：</th>
		<td width="70"><input type="text" name="fieldName27" value="${qcTemplate.fieldName27}"></td>
		<th width="20">字段名称28：</th>
		<td width="70"><input type="text" name="fieldName28" value="${qcTemplate.fieldName28}"></td>
	</tr>
	<tr>
		<th width="20">字段名称29：</th>
		<td width="70"><input type="text" name="fieldName29" value="${qcTemplate.fieldName29}"></td>
		<th width="20">字段名称30：</th>
		<td width="70"><input type="text" name="fieldName30" value="${qcTemplate.fieldName30}"></td>
	</tr>
	<tr>
		<th align="right">操作：</th>
		<td colspan="3"><input type="button" name="submitBtn" class="blue"  value="提交" id="submitBtn" onclick="doSubmit(this)"></td>
	</tr>
	</table>
</form>
</div>
</div>
</body>
</html>
