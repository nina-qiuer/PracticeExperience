<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>
<html>
<head>
<title>质量问题单</title>
<script type="text/javascript">
function iFrameHeight(iframe) {   
	var ifm= document.getElementById(iframe.id);   
	//var subWeb = document.frames ? document.frames[iframe.id].document : ifm.contentDocument;   
	if(ifm != null) {
	   ifm.height = "400px";
	//   ifm.width = "1000px";
	}   
}

$(document).ready(function(){
	
	$(".accordion").each(function() {
		$(this).accordion({
	    	collapsible: true,
	    	heightStyle: "content"
	    });
	});

});

function deleteQp(qpId){
	
	var msg = "您确定删除编号["+qpId+"]的质量问题单吗？";
	layer.confirm(msg, {icon: 3}, function(index){
	$.ajax( {
		url : 'qc/qualityProblem/deleteQp',
		data : 	{qpId:qpId} ,
		type : 'post',
		dataType:'json',
		cache : false,
		success : function(result) {
			if(result)
			{
		    	if(result.retCode == "0")
				{
		    		 parent.location.reload();
		    		//document.frames['qualityProblem'].location.reload();
				 }else{
					
					layer.alert(result.resMsg, {icon: 2});
				}
		     }
		 }
	    });
	});
}

function deleteUpLoad(upId){
	
	$.ajax( {
		url : 'qc/upload/deleteUpLoad',
		data : 	{upId:upId} ,
		type : 'post',
		dataType:'json',
		cache : false,
		success : function(result) {
			if(result)
			{
		    	if(result.retCode == "0")
				{
		    		 parent.location.reload();
		    		//window.location.reload();
		    		// parent.frames['qualityProblem'].location.reload();
				 }else{
					
					layer.alert(result.resMsg, {icon: 2});
				}
		     }
		 }
	    });
	
}

function openWinow(title, url, width, height,top,left) {
	parent.layer.open({
        type: 2,
        shade: false,
        maxmin: true,
        title: title,
        content: url,
        offset : [top ,left],
        area: [width+'px', height+'px']
    });
}
</script>
</head>
<body >
<form name="form" id="searchForm1" method="post" action="">
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
<input type="hidden" name="qcBillId" id="qcBillId" value="${qcBillId}">
<div class="pici_search pd5" align="right">
	<input type="button" value="添加质量问题单" title="添加质量问题单" class="blue" style="cursor: pointer;"
		onclick="openWinow('添加质量问题单', 'qc/qualityProblem/${qcBillId}/addInnerQuestion', 870, 450,'20%','30%')">
</div>
<c:forEach items="${qpList}" var="qp" varStatus="st">
<div class="common-box">
	<div class="common-box-hd">
		<span class="title2">质量问题单 ${st.count}</span>
	</div>
<table class="datatable">
	<tr>
		<th width="100">问题单号：</th><td>${qp.id}</td>
		<th width="100">问题类型：</th><td>
			${qp.qptName}
		</td>
		<th width="80" rowspan="3">操作：</th>
		<td width="150" align="center" rowspan="3">
			<input type="button" class="blue" style="width:100px" value="修改" onclick="openWinow('修改质量问题单', 'qc/qualityProblem/${qp.id}/updateInnerQuestion', 870, 450,'20%','30%')"><br>
			<input type="button" class="blue" style="width:100px" value="删除" onclick="deleteQp(${qp.id})"><br>
			<input type="button" class="blue" style="width:100px" value="上传附件" onclick="parent.openWin('上传附件', 'qc/upload/${qp.id}/qcUploadFile', 870, 300)">
		</td>
	</tr>
	<tr>
		<th>问题描述：</th><td colspan="3">${qp.description}</td>
	</tr>
	<tr>
		<th>问题判定：</th><td colspan="3">${qp.impAdvice}</td>
	</tr>
	<c:if test="${qp.upLoadList!=null && fn:length(qp.upLoadList)>0}">
	<tr>
	<th>附件：</th>
	<td colspan="5">
		<table>
			<tr>
				<c:forEach items="${qp.upLoadList}" var="upload" >
			  <td >
			     <a href="${upload.path}" target="_blank">${upload.name}</a>
			       <input type="image" src="res/plugins/validation/images/unchecked.gif" onclick="deleteUpLoad(${upload.id});return false;" >
			       </td>
			         </c:forEach>
			   </tr>
			 
		 </table>
	  </td>
	</tr>
	</c:if>
	<tr>
		<td colspan="6">
			<div class="accordion">
				<h3>责任单</h3>
				<div>
				 <iframe src="qc/qualityProblem/${qp.id}/${qp.qcId}/innerResp" width="100%" height="250px" id="respBill" name="respBill" scrolling="auto" frameborder="0"></iframe>
				</div>
			</div>
		</td>
	</tr>
</table>
</div>
</c:forEach>
</form>
</body>
</html>
