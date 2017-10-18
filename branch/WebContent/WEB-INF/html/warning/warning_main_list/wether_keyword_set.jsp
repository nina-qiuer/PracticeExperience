<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<head>
<link type="text/css" href="${CONFIG.res_url}css/bui.css" rel="stylesheet" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
.common-box {
	border: 1px solid #8CBFDE;
	margin:0 0 10px 0;
}
</style>
<title>天气关键词设置</title>
<script type="text/javascript">
function keyWordSet(wc) {
	var wcId = wc.value;
	var flag = wc.checked == true ? 1 : 0;
	$.ajax({
		type: "post",
		data: {"id":wcId,"keyWordFlag":flag},
		url: "early_warning-wetherKeywordSet",
		success: function(data) {},
		error:function() {alert("error");}
	});
}
</script>
</head>
<body>
<div class="top_crumbs">
	您当前所在的位置：<a href="#">预警系统</a>&gt;&gt;<span class="top_crumbs_txt">天气关键词设置</span>
</div>
<div class="common-box">
<table width="100%" class="datatable">
	<tr>
	<c:forEach items="${wcList}" var="wc" varStatus="st">
		<td>
		<label>
			<input type="checkbox" id="wcId_${wc.id }" value="${wc.id }" onchange="keyWordSet(this)" <c:if test="${wc.keyWordFlag == 1}">checked='checked'</c:if>>
			${wc.name}（${wc.code}）
		</label>
		</td>
		<c:if test="${st.count % 6 == 0}">
		</tr>
		<tr>
		</c:if>
	</c:forEach>
	</tr>
</table>
</div>
</body>
