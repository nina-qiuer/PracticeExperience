<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>牛人专线组团形式问题发生率-统计日期维度</title>
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
    	var depNames = new Array();
    	var substdNums = new Array();
    	var totalNums = new Array();
    	var rates = new Array();
		<c:forEach items="${dataList}" var="map">
			depNames.push('${map.statisticDate}');
			substdNums.push('${map.unAgPrdNumTotal}');
			totalNums.push('${map.onsellPrdNumTotal}');
			rates.push('${map.rate}');
		</c:forEach>
    
        // 基于准备好的dom，初始化echarts图表
        var grChart = ec.init(document.getElementById('main'));
        option = {
		    tooltip : {
		        trigger: 'axis',
		        formatter: "{b}<br/>" + 
		        		   "{a0} : {c0}<br/>" + 
		        		   "{a1} : {c1}<br/>" + 
		        		   "{a2} : {c2}%"
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
		        data:['分销独立成团数','分销售卖的牛人专线产品总数','问题发生率']
		    },
		    xAxis : [
		        {
		            type : 'category',
		            data : depNames
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value',
		            name : '产品数',
		            axisLabel : {
		                formatter: '{value}'
		            }
		        },
		        {
		            type : 'value',
		            name : '发生率',
		            axisLabel : {
		                formatter: '{value}%'
		            }
		        }
		    ],
		    series : [
		        {
		            name:'非独立成团数',
		            type:'bar',
		            itemStyle: {
		                normal: {
		                    label : {
		                        show: true, 
		                        position: 'top'
		                    }
		                }
		            },
		            data:substdNums
		        },
		        {
		            name:'分销售卖的牛人专线产品总数',
		            type:'bar',
		            itemStyle: {
		                normal: {
		                    label : {
		                        show: true, 
		                        position: 'top'
		                    }
		                }
		            },
		            data:totalNums
		        },
		        {
		            name:'问题发生率',
		            type:'line',
		            itemStyle: {
		                normal: {
		                    borderWidth: 6,
		                    label : {
		                        show: true, 
		                        position: 'top',
		                        formatter: '{c}%'
		                    }
		                }
		            },
		            yAxisIndex: 1,
		            data:rates
		        }
		    ]
		};

        // 为echarts对象加载数据 
        grChart.setOption(option);
    }
);

$(function(){
	$('#accordion').accordion({
    	collapsible: true,
    	heightStyle: "content"
    });
});

function search() {
	$("#grForm").attr("action", "qs/substdProductSnapshot/showUnAloneGroupRate2");
	$("#grForm").submit();
}
</script>
</head>
<body>
<form id="grForm" method="post" action="qs/substdProductSnapshot/showUnAloneGroupRate2">
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
<div id="accordion">
<h3>牛人专线组团形式问题发生率-统计日期维度</h3>
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
<div id="main" style="height:400px"></div>
</form>
</body>
</html>
