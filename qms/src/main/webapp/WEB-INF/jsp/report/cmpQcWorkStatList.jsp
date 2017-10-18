<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>投诉质检工作量统计</title>
<style type="text/css">
/* .listtable td{text-align:center;background:none}
.listtable th{background-color:#4C68A2;color:white;font-weight:bold} */
.hidden{	display:none;} 

img{
	vertical-align:middle;
}


img:hover{
	cursor: pointer;
}
</style>
<script type="text/javascript">
const shrinkImgSrc= "res/img/nolines_plus.gif";
const expandImgSrc= "res/img/nolines_minus.gif";
const deptLevelPre = 'dept_level_';
$(function(){
	$('#accordion').accordion({
    	collapsible: true,
    	heightStyle: "content"
    });
	
	$('tr[class^=dept_level_]').addClass('hidden');
	$('.dept_level_1').removeClass('hidden');
	$('img').attr('src',shrinkImgSrc).click(
			function(){
				toggleData(this);
			});
	
});

function toggleData(img){
	//拿到class,确定当前操作类型
	var imgClass = $(img).attr('class');
	var parentTr = $(img).parent().parent();
	//拿到level
	var deptLevel = $(parentTr).attr('class').substr(-1);
	
	if(imgClass=='expand'){ //如果当前为展开状态，则收缩所有子孙节点
		$(img).attr('src',shrinkImgSrc).removeClass('expand'); //改变当前图片
		getDescendant(parentTr,deptLevel).addClass("hidden"); //所有子节点都隐藏
	}else {
		$(img).attr({'src':expandImgSrc}).addClass('expand');
		getDescendant(parentTr,deptLevel).removeClass("hidden");
		/* 展示隐藏的下级部门 */
		/* showHideChild(img);  */
	}
}

/* 展示隐藏的下级部门 */
function showHideChild(img){
	var imgClass = $(img).attr('class');
	var parentTr = $(img).parent().parent();
	var deptLevel = $(parentTr).attr('class').substr(-1);
	getChildren(parentTr,deptLevel).removeClass("hidden");
	
	/* getChildren(parentTr,deptLevel).find('.expand').each(function(){
		showHideChild(this);
	}); */
	
}
	
/* 获取子孙部门 */
function getDescendant(ancestorTr,deptLevel){
	var trs = $('tr[id^="'+$(ancestorTr).attr('id')+'_"]');
	return trs;
	
}

/* 获取子部门 */
function getChildren(parentTr,deptLevel){
	var trs = $('tr[id^="'+$(parentTr).attr('id')+'_"][class*=dept_level_'+(deptLevel-0+1)+']');
	return trs;
}


function search() {
	$("#cmpQcWorkStatForm").attr("action", "report/cmpQcWorkStat/list");
	$("#cmpQcWorkStatForm").submit();
}

function resetForm() {
	$("input[type=text]", "#cmpQcWorkStatForm").val('');
}

function setMaxDate(id){
	WdatePicker({maxDate:'#F{$dp.$D(\''+id+'\')}'});
}

function setMinDate(id){
	WdatePicker({minDate:'#F{$dp.$D(\''+id+'\')}'});
}
</script>
</head>
<body>
<form id="cmpQcWorkStatForm" method="post" action="">
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
<div id="accordion">
<h3>投诉质检工作量统计列表</h3>
<div align="right">
<table width="95%">
	<tr>
		<td>
			统计日期：<form:input path="dto.statDateBgn" onfocus="setMaxDate('statDateEnd')"  readonly="readonly" size="10"/> - 
			<form:input path="dto.statDateEnd" onfocus="setMinDate('statDateBgn')"  readonly="readonly" size="10"/>　
			<input type="button" class="blue" value="查询" onclick="search()">
			<input type="button" class="blue" value="重置" onclick="resetForm()">
		</td>
	</tr>
</table>
</div>
</div>
<table class="listtable">
	<tr>
		<th>部门</th>
		<th>分配单数</th>
		<th>总完成单数</th>
		<th>投诉未完成<br>质检已完成单数</th>
		<th>撤销单数</th>
		<th>撤销率</th>
		<th>总产出<br>问题点数</th>
		<th>问题率</th>
		<th>上班天数</th>
		<th>日均<br>完成单数</th>
		<th>日均产出<br>问题点数</th>
		<th>执行<br>问题个数</th>
		<th>流程系统<br>问题个数</th>
		<th>供应商<br>问题个数</th>
		<th>低满意度<br>问题个数</th>
		
		<th>及时<br>完成单数</th>
		<th>到期单数</th>
		<th>及时率</th>
		<th>质检期开始<br>已完成单数</th>
		<th>质检期开始<br>总单数</th>
		<th>完成率</th>
	</tr>
	
	<c:forEach items = "${dataList}" var = "groupData" varStatus="level1Status">
		<tr style="background-color:#9EB6E4;" class="dept_level_1" id="dept_${level1Status.count}">
			<td style="text-align:left">
				<img>
				${groupData.name}
			</td>
			<td>${groupData.data.distribNum}</td>
			<td>${groupData.data.doneTotalNum}</td>
			<td>${groupData.data.cmpUnDoneQcDoneNum}</td>
			<td>${groupData.data.cancelNum}</td>
			<td><fmt:formatNumber value="${groupData.data.cancelRate}" type="percent" pattern="#0.0%"/></td>
			<td>${groupData.data.problemTotalNum}</td>
			<td><fmt:formatNumber value="${groupData.data.problemRate}" type="percent" pattern="#0.0%"/></td>
			<td>${groupData.data.workDayNum}</td>
			<td>${groupData.data.avgDailyDoneNum}</td>
			<td>${groupData.data.avgDailyProblemNum}</td>
			<td>${groupData.data.zxNum}</td>
			<td>${groupData.data.lcxtNum}</td>
			<td>${groupData.data.gysNum}</td>
			<td>${groupData.data.dmyNum}</td>
			<td>${groupData.data.timelyDoneNum}</td>
			<td>${groupData.data.expireNum}</td>
			<td><fmt:formatNumber value="${groupData.data.timelyRate}" type="percent" pattern="#0.0%"/></td>
			<td>${groupData.data.qcPeriodBgnDoneNum}</td>
			<td>${groupData.data.qcPeriodBgnNum}</td>
			<td><fmt:formatNumber value="${groupData.data.doneRate}" type="percent" pattern="#0.0%"/></td>
		</tr>
		<c:forEach items = "${groupData.children}" var = "personData" varStatus="level2Status">
			<tr id="dept_${level1Status.count}_${level2Status.count}" class="dept_level_2">
				<td>
					${personData.name }
				</td>
				<td>${personData.data.distribNum}</td>
				<td>${personData.data.doneTotalNum}</td>
				<td>${personData.data.cmpUnDoneQcDoneNum}</td>
				<td>${personData.data.cancelNum}</td>
				<td><fmt:formatNumber value="${personData.data.cancelRate}" type="percent" pattern="#0.0%"/></td>
				<td>${personData.data.problemTotalNum}</td>
				<td><fmt:formatNumber value="${personData.data.problemRate}" type="percent" pattern="#0.0%"/></td>
				<td>${personData.data.workDayNum}</td>
				<td>${personData.data.avgDailyDoneNum}</td>
				<td>${personData.data.avgDailyProblemNum}</td>
				<td>${personData.data.zxNum}</td>
				<td>${personData.data.lcxtNum}</td>
				<td>${personData.data.gysNum}</td>
				<td>${personData.data.dmyNum}</td>
				<td>${personData.data.timelyDoneNum}</td>
				<td>${personData.data.expireNum}</td>
				<td><fmt:formatNumber value="${personData.data.timelyRate}" type="percent" pattern="#0.0%"/></td>
				<td>${personData.data.qcPeriodBgnDoneNum}</td>
				<td>${personData.data.qcPeriodBgnNum}</td>
				<td><fmt:formatNumber value="${personData.data.doneRate}" type="percent" pattern="#0.0%"/></td>
			</tr>
		</c:forEach>
		<tr class="dept_level_2" id="dept_${level1Status.count}_${level2Status.count}_1">
			<td>${groupData.cmpQcWorkProportion.qcPerson}</td>
			<td>${groupData.cmpQcWorkProportion.distribNum}</td>
			<td>${groupData.cmpQcWorkProportion.doneTotalNum}</td>
			<td>${groupData.cmpQcWorkProportion.cmpUnDoneQcDoneNum}</td>
			<td>${groupData.cmpQcWorkProportion.cancelNum}</td>
			<td><fmt:formatNumber value="${groupData.cmpQcWorkProportion.cancelRate}" type="percent" pattern="#0.0%"/></td>
			<td>${groupData.cmpQcWorkProportion.problemTotalNum}</td>
			<td><fmt:formatNumber value="${groupData.cmpQcWorkProportion.problemRate}" type="percent" pattern="#0.0%"/></td>
			<td>${groupData.cmpQcWorkProportion.workDayNum}</td>
			<td>${groupData.cmpQcWorkProportion.avgDailyDoneNum}</td>
			<td>${groupData.cmpQcWorkProportion.avgDailyProblemNum}</td>
			<td>${groupData.cmpQcWorkProportion.zxNum}</td>
			<td>${groupData.cmpQcWorkProportion.lcxtNum}</td>
			<td>${groupData.cmpQcWorkProportion.gysNum}</td>
			<td>${groupData.cmpQcWorkProportion.dmyNum}</td>
			<td>${groupData.cmpQcWorkProportion.timelyDoneNum}</td>
			<td>${groupData.cmpQcWorkProportion.expireNum}</td>
			<td><fmt:formatNumber value="${groupData.cmpQcWorkProportion.timelyRate}" type="percent" pattern="#0.0%"/></td>
			<td>${groupData.cmpQcWorkProportion.qcPeriodBgnDoneNum}</td>
			<td>${groupData.cmpQcWorkProportion.qcPeriodBgnNum}</td>
			<td><fmt:formatNumber value="${groupData.cmpQcWorkProportion.doneRate}" type="percent" pattern="#0.0%"/></td>
		</tr>
	</c:forEach>
	<c:if test="${departmentTotal != null}">
		<tr>
			<td>${departmentTotal.departmentName}</td>
			<td>${departmentTotal.distribNum}</td>
			<td>${departmentTotal.doneTotalNum}</td>
			<td>${departmentTotal.cmpUnDoneQcDoneNum}</td>
			<td>${departmentTotal.cancelNum}</td>
			<td><fmt:formatNumber value="${departmentTotal.cancelRate}" type="percent" pattern="#0.0%"/></td>
			<td>${departmentTotal.problemTotalNum}</td>
			<td><fmt:formatNumber value="${departmentTotal.problemRate}" type="percent" pattern="#0.0%"/></td>
			<td>${departmentTotal.workDayNum}</td>
			<td>${departmentTotal.avgDailyDoneNum}</td>
			<td>${departmentTotal.avgDailyProblemNum}</td>
			<td>${departmentTotal.zxNum}</td>
			<td>${departmentTotal.lcxtNum}</td>
			<td>${departmentTotal.gysNum}</td>
			<td>${departmentTotal.dmyNum}</td>
			<td>${departmentTotal.timelyDoneNum}</td>
			<td>${departmentTotal.expireNum}</td>
			<td><fmt:formatNumber value="${departmentTotal.timelyRate}" type="percent" pattern="#0.0%"/></td>
			<td>${departmentTotal.qcPeriodBgnDoneNum}</td>
			<td>${departmentTotal.qcPeriodBgnNum}</td>
			<td><fmt:formatNumber value="${departmentTotal.doneRate}" type="percent" pattern="#0.0%"/></td>
		</tr>
		<tr>
			<td>${departmentWorPro.qcPerson}</td>
			<td>${departmentWorPro.distribNum}</td>
			<td>${departmentWorPro.doneTotalNum}</td>
			<td>${departmentWorPro.cmpUnDoneQcDoneNum}</td>
			<td>${departmentWorPro.cancelNum}</td>
			<td><fmt:formatNumber value="${departmentWorPro.cancelRate}" type="percent" pattern="#0.0%"/></td>
			<td>${departmentWorPro.problemTotalNum}</td>
			<td><fmt:formatNumber value="${departmentWorPro.problemRate}" type="percent" pattern="#0.0%"/></td>
			<td>${departmentWorPro.workDayNum}</td>
			<td>${departmentWorPro.avgDailyDoneNum}</td>
			<td>${departmentWorPro.avgDailyProblemNum}</td>
			<td>${departmentWorPro.zxNum}</td>
			<td>${departmentWorPro.lcxtNum}</td>
			<td>${departmentWorPro.gysNum}</td>
			<td>${departmentWorPro.dmyNum}</td>
			<td>${departmentWorPro.timelyDoneNum}</td>
			<td>${departmentWorPro.expireNum}</td>
			<td><fmt:formatNumber value="${departmentWorPro.timelyRate}" type="percent" pattern="#0.0%"/></td>
			<td>${departmentWorPro.qcPeriodBgnDoneNum}</td>
			<td>${departmentWorPro.qcPeriodBgnNum}</td>
			<td><fmt:formatNumber value="${departmentWorPro.doneRate}" type="percent" pattern="#0.0%"/></td>
		</tr>
	</c:if>
</table>
<br>
<b>名词解释：</b><br>
　　<b> 1. 统计期：</b>界面选取的时间范围筛选条件；<br>
　　<b> 2. 质检期：</b>质检期开始时间 --- 质检期结束时间，对于投诉质检，投诉未处理完成则不计算质检期；<br>
　　<b> 3. 质检期开始时间：</b>质检分配时间与投诉完成时间取较晚的一个（如非投诉质检，则不判断投诉完成时间），如质检开始时间为节假日，则向后顺延该节假日放假天数；<br>
　　<b> 4. 质检期结束时间：</b>即质检到期时间，是质检期开始时间 + 三个工作日；<br>
　　<b> 5. 投诉未完成质检已完成单数：</b>质检完成时间在统计期内，且[ 投诉尚未完成或投诉完成时间在质检完成时间之后 ]的质检单数；<br>
　　<b> 6. 及时率： </b>及时完成单数 / 到期单数；<br>
　　<b> 7. 及时完成单数：</b>质检到期时间在统计期内的所有质检单中 --- 在质检到期时间之前完成的质检单数；<br>
　　<b> 8. 到期单数：</b>质检到期时间在统计期内的质检单数；<br>
　　<b> 9. 完成率：</b>质检期开始已完成单数 / 质检期开始总单数；<br>
　　<b>10. 质检期开始已完成单数：</b>质检期开始时间在统计期内的所有质检单中 --- 已完成的质检单数；<br>
　　<b>11. 质检期开始总单数：</b>质检期开始时间在统计期内的质检单数。
</form>
</body>
</html>
