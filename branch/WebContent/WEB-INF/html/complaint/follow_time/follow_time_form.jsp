<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<script language="javascript" src="${CONFIG.res_url}script/complaint/complaint/follow_time/follow_time.js" ></script>


</HEAD>
<BODY>
<h2>${request.formTitle}</h2>
<form name="form" id ="form" method="post" enctype="multipart/form-data"  action="follow_time-save" onSubmit = "return follow_time_check_form();">
<input type="hidden" name="entity.id" id="id"  value="${entity.id}" />
<input type="hidden" name="refer_to" id="refer_to" value="" />
<table width="700" class="datatable">
	
	<tr>
		 <th align="right" width="100">提醒时间<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.time" id="time" value="${entity.time}"  />
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">关联投诉id<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.complaintId" id="complaint_id" value="${entity.complaintId}"  />
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">提醒用户<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.userId" id="user_id" value="${entity.userId}"  />
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">订单id<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.orderId" id="order_id" value="${entity.orderId}"  />
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">跟进备注<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.note" id="note" value="${entity.note}"  />
		</td>
	</tr>		
	
	<tr>
		<td colspan="2">
			<input type="submit" name="submit1" value="提交" />
		</td>
	</tr>	

</table>
</form>
<%@include file="/WEB-INF/html/foot.jsp" %> 
