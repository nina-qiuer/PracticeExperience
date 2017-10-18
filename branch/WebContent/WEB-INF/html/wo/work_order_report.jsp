<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/html/head.jsp" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<link href="${CONFIG.res_url}css/easydialog.css" rel="stylesheet" type="text/css" />
<style type="text/css">
	.main{
		margin:0 auto;
		width:100%
	}
</style>
<title>工单报表</title>
</head>
<body>
<div class="top_crumbs">您当前所在的位置：投诉质检管理>><span class="top_crumbs_txt">预订工作台</span>>><span class="top_crumbs_txt">工单报表</span></div>
<div class="main">
<form name="form" id="search_form" method="post" enctype="multipart/form-data" action="">
    <div class="pici_search search pd5 mb10">
		<label class="mr10">统计时间：
            <input id="addTimeBgn" type="text"  name="searchVo.addTimeBgn" value="${searchVo.addTimeBgn}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'addTimeEnd\')}'})" readOnly="readonly"/>
           	 至
            <input id="addTimeEnd" type="text"  name="searchVo.addTimeEnd" value="${searchVo.addTimeEnd}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'addTimeBgn\')}'})" readOnly="readonly"/>
        </label>
        <label>
		   	<input type="button" class="blue" 	value="查询" onclick="searchResetPage()"/> 
			<input type="button" class="blue" value="重置" onclick="resetSearchTable()" />
        </label>
    </div>  
	
	<table width="100%">
		<tr>
			<td width="50%" style="vertical-align:top;"><div id="mainPie" style="height:500px"></div></td>
			<td width="50%" style="vertical-align:top;"><div id="returnPie" style="height:500px;"></div></td>
		</tr>
	</table>
	
</form>
<script src="${CONFIG.res_url}script/echarts.min.js" type="text/javascript"></script>
</body>
<script type="text/javascript">
$(function(){
	//searchResetPage();
});

var chart_rs_two = echarts.init(document.getElementById("returnPie"));
var chart_rs = echarts.init(document.getElementById("mainPie"));

function searchResetPage(){
	var addTimeBgn = $("#addTimeBgn").val();
	var addTimeEnd = $("#addTimeEnd").val();
	
	if("" == addTimeBgn || "" == addTimeEnd){
		alert("请选择查询时间！");
		return;
	}
	
	$.ajax({
		url: "work_order-getWorkOrderNumsByAjax",
		type : "POST",
		data : {
			"searchVo.addTimeBgn": addTimeBgn,
			"searchVo.addTimeEnd": addTimeEnd
		},
		success : function(data) {
			if(null != data){
				applyChart(data);
			}else{
				alert("查询无数据，请重新选择查询时间！");
			}
		}
	});
}

function applyChart(data){
	var workOrderNums = new Array();
	var workOrderNames = new Array();
	var returnWorkOrderNums = new Array();
	var returnWorkOrderNames = new Array();
	
	var numArray = eval(data);
	var size = numArray.length;
	
	for(var i = 0; i < size; i++){
		if(numArray[i].name.indexOf("回转") >= 0){
			returnWorkOrderNames.push(numArray[i].name);
			returnWorkOrderNums.push({
				"value" : numArray[i].workOrderNums,
				"name" : numArray[i].name
			});
		}else{
			workOrderNames.push(numArray[i].name);
			workOrderNums.push({
				"value" : numArray[i].workOrderNums,
				"name" : numArray[i].name
			});
		}
	}
	
	if(workOrderNames.length > 0){
		var option = getConfigs("工单类别占比", workOrderNames, workOrderNums);
		chart_rs.clear();
		chart_rs.setOption(option);
	}
	
	if(returnWorkOrderNames.length > 0){
		var optionTwo = getConfigs("回转工单占比", returnWorkOrderNames, returnWorkOrderNums);
		chart_rs_two.clear();
		chart_rs_two.setOption(optionTwo);
	}
}

function getConfigs(titleName, workOrderNames, workOrderNums){
	var config = {
		title : {
	        text: titleName,
	        x:'center'
	    },
	    tooltip : {
	        trigger: 'item',
	        formatter: "{a} <br/>{b} : {c} ({d}%)"
	    },
	    legend: {
	        orient: 'vertical',
	        x : 'left',
		    padding:15,
	        data: workOrderNames
	    },
	    toolbox: {
	        show : true,
	        feature : {
	            restore : {show: true},
	            saveAsImage : {show: true}
	        }
	    },
	    calculable : true,
	    series : [
	        {
	            name: '数量',
	            type: 'pie',
	            radius : '55%',
	            center: ['50%', '60%'],
	            data: workOrderNums,
	            itemStyle : {
	                normal : {
	                    label : {
	                        show : true,
	                        formatter: '{b} {c} ({d}%)'
	                    },
	                    labelLine : {
	                        show : true
	                    }
	                }
	            }
	        }
	    ]
    };
	
	return config;
}

/**
 * 窗口大小改变后自动调整echarts大小
 */
$(window).on("resize", function() {
	resize();
});

/**
 * 图表自动调整大小
 */
function resize() {
	chart_rs.resize();
}

function setMaxDate(id){
	WdatePicker({maxDate:'#F{$dp.$D(\''+id+'\')}'});
}

function setMinDate(id){
	WdatePicker({minDate:'#F{$dp.$D(\''+id+'\')}'});
}

function resetSearchTable(){
	$('.search :text').val('');
}
</script>
</html>
