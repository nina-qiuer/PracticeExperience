<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>
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
	text-align: right;
}

.search td input[type=text] {
	width: 100px;
}
</style>
<script  type="text/javascript">
$(document).ready(function() {
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
		$('.listtable td :checkbox[name = relationIds]').each(function() {
			this.checked = flag;
		});
	});
	$('#radioset').buttonset();
	$('#radioset').click(function() {
		searchResetPage();
	});
	
	
});

function search() {
	
		$("#searchForm").attr("action", "qc/qcBillRelation/list");
		$("#searchForm").submit();
}


/* function closeRelation(relationId){
	$.ajax( {
		url : 'qc/qcBillRelation/closeRelation',
		data : 
		{
			relationId : relationId
		},
		type : 'post',
		dataType:'json',
		cache: false,
		success : function(result) {
			if(result)
			{
		    	if(result.retCode == "0")
				{
		    		
		    		  search();
				}else{
					
					 layer.alert(result.resMsg,{icon: 2});
				}
		     }
		 }
	    });
} */

function checkRelation(){

	if ($(':checkbox[name="relationIds"]:checked').length <= 0) {
		layer.alert("请至少选择一个投诉关联单！", {
			icon : 2
		});
		return false;
	}
	var str = document.getElementsByName("relationIds");
    var relationIds="";
	 for(var i=0;i<str.length;i++){
	             if(str[i].checked){
	            	 relationIds += str[i].value+",";
	            }
	    } 
	openWin('关联研发质检', 'qc/qcBillRelation/relationQcId?relationIds='+relationIds, 400, 200);
	
}

function batchRelation(){

	if ($(':checkbox[name="relationIds"]:checked').length <= 0) {
		layer.alert("请至少选择一个投诉关联单！", {
			icon : 2
		});
		return false;
	}
	var str = document.getElementsByName("relationIds");
    var relationIds="";
	 for(var i=0;i<str.length;i++){
	             if(str[i].checked){
	            	 relationIds += str[i].value+",";
	            }
	    } 
	openWin('发起研发质检', 'qc/qcBillRelation/toAddQcBill?relationIds='+relationIds, 850, 500);
	
}


function resetSearchTable(){
	
	$('.search :text').val('');
}
function setMaxDate(id){
	WdatePicker({maxDate:'#F{$dp.$D(\''+id+'\')}'});
}

function setMinDate(id){
	WdatePicker({minDate:'#F{$dp.$D(\''+id+'\')}'});
}
</script>
<title>投诉单研发质检单列表</title>
</head>
<body >
<form name="searchForm" id="searchForm" method="post" action="" >
<div class="accordion">
<h3>投诉单研发质检单列表</h3> 
<div>
<table width="100%" class="search">
	<tr>
		<td>投诉质检单号：</td>
		<td><form:input path="dto.qcId"/></td>
		<td>研发质检单号：</td>
		<td><form:input path="dto.devId"/></td>
		<td>投诉单号：</td>
		<td><form:input path="dto.cmpId" /></td>
		<td>订单号：</td>
		<td><form:input path="dto.ordId" /></td>
		
	</tr>
	<tr>
		<td>关联单状态：</td>
		<td>
			<div id="radioset">
				<form:radiobutton path="dto.flag" value="0" label="待关联" />
				<form:radiobutton path="dto.flag" value="1" label="已关联" />
				<form:radiobutton path="dto.flag" value="2" label="已关闭" />
				<form:radiobutton path="dto.flag" value="3" label="已返回" />
			</div>
		</td>
		<td>投诉时间：</td>
		<td>
			<form:input path="dto.cmpStartTime"  onfocus="setMaxDate('cmpEndTime')" />
			 至
			<form:input path="dto.cmpEndTime"  onfocus="setMinDate('cmpStartTime')" />
		</td>
		<td>投诉质检员：</td>
		<td><form:input path="dto.qcPerson" /></td>
		<td>关闭原因：</td>
		<td>
	 		<form:select path="dto.closeType" style="width:100px">
	 			<form:option value="">全部</form:option>
	 			<c:forEach var="type" items="${cmTypeList}">
	 				<form:option value="${type.id}">${type.name}</form:option>
	 			</c:forEach>
	 		</form:select>
 		</td>
 	</tr>
 	<tr>
		<td colspan="8" style="text-align: right;">
			<input type="button" class="blue" value="查询" onclick="searchResetPage()"/>
			<input type="button" class="blue" value="重置" onclick="resetSearchTable()"/>
			<input type="button" class="blue" value="批量关联" onclick="checkRelation()"/>
	        <input type="button" class="blue" value="批量创建" onclick="batchRelation()"/>
		</td>
	</tr>
	</table>
</div>
</div>
<table class="listtable">
  	<tr>
  		<th><input type="checkbox" id="chkAll" title="全选"></th>
	<th class="qcId">投诉质检单号</th>
	<th class="devId">研发质检单号</th>
	<th class="cmpId">投诉单号</th>
	<th class="ordId">订单号</th>
	<th class="qcPerson">投诉质检员</th>
	<th class="cmpTime">投诉时间</th>
	<th>转单时间 </th>
    <th class="indemnifyAmount">对客赔偿</th>
    <th class="claimAmount">供应商赔偿</th>
     <th class="remark">备注</th>
	<th>操作</th>
 		 </tr>
 		<c:forEach items="${dto.dataList}" var="relationBill">
 		 <tr>
 		 	 <td ><input type="checkbox" autocomplete="off"  name="relationIds" value="${relationBill.id }" /></td>
  		 <td class="qcId">
  		<a href="javascript:void(0)" onclick="openWin('质检报告','qc/qcBill/${relationBill.qcId}/qcReport',1000,520)">${relationBill.qcId}</a> 
  		 </td>
  		 <td class="devId">${relationBill.devId }</td>
  		 <td class="cmpId">${relationBill.cmpId }</td>
  		  <td class="orderId">${relationBill.ordId }</td>
  		 <td class="qcPerson">${relationBill.qcPerson }</td>
  		 <td class="cmpTime"><fmt:formatDate value="${relationBill.cmpTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
  		 <td><fmt:formatDate value="${relationBill.addTime }" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
  		 <td class="indemnifyAmount">${relationBill.indemnifyAmount }</td>
  		 <td class="claimAmount">${relationBill.claimAmount }</td>
  		 <td class="remark">${relationBill.remark}</td>
   		 <td> 
   		 	 <c:if test="${relationBill.flag ==0 }">
	   		 	<input type="button" class="blue" value="关联"  style="cursor: pointer;"
				 	onclick="openWin('关联质检单号', 'qc/qcBillRelation/relationQcId?relationIds=${relationBill.id}', 400, 200)">
				 <input type="button" class="blue" value="创建"  style="cursor: pointer;"
				 	onclick="openWin('发起研发质检', 'qc/qcBillRelation/toAddQcBill?relationIds=${relationBill.id}', 850, 500)">
				<input type="button" class="blue" value="关闭" style="cursor: pointer;"
					onclick="openWin('关闭', 'qc/qcBillRelation/toCloseRelation?relationIds=${relationBill.id}', 330, 180)">
				<input type="button" class="blue" value="返回"  style="cursor: pointer;"
				 	onclick="openWin('返回投诉质检', 'qc/qcBillRelation/backToCmpQcBill?relationIds=${relationBill.id}', 480, 250)">
   		 	</c:if>
   		 	<c:if test="${relationBill.flag == 1 }">
	   		 	 <input type="button"  disabled="disabled" value="已关联" >
				 <input type="button" class="blue" value="关闭" style="cursor: pointer;"
					onclick="openWin('关闭', 'qc/qcBillRelation/toCloseRelation?relationIds=${relationBill.id}', 330, 180)">
  		 	</c:if>
   		  </td>
 		 </tr>
      </c:forEach>
</table>
<%@include file="/WEB-INF/jsp/common/pager.jsp"%>
</form>
</body>
</html>
