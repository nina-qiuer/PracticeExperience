<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<style type="text/css">
/*搜索框表格样式*/
.clearfix:after {
	content: ".";
	display: block;
	height: 0;
	clear: both;
}

/*覆盖插件默认样式*/
.ui-autocomplete {
	max-height: 100px;
	overflow-y: auto;
	/* prevent horizontal scrollbar */
	overflow-x: hidden;
}

.ui-widget {
	font-family: Microsoft YaHei;
}

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
	
	  $("#searchForm").attr("action", "qc/resDevQcBill/list");
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
	
	$("#assignTo").autocomplete({
	    source: userArr,
	    autoFocus : true
	}).data( "ui-autocomplete" )._renderItem = function( ul, item ) {
		return $( "<li>" )
        .append( "<a>" + item.value + "</a>" )
        .appendTo( ul );
	};
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
	if('${userRealName}' == assignTo){
		
		layer.alert("分配人不能为本人!",{icon: 2});
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
		url : 'qc/resDevQcBill/assignDelPerson',
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

function exportExcel(input){
	
	disableButton(input);
	$.ajax( {
		url : 'qc/resDevQcBill/checkExport',
		data : $('#searchForm').serialize(),
		type : 'post',
		dataType:'json',
		cache : false,
		success : function(result) {
			
			if(result)
			{
		    	if(result.retCode == "0")
				{
		    		$("#searchForm").attr("action","qc/resDevQcBill/exports"); 
		    		$("#searchForm").submit(); 	
				 }else{
					
					 layer.alert(result.resMsg,{icon: 2});
					 enableButton(input);
				}
		     }
		 }
	    }); 
}

//重置搜索框内容：直接使用reset会影响所有form中内容
function resetSearchTable() {
	$('.search :text').val('');
	$('#qualityEventClass').val("");
}

</script>
<title>研发质检列表</title>
</head>
<body >
<form name="searchForm" id="searchForm" method="post" action="" >
<form:hidden path="dto.orderField"/>
<form:hidden path="dto.orderDirect"/>
<div class="accordion">
<h3>研发质检列表</h3> 
<div>
<table width="100%" class="search">
	<tr>
	    <td>质检单号：</td>
    	<td><form:input path="dto.qcId"/></td>
		<td>问题等级：</td>
		<td>
			<form:select path="dto.qualityEventClass" id="qualityEventClass" onchange="searchResetPage()"  style="width:100px">
				<form:option value="" label="全部" />
				<form:option value="S" label="S级" />
				<form:option value="A" label="A级" />
				<form:option value="B" label="B级" />
				<form:option value="C" label="C级" />
				<form:option value="非研发故障" label="非研发故障" />
			</form:select>
		</td>
		<td>报告添加时间：</td>
		<td>
			<form:input path="dto.addTimeFrom"  onfocus="setMaxDate('addTimeTo')" />
			至
			<form:input path="dto.addTimeTo"  onfocus="setMinDate('addTimeFrom')" />
		</td>
		<td colspan="2">
		<c:if test="${dto.state ==3}">
			    <input type="text" id="assignTo" name="assignTo" onfocus="userAutoComplete()"/>
				<input type="button" class="blue" name="assginBtn" value="分配处理人" onclick="assign(this)" />
		</c:if>
		<c:if test="${dto.state !=3}">
			    <input type="hidden" id="assignTo" name="assignTo" value=""  />
				<input type="hidden" class="blue" name="assginBtn" value="分配处理人" onclick="assign(this)" />
		</c:if>
		</td>
	</tr>
	<tr>
		<td>质检人：</td>
	    <td><form:input path="dto.qcPerson"  onfocus="userAutoComplete()"/></td>
		<td>报告完成时间：</td>
		<td>
			<form:input path="dto.finishTimeBgn"  onfocus="setMaxDate('finishTimeEnd')" />
			至
			<form:input path="dto.finishTimeEnd"  onfocus="setMinDate('finishTimeBgn')" />
		</td>	
		<td>故障发生时间：</td>
		<td>
			<form:input path="dto.faultHappenTimeFrom"  onfocus="setMaxDate('faultHappenTimeTo')" />
			至
			<form:input path="dto.faultHappenTimeTo"  onfocus="setMinDate('faultHappenTimeFrom')" />
		</td>
		<td>故障完成时间：</td>
		<td>
			<form:input path="dto.faultFinishTimeFrom"  onfocus="setMaxDate('faultFinishTimeTo')" />
			至
			<form:input path="dto.faultFinishTimeTo"  onfocus="setMinDate('faultFinishTimeFrom')" />
		</td>
		
	</tr>
	<tr>
		<td>质检状态：</td>
		<td >
			<div id="radioset" onclick="searchResetPage()">
				<form:radiobutton path="dto.state" value="-1" label="全部" />
				<form:radiobutton path="dto.state" value="3" label="质检中" />
				<form:radiobutton path="dto.state" value="4" label="已完成" />
				<form:radiobutton path="dto.state" value="5" label="已撤销" />
			</div>
		</td>
		
		<td colspan="6" style="text-align: right">
			<input type="button" class="blue" 	value="发起质检" onclick="openWin('发起研发质检','qc/resDevQcBill/toAdd','850','450')"/>
			<c:if test="${dto.state ==4}">
			<input type="button" class="blue" id ="exports" name="exports" value="导出" onclick="exportExcel(this)" />
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
  	<th><input type="checkbox" id="chkAll" title="全选"></th>
  	<th id="importantFlag" class="importantFlag orderable">重要</th>
	<th class="id">质检单号</th>
	<th class="influenceTime">影响时长(分)</th>
	<th class="qualityEventClass">质量问题等级</th>
	<th class="qcAffairSummary" width="570px">质检事宜概述</th>
	<th class="addPerson">添加人</th>
	<th class="qcPerson">质检人</th>
	<th class="addTime">报告添加时间</th>
	<th class="finishTime">报告完成时间</th>
	<th class="faultHappenTime">故障发生时间</th>
	<th class="faultFinishTime">故障完成时间</th>
	<th >操作</th>
  </tr>
 		<c:forEach items="${dto.dataList}" var="qcBill">
 		 <tr>
 		 <td ><input type="checkbox" autocomplete="off"  name="qcBillIds" value="${qcBill.id }" /></td>
  		 <td class="importantFlag">
			<c:if test="${qcBill.importantFlag ==1}"><img src="res/img/important.png" width="16px" height="16px"/></c:if>
			<c:if test="${qcBill.importantFlag ==0}"><img src="res/img/not_important.png" width="16px" height="16px"/></c:if>
		</td>
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
  		 <td class="shorten35" >${qcBill.qcAffairSummary }</td>
  		 <td class="addPerson">${qcBill.addPerson }</td>
  		 <td class="qcPerson">${qcBill.qcPerson }</td>
		 <td class="addTime" title='<fmt:formatDate value="${qcBill.addTime}" pattern="yyyy-MM-dd HH:mm:ss"/>'><fmt:formatDate value="${qcBill.addTime}" pattern="yyyy-MM-dd"/></td>
		 <td class="finishTime" title='<fmt:formatDate value="${qcBill.finishTime}" pattern="yyyy-MM-dd HH:mm:ss"/>'><fmt:formatDate value="${qcBill.finishTime}" pattern="yyyy-MM-dd"/></td>
  		 <td class="faultHappenTime">${qcBill.faultHappenTime}</td>
		 <td class="faultFinishTime">${qcBill.faultFinishTime}</td>
  		 <td class="operate">
  		 <c:if test="${qcBill.state==3 }">
				<a href="javascript:void(0)" onclick="window.open('qc/resDevQcBill/${qcBill.id}/toBill','_blank')">质检</a>
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
