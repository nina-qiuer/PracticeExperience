<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@include file="/WEB-INF/html/head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>邮件配置详情</title>
<link type="text/css" rel="stylesheet" href="${CONFIG.res_url}css/special.css" />
<script type="text/javascript">
	$(function() {
		$(".product_type_select").unbind("change")
				.bind("change", setNextSelect);
		$(".dest_class_select").unbind("change").bind("change", setDestFGroup);
		$(".submit_btn").unbind("click").bind("click", submitConfig);
	})

	var setNextSelect = function() {
		var nextSelectEle = $(this).closest("tr").next().find("select");
		$.ajax({
			type : "POST",
			url : "email_config-getProductInfoListData",
			data : {
				"product_level" : $(this).attr("product_level"),
				"product_id" : $(this).val()
			},
			async : false,
			success : function(data) {
				buildHtml(nextSelectEle, data);
			}
		});
	}

	var setDestFGroup = function() {
		var destFGroupEle = $(this).closest("tr").next().find("select");
		$.ajax({
			type : "POST",
			url : "email_config-getDestGroupData",
			data : {
				"type_id" : $("#class_brand").val(),
				"dest_class_id" : $(this).val()
			},
			async : false,
			success : function(data) {
				buildHtml(destFGroupEle, data);
			}
		});
	}

	var buildHtml = function(ele, data) {
		ele.empty();
		var option = '<option value="-1">全部</option>';
		ele.closest("tr").nextAll("tr").find("select").empty().append(option);
		$.each(data.resultList, function(index, product) {
			option += '<option value="'+product.id+'">' + product.name
					+ '</option>';
		})
		ele.append(option);
	}

	var submitConfig = function() {
		$(".submit_btn").addClass("active");
		if(validateEmailText()){
			var submitData = {
				"entity.class_brand_parent_name" : $("#class_brand_parent").val() == -1 ? ''
						: $("#class_brand_parent").find("option:selected").text(),
				"entity.class_brand_name" : $("#class_brand").val() == -1 ? '' : $(
						"#class_brand").find("option:selected").text(),
				"entity.product_new_line_type_name" : $("#product_new_line_type")
						.val() == -1 ? '' : $("#product_new_line_type").find(
						"option:selected").text(),
				"entity.dest_group_name" : $("#dest_group").val() == -1 ? '' : $(
						"#dest_group").find("option:selected").text()
			}
			submitData = $.extend(submitData, $('#emailConfig').serializeObject());
			$.ajax({
				type : "POST",
				url : "email_config-submintEmailConfig",
				data : submitData,
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

	var validateEmailText = function() {
		var email_content = $("#emails").val().trim();
		if (email_content == "") {
			layer.alert("请填写具体邮箱", {
				icon : 2
			});
			return false;
		}
		//替换中文逗号
		email_content = email_content.replaceAll("，", ",")
		//替换分号
		email_content = email_content.replaceAll(";", ",")
		$("#emails").val(email_content);
		return true;
	}

	$.fn.serializeObject = function() {
		var o = {};
		var a = this.serializeArray();
		$.each(a, function() {
			if (o[this.name]) {
				if (!o[this.name].push) {
					o[this.name] = [ o[this.name] ];
				}
				o[this.name].push(this.value || '');
			} else {
				o[this.name] = this.value || '';
			}
		});
		return o;
	};
</script>
</head>
<body style="overflow: hidden; background-color: rgba(152, 245, 255, 0.2)">
	<form id="emailConfig">
		<div class="data_form">
			<s:hidden name="entity.id" />
			<table class="form_table">
				<tr>
					<th>收件/抄送：</th>
					<td><select name="entity.mail_type">
							<option value="1">收件人</option>
							<option value="2" <c:if test="${entity.mail_type==2}">selected</c:if>>抄送人</option>
						</select></td>
				</tr>
				<tr>
					<th>处理岗：</th>
					<td><s:select name="entity.deal_depart" list="dealDepartments" headerKey="" headerValue="全部" /></td>
				</tr>
				<tr>
					<th>投诉等级：</th>
					<td><select name="entity.complaint_level">
							<option value="0">全部</option>
							<option value="1" <c:if test="${entity.complaint_level==1}">selected</c:if>>1</option>
							<option value="2" <c:if test="${entity.complaint_level==2}">selected</c:if>>2</option>
							<option value="3" <c:if test="${entity.complaint_level==3}">selected</c:if>>3</option>
						</select></td>
				</tr>
				<tr>
					<th>投诉来源：</th>
					<td><select name="entity.come_from">
							<option value="">所有来源</option>
							<option value="网站" <c:if test="${'网站'.equals(entity.come_from)}">selected</c:if>>网站</option>
							<option value="门市" <c:if test="${'门市'.equals(entity.come_from)}">selected</c:if>>门市</option>
							<option value="当地质检" <c:if test="${'当地质检'.equals(entity.come_from)}">selected</c:if>>当地质检</option>
							<option value="来电投诉" <c:if test="${'来电投诉'.equals(entity.come_from)}">selected</c:if>>来电投诉</option>
							<option value="CS邮箱" <c:if test="${'CS邮箱'.equals(entity.come_from)}">selected</c:if>>CS邮箱</option>
							<option value="回访" <c:if test="${'回访'.equals(entity.come_from)}">selected</c:if>>回访</option>
							<option value="旅游局" <c:if test="${'旅游局'.equals(entity.come_from)}">selected</c:if>>旅游局</option>
							<option value="微博" <c:if test="${'微博'.equals(entity.come_from)}">selected</c:if>>微博</option>
							<option value="其他" <c:if test="${'其他'.equals(entity.come_from)}">selected</c:if>>其他</option>
						</select></td>
				</tr>
				<tr>
					<th>一级品类：</th>
					<td><select class="product_type_select" name="entity.class_brand_parent_id" product_level="2" id="class_brand_parent">
							<option value="-1">全部</option>
							<c:forEach items="${classBrandParentList}" var="classBrandParentInfo">
								<option value="${classBrandParentInfo.id }"
									<c:if test="${entity.class_brand_parent_id==classBrandParentInfo.id}">selected</c:if>>${classBrandParentInfo.name}</option>
							</c:forEach>
						</select></td>
				</tr>
				<tr>
					<th>二级品类：</th>
					<td><select class="product_type_select" name="entity.class_brand_id" product_level="3" id="class_brand">
							<option value="-1">全部</option>
							<c:forEach items="${classBrandList}" var="classBrandInfo">
								<option value="${classBrandInfo.id }" <c:if test="${entity.class_brand_id==classBrandInfo.id}">selected</c:if>>${classBrandInfo.name}</option>
							</c:forEach>
						</select></td>
				</tr>
				<tr>
					<th>目的地大类：</th>
					<td><select class="dest_class_select" name="entity.product_new_line_type_id" id="product_new_line_type">
							<option value="-1">全部</option>
							<c:forEach items="${destClassList}" var="destClassInfo">
								<option value="${destClassInfo.id }" <c:if test="${entity.product_new_line_type_id==destClassInfo.id}">selected</c:if>>${destClassInfo.name}</option>
							</c:forEach>
						</select></td>
				</tr>
				<tr>
					<th>目的地分组：</th>
					<td><select name="entity.dest_group_id" id="dest_group">
							<option value="-1">全部</option>
							<c:forEach items="${destGroupList}" var="destGroupInfo">
								<option value="${destGroupInfo.id }" <c:if test="${entity.dest_group_id==destGroupInfo.id}">selected</c:if>>${destGroupInfo.name}</option>
							</c:forEach>
						</select></td>
				</tr>
				<tr>
					<th>邮件配置：</th>
					<td><textarea id="emails" placeholder="邮箱之间请按照,分割" name="entity.emails" maxlength="1000">${entity.emails}</textarea></td>

				</tr>
				<tr>
					<th>备注：</th>
					<td><textarea name="entity.content" maxlength="1000">${entity.content}</textarea></td>
				</tr>
			</table>
		</div>
	</form>
	<div class="submit_btn">提交</div>
</body>
</html>