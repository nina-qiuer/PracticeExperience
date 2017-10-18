<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>赔付理论详情</title>
<link type="text/css" rel="stylesheet" href="../../res/css/special.css" />
<script language="javascript" src="../../res/script/jquery/jquery-1.7.1.min.js"></script>
<script language="javascript" src="../../res/script/jquery/plugin/layer/layer.js"></script>
<script type="text/javascript">
	$(function() {
		first_part.init();
		
		addBtnEventBind();
	})

	var first_part = {
		init : function() {
			first_part.getDataAndBuildHtml();
		},
		getDataAndBuildHtml : function() {
			$(".background_mask").show();
			$.ajax({
				type : "POST",
				url : "getPayoutBaseData",
				success : function(data) {
					first_part.dealDataAndBuildHtml(data);
				},
				error : first_part.getDataError
			});
		},
		dealDataAndBuildHtml : function(data) {
			var firstPart = $(".first_part").find(".data_body");
			firstPart.empty();
			var result = [];
			var optiontd = '<td class="operate">'
					+ '<input class="editBtn btn_class" type="button" value="修改"/>'
					+ '<input class="delBtn btn_class" type="button" value="删除"/></td>';
			$.each(data, function(index, ele) {
				var payoutBase = ele.data.payoutBase;
				var trstr = '<tr config_id="'+ele.data.id+'">';
				trstr += '<td class="config_name" title="' + payoutBase + '">'
						+ payoutBase + '</td>';
				trstr += optiontd;
				trstr += '</tr>';
				result.push(trstr);
			})
			firstPart.append(result.join(""));
			first_part.buttonEventBind();
			$(".background_mask").hide();
		},
		getDataError : function() {
			layer.alert("查询失败,请刷新页面", {
				icon : 2
			});
			$(".background_mask").hide();
		},
		chooseData : function() {
			var firstPart = $(".first_part").find(".data_body");
			firstPart.find("td.config_name").removeClass("active");
			$(this).addClass("active");
			second_part.init($(this).closest("tr").attr("config_id"));
			third_part.clearData();
		},
		buttonEventBind : function() {
			var firstPart = $(".first_part").find(".data_body");
			firstPart.find("td.config_name").unbind("click").bind("click",
					first_part.chooseData);
			firstPart.find("td.operate .delBtn").unbind("click").bind("click",
					first_part.deleteData);
			firstPart.find("td.operate .editBtn").unbind("click").bind("click",
					first_part.editConfigData);
		},
		refresh : function() {
			first_part.init();
			second_part.clearData();
			third_part.clearData();
		},
		deleteData : function() {
			var config_id = $(this).closest("tr").attr("config_id");
			deleteFunction(config_id, first_part.refresh);
		},
		editConfigData : function() {
			var config_name_td = $(this).closest("tr").find("td.config_name");
			editFunction(config_name_td, first_part.saveConfigData,
					first_part.refresh);
		},
		saveConfigData : function() {
			var submitTr = $(this).closest("tr");
			saveBtnFunction(submitTr, first_part.refresh);
		},
		addConfigData : function() {
			var firstPart = $(".first_part").find(".data_body");
			var operate_btn = '<tr><td class="config_name"><input class="config_input"></td>'
					+ '<td class="operate"><input class="saveBtn btn_class" type="button" value="保存"/>'
					+ '<input class="cancelBtn btn_class" type="button" value="取消"/></td></tr>';
			cancelOtherInputState();
			firstPart.append(operate_btn);
			var operate_td = firstPart.find(".config_input").closest("tr");
			operate_td.find(".saveBtn").unbind("click").bind("click",
					first_part.addConfigFunction);
			operate_td.find(".cancelBtn").unbind("click").bind("click",
					first_part.refresh);
		},
		addConfigFunction : function() {
			var sumbitTr = $(this).closest("tr");
			var config_input = sumbitTr.find(".config_input");
			var config_name = config_input.val();
			if (!validateSumbitData(config_name)) {
				config_input.focus();
				return;
			}
			var submitData = {
				"fatherId" : 0,
				"payoutBase" : config_name
			};
			sumbitConfigData(submitData, first_part.refresh);
		},
	}

	var second_part = {
		init : function(config_id) {
			second_part.getDataAndBuildHtml(config_id);
		},
		getDataAndBuildHtml : function(config_id) {
			$(".background_mask").show();
			$.ajax({
				type : "POST",
				url : "getPayoutBaseData",
				data : {
					"father_id" : config_id
				},
				success : function(data) {
					var secondPart = $(".second_part").find(".data_body");
					secondPart.attr("config_id", config_id);
					second_part.dealDataAndBuildHtml(data);
				},
				error : second_part.getDataError
			});
		},
		dealDataAndBuildHtml : function(data) {
			var secondPart = $(".second_part").find(".data_body");
			secondPart.empty();
			var result = [];
			var optiontd = '<td class="operate">'
					+ '<input class="editBtn btn_class" type="button" value="修改"/>'
					+ '<input class="delBtn btn_class" type="button" value="删除"/></td>';
			$.each(data, function(index, ele) {
				var payoutBase = ele.data.payoutBase;
				var trstr = '<tr config_id="'+ele.data.id+'">';
				trstr += '<td class="config_name" title="' + payoutBase + '">'
						+ payoutBase + '</td>';
				trstr += optiontd;
				trstr += '</tr>';
				result.push(trstr);
			})
			secondPart.append(result.join(""));
			second_part.buttonEventBind();
			$(".background_mask").hide();
		},
		getDataError : function() {
			layer.alert("查询失败,请刷新页面", {
				icon : 2
			});
			$(".background_mask").hide();
		},
		buttonEventBind : function() {
			var secondPart = $(".second_part").find(".data_body");
			secondPart.find("td.config_name").unbind("click").bind("click",
					second_part.chooseData);
			secondPart.find("td.operate .delBtn").unbind("click").bind("click",
					second_part.deleteData);
			secondPart.find("td.operate .editBtn").unbind("click").bind(
					"click", second_part.editConfigData);
		},
		chooseData : function() {
			var secondPart = $(".second_part").find(".data_body");
			secondPart.find("td.config_name").removeClass("active");
			$(this).addClass("active");
			third_part.init($(this).closest("tr").attr("config_id"));
		},
		clearData : function() {
			var secondPart = $(".second_part").find(".data_body");
			secondPart.removeAttr("config_id");
			secondPart.empty();
		},
		refresh : function() {
			var secondPart = $(".second_part").find(".data_body");
			second_part.init(secondPart.attr("config_id"));
			third_part.clearData();
		},
		deleteData : function() {
			var config_id = $(this).closest("tr").attr("config_id");
			deleteFunction(config_id, second_part.refresh);
		},
		editConfigData : function() {
			var config_name_td = $(this).closest("tr").find("td.config_name");
			editFunction(config_name_td, second_part.saveConfigData,
					second_part.refresh);
		},
		saveConfigData : function() {
			var submitTr = $(this).closest("tr");
			saveBtnFunction(submitTr, second_part.refresh);
		},
		addConfigData : function() {
			var secondPart = $(".second_part").find(".data_body");
			var config_id = secondPart.attr("config_id");
			if (!config_id) {
				layer.alert("请选择一级分类", {
					icon : 2
				});
				return false;
			}
			cancelOtherInputState();
			var operate_btn = '<tr><td class="config_name"><input class="config_input"></td>'
					+ '<td class="operate"><input class="saveBtn btn_class" type="button" value="保存"/>'
					+ '<input class="cancelBtn btn_class" type="button" value="取消"/></td></tr>';
			secondPart.append(operate_btn);
			var operate_td = secondPart.find(".config_input").closest("tr");
			operate_td.find(".saveBtn").unbind("click").bind("click",
					second_part.addConfigFunction);
			operate_td.find(".cancelBtn").unbind("click").bind("click",
					second_part.refresh);
		},
		addConfigFunction : function() {
			var sumbitTr = $(this).closest("tr");
			var father_id = sumbitTr.closest(".data_body").attr("config_id")
			var config_input = sumbitTr.find(".config_input");
			var config_name = config_input.val();
			if (!validateSumbitData(config_name)) {
				config_input.focus();
				return;
			}
			var submitData = {
				"fatherId" : father_id,
				"payoutBase" : config_name
			};
			sumbitConfigData(submitData, second_part.refresh);
		},
	}

	var third_part = {
		init : function(config_id) {
			third_part.getDataAndBuildHtml(config_id);
		},
		getDataAndBuildHtml : function(config_id) {
			$(".background_mask").show();
			$.ajax({
				type : "POST",
				url : "getPayoutBaseData",
				data : {
					"father_id" : config_id
				},
				success : function(data) {
					var thirdPart = $(".third_part").find(".data_body");
					thirdPart.attr("config_id", config_id);
					third_part.dealDataAndBuildHtml(data);
				},
				error : third_part.getDataError
			});
		},
		dealDataAndBuildHtml : function(data) {
			var thirdPart = $(".third_part").find(".data_body");
			thirdPart.empty();
			var result = [];
			var optiontd = '<td class="operate">'
					+ '<input class="editBtn btn_class" type="button" value="修改"/>'
					+ '<input class="delBtn btn_class" type="button" value="删除"/></td>';
			$.each(data, function(index, ele) {
				var payoutBase = ele.data.payoutBase;
				var trstr = '<tr config_id="'+ele.data.id+'">';
				trstr += '<td class="config_name" title="' + payoutBase + '">'
						+ payoutBase + '</td>';
				trstr += optiontd;
				trstr += '</tr>';
				result.push(trstr);
			})
			thirdPart.append(result.join(""));
			third_part.buttonEventBind();
			$(".background_mask").hide();
		},
		getDataError : function() {
			layer.alert("查询失败,请刷新页面", {
				icon : 2
			});
			$(".background_mask").hide();
		},
		buttonEventBind : function() {
			var thirdPart = $(".third_part").find(".data_body");
			thirdPart.find("td.operate .delBtn").unbind("click").bind("click",
					third_part.deleteData);
			thirdPart.find("td.operate .editBtn").unbind("click").bind("click",
					third_part.editConfigData);
		},
		clearData : function() {
			var thirdPart = $(".third_part").find(".data_body");
			thirdPart.removeAttr("config_id");
			thirdPart.empty();
		},
		refresh : function() {
			var thirdPart = $(".third_part").find(".data_body");
			third_part.init(thirdPart.attr("config_id"));
		},
		deleteData : function() {
			var config_id = $(this).closest("tr").attr("config_id");
			deleteFunction(config_id, third_part.refresh);
		},
		editConfigData : function() {
			var config_name_td = $(this).closest("tr").find("td.config_name");
			editFunction(config_name_td, third_part.saveConfigData,
					third_part.refresh);
		},
		saveConfigData : function() {
			var submitTr = $(this).closest("tr");
			saveBtnFunction(submitTr, third_part.refresh);
		},
		addConfigData : function() {
			var thirdPart = $(".third_part").find(".data_body");
			var config_id = thirdPart.attr("config_id");
			if (!config_id) {
				layer.alert("请选择二级分类", {
					icon : 2
				});
				return false;
			}
			cancelOtherInputState();
			var operate_btn = '<tr><td class="config_name"><input class="config_input"></td>'
					+ '<td class="operate"><input class="saveBtn btn_class" type="button" value="保存"/>'
					+ '<input class="cancelBtn btn_class" type="button" value="取消"/></td></tr>';
			thirdPart.append(operate_btn);
			var operate_td = thirdPart.find(".config_input").closest("tr");
			operate_td.find(".saveBtn").unbind("click").bind("click",
					third_part.addConfigFunction);
			operate_td.find(".cancelBtn").unbind("click").bind("click",
					third_part.refresh);
		},
		addConfigFunction : function() {
			var sumbitTr = $(this).closest("tr");
			var father_id = sumbitTr.closest(".data_body").attr("config_id")
			var config_input = sumbitTr.find(".config_input");
			var config_name = config_input.val();
			if (!validateSumbitData(config_name)) {
				config_input.focus();
				return;
			}
			var submitData = {
				"fatherId" : father_id,
				"payoutBase" : config_name
			};
			sumbitConfigData(submitData, third_part.refresh);
		},
	}

	//保存字典值
	var saveBtnFunction = function(submitTr, refrehFunction) {
		var config_input = submitTr.find(".config_input");
		var config_name = config_input.val();
		if (!validateSumbitData(config_name)) {
			config_input.focus();
			return;
		}
		var submitData = {
			"id" : submitTr.attr("config_id"),
			"payoutBase" : config_name
		};
		sumbitConfigData(submitData, refrehFunction);
	}

	//提交数据
	var sumbitConfigData = function(submitData, refrehFunction) {
		$(".background_mask").show();
		$.ajax({
			type : "POST",
			url : "addOrUpdateConfigData",
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			data : submitData,
			success : function(data) {
				$(".background_mask").hide();
				layer.alert("保存成功", {
					icon : 1
				});
				refrehFunction();
			},
			error : function() {
				$(".background_mask").hide();
				layer.alert("保存失败,请刷新页面", {
					icon : 2
				});
			}
		});
	}

	var validateSumbitData = function(config_name) {
		var result = true;
		if (config_name.trim().length == 0) {
			layer.alert("请填写内容", {
				icon : 2
			});
			result = false;
		}
		return result;
	}

	var cancelOtherInputState = function() {
		var operate = '<input class="editBtn btn_class" type="button" value="修改"/>'
				+ '<input class="delBtn btn_class" type="button" value="删除"/>';
		$.each($(".config_input"), function(index, ele) {
			var inputTr = $(ele).closest("tr");
			var thisDataList = $(ele).closest(".data_list");
			if (!inputTr.attr("config_id")) {
				inputTr.remove();
			} else {
				var config_name = inputTr.find(".config_name").attr("title");
				inputTr.find("td.config_name").attr("title", config_name).text(
						config_name);
				inputTr.find("td.operate").html(operate);
				if (thisDataList.hasClass("first_part")) {
					inputTr.find(".editBtn").unbind("click").bind("click",
							first_part.editConfigData);
					inputTr.find(".delBtn").unbind("click").bind("click",
							first_part.deleteData);
				}
				if (thisDataList.hasClass("second_part")) {
					inputTr.find(".editBtn").unbind("click").bind("click",
							second_part.editConfigData);
					inputTr.find(".delBtn").unbind("click").bind("click",
							second_part.deleteData);
				}
				if (thisDataList.hasClass("third_part")) {
					inputTr.find(".editBtn").unbind("click").bind("click",
							third_part.editConfigData);
					inputTr.find(".delBtn").unbind("click").bind("click",
							third_part.deleteData);
				}
			}
		})
	}

	//编辑字典值
	var editFunction = function(config_name_td, saveFunction, refrehFunction) {
		cancelOtherInputState();
		var operate_td = config_name_td.siblings();
		var config_input = '<input class="config_input" value="'
				+ config_name_td.text() + '"/>';
		config_name_td.html(config_input);
		var operate_btn = '<input class="saveBtn btn_class" type="button" value="保存"/>'
				+ '<input class="cancelBtn btn_class" type="button" value="取消"/>';
		operate_td.html(operate_btn);
		config_name_td.find(".config_input").unbind("keydown").bind("keydown",
				function(e) {
					if (e.keyCode == 13) {
						operate_td.find(".saveBtn").click();
					}
				})
		operate_td.find(".saveBtn").unbind("click").bind("click", saveFunction);
		operate_td.find(".cancelBtn").unbind("click").bind("click",
				refrehFunction);
		config_name_td.find(".config_input").focus();
	}

	var deleteFunction = function(config_id, refrehFunction) {
		layer.confirm('确定删除吗？', function(index) {
			$(".background_mask").show();
			$.ajax({
				type : "POST",
				url : "deletePayoutBaseData",
				data : {
					"config_id" : config_id
				},
				success : function(data) {
					$(".background_mask").hide();
					refrehFunction();
				},
				error : function() {
					$(".background_mask").hide();
					layer.alert("删除失败,请刷新页面", {
						icon : 2
					});
				}
			});
			layer.close(index);
		}, function() {
		});
	}

	var addBtnEventBind = function() {
		$(".first_part").find(".addBtn").unbind("click").bind("click",
				first_part.addConfigData);
		$(".second_part").find(".addBtn").unbind("click").bind("click",
				second_part.addConfigData);
		$(".third_part").find(".addBtn").unbind("click").bind("click",
				third_part.addConfigData);
	}
</script>
<style>
.first_part .list_table .data_body tr td {
	cursor: pointer;
	background: rgba(152, 245, 255, 1);
}

.first_part .list_table .data_body tr td.active {
	background: rgba(0, 191, 255, 1);
}

.second_part .list_table .data_body tr td {
	cursor: pointer;
	background: rgba(152, 245, 255, 0.6);
}

.second_part .list_table .data_body tr td.active {
	background: rgba(0, 191, 255, 0.6);
}

.third_part .list_table .data_body tr td {
	background: rgba(152, 245, 255, 0.2);
}

.data_list .list_table tr td .config_input {
	width: 100%;
	font-size: 16px;
	background-color: #FAFFBD;
	border: none;
	height: 28px;
	text-indent: 5px;
}
</style>
</head>
<body>
	<div class="background_mask"></div>
	<div style="width: 30%;" class="data_list first_part">
		<table width="100%" class="list_table">
			<tr>
				<th>一级分类</th>
				<th width="130px" class="text_left" style="text-indent: 10px">
					操作
					<input style="margin-left: 25px; width: 60px; height: 28px;" class="addBtn" type="button" value="添加" />
				</th>
			</tr>
			<tbody class="data_body">
			</tbody>
		</table>
	</div>
	<div style="width: 30%;" class="data_list second_part">
		<table width="100%" class="list_table">
			<tr>
				<th>二级分类</th>
				<th width="130px" class="text_left" style="text-indent: 10px">
					操作
					<input style="margin-left: 25px; width: 60px; height: 28px;" class="addBtn" type="button" value="添加" />
				</th>
			</tr>
			<tbody class="data_body">
			</tbody>
		</table>
	</div>
	<div style="width: 40%;" class="data_list third_part">
		<table width="100%" class="list_table">
			<tr>
				<th>赔付理据</th>
				<th width="130px" class="text_left" style="text-indent: 10px">
					操作
					<input style="margin-left: 25px; width: 60px; height: 28px;" class="addBtn" type="button" value="添加" />
				</th>
			</tr>
			<tbody class="data_body">
			</tbody>
		</table>
	</div>
</body>
</html>