<!-- HTML head 元素已经在head.jsp中包含 -->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>质检报告</title>
<link type="text/css" href="res_urlcss/bui.css" rel="stylesheet" />
<style type="text/css">
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
	<div class="top_crumbs">
		您当前所在的位置：<a href="#">投诉质检管理</a>>><span class="top_crumbs_txt">质检报告</span>
	</div>
	<div class="common-box">
		<div class="common-box-hd">
			<span class="title2">投诉情况说明单</span>
		</div>
		<table width="100%" class="datatable">
			<!-- tr>
				<th width="100" align="right">标题：</th>
				<td colspan="5">titles</td>
			</tr-->
			<tr>
				<th width="100" align="right">订单号：</th>
				<td>orderId</td>
				<th width="100" align="right">客户姓名：</th>
				<td>guestName</td>
				<th width="100" align="right">人数：</th>
				<td>guestNum</td>
			</tr>
			<tr>
				<th align="right">出发地：</th>
				<td>startCity</td>
				<th align="right">出发时间：</th>
				<td colspan="3">startTime</td>
			</tr>
			<tr>
				<th align="right">线路：</th>
				<td colspan="5">route</td>
			</tr>
			<tr>
				<th align="right">满意度：</th>
				<td colspan="5">score</td>
			</tr>
			<tr>
				<th height="36" align="right">售前客服：</th>
				<td>customer</td>
				<th align="right">客服经理：</th>
				<td>customerLeader</td>
				<th align="right">高级客服经理:</th>
				<td>serviceManager</td>
			</tr>
			<tr>
				<th align="right">产品专员：</th>
				<td>producter</td>
				<th align="right">产品经理：</th>
				<td>productLeader</td>
				<th align="right">高级产品经理：</th>
				<td>seniorManager</td>
			</tr>
			<tr>
				<th align="right">投诉级别：</th>
				<td colspan="5">level</td>
			</tr>
			<tr>
				<th align="right">投诉说明：</th>
				<td colspan="5">descript</td>
			</tr>
			<tr>
				<th align="right">客户要求：</th>
				<td colspan="5">requirement</td>
			</tr>
			<tr>
				<th align="right">受理人：</th>
				<td>dealName</td>
				<th align="right">受理时间：</th>
				<td colspan="3">buildDate</td>
			</tr>
			<tr>
				<th align="right">相关供应商：</th>
				<td colspan="5">agencyName</td>
			</tr>
		</table>
	</div>
	<div class="common-box">
		<div class="common-box-hd">
			<span class="title2">质检报告</span>
		</div>
		<table width="100%" class="datatable">
			<tr>
				<th width="100" align="right">核实情况：</th>
				<td>verify</td>
			</tr>
			<tr>
				<th width="100" align="right">质检结论：</th>
				<td>conclusion</td>
			</tr>


			<tr>
				<th height="36" align="right">问题类型：</th>
				<td>bigClassName</td>
			</tr>
			
			<tr>
				<th align="right">责任归属：</th>
				<td>responsibility</td>
			</tr>
			
			<tr>
				<th align="right">执行岗位：</th>
				<td>position</td>
			</tr>

			<tr>
				<th align="right">责任人：</th>
				<td>responsiblePerson</td>
			</tr>

			<tr>
				<th align="right">改进人：</th>
				<td>improver</td>
			</tr>

		</table>
	</div>
</body>
</html>
