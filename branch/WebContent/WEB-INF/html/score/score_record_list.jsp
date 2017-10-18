<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<script type="text/javascript" src="${CONFIG.res_url}script/DatePickerNew/WdatePicker.js" ></script>
<script type="text/javascript" src="${CONFIG.res_url}script/thickbox-compressed.js"></script>
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
        $("[name = page.srIds]:checkbox").attr("checked", this.checked);
    });

    blurJiraNum();
});

function clickJiraNum() {
	var jiraNum = $("#jiraNum").val();
	if ("格式：BOSS-3598" == jiraNum) {
    	$("#jiraNum").val("");
    	$("#jiraNum").css("color", "#000000");
    }
}

function blurJiraNum() {
	var jiraNum = $("#jiraNum").val();
	if ("" == jiraNum) {
    	$("#jiraNum").val("格式：BOSS-3598");
    	$("#jiraNum").css("color", "#757575");
    }
}

function checkParam() {
	clickJiraNum();
}

function onResetClicked() {
    $(':input','#search_form').not(':button, :submit, :reset, :hidden')  
    .val('').removeAttr('checked').removeAttr('selected');
    $("#pageNo").val(1);
    blurJiraNum();
}

function changeTwo(pid, optStr, claStr) {
	$(optStr).each(function() {
		if ($(this).val() > 0) {
			$(this).hide();
			$(this).attr("disabled", true);
		} else {
			$(this).attr("selected", "selected");
		}
	 });
	if (pid > 0) {
		$(claStr + pid).each(function() {
			$(this).show();
			$(this).attr("disabled", false);
		 });
	}
}

function changeDepTwo(pid) {
	changeTwo(pid, "#depTwo option", ".dep_");
	$("#search_form").submit();
}

function search() {
	
	clickJiraNum();
	$("#search_form").attr("action", "score_record");
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

function delScoreRecord(id) {
	if (confirm("确定要删除记分单[" + id + "]吗？")) {
		$.ajax({
			type: "POST",
			url: "score_record-delete",
			data: {"entity.id":id},
			async: false,
			success: function(data) {
				search();
			} 
		});
	}
}

function batchDel() {
	if($("input[name='page.srIds']:checked").length <= 0){
		alert("至少选择一条记录");
		return false;
	} else if (confirm("确定要删除这些记分单吗？")) {
		$.ajax({
			type: "POST",
			url: "score_record-batchDel",
			data: $('#search_form').serialize(),
			async: false,
			success: function(data) {
				search();
			}
		});
	}
}

function exports() {
	$("#search_form").attr("action", "score_record-exports");
	$("#search_form").submit();
}
</script>
</HEAD>
<BODY>
<div class="top_crumbs">您当前所在的位置：<a href="#">记分管理系统</a>&gt;&gt;<span class="top_crumbs_txt">记分管理列表</span></div>
<form name="form" id="search_form" method="post" action="score_record" onsubmit="checkParam()">
<div class="pici_search pd5">
	<div class="pici_search pd5 mb10">
	质检日期：<input type="text" size="10" name="page.qcDateBgn" id="qcDateBgn" value="${page.qcDateBgn}" 
		   onclick="var qcDateEnd=$dp.$('qcDateEnd');WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,onpicked:function(){qcDateEnd.click();},maxDate:'#F{$dp.$D(\'qcDateEnd\')}'})" readonly="readonly">&nbsp;至&nbsp;
		  <input type="text" size="10" name="page.qcDateEnd" id="qcDateEnd" value="${page.qcDateEnd}" 
		   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'qcDateBgn\')}'})" readonly="readonly" />
	<label class="mr10">　记分单号： <input type="text" size="10" id="srId" name="page.srId" value="${page.srId}"> </label>
	<label class="mr10">质检组： 
		<select name="page.qcTeamId" onchange="search()">
		<option value=""></option>
		<c:forEach items="${qcTeams}" var="team">
			<option value="${team.id}" title="${team.persons}" <c:if test="${page.qcTeamId == team.id}">selected</c:if>>${team.teamName}</option>
		</c:forEach>
		</select> 
	</label>
	<label class="mr10">质检人： <input type="text" size="10" id="qcPerson" name="page.qcPerson" value="${page.qcPerson}" /> </label>
	<label class="mr10">订单号： <input type="text" size="10" id="orderId" name="page.orderId" value="${page.orderId}" /> </label>
	<label class="mr10">线路号： <input type="text" size="10" id="routeId" name="page.routeId" value="${page.routeId}" /> </label><br>
	<label class="mr10">Jira号： <input type="text" size="15" id="jiraNum" name="page.jiraNum" value="${page.jiraNum}" onclick="clickJiraNum()" onblur="blurJiraNum()"> </label>
	<label class="mr10">一级部门： 
		<select name="page.depId1" onchange="changeDepTwo(this.value)">
		<option value="0"></option>
		<c:forEach items="${departments}" var="dep">
			<c:if test="${dep.fatherId == 1}">
				<option value="${dep.id}" <c:if test="${page.depId1 == dep.id}">selected</c:if>>${dep.depName}</option>
			</c:if>
		</c:forEach>
		</select> 
	</label>
	<label class="mr10">二级部门： 
		<select name="page.depId2" id="depTwo" onchange="$('#search_form').submit()">
		<option value="0"></option>
		<c:forEach items="${departments}" var="dep">
			<c:if test="${dep.fatherId > 1}">
			<c:choose>
				<c:when test="${dep.fatherId == page.depId1}">
					<option value="${dep.id}" class="dep_${dep.fatherId}" <c:if test="${page.depId2 == dep.id}">selected</c:if>>${dep.depName}</option>
				</c:when>
				<c:otherwise>
					<option value="${dep.id}" class="dep_${dep.fatherId}" style="display: none;" disabled="disabled">${dep.depName}</option>
				</c:otherwise>
			</c:choose>
			</c:if>
		</c:forEach>
		</select> 
	</label>
	<label class="mr10">记分对象1： <input type="text" size="10" id="scoreTarget1" name="page.scoreTarget1" value="${page.scoreTarget1}" /> </label>
	<label class="mr10">记分对象2： <input type="text" size="10" id="scoreTarget2" name="page.scoreTarget2" value="${page.scoreTarget2}" /> </label>
	<label class="mr10"><input type="button" value="查询" class="blue" onclick="onSearchClicked()" style="cursor: pointer;"> </label>
	<label class="mr10"><input type="button" value="重置" class="blue" onclick="onResetClicked()" style="cursor: pointer;"> </label>
	<label class="mr10">
	<c:choose>
		<c:when test="${user.depId==973 || user.id==5175 || user.id==7167 || user.id==4405}">
		<input type="button" value="新增" title="新增记分单" class="thickbox blue" style="cursor: pointer;"
		alt="score_record-toAdd?placeValuesBeforeTB_=savedValues&TB_iframe=true&height=445&width=800&modal=false"> 
		</c:when>
		<c:otherwise>
		<input type="button" value="新增" title="新增记分单" class="thickbox blue" style="cursor: pointer;"
		alt="score_record-toAdd?placeValuesBeforeTB_=savedValues&TB_iframe=true&height=380&width=800&modal=false"> 
		</c:otherwise>
	</c:choose>
	</label>
	<c:if test="${isManager == 1}">
	<label class="mr10"><input type="button" value="批量删除" onclick="batchDel()" class="blue" style="cursor: pointer;"> </label>
	</c:if>
	<label class="mr10">
	<input type="button" value="导入" title="记分单导入" class="thickbox blue" style="cursor: pointer;"
		alt="score_record-toImport?placeValuesBeforeTB_=savedValues&TB_iframe=true&height=85&width=400&modal=false"> 
	</label>
	<!-- <label class="mr10"><input type="button" value="导出" class="blue" style="cursor: pointer;" onclick="exports()"></label> -->
	</div>
</div>
<table class="listtable" width="100%">
<thead>
	<th><input type="checkbox" id="chkAll" title="全选"></th>
	<th>记分单号</th>
	<th>订单号 /线路号/jira号</th>
	<th>一级部门</th>
	<th>二级部门</th>
	<th>记分性质</th>
	<th>记分类型</th>
	<th>记分对象1</th>
	<th>记分值1</th>
	<th>记分对象2</th>
	<th>记分值2</th>
	<th>总分</th>
	<th>质检人</th>
	<th>质检日期</th>
	<c:if test="${isManager == 1}">
	<th>操作</th>
	</c:if>
</thead>
<tbody>
	<c:forEach items="${page.srList}" var="v"  varStatus="st"> 
	<tr align="center">
		<td><input type="checkbox" name="page.srIds" value="${v.id}"></td>
		<td>
		<a href="score_record-toInfo?entity.id=${v.id}&keepThis=true&TB_iframe=true&height=300&width=600" class="thickbox" title="记分单[${v.id}]">
		${v.id }</a>
		</td>
		<td>
			<c:choose>
				<c:when test="${v.orderId > 0}">
					<a href="#" onclick="showOrder(${user.id},'${user.realName}',${v.orderId})">${v.orderId}</a>
				</c:when>
				<c:when test="${v.routeId > 0}">${v.routeId}</c:when>
				<c:otherwise>
					<a href="http://jira.tuniu.org/browse/${v.jiraNum}" target="_blank">${v.jiraNum}</a>
				</c:otherwise>
			</c:choose>
		</td>
		<td>${v.depName1 }</td>
		<td>${v.depName2 }</td>
		<td>${v.scoreTypeName1}</td>
		<td>${v.scoreTypeName2}</td>
		<td>${v.scoreTarget1}</td>
		<td>${v.scoreValue1}</td>
		<td>${v.scoreTarget2}</td>
		<td>${v.scoreValue2}</td>
		<td>${v.scoreValue1 + v.scoreValue2}</td>
		<td>${v.qcPerson}</td>
		<td><fmt:formatDate value="${v.qcDate}" pattern="yyyy-MM-dd"/></td>
		<c:if test="${isManager == 1}">
		<td>
			<c:choose>
				<c:when test="${user.depId==973 || user.id==5175 || user.id==7167 || user.id==4405}">
				<input type="button" value="编辑" title="修改记分单[${v.id}]" class="thickbox" style="cursor: pointer;"
				alt="score_record-toUpdate?entity.id=${v.id}&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=445&width=800&modal=false">　
				</c:when>
				<c:otherwise>
				<input type="button" value="编辑" title="修改记分单[${v.id}]" class="thickbox" style="cursor: pointer;"
				alt="score_record-toUpdate?entity.id=${v.id}&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=380&width=800&modal=false">　
				</c:otherwise>
			</c:choose>
			<input type="button" value="删除" onclick="delScoreRecord('${v.id}')">
		</td>
		</c:if>
	</tr>
	</c:forEach>
</tbody>
</table>
<table width="100%">
<tr>
<td><%@include file="/WEB-INF/html/pager2.jsp" %></td>
<td align="right" valign="bottom"">当前总分为：${page.totalValue}　　</td>
</tr>
</table>
</form>
</BODY>
