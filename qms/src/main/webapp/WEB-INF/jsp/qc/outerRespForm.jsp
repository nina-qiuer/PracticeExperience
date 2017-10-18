<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp"%>
<html>
<head>
<script type="text/javascript">
	var userArr = new Array();
	var depArr = new Array();
	var jobArr = new Array();
	var realNameArr = new Array();
	
	$(document).ready(function() {
		
		var userFlag = ${userFlag};//是否是投诉质检的责任单，true：显示改进人岗位、异议提出人    false：不显示
		
		userAutoComplete(userFlag);
		depAutoComplete();
		jobAutoComplete();
		
		$("#outer_form").validate({
			rules : {
				respPersonName : {
					required : true,
					userExists : true
				},
				depName : {
					required : true,
					depExists : true
				},
				appealPersonName : {
					required : true,
					userExists : true
				},
			},
			messages : {
				depName : {
					required : "请输入部门",
					depExists : "部门不存在"
				},
				respPersonName : {
					required : "请输入责任人",
					userExists : "责任人不存在"
				},
				appealPersonName : {
					required : "请输入异议提出人",
					userExists : "异议提出人不存在"
				}

			}

		});
		jQuery.validator.addMethod("depExists", function(value, element) {
			if ($.trim(value) != '') {
				var isExists = false;
				for ( var i = 0; i < depArr.length; i++) {

					if (value == depArr[i]) {
						isExists = true;
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
					}
				}

				return isExists;
			} else {
				return true;
			}
		}, "");
	});
	function userAutoComplete(userFlag) {

		<c:forEach items="${userNames}" var="userMap">
		userArr.push({
			label : '${userMap.label}',
			value : '${userMap.realName}'
		});
		realNameArr.push('${userMap.realName}');
		</c:forEach>

		$("#impPersonName").autocomplete({
			source : userArr,
			autoFocus : true
		}).data("ui-autocomplete")._renderItem = function(ul, item) {
			return $("<li>").append("<a>" + item.value + "</a>").appendTo(ul);
		};
		
		$("#respPersonName").autocomplete({
			source : userArr,
			autoFocus : true
		}).data("ui-autocomplete")._renderItem = function(ul, item) {
			return $("<li>").append("<a>" + item.value + "</a>").appendTo(ul);
		};
		
		if(userFlag == 1){
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
	
	function jobAutoComplete() {
		<c:forEach items="${jobNames}" var="jobName">
			jobArr.push('${jobName}');
		</c:forEach>
		
		$("#managerJobName").autocomplete({
			source : jobArr,
			autoFocus : true
		});
		
		$("#generalJobName").autocomplete({
			source : jobArr,
			autoFocus : true
		});
	}
	
	function queryAgency() {
		$(".errorMsg").html("");
		var agencyName = $('#agencyName').val();
		if ($.trim(agencyName) == '') {
			$(".errorMsg").html("供应商不能为空!");
			$(".errorMsg").css("color", "red");
			$(".errorMsg").addClass("msg");
			return false;
		}
		
		var userFlag = $("#userFlag").val();//使用方标志位   1：投诉质检 4：内部质检
		
		$.ajax({
			url : 'qc/qualityProblem/getAgency',
			data : {
				agencyName : agencyName,
				qcId : '${qcId}'
			},
			type : 'post',
			dataType : 'json',
			cache : false,
			success : function(result) {
				if (result) {
					if (result.retCode == "0") {
						var respBill = eval(result.retObj);
						$('#agencyId').val(respBill.agencyId);
						$('#impPersonName').val(respBill.impPersonName);
						$('#depName').val(respBill.depName);
						if(userFlag == 1){
							$("#appealPersonName").val(respBill.appealPersonName);
						}
					} else {
						$('#agencyName').val('');
						$('#agencyId').val('');
						$('#impPersonName').val('');
						$('#depName').val('');
						$(".errorMsg").html("供应商不存在!");
						$(".errorMsg").css("color", "red");
						$(".errorMsg").addClass("msg");
					}
				}
			}
		});

	}
	function getUserDetail(personName, level){
		var personNameLabel = "#" + personName;
		
		var name = $(personNameLabel).val();
		if(($.trim(name) == '') || ($.trim(name) == "无")){
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
		var userFlag = $("#userFlag").val();
		
		var detail = eval(result.retObj);
		switch(personType){
			case "impPersonName":
				if (result.retCode == "0") {
					$('#depName').val(detail.depName);
					if(userFlag == 1){
						$("#appealPersonName").val(detail.impPersonName);
					}
				} else {
					$('.impPerson').val("");
					errorClass = ".errorRespMsg";
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

		var agencyName = $('#agencyName').val();
		var agencyId = $('#agencyId').val();
		if ($.trim(agencyName) == '' || agencyId == '') {
			$(".errorMsg").html("请输入供应商");
			$(".errorMsg").css("color", "red");
			$(".errorMsg").addClass("msg");
			return false;
		}
		
		if ($('#outer_form').valid()) {
			var url = "";
			var outerId = $('#id').val();
			if (outerId != "" && outerId != null) {
				url = "qc/outerResp/updateOuterResp";
			} else {
				url = "qc/outerResp/addOuterResp";
			}
			
			$.ajax({
				url : url,
				data : $('#outer_form').serialize(),
				type : 'post',
				dataType : 'json',
				cache : false,
				success : function(result) {
					enableButton(input);
					if (result) {
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
	}
</script>
</head>
<body>
	<form name="outer_form" id="outer_form" method="post" action="">
		<form:hidden path="outerResp.qpId" />
		<form:hidden path="outerResp.qcId" />
		<form:hidden path="outerResp.id" />
		<input type="hidden" id="userFlag" value="${userFlag}" />
		<table class="datatable">
			<tr>
				<th align="right" width="100" height="30">
					<span style="color: red">*</span>供应商名称：
				</th>
				<td>
					<c:if test="${outerResp.id==null}">
						<form:input path="outerResp.agencyName" onblur="queryAgency()" />
					</c:if> 
					<c:if test="${outerResp.id != null}">
						<form:input path="outerResp.agencyName" readonly="true" />
					</c:if> 
					<span class="errorMsg"></span>
				</td>
			</tr>
			<tr>
				<th align="right" width="100" height="30">供应商编号：</th>
				<td><form:input path="outerResp.agencyId" readonly="true" /></td>
			</tr>
			<c:if test="${userFlag == 1}">
				<tr>
					<th align="right" width="100" height="30">异议提出人姓名：</th>
					<td>
						<form:input path="outerResp.appealPersonName" class="impPerson"/> 
					</td>
				</tr>
			</c:if>
			<tr>
				<th align="right" width="100" height="30">改进人姓名：</th>
				<td>
					<form:input path="outerResp.impPersonName" onblur='getUserDetail("impPersonName", 1)' class="impPerson"/> 
					<span class="errorRespMsg"></span>
				</td>
			</tr>
			<tr>
				<th align="right" width="100" height="30">责任部门：</th>
				<td><form:input path="outerResp.depName" style="width:300px" class="impPerson"/></td>
			</tr>
			<tr>
				<th align="right" width="100" height="30">责任人：</th>
				<td><form:input path="outerResp.respPersonName" /></td>
			</tr>
			<tr>
				<th align="right" width="100" height="30">责任经理：</th>
				<td>
					<form:input path="outerResp.respManagerName" onblur='getUserDetail("respManagerName", 0)' class="respManager" required="required"/>
					<span class="errorRespManagerMsg"></span>
				</td>
			</tr>
			<tr>
				<th align="right" width="100" height="30">责任经理岗位：</th>
				<td>
					<form:input path="outerResp.managerJobName" style="width:300px" class="respManager" required="required"/>
				</td>
			</tr>
			<tr>
				<th align="right" width="100" height="30">责任总监：</th>
				<td>
					<form:input path="outerResp.respGeneralName" onblur='getUserDetail("respGeneralName", 0)' class="respGeneral" required="required"/> 
					<span class="errorRespGeneralMsg"></span>
				</td>
			</tr>
			<tr>
				<th align="right" width="100" height="30">责任总监岗位：</th>
				<td>
					<form:input path="outerResp.generalJobName" style="width:300px" class="respGeneral" required="required"/>
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