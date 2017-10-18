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
	
	  $("#searchForm").attr("action", "qc/qcMonitor/list");
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

function deleteQcMonitor(input,id){
	
	layer.confirm('确认删除？', {
	  btn: ['确定','关闭'] //按钮
	}, function(){
		$.ajax( {
			url : 'qc/qcMonitor/deleteQcMonitor',
			data : 	{id:id} ,
			type : 'post',
			dataType:'json',
			cache : false,
			success : function(result) {
				if(result)
				{
			    	if(result.retCode == "0")
					{
			    		layer.alert("删除成功", {icon: 1});
			    		search();
					 }else{
						
						layer.alert(result.resMsg, {icon: 2});
					}
			     }
			 }
	    });
	}, function(){
	  
	});
	
	
}
</script>
<title>质检监控列表</title>
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
<h3>质检监控列表</h3> 
<div align="right">
<table width="85%" class="search">
	<tr>
	    <td>订单号：</td>
	    <td><form:input path="dto.orderId"  /></td>
	    <td>线路编号：</td>
    	<td><form:input path="dto.routeId"/></td>
    	<td>质检日期：</td>
		<td>
			<form:input path="dto.addTimeFrom"  onfocus="setMaxDate('addTimeTo')" />
			至
			<form:input path="dto.addTimeTo"  onfocus="setMinDate('addTimeFrom')" />
		</td>
        <td>质检类型：</td>
		<td>
			<form:input path="dto.qcType"/>
		</td>	
	</tr>
	<tr>
	<td>是否合格：</td>
	<td>
		<form:select path="dto.isQualified" id="isQualified" onchange="searchResetPage()"  style="width:100px">
	     	<form:option value="" label="所有" />
			<form:option value="1" label="合格" />
			<form:option value="0" label="不合格" />
		</form:select>
	</td>
	<td>关联部门：</td>
	<td><form:input path="dto.relevantDepartment"/></td>
    <td>关联岗位：</td>
	<td><form:input path="dto.relevantPost"/></td>
    <td>相关人员：</td>
	<td><form:input path="dto.relevantPerson"/></td>
	</tr>
	<tr>
		<td colspan="8">
			<input type="button" class="blue" 	value="查询" onclick="searchResetPage()"/>
		    <input type="button" class="blue" value="重置" onclick="resetSearchTable()"/>
		    <input type="button" class="blue"   value="导入"  onclick="openWin('质检监测导入', 'qc/qcMonitor/toImport',  '870', '300')"/>
		</td>
	</tr>
</table>
</div>
</div>
<table class="listtable">
  	<tr>
  	<th class="id">质检单号</th>
	<th class="circle">周期</th>
	<th class="qcType">质检类型</th>
	<th class="type">分类</th>
	<th class="orderId">订单号</th>
	<th class="routeId">线路编号</th>
	<!-- <th class="relatedFlag">是否连带责任</th> -->
	<th class="relevantPerson1">相关人员1</th>
	<th class="relevantPost1">关联岗位1</th>
	<th class="relevantDepartment1">关联部门1</th>
	<th class="relevantPerson2">相关人员2</th>
	<th class="relevantPost2">关联岗位2</th>
	<th class="relevantDepartment2">关联部门2</th>
	<th class="isQualified">是否合格</th>
	<th class="questionType">问题分类</th>
	<th class="poorMoney">欠收金额</th>
	<th class="receivableMoney">应收金额</th>
	<th class="qcConclusion">质检结论</th>
	<th class="qcPerson">质检员</th>
	<th class="qcDate">质检日期</th>
	<th >操作</th>
 	 </tr>
	<c:forEach items="${dto.dataList}" var="qcMonitor">
	 <tr>
		 <td class="id">${qcMonitor.id } </td>
		 <td class="circle">${qcMonitor.circle } 周</td>
		 <td class="shorten10">${qcMonitor.qcType } </td>
		 <td class="type">${qcMonitor.type } </td>
		 <td class="orderId">${qcMonitor.orderId } </td>
		 <td class="prdId" >${qcMonitor.routeId }</td>
		 <!-- 
		 <td class="relatedFlag">
		 	<c:choose>
			 	<c:when test="${qcMonitor.relatedFlag == 1}">是</c:when>
			 	<c:when test="${qcMonitor.relatedFlag == 0}">否</c:when>
		 	</c:choose>
		  </td>
		  -->
		 <td class="relevantPerson1">${qcMonitor.relevantPerson1 }</td>		 
		 <td class="relevantPost1">${qcMonitor.relevantPost1 }</td>
		 <td class="shorten10">${qcMonitor.relevantDepartment1 } </td>
		 <td class="relevantPerson2">${qcMonitor.relevantPerson2 }</td>		 
		 <td class="relevantPost2">${qcMonitor.relevantPost2 }</td>
		 <td class="shorten10">${qcMonitor.relevantDepartment2 } </td>
		 <td class="isQualified">
		 	<c:choose>
			 	<c:when test="${qcMonitor.isQualified == 1}">合格</c:when>
			 	<c:when test="${qcMonitor.isQualified == 0}">不合格</c:when>
		 	</c:choose>
		  </td>
		 <td class="shorten10">${qcMonitor.questionType }</td>
		 <td class="poorMoney">${qcMonitor.poorMoney }</td>
		 <td class="receivableMoney">${qcMonitor.receivableMoney }</td>
	 	 <td class="shorten10">${qcMonitor.qcConclusion }</td>
		 <td class="qcPerson">${qcMonitor.qcPerson }</td>
		 <td class="qcDate"><fmt:formatDate value="${qcMonitor.qcDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		 <td>
		 	<input type="button" value="删除" id="deleteBtn" class="blue" name="deleteBtn" onclick="deleteQcMonitor(this,${qcMonitor.id})">
		 </td>
	</tr>
    </c:forEach>
</table>
<%@include file="/WEB-INF/jsp/common/pager.jsp"%>
</form>
</body>
</html>
