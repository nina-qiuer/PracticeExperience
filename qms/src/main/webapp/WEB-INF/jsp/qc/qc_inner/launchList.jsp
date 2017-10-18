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
			},
			ordId:{
                digits:true,
                min:0
			},
			prdId:{
                digits:true,
                min:0
			},
			
        },
        messages:{
        	qcId:{digits:"请输入整数"},
        	ordId:{digits:"请输入整数"},
        	prdId:{digits:"请输入整数"}
        }
		
	});
});

function search() {
	
	  $("#searchForm").attr("action", "qc/innerQcBill/launchList");
	  $("#searchForm").submit();

}
function setMaxDate(id){
	WdatePicker({maxDate:'#F{$dp.$D(\''+id+'\')}'});
}

function setMinDate(id){
	WdatePicker({minDate:'#F{$dp.$D(\''+id+'\')}'});
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
	
	$("#addPerson").autocomplete({
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
}

function deleteQc(input,id){
	
	$.ajax( {
		url : 'qc/innerQcBill/deleteQc',
		data : 	{id:id} ,
		type : 'post',
		dataType:'json',
		cache : false,
		success : function(result) {
			if(result)
			{
		    	if(result.retCode == "0")
				{
		    		search();
				 }else{
					
					layer.alert(result.resMsg, {icon: 2});
				}
		     }
		 }
	    });
	
}
</script>
<title>内部质检申请列表</title>
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
<h3>内部质检申请列表</h3> 
<div align="right">
<table width="85%" class="search">
	<tr>
	    <td>质检单号：</td>
    	<td><form:input path="dto.id"/></td>
        <td>订单号：</td>
	    <td><form:input path="dto.orderId"  /></td>
	    <td>产品单号：</td>
	    <td><form:input path="dto.prdId"  /></td>	
	    <td>提交时间：</td>
		<td>
			<form:input path="dto.addTimeFrom"  onfocus="setMaxDate('addTimeTo')" />
			至
			<form:input path="dto.addTimeTo"  onfocus="setMinDate('addTimeFrom')" />
		</td>
	</tr>
	<tr>
	<td>申请人：</td>
	<c:if test="${dto.checkFlag == 0 }">
    <td><form:input path="dto.addPerson"  disabled="true"/></td>
    </c:if>
    <c:if test="${dto.checkFlag == 1 }">
    <td><form:input path="dto.addPerson"  onfocus="userAutoComplete()"/></td>
    </c:if>
    <td>质检人：</td>
    <td><form:input path="dto.qcPerson"  onfocus="userAutoComplete()"/></td>
   	<td>质检类型：</td>
		<td>
			<form:select path="dto.qcTypeId" id="qcTypeId" onchange="searchResetPage()"  style="width:100px">
		     	<option value="">全部</option>
		     	<form:options items="${qcTypeList}" itemValue="id" itemLabel="name"/>
			</form:select>
		</td>
	<td></td>
	<td >
	 	 <c:if test="${dto.launchFlag == 0 || fn:contains(loginUser_WCS,'INNER_APPLY_STATE')}">
			<input type="button" class="blue" 	value="发起质检" onclick="openWin('发起内部质检','qc/innerQcBill/toAdd','850','520')"/>
			<input type="button" class="blue"   value="导入"  onclick="openWin('内部质检导入', 'qc/innerQcBill/toImport',  '870', '300')"/>
		</c:if>
			<input type="button" class="blue" 	value="查询" onclick="searchResetPage()"/>
		    <input type="button" class="blue" value="重置" onclick="resetSearchTable()"/>
		</td>
	</tr>
</table>
</div>
</div>
<table class="listtable">
  	<tr>
	<th class="id">质检单号</th>
	<th class="ordId">订单号</th>
	<th class="prdId">产品单号</th>
	<th class="lossAmount">公司损失</th>
	<th class="qcTypeId">质检类型</th>
	<th class="qcAffairSummary" width="570px">质检事宜概述</th>
	<th class="addPerson">申请人</th>
	<th class="qcPerson">质检人</th>
	<th class="state">质检状态</th>
	<th class="addTime">发起时间</th>
	<th >操作</th>
 	 </tr>
 		<c:forEach items="${dto.dataList}" var="qcBill">
 		 <tr>
  		 <td class="id">
  		 	<a href="javascript:void(0)" onclick="openWin('质检报告','qc/innerQcBill/${qcBill.id}/qcReport',1000,520)">${qcBill.id}</a>
  		 </td>
  		 <td class="orderId">${qcBill.ordId } </td>
  		 <td class="prdId">${qcBill.prdId } </td>
  		 <td class="lossAmount">${qcBill.lossAmount } </td>
  		 <td class="qcTypeName">${qcBill.qcTypeName} </td>
  		 <td class="shorten45" >${qcBill.qcAffairSummary }</td>
  		 <td class="addPerson">${qcBill.addPerson }</td>
  		 <td class="qcPerson">${qcBill.qcPerson }</td>
  		 <td class="state">
  		  <c:if test="${qcBill.state ==1}">
  		        发起中
  		  </c:if>
  		  <c:if test="${qcBill.state ==2}">
  		        待分配
  		  </c:if>
  		    <c:if test="${qcBill.state ==3}">
  		        质检中
  		  </c:if>
  		    <c:if test="${qcBill.state ==4}">
  		        已完成
  		  </c:if>
  		  </td>
		 <td class="addTime" ><fmt:formatDate value="${qcBill.addTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
  		 <td>
  		     <c:if test="${qcBill.state ==1 && userFlag== 0}">
				<input type="button" value="编辑" id="editeBtn" class="blue" name="editeBtn"  onclick="openWin('修改质检单','qc/innerQcBill/${qcBill.id}/toUpdate',850,520)">
				<input type="button" value="删除" id="deleteBtn" class="blue" name="deleteBtn" onclick="deleteQc(this,${qcBill.id})">
			</c:if>
		 </td>
 		</tr>
      </c:forEach>
</table>
<%@include file="/WEB-INF/jsp/common/pager.jsp"%>
</form>
</body>
</html>
