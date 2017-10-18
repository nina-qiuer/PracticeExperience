<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp"%>
<style type="text/css">
.common-box {
	border: 1px solid #8CBFDE;
	margin:0 0 10px 0;
	width:800px;
	margin-left: auto;
	margin-right: auto;
}
.common-box-hd {
	margin-top: 1px;
	padding-left: 10px;
	background: #C6E3F1;
	color: #005590;
	font-size: 14px;
	font-weight: bold;
	height: 25px;
	line-height: 25px;
	border-bottom: 1px solid #8CBFDE;
	position: relative;
}
.common-box-hd span.title2 {
	cursor: pointer;
	display: inline-block;
	line-height: 25px;
	margin-right: 15px;
}
</style>

<script type="text/javascript" src="${CONFIG.res_url}/script/jquery/plugin/jquery.form.js"></script>
<script type="text/javascript">
$(document).ready(function() { 
    var options = { 
        beforeSubmit:  check,  // pre-submit callback 
        success:       success_function,  // post-submit callback 
    }; 
    // bind form using 'ajaxForm' 
    $('#reply_form').ajaxForm(options); 
});
//表单验证
function check(){
	var content = $("#reply_content").val();
	if(content == ''){
		alert("反馈核实信息不可为空。");
		return false;
	}
	
	$('#button').attr('disabled' , 'true');
	
	return true;
}

function success_function(){
	alert('发送成功');
	location.replace(location.href);
}
</script>
</HEAD>
<BODY>
<div class="common-box">
	<div class="common-box-hd">
		<span class="title2">投诉情况说明单</span>
	</div>
	<table width="100%" class="bgnavy">
		<tr>
			<td class="pl10">订单状态：<span class="cred">${complaint.orderState}</span>
			</td>
			<td>订单类型：<span class="cred">${complaint.orderType }</span></td>
			<td>线路类型：<span class="cred">${complaint.routeType }</span></td>
			<td>订单来源：<span class="cred">${request.complaint.orderComeFrom}</span></td>
		</tr>
	</table>
	<table width="100%" class="datatable">
		<tr>
			<th width="100" align="right">投诉来源：</th>
			<td>${complaint.comeFrom}</td>

			<th width="100" align="right">投诉级别：</th>
			<td colspan="3">${complaint.level}级</td>
		</tr>
		<tr>
			<th align="right">订单号：</th>
			<td>${complaint.orderId}</td>
			<th align="right">出发地：</th>

			<td>${complaint.startCity}</td>
			<th width="100" align="right">线路：</th>
			<td>${complaint.route}</td>
		</tr>
		<tr>
			<th align="right">客户姓名：</th>
			<td>${complaint.guestName}</td>

			<th align="right">人数：</th>
			<td colspan="3">${complaint.guestNum}</td>
		</tr>
		<tr>
			<th align="right">售前客服：</th>
			<td>${complaint.customer}</td>
			<th align="right">客服经理：</th>

			<td colspan="3">${complaint.customerLeader}</td>
		</tr>
		<tr>
			<th align="right">产品专员：</th>
			<td>${complaint.producter}</td>
			<th align="right">产品经理：</th>
			<td>${complaint.productLeader}</td>

			<th align="right">高级经理：</th>
			<td>${complaint.seniorManager}</td>
		</tr>
		<!--  
		<tr>
			<th align="right">投诉事宜：</th>
			<td colspan="5"><p>
					<span class="mr20">住宿-周边环境</span>周边有个垃圾站，气味难闻
				</p></td>

		</tr>
		-->
		<tr>
			<th align="right">其他说明：</th>
			<td colspan="5">${complaint.descript}</td>
		</tr>
		<tr>
			<th align="right">客人要求：</th>

			<td colspan="5">${complaint.requirement}</td>
		</tr>
		<tr>
			<th align="right">发起人：</th>
			<td>${complaint.ownerName}</td>
			<th>日期：</th>
			<td colspan="3"><fmt:formatDate value="${complaint.buildDate}"
					pattern="yyyy-MM-dd" /></td>

		</tr>
	</table>
</div>

<div class="common-box">
	<div class="common-box-hd">
		<span class="title2">投诉事宜记录</span>
	</div>
	<table width="100%" class="listtable">
	<!--  
		<tr>
			<td>添加时间</td>
			<td>投诉类别</td>
			<td>投诉详细</td>
			<td>备注</td>
		</tr>
	-->
		<c:forEach items="${complaint_reason }" var="v">
			<tr align="center">
				<td width="130"><fmt:formatDate value="${v.addTime}"
						pattern="yyyy-MM-dd HH:mm:ss" /></td>

				<td align="left">${v.type }-${v.secondType }</td>
				<td align="left">${v.typeDescript}</td>
				<td align="left">${v.descript }</td>
			</tr>
		</c:forEach>
	</table>
</div>

<div class="common-box">
	<div class="common-box-hd">
		<span class="title2">投诉核实记录</span>
	</div>
	<c:forEach items="${request.check_mail_list }" var="v">
		<table class="bgnavy f13 fb" width="100%">
			<tr>
				<td width="90" class="pl10">${v.sender}</td>
				<td><fmt:formatDate value="${v.buildDate}"
						pattern="yyyy-MM-dd HH:mm:ss" /></td>
				<c:if test="${v.mark==0}">
					<td>发起核实请求</td>
					<td>发送至：${v.address}</td>
					<td></td>
				</c:if>
				<c:if test="${v.mark==1}">
					<td width="150">反馈核实请求</td>
					<td></td>
					<td></td>
				</c:if>
			</tr>
		</table>

		<table width="100%" class="datatable">
			<tr>
				<th width="100" align="right">内容：</th>
				<!-- <td>${v.content}</td> -->
				<td>${v.content}</td>
			</tr>
		</table>
	</c:forEach>
</div>

<div class="common-box">
	<div class="common-box-hd">
		<span class="title2">反馈内容：</span>
	</div>
	<form id="reply_form" method="post" enctype="multipart/form-data"  action="check_email-saveReply">
	<input name="entity.complaintId" type="hidden" value="${entity.complaintId}"/>
	<input name="entity.id" type="hidden" value="${entity.id}"/>
	<input name="entity.sender" type="hidden" value="${entity.sender}"/>
	<table class="datatable" width="100%">
		<tr>
			<td>
				<textarea name="entity.content" id="reply_content" cols="94" rows="5"></textarea>
			</td>
		</tr>
		<tr>
			<td>
				<input class="pd5" type="submit" name="button" id="button" value="发送" /> 
			</td>
		</tr>
	</table>
	</form>
</div>
	<%@include file="/WEB-INF/html/foot.jsp"%>