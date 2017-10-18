<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<style type="text/css">
body{font-family:Arial,sans-serif;padding:0;background:#ffffff;font-size:12px;color:#000000;line-height:1.5;}

/*table*/
table {font-size:12px;}
.listtable { border:1px solid #C1D3EB;border-collapse:collapse; font-size:12px;width:100%}
.listtable th { padding:2px;text-align:center;border:1px solid #C1D3EB;color:#3E649D;background:#DFEAFB;font-weight:normal;}
.listtable td {	text-align:center;border: 1px solid #C1D3EB;padding:2px; background:#fff;}
.listtable tr:hover { background-color:#FFC;}
.listtable tr:hover td { background-color:#FFC;}
.listtable .zrow td { background:#F3F5F8;}
.listtable .avb td { background:#BFFEBF;}
.datatable { border:1px solid #fff;border-collapse:collapse;font-size:12px;width:100%}
.datatable th{border:1px solid #fff;color:#355586;background:#DFEAFB; padding:2px; text-align: right; height: 27px;}
.datatable td{padding:2px;background-color: #F9F5F2;border: 1px solid #fff;}
.datatable .zrow td { background:#F3F5F8;}
.listtable a,.datatable a { text-decoration: underline;}
.greytable { border:1px solid #dcdcdc;border-collapse:collapse;}
.greytable th{border:1px solid #dcdcdc;background:#f6f6f6;height:28px;font-weight:normal;}
.greytable td{border:1px solid #dcdcdc;padding:2px;height:20px;line-height:20px;}
.greytable .zrow td { background:#f7f7f7;}
</style>
</head>
<body>
<#if (splList?size > 0)>
<h3>销售期长不合格产品列表</h3>
	<table class="listtable">
		<tr>
			<th>产品ID</th>
			<th>产品名称</th>
			<th>产品部</th>
			<th>产品组</th>
			<th>产品经理</th>
			<th>产品专员</th>
			<th>目的地大类</th>
			<th>最远团期</th>
			<th>销售期长<br>（天）</th>
			<th>标准销售期长<br>（天）</th>
			<th>差值<br>（天）</th>
		</tr>
		<#list splList as spl>
		<tr>
			<td>${spl.prdId?c}</td>
			<td title="${spl.prdName}" style="text-align: left;">
				<#if spl.prdName?length gt 12>
					${spl.prdName[0..10]}......
				<#else>
	     			${spl.prdName}
				</#if>
			</td>
			<td>${spl.prdDep}</td>
			<td>${spl.prdTeam}</td>
			<td>${spl.prdManager}</td>
			<td>${spl.producter}</td>
			<td>${spl.destCate}</td>
			<td>${spl.furthestGroupDate}</td>
			<td>${spl.salesPeriodLength}</td>
			<td>${spl.stdSalesPeriodLength}</td>
			<td>${spl.splDifValue}</td>
		</tr>
		</#list>
	</table>
<hr style="height:10px;border:none;border-top:10px groove skyblue;" />
</#if>

<#if (grList?size > 0)>
<h3>团期丰富度不合格产品列表</h3>
	<table class="listtable">
		<tr>
			<th>产品ID</th>
			<th>产品名称</th>
			<th>产品部</th>
			<th>产品组</th>
			<th>产品经理</th>
			<th>产品专员</th>
			<th>目的地大类</th>
			<th>最远团期</th>
			<th>销售期长<br>（天）</th>
			<th>当前剩余<br>团期数量</th>
			<th>团期丰富度</th>
			<th>标准<br>团期丰富度</th>
			<th>差值</th>
		</tr>
		<#list grList as gr>
		<tr>
			<td>${gr.prdId?c}</td>
			<td title="${gr.prdName}" style="text-align: left;">
				<#if gr.prdName?length gt 12>
					${gr.prdName[0..10]}......
				<#else>
	     			${gr.prdName}
				</#if>
			</td>
			<td>${gr.prdDep}</td>
			<td>${gr.prdTeam}</td>
			<td>${gr.prdManager}</td>
			<td>${gr.producter}</td>
			<td>${gr.destCate}</td>
			<td>${gr.furthestGroupDate}</td>
			<td>${gr.salesPeriodLength}</td>
			<td>${gr.surplusGroupNum}</td>
			<td>${gr.groupRichness?string('0.0%')}</td>
			<td>${gr.stdGroupRichness?string('0.0%')}</td>
			<td>${gr.grDifValue?string('0.0%')}</td>
		</tr>
		</#list>
	</table>
<hr style="height:10px;border:none;border-top:10px groove skyblue;" />
</#if>

<#if (agList?size > 0)>
<h3>非独立成团牛人专线列表</h3>
	<table class="listtable">
		<tr>
			<th>产品ID</th>
			<th>产品名称</th>
			<th>产品部</th>
			<th>产品组</th>
			<th>产品经理</th>
			<th>产品专员</th>
			<th>目的地大类</th>
		</tr>
		<#list agList as uag>
		<tr>
			<td>${uag.prdId?c}</td>
			<td title="${uag.prdName}" style="text-align: left;">
				<#if uag.prdName?length gt 12>
					${uag.prdName[0..10]}......
				<#else>
	     			${uag.prdName}
				</#if>
			</td>
			<td>${uag.prdDep}</td>
			<td>${uag.prdTeam}</td>
			<td>${uag.prdManager}</td>
			<td>${uag.producter}</td>
			<td>${uag.destCate}</td>
		</tr>
		</#list>
	</table>
<hr style="height:10px;border:none;border-top:10px groove skyblue;" />
</#if>

<#if (ntList?size > 0)>
<h3>出团通知发送超时订单列表</h3>
	<table class="listtable">
		<tr>
			<th>订单ID</th>
			<th>产品ID</th>
			<th>产品名称</th>
			<th>产品部</th>
			<th>产品组</th>
			<th>产品经理</th>
			<th>产品专员</th>
			<th>目的地大类</th>
			<th>出游团期</th>
			<th>出团通知<br>发送日期</th>
			<th>超时天数</th>
		</tr>
		<#list ntList as ord>
		<tr>
			<td>${ord.ordId?c}</td>
			<td>${ord.prdId?c}</td>
			<td title="${ord.prdName}" style="text-align: left;">
				<#if ord.prdName?length gt 12>
					${ord.prdName[0..10]}......
				<#else>
	     			${ord.prdName}
				</#if>
			</td>
			<td>${ord.prdDep}</td>
			<td>${ord.prdTeam}</td>
			<td>${ord.prdManager}</td>
			<td>${ord.producter}</td>
			<td>${ord.destCate}</td>
			<td>${ord.departDate}</td>
			<td>${(ord.noticeSendDate)!}</td>
			<td>${ord.timeOutDays}</td>
		</tr>
		</#list>
	</table>
</#if>
</body>
</html>
