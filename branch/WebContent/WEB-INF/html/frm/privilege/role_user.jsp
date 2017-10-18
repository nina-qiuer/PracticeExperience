<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<script language="javascript" src="${CONFIG.res_url}script/frm/privilege/role.js" ></script> 
<script language="javascript" src="${CONFIG.res_url}script/tree.js" ></script> 
<script language="javascript" src="${CONFIG.web_url}cache/data/menu.js" ></script> 
<script language="javascript" src="${CONFIG.res_url}script/jquery/plugin/jquery.form.js" ></script> 
<script>
g_menu_selected = "${menuSelected}";

$(function(){
	var tree1 = new tree(); 
	var config = {
		data:g_menu_obj
	};
	tree1.init(config); 
	$('input[type="checkbox"][name^="menu_id"]').each(function(){
		$(this).bind("click",function(){
			if(typeof($(this).attr("checked")) != 'undefined') {
				$(this).parent().find("input").attr("checked",true);
				$(this).parentsUntil("li").children('input[type="checkbox"][name^="menu_id"]').attr("checked",true);  
			} else {
				$(this).parent().find("input").removeAttr("checked");
			}
		})
	});
	$('input[type="checkbox"][name^="privilege_id_"]').each(function(){
		$(this).bind("click",function(){
			if(typeof($(this).attr("checked")) != 'undefined') {
				
				$(this).parentsUntil("li").children('input[type="checkbox"][name^="menu_id"]').attr("checked",true); 
			} 
		})
	});
	
	var privilegeId_arr = "${privilegeIds}".split(",");
	for(var i=0;i<privilegeId_arr.length;i++) {
		$('*[id^="privilege_id_"][value="'+privilegeId_arr[i]+'"]').attr("disabled",true);
	}
	
});
</script>
</HEAD>
<BODY>
<%@include file="sa_navi.jsp" %> 
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr valign="top">
    <td width="40%">
    	<table class="listtable" width="100%" border="0" cellspacing="0" cellpadding="0">
    		<c:forEach items="${userList}" var="v"  varStatus="st"> 
    			<c:if test="${st.index%5==0}">
	    			<tr>
				</c:if>	    		
		        	<th>
		        		<a href="${manageUrl}-user?user_id=${v.id}">${v.realName}</a> 
		        	</th>
	        <c:if test="${st.count%5==0}">	
	       		</tr>
	        </c:if>	
	        </c:forEach>
    	</table>
    </td>
    <td width="60%"><div id="main">
    	<c:if test="${user_id>0}">
    	<form id="role_user_attr_form" name="role_user_attr_form" method="post" action="${CONFIG.app_url}frm/ajax/privilege/role_user_attr-doAdd">
	        <input type="hidden" name="ajaxEntity.priId" value="${user_id}" /> 
	        <input type="hidden" name="ajaxEntity.priType" value="user" />
	        <ul id="navigation" class="treeview">
	        	<li>
	        		<span>人员角色配置</span>
					<ul>
						${ui:makeCheckbox(roleList,"role_ids",userRoleIds,"id,roleName,5")}  
					</ul>
				</li>
				<li><input type="submit" value="提交" onclick="ajax_role_user_attr_do_add();" ></li> 
			</ul>
		</form>
		</c:if>  
      </td>
  </tr>
</table>

<%@include file="/WEB-INF/html/foot.jsp" %>