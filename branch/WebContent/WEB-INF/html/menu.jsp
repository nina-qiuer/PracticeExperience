<%@ page  contentType="text/html; charset=UTF-8"  pageEncoding="utf-8"%>
<h3>管理</h3>
<script language="javascript" src="${CONFIG.res_url}script/tree.js" ></script> 
<script language="javascript" src="${CONFIG.web_url}cache/data/menu.js" ></script>
<script>
$(document).ready(function(){ 
	var tree1 = new tree(); 
	var config = {
		data:g_menu_obj
	};
	tree1.init(config);   
})

</script>
<div id="admin-menu"> 
	<ul>
	
	
	<c:forEach items="${request.menuList}" var="v">
		<li>
			<div style="display:none;" id='tree_node_${v.fatherId}_${v.id}'>  
				<span style="cursor:pointer;display:inline-block;vertical-align:middle;" id='symbol_${v.id}'></span> 
				<span style="vertical-align:middle;cursor:pointer;">
				<c:choose>
					<c:when  test="${v.menuUrl !=''}" > 
						<a href='${CONFIG.app_url}${v.menuUrl}' target="${v.target}">${v.menuName}</a> 
					</c:when>
					<c:otherwise>
						${v.menuName}
					</c:otherwise>
				</c:choose>  
				
				</span>  
				<div id='childs_${v.id}' style="display:none;"></div>    
			</div>
		</li>
	</c:forEach> 
	</ul> 
</div>

