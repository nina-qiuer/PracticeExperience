<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<script language="javascript" src="${CONFIG.res_url}script/frm/{dir_path}/control.js" ></script> 
</HEAD>
<BODY>

<h2><a href="${manageUrl}-add">新增</a></h2>
<table class="listtable" width="98%">
	<thead>
		
		<th>id</th>
		
		<th>control_name</th>
		
		<th>data_source</th>
		
		<th>control_res</th>
		
		<th>操作</th>
	</thead>
	<tbody>
		<c:forEach items="${request.dataList}" var="v"  varStatus="st"> 
		<tr>
			
			<td>${v.id}</td> 
			
			<td>${v.controlName}</td> 
			
			<td>${v.dataSource}</td> 
			
			<td>${v.controlRes}</td> 
			
			
			
			<td>
				<a href="${manageUrl}-edit?id=${v.id}">编辑</a>  
				<a href="${manageUrl}-doDel?id=${v.id}" onclick="return confirm('本操作不可恢复,确认删除?')"> 删除</a> 
			</td>
		</tr>
		</c:forEach>
	</tbody>
</table>
<%@include file="/WEB-INF/html/pager.jsp" %>
<%@include file="/WEB-INF/html/foot.jsp" %>
