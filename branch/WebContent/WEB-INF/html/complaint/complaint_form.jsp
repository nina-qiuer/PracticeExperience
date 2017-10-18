<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<script language="javascript" src="${CONFIG.res_url}script/complaint/complaint/complaint.js" ></script>


</HEAD>
<BODY>
<h2>${request.formTitle}</h2>
<form name="form" id ="form" method="post" enctype="multipart/form-data"  action="${manageUrl}-${request.formAction}" onSubmit = "return complaint_check_form();">
<input type="hidden" name="entity.id" id="id"  value="${entity.id}" />
<input type="hidden" name="refer_to" id="refer_to" value="" />
<table width="700" class="datatable">
	
	<tr>
		 <th align="right" width="100">负责人<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.manager" id="manager" value="${entity.manager}"  />
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">发起人姓名<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.ownerName" id="owner_name" value="${entity.ownerName}"  />
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">订单状态<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.orderState" id="order_state" value="${entity.orderState}"  />
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">客人姓名<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.guestName" id="guest_name" value="${entity.guestName}"  />
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">状态位<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.state" id="state" value="${entity.state}"  />
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">关联订单id<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.orderId" id="order_id" value="${entity.orderId}"  />
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">售前客服<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.customer" id="customer" value="${entity.customer}"  />
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">投诉等级<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.level" id="level" value="${entity.level}"  />
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">处理人<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.deal" id="deal" value="${entity.deal}"  />
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">处理人姓名<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.dealName" id="deal_name" value="${entity.dealName}"  />
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">高级经理<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.seniorManager" id="senior_manager" value="${entity.seniorManager}"  />
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">客服要求<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.requirement" id="requirement" value="${entity.requirement}"  />
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">订单来源<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.orderComeFrom" id="order_come_from" value="${entity.orderComeFrom}"  />
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">发起人所在

部门<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.ownerPartment" id="owner_partment" value="${entity.ownerPartment}"  />
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">投诉时间<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.buildDate" id="build_date" value="${entity.buildDate}"  />
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">投诉说明<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.descript" id="descript" value="${entity.descript}"  />
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">客服经理<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.customerLeader" id="customer_leader" value="${entity.customerLeader}"  />
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">出发城市<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.startCity" id="start_city" value="${entity.startCity}"  />
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">线路类型<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.routeType" id="route_type" value="${entity.routeType}"  />
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">线路<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.route" id="route" value="${entity.route}"  />
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">产品经理<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.productLeader" id="product_leader" value="${entity.productLeader}"  />
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">投诉发起人<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.ownerId" id="owner_id" value="${entity.ownerId}"  />
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">负责人姓名<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.managerName" id="manager_name" value="${entity.managerName}"  />
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">产品专员<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.producter" id="producter" value="${entity.producter}"  />
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">投诉来源<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.comeFrom" id="come_from" value="${entity.comeFrom}"  />
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">完成时间<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.finishDate" id="finish_date" value="${entity.finishDate}"  />
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">订单类型<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.orderType" id="order_type" value="${entity.orderType}"  />
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">客人数量<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.guestNum" id="guest_num" value="${entity.guestNum}"  />
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
