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

function onSearchClicked() {
	//$('#complaint_form').attr("action", "${manageUrl}");
	$('#satisfaction_form').attr("action", "signSatisfaction");
	$('#satisfaction_form').submit();
}

function onSearchExport() { 
	//var baseUrl = "${manageUrl}"; 
	//$('#complaint_form').attr("action", baseUrl + "-exports");
	$('#satisfaction_form').attr("action", "signSatisfaction-exports");
	$('#satisfaction_form').submit();
}

function onSearchExportToday() { 
	//var baseUrl = "${manageUrl}"; 
	//$('#complaint_form').attr("action", baseUrl + "-exports");
	$('#satisfaction_form').attr("action", "signSatisfaction-exportsToday");
	$('#satisfaction_form').submit();
}

/**
 * 自动填充
 */
function textAutocomplete(){
	var customLeaderSearchArray = new Array();		//客服经理(查询条件)
	<c:forEach items="${customLeaderSearch}" var="userItem">
		customLeaderSearchArray.push({'id' : '${userItem.id}', 'realName' : '${userItem.realName}', 'userName' : '${userItem.userName}', 'name' : '${userItem.realName}${userItem.userName}'});
	</c:forEach>
	
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


</script>
</HEAD>	
<BODY>
<div class="top_crumbs">您当前所在的位置：<a href="#">客服满意度管理</a>&gt;&gt;<span class="top_crumbs_txt">上门/门市签约客服满意度列表</span></div>
	<form name="form" id="satisfaction_form" method="post" onSubmit=""
		enctype="multipart/form-data" action="signSatisfaction-execute">

		<div class="pici_search pd5">

			<div class="pici_search pd5 mb10">
			<label class="mr10">订&nbsp;&nbsp;单&nbsp;&nbsp;号： <input type="text" size="10" name="search.orderId" value="${search.orderId}" /> </label>
			<label class="mr10">线&nbsp;&nbsp;路&nbsp;&nbsp;号： <input type="text" size="10" name="search.routeId" value="${search.routeId}" /> </label>
			<label class="mr10">门市/上门签约人员姓名： <input type="text" size="10" name="search.faceSaleName" value="${search.faceSaleName}" /> </label>
			
			
			签约方式：
				<c:if test="${search.signType==''}">
				<select class="mr10" name="search.signType">
						<option value=""  selected>请选择</option>
						<option value="0" >上门签约</option>
						<option value="1" >门市签约</option>
				</select>
				</c:if>
				<c:if test="${search.signType!=''}">
				<select class="mr10" name="search.signType">
						<option value="" >请选择</option>
						<option value="0" <c:if test="${search.signType==0}">selected</c:if> >上门签约</option>
						<option value="1" <c:if test="${search.signType==1}">selected</c:if> >门市签约</option>
				</select>
				</c:if>
				 
				<select class="mr10" name="search.productLeader">
						<option value="">产品经理</option>
						<c:forEach items="${productManagers}" var="pm">
						<option value="${pm.id}" <c:if test="${search.productLeader==pm.id}">selected</c:if>  >${pm.realName}</option>
						</c:forEach>
				</select>
				
				订单类型：
				<c:if test="${search.orderType==''}">
				<select class="mr10" name="search.orderType">
						<option value=""  selected>请选择</option>
						<option value="0" >跟团</option>
						<option value="1" >自助游</option>
						<option value="2" >团队</option>
				</select>
				</c:if>
				<c:if test="${search.orderType!=''}">
				<select class="mr10" name="search.orderType">
						<option value="" >请选择</option>
						<option value="0" <c:if test="${search.orderType==0}">selected</c:if> >跟团</option>
						<option value="1" <c:if test="${search.orderType==1}">selected</c:if> >自助游</option>
						<option value="2" <c:if test="${search.orderType==2}">selected</c:if> >团队</option>
				</select>
				</c:if>
				
				预订城市：
				<select class="mr10" name="search.bookCity">
						<option value="" >请选择</option>
						<c:forEach items="${bookCityList}" var="bcl"  > 
							<option value="${bcl}" <c:if test="${search.bookCity==bcl}">selected</c:if> >${bcl}</option>
						</c:forEach>
				</select>
				
				出发城市：
				<select class="mr10" name="search.startCity">
						<option value="" >请选择</option>
						<c:forEach items="${startCityList}" var="scl"  > 
							<option value="${scl}" <c:if test="${search.startCity==scl}">selected</c:if> >${scl}</option>
						</c:forEach>
				</select>
				
				<br/>
				<label> 出游日期： <input type="text" size="20" name="search.outDateBegin" id="out_date_begin"	value="${search.outDateBegin }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readOnly="readonly"/> </label> 
				<label class="mr10">至 <input type="text" size="20" name="search.outDateEnd" id="out_date_end"value="${search.outDateEnd }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readOnly="readonly"/> </label> 
				
				<label> 归来日期： <input type="text" size="20" name="search.backDateBegin" id="back_date_begin"	value="${search.backDateBegin }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readOnly="readonly"/> </label> 
				<label class="mr10">至 <input type="text" size="20" name="search.backDateEnd" id="back_date_end"value="${search.backDateEnd }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readOnly="readonly"/> </label> 
			    
			    <label class="mr10">
					客服经理：
					<input type="text" name="search.customerLeader" id="manager" value="${search.customerLeader}"/>
				</label>
				
				<label class="mr10">
					订单联系人姓名：
					<input type="text" name="search.customName" id="customName"  value="${search.customName}"/>
				</label>
			           
				<input type="button" value="查询" class="blue" name="" onclick="onSearchClicked();"/> 
				<input type="button" value="导出" class="blue" name="" onclick="onSearchExport();"/>
				<input type="button" value="今日工作导出" class="blue" name="" onclick="onSearchExportToday();"/>
			</div>
		</div>
	</form>
	<!-- 分配负责人或者交接工作表单 -->

<table class="listtable" width="98%">
	<thead>
		<th>关联订单id</th>
		<th>门市/上门签约人员姓名</th>
		<th>门市/上门签约人员服务满意度</th>
		<th>签约方式</th>
		<th>预订城市</th>
		<th>出发城市</th>
		<th>目的地</th>
		<th>客服经理</th>
		<th>产品经理</th>
		<th>订单类型</th>
		<th>线路编号</th>
		<th>出游日期</th>
		<th>归来日期</th>
		<th>订单联系人姓名</th>
		<th>订单联系人电话</th>
	</thead>
	<tbody>
		<c:forEach items="${dataList}" var="v"  varStatus="st"> 
		<tr align="center"  class="trbg" >
			<td><a href="#" onclick="showOrder(${user.id},'${user.realName}',${v.orderId})">${v.orderId}</a></td>
			<td>${v.faceSaleName} </td> 
			<td>${v.faceSaleSatisfaction} </td> 
			<td>
				<c:if test="${v.orderType=='0'}">
					<c:if test="${v.signType=='5'}">上门签约</c:if>
					<c:if test="${v.signType=='1'}">门市签约</c:if>
				</c:if>
				<c:if test="${v.orderType=='1'}">
					<c:if test="${v.signType=='2'}">上门签约</c:if>
					<c:if test="${v.signType=='1'}">门市签约</c:if>
				</c:if>
				<c:if test="${v.orderType=='2'}">
					<c:if test="${v.signType=='4'}">上门签约</c:if>
					<c:if test="${v.signType=='1'}">门市签约</c:if>
				</c:if>
			</td> 
			<td>${v.bookCity} </td> 
			<td>${v.startCity} </td> 
			<td>${v.desCity} </td> 
			<td>${v.customerLeader} </td> 
			<td>${v.productLeader} </td> 
			<td>
				<c:if test="${v.orderType=='0'}">跟团</c:if>
				<c:if test="${v.orderType=='1'}">自助游</c:if>
				<c:if test="${v.orderType=='2'}">团队</c:if>
			 </td> 
			<td>${v.routeId}</td> 
			<td><fmt:formatDate value="${v.outDate}" pattern="yyyy-MM-dd" /></td> 
			<td><fmt:formatDate value="${v.backDate}" pattern="yyyy-MM-dd" /></td> 
			<td>${v.customName}</td> 
			<td>${v.telNo}</td> 
		</tr>
		</c:forEach>
	</tbody>
</table>



<script type="text/javascript">
$(document).ready(function(){
	
	
	$(document).keydown(function(event) {  //屏蔽回车提交
		  if (event.keyCode == 13) {
		    $('form').each(function() {
		      event.preventDefault();
		    });
		  }
		}); 
	
	textAutocomplete();
});

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

<%@include file="/WEB-INF/html/pager.jsp" %>
<%@include file="/WEB-INF/html/foot.jsp" %>
