<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@include file="/WEB-INF/html/head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>投诉邮件配置表</title>
<link type="text/css" rel="stylesheet" href="${CONFIG.res_url}css/special.css" />
<%-- <link type="text/css" rel="stylesheet" href="${CONFIG.res_url}script/jquery/plugin/jqPagination/css/jqpagination.css" />
<script type="text/javascript" src="${CONFIG.res_url}script/jquery/plugin/jqPagination/jquery.jqpagination.js"></script> --%>
<script type="text/javascript">
	$(function() {

		$(".addBtn").unbind("click").bind("click", addEmailConfig);
		$(".searchBtn").unbind("click").bind("click", showThirdPartHtml);
		showThirdPartHtml();
	})

	//获取展示数据
	var showThirdPartHtml = function() {
		var searchData = {
			"complaint_id" : $(".list_table").attr("complaint_id")
		};
		$.ajax({
			type : "POST",
			url : "upgrade_third_part-getComplaintThirdPartLists",
			data : searchData,
			async : false,
			success : function(data) {
				builConfigList(data);
			}
		});
	}

	var builConfigList = function(data) {
		$(".data_body").empty();
		var html = [];
		$.each(data.resultList,function(index, ele) {
			var trstr = '<tr config_id="'+ele.id+'">';
			trstr += '<td title="' + ele.third_type_name + '">'
					+ ele.third_type_name + '</td>';
			trstr += '<td title="'+ele.third_second_type_name+'">'
					+ ele.third_second_type_name + '</td>';
			trstr += '<td title="' + ele.content + '">' +ele.content + "</td>";
			trstr += '<td><input class="editBtn btn_class" type="button" value="修改"/>'
					+ '<input class="delBtn btn_class" type="button" value="删除"/></td>';
			trstr += '</tr>';
			html.push(trstr);
		})
		$(".data_body").append(html.join());
		$(".delBtn").unbind("click").bind("click", clickDelBtn);
		$(".editBtn").unbind("click").bind("click", clickEditBtn);
	}

	var addEmailConfig = function() {
		openLayer('添加第三方来源', 'upgrade_third_part-getComplaintThirdPart?complaint_id='+$(".list_table").attr("complaint_id"), '400px', '250px');
	}

	var clickDelBtn = function() {
		var configId = $(this).closest("tr").attr("config_id");
		layer.confirm('确定删除此配置吗', function(index) {
			delEmailConfig(configId);
			layer.close(index);
		});
	}

	var clickEditBtn = function() {
		var configId = $(this).closest("tr").attr("config_id");
		openLayer('修改第三方来源', 'upgrade_third_part-getComplaintThirdPart?id=' + configId,
				'400px', '250px');
	}

	var delEmailConfig = function(configId) {
		$.ajax({
			type : "POST",
			url : "upgrade_third_part-delThirdPart",
			data : {
				"third_part_id" : configId
			},
			async : false,
			success : function(data) {
				if (data.retObj == "failed") {
					layer.alert("删除失败", {
						icon : 2
					});
				} else {
					layer.alert("删除成功", {
						icon : 1
					});
				}
				showThirdPartHtml();
			}
		});
	}

	function openLayer(title, url, width, height) {
		parent.layer.open({
			type : 2,
			title : title,
			shadeClose : true,
			shade : false,
			end : showThirdPartHtml,
			maxmin : true,
			area : [ width, height ],
			content : url
		});
	}
</script>
</head>
<body>
	<div class="background_mask"></div>
	<div class="data_list">
		<table width="100%" class="list_table" complaint_id="${complaint_id }">
			<tr>
				<th width="80px">第三方机构</th>
				<th width="80px">地区/媒体</th>
				<th width="60px">升级说明</th>
				<th width="130px" class="text_left" style="text-indent: 10px">操作 <input style="margin-left: 25px; width: 60px;"
						class="addBtn" type="button" value="添加" /></th>
			</tr>
			<tbody class="data_body">
			</tbody>
		</table>
	</div>
</body>
</html>