<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<link href="${CONFIG.res_url}css/thickbox.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${CONFIG.res_url}script/jquery/plugin/auto_complete/jquery.autocomplete.css"/>

<script type="text/javascript" src="${CONFIG.res_url}script/thickbox-compressed.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}script/DatePickerNew/WdatePicker.js" ></script>  
<script type="text/javascript" src="${CONFIG.res_url}script/jquery/plugin/auto_complete/jquery.autocomplete.js"></script>
<style type="text/css">
<!-- tr变黄色 -->
.trbg td{background:#ff9;}

.listtable tr.yellowbg td{background:#FFFF99}
</style>

<script>
$(function(){
	/*移除thickbox默认样式*/
	$('.thickbox').click(function(){
		$('#TB_closeAjaxWindow').html("<a href='javascript:void(0)' id='TB_closeWindowButton' title='Close'>×</a>");
		$('#TB_closeWindowButton').live('click',function(){
			tb_remove();
		});
	});
	
});

$(function(){
	change_menu('${request.type}');
	//change_menu(2);
	$("#deal_form").submit(function(){
	   //alert($("#assignFlag").val());
	   //return check_select_people();
	}); 
})
//标签转换时更新标签样式
function change_menu(value) {
	$("li[id^='menu_']").each(function(){
		$(this).removeClass("menu_on");
	});
	$('#menu_'+value).addClass("menu_on");	
}
//列表标签表单提交
function searchTable(menuId){ 
	//$('#complaint_form').attr("action", "${manageUrl}");
	$('#complaint_form').attr("action", "complaint");
	$('#c_type').attr("value",menuId);
	$('#complaint_form').submit();
}
//校验已选择的分配人
function check_select_people(){
	var on_select_deal = $("#select_delPeople").val();
	var on_select_join = $("#deal_joinPeople").val();
	var on_select_complete_id = 0 ;
	$("input[name='ids']:checked").each(function(){
		on_select_complete_id = 1;
	})
	if (!${isCtOfficer}){
		on_select_deal = "0";
	}
	if (on_select_deal == "0" && on_select_join == "0") {
		alert("请选择分配人");
		return false;
	} else if (on_select_complete_id == 0) {
		alert("请选择投诉单号");
		return false;
	} else {
		return true;
	}
}


function onSearchClicked() {
	//$('#complaint_form').attr("action", "${manageUrl}");
	$('#complaint_form').attr("action", "complaint");
	$('#complaint_form').submit();
}

function onSearchExport() { 
	//var baseUrl = "${manageUrl}"; 
	//$('#complaint_form').attr("action", baseUrl + "-exports");
	$('#complaint_form').attr("action", "complaint-exports");
	$('#complaint_form').submit();
}

function dealjoinPeople(){
	$("#select_delPeople").attr("value",0);
	$("#select2_delPeople").attr("value",0);
	$("#assignFlag").val("dealjoin");
	if(check_select_people()){
		$("#deal_form").submit(); 
	} 
}

function selectdelPeople(type){
	var delPeople = "";
	if (type == 0){//分配
		delPeople = $("#select_delPeople").val();
		$("#assignFlag").val("assign");
		$("#select2_delPeople").attr("value",0);
	}else if (type == 1) {//重新分配
		delPeople = $("#select2_delPeople").val();
		$("#assignFlag").val("assignNew");
		$("#select_delPeople").attr("value",0);
	}
	$("#deal_joinPeople").attr("value",0);
	$("#select_delPeople").val(delPeople);
	if(check_select_people()){
		$("#deal_form").submit(); 
	}
}


//修改订单处理状态
function changeOrderState(select, orderState, complaintId){
	if(select.value != orderState) {
		if(confirm("确认修改订单状态为" + select.value + "?")){
			$.ajax({
				type:"post",
				url:"complaint-changeOrderState?orderState=" + select.value + "&id=" + complaintId,
				//dataType:"json",
				success:function(data){
					alert( data ); 
					$('#orderState_' + complaintId).hide();
					$('#oldOrderState_' + complaintId).html(select.value);
				} 
			});
		}
	}
	
}

//升级售后订单退回
function sendBack(id, reason, tab){
	var reason = $('#back_reason_' + id).val();
	reason = reason.replace(/\s+/g,""); 
	if (reason == '') {
		alert("请填写退回原因");
		return false;
	}
	if (reason.length > 500) {
		alert("退回原因不能超过500字");
		return false;
	}
	//location.href="complaint-sendBack?id=" + id + "&reason=" + reason + "&c_type=" + tab;
	$.ajax({
		type: "POST",
		url: "complaint-sendBack",
		data: "id=" + id + "&reason=" + reason + "&c_type=" + tab,
		success: function(data){
			alert( data ); 
			location.href="complaint-execute?c_type=" + tab;
		} 
	})
}


//变更客服经理
function changeCustomerManager(){
	var customLeader = $('#select_customer').val();
	
	var complaintId = new Array() ;
	var count = 0;
	$("input[name='ids']:checked").each(function(){
		complaintId.push($(this).val());
		count ++; 
	})
	
	if (count == 0) {
		alert("请选择投诉单号");
		return false;
	}
	if (customLeader == 0 || customLeader == '') {
		alert("请选择客服经理");
		return false;
	}
	
	var name = $('#h_select_customer_name').val();
	$('#select_customer').val(name);
	if (confirm("确定要将投诉单号" + complaintId + "的客服经理修改为" + name + "吗？")) {
		$('#deal_form').attr("action", "complaint-changeCustomLeader?id=" + complaintId.join());
		$("#deal_form").submit();
	}
}

function diffDate(id, curDate, addDate){
	var curDates = curDate.split('-');
	var addDates = addDate.split('-');
	var curDate1 = new Date(curDates[0], curDates[1], curDates[2]);
	var addDate1 = new Date(addDates[0], addDates[1], addDates[2]);
	var diff = (curDate1.getTime()-addDate1.getTime())/1000/24/3600;
	if (diff > 3) {
		$('#td_' + id).css('color', 'red');
	//	$('#td_' + id).html('<font color="red">'+id+'</font>');
	//	var html = '<a href="complaint-toBill?id='+id+'" target="_blank" style="color: red;">'+id+'</a>';
	//	$('#td_' + id).html("111");
	}
}

/**
 * 自动填充
 */
function textAutocomplete(){
	var selectDelPeopleArray = new Array();		//处理人
	<c:forEach items="${sameGroupUsers}" var="userItem">
		selectDelPeopleArray.push({'id' : '${userItem.id}', 'realName' : '${userItem.realName}', 'userName' : '${userItem.userName}', 'name' : '${userItem.realName}${userItem.userName}'});
	</c:forEach>
	
	var selectCustomerArray = new Array();		//变更客服经理
	<c:forEach items="${customLeader}" var="userItem">
		selectCustomerArray.push({'id' : '${userItem.id}', 'realName' : '${userItem.realName}', 'userName' : '${userItem.userName}', 'name' : '${userItem.realName}${userItem.userName}'});
	</c:forEach>
	
	var customLeaderSearchArray = new Array();		//客服经理(查询条件)
	<c:forEach items="${customLeaderSearch}" var="userItem">
		customLeaderSearchArray.push({'id' : '${userItem.id}', 'realName' : '${userItem.realName}', 'userName' : '${userItem.userName}', 'name' : '${userItem.realName}${userItem.userName}'});
	</c:forEach>
	
	
		
	//分配处理人
	$('#select_delPeople_name').autocomplete(selectDelPeopleArray, {
		max : 500, //列表里的条目数 
		minChars : 0, //自动完成激活之前填入的最小字符 
		width : 200, //提示的宽度，溢出隐藏 
		scrollHeight : 300, //提示的高度，溢出显示滚动条 
		matchContains : true, //包含匹配，就是data参数里的数据，是否只要包含文本框里的数据就显示 
		autoFill : false, //自动填充 
		mustMatch : false,
		dataType : 'json',
		parse: function(data) {
			var rows = [];
			var list = data;
	        for(var i=0; i<list.length; i++){
	        	rows[rows.length] = {
	            data: list[i],
	            value: list[i].name,
	            result: list[i].name
	            };
	        }
	        return rows;
	    },   
		formatItem : function(row, i, max) {
			return row.realName;
		},
		formatMatch : function(row, i, max) {
			return row.name;
		},
		formatResult : function(row) {
			return row.realName;
		}

	}).result(function(event, row, formatted) {
		$('#select_delPeople').val(row.id);
		$('#select_delPeople_button').val('分配给' + row.realName);
		
	});

	//交接人
	$('#deal_joinPeople_name').autocomplete(selectDelPeopleArray, {
		max : 500, //列表里的条目数 
		minChars : 0, //自动完成激活之前填入的最小字符 
		width : 200, //提示的宽度，溢出隐藏 
		scrollHeight : 300, //提示的高度，溢出显示滚动条 
		matchContains : true, //包含匹配，就是data参数里的数据，是否只要包含文本框里的数据就显示 
		autoFill : false, //自动填充 
		mustMatch : false,
		dataType : 'json',
		parse: function(data) {
			var rows = [];
			var list = data;
	        for(var i=0; i<list.length; i++){
	        	rows[rows.length] = {
	            data: list[i],
	            value: list[i].name,
	            result: list[i].name
	            };
	        }
	        return rows;
	    },   
		formatItem : function(row, i, max) {
			return row.realName;
		},
		formatMatch : function(row, i, max) {
			return row.name;
		},
		formatResult : function(row) {
			return row.realName;
		}

	}).result(function(event, row, formatted) {
		$('#deal_joinPeople').val(row.id);
		$('#deal_joinPeople_button').val('工作交接给' + row.realName);
		
	});
	
	//重新分配
	$('#select2_delPeople_name').autocomplete(selectDelPeopleArray, {
		max : 500, //列表里的条目数 
		minChars : 0, //自动完成激活之前填入的最小字符 
		width : 200, //提示的宽度，溢出隐藏 
		scrollHeight : 300, //提示的高度，溢出显示滚动条 
		matchContains : true, //包含匹配，就是data参数里的数据，是否只要包含文本框里的数据就显示 
		autoFill : false, //自动填充 
		mustMatch : false,
		dataType : 'json',
		parse: function(data) {
			var rows = [];
			var list = data;
	        for(var i=0; i<list.length; i++){
	        	rows[rows.length] = {
	            data: list[i],
	            value: list[i].name,
	            result: list[i].name
	            };
	        }
	        return rows;
	    },   
		formatItem : function(row, i, max) {
			return row.realName;
		},
		formatMatch : function(row, i, max) {
			return row.name;
		},
		formatResult : function(row) {
			return row.realName;
		}

	}).result(function(event, row, formatted) {
		$('#select2_delPeople').val(row.id);
		$('#select2_delPeople_button').val('重新分配给' + row.realName);
		
	});
	
	//变更客服经理
	$('#select_customer').autocomplete(selectCustomerArray, {
		max : 500, //列表里的条目数 
		minChars : 0, //自动完成激活之前填入的最小字符 
		width : 200, //提示的宽度，溢出隐藏 
		scrollHeight : 300, //提示的高度，溢出显示滚动条 
		matchContains : true, //包含匹配，就是data参数里的数据，是否只要包含文本框里的数据就显示 
		autoFill : false, //自动填充 
		mustMatch : false,
		dataType : 'json',
		parse: function(data) {
			var rows = [];
			var list = data;
	        for(var i=0; i<list.length; i++){
	        	rows[rows.length] = {
	            data: list[i],
	            value: list[i].name,
	            result: list[i].name
	            };
	        }
	        return rows;
	    },   
		formatItem : function(row, i, max) {
			return row.realName;
		},
		formatMatch : function(row, i, max) {
			return row.name;
		},
		formatResult : function(row) {
			return row.realName;
		}

	}).result(function(event, row, formatted) {
		$('#h_select_customer_name').val(row.realName);
		$('#select_customer_button').val('变更客服经理为' + row.realName);
		
	});
	
	//处理人（查询条件）
	$('#deal_name').autocomplete(selectDelPeopleArray, {
		max : 500, //列表里的条目数 
		minChars : 0, //自动完成激活之前填入的最小字符 
		width : 200, //提示的宽度，溢出隐藏 
		scrollHeight : 300, //提示的高度，溢出显示滚动条 
		matchContains : true, //包含匹配，就是data参数里的数据，是否只要包含文本框里的数据就显示 
		autoFill : false, //自动填充 
		mustMatch : false,
		dataType : 'json',
		parse: function(data) {
			var rows = [];
			var list = data;
	        for(var i=0; i<list.length; i++){
	        	rows[rows.length] = {
	            data: list[i],
	            value: list[i].name,
	            result: list[i].name
	            };
	        }
	        return rows;
	    },   
		formatItem : function(row, i, max) {
			return row.realName;
		},
		formatMatch : function(row, i, max) {
			return row.name;
		},
		formatResult : function(row) {
			return row.realName;
		}

	}).result(function(event, row, formatted) {
		$('#deal').val(row.id);
	});
	
	//客服经理(查询条件)
	$('#manager').autocomplete(customLeaderSearchArray, {
		max : 500, //列表里的条目数 
		minChars : 0, //自动完成激活之前填入的最小字符 
		width : 200, //提示的宽度，溢出隐藏 
		scrollHeight : 300, //提示的高度，溢出显示滚动条 
		matchContains : true, //包含匹配，就是data参数里的数据，是否只要包含文本框里的数据就显示 
		autoFill : false, //自动填充 
		mustMatch : false,
		dataType : 'json',
		parse: function(data) {
			var rows = [];
			var list = data;
	        for(var i=0; i<list.length; i++){
	        	rows[rows.length] = {
	            data: list[i],
	            value: list[i].name,
	            result: list[i].name
	            };
	        }
	        return rows;
	    },   
		formatItem : function(row, i, max) {
			return row.realName;
		},
		formatMatch : function(row, i, max) {
			return row.name;
		},
		formatResult : function(row) {
			return row.realName;
		}

	}).result(function(event, row, formatted) {
	});
}

/**
 * 查询前检验处理人
 */
function beforeFormSubmit(){
	if($('#deal_name').val() == ''){
		$('#deal').val(0);
	}
}
</script>
</HEAD>
<BODY>
<div class="top_crumbs">您当前所在的位置：<a href="#">投诉质检管理</a>&gt;&gt;<span class="top_crumbs_txt">投诉处理列表</span></div>
	<form name="form" id="complaint_form" method="post" onSubmit="return beforeFormSubmit();"
		enctype="multipart/form-data" action="complaint-execute">
		<input type="hidden" name="c_type" id="c_type" value="menu_${type }">
		<div id="pici_tab" class="clear">
			<ul>
				<c:if test="${isCtOfficer == true}">
				<li class="menu_on" onclick="searchTable(this.id)" id="menu_1">
					<s class="rc-l"></s><s class="rc-r"></s><a href="javascript:void(0)">投诉待指定</a>
				</li>
				</c:if>
				<li onclick="searchTable(this.id)" id="menu_2">
					<s class="rc-l"></s><s class="rc-r"></s><a href="javascript:void(0)">投诉处理中</a>
				</li>
				<li onclick="searchTable(this.id)" id="menu_3">
					<s class="rc-l"></s><s class="rc-r"></s><a href="javascript:void(0)">投诉已待结</a>
				</li>
				<li onclick="searchTable(this.id);" id="menu_4">
					<s class="rc-l"></s><s class="rc-r"></s><a href="javascript:void(0)">投诉已完成</a>
				</li>
				<li onclick="searchTable(this.id);" id="menu_5">
					<s class="rc-l"></s><s class="rc-r"></s><a href="javascript:void(0)">订单号相同的投诉单</a>
				</li>
			</ul>
		</div>

		<div class="pici_search pd5">

			<div class="pici_search pd5 mb10">
			<label class="mr10">订&nbsp;&nbsp;单&nbsp;&nbsp;号： <input type="text" size="10" name="search.orderId" value="${search.orderId}" /> </label>
			<label class="mr10">投诉单号： <input type="text" size="10" name="search.id" value="${search.id}" /> </label>
				投诉来源：
				<select class="mr10" name="search.comeFrom">
					<option value="">全部</option>
					<option value="网站" <c:if test="${'网站'.equals(search.comeFrom)}">selected</c:if>>网站</option>
					<option value="门市" <c:if test="${'门市'.equals(search.comeFrom)}">selected</c:if>>门市</option>
					<option value="当地质检" <c:if test="${'当地质检'.equals(search.comeFrom)}">selected</c:if>>当地质检</option>
					<option value="来电投诉" <c:if test="${'来电投诉'.equals(search.comeFrom)}">selected</c:if>>来电投诉</option>
					<option value="CS邮箱 " <c:if test="${'CS邮箱 '.equals(search.comeFrom)}">selected</c:if>>CS邮箱</option>
					<option value="回访" <c:if test="${'回访'.equals(search.comeFrom)}">selected</c:if>>回访</option>
					<option value="点评" <c:if test="${'点评'.equals(search.comeFrom)}">selected</c:if>>点评</option>
					<option value="旅游局" <c:if test="${'旅游局'.equals(search.comeFrom)}">selected</c:if>>旅游局</option>
					<option value="APP" <c:if test="${'APP'.equals(search.comeFrom)}">selected</c:if>>APP</option>
					<option value="其他" <c:if test="${'其他'.equals(search.comeFrom)}">selected</c:if>>其他</option>
				</select> 
				<label>出发城市: <!-- <input type="text" name="search.startCity" value="${search.startCity}" /> -->
				
				<select class="mr10" name="search.startCity" id="startCity">
						<option value="">请选择</option>
						<c:forEach items="${listMap}" var="sc">
						<option value="${sc.name}" <c:if test="${search.startCity==sc.name}">selected</c:if>  >${sc.name}</option>
						</c:forEach>
				</select> 
				
				</label>
				 
				<select class="mr10" name="search.productLeader">
						<option value="">产品经理</option>
						<c:forEach items="${productManagers}" var="pm">
						<option value="${pm.id}" <c:if test="${search.productLeader==pm.id}">selected</c:if>  >${pm.realName}</option>
						</c:forEach>
				</select>
				<c:if test="${isSQ == 1}">
				投诉状态：
				<select class="mr10" name="search.state">
						<option value="1" <c:if test="${search.state==1}">selected</c:if> >正常</option>
						<option value="2" <c:if test="${search.state==2}">selected</c:if> >已撤销</option>
						<!-- option value="0" <c:if test="${search.state==0}">selected</c:if> >全部</option-->
				</select>
				</c:if>
				订单状态：
				<select class="mr10" name="search.orderStateTemp">
					<option value="" >请选择</option>
						<option value="0" <c:if test="${search.orderStateTemp==0}">selected</c:if> >出游前</option>
						<option value="1" <c:if test="${search.orderStateTemp==1}">selected</c:if> >出游中</option>
						<option value="2" <c:if test="${search.orderStateTemp==2}">selected</c:if> >出游后</option>
				</select>
				处理岗：
				<select class="mr10" name="search.dealDepart">
					<option value="" >请选择</option>
					<option value="售前组" <c:if test="${search.dealDepart=='售前组'}">selected</c:if> >售前组</option>
					<option value="售后组" <c:if test="${search.dealDepart=='售后组'}">selected</c:if> >售后组</option>
					<option value="资深组" <c:if test="${search.dealDepart=='资深组'}">selected</c:if> >资深组</option>
				</select>
				
				<!-- label> 供应商：<input type="text" size="20" name="search.agencyName" value="${search.agencyName}"/> </label-->
				<br /> 

				<label> 投诉时间： <input type="text" size="20" name="search.startBuildDate" id="start_build_date"	value="${search.startBuildDate }" onclick="WdatePicker()" readOnly="readonly"/> </label> 
				<label class="mr10">至 <input type="text" size="20" name="search.endBuildDate" id="end_build_date"value="${search.endBuildDate }" onclick="WdatePicker()" readOnly="readonly"/> </label> 
				<label> 完成时间： <input type="text" size="20" name="search.startFinishDate" id="start_finish_date" value="${search.startFinishDate }"onclick="WdatePicker()" readOnly="readonly"/> </label> 
				<label class="mr10">至 <input type="text" size="20" name="search.endFinishDate" id="end_finish_date" value="${search.endFinishDate }" onclick="WdatePicker()" readOnly="readonly"/> </label> 
				<label class="mr10">
					处理人： 
					<input	type="text" size="10" name="search.dealNameText" id="deal_name" value="${search.dealNameText}"/>
					<input type="hidden" name="search.deal" id="deal" value="${search.deal}"/>
					
					
				</label>  
				<label class="mr10">
					客服经理：
					<input type="text" name="search.customerLeader" id="manager" value="${search.customerLeader}" />
				</label>
				<select class="mr10" name="search.level">
					<option value="">投诉等级</option>
					<option value="3" <c:if test="${search.level==3}">selected</c:if> >3级</option>
					<option value="2" <c:if test="${search.level==2}">selected</c:if> >2级</option>
					<option value="1" <c:if test="${search.level==1}">selected</c:if> >1级</option>
				</select>
				
				<input type="button" value="查询" class="blue" name="" onclick="onSearchClicked();"/> 
				<!-- <input type="button" value="导出" class="blue" name="" onclick="onSearchExport();"/> -->
			</div>
		</div>
	</form>
	<!-- 分配负责人或者交接工作表单 -->
<form method="post" enctype="multipart/form-data"  action="complaint-dealPeople" id="deal_form" name="deal_form">
  	<input type="hidden" name="c_type" id="c_type" value="menu_${type }">
  	<input type="hidden" name="assignFlag" id="assignFlag" value="">
 	<!--1 待处理-->
	<c:if test="${isCtOfficer == true}"> <!-- 主管才有分配权限 -->
		<c:if test="${type==1}">
			<div class="notice mb10">
			<p class="pd5">	
				<input type="text" size="10" id="select_delPeople_name" name="select_delPeople_name" value="双击显示所有" style="color: rgb(153, 153, 153);" onfocus="if(this.value=='双击显示所有'){this.value='';this.style.color='#000';}" onblur="if(this.value==''){this.value='双击显示所有';this.style.color='#999';}"/>
				<input type="hidden" id="select_delPeople" name="select_delPeople" value="0"/>
				<input id="select_delPeople_button" class="mr20" type="button" name="button" value="分配处理人" onclick="selectdelPeople(0)"/>
				
				<c:if test="${isSQ == 1}">
					<input type="text" id="select_customer" name="select_customer" value=""/>
					<input type="hidden" id="h_select_customer_name" value="0"/>
					<input id="select_customer_button" class="mr20" type="button" name="button2" value="变更客服经理" onclick="changeCustomerManager()"/>
				</c:if>
				
      			<span class="cred">超期标准：从投诉单发起时刻起，国内3天，出境5天，按工作日计算</span>
      			<span style="float:right;font-size:20px;">请分配处理人前确认记录行处理岗为自己所在组织，不符时请仅查看记录（不要操作）</span>
      			</p>
			</p>
			</div>
		</c:if>
	</c:if>
  
  <c:if test="${type==2}">
  	<div class="notice mb10">
     <p class="pd5">
		<input type="text" size="10" id="deal_joinPeople_name" name="deal_joinPeople_name" value="双击显示所有" style="color: rgb(153, 153, 153);" onfocus="if(this.value=='双击显示所有'){this.value='';this.style.color='#000';}" onblur="if(this.value==''){this.value='双击显示所有';this.style.color='#999';}"/>
		<input type="hidden" id="deal_joinPeople" name="deal_joinPeople" value="0"/>
      	<input id="deal_joinPeople_button" class="mr20" type="button" name="button1" value="工作交接" onclick="dealjoinPeople()" />
      
      <c:if test="${isCtOfficer == true}"> <!-- 主管才有分配权限 -->
      	<input type="text" size="10" id="select2_delPeople_name" name="select2_delPeople_name" value="双击显示所有" style="color: rgb(153, 153, 153);" onfocus="if(this.value=='双击显示所有'){this.value='';this.style.color='#000';}" onblur="if(this.value==''){this.value='双击显示所有';this.style.color='#999';}"/>
		<input type="hidden" id="select2_delPeople" name="select2_delPeople" value="0"/>
		<input type="hidden" id="select_delPeople" name="select_delPeople" value="0"/>		
		<input id="select2_delPeople_button" class="mr20" type="button" name="button" value="重新分配" onclick="selectdelPeople(1)"/>
	  </c:if>
	  
	  <c:if test="${isSQ == 1}">
		<input type="text" id="select_customer" name="select_customer" value=""/>
		<input type="hidden" id="h_select_customer_name" value="0"/>
		<input id="select_customer_button" class="mr20" type="button" name="button2" value="变更客服经理" onclick="changeCustomerManager()"/>
	  </c:if>
	  
      <span class="cred">超期标准：从投诉单发起时刻起，国内3天，出境5天，按工作日计算</span>
      </p>
  </div>
  </c:if>
  
  <c:if test="${type==3}">
  	<div class="notice mb10">
     <p class="pd5">
     	<c:if test="${isSQ == 1}">
			<input type="text" id="select_customer" name="select_customer" value=""/>
			<input type="hidden" id="h_select_customer_name" value="0"/>
			<input id="select_customer_button" class="mr20" type="button" name="button2" value="变更客服经理" onclick="changeCustomerManager()"/>
	  	</c:if>
      
      <span class="cred">超期标准：从投诉单发起时刻起，国内3天，出境5天，按工作日计算</span>
      </p>
  </div>
  </c:if>
  
  <c:if test="${type==4}">
  	<div class="notice mb10">
     <p class="pd5">
      <span class="cred">超期标准：从投诉单发起时刻起，国内3天，出境5天，按工作日计算</span>
      </p>
  	</div>
  </c:if>
  
  


<table class="listtable" width="98%">
	<thead>
		
		<th>投诉号</th>
		<th>关联订单id</th>
		<th>客人姓名</th>
		<th>出发地/线路</th>
		<th>投诉时间</th>
		<th>客服经理</th>
		<th>产品经理</th>
		<th>订单状态</th>
		<th>处理岗</th>
		<th>投诉来源</th>
		<th>投诉等级</th>
		<th>处理人</th>
		<th>投诉处理状态</th>  
	</thead>
	<tbody>
		<c:forEach items="${dataList}" var="v"  varStatus="st"> 
		<tr align="center" <c:if test="${v.state==6}">class="yellowbg"</c:if> class="trbg" >
			
			<fmt:formatDate var="curDate1" value="${curDate}" pattern="yyyy-MM-dd" />
			<fmt:formatDate var="addDate1" value="${v.buildDate}" pattern="yyyy-MM-dd" />
			<td><a href="complaint-toBill?id=${v.id}&repeat=1" target="_blank" id="td_${v.id}">${v.id}</a></td> 
			<c:if test="${v.state < 4}"><script>diffDate("${v.id}","${curDate1}","${addDate1}");</script></c:if>
			<td>${v.orderId}</td> 
			<td>${v.guestName}</td> 
			<td>${v.startCity}-${v.route}</td> 
			<td><fmt:formatDate value="${v.buildDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td> 
			<td>${v.customerLeader}</td> 
			<td>${v.productLeader}</td> 
			<td>
			<span id="oldOrderState_${v.id}">${v.orderState}</span>
			<c:if test="${type==1 && isChangeOrderState==1 && v.orderState.equals(v.dealDepart) && ''.equals(v.dealName)}">
			<span style="display:none" id="orderState_${v.id}">
				<select class="mr10" name="orderState" onchange="changeOrderState(this, '${v.orderState}', ${v.id})">
					<option value="出游前" <c:if test="${'出游前'.equals(v.orderState)}">selected</c:if> >出游前</option>
					<option value="出游中" <c:if test="${'出游中'.equals(v.orderState)}">selected</c:if> >出游中</option>
					<option value="出游后" <c:if test="${'出游后'.equals(v.orderState)}">selected</c:if> >出游后</option>
				</select>
			</span>
			</c:if>
			</td> 
			<td><span <c:if test="${!v.orderState.equals(v.dealDepart) }">style="color:red;"</c:if>>${v.dealDepart}</span>
			</td>
			<td>${v.comeFrom}</td> 
			<td>${v.level}</td>
			<td>${v.dealName}</td>
			<td><c:if test="${v.state==1}">投诉待处理</c:if>
				<c:if test="${v.state==2}">投诉处理中</c:if>
				<c:if test="${v.state==3}">投诉已待结</c:if>
				<c:if test="${v.state==4}">投诉已完成</c:if>
				<c:if test="${v.state==5}">投诉待指定（升级售后）</c:if>
				<c:if test="${v.state==6}">投诉待指定（提交售后填写分担方案）</c:if>
				<c:if test="${v.state==9}">已撤销</c:if>
			</td> 
			
		</tr>
		</c:forEach>
	</tbody>
</table>
</form>



<script type="text/javascript">
$(document).ready(function(){
	var startCity = "${search.startCity}";
	if(startCity != ''){
		$("#startCity").attr("value", startCity);
	};
	
	$(document).keydown(function(event) {  //屏蔽回车提交
		  if (event.keyCode == 13) {
		    $('form').each(function() {
		      event.preventDefault();
		    });
		  }
		}); 
	
	textAutocomplete();
});
</script>

<%@include file="/WEB-INF/html/pager.jsp" %>
<%@include file="/WEB-INF/html/foot.jsp" %>
