<html lang="en">
<head>
<meta charset="UTF-8">
<title></title>
<style type="text/css">
 table{border-collapse:collapse;border-spacing:0;border-left:1px solid #888;border-top:1px solid #888;background:#efefef;}
 th,td{border-right:1px solid #888;border-bottom:1px solid #888;padding:5px 15px;}
 th{font-weight:bold;background:#ccc;width:200px}
</style>
</head>
<body>
亲爱的业务小伙伴:<br/>
	<strong>&nbsp;&nbsp;&nbsp;&nbsp;质监通过吸星大法发现你的订单存在实际收款少于订单总价的情况，快快核实，是否存在钱款欠收的风险。</strong><br/>
	<strong>&nbsp;&nbsp;&nbsp;&nbsp;本次共抓取${uncollectionMoneys?c}元，${orderAmount?c}单订单，请及时查验，不然出游归来收不回来钱，鸡腿都没得打赏了。</strong><br/><br/>
	数据已经代你整理好了，快施展你的才华吧！<br/><br/>
	<table>
		<tr>
			<th>一级品类</th>
			<th>订单号</th>
			<th>签约时间</th>
			<th>出发日期</th>
			<th>售卖方式</th>
			<th>客服专员</th>
			<th>客服经理</th>
			<th>一级部门</th>
			<th>二级部门</th>
			<th>三级部门</th>
			<th>分销客户经理</th>
		    <th>分销商id </th>
			<th>分销商品牌名</th>
			<th>押款金额</th>
			<th>押款收回日期</th>	
			<th>退团款</th>
			<th>已收金额</th>
			<th>订单金额</th>
			<th>实收金额</th>
			<th>欠收金额</th>				
			<th>距离出游日期</th>
		</tr>
		<#list data.orderList as order>
			<tr>
				<td>${order.oneProducttypeName!}</td>
				<td>${order.orderId?c}</td>
				<td><#if order.signTime??>${order.signTime!}</#if></td>
				<td>${order.startDate!}</td>
				<td>${order.saleType!}</td>
				<td>${order.followSalerName!}</td>
				<td>${order.salerManager!}</td>
				<td>${order.managerOneDept!}</td>
				<td>${order.managerTwoDept!}</td>
				<td>${order.managerThreeDept!}</td>
				<td>${order.distributeManager!}</td>
				<td><#if order.distributorId??> ${order.distributorId?c}</#if></td>
				<td><#if order.distributeCompanyBrand??>${order.distributeCompanyBrand!}</#if></td>
				<td><#if order.orderMortgageSum??>${order.orderMortgageSum?c}</#if></td>
				<td><#if order.estimateRecoveryDate??>${order.estimateRecoveryDate!}</#if></td>
				<td><#if order.refundAll??>${order.refundAll?c}</#if></td>
				<td><#if order.received??>${order.received?c}</#if></td>
				<td><#if order.orderAmount??>${order.orderAmount?c}</#if></td>
				<td><#if order.factReceived??>${order.factReceived?c}</#if></td>
				<td><#if order.orderLostAmount??>${order.orderLostAmount?c}</#if></td>	
				<td><#if order.distanceTravelDayNum??>${order.distanceTravelDayNum?c}</#if></td>
			</tr>
		</#list>
	</table>
	
	<br/></br/>
	<strong>预警说明</strong><br/>
	<strong>&nbsp;&nbsp;监控逻辑：</strong>订单欠收判断：订单总价＞实际收款（已收钱款-退款金额）<br/>		
	<strong>&nbsp;&nbsp;邮件通知逻辑：</strong>对未来5日出游团期的签约订单进行滚动监控，每日抓取存在欠收风险的订单通过邮件提醒业务部门<br/>
	<strong>&nbsp;&nbsp;常见场景及解决方案举例：</strong><br/>
		&nbsp;&nbsp;1.	实际存在应收未收，客服需及时联系客人收取<br/>
		&nbsp;&nbsp;2.	对客已收齐钱款，订单总价错误，需检查是否忘记添加合同增补单<br/>
		&nbsp;&nbsp;3.	实际收款金额少于订单总价，但订单右上角应收未收为0，为系统数据对接问题导致，此时需客服使用订单右侧“工具栏”—“常用操作”—“一键修复”<br/>
</body>
</html>