<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp"%>
<script type="text/javascript"
	src="${CONFIG.res_url}script/complaint/complaint/appoint_manager.js"></script>
		
	<link rel="stylesheet" href="${CONFIG.res_url}css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="${CONFIG.res_url}script/ztree/jquery.ztree.core-3.1.js"></script>
		
	<SCRIPT type="text/javascript">
	
		function onSubmitClicked() {
			if ($.trim($('#group_email').val()) == '') {
				alert('请填写邮件组！');
				return false;
			}
			if ($.trim($('#group_email_name').val()) == '') {
				alert('请填写邮件组显示名！');
				return false;
			}
			$("form").attr("action", "${manageUrl}" + "-doAddGroup");
			$("form").submit();
			return false;
		}
		
		function onUserSelChanged() {
			var userName = $("#userSel option:selected").text();
			$("#userName").val(userName);
		}
		
		//-->
	</SCRIPT>
</HEAD>
<BODY>
	<h2>增加邮件组</h2>

	<form name="form" id="form" method="post" enctype="multipart/form-data"
		action="receiver_email-save" onSubmit="">
		<input type="hidden" name="entity.id" id="id" value="${entity.id}" />
		<input type="hidden" name="type" id="refer_to" value="${type }" />
		<input type="hidden" name="orderState" id="orderState" value="${orderState }" />
		<table width="100%" class="datatable">
			<tr>
				<th>邮件组</th>
				<td>
					<input type="text" name="group_email" id="group_email" />@tuniu.com
				</td>
			</tr>
			<tr>
				<th>组名</th>
				<td>
					<input type="text" name="group_email_name" id="group_email_name" />
				</td>
			</tr>
			<tr>
				<th></th>
				<td>
					<input type="submit"  class="pd5" value="增加" onclick="onSubmitClicked();self.parent.location.reload();" /> 
					<input type="button" class="pd5" value="取消" onclick="self.parent.tb_remove();" />
				</td>
			</tr>
		</table>
		<font color="red">
		<br />友情提醒：<br />1. 一次只能添加一个邮件组；<br />2. 只需填上邮件组名即可，不需要加后缀；<br />
		</font>
	</form>


	<%@include file="/WEB-INF/html/foot.jsp"%>