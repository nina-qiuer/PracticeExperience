<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript">
$(document).ready(function() {
	$(".accordion").each(function() {
		$(this).accordion({
	    	collapsible: true,
	    	heightStyle: "content"
	    });
	});
});

function search() {
	
	$("#searchForm").attr("action", "qc/outerResp/list");
	$("#searchForm").submit();
}
</script>
<title>外部责任单列表</title>

</head>
<body >
<form name="searchForm" id="searchForm" method="post" action="" >
	  <div class="accordion">
	   <h3>外部责任单列表</h3> 
	     <div>
			<table width="100%" class="search">
				<tr>
					<td>质量问题编号：<input type="text" id="qpId" name="qpId" value="${dto.qpId}"/></td>
					<td>供应商编号：<input type="text" id="agencyId" name="agencyId" value="${dto.agencyId}"/></td>
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
				<th class="id">外部责任单编号</th>
				<th class="qpId">质量问题编号</th>
				<th class="agencyId">供应商编号</th>
				<th class="agencyName">供应商名称</th>
				<th class="impPersonId">改进人编号</th>
				<th class="impPersonName">改进人姓名</th>
				<th class="addPerson">添加人</th>
				<th class="addTime">添加时间</th>
	   		 </tr>
	   		<c:forEach items="${dto.dataList}" var="outRespBill">
	   		 <tr>
		   		 <td class="id">${outRespBill.id }</td>
		   		 <td class="qpId">${outRespBill.qpId }</td>
		   		 <td class="agencyId">${outRespBill.agencyId }</td>
		   		 <td class="agencyName">${outRespBill.agencyName }</td>
		   		 <td class="impPersonId">${outRespBill.impPersonId }</td>
		   		 <td class="impPersonName">${outRespBill.impPersonName }</td>
		   		 <td class="addPerson">${outRespBill.addPerson }</td>
		   		 <td class="addTime"><fmt:formatDate value="${outRespBill.addTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	   		 </tr>
	        </c:forEach>
	  </table>
 	<%@include file="/WEB-INF/jsp/common/pager.jsp"%>
</form>
</body>
</html>
