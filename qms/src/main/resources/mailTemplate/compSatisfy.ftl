<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>综合满意度预警</title>
	<style type="text/css">
		.datatable { border:1px solid #fff;border-collapse:collapse;font-size:12px;width: 100%;}
		.datatable th{border:1px solid #fff;text-align:right;color:#355586;background:#DFEAFB; padding:2px;}
		.datatable td{padding:2px;background-color: #F9F5F2;border: 1px solid #fff;}
		.datatable .zrow td { background:#F3F5F8;}
		.main_div{
			width:80%;
			margin: 0 auto;
			font-size: 12px;
		}

		.footer{
			text-align: right;
			margin-top: 10px;
		}
	</style>
</head>
<body>
	<div class="main_div">
		<table class="datatable" style="border:1px solid lightblue">
		   <tr>
		   <td colspan="8" style="text-align:center;color:red">
		      ${title!}
		   </td>
		   </tr>
			<tr>
				<th style="width:12%">综合满意度实际值：</th>
				<td>${compSatisfy!}</td>
				<th style="width:10%">综合满意度目标值：</th>
				<td>${targetValue!}</td>
				<th style="width:10%">投诉率：</th>
				<td>${cmpRate!}</td>
				<th style="width:8%">达成率：</th>
				<td >${reachRate!}</td>
			</tr>
		</table>
		<br>
		<div class="footer">
			<span><strong>质量对接人：</strong>${qcPerson}</span>&nbsp;&nbsp;&nbsp;&nbsp;
			<span><strong>日期：</strong>${dateTime?date}</span>
		</div>
	</div>
</body>
</html>