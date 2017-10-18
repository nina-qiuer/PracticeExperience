<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/html/head.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>供应商承担详细</title>
<link type="text/css" href="${CONFIG.res_url}css/bui.css" rel="stylesheet" />
<script type="text/javascript" src="${CONFIG.res_url}script/jquery/plugin/thickbox/thickbox-compressed.js"></script> 
<link type="text/css" rel="stylesheet" href="${CONFIG.res_url}script/jquery/plugin/thickbox/thickbox.css" />
</head>
<body>
	<div class="top_crumbs">
		查看详细
	</div>
	<table width="100%" class="listtable mb10">
		<tr>
			<th>供应商名称</th>
			<th>承担金额</th>
			
		</tr>
		<c:if test="${empty request.supportShareVoList }">
			<tr>
				<td colspan="2" align="center">暂无数据</td>
			</tr>
		</c:if>
		<c:forEach items="${request.supportShareVoList }" var="supportShareVo">
			<tr align="center">
				<td >${supportShareVo.supprotShareEntity.name}</td>
				<td>${supportShareVo.supprotShareEntity.number}</td>
			</tr>					
		</c:forEach>
	<table>
</body>
</html>
