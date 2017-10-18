<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp"%>
<script type="text/javascript" src="${CONFIG.res_url}/script/jquery/plugin/jquery.form.js"></script>
<style type="text/css">
.common-box {
	border: 1px solid #8CBFDE;
	margin:0 0 10px 0;
}
</style>
<script type="text/javascript">
$(document).ready(function() {
	var succFlag = ${succFlag};
    if (1 == succFlag) {
    	alert('提交成功！');
    	parent.location.replace(parent.location.href);
    }
});

function submitSolution(complaintId){
	var param = {"entity.complaintId":complaintId};
	$.ajax({
	type: "POST",
	async:false,
	url: "complaint_solution-submitSolution",
	data: param,
	success: function(data){
			alert(data.msg);
			
			if(data.success){
				parent.location.replace(parent.location.href);
			}
     }
   });
}
</script>

</HEAD>
<BODY>
<c:if test="${shareTotal > 0}">
<div class="common-box">
	<span style="color: navy;">分担方案已提交，分担总额为：${shareTotal } 元</span>
</div>
</c:if>
<div class="common-box">
<table class="datatable" width="100%">
	<tr>
		<th width="100" align="right" colspan="2">对客赔偿总额：</th>
		<td><span id="payTotal">${entity.cash + entity.touristBook}</span> 元 <font color="red">（赔偿总额 = 现金 + 旅游券）</td>
	</tr>
	<tr>
		<th width="100" align="right" colspan="2">联系人：</th>
		<td>${entity.userName }</td>
	</tr>
	<tr>
		<th width="100" align="right" colspan="2">联系手机：</th>
		<td>${entity.phone }</td>
	</tr>
	<c:if test="${entity.cash > 0}">
	<tr>
		<c:if test="${1 == entity.payment}"><th id="th_cash" rowspan="4"></c:if>
    	<c:if test="${2 == entity.payment}"><th id="th_cash" rowspan="7"></c:if>
    	<c:if test="${3 == entity.payment}"><th id="th_cash" rowspan="7"></c:if>
    	<c:if test="${25 == entity.payment}"><th id="th_cash" rowspan="3"></c:if>
		赔<br>偿<br>现<br>金</th>
		<th width="100" align="right">金额：</th>
		<td>${entity.cash } 元
		<c:if test="${entity.refundId > 0}">
			（财务状态：<span style="color: blue;">${entity.refundState }</span>）
		</c:if>
		</td>
	</tr>
	<tr>
		<th width="100" align="right">退款方式：</th>
	    <td>
	    	<c:if test="${1 == entity.payment}">现金</c:if>
	    	<c:if test="${2 == entity.payment}">对私汇款</c:if>
	    	<c:if test="${3 == entity.payment}">对公汇款</c:if>
	    	<c:if test="${25 == entity.payment}">转单</c:if>
	    </td>
	</tr>
	<c:if test="${1 == entity.payment}">
	<tr>
		<th width="100" align="right">收款人：</th>
	    <td>${entity.receiver }</td>
	</tr>
	<tr>
		<th width="100" align="right">身份证号码：</th>
	    <td>${entity.idCardNo }</td>
	</tr>
	</c:if>
   	<c:if test="${2 == entity.payment}">
   	<tr>
		<th width="100" align="right">收款人：</th>
	    <td>${entity.collectionUnit }</td>
	</tr>
	
	<tr>
		<th width="100" align="right">银行名称：</th>
	    <td>${entity.bigBank }</td>
	</tr>
	
	<tr>
	    <th width="100" align="right">银行省份城市：</th>
           <td>${entity.bankProvince }省 ${entity.bankCity }市 </td>
	</tr>
	<tr>
	    <th width="100" align="right">分行名称：</th>
           <td>${entity.bank }</td>
	</tr>
	<tr>
	    <th width="100" align="right">账号：</th>
           <td>${entity.account }</td>
	</tr>
   	</c:if>
   	<c:if test="${3 == entity.payment}">
   	<tr>
		<th width="100" align="right">收款单位：</th>
	    <td>${entity.collectionUnit }</td>
	</tr>
	<tr>
		<th width="100" align="right">银行名称：</th>
	    <td>${entity.bigBank }</td>
	</tr>
	<tr>
	    <th width="100" align="right">银行省份城市：</th>
           <td>${entity.bankProvince }省 ${entity.bankCity }市 </td>
	</tr>
	<tr>
	    <th width="100" align="right">开户银行：</th>
           <td>${entity.bank }</td>
	</tr>
	<tr>
	    <th width="100" align="right">账号：</th>
           <td>${entity.account }</td>
	</tr>
   	</c:if>
   	<c:if test="${25 == entity.payment}">
   	<tr>
		<th width="100" align="right">订单号：</th>
	    <td>${entity.toOrderId }</td>
	</tr>
   	</c:if>
	</c:if>
	<tr>
		<th align="right" colspan="2">赔偿旅游券：</th>
		<td>
		    <table>
		        <tr>
		            <th width="150">手机号</th>
		            <th>金额</th>
		        </tr>
		        <c:forEach items="${entity.tourticketList }" var="tour">
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
	<tr>
		<th align="right" colspan="2">赔偿抵用券：</th>
		<td>
		    <table>
		        <tr>
		            <th width="170">手机号</th>
		            <th width="50">会员号</th>
		            <th width="170">金额</th>
		        </tr>
		        <c:forEach items="${entity.voucherList }" var="voucher">
		        <tr>
		            <td>${voucher.mobileNo }</td>
		            <td>${voucher.custId }</td>
		            <td>${voucher.amount } 元</td>
		        </tr>
		        </c:forEach>
		    </table>
		</td>
	</tr>
	<tr>
	    <th align="right" colspan="2">赠送礼品：</th>
		<td>
			<c:forEach items="${entity.giftInfoList }" var="giftInfo">
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
	<tr>
		<th width="100" align="right" colspan="2">时间要求：</th>
	    <td><fmt:formatDate value="${entity.appointedTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>
	<tr>
		<th width="100" align="right" colspan="2">备注：</th>
	    <td>${entity.descript }</td>
	</tr>
	<tr>
		<th width="100" align="right" colspan="2">是否满意完结：</th>
	    <td>
	    	<c:if test="${entity.satisfactionFlag==1 }">满意</c:if>
	    	<c:if test="${entity.satisfactionFlag==0 }">不满意</c:if>
	    </td>
	</tr>
	<!-- 
	<tr>
			<th width="100" align="right" colspan="2">邮件提醒：</th>
			<td>
				<c:if test="${sendEmail==1 }">是</c:if>
	    	    <c:if test="${sendEmail==0 }">否</c:if>
			</td>
		</tr>
		<c:if test="${sendEmail==1 }">
		<tr id="tr_emailType" class="emailType_1 emailType_2 emailType_3">
			<th width="100" align="right" colspan="2">邮件类型：</th>
			<td>
			<c:if test="${cseEntity.emailType==1 }">结单回复</c:if>
	    	<c:if test="${cseEntity.emailType==2 }">受伤报备</c:if>
	    	<c:if test="${cseEntity.emailType==3 }">集体投诉</c:if>
			</td>
		</tr>
		<tr id="tr_emailName" class="emailType_1 emailType_2 emailType_3">
			<th width="100" align="right" colspan="2">邮件名称：</th>
			<td>${cseEntity.emailName}</td>
		</tr>
		<tr id="tr_route" class="emailType_1 emailType_2 emailType_3">
			<th width="100" align="right" colspan="2">线路名称：</th>
			<td>${cseEntity.route}</td>
		</tr>
		<tr id="tr_orderId" class="emailType_1 emailType_2 emailType_3">
			<th width="100" align="right" colspan="2">订单号：</th>
			<td>${cseEntity.orderId}</td>
		</tr>
		<tr id="tr_agencyName" class="emailType_1 emailType_2 emailType_3">
			<th width="100" align="right" colspan="2">供应商名称（多个用英文,分隔）：</th>
			<td>${cseEntity.agencyName}</td>
		</tr>
		<tr id="tr_startDate" class="emailType_1 emailType_2 emailType_3">
			<th width="100" align="right" colspan="2">出游日期：</th>
			<td><fmt:formatDate value="${cseEntity.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		<c:if test="${cseEntity.emailType==2 }">
		<tr id="tr_guestNum"  class="emailType_2">
			<th width="100" align="right" colspan="2">出游人数：</th>
		    <td>${cseEntity.guestNum}</td>
		</tr>
		</c:if>
		<c:if test="${cseEntity.emailType==3 }">
		<tr id="tr_groupOrder" class="emailType_3">
			<th width="100" align="right" colspan="2">同团订单：</th>
		    <td>${cseEntity.groupOrders}</td>
		</tr>
		</c:if>
		<c:if test="${cseEntity.emailType==2 }">
		<tr id="tr_passengerInfo" class="emailType_2">
			<th width="100" align="right" colspan="2">受伤客人信息：</th>
		    <td>${cseEntity.passengerInfo}</td>
		</tr>
		</c:if>
		<tr id="tr_receiveName" class="emailType_1 emailType_2 emailType_3">
			<th width="100" align="right" colspan="2">补充收件人（多个用英文,分隔）：</th>
			<td>${cseEntity.receiveName}</td>
		</tr>
		<tr id="tr_ccName" class="emailType_1 emailType_2 emailType_3">
			<th width="100" align="right" colspan="2">补充抄送人（多个用英文,分隔）：</th>
		    <td>${cseEntity.ccName}</td>
		</tr>
		<tr id="tr_remark" class="emailType_1 emailType_2 emailType_3">
			<th width="100" align="right" colspan="2">客人投诉点：</th>
		    <td>
		    	${cseEntity.remark}
            </td>
		</tr>
		<tr id="tr_checkProgress" class="emailType_1 emailType_2 emailType_3">
			<th width="100" align="right" colspan="2">核实处理情况：</th>
		    <td>
		    	${cseEntity.checkProgress}
            </td>
		</tr>
		<c:if test="${cseEntity.emailType==1 }">
		<tr id="tr_makeBetter" class="emailType_1">
			<th width="100" align="right" colspan="2">建议改进点：</th>
		    <td>
		    	${cseEntity.makeBetter}
            </td>
		</tr>
		</c:if>
		</c:if>
		 -->
	<c:if test="${'submitConfirm' != pageInfo}">
	<tr>
		<th width="100" align="right" colspan="2">审核状态：</th>
	    <td>
	    	<span style="color: blue;">
	    	<c:if test="${entity.auditFlag==-1}">无需审核</c:if>
			<c:if test="${entity.auditFlag==0}">待初审</c:if>
			<c:if test="${entity.auditFlag==1}">已初审</c:if>
			<c:if test="${entity.auditFlag==2}">已复审（一）</c:if>
			<c:if test="${entity.auditFlag==3}">已复审（二）</c:if>
			<c:if test="${entity.auditFlag==5}">已复审（三）</c:if>
			<c:if test="${entity.auditFlag==4}">通过审核</c:if>
			</span>
	    </td>
	</tr>
	</c:if>
	<c:if test="${'submitConfirm' == pageInfo}">
	<tr>
		<th colspan="2">&nbsp;</th>
		<td>
			<!-- 
			 <input class="pd5" type="button" value="确认提交" onclick="window.location.href='complaint_solution-submitSolution?entity.complaintId=${entity.complaintId }'">　
			-->
			<input class="pd5" type="button" value="确认提交" onclick="submitSolution(${entity.complaintId })">　
			
			<input class="pd5" type="button" value="取消" onclick="parent.location.replace(parent.location.href)">
		</td>
	</tr>
	</c:if>
</table>
</div>
</BODY>
