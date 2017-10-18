<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据字典配置</title>
<link type="text/css" rel="stylesheet" href="../../res/css/special.css" />
<script language="javascript" src="../../res/script/jquery/jquery-1.7.1.min.js"></script>
<script language="javascript" src="../../res/script/jquery/plugin/layer/layer.js"></script>
<script type="text/javascript">
	$(function() {
		left_part.buildHtml();
		$(".addBtn").unbind("click").bind("click", addDictionaryData);
	})

	var left_part = {
		buildHtml : function() {
			buildFirstTbodyHtml();
		}
	}

	var right_part = {
		buildHtml : function(dictionary_id) {
			buildSecondTbodyHtml(dictionary_id);
		},
		refresh : function() {
			var thisData_body = $(".right_part").find(".data_body");
			dictionary_id = thisData_body.attr("dictionary_id");
			right_part.buildHtml(dictionary_id);
		}
	}
	
	var buildRightPart = function() {
		var dictionary_id = $(this).attr("dictionary_id");
		right_part.buildHtml(dictionary_id);
	}

	//查询字典配置项
	var buildFirstTbodyHtml = function() {
		var thisData_body = $(".left_part").find(".data_body");
		thisData_body.empty();
		$(".background_mask").show();
		$.ajax({
			type : "POST",
			url : "getDictionaryData",
			data : {
				"father_id" : 0
			},
			success : function(data) {
				var result = [];
				$.each(data, function(index, ele) {
					var trstr = '<tr dictionary_id="'+ele.id+'">';
					trstr += '<td title="' + ele.name + '">' + ele.name
							+ '</td>';
					trstr += '</tr>';
					result.push(trstr);
				})
				thisData_body.append(result.join(""));
				thisData_body.find("tr").unbind("click").bind("click",
						buildRightPart);
				$(".background_mask").hide();
			},
			error : function() {
				$(".background_mask").hide();
				layer.alert("查询失败,请刷新页面", {
					icon : 2
				});
			}
		});

	}

	//根据配置项id查询具体字典项
	var buildSecondTbodyHtml = function(dictionary_id) {
		var thisData_body = $(".right_part").find(".data_body");
		thisData_body.empty();
		thisData_body.attr("dictionary_id", dictionary_id);
		$(".background_mask").show();
		$.ajax({
			type : "POST",
			url : "getDictionaryData",
			data : {
				"father_id" : dictionary_id
			},
			success : function(data) {
				var result = [];
				$.each(data, function(index, ele) {
					var trstr = '<tr dictionary_id="'+ele.id+'">';
					trstr += '<td class="dictionary_name" title="' + ele.name + '">' + ele.name
							+ '</td>';
					trstr += '<td class="operate">'
							+'<input class="editBtn btn_class" type="button" value="修改"/>'
							+ '<input class="delBtn btn_class" type="button" value="删除"/></td>';
					trstr += '</tr>';
					result.push(trstr);
				})
				thisData_body.append(result.join(""));
				$(thisData_body).find(".editBtn").unbind("click").bind("click",
						editDictionaryData);
				$(thisData_body).find(".delBtn").unbind("click").bind("click",
						delDictionaryData);
				$(".background_mask").hide();
			},
			error : function() {
				$(".background_mask").hide();
				layer.alert("查询失败,请刷新页面", {
					icon : 2
				});
			}
		});

	}

	//新增字典项
	var addDictionaryData = function (){
		cancelOtherInputState();
		var thisData_body = $(".right_part").find(".data_body");
		dictionary_id = thisData_body.attr("dictionary_id");
		if(!dictionary_id){
			layer.alert("请选择配置项", {
				icon : 2
			});
			return false;
		}
		var operate_btn = '<tr><td class="dictionary_name"><input class="dictionary_input"></td>'
			+'<td class="operate"><input class="saveBtn btn_class" type="button" value="保存"/>'
			+'<input class="cancelBtn btn_class" type="button" value="取消"/></td></tr>';
		thisData_body.append(operate_btn);
		var currentTr = thisData_body.find(".dictionary_input").closest("tr");
 		currentTr.find(".dictionary_input").unbind("keydown").bind("keydown",function(e){
			if(e.keyCode==13){
				currentTr.find(".saveBtn").click();
			}
		}) 
		currentTr.find(".saveBtn").unbind("click").bind("click",addBtnFunction);
		currentTr.find(".cancelBtn").unbind("click").bind("click",right_part.refresh);
		currentTr.find(".dictionary_input").focus();
	}
	
	//编辑字典值
	var editDictionaryData = function (){
		cancelOtherInputState();
		var dictionary_name_td = $(this).closest("tr").find("td.dictionary_name");
		var operate_td = dictionary_name_td.siblings();
		var dictionary_input = '<input class="dictionary_input" value="'+dictionary_name_td.text()+'"/>';
		dictionary_name_td.html(dictionary_input);
		var operate_btn = '<input class="saveBtn btn_class" type="button" value="保存"/>'
			+'<input class="cancelBtn btn_class" type="button" value="取消"/>';
		operate_td.html(operate_btn);
		dictionary_name_td.find(".dictionary_input").unbind("keydown").bind("keydown",function(e){
			if(e.keyCode==13){
				operate_td.find(".saveBtn").click();
			}
		})
		operate_td.find(".saveBtn").unbind("click").bind("click",saveBtnFunction);
		operate_td.find(".cancelBtn").unbind("click").bind("click",right_part.refresh);
		dictionary_name_td.find(".dictionary_input").focus();
	}
	
	var cancelOtherInputState = function(){
		var thisData_body = $(".right_part").find(".data_body");
		$.each(thisData_body.find(".dictionary_input"),function(index,ele){
			var inputTr = $(ele).closest("tr");
			if(!inputTr.attr("dictionary_id")){
				inputTr.remove();
			}else{
				var dictionary_name = inputTr.find(".dictionary_name").attr("title");
				inputTr.find("td.dictionary_name").attr("title",dictionary_name).text(dictionary_name);
				var operate = '<input class="editBtn btn_class" type="button" value="修改"/>'
					+ '<input class="delBtn btn_class" type="button" value="删除"/>';
				inputTr.find("td.operate").html(operate);
				inputTr.find(".editBtn").unbind("click").bind("click",editDictionaryData);
				inputTr.find(".delBtn").unbind("click").bind("click",delDictionaryData);
			}
		})
	}	
	
	//添加字典项
	var addBtnFunction = function(){
		var sumbitTr = $(this).closest("tr");
		var dictionary_input = sumbitTr.find(".dictionary_input");
		var dictionary_name = dictionary_input.val();
		if(!validateSumbitDate(dictionary_name)){
			dictionary_input.focus();
			return;
		}
		var submitData = {
			"father_id" : sumbitTr.closest(".data_body").attr("dictionary_id"),
			"name" : dictionary_name
		};
		sumbitDictionaryData(submitData);
	}
	
	//修改字典项
	var saveBtnFunction = function (){
		var sumbitTr = $(this).closest("tr");
		var dictionary_input = sumbitTr.find(".dictionary_input");
		var dictionary_name = dictionary_input.val();
		if(!validateSumbitDate(dictionary_name)){
			dictionary_input.focus();
			return;
		}
		var submitData = {
			"id" : sumbitTr.attr("dictionary_id"),
			"name" : dictionary_name
		};
		sumbitDictionaryData(submitData);
	}
	
	var sumbitDictionaryData = function (submitData){
		$(".background_mask").show();
		$.ajax({
			type : "POST",
			url : "addOrUpdateDictionaryData",
			contentType: "application/x-www-form-urlencoded; charset=utf-8",
			data : submitData,
			success : function(data) {
				$(".background_mask").hide();
				layer.alert("保存成功", {
					icon : 1
				});
				right_part.refresh();
			},
			error : function() {
				$(".background_mask").hide();
				layer.alert("保存失败,请刷新页面", {
					icon : 2
				});
			}
		});
	}
	
	//验证输入
	var validateSumbitDate = function(dictionary_name) {
		var result = true;
		if(dictionary_name.trim().length==0){
			layer.alert("请填写字典项", {
				icon : 2
			});
			result = false;
		}
		return result;
	}
	
	//删除字典项
	var delDictionaryData = function() {
		var dictionary_id =$(this).closest("tr").attr("dictionary_id");
		layer.confirm('确定删除吗？',function(index){
			$(".background_mask").show();
			$.ajax({
				type : "POST",
				url : "deleteDictionaryData",
				data : {
					"dictionary_id" : dictionary_id
				},
				success : function(data) {
					$(".background_mask").hide();
					right_part.refresh();
				},
				error : function() {
					$(".background_mask").hide();
					layer.alert("删除失败,请刷新页面", {
						icon : 2
					});
				}
			});
			layer.close(index);
		},function(){});
	}
</script>
<style>
.left_part .list_table .data_body tr td{
    cursor: pointer;
    background: rgba(152, 245, 255,1.5);
}
</style>
</head>
<body>
	<div class="background_mask"></div>
	<div style="width: 30%;" class="data_list left_part">
		<table width="100%" class="list_table">
			<tr>
				<th width="90px">配置项</th>
			</tr>
			<tbody class="data_body">
			</tbody>
		</table>
	</div>
	<div style="width: 70%;" class="data_list right_part">
		<table width="100%" class="list_table">
			<tr>
				<th>字典项</th>
				<th width="130px" class="text_left" style="text-indent: 10px">操作 <input
						style="margin-left: 25px; width: 60px; height: 28px;" class="addBtn" type="button" value="添加" /></th>
			</tr>
			<tbody class="data_body">
			</tbody>
		</table>
	</div>
</body>
</html>