<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@include file="/WEB-INF/html/head.jsp" %>

<script type="text/javascript" src="${CONFIG.res_url}script/echarts.min.js" ></script>

<link rel="stylesheet" type="text/css" media="screen" href="${CONFIG.res_url}script/jquery/jquery-ui.css" />
<link rel="stylesheet" type="text/css" media="screen" href="${CONFIG.res_url}script/jquery/jqGrid/src/css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" media="screen" href="${CONFIG.res_url}script/jquery/jqGrid/plugins/searchFilter.css" />
<script src="${CONFIG.res_url}script/jquery/jqGrid/js/i18n/grid.locale-cn.js" type="text/javascript"></script>
<script src="${CONFIG.res_url}script/jquery/jqGrid/js/jquery.jqGrid.min.js" type="text/javascript"></script>



<script type="text/javascript">
$(document).ready(function () {
});


function onSubmit() {
	//日期校验
	if(!(startDate.value && endDate.value)){
		alert('请选择起止日期');
		return false;
	}
	
	//统计单位和展现方式的校验
	var showType = $('input[type=radio][name=showType]:checked').val();
	var statisticUnit = $('input[type=radio][name=statisticUnit]:checked').val();
	var timeDimension = $('input[type=radio][name=timeDimension]:checked').val();
	if(timeDimension==undefined){
		alert('请选择时间维度');
		return false;
	}
	if(statisticUnit==undefined || showType==undefined){
		alert('请选择统计单位和展现方式');
		return false;
	}

	//先清空原有内容
	$('#main').empty();
	
	//根据showType和statisticUnit生成新内容
	if(showType==0){ //表
			$.ajax({
				type:'post',
				url:'complaintNum_stat-getTableData',
				data:$('#complaintNum_form').serialize(),
				success:function(data){
					buildjQgrid(data.rows);
				}
			});
	}else{ //图
		//1.先清空原有内容
		$('#main').empty(); 
		//2.构造echarts容器
		$('<div/>',{
			id:'graph',
			style:'width: 100%;height:600px;'
		}).appendTo($('#main'));
		
		if(statisticUnit=='hour'){ // 小时维度
			$.ajax({
				type:'post',
				url:'complaintNum_stat-getHourGraphData',
				data:$('#complaintNum_form').serialize(),
				success:function(data){
					buildHourGraph(data);
				}
			});
		}else{ // 天的维度
			$.ajax({
				type:'post',
				url:'complaintNum_stat-getComplaintNumListForDayGraph',
				data:$('#complaintNum_form').serialize(),
				success:function(data){
					buildDayGraph(data.rows);
				}
			});
		}
	}
	
}

function buildHourGraph(data){
	var x_data = ["h00","h01","h03","h04","h05","h06","h07","h08","h09","h10","h11","h12","h13","h14","h15","h16","h17","h18","h19","h20","h21","h22","h23"];
	var series_data = []; 
	if(data == null){
		series_data = [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
	}else{
		for(key in x_data){
			series_data.push(data[x_data[key]]);
		}
	}
	
	 var myChart = echarts.init(document.getElementById('graph'));
     // 指定图表的配置项和数据
     var option = {
         title: {
             text: dealDepartment.value+' 小时分单量统计图（'+startDate.value+'到'+endDate.value+'）'
         },
         tooltip: {},
         toolbox: {
             show: true,
             feature: {
                 dataView: {readOnly: false},
                 magicType: {type: ['line', 'bar']},
                 restore: {},
                 saveAsImage: {}
             }
         },
         legend: {
             data:['分单量']
         },
         xAxis: {
             data: x_data
         },
         yAxis: {},
         series: [{
             name: '分单量',
             type: 'bar',
             itemStyle: {
                 normal: {
                     label : {
                         show: true, 
                         position: 'top'
                     }
                 }
             },
             data: series_data
         }]
     };
     
     myChart.setOption(option);
}

function buildDayGraph(data){
	 var myChart = echarts.init(document.getElementById('graph'));
     // 指定图表的配置项和数据
     var option = {
         title: {
             text:dealDepartment.value+' 日分单量统计图（'+startDate.value+'到'+endDate.value+'）'
         },
         tooltip: {
             trigger: 'axis',
             formatter: function (params) {
                 params = params[0];
                 var date = new Date(params.name);
                 return date.getFullYear() + '/' + (date.getMonth() + 1) + '/' + date.getDate() + ' : ' + params.value[1];
             },
             axisPointer: {
                 animation: false
             }
         },
         toolbox: {
             show: true,
             feature: {
                 dataView: {readOnly: false},
                 restore: {},
                 saveAsImage: {}
             }
         },
         legend: {
             data:['分单量']
         },
         xAxis: {
             type: 'time',
             splitLine: {
                 show: false
             }
         },
         yAxis: {
             type: 'value'
         },
         series: [{
             name: '分单量',
             type: 'line',
             itemStyle: {
                 normal: {
                     label : {
                         show: true, 
                         position: 'top'
                     }
                 }
             },
             data: data
         }]
     };
     
     myChart.setOption(option);
}

function buildjQgrid(data){
	$('#main').empty(); 
	//1.构造jqgrid容器
	$('<table/>',{
		id:'theGrid'
	}).appendTo($('#main'));
	//2.构造jqgrid参数
	var colNames = [];
	var colModel = [];
	var autowidth;
	var caption;
	var statisticUnit = $('input[type=radio][name=statisticUnit]:checked').val();
	if(statisticUnit=='hour'){
		colNames = ['日期\\小时', 'h00', 'h01','h02','h03','h04','h05','h06','h07','h08','h09','h10','h11','h12','h13','h14','h15','h16','h17','h18','h19','h20','h21','h22','h23','总计'];
		colModel = [
				    {name: 'date', index: 'date',align:"center",sortable:false},
		            {name: 'h00', index: 'h00',align:"center",sortable:false},
		            {name: 'h01', index: 'h01',align:"center",sortable:false},
		            {name: 'h02', index: 'h02',align:"center",sortable:false},
		            {name: 'h03', index: 'h03',align:"center",sortable:false},
		            {name: 'h04', index: 'h04',align:"center",sortable:false},
		            {name: 'h05', index: 'h05',align:"center",sortable:false},
		            {name: 'h06', index: 'h06',align:"center",sortable:false},
		            {name: 'h07', index: 'h07',align:"center",sortable:false},
		            {name: 'h08', index: 'h08',align:"center",sortable:false},
		            {name: 'h09', index: 'h09',align:"center",sortable:false},
		            {name: 'h10', index: 'h10',align:"center",sortable:false},
		            {name: 'h11', index: 'h11',align:"center",sortable:false},
		            {name: 'h12', index: 'h12',align:"center",sortable:false},
		            {name: 'h13', index: 'h13',align:"center",sortable:false},
		            {name: 'h14', index: 'h14',align:"center",sortable:false},
		            {name: 'h15', index: 'h15',align:"center",sortable:false},
		            {name: 'h16', index: 'h16',align:"center",sortable:false},
		            {name: 'h17', index: 'h17',align:"center",sortable:false},
		            {name: 'h18', index: 'h18',align:"center",sortable:false},
		            {name: 'h19', index: 'h19',align:"center",sortable:false},
		            {name: 'h20', index: 'h20',align:"center",sortable:false},
		            {name: 'h21', index: 'h21',align:"center",sortable:false},
		            {name: 'h22', index: 'h22',align:"center",sortable:false},
		            {name: 'h23', index: 'h23',align:"center",sortable:false},
		            {name: 'datetotal', index: 'datetotal',align:"center",sortable:false}
		        ];
		autowidth = true;
		caption = dealDepartment.value+' 小时分单量统计报表（'+startDate.value+'到'+endDate.value+'）';
	}else{
		colNames = ['日期','分单量'];
		colModel = [
						{name: 'date', index: 'date',align:"center",sortable:false},
						{name: 'count', index: 'count',align:"center",sortable:false}
		            ];
		autowidth = false;
		caption = dealDepartment.value+' 日分单量统计报表（'+startDate.value+'到'+endDate.value+'）';
	}

	//3.构造jqgrid
	$('#theGrid').jqGrid({
		datatype: "local",
        colNames: colNames,
        colModel: colModel,
		height:'600px',
		autowidth:autowidth,
		rowNum:'all',
		caption:caption //出游前 小时分单量统计报表（xx-xx）
    });

	//4.填充数据
    for(var i=0;i<=data.length;i++) 
		$("#theGrid").jqGrid('addRowData',i+1,data[i]);
}

</script>
</HEAD>
<BODY>
<form name="complaintNum_form" id="complaintNum_form" method="post" action="">
	<div class="pici_search pd5">
		<div class="pici_search pd5 mb10">
		日期范围：<input type="text" size="20" name="startDate" id="startDate" value="${startDate }" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endDate\')}',minDate:'#F{$dp.$D(\'endDate\',{M:-3})}'})" readonly="readonly" /> 至 
		      <input type="text" size="20" name="endDate" id="endDate" value="${endDate }" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'#F{$dp.$D(\'startDate\',{M:3})}'})" readonly="readonly" />
		 <br/>
		 时间维度：
		<s:radio id="timeDimension" name="timeDimension" list="#{'build_date':'投诉时间','assign_time':'分配时间'}" listKey="key" listValue="value"/>
		<br/>
		处理岗：
		<s:select id="dealDepartment" name="dealDepartment" list="dealDepartments" ></s:select>
		<br/>
		展现方式：
		<s:radio id="showType" name="showType" list="#{'0':'表','1':'图'}" listKey="key" listValue="value"/>
		<br/>
		统计单位：
		<s:radio id="statisticUnit" name="statisticUnit" list="#{'hour':'小时','day':'日'}" listKey="key" listValue="value"/>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" value="查询"  onclick="onSubmit()">
		</div>
	</div>
</form>

<div id="main"></div>

</BODY>
</HTML>
