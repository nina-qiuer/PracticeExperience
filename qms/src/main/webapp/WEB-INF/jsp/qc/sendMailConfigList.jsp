<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>邮件发送模板</title>
<script type="text/javascript">
$(function(){
	$('#accordion').accordion({
    	collapsible: true,
    	heightStyle: "content"
    });
	
	
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

	function deleteMc(id) {
		var msg = "确定要删除此模板吗？";
		layer.confirm(msg, {
			icon : 3
		}, function() {
			$.ajax({
				type : "GET",
				url : "qc/sendMailConfig/" + id + "/delete",
				async : false,
				dataType : "json",
				success : function(result) {
					if(result)
					{
				    	if(result.retCode == "0")
						{
				    		searchResetPage();
				    	
						 }else{
							
							layer.alert(result.resMsg, {icon: 2});
						}
				     }
				 }
			});
		});
	}
	
	function search() {
		
		$("#mcForm").attr("action", "qc/sendMailConfig/list");
		$("#mcForm").submit();
	}
	function resetSearchTable() {
		$('.search :text').val('');
	}
</script>
<style type="text/css">
</style>
</head>
<body>
<form name="mcForm" id="mcForm" method="post" action="">
<div id="accordion">
<h3>邮件发送模板</h3>
<div align="right">
   <table width="85%" class="search">
   <tr>
      <td style="text-align: right">配置类型：</td>
     <td style="text-align: left">
         <form:input path="dto.mailType"/>
	</td>
	<td>
		<input type="button" class="blue" 	value="查询" onclick="searchResetPage()"/>
		<input type="button" class="blue" 	value="重置" onclick="resetSearchTable()"/>
		<input type="button" class="blue" value="添加" onclick="openWin('添加邮件发送模板', 'qc/sendMailConfig/toAdd', 600, 404)">
	</td>
	</tr>
	</table>
</div>
</div>

<table class="listtable">
	<tr>
		<th style="width:10%">配置类型</th>
		<th style="width:40%">发送人</th>
		<th style="width:10%">操作</th>
	</tr>
	<c:forEach items="${dataList}" var="qmc">
	<tr>
		<td style="width:10%">${qmc.mailType}</td>
		<td style="text-align: left;width:45%" class="shorten130" title="${qmc.sendAddrs}">${qmc.sendAddrs}</td>
		<td style="width:10%">
			<input type="button" class="blue" value="修改" onclick="openWin('修改配置', 'qc/sendMailConfig/${qmc.id}/toUpdate', 600, 404)">
			<input type="button" class="blue" value="删除" onclick="deleteMc('${qmc.id}')">
		</td>
	</tr>
	</c:forEach>
</table>
</form>
</body>
</html>
