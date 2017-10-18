<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<script language="javascript" src="${CONFIG.res_url}script/frm/privilege/role.js" ></script> 
</HEAD>
<BODY>
<%@include file="sa_navi.jsp" %>
<p><a target="_blank" href="${manageUrl}-add"><input type="button" value="新增" name="" id="" class="blue"></a></p>
<table class="listtable" width="98%">
	<thead>
		
		<th>id</th>
			
		<th>角色名</th>
		
		<th>超级管理员id</th>
		
		<th>普通管理员ids</th>
	
		<th>添加时间</th>
		
		<th>操作</th>
	</thead>
	<tbody>
		<c:forEach items="${request.dataList}" var="v"  varStatus="st"> 
		<tr>
			
			<td>${v.id}</td> 
			
			<td>${v.roleName}</td> 
			
			<td>${v.superManageId}</td> 
			
			<td>${v.manageIds}</td> 
	
			<td><fmt:formatDate value="${v.addTime}" pattern="yyyy-MM-dd HH:mm:ss"/> </td> 
	
			<td>
				<a href="${manageUrl}-edit?id=${v.id}">编辑</a>  
				<a href="${manageUrl}-doDel?id=${v.id}" onclick="return confirm('本操作不可恢复,确认删除?')"> 删除</a> 
				<a href="${CONFIG.app_url}frm/action/privilege/role-system?role_id=${v.id}">设定权限</a>   
			</td>
		</tr>
		</c:forEach>
	</tbody>
</table>
<%@include file="/WEB-INF/html/pager.jsp" %>
<%@include file="/WEB-INF/html/foot.jsp" %>