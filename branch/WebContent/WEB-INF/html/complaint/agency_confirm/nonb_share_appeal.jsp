<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<script type="text/javascript" src="${CONFIG.res_url}script/DatePickerNew/WdatePicker.js" ></script>
<link href="${CONFIG.res_url}css/thickbox.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${CONFIG.res_url}script/thickbox-compressed.js"></script>
<script type="text/javascript">
$(function(){
	
});

function search() {
	$('#search_form').submit();
}

function beforeFormSubmit() {
	var reg = new RegExp("^[0-9]*$");
	if (!reg.test($('#pageNo').val())) {
		$('#pageNo').attr("value",1);
	}

	var orderId = $('#orderId').val();
	if (orderId != "" && !reg.test(orderId)) {
		alert("订单号应为数字！~");
		$('#orderId').focus();
		return false;
	}

	var complaintId = $('#complaintId').val();
	if (complaintId != "" && !reg.test(complaintId)) {
		alert("投诉单号应为数字！~");
		$('#complaintId').focus();
		return false;
	}

	var agencyId = $('#agencyId').val();
	if (agencyId != "" && !reg.test(agencyId)) {
		alert("供应商ID应为数字！~");
		$('#agencyId').focus();
		return false;
	}

	var routeId = $('#routeId').val();
	if (routeId != "" && !reg.test(routeId)) {
		alert("线路号应为数字！~");
		$('#routeId').focus();
		return false;
	}

	return true;
}

//发起申请按钮事件
function productAppeal(supportId, agencyName,orderId, complaintId) {
 	$(".background_mask").show();
	$(".background_mask").unbind("click").bind("click",closeCofirmDiv);
	var title='填写投诉单'+ complaintId + '的申诉原因';
	$("body").append('<div class="confirm_div"><div class="confirm_title">'+title+
			'</div><div class="confirm_content"><textarea id="appealContent" placeholder="请填写申诉原因" maxlength="1000">'+
			'</textarea></div><div class="submit_btn bule_btn">确定</div><div class="cancel_btn bule_btn">取消</div></div>');
	$(".submit_btn").unbind("click").bind("click",function(){
		$(this).addClass("active");
		launchAppealFlow(supportId, agencyName, orderId);
	});
	$(".cancel_btn").unbind("click").bind("click",closeCofirmDiv);
}

//关闭confirm页面
function closeCofirmDiv(){
	$(".confirm_div").remove();
	$(".background_mask").hide();
}

//发起申诉流程
function launchAppealFlow(supportId, agencyName, orderId){
	if($("#appealContent").val().length==0){
		layer.alert("请填写申诉原因", {
			icon : 2
		});
		$(".submit_btn").removeClass("active");
		return;
	}
	
	layer.confirm("确认替供应商[" + agencyName + "]申诉订单[" + orderId + "]的赔偿方案吗？", function(index) {
		var param = {"supportId":supportId,"appealContent":$("#appealContent").val()};
		$.ajax({
			type: "POST",
			async:false,
			url: "agency_confirm-launchAppealFlow",
			data: param,
			success: function(data){
				closeCofirmDiv();
				search();
		    },
			error:function(){
				closeCofirmDiv();
				layer.alert("发起申诉失败", {
					icon : 2
				});
			}
		});
		layer.close(index);
	});
	$(".submit_btn").removeClass("active");
}
</script>
<style>
.confirm_div {
	height: 300px;
	width: 400px;
	left: 50%;
	top: 50%;
	margin-top: -150px;
	margin-left: -200px;
	position: absolute;
	background-color: #FFFFFF;
	border-radius: 6px;
	z-index: 10000;
	float: left;
}

.confirm_div .confirm_title {
	height: 40px;
	line-height: 40px;
	text-align: center;
	font-size: 16px;
	font-family: '微软雅黑';
	background-color: #87CEFA;
	border: 1px solid #3bb3e0;
	border-radius: 4px;
}

.confirm_div .confirm_content textarea{
	width:376px;
	height:200px;
	margin: 10px 0 0 10px;
	resize: none;
	font-size: 14px;
}

.confirm_div .bule_btn{
	float: left;
	width: 50px;
	height: 30px;
	background-color: #3bb3e0;
	margin: 5px 0px 0 102px;
	cursor: pointer;
	border-radius: 5px;
	font-size: 14px;
	line-height: 30px;
	text-align: center;
	font-family: '微软雅黑';
	-webkit-box-shadow: 0px 2px 0px #3bb3e0, 0px 9px 25px rgba(0, 0, 0, .7);
	-moz-box-shadow: 0px 2px 0px #3bb3e0, 0px 9px 25px rgba(0, 0, 0, .7);
	box-shadow: 0px 3px 0px #186f8f, 0px 9px 25px rgba(0, 0, 0, .7);
}

.confirm_div .bule_btn.active {
	-webkit-box-shadow: 0px 0px 0px #3bb3e0, 0px 3px 6px
		rgba(0, 0, 0, .9);
	-moz-box-shadow: 0px 0px 0px #3bb3e0, 0px 3px 6px
		rgba(0, 0, 0, .9);
	box-shadow: 0px 0px 0px #186f8f, 0px 3px 6px
		rgba(0, 0, 0, .9);
	position: relative;
	top: 3px;
}

.background_mask {
	display: none;
	position: absolute;
	top: 0%;
	left: 0%;
	width: 100%;
	height: 100%;
	background-color: black;
	z-index: 999;
	-moz-opacity: 0.5;
	opacity: .50;
	filter: alpha(opacity = 70);
}
</style>
</HEAD>
<BODY>
<div class="background_mask"></div>
<div class="top_crumbs">您当前所在的位置：<a href="#">投诉质检管理</a>>><span class="top_crumbs_txt">供应商承担申诉列表</span></div>
<form name="form" id="search_form" method="post" enctype="multipart/form-data" action="agency_confirm-getNonbShareAppeal" onsubmit="return beforeFormSubmit()">
<input type="hidden" name="page.menuId" id="menu_id" value="${page.menuId }">
<div class="pici_search pd5 mb10">
  <label class="mr10">订单号：
    <input type="text" size="10" id="orderId" name="page.orderId" value="${page.orderId==0 ? '' : page.orderId}"/>
  </label>
  <label class="mr10">投诉单号：
    <input type="text" size="10" id="complaintId" name="page.complaintId" value="${page.complaintId==0 ? '' : page.complaintId}"/>
  </label>
  <label class="mr10">供应商ID：
    <input type="text" size="10" id="agencyId" name="page.agencyId" value="${page.agencyId==0 ? '' : page.agencyId}"/>
  </label>
  <label class="mr10">供应商名称：
    <input type="text" size="20" name="page.agencyName" value="${page.agencyName}"/>
  </label>
  <label class="mr10">线路号：
    <input type="text" size="10" id="routeId" name="page.routeId" value="${page.routeId==0 ? '' : page.routeId}"/>
  </label>
  <label class="mr10">出发城市：
    <input type="text" size="10" name="page.startCity" value="${page.startCity}"/>
  </label>
  <br>
       投诉时间： <input type="text" size="20" name="page.complaintDateBgn" id="complaintDateBgn" value="${page.complaintDateBgn}" 
               onclick="var complaintDateEnd=$dp.$('complaintDateEnd');WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,onpicked:function(){complaintDateEnd.click();},maxDate:'#F{$dp.$D(\'complaintDateEnd\')}'})" readonly="readonly" /> 至 
        <input type="text" size="20" name="page.complaintDateEnd" id="complaintDateEnd" value="${page.complaintDateEnd}" 
               onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'complaintDateBgn\')}'})" readonly="readonly" />　
       出游日期： <input type="text" size="20" name="page.startDateBgn" id="startDateBgn" value="${page.startDateBgn}" 
               onclick="var startDateEnd=$dp.$('startDateEnd');WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,onpicked:function(){startDateEnd.click();},maxDate:'#F{$dp.$D(\'startDateEnd\')}'})" readonly="readonly" /> 至 
        <input type="text" size="20" name="page.startDateEnd" id="startDateEnd" value="${page.startDateEnd}" 
               onclick="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'startDateBgn\')}'})" readonly="readonly" />　
  <label class="mr10">处理状态：
    <select name="page.confirmState">
    	<option value="0">全部</option>
		<option value="1" <c:if test="${page.confirmState == 1}">selected</c:if>>已确认</option>
		<option value="2" <c:if test="${page.confirmState == 2}">selected</c:if>>到期默认</option>
		<option value="3" <c:if test="${page.confirmState == 3}">selected</c:if>>已申诉</option>
    </select>
  </label>
  <input type="submit" value="查询" class="blue" />
</div>
<table width="100%" class="listtable mb10">
	<tr>
	    <th>供应商ID</th>
	    <th>供应商名称</th>
		<th>投诉单号</th>
		<th>订单号</th>
		<th>线路号</th>
		<th>线路名称</th>
		<th>客人姓名</th>
		<th>出发城市</th>
		<th>出游日期</th>
		<th>投诉时间</th>
		<th>投诉等级</th>
		<th>承担总额</th>
		<th>确认状态</th>
		<th>赔偿通知单</th>
		<th>操作</th>
	</tr>
	<c:forEach items="${page.dataList }" var="v" varStatus="st">
	<tr>
	    <td align="center">${v.agencyId }</td>
	    <td title="${v.agencyName }">
		    <c:choose>
				<c:when test="${fn:length(v.agencyName) > 12}">
					<c:out value="${fn:substring(v.agencyName, 0, 10)}......" />
				</c:when>
				<c:otherwise>
					<c:out value="${v.agencyName}" />
				</c:otherwise>
			</c:choose>
		</td>
		<td align="center"><a href="complaint-toBill?id=${v.complaintId}" target="_blank">${v.complaintId }</a></td>
		<td align="center"><a href="#" onclick="showOrder(${user.id},'${user.realName}',${v.orderId})">${v.orderId }</a></td>
		<td align="center">${v.routeId }</td>
		<td title="${v.routeName }">
		    <c:choose>
				<c:when test="${fn:length(v.routeName) > 12}">
					<c:out value="${fn:substring(v.routeName, 0, 10)}......" />
				</c:when>
				<c:otherwise>
					<c:out value="${v.routeName}" />
				</c:otherwise>
			</c:choose>
		</td>
		<td align="center">${v.guestName }</td>
		<td align="center">${v.startCity }</td>
		<td align="center">${v.startDate }</td>
		<td align="center">${v.complaintTime }</td>
		<td align="center">${v.complaintLevelName }</td>
		<td>
			人民币： ${v.localCurrencyAmount }
			<c:if test="${v.foreignCurrencyAmount > 0}">
			    <br><span>${v.foreignCurrencyName }: ${v.foreignCurrencyAmount }</span>
			</c:if>
		</td>
		<td align="center">
		    <c:choose>
				<c:when test="${v.appealInfo != null}">
					<c:if test="${v.appealInfo.resultFlag == -1}">
						<a style="color: orangered" href="agency_confirm-toAppealInfoBill?agencyId=${v.agencyId}&complaintId=${v.complaintId}&keepThis=true&TB_iframe=true&height=350&width=600" class="thickbox" title="供应商申诉处理">
				        	<c:out value="${v.confirmState }" />
				    	</a>
					</c:if>
					<c:if test="${v.appealInfo.resultFlag == 1}">
						<a style="color: green;" href="agency_confirm-toAppealInfoBill?agencyId=${v.agencyId}&complaintId=${v.complaintId}&keepThis=true&TB_iframe=true&height=350&width=600" class="thickbox" title="供应商申诉处理">
				        	<c:out value="${v.confirmState }" />
				    	</a>
					</c:if>
				    <c:if test="${v.appealInfo.resultFlag == 0}">
						<a href="agency_confirm-toAppealInfoBill?agencyId=${v.agencyId}&complaintId=${v.complaintId}&keepThis=true&TB_iframe=true&height=350&width=600" class="thickbox" title="供应商申诉处理">
				        	<c:out value="${v.confirmState }" />
				    	</a>
					</c:if>
				</c:when>
				<c:otherwise>
					<c:out value="${v.confirmState }" />
				</c:otherwise>
			</c:choose>
		</td>
		<td align="center"><input title="赔偿通知单"
			alt="agency_confirm-toAgencyPayoutBill?agencyId=${v.agencyId }&complaintId=${v.complaintId }&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=300&width=700&modal=false"
			class="thickbox pd3 mr10" style="cursor: pointer;" name="amountDetail" type="button" value="查看" />
		</td>
		<td align="center">
			<c:if test="${v.confirmStateId==-1||v.confirmStateId==1||v.confirmStateId==2||v.confirmStateId==3}">
				<input title="发起申诉" class="pd3 mr10" style="cursor: pointer;" type="button" value="发起申请"
				onclick="productAppeal('${v.id}', '${v.agencyName}', '${v.orderId}', '${v.complaintId}')"/>
			</c:if>
		</td>
	</tr>
	</c:forEach>
</table>
<%@include file="/WEB-INF/html/pager2.jsp" %>
</form>
</BODY>
<script type="text/javascript">
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
