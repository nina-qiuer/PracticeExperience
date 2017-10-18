<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script  type="text/javascript">
$(document).ready(function() {
	$(".accordion").each(function() {
		$(this).accordion({
	    	collapsible: true,
//	    	active:false, //默认折叠
	    	heightStyle: "content"
	    });
	});
});

function search() {
	
	$("#searchForm").attr("action", "qc/innerResp/list");
	$("#searchForm").submit();
}
</script>
<title>内部责任单列表</title>

</head>
<body >
<form name="searchForm" id="searchForm" method="post" action="" >
	  <div class="accordion">
	   <h3>内部责任单列表</h3> 
	   <div>
			<table width="100%" class="search">
				<tr>
					<td>质量问题编号：<input type="text" id="qpId" name="qpId" value="${dto.qpId}"/></td>
					<td>添加时间：
					<input id="addTimeFrom" name="addTimeFrom" class="Wdate" type="text"
						value='<fmt:formatDate value="${dto.addTimeFrom }" pattern="yyyy-MM-dd"/>'
						onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'addTimeTo\')}'})" readonly="readonly"/>
						至 <input id="addTimeTo" name="addTimeTo" class="Wdate"
						type="text"  value='<fmt:formatDate value="${dto.addTimeTo }" pattern="yyyy-MM-dd"/>'
						onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'addTimeFrom\')}'})" readonly="readonly"/>
						
						</td>
					<td style="text-align: left"><input type="button" class="blue" 
						value="查询" onclick="search()"/> <input type="reset" class="blue" value="重置" />
						</td>
				</tr>
				</table>
			</div>
	  </div>
	  <table class="listtable">
	    	<tr>
				<th class="id">内部责任单编号</th>
				<th class="qpId">质量问题编号</th>
				<th class="respPersonName">责任人名称</th>
				<th class="depId">责任部门编号</th>
				<th class="jobId">责任岗位编号</th>
				<th class="impPersonName">改进人名称</th>
				<th class="addPerson">添加人</th>
				<th class="addTime">添加时间</th>
	   		 </tr>
	   		<c:forEach items="${dto.dataList}" var="innerRespBill">
	   		 <tr>
		   		 <td class="id">${innerRespBill.id }</td>
		   		 <td class="qpId">${innerRespBill.qpId }</td>
		   		 <td class="respPersonName">${innerRespBill.respPersonName }</td>
		   		 <td class="depId">${innerRespBill.depId }</td>
		   		 <td class="jobId">${innerRespBill.jobId }</td>
		   		 <td class="impPersonName">${innerRespBill.impPersonName }</td>
		   		 <td class="addPerson">${innerRespBill.addPerson }</td>
		   		 <td class="addTime"><fmt:formatDate value="${innerRespBill.addTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	   		 </tr>
	        </c:forEach>
	  </table>
 	<%@include file="/WEB-INF/jsp/common/pager.jsp"%>
</form>
</body>
</html>
