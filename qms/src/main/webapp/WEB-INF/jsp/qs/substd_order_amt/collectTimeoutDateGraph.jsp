<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>订单收款超时图表-统计日期维度</title>
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
        'echarts/chart/bar', // 使用柱状图就加载bar模块，按需加载
        'echarts/chart/line'
    ],
    function (ec) {
    	var statisticDatesNum = new Array();
    	var nums = new Array();
    	<c:forEach items="${dataListNum}" var="map">
			statisticDatesNum.push('${map.statisticDate}');
			nums.push('${map.num}');
		</c:forEach>
    	
    	var statisticDatesTotAmount = new Array();
    	var totAmounts = new Array();
		<c:forEach items="${dataListTotAmount}" var="map">
			statisticDatesTotAmount.push('${map.statisticDate}');
			totAmounts.push('${map.totAmount}');
		</c:forEach>
    
        optionNum = {
		    tooltip : {
		        trigger: 'axis',
		        formatter: "{b}<br/>" + 
		        		   "{a0} : {c0}<br/>"
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            magicType: {show: true, type: ['line', 'bar']},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : true,
		    legend: {
		        data:['超时收款单数']
		    },
		    xAxis : [
		        {
		            type : 'category',
		            data : statisticDatesNum
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value',
		            name : '单数',
		            axisLabel : {
		                formatter: '{value}'
		            }
		        }
		    ],
		    series : [
		        {
		            name:'超时收款单数',
		            type:'bar',
		            itemStyle: {
		                normal: {
		                    label : {
		                        show: true, 
		                        position: 'top'
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
		    toolbox: {
		        show : true,
		        feature : {
		            magicType: {show: true, type: ['line', 'bar']},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : true,
		    legend: {
		        data:['超时未收总额']
		    },
		    xAxis : [
		        {
		            type : 'category',
		            data : statisticDatesTotAmount
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value',
		            name : '金额（元）'
		        }
		    ],
		    series : [
		        {
		            name:'超时未收总额',
		            type:'bar',
		            itemStyle: {
		                normal: {
		                	color: '#87cefa',
		                    label : {
		                        show: true, 
		                        position: 'top'
		                    }
		                }
		            },
		            data:totAmounts
		        }
		    ]
		};
		
        var dataChartNum = ec.init(document.getElementById('mainNum'));
        dataChartNum.setOption(optionNum); // 超时收款单数
        
        var dataChartTotAmount = ec.init(document.getElementById('mainTotAmount'));
        dataChartTotAmount.setOption(optionTotAmount); // 超时未收总额
    }
);

$(function(){
	$('#accordion').accordion({
    	collapsible: true,
    	heightStyle: "content"
    });
});

function search() {
	$("#dataForm").attr("action", "qs/substdOrderAmt/collectTimeoutDateGraph");
	$("#dataForm").submit();
}
</script>
</head>
<body>
<form id="dataForm" method="post" action="qs/substdOrderAmt/collectTimeoutDateGraph">
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
<div id="accordion">
<h3>订单收款超时图表-统计日期维度</h3>
<div align="right">
<table width="70%">
	<tr>
		<td>
			统计日期：<form:input path="dto.statisticDateBgn" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly" size="10"/> - 
			<form:input path="dto.statisticDateEnd" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly" size="10"/>
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
<br>
<div id="mainNum" style="height:300px"></div>
<div id="mainTotAmount" style="height:300px"></div>
</form>
</body>
</html>
