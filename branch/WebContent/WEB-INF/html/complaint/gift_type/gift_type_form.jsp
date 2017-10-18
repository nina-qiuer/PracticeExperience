<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<script language="javascript" src="${CONFIG.res_url}script/complaint/complaint/gift_type/gift_type.js" ></script>


</HEAD>
<BODY>
<h2>${request.formTitle}</h2>
<form name="form" id ="form" method="post" enctype="multipart/form-data"  action="${manageUrl}-${request.formAction}" onSubmit = "return gift_type_check_form();">
<input type="hidden" name="entity.id" id="id"  value="${entity.id}" />
<input type="hidden" name="refer_to" id="refer_to" value="" />
<table width="700" class="datatable">
	
	<tr>
		 <th align="right" width="100">价格<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.price" id="price" value="${entity.price}"  />
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">名字<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.name" id="name" value="${entity.name}"  />
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">描述<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.descript" id="descript" value="${entity.descript}"  />
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">成本<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.cost" id="cost" value="${entity.cost}"  />
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
