<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<script language="javascript" src="${CONFIG.res_url}script/complaint/complaint/share_solution.js" ></script> 


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
		
		<th>id</th>
		
		<th>赔偿总额</th>
		
		<th>供应商确认状态,0为未确认，1为确认</th>
		
		<th>抵用卷</th>
		
		<th>礼品折合</th>
		
		<th>关联投诉id</th>
		
		<th>旅游卷</th>
		
		<th>现金</th>
		
		<th>特殊费用</th>
		
		<th>员工确认状态</th>
		
		<th>操作</th>
	</thead>
	<tbody>
		<c:forEach items="${request.dataList}" var="v"  varStatus="st"> 
		<tr>
			
			<td>${v.id}</td> 
			
			<td>${v.total}</td> 
			
			<td>${v.supplierMark}</td> 
			
			<td>${v.replaceBook}</td> 
			
			<td>${v.gift}</td> 
			
			<td>${v.complaintId}</td> 
			
			<td>${v.touristBook}</td> 
			
			<td>${v.cash}</td> 
			
			<td>${v.special}</td> 
			
			<td>${v.employee}</td> 
			
			<td>
				<a href="${manageUrl}-edit?id=${v.id}">编辑</a>  
				<a href="${manageUrl}-doDel?id=${v.id}" onclick="return confirm('本操作不可恢复,确认删除?')"> 删除</a> 
			</td>
		</tr>
		</c:forEach>
	</tbody>
</table>
<%@include file="/WEB-INF/html/pager.jsp" %>
<%@include file="/WEB-INF/html/foot.jsp" %>
