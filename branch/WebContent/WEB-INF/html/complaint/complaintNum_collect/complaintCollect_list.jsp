<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>

<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>投诉质检汇总统计</title>
<script type="text/javascript" src="${CONFIG.res_url}script/DatePickerNew/WdatePicker.js" ></script>

<script type="text/javascript">

//提交
function onSubmit() {
	
	$('#complaintCollect_form').attr("action", "complaintCollect_stat");
	$('#complaintCollect_form').submit();
}

function commitPage(currentPage,pageSize){
	$('#complaintCollect_form').attr("action","complaintCollect_stat?1=1&page.currentPage="+currentPage+"&page.pageSize="+pageSize);
	$('#complaintCollect_form').submit();
}
// 导出功能
function onSearchExport() {
		var param = $('#complaintCollect_form').serialize();
		$.ajax({
			type: "POST",
			url: "complaintCollect_stat-getExportDataTotal",
			data: param,
			success: function(data){
				if(data > 40000) {
					alert("数据超过40000条，请缩小查询范围，分多次导出！");
				} else {
					$('#complaintCollect_form').attr("action", "complaintCollect_stat-exports");
					$('#complaintCollect_form').submit();
				}
			}
		});
	}

</script>
</HEAD>
<BODY>
<div class="top_crumbs">您当前所在的位置：投诉质检系统&nbsp;&nbsp;&gt;&gt;&nbsp;&nbsp;<span class="top_crumbs_txt">质检责任单统计表</span></div>
<form name="complaintCollect_form" id="complaintCollect_form" method="post" action="complaintCollect_stat">

	<div class="pici_search pd5">
		<div class="pici_search pd5 mb10">
		质检完成日期范围：<input type="text" size="15" name="finishBeginDate" id="finishBeginDate" value="${finishBeginDate }" onfocus="var finishEndDate=$dp.$('finishEndDate');WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,onpicked:function(){finishEndDate.focus();},maxDate:'#F{$dp.$D(\'finishEndDate\')}'})" readonly="readonly" /> 至 
		      <input type="text" size="15" name="finishEndDate" id="finishEndDate" value="${finishEndDate }" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'finishBeginDate\')}'})" readonly="readonly" />
		<!-- 质检完成时间：<input type="text" size="20" name="finishDate" id="finishDate" value="${finishDate}"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" readonly="readonly" />  -->
		&nbsp;&nbsp;&nbsp;&nbsp;
		投诉日期范围：<input type="text" size="15" name="buildBeginDate" id="buildBeginDate" value="${buildBeginDate }" onfocus="var buildEndDate=$dp.$('buildEndDate');WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,onpicked:function(){buildEndDate.focus();},maxDate:'#F{$dp.$D(\'buildEndDate\')}'})" readonly="readonly" /> 至 
		      <input type="text" size="15" name="buildEndDate" id="buildEndDate" value="${buildEndDate }" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'buildBeginDate\')}'})" readonly="readonly" />
		
		<!--  投诉时间：<input type="text" size="20" name="buildDate" id="buildDate" value="${buildDate}"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  />--> 
		&nbsp;&nbsp;&nbsp;&nbsp;
		质检人：<input type="text" size ="15" name ="qcPersonName" id="qcPersonName" value="${qcPersonName}">
		&nbsp;&nbsp;&nbsp;&nbsp;
		问题大类型：
		<select class="mr10" name="bigClassId" id="bigClassId">
			<option value="" >所  有</option>
			<option value="1" <c:if test="${bigClassId=='1' }">selected='selected'</c:if>>执行问题</option>
			<option value="2" <c:if test="${bigClassId=='2' }">selected='selected'</c:if>>供应商问题</option>
			<option value="4" <c:if test="${bigClassId=='4' }">selected='selected'</c:if>>不可抗力</option>
			<option value="5" <c:if test="${bigClassId=='5' }">selected='selected'</c:if>>低满意度</option>
		</select>
		&nbsp;&nbsp;&nbsp;&nbsp;
		改进人：<input type="text" size ="15" name ="improverName" id="improverName" value="${improverName}" >
		&nbsp;&nbsp;&nbsp;&nbsp;
		记分等级：
		<select class="mr10" name="scoreLevel" id="scoreLevel">
			<option value="">所有</option>
			<option value="一级甲等" <c:if test="${scoreLevel=='一级甲等' }">selected='selected'</c:if>>一级甲等</option>
			<option value="一级乙等" <c:if test="${scoreLevel=='一级乙等' }">selected='selected'</c:if>>一级乙等</option>
			<option value="一级丙等" <c:if test="${scoreLevel=='一级丙等' }">selected='selected'</c:if>>一级丙等</option>
		    <option value="二级" <c:if test="${scoreLevel=='二级' }">selected='selected'</c:if>>二级</option>
		    <option value="三级" <c:if test="${scoreLevel=='三级' }">selected='selected'</c:if>>三级</option>
			<option value="一级乙等-红线" <c:if test="${scoreLevel=='一级乙等-红线' }">selected='selected'</c:if>>一级乙等-红线</option>
			<option value="一级乙等-非红线" <c:if test="${scoreLevel=='一级乙等-非红线' }">selected='selected'</c:if>>一级乙等-非红线</option>
		</select>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" value="查询" class="blue" onclick="onSubmit()">
		&nbsp;&nbsp;
		<input type="button" value="导出" class="blue" onclick="onSearchExport()">
		</div>
	</div>
<div id="view_2" >
<table class="listtable" width="100%">
	<thead>
		<th>质检完成时间</th>
		<th>订单号</th>
		<th>投诉单号</th>
		<th>产品类型</th>
		<th>投诉等级</th>
		<th>改进人</th>
		<th>问题大类型</th>
		<th>记分等级</th>
		<th>记分对象1</th>
		<th>记分对象2</th>
		<th>质检人</th>
	</thead>
	<tbody>
		<c:forEach items="${collectList}" var="vo">
		<tr align="center" class="trbg">
			<td>${vo.finishDate }</td>
			<td>${vo.orderId }</td>
			<td>${vo.complaintId }</td>
			<td>${vo.niuLineFlag }</td>
			<td>${vo.level }</td>
			<td>${vo.improverName }</td>
			<td>
			<c:if test="${vo.bigClassId==1}">执行问题</c:if>
			<c:if test="${vo.bigClassId==2}">供应商问题</c:if>
			<c:if test="${vo.bigClassId==4}">不可抗力</c:if>
			<c:if test="${vo.bigClassId==5}">低满意度</c:if>
			</td>
			<td>${vo.scoreLevel }</td>
			<td>${vo.scoreTarget1 }</td>
			<td>${vo.scoreTarget2 }</td>
			<td>${vo.qcPersonName }</td>
		</tr>
		</c:forEach>
	</tbody>
</table>
</div>
</form>
</BODY>
<%@include file="/WEB-INF/html/pagerCommon.jsp" %>
