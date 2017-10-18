<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<script language="javascript" src="${CONFIG.res_url}script/{?app_id}/{?module_dir}{?table_name}.js" ></script> 
{?res}
{?initCode}
</HEAD>
<BODY>
<div class="clear" id="pici_tab">
  <ul> 
    <span class="rf"><a target="_blank" href="${manageUrl}-add"><input type="button" value="新增" name="" id="" class="blue"></a></span>   
  	<li class="menu_on"><s class="rc-l"></s><s class="rc-r"></s><a href="#">列表</a></li>
  </ul>
</div>
<!-- if searchFormMap -->
<div class="pici_search pd5">
<form name="search_form" id="search_form" method="POST" action="${manageUrl}">
	<!-- loop searchFormMap -->
		{?form_title}:{?control_html}
	<!-- end loop -->
	<input type="submit" name="search_submit" id="search_submit" value="搜索" />
</form>	
</div>
<!-- end if -->
<table class="listtable" width="98%">
	<thead>
		<!-- loop listMap -->
		<th>{?list_title}</th>
		<!-- end loop -->
		<th>操作</th>
	</thead>
	<tbody>
		<c:forEach items="${request.dataList}" var="v"  varStatus="st"> 
		<tr>
			<!-- loop listMap -->
			<td>{?standard_field_name_pre}${v.{?standard_field_name}}{?standard_field_name_aft}</td> 
			<!-- end loop -->
			<td>
				<a href="${manageUrl}-edit?id=${v.id}">编辑</a>  
				<a href="${manageUrl}-doDel?id=${v.id}" onclick="return confirm('本操作不可恢复,确认删除?')">删除</a> 
			</td>
		</tr>
		</c:forEach>
	</tbody>
</table>
<%@include file="/WEB-INF/html/pager.jsp" %>
<%@include file="/WEB-INF/html/foot.jsp" %>