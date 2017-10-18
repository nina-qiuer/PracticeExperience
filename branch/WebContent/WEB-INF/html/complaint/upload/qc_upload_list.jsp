<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<script language="javascript" src="${CONFIG.res_url}script/complaint/complaint/follow_time/follow_time.js" ></script> 

<script>
//批量添加成本类型附件
function addRow_toolFile(){
	var data_json = [ {} ];
	$.tmpl.add_row("tr_tool_file", data_json);
}

function getFileSize(obj)
{ 
	var fileSize = obj.files[0].size;
	if(fileSize>20971520){
		alert("请上传小于20M的文件！");
		obj.parentNode.parentNode.remove();
	}
}
</script>
</HEAD>
<BODY>
<table class="listtable" width="98%">
	<thead>
		<th>附件名称</th>
		
		<th>上传时间</th>
		
		<th>上传人</th>
		
		<th>备注</th>
		<c:if test="${type=='act' }">
		<th>操作</th>
		</c:if>
	</thead>
	<tbody>
		<c:forEach items="${request.attachList}" var="v"  varStatus="st"> 
		<tr align="center">
			<td><a href="${v.path}">${v.name}</a></td> 
			
			<td><fmt:formatDate value="${v.updateTime}" pattern="yyyy-MM-dd hh:mm:ss" /></td> 
			
			<td>${v.addPerson}</td> 
			
			<td>${v.descript}</td> 
			<c:if test="${type=='act' }">
			<td>
				<a href="complaint-delQcToolFile?attachId=${v.id}&complaintId=${v.complaintId}" onclick="return confirm('本操作不可恢复,确认删除?')">删除</a> 
			</td>
			</c:if>
		</tr>
		</c:forEach>
	</tbody>
</table>
<c:if test="${type=='act' }">
<input type="button" value="添加附件" onclick="addRow_toolFile()" />
<form id="upload" action="complaint-qcUpload" enctype="multipart/form-data" method="post">
<table id="file_table_add" class="listtable">
<input type="hidden" name="complaintId" value="${complaintId}"/>
<tr id="tr_tool_file" style="display: none">
	<td><input type="file" name="toolFile" onchange="getFileSize(this);"/></td>
	<td>
		<input type="button" onclick="$(this).parent().parent().remove();" value="删除">　
		<input type="submit" value="上传">
	</td>
</tr>
</table>
</form>
</c:if>
