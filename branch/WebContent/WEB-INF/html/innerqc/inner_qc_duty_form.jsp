<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<script type="text/javascript" src="${CONFIG.res_url}script/jquery/plugin/auto_complete/jquery.autocomplete.js"></script>
<link rel="stylesheet" type="text/css" href="${CONFIG.res_url}script/jquery/plugin/auto_complete/jquery.autocomplete.css"/>

<script type="text/javascript">
<!--
$(document).ready(function(){
	var resultInfo = '${resultInfo}';
	if ("Success" == resultInfo) {
		var questionId = "${entity.questionId}";
		self.parent.refreshIfm2_x(questionId);
		self.parent.easyDialog.close();
	}
	
    $("#form").validate();
    textAutocomplete();
});

function textAutocomplete(){
	var userArr = new Array();
	<c:forEach items="${users}" var="userItem">
		userArr.push({'id' : '${userItem.id}', 'realName' : '${userItem.realName}', 'userName' : '${userItem.userName}', 'name' : '${userItem.realName}${userItem.userName}'});
	</c:forEach>

    $('#respPersonName').autocomplete(userArr, {
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
		$('#respPersonId').val(row.id);
		//$('#respPersonName').val(row.realName);
	});
}

function doSubmit(id) {
	var fineAmount = $("#fineAmount").val();
	if ('' == fineAmount) {
		$("#fineAmount").val(0);
	}
	
	if (id > 0) {
		$('#form').attr("action", "inner_qc_duty-update");
		$('#form').submit();
	} else {
		$('#form').attr("action", "inner_qc_duty-add");
		$('#form').submit();
	}
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
}
//-->
</script>
</HEAD>
<BODY>
<form name="form" id="form" method="post" action="" enctype="multipart/form-data">
<input type="hidden" name="entity.id" value="${entity.id }">
<input type="hidden" name="entity.questionId" value="${entity.questionId }">
<table width="100%" class="datatable">
	<tr>
		<th align="right" width="120" height="30"><span style="color: red">*</span>&nbsp;一级责任部门：</th>
		<td>
			<select name="entity.depId1" onchange="changeDepTwo(this.value)" class="required">
			<option value=""></option>
			<c:forEach items="${departments}" var="dep">
				<c:if test="${dep.fatherId == 1}">
					<option value="${dep.id}" <c:if test="${entity.depId1 == dep.id}">selected</c:if>>${dep.depName}</option>
				</c:if>
			</c:forEach>
			</select>
		</td>
	</tr>
	<tr>
		<th align="right" width="120" height="30"><span style="color: red">*</span>&nbsp;二级责任部门：</th>
		<td>
			<select name="entity.depId2" id="depTwo" class="required">
			<option value=""></option>
			<c:forEach items="${departments}" var="dep">
				<c:if test="${dep.fatherId > 1}">
					<c:choose>
						<c:when test="${dep.fatherId == entity.depId1}">
							<option value="${dep.id}" class="dep_${dep.fatherId}" <c:if test="${entity.depId2 == dep.id}">selected</c:if>>${dep.depName}</option>
						</c:when>
						<c:otherwise>
							<option value="${dep.id}" class="dep_${dep.fatherId}" style="display: none;" disabled="disabled">${dep.depName}</option>
						</c:otherwise>
					</c:choose>
				</c:if>
			</c:forEach>
			</select>
		</td>
	</tr>
	<tr>
		<th align="right" width="120" height="30"><span style="color: red">*</span>&nbsp;责任岗位：</th>
		<td>
			<select name="entity.positionId" class="required">
				<option value=""></option>
				<c:if test="${entity.positionId == null}">${ui:makeSelect(positionMap, 0, null)}</c:if>
				<c:if test="${entity.positionId != null}">${ui:makeSelect(positionMap, entity.positionId, null)}</c:if>
			</select>
		</td>
	</tr>
	<tr>
		<th align="right" width="120" height="30"><span style="color: red">*</span>&nbsp;责任人：</th>
		<td>
			<input type="text" name="entity.respPersonName" id="respPersonName" value="${entity.respPersonName}" class="required">
			<input type="hidden" name="entity.respPersonId" id="respPersonId" value="${entity.respPersonId}">
		</td>
	</tr>
	<tr>
		<th align="right" width="120" height="30">记分值：</th>
		<td>
			<select name="entity.scoreValue">
				<option value="0" <c:if test="${entity.scoreValue == 0}">selected</c:if>>0</option>
				<option value="1" <c:if test="${entity.scoreValue == 1}">selected</c:if>>1</option>
				<option value="2" <c:if test="${entity.scoreValue == 2}">selected</c:if>>2</option>
				<option value="3" <c:if test="${entity.scoreValue == 3}">selected</c:if>>3</option>
				<option value="5" <c:if test="${entity.scoreValue == 5}">selected</c:if>>5</option>
				<option value="10" <c:if test="${entity.scoreValue == 10}">selected</c:if>>10</option>
			</select>
		</td>
	</tr>
	<tr>
		<th align="right" width="120" height="30">罚款金额：</th>
		<td>
			<input type="text" name="entity.fineAmount" id="fineAmount" value="${entity.fineAmount}" size="17" class="number">&nbsp;元
		</td>
	</tr>
	<tr>
		<th align="right" width="120" height="30"></th>
		<td>
			<input type="button" class="pd5" value="提交" id="submitBtn" onclick="doSubmit('${entity.id}')">
		</td>
	</tr>
</table>
</form>
</BODY>
