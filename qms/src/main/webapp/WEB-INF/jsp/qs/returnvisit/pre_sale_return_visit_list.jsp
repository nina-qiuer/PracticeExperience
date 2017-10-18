<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<style type="text/css">
/*搜索框表格样式*/
.search {
	font-size: 12px;
	font-family: Microsoft YaHei;
}

.search td:nth-child(even) {
	text-align: left;
}

.search td:nth-child(odd) {
	text-align: right;
}

.search td input[type=text] {
	width: 100px;
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
	
		$("#searchForm").attr("action", "qs/returnVisit/list");
		$("#searchForm").submit();

}
function setMaxDate(id){
	WdatePicker({maxDate:'#F{$dp.$D(\''+id+'\')}'});
}

function setMinDate(id){
	WdatePicker({minDate:'#F{$dp.$D(\''+id+'\')}'});
}

function resetSearchTable(){
	
	$('.search :text').val('');
}

</script>
<title>售前短信回访列表</title>
</head>
<body>
<form name="searchForm" id="searchForm" method="post" action="">
	  <div class="accordion">
	   <h3>售前短信回访列表</h3> 
	   <div>
			<table width="100%" class="search">
				<tr>
					<td>订单号：</td>
					<td><form:input path="dto.ordId"/></td>
					<td>客服专员：</td>
					<td><form:input path="dto.customer"/></td>
					<td>客服经理：</td>
					<td><form:input path="dto.customerLeader"/></td>
					<td>售前服务评分</td>
					<td>
					<form:select path="dto.score" class="score" style="width:100px" >
								<form:option value="" label="全部" />
								<form:option value="3" label="满意(3分)" />
								<form:option value="2" label="一般(2分)" />
								<form:option value="0" label="不满意(0分)" />
						 	</form:select>		
					</td>
					<td>不满意原因</td>
					<td>
					<form:select path="dto.unsatisfyReason" class="unsatisfyReason" style="width:110px" >
								<form:option value="" label="全部" />
								<form:option value="4" label="业务熟练度" />
								<form:option value="5" label="解决问题及时性" />
								<form:option value="6" label="服务态度" />
								<form:option value="7" label="工作责任心" />
						 	</form:select>		
					</td>
					<td>回访日期：</td>
					<td>
						<form:input path="dto.returnVisitDateBgn"  onfocus="setMaxDate('returnVisitDateEnd')" />
						 至
						<form:input path="dto.returnVisitDateEnd"  onfocus="setMinDate('returnVisitDateBgn')" />
					</td>
					<td>
					    <input type="button" class="blue" 	value="查询" onclick="searchResetPage()"/> 
						<input type="button" class="blue" value="重置" onclick="resetSearchTable()" />
					</td>
				</tr>
				</table>
			</div>
	  </div>
	  <table class="listtable">
	    	<tr>
				<th>订单号</th>
				<th>产品名称</th>
				<th>事业部</th>
				<th>部门</th>
				<th>组别</th>
				<th>售前客服专员</th>
				<th>售前客服经理</th>
				<th>售前服务评分</th>
				<th>不满意原因</th>
				<th>回访日期</th>
	   		 </tr>
	   		<c:forEach items="${dto.dataList}" var="v">
	   		 <tr>
		   		 <td class="orderId" >${v.ordId }</td>
		   		 <td class="shorten30">${v.prdName }</td>
		   		 <td class="businessUnitName">${v.businessUnitName }</td>
		   		 <td class="departmentName">${v.departmentName }</td>
		   		 <td class="groupName" >${v.groupName }</td>
		   		 <td class="customer">${v.customer }</td>
		   		 <td class="customerLeader">${v.customerLeader }</td>
		   		 <td class="score">
		   		    <c:if test="${v.score!=-1 }">
					  ${v.score }
					</c:if>
		   		 </td>
		   		 <td class="unsatisfyReason">
			   		<c:if test="${v.unsatisfyReason ==4}">业务熟练度</c:if>
					<c:if test="${v.unsatisfyReason ==5}">解决问题及时性</c:if>
					<c:if test="${v.unsatisfyReason ==6}">服务态度</c:if>
					<c:if test="${v.unsatisfyReason ==7}">工作责任心</c:if>
		   		 </td>
		   		 <td class="returnVisitDate"><fmt:formatDate value="${v.returnVisitDate }" pattern="yyyy-MM-dd"/></td>
	   		 </tr>
	        </c:forEach>
	  </table>
 	<%@include file="/WEB-INF/jsp/common/pager.jsp"%>
</form>
</body>
</html>
