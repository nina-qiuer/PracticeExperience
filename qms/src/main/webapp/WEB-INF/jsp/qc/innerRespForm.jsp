<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp"%>
<html>
<head>

<script type="text/javascript">
	var depArr = new Array();
	var jobArr = new Array();
	var userArr = new Array();
	var realNameArr = new Array();
	
	$(document).ready(function() {
		var isComplaintQc = ${isComplaintQc};//是否是投诉质检的责任单，true：显示改进人岗位、异议提出人    false：不显示
		
		jobAutoComplete(isComplaintQc);
		userAutoComplete(isComplaintQc);
		depAutoComplete();
		
		initDataValidate(isComplaintQc);
	});
	
	/**
	 *数据校验规则
	 */
	function initDataValidate(isComplaintQc){
		var rulesParam = {
				respPersonName: {
					userExists: true,
					required: true
				},
				depName : {
					required : true,
					depExists : true
				},
				jobName : {
					required : true,
					jobExists : true
				},
				impPersonName : {
					required: true,
					userExists: true
				},
				impJobName:{
					required: true,
					jobExists : true
				},
				appealPersonName : {
					required: true,
					userExists: true
				},
				appealJobName:{
					required: true,
					jobExists : true
				}
			};
		var messagesParam = {
				respPersonName : {
					required : "请输入责任人",
					userExists : "责任人不存在"
				},
				depName : {
					required : "请输入责任部门",
					depExists : "责任部门不存在"
				},
				jobName : {
					required : "请输入岗位",
					jobExists : "岗位不存在"
				},
				impPersonName : {
					required : "请输入改进人",
					userExists : "改进人不存在"
				},
				impJobName : {
					required : "请输入改进人岗位",
					jobExists : "岗位不存在"
				},
				appealJobName : {
					required : "请输入异议提出人岗位",
					jobExists : "岗位不存在"
				},
				appealPersonName : {
					required : "请输入异议提出人",
					userExists : "异议提出人不存在"
				}
			};
		
		$("#inner_form").validate({
			rules : rulesParam,
			messages : messagesParam
		});
		
		jQuery.validator.addMethod("depExists", function(value, element) {
			if ($.trim(value) != '') {
				var isExists = false;
				for ( var i = 0; i < depArr.length; i++) {
					if (value == depArr[i]) {
						isExists = true;
						break;
					}
				}
				return isExists;
			} else {
				return true;

			}
		}, "");
		jQuery.validator.addMethod("jobExists", function(value, element) {
			if ($.trim(value) != '') {
				var isExists = false;
				for ( var i = 0; i < jobArr.length; i++) {
					if (value == jobArr[i]) {
						isExists = true;
						break;
					}
				}
				return isExists;
			} else {
				return true;
			}
		}, "");
		jQuery.validator.addMethod("userExists", function(value, element) {
			if ($.trim(value) != '') {
				var isExists = false;
				for ( var i = 0; i < realNameArr.length; i++) {
					if (value == realNameArr[i]) {
						isExists = true;
						break;
					}
				}
				return isExists;
			} else {
				return true;
			}
		}, "");
	}

	function userAutoComplete(isComplaintQc) {

		<c:forEach items="${userNames}" var="userMap">
			userArr.push({
				label : '${userMap.label}',
				value : '${userMap.realName}'
			});
			realNameArr.push('${userMap.realName}');
		</c:forEach>

		$("#respPersonName").autocomplete({
			source : userArr,
			autoFocus : true
		}).data("ui-autocomplete")._renderItem = function(ul, item) {
			return $("<li>").append("<a>" + item.value + "</a>").appendTo(ul);
		};
		$("#impPersonName").autocomplete({
			source : userArr,
			autoFocus : true
		}).data("ui-autocomplete")._renderItem = function(ul, item) {
			return $("<li>").append("<a>" + item.value + "</a>").appendTo(ul);
		};
		
		if(isComplaintQc){
			$("#appealPersonName").autocomplete({
				source : userArr,
				autoFocus : true
			}).data("ui-autocomplete")._renderItem = function(ul, item) {
				return $("<li>").append("<a>" + item.value + "</a>").appendTo(ul);
			};
		}
		
		$("#respManagerName").autocomplete({
			source : userArr,
			autoFocus : true
		}).data("ui-autocomplete")._renderItem = function(ul, item) {
			return $("<li>").append("<a>" + item.value + "</a>").appendTo(ul);
		};
		
		$("#respGeneralName").autocomplete({
			source : userArr,
			autoFocus : true
		}).data("ui-autocomplete")._renderItem = function(ul, item) {
			return $("<li>").append("<a>" + item.value + "</a>").appendTo(ul);
		};
	}
	function depAutoComplete() {

		<c:forEach items="${depNames}" var="depName">
			depArr.push('${depName}');
		</c:forEach>

		$("#depName").autocomplete({
			source : depArr,
			autoFocus : true
		});
	}
	function jobAutoComplete(isComplaintQc) {

		<c:forEach items="${jobNames}" var="jobName">
			jobArr.push('${jobName}');
		</c:forEach>

		$("#jobName").autocomplete({
			source : jobArr,
			autoFocus : true
		});
		
		if(isComplaintQc){
			$("#impJobName").autocomplete({
				source : jobArr,
				autoFocus : true
			});
			
			$("#appealJobName").autocomplete({
				source : jobArr,
				autoFocus : true
			});
		}
		
		$("#managerJobName").autocomplete({
			source : jobArr,
			autoFocus : true
		});
		
		$("#generalJobName").autocomplete({
			source : jobArr,
			autoFocus : true
		});
	}
	
	/**
	* 输入框验证， labelType 验证文本框标示   1： 责任人  2：改进人   3： 异议提出人
	*/
	function errorMsg(labelType){
		var errorClass = "";
		var flag = true;
		
		switch(labelType){
			case 1:
				var respPersonName = $('#respPersonName').val();
				if ($.trim(respPersonName) == '') {
					errorClass = ".errorMsg";
					flag = false;
				}
				break;
			case 2:
				var impPersonName = $('#impPersonName').val();
				if ($.trim(impPersonName) == '') {
					errorClass = ".errorImpMsg";
					flag = false;
				}
				break;
			case 3:
				var appealPersonName = $('#appealPersonName').val();
				if ($.trim(appealPersonName) == '') {
					errorClass = ".errorAppealMsg";
					flag = false;
				}
				break;
			default: flag = false;
		}
		$(errorClass).html("");
		
		if(!flag){
			$(errorClass).html("请输入姓名");
			$(errorClass).css("color", "red");
			$(errorClass).addClass("msg");
		}
		
		return flag;
	}
	
	/**
	* personName 查询人员信息
	*/
	function getUserDetail(personName, level) {
		//var vaildFlag = errorMsg(personType);
		var personNameLabel = "#" + personName;
		
		var name = $(personNameLabel).val();
		if(($.trim(name) == '') || ($.trim(name) == "无") ){
			return false;
		}
		
		var param = {
				"personName": name,
				"level": level
			};
		
		$.ajax({
			url : 'qc/qualityProblem/getUserDetails',
			data : param,
			type : 'post',
			dataType : 'json',
			success : function(result) {
				if (result) {
					fillInputData(result, personName);//返回数据填充
				}
			}
		});
	}
	
	/**
	 * 人员详情查询返回结果填充
	 */
	function fillInputData(result, personType){
		var errorClass = "";
		var isComplaintQc = $("#isComplaintQc").val();
		
		var detail = eval(result.retObj);
		switch(personType){
			case "respPersonName":
				if (result.retCode == "0") {
					$('#depName').val(detail.impDepName);
					$('#jobName').val(detail.jobName);
					$('#respPersonId').val(detail.id);
					$('#impPersonName').val(detail.impPersonName);
					if("true" == isComplaintQc){
						$('#impJobName').val(detail.impPersonJob);
						$('#appealPersonName').val(detail.secondImpPersonName);
						$('#appealJobName').val(detail.secondImpPersonJob);
					}
				} else {
					$('.respPerson').val("");
					$('.impPerson').val("");
					$('.appealPerson').val("");
					errorClass = ".errorMsg";
				}
				break;
			case "impPersonName":
				if (result.retCode == "0") {
					$('#depName').val(detail.depName);
					if("true" == isComplaintQc){
						$('#impJobName').val(detail.jobName);
						$('#appealPersonName').val(detail.impPersonName);
						$('#appealJobName').val(detail.impPersonJob);
					}
				} else {
					$('.impPerson').val("");
					$('.appealPerson').val("");
					errorClass = ".errorImpMsg";
				}
				break;
			case "appealPersonName":
				if (result.retCode == "0") {
					$('#appealJobName').val(detail.jobName);
				} else {
					$('.appealPerson').val("");
					errorClass = ".errorAppealMsg";
				}
				break;
			case "respManagerName":
				if (result.retCode == "0") {
					$('#managerJobName').val(detail.jobName);
				} else {
					$('.respManager').val("");
					errorClass = ".errorRespManagerMsg";
				}
				break;
			case "respGeneralName":
				if (result.retCode == "0") {
					$('#generalJobName').val(detail.jobName);
				} else {
					$('.respGeneral').val("");
					errorClass = ".errorRespGeneralMsg";
				}
				break;
			default: ;
		}
		
		$(errorClass).html("");
		if(errorClass != ""){
			$(errorClass).html(result.resMsg);
			$(errorClass).css("color", "red");
			$(errorClass).addClass("msg");
		}
	}
	
	function doSubmit(input) {
		if (!$('#inner_form').valid()) {
			return;	
		}
		disableButton(input);

		var innerId = $('#id').val();
		var url = '';
		if (innerId != null && innerId != "") {
			url = 'qc/innerResp/updateInnerResp';
		} else {
			url = 'qc/innerResp/addInnerResp';
		}

		$.ajax({
			url : url,
			data : $('#inner_form').serialize(),
			type : 'post',
			dataType : 'json',
			success : function(result) {
				if (result != null) {
					enableButton(input);
					if (result.retCode == "0") {
						parent.parent.location.reload();
						parent.layer.closeAll();
					} else {
						layer.alert(result.resMsg, {
							icon : 2
						});
					}
				}
			}
		});
	}
</script>
</head>
<body>
	<form name="inner_form" id="inner_form" method="post" action="">
		<form:hidden path="innerResp.qpId" />
		<form:hidden path="innerResp.id" />
		<form:hidden path="innerResp.respPersonId" class="respPerson"/>
		<form:hidden path="innerResp.qcId" />
		<input type="hidden" id="isComplaintQc" value="${isComplaintQc}" />
		<table class="datatable">
			<tr>
				<th align="right" width="100" height="30"><span style="color: red">*</span>责任人姓名：</th>
				<td>
					<c:if test="${innerResp.id == null }">
						<form:input path="innerResp.respPersonName" class="respPerson" onblur='getUserDetail("respPersonName", 2)' />
					</c:if> 
					<c:if test="${innerResp.id != null }">
						<form:input path="innerResp.respPersonName" class="respPerson" readonly="true" />
					</c:if> 
					<span class="errorMsg"></span>
				</td>
			</tr>
			<tr>
				<th align="right" width="100" height="30">责任部门：</th>
				<td>
					<form:input path="innerResp.depName" style="width:300px" class="impPerson"/>
				</td>
			</tr>
			<tr>
				<th align="right" width="100" height="30">责任岗位：</th>
				<td><form:input path="innerResp.jobName" class="respPerson"/></td>
			</tr>
			<tr>
				<th align="right" width="100" height="30">改进人姓名：</th>
				<td>
					<form:input path="innerResp.impPersonName" onblur='getUserDetail("impPersonName", 1)' class="impPerson"/> 
					<span class="errorImpMsg"></span>
				</td>
			</tr>
			<c:if test="${isComplaintQc}">
				<tr>
					<th align="right" width="100" height="30">改进人岗位：</th>
					<td>
						<form:input path="innerResp.impJobName" style="width:300px" class="impPerson"/>
					</td>
				</tr>
				<tr>
					<th align="right" width="100" height="30">异议提出人姓名：</th>
					<td>
						<form:input path="innerResp.appealPersonName" onblur='getUserDetail("appealPersonName", 0)' class="appealPerson"/> 
						<span class="errorAppealMsg"></span>
					</td>
				</tr>
				<tr>
					<th align="right" width="100" height="30">异议提出人岗位：</th>
					<td>
						<form:input path="innerResp.appealJobName" style="width:300px" class="appealPerson"/>
					</td>
				</tr>
			</c:if>
			<tr>
				<th align="right" width="100" height="30">责任经理：</th>
				<td>
					<form:input path="innerResp.respManagerName" onblur='getUserDetail("respManagerName", 0)' class="respManager" required="required"/>
					<span class="errorRespManagerMsg"></span>
				</td>
			</tr>
			<tr>
				<th align="right" width="100" height="30">责任经理岗位：</th>
				<td>
					<form:input path="innerResp.managerJobName" style="width:300px" class="respManager" required="required"/>
				</td>
			</tr>
			<tr>
				<th align="right" width="100" height="30">责任总监：</th>
				<td>
					<form:input path="innerResp.respGeneralName" onblur='getUserDetail("respGeneralName", 0)' class="respGeneral" required="required"/> 
					<span class="errorRespGeneralMsg"></span>
				</td>
			</tr>
			<tr>
				<th align="right" width="100" height="30">责任总监岗位：</th>
				<td>
					<form:input path="innerResp.generalJobName" style="width:300px" class="respGeneral" required="required"/>
				</td>
			</tr>
			<tr>
				<th align="right" width="100" height="30"></th>
				<td colspan="3">
					<input type="button" name="submitBtn" class="blue" value="提交" id="submitBtn" onclick="doSubmit(this)">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>