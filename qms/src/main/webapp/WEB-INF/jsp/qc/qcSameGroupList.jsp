<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<style type="text/css">

.td1{ 
background-color: #000000; 
border-right:  1px solid #c1d3eb; 
border-top:  0px solid;
border-left:  0px solid;
border-bottom:  0px solid;
} 

</style>
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
	
		$("#searchForm").attr("action", "qc/qcBill/listSameGroup");
		$("#searchForm").submit();
}



</script>

<title>同团期投诉订单列表</title>
</head>
<body >
<form name="searchForm" id="searchForm" method="post" action="" >
<form:hidden path="dto.qcBillId"/>
<div class="accordion">
<h3>同团期投诉订单列表</h3> 
</div>
<table class="listtable" style="width:100%">
  <tr>
  		<th class="orderId" style="width:100px">订单号</th>
 		<th class="qcId" style="width:100px">质检单号</th>
		<th class="cmpId" style="width:100px">投诉单号</th>
		<th class="cmpStateName" style="width:200px">投诉处理状态</th>
		<th class="qcStateName" style="width:200px">质检状态</th>
		<th class="qcPerson" style="width:100px">质检员</th>
		<th class="solution">对客处理方案</th>
		
  </tr>
 <c:forEach items="${dto.dataList}" var="qcSameGroup">
 
   <c:if test="${qcSameGroup.list!=null && fn:length(qcSameGroup.list) > 0}">
       <tr>
	 	     <td  class="orderId" style="width:100px" rowspan="${fn:length(qcSameGroup.list)}" >${qcSameGroup.orderId }</td>
	 	 	<c:forEach items="${ qcSameGroup.list}" var="qcGroup" > 
		 	 <td class="qcId" >
		  		 <a href="javascript:void(0)" onclick="openWin('质检报告','qc/qcBill/${qcGroup.qcId}/qcReport',1000,520)" >${qcGroup.qcId}</a>
		     </td>
		  	 <td  class="cmpId" >${qcGroup.cmpId }</td>
		     <td  class="cmpStateName" >${qcGroup.cmpStateName }</td>
		  	 <td  class="qcStateName"  >${qcGroup.qcStateName }</td>
		  	 <td  class="qcPerson"  >${qcGroup.qcPerson }</td>
		  	 <td  class="solution" >${qcGroup.solution }</td>
		  	 </tr>
		  	 </c:forEach>
 	</c:if>
 	 <c:if test="${qcSameGroup.list==null || fn:length(qcSameGroup.list) == 0}">
 	 <tr>
 	   <td style="width:100px" class="orderId" >${qcSameGroup.orderId }</td>
	 	    <td style="width:100px"class="qcId"  style="width:10%">
	  			 <a href="javascript:void(0)" onclick="openWin('质检报告','qc/qcBill/${qcGroup.qcId}/qcReport',1000,520)"></a>
	  		 </td>
	  		 <td style="width:100px" class="cmpId"></td>
	  		 <td style="width:200px" class="cmpStateName"></td>
	  		 <td style="width:200px" class="qcStateName"></td>
	  		 <td style="width:200px" class="qcPerson"></td>
	  		 <td ></td>
 	</tr>
 	</c:if>
 </c:forEach>
</table>
</form>
</body>
</html>
