<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<script language="javascript" src="${CONFIG.res_url}script/frm/privilege/menu.js" ></script>
<script>
$(function(){
	$("input[id^='is_menu_']").bind("click",function(){
		if($(this).val() == 1) {
			$(".m_1").show();
		} else {
			$(".m_1").hide();
		}
	});
	
	$("input[id^='is_external_']").bind("click",function(){
		if($(this).val() == 1) {
			$(".e_1").hide();
		} else {
			$(".e_1").show();
		}
	});
	
});
</script>
</HEAD>
<BODY>
<h2>${request.formTitle}菜单</h2>
<form name="form" id ="form" method="post" enctype="multipart/form-data"  action="${manageUrl}-${request.formAction}">
<input type="hidden" name="entity.id" id="id"  value="${entity.id}" />
<input type="hidden" name="refer_to" id="refer_to" value="" />
<table width="700" class="datatable">
	
	
	<tr>
		 <th align="right" width="100">上级</th>
		 <td>
		 	<select name="entity.fatherId" id="father_id">
		 	<option value=0>根目录</option>
		 	${ui:makeSelect(request.menuFullnameMap,entity.fatherId,null)}
		 	</select>
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">菜单名称<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.menuName" id="menu_name" value="${entity.menuName}"  />
		</td>
	</tr>
	
	<tr>
		 <th align="right" width="100">是否菜单</th>
		 <td>
			${ui:makeRadio(Constant.defaultRadioOptionMap,"entity.isMenu",entity.isMenu,null)}
		</td>
	</tr>	
	
	
	
	<tr class="m_1">
		 <th align="right" width="100">是否外链<span class="required"> *</span></th>
		 <td>
			${ui:makeRadio(Constant.defaultRadioOptionMap,"entity.isExternal",entity.isExternal,null)}
		</td>
	</tr>	

	<tr class="m_1">
		 <th align="right" width="100">菜单地址</th>
		 <td>
			<input type="text" name="entity.menuUrl" id="menu_url" value="${entity.menuUrl}"  />
		</td>
	</tr>	
	
	<tr class="m_1 e_1">
		 <th align="right" width="100">methods<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.methods" id="methods" value="${entity.methods}"  />
		</td>
	</tr>		
	
		
	<tr>
		 <th align="right" width="100">排序</th>
		 <td>
			<input type="text" name="entity.sequence" id="sequence" value="${entity.sequence}"  />
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
