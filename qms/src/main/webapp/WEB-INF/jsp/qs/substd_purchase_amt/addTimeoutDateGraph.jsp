<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>采购单添加超时图表-添加日期维度</title>
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
    	var addDatesNum = new Array();
    	var nums = new Array();
    	<c:forEach items="${dataListNum}" var="map">
			addDatesNum.push('${map.addDate}');
			nums.push('${map.num}');
		</c:forEach>
    	
    	var addDatesTotPrice = new Array();
    	var totPrices = new Array();
		<c:forEach items="${dataListTotPrice}" var="map">
			addDatesTotPrice.push('${map.addDate}');
			totPrices.push('${map.totPrice}');
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
		        data:['超时采购单数']
		    },
		    xAxis : [
		        {
		            type : 'category',
		            data : addDatesNum
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
		            name:'超时采购单数',
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
		
		optionTotPrice = {
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
		        data:['超时采购总额']
		    },
		    xAxis : [
		        {
		            type : 'category',
		            data : addDatesTotPrice
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
		            name:'超时采购总额',
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
		            data:totPrices
		        }
		    ]
		};
		
        var dataChartNum = ec.init(document.getElementById('mainNum'));
        dataChartNum.setOption(optionNum); // 超时采购单数
        
        var dataChartTotPrice = ec.init(document.getElementById('mainTotPrice'));
        dataChartTotPrice.setOption(optionTotPrice); // 超时采购总额
    }
);

$(function(){
	$('#accordion').accordion({
    	collapsible: true,
    	heightStyle: "content"
    });
});

function search() {
	$("#dataForm").attr("action", "qs/substdPurchaseAmt/addTimeoutDateGraph");
	$("#dataForm").submit();
}
</script>
</head>
<body>
<form id="dataForm" method="post" action="qs/substdPurchaseAmt/addTimeoutDateGraph">
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
<div id="accordion">
<h3>采购单添加超时图表-添加日期维度</h3>
<div align="right">
<table width="70%">
	<tr>
		<td>
			统计日期：<form:input path="dto.addDateBgn" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly" size="10"/> - 
			<form:input path="dto.addDateEnd" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly" size="10"/>
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
<div id="mainTotPrice" style="height:300px"></div>
</form>
</body>
</html>
