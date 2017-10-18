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
	var parentname;
	$(function() {
		firstPayoutBase.buildHtml();
		$(".submit_btn").unbind("click").bind("click", submitFunction);
		parentname = '${parentname}';
	})

	var firstPayoutBase = {
		buildHtml : function() {
			$(".background_mask").show();
			$.ajax({
				type : "POST",
				url : "getPayoutBaseData",
				success : function(data) {
					buildPayoutBaseHtml(data, 0);
				},
				error : function() {
					layer.alert("查询失败,请刷新页面", {
						icon : 2
					});
					$(".background_mask").hide();
				}
			});
		}
	}

	var secondPayoutBase = {
		buildHtml : function(fatherId, title) {
			$(".background_mask").show();
			$.ajax({
				type : "POST",
				url : "getPayoutBaseData",
				data : {
					"father_id" : fatherId
				},
				success : function(data) {
					buildSecondPayoutBaseHtml(data, fatherId, title);
				},
				error : function() {
					layer.alert("查询失败,请刷新页面", {
						icon : 2
					});
					$(".background_mask").hide();
				}
			});
		},
		removeHtml : function(fatherId) {
			var secondPayouyTr = $('.second_payout_base tr[father_id='
					+ fatherId + ']');
			$.each(secondPayouyTr.find("input"), function(i, ele) {
				thirdPayoutBase.removeHtml($(ele).val());
			})
			secondPayouyTr.remove();
		}
	}

	var thirdPayoutBase = {
		buildHtml : function(fatherId, title) {
			$(".background_mask").show();
			$.ajax({
				type : "POST",
				url : "getPayoutBaseData",
				data : {
					"father_id" : fatherId
				},
				success : function(data) {
					buildThirdPayoutBaseHtml(data, fatherId, title);
				},
				error : function() {
					layer.alert("查询失败,请刷新页面", {
						icon : 2
					});
					$(".background_mask").hide();
				}
			});
		},
		removeHtml : function(fatherId) {
			$('.third_payout_base tr[father_id=' + fatherId + ']').remove();
		}
	}

	var buildPayoutBaseHtml = function(data, fatherId) {
		$(".first_payout_base").append(getPayoutBaseElement(data, fatherId, 6));
		firstPayoutCheckBoxClick();
		$(".background_mask").hide();
	}

	var buildSecondPayoutBaseHtml = function(data, fatherId, title) {
		$(".second_payout_base").append(
				getPayoutBaseElement(data, fatherId, 3, title));
		secondPayoutCheckBoxClick();
		$(".background_mask").hide();
	}

	var buildThirdPayoutBaseHtml = function(data, fatherId, title) {
		$(".third_payout_base").append(
				getPayoutBaseElement(data, fatherId, 1, title));
		$(".background_mask").hide();
	}

	var getPayoutBaseElement = function(data, fatherId, newlinenum, title) {
		var result = [];
		result.push('<tr father_id="'+fatherId+'">');
		if (title != undefined) {
			result.push('<td style="text-indent:5px;">' + title + '</td>')
		}
		result.push('<td>')
		$.each(data, function(index, ele) {
			var checkbox = '<label title="'+ele.data.payoutBase+'">'
					+ '<input type="checkbox" value="'+ele.data.id+'"/>'
					+ ele.data.payoutBase + '</label>';
			if (index != 0 && index % newlinenum == 0) {
				result.push('</td><td>');
			}
			result.push(checkbox);
		})
		result.push('</td></tr>');
		return result.join("");
	}

	var firstPayoutCheckBoxClick = function() {
		$(".first_payout_base").find("input[type='checkbox']").unbind("click")
				.bind("click", function() {
					if ($(this).attr('checked') == "checked") {
						var father_id = $(this).val();
						var title = $(this).closest("label").attr("title");
						secondPayoutBase.buildHtml(father_id, title);
					} else {
						var father_id = $(this).val();
						secondPayoutBase.removeHtml(father_id);
					}
				})
	}

	var secondPayoutCheckBoxClick = function() {
		$(".second_payout_base").find("input[type='checkbox']").unbind("click")
				.bind("click", function() {
					if ($(this).attr('checked') == "checked") {
						var father_id = $(this).val();
						var title = $(this).closest("tr").find("td").eq(0).text();
						title += "  ---   "+$(this).closest("label").attr("title");
						thirdPayoutBase.buildHtml(father_id, title);
					} else {
						var father_id = $(this).val();
						thirdPayoutBase.removeHtml(father_id);
					}
				})
	}

	var submitFunction = function() {
		var display = '';
		if ($(".first_payout_base input[type='checkbox']:checked").length == 0) {
			layer.alert("请选择一级分类", {
				icon : 2
			});
			return;
		}
		if ($(".second_payout_base input[type='checkbox']:checked").length == 0) {
			layer.alert("请选择二级分类", {
				icon : 2
			});
			return;
		}
		if ($(".third_payout_base input[type='checkbox']:checked").length == 0) {
			layer.alert("请选择赔付理据", {
				icon : 2
			});
			return;
		}
		$.each($(".third_payout_base input[type='checkbox']:checked"),
				function(i, ele) {
					if (i > 0) {
						display += "</br>";
					}
					display += $(this).closest("label").text();

				})
		var pname = parentname;
		pname = pname.toString();
		$('[name="' + pname + '"]', parent.document).val(display);
		var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
		parent.layer.close(index); //再执行关闭
	}
</script>
<style>
.data_list .list_table tr td {
	font-size: 12px;
	font-family: 微软雅黑;
	background: none;
	height: 40px;
	line-height: 40px;
	border: none;
	text-overflow: ellipsis;
	overflow: hidden;
	white-space: nowrap;
	float: left;
	border: none;
}

.list_table td label {
	text-overflow: ellipsis;
	white-space: nowrap;
	display: inline-block;
	overflow: hidden;
	white-space: nowrap;
}

.list_table .first_payout_base td label {
	width: 110px;
}

.list_table .second_payout_base td label {
	width: 220px;
}

.list_table .third_payout_base td label {
	width: 660px;
}

.submit_btn {
	margin: 10px 0px 0 375px;
}
</style>
</head>
<body style="background-color: rgba(152, 245, 255, 0.2)">
	<div class="background_mask"></div>
	<div class="data_list left_part">
		<table width="100%" class="list_table">
			<tr class="first_payout_base">
				<th width="90px">一级分类</th>
			</tr>
			<tr class="second_payout_base">
				<th width="90px">二级分类</th>
			</tr>
			<tr class="third_payout_base">
				<th width="90px">赔付理据</th>
			</tr>
		</table>
		<div class="submit_btn">保存</div>
	</div>
</body>
</html>