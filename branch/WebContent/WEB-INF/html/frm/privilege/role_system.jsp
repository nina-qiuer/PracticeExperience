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
	
});
</script>
</HEAD>
<BODY>
<%@include file="sa_navi.jsp" %> 


<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr valign="top">
    <td width="40%">
    	<table class="listtable" width="100%" border="0" cellspacing="0" cellpadding="0">
    		<c:forEach items="${request.dataList}" var="v"  varStatus="st"> 
    			<c:if test="${st.index%5==0}">
	    			<tr>
				</c:if>	    		
		        	<th>
		        		<a href="${manageUrl}-system?role_id=${v.id}">${v.roleName}</a> 
		        	</th>
	        <c:if test="${st.count%5==0}">	
	       		</tr>
	        </c:if>	
	        </c:forEach>
    	</table>
    </td>
    <td width="60%"><div id="main">
    	<c:if test="${role_id>0}">
    	<form id="role_privilege_form" name="role_privilege_form" method="post" action="${CONFIG.app_url}frm/ajax/privilege/role_privilege-doSystemAllocate">
	        <input type="hidden" name="role_id" value="${role_id}" /> 
	        <ul id="navigation" class="treeview">
	        	<li>
	        		<span>角色权限配置</span>
					<ul>
						<c:forEach items="${menuList}" var="v">
							<li>
								<div style="display:none;" id='tree_node_${v.fatherId}_${v.id}'> 
									<c:set var="menu_id" value="${fn:trim(v.id)}" />
									<span style="cursor:pointer;display:inline-block;vertical-align:middle;" id='symbol_${v.id}'></span> 
									<input type="checkbox" name="menu_id" value="${v.id}" <c:if test="${rolePrivilegeMap[menu_id] != null}">checked</c:if>> 
									<span style="vertical-align:middle;cursor:pointer;" id="menu_name_${v.id}">
									${v.menuName}
									</span>
									<c:set var="checkbox_name" value="privilege_id_${v.id}" />
									${ui:makeCheckbox(v.privileges,checkbox_name,rolePrivilegeMap[menu_id].privilegeIds,"id,privilegeName")} 
									
									<div id='childs_${v.id}' style="display:none;"></div>    
								</div>
							</li>
						</c:forEach> 
					</ul>
				</li>
				<li><input type="submit" value="提交" onclick="ajax_do_system_allocate_role_privilege();" ></li> 
			</ul>
		</form>
		</c:if>  
      </td>
  </tr>
</table>

<%@include file="/WEB-INF/html/foot.jsp" %>