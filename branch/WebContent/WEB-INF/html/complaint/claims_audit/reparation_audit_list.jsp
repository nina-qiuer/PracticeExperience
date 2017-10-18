<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %> 
<%@include file="/WEB-INF/html/head.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>赔款审核列表</title>
<link href="${CONFIG.res_url}css/thickbox.css" rel="stylesheet" type="text/css" />
<style type="text/css">
a:link{
text-decoration:none;
}
a:visited{
text-decoration:none;
}
a:hover{
text-decoration:none;
}
a:active{
text-decoration:none;
}
</style>
<script type="text/javascript" src="${CONFIG.res_url}script/thickbox-compressed.js"></script>

<script type="text/javascript">
var claimsAuditGroups;

$(function(){
	var tab_flag = '${request.tab_flag}';
	if (null != tab_flag) {
		change_menu(tab_flag);
	}
})

//标签转换时更新标签样式
function change_menu(value) {
	$("li[id^='menu_']").each(function(){
		$(this).removeClass("menu_on");
	});
	$('#'+value).addClass("menu_on");	
}

//列表标签表单提交
function searchTable(menuId) {
	$('#reparation_form').attr("action", "claims_audit");
	$('#searchTab li').removeClass("menu_on");
	$('#'+menuId).addClass("menu_on");
	$('#tab_flag').attr("value",menuId);
	$('#reparation_form').submit();
}

function toPage(pageNo) {
	$('#pageNo').attr("value",pageNo);
	$('#reparation_form').submit();
}

function beforeFormSubmit() {
	var reg = new RegExp("^[0-9]*$");
	if (!reg.test($('#pageNo').val())) {
		$('#pageNo').attr("value",1);
	}

	if (!reg.test($('#orderId').val())) {
		alert("订单号应为数字！~");
		$('#orderId').focus();
		return false;
	}
	
	if (!reg.test($('#cmpId').val())) {
		alert("投诉号应为数字！~");
		$('#cmpId').focus();
		return false;
	}

	return true;
}

function setGroupId(depId)
{
	var groupId = $(depId).next();
	var oldgroupId = $(groupId).val();
	$(groupId).empty(); //清空旧的二级分类选项
	var newGroupIds;
	var option='<option value="">请选择</option>';
	if(claimsAuditGroups==undefined){
		getClaimsAuditGroups();
	}
	for(var index=0;index<claimsAuditGroups.length;index++){
		if(claimsAuditGroups[index].data.id==depId.value){
			newGroupIds=claimsAuditGroups[index].childs;
		}
	}
	//二级菜单变化
	if(newGroupIds!=undefined){
		for(var index=0;index<newGroupIds.length;index++){
			option+= '<option value="'+newGroupIds[index].data.id+'"';
			if(oldgroupId==newGroupIds[index].data.id){
				option+=' selected';
			}
			option+='>'+newGroupIds[index].data.depName+'</option>';
		}
	}
	$(groupId).append(option);
}

function getClaimsAuditGroups(){
	$.ajax({
		type : "GET",
		url : "claims_audit-getClaimsAuditGroupsByAjax",
		data : {},
		async : false,
		success : function(data) {
			claimsAuditGroups = data.retObj;
		}
	});
}
</script>
</HEAD>
<BODY>
<div class="top_crumbs">您当前所在的位置：订单管理&nbsp;&nbsp;&gt;&gt;&nbsp;&nbsp;快速链接&nbsp;&nbsp;&gt;&gt;&nbsp;&nbsp;<span class="top_crumbs_txt">赔款审核列表</span></div>
<form name="reparation_form" id="reparation_form" method="post" action="claims_audit" onSubmit="return beforeFormSubmit();">
	<input type="hidden" name="claimsAuditEntity.menuId" id="menu_id" value="${claimsAuditEntity.menuId }">
	<input type="hidden" name="tab_flag" id="tab_flag" value="${tab_flag}">
	<div id="pici_tab" class="clear">
		<ul>
			<li class="menu_on" onclick="searchTable(this.id)" id="menu_1">
				<s class="rc-l"></s><s class="rc-r"></s><a href="javascript:void(0)">赔款总表</a>
			</li>
			<li onclick="searchTable(this.id)" id="menu_2">
				<s class="rc-l"></s><s class="rc-r"></s><a href="javascript:void(0)">初审(经理)</a>
			</li>
			<li onclick="searchTable(this.id)" id="menu_3">
				<s class="rc-l"></s><s class="rc-r"></s><a href="javascript:void(0)">复审(部门长/总监)</a>
			</li>
			<li onclick="searchTable(this.id);" id="menu_4">
				<s class="rc-l"></s><s class="rc-r"></s><a href="javascript:void(0)">复审(总经理)</a>
			</li>
			<li onclick="searchTable(this.id);" id="menu_5">
				<s class="rc-l"></s><s class="rc-r"></s><a href="javascript:void(0)">复审(AVP)</a>
			</li>
			<li onclick="searchTable(this.id);" id="menu_6">
				<s class="rc-l"></s><s class="rc-r"></s><a href="javascript:void(0)">终审(CEO、COO)</a>
			</li>
			<li onclick="searchTable(this.id);" id="menu_7">
				<s class="rc-l"></s><s class="rc-r"></s><a href="javascript:void(0)">完成审核</a>
			</li>
		</ul>
	</div>
	<div class="pici_search pd5">
		<div class="pici_search pd5 mb10">
		申请时间：<input type="text" size="20" name="claimsAuditEntity.startDate" id="startDate" value="<fmt:formatDate value='${claimsAuditEntity.startDate }'
								pattern='yyyy-MM-dd' />" onfocus="var endDate=$dp.$('endDate');WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,onpicked:function(){endDate.focus();},maxDate:'#F{$dp.$D(\'endDate\')}'})" readonly="readonly" /> 至 
		      <input type="text" size="20" name="claimsAuditEntity.endDate" id="endDate" value="<fmt:formatDate value='${claimsAuditEntity.endDate }'
								pattern='yyyy-MM-dd' />" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'startDate\')}'})" readonly="readonly" />
		&nbsp;&nbsp;&nbsp;&nbsp;
		<c:if test="${request.tab_flag == 'menu_7'}">
		审核时间：<input type="text" size="20" name="claimsAuditEntity.auditStartTime" id="auditStartTime" value="<fmt:formatDate value='${claimsAuditEntity.auditStartTime }'
								pattern='yyyy-MM-dd' />" onfocus="var endDate=$dp.$('auditEndTime');WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,onpicked:function(){auditEndTime.focus();},maxDate:'#F{$dp.$D(\'auditEndTime\')}'})" readonly="readonly" /> 至 
		      <input type="text" size="20" name="claimsAuditEntity.auditEndTime" id="auditEndTime" value="<fmt:formatDate value='${claimsAuditEntity.auditEndTime }'
								pattern='yyyy-MM-dd' />" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'auditStartTime\')}'})" readonly="readonly" />
		&nbsp;&nbsp;&nbsp;&nbsp;
		</c:if>
		审核人： <input type="text" size="10" id="auditName" name="claimsAuditEntity.auditName" value="<c:if test='${claimsAuditEntity.auditName != "" }'>${claimsAuditEntity.auditName }</c:if>" />
		&nbsp;&nbsp;&nbsp;&nbsp;
		订单号： <input type="text" size="10" id="orderId" name="claimsAuditEntity.orderId" value="<c:if test='${claimsAuditEntity.orderId != 0 }'>${claimsAuditEntity.orderId }</c:if>" />
		&nbsp;&nbsp;&nbsp;&nbsp;
		投诉单号：<input type="text" size="10" id="cmpId" name="claimsAuditEntity.cmpId" value="<c:if test='${claimsAuditEntity.cmpId != 0 }'>${claimsAuditEntity.cmpId}</c:if>" />
		&nbsp;&nbsp;&nbsp;&nbsp;
		处理岗：<s:select list="dealDepartments" headerKey="" headerValue="请选择" name="claimsAuditEntity.dealDepart"/>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<c:if test="${request.tab_flag == 'menu_2'||request.tab_flag == 'menu_3'}">
		二级部：
		<select name="claimsAuditEntity.depId" onchange="setGroupId(this)">
			<option value="">请选择</option>
			<c:forEach items="${claimsAuditGroups}" var="claimsAuditDept" >
				<option value="${claimsAuditDept.data.id }" 
				<c:if test="${claimsAuditDept.data.id == claimsAuditEntity.depId }">selected</c:if>>${claimsAuditDept.data.depName}</option>
			</c:forEach>
		</select>
		&nbsp;&nbsp;三级组：
		<select name="claimsAuditEntity.groupId">
			<option value="">请选择</option>
			<c:choose>
		  		<c:when test="${claimsAuditEntity.depId == null}"></c:when>
				<c:otherwise> 
					<c:forEach items="${claimsAuditGroups}" var="claimsAuditSecondDept" >
						<c:if test="${claimsAuditSecondDept.data.id == claimsAuditEntity.depId }">
							<c:forEach items="${claimsAuditSecondDept.childs}" var="claimsAuditGroup" >
								<option value="${claimsAuditGroup.data.id }" 
								<c:if test="${claimsAuditGroup.data.id == claimsAuditEntity.groupId }">selected</c:if>>${claimsAuditGroup.data.depName}</option>
							</c:forEach>
						</c:if>
					</c:forEach>
				</c:otherwise>		
			</c:choose>			
		</select>
		</c:if>
		<input type="submit" value="查询" class="blue">
		</div>
	</div>
</form>
<table class="listtable" width="100%">
	<thead>
		<th>订单号</th>
		<th>投诉单号</th>
		<th>退款总额</th>
		<th>分担总额</th>
		<th>约定付款时间</th>
		<th>操作</th>
		<th>金额概览</th>
		<th>附件</th>
		<th>处理岗</th>
		<th>分类</th>
		<th>审核状态</th>
		<th>申请时间</th>
		<c:if test="${request.tab_flag == 'menu_7'}">
		<th>审核时间</th>
		</c:if>
		<th>退款申请人</th>
	</thead>
	<tbody>
		<c:forEach items="${claimsAuditEntitys}" var="cae">
			<tr align="center" class="trbg">
				<td rowspan="2"><a href="#" onclick="showOrder(${user.id},'${user.realName}',${cae.complaintEntity.orderId})">${cae.complaintEntity.orderId}</a></td>
				<td rowspan="2"><a href="complaint-toBill?id=${cae.complaintEntity.id}" target="_blank">${cae.complaintEntity.id}</a></td>
				<td rowspan="2">${cae.complaintSolutionEntity.cash + cae.complaintSolutionEntity.touristBook}</td>
				<td rowspan="2">${cae.shareSolutionEntity.total}</td>
				<td rowspan="2"><fmt:formatDate value="${cae.complaintSolutionEntity.appointedTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
				<td rowspan="2">
					<c:if test="${tab_flag!='menu_1' && tab_flag!='menu_7'}">
						<input type="button" class="pd5" id="btnID${cae.complaintEntity.id}" value="审核" 
						onclick="confirmDetail(${cae.complaintEntity.id},${cae.complaintEntity.createType},${cae.complaintEntity.orderId})">
					</c:if>
					<c:if test="${tab_flag=='menu_7' || tab_flag=='menu_1'}">
						<input type="button" class="pd5" id="btnID${cae.complaintEntity.id}" value="查看" 
						onclick="confirmDetail(${cae.complaintEntity.id},${cae.complaintEntity.createType},${cae.complaintEntity.orderId})">
					</c:if>
				</td>
				<td rowspan="2">
					<input title="分担方案金额概览" class="thickbox pd5" name="amountDetail" type="button" value="金额概览" 
					alt="claims_audit-queryMoney?complaintId=${cae.complaintEntity.id}&createType=${cae.complaintEntity.createType}&orderId=${cae.complaintEntity.orderId}&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=230&width=750&modal=false">
				</td>
			 	<td rowspan="2">
			 		<input title="附件列表" class="thickbox pd5" name="amountDetail" type="button" value="附件列表" 
					alt="complaint-queryUpload?complaintId=${cae.complaintEntity.id}&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=230&width=750&modal=false">
				</td>
				<td rowspan="2">${cae. complaintEntity.dealDepart}</td>
			 	<td>对客</td>
			 	<td>
					<c:if test="${cae.complaintSolutionEntity.auditFlag==null || cae.complaintSolutionEntity.submitFlag==0}">暂无对客方案</c:if>
					<c:if test="${cae.complaintSolutionEntity.auditFlag==-1 && cae.complaintSolutionEntity.submitFlag==1}">无需审核</c:if>
					<c:if test="${cae.complaintSolutionEntity.auditFlag==0 && cae.complaintSolutionEntity.submitFlag==1}">待初审</c:if>
					<c:if test="${cae.complaintSolutionEntity.auditFlag==1 && cae.complaintSolutionEntity.submitFlag==1}">已初审</c:if>
					<c:if test="${cae.complaintSolutionEntity.auditFlag==2 && cae.complaintSolutionEntity.submitFlag==1}">已复审（一）</c:if>
					<c:if test="${cae.complaintSolutionEntity.auditFlag==3 && cae.complaintSolutionEntity.submitFlag==1}">已复审（二）</c:if>
					<c:if test="${cae.complaintSolutionEntity.auditFlag==5 && cae.complaintSolutionEntity.submitFlag==1}">已复审（三）</c:if>
					<c:if test="${cae.complaintSolutionEntity.auditFlag==4 && cae.complaintSolutionEntity.submitFlag==1}">通过审核</c:if>
				</td>
			 	<td><fmt:formatDate value="${cae.complaintSolutionEntity.submitTime}" pattern="yyyy-MM-dd" /></td>
			 	<c:if test="${request.tab_flag == 'menu_7'}">
			 	<td><fmt:formatDate value="${cae.complaintSolutionEntity.auditTime}" pattern="yyyy-MM-dd" /></td>
			 	</c:if>
			 	<td>${cae.complaintSolutionEntity.dealName}</td>
				<tr align="center" class="trbg">
				<td>分担</td>
				<td>
					<c:if test="${cae.shareSolutionEntity.auditFlag==null || cae.shareSolutionEntity.submitFlag==0}">暂无分担方案</c:if>
					<c:if test="${cae.shareSolutionEntity.auditFlag==-1 && cae.shareSolutionEntity.submitFlag==1}">无需审核</c:if>
					<c:if test="${cae.shareSolutionEntity.auditFlag==0 && cae.shareSolutionEntity.submitFlag==1}">待初审</c:if>
					<c:if test="${cae.shareSolutionEntity.auditFlag==1 && cae.shareSolutionEntity.submitFlag==1}">已初审</c:if>
					<c:if test="${cae.shareSolutionEntity.auditFlag==2 && cae.shareSolutionEntity.submitFlag==1}">已复审（一）</c:if>
					<c:if test="${cae.shareSolutionEntity.auditFlag==3 && cae.shareSolutionEntity.submitFlag==1}">已复审（二）</c:if>
					<c:if test="${cae.shareSolutionEntity.auditFlag==5 && cae.shareSolutionEntity.submitFlag==1}">已复审（三）</c:if>
					<c:if test="${cae.shareSolutionEntity.auditFlag==4 && cae.shareSolutionEntity.submitFlag==1}">通过审核</c:if>
				</td>
				<td><fmt:formatDate value="${cae.shareSolutionEntity.submitTime}" pattern="yyyy-MM-dd" /></td>
				<c:if test="${request.tab_flag == 'menu_7'}">
				<td><fmt:formatDate value="${cae.shareSolutionEntity.auditTime}" pattern="yyyy-MM-dd" /></td>
				</c:if>
				<td>${cae.shareSolutionEntity.dealName}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<%@include file="/WEB-INF/html/pagerCommon.jsp" %>


</BODY>
</HTML>
<script type="text/javascript">
function commitPage(currentPage,pageSize){
	$('#reparation_form').attr("action","claims_audit?1=1&page.currentPage="+currentPage+"&page.pageSize="+pageSize);
	$('#reparation_form').submit();
}
$(function(){
	/*移除thickbox默认样式*/
	$('.thickbox').click(function(){
		$('#TB_closeAjaxWindow').html("<a href='javascript:void(0)' id='TB_closeWindowButton' title='Close'>×</a>");
		$('#TB_closeWindowButton').live('click',function(){
			tb_remove();
		});
	});
});

function confirmDetail(complaintId,createType,orderId){
     var tab_flag =  $('#tab_flag').val();
    //document.getElementById('btnID').style.color="red"; 
    document.getElementById('btnID'+complaintId+'').style.background="green"; 
	window.open('claims_audit-doAudit?complaintId='+complaintId+'&createType='+createType+'&orderId='+orderId+'&tab_flag='+tab_flag+'');
	
}
</script>
