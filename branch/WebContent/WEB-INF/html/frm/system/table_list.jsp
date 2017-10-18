<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
</HEAD>
<BODY>
<h2><a href="${manageUrl}-add">新增</a></h2>
<table class="listtable" width="98%">
		<thead>
		<th>id</th>

				<th>所属应用</th> 
				
				<th>表名</th>

				<th>是否缓存数据</th>

				<th>程序生成目录</th>

			

				<th>添加时间</th>

				<th>编辑时间</th>

				<th>是否生成action</th>

				<th>真实表</th>

				<th>操作</th>

				</thead>

		<tbody>
	
				<c:forEach items="${request.dataList}" var="v"  >
				

				<tr>


						<td>${v.id}</td>   
						
						<td>${v.appId}</td>  

						<td>${v.tableName}</td>

						<td>${v.cacheData}</td>

						<td>${v.dirPath}</td>

						
						<td>
							<fmt:formatDate value="${v.addTime}" pattern="yyyy-MM-dd HH:mm:ss" />
					   </td>

						<td>
							<fmt:formatDate value="${v.updateTime}" pattern="yyyy-MM-dd HH:mm:ss" />
						</td>

						<td>${v.formAction}</td>

						<td>${v.tableExist}</td>  

						<td><a href="${manageUrl}-edit?id=${v.id}">编辑</a>  

						<a href="${manageUrl}-doDel?id=${v.id}" onclick="return confirm('本操作不可恢复,确认删除?')"> 删除</a> 

						<a href="${CONFIG.app_url}frm/action/system/table_field?table_id=${v.id}"> 结构</a>

						</td>

				</tr>

				</c:forEach>


		</tbody>

</table>
<%@include file="/WEB-INF/html/pager.jsp" %>
<%@include file="/WEB-INF/html/foot.jsp" %>