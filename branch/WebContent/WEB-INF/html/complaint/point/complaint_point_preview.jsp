<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/html/head.jsp"%>
<title>投诉点</title>
<style type="text/css">
.datatable td{
	text-align:center;
}

.common-box {
	border: 1px solid #8CBFDE;
	margin: 0 0 10px 0;
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

</head>
<body>
	<div class="common-box">
			<div class="common-box-hd">
				<span class="title2">投诉质检点</span>
			</div>
			<table class="datatable" style="width:100%">
			   <c:forEach items="${dataList }" var = "point" varStatus="status">
					<tr>
						<th style="width:10%">投诉点${status.count }：</th>
						<td style="width:90%" class="shorten200">${point.typeDescript }</td>
					</tr>
				</c:forEach>
			</table>
	</div>
	<div class="common-box">
			<div class="common-box-hd">
				<span class="title2">核实结果</span>
			</div>
			<table class="datatable" style="width:100%">
			<c:forEach items="${dataList }" var = "point" varStatus="status">
					<tr>
						<th style="width:10%">核实结果${status.count }：</th>
						<td style="width:65%" class="shorten200">${point.verify }</td>
						<th style="width:15%">理论赔付金额：</th>
						<td style="width:10%">${point.theoryPayout }元</td>
					</tr>
			</c:forEach>
			</table>
	</div>
	<div class="common-box">
			<div class="common-box-hd">
				<span class="title2">最终处理方案</span>
			</div>
			<table class="datatable" style="width:100%">
				<tr>
					<td class="shorten500">${finalConclution }</td>
				</tr>
			</table>
	</div>
	
	<div class="common-box">
			<div class="common-box-hd">
				<span class="title2">处理方案</span>
			</div>
			<table class="datatable" style="width:100%">
					<tr>
						<th style="width:15%">理论赔付总额：</th>
						<td style="width:85%">${totalTheoryPayout }元</td>
					</tr>
					<tr>
						<th style="width:15%">实际赔付总额：</th>
						<td style="width:85%">${actualTotalPayout }元</td>
					</tr>
			</table>
	</div>
</body>
</html>
