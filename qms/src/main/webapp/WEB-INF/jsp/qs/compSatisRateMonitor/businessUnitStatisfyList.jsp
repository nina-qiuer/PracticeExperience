<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<style type="text/css">
/*搜索框表格样式*/
<style type="text/css">
/*basic*/
.clearfix:after {
	content: ".";
	display: block;
	height: 0;
	clear: both;
}

/*覆盖插件默认样式*/
.ui-autocomplete {
	max-height: 100px;
	overflow-y: auto;
	/* prevent horizontal scrollbar */
	overflow-x: hidden;
}

.ui-widget {
	font-family: Microsoft YaHei;
}

/*搜索框表格样式*/
.search {
	font-size: 12px;
	font-family: Microsoft YaHei;
}

/* .search td:nth-child(even) {
	text-align: left;
}

.search td:nth-child(odd) {
	text-align: right;
} */

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
<script type="text/javascript">
var depArr = new Array();
$(function(){
	$('#accordion').accordion({
    	collapsible: true,
    	heightStyle: "content"
    });
	
});


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
		
		   $("#quarter").show(); 
		   $("#month").css('display', 'none'); 
	}
	if(time_type==2){
		
		   $("#quarter").css('display', 'none');
		   $("#month").show(); 
	}
}

function search() {
	
	$("#dataForm").attr("action", "qs/compSatisRate/list");
	$("#dataForm").submit();
}

function addSatisfyTabx(businessUnit,targetId) {
 
	  var timeType = '${dto.timeType}';
	  var qYear =  '${dto.qYear}';
	  var quarter ='${dto.quarter}';
	  var mYear = '${dto.mYear}';
	  var month = '${dto.month}';
	  if(timeType ==1){
		  
		  nextSearch = qYear + quarter;
	  }else{
		  
		  nextSearch = mYear + month;
	  }
	  if(targetId ==0 ){
			 
	    	 layer.alert("没有配置目标值", {icon: 2});
			 return false;
			 
		 }else{
			parent.addSatisfyTab(businessUnit,targetId,"prdDepList",timeType,nextSearch);
		 }
}

function sendMail(targetId){
	
	  var search ="";
	  if(targetId == 0 ){
			 
	    	 layer.alert("没有配置目标值", {icon: 2});
			 return false;
			 
		 }else{
			 
			  var timeType = '${dto.timeType}';
			  var qYear =  '${dto.qYear}';
			  var quarter ='${dto.quarter}';
			  var mYear = '${dto.mYear}';
			  var month = '${dto.month}';
			  if(timeType ==1){
				  
				  search = qYear + quarter;
			  }else{
				  
				  search = mYear + month;
			  }
			 
			 openWin('发送预警报告','qs/compSatisRate/'+targetId+'/'+search+'/'+timeType+'/toAddEmail',1050, 450);
		 }
	
}
</script>
</head>
<body>
<form id="dataForm" method="post" action="">
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
  <div id="accordion">
<h3>综合满意度达成率监控列表</h3>
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
					 <td style="text-align: right" > 时间范围：</td> 
					<c:if test="${ dto.timeType==1}">
					<td id="quarter" style="text-align: left">
					 <form:select path="dto.qYear" class="qYear"  style="width:100px" >
							<form:option value="${dto.nowYear}" label="${dto.nowYear}年" />
							<form:option value="${dto.nowYear-1}" label="${dto.nowYear-1}年" />
							<form:option value="${dto.nowYear-2}" label="${dto.nowYear-2}年" />
					 	</form:select>-
						<form:select path="dto.quarter" class="quarter" style="width:60px" >
							<form:option value="01" label="Q1" />
							<form:option value="02" label="Q2" />
							<form:option value="03" label="Q3" />
							<form:option value="04" label="Q4" />
					 	</form:select>		
					</td>
					<td id="month" style="display: none">
						<form:select path="dto.mYear" class="mYear"  style="width:100px" >
								<form:option value="${dto.nowYear}" label="${dto.nowYear}年" />
								<form:option value="${dto.nowYear-1}" label="${dto.nowYear-1}年" />
								<form:option value="${dto.nowYear-2}" label="${dto.nowYear-2}年" />
						 	</form:select>-
					      <form:select path="dto.month" >
								<form:options items="${monthList}"/>
						 </form:select>
					 </td>
					</c:if>
					<c:if test="${dto.timeType==2}">
						<td id="month" style="text-align: left" >
							    <form:select path="dto.mYear" class="mYear"  style="width:100px" >
									<form:option value="${dto.nowYear}" label="${dto.nowYear}年" />
									<form:option value="${dto.nowYear-1}" label="${dto.nowYear-1}年" />
									<form:option value="${dto.nowYear-2}" label="${dto.nowYear-2}年" />
							 	</form:select>-
						      <form:select path="dto.month" >
									<form:options items="${monthList}"/>
							 </form:select>
							</td>
						<td id="quarter" style="display: none">
						   <form:select path="dto.qYear" class="qYear"  style="width:100px" >
								<form:option value="${dto.nowYear}" label="${dto.nowYear}年" />
								<form:option value="${dto.nowYear-1}" label="${dto.nowYear-1}年" />
								<form:option value="${dto.nowYear-2}" label="${dto.nowYear-2}年" />
						 	</form:select>-
							<form:select path="dto.quarter" class="quarter" style="width:60px" >
								<form:option value="01" label="Q1" />
								<form:option value="02" label="Q2" />
								<form:option value="03" label="Q3" />
								<form:option value="04" label="Q4" />
						 	</form:select>		
						</td>
					</c:if>
					<td>
						<input type="button" class="blue" 	value="查询" onclick="searchResetPage()"/> 
						<input type="button" class="blue" value="重置" onclick="resetSearchTable()" />
					</td>
				  </tr>
	</table>
</div>
</div>
<table class="listtable">
<tr>
		<th width="50px">排名</th>
		<th width="50px">事业部</th>
		<th style="display: none">事业部ID</th>
		<th width="50px">目标</th>
		<th width="50px">投诉订单数</th>
		<th width="50px">点评量</th>
		<th width="50px">投诉率</th>
		<th width="50px">满意度</th>
		<th width="50px" >综合满意度</th>
		<th width="50px" >达成率</th>
		<th width="50px" >详情</th>
		<th width="50px">操作</th>
	</tr>
	<c:forEach items="${dataList}" var="satisfy" varStatus="statis">
	<tr>
		<td >${statis.index+1}</td>
	    <td >${satisfy.businessUnit}</td>
	    <td  style="display: none">${satisfy.targetId}</td>
	    <td >${satisfy.targetValue}</td>
		<td >${satisfy.cmpNum}</td>
		<td >${satisfy.commentNum}</td>
		<td ><fmt:formatNumber value="${satisfy.cmpRate}" type="percent" pattern="#0.00%"/></td>
		<td ><fmt:formatNumber value="${satisfy.satisfaction}" type="percent" pattern="#0.00%"/></td>
		<td ><fmt:formatNumber value="${satisfy.compSatisfaction}" type="percent" pattern="#0.00%"/></td>
		<td ><fmt:formatNumber value="${satisfy.reacheRate}" type="percent" pattern="#0.00%"/></td>
		<td >
		  <a href="javascript:void(0)" onclick="addSatisfyTabx('${satisfy.businessUnit}','${satisfy.targetId}')">详情</a>
		</td>
		<td>
		<c:if test="${fn:contains(loginUser_WCS,'satisfyAlert')}">
		<input type="button" class='blue' name="submitBtn" id="submitBtn" value="预警" onclick="sendMail('${satisfy.targetId}')" >
		</c:if>
		</td>
	</tr>
	</c:forEach>
</table>
</form>
</body>
</html>
