<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %> 
<%@include file="/WEB-INF/html/head.jsp" %>
<link href="${CONFIG.res_url}css/thickbox.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${CONFIG.res_url}script/jquery/plugin/auto_complete/jquery.autocomplete.css"/>

<script type="text/javascript" src="${CONFIG.res_url}script/thickbox-compressed.js"></script>
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

	$('#chkAll').click(function() {
		if($(this).attr("checked")) {
			$(this).parents('table').find(":checkbox[name='ids']").attr("checked", true);
		} else {
			$(this).parents('table').find(":checkbox[name='ids']").attr("checked", false);
		}
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
	$('#custUnAgreed').addClass("menu_on");
	$('#normal').addClass("menu_on");
	if(menuId == "menu_2"){
		searchTab("custUnAgreed");
	} else if(menuId == "menu_3"){
		searchTab("normal");
	}else{
		$('#complaint_form').submit();
	}
}

function searchTab(flag){ 
	//$('#complaint_form').attr("action", "${manageUrl}");
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

function initExport(){
	if($("#butExp").val()=="打开导出界面"){
		$("#exportEntity").show();
		$("#butExp").val("关闭导出界面");
	}else{
		$("#exportEntity").hide();
		$("#butExp").val("打开导出界面");
	}
}

function onSearchExport() {
	$('#complaint_form').attr("action", "complaint-exports");
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

//变更处理岗
function changeDealDepart(select){
	var newDealDepart = select.value;
	var complaintId = select.parentNode.id.split('_')[1];
	var oldDealDepart =  $('#olddealDepart_'+complaintId).text();
	if(newDealDepart != oldDealDepart) {
		if(confirm("确认变更处理岗为" + newDealDepart + "?")){
			$.ajax({
				type:"post",
				url:"complaint-changeDealDepart",
				data:{"dealDepart":newDealDepart,"id":complaintId },
				success:function(data){
					alert("操作成功"); 
					onSearchClicked();
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
	<c:forEach items="${users}" var="userItem">
		selectDelPeopleArray.push({'id' : '${userItem.id}', 'realName' : '${userItem.realName}', 'userName' : '${userItem.userName}', 'name' : '${userItem.realName}${userItem.userName}'});
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

}

/**
 * 查询前检验处理人
 */
function beforeFormSubmit(){
	if($('#deal_name').val() == ''){
		$('#deal').val(0);
	}
}


function hideShowNOs(id){
	$('#show_'+id).hide();
}

function custAgree(id, custAgreeFlag){
	$.ajax({
		"type":"POST",
		"url":"complaint-custAgree",
		data: {"id":id, "custAgreeFlag":custAgreeFlag},
		success: function(data) {
			onSearchClicked();
		},
		error:function() {
			alert("error");
		}
	});
}

function cancelComplaint(id){
	if(confirm('确定撤销吗？')){
		$.ajax({
			"type":"GET",
			"url":"complaint-cancel",
			data: "id="  + id + "&c_type=menu_" + ${type},
			success: function(data) {				
				onSearchClicked();
			},
			error:function() {
				alert("error");
			}
		});
	}
	
}

function onResetClicked() {
    $(':input','#complaint_form').not(':button, :submit, :reset, :hidden')  
    .val('').removeAttr('checked').removeAttr('selected');
}

function back2Processing(id){
	$.ajax({
		"type":"POST",
		"url":"complaint-back2Processing",
		data: {"id":id},
		success: function(data) {
			onSearchClicked();
		},
		error:function() {
			alert("error");
		}
	});
}

//根据投诉单id交接
function handover(id){
	if(confirm('确定交接吗？')){
		$.ajax({
			"type":"POST",
			"url":"complaint-handover",
			data: {"id":id},
			success: function(data) {
				onSearchClicked();
			},
			error:function() {
				alert("error");
			}
		});
	}
}

//批量交接
function handoverAll(){
	if(confirm('确定批量交接吗？')){
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
			var param = "ids="+ids;
			$.ajax({
				type: "POST",
				async:false,
				url: "complaint-handoverAll",
				data: param,
				success: function(data){
					onSearchClicked();
			     }
			   });
		}
	}
}

</script>
</HEAD>
<BODY>
<div class="top_crumbs">您当前所在的位置：<a href="#">投诉质检管理</a>&gt;&gt;<span class="top_crumbs_txt">投诉处理列表</span></div>
	<form name="form" id="complaint_form" method="post" onSubmit="return beforeFormSubmit();"
		enctype="multipart/form-data" action="complaint-execute">
		<input type="hidden" name="c_type" id="c_type" value="menu_${type }">
		<input type="hidden" name="search.tab_flag" id="tab_flag" value="${stab_flag}">
		<input type="hidden" name="showCompareMsg" id="showCompareMsg" value="${showCompareMsg}">
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
					<s class="rc-l"></s><s class="rc-r"></s><a href="javascript:void(0)">投诉已撤销</a>
				</li>
				
			</ul>
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
				<label class="mr10">出发城市：<input type="text" size="10" name="search.startCity" value="${search.startCity}"></label>
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
				签约城市：<input type="text" name="search.signCity" value="${search.signCity}">	
				<br>
				<label class="mr10">
					供应商名称：
					<input type="text" name="search.agencyName" id="agencyName" value="${search.agencyName}"/>
				</label>
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
				
			    <label class="mr10">客服经理：<input type="text" size="10" name="search.customerLeader" value="${search.customerLeader}"/></label> 
				<label class="mr10">产品经理： <input type="text" size="10" name="search.productLeader" value="${search.productLeader}" /></label>
				&nbsp;&nbsp;处理优先级：
				<select class="mr10" name="search.priority">
					<option value="-1" >请选择</option>
					<option value="1" <c:if test="${search.priority=='1'}">selected</c:if> >紧急</option>
					<option value="2" <c:if test="${search.priority=='2'}">selected</c:if> >重要</option>
					<option value="3" <c:if test="${search.priority=='3'}">selected</c:if> >普通</option>
					<option value="4" <c:if test="${search.priority=='4'}">selected</c:if> >一般</option>
				</select>
				<br>
				<label> 投诉时间： <input type="text" size="20" name="search.startBuildDate" id="start_build_date"	value="${search.startBuildDate }" onclick="WdatePicker()" readOnly="readonly"/> </label> 
				<label class="mr10">至 <input type="text" size="20" name="search.endBuildDate" id="end_build_date"value="${search.endBuildDate }" onclick="WdatePicker()" readOnly="readonly"/> </label> 
				<label> 完成时间： <input type="text" size="20" name="search.startFinishDate" id="start_finish_date" value="${search.startFinishDate }"onclick="WdatePicker()" readOnly="readonly"/> </label> 
				<label class="mr10">至 <input type="text" size="20" name="search.endFinishDate" id="end_finish_date" value="${search.endFinishDate }" onclick="WdatePicker()" readOnly="readonly"/> </label> 
				<label> 分配时间： <input type="text" size="20" name="search.startAssignTime" id="start_assign_time" value="${search.startAssignTime }"onclick="WdatePicker()" readOnly="readonly"/> </label> 
				<label class="mr10">至 <input type="text" size="20" name="search.endAssignTime" id="end_assign_time" value="${search.endAssignTime }" onclick="WdatePicker()" readOnly="readonly"/> </label>  
				<br>
				<label class="mr10">
					处理人： 
					<input	type="text" size="10" name="search.dealNameText" id="deal_name" value="${search.dealNameText}"/>
					<input type="hidden" name="search.deal" id="deal" value="${search.deal}"/>
				</label>
				处理岗：<s:select list="dealDepartments" headerKey="" headerValue="请选择" name="search.dealDepart"/>
				投诉发起人：<input type="text" name="search.ownerName" value="${search.ownerName}">　
				航变：
				<select class="mr10" name="search.chgFlightFlag">
					<option value="" >请选择</option>
					<option value="1" <c:if test="${search.chgFlightFlag=='1'}">selected</c:if> >已航变</option>
					<option value="0" <c:if test="${search.chgFlightFlag=='0'}">selected</c:if> >未航变</option>
				</select>　
				是否为赔款单：<select class="mr10" name="search.isReparations">
					<option value="" >请选择</option>
					<option value="1" <c:if test="${search.isReparations=='1'}">selected</c:if> >是</option>
					<option value="0" <c:if test="${search.isReparations=='0'}">selected</c:if> >否</option>
				</select>
				是否为无订单：<select class="mr10" name="search.isNoOrder">
					<option value="" >请选择</option>
					<option value="1" <c:if test="${search.isNoOrder=='1'}">selected</c:if> >是</option>
					<option value="0" <c:if test="${search.isNoOrder=='0'}">selected</c:if> >否</option>
				</select>
				<c:if test="${is_distribute_saler==1}">
				<%-- <select class="mr10" name="search.isDistribute">
					<option value="" >分销订单类型</option>
					<option value="0" <c:if test="${search.isDistribute=='0'}">selected</c:if> >分销度假</option>
					<option value="1" <c:if test="${search.isDistribute=='1'}">selected</c:if> >分销机票</option>
				</select> --%>
				</c:if>
				<input type="button" value="查询" class="blue" onclick="onSearchClicked();"/>　
				<input type="button" value="重置" class="blue" onclick="onResetClicked();"/> 
				<!-- <input type="button" id="butExp" value="打开导出界面" class="blue" name="" onclick="initExport();"/> -->
				
				<div align="right" id="exportEntity" style="display: none;">
					<label><input type="checkbox" value="routeId" name="exportEntity">线路号</label>
					<label><input type="checkbox" value="comeFrom" name="exportEntity">投诉来源</label>
					<label><input type="checkbox" value="startCity" name="exportEntity">出发城市</label>
					<label><input type="checkbox" value="productLeader" name="exportEntity">产品经理</label>
					<label><input type="checkbox" value="customerLeader" name="exportEntity">客服经理</label>
					
					<label><input type="checkbox" value="buildDate" name="exportEntity">投诉时间</label>
					<label><input type="checkbox" value="finishDate" name="exportEntity">处理完成时间</label>
					<label><input type="checkbox" value="agencyId" name="exportEntity">供应商ID</label>
					<label><input type="checkbox" value="agencyName" name="exportEntity">供应商名称</label>
					
					<label><input type="checkbox" value="destCategoryName" name="exportEntity">目的地大类</label>
					<label><input type="checkbox" value="dealDepart" name="exportEntity">处理岗</label>
					<label><input type="checkbox" value="dealName" name="exportEntity">处理人</label>
					<label><input type="checkbox" value="ownerName" name="exportEntity">发起人</label>
					<label><input type="checkbox" value="level" name="exportEntity">投诉等级</label>
					<label><input type="checkbox" value="reason" name="exportEntity">投诉事宜</label>
					<!-- <input type="button" value="导出" class="blue" name="" onclick="onSearchExport();"/> -->
				</div>
				
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
				<input type="text" size="10" id="select_delPeople_name" name="select_delPeople_name"/>
				<input type="hidden" id="select_delPeople" name="select_delPeople" value="0"/>
				<input id="select_delPeople_button" class="mr20" type="button" name="button" value="分配处理人" onclick="selectdelPeople(0)"/>
				
				<!-- 
				<c:if test="${isSQ == 1}">
					<input type="text" id="select_customer" name="select_customer" value=""/>
					<input type="hidden" id="h_select_customer_name" value="0"/>
					<input id="select_customer_button" class="mr20" type="button" name="button2" value="变更客服经理" onclick="changeCustomerManager()"/>
				</c:if> -->
				
      			<span class="cred">超期标准：从投诉单发起时刻起，国内3天，出境5天，按工作日计算</span>
      			<span style="float:right;font-size:20px;">请分配处理人前确认记录行处理岗为自己所在组织，不符时请仅查看记录（不要操作）</span>
      			</p>
			</p>
			</div>
		</c:if>
	</c:if>
  
  <c:if test="${type==2 || type==3}">
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
	  <%-- <c:if test="${type==2&&handOverAll==1}">
		<input class="mr20" type="button" name="button" value="批量交接" onclick="handoverAll()"/>
	  </c:if> --%>
      <span class="cred">超期标准：从投诉单发起时刻起，国内3天，出境5天，按工作日计算</span>
      </p>
  </div>
  
  </c:if>
  
  <c:if test="${type==2}">
  	<div id="pici_tab" class="clear">
		<ul id="searchTab">
			<li onclick="searchTab(this.id)" id="custUnAgreed">
				<s class="rc-l"></s><s class="rc-r"></s><a href="javascript:void(0)">对客未达成</a>
			</li>
			<li onclick="searchTab(this.id)" id="custAgreed">
				<s class="rc-l"></s><s class="rc-r"></s><a href="javascript:void(0)">对客已达成</a>
			</li>
		</ul>
	</div>
  </c:if>
  
  <c:if test="${type==3}">
  <div id="pici_tab" class="clear">
			<ul id="searchTab">
				<li onclick="searchTab(this.id)" id="normal">
					<s class="rc-l"></s><s class="rc-r"></s><a href="javascript:void(0)">非理赔退货</a>
				</li>
				<li onclick="searchTab(this.id)" id="insurance">
					<s class="rc-l"></s><s class="rc-r"></s><a href="javascript:void(0)">保险理赔</a>
				</li>
				<li onclick="searchTab(this.id)" id="returnGood">
					<s class="rc-l"></s><s class="rc-r"></s><a href="javascript:void(0)">退货</a>
				</li>
			</ul>
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
		<th><input type="checkbox" id="chkAll"></th>
		<th>投诉单号</th>
		<th>订单号</th>
		<th>签约城市</th>
		<th>客人姓名</th>
		<th>客人等级</th>
		<th>特殊会员</th>
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
		<c:if test="${type==2}">
			<th>NB沟通状态</th>
		</c:if>
		<th>供应商名称</th>
		<th>处理优先级</th>
		<th>备注</th>
		<th>操作</th>
	</thead>
	<tbody>
		<c:forEach items="${dataList}" var="v"  varStatus="st"> 
		<tr align="center" <c:if test="${v.state==6}">class="yellowbg"</c:if> class="trbg" >
			<td><input name="ids" type="checkbox" value="${v.id}"/></td> 
			<fmt:formatDate var="curDate1" value="${curDate}" pattern="yyyy-MM-dd" />
			<fmt:formatDate var="addDate1" value="${v.buildDate}" pattern="yyyy-MM-dd" />
			<td width="100px">
				<c:if test="${v.state!=3&&v.state!=4&&v.isSticky==1}"><font color="red" title="新增投诉事宜">★ </font></c:if>
				<c:if test="${v.state!=3&&v.state!=4&&v.isSticky==2}"><font color="limegreen" title="重复投诉">◆ </font></c:if>
				<c:if test="${v.state!=3&&v.state!=4&&v.isSticky==3}"><font color="orange" title="[重复投诉]新增投诉事宜">◆ </font></c:if>
				<a href="complaint-toBill?id=${v.id}" target="_blank" id="td_${v.id}">${v.id}</a>
				<c:if test="${v.chgFlightFlag==1}"><img title="航变" src="${CONFIG.res_url}images/icon/default/plane.png" width="15px" height="15px"/> </c:if>
				<c:if test="${v.warningFlag==1}"><img title="预警" src="${CONFIG.res_url}images/icon/default/warning.jpg" width="15px" height="15px"/> </c:if>
				<c:if test="${1<v.batchCompNum && v.batchCompNum<=10}"><span title="批量投诉涉及订单数量" style="color: darkorange">[${v.batchCompNum}]</span></c:if>
				<c:if test="${v.batchCompNum>10}"><span title="批量投诉涉及订单数量" style="color: red">[${v.batchCompNum}]</span></c:if>
			</td>
			<c:if test="${v.state < 4}"><script>diffDate("${v.id}","${curDate1}","${addDate1}");</script></c:if>
			<td>
			<c:if test="${v.orderId > 0}">
			
				<a href="#" onclick="showOrder(${user.id},'${user.realName}',${v.orderId})">${v.orderId}</a>
			</c:if>
			</td>
	  		<td>
				${v.signCity}
			</td>
			<td>${v.guestName}</td>
			<td <c:if test="${v.guestLevelNum>=5 }">style='color:red'</c:if>>
				${v.guestLevel}
			</td>
			<td style="color: red">
				<c:forEach items="${tagList}" var="tag"  varStatus="st"> 
					<c:if test="${v.custId==tag.cust_id}">${tag.tag_values}</c:if>
				</c:forEach>
			</td>
			<td><c:if test="${v.startCity != '' || v.routeId > 0}">${v.startCity}-${v.routeId}</c:if></td> 
			
			<!-- 针对“投诉处理中”的订单，最后一次跟进时间和当前时间对比，一旦大于等于一定时间后，在投诉事件字段背景变成“橙色”预警（提醒客服和主管及时跟进，避免跟进不及时）。 -->
			<!-- PS：规则，出游前2天，出游中3天，出游后3天。 -->
			<td
			<c:if test="${v.state==2}">
				<c:if test="${(currTime.getTime()-v.updateTime.getTime()>172800000 && v.orderState=='出游前') || (currTime.getTime()-v.updateTime.getTime()>259200000 && v.orderState=='出游中') || (currTime.getTime()-v.updateTime.getTime()>259200000 && v.orderState=='出游后')}">
					class="orange"
				</c:if>
			</c:if>
			<c:if test="${v.state==4}">
				<c:if test="${(((v.finishDate.getTime()-v.assignTime.getTime())>432000000) && fn:indexOf(v.destCategoryName,'出境') != -1) || (((v.finishDate.getTime()-v.assignTime.getTime())>259200000) && fn:indexOf(v.destCategoryName,'出境') == -1)}">
					class="orange"
				</c:if>
			</c:if>>
			<fmt:formatDate value="${v.buildDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td> 
			<td>${v.customerLeader}</td> 
			<td>${v.productLeader}</td> 
			<td>${v.orderState}</td> 
			<td>
			<span id="olddealDepart_${v.id}" <c:if test="${!v.orderState.equals(v.dealDepart) }">style="color:red;"</c:if>>${v.dealDepart}</span>
			<span style="display:none" id="dealDepart_${v.id}">
				<s:select list="dealDepartments" headerKey="" headerValue="请选择"  onchange="changeDealDepart(this)"/>
			</span>
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
				<c:if test="${v.state==7}">投诉待指定（升级售前）</c:if>
				<c:if test="${v.state==9}">已撤销</c:if>
				<c:if test="${v.state==10}">投诉待指定（升级客服中心售后）</c:if>
			</td> 
			<c:if test="${type==2}">
			<td style="width:80px">
			   <c:if test="${v.commitStatusName==''||v.commitStatusName ==null}">
			  	  未发起
				</c:if>
			     <c:if test="${v.commitFlag=='1' }">
			     ${v.commitStatusName}<span style="color: red;">(${v.commitFlagName})</span>
				</c:if>
				 <c:if test="${v.commitFlag=='0' }">
				  ${v.commitStatusName}
			</c:if>
			</td>
			</c:if>
			<td>${v.agencyName}</td>
			<td>
			<c:if test="${v.priority==1}">
			 <span style="color: red;">  紧急</span>
			</c:if>
			<c:if test="${v.priority==2}">
			 <span style="color: red;"> 重要</span>	
			</c:if>
			<c:if test="${v.priority==3}">
			 	普通
			</c:if>
			<c:if test="${v.priority==4}">
			 	一般
			</c:if>
			</td>
			<td class="shorten10">${v.priorityContent}</td>
			<c:if test="${v.state!=9}">
			<td>
				<c:if test="${type==2}">
					<c:if test="${v.custAgreeFlag == 0}">
						<span><a href="javascript: custAgree('${v.id}', 1)">转入已达成</a></span>
					</c:if>
					<c:if test="${v.custAgreeFlag == 1}">
						<span><a href="javascript: custAgree('${v.id}', 0)">转入未达成</a></span>
					</c:if>
				</c:if>
				<c:if test="${is_after_saler==1 || is_air_ticket_saler==1 || cancelAuth==1}">
					<span><a href=" javascript: cancelComplaint(${v.id}) ">撤销</a></span>
				</c:if>
				<span <c:if test="${type!=1 && type!=2}">style="display:none"</c:if>>
					<c:if test="${v.state==10 && is_after_saler==1}">
						<!-- <a href="complaint-sendBack?id=${v.id}&c_type=menu_${type}">退回</a> -->
						<a href="#TB_inline?placehold=1&height=200&width=500&inlineId=backDivContent_${v.id}" title="退回原因" class="thickbox">退回</a>
						<div id="backDivContent_${v.id}" style="display:none;">
							<table>
								<tr>
								<th align="right">退回原因<span class="cred">*</span>：</th>
								<td colspan="2"><textarea rows="5" cols="45" id="back_reason_${v.id}" name="back_reason"></textarea></td>
								</tr>
								<tr>
								<th align="right"></th>
								<td>
								<input type="button" name="button" value="确认" onclick="sendBack(${v.id}, 'menu_${type}')"/>
								</td>
								<td>
								<input type="button" name="cancel" value="取消" onclick="self.tb_remove();"/>
								</td>
								</tr>
							</table>
						</div>
					</c:if>
					<c:if test="${is_before_saler==1 || is_in_saler==1 || is_special_before_saler==1 || is_after_saler==1 || is_air_ticket_saler==1}">
						<c:if test="${v.orderId>0 && v.state!=6 && v.state!=7 && v.state!=10}">
							<c:choose>
								<c:when test="${v.dealDepart=='售后组'||v.dealDepart=='机票组'}">
									<a href="javascript:void(0)" onclick="openWinX('选择升级原因', 'complaint-upgradeReason?complaintId=${v.id}',536,210)">升级投诉</a>
								</c:when>
								<c:when test="${v.dealDepart=='售前组'||v.dealDepart=='会员事业部'||v.dealDepart=='途致事业部'
									||v.dealDepart=='预订中心'||v.dealDepart=='客户事业部'||v.dealDepart=='会员顾问'}">
									<a href="complaint-upgrade?id=${v.id}&c_type=menu_${type}">升级投诉</a>
								</c:when>
							</c:choose>
						</c:if>
					</c:if>
				</span> 
				<c:if test="${(type==1 || type==2) && isChangeDealDepart==1}">
					<a href="#" onclick="$('#dealDepart_${v.id}').toggle();">变更处理岗</a>
				</c:if>
				<c:if test="${v.state==2&&(v.dealDepart=='售后组'||v.dealDepart=='酒店组'||v.dealDepart=='机票组'||v.dealDepart=='交通组')}">
					<a href="#" onclick="openWinX('填写跟进内容', 'complaint-follow_handover?complaintId=${v.id}&orderId=${v.orderId}',600,230)">交接</a>
				</c:if>
			</td>
			</c:if>
			<c:if test="${v.state==9}">
			<td>
				<a href="#" onclick="back2Processing(${v.id})">返回处理中</a>
			</td>
			</c:if>
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
	var showCompareMsg = $('#showCompareMsg').val();
	if(showCompareMsg == 1){
		alert("本次导出的数据超过上限50000条！");
	}
});

function checkOrangeNotify(){
	return true;
}
</script>

<%@include file="/WEB-INF/html/pager.jsp" %>
