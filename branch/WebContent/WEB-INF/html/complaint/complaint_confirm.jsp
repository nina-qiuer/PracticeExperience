<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<script language="javascript" src="${CONFIG.res_url}script/complaint/complaint/complaint_solution.js" ></script>
<script type="text/javascript" src="${CONFIG.res_url}script/DatePickerNew/WdatePicker.js" ></script>   
<script type="text/javascript">
//确认，保存后台数据
function user_confirm(type,id,span_id){
//	alert("进入");
	var str="";
	if(type==1){
		str="已确认";
	}
	$.ajax({
		type: "POST",
		url: "share_solution-userConfirm",
		data: "type="+type+"&id="+id,
		success: function(){
			alert( "已经确认" ); 
			$("#"+span_id).html(str);
			location.reload();
		} //操作成功后的操作！msg是后台传过来的值
	})
}


$(function(){
	//为供应商承担详情链接绑定click事件
	$("[id^='support_share_']").click(
		function(e){
			var divWidth = $('#support_share_frame').attr('width');
			var divHeight = $('#support_share_frame').attr('height');
			$('#support_share_frame_div').css("left",e.clientX-divWidth/2);
			$('#support_share_frame_div').css("top",e.clientY-divHeight/4);
			var complaintId = $(this).attr("complaintId");			
			$('#support_share_frame_div').toggle();
			$('#support_share_frame').attr('src','support_share-getSupportSharesByCompId?complaintId='+complaintId);
		}		
	);
	
	//为员工承担详情链接绑定click事件
	$("[id^='employee_share_']").click(
		function(e){
			var divWidth = $('#employee_share_frame').attr('width');
			var divHeight = $('#employee_share_frame').attr('height');
			$('#employee_share_frame_div').css("left",e.clientX-divWidth/2);
			$('#employee_share_frame_div').css("top",e.clientY-divHeight/4);
			var complaintId = $(this).attr("complaintId");			
			$('#employee_share_frame_div').toggle();
			$('#employee_share_frame').attr('src','employee_share-getEmployeeSharesByCompId?complaintId='+complaintId);
		}		
	);
	
	//为供应商承担弹出div绑定mouseout事件
	$("#support_share_frame_div").mouseout(function(){
		$('#support_share_frame').attr('src','');
		$(this).css("display","none");
	});
	
	//为员工承担弹出div绑定mouseout事件
	$("#employee_share_frame_div").mouseout(function(){
		$('#employee_share_frame').attr('src','');
		$(this).css("display","none");
	});
	
	
});

function search(){
	$('#search_form').submit();
}
</script>

</HEAD>
<BODY>
<div class="top_crumbs">您当前所在的位置：<a href="#">投诉质检管理</a>>><span class="top_crumbs_txt">执行投诉确认表</span></div>
<div id="pici_tab" class="clear">
  <ul>
    <li class="menu_on"><s class="rc-l"></s><s class="rc-r"></s><a href="#">执行投诉确认表</a></li>
  </ul>
</div>
<form name="form" id="search_form" method="post" enctype="multipart/form-data" action="share_solution-confirm">
<div class="pici_search pd5 mb10">
  <label class="mr10">订单号：
    <input type="text" size="10" name="search.orderId" value="${search.orderId}"/>
  </label>
  <label class="mr10">投诉号：
    <input type="text" size="10" name="search.complaintId" value="${search.complaintId}"/>
  </label>
  <label class="mr10">供应商：
    <input type="text" size="20" name="search.agencyName" value="${search.agencyName}"/>
  </label>
  <label class="mr10">投诉处理人 ：
    <input type="text" size="10" name="search.dealName" value="${search.dealName}"/>
  </label>
<!-- 
  <label class="mr10">负责员工 ：
    <input type="text" size="10" name=""/>
  </label>
  <label class="mr10">投诉负责人 ：
    <input type="text" size="10" name=""/>
  </label>
-->
  <label class="mr10">是否确认 ：
    <select class="mr10" name="search.confirm">
    	<option value="2" <c:if test="${search.confirm==2}">selected</c:if> >未确认</option>
		<option value="" <c:if test="${search.confirm.equals('')}">selected</c:if> >全部</option>
		<option value="1" <c:if test="${search.confirm==1}">selected</c:if> >已确认</option>
	</select>
  </label>
  <label> 待结时间：
	<input type="text" size="20" name="search.startUpdateTime" id="" value="${search.startUpdateTime}" onclick="WdatePicker()" readOnly="readonly"/>
  </label>
  <label class="mr10">至 
  	<input type="text" size="20" name="search.endUpdateTime" id="" value="${search.endUpdateTime}" onclick="WdatePicker()" readOnly="readonly"/>
  </label>
  <input type="button" value="查询" class="blue" name="" onclick="search()"/>
</div>
</form>
	<table width="100%" class="listtable mb10">
		<tr>
			<th>投诉号</th>
			<th>订单号</th>
			<th>对客人赔偿总金额</th>

			<th>供应商承担</th>
			<th>订单利润承担</th>
			<th>员工承担</th>
			<th>公司承担</th>
			<th>成本类型</th>
			<th>礼品</th>
			<th>待结时间</th>

			<th>投诉处理人</th>
			<th>确认状态</th>

		</tr>
		<c:forEach items="${dataList }" var="v" varStatus="st">
			<tr align="center">
				<td><a href="complaint-toBill?id=${v.complaintId}" target="_blank">${v.complaintId }</a></td>
				<td><a href="#" onclick="showOrder(${user.id},'${user.realName}',${v.orderId})">${v.orderId}</a></td>
				<td>${v.total }</td>
				<td>${v.supplierTotal}
				 <span id="supply_span${v.id }">
				 	<c:if test="${v.supplierMark!=1}">
						<input type="button" name="button" id="button" onclick="user_confirm('2',${v.id},'supply_span${v.id }');" value="确认" />
						<a href="#" id="support_share_${v.complaintId}" complaintId=${v.complaintId}>详细</a>
					</c:if>
					<c:if test="${v.supplierMark==1}">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" id="support_share_${v.complaintId}" complaintId=${v.complaintId}>详细</a>
					</c:if>
				</span>
				
				</td>
				<td>${v.orderGains }</td>
				<td>${v.employeeTotal } 
				<span id="employee_span${v.id }">
					<c:if test="${v.employee!=1}">
						<input type="button" name="button" id="button" onclick="user_confirm('3',${v.id},'employee_span${v.id }');" value="确认" />
						<a href="#" id="employee_share_${v.complaintId}" complaintId=${v.complaintId}>详细</a>
					</c:if>
					<c:if test="${v.employee ==1}">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" id="employee_share_${v.complaintId}" complaintId=${v.complaintId}>详细</a>
					</c:if>
				</span>
				
				</td>
				<td>${v.special } </td>
				<td>${v.qualityToolTotal } </td>
				<td><c:if test="${v.gift>0 }">有</c:if><c:if test="${v.gift==0 }">无</c:if></td>
				<td><fmt:formatDate value="${v.updateTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>

				<td>${v.dealname}</td>
				<td>
					<c:if test="${v.confirm==1 }">已确认</c:if>
					<c:if test="${v.supplierMark==0&&v.employee==0 }">未确认</c:if>
					<c:if test="${v.supplierMark==1&&v.employee==0 }">结算已确认，待人事确认</c:if>
					<c:if test="${v.supplierMark==0&&v.employee==1 }">人事已确认，待结算确认</c:if>
					<c:if test="${v.supplierMark==1&&v.employee==1&&v.confirm!=1 }">
					<span id="user_span${v.id }">待确认
						<input type="button" name="" onclick="user_confirm('1',${v.id},'user_span${v.id }')" value="确认"/>
					</span>
					</c:if>					
				</td>
			</tr>
		</c:forEach>
	</table>
	
	<div  style="background: none repeat rgb(255, 255, 255); position: absolute;z-index:1000; display: none;" id="support_share_frame_div">
		<iframe width="500" height="150" id="support_share_frame"></iframe>
    </div>
    
    <div style="background: none repeat rgb(255, 255, 255); position: absolute; z-index: 1000; display: none;" id="employee_share_frame_div">
		<iframe width="500" height="150" id="employee_share_frame"></iframe>
    </div>
	
<%@include file="/WEB-INF/html/pager.jsp" %>
<%@include file="/WEB-INF/html/foot.jsp" %>
