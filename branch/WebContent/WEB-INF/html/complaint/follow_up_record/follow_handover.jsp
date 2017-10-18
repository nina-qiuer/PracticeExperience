<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp"%>
<script type="text/javascript"
	src="${CONFIG.res_url}/script/jquery/plugin/jquery.form.js"></script>
<link type="text/css" rel="stylesheet" href="${CONFIG.res_url}css/special.css" />
<script type="text/javascript">
	$(function(){
		$(".assign_self").unbind("click").bind("click", function() {
			AssginToSelf();
		})
	})

	function handover() {
		layer.confirm('确定交接吗？',function(index){submit();layer.close(index);},function(){});
	}
	
	//提交
	function submit(){
		$("#handOverBtn").attr("disabled","disabled");
		$("#note").val(Trim($('#note').val(),'g'));
		var note = $("#note").val().length>0?note = $("#note").val():$("#note").attr("placeholder");
		var url = $("#dealBySelf").val()==1?"complaint-handoverAndAutoAssign":"complaint-handover";
		$.ajax({
			"type" : "POST",
			"url" : url,
			data : {
				"complaintId" : $("#complaintId").val(),
				"orderId" : $("#orderId").val(),
				"note": note
			},
			success : function(data) {
				window.parent.location.reload();
			},
			error : function() {
				layer.alert("交接失败,请尝试刷新页面");
				$("#handOverBtn").removeAttr("disabled");
			}
		});
	}
	
	//分配给自己效果
	function AssginToSelf() {
		var timeOutTime = 100;
		if ($(".seebutton").hasClass("active")) {
			$(".seecontent").animate({
				left : "26px"
			}, timeOutTime);
			$(".seebutton").animate({
				left : "0px"
			}, timeOutTime);
			$(".seebutton").removeClass("active");
			$(".assign_self").removeClass("active");
			$(".seecontent").text("否");
			$("#dealBySelf").attr("value", 0);
			$("#note").attr("placeholder","下班交接");
		} else {
			$(".seecontent").animate({
				left : "20px"
			}, timeOutTime);
			$(".seebutton").animate({
				left : "47px"
			}, timeOutTime);
			$(".assign_self").addClass("active");
			$(".seecontent").text("是");
			$(".seebutton").addClass("active");
			$("#dealBySelf").attr("value", 1);
			$("#note").attr("placeholder","大夜交接");
		}
	}
</script>
</HEAD>
<BODY style="overflow-y:hidden">
	<div>
		<s:hidden name="dealBySelf" id="dealBySelf" value="0" />
		<form name="form" id="form" method="post"
			enctype="multipart/form-data" action="complaint-handover">
			<input type="hidden" name="complaintId" id="complaintId"
				value="${complaintId}" />
			<input type="hidden" name="orderId" id="orderId"
				value="${orderId}" />
			<table class="datatable" width="100%">
				<tr>
					<th align="right">本次跟进内容：</th>
					<td><textarea name="note" id="note" cols="45" rows="5" placeholder="下班交接"></textarea>
					</td>
				</tr>
				<c:if test="${canBigNight!=null}">
				<tr>
					<th align="right">是否大夜交接：</th>
					<td><label class="assign_self"><div class="seebutton"></div>
							<div class="seecontent">否</div></label></td>
				</tr>
				</c:if>
				<tr>
					<th>&nbsp;</th>
					<td><input class="pd5" type="button" id="handOverBtn" onclick="handover()"
						value="确定" /></td>
				</tr>
			</table>
		</form>
	</div>