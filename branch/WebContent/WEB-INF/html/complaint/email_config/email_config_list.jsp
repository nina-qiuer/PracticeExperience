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
		$(".searchBtn").unbind("click").bind("click", showEmailConfigHtml);
		$(".clearBtn").unbind("click").bind("click", clearFrom);
		showEmailConfigHtml();
	})
	
	var clearFrom = function() {
		$('#search_from')[0].reset();
	}

	//获取展示数据
	var showEmailConfigHtml = function() {
		//$('#main').empty();//清空背景
		$(".background_mask").show();
		$.ajax({
			type : "POST",
			url : "email_config-getEmailConfigData",
			data : $('#search_from').serialize(),
			async : true,
			success : function(data) {
				builConfigList(data);
				$(".background_mask").hide();
			}
		});
	}

	var builConfigList = function(data) {
		$(".data_body").empty();
		var html = [];
		$.each(data.resultList,function(index, ele) {
			var deal_depart = ele.deal_depart == '' ? '全部'
					: ele.deal_depart;
			var complaint_level = ele.complaint_level == 0 ? '1,2,3'
					: ele.complaint_level;
			var come_from = ele.come_from == '' ? '所有来源'
					: ele.come_from;
			var mail_type = ele.mail_type == 1 ? '收件人' : '抄送人';
			var trstr = '<tr config_id="'+ele.id+'">';
			trstr += '<td class="text_center" title="' + deal_depart + '">'
					+ deal_depart + '</td>';
			trstr += '<td>' + complaint_level + '</td>';
			trstr += '<td title="' + come_from + '">'
					+ come_from + '</td>';
			trstr += '<td title="' + ele.class_brand_parent_name + '">'
					+ ele.class_brand_parent_name + '</td>';
			trstr += '<td title="' + ele.class_brand_name + '">'
					+ ele.class_brand_name + '</td>';
			trstr += '<td title="' + ele.product_new_line_type_name + '">'
					+ ele.product_new_line_type_name + '</td>';
			trstr += '<td title="' + ele.dest_group_name + '">'
					+ ele.dest_group_name + '</td>';
			trstr += '<td class="text_center">' + mail_type
					+ '</td>';
			trstr += '<td title="' + ele.emails + '">'
					+ ele.emails + '</td>';
			trstr += '<td title="' + ele.content + '">'
					+ ele.content + '</td>';
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
		openLayer('添加邮件配置', 'email_config-addOrEditConfig', '400px', '540px');
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
		openLayer('修改邮件配置', 'email_config-addOrEditConfig?id=' + configId,
				'400px', '540px');
	}

	var delEmailConfig = function(configId) {
		$.ajax({
			type : "POST",
			url : "email_config-delEmailConfig",
			data : {
				"entity.id" : configId
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
				showEmailConfigHtml();
			}
		});
	}

	function openLayer(title, url, width, height) {
		layer.open({
			type : 2,
			title : title,
			shadeClose : true,
			shade : false,
			end : showEmailConfigHtml,
			maxmin : true,
			area : [ width, height ],
			content : url
		});
	}
	
	function tabFuncdd(showId,navObj){ 
		$(".tab_part").hide();
		$("#pici_tab .menu_on").removeClass("menu_on");
		$(navObj).addClass("menu_on");
		$(showId).show();
		return false;
	}
</script>
</head>
<body>
	<div id="pici_tab" class="clear">
		<ul>
			<li class="" onclick="tabFuncdd('#001',this)">
			<s class="rc-l"></s><s class="rc-r"></s><a href="appoint_manager">负责人</a>
			</li>
			<li class="menu_on"><s class="rc-l"></s>
			<s class="rc-r"></s><a href="#">邮件配置</a>
			</li>
		</ul>
	</div>
	<div class="background_mask"></div>
	<div class="search_list">
		<form id="search_from">
			<div class="search_left">
				<ul>
					<li class="search_words">收件/抄送：</li>
					<li><select name="mail_type">
							<option value="">全部</option>
							<option value="1">收件人</option>
							<option value="2">抄送人</option>
						</select></li>
				</ul>
				<ul>
					<li class="search_words">处理岗：</li>
					<li><s:select name="deal_depart" list="dealDepartments" headerKey="" headerValue="全部" /></li>
				</ul>
				<ul>
					<li class="search_words">投诉等级：</li>
					<li><select name="complaint_level">
							<option value="">全部</option>
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
						</select></li>
				</ul>
				<ul>
					<li class="search_words">投诉来源：</li>
					<li><select name="come_from">
							<option value="">所有来源</option>
							<option value="网站">网站</option>
							<option value="门市">门市</option>
							<option value="当地质检">当地质检</option>
							<option value="来电投诉">来电投诉</option>
							<option value="CS邮箱">CS邮箱</option>
							<option value="回访">回访</option>
							<option value="旅游局">旅游局</option>
							<option value="微博">微博</option>
							<option value="其他">其他</option>
						</select></li>
				</ul>
				<ul>
					<li class="search_words">一级品类：</li>
					<li><select class="product_type_select" name="class_brand_parent_id" product_level="2" id="class_brand_parent">
							<option value="">全部</option>
							<c:forEach items="${classBrandParentList}" var="classBrandParentInfo">
								<option value="${classBrandParentInfo.id }">${classBrandParentInfo.name}</option>
							</c:forEach>
						</select></li>
				</ul>
			</div>
		</form>
		<div class="search_right">
			<input style="width: 60px; height: 30px; top: 17px; margin-left: 80px; cursor: pointer;" class="searchBtn" type="button"
				value="查询" />
			<input style="width: 60px; height: 30px; top: 17px; margin-left: 30px; cursor: pointer;" class="clearBtn" type="button"
				value="清空" />
		</div>
	</div>
	<div class="data_list">
		<table width="100%" class="list_table">
			<tr>
				<th width="90px">处理岗</th>
				<th width="60px">投诉等级</th>
				<th width="60px">投诉来源</th>
				<th width="60px">一级品类</th>
				<th width="60px">二级品类</th>
				<th width="80px">目的地大类</th>
				<th width="80px">目的地分组</th>
				<th width="70px">收件/抄送</th>
				<th>邮箱</th>
				<th width="200px">备注</th>
				<th width="130px" class="text_left" style="text-indent: 10px">操作 <input style="margin-left: 25px; width: 60px;"
						class="addBtn" type="button" value="添加" /></th>
			</tr>
			<tbody class="data_body">
			</tbody>
		</table>
	</div>
</body>
</html>