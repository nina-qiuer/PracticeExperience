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
		
	    dealItemShowHide();
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
	
	function checkMedia(){
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
			$("#special_event_flag").val(0);
		}else{
			$("#special_event_flag").val(1);
		}
	}
	
	function complaint_add_check_form(){
		var comeFrom = $('#come_from').val();
		var level = $('#level').val();
		var orderId = $('#order_id').val();
		var reason = $('#reason_div').html();
		var edit = '${edit}';
		var cmpPerson = $("#cmpPerson").val();
		var cmpPhone = $.trim($("#cmpPhone").val());
		
		if(comeFrom == 0){
			alert('请选择投诉来源');
			return false;
		}
		if(level == ''){
			alert('请选择投诉等级');
			return false;
		}
		if(orderId == ''){
			alert('请输入订单号');
			return false;
		}
		if(isNaN(orderId)) {
			alert('订单号必须为数字');
			return false;
		}
		if (reason == '' && edit != 1) {
			alert('请填写投诉事宜');
			return false;
		}
		if(cmpPerson == ''){
			alert('请填写投诉人');
			return false;
		}
		if(cmpPerson.length>50){
			alert('投诉人名字过长，请填写简称，将其全名写在备注中');
			return false;
		}
		if(cmpPhone == ''){
			alert('请填写投诉人电话');
			return false;
		}
		
		
		$('#saveInfo').attr('disabled' , 'true');
		$('#submitInfo').attr('disabled' , 'true');
		$("#isSubmit").val(1);
		return true;
	}
	
	function success_function() {
		alert("提交成功!");
		parent.searchTable("menu_2");
	}
	//提交订单号，跟出相应内容
	function send_orderid() {
		var order_id = $("#order_id").val();
		var come_from = $("#come_from").val();
		var level = $("#level").val();
		var descript = $("#descript").val();
		var requirement = $("#requirement").val();
		var isMedia = $("#media").val();
		var cmpPerson = $("#cmpPerson").val();
		var cmpPhone = $("#cmpPhone").val();
		if (order_id != "" && !isNaN(order_id)&&order_id!=0) {
			$("#_orderId").val(order_id);
			$("#_comeFrom").val(come_from);
			$("#_level").val(level);
			$("#_isMedia").val(isMedia);
			$("#_cmpPerson").val(cmpPerson);
			$("#_cmpPhone").val(cmpPhone);
			$("#_descript").val(descript);
			$("#_requirement").val(requirement);
			$('#order_form').submit();
		}
	}
	
	function onSaveInfoClicked(edit) {
		 if(edit==1){
		   $('#complaint_form').attr("action", "complaint-modifyComplaint");
		   $('#complaint_form').submit();
		 }else{
		   $('#complaint_form').attr("action", "complaint-doAddComplaint");
		   $('#complaint_form').submit();
		 }
    }

    function dealItemShowHide() {
    	var orderState = $('#order_state').val();
    	if ("出游前" == orderState) {
    		$('#dealItem').hide();
    	} else if ("出游中" == orderState) {
    		$('#dealItem').show();
    	} else if ("出游后" == orderState) {
    		$('#dealItem').show();
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
<form id="order_form" method="post" enctype="multipart/form-data" action="complaint-sendOrderInfo">
<input type="hidden" name="orderId" id="_orderId">
<input type="hidden" name="comeFrom" id="_comeFrom">
<input type="hidden" name="level" id="_level">
<input type="hidden" name="isMedia" id="_isMedia">
<input type="hidden" name="cmpPerson" id="_cmpPerson">
<input type="hidden" name="cmpPhone" id="_cmpPhone">
<input type="hidden" name="descript" id="_descript">
<input type="hidden" name="requirement" id="_requirement">
<input type="hidden" name="is_applay_send" value="1">
</form>
<form name="form" id ="complaint_form" method="post" enctype="multipart/form-data">
<input type="hidden"  id="isSubmit" value="0" />
<input type="hidden" name="entity.id" value="${entity.id}"/>
<input type="hidden" name="entity.agencyId" value="${entity.agencyId}"/>
<input type="hidden" name="entity.createType" value="${entity.createType}"/>
<input type="hidden" name="entity.depName" value="${entity.depName}"/>
<input type="hidden" name="entity.depManager" value="${entity.depManager}"/>
<input type="hidden" name="entity.productManager" value="${entity.productManager}"/>
<input type="hidden" name="entity.score" value="${entity.score}"/>
<input type="hidden" name="entity.startTime" value="<fmt:formatDate value="${entity.startTime}" pattern="yyyy-MM-dd HH:mm:ss" />"/>
<input type="hidden" name="entity.backTime" value="<fmt:formatDate value="${entity.backTime}" pattern="yyyy-MM-dd HH:mm:ss" />"/>
<input type="hidden" name="entity.isMedia" id="media" value="${entity.isMedia}"/>
<input type="hidden" name="entity.special_event_flag" id="special_event_flag" value="${entity.special_event_flag}"/>
<input type="hidden" name="entity.contactId" id="contactId" value="${entity.contactId}"/>
<input type="hidden" name="entity.productLineId" id="productLineId" value="${entity.productLineId}"/>
<input type="hidden" name="entity.endCity" id="endCity" value="${entity.endCity}"/>
<input type="hidden" name="entity.productPrice" id="productPrice" value="${entity.productPrice}"/>
<input type="hidden" name="entity.routeTypeSp" id="routeTypeSp" value="${entity.routeTypeSp}"/>
<input type="hidden" name="entity.airFlag" id="airFlag" value="${entity.airFlag}"/>
<input type="hidden" name="entity.airfare" id="airfare" value="${entity.airfare}"/>
<input type="hidden" name="entity.niuLineFlag" id="niuLineFlag" value="${entity.niuLineFlag}"/>
<input type="hidden" name="entity.signCityCode" id="signCityCode" value="${entity.signCityCode}"/>
<input type="hidden" name="entity.signCity" id="signCity" value="${entity.signCity}"/>
<input type="hidden" name="entity.custId" id="custId" value="${entity.custId}"/>
<input type="hidden" name="entity.bdpName" id="bdpName" value="${entity.bdpName}"/>
<input type="hidden" name="entity.destCategoryId" id="destCategoryId" value="${entity.destCategoryId}"/>
<input type="hidden" name="entity.destCategoryName" id="destCategoryName" value="${entity.destCategoryName}"/>
<input type="hidden" name="entity.secondaryDepId" id="secondaryDepId" value="${entity.secondaryDepId}"/>
<input type="hidden" name="entity.brandName" id="brandName" value="${entity.brandName}"/>
<input type="hidden" name="entity.operateName" id="operateName" value="${entity.operateName}"/>
<input type="hidden" name="entity.operateManagerName" id="operateManagerName" value="${entity.operateManagerName}"/>
<input type="hidden" name="entity.clientTypeExpand" id="clientTypeExpand" value="${entity.clientTypeExpand}"/>

<input type="hidden" name="entity.classBrandParentId" id="classBrandParentId" value="${entity.classBrandParentId}"/>
<input type="hidden" name="entity.classBrandId" id="classBrandId" value="${entity.classBrandId}"/>
<input type="hidden" name="entity.productNewLineTypeId" id="productNewLineTypeId" value="${entity.productNewLineTypeId}"/>
<input type="hidden" name="entity.destGroupId" id="destGroupId" value="${entity.destGroupId}"/>

<table width="100%" class="notice2 mb5">
	<tr>
		<td style="display:none">订单状态：<span class="cred">
		<select name="entity.orderState" id="order_state" onchange="dealItemShowHide()">
				<option value="出游前" <c:if test="${'出游前'.equals(entity.orderState)}">selected</c:if>>出游前</option>
				<option value="出游中" <c:if test="${'出游中'.equals(entity.orderState)}">selected</c:if>>出游中</option>
				<option value="出游后" <c:if test="${'出游后'.equals(entity.orderState)}">selected</c:if>>出游后</option>
		</select><br>
		<span><font color="red"><strong>请在提交投诉前确认订单状态是否准确</strong></font></span>
		</td>
		<td style="padding-left:47px;width:215px">订单类型：<span class="cred"><input name="entity.orderType" type="text" id="order_type" size="10" value="${entity.orderType}" readonly/></span></td>
		<td style="width:273px">线路类型：<span class="cred"><input name="entity.routeType" type="text" id="route_type" size="10" value="${entity.routeType}" readonly/></span></td>
		<td>订单来源：<span class="cred"><input name="entity.orderComeFrom" type="text" id="order_come_from" size="10" value="${entity.orderComeFrom}" readonly/></span></td>
	</tr>
</table>
<table width="100%" class="datatable">
	<tr>
		<th width="100" align="right">投诉来源：</th>
		<td>
		<select name="entity.comeFrom" id="come_from">
				<option value="0">投诉来源</option>
				<option value="网站" <c:if test="${'网站'.equals(entity.comeFrom)}">selected</c:if>> 网站</option>
				<option value="门市" <c:if test="${'门市'.equals(entity.comeFrom)}">selected</c:if>>门市</option>
				<option value="当地质检" <c:if test="${'当地质检'.equals(entity.comeFrom)}">selected</c:if>>当地质检</option>
				<option value="来电投诉" <c:if test="${'来电投诉'.equals(entity.comeFrom)}">selected</c:if>>来电投诉</option>
				<option value="CS邮箱" <c:if test="${'CS邮箱'.equals(entity.comeFrom)}">selected</c:if>>CS邮箱</option>
				<option value="回访" <c:if test="${'回访'.equals(entity.comeFrom)}">selected</c:if>>回访</option>
				<option value="旅游局" <c:if test="${'旅游局'.equals(entity.comeFrom)}">selected</c:if>>旅游局</option>
				<option value="微博" <c:if test="${'微博'.equals(entity.comeFrom)}">selected</c:if>>微博</option>
				<option value="其他" <c:if test="${'其他'.equals(entity.comeFrom)}">selected</c:if>>其他</option>
		</select> <span class="cred">*</span>
		</td>
		<th width="100" align="right">投诉级别：</th>
		<td><select name="entity.level" id="level">
				<option value="" >投诉等级</option>
				<option value="3" <c:if test="${entity.level==3 }">selected</c:if> >3级</option>
			 	<option value="2" <c:if test="${entity.level==2 }">selected</c:if> >2级</option>
				<option value="1" onclick="javascript:alert('1级为重大投诉，请慎重选择')" <c:if test="${entity.level==1 }">selected</c:if> >1级</option>
		</select> <span class="cred">*</span></td>
		<td colspan="2">
			<label>
			<input type="checkbox" id="specialEventFlag" onclick="checkSpecial()">特殊事件&nbsp;&nbsp;&nbsp;
			</label>
			<label>
			<input type="checkbox" id="isMedia" <c:if test="${entity.isMedia==1}">checked</c:if> onclick="checkMedia()">投诉方为媒体方(或有媒体参与)
			</label>
		</td>
	</tr>
	<tr>
		<th align="right">投诉人：</th>
		<td><input name="entity.cmpPerson" type="text" id="cmpPerson" size="10" value="${entity.cmpPerson}"><span class="cred">*</span></td>
		<th align="right">投诉人电话：</th>
		<td><input name="entity.cmpPhone" type="text" id="cmpPhone" size="10" value="${entity.cmpPhone}"><span class="cred">*</span></td>
		<th align="right">处理优先级：</th>
		<td>
			<select class="mr10" name="entity.priority" id="priority" >
				<option value="">请选择</option>
				<option value="3" <c:if test="${entity.priority== 3}">selected</c:if> >普通</option>
				<option value="1" <c:if test="${entity.priority== 1}">selected</c:if> >紧急</option>
				<option value="2" <c:if test="${entity.priority== 2}">selected</c:if> >重要</option>
			</select>
		</td>
	</tr>
	<tr>
		<th align="right">订单号：</th>
		<td><input name="entity.orderId" type="text" id="order_id" size="10" value="${entity.orderId}" onblur="send_orderid();"/> 
			<span class="cred">*</span><span calss="cred" id="check_order_exist">${alertOrderIdInfo}</span>
		</td>
		<th align="right">客户姓名：</th>
		<td><input name="entity.guestName" type="text" id="guest_name" size="10" value="${entity.guestName}" readonly/></td>
		<th width="100" align="right">出游人数：</th>
		<td><input name="entity.guestNum" type="text" id="guest_num" size="10" value="${entity.guestNum}" readonly/></td>
	</tr>
	<tr>
		<th align="right">联系人：</th>
		<td><input name="entity.contactPerson" type="text" id="contact_person" size="10" value="${entity.contactPerson}" readonly/></td>
		<th align="right">联系人手机：</th>
		<td><input name="entity.contactPhone" type="text" id="contact_phone" size="10" value="${entity.contactPhone}" readonly/></td>
		<th align="right">联系人邮箱：</th>
		<td><input name="entity.contactMail" type="text" id="contact_mail" size="10" value="${entity.contactMail}" readonly/></td>
	</tr>
	<tr>
		<th align="right">出发地：</th>
		<td><input name="entity.startCity" type="text" id="start_city" size="10" value="${entity.startCity}" readonly/></td>
		<th align="right">线路：</th>
		<td colspan=""><input name="entity.routeId" type="text" id="routeId" size="10" value="${entity.routeId}" readonly/></td>
		
		<th align="right">线路名称：</th>
		<td colspan=""><input name="entity.route" type="text" id="route" size="10" value="${entity.route}" readonly/></td>
	</tr>
	<tr>
		<th align="right">售前客服：</th>
		<td><input name="entity.customer" type="text" id="customer" size="10" value="${entity.customer}" readonly/></td>
		<th align="right">客服经理：</th>
		<td><input name="entity.customerLeader" type="text" id="customer_leader" size="10" value="${entity.customerLeader}" readonly/>
						<input name="entity.customerLeaderId" type="hidden" id="customer_leaderId" size="10" value="${entity.customerLeaderId}"/></td>
		<th align="right">高级客服经理：</th>
		<td><input name="entity.serviceManager" type="text" id="service_manager" size="10" value="${entity.serviceManager}" readonly/></td>
	</tr>
	<tr>
		<th align="right">产品专员：</th>
		<td><input name="entity.producter" type="text" id="producter" size="10" value="${entity.producter}" readonly/></td>
		<th align="right">产品经理：</th>
		<td><input name="entity.productLeader" type="text" id="product_leader" size="10" value="${entity.productLeader}" readonly/></td>
		<th align="right">高级产品经理：</th>
		<td><input name="entity.seniorManager" type="text" id="senior_manager" size="10" value="${entity.seniorManager}" readonly/></td>
	</tr>
	<tr>
		<th align="right">导游编号：</th>
		<td><input name="entity.guideId" type="text" id="guideId" size="10" value="${entity.guideId}" readonly/></td>
		<th align="right">导游姓名：</th>
		<td><input name="entity.guideName" type="text" id="guideName" size="10" value="${entity.guideName}" readonly/></td>
		<th align="right">导游电话：</th>
		<td><input name="entity.guideCall" type="text" id="guideCall" size="10" value="${entity.guideCall}" readonly/></td>
	</tr>
	<tr>
		<th align="right">出发时间：</th>
		<td ><input name="entity.startTime" type="text" id="startTime" size="10" value="<fmt:formatDate value="${entity.startTime}" pattern="yyyy-MM-dd"/>" readonly/></td>
	    <th align="right">客人等级：</th>
		<td >
			<input name="entity.guestLevel" type="text" id="guestLevel" size="10" value="${entity.guestLevel}" readonly/>
			<input type = "hidden" value="${entity.guestLevelNum }" name="entity.guestLevelNum"/>
		</td>
  	    <th align="right">供应商名称：</th>
		<td ><input name="entity.agencyName" type="text" id="agencyName" size="10" value="${entity.agencyName}"  readonly/></td>
	</tr>
	<tr>
		<th align="right">订单投诉记录：</th>
		<td colspan="5" id="orderComplaintRecord">
		<c:forEach items="${complaintReasonEntityList}" var="v" varStatus="st">
			<p>
			投诉号：<a href="complaint-toBill?id=${v.complaintId}" class="mr20" target="_blank">${v.complaintId}</a> <span class="mr20">投诉事宜：${v.type}-${v.secondType}</span>${v.typeDescript}
			</p>
		</c:forEach>	
		</td>
	</tr>
	<tr <c:if test="${edit == 1}">style="display:none;"</c:if>>
		<th align="right">投诉事宜：<span class="cred">*</span></th>
		<td colspan="5">
		<input title="填写投诉事宜" class="pd5 mr10" type="button" value="填写投诉事宜" onclick="easyDialog.open({container : 'addReasonBox', overlay : false})"/>
		<br><div id="reason_display">投诉事宜填写结果</div>
	</td>
	</tr>
	<tr>
		<th align="right">其他说明：</th>
		<td colspan="5"><textarea name="entity.descript" id="descript"
				cols="45" rows="3" >${entity.descript}</textarea></td>
	</tr>
	<tr>
		<th align="right">客人要求：</th>
		<td colspan="5"><textarea name="entity.requirement" id="requirement"
				cols="45" rows="3" >${entity.requirement}</textarea></td>
	</tr>
	<tr>
		<th align="right">发起人：</th>
		<td><input name="entity.ownerName" type="text" id="owner_name" size="10" value="${entity.ownerName}" readonly/></td>
		<th align="right">部门：</th>
		<td><input name="entity.ownerPartment" type="text" id="owner_partment" size="20" value="${entity.ownerPartment}" readonly/></td>
		<th align="right">日期：</th>
		<td><input name="entity.buildDate" type="text" id="build_date" size="20" value="<fmt:formatDate value="${entity.buildDate}" pattern="yyyy-MM-dd HH:mm:ss" />" readonly/></td>
	</tr>
	<c:if test="${1 != edit}">
		<tr id="dealItem">
			<th align="right">处理选项：</th>
			<td colspan="5">
				<label><input name="dealBySelf" type="checkbox" id="dealSelf" value="1"/>
				<span style="color: green;">选中表示分配给自己处理，未选中表示走系统正常分配流程。</span></label>
			</td>
		</tr>
	</c:if>
	<tr>
		<th>&nbsp;</th>
		<td colspan="5">
			<input class="pd5" type="button" id="saveInfo" value="提交" onclick="onSaveInfoClicked(${edit})" <c:if test="${isGetOrder!=1 && edit!=1}">disabled</c:if>/> 
			<input class="pd5" type="button" id="submitInfo" onclick="searchTable('menu_1')" value="重置" />
		</td>
	</tr>
</table>
<div id="reason_div" style="display:none;"></div>
</form>

<div id="addReasonBox" style="display: none;">
	<iframe src="complaint_reason-addComplaint?complaintId=${complaint.id }&isOrder=1" frameborder="0" width="800" height="500"></iframe>
</div>

</body>
