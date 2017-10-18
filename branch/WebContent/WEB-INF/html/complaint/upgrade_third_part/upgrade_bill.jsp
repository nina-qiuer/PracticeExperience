<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@include file="/WEB-INF/html/head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>投诉至第三方</title>
<script language="javascript" src="../../res/script/jquery/jquery-1.7.1.min.js"></script>
<link type="text/css" rel="stylesheet" href="../../res/css/special.css" />
<script type="text/javascript">
	$(function() {
		$(".third_part_type").unbind("change").bind("change", setNextSelect);
		$(".submit_btn").unbind("click").bind("click", submitThirdPart);
	})

	var setNextSelect = function() {
		var nextSelectEle = $(".third_part_second_type");
		var fatherTypeId = $(this).val();
		$.ajax({
			type : "POST",
			url : "upgrade_third_part-getThirdPartNextType",
			data : {
				"type_id" : $(this).val()
			},
			async : false,
			success : function(data) {
				buildSelect(nextSelectEle, data);
			}
		});
	}

	var buildSelect = function(ele, data) {
		ele.empty();
		var option = '<option value="">请选择</option>';
		if (data.retCode != "error") {
			$.each(data.resultList, function(index, product) {
				option += '<option value="'+product.id+'">' + product.name
						+ '</option>';
			})
		}
		ele.append(option);
	}
	
	var submitThirdPart = function() {
		$(".submit_btn").addClass("active");
		if(submitValidate()){
			$.ajax({
				type : "POST",
				url : "upgrade_third_part-submitThirdPart",
				data : $('#upgradeThirdPartFrom').serialize(),
				async : false,
				success : function(data) {
					if (data.retObj == "failed") {
						layer.alert("提交失败", {
							icon : 2
						});
					} else {
						layer.alert("提交成功", {
							icon : 1
						});
						parent.layer.closeAll();
					}
				}
			});
		}
		$(".submit_btn").removeClass("active");
	}
	
	var submitValidate = function() {
		var email_content = $(".third_part_type").val().trim();
		if (email_content == "") {
			layer.alert("请选择第三方机构", {
				icon : 2
			});
			return false;
		}
		return true;
	}
</script>
</head>
<body style="overflow: hidden; background-color: rgba(152, 245, 255, 0.2)">
	<form id="upgradeThirdPartFrom">
		<div class="data_form">
			<s:hidden name="entity.id" />
			<s:hidden name="entity.complaint_id" />
			<table class="form_table">
				<tr>
					<th>第三方机构：</th>
					<td><select class="third_part_type" name="entity.third_type">
							<option value="">请选择</option>
							<c:forEach items="${thirdPartTypes}" var="thirdPartType">
								<option value="${thirdPartType.id }" <c:if test="${entity.third_type==thirdPartType.id}">selected</c:if>>${thirdPartType.name}</option>
							</c:forEach>
						</select></td>
				</tr>
				<tr>
					<th>地区/媒体：</th>
					<td><select class="third_part_second_type" name="entity.third_second_type">
							<option value="0">请选择</option>
							<c:forEach items="${thirdPartSecondTypes}" var="thirdPartSecondType">
								<option value="${thirdPartSecondType.id }" <c:if test="${entity.third_second_type==thirdPartSecondType.id}">selected</c:if>>${thirdPartSecondType.name}</option>
							</c:forEach>
						</select></td>
				</tr>
				<tr>
					<th>升级说明：</th>
					<td><textarea name="entity.content" maxlength="20000">${entity.content}</textarea></td>

				</tr>
			</table>
		</div>
	</form>
	<div class="submit_btn">提交</div>
</body>
</html>