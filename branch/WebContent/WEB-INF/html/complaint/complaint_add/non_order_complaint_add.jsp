<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${CONFIG.res_url}/script/jquery/plugin/jquery.form.js"></script>
<link href="${CONFIG.res_url}css/easydialog.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${CONFIG.res_url}/script/easydialog.min.js"></script>

<script type="text/javascript">
$(document).ready(function() {
	change_menu('${menuId}');
	
    var options = {
        beforeSubmit:  complaint_add_check_form,  // pre-submit callback 
        success:       success_function,  // post-submit callback 
    }; 
    $('#complaint_form').ajaxForm(options);
});

//列表标签表单提交
function searchTable(menuId) {
	$('#menuId').attr("value",menuId);
	change_menu(menuId);
	window.location.href="complaint-toEditComplaint?menuId="+menuId;
}

function change_menu(menuId) {
	$("li[id^='menu_']").each(function(){
		$(this).removeClass("menu_on");
	});
	$('#'+menuId).addClass("menu_on");
}

function checkMedia() {
	var s = $("#isMedia").attr("checked");
	if(s == undefined){
		$("#media").val(0);
	}else{
		$("#media").val(1);
	}
}

function checkSpecial(){
	var s = $("#specialEventFlag").attr("checked");
	if(s == undefined){
		$("#specialEventFlag").val(0);
	}else{
		$("#specialEventFlag").val(1);
	}
}

function checkHotel() {
	var s = $("#isHotel").attr("checked");
	if(s == undefined){
		$("#isHotel").val(0);
	}else{
		$("#isHotel").val(1);
	}
}

function complaint_add_check_form(){
	var comeFrom = $('#come_from').val();
	var level = $('#level').val();
	var reason = $('#reason_div').html();
	var contactPerson = $('#contact_person').val();
	var contactPhone = $('#contact_phone').val();
	var edit = '${edit}';
	//alert(edit);
	if(comeFrom == 0){
		alert('请选择投诉来源');
		return false;
	}
	if(level == ''){
		alert('请选择投诉等级');
		return false;
	}
	if(contactPerson == ''){
		alert('请填写联系人');
		return false;
	}
	if(contactPhone == ''){
		alert('请填写联系电话');
		return false;
	}
	if (reason == '' && edit != 1) {
		alert('请填写投诉事宜');
		return false;
	}
	
	$('#saveInfo').attr('disabled' , 'true');
	$('#submitInfo').attr('disabled' , 'true');
	return true;
}

function success_function() {
	alert("提交成功!");
	parent.searchTable("menu_2");
}

function onSaveInfoClicked(edit) {
	if(edit==1){
	   $('#complaint_form').attr("action", "complaint-modifyComplaint");
	   $('#complaint_form').submit();
	} else {
	   $('#complaint_form').attr("action", "complaint-doAddNonOrderComplaint");
	   $('#complaint_form').submit();
	}
}

</script>

<title>投诉情况说明单</title>
</head>
<body>
<div id="pici_tab" class="clear">
	<ul>
		<li class="menu_on" onclick="searchTable(this.id)" id="menu_1">
			<s class="rc-l"></s><s class="rc-r"></s><a href="javascript:void(0)">有订单投诉</a>
		</li>
		<li onclick="searchTable(this.id)" id="menu_2">
			<s class="rc-l"></s><s class="rc-r"></s><a href="javascript:void(0)">无订单投诉</a>
		</li>
		<li onclick="searchTable(this.id)" id="menu_3">
			<s class="rc-l"></s><s class="rc-r"></s><a href="javascript:void(0)">批量变更</a>
		</li>
	</ul>
</div>
<form name="form" id ="complaint_form" method="post" enctype="multipart/form-data">
<input type="hidden" name="menuId" id="menuId" value="${menuId }">
<table width="100%" class="datatable">
	<tr>
		<th width="100" align="right"><span class="cred">*</span> 投诉来源：</th>
		<td>
		<select name="entity.comeFrom" id="come_from">
				<option value="0">投诉来源</option>
				<option value="网站" <c:if test="${'网站'.equals(entity.comeFrom)}">selected</c:if>>网站</option>
				<option value="门市" <c:if test="${'门市'.equals(entity.comeFrom)}">selected</c:if>>门市</option>
				<option value="当地质检" <c:if test="${'当地质检'.equals(entity.comeFrom)}">selected</c:if>>当地质检</option>
				<option value="来电投诉" <c:if test="${'来电投诉'.equals(entity.comeFrom)}">selected</c:if>>来电投诉</option>
				<option value="CS邮箱" <c:if test="${'CS邮箱'.equals(entity.comeFrom)}">selected</c:if>>CS邮箱</option>
				<option value="回访" <c:if test="${'回访'.equals(entity.comeFrom)}">selected</c:if>>回访</option>
				<option value="旅游局" <c:if test="${'旅游局'.equals(entity.comeFrom)}">selected</c:if>>旅游局</option>
				<option value="微博" <c:if test="${'微博'.equals(entity.comeFrom)}">selected</c:if>>微博</option>
				<option value="其他" <c:if test="${'其他'.equals(entity.comeFrom)}">selected</c:if>>其他</option>
		</select>
		</td>
		<th width="100" align="right"><span class="cred">*</span> 投诉级别：</th>
		<td><select name="entity.level" id="level">
				<option value="" >投诉等级</option>
				<option value="3" <c:if test="${entity.level==3 }">selected</c:if> >3级</option>
			 	<option value="2" <c:if test="${entity.level==2 }">selected</c:if> >2级</option>
				<option value="1" onclick="javascript:alert('1级为重大投诉，请慎重选择')" <c:if test="${entity.level==1 }">selected</c:if> >1级</option>
		</select>&nbsp;&nbsp;&nbsp;<input type="checkbox" name="specialEventFlag" id="specialEventFlag" onclick="checkSpecial()" value="0">特殊事件
		</td>
		<td colspan="2">
			<input type="checkbox" id="isMedia"  <c:if test="${entity.isMedia==1}">checked</c:if> onclick="checkMedia()">投诉方为媒体方(或有媒体参与)
			&nbsp;&nbsp;&nbsp;<input type="checkbox"  name="isHotel" value="1">酒店投诉
		</td>
	</tr>
	<tr>
		<th align="right"><span class="cred">*</span> 联系人：</th>
		<td><input name="entity.contactPerson" type="text" id="contact_person" size="10" value="${entity.contactPerson}"/></td>
		<th align="right"><span class="cred">*</span> 联系人电话：</th>
		<td><input name="entity.contactPhone" type="text" id="contact_phone" size="10" value="${entity.contactPhone}"/></td>
		<th align="right">联系人邮箱：</th>
		<td><input name="entity.contactMail" type="text" id="contact_mail" size="10" value="${entity.contactMail}"/></td>
	</tr>
	<tr <c:if test="${edit == 1}">style="display:none;"</c:if>>
		<th align="right"><span class="cred">*</span> 投诉事宜：</th>
		<td colspan="5">
		<input title="填写投诉事宜" class="pd5 mr10" type="button" value="填写投诉事宜" onclick="easyDialog.open({container : 'addReasonBox', overlay : false})"/>
	<br /> 
	<div id="reason_display">投诉事宜填写结果</div>
	</td>
	</tr>
	<tr>
		<th align="right">其他说明：</th>
		<td colspan="5">
			<textarea name="entity.descript" id="descript" cols="45" rows="3">${entity.descript}</textarea>
		</td>
	</tr>
	<tr>
		<th align="right">客人要求：</th>
		<td colspan="5">
			<textarea name="entity.requirement" id="requirement" cols="45" rows="3" >${entity.requirement}</textarea>
		</td>
	</tr>
	<tr>
		<th align="right">发起人：</th>
		<td><input name="entity.ownerName" type="text" id="owner_name" size="10" value="${entity.ownerName}" readonly/></td>
		<th align="right">部门：</th>
		<td><input name="entity.ownerPartment" type="text" id="owner_partment" size="20" value="${entity.ownerPartment}" readonly/></td>
		<th align="right">日期：</th>
		<td><input name="entity.buildDate" type="text" id="build_date" size="20" value="<fmt:formatDate value="${entity.buildDate}" pattern="yyyy-MM-dd HH:mm:ss" />" readonly/></td>
	</tr>
	<tr>
		<th>&nbsp;</th>
		<td colspan="5">
			<input  class="pd5" type="button" id="saveInfo" value="提交" onclick="onSaveInfoClicked(${edit})"/> 
			<input class="pd5" type="button" id="submitInfo" onclick="searchTable('menu_2')" value="重置" />
		</td>
	</tr>
</table>
<div id="reason_div" style="display:none;"></div>
</form>

<div id="addReasonBox" style="display: none;">
	<iframe src="complaint_reason-addComplaint?complaintId=${complaint.id }&isOrder=0" frameborder="0" width="800" height="500"></iframe>
</div>

</body>
