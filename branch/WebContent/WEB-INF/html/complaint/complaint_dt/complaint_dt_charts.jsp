<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>投诉处理数据图表</title>
<script type="text/javascript" src="${CONFIG.res_url}script/highcharts/highcharts.js"></script>
<link type="text/css" rel="stylesheet" href="${CONFIG.res_url}css/special.css?111" />
<script type="text/javascript">
	var chart_type = 'bar';
	var stacking_type = 'normal';

	$(function() {
		//初始化图表高度
		setAllSize();
		//窗口改变时变化图表高度
		$(window).resize(setAllSize);
	})

	//获取展示数据
	var getPayOutData = function() {
		$('#main').empty();//清空背景
		$(".background_mask").show();
		$.ajax({
			type : "POST",
			url : "complaint_dt_charts-echartsDataInit",
			data : {
				"assignTimeBgn" : $("#assignTimeBgn").val(),
				"assignTimeEnd" : $("#assignTimeEnd").val()
			},
			async : true,
			success : function(data) {
				$(".background_mask").hide();
				var resultJson = [];
				$.each(data.retObj, function(name, data) {
					for (var index = 0; index < data.length; index++) {
						data[index] = data[index] || 0;
					}
					var tempJson = {
						name : name,
						data : data
					};
					resultJson.push(tempJson);
				})
				//highchartsInit图表初始化
				highchartsInit(chart_type, stacking_type, resultJson);
			}
		});
	}

	var highchartsInit = function(chart_type, stacking_type, series) {
		$('#main').highcharts(
				{
					chart : {
						type : chart_type
					},
					colors : [ '#F44927', '#8085e8', '#FD9538', '#90ed7d',
							'#9A9974', '#D34530', '#40E0D0', '#A26157',
							'#000000' ],
					title : {
						text : ''
					},
					xAxis : {
						categories : [ "公司理论赔付总额", "实际赔付总额", "分担总额", "供应商承担金额",
								"公司承担金额", "质量工程承担金额", "订单利润承担金额","质量成本实际使用额",
								"实际收益", "理论收益" ]
					},
					yAxis : {
						stackLabels : {
							enabled : true,
							style : {
								fontWeight : 'bold',
								color : '#434348'
							}
						},
						title : {
							text : 'Money'
						}
					},
					legend : {
						reversed : true,
						align : 'right',
						x : -30,
						verticalAlign : 'top'
					},
					plotOptions : {
						series : {
							stacking : stacking_type
						},
						column : {
							//条形图上方显示数值
							dataLabels : {
								enabled : true, // dataLabels设为true
								style : {
									color : '#434348'
								}
							}
						}
					},
					series : series,
					credits : {
						enabled : false
					}
				});
	}

	function setAllSize() {
		//获取窗口高度
		$("#main").height($(window).height() - 80 - 50 - 50);
		var search_margin_left = ($(window).width() - 560) / 2;
		$("#search").css("margin-left", search_margin_left);
		var head_margin_left = ($(window).width() - 220) / 2;
		$(".headWordSet").css("margin-left", head_margin_left);
		//展示数据
		getPayOutData();
		//查询按钮点击事件
		$(".searchBtn").unbind("click").bind("click", function() {
			//展示数据
			getPayOutData();
		})

		$(".tiaoxingtu").unbind("click").bind("click", function() {
			chart_type = "column";
			stacking_type = "";
			$(".headWordSet").text("投诉处理赔付统计条形图")
			//展示数据
			getPayOutData();
		})
		$(".duidietu").unbind("click").bind("click", function() {
			chart_type = "bar";
			stacking_type = "normal";
			$(".headWordSet").text("投诉处理赔付统计堆叠图")
			//展示数据
			getPayOutData();
		})
	}
</script>
<style>
.searchDiv {
	width: 100px;
	height: 30px;
	line-height: 30px;
	font-size: 16px;
	font-family: 微软雅黑;
	text-align: left;
	float: left;
}

.searchBtn {
	width: 60px;
	height: 28px;
	line-height: 28px;
	background-color: #E6E6E6;
	/* margin: 10px 1000px 10px; */
	font-size: 16px;
	font-family: 微软雅黑;
	text-align: center;
	border-radius: 6px;
	cursor: pointer;
	float: left;
	border: #94BCD6 1px solid;
	margin-left: 20px;
}

.headTitle {
	height: 50px;
}

.headWordSet {
	float: left;
	height: 50px;
	line-height: 50px;
	font-size: 20px;
	font-family: 微软雅黑;
	width: 220px;
}

.tiaoxingtu {
	float: left;
	height: 30px;
	width: 30px;
	margin-top: 10px;
	cursor: pointer;
	margin-left: 200px;
	background: url("${CONFIG.res_url}images/icon/default/tiaoxingtu.png")
		no-repeat 0px 0px;
}

.duidietu {
	float: left;
	height: 30px;
	width: 30px;
	margin-top: 10px;
	cursor: pointer;
	margin-left: 20px;
	background-color: #F3F3F3;
	background: url("${CONFIG.res_url}images/icon/default/duidietu.png")
		no-repeat 0px 0px;
	cursor: pointer;
}

.datePiker {
	height: 24px;
	line-height: 24px;
	float: left;
	margin-right: 20px;
}

.wordSet {
	font-size: 16px;
	font-family: 微软雅黑;
	text-align: center;
	height: 28px;
	line-height: 28px;
}
</style>
</head>
<body>
	<div class="background_mask"></div>
	<div id="search" style="height: 40px; margin-top: 10px;">
		<div class="searchDiv">统计周期</div>
		<input type="text" id="assignTimeBgn" class="MyWdate datePiker"
			onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'assignTimeEnd\')}',minDate:'#F{$dp.$D(\'assignTimeEnd\',{M:-24})}'})"
			readOnly="readonly">
		<div class="datePiker wordSet">至</div>
		<input type="text" class="MyWdate datePiker" id="assignTimeEnd"
			onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'assignTimeBgn\')}',maxDate:'#F{$dp.$D(\'assignTimeBgn\',{M:24})}'})"
			readOnly="readonly">
		<div class="searchBtn">查询</div>
	</div>
	<div class="headTitle">
		<div class="headWordSet">投诉处理赔付统计堆叠图</div>
		<div class="tiaoxingtu" title="条形图切换"></div>
		<div class="duidietu" title="堆叠图切换"></div>
	</div>
	<div id="main"></div>
</body>
</html>