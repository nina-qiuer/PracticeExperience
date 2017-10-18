<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>质检邮件发送范围模板</title>
<script type="text/javascript">
$(function(){

	$(document).tooltip({
	      content: function() {
	          var element = $( this );
	            var text = $(element).attr("title");
	            var emails = text.split(";");
	            var tooltip="";
	            for (var i=0;i<emails.length;i++){
	            	tooltip += emails[i]+";<br/>";
	            }
	            return tooltip;
	        }
	});
	
	

	});
	
	function chooseConfig(obj){
		var email = '${email}';
		var  reEmails = "";
		var  ccEmails = "";
		var reEmailsTd  =  $(obj).parent().next().next().next();
		var ccEmailsTd =  $(reEmailsTd).next();
		if($(reEmailsTd).attr('title')) {
			reEmails = $(reEmailsTd).attr('title');
		}else {
			reEmails = $(reEmailsTd).text();
		}
		
		if($(ccEmailsTd).attr('title')) {
			ccEmails = $(ccEmailsTd).attr('title');
		}else {
			ccEmails = $(ccEmailsTd).text();
		}
		
		parent.reEmails.value=reEmails;
		if(email!=''){
			
			parent.ccEmails.value=ccEmails+";"+email;
		}else{
			
			parent.ccEmails.value=ccEmails;
		}
		
		 var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
		parent.layer.close(index); //再执行关闭   
	}

</script>
<style type="text/css">
</style>
</head>
<body>
<table class="listtable">
	<tr>
		<th style="width:10%">选择</th>
		<th style="width:10%">质量问题等级</th>
		<th style="width:10%">投诉等级</th>
		<th style="width:25%">收件人</th>
		<th style="width:45%">抄送人</th>
	</tr>
	<c:forEach items="${dataList}" var="qmc">
	<tr>
		<td style="width:10%"><input type="radio"  name="choosedConfig"  value="${qmc.id}"  title="点选后自动关闭并填充"  onclick="chooseConfig(this)"/></td>
		<td style="width:10%">${qmc.respType}</td>
		<td style="width:10%">
			${qmc.cmpLevel}
		</td>
		<td style="text-align: left;width:25%" class="shorten15">${qmc.reAddrs}</td>
		<td style="text-align: left;width:45%" class="shorten50" title="${qmc.ccAddrs}">${qmc.ccAddrs}</td>
	</tr>
	</c:forEach>
</table>
</body>
</html>
