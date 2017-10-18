<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<script language="javascript" src="${CONFIG.res_url}script/frm/privilege/menu_privilege.js" ></script>
</HEAD>
<BODY>
<h2>${request.formTitle}</h2>
<form name="form" id ="form" method="post" enctype="multipart/form-data"  action="${manageUrl}-${request.formAction}" onSubmit = "return menu_privilege_check_form();">
<input type="hidden" name="entity.id" id="id"  value="${entity.id}" />
<input type="hidden" name="refer_to" id="refer_to" value="" />
<table width="700" class="datatable">
	
	<tr>
		 <th align="right" width="100">菜单id<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.menuId" id="menu_id" value="${entity.menuId}"  />
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">权限名称<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.privilegeName" id="privilege_name" value="${entity.privilegeName}"  />
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">权限url<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.privilegeUrl" id="privilege_url" value="${entity.privilegeUrl}"  />
		</td>
	</tr>		
	
	<tr>
		<td colspan="2">
			<input type="submit" name="submit1" value="提交" />
		</td>
	</tr>	

</table>
</form>
<%@include file="/WEB-INF/html/foot.jsp" %> 
