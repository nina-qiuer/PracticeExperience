<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>第三方投诉中间层处理</title>
<style type="text/css">
.common-box {
	border: 2px solid #8CBFDE;
	margin:0 0 10px 0;
}
.common-box-hd {
	margin-top: 1px;
	padding-left: 10px;
	background: #C6E3F1;
	color: #005590;
	font-size: 14px;
	font-weight: bold;
	height: 25px;
	line-height: 25px;
	border-bottom: 1px solid #8CBFDE;
	position: relative;
}
.common-box-hd span.title2 {
	cursor: pointer;
	display: inline-block;
	line-height: 25px;
	margin-right: 15px;
}
</style>
<script type="text/javascript">
$(document).ready(function() {  
	$(".content").click(function() { // 注册点击事件
	    var $td = $(this);
	    // 检测此td是否已经被替换了，如果被替换直接返回
	    if ($td.children("input").length > 0) {
	        return false;
	    }
	    var text = $td.text();
	    var $textarea = $("<textarea style='height: 19px; font-size: 10pt' rows='1' cols='70'></textarea>");
	    $textarea.text(text);
	    $td.html("");
	    $td.append($textarea);
	    $textarea.focus();

	    $textarea.click(function(event) {
			event.stopPropagation();
		});

	    $textarea.blur(function() { // 鼠标点出即提交，将td中的内容修改成获取的value值
	    	var value = $textarea.val();
	    	var idStr = $td.attr("id");
	    	var id = idStr.substring(8, idStr.length);
	    	$.ajax({
	    		type: "POST",
	    		url: "complaint-updateContent",
	    		data: {"id":id, "content":value},
	    		async: false,
	    		success: function(data) {
	    			$td.html(value);
	    		} 
	    	});
	    });
	    
	    $textarea.keyup(function(event) {
	        if (event.which == 27) { // 按ESC，将td中的内容还原
	        	$td.html(text);
	        }
	    });
	});
});

function chgVisible(id, flag, complaintId) {
	$.ajax({
		type: "POST",
		url: "complaint-chgVisible",
		data: {"id":id, "flag":flag, "complaintId":complaintId},
		async: false,
		success: function(data) {
			if (1 == flag) {
				$("#showButton_" + id).attr("disabled", true);
				$("#hideButton_" + id).attr("disabled", false);
				$("#hideButton_" + id).parent().parent().css("color", "black");
			} else {
				$("#showButton_" + id).attr("disabled", false);
				$("#hideButton_" + id).attr("disabled", true);
				$("#hideButton_" + id).parent().parent().css("color", "gray");
			}
		} 
	});
}
</script>
</HEAD>
<body>
<div class="common-box">
	<div class="common-box-hd">
		<span class="title2">投诉处理记录</span>
	</div>
	<table width="100%" class="listtable" style="TABLE-LAYOUT: fixed;">
		<tr align="center">
			<th width="130">添加时间</th>
			<th width="60">操作人</th>
			<th width="130">事件</th>
			<th>详情</th>
			<th width="150">对第三方</th>
		</tr>
		<c:forEach items="${noteThs }" var="v">
		<c:if test="${v.visibleFlag == 0}"><tr align="center" style="color: blue;"></c:if>
		<c:if test="${v.visibleFlag == 1}"><tr align="center" style="color: black;"></c:if>
		<c:if test="${v.visibleFlag == -1}"><tr align="center" style="color: gray;"></c:if>
			<td><fmt:formatDate value="${v.addTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td>${v.personName }</td>
			<td>${v.flowName }</td>
			<td align="left" class="content" id="content_${v.id}">${v.content }</td>
			<td>
				<input type="button" id="showButton_${v.id}" value="显示" class="blue" onclick="chgVisible(${v.id}, 1, ${v.complaintId});" <c:if test="${v.visibleFlag == 1}">disabled</c:if>>　
				<input type="button" id="hideButton_${v.id}" value="隐藏" class="blue" onclick="chgVisible(${v.id}, -1, ${v.complaintId});" <c:if test="${v.visibleFlag == -1}">disabled</c:if>>
			</td>
		</tr>
		</c:forEach>
	</table>
</div>
</body>
</HTML>
