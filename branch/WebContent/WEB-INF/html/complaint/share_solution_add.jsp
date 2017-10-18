<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>

<link href="${CONFIG.res_url}css/thickbox.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${CONFIG.res_url}/script/thickbox-compressed.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}/script/jquery/plugin/jquery.form.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}/script/DatePickerNew/WdatePicker.js" ></script>
<style type="text/css">
.common-box {
	border: 1px solid #8CBFDE;
	margin:0 0 10px 0;
}
</style>
<script type="text/javascript">
$(document).ready(function() {
	var succFlag = '${succFlag}';
    if (1 == succFlag) {
    	alert('保存成功！');
    	parent.location.replace(parent.location.href);
    }
});

function share_solution_check_form() {
	var customerTotal = '${customerTotal}';
	if (customerTotal > 0) {
		var shareTotal = parseFloat($("#share_total").html());
		if (shareTotal < customerTotal) {
			alert("分担总额不能小于对客赔偿总额！");
			return false;
		}
	}

	//外币结算的供应商外币数额必填校验
	var foreignCurrencyNums = $('.fcBlock:visible input');
	for(var i=0;i<foreignCurrencyNums.length;i++){
		var foreignCurrencyNum = foreignCurrencyNums[i].value;
		if(foreignCurrencyNum==''||isNaN(foreignCurrencyNum)){
			alert('请填写外币金额');
			return false;
		}
	}
	
	
	return true;
}

var data_json = [ {} ];

//批量增加责任人
function addRow_employee() {
	zeroSet();
	$.tmpl.add_row("employee_row", data_json);
}

//批量增加成本类型
function addRow_qualityTool(){
	zeroSet();
	$.tmpl.add_row("quality_row", data_json);
}

//批量增加供应商
function addTable_supplier() {
	zeroSet();
	var name = $("#agencyName option:selected").text();
	if(name != "全部"){
		$("#agency_name").attr("value",name);
		
		var agencyId = getAgency(name).agencyId;
		if(agencyId != 0){
			$("#agency_code").attr("value",agencyId);
		}
		
	}
	
	var data_json = [ {} ];
	$.tmpl.add_row("n_x", data_json);
	//console.debug($("#agency_name"));

}

function getAgency(name) {
	var nameEx = name.replace(new RegExp(/(&)/g),'<>');
	var agency;
	if(name != '' && name != "全部") {
		$.ajax({
			type: "POST",
			url: "share_solution-checkSupplier",
			data: {
				"name" : nameEx,
				"complaintId" : $("#complaintId").val()
			},
			async:false,
			success: function(data){
				//alert( data); 
				var json=jQuery.parseJSON(data);
				if(json.agencyId == 0) {
					alert("请输入正确的供应商品牌名");
				} else {
					agency = json;
				}
			} 
		});
	}
	return agency;
}

function getNbFlag(agencyId){
	var nbFlag;
	if(agencyId != '' && agencyId != 0) {
		$.ajax({
			type: "POST",
			url: "share_solution-getNbFlag",
			data: {
				"agencyId" : agencyId,
				"complaintId" : $("#complaintId").val()
			},
			async: false,
			success: function(data) {
				var json = jQuery.parseJSON(data);
				nbFlag = json.nbFlag;
			} 
		})
	}
	return nbFlag;
}

function resetAllIndex() {
	resetIndex("input", "entity.employeeShareList[xxx].name");
	resetIndex("input", "entity.employeeShareList[xxx].number");
	resetIndex("select", "entity.qualityToolList[xxx].toolId");
	resetIndex("input", "entity.qualityToolList[xxx].toolName");
	resetIndex("input", "entity.qualityToolList[xxx].total");
}

function resetIndex(type, name) {
	var objs = $(type + "[name='" + name + "']");
	if (objs.length > 1) {
		for(var i=0; i<objs.length-1; i++) {
			objs[i+1].name = objs[i+1].name.replace(/xxx/, i);
		}
	}
}

/**
 * 统计成本类型金额总数
 */
function sumQualityTool() {
	var total = sumTotal("entity.qualityToolList[xxx].total");
	$('#quality_tool_total_price').html('（共计：' + total + '元）');
	$("#qualityToolTotal").attr("value", total);
	sumAllTotal();
}

function sumEmployeeTotal() {
	var total = sumTotal("entity.employeeShareList[xxx].number");
	$("#employeeTotal").attr("value", total);
	sumAllTotal();
}

function sumSupplierTotal() {
	var payoutNums = $.find("input[type='text'][name^='entity.supportShareList['][name$='payoutNum']");
	var total = 0.00;
	for (var i=0; i<payoutNums.length; i++) {
		var m = $.trim(payoutNums[i].value);
		if (m != '') {
			total += parseFloat(m);
		}
	}
	total = total.toFixed(2);
	$("#supplierTotal").attr("value", total);
	sumAllTotal();
}

function sumTotal(name) {
	var empObjs = $("input[name='" + name + "']");
	var total = 0.00;
	for (var i=0; i<empObjs.length; i++) {
		var m = $.trim(empObjs[i].value);
		if (m != '') {
			total += parseFloat(m);
		}
	}
	total = total.toFixed(2);
	return total;
}

function sumAllTotal() {
	var qualityToolTotal = $.trim($("#qualityToolTotal").val());
	if ('' == qualityToolTotal) {
		qualityToolTotal = 0;
	}
	var employeeTotal = $.trim($("#employeeTotal").val());
	if ('' == employeeTotal) {
		employeeTotal = 0;
	}
	var supplierTotal = $.trim($("#supplierTotal").val());
	if ('' == supplierTotal) {
		supplierTotal = 0;
	}
	var orderGains = $.trim($("#orderGains").val());
	if ('' == orderGains) {
		orderGains = 0;
	}
	var special = $.trim($("#special").val());
	if ('' == special) {
		special = 0;
	}
	var refundToIndemnity = $.trim($("#refundToIndemnity").val());
	if ('' == refundToIndemnity) {
		refundToIndemnity = 0;
	}
	
	var total = parseFloat(qualityToolTotal) + parseFloat(employeeTotal) + parseFloat(supplierTotal) 
					+ parseFloat(orderGains) + parseFloat(special);
	if (total > 0) {
		zeroSet();
	} else {
		total = parseFloat(refundToIndemnity);
	}
	
	$("#total").attr("value", total);
	$('#share_total').html(total);
}

/**
 * 1. 根据供应商品牌名取供应商编号
 * 2. 判断供应商是否已经存在
 */
function checkSupplier(input){
	var name = input.value.replace(new RegExp(/(&)/g),'<>');
	var agencyId = $(input).parent().parent().find("input[name^='entity.supportShareList'][name$='code']").val(); 
	if(input.value != '') {
		$.ajax({
			type: "POST",
			url: "share_solution-checkSupplier",
			data:  {
				"name" : name,
				"complaintId" : $("#complaintId").val()
			},
			success: function(data){
				var json=jQuery.parseJSON(data);
				if(json.agencyId == 0) {
					alert("请输入正确的供应商品牌名");
					$(input).parent().parent().find("input[name^='entity.supportShareList'][name$='code']").val("");
				} else {
					var agencyId = json.agencyId;
					var fcType=json.foreignCurrencyType;
					var currencyName=json.currencyName;
					var agencys = $.find("input[type='text'][name^='entity.supportShareList[']");
					var aLength = agencys.length;
					for (var i=0; i<aLength; i++) {
						if (agencyId != '' && $.trim(agencys[i].value) == $.trim(agencyId)) {
							alert('供应商承担不允许添加两个相同的供应商');
							return false;
						}
					}
					//$(input).attr("readonly", "readonly");
					$(input).parent().parent().find("input[name^='entity.supportShareList'][name$='code']").val(agencyId);

					var nbFlag = getNbFlag(agencyId);
                    if (1 == nbFlag) {
                    	$(input).parent().parent().find("span[id^='nbFlagText']").html("[NB供应商赔付确认]");
                    	$(input).parent().parent().find("input[name^='entity.supportShareList'][name$='nbFlag']").val(nbFlag);
                    }
					
					var agencyTr =$(input).parent().parent().siblings('tr');
					
					$(input).parent().parent().find('[name*=".foreignCurrencyType"]').val(fcType);
					$(input).parent().parent().find('[name*=".foreignCurrencyName"]').val(currencyName);
					if(fcType!=undefined && fcType!="" && fcType!='0' && fcType!='8' ){
						agencyTr.find('.fcBlock').css("display","block");
						agencyTr.find('.foreignCurrency').html(currencyName);
					}
				}
			} 
		})
	}
}

function checkemploy(input){
	$(input).attr("readonly",true);
	if(input.value==''||input.value==null){
		alert("员工人名为空,请重新输入!");
		$(input).attr("readonly",false);
	}else {
		$.ajax({
		type: "POST",
		url: "share_solution-checkEmploy",
		data: "name=" + input.value,
		success: function(data){
			if(data==0) {
				alert("请输入正确的员工姓名");
				$(input).attr("readonly",false);
			}
		}
		});
	}
}

function mutex() {
    if($("#refundToIndemnity").val()>0){
	     $("#quality_tool_table tr").each(function(trindex,tritem){
	     	if(trindex > 0){
	     		$(this).remove();
	     	}
	     });
	     $("#employee_table tr").each(function(trindex,tritem){
	     	if(trindex > 0){
	     		$(this).remove();
	     	}
	     });
	     $("#agencyTable").html("");
	    
	     $("#orderGains").val(0.0);
	     $("#special").val(0.0);
	     $("#qualityToolTotal").val(0.0);
	     $("#employeeTotal").val(0.0);
	     $("#supplierTotal").val(0.0);
	     $('#quality_tool_total_price').html('（共计：0.00 元）');
	     sumAllTotal();
     }
}

function zeroSet() {
   $("#refundToIndemnity").val(0.0);
}

function doSubmit(flag) {
	if (!share_solution_check_form()) {
		return false;
	}
	resetAllIndex();
	$("#saveOrSubmit").val(flag);
	$("#share_solution_form").submit();
}

function setToolName(obj) {
	var name = $(obj).find("option:selected").text();
	$(obj).next().val(name);
}

</script>

</HEAD>
<BODY>
<c:if test="${customerTotal > 0}">
<div class="common-box">
	<span style="color: navy;">对客解决方案已提交，对客赔偿总额为：${customerTotal } 元</span>
</div>
</c:if>
<div class="common-box">
<form name="form" id ="share_solution_form" method="post" enctype="multipart/form-data" action="share_solution-addShare">
	<input type="hidden" name="entity.orderId" id="id" value="${orderId}">
	<input type="hidden" name="entity.complaintId" value="${complaintId}" id="complaintId">
	<input type="hidden" name="entity.total" id="total">
	<input type="hidden" name="entity.supplierTotal" id="supplierTotal">
	<input type="hidden" name="entity.qualityToolTotal" id="qualityToolTotal">
	<input type="hidden" name="entity.employeeTotal" id="employeeTotal">
	<input type="hidden" name="entity.saveOrSubmit" id="saveOrSubmit">
	<table class="datatable" width="100%">
		<tr>
			<th width="156" align="right">分担总额：</th>
			<td><span id="share_total">0.00</span> 元</td>
		</tr>
		<tr>
			<th width="156" align="right">供应商承担赔偿金额：</th>
			<td>
				<select class="mr10" name="a_name" id="agencyName">
					<option value="">全部</option>
					<c:forEach items="${supportShareListFirst}" var="v">
						<option value="${v.name }">${v.name }</option>
					</c:forEach>
				</select> 
				<input type="button" name="button3" value="添加供应商" onclick="addTable_agency()" class="blue">　　
				<c:if test="${complaint.niuLineFlag > 0}">
					<img src="${CONFIG.res_url}images/icon/default/niurenLine.gif" style="vertical-align: middle;">
				</c:if>
			</td>
		</tr>
		<tr>
		    <th></th>
		    <td id="agencyTable">
		    
			</td>
		</tr>
		<tr>
			<th align="right">订单利润承担赔偿金额：</th>
			<td><input name="entity.orderGains" type="text" id="orderGains"	size="10" onblur="sumAllTotal()"> 元</td>
		</tr>
		<tr>
			<th align="right">员工承担赔偿金额：</th>
			<td><input type="button" name="button2" value="添加责任人" onclick="addRow_employee()" class="blue"></td>
		</tr>
		<tr>
			<th align="left">&nbsp;</th>
			<td>
				<table border="0" cellpadding="0" cellspacing="0" id="employee_table">
					<tr id="employee_row" style="display: none">
						<td><input name="entity.employeeShareList[xxx].name" type="text" size="10" onblur="checkemploy(this);"/></td>
						<td width="100px"></td>
						<td><input name="entity.employeeShareList[xxx].number" class="employeeList_number" type="text" size="5" onblur="sumEmployeeTotal()"> 元</td>
						<td align="right"><input type="button" onclick="$(this).parent().parent().remove();sumEmployeeTotal()" value="删除" /></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<th align="right">公司承担：</th>
			<td><input name="entity.special" type="text" id="special" size="10" onblur="sumAllTotal()"> 元</td>
		</tr>
		<tr>
			<th align="right">成本类型：</th>
			<td>
				<div style="float: left;">
				<input type="button" value="添加" onclick="addRow_qualityTool()" class="blue">
				<span id="quality_tool_total_price">（共计：0.0 元）</span>
				<table border="0" cellpadding="0" cellspacing="0" id="quality_tool_table">
					<tr id="quality_row" style="display: none">
						<td>
							<select name="entity.qualityToolList[xxx].toolId" onchange="setToolName(this)">
								<c:forEach items="${toolList}" var="v">
								<option value="${v.id}">${v.name}</option>
								</c:forEach>
							</select>
							<input type="hidden" name="entity.qualityToolList[xxx].toolName" value="大病质量工具">
						</td>
						<td><input type="text" style="width: 60px;" name="entity.qualityToolList[xxx].total" value="0.0" onblur="sumQualityTool()">元</td>
						<td><input type="button" onclick="$(this).parent().parent().remove();sumQualityTool();" value="删除" /></td>
					</tr>
				</table>
				</div>
			</td>
		</tr>
		<tr>
			<th align="right">退转赔：</th>
			<td>
				<input name="entity.refundToIndemnity" type="text" id="refundToIndemnity" size="10" onchange="mutex();" /> 元
				<font color="red" style="margin-left:80px">该分担方式与其他分担方式互斥</font>
			</td>
			
		</tr>
		<tr>
			<th>&nbsp;</th>
			<td>
				<input class="pd5" type="button" name="save" value="保存" id="saveInfo" onclick="doSubmit(0)">  
				<input class="pd5" type="button" name="add" value="提交" id="submitInfo" onclick="doSubmit(1)">
			</td>
		</tr>
	</table>
</form>
</div>
<font color="red">友情提醒：金额最多支持两位小数</font>

<div id="thickbox_x" style="display: none">
	<input type="button" value="选择" onclick="openDialog('赔付理据','../../config/payout_base/payoutBaseBill?name=entity.supportShareList[-1].agencyPayoutList[-1].payoutBase')"/>
</div>

<table style="display: none">
  	<tr id="x">
	    <td><textarea id="complaintInfo" rows="1" cols="20" name="entity.supportShareList[-1].agencyPayoutList[-1].complaintInfo" style="width: 173px; height: 21px;"></textarea></td>
	    <td class="payoutCl"><textarea id="payoutBase" rows="1" cols="20" name="entity.supportShareList[-1].agencyPayoutList[-1].payoutBase" style="width: 173px; height: 21px;"></textarea>
	    </td>
	    <td>
	    	<input type="text" name="entity.supportShareList[-1].agencyPayoutList[-1].payoutNum" onblur="sumSupplierTotal()" size="10"> 元
	        <span class="fcBlock" style="display:none">
	        <input class="foreignCurrencyNumber" type="text" name="entity.supportShareList[-1].agencyPayoutList[-1].foreignCurrencyNumber" size="10"> 
	        <span class="foreignCurrency">外币</span>
	        </span>
	    </td>
	    <td><input type="button" onclick="$(this).parent().parent().remove();sumSupplierTotal()" value="删除"></td>
    </tr>
</table>

<table id="n_x" style="display: none">
	<tr>
		<td colspan="2"><input name="entity.supportShareList[-1].name" id="agency_name" type="text"  size="14" onchange="checkSupplier(this)"/>　
		         <input name="entity.supportShareList[-1].code"  id="agency_code" type="text" size="3"  class="a_code"  readonly/>　　
		         <span id="nbFlagText" style="color: green;"></span>
		         <input name="entity.supportShareList[-1].nbFlag"  type="hidden" value="0">
		         <input name="entity.supportShareList[-1].foreignCurrencyType"  type="hidden"  >
		         <input name="entity.supportShareList[-1].foreignCurrencyName"  type="hidden"  >
		         </td>
		<td><input type="button" onclick="$(this).parent().parent().parent().remove();sumSupplierTotal()" value="删除"></td>
	</tr>
	<tr>
		<th>投诉详情</th>
		<th>赔付理据</th>
		<th>承担金额</th>
		<td><input type="button" name="entity.supportShareList[-1].button_info" value="添加赔付" flag="" onclick="addRow_supplier(this)"/>
		</td>
	</tr>
 	<tr>
 	    <th>备注</th>
	    <td colspan="2"><textarea id="remark" rows="3" cols="40" name="entity.supportShareList[-1].remark"></textarea></td>
    </tr>
</table>

<script type="text/javascript">
var INCREASE_INDEX = 0; //自增的下标
function addTable_agency() {
	var name = $("#agencyName option:selected").text();
	var agencyList = $("input[name^='entity.supportShareList'][name$='.name']");
	for (var i=0; i<agencyList.length; i++) {
		if (name != '' && $.trim(agencyList[i].value) == $.trim(name)) {
			alert('供应商承担不允许添加两个相同的供应商');
			return false;
		}
	}
	var index = INCREASE_INDEX;
	var agencyTable = $($('#n_x').html().replace(/entity.supportShareList\[-1\]/g,'entity.supportShareList[' + index + ']'));

	$("#agencyTable").append(agencyTable);
	$("input[name='entity.supportShareList["+index+"].button_info']").attr("flag",index);
	if(name != "全部"){
		$("input[name='entity.supportShareList["+index+"].name']").attr("value",name);
		var agency = getAgency(name);
		var nbFlag = getNbFlag(agency.agencyId);
		if (1 == nbFlag) {
			$("#nbFlagText").html("[NB供应商赔付确认]");
			$("input[name='entity.supportShareList["+index+"].nbFlag']").attr("value",1);
		}
		if(agency.agencyId != 0) {
			$("input[name='entity.supportShareList["+index+"].code']").attr("value",agency.agencyId);
			$("input[name='entity.supportShareList["+index+"].foreignCurrencyType']").attr("value",agency.foreignCurrencyType);
			$("input[name='entity.supportShareList["+index+"].foreignCurrencyName']").attr("value",agency.currencyName);
		}
	}
	setComplaintInfo($("input[name='entity.supportShareList["+index+"].button_info']"));
	INCREASE_INDEX++;
}

function setComplaintInfo(o){
	var complaintId=${complaintId};
	var json = {};
	$.ajax({
		type: "POST",
		url: "share_solution-getComplaintInfo",
		data: "complaintId=" + complaintId,
		async:false,
		success: function(data){
			console.debug(jQuery.parseJSON(data));
			console.debug(jQuery.parseJSON(data).json.length);
			json = jQuery.parseJSON(data).json;
			$.each(json,function(i,n){
				addRow_supplier(o,n);
			});
		} 
	})	
}

function addRow_supplier(o,json) {
	var flag = $(o).attr("flag");
    var size = $("input[name*='agencyPayoutList[']").length + 0;
	var index = size;
	var agencyRow = $('<tr>' + $("#x").html().replace(/agencyPayoutList\[-1\]/g,'agencyPayoutList[' + index + ']').replace(/entity.supportShareList\[-1\]/g,'entity.supportShareList[' + flag + ']') + '</tr>');
	agencyRow = agencyRow.find('.payoutCl').append($("#thickbox_x").html().replace(/agencyPayoutList\[-1\]/g,'agencyPayoutList[' + index + ']').replace(/entity.supportShareList\[-1\]/g,'entity.supportShareList[' + flag + ']')).end();
	$(o).parent().parent().after(agencyRow);
	agencyRow.find('.thickbox').click(function() {
		var t = this.title || this.name || null;
		var g = this.rel || false;
		tb_show(t,$(this).attr('alt'),g);
		this.blur();
	});
	
	if(null != json){
		console.debug(agencyRow.find('[name="entity.supportShareList[' + flag + '].agencyPayoutList[' + index + '].complaintInfo"]'));
		agencyRow.find('[name="entity.supportShareList[' + flag + '].agencyPayoutList[' + index + '].complaintInfo"]').val(json.complaintInfo);
	}
	
	var foreignCurrencyType=agencyRow.siblings().find('[name="entity.supportShareList[' + flag + '].foreignCurrencyType"]').val();
	var foreignCurrencyName=agencyRow.siblings().find('[name="entity.supportShareList[' + flag + '].foreignCurrencyName"]').val();
	if(foreignCurrencyType!=undefined && foreignCurrencyType!="" && foreignCurrencyType!='0' && foreignCurrencyType!='8' ){
		agencyRow.find('.fcBlock').css("display","block");
		agencyRow.find('.foreignCurrency').html(foreignCurrencyName);
	}
}
</script>
</BODY>
