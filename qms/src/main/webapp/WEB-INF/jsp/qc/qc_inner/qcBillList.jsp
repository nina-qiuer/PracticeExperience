<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<script  type="text/javascript">
var realNameArr = new Array();
var userArr = new Array();
$(document).ready(function() {
	WdatePicker('${dto.submitTimeFrom}');
	WdatePicker('${dto.submitTimeTo}');
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
	
	  $("#searchForm").attr("action", "qc/innerQcBill/list");
	  $("#searchForm").submit();

}
function setMaxDate(id){
	WdatePicker({maxDate:'#F{$dp.$D(\''+id+'\')}'});
}

function setMinDate(id){
	WdatePicker({minDate:'#F{$dp.$D(\''+id+'\')}'});
}

function back2Processing(id) {
	$("#searchForm").attr("action", "qc/innerQcBill/" + id + "/back2Processing");
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
	
	$("#assignTo").autocomplete({
	    source: userArr,
	    autoFocus : true
	}).data( "ui-autocomplete" )._renderItem = function( ul, item ) {
		return $( "<li>" )
        .append( "<a>" + item.value + "</a>" )
        .appendTo( ul );
	};
	}
}

function assign(input){
	var isExists = false;
	var assignTo = $('#assignTo').val();
	if($.trim(assignTo)==''){
		
		 layer.alert("请输入分配人!",{icon: 2});
		return  false; 
	}
	for(var i=0;i<realNameArr.length;i++){
		
		if(assignTo == realNameArr[i]){
			
			isExists = true;
		}
	}
	if(isExists ==false){
		
		layer.alert("分配人不存在!",{icon: 2});
		return  false; 
	}
	if ($(':checkbox[name="qcBillIds"]:checked').length <= 0) {
		layer.alert("请至少选择一个质检单！", {
			icon : 2
		});
		return false;
	}
	disableButton(input);
	$.ajax( {
		url : 'qc/innerQcBill/assignDelPerson',
		data : $('#searchForm').serialize(),
		type : 'post',
		dataType:'json',
		cache : false,
		success : function(result) {
			enableButton(input);
			if(result)
			{
		    	if(result.retCode == "0")
				{
		    		 layer.alert("分配成功!",{icon: 1});
		    		 search();
		    	
				 }else{
					
					 layer.alert(result.resMsg,{icon: 2});
				}
		     }
		 }
	    });
}


//重置搜索框内容：直接使用reset会影响所有form中内容
function resetSearchTable() {
	$('.search :text').val('');
}

</script>
<title>内部质检单列表</title>
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
<h3>内部质检列表</h3> 
<div align="right">
<table width="100%" class="search">
	<tr>
	    <td>质检单号：</td>
    	<td><form:input path="dto.id"/></td>
    	<td>产品编号：</td>
    	<td><form:input path="dto.prdId"/></td>
		<td>提交时间：</td>
		<td>
			<form:input path="dto.submitTimeFrom"  onfocus="setMaxDate('submitTimeTo')" />
			至
			<form:input path="dto.submitTimeTo"  onfocus="setMinDate('submitTimeFrom')" />
		</td>
		
		<td>质检类型：</td>
		<td>
			<form:select path="dto.qcTypeId" id="qcTypeId" onchange="searchResetPage()"  style="width:100px">
		     	<option value="">全部</option>
		     	<form:options items="${qcTypeList}" itemValue="id" itemLabel="name"/>
			</form:select>
		</td>
	</tr>
	<tr>
    	<td>质检人：</td>
		<td>
			<form:input path="dto.qcPerson"/>
		</td>
		<td>订单编号：</td>
    	<td><form:input path="dto.orderId"/></td>
    	<td>完成时间：</td>
		<td>
			<form:input path="dto.finishTimeBgn"  onfocus="setMaxDate('finishTimeEnd')" />
			至
			<form:input path="dto.finishTimeEnd"  onfocus="setMinDate('finishTimeBgn')" />
		</td>	
		
		<td></td>
		<td colspan="2" style="text-align: left;">
		<c:if test="${dto.state ==3 || dto.state==2}">
			    <input type="text" id="assignTo" name="assignTo" onfocus="userAutoComplete()"/>
				<input type="button" class="blue" name="assginBtn" value="分配处理人" onclick="assign(this)" />
		</c:if>
		<c:if test="${dto.state !=3 && dto.state!=2 }">
			    <input type="hidden" id="assignTo" name="assignTo" value=""  />
				<input type="hidden" class="blue" name="assginBtn" value="分配处理人" onclick="assign(this)" />
		</c:if>
		</td>
	</tr>
	<tr>
		<td>质检状态：</td>
		<td>
			<div id="radioset" >
				<c:if test="${fn:contains(loginUser_WCS,'MANAGER_QC_STATE')}">
					<form:radiobutton path="dto.state" value="-1" label="全部" />
					<form:radiobutton path="dto.state" value="2" label="待分配" />
				</c:if>
				<form:radiobutton path="dto.state" value="3" label="质检中" />
				<form:radiobutton path="dto.state" value="4" label="已完成" />
			</div>
		</td>
		<td></td><td></td><td></td><td></td>
		<td colspan="2" style="text-align: center;">
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
	<th class="orderId">订单编号</th>
	<th class="prdId">产品编号</th>
	<th class="lossAmount">公司损失</th>
	<th class="qcTypeId">质检类型</th>
	<th class="qcAffairSummary" width="570px">质检事宜概述</th>
	<th class="submitTime">提交时间</th>
	<th class="finishTime">完成时间</th>
	<th class="addPerson">申请人</th>
	<th class="qcPerson">质检人</th>
	<th >操作</th>
  </tr>
 <c:forEach items="${dto.dataList}" var="qcBill">
 		 <tr>
 		 <td ><input type="checkbox" autocomplete="off"  name="qcBillIds" value="${qcBill.id }" /></td>
  		 <td class="id">
  		 	<a href="javascript:void(0)" onclick="openWin('质检单','qc/innerQcBill/${qcBill.id}/qcReport',1000,450)" >${qcBill.id}</a>
  		 </td>
  		 <td class="orderId">${qcBill.ordId }</td>
  		 <td class="prdId">${qcBill.prdId }</td>
  		 <td class="lossAmount" >${qcBill.lossAmount }</td>
  		 <td class="qcTypeId" >${qcBill.qcTypeName }</td>
  		 <td class="qcAffairSummary" >${qcBill.qcAffairSummary }</td>
  		 <td class="submitTime"><fmt:formatDate value="${qcBill.submitTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
  		 <td class="finishTime"><fmt:formatDate value="${qcBill.finishTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
  		 <td class="addPerson">${qcBill.addPerson }</td>
  		 <td class="qcPerson">${qcBill.qcPerson }</td>
  		 <td class="inner">
  		 <c:if test="${qcBill.state==3 }">
  	      	<a href="javascript:void(0)" onclick="window.open('qc/innerQcBill/${qcBill.id}/toBill','_blank')">质检</a>
  		 </c:if>
  		 <c:if test="${qcBill.state==4&&(loginUser.role.type==2||loginUser.role.type==3)&&fn:contains(loginUser_WCS,'BACK2PROCESSING')}">
				<a href="javascript:void(0)" onclick="back2Processing(${qcBill.id})">返回质检中</a>
			</c:if>
		</td>
 	 </tr>
   </c:forEach>
</table>
<%@include file="/WEB-INF/jsp/common/pager.jsp"%>
</form>
</body>
</html>
