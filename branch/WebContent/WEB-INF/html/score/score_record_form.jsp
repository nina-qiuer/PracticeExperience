<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<script type="text/javascript" src="${CONFIG.res_url}/script/jquery/plugin/jquery.form.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}script/DatePickerNew/WdatePicker.js"></script>
<script type="text/javascript">
<!--
$(document).ready(function(){
	var options = {
	    beforeSubmit:  checkSubmit,  // pre-submit callback 
	    success:       success_function,  // post-submit callback 
	}; 
    $('#form').ajaxForm(options);

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

function checkSubmit() {
	var qcDate = $("#qcDate").val();
	if ("" == qcDate) {
		alert("质检日期不能为空！~");
		return false;
	}

	var orderId = $("#orderId").val();
	var routeId = $("#routeId").val();
	var jiraNum = $("#jiraNum").val();
	if ((orderId != '' && (routeId != '' || (jiraNum != '' && jiraNum != '格式：BOSS-3598'))) || 
		(routeId != '' && (orderId != '' || (jiraNum != '' && jiraNum != '格式：BOSS-3598'))) || 
		((jiraNum != '' && jiraNum != '格式：BOSS-3598') && (routeId != '' || orderId != ''))) {
		alert("订单号、线路号、Jira号只能填写一个！~");
		return false;
	}

	var typeOne = $("#typeOne").val();
	if (0 == typeOne) {
		alert("记分性质不能为空！~");
		return false;
	}

	var typeTwo = $("#typeTwo").val();
	if (0 == typeTwo) {
		alert("记分类型不能为空！~");
		return false;
	}
	
	if (orderId == '') {
		$("#orderId").val(0);
	}
	if (routeId == '') {
		$("#routeId").val(0);
	}
	var complaintId = $("#complaintId").val();
	if (complaintId == '') {
		$("#complaintId").val(0);
	}

	$("#submitBtn").attr("disabled", true);
}

function success_function() {
	self.parent.closeWindow('${entity.id}');
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

function changeClassTwo(pid) {
	changeTwo(pid, "#classTwo option", ".cla_");
}

function changeTypeTwo(pid) {
	changeTwo(pid, "#typeTwo option", ".type_");
}

function changeDepTwo(pid) {
	changeTwo(pid, "#depTwo option", ".dep_");
}

function computeTotal() {
	var v1 = $("#scoreValue1").val();
	var v2 = $("#scoreValue2").val();
	var total = parseInt(v1) + parseInt(v2);
	$("#totalScore").html(total);
}

function doSubmit(id) {
	clickJiraNum();
	if (id > 0) {
		$('#form').attr("action", "score_record-update");
		$('#form').submit();
	} else {
		$('#form').attr("action", "score_record-add");
		$('#form').submit();
	}
}
//-->
</script>
</HEAD>
<BODY>
<form name="form" id="form" method="post" action="" enctype="multipart/form-data">
<input type="hidden" name="entity.id" value="${entity.id }">
<table width="100%" class="datatable">
	<tr>
		<th align="right" width="80" height="30"><span style="color: red;">*&nbsp;</span>质检日期</th>
		<td>
			<input type="text" name="entity.qcDate" id="qcDate" value="${entity.qcDate }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readOnly="readonly">
		</td>
		<th align="right" width="80" height="30">质检专员</th>
		<td colspan="3">
		<c:if test="${entity.id > 0}">
			${entity.qcPerson}<input type="hidden" name="entity.qcPerson" id="qcPerson" value="${entity.qcPerson}">
		</c:if>
		<c:if test="${entity.id == null || entity.id == 0}">
			${user.realName}<input type="hidden" name="entity.qcPerson" id="qcPerson" value="${user.realName}">
		</c:if>
		</td>
	</tr>
	<tr>
		<th align="right" width="80" height="30">订单号</th>
		<td>
			<c:if test="${entity.orderId == 0}"><input type="text" name="entity.orderId" id="orderId"></c:if>
			<c:if test="${entity.orderId > 0}"><input type="text" name="entity.orderId" id="orderId" value="${entity.orderId }"></c:if>
		</td>
		<th align="right" width="80" height="30">线路号</th>
		<td>
			<c:if test="${entity.routeId == 0}"><input type="text" name="entity.routeId" id="routeId"></c:if>
			<c:if test="${entity.routeId > 0}"><input type="text" name="entity.routeId" id="routeId" value="${entity.routeId }"></c:if>
		</td>
		<th align="right" width="80" height="30">Jira号</th>
		<td>
			<input type="text" name="entity.jiraNum" id="jiraNum" value="${entity.jiraNum }" onclick="clickJiraNum()" onblur="blurJiraNum()">
		</td>
	</tr>
	<c:if test="${user.depId==973 || user.id==5175 || user.id==7167 || user.id==4405}">
	<tr>
		<th align="right" width="80" height="30">投诉单号</th>
		<td>
			<c:if test="${entity.complaintId == 0}"><input type="text" name="entity.complaintId" id="complaintId"></c:if>
			<c:if test="${entity.complaintId > 0}"><input type="text" name="entity.complaintId" id="complaintId" value="${entity.complaintId }"></c:if>
		</td>
		<th align="right" width="80" height="30">问题类型一</th>
		<td>
			<select onchange="changeClassTwo(this.value)">
			<option value="0"></option>
			<c:forEach items="${questionClasses}" var="bigClass">
				<c:if test="${bigClass.childClasses != null}">
					<option value="${bigClass.id}" <c:if test="${entity.bigClassName == bigClass.description}">selected</c:if>>${bigClass.description}</option>
				</c:if>
			</c:forEach>
			</select>
		</td>
		<th align="right" width="80" height="30">问题类型二</th>
		<td colspan="3">
			<select name="entity.questionClassId" id="classTwo">
			<option value="0"></option>
			<c:forEach items="${questionClasses}" var="smallClass">
				<c:if test="${smallClass.childClasses == null}">
					<c:choose>
						<c:when test="${smallClass.parentId == entity.questionClassId1}">
							<option value="${smallClass.id}" class="cla_${smallClass.parentId}" <c:if test="${smallClass.id == entity.questionClassId}">selected</c:if>>${smallClass.description}</option>
						</c:when>
						<c:otherwise>
							<option value="${smallClass.id}" class="cla_${smallClass.parentId}" style="display: none;" disabled="disabled">${smallClass.description}</option>
						</c:otherwise>
					</c:choose>
				</c:if>
			</c:forEach>
			</select>
		</td>
	</tr>
	<tr>
		<th align="right" width="80" height="30">责任人</th>
		<td>
			<input type="text" name="entity.responsiblePerson" id="responsiblePerson" value="${entity.responsiblePerson}">
		</td>
		<th align="right" width="80" height="30">改进人</th>
		<td colspan="3">
			<input type="text" name="entity.improver" id="improver" value="${entity.improver}">
		</td>
	</tr>
	</c:if>
	<tr>
		<th align="right" width="80" height="30">一级部门</th>
		<td>
			<select name="entity.depId1" onchange="changeDepTwo(this.value)">
			<option value="0"></option>
			<c:forEach items="${departments}" var="dep">
				<c:if test="${dep.fatherId == 1}">
					<option value="${dep.id}" <c:if test="${entity.depId1 == dep.id}">selected</c:if>>${dep.depName}</option>
				</c:if>
			</c:forEach>
			</select>
		</td>
		<th align="right" width="80" height="30">二级部门</th>
		<td>
			<select name="entity.depId2" id="depTwo">
			<option value="0"></option>
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
		<th align="right" width="80" height="30">岗位</th>
		<td>
			<select class="mr20" name="entity.positionId">
				<option value="0"></option>
				${ui:makeSelect(positionMap, entity.positionId, null)}
			</select>
		</td>
	</tr>
	<tr>
		<th align="right" width="80" height="30">记分对象1</th>
		<td>
			<input type="text" name="entity.scoreTarget1" id="scoreTarget1" value="${entity.scoreTarget1 }">
		</td>
		<th align="right" width="80" height="30">记分值1</th>
		<td>
			<select name="entity.scoreValue1" id="scoreValue1" onchange="computeTotal()">
				<option value="0" <c:if test="${entity.scoreValue1 == 0}">selected</c:if>>0</option>
				<option value="1" <c:if test="${entity.scoreValue1 == 1}">selected</c:if>>1</option>
				<option value="2" <c:if test="${entity.scoreValue1 == 2}">selected</c:if>>2</option>
				<option value="3" <c:if test="${entity.scoreValue1 == 3}">selected</c:if>>3</option>
				<option value="5" <c:if test="${entity.scoreValue1 == 5}">selected</c:if>>5</option>
				<option value="10" <c:if test="${entity.scoreValue1 == 10}">selected</c:if>>10</option>
			</select>
		</td>
		<th align="right" width="80" rowspan="2">总记分</th>
		<td id="totalScore" rowspan="2">${entity.scoreValue1 + entity.scoreValue2}</td>
	</tr>
	<tr>
		<th align="right" width="80" height="30">记分对象2</th>
		<td>
			<input type="text" name="entity.scoreTarget2" id="scoreTarget2" value="${entity.scoreTarget2 }">
		</td>
		<th align="right" width="80" height="30">记分值2</th>
		<td>
			<select name="entity.scoreValue2" id="scoreValue2" onchange="computeTotal()">
				<option value="0" <c:if test="${entity.scoreValue2 == 0}">selected</c:if>>0</option>
				<option value="1" <c:if test="${entity.scoreValue2 == 1}">selected</c:if>>1</option>
				<option value="2" <c:if test="${entity.scoreValue2 == 2}">selected</c:if>>2</option>
				<option value="3" <c:if test="${entity.scoreValue2 == 3}">selected</c:if>>3</option>
				<option value="5" <c:if test="${entity.scoreValue2 == 5}">selected</c:if>>5</option>
				<option value="10" <c:if test="${entity.scoreValue2 == 10}">selected</c:if>>10</option>
			</select>
		</td>
	</tr>
	<tr>
		<th align="right" width="80" height="30"><span style="color: red;">*&nbsp;</span>记分性质</th>
		<td>
			<select id="typeOne" onchange="changeTypeTwo(this.value)">
			<option value="0"></option>
			<c:forEach items="${scoreTypes}" var="type">
				<c:if test="${type.parentId == 0}">
					<option value="${type.id}" <c:if test="${entity.scoreTypeName1 == type.name}">selected</c:if>>${type.name}</option>
				</c:if>
			</c:forEach>
			</select>
		</td>
		<th align="right" width="80" height="30"><span style="color: red;">*&nbsp;</span>记分类型</th>
		<td colspan="3">
			<select name="entity.scoreTypeId" id="typeTwo">
			<option value="0"></option>
			<c:forEach items="${scoreTypes}" var="type">
				<c:if test="${type.parentId > 0}">
					<c:choose>
						<c:when test="${type.parentId == entity.scoreTypeId1}">
							<option value="${type.id}" class="type_${type.parentId}" <c:if test="${type.id == entity.scoreTypeId}">selected</c:if>>${type.name}</option>
						</c:when>
						<c:otherwise>
							<option value="${type.id}" class="type_${type.parentId}" style="display: none;" disabled="disabled">${type.name}</option>
						</c:otherwise>
					</c:choose>
				</c:if>
			</c:forEach>
			</select>
		</td>
	</tr>
	<tr>
		<th align="right" width="80">问题描述</th>
		<td colspan="7">
			<textarea name="entity.description" rows="5" cols="80">${entity.description }</textarea>
		</td>
	</tr>
	<tr>
		<th align="right" width="80" height="30"></th>
		<td colspan="7">
			<input type="button" value="提交" id="submitBtn" onclick="doSubmit('${entity.id}')">
		</td>
	</tr>
</table>
</form>
</BODY>
