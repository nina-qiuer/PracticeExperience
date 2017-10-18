<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<link href="${CONFIG.res_url}css/thickbox.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${CONFIG.res_url}script/thickbox-compressed.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}script/complaint/complaint/appoint_manager.js" ></script> 
<script type="text/javascript" src="${CONFIG.res_url}script/DatePickerNew/WdatePicker.js" ></script>
<script type="text/javascript">

</script>
<style type="text/css">
	.datatable td table{border-collapse:collapse;}
	.datatable td td{border:0 none;word-wrap:break-word;word-break:break-all;}
	.trbg td{background:#ff9;}
		.listtable tr.yellowbg td{background:#FFFF99}
</style>
</HEAD>
<BODY>
<div>
	<form name="form" id="form" method="post" enctype="multipart/form-data" action="robComplaint-toStatistics">
		<table width="100%" class="datatable">
		    
		    <tr>
   				<td><span class="cred">投诉抢单统计</span></td>
   				<td>
   				<input type="text" size="12" name="statDate" id="stat_date"	value="${statDate }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readOnly="readonly"/>&nbsp;&nbsp;
   				<input type="submit" value="查询" class="blue"/>
   				</td>
		    </tr>
		    <tr>
		      <td colspan="2">
		   	   <table class="listtable" width="100%">
				   <thead>
					 <th>人员</th>
					 <th>当天已分配的投诉单</th>
					 <th>当日分单量</th>
					</thead>
					<tbody>
					<c:forEach items="${complaintList}" var="v"  varStatus="st"> 
					  <tr>
					  <td>${v.userName}</td>
					  <td>${v.orderIds}</td>
					  <td>${v.listNums}</td>
					  </tr>
					</c:forEach>
					</tbody>
		       </table>
		       </td>
		    </tr>
		</table>
	</form>
</div>
</div>
</BODY>
