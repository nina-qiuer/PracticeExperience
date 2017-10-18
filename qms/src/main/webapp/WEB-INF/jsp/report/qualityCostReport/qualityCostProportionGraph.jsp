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
<title>质量成本占比</title>
<script src="res/plugins/echarts-2.2.5/build/dist/echarts.js" type="text/javascript"></script>
<script type="text/javascript">
var depArr = new Array();
var qptArr = new Array();
var jobArr = new Array();
var realNameArr = new Array();
var userArr = new Array();
require.config({
    paths: {
        echarts: 'res/plugins/echarts-2.2.5/build/dist'
    }
});
var viewFlag = "${dto.viewFlag}";
var qpTypeName = "";
var qpTypeNameRank ="";
var showName = "金额(元)";
require(
    [
        'echarts',
        'echarts/chart/bar',
        'echarts/chart/pie'
    ],
    function (ec) {
    
    	if(viewFlag ==1){
    		
    		var qpType = "${dto.qptType}";
	      
	        if(qpType==1){
	        	
	        	 qpTypeName = "质量成本一级占比";
	        	 qpTypeNameRank ="质量成本一级总额";
	        	 
	        }else if(qpType==2){
	        	
	        	 qpTypeName = "质量成本二级占比";
	        	 qpTypeNameRank ="质量成本二级总额";
	        	 
	        }else if(qpType==3){
	        	
	        	 qpTypeName = "质量成本三级占比";
	        	 qpTypeNameRank ="质量成本三级总额";
	        	 
	        }
    	}else if(viewFlag==2) {
    		
    		var depType = "${dto.depType}";
	        if(depType==1){
	        	
	        	qpTypeName = "一级责任部门占比";
	        	qpTypeNameRank ="一级责任部门总额";
	        	 
	        }else if(depType==2){
	        	
	        	qpTypeName = "二级责任部门占比";
	        	qpTypeNameRank ="二级责任部门总额";
	        }else if(depType==3){
	        	
	        	qpTypeName = "三级责任部门占比";
	        	qpTypeNameRank ="三级责任部门总额";
	        }
    	}
		else if(viewFlag==3) {
		    		
			qpTypeName="责任岗位占比";	
			qpTypeNameRank ="责任岗位总额";
		 }
		else if(viewFlag==4) {
			
			qpTypeName="责任人占比";	
			qpTypeNameRank ="责任人总额";
		}
    		
    		var qpTypeNum = new Array();
    		var qpTypeNumRank = new Array();
        	var nums = new Array();
        	var signDeptList = new Array();
        	<c:forEach items="${dataList}" var="map">
        		var tempMap = {};
    			qpTypeNum.push(lineFeed('${map.qpTypeName}'));
    			var rank = parseInt('${map.rank}');
    			if(rank ==0){
    				
    				qpTypeNumRank.push(lineFeed('${map.qpTypeName}'));
    				
    			}else{
    				
    				qpTypeNumRank.push(lineFeed('${map.qpTypeName}') +"["+ rank+"]");
    			}
    			
    			nums.push('${map.num}');
    			tempMap['name'] = lineFeed('${map.qpTypeName}');
    			tempMap['value'] = '${map.num}';
    		    signDeptList.push(tempMap);
    		</c:forEach>
    		optionPie = {
    			    title : {
    			        text: qpTypeName,
    			        x:'center'
    			    },
    			    tooltip : {
    			        trigger: 'item',
    			        formatter: "{a} <br/>{b} : {c} ({d}%)"
    			    },
    			    legend: {
    			        orient : 'vertical',
    			        x : 'left',
    			        padding:10,
    			        data:qpTypeNum
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
    			            name: showName,
    			            type:'pie',
    			            radius : '55%',
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
    			            },
    			            center: ['50%', '60%'],
    			            data:signDeptList
    			        }
    			    ]
    			};
          
    		optionNum = {
    	        	tooltip : {
    			        trigger: 'axis',
    			        formatter: "{b}<br/>" + 
    			        		   "{a0} : {c0}<br/>"
    			    },
    			    calculable : true,
    			    legend: {
    			    		 data:[qpTypeNameRank]
    			       
    			    },
    			    toolbox: {
    			        show : true,
    			        feature : {
    			            restore : {show: true},
    			            saveAsImage : {show: true}
    			        }
    			    },
    			    xAxis : [
    			    	{
    			            type : 'value',
    			            position: 'top',
    			            name : showName,
    			            axisLabel : {
    			                formatter: '{value}'
    			            }
    			        }
    			    ],
    			    yAxis : [
    			        {
    			            type : 'category',
    			            data : qpTypeNumRank
    			        }
    			    ],
    			    grid: {
    			        x:120
    			    },
    			    series : [
    			        {
    			            name: qpTypeNameRank,
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
    			            data:nums
    			        }
    			    ]
    			};
    		
    	
    	var dataChartPie = ec.init(document.getElementById('mainPie')); 
		dataChartPie.setOption(optionPie);
		
    	var dataChartNum = ec.init(document.getElementById('mainNum')); 
        dataChartNum.setOption(optionNum);
    }
);

$(function(){
	$('#accordion').accordion({
    	collapsible: true,
    	heightStyle: "content"
    });
	$('#radioset').buttonset();
	$('#radioset').click(function() {
		searchResetPage();
	});
});


function depAutoComplete() {
	
	if (depArr.length > 0) {
		return;
	} else {
		$.ajax({
			type : "POST",
			url : "qc/qcBill/getDepNames",
			success : function(data) {
				for (var i = 0; i < data.length; i++) {
					depArr.push(data[i]);
				}
			}
		});
	
	$("#depName").autocomplete({
	    source: depArr,
	    autoFocus : true
	});
	
	}
}

function depExists(input){
	  
	var isExists = false;
	var depName = input.value;
	if($.trim(depName)!=''){
		for(var i=0;i<depArr.length;i++){
			
			if(depName == depArr[i]){
				
				isExists =true;
			}
		}
		if(isExists==false){
			
			   //layer.alert("责任部门不存在",{icon: 2});	
		       $(input).val("");
		       return false;
		}
	}
}

function jobAutoComplete() {
	
	if (jobArr.length > 0) {
		return;
	} else {
		$.ajax({
			type : "POST",
			url : "qc/qcBill/getJobNames",
			success : function(data) {
				for (var i = 0; i < data.length; i++) {
					jobArr.push(data[i]);
				}
			}
		});
	
	$("#jobName").autocomplete({
	    source: jobArr,
	    autoFocus : true
	});
	
	}
}
function jobExists(input){
	  
	var isExists = false;
	var jobName = input.value;
	if($.trim(jobName)!=''){
		for(var i=0;i<jobArr.length;i++){
			
			if(jobName == jobArr[i]){
				
				isExists =true;
			}
		}
		if(isExists==false){
			
			  // layer.alert("责任岗位不存在",{icon: 2});	
		       $(input).val("");
		       return false;
		}
	}
}

function qptAutoComplete() {
	
	if (qptArr.length > 0) {
		return;
	} else {
		$.ajax({
			type : "POST",
			url : "qs/costLedger/getCostAccount",
			success : function(data) {
				for (var i = 0; i < data.length; i++) {
					qptArr.push(data[i]);
				}
			}
		});
	
	$("#qptName").autocomplete({
	    source: qptArr,
	    autoFocus : true
	});
 }
}

function qptExists(input){
	  
	var isExists = false;
	var qptName = input.value;
	if($.trim(qptName)!=''){
		for(var i=0;i<qptArr.length;i++){
			
			if(qptName == qptArr[i]){
				
				isExists =true;
			}
		}
		if(isExists==false){
			
			//   layer.alert("质量成本不存在",{icon: 2});	
		       $(input).val("");
		       return false;
		}
	}
}
function userAutoComplete() {
	
	if (userArr.length > 0) {
		return;
	} else {
		$.ajax({
			type : "POST",
			url : "qc/qcBill/getUserNamesInJSON",
			success : function(data) {
				for (var i = 0; i < data.length; i++) {
					userArr.push({
						label : data[i].label,
						value : data[i].realName
					});
					realNameArr.push(data[i].realName);
				}
			}
		});
	
	$("#respPersonName").autocomplete({
	    source: userArr,
	    autoFocus : true
	}).data( "ui-autocomplete" )._renderItem = function( ul, item ) {
		return $( "<li>" )
        .append( "<a>" + item.value + "</a>" )
        .appendTo( ul );
	};

	}
}
function respExists(input){
	  
	var isExists = false;
	var respName = input.value;
	if($.trim(respName)!=''){
		for(var i=0;i<userArr.length;i++){
			
			if(respName == userArr[i].value){
				
				isExists = true;
			}
		}
		if(isExists==false){
			
			 //  layer.alert("责任人不存在",{icon: 2});	
		       $(input).val("");
		       return false;
		}
	}
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

function checkTime(){
	
	var timeType = document.getElementById("timeType").value;
	var time_type = timeType;
	if(time_type==1){
		
		   $("#year").show();
		   $("#quarter").css('display', 'none'); 
		   $("#month").css('display', 'none'); 
		   $("#week").css('display', 'none'); 
		   $("#day").css('display', 'none'); 
	}
	if(time_type==2){
		
		   $("#quarter").show();
		   $("#year").css('display', 'none'); 
		   $("#month").css('display', 'none'); 
		   $("#week").css('display', 'none'); 
		   $("#day").css('display', 'none'); 
	}
	if(time_type==3){
		
		   $("#month").show();
		   $("#year").css('display', 'none'); 
		   $("#quarter").css('display', 'none'); 
		   $("#week").css('display', 'none'); 
		   $("#day").css('display', 'none');
	}
	if(time_type==4){
		
		   $("#week").show();
		   $("#year").css('display', 'none'); 
		   $("#quarter").css('display', 'none'); 
		   $("#month").css('display', 'none'); 
		   $("#day").css('display', 'none'); 
	}
	if(time_type==5){
		
		  $("#day").show();
		  $("#year").css('display', 'none'); 
		  $("#quarter").css('display', 'none'); 
		  $("#month").css('display', 'none'); 
		  $("#week").css('display', 'none'); 
	}
}
function checkBeginYear(){
	
	var yearBgn = document.getElementById("yearBgn").value;
	var yearEnd = document.getElementById("yearEnd").value;

	 if(yearBgn !=''&& yearEnd!=''){
		 if(yearBgn>yearEnd){
			layer.alert("开始年份不能大于结束年份", {icon: 2});
			 document.getElementById("yearBgn").value=yearEnd; 
			 return;
		 }
		 
	 }
}

function checkEndYear(){
	var yearBgn = document.getElementById("yearBgn").value;
	var yearEnd = document.getElementById("yearEnd").value;
	if(yearBgn !=''&& yearEnd!=''){
		 if(yearBgn>yearEnd){
			layer.alert("结束年份不能小于开始年份", {icon: 2});
			 document.getElementById("yearEnd").value=yearBgn;
			 return;
		 }
		 
	 }
}
function checkQBeginYear(){
	
	var yearBgn = document.getElementById("qYearBgn").value;
	var yearEnd = document.getElementById("qYearEnd").value;

	 if(yearBgn !=''&& yearEnd!=''){
		 if(yearBgn>yearEnd){
			layer.alert("开始年份不能大于结束年份", {icon: 2});
			 document.getElementById("qYearBgn").value=yearEnd; 
			 return;
		 }
		 
	 }
}

function checkQEndYear(){
	var yearBgn = document.getElementById("qYearBgn").value;
	var yearEnd = document.getElementById("qYearEnd").value;
	if(yearBgn !=''&& yearEnd!=''){
		 if(yearBgn>yearEnd){
			layer.alert("结束年份不能小于开始年份", {icon: 2});
			 document.getElementById("qYearEnd").value=yearBgn;
			 return;
		 }
		 
	 }
}
function checkMBeginYear(){
	
	var yearBgn = document.getElementById("mYearBgn").value;
	var yearEnd = document.getElementById("mYearEnd").value;

	 if(yearBgn !=''&& yearEnd!=''){
		 if(yearBgn>yearEnd){
			layer.alert("开始年份不能大于结束年份", {icon: 2});
			 document.getElementById("mYearBgn").value=yearEnd; 
			 return;
		 }
		 
	 }
}

function checkMEndYear(){
	var yearBgn = document.getElementById("mYearBgn").value;
	var yearEnd = document.getElementById("mYearEnd").value;
	if(yearBgn !=''&& yearEnd!=''){
		 if(yearBgn>yearEnd){
			layer.alert("结束年份不能小于开始年份", {icon: 2});
			 document.getElementById("mYearEnd").value=yearBgn;
			 return;
		 }
		 
	 }
}
function checkWBeginYear(){
	
	var yearBgn = document.getElementById("wYearBgn").value;
	var yearEnd = document.getElementById("wYearEnd").value;

	 if(yearBgn !=''&& yearEnd!=''){
		 if(yearBgn>yearEnd){
			layer.alert("开始年份不能大于结束年份", {icon: 2});
			 document.getElementById("wYearBgn").value=yearEnd; 
			 return;
		 }
		 
	 }
}

function checkWEndYear(){
	var yearBgn = document.getElementById("wYearBgn").value;
	var yearEnd = document.getElementById("wYearEnd").value;
	if(yearBgn !=''&& yearEnd!=''){
		 if(yearBgn>yearEnd){
			layer.alert("结束年份不能小于开始年份", {icon: 2});
			 document.getElementById("wYearEnd").value=yearBgn;
			 return;
		 }
		 
	 }
}
function checkBeginQuarter(){
	
	var quarterBgn = document.getElementById("quarterBgn").value;
	var quarterEnd = document.getElementById("quarterEnd").value;
	var yearBgn = document.getElementById("qYearBgn").value;
	var yearEnd = document.getElementById("qYearEnd").value;
	if(yearBgn !=''&& yearEnd!=''){
		 
		 if(yearBgn==yearEnd){
			 
			  if(quarterBgn !=''&& quarterEnd!=''){
					 if(quarterBgn>quarterEnd){
						layer.alert("开始季度不能大于结束季度", {icon: 2});
						 document.getElementById("quarterBgn").value=quarterEnd; 
						 return;
					 }
					 
				 } 
			 
		 }
	 }

}

function checkEndQuarter(){
	var quarterBgn = document.getElementById("quarterBgn").value;
	var quarterEnd = document.getElementById("quarterEnd").value;
	var yearBgn = document.getElementById("qYearBgn").value;
	var yearEnd = document.getElementById("qYearEnd").value;
	if(yearBgn !=''&& yearEnd!=''){
		 
		 if(yearBgn==yearEnd){
			 
			 if(quarterBgn !=''&& quarterEnd!=''){
				 if(quarterBgn>quarterEnd){
					 layer.alert("结束季度不能小于开始季度", {icon: 2});
					 document.getElementById("quarterEnd").value=quarterBgn;
					 return;
				 }
		 }
	 }
	}
}

function checkBeginMonth(){
	
	var monthBgn = document.getElementById("monthBgn").value;
	var monthEnd = document.getElementById("monthEnd").value;
	var yearBgn = document.getElementById("mYearBgn").value;
	var yearEnd = document.getElementById("mYearEnd").value;
	if(yearBgn !=''&& yearEnd!=''){
		 
		 if(yearBgn==yearEnd){
			 
			  if(monthBgn !=''&& monthEnd!=''){
					 if(monthBgn>monthEnd){
						layer.alert("开始月不能大于结束月", {icon: 2});
						 document.getElementById("monthBgn").value=monthEnd; 
						 return;
					 }
					 
				 } 
			 
		 }
	 }

}

function checkEndMonth(){

	var monthBgn = document.getElementById("monthBgn").value;
	var monthEnd = document.getElementById("monthEnd").value;
	var yearBgn = document.getElementById("mYearBgn").value;
	var yearEnd = document.getElementById("mYearEnd").value;
	if(yearBgn !=''&& yearEnd!=''){
		 
		 if(yearBgn==yearEnd){
			 
			  if(monthBgn !=''&& monthEnd!=''){
					 if(monthBgn>monthEnd){
						layer.alert("结束月不能小于开始月", {icon: 2});
						 document.getElementById("monthEnd").value=monthBgn; 
						 return;
					 }
					 
				 } 
			 
		 }
	 }
}


function checkBeginWeek(){
	
	var weekBgn = document.getElementById("weekBgn").value;
	var weekEnd = document.getElementById("weekEnd").value;
	var yearBgn = document.getElementById("wYearBgn").value;
	var yearEnd = document.getElementById("wYearEnd").value;
	if(yearBgn !=''&& yearEnd!=''){
		 
		 if(yearBgn==yearEnd){
			 
			  if(weekBgn !=''&& weekEnd!=''){
					 if(weekBgn>weekEnd){
						layer.alert("开始周不能大于结束周", {icon: 2});
						 document.getElementById("weekBgn").value=weekEnd; 
						 return;
					 }
					 
				 } 
			 
		 }
	 }

}

function checkEndWeek(){

	var weekBgn = document.getElementById("weekBgn").value;
	var weekEnd = document.getElementById("weekEnd").value;
	var yearBgn = document.getElementById("wYearBgn").value;
	var yearEnd = document.getElementById("wYearEnd").value;
	if(yearBgn !=''&& yearEnd!=''){
		 
		 if(yearBgn==yearEnd){
			 
			  if(weekBgn !=''&& weekEnd!=''){
					 if(weekBgn>weekEnd){
						layer.alert("结束周不能大于开始周", {icon: 2});
						 document.getElementById("weekEnd").value=weekBgn; 
						 return;
					 }
					 
				 } 
			 
		 }
	 }
}
function search() {
	
	$("#dataForm").attr("action", "report/qualityCostReport/qualityCostProportion");
	$("#dataForm").submit();
}
</script>
</head>
<body>
<form id="dataForm" method="post" action="">
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
  <div id="accordion">
<h3>质量成本占比</h3>
<div align="right">
<table width="100%" class="search">
				<tr>
				   <td>时间粒度：</td>
				   <td>
						<form:select path="dto.timeType" class="timeType"   onchange="checkTime()" style="width:100px">
							<form:option value="1" label="年" selected="selected"  />
							<form:option value="2" label="季" />
							<form:option value="3" label="月" />
							<form:option value="4" label="周" />
							<form:option value="5" label="日" />
						</form:select>
					</td>
					<td> 时间范围：</td> 
					<c:if test="${dto.timeType==null || dto.timeType==1}">
					<td id="year" >
					  	 <form:select path="dto.yearBgn" class="yearBgn"   onchange="checkBeginYear()" style="width:100px" >
					  		<option value="">全部</option>
							<form:option value="${dto.nowYear}" label="${dto.nowYear}年" />
							<form:option value="${dto.nowYear-1}" label="${dto.nowYear-1}年" />
							<form:option value="${dto.nowYear-2}" label="${dto.nowYear-2}年" />
					 	</form:select>-
						<form:select path="dto.yearEnd" class="yearEnd"   onchange="checkEndYear()" style="width:100px">
						   <option value="">全部</option>
							<form:option value="${dto.nowYear}" label="${dto.nowYear}年" />
							<form:option value="${dto.nowYear-1}" label="${dto.nowYear-1}年" />
							<form:option value="${dto.nowYear-2}" label="${dto.nowYear-2}年" />
						</form:select>
					</td>
					<td id="quarter" style="display: none">
					 <form:select path="dto.qYearBgn" class="qYearBgn"   onchange="checkQBeginYear()" style="width:100px" >
							<form:option value="${dto.nowYear}" label="${dto.nowYear}年" />
							<form:option value="${dto.nowYear-1}" label="${dto.nowYear-1}年" />
							<form:option value="${dto.nowYear-2}" label="${dto.nowYear-2}年" />
					 	</form:select>-
						<form:select path="dto.quarterBgn" class="quarterBgn"   onchange="checkBeginQuarter()" style="width:60px" >
							<form:option value="01" label="Q1" />
							<form:option value="02" label="Q2" />
							<form:option value="03" label="Q3" />
							<form:option value="04" label="Q4" />
					 	</form:select>至
					 	<form:select path="dto.qYearEnd" class="qYearEnd"   onchange="checkQEndYear()" style="width:100px">
							<form:option value="${dto.nowYear}" label="${dto.nowYear}年" />
							<form:option value="${dto.nowYear-1}" label="${dto.nowYear-1}年" />
							<form:option value="${dto.nowYear-2}" label="${dto.nowYear-2}年" />
						</form:select>-
						<form:select path="dto.quarterEnd" class="quarterEnd"   onchange="checkEndQuarter()" style="width:60px">
							<form:option value="01" label="Q1" />
							<form:option value="02" label="Q2" />
							<form:option value="03" label="Q3" />
							<form:option value="04" label="Q4" />
						</form:select>			
					</td>
					<td id="month" style="display: none">
						<form:select path="dto.mYearBgn" class="mYearBgn"   onchange="checkMBeginYear()" style="width:100px" >
								<form:option value="${dto.nowYear}" label="${dto.nowYear}年" />
								<form:option value="${dto.nowYear-1}" label="${dto.nowYear-1}年" />
								<form:option value="${dto.nowYear-2}" label="${dto.nowYear-2}年" />
						 	</form:select>-
					      <form:select path="dto.monthBgn" onchange="checkBeginMonth()">
								<form:options items="${monthList}"/>
						 </form:select>至
						 <form:select path="dto.mYearEnd" class="mYearEnd"   onchange="checkMEndYear()" style="width:100px" >
								<form:option value="${dto.nowYear}" label="${dto.nowYear}年" />
								<form:option value="${dto.nowYear-1}" label="${dto.nowYear-1}年" />
								<form:option value="${dto.nowYear-2}" label="${dto.nowYear-2}年" />
						 	</form:select>-
						 <form:select path="dto.monthEnd" onchange="checkEndMonth()">
								<form:options items="${monthList}"/>
							</form:select>
					 </td>
					<td id="week" style="display: none">
					  	   <form:select path="dto.wYearBgn" class="wYearBgn"   onchange="checkWBeginYear()" style="width:100px" >
					  	        <form:option value="${dto.nowYear+1}" label="${dto.nowYear+1}年" />
								<form:option value="${dto.nowYear}" label="${dto.nowYear}年" selected="selected"/>
								<form:option value="${dto.nowYear-1}" label="${dto.nowYear-1}年" />
								<form:option value="${dto.nowYear-2}" label="${dto.nowYear-2}年" />
						 	</form:select>-
					      <form:select path="dto.weekBgn" onchange="checkBeginWeek()">
								<form:options items="${weekList}"/>
						 </form:select>至
						 <form:select path="dto.wYearEnd" class="wYearEnd"   onchange="checkWEndYear()" style="width:100px" >
						        <form:option value="${dto.nowYear+1}" label="${dto.nowYear+1}年" />
								<form:option value="${dto.nowYear}" label="${dto.nowYear}年" selected="selected"/>
								<form:option value="${dto.nowYear-1}" label="${dto.nowYear-1}年" />
								<form:option value="${dto.nowYear-2}" label="${dto.nowYear-2}年" />
						 	</form:select>-
						 <form:select path="dto.weekEnd" onchange="checkEndWeek()">
								<form:options items="${weekList}"/>
							</form:select>
					</td>
					<td id="day" style="display: none">
					  	           <form:input path="dto.addTimeBgn" onfocus="setMaxDate('addTimeEnd')"  />-
									<form:input path="dto.addTimeEnd" onfocus="setMinDate('addTimeBgn')" />
					</td>
					</c:if>
					<c:if test="${dto.timeType==2}">
						 <td id="year"  style="display: none">
					        <form:select path="dto.yearBgn" class="yearBgn"   onchange="checkBeginYear()" style="width:100px" >
					  		 <option value="">全部</option>
							<form:option value="${dto.nowYear}" label="${dto.nowYear}年" />
							<form:option value="${dto.nowYear-1}" label="${dto.nowYear-1}年" />
							<form:option value="${dto.nowYear-2}" label="${dto.nowYear-2}年" />
					 	</form:select>-
						<form:select path="dto.yearEnd" class="yearEnd"   onchange="checkEndYear()" style="width:100px">
						   <option value="">全部</option>
							<form:option value="${dto.nowYear}" label="${dto.nowYear}年" />
							<form:option value="${dto.nowYear-1}" label="${dto.nowYear-1}年" />
							<form:option value="${dto.nowYear-2}" label="${dto.nowYear-2}年" />
						</form:select>
						</td>
						<td id="quarter">
						  	 <form:select path="dto.qYearBgn" class="qYearBgn"   onchange="checkQBeginYear()" style="width:100px" >
								<form:option value="${dto.nowYear}" label="${dto.nowYear}年" />
								<form:option value="${dto.nowYear-1}" label="${dto.nowYear-1}年" />
								<form:option value="${dto.nowYear-2}" label="${dto.nowYear-2}年" />
						 	</form:select>-
							<form:select path="dto.quarterBgn" class="quarterBgn"   onchange="checkBeginQuarter()" style="width:60px" >
							
								<form:option value="01" label="Q1" />
								<form:option value="02" label="Q2" />
								<form:option value="03" label="Q3" />
								<form:option value="04" label="Q4" />
						 	</form:select>至
						 	<form:select path="dto.qYearEnd" class="qYearEnd"   onchange="checkQEndYear()" style="width:100px">
								<form:option value="${dto.nowYear}" label="${dto.nowYear}年" />
								<form:option value="${dto.nowYear-1}" label="${dto.nowYear-1}年" />
								<form:option value="${dto.nowYear-2}" label="${dto.nowYear-2}年" />
							</form:select>-
							<form:select path="dto.quarterEnd" class="quarterEnd"   onchange="checkEndQuarter()" style="width:60px">
								<form:option value="01" label="Q1" />
								<form:option value="02" label="Q2" />
								<form:option value="03" label="Q3" />
								<form:option value="04" label="Q4" />
							</form:select>			
						</td>
						<td id="month" style="display: none">
						    <form:select path="dto.mYearBgn" class="mYearBgn"   onchange="checkMBeginYear()" style="width:100px" >
								<form:option value="${dto.nowYear}" label="${dto.nowYear}年" />
								<form:option value="${dto.nowYear-1}" label="${dto.nowYear-1}年" />
								<form:option value="${dto.nowYear-2}" label="${dto.nowYear-2}年" />
						 	</form:select>-
					      <form:select path="dto.monthBgn" onchange="checkBeginMonth()">
								<form:options items="${monthList}"/>
						 </form:select>至
						 <form:select path="dto.mYearEnd" class="mYearEnd"   onchange="checkMEndYear()" style="width:100px" >
								<form:option value="${dto.nowYear}" label="${dto.nowYear}年" />
								<form:option value="${dto.nowYear-1}" label="${dto.nowYear-1}年" />
								<form:option value="${dto.nowYear-2}" label="${dto.nowYear-2}年" />
						 	</form:select>-
						 <form:select path="dto.monthEnd" onchange="checkEndMonth()">
								<form:options items="${monthList}"/>
							</form:select>
	
						</td>
						<td id="week" style="display: none">
						     <form:select path="dto.wYearBgn" class="wYearBgn"   onchange="checkWBeginYear()" style="width:100px" >
								<form:option value="${dto.nowYear+1}" label="${dto.nowYear+1}年" />
								<form:option value="${dto.nowYear}" label="${dto.nowYear}年" selected="selected"/>
								<form:option value="${dto.nowYear-1}" label="${dto.nowYear-1}年" />
								<form:option value="${dto.nowYear-2}" label="${dto.nowYear-2}年" />
						 	</form:select>-
					      <form:select path="dto.weekBgn" onchange="checkBeginWeek()">
								<form:options items="${weekList}"/>
						 </form:select>至
						 <form:select path="dto.wYearEnd" class="wYearEnd"   onchange="checkWEndYear()" style="width:100px" >
							     <form:option value="${dto.nowYear+1}" label="${dto.nowYear+1}年" />
								<form:option value="${dto.nowYear}" label="${dto.nowYear}年" selected="selected"/>
								<form:option value="${dto.nowYear-1}" label="${dto.nowYear-1}年" />
								<form:option value="${dto.nowYear-2}" label="${dto.nowYear-2}年" />
						 	</form:select>-
						 <form:select path="dto.weekEnd" onchange="checkEndWeek()">
								<form:options items="${weekList}"/>
							</form:select>
						</td>
						<td id="day" style="display: none">
						  	           <form:input path="dto.addTimeBgn" onfocus="setMaxDate('addTimeEnd')"  />-
										<form:input path="dto.addTimeEnd" onfocus="setMinDate('addTimeBgn')" />
						</td>
					</c:if>
					<c:if test="${dto.timeType==3}">
						<td id="year"  style="display: none">
						    <form:select path="dto.yearBgn" class="yearBgn"   onchange="checkBeginYear()" style="width:100px" >
					  		 <option value="">全部</option>
							<form:option value="${dto.nowYear}" label="${dto.nowYear}年" />
							<form:option value="${dto.nowYear-1}" label="${dto.nowYear-1}年" />
							<form:option value="${dto.nowYear-2}" label="${dto.nowYear-2}年" />
					 	</form:select>-
						<form:select path="dto.yearEnd" class="yearEnd"   onchange="checkEndYear()" style="width:100px">
						   <option value="">全部</option>
							<form:option value="${dto.nowYear}" label="${dto.nowYear}年" />
							<form:option value="${dto.nowYear-1}" label="${dto.nowYear-1}年" />
							<form:option value="${dto.nowYear-2}" label="${dto.nowYear-2}年" />
						</form:select>
						</td>
						<td id="quarter" style="display: none">
						  	    <form:select path="dto.qYearBgn" class="qYearBgn"   onchange="checkQBeginYear()" style="width:100px" >
								<form:option value="${dto.nowYear}" label="${dto.nowYear}年" />
								<form:option value="${dto.nowYear-1}" label="${dto.nowYear-1}年" />
								<form:option value="${dto.nowYear-2}" label="${dto.nowYear-2}年" />
						 	</form:select>-
							<form:select path="dto.quarterBgn" class="quarterBgn"   onchange="checkBeginQuarter()" style="width:60px" >
								<form:option value="01" label="Q1" />
								<form:option value="02" label="Q2" />
								<form:option value="03" label="Q3" />
								<form:option value="04" label="Q4" />
						 	</form:select>至
						 	<form:select path="dto.qYearEnd" class="qYearEnd"   onchange="checkQEndYear()" style="width:100px">
								<form:option value="${dto.nowYear}" label="${dto.nowYear}年" />
								<form:option value="${dto.nowYear-1}" label="${dto.nowYear-1}年" />
								<form:option value="${dto.nowYear-2}" label="${dto.nowYear-2}年" />
							</form:select>-
							<form:select path="dto.quarterEnd" class="quarterEnd"   onchange="checkEndQuarter()" style="width:60px">
								<form:option value="01" label="Q1" />
								<form:option value="02" label="Q2" />
								<form:option value="03" label="Q3" />
								<form:option value="04" label="Q4" />
							</form:select>			
						</td>
						<td id="month" >
						    <form:select path="dto.mYearBgn" class="mYearBgn"   onchange="checkMBeginYear()" style="width:100px" >
								<form:option value="${dto.nowYear}" label="${dto.nowYear}年" />
								<form:option value="${dto.nowYear-1}" label="${dto.nowYear-1}年" />
								<form:option value="${dto.nowYear-2}" label="${dto.nowYear-2}年" />
						 	</form:select>-
					      <form:select path="dto.monthBgn" onchange="checkBeginMonth()">
								<form:options items="${monthList}"/>
						 </form:select>至
						 <form:select path="dto.mYearEnd" class="mYearEnd"   onchange="checkMEndYear()" style="width:100px" >
								<form:option value="${dto.nowYear}" label="${dto.nowYear}年" />
								<form:option value="${dto.nowYear-1}" label="${dto.nowYear-1}年" />
								<form:option value="${dto.nowYear-2}" label="${dto.nowYear-2}年" />
						 	</form:select>-
						 <form:select path="dto.monthEnd" onchange="checkEndMonth()">
								<form:options items="${monthList}"/>
							</form:select>
						</td>
						<td id="week" style="display: none">
						    <form:select path="dto.wYearBgn" class="wYearBgn"   onchange="checkWBeginYear()" style="width:100px" >
								     <form:option value="${dto.nowYear+1}" label="${dto.nowYear+1}年" />
								<form:option value="${dto.nowYear}" label="${dto.nowYear}年" selected="selected"/>
								<form:option value="${dto.nowYear-1}" label="${dto.nowYear-1}年" />
								<form:option value="${dto.nowYear-2}" label="${dto.nowYear-2}年" />
						 	</form:select>-
					      <form:select path="dto.weekBgn" onchange="checkBeginWeek()">
								<form:options items="${weekList}"/>
						 </form:select>至
						 <form:select path="dto.wYearEnd" class="wYearEnd"   onchange="checkWEndYear()" style="width:100px" >
							     <form:option value="${dto.nowYear+1}" label="${dto.nowYear+1}年" />
								<form:option value="${dto.nowYear}" label="${dto.nowYear}年" selected="selected"/>
								<form:option value="${dto.nowYear-1}" label="${dto.nowYear-1}年" />
								<form:option value="${dto.nowYear-2}" label="${dto.nowYear-2}年" />
						 	</form:select>-
						 <form:select path="dto.weekEnd" onchange="checkEndWeek()">
								<form:options items="${weekList}"/>
							</form:select>
						</td>
						<td id="day" style="display: none">
						  	           <form:input path="dto.addTimeBgn" onfocus="setMaxDate('addTimeEnd')"  />-
										<form:input path="dto.addTimeEnd" onfocus="setMinDate('addTimeBgn')" />
						</td>
					</c:if>
					<c:if test="${dto.timeType==4}">
					<td id="year"  style="display: none">
					      <form:select path="dto.yearBgn" class="yearBgn"   onchange="checkBeginYear()" style="width:100px" >
					  		 <option value="">全部</option>
							<form:option value="${dto.nowYear}" label="${dto.nowYear}年" />
							<form:option value="${dto.nowYear-1}" label="${dto.nowYear-1}年" />
							<form:option value="${dto.nowYear-2}" label="${dto.nowYear-2}年" />
					 	</form:select>-
						<form:select path="dto.yearEnd" class="yearEnd"   onchange="checkEndYear()" style="width:100px">
						   <option value="">全部</option>
							<form:option value="${dto.nowYear}" label="${dto.nowYear}年" />
							<form:option value="${dto.nowYear-1}" label="${dto.nowYear-1}年" />
							<form:option value="${dto.nowYear-2}" label="${dto.nowYear-2}年" />
						</form:select>
					</td>
					<td id="quarter" style="display: none">
					  	     <form:select path="dto.qYearBgn" class="qYearBgn"   onchange="checkQBeginYear()" style="width:100px" >
								<form:option value="${dto.nowYear}" label="${dto.nowYear}年" />
								<form:option value="${dto.nowYear-1}" label="${dto.nowYear-1}年" />
								<form:option value="${dto.nowYear-2}" label="${dto.nowYear-2}年" />
						 	</form:select>-
							<form:select path="dto.quarterBgn" class="quarterBgn"   onchange="checkBeginQuarter()" style="width:60px" >
								<form:option value="01" label="Q1" />
								<form:option value="02" label="Q2" />
								<form:option value="03" label="Q3" />
								<form:option value="04" label="Q4" />
						 	</form:select>至
						 	<form:select path="dto.qYearEnd" class="qYearEnd"   onchange="checkQEndYear()" style="width:100px">
								<form:option value="${dto.nowYear}" label="${dto.nowYear}年" />
								<form:option value="${dto.nowYear-1}" label="${dto.nowYear-1}年" />
								<form:option value="${dto.nowYear-2}" label="${dto.nowYear-2}年" />
							</form:select>-
							<form:select path="dto.quarterEnd" class="quarterEnd"   onchange="checkEndQuarter()" style="width:60px">
								<form:option value="01" label="Q1" />
								<form:option value="02" label="Q2" />
								<form:option value="03" label="Q3" />
								<form:option value="04" label="Q4" />
							</form:select>			
					</td>
					<td id="month" style="display: none">
					  	    <form:select path="dto.mYearBgn" class="mYearBgn"   onchange="checkMBeginYear()" style="width:100px" >
								<form:option value="${dto.nowYear}" label="${dto.nowYear}年" />
								<form:option value="${dto.nowYear-1}" label="${dto.nowYear-1}年" />
								<form:option value="${dto.nowYear-2}" label="${dto.nowYear-2}年" />
						 	</form:select>-
					      <form:select path="dto.monthBgn" onchange="checkBeginMonth()">
								<form:options items="${monthList}"/>
						 </form:select>至
						 <form:select path="dto.mYearEnd" class="mYearEnd"   onchange="checkMEndYear()" style="width:100px" >
								<form:option value="${dto.nowYear}" label="${dto.nowYear}年" />
								<form:option value="${dto.nowYear-1}" label="${dto.nowYear-1}年" />
								<form:option value="${dto.nowYear-2}" label="${dto.nowYear-2}年" />
						 	</form:select>-
						 <form:select path="dto.monthEnd" onchange="checkEndMonth()">
								<form:options items="${monthList}"/>
							</form:select>
					</td>
					<td id="week">
					      <form:select path="dto.wYearBgn" class="wYearBgn"   onchange="checkWBeginYear()" style="width:100px" >
								<form:option value="${dto.nowYear+1}" label="${dto.nowYear+1}年" />
								<form:option value="${dto.nowYear}" label="${dto.nowYear}年" selected="selected"/>
								<form:option value="${dto.nowYear-1}" label="${dto.nowYear-1}年" />
								<form:option value="${dto.nowYear-2}" label="${dto.nowYear-2}年" />
						 	</form:select>-
					      <form:select path="dto.weekBgn" onchange="checkBeginWeek()">
								<form:options items="${weekList}"/>
						 </form:select>至
						 <form:select path="dto.wYearEnd" class="wYearEnd"   onchange="checkWEndYear()" style="width:100px" >
							    <form:option value="${dto.nowYear+1}" label="${dto.nowYear+1}年" />
								<form:option value="${dto.nowYear}" label="${dto.nowYear}年" selected="selected"/>
								<form:option value="${dto.nowYear-1}" label="${dto.nowYear-1}年" />
								<form:option value="${dto.nowYear-2}" label="${dto.nowYear-2}年" />
						 	</form:select>-
						 <form:select path="dto.weekEnd" onchange="checkEndWeek()">
								<form:options items="${weekList}"/>
							</form:select>
					</td>
					<td id="day" style="display: none">
					  	           <form:input path="dto.addTimeBgn" onfocus="setMaxDate('addTimeEnd')"  />-
									<form:input path="dto.addTimeEnd" onfocus="setMinDate('addTimeBgn')" />
					</td>
					</c:if>
					<c:if test="${dto.timeType==5}">
					<td id="year"  style="display: none">
					   <form:select path="dto.yearBgn" class="yearBgn"   onchange="checkBeginYear()" style="width:100px" >
					  		 <option value="">全部</option>
							<form:option value="${dto.nowYear}" label="${dto.nowYear}年" />
							<form:option value="${dto.nowYear-1}" label="${dto.nowYear-1}年" />
							<form:option value="${dto.nowYear-2}" label="${dto.nowYear-2}年" />
					 	</form:select>-
						<form:select path="dto.yearEnd" class="yearEnd"   onchange="checkEndYear()" style="width:100px">
						   <option value="">全部</option>
							<form:option value="${dto.nowYear}" label="${dto.nowYear}年" />
							<form:option value="${dto.nowYear-1}" label="${dto.nowYear-1}年" />
							<form:option value="${dto.nowYear-2}" label="${dto.nowYear-2}年" />
						</form:select>
					</td>
					<td id="quarter" style="display: none">
					  	     <form:select path="dto.qYearBgn" class="qYearBgn"   onchange="checkQBeginYear()" style="width:100px" >
								<form:option value="${dto.nowYear}" label="${dto.nowYear}年" />
								<form:option value="${dto.nowYear-1}" label="${dto.nowYear-1}年" />
								<form:option value="${dto.nowYear-2}" label="${dto.nowYear-2}年" />
						 	</form:select>-
							<form:select path="dto.quarterBgn" class="quarterBgn"   onchange="checkBeginQuarter()" style="width:60px" >
								<form:option value="01" label="Q1" />
								<form:option value="02" label="Q2" />
								<form:option value="03" label="Q3" />
								<form:option value="04" label="Q4" />
						 	</form:select>至
						 	<form:select path="dto.qYearEnd" class="qYearEnd"   onchange="checkQEndYear()" style="width:100px">
								<form:option value="${dto.nowYear}" label="${dto.nowYear}年" />
								<form:option value="${dto.nowYear-1}" label="${dto.nowYear-1}年" />
								<form:option value="${dto.nowYear-2}" label="${dto.nowYear-2}年" />
							</form:select>-
							<form:select path="dto.quarterEnd" class="quarterEnd"   onchange="checkEndQuarter()" style="width:60px">
								<form:option value="01" label="Q1" />
								<form:option value="02" label="Q2" />
								<form:option value="03" label="Q3" />
								<form:option value="04" label="Q4" />
							</form:select>			
					</td>
					<td id="month" style="display: none">
					  	    <form:select path="dto.mYearBgn" class="mYearBgn"   onchange="checkMBeginYear()" style="width:100px" >
								<form:option value="${dto.nowYear}" label="${dto.nowYear}年" />
								<form:option value="${dto.nowYear-1}" label="${dto.nowYear-1}年" />
								<form:option value="${dto.nowYear-2}" label="${dto.nowYear-2}年" />
						 	</form:select>-
					      <form:select path="dto.monthBgn" onchange="checkBeginMonth()">
								<form:options items="${monthList}"/>
						 </form:select>至
						 <form:select path="dto.mYearEnd" class="mYearEnd"   onchange="checkMEndYear()" style="width:100px" >
								<form:option value="${dto.nowYear}" label="${dto.nowYear}年" />
								<form:option value="${dto.nowYear-1}" label="${dto.nowYear-1}年" />
								<form:option value="${dto.nowYear-2}" label="${dto.nowYear-2}年" />
						 	</form:select>-
						 <form:select path="dto.monthEnd" onchange="checkEndMonth()">
								<form:options items="${monthList}"/>
							</form:select>
					</td>
					<td id="week" style="display: none">
					   <form:select path="dto.wYearBgn" class="wYearBgn"   onchange="checkWBeginYear()" style="width:100px" >
							    <form:option value="${dto.nowYear+1}" label="${dto.nowYear+1}年" />
								<form:option value="${dto.nowYear}" label="${dto.nowYear}年" selected="selected"/>
								<form:option value="${dto.nowYear-1}" label="${dto.nowYear-1}年" />
								<form:option value="${dto.nowYear-2}" label="${dto.nowYear-2}年" />
						 	</form:select>-
					      <form:select path="dto.weekBgn" onchange="checkBeginWeek()">
								<form:options items="${weekList}"/>
						 </form:select>至
						 <form:select path="dto.wYearEnd" class="wYearEnd"   onchange="checkWEndYear()" style="width:100px" >
							     <form:option value="${dto.nowYear+1}" label="${dto.nowYear+1}年" />
								<form:option value="${dto.nowYear}" label="${dto.nowYear}年" selected="selected"/>
								<form:option value="${dto.nowYear-1}" label="${dto.nowYear-1}年" />
								<form:option value="${dto.nowYear-2}" label="${dto.nowYear-2}年" />
						 	</form:select>-
						 <form:select path="dto.weekEnd" onchange="checkEndWeek()">
								<form:options items="${weekList}"/>
							</form:select>
					</td>
					<td id="day" >
					  	           <form:input path="dto.addTimeBgn" onfocus="setMaxDate('addTimeEnd')"  />-
									<form:input path="dto.addTimeEnd" onfocus="setMinDate('addTimeBgn')" />
					</td>
					</c:if>
					<td>成本科目：</td>
					<td>
					   <form:input path="dto.qptName" style="width:250px" onfocus="qptAutoComplete()" onblur="qptExists(this)"/> 
					</td>
					<td>维度：</td>
					<td colspan="3">
						<div id="radioset">
							<form:radiobutton path="dto.viewFlag" value="1" label="成本科目" />
							<form:radiobutton path="dto.viewFlag" value="2" label="责任部门" />
							<form:radiobutton path="dto.viewFlag" value="3" label="责任岗位" />
							<form:radiobutton path="dto.viewFlag" value="4" label="责任人" />
						</div>
				</td>
				</tr>
				<tr>
				
					<td>责任岗位 :</td>
					<td>
					 <form:input path="dto.jobName" onfocus="jobAutoComplete()" onblur="jobExists(this)"/>　
					 </td>
					<td>责任人：</td>
			    	<td><form:input path="dto.respPersonName"  onfocus="userAutoComplete()" onblur="respExists(this)"/></td>
					<td>责任部门 ：</td>
					<td>
					  <form:input path="dto.depName" style="width:250px" onfocus="depAutoComplete()" onblur="depExists(this)"/> 
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
		<td width="50%" style="vertical-align:top;"><div id="mainPie" style="height:500px"></div></td>
		<td width="50%" style="vertical-align:top;"><div id="mainNum" style="height:${fn:length(dataList)*40 + 120 - (fn:length(dataList)-1)*10}px"></div></td>
</tr>
</table>
</form>
</body>
</html>
