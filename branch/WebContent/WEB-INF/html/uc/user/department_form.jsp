<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<script language="javascript" src="${CONFIG.res_url}script/uc/user/department.js" ></script>


</HEAD>
<BODY>
<h2>${request.formTitle}</h2>
<form name="form" id ="form" method="post" enctype="multipart/form-data"  action="${manageUrl}-${request.formAction}" onSubmit = "return department_check_form();">
<input type="hidden" name="entity.id" id="id"  value="${entity.id}" />
<input type="hidden" name="refer_to" id="refer_to" value="" />
<table width="700" class="datatable">
	
	<tr>
		 <th align="right" width="100">树形结点父id<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.treeFatherId" id="tree_father_id" value="${entity.treeFatherId}"  />
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">部门名称<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.depName" id="dep_name" value="${entity.depName}"  />
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">直接上级ID<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.fatherId" id="father_id" value="${entity.fatherId}"  />
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">深度<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.depth" id="depth" value="${entity.depth}"  />
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">树形结点id<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.treeId" id="tree_id" value="${entity.treeId}"  />
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
