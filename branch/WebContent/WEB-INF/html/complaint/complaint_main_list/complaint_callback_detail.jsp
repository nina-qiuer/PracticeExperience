<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp"%>
<link type="text/css" href="${CONFIG.res_url}css/bui.css"
	rel="stylesheet" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
.common-box {
	border: 1px solid #8CBFDE;
	margin: 0 0 10px 0;
}

.common-box-hd {
	margin-top: 1px;
	padding-left: 10px;
	background: #C6E3F1;
	color: #005590;
	font-size: 14px;
	font-weight: bold;
	height: 25px;
	line-height: 25px;
	border-bottom: 1px solid #8CBFDE;
	position: relative;
}

.common-box-hd span.title2 {
	cursor: pointer;
	display: inline-block;
	line-height: 25px;
	margin-right: 15px;
}

.callform .cmpPerson, .callBackTime {
	width: 137px;
	font-size: 12px;
	font-family: Arial, "微软雅黑";
}

.callform .cmpPhone {
	width: 138px;
}

.callform .content {
	margin: 0px;
	width: 373px;
	height: 109px;
	resize: none;
	font-size: 12px;
	font-family: Arial, "微软雅黑";
}
</style>
<script type="text/javascript">
	var callBackTime;
	$(function() {
		var orginalTime = '${orginalTime}';
		$(".callBackTime").val(orginalTime);
		orginalTime = orginalTime.replaceAll("-","/");
		callBackTime = new Date(orginalTime);

		var addtimer = setInterval(dateAddBySecond, 1000);
		$(".callBackTime").one("focus", function() {
			clearInterval(addtimer);
		});
	})

	function submitCallBack(input) {
		var cmpPerson = $('.cmpPerson').val();
		var cmpPhone = $('.cmpPhone').val();
		var callBackTime = $('.callBackTime').val();
		if ($.trim(cmpPerson) == '') {
			layer.alert("请填写投诉人姓名", {
				icon : 2
			});
			return false;
		}
		if ($.trim(cmpPhone) == '') {
			layer.alert("请填写投诉人电话", {
				icon : 2
			});
			return false;
		}
		if ($.trim(callBackTime) == '') {
			layer.alert("请选择回呼时间", {
				icon : 2
			});
			return false;
		}
		disableButton(input);
		$.ajax({
			url : 'complaint-callBack',
			data : $('.callform').serialize(),
			type : 'post',
			dataType : 'json',
			cache : false,
			success : function(result) {
				if (result) {
					enableButton(input);
					if (result.retObj == "success") {
						alert("提交成功");
						parent.layer.closeAll();
					} else {
						alert(result.resMsg);
					}
				}
			}
		});

	}

	function dateAddBySecond() {
		callBackTime.setSeconds(callBackTime.getSeconds() + 1);
		var dateStr = callBackTime.Format("yyyy-MM-dd hh:mm:ss");
		$(".callBackTime").val(dateStr);
	}
</script>
<title>回呼</title>
</head>
<body>
	<div class="common-box">
		<form name="callform" class="callform">
			<input type="hidden" name="cmpId" value="${complaint.id}">
			<table width="100%" class="listtable" style="TABLE-LAYOUT: fixed;">
				<tr>
					<th width="80">投诉人姓名</th>
					<td><input type="text" name="cmpPerson" class="cmpPerson"
						maxlength="30" value="${complaint.cmpPerson }" /></td>
					<th width="80">投诉人电话</th>
					<td><input type="text" name="cmpPhone" class="cmpPhone"
						maxlength="20" value="${complaint.cmpPhone}" /></td>
				</tr>
				<tr>
					<th width="80">要求回呼时间</th>
					<td colspan="3"><input type="text"
						class="MyWdate datePiker callBackTime" name="callBackTime"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"><span
						style="color: red; margin-left: 6px;">若未指定回呼时间，默认为30分钟</span></td>
				</tr>
				<tr>
					<th width="80">回呼备注</th>
					<td colspan="3"><textarea rows="6" maxlength="800"
							name="content" class="content"></textarea></td>
				</tr>
				<tr>
					<th width="80">操作</th>
					<td colspan="3"><input type="button" name="submitBtn"
						value="提交" onclick="submitCallBack(this)"></td>
				</tr>
			</table>
		</form>
	</div>
</body>
