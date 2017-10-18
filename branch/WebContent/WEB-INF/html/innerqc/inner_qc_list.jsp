<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<script type="text/javascript" src="${CONFIG.res_url}script/DatePickerNew/WdatePicker.js" ></script>
<script type="text/javascript" src="${CONFIG.res_url}script/thickbox-compressed.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}script/jquery/plugin/auto_complete/jquery.autocomplete.js"></script>
<link rel="stylesheet" type="text/css" href="${CONFIG.res_url}script/jquery/plugin/auto_complete/jquery.autocomplete.css"/>
<link href="${CONFIG.res_url}css/thickbox.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!-- tr变黄色 -->
.trbg td{background:#ff9;}
.trbg1 td{background:#87CEFF;}
.listtable tr.yellowbg td{background:#FFFF99}
.listtable tr td.orange{background:orange;}
</style>
<script>
$(function(){
	/*移除thickbox默认样式*/
	$('.thickbox').click(function(){
		$('#TB_closeAjaxWindow').html("<a href='javascript:void(0)' id='TB_closeWindowButton' title='Close'>×</a>");
		$('#TB_closeWindowButton').live('click',function(){
			tb_remove();
		});
	});
	
    // chkAll全选事件
    $("#chkAll").bind("click", function () {
        $("[name = page.iqcIds]:checkbox").attr("checked", this.checked);
    });

    textAutocomplete();

});

function textAutocomplete(){
	var userArr = new Array();
	<c:forEach items="${users}" var="userItem">
		userArr.push({'id' : '${userItem.id}', 'realName' : '${userItem.realName}', 'userName' : '${userItem.userName}', 'name' : '${userItem.realName}${userItem.userName}'});
	</c:forEach>

	nameAutocomplete(userArr, "addPersonName", "addPersonId");
	nameAutocomplete(userArr, "dealPersonName", "dealPersonId");
}

function nameAutocomplete(userArr, personName, personId) {
	$('#' + personName).autocomplete(userArr, {
		max : 10, //列表里的条目数 
		minChars : 0, //自动完成激活之前填入的最小字符 
		width : 120, //提示的宽度，溢出隐藏 
		scrollHeight : 300, //提示的高度，溢出显示滚动条 
		matchContains : true, //包含匹配，就是data参数里的数据，是否只要包含文本框里的数据就显示 
		autoFill : false, //自动填充 
		mustMatch : true,
		dataType : 'json',
		parse: function(data) {
			var rows = [];
			var list = data;
	        for(var i=0; i<list.length; i++){
	        	rows[rows.length] = {
	            data: list[i],
	            value: list[i].name,
	            result: list[i].name
	            };
	        }
	        return rows;
	    },   
		formatItem : function(row, i, max) {
			return row.realName;
		},
		formatMatch : function(row, i, max) {
			return row.name;
		},
		formatResult : function(row) {
			return row.realName;
		}

	}).result(function(event, row, formatted) {
		$('#' + personId).val(row.id);
	});
}

function onResetClicked() {
    $(':input','#search_form').not(':button, :submit, :reset, :hidden')  
    .val('').removeAttr('checked').removeAttr('selected');
    $("#pageNo").val(1);
    $('#addPersonId').val('');
    $('#dealPersonId').val('');
}

function search() {
	var addPersonName = $('#addPersonName').val();
	if ("" == addPersonName) {
		$('#addPersonId').val('');
	}
	
	var dealPersonName = $('#dealPersonName').val();
	if ("" == dealPersonName) {
		$('#dealPersonId').val('');
	}
	
	$("#search_form").attr("action", "inner_qc");
	$("#search_form").submit();
}

function onSearchClicked() {
	$("#pageNo").val(1);
	search();
}

function closeWindow(id) {
	tb_remove();
	if (id > 0) {
		search();
	} else {
		onResetClicked();
		toPage(1);
	}
}

function delInnerQc(id) {
	if (confirm("确定要删除质检单[" + id + "]吗？")) {
		$.ajax({
			type: "POST",
			url: "inner_qc-delete",
			data: {"entity.id":id},
			async: false,
			success: function(data) {
				search();
			} 
		});
	}
}

function assignDealPerson() {
	if($("input[name='page.iqcIds']:checked").length <= 0){
		alert("至少选择一条记录！");
		return false;
	}
	
	if ("" == $('#dealPersonName').val()) {
		alert("处理人不能为空！");
		return false;
	}

	$("#search_form").attr("action", "inner_qc-assignDealPerson");
	$("#search_form").submit();
}

function exports() {
	$("#search_form").attr("action", "inner_qc-exports");
	$("#search_form").submit();
}
</script>
</HEAD>
<BODY>
<div class="top_crumbs">您当前所在的位置：<a href="#">质检管理系统</a>&gt;&gt;<span class="top_crumbs_txt">内部质检列表</span></div>
<form name="form" id="search_form" method="post" action="inner_qc">
<div class="pici_search pd5">
	<div class="pici_search pd5 mb10">
	<label class="mr10">质检单号：<input type="text" size="10" id="iqcId" name="page.iqcId" value="${page.iqcId}"> </label>
	<label class="mr10">单据状态： 
		<select name="page.state" onchange="search()">
		<c:if test="${showFlag !=1 }">
			<option value="">全部</option>
			<option value="0" <c:if test="${'0' == page.state}">selected</c:if>>发起中</option>
			<option value="1" <c:if test="${'1' == page.state}">selected</c:if>>待分配</option>
			<option value="2" <c:if test="${'2' == page.state}">selected</c:if>>处理中</option>
		</c:if>
			<option value="3" <c:if test="${'3' == page.state}">selected</c:if>>已完成</option>
		</select> 
	</label>
	<label class="mr10">质检事宜类型： 
		<select name="page.typeId" onchange="search()">
			<option value="">全部</option>
			<c:forEach items="${typeList}" var="type">
				<option value="${type.id}" <c:if test="${type.id == page.typeId}">selected</c:if>>${type.name}</option>
			</c:forEach>
		</select> 
	</label>
	<label class="mr10">关联单据类型： 
		<select name="page.relBillType" onchange="search()">
			<option value="">全部</option>
			<option value="0" <c:if test="${'0' == page.relBillType}">selected</c:if>>无关联</option>
			<option value="1" <c:if test="${'1' == page.relBillType}">selected</c:if>>订单</option>
			<option value="2" <c:if test="${'2' == page.relBillType}">selected</c:if>>产品</option>
			<option value="3" <c:if test="${'3' == page.relBillType}">selected</c:if>>Jira</option>
		</select> 
	</label>
	<label class="mr10">关联单据编号： <input type="text" size="10" id="relBillNum" name="page.relBillNum" value="${page.relBillNum}"> </label>
	<label class="mr10">申请人： <input type="text" size="10" id="addPersonName" name="page.addPersonName" value="${page.addPersonName}">
		<input type="hidden" size="10" id="addPersonId" name="page.addPersonId" value="${page.addPersonId}"> </label>
	<br>
	发起时间：<input type="text" size="10" name="page.addTimeBgn" id="addTimeBgn" value="${page.addTimeBgn}" 
		   onclick="var addTimeEnd=$dp.$('addTimeEnd');WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,onpicked:function(){addTimeEnd.click();},maxDate:'#F{$dp.$D(\'addTimeEnd\')}'})" readonly="readonly">&nbsp;至&nbsp;
		  <input type="text" size="10" name="page.addTimeEnd" id="addTimeEnd" value="${page.addTimeEnd}" 
		   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'addTimeBgn\')}'})" readonly="readonly"> 　
	<label class="mr10">质检员： 
		<input type="hidden" size="10" id="dealPersonId" name="page.dealPersonId" value="${page.dealPersonId}">
		<input type="text" size="10" id="dealPersonName" name="page.dealPersonName" value="${page.dealPersonName}"> 
	</label>
	<c:if test="${('1' == page.state || '2' == page.state) && userFlag==1}">
	<label class="mr10"><input type="button" value="分配处理人" class="blue" onclick="assignDealPerson()" style="cursor: pointer;"> </label>
	</c:if>
	<label class="mr10"><input type="button" value="查询" class="blue" onclick="onSearchClicked()" style="cursor: pointer;"> </label>
	<label class="mr10"><input type="button" value="重置" class="blue" onclick="onResetClicked()" style="cursor: pointer;"> </label>
    <%-- <c:if test="${showFlag!=1}">
	<label class="mr10">
		<input type="button" value="发起质检" title="发起质检" class="thickbox blue" style="cursor: pointer;"
		alt="inner_qc-toAdd?placeValuesBeforeTB_=savedValues&TB_iframe=true&height=480&width=820&modal=false">
	</label>
	</c:if> --%>
	<!-- <label class="mr10"><input type="button" value="导出" class="blue" style="cursor: pointer;" onclick="exports()"></label> -->
	</div>
</div>
<table class="listtable" width="100%">
<thead>
	<th><input type="checkbox" id="chkAll" title="全选"></th>
	<th>质检单号</th>
	<th>关联单据类型</th>
	<th>关联单据编号</th>
	<th>申请人</th>
	<th>公司损失</th>
	<th>质检事宜类型</th>
	<th>质检事宜概述</th>
	<th>发起时间</th>
	<th>质检员</th>
	<th>状态</th>
	<th>操作</th>
</thead>
<tbody>
	<c:forEach items="${page.iqcList}" var="v"  varStatus="st"> 
	<tr align="center">
		<td><input type="checkbox" name="page.iqcIds" value="${v.id}"></td>
		<td>
		<a href="inner_qc-toReport?entity.id=${v.id}&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=500&width=850" 
			class="thickbox" title="质检单[${v.id}]">${v.id}</a>
		</td>
		<td>
			<c:if test="${0 == v.relBillType}">无关联</c:if>
			<c:if test="${1 == v.relBillType}">订单</c:if>
			<c:if test="${2 == v.relBillType}">产品</c:if>
			<c:if test="${3 == v.relBillType}">Jira</c:if>
		</td>
		<td>
			<c:if test="${1 == v.relBillType}">
				<a href="#" onclick="showOrder(${user.id},'${user.realName}',${v.relBillNum})">${v.relBillNum}</a>
			</c:if>
			<c:if test="${2 == v.relBillType}">${v.relBillNum}</c:if>
			<c:if test="${3 == v.relBillType}">
				<a href="http://jira.tuniu.org/browse/${v.relBillNum}" target="_blank">${v.relBillNum}</a>
			</c:if>
		</td>
		<td>${v.addPersonName}</td>
		<td>${v.lossAmount}</td>
		<td>${v.typeName}</td>
		<td title="${v.qcEventSummary}" align="left">
			<c:choose>
				<c:when test="${fn:length(v.qcEventSummary) > 16}">
					<c:out value="${fn:substring(v.qcEventSummary, 0, 14)}......" />
				</c:when>
				<c:otherwise>
					<c:out value="${v.qcEventSummary}" />
				</c:otherwise>
			</c:choose>
		</td>
		<td><fmt:formatDate value="${v.addTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		<td><c:if test="${v.dealPersonId > 0}">${v.dealPersonName}</c:if></td>
		<td>
			<c:if test="${0 == v.state}"><span style="color: gray">发起中</span></c:if>
			<c:if test="${1 == v.state}"><span style="color: blue">待分配</span></c:if>
			<c:if test="${2 == v.state}"><span style="color: green">处理中</span></c:if>
			<c:if test="${3 == v.state}">已完成</c:if>
		</td>
		<td>
		<c:if test="${showFlag!=1 }">
			<c:if test="${(0 == v.state && user.id == v.addPersonId) || isDevPerson}">
			<input type="button" value="编辑" title="修改质检单[${v.id}]" class="thickbox pd5" style="cursor: pointer;"
				alt="inner_qc-toUpdate?entity.id=${v.id}&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=480&width=820&modal=false">　
			<input type="button" class="pd5" value="删除" onclick="delInnerQc('${v.id}')">
			</c:if>
			<c:if test="${(2 == v.state && user.id == v.dealPersonId) || isDevPerson}">
			<input type="button" class="pd5" value="处理" title="处理质检单[${v.id}]" style="cursor: pointer;"
				onclick="window.open('inner_qc-toBill?entity.id=${v.id}&entity.addPersonId=${v.addPersonId}&entity.dealPersonId=${v.dealPersonId }')">　
			<input type="button" value="退回" title="退回质检单[${v.id}]" class="thickbox pd5" style="cursor: pointer;"
				alt="inner_qc-toBack?entity.id=${v.id}&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=185&width=750&modal=false">
			</c:if>
		</c:if>
		</td>
	</tr>
	</c:forEach>
</tbody>
</table>
<table width="100%">
<tr>
<td><%@include file="/WEB-INF/html/pager2.jsp" %></td>
</tr>
</table>
</form>
</BODY>
