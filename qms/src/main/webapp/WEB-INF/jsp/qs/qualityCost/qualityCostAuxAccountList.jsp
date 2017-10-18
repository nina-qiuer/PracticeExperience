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
.listtable .orderable {
	background: #DFEAFB url('res/img/line.png') no-repeat right 5px  center;
	text-align: right;
	padding-right: 26px;
}
</style>
<script  type="text/javascript">
var depArr = new Array();
var jobArr = new Array();
var qptArr = new Array();
var realNameArr = new Array();
var userArr = new Array();
$(document).ready(function() {
	
	$(".accordion").each(function() {
		$(this).accordion({
	    	collapsible: true,
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
			ispId:{
				
				 digits:true,
	              min:0
			}
        },
        messages:{
        	qcId:{digits:"请输入整数"},
        	ordId:{digits:"请输入整数"},
        	ispId:{digits:"请输入整数"}
        }
		
	});
	$('.orderable').click(function() {
		//取orderField
		var orderField = $('#orderField').val();
		//取orderDirect
		var orderDirect = $('#orderDirect').val();
		//如果orderField和当前点击的id一样，则orderDirect+1%3
		var  clickId = $(this).attr('id');
		if(clickId == orderField) {
			orderDirect = (orderDirect+1)%3;
		}else{//如果orderField和当前点击的id不一样，则orderDirect=1
			orderDirect = 1;
		}
		
		$('#orderField').val(clickId);
		$('#orderDirect').val(orderDirect);
		
		//提交
		searchResetPage();
	});
	dealOrderStyle();
	
});

function dealOrderStyle(){
	var orderFiled = $('#orderField').val();
	var orderDirect = $('#orderDirect').val();
	if(orderDirect==1){
		$('.listtable th#'+orderFiled).css('background',"#DFEAFB url('res/img/down.png') no-repeat right 5px center");
	}else if (orderDirect ==2){
		$('.listtable th#'+orderFiled).css('background',"#DFEAFB url('res/img/up.png') no-repeat right 5px center");
	}
}
function search() {
	
	  $("#searchForm").attr("action", "qs/costAuxAccount/list");
	  $("#searchForm").submit();

}
function depAutoComplete() {
	
	if (depArr.length > 0) {
		return;
	} else {
		$.ajax({
			type : "POST",
			url : "qc/qcBill/getDepNames",
			success : function(data) {
				for (var i = 0; i < data.length; i++) {
					depArr.push(data[i]);
				}
			}
		});
	
	$("#depName").autocomplete({
	    source: depArr,
	    autoFocus : true
	});
	
	}
}

function depExists(input){
	  
	var isExists = false;
	var depName = input.value;
	if($.trim(depName)!=''){
		for(var i=0;i<depArr.length;i++){
			
			if(depName == depArr[i]){
				
				isExists =true;
			}
		}
		if(isExists==false){
			
			//   layer.alert("责任部门不存在",{icon: 2});	
		       $(input).val("");
		       return false;
		}
	}
}
function qptAutoComplete() {
	
	if (qptArr.length > 0) {
		return;
	} else {
		$.ajax({
			type : "POST",
			url : "qs/costLedger/getCostAccount",
			success : function(data) {
				for (var i = 0; i < data.length; i++) {
					qptArr.push(data[i]);
				}
			}
		});
	
	$("#qptName").autocomplete({
	    source: qptArr,
	    autoFocus : true
	});
 }
}
function qptExists(input){
	  
	var isExists = false;
	var qptName = input.value;
	if($.trim(qptName)!=''){	
		for(var i=0;i<qptArr.length;i++){
			
			if(qptName == qptArr[i]){
				
				isExists =true;
			}
		}
		if(isExists==false){
			
			 //  layer.alert("成本科目不存在",{icon: 2});	
		       $(input).val("");
		       return false;
		}
	}
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
	
	$("#dealPersonName").autocomplete({
	    source: userArr,
	    autoFocus : true
	}).data( "ui-autocomplete" )._renderItem = function( ul, item ) {
		return $( "<li>" )
        .append( "<a>" + item.value + "</a>" )
        .appendTo( ul );
	};
	}
}

function setMaxDate(id){
	WdatePicker({maxDate:'#F{$dp.$D(\''+id+'\')}'});
}

function setMinDate(id){
	WdatePicker({minDate:'#F{$dp.$D(\''+id+'\')}'});
}

function resetSearchTable(){
	
	$('.search :text').val('');
}
</script>
<title>质量成本辅助账列表</title>
</head>
<body>
<form name="searchForm" id="searchForm" method="post" action="">
<form:hidden path="dto.orderField"/>
<form:hidden path="dto.orderDirect"/>
	  <div class="accordion">
	   <h3>质量成本辅助账列表</h3> 
	   <div align="right">
			<table width="90%" class="search">
				<tr>
				    <td>责任单号：</td>
					<td><form:input path="dto.ispId"/></td>
					<td>责任部门 :</td>
					<td>
					  <form:input path="dto.depName" style="width:250px" onfocus="depAutoComplete()" onblur="depExists(this)" /> 
					</td>
					<td>订单号：</td>
					<td><form:input path="dto.ordId"/></td>
					<td>添加时间：</td>
					<td>
						<form:input path="dto.addTimeBgn"  onfocus="setMaxDate('addTimeEnd')" />
						 至
						<form:input path="dto.addTimeEnd"  onfocus="setMinDate('addTimeBgn')" />
					</td>
				</tr>
				<tr>
			   		<td>质检单号：</td>
					<td><form:input path="dto.qcId"/></td>
					<td>成本科目:</td>
					<td>
					   <form:input path="dto.qptName" style="width:250px" onfocus="qptAutoComplete()"  onblur="qptExists(this)" /> 
					</td>
					<td>处理人：</td>
					<td>
						<form:input path="dto.dealPersonName"  onfocus="userAutoComplete()"/>　
					</td>
					<td></td>
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
				<th class="ispId">责任单号</th>
				<th class="ordId">订单号</th>
				<th class="cmpId">投诉单号</th>
				<th class="qcId">质检单号</th>
			    <th class="firstCostAccount">一级成本科目</th>
				<th class="twoCostAccount">二级成本科目</th>
		   	    <th class="threeCostAccount">三级成本科目</th>
				<th class="firstDepName">一级责任部门</th>
				<th class="twoDepName">二级责任部门</th>
				<th class="threeDepName">三级责任部门</th>
				<th class="respRate">责任占比</th>
				<th id="qualityCost" class="qualityCost orderable">质量成本</th>
				<th class="dealPersonName">处理人</th>
				<th class="addTime">添加时间</th>
	   		 </tr>
	   		<c:forEach items="${dto.dataList}" var="qualityCost">
	   		 <tr>
		   		 <td class="ispId">${qualityCost.ispId }</td>
		   		 <td class="orderId">${qualityCost.ordId }</td>
		   		 <td class="cmpId">${qualityCost.cmpId }</td>
		   		 <td class="qcId">
		   		   <c:if test="${qualityCost.qcId!=0}">
		   		 	<a href="javascript:void(0)" onclick="openWin('质检报告','qc/qcBill/${qualityCost.qcId}/qcReport',1000,520)">${qualityCost.qcId}</a>
		   		    </c:if>
		   		    <c:if test="${qualityCost.qcId==0}">
		   		     	 ${qualityCost.qcId}
		   		     </c:if>
		   		 </td>
		   		 
		   		 <td class="firstCostAccount">${qualityCost.firstCostAccount }</td>
		   		 <td class="twoCostAccount">${qualityCost.twoCostAccount }</td>
		   		 <td class="threeCostAccount">${qualityCost.threeCostAccount }</td>
		   		 <td class="firstDepName">${qualityCost.firstDepName }</td>
		   		 <td class="twoDepName">${qualityCost.twoDepName }</td>
		   		 <td class="threeDepName">${qualityCost.threeDepName }</td>
		   		 <td class="respRate">${qualityCost.respRate}%</td>
		   		 <td class="qualityCost">${qualityCost.qualityCost }</td>
		   		 <td class="dealPersonName">${qualityCost.dealPersonName }</td>
		   		 <td class="addTime"><fmt:formatDate value="${qualityCost.addTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	   		 </tr>
	        </c:forEach>
	  </table>
 	<%@include file="/WEB-INF/jsp/common/pager.jsp"%>
</form>
</body>
</html>
