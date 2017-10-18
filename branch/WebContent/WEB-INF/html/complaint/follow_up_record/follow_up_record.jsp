<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp"%>
<script type="text/javascript" src="${CONFIG.res_url}/script/jquery/plugin/jquery.form.js"></script>
<script type="text/javascript">
	$(document).ready(
			function() {
				var options = {
					beforeSubmit : check_follow_form, // pre-submit callback 
					success : success_function, // post-submit callback 
				};
				// bind form using 'ajaxForm' 
				$('#form').ajaxForm(options);
				$("input[name='entity.contactType']").unbind("click").bind(
						"click", contactTypeClick);
				$("input[name='contactPerson']").unbind("click").bind(
						"click", contactPersonClick);
				$("input[name='contactResult']").unbind("click").bind("click",
						contactResultClick);
				$(".follow_note_checkbox").unbind("click").bind("click",
						followNoteCheckboxFunction);
				$(".submitBtn").unbind("click").bind("click",
						submitDataFunction);
			});

	function check_follow_form() {
		var time = $('#time').val();
		if (time == '') {
			alert('下次跟进时间不能为空');
			return false;
		}
		$('#button').attr('disabled', 'true');
		$('#isSubmit').val(1);
		return true;
	}

	function success_function() {
		if ($("#sendSms:checked").length > 0) {
			return false;
		}
		parent.location.replace(parent.location.href);
	}

	function sendSMS() {
		var smsTel = $('#smsTel').val();
		var smsContent = $('#smsContent')
				.val()
				.replace(
						"/\r\n|\r|\n|\n\r|\t|\t\r|\t\n|\n\t|\r\t|\t\r\n|\t\n\r|\r\t\n|\n\t\r|\n\r\t|\r\n\t/",
						"");
		if (!confirm("是否要发送短信？")) {
			return false;
		}
		if (smsTel == '') {
			alert("手机号不能为空");
			return false;
		}
		var reg = /^0?1[3|4|5|8][0-9]\d{8}$/;
		if (!reg.test(smsTel)) {
			alert("手机号码有误~");
			return false;
		}
		;
		if (smsContent == '') {
			alert("短信内容不能为空");
			return false;
		}
		if (smsContent.length > 250) {
			alert("短信内容不能超过250个字");
			return false;
		}
		var param = {
			"smsTel" : smsTel,
			"smsContent" : smsContent,
			"complaintId" : $('#complaintId').val()
		};
		$.ajax({
			type : "POST",
			async : false,
			url : "follow_up_record-sendSMS",
			data : param,
			success : function(data) {
				var json = jQuery.parseJSON(data);
				if (json.num > 0) {
					alert("短信发送成功(" + json.num + "条)");
					parent.location.replace(parent.location.href);
				}
				if (json.num <= 0) {
					if (json.num == -1) {
						alert("两天内不能重复发送相同内容");
					} else {
						alert("短信发送失败，请确认后发送");
					}

				}
			}
		});
	}

	function isSendSms() {
		if ($("#sendSms:checked").length > 0) {
			$("#isSand").show();
		} else {
			$("#isSand").hide();
		}
	}

	function contactTypeClick() {
		var contactType = $("[name='entity.contactType']:checked").val();
		
		if (contactType== 0) {
			//当电话跟进时展示电话跟进选项
			$(".contact_phone .no_result").show();
			$(".contact_phone th").text("联系号码：");
			if ($('input[name="contactResult"]:checked').length > 0) {
				//联系不上将填写内容隐藏
				$(".follow_note_tr").hide();
			}
			
		} else {
			if(contactType==1){
				$(".contact_phone th").text("邮箱地址：");
			}else{
				$(".contact_phone th").text("联系方式：");
			}
			$(".contact_phone .no_result").hide();
			followNoteTriggle();
		}
	}
	
	function contactPersonClick(){
		//当电话跟进且未联系不上时  切换填写内容
		if ($("[name='entity.contactType']:checked").val() != 0
				||$('input[name="contactResult"]:checked').length == 0) {
			if ($("[name='contactPerson']:checked").val() == "客人") {
				$(".other_follow").hide();
				$(".guest_follow").show();
			}else{
				$(".other_follow").show();
				$(".guest_follow").hide();
			}
		}
	}

	function contactResultClick() {
		if ($(this).hasClass("active")) {
			$(this).removeClass("active");
			$(this).removeAttr("checked");
			followNoteTriggle();
		} else {
			$(this).siblings().removeClass("active");
			$(this).addClass("active");
			$(".follow_note_tr").hide();
		}
	}

	//具体填写项目显示
	function followNoteTriggle(){
		if ($("[name='contactPerson']:checked").val() == "客人") {
			$(".follow_note_tr").not(".other_follow").show();
			$(".other_follow").hide();
		}else{
			$(".follow_note_tr").not(".guest_follow").show();
			$(".guest_follow").hide();
		}
	}
	
	var followNoteCheckboxFunction = function() {
		if ($(this).hasClass("active")) {
			$(this).removeClass("active");
			$(this).siblings().show();
		} else {
			$(this).addClass("active");
			$(this).siblings().hide();
		}
	}

	var submitDataFunction = function() {
		if (!sumbitValidate()) {
			return;
		}
		var submitData = $('#form').serializeObject();
		submitData = $.extend(submitData, getFollowUpRecordNote());
		$.ajax({
			type : "POST",
			url : "follow_up_record-save",
			data : submitData,
			success : function(data) {
				window.parent.location.reload();
				parent.layer.closeAll();
			},
			error : function() {
				layer.alert("提交失败,请刷新页面", {
					icon : 2
				});
			}
		});
	}

	var getFollowUpRecordNote = function() {
		//跟进方式文本添加
		var note = '【跟进方式】'
				+ $("input[name='entity.contactType']:checked").attr(
						"textValue") + '；';
		//沟通角色文本添加
		note += '【沟通角色】' + $("input[name='contactPerson']:checked").val() + '；';
		//联系方式文本添加
		var contactPhoneText = '【'+$("#contactPhone").closest("tr").find("th").text().replace("：","")+ '】';
		note += contactPhoneText + $("#contactPhone").val();
		if ($("[name='entity.contactType']:checked").val() == 0) {
			if ($('input[name="contactResult"]:checked').length > 0) {
				note += ' ' + $('input[name="contactResult"]:checked').val();
				note += '；';
			} else {
				note += '；';
				$.each($(".follow_note_tr:visible").find(".follow_note_checkbox").not(
						"input:checked"), function(index, ele) {
					var tempNote = '【' + $(ele).attr("textValue") + '】';
					tempNote += $(ele).siblings("textarea").val() + '；';
					note += tempNote;
				})
			}
		} else {
			note += '；';
			$.each($(".follow_note_tr:visible").find(".follow_note_checkbox").not(
					"input:checked"), function(index, ele) {
				var tempNote = '【' + $(ele).attr("textValue") + '】';
				tempNote += $(ele).siblings("textarea").val() + '；';
				note += tempNote;
			})
		}

		var result = {
			"entity.note" : note
		}
		return result;
	}

	var sumbitValidate = function() {
		var result = true;
		var needValidateContent = true;
		if ($("#contactPhone").val().trim().length == 0) {
			var note = $("#contactPhone").closest("tr").find("th").text().replace("：","");
			result = false;
			layer.alert("请填写"+note, {
				icon : 2
			});
		}
		if ($("input[name='entity.contactType']:checked").val() == 0) {
			if ($("input[name='contactResult']:checked").length > 0) {
				needValidateContent = false;
			}
		}
		if (result && needValidateContent) {
			$.each($(".follow_note_tr:visible").find(".follow_note_checkbox").not(
					"input:checked"), function(index, ele) {
				if ($(ele).siblings("textarea").val().trim().length == 0) {
					var textValue = $(ele).attr("textValue");
					var alerttext = '请填写' + textValue + '或勾选"' + $(ele).val()
							+ '"';
					layer.alert(alerttext, {
						icon : 2
					});
					result = false;
					//each方法打破循环使用 不是方法的返回值
					return false;
				}
			})
		}
		return result;
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
<style>
.follow_note {
	width: 265px;
}
</style>
</HEAD>
<BODY>
	<h2>${request.formTitle}</h2>
	<form name="form" id="form" method="post" enctype="multipart/form-data" action="follow_up_record-save">
		<input type="hidden" id="isSubmit" value="0" />
		<input type="hidden" name="entity.complaintId" id="complaintId" value="${entity.complaintId}" />
		<input type="hidden" name="entity.orderId" id="refer_to" value="${entity.orderId}" />
		<table class="datatable" width="100%">
			<tr>
				<th align="right">本次跟进方式：</th>
				<td id="contactType">
					<input type="radio" value="0" name="entity.contactType" textValue="电话" checked="checked">
					电话
					<input type="radio" value="1" name="entity.contactType" textValue="邮件" />
					邮件
					<input type="radio" value="2" name="entity.contactType" textValue="第三方沟通工具" />
					第三方沟通工具
					<c:if test="${user.id == entity.deal || user.id == entity.associater}">
						<input type="checkbox" id="sendSms" onchange="isSendSms()">是否发送短信
					</c:if>
				</td>
			</tr>
			<tr>
				<th align="right">沟通角色：</th>
				<td id="contactType">
					<input type="radio" value="客人" name="contactPerson" checked="checked">
					客人
					<input type="radio" value="供应商" name="contactPerson">
					供应商
					<input type="radio" value="我司人员" name="contactPerson">
					我司人员
					<input type="radio" value="其他" name="contactPerson">
					其他
				</td>
			</tr>
			<tr class="contact_phone">
				<th align="right" rows="2">联系号码：</th>
				<td>
					<input style="border: 1px solid #999999;" name="contactPhone" id="contactPhone" maxlength="50"/>
					</br>
					<div class ="no_result">
						<input type="radio" value="无人接听" name="contactResult">
						无人接听
						<input type="radio" value="挂断" name="contactResult">
						挂断
						<input type="radio" value="占线" name="contactResult">
						占线
						<input type="radio" value="无法接通" name="contactResult">
						无法接通
						<input type="radio" value="空号" name="contactResult">
						空号
					</div>
				</td>
			</tr>
			<tr class="follow_note_tr guest_follow">
				<th align="right">客人诉求：</th>
				<td id="appeal">
					<input class="follow_note_checkbox" value="无新增事宜" type="checkbox" textValue="客人诉求" />
					无新增事宜</br>
					<textarea class="follow_note" name="appeal" cols="45" maxlength="500" placeholder="最多填写500字符"></textarea>
				</td>
			</tr>
			<tr class="follow_note_tr">
				<th align="right">核实内容：</th>
				<td id="content">
					<input class="follow_note_checkbox" value="暂无" type="checkbox" textValue="核实内容" />
					暂无</br>
					<textarea class="follow_note" name="content" cols="45" maxlength="500" placeholder="最多填写500字符"></textarea>
				</td>
			</tr>
			<tr class="follow_note_tr">
				<th align="right">目前方案：</th>
				<td id="plan">
					<input class="follow_note_checkbox" value="暂无" type="checkbox" textValue="目前方案" />
					暂无</br>
					<textarea class="follow_note" name="plan" cols="45" maxlength="500" placeholder="最多填写500字符"></textarea>
				</td>
			</tr>
			<tr class="follow_note_tr other_follow" style="display:none;">
				<th align="right">赔付理据：</th>
				<td id="payment">
					<input class="follow_note_checkbox" value="暂无" type="checkbox" textValue="赔付理据" />
					暂无</br>
					<textarea class="follow_note" name="payment" cols="45" maxlength="500" placeholder="最多填写500字符"></textarea>
				</td>
			</tr>
			<tr>
				<th>&nbsp;</th>
				<td>
					<input class="pd5 submitBtn" type="button" value="确定" />
				</td>
			</tr>
		</table>
		<table id="isSand" style="display: none" class="datatable" width="100%">
			<tr>
				<th align="right">发送短信号码：</th>
				<td>
					<input type="text" id="smsTel" value="${entity.tel}" />
				</td>
			</tr>
			<tr>
				<th align="right">短信内容：</th>
				<td>
					<textarea id="smsContent" cols="45" rows="3">${entity.smsContent}</textarea>
				</td>
			</tr>
			<tr>
				<th>&nbsp;</th>
				<td>
					<input class="pd5" type="button" name="button" onclick="sendSMS()" value="发送短信" />
				</td>
			</tr>
		</table>
	</form>