<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" deferredSyntaxAllowedAsLiteral="true"%> 
<%@include file="/WEB-INF/html/head.jsp" %> 

</HEAD>
<BODY>
<h2>添加新表</h2>
<form name="form" id ="form" method="post" enctype="multipart/form-data"  action="${manageUrl}-${request.formAction}">
<input type="hidden" name="entity.id" id="id"  value="${entity.id}" />
<input type="hidden" name="refer_to" id="refer_to" value="" />
<table width="700" class="datatable">

	 <tr>
	 	<th align="right" width="100">所属应用前缀<span class="required"> *</span></th>
		<td>
			
			<select name="entity.appId" id="app_id"> 
				${ui:makeSelect(request.appTblPreMap,entity.appId,null)} 
			</select>
		</td>
	</tr>
	
	 <tr>
	 <th align="right" width="100">表名<span class="required"> *</span></th>
		<td>
			<input type="text" name="entity.tableName" id="table_name" value="${entity.tableName}"   /> 			
		</td>
	</tr>
	
	 <tr>
	  <th align="right" width="100">表是否存在<span class="required"> *</span></th>
		<td>
			${ui:makeRadio(constant.defaultRadioOptionMap,"entity.tableExist",entity.tableExist,null)}			
		</td>
	</tr>
	
	 <tr>
	 <th align="right" width="100">程序生成目录<span class="required"> *</span></th>
		<td>
			<input type="text" name="entity.dirPath" id="table_name" value="${entity.dirPath}"   /> 	
		</td>
	</tr>
	
	 <tr>
		 <th align="right" width="100">生成action<span class="required"> *</span></th>
		<td>
			${ui:makeCheckbox("1:是","entity.formAction",entity.formAction,null)} 
		</td>
	</tr>
		
	<tr> <td colspan="2"><input type="submit" name="submit1" value="提交" /> </td></tr> 
		

</table>
</form>
<%@include file="/WEB-INF/html/foot.jsp" %>