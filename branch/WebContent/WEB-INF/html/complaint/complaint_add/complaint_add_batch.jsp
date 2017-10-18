<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<link href="${CONFIG.res_url}css/thickbox.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${CONFIG.res_url}script/thickbox-compressed.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}script/DatePickerNew/WdatePicker.js" ></script>
<link href="${CONFIG.res_url}css/easydialog.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${CONFIG.res_url}/script/easydialog.min.js"></script>
<style type="text/css">
<!-- tr变黄色 -->
.trbg td{background:#ff9;}
.listtable tr.yellowbg td{background:#FFFF99}
.listtable tr td.orange{background:orange;}
</style>
<script>
$(function(){
	change_menu('${menuId}');

	var succNum = '${succNum}';
	if (succNum > 0) {
		alert("已成功发起变更：" + succNum + "单！");
		parent.searchTable("menu_2");
	}
	
	/*移除thickbox默认样式*/
	$('.thickbox').click(function(){
		$('#TB_closeAjaxWindow').html("<a href='javascript:void(0)' id='TB_closeWindowButton' title='Close'>×</a>");
		$('#TB_closeWindowButton').live('click',function(){
			tb_remove();
		});
	});
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

function complaintAddBatch() {
	var reason = $('#reason_div').html();
	if (reason == '') {
		alert('请填写变更事宜');
		return false;
	}
	
	$("#createButton").attr("disabled", "disabled");
	$("#createButton").val("进行中...");
	
	$('#complaint_form').attr("action", "complaint-complaintAddBatch");
	$('#complaint_form').submit();
}

function onSearchClicked() {
	if (checkSubmit()) {
		$('#complaint_form').attr("action", "complaint-toEditComplaint");
		$('#complaint_form').submit();
	}
}

function checkSubmit() {
	var orderIds = $("#orderIds").val();
	var orderIdArr = orderIds.split(",");
	var regx=/^\d+$/;
	var rs;
	for(var i=0; i<orderIdArr.length; i++) {
		var orderId = orderIdArr[i];
		rs=regx.exec(orderId);
		if (rs==null) {
			alert("订单号格式有误，请修正:\n"+"错误位置："+orderId);
			$("#orderIds").get(0).focus();//光标移动到输入框，方便操作人员修改
			return false;
		}
	}
	return true;
}

function addRow_attach(){
	var data_json = [ {} ];
	$.tmpl.add_row("tr_attach", data_json);
}

function getFileSize(obj) { 
	var fileSize = obj.files[0].size;
	if(fileSize>20971520){
		alert("请上传小于20M的文件！");
		obj.parentNode.parentNode.remove();
	}
}
</script>
</HEAD>
<BODY>
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
<form name="form" id="complaint_form" method="post" enctype="multipart/form-data" action="complaint-toEditComplaint">
<input type="hidden" name="menuId" id="menuId" value="${menuId}">
<div class="pici_search pd5">
	<div class="pici_search pd5 mb10">
	<label class="mr10">订单号： <textarea rows="3" cols="100" id="orderIds" name="orderIds">${orderIds}</textarea> </label>
	<input type="button" value="查询" class="blue" onclick="onSearchClicked();">
	<div style="color: red;">【提示】多个订单号请使用英文逗号间隔，如：4968088,4698851</div>
	</div>
</div>
<table class="listtable" width="100%">
	<tr>
		<th>序号</th>
		<th>订单号</th>
		<th>线路号</th>
		<th>线路名称</th>
		<th>团期</th>
		<th>客服专员</th>
		<th>客服经理</th>
		<th>出发地</th>
	</tr>
	<c:forEach items="${complaintList}" var="v" varStatus="st">
	<tr align="center" class="trbg">
		<td>${st.count}</td>
		<td>
			<a href="#" onclick="showOrder(${user.id},'${user.realName}',${v.orderId})">${v.orderId}</a>
		</td>
		<td>${v.routeId}</td>
		<td align="left" title="${v.route }">
		    <c:choose>
				<c:when test="${fn:length(v.route) > 14}">
					<c:out value="${fn:substring(v.route, 0, 12)}......" />
				</c:when>
				<c:otherwise>
					<c:out value="${v.route}" />
				</c:otherwise>
			</c:choose>
		</td>
		<td><fmt:formatDate value="${v.startTime}" pattern="yyyy-MM-dd"/></td>
		<td>${v.customer}</td>
		<td>${v.customerLeader}</td>
		<td>${v.startCity}</td>
	</tr>
	</c:forEach>
</table>
<table width="100%" class="datatable">
<tr>
	<th align="right" width="100" height="30">变更事宜：<span class="cred">*</span></th>
	<td>
	<input title="填写变更事宜" class="pd5 mr10" type="button" value="填写变更事宜" onclick="easyDialog.open({container : 'addReasonBox', overlay : false})"/>
	<br><div id="reason_display">变更事宜填写结果</div>
	</td>
</tr>
<tr>
	<th align="right" width="100" height="30">附件：</th>
	<td><input type="button" class="blue" value="添加" id="addButton" onclick="addRow_attach()">
	<table class="listtable">
	<tr id="tr_attach" style="display: none">
		<td><input type="file" name="cmpAttach" onchange="getFileSize(this);"></td>
		<td>
			<input type="button" onclick="$(this).parent().parent().remove();" value="删除">
		</td>
	</tr>
	</table>
	</td>
</tr>
<tr>
	<th>&nbsp;</th>
	<td>
		<input class="pd5" type="button" id="createButton" value="提交" onclick="complaintAddBatch()"/>
	</td>
</tr>
</table>
<div id="reason_div" style="display:none;"></div>
</form>
<div id="addReasonBox" style="display: none;">
	<iframe src="complaint_reason-addComplaint?complaintId=${complaint.id }&isOrder=1" frameborder="0" width="800" height="500"></iframe>
</div>
</BODY>
