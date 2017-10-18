<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<script language="javascript" src="${CONFIG.res_url}script/frm/privilege/role.js" ></script>
<script language="javascript" src="${CONFIG.res_url}script/jquery/plugin/jquery.form.js" ></script> 
<link type="text/css" rel="stylesheet" href="${CONFIG.res_url}script/jquery/plugin/auto_complete/jquery.autocomplete.css" />
<script language="javascript" src="${CONFIG.res_url}script/jquery/plugin/auto_complete/jquery.autocomplete.js" ></script>
<script>
$(function(){
	var options = {
		max:50, 
		dataType:"json",
		formatItem:function(row) {
			
			return row.realName + "("+row.userName+")";
		},
		formatResult:function(row) {
			return row.realName;  
		},  
		matchContains:true,
		parse:function(json) { 
			var ret = [];
			for(i=0;i<json.data.length;i++) {
				ret[i]={
					data:json.data[i],
					value:json.data[i].realName,
					result:options.formatResult(json.data[i])  
				}
			}
			return ret;
		}
	} 
	$("#super_manage_id_auto").autocomplete(WEB_URL+"frm/ajax/system/user-getMatchUserListByName",options).result(function(event, row, formatted) {
		$('#super_manage_id').val(row.id);  
	})
	
	$("#new_manage_name").autocomplete(WEB_URL+"frm/ajax/system/user-getMatchUserListByName",options).result(function(event, row, formatted) {
		$('#new_manage_id').val(row.id);  
	})

});
</script>

</HEAD>
<BODY>

<h2>${request.formTitle}</h2>
<form name="form" id ="form" method="post" enctype="multipart/form-data"  action="${manageUrl}-${request.formAction}" onSubmit = "return role_check_form();">
<input type="hidden" name="entity.id" id="id"  value="${entity.id}" />
<input type="hidden" name="refer_to" id="refer_to" value="" />
<table width="700" class="datatable">
	
	<tr>
		 <th align="right" width="100">角色名<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.roleName" id="role_name" value="${entity.roleName}"  />
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">超级管理员id<span class="required"> *</span></th>
		 <td>
		 	<input type="hidden" name="entity.superManageId" id="super_manage_id"  value="${entity.superManageId}" />
		 	<input type="text" name="super_manage_id_auto" id="super_manage_id_auto" value="${superUser.realName}" /> 
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">普通管理员ids<span class="required"> *</span></th>
		 <td>
			<c:forEach items="${userList}" var="userInfo">
				<span >${userInfo.realName} <a href="" onclick="return ajax_do_del_manage(this,'${userInfo.id}');">删除</a></span>
			</c:forEach>
			<span id="user_info_tpl" style="display:none;">\${realName} <a href="" onclick="return ajax_do_del_manage(this,'\${id}');">删除</a></span>
			<div>
			
				<input  type="hidden" name="new_manage_id" id="new_manage_id" value="0" /> 
				<input type="text" name="new_manage_name" id="new_manage_name"  /> 
				<input type="button" value="新增" onclick="ajax_do_add_manage();" />
			
			</div>
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
