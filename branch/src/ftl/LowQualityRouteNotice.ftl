<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>低质量产品预计下线通知</title>
	<style type="text/css">

		div{
			width:800px;
			margin: 0 auto;
		}
		table{
			width: 100%;
			border-collapse: collapse;
		}
		table th{
			font-weight: normal;
			width: 20%;
			background-color: #A6A6A6;
			border: 1px solid black;
		}
		table td{
			border: 1px solid black;

		}
	</style>
</head>
<body>
	<div>
		<h3 style="text-align:center">低质量产品下线通知</h3>
		<table>
			<tr>
				<th>线路名称</th>
				<td>${vo.routeName!}</td>
			</tr>
			<tr>
				<th>线路编号</th>
				<td>${vo.routeId?c}</td>
			</tr>
			<tr>
				<th>产品专员</th>
				<td>${vo.prdOfficer!}</td>
			</tr>
			<tr>
				<th>产品经理</th>
				<td>${vo.prdManager!}</td>
			</tr>
			<tr>
				<th>GMV排名</th>
				<td>${vo.prdArea}方向后5%（${vo.prdName}/${vo.prdArea}）</td>
			</tr>
			<tr>
				<th>低质量原因</th>
				<td>
					<#switch vo.offlineCause>
						<#case 1><p>触红次数达到2次的产品线路</p>
						<p>触红信息:</p>
						<#list vo.touchRedCauseList as cause>
							<p>${cause.triggerTime?date}  <a href="${APP_URL}complaint/action/qc-view?id=${cause.qcId?c}">查看详情</a></p>
						</#list>
						<#break>
						<#case 2><p>满意度低：(75%非牛人专线产品小于75%；牛人专线产品线路小于90%)</p><#break>
						<#case 3><p>综合满意度后5%的产品线路。 满意度值为：${vo.satisfation!100}%</p><#break>
						<#default>
					</#switch>
					
				</td>
			</tr>
			<tr>
				<th>详细情况</th>
				<td><a href="${APP_URL}punishprd/action/punishprd-lowQualityDetail?id=${vo.id?c}">查看</a><font color='red'>（链接到“低质量产品详情”表单</font>）</td>
			</tr>
		</table>
	</div>
</body>
</html>