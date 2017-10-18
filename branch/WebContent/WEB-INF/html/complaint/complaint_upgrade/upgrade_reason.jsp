<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@include file="/WEB-INF/html/head.jsp"%>
<script type="text/javascript">
	$(function() {
		$(".submit_btn").unbind("click").bind("click", upgrade);
	})

	function upgrade() {
		$(".submit_btn").addClass("active");
		if ($("#upgrade_reason").val().length == 0) {
			alert('请选择升级原因');
			$(".submit_btn").removeClass("active");
			return;
		}
		if (confirm('确定升级吗？')) {
			$(".submit_btn").attr("disabled", "disabled");
			$.ajax({
				"type" : "POST",
				"url" : "complaint-upgradeSenior",
				data : {
					"complaintId" : $("#complaintId").val(),
					"upgrade_reason" : $("#upgrade_reason").val(),
					"upgrade_note" : $("#upgrade_note").val()
				},
				success : function(data) {
					window.parent.location.reload();
				},
				error : function() {
					alert("error");
				}
			});
			$(".submit_btn").removeClass("active");
		}
	}
</script>
<style>
.div_head {
	margin-bottom: 10px;
	margin-left: 10px;
}

.note_txt {
	width: 500px;
	height: 80px;
	margin-left: 10px;
}

body, button, input, select, textarea {
	font: 14px/1.5 tahoma, arial, \5b8b\4f53, sans-serif;
}

.submit_btn {
	width: 50px;
	height: 30px;
	background-color: #3bb3e0;
	margin: 10px 0px 0 242px;
	cursor: pointer;
	border-radius: 5px;
	font-size: 14px;
	line-height: 30px;
	text-align: center;
	font-family: '微软雅黑';
	-webkit-box-shadow: 0px 2px 0px #3bb3e0, 0px 9px 25px rgba(0, 0, 0, .7);
	-moz-box-shadow: 0px 2px 0px #3bb3e0, 0px 9px 25px rgba(0, 0, 0, .7);
	box-shadow: 0px 3px 0px #186f8f, 0px 9px 25px rgba(0, 0, 0, .7);
}

.submit_btn.active {
	-webkit-box-shadow: 0px 0px 0px #3bb3e0, 0px 3px 6px
		rgba(0, 0, 0, .9);
	-moz-box-shadow: 0px 0px 0px #3bb3e0, 0px 3px 6px
		rgba(0, 0, 0, .9);
	box-shadow: 0px 0px 0px #186f8f, 0px 3px 6px
		rgba(0, 0, 0, .9);
	position: relative;
	top: 3px;
}
</style>
</HEAD>
<BODY style="overflow: hidden">
	<div class="div_head">
		<input type="hidden" name="complaintId" id="complaintId"
			value="${complaintId}" /> <span>升级原因 ：</span>
		<s:select list="upgradeReasons" headerKey="" headerValue="请选择"
			name="upgrade_reason" />
	</div>
	<div>
		<textarea class="note_txt" id="upgrade_note" placeholder="还想说点什么"
			maxlength="500"></textarea>
	</div>
	<div class="submit_btn">确定</div>
</BODY>