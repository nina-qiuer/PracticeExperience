<%@page import="java.net.URLEncoder"%>
<%@page import="com.opensymphony.xwork2.ognl.OgnlValueStack"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %> 
<%@ include file="/WEB-INF/html/head.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>质检投诉处理列表</title>
<link type="text/css" href="${CONFIG.res_url}css/bui.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="${CONFIG.res_url}script/jquery/plugin/auto_complete/jquery.autocomplete.css"/>
<script type="text/javascript" src="${CONFIG.res_url}script/jquery/plugin/thickbox/thickbox-compressed.js"></script> 
<link type="text/css" rel="stylesheet" href="${CONFIG.res_url}script/jquery/plugin/thickbox/thickbox.css" />
<script type="text/javascript" src="${CONFIG.res_url}script/DatePickerNew/WdatePicker.js" ></script>  
<script type="text/javascript" src="${CONFIG.res_url}script/jquery/plugin/auto_complete/jquery.autocomplete.js"></script>

<style>
.listtable tr.redbg td{background:#FFE6E6}
.listtable tr.bluebg td{background:blue}
</style>
<script type="text/javascript">

//TAB 切换
$(document).ready(function(){
	var status = ${status};
	var tab = "#00" + (status + 1);
	
	tabSelected(tab);
});

/*tab sel func*/
function tabSelected(showId) {
	$(".tab_part").hide();
	$("#pici_tab .menu_on").removeClass("menu_on");
	//$(navObj).addClass("menu_on");
	$(showId+"_m").addClass("menu_on");
	$(showId).show();
}

function onTabClicked(showId) {
	tabSelected(showId);

	$(showId).html("");
	
	var status = 0;
	if (showId == "#001") {
		status = 0;
	} else if (showId == "#002") {
		status = 1;
	} else if (showId == "#003") {
		status = 2;
	}else if (showId == "#004") {
		status = 3;
	} else {
		alert("Oops! Select Error!");
	}
	
	$('#status').attr("value", status);
	var baseUrl = "${manageUrl}"; 
	$('#search_form').attr("action","qc-execute");
	$('#search_form').submit();
	
	return false;
}

function onSearchClicked() {
	$('#search_form').attr("action", "qc");
	$('#search_form').submit();
}

function onResetClicked(){
	$(':input','#search_form').not(':button, :submit, :reset, :hidden')  
    .val('').removeAttr('checked').removeAttr('selected');
}

function onSearchExport() {
	var param = $('#search_form').serialize();
	$.ajax({
		type: "POST",
		url: "qc-getExportDataTotal",
		data: param,
		success: function(data){
			if(data > 30000) {
				alert("数据超过30000条，请缩小查询范围，分多次导出！");
			} else {
				$('#search_form').attr("action", "qc-exports");
				$('#search_form').submit();
			}
		}
	});
}

$(function() {
	/*移除thickbox默认样式*/
	$('.thickbox')
			.click(
					function() {
						$('#TB_closeAjaxWindow')
								.html(
										"<a href='javascript:void(0)' id='TB_closeWindowButton' title='Close'>×</a>");
						$('#TB_closeWindowButton').live('click',
								function() {
									tb_remove();
								});
						
					  $("#TB_overlay").unbind('click');
					});
	
	$('#chkPreProcess,#chkProcessing').click(function(){
		if($(this).attr("checked")) {
			$(this).parents('table').find(":checkbox[name='ids']").attr("checked", true);
		}else{
			$(this).parents('table').find(":checkbox[name='ids']").attr("checked", false);
		}
	});
	
});

function toSpecialConsultation(){
	var c = $(":checkbox[name='ids']:checked").length;
	if(c == 0){
		alert("请至少选择一条投诉单!");
		return false;
	}
	if(confirm("确认要返回到待处理列表么?")){
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
		
		var param = "ids="+ids;
		$.ajax({
			type: "POST",
			async:false,
			url: "qc-toSpecialConsultation",
			data: param,
			success: function(data){
				onSearchClicked();
		     }
		   });
	}
	
}

function onBatchAssignClicked(type) {
	var sel = "";
	var userName = "";
	if(type == 0){
		sel = $("#batchAssignSel").val();
	}else if(type == 1){
		sel = $("#batch2AssignSel").val();
		$("#batchAssignSel").val(sel);
	}
	if(sel == 0){
		alert("请先选择处理人!");
		return false;
	}
	var c = $(":checkbox[name='ids']:checked").length;
	if(c == 0){
		alert("请至少选择一条投诉单!");
		return false;
	}
	
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
	
	
	if(type == 0){
		$('#assignFlag').val("assign");
		userName = $("#batchAssignSel option:selected").text();
	}else if(type == 1){
		$('#assignFlag').val("assignNew");
		userName = $("#batch2AssignSel option:selected").text();
	}
	$("#username").val(userName);
	
	var param = "username="+userName+"&batchAssignSel="+$("#batchAssignSel").val()+"&assignFlag="+$('#assignFlag').val()+"&ids="+ids;
	$.ajax({
		type: "POST",
		async:false,
		url: "qc-doBatchAssign",
		data: param,
		success: function(data){
			onSearchClicked();
	     }
	   });
	
}

// 分配质检负责人 
// qid 质检id
function onAssignClicked(qid,type) {
	var userName = "";
	var selu = "";
	var sel = "#assignSelValue" + qid; 
	if ($(sel).val()=="" || $(sel).val()==0){
		alert("请选择处理人!");
		return false;
	}
	if (type == 0){
		// 设置质检负责人
		$('#assignFlag').val("assign");
		selu = "#assignSel" + qid + " option:selected";
	}else if (type == 1){
		$('#assignFlag').val("assignNew");
		selu = "#assign2Sel" + qid + " option:selected";
	}
	userName = $(selu).text();
	$("#username").val(userName);

	// 质检单号
	$("#qid").val(qid);
	
	var param = "username="+userName+"&assignSelValue"+qid+"="+$(sel).val()+"&assignFlag="+$('#assignFlag').val()+"&qid="+qid;
	$.ajax({
		type: "POST",
		async:false,
		url: "qc-doAssign",
		data: param,
		success: function(data){
			onSearchClicked();
	     }
	   });
	
}


function changesel(uid,qid,type) {
	var sel = "";
	if(type == 0){
		sel = "#assignSelValue" + qid;
	}else if(type == 1){
		sel = "#assignSelValue" + qid;
	}
	$(sel).val(uid);
}

// 认领
// qid 质检id
function onClaimClicked(qid)	{
	$("#qid").val(qid);
	
	$('#assign_form').attr("action", "qc" + "-doClaim");
	$('#assign_form').submit();
}

// 工作交接
function onHandoverClicked() {
	var userName = $("#handoverSel option:selected").text();
	$("#username").val(userName);
	
	$('#assign_form').attr("action", "qc" + "-doHandover");
	$('#assign_form').submit();
}

var task = null;
//鼠标停留2秒，iframe加载投诉单详情
function startLoadComplaint(tabId, complaintId){
	task = setTimeout(function(){doLoadComplaint(tabId, complaintId)}, 2000);
}

function doLoadComplaint(tabId, complaintId){
	$('#complaint'+tabId+'_'+complaintId+'_frame').attr('src','complaint-toBill?id='+complaintId+'&viewType=qcView');
	$('div[name=complaint'+tabId+']').hide();$('#complaint'+tabId+'_'+complaintId).show();
}
//鼠标停留不足2秒，不加载iframe投诉单详情
function stopLoadComplaint(complaintId){
	clearTimeout(task);
}

function mark(complaintId) {
	var param = {"complaintId":complaintId};
	$.ajax({
		type: "POST",
		async:false,
		url: "qc-markOrder",
		data: param,
		success: function(data) {
			$('#search_form').attr("action", "qc");
			$('#search_form').submit();
	    }
   });
}

function cancelMark(complaintId){
	var param = {"complaintId":complaintId};
	$.ajax({
		type: "POST",
		async:false,
		url: "qc-cancelMarkOrder",
		data: param,
		success: function(data) {
			$('#search_form').attr("action", "qc");
			$('#search_form').submit();
	    }
   });
}

</script>
	
</head>
<body>
	<div class="top_crumbs">
		您当前所在的位置：<a href="#">投诉质检管理</a>&gt;&gt;<span class="top_crumbs_txt">质检投诉处理列表</span>
	</div>

	<!-- 查询表单 -->
	<form name="form" id="search_form" method="post"
		enctype="multipart/form-data" action="qc">
	<input type="hidden" name="status" id="status" value="${status}">
	<div id="pici_tab" class="clear">
		<ul>
			<li class="menu_on" onclick="onTabClicked('#001')" id="001_m"><span
				class="rc-l"></span><span class="rc-r"></span><a href="#">待处理</a></li>
			<li onclick="onTabClicked('#002')" id="002_m"><span class="rc-l"></span><span
				class="rc-r"></span><a href="#">处理中</a></li>
			<li onclick="onTabClicked('#003')" id="003_m"><span class="rc-l"></span><span
				class="rc-r"></span><a href="#">已处理</a></li>
			<li onclick="onTabClicked('#004')" id="004_m"><span class="rc-l"></span><span
				class="rc-r"></span><a href="#">咨询单</a></li>
		</ul>
	</div>

	<div class="pici_search pd5 mb10">
		<label class="mr10">订&nbsp;&nbsp;单&nbsp;&nbsp;号： 
			<input type="text" size="10" name="search.orderId" value="${search.orderId}" />
		</label> 
		 <label class="mr10">投诉单号： <input type="text" size="10" name="search.complaintId" value="${search.complaintId}" /> </label> 
		投诉来源：
		<select class="mr10" name="search.source">
			<option value="">全部</option>
			<option value="网站" <c:if test="${'网站'.equals(search.source)}">selected</c:if>>网站</option>
			<option value="门市" <c:if test="${'门市'.equals(search.source)}">selected</c:if>>门市</option>
			<option value="当地质检" <c:if test="${'当地质检'.equals(search.source)}">selected</c:if>>当地质检</option>
			<option value="来电投诉" <c:if test="${'来电投诉'.equals(search.source)}">selected</c:if>>来电投诉</option>
			<option value="CS邮箱 " <c:if test="${'CS邮箱 '.equals(search.source)}">selected</c:if>>CS邮箱</option>
			<option value="回访" <c:if test="${'回访'.equals(search.source)}">selected</c:if>>回访</option>
			<option value="点评" <c:if test="${'点评'.equals(search.source)}">selected</c:if>>点评</option>
			<option value="旅游局" <c:if test="${'旅游局'.equals(search.source)}">selected</c:if>>旅游局</option>
			<option value="微博" <c:if test="${'微博'.equals(search.source)}">selected</c:if>>微博</option>
			<option value="APP" <c:if test="${'APP'.equals(search.source)}">selected</c:if>>APP</option>
			<option value="其他" <c:if test="${'其他'.equals(search.source)}">selected</c:if>>其他</option>
		</select> 
		<label class="mr10">出发城市： 
			<input type="text" size="10" name="search.startCity" value="${search.startCity}"/>
		</label> 
		<label class="mr10">线路： 
			<input type="text" size="10" name="search.destination" value="${search.destination}"/>
		</label> 
		<label> 供应商： <input type="text" size="20" name="search.agencyName" value="${search.agencyName}"/>
		</label> 
		投诉等级：
		<select class="mr10" name="search.level">
			<option value="">全部</option>
			<option value="3" <c:if test="${search.level==3}">selected</c:if> >3级</option>
			<option value="2" <c:if test="${search.level==2}">selected</c:if> >2级</option>
			<option value="1" <c:if test="${search.level==1}">selected</c:if> >1级</option>
		</select>
		投诉状态：
		<select class="mr10" name="search.state">
			<option value="">全部</option>
			<option value="1" <c:if test="${search.state==1}">selected</c:if> >投诉待处理</option>
			<option value="2" <c:if test="${search.state==2}">selected</c:if> >投诉处理中</option>
			<option value="3" <c:if test="${search.state==3}">selected</c:if> >投诉已待结</option>
			<option value="4" <c:if test="${search.state==4}">selected</c:if> >投诉已完成</option>
			<option value="5" <c:if test="${search.state==5}">selected</c:if> >升级售后</option>
			<option value="6" <c:if test="${search.state==6}">selected</c:if> >提交售后填写分担方案</option>
			<option value="7" <c:if test="${search.state==7}">selected</c:if> >升级售前</option>
			<!--<option value="9" <c:if test="${search.state==9}">selected</c:if> >已撤销</option>-->
			<option value="10" <c:if test="${search.state==10}">selected</c:if> >升级客服中心售后</option>
		</select>
		<br>
		投诉时间： <input type="text" size="20" name="search.complaintStartTime" id="complaintStartTime" value="${search.complaintStartTime}" 
		             onclick="var complaintEndTime=$dp.$('complaintEndTime');WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,onpicked:function(){complaintEndTime.click();},maxDate:'#F{$dp.$D(\'complaintEndTime\')}'})" readonly="readonly" /> 至 
		      <input type="text" size="20" name="search.complaintEndTime" id="complaintEndTime" value="${search.complaintEndTime}" 
		             onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'complaintStartTime\')}'})" readonly="readonly" />　
		<c:if test="${!(status == 0)}">
		完成时间： <input type="text" size="20" name="search.closedStartTime" id="closedStartTime" value="${search.closedStartTime}" 
		             onclick="var closedEndTime=$dp.$('closedEndTime');WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,onpicked:function(){closedEndTime.click();},maxDate:'#F{$dp.$D(\'closedEndTime\')}'})" readonly="readonly" /> 至 
		      <input type="text" size="20" name="search.closedEndTime" id="closedEndTime" value="${search.closedEndTime}" 
		             onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'closedStartTime\')}'})" readonly="readonly" />　
		</c:if>
		<label class="mr10">处理人： <input type="text" size="10" name="search.assignee" value="${search.assignee}"/>
		</label> 
		<label class="mr10">负责人： <input type="text" size="10" name="search.leader" value="${search.leader}"/>
		</label><br>
		质检人：
		<select class="mr10" name="search.qcPersonName">
						<option value="0">全部</option>
						<c:forEach items="${sameGroupUsers}" var="userItem">							
							<option value="${userItem.id}" <c:if test="${search.qcPersonName==userItem.id}">selected</c:if> >${userItem.realName}</option>
						</c:forEach>
		</select> 
		<label class="mr10">售前客服： <input type="text" size="10" name="search.preSales" value="${search.preSales}"/></label> 
		<label class="mr10">客服经理： <input type="text" size="10" name="search.salesLeader" value="${search.salesLeader}"/></label> 
		<label class="mr10">产品专员： <input type="text" size="10" name="search.producter" value="${search.producter}"/></label> 
		<label class="mr10">产品经理： <input type="text" size="10" name="search.productManager" value="${search.productManager}"/></label>
		<label class="mr10">一级部门：<s:select name="search.bdpName" list="bdpNames"  headerKey="" headerValue="请选择" /></label>
		归来时间： <input type="text" size="20" name="search.backTimeStart" id="backTimeStart" value="${search.backTimeStart}" 
		             onclick="var backTimeEnd=$dp.$('backTimeEnd');WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,onpicked:function(){backTimeEnd.click();},maxDate:'#F{$dp.$D(\'backTimeEnd\')}'})" readonly="readonly" /> 至 
		      <input type="text" size="20" name="search.backTimeEnd" id="backTimeEnd" value="${search.backTimeEnd}" 
		             onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'backTimeStart\')}'})" readonly="readonly" />　
		<br>
                       投诉事宜：
		<select class="mr10" name="search.reason" >
			<option value="">全部</option>
			<option value="住宿" <c:if test="${'住宿'.equals(search.reason)}">selected</c:if>>住宿</option>
			<option value="餐饮" <c:if test="${'餐饮'.equals(search.reason)}">selected</c:if>>餐饮</option>
			<option value="导游" <c:if test="${'导游'.equals(search.reason)}">selected</c:if>>导游</option>
			<option value="交通" <c:if test="${'交通'.equals(search.reason)}">selected</c:if>>交通</option>
			<option value="购物" <c:if test="${'购物'.equals(search.reason)}">selected</c:if>>购物</option>
			<option value="行程安排" <c:if test="${'行程安排'.equals(search.reason)}">selected</c:if>>行程安排</option>
			<option value="产品" <c:if test="${'产品'.equals(search.reason)}">selected</c:if>>产品</option>
			<option value="产品人员" <c:if test="${'产品人员'.equals(search.reason)}">selected</c:if>>产品人员</option>
			<option value="呼入" <c:if test="${'呼入'.equals(search.reason)}">selected</c:if>>呼入</option>
			<option value="售前客服" <c:if test="${'售前客服'.equals(search.reason)}">selected</c:if>>售前客服</option>
			<option value="售中客服" <c:if test="${'售中客服'.equals(search.reason)}">selected</c:if>>售中客服</option>
			<option value="售后客服" <c:if test="${'售后客服'.equals(search.reason)}">selected</c:if>>售后客服</option>
			<option value="门市服务人员" <c:if test="${'门市服务人员'.equals(search.reason)}">selected</c:if>>门市服务人员</option>
			<option value="上门签约" <c:if test="${'上门签约'.equals(search.reason)}">selected</c:if>>上门签约</option>
			<option value="在线预订" <c:if test="${'在线预订'.equals(search.reason)}">selected</c:if>>在线预订</option>
			<option value="电话预订" <c:if test="${'电话预订'.equals(search.reason)}">selected</c:if>>电话预订</option>
			<option value="网上浏览" <c:if test="${'网上浏览'.equals(search.reason)}">selected</c:if>>网上浏览</option>
			<option value="门票" <c:if test="${search.reason=='门票'}">selected</c:if>>门票</option>
			<option value="导游/领队" <c:if test="${search.reason=='导游/领队'}">selected</c:if>>导游/领队</option>
			<option value="客户反馈" <c:if test="${search.reason=='客户反馈'}">selected</c:if>>客户反馈</option>
			<option value="服务态度" <c:if test="${search.reason=='服务态度'}">selected</c:if>>服务态度</option>
			<option value="点评" <c:if test="${search.reason=='点评'}">selected</c:if>>点评</option>
			<option value="其他" <c:if test="${search.reason=='其他'}">selected</c:if>>其他</option> 
		</select> 
		<input type="button" value="查询" class="blue" onclick="onSearchClicked();" />
		<input type="button" value="重置" class="blue" onclick="onResetClicked();" />
		<%-- <c:if test="${status == 2}"> --%>
		<!--<input type="button" value="导出" class="blue" onclick="onSearchExport();" id="exportButton"/>
		--><%--</c:if>--%>
	</div>
	</form>
	
	<form name="form" id="assign_form" method="post" enctype="multipart/form-data">
	<input type="hidden" name="status" id="status" value="${status}">
	<input type="hidden" name="qid" id="qid" > <!-- 认领任务id -->
	<input type="hidden" name="complaintId" id="complaintId" > <!-- 投诉id -->
	<input type="hidden" name="username" id="username"/> <!-- 被分配人,或者交接人 -->
	<input type="hidden" name="assignFlag" id="assignFlag" value=""/> <!-- 被分配人,或者交接人 -->
	<input type="hidden" name="curUrl" id="curUrl" value="${pager.url}${pager.currPage}&${pager.param}"/>
	<div id="001" class="tab_part">
		<!--1 待处理-->
		<c:if test="${isQcOfficer == true}"> <!-- 主管才有分配权限 -->
			<div class="notice mb10">
				<p class="pd5">
					<select name="batchAssignSel" id="batchAssignSel">
						<option value="0">分配处理人</option>
						<c:forEach items="${sameGroupUsers}" var="userItem">							
							<option value="${userItem.id}">${userItem.realName}</option>
						</c:forEach>
					</select> 
					
					<input class="mr20" type="button" name="batchAssign" id="button" value="分配" onclick="onBatchAssignClicked(0);"/>
				</p>
			</div>
		</c:if>
		
		<table width="100%" class="listtable mb10">
			<tr>
				<th><input type="checkbox" id="chkPreProcess"/></th>
				<th>投诉号</th>
				<th>订单号</th>
				<th>客人姓名</th>
				<th>出游人数</th>
				<th>出发地</th>
				<th>线路</th>
				<th>出发时间</th>
				<th>归来时间</th>
				<th>投诉时间</th>
				<th>客服经理</th>
				<th>产品经理</th>
				<th>一级部门</th>
				<th>投诉处理状态</th>
				<th>对客赔偿金额</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${dataList}" var="item" varStatus="vs">
				<tr align="center">
					<td onmouseover="$('div[name=complaint]').hide();"><input type="checkbox" name="ids" value="${item.id}" /></td>
					<!-- 投诉单号 -->
					<td onmouseover="startLoadComplaint('', ${item.complaintId})" onmouseout="stopLoadComplaint()">
						<c:choose><c:when test="${item.impFlag==1}"><font color="red" >★ </font></c:when><c:otherwise><c:if test="${item.checkFlag==1}"><font color="red" >▲</font></c:if></c:otherwise></c:choose><a href="complaint-toBill?id=${item.complaintId}" target="_blank">${item.complaintId}</a>
						<div style=" background: none repeat scroll 0% 0% rgb(255, 255, 255); position: absolute; left:8%; z-index: 1000; display: none;" id="complaint_${item.complaintId}" name="complaint" onmouseout="$('#complaint_${item.complaintId}').hide();">
							<iframe src="" width="1000" height="600" id="complaint_${item.complaintId}_frame"></iframe>
						</div>
					</td>
					<!-- 订单号 -->
					<td>
					<c:if test="${item.orderId > 0}">
					<a href="#" onclick="showOrder(${user.id},'${user.realName}',${item.orderId})">${item.orderId}</a>
					</c:if>
					</td>
					<!-- 客人姓名 -->
					<td>${item.complaint.guestName}</td>
					<!-- 出游人数 -->
					<td>${item.complaint.guestNum}</td>
					<!-- 出发地/线路 -->
					<td>${item.complaint.startCity}</td>
					<td align="left" title="${item.complaint.route }">
					    <c:choose>
							<c:when test="${fn:length(item.complaint.route) > 12}">
								<c:out value="${fn:substring(item.complaint.route, 0, 10)}......" />
							</c:when>
							<c:otherwise>
								<c:out value="${item.complaint.route}" />
							</c:otherwise>
						</c:choose>
					</td>
					<!-- 出发时间 -->
					<td><fmt:formatDate value="${item.complaint.startTime}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<!-- 出发时间 -->
					<td><fmt:formatDate value="${item.complaint.backTime}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<!-- 投诉时间 -->
					<td><fmt:formatDate value="${item.complaint.buildDate}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<!-- 客服经理 -->
					<td>${item.complaint.customerLeader}</td>
					<!-- 产品经理 -->
					<td>${item.complaint.productLeader}</td>
					<!-- 一级部门 -->
					<td>${item.complaint.bdpName }</td>
					<!-- 投诉处理状态 -->
					<td>
						<c:if test="${item.complaint.state==1}">投诉待处理</c:if>
						<c:if test="${item.complaint.state==2}">投诉处理中</c:if>
						<c:if test="${item.complaint.state==3}">投诉已待结</c:if>
						<c:if test="${item.complaint.state==4}">投诉已完成</c:if>
						<c:if test="${item.complaint.state==5}">投诉待指定（升级售后）</c:if>
						<c:if test="${item.complaint.state==6}">投诉待指定（提交售后填写分担方案）</c:if>
						<c:if test="${item.complaint.state==7}">投诉待指定（升级售前）</c:if>
						<c:if test="${item.complaint.state==9}">已撤销</c:if>
						<c:if test="${item.complaint.state==10}">投诉待指定（升级客服中心售后）</c:if>
					</td>
					<!-- 对客赔偿金额 -->
					<td>${item.paymentForCust }</td>
					<!-- 操作 -->
					<td>
						<!-- 主管可以分配人，基础员工认领 -->
						<c:choose> 							
							<c:when test="${isQcOfficer == false}">
								<input type="submit" name="claim" value="认领" onclick="onClaimClicked(${item.id});"/>
							</c:when>
							<c:otherwise>
								<select name="assignSel${item.id}" id="assignSel${item.id}" onchange="changesel(this.value,${item.id},0)">
						          	<option value="0">分配处理人</option>
						          	<c:forEach items="${sameGroupUsers}" var="userItem">							
										<option value="${userItem.id}">${userItem.realName}</option>
									</c:forEach>
						        </select>
						        <input type="hidden" name="assignSelValue${item.id}" id="assignSelValue${item.id}" value="" />
						        <input type="button" name="assign" value="分配" onclick="onAssignClicked(${item.id},0);"/>
							</c:otherwise>
						</c:choose>
					</td>
			</c:forEach>
		</table>
	</div>

	<!--2 处理中 -->
	<div id="002" style="display: none;" class="tab_part">
		<div class="notice mb10">
			<p class="pd5">
				<select name="handoverSel" id="handoverSel">
					<option value="0">交接人</option>
					<c:forEach items="${sameGroupUsers}" var="userItem">							
						<option value="${userItem.id}">${userItem.realName}</option>
					</c:forEach>					
				</select> 
				<input class="mr20" type="submit" value="工作交接" onclick="onHandoverClicked();"/>
				<c:if test="${isQcOfficer == true}"> <!-- 主管才有分配权限 -->
					<select name="batch2AssignSel" id="batch2AssignSel">
						<option value="0">分配处理人</option>
						<c:forEach items="${sameGroupUsers}" var="userItem">							
							<option value="${userItem.id}">${userItem.realName}</option>
						</c:forEach>
					</select>
					<input class="mr20" type="button" name="batchAssign" id="button" value="分配" onclick="onBatchAssignClicked(1);"/>
				</c:if>
			</p>
		</div>
		<table width="100%" class="listtable mb10">
			<tr>
				<th><input type="checkbox" id="chkProcessing"/></th>
				<th>投诉号</th>
				<th>订单号</th>
				<th>客人姓名</th>
				<th>出游人数</th>
				<th>出发地</th>
				<th>线路</th>
				<th>出发时间</th>
				<th>归来时间</th>
				<th>投诉时间</th>
				<th>客服经理</th>
				<th>产品经理</th>
				<th>一级部门</th>
				<th>投诉处理状态</th>
				<th>对客赔偿金额</th>
				<th>质检人</th>
				<c:if test="${isQcOfficer == true}">
				<th>重新分配</th>
				</c:if>
				<th>操作</th>
				<th>质检报告</th>
			</tr>
			<c:forEach items="${qe}" var="item" varStatus="vs">
				<c:if test="${item.red_color_flag == 1}">
					<tr align="center" class="redbg">
				</c:if>
				<c:if test="${item.red_color_flag == 0}">
					<tr align="center">
				</c:if>
					<td onmouseover="$('div[name=complaint2]').hide();"><input type="checkbox" name="ids" value="${item.id}" /></td>
					<!-- 投诉单号 -->
					<td onmouseover="startLoadComplaint(2, ${item.complaintId})" onmouseout="stopLoadComplaint()">
						<c:choose><c:when test="${item.impFlag==1}"><font color="red" >★ </font></c:when><c:otherwise><c:if test="${item.checkFlag==1}"><font color="red" >▲</font></c:if></c:otherwise></c:choose> <a href="complaint-toBill?id=${item.complaintId}" target="_blank" >${item.complaintId}</a>
						<div style=" background: none repeat scroll 0% 0% rgb(255, 255, 255); position: absolute; left:8%; z-index: 1000; display: none;" id="complaint2_${item.complaintId}" name="complaint2" onmouseout="$('#complaint2_${item.complaintId}').hide();">
							<iframe src="" width="1000" height="600" id="complaint2_${item.complaintId}_frame"></iframe>
						</div>
					</td>
					<!-- 订单号 -->
					<td>
					<c:if test="${item.orderId > 0}">
					
						<a href="#" onclick="showOrder(${user.id},'${user.realName}',${item.orderId})">${item.orderId}</a>
					</c:if>
					</td>
					<!-- 客人姓名 -->
					<td>${item.complaint.guestName}</td>
					<!-- 出游人数 -->
					<td>${item.complaint.guestNum}</td>
					<!-- 出发地/线路 -->
					<td>${item.complaint.startCity}</td>
					<td align="left" title="${item.complaint.route }">
					    <c:choose>
							<c:when test="${fn:length(item.complaint.route) > 12}">
								<c:out value="${fn:substring(item.complaint.route, 0, 10)}......" />
							</c:when>
							<c:otherwise>
								<c:out value="${item.complaint.route}" />
							</c:otherwise>
						</c:choose>
					</td>
					<!-- 出发时间 -->
					<td><fmt:formatDate value="${item.complaint.startTime}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td><fmt:formatDate value="${item.complaint.backTime}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<!-- 投诉时间 -->
					<td><fmt:formatDate value="${item.complaint.buildDate}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<!-- 客服经理 -->
					<td>${item.complaint.customerLeader}</td>
					<!-- 产品经理 -->
					<td>${item.complaint.productLeader}</td>
					<!-- 一级部门 -->
					<td>${item.complaint.bdpName }</td>
					<!-- 投诉处理状态 -->
					<td>
						<c:if test="${item.complaint.state==1}">投诉待处理</c:if>
						<c:if test="${item.complaint.state==2}">投诉处理中</c:if>
						<c:if test="${item.complaint.state==3}">投诉已待结</c:if>
						<c:if test="${item.complaint.state==4}">投诉已完成</c:if>
						<c:if test="${item.complaint.state==5}">投诉待指定（升级售后）</c:if>
						<c:if test="${item.complaint.state==6}">投诉待指定（提交售后填写分担方案）</c:if>
						<c:if test="${item.complaint.state==7}">投诉待指定（升级售前）</c:if>
						<c:if test="${item.complaint.state==9}">已撤销</c:if>
						<c:if test="${item.complaint.state==10}">投诉待指定（升级客服中心售后）</c:if>
					</td>
					<!-- 对客赔偿金额 -->
					<td>${item.paymentForCust }</td>
					<!-- 质检人 -->
					<td>${item.qcPersonName}</td>
					<!-- 重新分配 -->
					<c:if test="${isQcOfficer == true}">
					<td>
						<select name="assign2Sel${item.id}" id="assign2Sel${item.id}" onchange="changesel(this.value,${item.id},1)">
						    <option value="0">分配处理人</option>
						    <c:forEach items="${sameGroupUsers}" var="userItem">							
								<option value="${userItem.id}">${userItem.realName}</option>
							</c:forEach>
						</select>
						<input type="hidden" name="assignSelValue${item.id}" id="assignSelValue${item.id}" value="" />
						<input type="button" name="assign" value="重新分配" onclick="onAssignClicked(${item.id},1);"/>
					</td>
					</c:if>
					<!-- 操作 -->
					<td>
						<input title="填写质检报告" class="thickbox" alt='qc-update?id=${item.id}&curUrl=<%OgnlValueStack stack = (OgnlValueStack) request.getAttribute("struts.valueStack"); %><%=URLEncoder.encode(stack.findValue("pager.url").toString()+stack.findValue("pager.currPage").toString() +"&"+(String)stack.findValue("pager.param").toString() ,"utf-8")%>&refill=0&TB_iframe=true&height=680&width=1050&modal=false' type="submit" value="填写质检报告" />
						<c:if test="${item.impFlag!=1}">
							<input id="btnMark" title="标记重要订单" type="button" alt="" value="标记重要订单" onclick="mark('${item.complaintId}')" />
						</c:if>
						<c:if test="${item.impFlag==1}">
							<input id="btnCancelMark" title="取消标记订单" type="button" alt="" value="取消标记订单" onclick="cancelMark('${item.complaintId}')" />
						</c:if>
					</td>
					<!-- 质检报告 -->
					<td  onmouseover="$('div[name=show2]').hide();$('#show2_${item.id}').show();">
						<!-- <a class="thickbox" href="qc-view?id=${item.id}&TB_iframe=true&height=480&width=840&modal=false">查看</a>&nbsp; -->
						<a href="#">查看</a>&nbsp;
							<div id="show2_${item.id}" name="show2" onmouseout="$('#show2_${item.id}').hide();"  style=" background: none repeat scroll 0% 0% rgb(255, 255, 255); position: absolute; right: 60px; z-index: 1000; display: none;">
								<table>
									<tr>
										<td>序号</td>
										<td>订单号</td>
										<td>报告号</td>
										<td>投诉号</td>
										<td>更新时间</td>
										<td>投诉状态</td>
										<td>操作</td>
									</tr>
									<c:if test="${item.haveReport == 0}"><tr><td colspan="7" align="center">暂无报告</td></tr></c:if>
									<c:forEach items="${qe}" var="item2" varStatus="vs2">
										<c:if test="${item2.complaintId == item.complaintId}">
											<c:forEach items="${item2.qcReports}" var="item3" varStatus="vs3">
												<c:if test="${item.id == item3.qcId}">
													<tr>
														<td>${vs3.count}</td>
														<td>${item.orderId}</td>
														<td>${item3.id}</td>
														<td>${item2.complaintId}</td>
														<td><fmt:formatDate value="${item3.updateTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
														<td>
															<c:if test="${item.complaint.state==1}">投诉待处理</c:if>
															<c:if test="${item.complaint.state==2}">投诉处理中</c:if>
															<c:if test="${item.complaint.state==3}">投诉已待结</c:if>
															<c:if test="${item.complaint.state==4}">投诉已完成</c:if>
															<c:if test="${item.complaint.state==5}">投诉待指定（升级售后）</c:if>
															<c:if test="${item.complaint.state==6}">投诉待指定（提交售后填写分担方案）</c:if>
															<c:if test="${item.complaint.state==7}">投诉待指定（升级售前）</c:if>
															<c:if test="${item.complaint.state==9}">已撤销</c:if>
															<c:if test="${item.complaint.state==10}">投诉待指定（升级客服中心售后）</c:if>
														</td>
														<td>
															<a class="thickbox" href="qc-view?id=${item2.id}&indexId=${vs3.count}&TB_iframe=true&height=480&width=840&modal=false" onclick="$('#show2${item.id}').toggle();">查看</a>&nbsp;
															<a href="qc-download?id=${item2.id}&indexId=${vs3.count}">下载</a>
														</td>
													</tr>
												</c:if>
											</c:forEach>
										</c:if>
									</c:forEach>
								</table>
							</div>
						<!-- <a href="#">下载</a> -->
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>

	<!--3 已处理-->
	<div id="003" style="display: none;" class="tab_part">
		<table width="100%" class="listtable mb10">
			<tr>
				<th>投诉号</th>
				<th>订单号</th>
				<th>客人姓名</th>
				<th>出游人数</th>
				<th>出发地</th>
				<th>线路</th>
				<th>出发时间</th>
				<th>归来时间</th>
				<th>投诉时间</th>
				<th>客服经理</th>
				<th>产品经理</th>
				<th>一级部门</th>
				<th>投诉处理状态</th>
				<th>对客赔偿金额</th>
				<th>质检人</th>
				<th>完成时间</th>
				<th>操作</th>
				<th>质检报告</th>
			</tr>
			<c:forEach items="${dataList}" var="item" varStatus="st">
				<tr align="center">
					<!-- 投诉单号 -->
					<td onmouseover="startLoadComplaint(3, ${item.complaintId})" onmouseout="stopLoadComplaint()">
						<c:choose><c:when test="${item.impFlag==1}"><font color="red" >★ </font></c:when><c:otherwise><c:if test="${item.checkFlag==1}"><font color="red" >▲</font></c:if></c:otherwise></c:choose><a href="complaint-toBill?id=${item.complaintId}" target="_blank">${item.complaintId}</a>
						<div style=" background: none repeat scroll 0% 0% rgb(255, 255, 255); position: absolute; left:8%; z-index: 1000; display: none;" id="complaint3_${item.complaintId}" name="complaint3" onmouseout="$('#complaint3_${item.complaintId}').hide();">
							<iframe src="" width="1000" height="600" id="complaint3_${item.complaintId}_frame"></iframe>
						</div>
					</td>
					<!-- 订单号 -->
					<td>
					<c:if test="${item.orderId > 0}">
					<a href="#" onclick="showOrder(${user.id},'${user.realName}',${item.orderId})">${item.orderId}</a>
					</c:if>
					</td>
					<!-- 客人姓名 -->
					<td>${item.complaint.guestName}</td>
					<!-- 出游人数 -->
					<td>${item.complaint.guestNum}</td>
					<!-- 出发地/线路 -->
					<td>${item.complaint.startCity}</td>
					<td align="left" title="${item.complaint.route }">
					    <c:choose>
							<c:when test="${fn:length(item.complaint.route) > 12}">
								<c:out value="${fn:substring(item.complaint.route, 0, 10)}......" />
							</c:when>
							<c:otherwise>
								<c:out value="${item.complaint.route}" />
							</c:otherwise>
						</c:choose>
					</td>
					<!-- 出发时间 -->
					<td><fmt:formatDate value="${item.complaint.startTime}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td><fmt:formatDate value="${item.complaint.backTime}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<!-- 投诉时间 -->
					<td><fmt:formatDate value="${item.complaint.buildDate}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<!-- 客服经理 -->
					<td>${item.complaint.customerLeader}</td>
					<!-- 产品经理 -->
					<td>${item.complaint.productLeader}</td>
					<!-- 一级部门 -->
					<td>${item.complaint.bdpName }</td>
					<!-- 投诉处理状态 -->
					<td>
						<c:if test="${item.complaint.state==1}">投诉待处理</c:if>
						<c:if test="${item.complaint.state==2}">投诉处理中</c:if>
						<c:if test="${item.complaint.state==3}">投诉已待结</c:if>
						<c:if test="${item.complaint.state==4}">投诉已完成</c:if>
						<c:if test="${item.complaint.state==5}">投诉待指定（升级售后）</c:if>
						<c:if test="${item.complaint.state==6}">投诉待指定（提交售后填写分担方案）</c:if>
						<c:if test="${item.complaint.state==7}">投诉待指定（升级售前）</c:if>
						<c:if test="${item.complaint.state==9}">已撤销</c:if>
						<c:if test="${item.complaint.state==10}">投诉待指定（升级客服中心售后）</c:if>
					</td>
					<!-- 对客赔偿金额 -->
					<td>${item.paymentForCust }</td>
					<!-- 质检人 -->
					<td>${item.qcPersonName}</td>
					<!-- 完成时间 -->
					<td><fmt:formatDate value="${item.finishDate}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<!-- 操作 -->
					<td><input title="重新填写质检报告" class="thickbox"
						alt="qc-update?id=${item.id}&refill=1&TB_iframe=true&height=680&width=1050&modal=false"
						type="button" name="button6" id="button5" value="重填质检报告"/></td>
					<!-- 质检报告 -->
					<td onmouseover="$('div[name=show]').hide();$('#show_${item.id}').show();">
						<!-- <a class="thickbox" href="qc-view?id=${item.id}&TB_iframe=true&height=480&width=840&modal=false">查看</a>&nbsp; -->
						<a href="#">查看</a>&nbsp;
							<div id="show_${item.id}" name="show" onmouseout="$('#show_${item.id}').hide();" style=" background: none repeat scroll 0% 0% rgb(255, 255, 255); position: absolute; right: 60px; z-index: 1000; display: none;">
								<table>
									<tr>
										<td>序号</td>
										<td>订单号</td>
										<td>报告号</td>
										<td>投诉号</td>
										<td>更新时间</td>
										<td>投诉状态</td>
										<td>操作</td>
									</tr>
									<c:forEach items="${qe}" var="item2" varStatus="st2">
										<c:if test="${item2.complaintId == item.complaintId}">
											<c:forEach items="${item2.qcReports}" var="item3" varStatus="st3">
												<c:if test="${item.id == item3.qcId}">
													<tr>
														<td>${st3.count}</td>
														<td>${item.orderId}</td>
														<td>${item3.id}</td>
														<td>${item2.complaintId}</td>
														<td><fmt:formatDate value="${item3.updateTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
														<td>
															<c:if test="${item.complaint.state==1}">投诉待处理</c:if>
															<c:if test="${item.complaint.state==2}">投诉处理中</c:if>
															<c:if test="${item.complaint.state==3}">投诉已待结</c:if>
															<c:if test="${item.complaint.state==4}">投诉已完成</c:if>
															<c:if test="${item.complaint.state==5}">投诉待指定（升级售后）</c:if>
															<c:if test="${item.complaint.state==6}">投诉待指定（提交售后填写分担方案）</c:if>
															<c:if test="${item.complaint.state==7}">投诉待指定（升级售前）</c:if>
															<c:if test="${item.complaint.state==9}">已撤销</c:if>
															<c:if test="${item.complaint.state==10}">投诉待指定（升级客服中心售后）</c:if>
														</td>
														<td>
															<a class="thickbox" href="qc-view?id=${item2.id}&indexId=${st3.count}&TB_iframe=true&height=480&width=840&modal=false" onclick="$('#show${item.id}').toggle();">查看</a>&nbsp;
															<a href="qc-download?id=${item2.id}&indexId=${st3.count}">下载</a>
														</td>
													</tr>
												</c:if>
											</c:forEach>
										</c:if>
									</c:forEach>
								</table>
							</div>
						<!-- <a href="qc-download?id=${item.id}">下载</a> -->
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	
	<div id="004"  style="display: none;" class="tab_part">
		<!--1 待处理-->
		<c:if test="${isQcOfficer == true}"> <!-- 主管才有分配权限 -->
			<div class="notice mb10">
				<p class="pd5">
					
					<input class="mr20" type="button" name="specialConsultation" id="button" value="返回待处理" onclick="toSpecialConsultation()"/>
				</p>
			</div>
		</c:if>
		
		<table width="100%" class="listtable mb10">
			<tr>
				<th><input type="checkbox" id="chkPreProcess"/></th>
				<th>投诉号</th>
				<th>订单号</th>
				<th>客人姓名</th>
				<th>出游人数</th>
				<th>出发地</th>
				<th>线路</th>
				<th>出发时间</th>
				<th>归来时间</th>
				<th>投诉时间</th>
				<th>客服经理</th>
				<th>产品经理</th>
				<th>一级部门</th>
				<th>投诉处理状态</th>
				<th>对客赔偿金额</th>
			</tr>
			<c:forEach items="${dataList}" var="item" varStatus="vs">
				<tr align="center">
					<td onmouseover="$('div[name=complaint]').hide();"><input type="checkbox" name="ids" value="${item.id}" /></td>
					<!-- 投诉单号 -->
					<td onmouseover="startLoadComplaint('', ${item.complaintId})" onmouseout="stopLoadComplaint()">
						<c:choose><c:when test="${item.impFlag==1}"><font color="red" >★ </font></c:when><c:otherwise><c:if test="${item.checkFlag==1}"><font color="red" >▲</font></c:if></c:otherwise></c:choose><a href="complaint-toBill?id=${item.complaintId}" target="_blank">${item.complaintId}</a>
						<div style=" background: none repeat scroll 0% 0% rgb(255, 255, 255); position: absolute; left:8%; z-index: 1000; display: none;" id="complaint_${item.complaintId}" name="complaint" onmouseout="$('#complaint_${item.complaintId}').hide();">
							<iframe src="" width="1000" height="600" id="complaint_${item.complaintId}_frame"></iframe>
						</div>
					</td>
					<!-- 订单号 -->
					<td>
					<c:if test="${item.orderId > 0}">
					<a href="#" onclick="showOrder(${user.id},'${user.realName}',${item.orderId})">${item.orderId}</a>
					</c:if>
					</td>
					<!-- 客人姓名 -->
					<td>${item.complaint.guestName}</td>
					<!-- 出游人数 -->
					<td>${item.complaint.guestNum}</td>
					<!-- 出发地/线路 -->
					<td>${item.complaint.startCity}</td>
					<td align="left" title="${item.complaint.route }">
					    <c:choose>
							<c:when test="${fn:length(item.complaint.route) > 12}">
								<c:out value="${fn:substring(item.complaint.route, 0, 10)}......" />
							</c:when>
							<c:otherwise>
								<c:out value="${item.complaint.route}" />
							</c:otherwise>
						</c:choose>
					</td>
					<!-- 出发时间 -->
					<td><fmt:formatDate value="${item.complaint.startTime}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<!-- 出发时间 -->
					<td><fmt:formatDate value="${item.complaint.backTime}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<!-- 投诉时间 -->
					<td><fmt:formatDate value="${item.complaint.buildDate}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<!-- 客服经理 -->
					<td>${item.complaint.customerLeader}</td>
					<!-- 产品经理 -->
					<td>${item.complaint.productLeader}</td>
					<!-- 一级部门 -->
					<td>${item.complaint.bdpName }</td>
					<!-- 投诉处理状态 -->
					<td>
						<c:if test="${item.complaint.state==1}">投诉待处理</c:if>
						<c:if test="${item.complaint.state==2}">投诉处理中</c:if>
						<c:if test="${item.complaint.state==3}">投诉已待结</c:if>
						<c:if test="${item.complaint.state==4}">投诉已完成</c:if>
						<c:if test="${item.complaint.state==5}">投诉待指定（升级售后）</c:if>
						<c:if test="${item.complaint.state==6}">投诉待指定（提交售后填写分担方案）</c:if>
						<c:if test="${item.complaint.state==7}">投诉待指定（升级售前）</c:if>
						<c:if test="${item.complaint.state==9}">已撤销</c:if>
						<c:if test="${item.complaint.state==10}">投诉待指定（升级客服中心售后）</c:if>
					</td>
					<!-- 对客赔偿金额 -->
					<td>${item.paymentForCust }</td>
			</c:forEach>
		</table>
	</div>

	</form>

<!-- 	
	<div class="nextprepage clear rf">
		<ul>
			<span class="page_wd">共1030条记录，当前第1-25条</span>
			<li><a href="#">1</a></li>
			<li><a href="#">2</a></li>
			<li><a href="#">3</a></li>
			<li><a href="#">4</a></li>
			<li><a href="#">下一页&gt;</a></li>
		</ul>
	</div>
-->
<%@ include file="/WEB-INF/html/pager.jsp"%>
</body>
</html>
