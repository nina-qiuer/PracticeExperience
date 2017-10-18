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
 	var id = $('#id').val();
    if(id!="" && id!=null){
 		
    	url ="qc/operateQcBill/updateAuxBill";
 	}else{
 		
 		url = "qc/operateQcBill/addAuxBill";
 	}
	disableButton(input);
		$.ajax( {
  			url : url,
  			data : 	$('#billform').serialize(),
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
  			    		 parent.frames['auxiliaryBill'].location.reload();
  			    		 parent.layer.closeAll();
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
<div>
<form name="billform" id="billform" method="post" action="">
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
<input type="hidden" name="templateId" id="templateId" value="${qcTemplate.id}">
<input type="hidden" name="id" id="id" value="${auxBill.id}">
<input type="hidden" name="qcId" id="qcId" value="${auxBill.qcId}">
	<table class="datatable">
  <tr>
		<c:if test="${qcTemplate.fieldName1!=''}">
	  <th width="80">${qcTemplate.fieldName1}:</th>
	  <td><form:input  path="auxBill.field1" style="width:300px" /></td>
    </c:if>
    </tr>
    <tr>
    <c:if test="${qcTemplate.fieldName2!=''}">
	  <th width="80">${qcTemplate.fieldName2}:</th>
	 <td><form:input  path="auxBill.field2" style="width:300px" /></td>
    </c:if>
    </tr>
    <tr>
    <c:if test="${qcTemplate.fieldName3!=''}">
	  <th width="80">${qcTemplate.fieldName3}:</th>
 	  <td><form:input  path="auxBill.field3" style="width:300px" /></td>
    </c:if>
    </tr>
    <tr>
    <c:if test="${qcTemplate.fieldName4!=''}">
	  <th width="80">${qcTemplate.fieldName4}:</th>
	 <td><form:input  path="auxBill.field4" style="width:300px" /></td>
    </c:if>
    </tr>
    <tr>
     <c:if test="${qcTemplate.fieldName5!=''}">
	  <th width="80">${qcTemplate.fieldName5}:</th>
	  <td><form:input  path="auxBill.field5" style="width:300px" /></td>
    </c:if>
    </tr>
    <tr>
     <c:if test="${qcTemplate.fieldName6!=''}">
	  <th width="80">${qcTemplate.fieldName6}:</th>
	  <td><form:input  path="auxBill.field6" style="width:300px" /></td>
    </c:if>
    </tr>
    <tr>
     <c:if test="${qcTemplate.fieldName7!=''}">
	  <th width="80">${qcTemplate.fieldName7}:</th>
	  <td><form:input  path="auxBill.field7" style="width:300px" /></td>
    </c:if>
    </tr>
    <tr>
    <c:if test="${qcTemplate.fieldName8!=''}">
	  <th width="80">${qcTemplate.fieldName8}:</th>
	  <td><form:input  path="auxBill.field8" style="width:300px" /></td>
    </c:if>
    </tr>
    <tr>
     <c:if test="${qcTemplate.fieldName9!=''}">
	  <th width="80">${qcTemplate.fieldName9}:</th>
	 <td><form:input  path="auxBill.field9" style="width:300px" /></td>
    </c:if>
    </tr>
    <tr>
     <c:if test="${qcTemplate.fieldName10!=''}">
	  <th width="80">${qcTemplate.fieldName10}:</th>
	   <td><form:input  path="auxBill.field10" style="width:300px" /></td>
    </c:if>
    </tr>
    <tr>
    <c:if test="${qcTemplate.fieldName11!=''}">
	  <th width="80">${qcTemplate.fieldName11}:</th>
	  <td><form:input  path="auxBill.field11" style="width:300px" /></td>
    </c:if>
    </tr>
    <tr>
    <c:if test="${qcTemplate.fieldName12!=''}">
	  <th width="80">${qcTemplate.fieldName12}:</th>
	   <td><form:input  path="auxBill.field12" style="width:300px" /></td>
    </c:if>
    </tr>
    <tr>
    <c:if test="${qcTemplate.fieldName13!=''}">
	  <th width="80">${qcTemplate.fieldName13}:</th>
	  <td><form:input  path="auxBill.field13" style="width:300px" /></td>
    </c:if>
    </tr>
    <tr>
    <c:if test="${qcTemplate.fieldName14!=''}">
	  <th width="80">${qcTemplate.fieldName14}:</th>
	  <td><form:input  path="auxBill.field14" style="width:300px" /></td>
    </c:if>
    </tr>
    <tr>
    <c:if test="${qcTemplate.fieldName15!=''}">
	  <th width="80">${qcTemplate.fieldName15}:</th>
	  <td><form:input  path="auxBill.field15" style="width:300px" /></td>
    </c:if>
    </tr>
    <tr>
    <c:if test="${qcTemplate.fieldName16!=''}">
	  <th width="80">${qcTemplate.fieldName16}:</th>
	   <td><form:input  path="auxBill.field16" style="width:300px" /></td>
    </c:if>
    </tr>
    <tr>
     <c:if test="${qcTemplate.fieldName17!=''}">
	  <th width="80">${qcTemplate.fieldName17}:</th>
	   <td><form:input  path="auxBill.field17" style="width:300px" /></td>
    </c:if>
    </tr>
    <tr>
     <c:if test="${qcTemplate.fieldName18!=''}">
	  <th width="80">${qcTemplate.fieldName18}:</th>
	   <td><form:input  path="auxBill.field18" style="width:300px" /></td>
    </c:if>
    </tr>
    <tr>
     <c:if test="${qcTemplate.fieldName19!=''}">
	  <th width="80">${qcTemplate.fieldName19}:</th>
	   <td><form:input  path="auxBill.field19" style="width:300px" /></td>
    </c:if>
    </tr>
    <tr>
    <c:if test="${qcTemplate.fieldName20!=''}">
	  <th width="80">${qcTemplate.fieldName20}:</th>
	   <td><form:input  path="auxBill.field20" style="width:300px" /></td>
    </c:if>
    </tr>
    <tr>
     <c:if test="${qcTemplate.fieldName21!=''}">
	  <th width="80">${qcTemplate.fieldName21}:</th>
	 <td><form:input  path="auxBill.field21" style="width:300px" /></td>
    </c:if>
    </tr>
    <tr>
     <c:if test="${qcTemplate.fieldName22!=''}">
	  <th width="80">${qcTemplate.fieldName22}:</th>
	 <td><form:input  path="auxBill.field22" style="width:300px" /></td>
    </c:if>
    </tr>
    <tr>
    <c:if test="${qcTemplate.fieldName23!=''}">
	  <th width="80">${qcTemplate.fieldName23}:</th>
	  <td><form:input  path="auxBill.field23" style="width:300px" /></td>
    </c:if>
    </tr>
    <tr>
    <c:if test="${qcTemplate.fieldName24!=''}">
	  <th width="80">${qcTemplate.fieldName24}:</th>
	  <td><form:input  path="auxBill.field24" style="width:300px" /></td>
    </c:if>
    </tr>
    <tr>
    <c:if test="${qcTemplate.fieldName25!=''}">
	  <th width="80">${qcTemplate.fieldName25}:</th>
	<td><form:input  path="auxBill.field25" style="width:300px" /></td>
    </c:if>
    </tr>
    <tr>
    <c:if test="${qcTemplate.fieldName26!=''}">
	  <th width="80">${qcTemplate.fieldName26}:</th>
	  <td><form:input  path="auxBill.field26" style="width:300px" /></td>
    </c:if>
    </tr>
    <tr>
     <c:if test="${qcTemplate.fieldName27!=''}">
	  <th width="80">${qcTemplate.fieldName27}:</th>
	 <td><form:input  path="auxBill.field27" style="width:300px" /></td>
    </c:if>
    </tr>
    <tr>
     <c:if test="${qcTemplate.fieldName28!=''}">
	  <th width="80">${qcTemplate.fieldName28}:</th>
	<td><form:input  path="auxBill.field28" style="width:300px" /></td>
    </c:if>
    </tr>
    <tr>
     <c:if test="${qcTemplate.fieldName29!=''}">
	  <th width="80">${qcTemplate.fieldName29}:</th>
	 <td><form:input  path="auxBill.field29" style="width:300px" /></td>
    </c:if>
    </tr>
    <tr>
    <c:if test="${qcTemplate.fieldName30!=''}">
	  <th width="80">${qcTemplate.fieldName30}:</th>
	 <td><form:input  path="auxBill.field30" style="width:300px" /></td>
    </c:if>
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
