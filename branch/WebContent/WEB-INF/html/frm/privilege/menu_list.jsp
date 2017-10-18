<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<script language="javascript" src="${CONFIG.res_url}script/frm/privilege/menu.js" ></script> 
<script language="javascript" src="${CONFIG.res_url}script/tree.js" ></script> 
<script language="javascript" src="${CONFIG.web_url}cache/data/menu.js" ></script>
<script language="javascript" src="${CONFIG.res_url}script/jquery/plugin/jquery.form.js" ></script> 
<script> 
$(document).ready(function(){ 
	var tree1 = new tree(); 
	var config = {
		data:g_menu_obj
	};
	tree1.init(config);   
	$(".privilege").each(function(){
		$(this).colorbox({
			inline:true,
			href:'#privilege_form',
			onOpen:function(){
				//动态获取数据
				$.ajax({
					url: $(this).attr("href"), 
					success: privilege_list,
					error:function(){
						alert("error");
					}					
				});
			}
		});
	});
})

function privilege_list(data) {
	var json_data = eval('('+data+')');
	$.tmpl.list("privilege_row_tpl",json_data.data); 
}
</script>


</HEAD>
<BODY>
<p>菜单管理  <a href="${manageUrl}-add?fid=0">添加子级</a></p>
<div id="admin-menu"> 
	<ul>
	
	
	<c:forEach items="${request.menuList}" var="v">
		<li>
			<div style="display:none;" id='tree_node_${v.fatherId}_${v.id}'>  
				<span style="cursor:pointer;display:inline-block;vertical-align:middle;" id='symbol_${v.id}'></span> 
				<span style="vertical-align:middle;cursor:pointer;">
				<!-- <c:choose>
					<c:when  test="${v.menuUrl !=''}" > 
						<a href='${CONFIG.app_url}${v.menuUrl}' target="main_right">${v.menuName}</a> 
					</c:when>
					<c:otherwise>
						${v.menuName}
					</c:otherwise>
				</c:choose> 
				-->
				${v.menuName}
				</span>
				<a href="${manageUrl}-add?entity.fatherId=${v.id}">添加子级</a> 
				<a href="${manageUrl}-edit?id=${v.id}">编辑</a> 
				<a href="${manageUrl}-doDel?id=${v.id}">删除</a>  
				<a class="privilege" id="p_${v.id}" onclick="$('#menu_id').val(${v.id});"  href="${CONFIG.app_url}frm/ajax/privilege/menu_privilege-getPrivilege?menu_id=${v.id}">权限点</a> 	
				<div id='childs_${v.id}' style="display:none;"></div>    
			</div>
		</li>
	</c:forEach> 
	</ul> 
</div>

<div style="display:none;">
	<div id="privilege_form" style="width:720px;height:100px;overflow:auto;"> 
		<form id="menu_privilege_form" method="post" action="${CONFIG.app_url}frm/ajax/privilege/menu_privilege-doAdd"> 
		<input type="hidden" id="menu_id" name="ajaxEntity.menuId" value = "0" />   
		<table width="700" class="datatable">
			
			<tr>
				 <th align="right" width="100">权限名称</th>
				 <th align="right" width="100">权限url </th>
				  <th align="right" width="100">操作 </th>
			</tr>
			
			<tr id="privilege_row_tpl" rowid="privilege_row_\${id}" style="display:none; color:red;">
				<td>\${privilegeName}</td>
				<td>\${privilegeUrl}</td>
				<td><a href="#" onclick="ajax_edit_menu_privilege(\${id});">编辑</a>  <a href="#" onclick="ajax_do_del_menu_privilege(\${id});">删除</a></td>    
			</tr>	
			 
			<tr>				
				<td>
					<input type="hidden" name="ajaxEntity.id" id="ajaxEntity.id" value="0"  />
					<input type="text" name="ajaxEntity.privilegeName" id="privilege_name" value=""  />
				</td>
				
				 <td>
					<input type="text" name="ajaxEntity.privilegeUrl" id="privilege_url"  value=""  /> 
				</td>
				<td>
					<input type="submit" name="submit1" value="添加" onclick="ajax_do_add_menu_privilege();" />  
					<input type="reset" name="retset1" value="重置" />
				</td>
			</tr>
		</table>
		</form>
	</div>
</div>
<%@include file="/WEB-INF/html/foot.jsp" %>
