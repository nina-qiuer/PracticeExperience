<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>
<html>
<head>
<title>质检单信息</title>
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
function deleteFault(devId){
	
	var msg = "您确定删除编号["+devId+"]的故障单吗？";
	layer.confirm(msg, {icon: 3}, function(index){
	$.ajax( {
		url : 'qc/devFault/deleteFault',
		data : 	{devId:devId} ,
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

function deleteDevUpLoad(upId){
	
	$.ajax( {
		url : 'qc/upload/deleteDevUpLoad',
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
</script>
</head>
<body >
<form name="form" id="searchForm1" method="post" action="" >
<input type="hidden" name="qcBillId" id="qcBillId" value="${qcBillId}">
		<div class="pici_search pd5" align="right">
			<input type="button" value="添加开发故障单" title="添加开发故障单" class="blue" style="cursor: pointer;"
		onclick="openWinow('添加故障单', 'qc/devFault/${qcBillId}/addDevFault',  870, 520,'15%','25%')">
		<input type="button" value="添加测试故障单" title="添加测试故障单" class="blue" style="cursor: pointer;"
		onclick="openWinow('添加故障单', 'qc/devFault/${qcBillId}/addTestFault',  870, 520,'15%','25%')">
	</div>
<c:forEach items="${devList}" var="dev"  varStatus="st">
	<c:if test="${dev.useFlag == 0}">
	<table class="datatable" >
	<tr>
		<th align="right" width="100">开发故障单号：</th>
		<td width="150" height="30px">${dev.id}</td>
		<th align="right" width="100">故障类型：</th>
		<td width="250" height="30px">${dev.qptName}</td>
		<th align="right" width="150">操作：</th>
		<td  width="400">　
			<input type="button" class="blue" style="width:100px"   value="编辑" onclick="openWinow('修改故障单', 'qc/devFault/${dev.id}/updateDevFault',  870, 520,'15%','25%')">　
			<input type="button" class="blue" style="width:100px"  value="删除" onclick="deleteFault(${dev.id})">
			<input type="button" class="blue" style="width:100px"  value="上传附件" onclick="openWinow('上传附件', 'qc/upload/${dev.id}/devUploadFile',  870, 300,'15%','25%')">　
		</td>
	</tr>
	<tr>
	<th align="right" width="100">是否为上线(含变更)引起：</th>
	  <c:if test="${dev.onLine == 0}">
			<td width="150" height="30px">是</td>
	  </c:if>
	    <c:if test="${dev.onLine == 1}">
			<td width="150" height="30px"  colspan="7">否</td>
	  </c:if>
	  <c:if test="${dev.onLine == 0}">
	  <th align="right" width="100">JIRA地址或单号：</th>
	  <td  colspan="5"  height="30px">${dev.jiraAddress }</td>
	 </c:if>
	</tr>
	<tr>
		<th align="right" width="100">责任系统：</th>
		<td  colspan="7"  title="${dev.influenceSystem}">${dev.influenceSystem}</td>
	</tr>
	<tr>
		<th align="right" width="100">原因分析：</th>
		<td  colspan="7"  title="${dev.causeAnalysis}">${dev.causeAnalysis}</td>
	</tr>
	<tr>
		<th align="right" width="100">处理措施：</th>
		<td  colspan="7" title="${dev.treMeasures}">${dev.treMeasures}</td>
	</tr>
	<tr>
		<th align="right" width="100">改进措施：</th>
		<td  colspan="7"  title="${dev.impMeasures}">${dev.impMeasures}</td>
	</tr>
	<c:if test="${dev.upLoadList!=null && fn:length(dev.upLoadList)>0}">
	<tr>
	<th align="right" width="100">附件：</th>
	<td  colspan="8" >
		<table>
			<tr>
				<c:forEach items="${dev.upLoadList}" var="upload" >
			  <td>
			     <a href="${upload.path}" target="_blank">${upload.name}</a>
			       <input type="image" src="res/plugins/validation/images/unchecked.gif" onclick="deleteDevUpLoad(${upload.id});return false;" >
			       </td>
			         </c:forEach>
			   </tr>
			 
		 </table>
	  </td>
	</tr>
	</c:if>
	<tr>
	<td colspan="8">
		<div class="accordion">
			<h3>责任归属</h3>
			<div>
			 <iframe src="qc/devFault/devResp?devId=${dev.id}" width="100%" height="250px" id="respBill" name="respBill" scrolling="auto" frameborder="0"  ></iframe>
			</div>
		</div>
	</td>
</tr>
	</table>
</c:if>
<c:if test="${dev.useFlag == 1}">
	<table class="datatable" >
	<tr>
		<th align="right" width="100">测试故障单号：</th>
		<td width="150" height="30px">${dev.id}</td>
		<th align="right" width="100">故障类型：</th>
		<td width="250" height="30px">${dev.qptName}</td>
		<th align="right" width="150">操作：</th>
		<td  width="400">　
			<input type="button" class="blue" style="width:100px"   value="编辑" onclick="openWinow('修改故障单', 'qc/devFault/${dev.id}/updateTestFault',  870, 520,'15%','25%')">　
			<input type="button" class="blue" style="width:100px"  value="删除" onclick="deleteFault(${dev.id})">
			<input type="button" class="blue" style="width:100px"  value="上传附件" onclick="openWinow('上传附件', 'qc/upload/${dev.id}/devUploadFile',  870, 300,'15%','25%')">　
		</td>
	</tr>
	<tr>
		<th align="right" width="100">漏测分析：</th>
		<td  colspan="7"  title="${dev.causeAnalysis}">${dev.causeAnalysis}</td>
	</tr>
	<tr>
		<th align="right" width="100">改进措施：</th>
		<td  colspan="7"  title="${dev.impMeasures}">${dev.impMeasures}</td>
	</tr>
	<c:if test="${dev.upLoadList!=null && fn:length(dev.upLoadList)>0}">
	<tr>
	<th align="right" width="100">附件：</th>
	<td  colspan="8" >
		<table>
			<tr>
				<c:forEach items="${dev.upLoadList}" var="upload" >
			  <td>
			     <a href="${upload.path}" target="_blank">${upload.name}</a>
			       <input type="image" src="res/plugins/validation/images/unchecked.gif" onclick="deleteDevUpLoad(${upload.id});return false;" >
			       </td>
			         </c:forEach>
			   </tr>
			 
		 </table>
	  </td>
	</tr>
	</c:if>
	<tr>
	<td colspan="8">
		<div class="accordion">
			<h3>责任归属</h3>
			<div>
			 <iframe src="qc/devFault/devResp?devId=${dev.id}" width="100%" height="250px" id="respBill" name="respBill" scrolling="auto" frameborder="0"  ></iframe>
			</div>
		</div>
	</td>
</tr>
	</table>
</c:if>
		<c:if test="${st.count < fn:length(devList)}"><hr style="height:2px;border:none;border-top:2px solid #8FBC8F;" /></c:if>
</c:forEach>
</form>
</body>
</html>
