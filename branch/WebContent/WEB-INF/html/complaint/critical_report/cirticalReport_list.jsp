<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>

<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>售后中心业务关键指标报表</title>
<script type="text/javascript" src="${CONFIG.res_url}script/DatePickerNew/WdatePicker.js" ></script>

<script type="text/javascript">

//提交
function onSubmit() {
	
	$('#cirticalReport_form').attr("action", "criticalReport");
	$('#cirticalReport_form').submit();
}

function commitPage(currentPage,pageSize){
	$('#cirticalReport_form').attr("action","criticalReport?1=1&page.currentPage="+currentPage+"&page.pageSize="+pageSize);
	$('#cirticalReport_form').submit();
}


</script>
</HEAD>
<BODY>
<div class="top_crumbs">您当前所在的位置：投诉质检系统&nbsp;&nbsp;&gt;&gt;&nbsp;&nbsp;<span class="top_crumbs_txt">售后中心业务关键指标报表</span></div>
<form name="cirticalReport_form" id="cirticalReport_form" method="post" action="criticalReport">
<div class="pici_search pd5">
		<div class="pici_search pd5 mb10">
		当前时间：${nowYear}年，第${nowWeek}周 <br>
		客服姓名：<input type="text" size ="15" name ="dealName" id="dealName" value="${dealName}" >
		&nbsp;&nbsp;&nbsp;&nbsp;
		所属岗位：
		<select class="mr10" name="state" id="state">
			<option value="">所有</option>
			<option value="售前组" <c:if test="${state=='售前组' }">selected='selected'</c:if>>售前组</option>
			<option value="售后组" <c:if test="${state=='售后组' }">selected='selected'</c:if>>售后组</option>
			<option value="资深组" <c:if test="${state=='资深组' }">selected='selected'</c:if>>资深组</option>
		    <option value="出游前客户服务" <c:if test="${state=='出游前客户服务' }">selected='selected'</c:if>>出游前客户服务</option>
		</select>
		 年份：
		 <select class="mr10" name="year" id="year">
			<option value="${nowYear}" <c:if test="${year==nowYear }">selected='selected'</c:if>>${nowYear}年</option>
			<option value="${nowYear-1}" <c:if test="${year==nowYear-1}">selected</c:if>>${nowYear-1}年</option>
			<option value="${nowYear-2}" <c:if test="${year==nowYear-2}">selected</c:if>>${nowYear-2}年</option>
		</select>
		 周数：
		 	<select class="mr10"  name="week" id="week">
			 	  <option value="1" <c:if test="${week=='1' }">selected='selected'</c:if>>第1周</option>
				<option value="2" <c:if test="${week=='2' }">selected='selected'</c:if>>第2周</option>
				<option value="3" <c:if test="${week=='3' }">selected='selected'</c:if>>第3周</option>
			    <option value="4" <c:if test="${week=='4' }">selected='selected'</c:if>>第4周</option>
				<option value="5" <c:if test="${week=='5' }">selected='selected'</c:if>>第5周</option>
				<option value="6" <c:if test="${week=='6' }">selected='selected'</c:if>>第6周</option>
				<option value="7" <c:if test="${week=='7' }">selected='selected'</c:if>>第7周</option>
			    <option value="8" <c:if test="${week=='8' }">selected='selected'</c:if>>第8周</option>
			    <option value="9" <c:if test="${week=='9' }">selected='selected'</c:if>>第9周</option>
				<option value="10" <c:if test="${week=='10' }">selected='selected'</c:if>>第10周</option>
				<option value="11" <c:if test="${week=='11' }">selected='selected'</c:if>>第11周</option>
			    <option value="12" <c:if test="${week=='12' }">selected='selected'</c:if>>第12周</option>
				<option value="13" <c:if test="${week=='13' }">selected='selected'</c:if>>第13周</option>
				<option value="14" <c:if test="${week=='14' }">selected='selected'</c:if>>第14周</option>
				<option value="15" <c:if test="${week=='15' }">selected='selected'</c:if>>第15周</option>
			    <option value="16" <c:if test="${week=='16' }">selected='selected'</c:if>>第16周</option> 
				<option value="17" <c:if test="${week=='17' }">selected='selected'</c:if>>第17周</option>
				<option value="18" <c:if test="${week=='18' }">selected='selected'</c:if>>第18周</option>
				<option value="19" <c:if test="${week=='19' }">selected='selected'</c:if>>第19周</option>
			    <option value="20" <c:if test="${week=='20' }">selected='selected'</c:if>>第20周</option> 
				<option value="21" <c:if test="${week=='21' }">selected='selected'</c:if>>第21周</option>
				<option value="22" <c:if test="${week=='22' }">selected='selected'</c:if>>第22周</option>
				<option value="23" <c:if test="${week=='23' }">selected='selected'</c:if>>第23周</option>
			    <option value="24" <c:if test="${week=='24' }">selected='selected'</c:if>>第24周</option> 
				<option value="25" <c:if test="${week=='25' }">selected='selected'</c:if>>第25周</option>
				<option value="26" <c:if test="${week=='26' }">selected='selected'</c:if>>第26周</option>
				<option value="27" <c:if test="${week=='27' }">selected='selected'</c:if>>第27周</option>
			    <option value="28" <c:if test="${week=='28' }">selected='selected'</c:if>>第28周</option> 
				<option value="29" <c:if test="${week=='29' }">selected='selected'</c:if>>第29周</option>
				<option value="30" <c:if test="${week=='30' }">selected='selected'</c:if>>第30周</option>
				<option value="31" <c:if test="${week=='31' }">selected='selected'</c:if>>第31周</option>
			    <option value="32" <c:if test="${week=='32' }">selected='selected'</c:if>>第32周</option> 
				<option value="33" <c:if test="${week=='33' }">selected='selected'</c:if>>第33周</option>
				<option value="34" <c:if test="${week=='34' }">selected='selected'</c:if>>第34周</option>
				<option value="35" <c:if test="${week=='35' }">selected='selected'</c:if>>第35周</option>
			    <option value="36" <c:if test="${week=='36' }">selected='selected'</c:if>>第36周</option>  
				<option value="37" <c:if test="${week=='37' }">selected='selected'</c:if>>第37周</option>
				<option value="38" <c:if test="${week=='38' }">selected='selected'</c:if>>第38周</option>
				<option value="39" <c:if test="${week=='39' }">selected='selected'</c:if>>第39周</option>
			    <option value="40" <c:if test="${week=='40' }">selected='selected'</c:if>>第40周</option> 
				<option value="41" <c:if test="${week=='41' }">selected='selected'</c:if>>第41周</option>
				<option value="42" <c:if test="${week=='42' }">selected='selected'</c:if>>第42周</option>
				<option value="43" <c:if test="${week=='43' }">selected='selected'</c:if>>第43周</option>
			    <option value="44" <c:if test="${week=='44' }">selected='selected'</c:if>>第44周</option>  
				<option value="45" <c:if test="${week=='45' }">selected='selected'</c:if>>第45周</option>
				<option value="46" <c:if test="${week=='46' }">selected='selected'</c:if>>第46周</option>
				<option value="47" <c:if test="${week=='47' }">selected='selected'</c:if>>第47周</option>
			    <option value="48" <c:if test="${week=='48' }">selected='selected'</c:if>>第48周</option>
				<option value="49" <c:if test="${week=='49' }">selected='selected'</c:if>>第49周</option>
				<option value="50" <c:if test="${week=='50' }">selected='selected'</c:if>>第50周</option>
				<option value="51" <c:if test="${week=='51' }">selected='selected'</c:if>>第51周</option>
			    <option value="52" <c:if test="${week=='52' }">selected='selected'</c:if>>第52周</option>  
				<option value="53" <c:if test="${week=='53' }">selected='selected'</c:if>>第53周</option>
				<option value="54" <c:if test="${week=='54' }">selected='selected'</c:if>>第54周</option>
	    	</select>
		<input type="button" value="查询" class="blue" onclick="onSubmit()">
		</div>
		
</div>
<div id="view_2" >
<table class="listtable" width="100%">
	<thead>
		<th>客服姓名</th>
		<th>所属岗位</th>
		<th>回呼及时率</th>
		<th>升级投诉率</th>
		<th>开始时间</th>
		<th>结束时间</th>
	</thead>
	<tbody>
		<c:forEach items="${cirticallist}" var="vo">
		<tr align="center" class="trbg">
			<td>${vo.dealName }</td>
			<td>${vo.state }</td>
			<td>${vo.callBackRate }</td>
			<td>${vo.upgradeComplaintRate }</td>
			<td>${vo.startTime }</td>
			<td>${vo.endTime }</td>
		</tr>
		</c:forEach>
	</tbody>
</table>
</div>
</form>
</BODY>
<%@include file="/WEB-INF/html/pagerCommon.jsp" %>