<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>投诉改进报告列表</title>
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
/*覆盖通用数据表格样式*/
.listtable th {
	display: none;
}

.listtable td {
	display: none;
}

/*basic*/
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
/*字段配置样式*/
#fieldListDiv {
	font-family: Microsoft YaHei;
	font-size: 12px;
	background: ##E1E4E6;
	padding: 20px 0;
}

#fieldListDiv ul {
	list-style: none;
	margin: 0px;
	float: left;
	clear: both
}

#fieldListDiv ul li {
	float: left;
	margin: 0 10px;
	display: block;
	line-height: 28px
}

#fieldListDiv ul li:hover {
	color: red;
}
</style>
<script  type="text/javascript">
var userArr = new Array();
var realNameArr = new Array();
$(document).ready(function() {
	$(".accordion").each(function() {
		$(this).accordion({
	    	collapsible: true,
	    	heightStyle: "content"
	    });
	});
	
	var fields =new Array();
	
	<c:forEach items="${dto.fields}" var="field">
		fields.push('${field}');
	</c:forEach>
	for (var i=0;i<fields.length;i++)
	{
		$("#f_" + fields[i]).click();
	}

	$('#fieldListDiv :checkbox').each(function() {
		$(this).click(function() {
			$('.' + this.id.substring(2)).toggle();
		});
		if (this.checked) {
			$('.' + this.id.substring(2)).toggle();
		}
	});

	$('#fieldListDiv').toggle();
	$('.listtable th:first').toggle();
	$('.listtable tr').find('td:first').toggle();
	$('.listtable th:last').toggle();
	$('.listtable tr').find('td:last').toggle();
	
	$('#radioset').buttonset();
	$('#radioset').click(function() {
		searchResetPage();
	});
	
	checkBoxClick();
});

/**
 * 点击全选按钮 
 */
function checkBoxClick(){
	$('#chkAll').click(function() {
		var flag = this.checked;
		$('.listtable td :checkbox[name = impBillIds]').each(function() {
			this.checked = flag;
		});
	});
}

function search(){
	$("#searchForm").attr("action", "qs/cmpImprove/list");
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
			url : "common/user/getUserNamesInJSON",
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
		$("#handlePersonA").autocomplete({
		    source: userArr,
		    autoFocus : true
		}).data( "ui-autocomplete" )._renderItem = function( ul, item ) {
			return $( "<li>" )
	        .append( "<a>" + item.value + "</a>" )
	        .appendTo( ul );
		};
		
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
	if($.trim(assignTo) == ''){
		layer.alert("请输入分配人!",{icon: 2});
		return  false; 
	}
	for(var i=0;i<realNameArr.length;i++){
		if(assignTo == realNameArr[i]){
			isExists = true;
			break;
		}
	}
	if(isExists == false){
		layer.alert("分配人不存在!",{icon: 2});
		return  false; 
	}
	
	if ($(':checkbox[name="impBillIds"]:checked').length <= 0) {
		layer.alert("请至少选择一个改进报告！", {
			icon : 2
		});
		return false;
	}
	disableButton(input);
	$.ajax( {
		url : 'qs/cmpImprove/assignDelPerson',
		data : $('#searchForm').serialize(),
		type : 'post',
		dataType:'json',
		cache : false,
		success : function(result) {
			enableButton(input);
			if(result){
		    	if(result.retCode == "0"){
		    		 search();
				 }else{
					 layer.alert(result.resMsg,{icon: 2});
				}
		     }
		 }
	    });
}

function finishImprove(impId){
	if(window.confirm( "确定完结吗？ ")){
		$.ajax({
			url : "qs/cmpImprove/finishImproveBill",
			data : {
				"id" : impId
			},
			type : 'post',
			dataType:'json',
			success : function(result) {
		    	if(result.retCode == "0"){
					search();
		    	}else{
					layer.alert(result.resMsg, {icon: 2});
				}
			}
		});
	}
}
//重置搜索框内容：直接使用reset会影响所有form中内容
function resetSearchTable() {
	$('.search :text').val('');
}

function searchReaload(){//添加或更新报告成功后，重新加载页面  为了和质检单中操作区分
	search();
}
</script>
</head>
<body>
	<form name="searchForm" id="searchForm" method="post" action="" >
		<div class="accordion">
		<h3>投诉改进报告列表</h3> 
		<div align="right">
		<table width="100%" class="search">
			<tr>
			    <td>改进报告单号：</td>
		    	<td><form:input path="dto.id"/></td>
		    	<td>投诉单号：</td>
		    	<td><form:input path="dto.cmpId"/></td>
				<td>添加时间：</td>
				<td>
					<form:input path="dto.addTimeBgn"  onfocus="setMaxDate('addTimeEnd')" />
					至
					<form:input path="dto.addTimeEnd"  onfocus="setMinDate('addTimeBgn')" />
				</td>
				<td>责任人：</td>
				<td>
					<form:input path="dto.impPerson"/>
				</td>
			</tr>
			<tr>
				<td>订单编号：</td>
		    	<td><form:input path="dto.ordId"/></td>
		    	<td>产品编号：</td>
    			<td><form:input path="dto.prdId"/></td>
		    	<td>预计改进完成时间：</td>
				<td>
					<form:input path="dto.improveFinTimeBgn"  onfocus="setMaxDate('improveFinTimeEnd')" />
					至
					<form:input path="dto.improveFinTimeEnd"  onfocus="setMinDate('improveFinTimeBgn')" />
				</td>
				<td>处理人：</td>
				<td>
					<form:input path="dto.handlePerson" id="handlePersonA" onfocus="userAutoComplete()"/>
				</td> 
				
			</tr>
			<tr>
				<td>责任人是否有责：</td>
				<td>
					<form:select path="dto.isRespFlag" onchange="searchResetPage()" style="width:100">
							<form:option value="" label="全部" />
							<form:option value="0" label="有责" />
							<form:option value="1" label="无责" />
							
					</form:select>
				</td>
				<td>供应商是否有责：</td>
				<td>
					<form:select path="dto.isAgencyRespFlag" onchange="searchResetPage()" style="width:100">
							<form:option value="" label="全部" />
							<form:option value="0" label="有责" />
							<form:option value="1" label="无责" />
					</form:select>
				</td>
				<td></td>
				<td colspan="2" style="text-align: left;">
					<c:if test="${(dto.state == 1 || dto.state == 3) && fn:contains(loginUser_WCS,'IMPROVE_BILL_ASSIGN')}"> 
						<input type="text" id="assignTo" name="assignTo" onfocus="userAutoComplete()"/>
						<input type="button" class="blue" name="assginBtn" value="分配处理人" onclick="assign(this)" />
					</c:if>
				</td>
			</tr>
			<tr>
				<td>报告状态：</td>
				<td colspan="2">
					<div id="radioset" >
						<form:radiobutton path="dto.state" value="-1" label="全部" />
						<form:radiobutton path="dto.state" value="1" label="待定责" />
						<form:radiobutton path="dto.state" value="2" label="处理中" />
						<form:radiobutton path="dto.state" value="3" label="待确认" />
						<form:radiobutton path="dto.state" value="4" label="已完结" />
					</div>
				</td>
				<td colspan="6" style="text-align: right;">
					<c:if test="${fn:contains(loginUser_WCS,'IMPROVE_BILL_CREATE')}">
						<input type="button" class="blue" 	value="发起改进报告" onclick="openWin('发起改进报告','qs/cmpImprove/toAdd',820,620)"/>
					</c:if>
					<input type="button" class="blue" value="字段选取" onclick="$('#fieldListDiv').toggle()" />
					<input type="button" class="blue" 	value="查询" onclick="searchResetPage()"/>
				    <input type="button" class="blue" value="重置" onclick="resetSearchTable()"/>
				</td>
			</tr>
		</table>
		</div>
		</div>
		<div id="fieldListDiv" class="clearfix">
	<ul>
		<li><strong>报告字段：</strong></li>
		<li><label><input type="checkbox" name="fields"
				value="id" id="f_id">改进单号</label></li>
		<li><label><input type="checkbox" name="fields"
				value="prdId" id="f_prdId">产品编号</label></li>
		<li><label><input type="checkbox" name="fields"
				value="orderId" id="f_orderId">订单编号</label></li>
		<li><label><input type="checkbox" name="fields"
				id="f_addTime" value="addTime">添加时间</label></li>
		<li><label><input type="checkbox" name="fields"
				id="f_handleEndTime" value="handleEndTime">处理到期时间</label></li>
		<li><label><input type="checkbox" name="fields"
				value="impPerson" id="f_impPerson">责任人</label></li>
		<li><label><input type="checkbox" name="fields"
				id="f_handlePerson" value="handlePerson">处理人</label></li>
		<li><label><input type="checkbox" name="fields"
				value="addPerson" id="f_addPerson">添加人</label></li>
		<li><label><input type="checkbox" name="fields"
				id="f_state" value="state">状态</label></li>
		<li><label><input type="checkbox" name="fields"
				value="cmpAffair" id="f_cmpAffair">投诉事宜</label></li>
		<li><label><input type="checkbox" name="fields"
				value="improvePoint" id="f_improvePoint">改进点</label></li>
		<li><label><input type="checkbox" name="fields"
				id="f_isRespFlag" value="isRespFlag">责任人是否有责</label></li>
		<br/>
		<li><label>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</label></li>
		<li><label><input type="checkbox" name="fields"
				id="f_otherPerson" value="otherPerson">其他责任人</label></li>
		<li><label><input type="checkbox" name="fields"
				id="f_otherAgencyName" value="otherAgencyName">责任供应商</label></li>
		<li><label><input type="checkbox" name="fields"
				id="f_improveMethod" value="improveMethod">改进措施</label></li>
		<li><label><input type="checkbox" value="improveFinTime"
				name="fields" id="f_improveFinTime">预计改进完成时间</label></li>
		<li><label><input type="checkbox" name="fields"
				id="f_improveResult" value="improveResult">改进结果</label></li>
		<li><label><input type="checkbox" name="fields"
				id="f_remark" value="remark">备注</label></li>
		
	</ul>
	<ul>
		<li><strong>投诉字段：</strong></li>
		<li><label><input type="checkbox" name="fields"
				value="cmpId" id="f_cmpId">投诉单号</label></li>
		<li><label><input type="checkbox" name="fields"
				value="cmpLevel" id="f_cmpLevel">投诉级别</label></li>
		<li><label><input type="checkbox" name="fields"
			value="cmpAddTime" id="f_cmpAddTime">投诉时间</label></li>
		<li><label><input type="checkbox" name="fields"
				value="comeFrom" id="f_comeFrom">投诉来源</label></li>
	</ul>
</div>
		
		<table class="listtable">
		  	<tr>
		  	<th><input type="checkbox" id="chkAll" title="全选"></th>
			<th class="id">改进报告单号</th>
			<th class="cmpId">投诉单号</th>
			<th class="orderId">订单编号</th>
			<th class="prdId">产品编号</th>
			<th class="addTime">添加时间</th>
			<th class="handleEndTime">处理到期时间</th>
			<th class="cmpAddTime">投诉时间</th>
			<th class="cmpLevel">投诉等级</th>
			<th class="comeFrom">投诉来源</th>
			<th class="impPerson">责任人</th>
			<th class="handlePerson">处理人</th>
			<th class="addPerson">添加人</th>
			<th class="state">状态</th>
			<th class="cmpAffair">投诉事宜</th>
			<th class="improvePoint">改进点</th>
			<th class="isRespFlag">责任人是否有责</th>
			<th class="otherPerson">其他责任人</th>
			<th class="otherAgencyName">责任供应商</th>
			<th class="improveMethod">改进措施</th>
			<th class="improveFinTime">预计改进完成时间</th>
			<th class="improveResult">改进结果</th>
			<th class="remark">备注</th>
			<th class="operate">操作</th>
		  </tr>
		 <c:forEach items="${dto.dataList}" var="impBill">
		 	<tr>
		 		<td ><input type="checkbox" autocomplete="off"  name="impBillIds" value="${impBill.id }"/></td>
		  		<td class="id"><a href="javascript:void(0)" onclick="openWin('投诉改进报告[${impBill.id}]','qs/cmpImprove/${impBill.id}/impReport',720,480)">${impBill.id}</a></td>
		  		<td class="cmpId">${impBill.cmpId}</td>
		  		<td class="orderId">${impBill.ordId }</td>
		  		<td class="prdId">${impBill.prdId }</td>
		  		<td class="addTime"><fmt:formatDate value="${impBill.addTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		  		<c:set var="nowDate" value="<%=System.currentTimeMillis()%>"></c:set>
				<c:if test="${nowDate - impBill.handleEndTime.time >= 0 && impBill.state == 2}">
		  			<td class="handleEndTime" style="color:red"><fmt:formatDate value="${impBill.handleEndTime}" pattern="yyyy-MM-dd"/></td>
		  		</c:if>
		  		<c:if test="${nowDate - impBill.handleEndTime.time < 0 || impBill.state != 2}">
		  			<td class="handleEndTime"><fmt:formatDate value="${impBill.handleEndTime}" pattern="yyyy-MM-dd"/></td>
		  		</c:if>
		  		<td class="cmpAddTime"><fmt:formatDate value="${impBill.cmpAddTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		  		<td class="cmpLevel">${impBill.cmpLevel }</td>
		  		<td class="comeFrom">${impBill.comeFrom }</td>
		  		<td class="impPerson">${impBill.impPerson }</td>
		  		<td class="handlePerson">${impBill.handlePerson }</td>
		  		<td class="addPerson">${impBill.addPerson }</td>
		  		<td class="state">${impBill.stateStr}</td>
		  		<td class="shorten18 cmpAffair">${impBill.cmpAffair }</td>
		  		<td class="shorten18 improvePoint" >${impBill.improvePoint }</td>
		  		<td class="isRespFlag">
					<c:if test="${impBill.isRespFlag == 0 }">
						有责
					</c:if>
					<c:if test="${impBill.isRespFlag == 1 }">
						无责
					</c:if>
				</td>
				<td class="otherPerson">${impBill.otherPerson }</td>
				<td class="otherAgencyName">${impBill.otherAgencyName }</td>
				<td class="shorten18 improveMethod">${impBill.improveMethod }</td>
				<td class="improveFinTime"><fmt:formatDate value="${impBill.improveFinTime}" pattern="yyyy-MM-dd"/></td>
				<td class="shorten18 improveResult">${impBill.improveResult }</td>
				<td class="shorten18 remark">${impBill.remark}</td>
				
		  		<td class="operate">
		  			<c:if test="${impBill.state == 1 && fn:contains(loginUser_WCS,'IMPROVE_BILL_OPERAT')}">
		  				<input type="button" class="blue" value="定责"  style="cursor: pointer;"
			 				onclick="openWin('更新改进报告', 'qs/cmpImprove/${impBill.id}/toUpdate', 820, 520)">
		  			</c:if>
		  			<c:if test="${(impBill.state == 1 || impBill.state == 3) && fn:contains(loginUser_WCS,'IMPROVE_BILL_OPERAT')}">
		  				<input type="button" class="blue" value="完结"  style="cursor: pointer;" onclick="finishImprove(${impBill.id})">
		  		 	</c:if>
		  		 	<input title="查看改进报告" class="blue" type="button" value="同投诉单下报告查看" style="cursor: pointer;"
				onclick="openWin('查看改进报告', 'qs/cmpImprove/${impBill.cmpId}/reportWithCmpId', 850, 520)"/>
		  		</td>
		 	</tr>
		   </c:forEach>
		</table>
		<%@include file="/WEB-INF/jsp/common/pager.jsp"%>
	</form>
</body>
</html>