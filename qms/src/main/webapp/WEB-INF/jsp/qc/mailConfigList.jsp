<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>质检邮件发送范围模板</title>
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
				url : "qc/mailConfig/" + id + "/delete",
				async : false,
				dataType : "json",
				success : function(data) {
					window.location.reload();
				}
			});
		});
	}
</script>
<style type="text/css">
/* .listtable td{word-break: break-all; word-wrap:break-word;} */
</style>
</head>
<body>
<div id="accordion">
<h3>质检邮件发送范围模板</h3>
<div align="right">
	<input type="button" class="blue" value="添加" onclick="openWin('添加质检邮件发送范围模板', 'qc/mailConfig/toAdd', 600, 404)">
</div>
</div>

<table class="listtable">
	<tr>
		<th style="width:10%">质量问题等级</th>
		<th style="width:10%">投诉等级</th>
		<th style="width:25%">收件人</th>
		<th style="width:45%">抄送人</th>
		<th style="width:10%">操作</th>
	</tr>
	<c:forEach items="${dataList}" var="qmc">
	<tr>
		<td style="width:10%">${qmc.respType}</td>
		<td style="width:10%">
			${qmc.cmpLevel}
		</td>
		<td style="text-align: left;width:25%">${qmc.reAddrs}</td>
		<td style="text-align: left;width:45%" class="shorten130" title="${qmc.ccAddrs}">${qmc.ccAddrs}</td>
		<td style="width:10%">
			<input type="button" class="blue" value="修改" onclick="openWin('修改处罚标准', 'qc/mailConfig/${qmc.id}/toUpdate', 600, 404)">
			<input type="button" class="blue" value="删除" onclick="deleteMc('${qmc.id}')">
		</td>
	</tr>
	</c:forEach>
</table>
</body>
</html>
