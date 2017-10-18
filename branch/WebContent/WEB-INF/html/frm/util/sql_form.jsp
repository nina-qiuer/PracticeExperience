<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%> 
<%@include file="/WEB-INF/html/head.jsp" %>  
</HEAD>
<BODY>
<h2>${request.formTitle}</h2>
<form name="form" id ="form" method="post" enctype="multipart/form-data"  action="${CONFIG.app_url}action/sql_result_map-doFormat">

<table width="98%" class="datatable">
	
	
	<tr>
		 <th align="right" width="100">sql语句<span class="required"> *</span></th>
		 <td>
			<textarea  name="sql" id="sql" rows="5" style="width:100%">${sql}</textarea> 
		</td>
	</tr>			
	<tr>
		<td colspan="2">
			<input type="submit" name="submit1" value="提交" />
		</td>
	</tr>	

</table>
</form>
<div>
sqlResult:<div style="color:red">${sqlResult}</div> 
</div>
<%@include file="/WEB-INF/html/foot.jsp" %> 
