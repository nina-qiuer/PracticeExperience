<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>
<html>
<head>
<script type="text/javascript">

function deleteUpLoad(upId){
	
	$.ajax( {
		url : 'qc/upload/deleteQcUpLoad',
		data : 	{upId:upId} ,
		type : 'post',
		dataType:'json',
		cache : false,
		success : function(result) {
			if(result){
		    	if(result.retCode == "0"){
		    		 parent.location.reload();
				 }else{
					layer.alert(result.resMsg, {icon: 2});
				}
		     }
		 }
	    });
}
</script>
</head>
<body>
<form name="attch_form" id="attch_form" method="post" action="" >
<div class="pici_search pd5" align="right">
		<input type="button" class="blue" style="width:100px" value="上传附件" 
		onclick="parent.openWin('上传附件', 'qc/upload/${impId}/improveUploadFile', 800, 300)">
</div>
 <table class="listtable">
	    	<tr>
				<th class="id">附件名称</th>
				<th >操作</th>
		    </tr>
			<c:forEach items="${list}" var="upload" >
				<tr>
			  	<td>
			      <a href="${upload.path}" target="_blank">${upload.name}</a>
			     </td>
			     <td>
			       <input type="button"  value="删除" onclick="deleteUpLoad(${upload.id})" >
			     </td>
			      </tr>
			 </c:forEach>
</table>
</form>
</body>
</html>