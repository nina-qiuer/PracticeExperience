<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<link rel="stylesheet" type="text/css" href="${CONFIG.res_url}script/jquery/plugin/auto_complete/jquery.autocomplete.css"/>
<script type="text/javascript" src="${CONFIG.res_url}script/jquery/plugin/auto_complete/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}/script/jquery/plugin/jquery.form.js"></script>
<style type="text/css">
.common-box {
	border: 1px solid #8CBFDE;
	margin:0 0 10px 0;
}

.disabled-input{
	color: #000000;
	background: #FFFFFF;
	cursor: not-allowed;
}
</style>
<script type="text/javascript">
//礼品json
var giftInfo = ${giftInfo};
var bankRemindMsg = "分行错误会导致无法赔款，建议通过选择录入";
$(document).ready(function() {	
	var payment = ${entity.payment};
	if (1 == payment) {
		$("#payment1").attr("checked", "checked");
		changePaymentItem(1);
	} else if (2 == payment) {
		$("#payment2").attr("checked", "checked");
		changePaymentItem(2);
	} else if (3 == payment) {
		$("#payment3").attr("checked", "checked");
		changePaymentItem(3);
	} else if (25 == payment) {
		$("#payment25").attr("checked", "checked");
		changePaymentItem(25);
	}

	if(giftInfo.length != 0){
		//大类生成下拉框
		var gift_item = $("#select");
		gift_item.empty();
		gift_item.append("<option value=''>--请选择--</option>");
		for(var i=0;i<giftInfo.length;i++){
			gift_item.append("<option value='" + i + "'>" + giftInfo[i].item_name + "</option>");
		}
	}
	
	$("#descript").blur();
	
	var succFlag = '${succFlag}';
    if (1 == succFlag) {
    	alert('修改成功！');
    	/* parent.location.replace(parent.location.href); */
    }
    var cseEntity = '${cseEntity}';
    if(cseEntity == null || cseEntity == ""){
    	$("#emailType").val("1");
    	$("#emailName").val('${entity.orderId}'+"投诉处理进展报备");
    	$("#route").val('${complaint.route}');
    	$("#orderId").val('${entity.orderId}');
    	$("#agencyName").val('');
    	var startTime = '${complaint.startTime}';
    	if(startTime != null && startTime != ''){
    		var startDate = new Date('${complaint.startTime}').Format("yyyy-MM-dd");
        	$("#startDate").val(startDate);
    	}
    	$("#checkProgress").val("针对客人投诉1、2、3,核实1、2、3，按照1、2、3，应当给到客人方案1、2、3。目前此单售后已对客达成一致");
    	$("#makeBetter").val("XXX  @XXX部门/人员   请关注改进");
    }else{
    	var emailType = '${cseEntity.emailType}';
    	$("#emailType").val('${cseEntity.emailType}');
    	$("#emailName").val('${cseEntity.emailName}');
    	$("#route").val('${cseEntity.route}');
    	$("#orderId").val('${cseEntity.orderId}');
    	$("#agencyName").val('${cseEntity.agencyName}');
    	var startTime = '${cseEntity.startDate}';
    	if(startTime != null && startTime != ''){
    	var startDate = new Date('${cseEntity.startDate}').Format("yyyy-MM-dd");
    	$("#startDate").val(startDate);
    	}
    	$("#checkProgress").val('${cseEntity.checkProgress}');
    	$("#makeBetter").val('${cseEntity.makeBetter}');
    	$("#receiveName").val('${cseEntity.receiveName}');
    	$("#ccName").val('${cseEntity.ccName}');
    	$("#guestNum").val('${cseEntity.guestNum}');
    	$("#groupOrders").val('${cseEntity.groupOrders}');
    	$("#passengerInfo").val('${cseEntity.passengerInfo}');
    	$("#remark").val('${cseEntity.remark}');
    }
    var sendEmail = ${sendEmail};
    $(".emailType_1").hide();
	$(".emailType_2").hide();
	$(".emailType_3").hide();
	$("input[name='sendEmail'][value="+sendEmail+"]").attr("checked",true);
    if(sendEmail == 0){
    	
    }else{
    	
  		var val = $("#emailType").val();
  		$(".emailType_"+val).show();
    	
    }
    
    $(".sendEmail").change(function(){ 
    	var val = $("input[name='sendEmail']:checked").val();//获得选中的radio的值 
    	if(val==1){
    		var val = $("#emailType").val();
    		$(".emailType_"+val).show();
    	}else{
    		$(".emailType_1").hide();
    		$(".emailType_2").hide();
    		$(".emailType_3").hide();
    	} 
    });
    $('#emailType').change(function(){ 
    	var val = $("#emailType").val();
    	$(".emailType_1").hide();
		$(".emailType_2").hide();
		$(".emailType_3").hide();
		
		if(cseEntity == null || cseEntity == ""){
	    	$("#emailType").val(val);
	    	$("#route").val('${complaint.route}');
	    	$("#orderId").val('${entity.orderId}');
	    	$("#agencyName").val('');
	    	var startTime = '${complaint.startTime}';
	    	if(startTime != null && startTime != ''){
	    		var startDate = new Date('${complaint.startTime}').Format("yyyy-MM-dd");
	        	$("#startDate").val(startDate);
	    	}
	    	if(val == 1){
	    		$("#emailName").val('${entity.orderId}'+"投诉处理进展报备");
		    	$("#checkProgress").val("针对客人投诉1、2、3,核实1、2、3，按照1、2、3，应当给到客人方案1、2、3。目前此单售后已对客达成一致");
		    	$("#makeBetter").val("XXX  @XXX部门/人员   请关注改进");
	    	}else if(val == 2){
	    		$("#emailName").val('${entity.orderId}'+"XXX关于客人XXX受伤情况处理报备");
		    	$("#checkProgress").val("核实处理情况：1、 受伤情况说明：伤情程度XX，时间XX、地点XX、人物XX、经过说明XX；2、 客人是否就医（是/否），医院名称:XX，住院/急诊，科室名称：XX，医疗费用支付方：XX；3、 旅行社是否有责（是/否）：已提醒地接社手写情况说明；4、 客人是否有保险（是/否）：已致电保险公司XXX，客服XXX给客人保险报案；5、 行程协调：@产品  请关注此单 @资深  关注安排后续上门慰问");
		    	$("#makeBetter").val("");
		    	$("#guestNum").val('${complaint.guestNum}');
		    	$("#passengerInfo").val('姓名XX、性别XX、年龄XX');
	    	}else{
	    		$("#emailName").val('${entity.orderId}'+"XXX 关于XXX问题处理一级报备");
		    	$("#checkProgress").val("默认内容：1、2、3、@产品 请关注并协助处理此批集体投诉售后持续跟进中");
		    	$("#makeBetter").val("");
		    	$("#groupOrders").val('${groupOrders}');
	    	}
	    	
	    }else{
	    	var emailType = '${cseEntity.emailType}';
	    	if(val == emailType){
	    		$("#emailType").val('${cseEntity.emailType}');
		    	$("#emailName").val('${cseEntity.emailName}');
		    	$("#route").val('${cseEntity.route}');
		    	$("#orderId").val('${cseEntity.orderId}');
		    	$("#agencyName").val('${cseEntity.agencyName}');
		    	var startTime = '${cseEntity.startDate}';
		    	if(startTime != null && startTime != ''){
		    	var startDate = new Date('${cseEntity.startDate}').Format("yyyy-MM-dd");
		    	$("#startDate").val(startDate);
		    	}
		    	$("#checkProgress").val('${cseEntity.checkProgress}');
		    	$("#makeBetter").val('${cseEntity.makeBetter}');
		    	$("#guestNum").val('${cseEntity.guestNum}');
		    	$("#passengerInfo").val('${cseEntity.passengerInfo}');
		    	$("#receiveName").val('${cseEntity.receiveName}');
		    	$("#ccName").val('${cseEntity.ccName}');
		    	$("#remark").val('${cseEntity.remark}');
	    	}else{
	    		$("#emailType").val(val);
		    	$("#route").val('${complaint.route}');
		    	$("#orderId").val('${entity.orderId}');
		    	$("#agencyName").val('');
		    	var startDate = new Date('${complaint.startTime}').Format("yyyy-MM-dd");
		    	$("#startDate").val(startDate);
		    	$("#remark").val('${cseEntity.remark}');
	    		if(val == 1){
		    		$("#emailName").val('${entity.orderId}'+"投诉处理进展报备");
			    	$("#checkProgress").val("针对客人投诉1、2、3,核实1、2、3，按照1、2、3，应当给到客人方案1、2、3。目前此单售后已对客达成一致");
			    	$("#makeBetter").val("XXX  @XXX部门/人员   请关注改进");
		    	}else if(val == 2){
		    		$("#emailName").val('${entity.orderId}'+"XXX关于客人XXX受伤情况处理报备");
			    	$("#checkProgress").val("核实处理情况：1、 受伤情况说明：伤情程度XX，时间XX、地点XX、人物XX、经过说明XX；2、 客人是否就医（是/否），医院名称:XX，住院/急诊，科室名称：XX，疗费用支付方：XX；3、 旅行社是否有责（是/否）：已提醒地接社手写情况说明；4、 客人是否有保险（是/否）：已致电保险公司XXX，客服XXX给客人保险报案；5、 行程协调：@产品  请关注此单 @资深  关注安排后续上门慰问");
			    	$("#makeBetter").val("");
			    	$("#guestNum").val('${cseEntity.guestNum}');
			    	$("#passengerInfo").val('${cseEntity.passengerInfo}');
		    	}else{
		    		$("#emailName").val('${entity.orderId}'+"XXX 关于XXX问题处理一级报备");
			    	$("#checkProgress").val("默认内容：1、2、3、@产品 请关注并协助处理此批集体投诉售后持续跟进中");
			    	$("#makeBetter").val("");
			    	$("#groupOrders").val('${cseEntity.groupOrders}');
		    	}
	    	}
	    	
	    }
		$(".emailType_"+val).show();
    });
	
});

//礼品分类onchange触发
function gift_type_changed(type){
	var big_idx = $(type).val();//大类索引
    var small_type =  giftInfo[big_idx].smal_types;//小类json数组
	var gift_small = $(type).parent().parent().find("select[name$='small']");
    //alert(gift_small);
	gift_small.empty();
	$("<option/>").attr("value","").html("--请选择--").appendTo(gift_small);
	for(var i=0;i<small_type.length;i++){
		$("<option/>").attr("value",i).html(small_type[i].smal_name).appendTo(gift_small);
	}
	
	$(type).parent().find("input[name$='type']").val(giftInfo[big_idx].item_name);
}

//礼品名称onchange触发
function gift_name_changed(name){
	var big_idx = $(name).parent().parent().find("select[name$='big']").val(); //大类索引
	var small_idx = $(name).val();//小类索引
	$(name).parent().parent().find("input[name$='price']").val(giftInfo[big_idx].smal_types[small_idx].pre_price); //小类价格
	$(name).parent().parent().find("input[name$='remark']").val(giftInfo[big_idx].smal_types[small_idx].comment); //小类备注
	
	$(name).parent().find("input[name$='name']").val(giftInfo[big_idx].smal_types[small_idx].smal_name);
	$(name).parent().find("input[name$='giftId']").val(giftInfo[big_idx].smal_types[small_idx].smal_id);
}

//表单验证
function check_solution_form() {
	//两券手机号加trim
	$('input[name$=mobileNo]').each(function(){
			$(this).val($.trim($(this).val()));
	});
	
	var shareTotal = '${shareTotal}';
	if (shareTotal > 0) {
		var payTotal = parseFloat($("#payTotal").html());
		if (payTotal > shareTotal) {
			alert("对客总额不能大于分担总额，如确实需要，请另发一个投诉单，分两笔赔给客人！");
			return false;
		}
	}

	var payment = $('input[name="entity.payment"]:checked').val();
	var cash = $("#cash").val();
	var cardUniqueId = $("#cardUniqueId").val();
	if (2 == payment && cash > 0) {
		if ("" == cardUniqueId) {
			alert("请填写账户信息！");
			return false;
		}
	}
	
	if (3 == payment && cash > 0) {
		if ("" == cardUniqueId) {
			alert("请填写账户信息！");
			return false;
		}
	}

	var descript = $("#descript").val();
	if ("" == descript || "请填写赔付理据！" == descript) {
		alert("请填写赔付理据！");
		$("#descript").click();
		$("#descript").focus();
		return false;
	}
	
	return true;
}

var data_json = [ {} ];

function addTouristRow() {
	$.tmpl.add_row("touristRow", data_json);
}

function addVoucherRow() {
	$.tmpl.add_row("voucherRow", data_json);
}

function addGiftTable() {
	$("#gift_tables").append($("#gift_table").html());
}

function addGiftRow(obj) {
	$(obj).parent().parent().after($("#giftRow").html());
}

function computePayTotal() {
	var cash = $("#cash").val();
	if("" == cash) {
		cash = 0;
	}
	var payTotal = parseFloat(cash);
	var tours = $("input[name='entity.tourticketList[xxx].amount']");
	if (tours.length > 1) {
		for(var i=0; i<tours.length-1; i++) {
			var amount = tours[i+1].value;
			if ("" == amount) {
				amount = 0;
			}
			payTotal += parseFloat(amount);
		}
	}
	$("#payTotal").html(payTotal);
}

//添加索引
function resetAllIndex() {
	resetIndex("input", "entity.tourticketList[xxx].mobileNo");
	resetIndex("input", "entity.tourticketList[xxx].amount");
	resetIndex("input", "entity.voucherList[xxx].mobileNo");
	resetIndex("input", "entity.voucherList[xxx].custId");
	resetIndex("input", "entity.voucherList[xxx].amount");
	resetIndex("select", "entity.giftInfoList[xxx].express");
	resetIndex("input", "entity.giftInfoList[xxx].receiver");
	resetIndex("input", "entity.giftInfoList[xxx].phone");
	resetIndex("input", "entity.giftInfoList[xxx].address");

	resetIndex2("input", "entity.giftInfoList[xxx].giftList[yyy].type");
	resetIndex2("input", "entity.giftInfoList[xxx].giftList[yyy].name");
	resetIndex2("input", "entity.giftInfoList[xxx].giftList[yyy].giftId");
	resetIndex2("input", "entity.giftInfoList[xxx].giftList[yyy].price");
	resetIndex2("input", "entity.giftInfoList[xxx].giftList[yyy].number");
	resetIndex2("input", "entity.giftInfoList[xxx].giftList[yyy].remark");
}

function resetIndex(type, name) {
	var objs = $(type + "[name='" + name + "']");
	if (objs.length > 1) {
		for(var i=0; i<objs.length-1; i++) {
			objs[i+1].name = objs[i+1].name.replace(/xxx/, i);
		}
	}
}

function resetIndex2(type, name) {
	var objs = $(type + "[name='" + name + "']");
	if (objs.length > 1) {
		var x = 0;
		var t = 0;
		for(var i=0; i<objs.length-1; i++) {
			objs[i].name = objs[i].name.replace(/xxx/, x).replace(/yyy/, i-t);
			var max = objs[i].parentNode.parentNode.parentNode.parentNode.rows.length;
			var idx = objs[i].parentNode.parentNode.rowIndex;
			if (idx == max-1) {
				x++;
				t = i+1;
			}
		}
	}
}

function doSubmit(flag) {
	if (!check_solution_form()) {
		return false;
	}

	var bank = $("#bank").val();
	if ("银行名称 开户行城市 支行/分行/分理处" == bank) {
		$("#bank").val('');
		
	}
	var descript = $("#descript").val();
	if ("请填写赔付理据！" == descript) {
		$("#descript").val('');
	}
	
	resetAllIndex();
	var dealDepart = '${complaint.dealDepart}';
	var payTotal = $("#payTotal").html();
	if (("售前组" == dealDepart||"会员事业部" == dealDepart||"途致事业部" == dealDepart||"预订中心" == dealDepart||"客户事业部" == dealDepart||"会员顾问" == dealDepart) && 0 == payTotal) {
		if (confirm("对客赔偿总额为0，是否结单？")) {
			flag = 1;
		}
	}
	$("#saveOrSubmit").val(flag);
	$("#form").submit();
}

/* 改变付款方式联动 */
function changePaymentItem(item) {
	if (1 == item) {
		$("#th_cash").attr("rowspan", "4");
		$("#tr_receiver").show();
		$("#tr_idCardNo").show();
		$("#tr_collectionUnit").hide();
		$('#tr_big_bank').hide();
		$('#tr_bank_district').hide();
		$("#tr_bank").hide();
		$("#tr_account").hide();
		$("#tr_toOrderId").hide();
	} else if (2 == item) {
		$("#th_cash").attr("rowspan", "7");
		$("#tr_receiver").hide();
		$("#tr_idCardNo").hide();
		$("#tr_collectionUnit").find("th").text("收款人");
		$("#tr_collectionUnit").show();
		$('#tr_big_bank').show();
		$('#tr_bank_district').show();
		$("#tr_bank").show();
		$("#tr_account").show();
		$("#tr_toOrderId").hide();
	} else if (3 == item) {
		$("#th_cash").attr("rowspan", "7");
		$("#tr_receiver").hide();
		$("#tr_idCardNo").hide();
		$("#tr_collectionUnit").find("th").text("收款单位");
		$("#tr_collectionUnit").show();
		$('#tr_big_bank').show();
		$('#tr_bank_district').show();
		$("#tr_bank").show();
		$("#tr_account").show();
		$("#tr_toOrderId").hide();
	} else if (25 == item) {
		$("#th_cash").attr("rowspan", "3");
		$("#tr_receiver").hide();
		$("#tr_idCardNo").hide();
		$("#tr_collectionUnit").hide();
		$('#tr_big_bank').hide();
		$('#tr_bank_district').hide();
		$("#tr_bank").hide();
		$("#tr_account").hide();
		$("#tr_toOrderId").show();
	}
}

function updateAccountWindow(){
	var	parameter="";
	var car_id="";
	if($("#cardUniqueId").val()==""){
		parameter="buzType=0&serviceType=1";
		parameter=encryptByMD5(parameter)
	}else{
		parameter="buzType=0&serviceType=2";
		parameter=encryptByMD5(parameter)+"&cardId="+$("#cardUniqueId").val();
	}
	openDialog('填写账户信息',"${CONFIG.PCI_URL}?"+parameter);
}

function encryptByMD5(parameter){
	var result;
	$.ajax({
		type : "POST",
		url : "complaint_solution-encryptByMD5",
		data : {
			"parameter" : parameter
		},
		async : false,
		success : function(data) {
			result=parameter+"&sign="+data.retObj;
		}
	});
	return result;
}

var bankInfo = "";//银行卡Id,收款人,银行名称,开户省,开户市,分行名称,帐号
window.addEventListener('message',function(e){
    bankInfo =e.data;
    writeBankInfo();
    // alert("Listener bankInfo:"+bankInfo);
    if(bankInfo == "") {
      colseBankInfoFrame();
    }
},false);

//由于https无法获取http父窗口元素，无法在弹出iframe关闭自身，所以回调此方法关闭，此处关闭方法为layer.js关闭方法，其他iframe调用方法关闭可自定义
function colseBankInfoFrame(){
    layer.close($(".layui-layer-iframe").attr("times"));//index是弹出层index
}

//将bankInfo中的信息写到页面上
function writeBankInfo(){
	var bankArr=bankInfo.split(",");
	bankArr[0]==undefined?"":bankArr[0]==""?"":$("#cardUniqueId").val(bankArr[0]);//银行卡id
	bankArr[1]==undefined?"":bankArr[1]==""?"":$("#collectionUnit").val(bankArr[1]);//收款单位
	bankArr[2]==undefined?"":bankArr[2]==""?"":$("#bigBank").val(bankArr[2]);//总行
	bankArr[3]==undefined?"":bankArr[3]==""?"":$("#bankProvince").val(bankArr[3]);//所在省
	bankArr[4]==undefined?"":bankArr[4]==""?"":$("#bankCity").val(bankArr[4]);//所在市
	bankArr[5]==undefined?"":bankArr[5]==""?"":$("#bank").val(bankArr[5]);//分行
	bankArr[6]==undefined?"":bankArr[6]==""?"":$("#account").val(bankArr[6]);//账号
}

</script>

</HEAD>
<BODY>
<c:if test="${shareTotal > 0}">
<div class="common-box">
	<span style="color: navy;">分担方案已提交，分担总额为：${shareTotal } 元</span>
</div>
</c:if>
<div class="common-box">
<form name="form" id="form" method="post" enctype="multipart/form-data" action="complaint_solution-updateSolution">
	<input type="hidden" name="entity.id" value="${entity.id }">
	<input type="hidden" name="entity.complaintId" value="${complaintId }">
	<input type="hidden" name="entity.saveOrSubmit" id="saveOrSubmit">
	<input type="hidden" name="entity.cardUniqueId" id="cardUniqueId" value="${entity.cardUniqueId }">
	<table class="datatable" width="100%">
		<tr>
			<th width="100" align="right" colspan="2">对客赔偿总额：</th>
			<td><span id="payTotal">${entity.cash + entity.touristBook}</span> 元 <font color="red">（赔偿总额 = 现金 + 旅游券）</td>
		</tr>
		<tr>
			<th width="100" align="right" colspan="2">联系人：</th>
			<td>
				<input name="entity.userName" id="userName" type="text" size="20" value="${entity.userName }"> 
			</td>
		</tr>
		<tr>
			<th width="100" align="right" colspan="2">联系手机：</th>
			<td>
				<input name="entity.phone" id="phone" type="text" size="20" value="${entity.phone }"> 
			</td>
		</tr>
		<tr>
			<th id="th_cash" rowspan="5">赔<br>偿<br>现<br>金</th>
			<th width="100" align="right">金额：</th>
			<td><input name="entity.cash" type="text" id="cash" size="20" onblur="computePayTotal()" value="${entity.cash }"> 元</td>
		</tr>
		<tr>
			<th width="100" align="right">退款方式：</th>
		    <td>
		    <label><input type="radio" name="entity.payment" id="payment1" value="1" onclick="changePaymentItem(1)"> 现金</label>
		    <label><input type="radio" name="entity.payment" id="payment2" value="2" onclick="changePaymentItem(2)"> 对私汇款</label>
		    <label><input type="radio" name="entity.payment" id="payment3" value="3" onclick="changePaymentItem(3)"> 对公汇款</label>
		    <label><input type="radio" name="entity.payment" id="payment25" value="25" onclick="changePaymentItem(25)"> 转单</label>
		    </td>
		</tr>
		<tr id="tr_receiver"  style="display: none;">
			<th width="100" align="right">收款人：</th>
		    <td><input name="entity.receiver" type="text" id="receiver" size="20" value="${entity.receiver }"></td>
		</tr>
		<tr id="tr_idCardNo" style="display: none;">
			<th width="100" align="right">身份证号码：</th>
		    <td><input name="entity.idCardNo" type="text" id="idCardNo" size="20" value="${entity.idCardNo }"></td>
		</tr>
		<tr id="tr_collectionUnit">
			<th width="100" align="right">收款人：</th>
		    <td><input type="text" id="collectionUnit" size="40" value="${entity.collectionUnit }" class="disabled-input" readonly="readonly" onfocus="this.blur()"/></td>
		</tr>
		
		<tr id="tr_big_bank" style="display: none;">
			<th width="100" align="right">银行名称：</th>
		    <td>
		    <input type="text" id="bigBank" size="40" value="${entity.bigBank }" class="disabled-input" readonly="readonly" onfocus="this.blur()"/>
		    <input type="button" class="blue"  value="填写账户信息" onclick="updateAccountWindow()"></td>
		    </td>
		</tr>
		
		<tr id="tr_bank_district">
			<th width="100" align="right">开户行省份城市</th>
			<td>
					<input type="text" size="10" id="bankProvince" value="${entity.bankProvince }" class="disabled-input" readonly="readonly" onfocus="this.blur()"/>省
					<input type="text" size="10" id="bankCity" value="${entity.bankCity }" class="disabled-input" readonly="readonly" onfocus="this.blur()"/>市
			</td>
		</tr>
		<tr id="tr_bank">
		    <th width="100" align="right">分行名称：</th>
            <td>
         	<input type="text" id="bank" size="40" value="${entity.bank }" class="disabled-input" readonly="readonly" onfocus="this.blur()"/>
            </td>
		</tr>
		<tr id="tr_account">
		    <th width="100" align="right">账号：</th>
            <td><input type="text" id="account" size="30" value="${entity.account }" class="disabled-input" readonly="readonly" onfocus="this.blur()"></td>
		</tr>
		<tr id="tr_toOrderId" style="display: none;">
			<th width="100" align="right">订单号：</th>
		    <td><input name="entity.toOrderId" type="text" id="toOrderId" size="20" value="${entity.toOrderId }"></td>
		</tr>
		<tr>
			<th align="right" colspan="2">赔偿旅游券：</th>
			<td>
			    <table>
			        <tr>
			            <th width="170">手机号</th>
			            <th width="170">金额</th>
			            <td><input type="button" class="blue" value="添加" onclick="addTouristRow()"></td>
			        </tr>
			        <tr id="touristRow" style="display: none" align="center">
						<td><input type="text" name="entity.tourticketList[xxx].mobileNo"></td>
			            <td><input type="text" name="entity.tourticketList[xxx].amount" onblur="computePayTotal()"> 元</td>
			            <td><input type="button" onclick="$(this).parent().parent().remove();computePayTotal()" value="删除"></td>
					</tr>
			        <c:forEach items="${entity.tourticketList }" var="tour">
			        <tr align="center">
						<td><input type="text" name="entity.tourticketList[xxx].mobileNo" value="${tour.mobileNo }"></td>
			            <td><input type="text" name="entity.tourticketList[xxx].amount" onblur="computePayTotal()" value="${tour.amount }"> 元</td>
			            <td><input type="button" onclick="$(this).parent().parent().remove();computePayTotal()" value="删除"></td>
					</tr>
			        </c:forEach>
			    </table>
			</td>
		</tr>
		<tr>
		    <th align="right" colspan="2">赠送礼品：</th>
			<td id="gift_tables">
				<input type="button" class="blue" value="添加收件人及礼品信息" onclick="addGiftTable()">
				<div id="gift_table" style="display: none">
					<table width="100%" border="1" cellspacing="0" cellpadding="0">
						<tr>
							<td colspan="6">
								<select name="entity.giftInfoList[xxx].express">
									<option value="2" selected="selected">快递领取</option>
									<option value="1">本人领取</option>
								</select>　
								<input name="entity.giftInfoList[xxx].receiver" type="text" style="width: 70px; color: #B0B0B0;" value="收件人" onclick="javascript:if(this.value=='收件人'){this.value='';};this.style.color='#000000';" onblur="javascript:if(this.value==''){this.value='收件人';this.style.color='#B0B0B0';}">　
								<input name="entity.giftInfoList[xxx].phone" type="text" style="width: 100px; color: #B0B0B0;" value="联系电话" onclick="javascript:if(this.value=='联系电话'){this.value='';};this.style.color='#000000';" onblur="javascript:if(this.value==''){this.value='联系电话';this.style.color='#B0B0B0';}">　
								<input name="entity.giftInfoList[xxx].address" type="text" style="width: 320px;color: #B0B0B0;" value="收件地址" onclick="javascript:if(this.value=='收件地址'){this.value='';};this.style.color='#000000';" onblur="javascript:if(this.value==''){this.value='收件地址';this.style.color='#B0B0B0';}">　
								<input type="button" onclick="$(this).parent().parent().parent().remove()" value="-删除-" />
							</td>
						</tr>
						<tr>
							<th>礼品分类</th>
							<th>礼品名称</th>
							<th>单价(元)</th>
							<th>数量</th>
							<th>备注</th>
							<td><input type="button" class="blue" value="添加" onclick="addGiftRow(this)"></td>
						</tr>
					</table>
				</div>
				<c:forEach items="${entity.giftInfoList }" var="giftInfo">
				<table width="100%" border="1" cellspacing="0" cellpadding="0">
					<tr>
						<td colspan="6">
							<select name="entity.giftInfoList[xxx].express">
								<option value="2" <c:if test="${2==giftInfo.express}">selected='selected'</c:if>>快递领取</option>
								<option value="1" <c:if test="${1==giftInfo.express}">selected='selected'</c:if>>本人领取</option>
							</select>　
							<input name="entity.giftInfoList[xxx].receiver" type="text" style="width: 70px;" value="${giftInfo.receiver }" onclick="javascript:if(this.value=='收件人'){this.value='';};this.style.color='#000000';" onblur="javascript:if(this.value==''){this.value='收件人';this.style.color='#B0B0B0';}">　
							<input name="entity.giftInfoList[xxx].phone" type="text" style="width: 100px;" value="${giftInfo.phone }" onclick="javascript:if(this.value=='联系电话'){this.value='';};this.style.color='#000000';" onblur="javascript:if(this.value==''){this.value='联系电话';this.style.color='#B0B0B0';}">　
							<input name="entity.giftInfoList[xxx].address" type="text" style="width: 320px;" value="${giftInfo.address }" onclick="javascript:if(this.value=='收件地址'){this.value='';};this.style.color='#000000';" onblur="javascript:if(this.value==''){this.value='收件地址';this.style.color='#B0B0B0';}">　
							<input type="button" onclick="$(this).parent().parent().parent().remove()" value="-删除-" />
						</td>
					</tr>
					<tr>
						<th>礼品分类</th>
						<th>礼品名称</th>
						<th>单价(元)</th>
						<th>数量</th>
						<th>备注</th>
						<td><input type="button" class="blue" value="添加" onclick="addGiftRow(this)"></td>
					</tr>
					<c:forEach items="${giftInfo.giftList }" var="gift">
					<tr align="center">
						<td>
							<select name="giftType_big" id="select" onchange="gift_type_changed(this)"></select>
							<input type="hidden" name="entity.giftInfoList[xxx].giftList[yyy].type" value="${gift.type }">
						</td>
						<td>
							<select name="giftType_small" onchange="gift_name_changed(this)"></select>
			 				<input type="hidden" name="entity.giftInfoList[xxx].giftList[yyy].name" value="${gift.name }">
							<input type="hidden" name="entity.giftInfoList[xxx].giftList[yyy].giftId" value="${gift.giftId }">
			 			</td>
			 			<td><input name="entity.giftInfoList[xxx].giftList[yyy].price" type="text" size="6" readonly="readonly" value="${gift.price }"></td>
						<td>
							<input name="entity.giftInfoList[xxx].giftList[yyy].number" type="text" value="${gift.number }" size="4"> 个
						</td>
			 			<td><input name="entity.giftInfoList[xxx].giftList[yyy].remark" type="text" size="10" readonly="readonly" value="${gift.remark }"></td>
						<td>
							<input type="button" onclick="$(this).parent().parent().remove()" value="删除" /> 
						</td>
					</tr>
					</c:forEach>
				</table>
				</c:forEach>
			</td>
		</tr>
		<tr>
			<th width="100" align="right" colspan="2">时间要求：</th>
		    <td><input name="entity.appointedTime" type="text" id="appointedTime" onclick="WdatePicker()" readOnly="readonly" style="width: 150px;" value="<fmt:formatDate value='${entity.appointedTime }' pattern='yyyy-MM-dd HH:mm:ss'/>"></td>
		</tr>
		<tr>
			<th width="100" align="right" colspan="2">备注：</th>
		    <td>
		    	<textarea name="entity.descript" id="descript" cols="45" rows="4" 
		    		onclick="javascript:if($(this).val()=='请填写赔付理据！'){$(this).val('');};this.style.color='#000000';" 
            		onblur="javascript:if($(this).val()==''){$(this).val('请填写赔付理据！');this.style.color='#B0B0B0';}"><c:if test="${entity.descript == ''}">请填写赔付理据！</c:if><c:if test="${entity.descript != ''}">${entity.descript}</c:if></textarea>
		    </td>
		</tr>
		<tr>
			<th width="100" align="right" colspan="2">是否满意完结：</th>
			<td>
				<s:radio list="#{1:'是',0:'否'}" name="entity.satisfactionFlag" ></s:radio>
			</td>
		</tr>
		<c:if test="${empty cseEntity}"> 
		<tr>
			<th width="100" align="right" colspan="2">邮件提醒：</th>
			<td>
				<s:radio list="#{0:'否',1:'是'}" name="sendEmail" cssClass="sendEmail"></s:radio>
			</td>
		</tr>
		<tr id="tr_emailType" class="emailType_1 emailType_2 emailType_3">
			<th width="100" align="right" colspan="2">邮件类型：</th>
			<td>
				<select aria-invalid="false" class="mr10 valid" name="cseEntity.emailType" id="emailType">
					<option value="1">结单回复</option>
					<option value="2">受伤报备</option>
					<option value="3">集体投诉</option>
				</select>
			</td>
		</tr>
		<tr id="tr_emailName" class="emailType_1 emailType_2 emailType_3">
			<th width="100" align="right" colspan="2">邮件名称：</th>
			<td><input type="text" name="cseEntity.emailName" id="emailName" size="40"></td>
		</tr>
		<tr id="tr_route" class="emailType_1 emailType_2 emailType_3">
			<th width="100" align="right" colspan="2">线路名称：</th>
			<td><input type="text" name="cseEntity.route" id="route" size="40"></td>
		</tr>
		<tr id="tr_orderId" class="emailType_1 emailType_2 emailType_3">
			<th width="100" align="right" colspan="2">订单号：</th>
			<td><input type="text" name="cseEntity.orderId" id="orderId" onkeyup="value=value.replace(/[^\d]/g,'')" size="40" ></td>
		</tr>
		<tr id="tr_agencyName" class="emailType_1 emailType_2 emailType_3">
			<th width="100" align="right" colspan="2">供应商名称（多个用英文,分隔）：</th>
			<td><input type="text" name="cseEntity.agencyName" id="agencyName" size="40"></td>
		</tr>
		<tr id="tr_startDate" class="emailType_1 emailType_2 emailType_3">
			<th width="100" align="right" colspan="2">出游日期：</th>
			<td><input name="cseEntity.startDate" type="text" id="startDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readOnly="readonly" style="width: 150px;"></td>
		</tr>
		<tr id="tr_guestNum"  class="emailType_2">
			<th width="100" align="right" colspan="2">出游人数：</th>
		    <td><input type="text" name="cseEntity.guestNum" id="guestNum" size="40"></td>
		</tr>
		<tr id="tr_groupOrder" class="emailType_3">
			<th width="100" align="right" colspan="2">同团订单：</th>
		    <td><input type="text" name="cseEntity.groupOrders" id="groupOrders" size="40"></td>
		</tr>
		<tr id="tr_passengerInfo" class="emailType_2">
			<th width="100" align="right" colspan="2">受伤客人信息：</th>
		    <td><input type="text" name="cseEntity.passengerInfo" id="passengerInfo" size="40"></td>
		</tr>
		<tr id="tr_receiveName" class="emailType_1 emailType_2 emailType_3">
			<th width="100" align="right" colspan="2">补充收件人（多个用英文,分隔）：</th>
			<td><input type="text" name="cseEntity.receiveName" id="receiveName" size="40"></td>
		</tr>
		<tr id="tr_ccName" class="emailType_1 emailType_2 emailType_3">
			<th width="100" align="right" colspan="2">补充抄送人（多个用英文,分隔）：</th>
		    <td><input type="text" name="cseEntity.ccName" id="ccName" size="40"></td>
		</tr>
		<tr id="tr_remark" class="emailType_1 emailType_2 emailType_3">
			<th width="100" align="right" colspan="2">客人投诉点：</th>
		    <td>
		    	<textarea name="cseEntity.remark" id="remark" cols="45" rows="4"></textarea>
            </td>
		</tr>
		<tr id="tr_checkProgress" class="emailType_1 emailType_2 emailType_3">
			<th width="100" align="right" colspan="2">核实处理情况：</th>
		    <td>
		    	<textarea name="cseEntity.checkProgress" id="checkProgress" cols="45" rows="4"></textarea>
            </td>
		</tr>
		<tr id="tr_makeBetter" class="emailType_1">
			<th width="100" align="right" colspan="2">建议改进点：</th>
		    <td>
		    	<textarea name="cseEntity.makeBetter" id="makeBetter" cols="45" rows="4"></textarea>
            </td>
		</tr>
		</c:if>
		<tr>
			<th colspan="2">&nbsp;</th>
			<td>
			    <input class="pd5" type="button" value="保存" onclick="doSubmit(0)">　
			    <c:if test="${canAddSoulation == true}">
			    	<input class="pd5" type="button" value="提交" onclick="doSubmit(1)">　
			    </c:if>
				<input class="pd5" type="button" value="重置" onclick="window.location.href='complaint_solution?orderId=${entity.orderId }&complaintId=${entity.complaintId }'">
			</td>
		</tr>
	</table>
	<table>
		<tbody id="giftRow" style="display: none">
			<tr align="center">
				<td>
					<select name="giftType_big" id="select" onchange="gift_type_changed(this)"></select>
					<input type="hidden" name="entity.giftInfoList[xxx].giftList[yyy].type">
				</td>
				<td>
					<select name="giftType_small" onchange="gift_name_changed(this)"></select>
	 				<input type="hidden" name="entity.giftInfoList[xxx].giftList[yyy].name">
					<input type="hidden" name="entity.giftInfoList[xxx].giftList[yyy].giftId">
	 			</td>
	 			<td><input name="entity.giftInfoList[xxx].giftList[yyy].price" type="text" size="6" readonly="readonly"></td>
				<td>
					<input name="entity.giftInfoList[xxx].giftList[yyy].number" type="text" value="1" size="4"> 个
				</td>
	 			<td><input name="entity.giftInfoList[xxx].giftList[yyy].remark" type="text" size="10" readonly="readonly"></td>
				<td>
					<input type="button" onclick="$(this).parent().parent().remove()" value="删除" /> 
				</td>
			</tr>
		</tbody>
	</table>
</form>
</div>
<font color="red">友情提醒：金额最多支持两位小数</font>
</BODY>
<script type="text/javascript">
//页面加载时，生成已保存礼品数据礼品分类及礼品下拉框，并选中保存的礼品分类及礼品
$(document).ready(function() {
	$("select[name$='big']").each(function(i, n){
		//alert($(n).parent().parent().attr("id"));
		if(giftInfo.length != 0 && $(n).parent().parent().attr("id") != "giftRow"){
			//大类生成下拉框
			$(n).empty();
			$(n).append("<option value=''>--请选择--</option>");
			for(var i=0;i<giftInfo.length;i++){
				if(giftInfo[i].item_name == $(n).parent().find("input[name$='type']").val()){
					$(n).append("<option value='" + i + "' selected>" + giftInfo[i].item_name + "</option>");
				} else {
					$(n).append("<option value='" + i + "'>" + giftInfo[i].item_name + "</option>");
				}
			}
			//小类生成下拉框
			var big_idx = $(n).val();//大类索引
		    var small_type =  giftInfo[big_idx].smal_types;//小类json数组
			var gift_small = $(n).parent().parent().find("select[name$='small']");
		    //alert(gift_small);
			gift_small.empty();
			$("<option/>").attr("value","").html("--请选择--").appendTo(gift_small);
			for(var i=0;i<small_type.length;i++){
				if(small_type[i].smal_name == $(gift_small).parent().find("input[name$='name']").val()){
					$("<option/>").attr("value",i).attr("selected", true).html(small_type[i].smal_name).appendTo(gift_small);
				} else {
					$("<option/>").attr("value",i).html(small_type[i].smal_name).appendTo(gift_small);
				}
			}
		}
	});
});
</script>
