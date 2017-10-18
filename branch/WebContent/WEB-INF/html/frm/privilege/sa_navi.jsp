<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
$(function(){
	$('#navi_menu>li').each(function(){
		if(typeof g_menu_selected != 'undefined') {
			if($(this).find("a").attr("href").indexOf(g_menu_selected) != -1) {
				$(this).addClass("menu_on");
			} else {
				$(this).removeClass("menu_on"); 
			}
		}
	});
});
</script>
<div id="pici_tab" class="clear mb10">
  <ul id="navi_menu">
	<li class="menu_on"><s class="rc-l"></s><s class="rc-r"></s><a href="${CONFIG.app_url}frm/action/privilege/role">角色管理</a></li> 
	<li><s class="rc-l"></s><s class="rc-r"></s><a href="${CONFIG.app_url}frm/action/privilege/role-system">角色-权限(系统管理员)</a></li> 
	<li><s class="rc-l"></s><s class="rc-r"></s><a href="${CONFIG.app_url}frm/action/privilege/role-superAdmin">角色-权限(角色超管)</a></li>
	<li><s class="rc-l"></s><s class="rc-r"></s><a href="${CONFIG.app_url}frm/action/privilege/role-admin">角色-权限(角色普管)</a></li>
	<li><s class="rc-l"></s><s class="rc-r"></s><a href="${CONFIG.app_url}frm/action/privilege/role-user">人员-角色</a></li>
	<li><s class="rc-l"></s><s class="rc-r"></s><a href="${CONFIG.app_url}frm/action/privilege/user_privilege">人员-权限</a></li>
  </ul>
</div>