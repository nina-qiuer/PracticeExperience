<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>

<HEAD>
<script type="text/javascript">
$(document).ready(function() {
	var succFlag = '${succFlag}';
    if (1 == succFlag) {
    	alert("提交退款申请成功！");
    	parent.location.replace(parent.location.href);
    }
});
</script>
</HEAD>
<BODY>
<h2>${request.formTitle}</h2>
<form name="form" id ="form" method="post" enctype="multipart/form-data"  action="refund_apply-submitApply">
<input type="hidden" name="entity.complaintId" value="${entity.complaintId}" />
<table class="datatable" width="100%">
	<tr>
		 <th align="center" width="120">投诉单号</th>
		 <td>${entity.complaintId}</td>
	</tr>
	<tr>
		 <th align="center">退款金额</th>
		 <td>
			<input type="text" name="entity.amount">&nbsp;元
		</td>
	</tr>
	<tr>
		 <th align="center">退款原因</th>
		 <td>
			<textarea rows="3" cols="50" name="entity.reason"></textarea>
		</td>
	</tr>
	<tr>
		 <th align="center">邮件收件人<br>（英文逗号间隔）</th>
		 <td>
			<textarea rows="1" cols="50" name="entity.recipients">${entity.recipients }</textarea>
			<br>（伴有RTX提醒）
		</td>
	</tr>
	<tr>
		 <th align="center">邮件抄送人<br>（英文逗号间隔）</th>
		 <td>
			<textarea rows="1" cols="50" name="entity.ccs">${entity.ccs }</textarea>
		</td>
	</tr>
	<tr>
		<th align="center"></th>
		<td>
			<input type="submit" class="pd5" value="提交">
		</td>
	</tr>	
</table>
</form>
</BODY>
