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
	
	  $("#searchForm").attr("action", "qc/operateQcBill/list");
	  $("#searchForm").submit();

}
function setMaxDate(id){
	WdatePicker({maxDate:'#F{$dp.$D(\''+id+'\')}'});
}

function setMinDate(id){
	WdatePicker({minDate:'#F{$dp.$D(\''+id+'\')}'});
}

function back2Processing(id) {
	$("#searchForm").attr("action", "qc/operateQcBill/" + id + "/back2Processing");
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
		url : 'qc/operateQcBill/assignDelPerson',
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
	$('#destCateName').val("");
}

</script>
<title>运营质检单列表</title>
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
<h3>运营质检列表</h3> 
<div align="right">
<table width="100%" class="search">
	<tr>
	    <td>质检单号：</td>
    	<td><form:input path="dto.qcId"/></td>
    	<td>出游时间：</td>
		<td>
			<form:input path="dto.departDateBgn" onfocus="setMaxDate('departDateEnd')" />
			至 
			<form:input path="dto.departDateEnd" onfocus="setMinDate('departDateBgn')" />
		</td>
		<td>创建时间：</td>
		<td>
			<form:input path="dto.addTimeFrom"  onfocus="setMaxDate('addTimeTo')" />
			至
			<form:input path="dto.addTimeTo"  onfocus="setMinDate('addTimeFrom')" />
		</td>
		<td>目的地大类：</td>
		<td>
			<form:select path="dto.destCateName" class="destCateName"   onchange="searchResetPage()">
					<form:option value="" label="全部" />
					<form:option value="出境长线" label="出境长线" />
					<form:option value="出境短线" label="出境短线" />
					<form:option value="国内长线" label="国内长线" />
					<form:option value="周边" label="周边" />
			</form:select>
		</td>
	</tr>
	<tr>
		<td>订单编号：</td>
    	<td><form:input path="dto.orderId"/></td>
		<td>质检状态：</td>
		<td colspan="2">
			<div id="radioset" >
				<c:if test="${fn:contains(loginUser_WCS,'MANAGER_QC_STATE')}">
					<form:radiobutton path="dto.state" value="-1" label="全部" />
					<form:radiobutton path="dto.state" value="2" label="待分配" />
				</c:if>
				<form:radiobutton path="dto.state" value="3" label="质检中" />
				<form:radiobutton path="dto.state" value="4" label="已完成" />
				<form:radiobutton path="dto.state" value="5" label="已撤销" />
			</div>
		</td>
		<td style="text-align: left;">
		<c:if test="${dto.state ==3 || dto.state==2}">
			    <input type="text" id="assignTo" name="assignTo" onfocus="userAutoComplete()"/>
				<input type="button" class="blue" name="assginBtn" value="分配处理人" onclick="assign(this)" />
		</c:if>
		<c:if test="${dto.state !=3 && dto.state!=2 }">
			    <input type="hidden" id="assignTo" name="assignTo" value=""  />
				<input type="hidden" class="blue" name="assginBtn" value="分配处理人" onclick="assign(this)" />
		</c:if>
		</td>
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
	<th class="destCateName">目的地大类</th>
	<th class="departDate">出游时间</th>
	<th class="addTime">创建时间</th>
	<th class="salerName">客服专员</th>
	<th class="salerManagerName">客服经理</th>
    <th class="salerBusinessUnit">事业部</th>
	<th class="salerDep">部门</th>
	<th class="salerTeam">客服组</th>
	<th class="qcPerson">质检人</th>
	<th >操作</th>
  </tr>
 <c:forEach items="${dto.dataList}" var="qcBill">
 		 <tr>
 		 <td ><input type="checkbox" autocomplete="off"  name="qcBillIds" value="${qcBill.id }" /></td>
  		 <td class="id">
  		 	<a href="javascript:void(0)" onclick="openWin('质检单','qc/operateQcBill/${qcBill.id}/qcReport',1000,450)" >${qcBill.id}</a>
  		 </td>
  		 <td class="orderId">${qcBill.ordId }</td>
  		 <td class="prdId">${qcBill.prdId }</td>
  		 <td class="destCateName" >${qcBill.product.destCateName }</td>
  		 <td class="departDate"><fmt:formatDate value="${qcBill.orderBill.departDate }" pattern="yyyy-MM-dd"/></td>
  		 <td class="addTime"><fmt:formatDate value="${qcBill.addTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
  		 <td class="salerName">${qcBill.orderBill.salerName }</td>
  		 <td class="salerManagerName" >${qcBill.orderBill.salerManagerName }</td>
  		 <td class="salerBusinessUnit">${qcBill.orderBill.salerBusinessUnit }</td>
  		 <td class="salerDep">${qcBill.orderBill.salerDep }</td>
  		 <td class="salerTeam">${qcBill.orderBill.salerTeam }</td>
  		 <td class="qcPerson">${qcBill.qcPerson }</td>
  		 <td class="operate">
  		 <c:if test="${qcBill.state==3 }">
  	      	<a href="javascript:void(0)" onclick="window.open('qc/operateQcBill/${qcBill.id}/toBill','_blank')">质检</a>
  		 </c:if>
  		 <c:if test="${qcBill.state==4&&(loginUser.role.type==2||loginUser.role.type==3)&&fn:contains(loginUser_WCS,'BACK2PROCESSING')}">
				<a href="javascript:void(0)" onclick="back2Processing(${qcBill.id})">返回质检中</a>
			</c:if>
			<c:if test="${qcBill.state==5&&fn:contains(loginUser_WCS,'BACK2PROCESSING')}">
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
