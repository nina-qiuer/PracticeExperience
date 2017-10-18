<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<link href="${CONFIG.res_url}css/thickbox.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${CONFIG.res_url}script/thickbox-compressed.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}script/DatePickerNew/WdatePicker.js" ></script>  
<style type="text/css">
<!-- tr变黄色 -->
.trbg td{background:#ff9;}
.listtable tr.yellowbg td{background:#FFFF99}
.listtable tr td.orange{background:orange;}
</style>
<script>
$(function(){
	change_menu('${page.menuId}');

	var fieldStr = "${fieldStr}";
	var fields = fieldStr.split(",");
	$.each(fields, function(key, val) {
		$("#" + val).click();
	});

	// chkAll全选事件
    $("#chkAll").bind("click", function () {
        $("[name=ids]:checkbox").attr("checked", this.checked);
    });
	
	/*移除thickbox默认样式*/
	$('.thickbox').click(function(){
		$('#TB_closeAjaxWindow').html("<a href='javascript:void(0)' id='TB_closeWindowButton' title='Close'>×</a>");
		$('#TB_closeWindowButton').live('click',function(){
			tb_remove();
		});
	});
});

//标签转换时更新标签样式
function change_menu(value) {
	$("li[id^='menu_']").each(function(){
		$(this).removeClass("menu_on");
	});
	$('#'+value).addClass("menu_on");	
}

//列表标签表单提交
function searchTable(menuId) {
	$('#menu_id').attr("value",menuId);
	onSearchClicked();
}

function verifyIdsChecked(scope) {
	if ("checked" == scope) {
		if($("input[name='ids']:checked").length <= 0){
			alert("至少选择一条记录");
			return false;
		}
	}
	return true;
}

function sendSMS() {
	var scope = $("input[name='page.sendScope']:checked").val();
	if (verifyIdsChecked(scope)) {
		$("#sendButton").attr("disabled", "disabled");
		$("#sendButton").val("进行中...");
		$.ajax({
			type: "POST",
			url: "ew_order-sendSMS",
			data: $('#warning_form').serialize(),
			async: false,
			success: function(data) {
				alert("已成功发送短信：" + data + "条！");
				search();
			} 
		});
	}
}

function launchComplaint() {
	var scope = $("input[name='page.launchScope']:checked").val();
	if (verifyIdsChecked(scope)) {
		$("#launchButton").attr("disabled", "disabled");
		$("#launchButton").val("进行中...");
		$.ajax({
			type: "POST",
			url: "ew_order-launchComplaint",
			data: $('#warning_form').serialize(),
			async: false,
			success: function(data) {
				alert("已成功发起投诉：" + data + "单！");
				search();
			} 
		});
	}
}

function toggleEwo(flag) {
	$('#toggleFlag').attr("value", flag);
	if (verifyIdsChecked("checked")) {
		$.ajax({
			type: "POST",
			url: "ew_order-toggleEwo",
			data: $('#warning_form').serialize(),
			async: false,
			success: function(data) {
				search();
			}
		});
	}
}

function onSearchClicked() {
	$('#pageNo').attr("value",1);
	search();
}

function search() {
	$('#warning_form').attr("action", "ew_order");
	$('#warning_form').submit();
}

function onExportClicked() {
	//$('#complaint_form').attr("action", "${manageUrl}");
	$('#warning_form').attr("action", "ew_order");
	$('#warning_form').submit();
}

function onResetClicked() {
    $(':input','#warning_form').not(':button, :submit, :reset, :hidden')  
    .val('').removeAttr('checked').removeAttr('selected');
}

function filedToggle(id) {
	$("#" + id + "h").toggle();
	$("td[name='" + id + "d']").toggle();
}
</script>
</HEAD>
<BODY>
<div class="top_crumbs">您当前所在的位置：<a href="#">预警系统</a>&gt;&gt;<span class="top_crumbs_txt">预警订单处理</span></div>
<div id="pici_tab" class="clear">
	<ul>
		<li class="menu_on" onclick="searchTable(this.id)" id="menu_1">
			<s class="rc-l"></s><s class="rc-r"></s><a href="javascript:void(0)">未解除</a>
		</li>
		<li onclick="searchTable(this.id)" id="menu_2">
			<s class="rc-l"></s><s class="rc-r"></s><a href="javascript:void(0)">已解除</a>
		</li>
	</ul>
</div>
<form name="form" id="warning_form" method="post" enctype="multipart/form-data" action="ew_order">
<input type="hidden" name="page.menuId" id="menu_id" value="${page.menuId }">
<div class="pici_search pd5">
	<div class="pici_search pd5 mb10">
	<label class="mr10">团期号： <input type="text" size="10" name="page.groupTermNum" value="${page.groupTermNum}" /> </label>
	<label class="mr10">线路号： <input type="text" size="10" name="page.routeId" value="${page.routeId}" /> </label>
	<label class="mr10">自组团号： <input type="text" size="10" name="page.selfGroupNum" value="${page.selfGroupNum}" /> </label>
	<label class="mr10">订单类型：
	<select class="mr10" name="page.orderType">
		<option value="" >全部</option>
		<option value="1" <c:if test ="${page.orderType == 1}">selected="selected"</c:if>>跟团</option>
		<option value="2" <c:if test ="${page.orderType == 2}">selected="selected"</c:if>>自助游</option>
		<option value="3" <c:if test ="${page.orderType == 3}">selected="selected"</c:if>>老邮轮</option>
		<option value="4" <c:if test ="${page.orderType == 4}">selected="selected"</c:if>>门票</option>
		<option value="6" <c:if test ="${page.orderType == 6}">selected="selected"</c:if>>酒店</option>
		<option value="7" <c:if test ="${page.orderType == 7}">selected="selected"</c:if>>团队</option>
		<option value="9" <c:if test ="${page.orderType == 9}">selected="selected"</c:if>>签证</option>
	</select>
	</label>
	<label class="mr10">出游日期：<input type="text" size="10" name="page.startTimeBegin" value="${page.startTimeBegin }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readOnly="readonly"/></label>
	<label class="mr10">至　<input type="text" size="10" name="page.startTimeEnd" value="${page.startTimeEnd }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readOnly="readonly"/> </label>
	<label class="mr10">归来日期：<input type="text" size="10" name="page.backTimeStart" value="${page.backTimeStart }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readOnly="readonly"/></label>
	<label class="mr10">至　<input type="text" size="10" name="page.backTimeEnd" value="${page.backTimeEnd }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readOnly="readonly"/> </label>
	<br>
	<label class="mr10">航班号： <input type="text" size="10" name="page.flightNo" value="${page.flightNo}"> </label>
	<label class="mr10">起飞地： <input type="text" size="10" name="page.flightDcitys" value="${page.flightDcitys}" /> </label>
	<label class="mr10">起飞时间：<input type="text" size="20" name="page.flightDtBegin" value="${page.flightDtBegin }" onclick="WdatePicker()" readOnly="readonly"/></label>
	<label class="mr10">至　<input type="text" size="20" name="page.flightDtEnd" value="${page.flightDtEnd }" onclick="WdatePicker()" readOnly="readonly"/> </label>
	<label class="mr10">降落地： <input type="text" size="10" name="page.flightLcitys" value="${page.flightLcitys}" /> </label>
	<label class="mr10">落地时间：<input type="text" size="20" name="page.flightLtBegin" value="${page.flightLtBegin }" onclick="WdatePicker()" readOnly="readonly"/></label>
	<label class="mr10">至　<input type="text" size="20" name="page.flightLtEnd" value="${page.flightLtEnd }" onclick="WdatePicker()" readOnly="readonly"/> </label>
	<br>
	<label class="mr10">订单号： <input type="text" size="10" name="page.orderId" value="${page.orderId}" /> </label>
	<label class="mr10">出发地： <input type="text" size="10" name="page.startCity" value="${page.startCity}" /> </label>
	<label class="mr10">目的地： <input type="text" size="10" name="page.backCity" value="${page.backCity}" /> </label>
	<label class="mr10">目的地大类：
	<select class="mr10" name="page.destCategoryId">
		<option value="" >全部</option>
		<option value="1" <c:if test ="${page.destCategoryId == 1}">selected="selected"</c:if>>周边</option>
		<option value="2" <c:if test ="${page.destCategoryId == 2}">selected="selected"</c:if>>国内长线</option>
		<option value="3" <c:if test ="${page.destCategoryId == 3}">selected="selected"</c:if>>出境短线</option>
		<option value="4" <c:if test ="${page.destCategoryId == 4}">selected="selected"</c:if>>出境长线</option>
	</select>
	</label>
	<label class="mr10">供应商ID： <input type="text" size="10" name="page.agencyId" value="${page.agencyId}" /> </label>
	<label class="mr10">供应商名称： <input type="text" size="10" name="page.agencyName" value="${page.agencyName}" /> </label>
	<br>
	<input type="button" value="查询" class="blue" onclick="onSearchClicked();">　
	<input type="button" value="重置" class="blue" onclick="onResetClicked();">　
	<!-- <input type="button" value="导出" class="blue" onclick="onSearchExport();" disabled="disabled">　 -->
	<c:if test="${page.menuId == 'menu_1'}">
	<input type="button" value="发送短信" class="blue" onclick="$('#sendSMSDiv').toggle()">　
	<input type="button" value="发起投诉" class="blue" onclick="$('#launchComplaintDiv').toggle()">　
	<input type="button" value="解除" class="blue" onclick="toggleEwo(1)">　
	</c:if>
	<c:if test="${page.menuId == 'menu_2'}">
	<input type="button" value="还原" class="blue" onclick="toggleEwo(0)">　
	</c:if>
	<input type="hidden" id="toggleFlag" name="page.toggleFlag" value="-1">
	<input type="button" value="字段选取" class="blue" onclick="$('#filedListDiv').toggle()">
	</div>
	<div id="filedListDiv" class="pici_search pd5 mb10" style="display: none;">
		<label class="mr10"><input type="checkbox" name="fileds" value="orderIdT" id="orderIdT" onclick="filedToggle(this.id)">订单号</label>
		<label class="mr10"><input type="checkbox" name="fileds" value="routeIdT" id="routeIdT" onclick="filedToggle(this.id)">线路号</label>
		<label class="mr10"><input type="checkbox" name="fileds" value="routeNameT" id="routeNameT" onclick="filedToggle(this.id)">线路名称</label>
		<label class="mr10"><input type="checkbox" name="fileds" value="destCategoryT" id="destCategoryT" onclick="filedToggle(this.id)">目的地大类</label>
		<label class="mr10"><input type="checkbox" name="fileds" value="orderTypeT" id="orderTypeT" onclick="filedToggle(this.id)">订单类型</label>
		<label class="mr10"><input type="checkbox" name="fileds" value="startDateT" id="startDateT" onclick="filedToggle(this.id)">出游日期</label>
		<label class="mr10"><input type="checkbox" name="fileds" value="backDateT" id="backDateT" onclick="filedToggle(this.id)">归来日期</label>
		<label class="mr10"><input type="checkbox" name="fileds" value="startCityT" id="startCityT" onclick="filedToggle(this.id)">出发地</label>
		<label class="mr10"><input type="checkbox" name="fileds" value="backCityT" id="backCityT" onclick="filedToggle(this.id)">目的地</label>
		<label class="mr10"><input type="checkbox" name="fileds" value="touristNumT" id="touristNumT" onclick="filedToggle(this.id)">出游人数</label>
		<label class="mr10"><input type="checkbox" name="fileds" value="contactNameT" id="contactNameT" onclick="filedToggle(this.id)">联系人</label>
		<label class="mr10"><input type="checkbox" name="fileds" value="contactPhoneT" id="contactPhoneT" onclick="filedToggle(this.id)">联系电话</label>
		<label class="mr10"><input type="checkbox" name="fileds" value="groupTermNumT" id="groupTermNumT" onclick="filedToggle(this.id)">团期号</label>
		<label class="mr10"><input type="checkbox" name="fileds" value="selfGroupNumT" id="selfGroupNumT" onclick="filedToggle(this.id)">自组团号</label>
		<label class="mr10"><input type="checkbox" name="fileds" value="agencyT" id="agencyT" onclick="filedToggle(this.id)">供应商</label>
		<label class="mr10"><input type="checkbox" name="fileds" value="flightT" id="flightT" onclick="filedToggle(this.id)">航班信息</label>
		<label class="mr10"><input type="checkbox" name="fileds" value="smsSendRecordT" id="smsSendRecordT" onclick="filedToggle(this.id)">短信发送记录</label>
		<label class="mr10"><input type="checkbox" name="fileds" value="compLaunchRecordT" id="compLaunchRecordT" onclick="filedToggle(this.id)">投诉发起记录</label>
	</div>
	<div id="sendSMSDiv" class="pici_search pd5 mb10" style="display: none;">
	<table class="listtable" width="80%">
		<tr>
			<th>发送范围</th>
			<th>短信内容</th>
			<th>操作</th>
		</tr>
		<tr>
			<td align="center">
				<label><input type="radio"" name="page.sendScope" value="searched" checked="checked">当前查询记录</label><br>
				<label><input type="radio" name="page.sendScope" value="checked">当前选定记录</label>
			</td>
			<td align="center">
				<textarea name="page.smsContent" id="smsContent" cols="80" rows="2">尊敬的途牛会员，途牛小秘书提醒您，【${page.warningContent}】，为了不影响您的出游计划，请您提前做好防范措施。如需帮助，请拨打4007999999。</textarea>
			</td>
			<td align="center"><input type="button" id="sendButton" value="发送" class="blue" onclick="sendSMS()"></td>
		</tr>
	</table>
	</div>
	<div id="launchComplaintDiv" class="pici_search pd5 mb10" style="display: none;">
	<table class="listtable" width="90%">
		<tr>
			<th>发起范围</th>
			<th>投诉等级</th>
			<th>投诉详情</th>
			<th>备注</th>
			<th>操作</th>
		</tr>
		<tr>
			<td align="center">
				<label><input type="radio"" name="page.launchScope" value="searched" checked="checked">当前查询记录</label><br>
				<label><input type="radio" name="page.launchScope" value="checked">当前选定记录</label>
			</td>
			<td align="center">
				<select name="page.level" id="level">
					<option value="3">3级</option>
				 	<option value="2">2级</option>
					<option value="1">1级</option>
				</select>
			</td>
			<td align="center">
				<textarea name="page.compContent" id="compContent" cols="50" rows="2">${page.warningContent}</textarea>
			</td>
			<td align="center">
				<textarea name="page.compRemark" id="compRemark" cols="30" rows="2"></textarea>
			</td>
			<td align="center"><input type="button" id="launchButton" value="发起" class="blue" onclick="launchComplaint()"></td>
		</tr>
	</table>
	</div>
</div>
<span style="color: darkgreen">
预警等级：<c:if test="${page.warningLv == 1}">
		  <span style="background-color: red;">【红】</span>
	  </c:if>
	  <c:if test="${page.warningLv == 2}">
		  <span style="background-color: darkorange;">【橙】</span>
	  </c:if>
	  <c:if test="${page.warningLv == 3}">
		  <span style="background-color: yellow;">【黄】</span>
	  </c:if>
	  <c:if test="${page.warningLv == 4}">
		  <span style="background-color: deepskyblue;">【蓝】</span>
	  </c:if>，
预警编号：${page.ewId}，
预警类型：<c:if test="${page.warningType == 1}">天气预警</c:if>
	  <c:if test="${page.warningType == 2}">突发事件</c:if>
	  <c:if test="${page.warningType == 99}">其他</c:if>
</span>
<input type="hidden" name="page.ewId" value="${page.ewId}">
<input type="hidden" name="page.warningType" value="${page.warningType}">
<input type="hidden" name="page.warningLv" value="${page.warningLv}">
<input type="hidden" name="page.warningContent" value="${page.warningContent}">
<table class="listtable" width="100%">
	<tr>
		<th><input type="checkbox" id="chkAll" title="全选"></th>
		<th style="display: none;" id="orderIdTh">订单号</th>
		<th style="display: none;" id="routeIdTh">线路号</th>
		<th style="display: none;" id="routeNameTh">线路名称</th>
		<th style="display: none;" id="destCategoryTh">目的地大类</th>
		<th style="display: none;" id="orderTypeTh">订单类型</th>
		<th style="display: none;" id="startDateTh">出游日期</th>
		<th style="display: none;" id="backDateTh">归来日期</th>
		<th style="display: none;" id="startCityTh">出发地</th>
		<th style="display: none;" id="backCityTh">目的地</th>
		<th style="display: none;" id="touristNumTh">出游人数</th>
		<th style="display: none;" id="contactNameTh">联系人</th>
		<th style="display: none;" id="contactPhoneTh">联系电话</th>
		<th style="display: none;" id="groupTermNumTh">团期号</th>
		<th style="display: none;" id="selfGroupNumTh">自组团号</th>
		<th style="display: none;" id="agencyTh">供应商</th>
		<th style="display: none;" id="flightTh">航班信息</th>
		<th style="display: none;" id="smsSendRecordTh">短信发送记录</th>
		<th style="display: none;" id="compLaunchRecordTh">投诉发起记录</th>
	</tr>
	<c:forEach items="${page.ewoList }" var="v" varStatus="st">
	<tr align="center" class="trbg">
		<td><input type="checkbox" name="ids" value="${v.id}"></td>
		<td style="display: none;" name="orderIdTd">
			<a href="#" onclick="showOrder(${user.id},'${user.realName}',${v.orderId})">${v.orderId}</a>
		</td>
		<td style="display: none;" name="routeIdTd">${v.routeId}</td>
		<td style="display: none;" name="routeNameTd" align="left" title="${v.routeName }">
		    <c:choose>
				<c:when test="${fn:length(v.routeName) > 12}">
					<c:out value="${fn:substring(v.routeName, 0, 10)}......" />
				</c:when>
				<c:otherwise>
					<c:out value="${v.routeName}" />
				</c:otherwise>
			</c:choose>
		</td>
		<td style="display: none;" name="destCategoryTd">${v.destCategoryName}</td>
		<td style="display: none;" name="orderTypeTd">
			<c:if test ="${v.orderType == 1}">跟团</c:if>
			<c:if test ="${v.orderType == 2}">自助游</c:if>
			<c:if test ="${v.orderType == 3}">老邮轮</c:if>
			<c:if test ="${v.orderType == 4}">门票</c:if>
			<c:if test ="${v.orderType == 6}">酒店</c:if>
			<c:if test ="${v.orderType == 7}">团队</c:if>
			<c:if test ="${v.orderType == 9}">签证</c:if>
		</td>
		<td style="display: none;" name="startDateTd">${v.startDate}</td>
		<td style="display: none;" name="backDateTd">${v.backDate}</td>
		<td style="display: none;" name="startCityTd">${v.startCity}</td>
		<td style="display: none;" name="backCityTd">${v.backCity}</td>
		<td style="display: none;" name="touristNumTd">${v.adultCnt}大${v.childCnt}小</td>
		<td style="display: none;" name="contactNameTd">${v.contactName}</td>
		<td style="display: none;" name="contactPhoneTd">${v.contactPhone}</td>
		<td style="display: none;" name="groupTermNumTd">${v.groupTermNum}</td>
		<td style="display: none;" name="selfGroupNumTd">${v.selfGroupNum}</td>
		<td style="display: none;" name="agencyTd" align="left">
		<c:forEach items="${v.agencyList }" var="agency" varStatus="st">
			${agency.agencyId}_${agency.agencyName}
			<c:if test="${st.count < v.agencyList.size()}"><br></c:if>
		</c:forEach>
		</td>
		<td style="display: none;" name="flightTd">
		<c:forEach items="${v.flightList }" var="flight" varStatus="st">
			${flight.flightNo}：<fmt:formatDate value="${flight.departureTime}" pattern="yyyy-MM-dd HH:mm"/>[${flight.departureCityName}]→
			<fmt:formatDate value="${flight.arriveTime}" pattern="yyyy-MM-dd HH:mm"/>[${flight.arriveCityName}]
			<c:if test="${st.count < v.flightList.size()}"><br></c:if>
		</c:forEach>
		</td>
		<td style="display: none;padding: 0px;" name="smsSendRecordTd">
		<c:if test="${v.smsList!=null && v.smsList.size()>0}">
		<c:forEach items="${v.smsList}" var="sms">
		<table width="100%" style="border-collapse: collapse;border: 1px;">
			<tr>
				<td>${sms.telNum }</td>
				<td title="${sms.content}">...内容...</td>
				<td>
				<c:if test="${sms.sendResult == '0'}"><span style="color: green;">成功</span></c:if>
				<c:if test="${sms.sendResult != '0'}"><span style="color: red;" title="${sms.resultMsg}">失败</span></c:if>
				</td>
			</tr>
		</table>
		</c:forEach>
		</c:if>
		</td>
		<td style="display: none;" name="compLaunchRecordTd">
		<c:if test="${v.complaintId > 0}">
			<a href="<%=request.getContextPath()%>/complaint/action/complaint-toBill?id=${v.complaintId}" target="_blank" id="td_${v.complaintId}">${v.complaintId}</a>
		</c:if>
		</td>
	</tr>
	</c:forEach>
</table>
<%@include file="/WEB-INF/html/pager2.jsp" %>
</form>
