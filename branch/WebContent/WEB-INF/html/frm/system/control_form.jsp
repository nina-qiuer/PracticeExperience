<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<script language="javascript" src="${CONFIG.res_url}script/frm/system/control.js" ></script>
<script>
$(function(){
	var json = {
		'control_html':'${entity.controlHtml}'	
	};
	fill_html(json);
});
</script>
</HEAD>
<BODY>
<h2>管理</h2>
<form name="form" id ="form" method="post" enctype="multipart/form-data"  action="${manageUrl}-${request.formAction}">
<input type="hidden" name="entity.id" id="id"  value="${entity.id}" />
<input type="hidden" name="refer_to" id="refer_to" value="" />
<table width="700" class="datatable">
	
	
	
	<tr>
		 <th align="right" width="100">控件名称<span class="required"> *</span></th>
		 <td>
			<input type="text" name="entity.controlName" id="control_name" value="${entity.controlName}"  /> 
		</td>
	</tr>		
		
	<tr>
		 <th align="right" width="100">是户定义<span class="required"> *</span></th>
		 <td>
			${ui:makeRadio(constant.defaultRadioOptionMap,"entity.userDefine",entity.userDefine,null)}
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">控件html<span class="required"> *</span></th>
		 <td>
			<textarea  name="entity.controlHtml" id="control_html" rows="3" cols="60" >${entity.controlHtml}</textarea>
		</td>
	</tr>		
	
	
	
	<tr>
		 <th align="right" width="100">控件使用资源<span class="required"> *</span></th>
		 <td>
			<textarea  name="entity.controlRes" id="control_res" rows="3" cols="60" >${entity.controlRes}</textarea>
		</td>
	</tr>	
	
	<tr>
		 <th align="right" width="100">初始化代码<span class="required"> *</span></th>
		 <td>
			<textarea  name="entity.initCode" id="init_code" rows="3" cols="60" >${entity.initCode}</textarea>
		</td>
	</tr>	
	
	
	<tr>
		 <th align="right" width="100">附加代码<span class="required"> *</span></th>
		 <td>
			<textarea  name="entity.aftCode" id="aft_code" rows="3" cols="60" >${entity.aftCode}</textarea>
		</td>
	</tr>		
	
	<tr>
		 <th align="right" width="100">是否有数据源<span class="required"> *</span></th>
		 <td>
			${ui:makeRadio(constant.defaultRadioOptionMap,"entity.needDs",entity.needDs,null)} 
		</td>
	</tr>	
	
	<tr>
		 <th align="right" width="100">数据源<span class="required"> *</span></th>
		 <td>
			<textarea  name="entity.dataSource" id="data_source" rows="3" cols="60" >${entity.dataSource}</textarea>
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



