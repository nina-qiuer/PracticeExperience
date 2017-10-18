<html lang="en">
<head>
<meta charset="UTF-8">
<title>供应商责任赔款查漏提醒</title>
</head>
<body>
	<table>
		<tr>
			<td><strong>投诉单号：</strong> ${cmpId?c}</td>
		</tr>
		<tr>
			<td><strong>质检单号：</strong> ${qcId?c}</td>
		</tr>
		<tr>
			<td><strong>订单号：</strong> ${ordId?c}</td>
		</tr>
		<tr>
			<td><strong>产品编号：</strong> ${prdId?c}</td>
		</tr>
		<tr>
			<td><strong>产品名称：</strong> ${prdName!}</td>
		</tr>
		<tr>
			<td><strong>责任供应商：</strong> ${agencyName!}</td>
		</tr>
		<tr>
			<td><strong>责任类型：</strong> ${qualityProblemName!}</td>
		</tr>
		<tr>
			<td><strong>分担总额：</strong> ${shareAmount?c}</td>
		</tr>
		<tr>
			<td><strong>备注：</strong><span style='color: red; font: bold;'>质检判定供应商有责，但投诉分担方案中供应商承担为零，分担方案异常，请产品向供应商索赔追责损失，调整分担方案。(内部邮件，请勿外传)</span></td>
		</tr>
	</table>
</body>
</html>