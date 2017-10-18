<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>退款单信息</title>
<style type="text/css">
.common-box {
	border: 1px solid #8CBFDE;
	margin:0 0 10px 0;
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
<script type="text/javascript">
$(function(){
	$("input.pd5").attr("disabled",false);
})

function auditPass(flag,complaintId,auditFlag){
	$("input.pd5").attr("disabled",true)
	 /* setTimeout(function(){
				$("input.pd5").attr("disabled",false)
				},2000) */
	if ("complaint" == flag || "share" == flag) {
		if (!confirm("确认通过审核？")) {
			$("input.pd5").attr("disabled",false);
			return false;
		}
	}

	if ("returnComplaint" == flag || "returnShare" == flag) {
		if (!confirm("确认退回？")) {
			$("input.pd5").attr("disabled",false);
			return false;
		}
	}

	var backMsg = $("#backMsg").val();
	var param = {"flag":flag,"complaintNum":complaintId,"auditFlag":auditFlag,"backMsg":backMsg,"tFlag":'${tab_flag}'};
	$.ajax({
	type: "POST",
	async:false,
	url: "claims_audit-auditPass",
	data: param,
	success: function(data){
			alert(data.msg);
			window.location.reload();
     }
   });
}

function hve_display(t_id){//显示隐藏程序
	var tr = document.getElementById(t_id);//表格ID
	if (tr.style.display == "none"){
		tr.style.display="";//切换为显示状态
	}else{
		tr.style.display="none";//切换为隐藏状态
	}
}
</script>
</HEAD>
<body>
<c:if test="${no!=null && no!=''}" var="seeAll">
	没有权限
</c:if>
<b><font color="orange">订单号：${orderId}，投诉单号：${complaintId}</font></b>
<c:if test="${!seeAll}">
<div class="common-box" onclick="hve_display('notable')">
	<div class="common-box-hd" >
			<span class="title2">投诉处理跟进记录：</span>
	</div>
	<table width="100%" class="datatable" id="notable" style="display: none">
		<tr>
			<th width="150" align="center">跟进时间</th>
			<th colspan="11">内容要点</th>
		</tr>
		<c:forEach items="${request.followUpRecordList }" var="v">
		<tr>
			<td width="150" align="center"><fmt:formatDate value="${v.addTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
			<td colspan="11">${v.note}</td>
		</tr>
		</c:forEach>
	</table>
</div>

<div class="common-box" onclick="hve_display('attachTable')">
	<div class="common-box-hd" >
			<span class="title2">附件列表：</span>
	</div>
	<table class="listtable" id="attachTable" width="100%" style="display: none">
	<thead>
		<th width="40%">附件名称</th>
		
		<th width="20%">上传时间</th>
		
		<th width="20%">上传人</th>
		
		<th>备注</th>
	</thead>
	<tbody>
		<c:forEach items="${request.attachList}" var="v"  varStatus="st"> 
		<tr align="center">
			<td><a href="${v.path}">${v.name}</a></td> 
			
			<td><fmt:formatDate value="${v.updateTime}" pattern="yyyy-MM-dd hh:mm:ss" /></td> 
			
			<td>${v.addPerson}</td> 
			
			<td>${v.descript}</td> 
		</tr>
		</c:forEach>
	</tbody>
</table>
</div>

<c:if test="${payInfoList != null && payInfoList.size() > 0}">
<div class="common-box">
	<div class="common-box-hd">
		<c:if test="${orderId > 0}">
			<span class="title2">订单相关赔付信息</span>
		</c:if>
		<c:if test="${orderId == 0}">
			<span class="title2">电话相关赔付信息</span>
		</c:if>
	</div>
	<table width="100%" class="listtable" style="TABLE-LAYOUT: fixed;">
		<tr align="center">
			<th width="120">投诉单号</th>
			<th width="120">处理人</th>
			<th>现金（元）</th>
			<th>旅游券（元）</th>
			<th>抵用券（元）</th>
			<th>礼品（元）</th>
			<th>分担总额（元）</th>
		</tr>
		<c:forEach items="${payInfoList }" var="v">
		<tr align="center">
			<td>
				<c:if test="${complaint.id == v.complaint_id}">
					${v.complaint_id}
				</c:if>
				<c:if test="${complaint.id != v.complaint_id}">
					<a href="complaint-toBill?id=${v.complaint_id}" target="_blank" id="td_${v.complaint_id}">${v.complaint_id}</a>
				</c:if>
			</td>
			<td>${v.deal_name }</td>
			<td>${v.cash }</td>
			<td>${v.tourist_book }</td>
			<td>${v.replace_book }</td>
			<td>${v.gift }</td>
			<td>${v.shareTotal}</td>
		</tr>
		</c:forEach>
	</table>
</div>
</c:if>

<div class="common-box">
	<div class="common-box-hd">
			<span class="title2">对客解决方案信息：</span>
	</div>
	<table width="100%" class="datatable">
		<c:if test="${complaintSolutionEntity==null || complaintSolutionEntity.submitFlag==0}" var="hasComplaintSolution">
			<th width="150" align="right">状态：</th>
			<td colspan="11">暂无对客解决方案</td>
		</c:if>
		<c:if test="${!hasComplaintSolution }">
			<tr>
			<th width="150" align="right">退款类型：</th>
			<td colspan="11">退赔款</td>
		</tr>
		<tr>
			<th width="150" align="right">联系人：</th>
			<td colspan="11">${complaintSolutionEntity.userName}</td>
		</tr>
		<tr>
			<th width="150" align="right">联系手机：</th>
			<td colspan="11">${complaintSolutionEntity.phone}</td>
		</tr>
		<tr>
			<th width="150" align="right">对客人赔偿总额：</th>
			<td colspan="11"><b>${complaintSolutionEntity.cash+complaintSolutionEntity.touristBook}元</b>（包括：现金${complaintSolutionEntity.cash}元 + 抵用券${complaintSolutionEntity.replaceBook}元 + 旅游券${complaintSolutionEntity.touristBook}元 + 礼品${complaintSolutionEntity.gift}元，<font color="red">赔偿总额 = 现金 + 旅游券</font>）</td>
		</tr>
		<c:if test="${complaintSolutionEntity.cash > 0}">
		<tr>
			<th width="150" align="right">现金：</th>
			<td colspan="11">${complaintSolutionEntity.cash}&nbsp;元</td>
		</tr>
		<tr>
			<th width="150" align="right">退款方式：</th>
			<td colspan="11">
				<c:if test="${complaintSolutionEntity.payment==1}">现金</c:if>
				<c:if test="${complaintSolutionEntity.payment==2}">对私汇款</c:if>
				<c:if test="${complaintSolutionEntity.payment==3}">对公汇款</c:if>
				<c:if test="${complaintSolutionEntity.payment==25}">转单</c:if>
			</td>
		</tr>
		<c:if test="${1 == complaintSolutionEntity.payment}">
		<tr>
			<th width="100" align="right">收款人：</th>
		    <td>${complaintSolutionEntity.receiver }
		     <c:if test="${touristFlag == 0 }">
		     &nbsp;&nbsp;&nbsp;<span style="color: red">(收款人非出游人)</span>
		    </c:if>
		    </td>
		   
		</tr>
		<tr>
			<th width="100" align="right">身份证号码：</th>
		    <td>${complaintSolutionEntity.idCardNo }</td>
		</tr>
		</c:if>
	   	<c:if test="${2 == complaintSolutionEntity.payment}">
	   	<tr>
			<th width="100" align="right">收款人：</th>
		    <td>${complaintSolutionEntity.collectionUnit } 
		      <c:if test="${touristFlag == 0 }">
		     &nbsp;&nbsp;&nbsp;<span style="color: red">(收款人非出游人)</span>
		    </c:if>
		    </td>
		</tr>
		<tr>
		    <th width="100" align="right">银行名称：</th>
	           <td>${complaintSolutionEntity.bigBank }</td>
		</tr>
		<tr>
	    <th width="100" align="right">银行省份城市：</th>
           <td>${complaintSolutionEntity.bankProvince }省 ${complaintSolutionEntity.bankCity }市 </td>
		</tr>
		<tr>
		    <th width="100" align="right">分行名称：</th>
	           <td>${complaintSolutionEntity.bank }</td>
		</tr>
		<tr>
		    <th width="100" align="right">账号：</th>
	           <td>${complaintSolutionEntity.account }</td>
		</tr>
	   	</c:if>
	   	<c:if test="${3 == complaintSolutionEntity.payment}">
	   	<tr>
			<th width="100" align="right">收款单位：</th>
		    <td>${complaintSolutionEntity.collectionUnit }</td>
		</tr>
		<tr>
	    <th width="100" align="right">银行省份城市：</th>
           <td>${complaintSolutionEntity.bankProvince }省 ${complaintSolutionEntity.bankCity }市 </td>
		</tr>
		<tr>
		    <th width="100" align="right">开户银行：</th>
	           <td>${complaintSolutionEntity.bank }</td>
		</tr>
		<tr>
		    <th width="100" align="right">账号：</th>
	           <td>${complaintSolutionEntity.account }</td>
		</tr>
	   	</c:if>
	   	<c:if test="${25 == complaintSolutionEntity.payment}">
	   	<tr>
			<th width="100" align="right">订单号：</th>
		    <td>${complaintSolutionEntity.toOrderId }</td>
		</tr>
	   	</c:if>
		</c:if>
		
   	<c:if test="${complaintSolutionEntity.replaceBook > 0}">
   		<tr>
		<th align="right">赔偿抵用券：</th>
		<td>
		    <table>
		        <tr>
		            <th width="170">手机号</th>
		            <th width="50">会员号</th>
		            <th width="170">金额</th>
		        </tr>
		        <c:forEach items="${complaintSolutionEntity.voucherList }" var="voucher">
		        <tr>
		            <td>${voucher.mobileNo }</td>
		            <td>${voucher.custId }</td>
		            <td>${voucher.amount } 元</td>
		        </tr>
		        </c:forEach>
		    </table>
		</td>
	</tr>
   	</c:if>
   	<c:if test="${complaintSolutionEntity.touristBook > 0}">
   		<tr>
		<th align="right">赔偿旅游券：</th>
		<td>
		    <table>
		        <tr>
		            <th width="150">手机号</th>
		            <th>金额</th>
		        </tr>
		        <c:forEach items="${complaintSolutionEntity.tourticketList }" var="tour">
		        <tr>
					<td align="center">${tour.mobileNo }</td>
		            <td>${tour.amount } 元
		            <c:if test="${tour.refundId > 0}">
						（财务状态：<span style="color: blue;">${tour.refundState }</span>）
					</c:if>
		            </td>
				</tr>
		        </c:forEach>
		    </table>
		</td>
	</tr>
   	</c:if>
   	<c:if test="${complaintSolutionEntity.gift > 0}">
   		<tr>
	    <th align="right">赠送礼品：</th>
		<td>
			<c:forEach items="${complaintSolutionEntity.giftInfoList }" var="giftInfo">
			<table width="100%" border="1" cellspacing="0" cellpadding="0">
				<tr>
					<td colspan="6">
						<strong>收件人及礼品信息：</strong>
						<c:if test="${1 == giftInfo.express}">本人领取　　</c:if>
						<c:if test="${2 == giftInfo.express}">快递领取　　</c:if>
						${giftInfo.receiver }　　${giftInfo.phone }　　${giftInfo.address }
					</td>
				</tr>
				<tr>
					<th>礼品分类</th>
					<th>礼品名称</th>
					<th>单价(元)</th>
					<th>数量</th>
					<th>备注</th>
				</tr>
				<c:forEach items="${giftInfo.giftList }" var="gift">
				<tr align="center">
					<td>${gift.type }</td>
					<td>${gift.name }</td>
					<td>${gift.price }</td>
					<td>${gift.number }</td>
					<td>${gift.remark }</td>
				</tr>
				</c:forEach>
			</table>
			</c:forEach>
		</td>
	</tr>
   	</c:if>
		
		<tr>
			<th width="150" align="right">时间要求：</th>
			<td colspan="11"><fmt:formatDate value="${complaintSolutionEntity.appointedTime}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
		</tr>
		<tr>
			<th width="150" align="right">备注：</th>
			<td colspan="11">${complaintSolutionEntity.descript}</td>
		</tr>
		<tr>
			<th width="150" align="right">是否满意完结：</th>
			<td colspan="11">
				<c:if test="${complaintSolutionEntity.satisfactionFlag==1 }">满意</c:if>
	    		<c:if test="${complaintSolutionEntity.satisfactionFlag==0 }">不满意</c:if>
			</td>
		</tr>
		<tr>
			<th width="150" align="right">操作：</th>
			<td colspan="11" id="complaintBut">
				<c:if test="${tab_flag=='menu_2' && complaintSolutionEntity.auditFlag==0 && authoritys.contains('1')}" var="chushen">
				    <input type="button" class="pd5" value="审核通过" onclick="auditPass('complaint',${complaintSolutionEntity.complaintId},${complaintSolutionEntity.auditFlag});">　　
					退回原因：<textarea rows="1" cols="60" id="backMsg"></textarea>&nbsp;
					<input type="button" class="pd5" value="退回" onclick="auditPass('returnComplaint',${complaintSolutionEntity.complaintId},${complaintSolutionEntity.auditFlag});">
				</c:if>
				<c:if test="${tab_flag=='menu_3' && complaintSolutionEntity.auditFlag==1 && authoritys.contains('2')}" var="fushen1">
				    <input type="button" class="pd5" value="审核通过" onclick="auditPass('complaint',${complaintSolutionEntity.complaintId},${complaintSolutionEntity.auditFlag});">　　
					退回原因：<textarea rows="1" cols="60" id="backMsg"></textarea>&nbsp;
					<input type="button" class="pd5" value="退回" onclick="auditPass('returnComplaint',${complaintSolutionEntity.complaintId},${complaintSolutionEntity.auditFlag});">
				</c:if>
				<c:if test="${tab_flag=='menu_4' && complaintSolutionEntity.auditFlag==2 && authoritys.contains('3')}" var="fushen2">
				    <input type="button" class="pd5" value="审核通过" onclick="auditPass('complaint',${complaintSolutionEntity.complaintId},${complaintSolutionEntity.auditFlag});">　　
					退回原因：<textarea rows="1" cols="60" id="backMsg"></textarea>&nbsp;
					<input type="button" class="pd5" value="退回" onclick="auditPass('returnComplaint',${complaintSolutionEntity.complaintId},${complaintSolutionEntity.auditFlag});">
				</c:if>
				<c:if test="${tab_flag=='menu_5' && complaintSolutionEntity.auditFlag==3 && authoritys.contains('5')}" var="fushen3">
				    <input type="button" class="pd5" value="审核通过" onclick="auditPass('complaint',${complaintSolutionEntity.complaintId},${complaintSolutionEntity.auditFlag});">　　
					退回原因：<textarea rows="1" cols="60" id="backMsg"></textarea>&nbsp;
					<input type="button" class="pd5" value="退回" onclick="auditPass('returnComplaint',${complaintSolutionEntity.complaintId},${complaintSolutionEntity.auditFlag});">
				</c:if>
				<c:if test="${tab_flag=='menu_6' && complaintSolutionEntity.auditFlag==5 && authoritys.contains('4')}" var="fushen2">
				    <input type="button" class="pd5" value="审核通过" onclick="auditPass('complaint',${complaintSolutionEntity.complaintId},${complaintSolutionEntity.auditFlag});">　　
					退回原因：<textarea rows="1" cols="60" id="backMsg"></textarea>&nbsp;
					<input type="button" class="pd5" value="退回" onclick="auditPass('returnComplaint',${complaintSolutionEntity.complaintId},${complaintSolutionEntity.auditFlag});">
				</c:if>
			</td>
			<td colspan="11" id="complaintBut2" style="display: none">
				
			</td>
		</tr>
		<tr>
			<th width="150" align="right">状态：</th>
			<td colspan="11"><font color="green">
				<c:if test="${complaintSolutionEntity.auditFlag==null || complaintSolutionEntity.submitFlag==0}">暂无对客方案</c:if>
					<c:if test="${complaintSolutionEntity.auditFlag==-1 && complaintSolutionEntity.submitFlag==1}">无需审核</c:if>
					<c:if test="${complaintSolutionEntity.auditFlag==0}">待初审</c:if>
					<c:if test="${complaintSolutionEntity.auditFlag==1}">已初审</c:if>
					<c:if test="${complaintSolutionEntity.auditFlag==2}">已复审（一）</c:if>
					<c:if test="${complaintSolutionEntity.auditFlag==3}">已复审（二）</c:if>
					<c:if test="${complaintSolutionEntity.auditFlag==5}">已复审（三）</c:if>
					<c:if test="${complaintSolutionEntity.auditFlag==4}">审核完成</c:if></font>
					&nbsp;&nbsp;&nbsp;&nbsp;${ctSolClaimAuditHistory }
			</td>
		</tr>
		</c:if>
	</table>
</div>
<div class="common-box">
	<div class="common-box-hd">
		<span class="title2">分担方案信息：</span>
	</div>
	<table width="100%" class="datatable">
		<c:if test="${shareSolutionEntity==null || shareSolutionEntity.submitFlag==0}" var="hasShareSolutionEntity">
			<th width="150" align="right">状态：</th>
			<td colspan="11">暂无分担方案</td>
		</c:if>
		<c:if test="${!hasShareSolutionEntity}">
			<tr>
			<th width="150" align="right">分担总额：</th>
			<td colspan="11"><b>${shareSolutionEntity.total}元</b></td>
		</tr>
		<c:forEach items="${shareSolutionEntity.supportShareList }" var="v">
		<tr>
			<th width="150" align="right">${v.name}：</th>
			<td colspan="11">
			    <table>
			        <tr>
			            <th width="50" align="right">总额：</th>
			            <td>${v.number}元</td>
			        </tr>
			        <tr>
			            <th align="right">明细：</th>
			            <td>
			                <table>
                                <tr>
                                    <td width="30%">投诉详情</td>
                                    <td width="30%">赔付理据</td>
                                    <td>承担金额</td>
                                </tr>
                                <c:forEach items="${v.agencyPayoutList }" var="payout">
	                                <tr>
	                                    <td>${payout.complaintInfo }</td>
	                                    <td>${payout.payoutBase }<br/></td>
	                                    <td>人民币:${payout.payoutNum }元
										    <c:if test="${payout.foreignCurrencyNumber > 0}">
										    , ${v.foreignCurrencyName }:${payout.foreignCurrencyNumber }元
										    </c:if>
									    </td>
	                                </tr>
                                </c:forEach>
                            </table>
			            </td>
			            
			        </tr>
			    </table>
			</td>
		</tr>
		</c:forEach>
			<tr>
				<th width="150" align="right">订单利润承担总额：</th>
				<td colspan="11">${shareSolutionEntity.orderGains }元</td>
			</tr>
		<tr>
			<th width="150" align="right">员工承担赔偿金额：</th>
			<td colspan="11">
				<c:forEach items="${shareSolutionEntity.employeeShareList }" var="empShare">
					<table border="0" cellpadding="0" cellspacing="0" id="employee_table">
						<tr id="employee_row">
							<td>${empShare.name }</td>
							<td width="100px"></td>
							<td>${empShare.number } 元</td>
							<td align="right"></td>
						</tr>
					</table>
		</c:forEach>
			</td>
		</tr>
		<tr>
			<th width="150" align="right">公司承担：</th>
			<td colspan="11">${shareSolutionEntity.special }元</td>
		</tr>
		<tr>
			<th width="150" align="right">成本类型：</th>
			<td colspan="11">
			    <table border="0" cellpadding="0" cellspacing="0" id="quality_tool_table">
					<c:forEach items="${shareSolutionEntity.qualityToolList }" var="quality">
						<tr>
							<td>${quality.toolName }</td>
							<td>${quality.total } 元</td>
							<td></td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
		<tr>
			<th width="150" align="right">退转赔：</th>
			<td colspan="11">${shareSolutionEntity.refundToIndemnity} 元</td>
		</tr>
		<tr>
			<th width="150" align="right">操作：</th>
			<td colspan="11" id="shareBut">
				<c:if test="${tab_flag=='menu_2' && shareSolutionEntity.auditFlag==0 && authoritys.contains('1')}" var="chushen">
				    <input type="button" class="pd5" value="审核通过" onclick="auditPass('share',${shareSolutionEntity.complaintId},${shareSolutionEntity.auditFlag});">　　
					退回原因：<textarea rows="1" cols="60" id="backMsg"></textarea>&nbsp;
					<input type="button" class="pd5" value="退回" onclick="auditPass('returnShare',${shareSolutionEntity.complaintId},${shareSolutionEntity.auditFlag});">
				</c:if>
				<c:if test="${tab_flag=='menu_3' && shareSolutionEntity.auditFlag==1 && authoritys.contains('2')}" var="fushen1">
				    <input type="button" class="pd5" value="审核通过" onclick="auditPass('share',${shareSolutionEntity.complaintId},${shareSolutionEntity.auditFlag});">　　
					退回原因：<textarea rows="1" cols="60" id="backMsg"></textarea>&nbsp;
					<input type="button" class="pd5" value="退回" onclick="auditPass('returnShare',${shareSolutionEntity.complaintId},${shareSolutionEntity.auditFlag});">
				</c:if>
				<c:if test="${tab_flag=='menu_4' && shareSolutionEntity.auditFlag==2 && authoritys.contains('3')}" var="fushen2">
				    <input type="button" class="pd5" value="审核通过" onclick="auditPass('share',${shareSolutionEntity.complaintId},${shareSolutionEntity.auditFlag});">　　
					退回原因：<textarea rows="1" cols="60" id="backMsg"></textarea>&nbsp;
					<input type="button" class="pd5" value="退回" onclick="auditPass('returnShare',${shareSolutionEntity.complaintId},${shareSolutionEntity.auditFlag});">
				</c:if>
				<c:if test="${tab_flag=='menu_5' && shareSolutionEntity.auditFlag==3 && authoritys.contains('5')}" var="fushen2">
				    <input type="button" class="pd5" value="审核通过" onclick="auditPass('share',${shareSolutionEntity.complaintId},${shareSolutionEntity.auditFlag});">　　
					退回原因：<textarea rows="1" cols="60" id="backMsg"></textarea>&nbsp;
					<input type="button" class="pd5" value="退回" onclick="auditPass('returnShare',${shareSolutionEntity.complaintId},${shareSolutionEntity.auditFlag});">
				</c:if>
				<c:if test="${tab_flag=='menu_6' && shareSolutionEntity.auditFlag==5 && authoritys.contains('4')}" var="zhongshen">
				    <input type="button" class="pd5" value="审核通过" onclick="auditPass('share',${shareSolutionEntity.complaintId},${shareSolutionEntity.auditFlag});">　　
					退回原因：<textarea rows="1" cols="60" id="backMsg"></textarea>&nbsp;
					<input type="button" class="pd5" value="退回" onclick="auditPass('returnShare',${shareSolutionEntity.complaintId},${shareSolutionEntity.auditFlag});">
				</c:if>
			</td>
			<td colspan="11" id="shareBut2" style="display: none">
				
			</td>
		</tr>
		<tr>
			<th width="150" align="right">状态：</th>
			<td colspan="11"><font color="green">
				<c:if test="${shareSolutionEntity.auditFlag==null || shareSolutionEntity.submitFlag==0}">暂无分担方案</c:if>
					<c:if test="${shareSolutionEntity.auditFlag==-1 && shareSolutionEntity.submitFlag==1}">无需审核</c:if>
					<c:if test="${shareSolutionEntity.auditFlag==0}">待初审</c:if>
					<c:if test="${shareSolutionEntity.auditFlag==1}">已初审</c:if>
					<c:if test="${shareSolutionEntity.auditFlag==2}">已复审（一）</c:if>
					<c:if test="${shareSolutionEntity.auditFlag==3}">已复审（二）</c:if>
					<c:if test="${shareSolutionEntity.auditFlag==5}">已复审（三）</c:if>
					<c:if test="${shareSolutionEntity.auditFlag==4}">审核已完成</c:if></font>
					&nbsp;&nbsp;&nbsp;&nbsp;${shareSolClaimAuditHistory }
			</td>
		</tr>
		</c:if>
	</table>
</div>
</c:if>
</body>
</HTML>
