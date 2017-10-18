<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<script language="javascript" src="${CONFIG.res_url}script/complaint/complaint/follow_time/follow_time.js" ></script> 


</HEAD>
<BODY>
<div class="clear" id="pici_tab">
  <ul> 
    <span class="rf"><a target="_blank" href="${manageUrl}-add"><input type="button" value="新增" name="" id="" class="blue"></a></span>   
  	<li class="menu_on"><s class="rc-l"></s><s class="rc-r"></s><a href="#">列表</a></li>
  </ul>
</div>

<table class="listtable" width="98%">
	<thead>
		
		<th>PK</th>
		
		<th>提醒时间</th>
		
		<th>关联投诉id</th>
		
		<th>提醒用户</th>
		
		<th>加入时间</th>
		
		<th>删除标示位</th>
		
		<th>订单id</th>
		
		<th>跟进备注</th>
		
		<th>操作</th>
	</thead>
	<tbody>
		<c:forEach items="${request.dataList}" var="v"  varStatus="st"> 
		<tr>
			
			<td>${v.id}</td> 
			
			<td>${v.time}</td> 
			
			<td>${v.complaintId}</td> 
			
			<td>${v.userId}</td> 
			
			<td>${v.addTime}</td> 
			
			<td>${v.delFlag}</td> 
			
			<td>${v.orderId}</td> 
			
			<td>${v.note}</td> 
			
			<td>
				<a href="${manageUrl}-edit?id=${v.id}">编辑</a>  
				<a href="${manageUrl}-doDel?id=${v.id}" onclick="return confirm('本操作不可恢复,确认删除?')">删除</a> 
			</td>
		</tr>
		</c:forEach>
	</tbody>
</table>
<%@include file="/WEB-INF/html/pager.jsp" %>
<%@include file="/WEB-INF/html/foot.jsp" %>
