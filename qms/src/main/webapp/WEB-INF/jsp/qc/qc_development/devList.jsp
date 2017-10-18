<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<script  type="text/javascript">
var realNameArr = new Array();
var userArr = new Array();
$(document).ready(function() {
	WdatePicker('${dto.addTimeFrom}');
	WdatePicker('${dto.addTimeTo}');
	$(".accordion").each(function() {
		$(this).accordion({
	    	collapsible: true,
//	    	active:false, //默认折叠
	    	heightStyle: "content"
	    });
	});
	
	$("#searchForm").validate({
		rules:{
			qcId:{
                digits:true,
                min:0
			}
        },
        messages:{
        	qcId:{digits:"请输入整数"}
        }
		
	});
});
$(function (){
	
	$('#chkAll').click(function() {
		var flag = this.checked;
		$('.listtable td :checkbox[name = qcBillIds]').each(function() {
			this.checked = flag;
		});
	});
	$('#radioset').buttonset();
	$('#radioset').click(function() {
		searchResetPage();
	});
	
	
});

function search() {
	
	  $("#searchForm").attr("action", "qc/resDevQcBill/devList");
	  $("#searchForm").submit();

}
function setMaxDate(id){
	WdatePicker({maxDate:'#F{$dp.$D(\''+id+'\')}'});
}

function setMinDate(id){
	WdatePicker({minDate:'#F{$dp.$D(\''+id+'\')}'});
}

function back2Processing(id) {
	$("#searchForm").attr("action", "qc/resDevQcBill/" + id + "/back2Processing");
	$("#searchForm").submit();
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
	
	$("#qcPerson").autocomplete({
	    source: userArr,
	    autoFocus : true
	}).data( "ui-autocomplete" )._renderItem = function( ul, item ) {
		return $( "<li>" )
        .append( "<a>" + item.value + "</a>" )
        .appendTo( ul );
	};
	}
}


//重置搜索框内容：直接使用reset会影响所有form中内容
function resetSearchTable() {
	$('.search :text').val('');
	$('#qualityEventClass').val("");
}

</script>
<title>研发质检列表</title>
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
	text-align: right;
}

.search td input[type=text] {
	width: 100px;
}

</style>
</head>
<body >
<form name="searchForm" id="searchForm" method="post" action="" >
<div class="accordion">
<h3>研发质检列表</h3> 
<div align="right">
<table width="85%" class="search">
	<tr>
	    <td>质检单号：</td>
    	<td><form:input path="dto.qcId"/></td>
    	<td>添加时间：</td>
		<td>
			<form:input path="dto.addTimeFrom"  onfocus="setMaxDate('addTimeTo')" />
			至
			<form:input path="dto.addTimeTo"  onfocus="setMinDate('addTimeFrom')" />
		</td>
		<td>质量问题等级：</td>
		<td>
			<form:select path="dto.qualityEventClass" id="qualityEventClass" onchange="searchResetPage()"  style="width:100px">
				<form:option value="" label="全部" />
				<form:option value="S" label="S级" />
				<form:option value="A" label="A级" />
				<form:option value="B" label="B级" />
				<form:option value="C" label="C级" />
			</form:select>
		</td>
		
		</tr>
		<tr>
		
		<td>质检人：</td>
    	<td><form:input path="dto.qcPerson"  onfocus="userAutoComplete()"/></td>
    	<td>完成时间：</td>
		<td>
			<form:input path="dto.finishTimeBgn"  onfocus="setMaxDate('finishTimeEnd')" />
			至
			<form:input path="dto.finishTimeEnd"  onfocus="setMinDate('finishTimeBgn')" />
		</td>
		<td >
			<input type="button" class="blue" 	value="查询" onclick="searchResetPage()"/>
		    <input type="button" class="blue" value="重置" onclick="resetSearchTable()"/>
		</td>
	</tr>
</table>
</div>
</div>
<table class="listtable">
  	<tr>
  	<th><input type="checkbox" id="chkAll" title="全选"></th>
	<th class="id">质检单号</th>
	<th class="influenceTime">影响时长(分)</th>
	<th class="qualityEventClass">质量问题等级</th>
	<th class="qcAffairSummary" width="570px">质检事宜概述</th>
	<th class="addPerson">添加人</th>
	<th class="qcPerson">质检人</th>
	<th class="addTime">添加时间</th>
	<th class="finishTime">完成时间</th>
 		 </tr>
 		<c:forEach items="${dto.dataList}" var="qcBill">
 		 <tr>
 		 <td ><input type="checkbox" autocomplete="off"  name="qcBillIds" value="${qcBill.id }" /></td>
  		 <td class="id">
  		 	<a href="javascript:void(0)" onclick="openWin('质检报告','qc/resDevQcBill/${qcBill.id}/qcReport',1000,520)">${qcBill.id}</a>
  		 	</td>
  		 <td class="influenceTime">${qcBill.influenceTime }</td>
  		 <c:if test="${qcBill.qualityEventClass =='S'}">
  		 <td class="qualityEventClass" style="color:red">${qcBill.qualityEventClass }  </td>
  		 </c:if>
  		 <c:if test="${qcBill.qualityEventClass !='S'}">
  		 <td class="qualityEventClass" >${qcBill.qualityEventClass }  </td>
  		 </c:if>
  		  <td class="shorten45" >${qcBill.qcAffairSummary }</td>
  		 <td class="addPerson">${qcBill.addPerson }</td>
  		 <td class="qcPerson">${qcBill.qcPerson }</td>
  		 <td class="addTime"><fmt:formatDate value="${qcBill.addTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
  		 <td class="finishTime"><fmt:formatDate value="${qcBill.finishTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
 		 </tr>
      </c:forEach>
</table>
<%@include file="/WEB-INF/jsp/common/pager.jsp"%>
</form>
</body>
</html>
