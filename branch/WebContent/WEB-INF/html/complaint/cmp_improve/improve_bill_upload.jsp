<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<script language="javascript" src="${CONFIG.res_url}script/complaint/complaint/follow_time/follow_time.js" ></script> 
<script>
function addRow_toolFile(){
	var data_json = [ {} ];
	$.tmpl.add_row("tr_tool_file", data_json);
}

function getFileSize(obj)
{ 
	var name = obj.files[0].name;
    var extStart = name.lastIndexOf(".");
	var ext = name.substring(extStart, name.length).toUpperCase();
	var fileType = ".XLS,.XLSX,.DOC,.DOCX,.GIF,.BMP,.JPG,.PNG,.TXT";
	if (fileType.indexOf(ext) <= 0) {
		alert("只允许上传.XLS,.XLSX,.GIF,.BMP,.JPG,.PNG,.TXT文件！");
		obj.parentNode.parentNode.remove();
		return false;
	}
	
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
		<th>操作</th>
	</thead>
	<tbody>
		<c:forEach items="${request.attachList}" var="v"  varStatus="st"> 
			<tr align="center">
				<td><a href="${v.path}">${v.name}</a></td> 
				<td><fmt:formatDate value="${v.updateTime}" pattern="yyyy-MM-dd hh:mm:ss" /></td> 
				<td>${v.addPerson}</td> 
				<td>
					<a href="complaint-delImproveAttach?attachId=${v.id}&complaintId=${v.complaintId}" onclick="return confirm('本操作不可恢复,确认删除?')">删除</a> 
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<input type="button" value="添加附件" onclick="addRow_toolFile()" />
<form id="upload" action="complaint-improveAttachUpload" enctype="multipart/form-data" method="post">
	<input type="hidden" name="complaintId" value="${complaintId}"/>
	<table id="file_table_add" class="listtable">
		<tr id="tr_tool_file" style="display: none">
			<td><input type="file" name="toolFile" onchange="getFileSize(this);"/></td>
			<td>
				<input type="button" onclick="$(this).parent().parent().remove();" value="删除">　
				<input type="submit" value="上传">
			</td>
		</tr>
	</table>
</form>
