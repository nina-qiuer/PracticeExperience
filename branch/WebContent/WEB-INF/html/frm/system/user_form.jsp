<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<script language="javascript" src="${CONFIG.res_url}script/frm/{dir_path}/user.js" ></script>
</HEAD>
<BODY>
<h2>管理</h2>
<form name="form" id ="form" method="post" enctype="multipart/form-data"  action="${manageUrl}-${request.formAction}">
<input type="hidden" name="entity.id" id="id"  value="${entity.id}" />
<input type="hidden" name="refer_to" id="refer_to" value="" />
<table width="700" class="datatable">
	
	<tr>
		 <th align="right" width="100">extension<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.extension" id="extension" value="${entity.extension}"  />
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">user_name<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.userName" id="user_name" value="${entity.userName}"  />
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">email<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.email" id="email" value="${entity.email}"  />
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">position_id<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.positionId" id="position_id" value="${entity.positionId}"  />
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">tel<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.tel" id="tel" value="${entity.tel}"  />
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">real_name<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.realName" id="real_name" value="${entity.realName}"  />
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">dep_id<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.depId" id="dep_id" value="${entity.depId}"  />
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">worknum<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.worknum" id="worknum" value="${entity.worknum}"  />
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">password<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.password" id="password" value="${entity.password}"  />
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">is_sa<span class="required"> *</span></th>
		 <td>
			${ui:makeRadio(constant.defaultRadioOptionMap,"entity.isSa",entity.isSa,null)}
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">mobile<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.mobile" id="mobile" value="${entity.mobile}"  />
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">job_id<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.jobId" id="job_id" value="${entity.jobId}"  />
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
