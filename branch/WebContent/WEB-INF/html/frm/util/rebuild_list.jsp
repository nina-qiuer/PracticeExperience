<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
</HEAD>
<BODY>
<h2>重建应用</h2>

<form class="mb10 clear" method="POST" id="search_form" action="${CONFIG.app_url}frm/action/util/rebuild"> 
	<select name="search.app_id" id="app_id" onChange="$('#search_form').get(0).submit();"> 
		${ui:makeSelect(request.appTblPreMap,search.app_id,null)} 
	</select>
	
</form>
<table class="listtable" width="98%">
		<thead>
				<th>id</th>

				<th>所属应用</th> 
				
				<th>表名</th>

				<th>程序生成目录</th>
				
				<th>模块</th>

				<th>操作</th>

				</thead>

		<tbody>
	
				<c:forEach items="${dataList}" var="v"  varStatus="st" >

				<tr>
						 
						<td>${v.id}</td>
						
						<td>${v.app_id}</td>  

						<td>${v.table_name}</td>
						
						<td>${v.dir_path}</td>
						
						<td>
							<input type="checkbox"  value="all" onclick="check_all('module[${st.index}]',this);"> 全部 
							<input type="checkbox" name="module[${st.index}][]" value="action"> action 
							<input type="checkbox" name="module[${st.index}][]" value="entity"> entity 
							<input type="checkbox" name="module[${st.index}][]" value="sqlmap"> sqlmap 
							<input type="checkbox" name="module[${st.index}][]" value="dao"> dao 
							<input type="checkbox" name="module[${st.index}][]" value="service"> service 
							<input type="checkbox" name="module[${st.index}][]" value="js"> js 
							<input type="checkbox" name="module[${st.index}][]" value="html"> html 						
						</td>

						<td>
							<a href="${CONFIG.app_url}frm/action/util/rebuild-doRebuild?table_id=${v.id}" onclick="this.href=this.href+'&modules='+get_checked_ids('module[${st.index}]');return window.confirm('本操作将强制生成并复盖原有文件');">重建</a>  

						</td>

				</tr>

				</c:forEach>

		</tbody>
</table>
<%@include file="/WEB-INF/html/foot.jsp" %>