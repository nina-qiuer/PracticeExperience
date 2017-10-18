<html lang="en"><head>
	<meta charset="UTF-8">
	<title>低满意度下线通知</title>
	<link type="text/css" rel="stylesheet" href="http://crm.tuniu.com/complaint/res/css/bui.css">
</head>
<body>
	<div style="">
		<table style="width:1000px;border-collapse: collapse;border: 2px solid #99bbe8;table-layout: fixed;margin:0 auto">
			<caption>低满意度下线通知</caption>
			<tbody><tr>
				<td style="width:10%;border: 2px solid #99bbe8;text-align: right;">线路名称:</td>
				<td colspan="3" style="border: 2px solid #99bbe8;text-align: left;">${vo.routeName!}</td>
			</tr>
			<tr>
				<td style="width:10%;border: 2px solid #99bbe8;text-align: right;">产品编号:</td>
				<td style="width:40%;border: 2px solid #99bbe8;text-align: left;">${vo.routeId?c}</td>
				<td style="width:12%;border: 2px solid #99bbe8;text-align: right;">低满意度下线次数:</td>
				<td style="width:38%;border: 2px solid #99bbe8;text-align: left;">${vo.offlineCount}</td>
			</tr>
			<tr>
				<td style="border: 2px solid #99bbe8;text-align: right;">备注:</td>
				<td colspan="3" style="border: 2px solid #99bbe8;text-align: left;">产品经理通知供应商针对低满意度下线产品进行整改，并提交整改报告给SQE进行审核。</td>
			</tr>
			<tr>
				<td colspan="4" style="width:15%;border: 2px solid #99bbe8;text-align: left;">低满意度下线规则：</td>
			</tr>
			<tr>
				<td colspan="4" style="color:red;">
					<p>当产品第1次达不到满意度要求时，产品下线整改时间为3天</p>
					<p>当产品第2次达不到满意度要求时，产品下线整改时间为7天；</p>
					<p>当产品第3次达不到满意度要求时，产品下线整改时间为15天；</p>
					<p>当产品第4次达不到满意度要求时，产品下线整改时间为30天；</p>
					<p>当产品第5次达不到满意度要求时，产品永久下线。</p>
				</td>
			</tr>
		</tbody></table>
	<br>
	<div style="width:1050px;height:495px;margin:0 auto">
				<h4>上上周，满意度<span id="detail_last_week_statisfaction">${(vo.twoWeeksAgoSasisfaction!0)?string.percent}</span>，点评详情如下：（<span id="detail_last_week_range">${vo.twoWeeksAgoBgn} 至 ${vo.twoWeeksAgoEnd}</span>）</h4>
				<div id="detail_two_week_ago_remark">
					
							<table style="width: 1000px;border-collapse: collapse;border: 2px solid #99bbe8;margin: auto;table-layout: fixed;">
						<tbody>
							<tr>
								<th style="width:10%;border: 2px solid #99bbe8;text-align: center;">点评时间</th>
								<th style="width:9%;border: 2px solid #99bbe8;text-align: center;">订单号</th>
								<th style="width:9%;border: 2px solid #99bbe8;text-align: center;">线路编号</th>
								<th style="width:9%;border: 2px solid #99bbe8;text-align: center;">出游日期</th>
								<th style="width:7%;border: 2px solid #99bbe8;text-align: center;">满意度</th>
								<th style="width:28%;border: 2px solid #99bbe8;text-align: center;">整体评价</th>
								<th style="width:28%;border: 2px solid #99bbe8;text-align: center;">分项评价</th>
							</tr>
							<#list vo.twoWeeksAgoRemarkList as remark>
							<tr>
								<td style="border: 2px solid #99bbe8;text-align: center;">${remark.remarkTime}</td>
								<td style="border: 2px solid #99bbe8;text-align: center;">${remark.orderId?c}</td>
								<td style="border: 2px solid #99bbe8;text-align: center;">${remark.routeId?c}</td>
								<td style="border: 2px solid #99bbe8;text-align: center;">${remark.startTime!}</td>
								<td style="border: 2px solid #99bbe8;text-align: center;">${remark.satisfaction}%</td>
								<td style="border: 2px solid #99bbe8;text-align: left;white-space: normal;text-overflow: ellipsis;overflow: hidden;" title="${remark.compTextContent!}">${remark.compTextContent!}</td>
								<td style="border: 2px solid #99bbe8;text-align: left;white-space: normal;text-overflow: ellipsis;overflow: hidden;" title="${remark.subGradeContents!}">${remark.subTextContent!}</td>
							</tr>
							</#list>
						</tbody>
					</table>
				</div>
				<br>
				<h4>上周，满意度<span id="detail_statisfaction">${(vo.lastWeekSatisfaction!0)?string.percent}</span>，点评详情如下：（<span id="detail_week_range">${vo.lastWeekBgn} 至 ${vo.lastWeekEnd}</span>）</h4>
				<div id="detail_remark">
					
						<table style="width: 1000px;border-collapse: collapse;border: 2px solid #99bbe8;margin: auto;table-layout: fixed;">
						<tbody>
							<tr>
								<th style="width:10%;border: 2px solid #99bbe8;text-align: center;">点评时间</th>
								<th style="width:9%;border: 2px solid #99bbe8;text-align: center;">订单号</th>
								<th style="width:9%;border: 2px solid #99bbe8;text-align: center;">线路编号</th>
								<th style="width:9%;border: 2px solid #99bbe8;text-align: center;">出游日期</th>
								<th style="width:7%;border: 2px solid #99bbe8;text-align: center;">满意度</th>
								<th style="width:28%;border: 2px solid #99bbe8;text-align: center;">整体评价</th>
								<th style="width:28%;border: 2px solid #99bbe8;text-align: center;">分项评价</th>
							</tr>
							<#list vo.lastWeekRemarkList as remark>
							<tr>
								<td style="border: 2px solid #99bbe8;text-align: center;">${remark.remarkTime}</td>
								<td style="border: 2px solid #99bbe8;text-align: center;">${remark.orderId?c}</td>
								<td style="border: 2px solid #99bbe8;text-align: center;">${remark.routeId?c}</td>
								<td style="border: 2px solid #99bbe8;text-align: center;">${remark.startTime!}</td>
								<td style="border: 2px solid #99bbe8;text-align: center;">${remark.satisfaction}%</td>
								<td style="border: 2px solid #99bbe8;text-align: left;white-space: normal;text-overflow: ellipsis;overflow: hidden;" title="${remark.compTextContent!}">${remark.compTextContent!}</td>
								<td style="border: 2px solid #99bbe8;text-align: left;white-space: normal;text-overflow: ellipsis;overflow: hidden;" title="${remark.subGradeContents!}">${remark.subTextContent!}</td>
							</tr>
							</#list>
						</tbody>
					</table>
				</div>
				<br>
			</div>
		</div>
	</body></html>