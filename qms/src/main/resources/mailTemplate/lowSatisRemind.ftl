<html lang="en"><head>
	<meta charset="UTF-8">
	<title>线路低满意度提醒</title>
	<link type="text/css" rel="stylesheet" href="http://crm.tuniu.com/complaint/res/css/bui.css">
</head>
<body>
	<div style="">
		<table style="width:1000px;border-collapse: collapse;border: 2px solid #99bbe8;table-layout: fixed;margin:0 auto">
			<caption>线路低满意度提醒</caption>
			<tbody>
			<tr>
			  <td  style="border: 2px solid #99bbe8;text-align: left;width:100px">
			   线路编号：
			 </td>
			 <td colspan="4" style="border: 2px solid #99bbe8;text-align: left;">
			  ${vo.routeId?c}
			 </td>
			 </tr>
			 <tr>
			  <td  style="border: 2px solid #99bbe8;text-align: left;width:100px">
			  线路名称：
			 </td>
			 <td colspan="4" style="border: 2px solid #99bbe8;text-align: left;">
			  ${vo.routeName!}
			 </td>
			</tr>
			<tr>
			   <td  style="border: 2px solid #99bbe8;text-align: left;width:100px">
			    	备注：
			   </td>
				<td colspan="4" style="border: 2px solid #99bbe8;text-align: left;">该线路上周满意度为${(vo.lastWeekSatisfaction!0)?string.percent},
				已初步符合低满意度下线标准,请产品经理及时通知供应商进行整改</td>
			</tr>
			<tr>
			 <td> 
			   <p>低满意度提醒标准</p>
			 </td>
				<td colspan="4" style="color:red;">
					<p>1、上线后成功点评5次以上，上周满意度低于75%的产品</p>
					<p>2、上线后成功点评5次以上，上周满意度低于85% 的实惠游产品；</p>
					<p>3、上线后成功点评5次以上，上周满意度低于90% 的牛人专线、 乐开花爸妈游、 出发吧我们、 瓜果亲子游产品；</p>
					<p>4、上线后成功点评5次以上，上周满意度低于92% 的朋派定制游产品；</p>
					<p>5、上线后成功点评5次以上，上周满意度低于95% 的一路之上产品；</p>
				</td>
			</tr>
		</tbody></table>
	<br>
	<div style="width:1050px;height:495px;margin:0 auto">
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