<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %> 
<%@include file="/WEB-INF/html/head.jsp" %>
<link rel="stylesheet" type="text/css" href="${CONFIG.res_url}script/jquery/plugin/auto_complete/jquery.autocomplete.css"/>
<script type="text/javascript" src="${CONFIG.res_url}script/jquery/plugin/auto_complete/jquery.autocomplete.js"></script>

<style type="text/css">
<!-- tr变黄色 -->
.trbg td{background:#ff9;}

.listtable tr.yellowbg td{background:#FFFF99}

.listtable tr td.orange{background:orange;}
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
	$("li").each(function(){
		$(this).removeClass("menu_on");
	});
	$('waitFirstCall').addClass("menu_on");	
}
//列表标签表单提交
function searchTable(menuId){ 
	//$('#complaint_form').attr("action", "${manageUrl}");
	$('#complaint_form').attr("action", "complaint");
	$('#c_type').attr("value",menuId);
	$('#normal').addClass("menu_on");
	searchTab("waitFirstCall");
}

function searchTab(flag){ 
	$('#complaint_form').attr("action", "complaint");
	$('#tab_flag').val(flag);
	$('#searchTab li').removeClass("menu_on");
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
	var flag = $("#assignFlag").val();
	if(flag=="assign" || flag=="assignNew"){
		if (on_select_deal == "0") {
			alert("请选择分配人");
			return false;
		} 
	}else if(flag=="dealjoin"){
		if (on_select_join == "0") {
			alert("请选择分配人");
			return false;
		} 
	}
	if (on_select_complete_id == 0) {
		alert("请选择投诉单号");
		return false;
	} 
	return true;
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

function onSearchExportAll() { 
	//var baseUrl = "${manageUrl}"; 
	//$('#complaint_form').attr("action", baseUrl + "-exports");
	$('#complaint_form').attr("action", "complaint-exportsAll");
	$('#complaint_form').submit();
}

function dealjoinPeople(){
	$("#select_delPeople").attr("value",0);
	$("#select2_delPeople").attr("value",0);
	$("#assignFlag").val("dealjoin");
	if(check_select_people()){
		var ids="";
		var r=document.getElementsByName("ids");
	    for(var i=0;i<r.length;i++){
	         if(r[i].checked){
	        	 if($('#'+r[i].value+'_state').html().trim()!='投诉处理中'){
		    		 alert('选择的记录中含有非处理中，无法完成工作交接');
		    		 return false;
		    	 };
	        	 if(ids==""){
		        	 ids += ""+r[i].value;
	        	 }else{
	        		 ids += ","+r[i].value;
	        	 }
	         }
	       }
		var param = "assignFlag="+$("#assignFlag").val()+"&deal_joinPeople="+$("#deal_joinPeople").val()+"&ids="+ids;
		$.ajax({
			type: "POST",
			async:false,
			url: "complaint-dealPeople",
			data: param,
			success: function(data){
				onSearchClicked();
		     }
		   });
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
	
	var ids="";
	var r=document.getElementsByName("ids");
    for(var i=0;i<r.length;i++){
         if(r[i].checked){
        	 if(ids==""){
	        	 ids += ""+r[i].value;
        	 }else{
        		 ids += ","+r[i].value;
        	 }
         }
       }
	
	if(check_select_people()){
		var param = "assignFlag="+$("#assignFlag").val()+"&select_delPeople="+$("#select_delPeople").val()+"&deal_joinPeople="+$("#select_delPeople").val()+"&ids="+ids;
		$.ajax({
			type: "POST",
			async:false,
			url: "complaint-dealPeople",
			data: param,
			success: function(data){
				onSearchClicked();
		     }
		   });
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
		var param = "select_customer="+name+"&id="+complaintId.join();
		$.ajax({
			type: "POST",
			async:false,
			url: "complaint-changeCustomLeader",
			data: param,
			success: function(data){
				onSearchClicked();
		     }
		   });
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

function startLoadComplaint(startTime, route, id){
	var oldValue=$('#show_'+id).html();
	if(oldValue == "" || oldValue == null){
		$.ajax({
			"type":"GET",
			"url":"complaint-querySameGroupNos",
			data: "routeId="  + route + "&startTime=" + startTime,
			success: function(data) {				
				var json_data = eval('('+data+')');
				$('#show_'+id).html("投诉号:" + id +"联动订单号：<br>"+ json_data[0].orderNosHasNoCt + "<br><font color='red'>" + json_data[0].orderNosHasCt + "</font><br>(双击此弹出框可关闭！)");
				$('#show_'+id).show();
			},
			error:function() {
				alert("error");
			}
		});
	}else{
		$('#show_'+id).show();
	}
}

function hideShowNOs(id){
	$('#show_'+id).hide();
}

function cancelComplaint(id){
	$.ajax({
		"type":"GET",
		"url":"complaint-cancel",
		data: "id="  + id + "&c_type=menu_" + ${type},
		success: function(data) {				
			window.location.href="complaint?c_type=menu_" + ${type}+"&p="+${p};
		},
		error:function() {
			alert("error");
		}
	});
}

function onResetClicked(){
	$("input[name='search.orderId']").val("");
	$("input[name='search.id']").val("");
	$("input[name='search.routeId']").val("");
	$("select[name='search.comeFrom']").val("");
	$("select[name='search.startCity']").val("");
	$("select[name='search.productLeader']").val("");
	$("select[name='search.state']").val("");
	$("select[name='search.orderStateTemp']").val("");
	$("select[name='search.level']").val("");
	$("select[name='search.notInTimeDeal']").val("");
	$("input[name='search.notInTimeDealDate']").val("");
	$("input[name='search.startTimeBegin']").val("");
	$("input[name='search.startTimeEnd']").val("");
	$("input[name='search.customerLeader']").val("");
	$("input[name='search.agencyName']").val("");
	$("input[name='search.startBuildDate']").val("");
	$("input[name='search.endBuildDate']").val("");
	$("input[name='search.startFinishDate']").val("");
	$("input[name='search.endFinishDate']").val("");
	$("input[name='search.dealNameText']").val("");
	$("select[name='search.dealDepart']").val("");
}
</script>
</HEAD>
<BODY>
<div class="top_crumbs">您当前所在的位置：<a href="#">投诉查询</a>&gt;&gt;<span class="top_crumbs_txt">投诉查询列表</span></div>
	<form name="form" id="complaint_form" method="post" onSubmit="return beforeFormSubmit();"
		enctype="multipart/form-data" action="complaint-execute">
		<input type="hidden" name="c_type" id="c_type" value="menu_${type }">
		<input type="hidden" name="act" id="c_type" value="query">
		<input type="hidden" name="search.tab_flag" id="tab_flag" value="${stab_flag}">
		<div id="pici_tab" class="clear">
			 <div id="pici_tab" class="clear">
			<ul id="searchTab">
				<li onclick="searchTab(this.id)" id="waitFirstCall">
					<s class="rc-l"></s><s class="rc-r"></s><a href="javascript:void(0)">待首呼</a>
				</li>
				<li onclick="searchTab(this.id)" id="waitFollowUp">
					<s class="rc-l"></s><s class="rc-r"></s><a href="javascript:void(0)">提醒未跟进</a>
				</li>
				<li onclick="searchTab(this.id)" id="timeOutNeedFollowUp">
					<s class="rc-l"></s><s class="rc-r"></s><a href="javascript:void(0)">超时处理中</a>
				</li>
				<li onclick="searchTab(this.id)" id="timeOutNeedEnd">
					<s class="rc-l"></s><s class="rc-r"></s><a href="javascript:void(0)">15日未完成</a>
				</li>
				<li onclick="searchTab(this.id)" id="waitHandOver">
					<s class="rc-l"></s><s class="rc-r"></s><a href="javascript:void(0)">交接单</a>
				</li>
				<li onclick="searchTab(this.id)" id="repeatedComplaints">
					<s class="rc-l"></s><s class="rc-r"></s><a href="javascript:void(0)">重复投诉单</a>
				</li>
				<li onclick="searchTab(this.id)" id="addComplaintMatter">
					<s class="rc-l"></s><s class="rc-r"></s><a href="javascript:void(0)">新增投诉事宜</a>
				</li>
			</ul>
		</div>
		</div>

		<div class="pici_search pd5">

			<div class="pici_search pd5 mb10">
			<label class="mr10">订&nbsp;&nbsp;单&nbsp;&nbsp;号： <input type="text" size="10" name="search.orderId" value="${search.orderId}" /> </label>
			<label class="mr10">投诉单号： <input type="text" size="10" name="search.id" value="${search.id}" /> </label>
			<label class="mr10">线&nbsp;&nbsp;路&nbsp;&nbsp;号： <input type="text" size="10" name="search.routeId" value="${search.routeId}" /> </label>
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
					<option value="微博" <c:if test="${'微博'.equals(search.comeFrom)}">selected</c:if>>微博</option>
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
						<option value="0" <c:if test="${search.orderStateTemp!=\"\" && search.orderStateTemp==0 }">selected</c:if> >出游前</option>
						<option value="1" <c:if test="${search.orderStateTemp==1}">selected</c:if> >出游中</option>
						<option value="2" <c:if test="${search.orderStateTemp==2}">selected</c:if> >出游后</option>
				</select>
				
				<select class="mr10" name="search.level">
					<option value="">投诉等级</option>
					<option value="3" <c:if test="${search.level==3}">selected</c:if> >3级</option>
					<option value="2" <c:if test="${search.level==2}">selected</c:if> >2级</option>
					<option value="1" <c:if test="${search.level==1}">selected</c:if> >1级</option>
				</select>
				
				<br>
		        <label>
					不及时处理：
					<select class="mr10" name="search.notInTimeDeal">
						<option value="0" selected >请选择</option>
						<option value="1" <c:if test="${search.notInTimeDeal==1}">selected</c:if> >四五星会员半小时未呼出</option>
						<option value="2" <c:if test="${search.notInTimeDeal==2}">selected</c:if> >其余星级会员两小时未呼出</option>
						<option value="3" <c:if test="${search.notInTimeDeal==3}">selected</c:if> >出境9个自然日未完成</option>
						<option value="4" <c:if test="${search.notInTimeDeal==4}">selected</c:if> >国内5个自然日未完成</option>
						<option value="5" <c:if test="${search.notInTimeDeal==5}">selected</c:if> >15个自然日未完成</option>
					</select>
			    </label>
				<label class="mr10"> <input type="text" size="20" name="search.notInTimeDealDate" id="notInTimeDealDate" value="${search.notInTimeDealDate }"onclick="WdatePicker()" readOnly="readonly"/></label>
				
				<label> 出发时间： <input type="text" size="20" name="search.startTimeBegin" id="start_time_begin"	value="${search.startTimeBegin }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readOnly="readonly"/> </label> 
				<label class="mr10">至 <input type="text" size="20" name="search.startTimeEnd" id="start_time_end"value="${search.startTimeEnd }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readOnly="readonly"/> </label> 
			    
			    <label class="mr10">
					客服经理：
					<input type="text" name="search.customerLeader" id="manager" value="${search.customerLeader}"/>
				</label>
				
				<label class="mr10">
					供应商名称：
					<input type="text" name="search.agencyName" id="agencyName" value="${search.agencyName}"/>
				</label>
			           
				
				<br> 

				<label> 投诉时间： <input type="text" size="20" name="search.startBuildDate" id="start_build_date"	value="${search.startBuildDate }" onclick="WdatePicker()" readOnly="readonly"/> </label> 
				<label class="mr10">至 <input type="text" size="20" name="search.endBuildDate" id="end_build_date"value="${search.endBuildDate }" onclick="WdatePicker()" readOnly="readonly"/> </label> 
				<label> 完成时间： <input type="text" size="20" name="search.startFinishDate" id="start_finish_date" value="${search.startFinishDate }"onclick="WdatePicker()" readOnly="readonly"/> </label> 
				<label class="mr10">至 <input type="text" size="20" name="search.endFinishDate" id="end_finish_date" value="${search.endFinishDate }" onclick="WdatePicker()" readOnly="readonly"/> </label> 
				<label class="mr10">
					处理人： 
					<input	type="text" size="10" name="search.dealNameText" id="deal_name" value="${search.dealNameText}"/>
					<input type="hidden" name="search.deal" id="deal" value="${search.deal}"/>
					
					
				</label>
				
				处理岗：<s:select list="dealDepartments" headerKey="" headerValue="请选择" name="search.dealDepart"/>
				
				处理状态：
				<select class="mr10" name="search.stateWork">
					<option value="-1" <c:if test="${search.stateWork=='1,2,3,4'}">selected</c:if>>全部</option>
					<option value="1" <c:if test="${search.stateWork=='1'}">selected</c:if>>待分配</option>
					<option value="2" <c:if test="${search.stateWork=='2'}">selected</c:if>>处理中</option>
					<option value="3" <c:if test="${search.stateWork=='3'}">selected</c:if>>已待结</option>
					<option value="4" <c:if test="${search.stateWork=='4'}">selected</c:if>>已完成</option>
					<option value="5" <c:if test="${search.stateWork=='5'}">selected</c:if>>投诉待指定（升级售后）</option>
					<option value="6" <c:if test="${search.stateWork=='6'}">selected</c:if>>投诉待指定（提交售后填写分担方案）</option>
					<option value="7" <c:if test="${search.stateWork=='7'}">selected</c:if>>投诉待指定（升级售前）</option>
					<option value="9" <c:if test="${search.stateWork=='9'}">selected</c:if>>已撤销</option>
					<option value="10" <c:if test="${search.stateWork=='10'}">selected</c:if>>投诉待指定（升级客服中心售后）</option>
				</select>  
				
				<input type="button" value="查询" class="blue" name="" onclick="onSearchClicked();"/>
				<input type="button" value="重置" class="blue" name="" onclick="onResetClicked();"/> 
			</div>
		</div>
	</form>
	<!-- 分配负责人或者交接工作表单 -->
<form method="post" enctype="multipart/form-data"  action="complaint-dealPeople" id="deal_form" name="deal_form">
  	<input type="hidden" name="c_type" id="c_type" value="menu_${type }">
  	<input type="hidden" name="assignFlag" id="assignFlag" value="">
 	<!--1 待处理-->
	
	<div class="notice mb10">
     <p class="pd5">
		<input type="text" size="10" id="deal_joinPeople_name" name="deal_joinPeople_name"/>
		<input type="hidden" id="deal_joinPeople" name="deal_joinPeople" value="0"/>
      	<input id="deal_joinPeople_button" class="mr20" type="button" name="button1" value="工作交接" onclick="dealjoinPeople()"/>
      
      <c:if test="${isCtOfficer == true}"> <!-- 主管才有分配权限 -->
      	<input type="text" size="10" id="select2_delPeople_name" name="select2_delPeople_name" />
		<input type="hidden" id="select2_delPeople" name="select2_delPeople" value="0"/>
		<input type="hidden" id="select_delPeople" name="select_delPeople" value="0"/>		
		<input id="select2_delPeople_button" class="mr20" type="button" name="button" value="重新分配" onclick="selectdelPeople(1)"/>
	  </c:if>
	  
	  <c:if test="${isSQ == 1}">
		<input type="text" id="select_customer" name="select_customer" value=""/>
		<input type="hidden" id="h_select_customer_name" value="0"/>
		<input id="select_customer_button" class="mr20" type="button" name="button2" value="变更客服经理" onclick="changeCustomerManager()"/>
	  </c:if>
	  <marquee onmouseover=this.stop() onmouseout=this.start() height="25" direction="up" scrolldelay="4" scrollamount="0.3" style="width: 50%">
	  <span class="cred">超期标准：从投诉单发起时刻起，国内3天，出境5天，按工作日计算
	  <br/>
	     待首呼：没有首呼的投诉。
	  <br/>
	     提醒未跟进：设置下次提醒超时未更进。（黄色表示超时一小时以上）
	  <br/>
	  15日未完成：投诉单创建后 15个工作日内未完成。
	     超时处理中：国内产品的投诉单（包含签证、门票）：投诉单创建三个自然日后未进入已待结或已完成状态
	   <br/>
	     出境产品的投诉单：投诉单创建后五个自然日内未进入已待结或已完成状态。
	   <br/>  
	     对于其它产品的投诉单：同国内的原则，即投诉单创建三个自然日后未进入已待结或已完成状态。
	   <br/>
	     交接单列表：交接给自己的投诉单。
	  </span>
	  </marquee>
      
      </p>
  </div>


<table class="listtable" width="98%">
	<thead>
		<th>选择</th>
		<th>投诉号</th>
		<th>关联订单id</th>
		<th>客人姓名</th>
		<th>客人等级</th>
		<th>特殊会员</th>
		<th>出发地/线路</th>
		<th>出发时间</th>
		<th>投诉时间</th>
		<th>最近重复投诉时间</th>
		<th>客服经理</th>
		<th>产品经理</th>
		<th>订单状态</th>
		<th>处理岗</th>
		<th>投诉来源</th>
		<th>投诉等级</th>
		<th>处理人</th>
		<c:if test="${stab_flag eq 'waitHandOver'}">
		<th>交接处理人</th>
		</c:if>
		<th>投诉处理状态</th>
		<th>供应商名称</th>
		<th>供应商电话</th>
	</thead>
	<tbody>
		<c:forEach items="${dataList}" var="v"  varStatus="st"> 
		<tr align="center" <c:if test="${(currTime.getTime()-v.minFollowTime.getTime()>3600000) && stab_flag eq 'waitFollowUp'}">class="yellowbg"</c:if> class="trbg" >
			<td>
			<span <c:if test="${(is_after_saler==0 && !v.orderState.equals(v.dealDepart)) || v.state==9 }">style="display:none"</c:if>>
				<input name="ids" type="checkbox" id="st.count" value="${v.id}"/>
			</span>
			</td> 
			<fmt:formatDate var="curDate1" value="${curDate}" pattern="yyyy-MM-dd" />
			<fmt:formatDate var="addDate1" value="${v.buildDate}" pattern="yyyy-MM-dd" />
			<td>
				<c:if test="${v.isSticky==1}"><font color="red" title="新增投诉事宜">★ </font></c:if>
				<c:if test="${v.isSticky==2}"><font color="limegreen" title="重复投诉">◆ </font></c:if>
				<c:if test="${v.isSticky==3}"><font color="orange" title="[重复投诉]新增投诉事宜">◆ </font></c:if>
				<a href="complaint-toBill?id=${v.id}" target="_blank" id="td_${v.id}">${v.id}</a>
			</td>
			<c:if test="${v.state < 4}"><script>diffDate("${v.id}","${curDate1}","${addDate1}");</script></c:if>
			<td>
			<font <c:if test="${v.sameGroup>1}">color="red" </c:if> >
			<c:if test="${v.orderId > 0}">
				<a href="#" onclick="showOrder(${user.id},'${user.realName}',${v.orderId})">${v.orderId}</a>
			</c:if>
			</font>
			<c:if test="${v.sameGroup>1}">
			<a href="javaScript:void(0);"  color="red" onclick='startLoadComplaint("<fmt:formatDate value='${v.startTime}' pattern='yyyy-MM-dd' />",${v.routeId}, ${v.id})'>
			<font color="red" size="1px" style="margin-top: 1px">(${v.sameGroup})</font></a>
			<div ondblclick="javaScript:hideShowNOs(${v.id});" id="show_${v.id}" style="border: 5px solid rgb(161, 165, 177); z-index: 999; left: 10%; background-color: rgb(100, 200, 100);position: absolute; display: none;height: auto; width: 200px; margin-top: -50px; word-wrap: break-word; text-align: left"></div>
			</c:if>
			</td> 
			<td>${v.guestName}</td>
			<td>${v.guestLevel}</td>
			<td style="color: red">
				<c:forEach items="${tagList}" var="tag"  varStatus="st"> 
					<c:if test="${v.custId==tag.cust_id}">${tag.tag_values}</c:if>
				</c:forEach>
			</td>
			<td><c:if test="${v.startCity != '' || v.routeId > 0}">${v.startCity}-${v.routeId}</c:if></td> 
			<td><fmt:formatDate value="${v.startTime}" pattern="yyyy-MM-dd" /></td> 
			
			<!-- 针对“投诉处理中”的订单，最后一次跟进时间和当前时间对比，一旦大于等于一定时间后，在投诉事件字段背景变成“橙色”预警（提醒客服和主管及时跟进，避免跟进不及时）。 -->
			<!-- PS：规则，出游前2天，出游中3天，出游后3天。 -->
			<td
			<c:if test="${v.state==2}">
				<c:if test="${(currTime.getTime()-v.updateTime.getTime()>172800000 && v.orderState=='出游前') || (currTime.getTime()-v.updateTime.getTime()>259200000 && v.orderState=='出游中') || (currTime.getTime()-v.updateTime.getTime()>259200000 && v.orderState=='出游后')}">
					class="orange"
				</c:if>
			</c:if>>
			
			<fmt:formatDate value="${v.buildDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td> 
			<td><fmt:formatDate value="${v.repeateTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td> 
			<td>${v.customerLeader}</td> 
			<td>${v.productLeader}</td> 
			<td>
			<span id="oldOrderState_${v.id}">${v.orderState}</span>
			<c:if test="${type==1 && isChangeOrderState==1 && v.orderState.equals(v.dealDepart) && ''.equals(v.dealName) || type==2 && isChangeOrderState==1}">
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
			<c:if test="${stab_flag eq 'waitHandOver'}">
			<td>${v.associaterName}</td>
			</c:if>
			<td id="${v.id}_state"><c:if test="${v.state==1}">投诉待处理</c:if>
				<c:if test="${v.state==2}">投诉处理中</c:if>
				<c:if test="${v.state==3}">投诉已待结</c:if>
				<c:if test="${v.state==4}">投诉已完成</c:if>
				<c:if test="${v.state==5}">投诉待指定（升级售后）</c:if>
				<c:if test="${v.state==6}">投诉待指定（提交售后填写分担方案）</c:if>
				<c:if test="${v.state==7}">投诉待指定（升级售前）</c:if>
				<c:if test="${v.state==9}">已撤销</c:if>
				<c:if test="${v.state==10}">投诉待指定（升级客服中心售后）</c:if>
			</td> 
			<td>${v.agencyName}</td>
			<td>${v.agencyTel}</td>
			
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
	var tab_flag = $('#tab_flag').val();
	$('#'+tab_flag).addClass("menu_on");
});

function checkOrangeNotify(){
	return true;
	
}
</script>

<%@include file="/WEB-INF/html/pager.jsp" %>
<%@include file="/WEB-INF/html/foot.jsp" %>
