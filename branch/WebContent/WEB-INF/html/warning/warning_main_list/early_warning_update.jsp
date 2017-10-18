<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<script type="text/javascript" src="${CONFIG.res_url}/script/jquery/plugin/jquery.form.js"></script>
<script type="text/javascript">
<!--
$(document).ready(function(){
	var options = {
	    beforeSubmit:  checkSubmit,  // pre-submit callback 
	    success:       success_function,  // post-submit callback 
	}; 
    $('#form').ajaxForm(options); 
});

function checkSubmit() {
	
}

function success_function() {
	self.parent.closeWindow();
}
//-->
</script>
</HEAD>
<BODY>
<form name="form" id ="form" method="post" enctype="multipart/form-data" action="early_warning-updateEarlyWarning">
<input type="hidden" name="entity.id" value="${entity.id}">
<table width="100%" class="datatable">
<tr>
	 <th align="right" width="80">预警等级</th>
	 <td>
		<select class="mr10" name="entity.warningLv">
			<option value="1" <c:if test='${entity.warningLv == 1}'>selected</c:if>>红色预警</option>
			<option value="2" <c:if test='${entity.warningLv == 2}'>selected</c:if>>橙色预警</option>
			<option value="3" <c:if test='${entity.warningLv == 3}'>selected</c:if>>黄色预警</option>
			<option value="4" <c:if test='${entity.warningLv == 4}'>selected</c:if>>蓝色预警</option>
		</select>
	</td>
</tr>
<tr>
	 <th align="right" width="80">预警类型</th>
	 <td>
		<select class="mr10" name="entity.warningType">
			<option value="1" <c:if test='${entity.warningType == 1}'>selected</c:if>>天气预警</option>
			<option value="2" <c:if test='${entity.warningType == 2}'>selected</c:if>>突发事件</option>
			<option value="99" <c:if test='${entity.warningType == 99}'>selected</c:if>>其他</option>
		</select>
	</td>
</tr>
<tr>
	 <th align="right" width="80">预警内容</th>
	 <td>
		<textarea name="entity.content" id="content" cols="35" rows="4">${entity.content}</textarea>
	</td>
</tr>
<tr>
	<th align="right" width="80"></th>
	<td>
		<input type="submit" value="提交">
	</td>
</tr>
</table>
</form>
</BODY>
