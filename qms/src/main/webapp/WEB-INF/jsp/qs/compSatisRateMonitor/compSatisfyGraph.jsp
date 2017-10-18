<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<style type="text/css">
/*搜索框表格样式*/
.search {
	font-size: 12px;
	font-family: Microsoft YaHei;
}

.search td:nth-child(even) {
	text-align: left;
}

.search td:nth-child(odd) {
	text-align: left;
}

.search td input[type=text] {
	width: 100px;
}
.listtable .orderable {
	background: #DFEAFB url('res/img/line.png') no-repeat right 5px  center;
	text-align: right;
	padding-right: 26px;
}
</style>
<title>综合满意度监控报表</title>
<script src="res/plugins/echarts-2.2.5/build/dist/echarts.js" type="text/javascript"></script>
<script type="text/javascript">
var depArr = new Array();
var timeName= "";
require.config({
    paths: {
        echarts: 'res/plugins/echarts-2.2.5/build/dist'
    }
});
require(
    [
        'echarts',
        'echarts/chart/bar',
        'echarts/chart/line'
    ],
    function (ec) {
    	
    	var timeType = "${dto.timeType}";
    	if(timeType ==1){
    		
    		timeName="季";
    		
    	}else if(timeType ==2){
    		
    		timeName="月";
    		
    	}
    	var statisticDatesNum = new Array();
    	var nums = new Array();
    	var befNums = new Array();
    	var satisNums = new Array();
    	var befSatisNums = new Array();
    	var cmpNums = new Array();
    	var befCmpNums = new Array();
    	<c:forEach items="${dataList}" var="map">
			statisticDatesNum.push('${map.statisticDate}');
			nums.push('${map.reacheRate}');
			satisNums.push('${map.satisfaction}');
			cmpNums.push('${map.cmpRate}');
		</c:forEach>
		<c:forEach items="${befDataList}" var="map1">
		    befNums.push('${map1.reacheRate}');
		    befSatisNums.push('${map1.satisfaction}');
		    befCmpNums.push('${map1.cmpRate}');
		</c:forEach>
		
    	        optionNumOne = {
    	        		
    	         title : {
    	        		        text: '综合满意度达成率同比'
    	        		    },
    			    tooltip : {
    			        trigger: 'axis',
    			        formatter: "{a} <br/>{b} : {c}% "   
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
    			        data:['${dto.year}','${dto.befYear}']
    			    },
    			    xAxis : [
    			        {
    			        	name : timeName,
    			            type : 'category',
    			            data : statisticDatesNum
    			        }
    			    ],
    			    yAxis : [
    			        {
    			            type : 'value',
    			            name : '比例',
    			            axisLabel : {
    			                formatter: '{value}%'
    			            }
    			        }
    			    ],
    			    series : [
    			        {
    			            name:'${dto.year}',
    			            type:'line',
    			            itemStyle: {
    			                normal: {
    			                    label : {
    			                        show: true, 
    			                        position: 'top'
    			                    }
    			                }
    			            },
    			            data:nums
    			        },
    			        {
    			            name:'${dto.befYear}',
    			            type:'line',
    			            itemStyle: {
    			                normal: {
    			                    label : {
    			                        show: true, 
    			                        position: 'top'
    			                    }
    			                }
    			            },
    			            data:befNums
    			        }
    			    ]
    			};
    	        optionNumTwo = {
    	        		
    	    	         title : {
    	    	        		        text: '满意度同比'
    	    	        		    },
    	    			    tooltip : {
    	    			        trigger: 'axis',
    	    			        formatter: "{a} <br/>{b} : {c}% "   
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
    	    			        data:['${dto.year}','${dto.befYear}']
    	    			    },
    	    			    xAxis : [
    	    			        {
    	    			        	name : timeName,
    	    			            type : 'category',
    	    			            data : statisticDatesNum
    	    			        }
    	    			    ],
    	    			    yAxis : [
    	    			        {
    	    			            type : 'value',
    	    			            name : '比例',
    	    			            axisLabel : {
    	    			                formatter: '{value}%'
    	    			            }
    	    			        }
    	    			    ],
    	    			    series : [
    	    			        {
    	    			            name:'${dto.year}',
    	    			            type:'line',
    	    			            itemStyle: {
    	    			                normal: {
    	    			                    label : {
    	    			                        show: true, 
    	    			                        position: 'top'
    	    			                    }
    	    			                }
    	    			            },
    	    			            data : satisNums
    	    			        },
    	    			        {
    	    			            name:'${dto.befYear}',
    	    			            type:'line',
    	    			            itemStyle: {
    	    			                normal: {
    	    			                    label : {
    	    			                        show: true, 
    	    			                        position: 'top'
    	    			                    }
    	    			                }
    	    			            },
    	    			            data : befSatisNums
    	    			        }
    	    			    ]
    	    			};
    	        optionNumThree = {
    	        		
   	    	         title : {
   	    	        		        text: '投诉率同比'
   	    	        		    },
   	    			    tooltip : {
   	    			        trigger: 'axis',
   	    			        formatter: "{a} <br/>{b} : {c}% "   
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
   	    			        data:['${dto.year}','${dto.befYear}']
   	    			    },
   	    			    xAxis : [
   	    			        {
   	    			        	name : timeName,
   	    			            type : 'category',
   	    			            data : statisticDatesNum
   	    			        }
   	    			    ],
   	    			    yAxis : [
   	    			        {
   	    			            type : 'value',
   	    			            name : '比例',
   	    			            axisLabel : {
   	    			                formatter: '{value}%'
   	    			            }
   	    			        }
   	    			    ],
   	    			    series : [
   	    			        {
   	    			            name:'${dto.year}',
   	    			            type:'line',
   	    			            itemStyle: {
   	    			                normal: {
   	    			                    label : {
   	    			                        show: true, 
   	    			                        position: 'top'
   	    			                    }
   	    			                }
   	    			            },
   	    			            data:cmpNums
   	    			        },
   	    			        {
   	    			            name:'${dto.befYear}',
   	    			            type:'line',
   	    			            itemStyle: {
   	    			                normal: {
   	    			                    label : {
   	    			                        show: true, 
   	    			                        position: 'top'
   	    			                    }
   	    			                }
   	    			            },
   	    			            data:befCmpNums
   	    			        }
   	    			    ]
   	    			};
    	var dataChartNumOne = ec.init(document.getElementById('mainNum')); 
    	dataChartNumOne.setOption(optionNumOne);
    	var dataChartNumTwo = ec.init(document.getElementById('mainNumTwo')); 
    	dataChartNumTwo.setOption(optionNumTwo);
    	var dataChartNumThree = ec.init(document.getElementById('mainNumThree')); 
    	dataChartNumThree.setOption(optionNumThree);
    }
);

$(function(){
	$('#accordion').accordion({
    	collapsible: true,
    	heightStyle: "content"
    });
	
});


function resetSearchTable(){
	
	$('.search :text').val('');
}

function checkTime(){
	
	var timeType = document.getElementById("timeType").value;
	var time_type = timeType;
	if(time_type==1){
		
		   $("#quarter").show(); 
		   $("#month").css('display', 'none'); 
	}
	if(time_type==2){
		
		   $("#quarter").css('display', 'none');
		   $("#month").show(); 
	}
}


function search() {
	
	$("#dataForm").attr("action", "qs/compSatisRate/businessUnitTrend");
	$("#dataForm").submit();
}
</script>
</head>
<body>
<form id="dataForm" method="post" action="">
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
  <div id="accordion">
<h3>综合满意度监控</h3>
<div align="right">
<table width="100%" class="search">
				<tr>
				     <td style="text-align: right">时间粒度：</td>
				     <td style="text-align: left">
						<form:select path="dto.timeType" class="timeType"   onchange="checkTime()" style="width:100px">
							<form:option value="1" label="季" />
							<form:option value="2" label="月" />
						</form:select>
					</td>
					  <td style="text-align: right" >  时间范围：</td> 
					<c:if test="${ dto.timeType==1}">
					 <td id="quarter"  style="text-align: left">
					 <form:select path="dto.qYear" class="qYear"  style="width:100px" >
							<form:option value="${dto.nowYear}" label="${dto.nowYear}年" />
							<form:option value="${dto.nowYear-1}" label="${dto.nowYear-1}年" />
							<form:option value="${dto.nowYear-2}" label="${dto.nowYear-2}年" />
					 	</form:select>
						<%-- <form:select path="dto.quarterBgn" class="quarterBgn"   onchange="checkBeginQuarter()" style="width:60px" >
							<form:option value="01" label="Q1" />
							<form:option value="02" label="Q2" />
							<form:option value="03" label="Q3" />
							<form:option value="04" label="Q4" />
					 	</form:select>至
						<form:select path="dto.quarterEnd" class="quarterEnd"   onchange="checkEndQuarter()" style="width:60px">
							<form:option value="01" label="Q1" />
							<form:option value="02" label="Q2" />
							<form:option value="03" label="Q3" />
							<form:option value="04" label="Q4" />
						</form:select>			 --%>
					</td>
					<td id="month" style="display: none">
						<form:select path="dto.mYear" class="mYear"   style="width:100px" >
								<form:option value="${dto.nowYear}" label="${dto.nowYear}年" />
								<form:option value="${dto.nowYear-1}" label="${dto.nowYear-1}年" />
								<form:option value="${dto.nowYear-2}" label="${dto.nowYear-2}年" />
						 	</form:select>
					   <%--    <form:select path="dto.monthBgn" onchange="checkBeginMonth()">
								<form:options items="${monthList}"/>
						 </form:select>至
						 <form:select path="dto.monthEnd" onchange="checkEndMonth()">
								<form:options items="${monthList}"/>
							</form:select> --%>
					 </td>
					</c:if>
					<c:if test="${dto.timeType==2}">
						<td id="month" style="text-align: left" >
						    <form:select path="dto.mYear" class="mYear"  style="width:100px" >
								<form:option value="${dto.nowYear}" label="${dto.nowYear}年" />
								<form:option value="${dto.nowYear-1}" label="${dto.nowYear-1}年" />
								<form:option value="${dto.nowYear-2}" label="${dto.nowYear-2}年" />
						 	</form:select>
					      <%-- <form:select path="dto.monthBgn" onchange="checkBeginMonth()">
								<form:options items="${monthList}"/>
						 </form:select>至
						 <form:select path="dto.monthEnd" onchange="checkEndMonth()">
								<form:options items="${monthList}"/>
							</form:select> --%>
	
						</td>
						<td id="quarter" style="display: none">
						  	 <form:select path="dto.qYear" class="qYear"   style="width:100px" >
								<form:option value="${dto.nowYear}" label="${dto.nowYear}年" />
								<form:option value="${dto.nowYear-1}" label="${dto.nowYear-1}年" />
								<form:option value="${dto.nowYear-2}" label="${dto.nowYear-2}年" />
						 	</form:select>
							<%-- <form:select path="dto.quarterBgn" class="quarterBgn"   onchange="checkBeginQuarter()" style="width:60px" >
								<form:option value="01" label="Q1" />
								<form:option value="02" label="Q2" />
								<form:option value="03" label="Q3" />
								<form:option value="04" label="Q4" />
						 	</form:select>至
							<form:select path="dto.quarterEnd" class="quarterEnd"   onchange="checkEndQuarter()" style="width:60px">
								<form:option value="01" label="Q1" />
								<form:option value="02" label="Q2" />
								<form:option value="03" label="Q3" />
								<form:option value="04" label="Q4" />
							</form:select>	 --%>		
						</td>
						
				</c:if>				
				
					<td style="text-align: right">事业部：</td>
					<td style="text-align: left">
					    <form:select path="dto.businessUnit" style="width:250px" >
					      <option value="">全部</option>
								<form:options items="${depList}"/>
					  </form:select>
					</td>
					<td>
						<input type="button" class="blue" 	value="查询" onclick="searchResetPage()"/> 
						<input type="button" class="blue" value="重置" onclick="resetSearchTable()" />
					</td>
				  </tr>
				</table>
</div>
</div>
<table width="100%">
<tr>
<td colspan=2  style="vertical-align:top;"><div id="mainNum" style="height:300px"></div></td>
</tr>
<tr>
<td width="50%" style="vertical-align:top;"><div id="mainNumTwo" style="height:300px"></div></td>
<td width="50%" style="vertical-align:top;"><div id="mainNumThree" style="height:300px"></div></td>
</tr>
</table>
</form>
</body>
</html>
