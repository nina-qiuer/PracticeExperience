<html lang="en"><head>
	<meta charset="UTF-8">
	<title>触红提醒</title>
	<link type="text/css" rel="stylesheet" href="http://crm.tuniu.com/complaint/res/css/bui.css">
</head>
<body>
		<table style="width:1000px;border-collapse: collapse;border: 2px solid #99bbe8;table-layout: fixed;margin:0 auto">
			<caption>产品[${prd.routeId?c}]第${prd.offlineCount?c}次触红通知</caption>
			<tbody><tr>
			<td>
				<strong>产品名称：</strong>	${prd.routeName!}
			</td>
			</tr>
			<tr>
			<td>
				<strong>产品编号：</strong>  ${prd.routeId?c}
			</td>
			</tr>
			<tr>
			<td>
				<strong>产品专员：</strong>  ${prd.prdOfficer!}
			</td>
			</tr>
			<tr>
			<td>
			  <strong>产品经理：</strong>  ${prd.prdManager!}
			</td>
			</tr>
			<tr>
			<td>
			<strong>问题订单号：</strong> ${prd.orderId?c}
			</td>
			</tr>
			<tr>
			<td>
			<strong>供应商：</strong> ${prd.supplier!}
			</td>
			</tr>
			<tr>
			<td>
				<strong>触红次数：</strong> ${prd.offlineCount?c}
			</td>
			</tr>
			<tr>
			<td>
			<strong>备注：</strong><span style='color: red;font: bold;'>请SQE审核下线</span>
			</td>
			</tr>
		</tbody>
		</table>
	</body>
	</html>