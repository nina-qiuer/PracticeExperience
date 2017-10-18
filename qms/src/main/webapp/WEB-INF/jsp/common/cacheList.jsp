<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>缓存管理</title>
<script type="text/javascript">
$(document).ready(function(){
	$(".accordion").each(function() {
		$(this).accordion({
	    	collapsible: true,
	    	heightStyle: "content"
	    });
	});
});

function removeCache(key) {
	var msg = "确定要清除缓存[" + key + "]吗？";
	layer.confirm(msg, {icon: 3}, function(){
	    $.ajax({
			type: "POST",
			url: "common/cache/remove",
			data: {"key":key},
			traditional: true,
			async: false,
			success: function(data) {
				window.location.reload();
			}
		});
	});
}

</script>
</head>

<body style="height: 550px;">
<div class="accordion">
	<h3>缓存数据列表</h3>
	<div>
		<table class="listtable" width="100%">
			<tr>
				<th>Key</th>
				<th>Value</th>
				<th>Operate</th>
			</tr>
			<c:forEach items="${dataMap}" var="data">
			<tr align="center">
				<td>${data.key}</td>
				<td>
					<div id="${data.key}" style="display: none;">
						<textarea rows="22" cols="119" readonly="readonly" style="resize: none;">${data.value}</textarea>
					</div>
					<input type="button" onclick="openContent('${data.key} - Value', '${data.key}', 986, 497)" value="See" class="blue">
				</td>
				<td>
					<input type="button" onclick="removeCache('${data.key}')" value="Remove" class="blue">
				</td>
			</tr>
			</c:forEach>
		</table>
	</div>
</div>
</body>
</html>
