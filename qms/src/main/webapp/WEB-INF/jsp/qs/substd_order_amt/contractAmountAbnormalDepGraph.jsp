<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>合同价异常图表-组织架构维度</title>
<script src="res/plugins/echarts-2.2.5/build/dist/echarts.js" type="text/javascript"></script>
<script type="text/javascript">
require.config({
    paths: {
        echarts: 'res/plugins/echarts-2.2.5/build/dist'
    }
});

require(
    [
        'echarts',
        'echarts/chart/bar'
    ],
    function (ec) {
    	var depNamesNum = new Array();
    	var nums = new Array();
    	<c:forEach items="${dataListNum}" var="map">
			depNamesNum.push('${map.depName}');
			nums.push('${map.num}');
		</c:forEach>
    	
    	var depNamesTotAmount = new Array();
    	var totAmounts = new Array();
    	<c:forEach items="${dataListTotAmount}" var="map">
			depNamesTotAmount.push('${map.depName}');
			totAmounts.push('${map.totAmount}');
		</c:forEach>
    
        optionNum = {
        	tooltip : {
		        trigger: 'axis',
		        formatter: "{b}<br/>" + 
		        		   "{a0} : {c0}<br/>"
		    },
		    calculable : true,
		    legend: {
		        data:['合同价异常单数']
		    },
		    xAxis : [
		    	{
		            type : 'value',
		            position: 'top',
		            name : '单数',
		            axisLabel : {
		                formatter: '{value}'
		            }
		        }
		    ],
		    yAxis : [
		        {
		            type : 'category',
		            data : depNamesNum
		        }
		    ],
		    grid: {
		        x:120
		    },
		    series : [
		        {
		            name:'合同价异常单数',
		            type:'bar',
		            itemStyle: {
		                normal: {
		                    label : {
		                        show: true, 
		                        position: 'right'
		                    }
		                }
		            },
		            data:nums
		        }
		    ]
		};
        
        optionTotAmount = {
        	tooltip : {
		        trigger: 'axis',
		        formatter: "{b}<br/>" + 
		        		   "{a0} : {c0}<br/>"
		    },
		    calculable : true,
		    legend: {
		        data:['合同价异常总额']
		    },
		    xAxis : [
		        {
		            type : 'value',
		            position: 'top',
		            name : '金额（元）'
		        }
		    ],
		    yAxis : [
		        {
		            type : 'category',
		            data : depNamesTotAmount
		        }
		    ],
		    grid: {
		        x:120
		    },
		    series : [
		        {
		            name:'合同价异常总额',
		            type:'bar',
		            itemStyle: {
		                normal: {
		                	color: '#87cefa',
		                    label : {
		                        show: true, 
		                        position: 'right'
		                    }
		                }
		            },
		            data:totAmounts
		        }
		    ]
		};
		
		var dataChartNum = ec.init(document.getElementById('mainNum')); // 合同价异常单数
        var dataChartTotAmount = ec.init(document.getElementById('mainTotAmount')); // 合同价异常总额
        dataChartNum.setOption(optionNum);
        dataChartTotAmount.setOption(optionTotAmount);
    }
);

$(function(){
	$('#accordion').accordion({
    	collapsible: true,
    	heightStyle: "content"
    });
});

function search() {
	$("#dataForm").attr("action", "qs/substdOrderAmt/contractAmountAbnormalDepGraph");
	$("#dataForm").submit();
}
</script>
</head>
<body>
<form id="dataForm" method="post" action="qs/substdOrderAmt/contractAmountAbnormalDepGraph">
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
<div id="accordion">
<h3>合同价异常图表-组织架构维度</h3>
<div align="right">
<table width="70%">
	<tr>
		<td>
			添加日期：<form:input path="dto.statisticDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly" size="10"/>
		</td>
		<td>事业部：
			<form:select path="dto.businessUnit" onchange="search()">
				<option value="">全部</option>
				<form:options items="${businessUnits}"/>
			</form:select>
		</td>
		<td>产品部：
			<form:select path="dto.prdDep" onchange="search()">
				<option value="">全部</option>
				<form:options items="${prdDeps}"/>
			</form:select>
		</td>
		<td align="center">
			<input type="button" class="blue" value="查询" onclick="search()">
		</td>
	</tr>
</table>
</div>
</div>
<table width="100%">
	<tr>
		<td width="50%"><div id="mainNum" style="height:${fn:length(dataListNum)*40 + 120 - (fn:length(dataListNum)-1)*10}px"></div></td>
		<td width="50%"><div id="mainTotAmount" style="height:${fn:length(dataListTotAmount)*40 + 120 - (fn:length(dataListNum)-1)*10}px"></div></td>
	</tr>
</table>
</form>
</body>
</html>
