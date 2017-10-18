<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<%@ taglib prefix="s" uri="/struts-tags" %> 
<link href="${CONFIG.res_url}css/easydialog.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${CONFIG.res_url}/script/easydialog.min.js"></script>

<script type="text/javascript">
$(function(){
	change_menu('${menuId }');
	
	$(".callBackClass").unbind("click").bind("click",chooseCallBackType);//回呼事件
	$(".addNote").unbind("click").bind("click",toAddNote);//备注事件
});

//标签转换时更新标签样式
function change_menu(value) {
	$("li[id^='menu_']").each(function(){
		$(this).removeClass("menu_on");
	});
	$('#'+value).addClass("menu_on");	
}

//列表标签表单提交
function searchTable(menuId) {
	$('#menu_id').attr("value",menuId);
	var actionUrl="complaint-addList";
	if($("#canLink").val()==1){
		actionUrl="complaint-addListCanLink";
	}
	$('#search_form').attr("action", actionUrl);
	$('#search_form').submit();
}

function onSearchClicked() { 
	var actionUrl="complaint-addList";
	if($("#canLink").val()==1){
		actionUrl="complaint-addListCanLink";
	}
	$('#search_form').attr("action", actionUrl);
	$('#search_form').submit();
}

function commitPage(currentPage,pageSize){
	var actionUrl="complaint-addList";
	if($("#canLink").val()==1){
		actionUrl="complaint-addListCanLink";
	}
	$('#search_form').attr("action", actionUrl);
	$('#search_form').attr("action",actionUrl+"?1=1&page.currentPage="+currentPage+"&page.pageSize="+pageSize);
	$('#search_form').submit();
}

function onResetClicked() {
    $(':input','#search_form').not(':button, :submit, :reset, :hidden')  
    .val('').removeAttr('checked').removeAttr('selected');
}


function toAddComplaint(){
	var src = $('#addComplaintBox iframe:first').attr('src');
	console.log('src:'+src);
	if(src==''){
		$('#addComplaintBox iframe').attr('src','complaint-toEditComplaint');
	}
	easyDialog.open({container : 'addComplaintBox', overlay : false});
	
}

function chooseCallBackType(){
	var complaintId=$(this).closest("td").attr("complaint_id");
	openLayer('投诉单[' + complaintId + ']回呼',
			'complaint-chooseCallBackType?complaintId='+complaintId);
}

function toAddNote(){
	var id=$(this).closest("td").attr("complaint_id");
	var src = $('#addNoteBox'+id +' iframe:first').attr('src');
	if(src==''){
		$('#addNoteBox'+id+ ' iframe:first').attr('src','complaint-addNote?id='+id);
	}
	easyDialog.open({container : 'addNoteBox'+id, overlay : false});
}

function openLayer(title, url) {
	layer.open({
		type : 2,
		title : title,
		shadeClose : true,
		shade : false,
		area : [ '400px', '133px' ],
		content : url
	});
}
</script>
<style>
.callBackClass{
    float: left;
    margin: 0px 0px 0px 10px;
    line-height: 23px;
    cursor: pointer;
    font-family:'微软雅黑';
    color:#003399;
}

.addNote{
	float: left;
    margin: 0px 0px 0px 10px;
    line-height: 23px;
    cursor: pointer;
    font-family:'微软雅黑';
    color:#003399;
}
</style>
</HEAD>
<BODY>
<div class="top_crumbs">您当前所在的位置：<a href="#">投诉质检管理</a>>><span class="top_crumbs_txt">投诉申请与查询</span></div>
	<form name="search_form" id="search_form" method="post" enctype="multipart/form-data" action="complaint-addList">
		<div id="pici_tab" class="clear">
			<ul>
				<li class="menu_on" onclick="searchTable(this.id)" id="menu_1">
					<s class="rc-l"></s><s class="rc-r"></s><a href="javascript:void(0)">所有投诉</a>
				</li>
				<li onclick="searchTable(this.id)" id="menu_2">
					<s class="rc-l"></s><s class="rc-r"></s><a href="javascript:void(0)">由我发起</a>
				</li>
			</ul>
		</div>
		<div class="pici_search pd5">
			<div class="pici_search pd5 mb10">
				<input title="投诉情况说明单" type="button" class="pd5 mr20 lf" value="发起投诉" onclick="toAddComplaint()"/>
				<input type="hidden" id="canLink" value="${canLink }"/>
				<input type="hidden" id="cacheRecord" value=""/>
				<input type="hidden" id="menu_id" name="menuId" value="${menuId }"/>
				<label class="mr10">订&nbsp;&nbsp;单&nbsp;&nbsp;号： <input type="text" size="10" name="search.orderId" value="${search.orderId}" />
				</label> <label class="mr10">投诉单号： <input type="text" size="10" name="search.id" value="${search.id}" /> </label>
				投诉来源： <select class="mr10" name="search.comeFrom">
					<option value="">全部</option>
					<option value="网站" <c:if test="${'网站'.equals(search.comeFrom)}">selected</c:if>>网站</option>
					<option value="门市" <c:if test="${'门市'.equals(search.comeFrom)}">selected</c:if>>门市</option>
					<option value="当地质检" <c:if test="${'当地质检'.equals(search.comeFrom)}">selected</c:if>>当地质检</option>
					<option value="来电投诉" <c:if test="${'来电投诉'.equals(search.comeFrom)}">selected</c:if>>来电投诉</option>
					<option value="CS邮箱 " <c:if test="${'CS邮箱 '.equals(search.source)}">selected</c:if>>CS邮箱</option>
					<option value="回访" <c:if test="${'回访'.equals(search.comeFrom)}">selected</c:if>>回访</option>
					<option value="点评" <c:if test="${'点评'.equals(search.comeFrom)}">selected</c:if>>点评</option>
					<option value="旅游局" <c:if test="${'旅游局'.equals(search.comeFrom)}">selected</c:if>>旅游局</option>
					<option value="微博" <c:if test="${'微博'.equals(search.comeFrom)}">selected</c:if>>微博</option>
					<option value="APP" <c:if test="${'APP'.equals(search.comeFrom)}">selected</c:if>>APP</option>
					<option value="其他" <c:if test="${'其他'.equals(search.comeFrom)}">selected</c:if>>其他</option>
				</select> 
				<label class="mr10">出发城市：<input type="text" size="10" name="search.startCity" value="${search.startCity}"></label>
				<label class="mr10">线路ID：<input type="text" size="10" name="search.routeId" value="${search.routeId}"></label>
				<label class="mr10">线路：<input type="text" size="10" name="search.route" value="${search.route}"></label>
				<label class="mr10">产品经理： <input type="text" size="10" name="search.productLeader" value="${search.productLeader}" /></label>
				<label class="mr10">联系电话：<input type="text" size="10" name="search.contactPhone" value="${search.contactPhone}"></label>　
				<br>
				<label> 供应商： <input type="text" size="20" name="search.agencyName" value="${search.agencyName }" /></label> 
				<label> 投诉时间： <input type="text" size="20" name="search.startBuildDate" value="${search.startBuildDate}" onclick="WdatePicker()" readOnly="readonly"/> </label> 
				<label class="mr10">至 <input type="text" size="20" name="search.endBuildDate" value="${search.endBuildDate}" onclick="WdatePicker()" readOnly="readonly"/> </label> 
				<label> 分配时间： <input type="text" size="20" name="search.startAssignTime" id="start_assign_time" value="${search.startAssignTime }"onclick="WdatePicker()" readOnly="readonly"/> </label> 
				<label class="mr10">至 <input type="text" size="20" name="search.endAssignTime" id="end_assign_time" value="${search.endAssignTime }" onclick="WdatePicker()" readOnly="readonly"/> </label>   
				<label> 完成时间： <input type="text" size="20" name="search.startFinishDate" value="${search.startFinishDate}" onclick="WdatePicker()" readOnly="readonly"/> </label> 
				<label class="mr10">至 <input type="text" size="20" name="search.endFinishDate" value="${search.endFinishDate}" onclick="WdatePicker()" readOnly="readonly"/> </label> 
				<br>
				<label class="mr10">客服经理：<input type="text" size="10" name="search.customerLeader" value="${search.customerLeader}"/></label>
				<label class="mr10">发起人：<input type="text" size="10" name="search.ownerName" value="${search.ownerName}"/></label>
				<label class="mr10">处理人： <input type="text" size="6" name="search.dealName" value="${search.dealName }" />
				</label> 
				<label class="mr10">负责人： <input type="text" size="6" name="search.managerName" value="${search.managerName }" />
				</label>
				<select class="mr10" name="search.level">
						<option value="">投诉等级</option>
						<option value="3" <c:if test="${search.level==3}">selected</c:if>>3级</option>
						<option value="2" <c:if test="${search.level==2}">selected</c:if>>2级</option>
						<option value="1" <c:if test="${search.level==1}">selected</c:if>>1级</option>
				</select>
				处理优先级：
				<select class="mr10" name="search.priority">
					<option value="-1" >请选择</option>
					<option value="1" <c:if test="${search.priority=='1'}">selected</c:if> >紧急</option>
					<option value="2" <c:if test="${search.priority=='2'}">selected</c:if> >重要</option>
					<option value="3" <c:if test="${search.priority=='3'}">selected</c:if> >普通</option>
					<option value="4" <c:if test="${search.priority=='4'}">selected</c:if> >一般</option>
				</select>
				处理岗：<s:select list="dealDepartments" headerKey="" headerValue="请选择" name="search.dealDepart"/>
				投诉状态：
				<select class="mr10" name="search.state">
					<option value="-1" >请选择</option>
					<option value="1" <c:if test="${search.state=='1'}">selected</c:if> >投诉待处理</option>
					<option value="2" <c:if test="${search.state=='2'}">selected</c:if> >投诉处理中</option>
					<option value="3" <c:if test="${search.state=='3'}">selected</c:if> >投诉已待结</option>
					<option value="4" <c:if test="${search.state=='4'}">selected</c:if> >投诉已完成</option>
					<option value="5" <c:if test="${search.state=='5'}">selected</c:if> >投诉待指定（升级售后）</option>
					<option value="6" <c:if test="${search.state=='6'}">selected</c:if> >投诉待指定（提交售后填写分担方案）</option>
					<option value="7" <c:if test="${search.state=='7'}">selected</c:if> >投诉待指定（升级售前）</option>
					<option value="9" <c:if test="${search.state=='9'}">selected</c:if> >已撤销</option>
					<option value="10" <c:if test="${search.state=='10'}">selected</c:if> >投诉待指定（升级客服中心售后）</option>
				</select>
				<input type="button" value="查询" class="blue" name="search_button" onclick="onSearchClicked()">　
				<input type="button" value="重置" class="blue" onclick="onResetClicked();">
			</div>
		</div>
	</form>
<div id="easyDialogBox">
<div id="addComplaintBox" style="width: 800px;" class="easyDialog_wrapper">
	<div class="easyDialog_content">
		<h4 class="easyDialog_title" id="easyDialogTitle"><a href="javascript:void(0)" title="关闭窗口" class="close_btn" id="closeBtn">×</a>投诉情况说明单</h4>
		<div>
			<iframe src="" frameborder="0" width="800" height="500"></iframe>
		</div>
	</div>
</div>
</div>
	<table class="listtable" width="98%">
		<thead>
			<th>投诉id</th>
			<th>关联订单id</th>
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
			<th>发起人</th>
			<th>处理人</th>
			<th>投诉处理状态</th>
			<th>处理优先级</th>
			<th>备注</th>
			<th>操作</th>
		</thead>
		<tbody>
			<c:forEach items="${request.dataList}" var="v" varStatus="st">
				<tr align="center">
					<%-- <td><a href="complaint-toBill?id=${v.id}" target="_blank">${v.id}</a></td> --%>
					<td>
					<c:if test="${canLink ==1 }"><a href="complaint-toBill?id=${v.id}" target="_blank"></c:if>
					${v.id}
					<c:if test="${canLink ==1 }"></a></c:if>
					</td>
					<td>
					<c:if test="${v.orderId > 0}">
					<a href="#" onclick="showOrder(${user.id},'${user.realName}',${v.orderId})">${v.orderId}</a>
					</c:if>
					</td>
					<td>${v.guestName}</td>
					<td>${v.guestLevel}</td>
					<td style="color: red">
						<c:forEach items="${tagList}" var="tag"  varStatus="st"> 
							<c:if test="${v.custId==tag.cust_id}">${tag.tag_values}</c:if>
						</c:forEach>
					</td>
					<td>
					<c:if test="${v.startCity != '' || v.routeId > 0}">
					${v.startCity}-${v.routeId}
					</c:if>
					</td>
					<td><fmt:formatDate value="${v.buildDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td>${v.customerLeader}</td>
					<td>${v.productLeader}</td>
					<td>${v.orderState}</td>
					<td>${v.dealDepart}</td>
					<td>${v.comeFrom}</td>
					<td>${v.level}</td>
					<td>${v.ownerName}</td>
					<td>${v.dealName}</td>
					<td>
						<c:if test="${v.state==1}">投诉待处理</c:if>
						<c:if test="${v.state==2}">投诉处理中</c:if>
						<c:if test="${v.state==3}">投诉已待结</c:if>
						<c:if test="${v.state==4}">投诉已完成</c:if>
						<c:if test="${v.state==5}">投诉待指定（升级售后）</c:if>
						<c:if test="${v.state==6}">投诉待指定（提交售后填写分担方案）</c:if>
						<c:if test="${v.state==7}">投诉待指定（升级售前）</c:if>
						<c:if test="${v.state==9}">已撤销</c:if>
						<c:if test="${v.state==10}">投诉待指定（升级客服中心售后）</c:if>
					</td>
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
					<td style="width: 80px;" complaint_id="${v.id}">
					<c:if test="${v.state!=3 && v.state!=4 && v.state!=9}">
						<div class="callBackClass">回呼</div>
						<div class="addNote">备注</div>
						<%-- <a href="javascript:void(0);" onclick="toAddNote(${v.id})" title="填写备注">备注</a> --%>
						<div id="addNoteBox${v.id }" style="display: none; width: 800px;" class="easyDialog_wrapper">
							<div class="easyDialog_content">
								<h4 class="easyDialog_title" id="easyDialogTitle"><a href="javascript:void(0)" title="关闭窗口" class="close_btn" id="closeBtn">×</a>填写备注</h4>
								<div>
									<iframe src="" frameborder="0" width="800" height="500"></iframe>
								</div>
							</div>
						</div>
					</c:if>
					<!-- 未处理之前可以由录入人编辑删除投诉  -->
					<c:if test="${v.state==1 && v.ownerId == userId}">
						<!--<a href="complaint-toEditOrDeleteComplaint?actFlag=edit&id=${v.id}" target="_blank">修改</a>-->
						<a href="complaint-toEditOrDeleteComplaint?actFlag=del&id=${v.id}">删除</a>
					</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</BODY>
<%@include file="/WEB-INF/html/pagerCommon.jsp" %>
