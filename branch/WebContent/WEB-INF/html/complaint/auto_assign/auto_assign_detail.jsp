<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<link href="${CONFIG.res_url}css/thickbox.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${CONFIG.res_url}script/thickbox-compressed.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}script/complaint/complaint/appoint_manager.js" ></script> 
<script type="text/javascript" src="${CONFIG.res_url}script/DatePickerNew/WdatePicker.js" ></script>
<script type="text/javascript">
function tabFuncdd(showId,navObj){ 
	$(".tab_part").hide();
	$("#pici_tab .menu_on").removeClass("menu_on");
	$(navObj).addClass("menu_on");
	$(showId).show();
	return false;
}

//thinkbox弹出框控制js
$(function(){
	/*移除thickbox默认样式*/
	$('.thickbox').click(function(){
		$('#TB_closeAjaxWindow').html("<a href='javascript:void(0)' id='TB_closeWindowButton' title='Close'>×</a>");
		$('#TB_closeWindowButton').live('click',function(){
			tb_remove();
		});
	});
});
</script>
<style type="text/css">
	.datatable td table{border-collapse:collapse;}
	.datatable td td{border:0 none;word-wrap:break-word;word-break:break-all;}
	.trbg td{background:#ff9;}
		.listtable tr.yellowbg td{background:#FFFF99}
</style>
</HEAD>
<BODY>
<div id="pici_tab" class="clear">
	<ul>
		<li onclick="tabFuncdd('#001',this)">
		<s class="rc-l"></s><s class="rc-r"></s><a href="auto_assign">自动分单配置</a>
		</li>
		<li  class="menu_on"  onclick="tabFuncdd('#002',this)"><s class="rc-l"></s>
		<s class="rc-r"></s><a href="">当前分单统计</a>
		</li>
	</ul>
</div>
<div id="001" class="tab_part">
	<form name="form" id="form" method="post" enctype="multipart/form-data" action="auto_assign-queryDetail">
		<table width="100%" class="datatable">
		    <c:if test="${isBefore || isMiddle || isAfter || isAir || isHotel || isDev || isComp || isTraffic || isVipDepart}">
		    <tr>
   				<td><span class="cred">投诉分单统计</span></td>
   				<td>
   				<input type="text" size="12" name="statDate" id="stat_date"	value="${statDate }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readOnly="readonly"/>&nbsp;&nbsp;
   				<input type="submit" value="查询" class="blue"/>
   				</td>
		    </tr>
		    <tr>
		      <td colspan="2">
		   	   <table class="listtable" width="100%">
				   <thead>
					 <th style="width:7%">时间节点</th>
					 <th style="width:7%">人员</th>
					 <th style="width:16%">部门</th>
					 <th style="width:60%">当天已分配的投诉单</th>
					 <th style="width:10%">当日分单量</th>
					</thead>
					<tbody>
					<c:forEach items="${complaintList}" var="v"  varStatus="st"> 
					  <tr>
					  <td>
					  	<c:if test="${v.tourTimeNode == 1 }">出游前客户服务</c:if>
						<c:if test="${v.tourTimeNode == 2 }">售后组</c:if>
						<c:if test="${v.tourTimeNode == 3 }">资深组</c:if>
						<c:if test="${v.tourTimeNode == 4 }">机票组</c:if>
						<c:if test="${v.tourTimeNode == 5 }">酒店组</c:if>
						<c:if test="${v.tourTimeNode == 6 }">交通组</c:if>
						<c:if test="${v.tourTimeNode == 8 }">会员顾问组</c:if>
					  </td>
					  <td>
					  	<c:if test="${v.readyFlag == 0}">${v.userName}</c:if>
					  	<c:if test="${v.readyFlag == 1}"><span style="color: green;">${v.userName}</span></c:if>
					  </td>
					  <td>${v.departmentName}</td>
					  <td>${v.orderIds}</td>
					  <td>${v.listNums}</td>
					  </tr>
					</c:forEach>
					</tbody>
		       </table>
		       </td>
		    </tr>
			</c:if>
               <c:if test="${isQc || isDev}">
		    <tr>
     			<td colspan="2"> <span class="cred">质检分单统计</span></td>
		    </tr>
			<tr>
		      <td colspan="2">
		   	   <table class="listtable" width="100%">
				   <thead>
					 <th style="width:10%">人员</th>
					 <th style="width:10%">部门</th>
					 <th style="width:70%">当天已分配的质检单</th>
					 <th style="width:10%">当日分单量</th>
					</thead>
					<tbody>
					<c:forEach items="${qcList}" var="v"  varStatus="st"> 
					  <tr>
					  <td>${v.userName}</td>
					  <td>${v.departmentName}</td>
					  <td>${v.orderIds}</td>
					  <td align="center">${v.listNums}</td>
					  </tr>
					</c:forEach>
					</tbody>
		       </table>
		       </td>
		    </tr>
			</c:if>
		</table>
	</form>
</div>
<div id="002" class="tab_part">
</div>
</BODY>
