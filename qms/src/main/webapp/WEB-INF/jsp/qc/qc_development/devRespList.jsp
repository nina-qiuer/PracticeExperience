<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>责任单信息</title>
<script type="text/javascript">
function iFrameHeight(iframe) {   
	var ifm= document.getElementById(iframe.id);   
	var subWeb = document.frames ? document.frames[iframe.id].document : ifm.contentDocument;   
	if(ifm != null && subWeb != null) {
	   ifm.height = subWeb.body.scrollHeight;
	   ifm.width = subWeb.body.scrollWidth;
	}   
}   

function openWinow(title, url, width, height) {
	parent.layer.open({
        type: 2,
        shade: false,
        maxmin: true,
        title: title,
        content: url,
      // offset : [top ,left],
        area: [width+'px', height+'px']
    });
}

function delDevResp(respId){
	 
	var msg = "您确定删除编号["+respId+"]的责任单吗？";
	layer.confirm(msg, {icon: 3}, function(index){
	$.ajax( {
			url : 'qc/devResp/deleteDevResp',
			data : 	{respId:respId} ,
			type : 'post',
			dataType:'json',
			cache : false,
			success : function(result) {
				if(result)
				{
			    	if(result.retCode == "0")
					{
			    		parent.parent.location.reload();
					 }else{
						layer.alert(result.resMsg, {icon: 2});
					}
			     }
			 }
		    });
	});
}

</script>
</head>
<body>
<form name="form" id="searchForm" method="post" action="" >
<input type="hidden" name="devId" id="devId" value="${devId}">
<div class="pici_search pd5" align="right">
		<input type="button" value="添加责任单" title="添加责任单" class="blue" style="cursor: pointer;"
		onclick="openWinow('添加责任单', 'qc/devResp/${devId}/toAdd',  600, 320)">
	</div>
<c:if test="${respList!=null && fn:length(respList) > 0}">
<table class="listtable" width="100%">
<tr>
	<th>研发责任单号</th>
	<th>故障单号</th>
	<th>责任人</th>
	<th>责任部门</th>
	<th>责任岗位</th>
	<th>改进人</th>
	<th>责任事由</th>
	<th>操作</th>
</tr>
<c:forEach items="${respList}" var="resp" varStatus="st"> 
<tr align="center">
	<td>${resp.id}</td>
	<td>${resp.devId}</td>
	<td>${resp.respPersonName}</td>
	<td class="left">${resp.depName}</td>
	<td>${resp.jobName}</td>
	<td>${resp.impPersonName}</td>
	<td  class="shorten30" title="${resp.respReason}">${resp.respReason}</td>	
	<td>
		<input type="button" class="blue" value="编辑" 
		 onclick="openWinow('修改责任单', 'qc/devResp/${resp.id}/updateResp', 600, 320,'15%','25%')">
		<input type="button" class="blue" value="删除" onclick="delDevResp('${resp.id}')">
	</td>
</tr>
</c:forEach>
</table>
</c:if>

</form>
</body>
