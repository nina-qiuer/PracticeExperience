<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>质检-辅助信息</title>

<script type="text/javascript">

$(document).ready(function() {
	
	var flag ="${complaintBill.qcFlag}";
	if(flag!=1){
		
		$("input[name='twoBtn']").attr("disabled",true);
		$("input[name='oneBtn']").attr("disabled",true);
		$("input[name='twoBtn']").removeClass('blue');
		$("input[name='oneBtn']").removeClass('blue');
	}else{
		
		$("input[name='twoBtn']").removeAttr('disabled','disabled');
		$("input[name='oneBtn']").removeAttr('disabled','disabled');
		$("input[name='twoBtn']").addClass('blue');
		$("input[name='oneBtn']").addClass('blue');
	}
	var avlPriceFlag = "${orderBill.avlPriceFlag}";
	if(avlPriceFlag!=0){
		
		$("input[name='savePriceBtn']").attr("disabled",true);
		$("input[name='savePriceBtn']").removeClass('blue');
	}else{
		
		$("input[name='savePriceBtn']").removeAttr('disabled','disabled');
		$("input[name='savePriceBtn']").addClass('blue');
	}
});

function saveAvlGroundPrice(){

	var price =$('#avlGroundPrice').val();
	var ordId ="${orderBill.id}";
	var compar = new RegExp("^0{1}([.]\\d{1,2})?$|^[1-9]\\d*([.]{1}[0-9]{1,2})?$");
	if(!compar.test(price)){
		
	   layer.alert("金额应为整数或最多两位小数",{icon: 2});	
	   return false;
		 
	}
	$.ajax({
		url : 'qc/qcBill/saveAvlGroundPrice',
		data : 
		{
			price : price,
			ordId : ordId
		},
		type : 'post',
		dataType:'json',
		cache: false,
		success : function(result) {
			if(result)
			{
		    	if(result.retCode == "0")
				{
		    		$('#avlGroundPrice').val(price);
		    		layer.msg('保存成功', {icon: 1});
				 }else{
					
					 layer.alert(result.resMsg,{icon: 2});
				}
		     }
		 }
	    });
}

function addQc(cmpId,qcFlag){
	
	var qcId="${qcBillId}";
	var msg="";
	if(qcFlag==3){
		
		 msg = "您确定双方质检吗？";
	}else{
		
		 msg = "您确定研发质检吗？";
	}
	layer.confirm(msg, {icon: 3}, function(index){
		 layer.close(index);
	$.ajax({
		url : 'qc/qcBillRelation/saveCmpToDev',
		data : 
		{
			qcId : qcId,
			qcFlag : qcFlag,
			cmpId: cmpId
		},
		type : 'post',
		dataType:'json',
		cache: false,
		success : function(result) {
			if(result)
			{
		    	if(result.retCode == "0")
				{
		    		if(qcFlag==2){
		    			
		    			window.parent.parent.opener.search();
			    		window.top.close(); 
		    			
		    		}else{
		    			
		    			parent.parent.location.reload();
		    		}
		    	
		    		
				 }else{
					
					 layer.alert(result.resMsg,{icon: 2});
				}
		     }
		 }
	    });
	});	
}

function openWinow(title, url, width, height,top,left) {
	parent.layer.open({
        type: 2,
        shade: false,
        maxmin: true,
        title: title,
        content: url,
        offset : [top ,left],
        area: [width+'px', height+'px']
    });
}

/**
 * 完结改进报告
 */
function finishImprove(impId, input){
	if(window.confirm( "确定完结吗？ ")){
		$.ajax({
			url : "qs/cmpImprove/finishImproveBill",
			data : {
				"id" : impId
			},
			type : 'post',
			dataType:'json',
			success : function(result) {
		    	if(result.retCode == "0"){
		    		parent.searchReaload();
		    		parent.layer.closeAll();
				 }else{
					layer.alert(result.resMsg, {icon: 2});
				}
			}
		});
	}
}
</script>
</head>
<body>
<form name="aux_form" id="aux_form" method="post" action="">
<c:if test="${product.id >0}">
<div class="common-box">
	<div class="common-box-hd">
		<span class="title2">产品信息</span>
	</div>
	<table class="datatable" width="100%">
		<tr>
			<th>产品编号：</th>
			<td class="prdId">${product.id}</td>
			<th>产品名称：</th>
			<td colspan="7">${product.prdName}</td>
		</tr>
		<tr>
			<th>品类：</th>
			<td>${product.cateName}</td>
			<th>子品类：</th>
			<td>${product.subCateName}</td>
			<th style="color: orangered;" width="100">品牌：</th>
			<td>${product.brandName}</td>
			<th>产品线目的地：</th>
			<td>${product.prdLineDestName}</td>
			<th>目的地大类：</th>
			<td>${product.destCateName}</td>
		</tr>
		<tr>
			<th>事业部：</th>
			<td>${product.businessUnitName}</td>
			<th>产品部：</th>
			<td>${product.prdDepName}</td>
			<th>产品组：</th>
			<td>${product.prdTeamName}</td>
			<th>产品经理：</th>
			<td>${product.prdManager}</td>
			<th>产品专员：</th>
			<td>${product.producter}</td>
		</tr>
	</table>
</div>
</c:if>
<c:if test="${orderBill.id>0}">
<div class="common-box">
	<div class="common-box-hd">
		<span class="title2">订单信息</span>
	</div>
	<table class="datatable" width="100%">
		<tr>
			<th>订单号：</th>
			<td class="orderId">${orderBill.id}</td>
			<th>订单价格：</th>
			<td>途牛成人价：${orderBill.prdAdultPrice}，机票价格：${orderBill.flightPrice}</td>
			<th>出游人数：</th>
			<td>${orderBill.adultNum}大&nbsp;${orderBill.childNum}小</td>
			<th>出游日期：</th>
			<td>${orderBill.departDate}</td>
			<th>归来日期：</th>
			<td>${orderBill.returnDate}</td>
		</tr>
		<tr>
			<th>出发地：</th>
			<td>${orderBill.departCity}</td>
			<th>售前客服：</th>
			<td>${orderBill.salerName}</td>
			<th>客服经理：</th>
			<td>${orderBill.salerManagerName}</td>
			<th>产品专员：</th>
			<td>${orderBill.producter}</td>
			<th>产品经理：</th>
			<td>${orderBill.prdManager}</td>
		</tr>
		<tr>
		    <th style="color: orangered;">客户星级：</th>
		    <td>${orderBill.custLevel}</td>
		    <th>导游编号：</th>
		    <td>${orderBill.guideId}</td>
		    <th>导游姓名：</th>
		    <td>${orderBill.guideName}</td>
		    <th>导游电话：</th>
		    <td colspan="3">${orderBill.guideCall}</td>
		</tr>
		<tr>
		    <th>地接成本价：</th>
		    <td>${orderBill.groundPrice}</td>
		    <th>出行天数：</th>
		    <td>${orderBill.departDay}</td>
		    <th>日均地接成本价：</th>
		    <td><input type="text" name="avlGroundPrice" id="avlGroundPrice" value="${orderBill.avlGroundPrice}">
		    <input type="button" name="savePriceBtn" id="savePriceBtn" class="blue" value="保存" onclick="saveAvlGroundPrice()"></td>
		    <td colspan="4"></td>
		</tr>
	</table>
	<table width="100%">
		<tr>
			<td colspan="5" width="20%" valign="top">
				<table class="listtable">
					<tr>
						<th>运营专员</th><th>运营经理</th>
					</tr>
					<c:forEach items="${orderBill.operators}" var="oper">
					<tr>
						<td>${oper.operName}</td><td>${oper.managerName}</td>
					</tr>
					</c:forEach>
				</table>
			</td>
			<td colspan="5" width="80%" valign="top">
				<table class="listtable">
					<tr>
						<th>供应商ID</th><th>供应商名称</th><th>供应商准入时间</th><th>供应商owner</th><th>签约公司</th>
					</tr>
					<c:forEach items="${orderBill.angencies}" var="agency">
					<tr>
						<td>${agency.agencyId}</td>
						<td>${agency.agencyName}</td>
						<td>${agency.agencyAccessTime}</td>
						<td>${agency.agencyOwner}</td>
						<td>${agency.signCompany}</td>
					</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
	</table>
</div>
</c:if>
<c:if test="${complaintBill.id>0}">
<div class="common-box">
	<div class="common-box-hd">
		<span class="title2">投诉单信息</span>
	</div>
	<table class="datatable" width="100%">
		<tr> 
			<th width="100">投诉单号：</th>
			<td class="cmpId" width="85">${complaintBill.id}</td>
			<th style="color: orangered;" width="100">投诉级别：</th>
			<td>${complaintBill.cmpLevel}　
				<input  type="button" value="修改投诉等级" name="updateLevelBtn" id="updateLevelBtn" class="blue" style="width:100px" 
					onclick="openWin('修改投诉等级', 'qc/qcBill/toLevel?qcId=${qcBillId}',  300, 117)">
			</td>
			<th width="120">投诉时间：</th>
			<td><fmt:formatDate value="${complaintBill.addTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
			<th width="120">投诉处理完成时间：</th>
			<td><fmt:formatDate value="${complaintBill.finishTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
		</tr>
		<tr>
			<th>处理人：</th>
			<td>${complaintBill.dealPerson}</td>
			<th>对客赔偿总额：</th>
			<td>${complaintBill.indemnifyAmount} 元</td>
			<th>供应商赔付总额：</th>
			<td>${complaintBill.claimAmount} 元</td>
			<th style="color: orangered;">公司损失：</th>
			<td>${complaintBill.companyLose} 元</td>
		</tr>
		<tr>
			<th>理论赔付金额：</th>
			<td>${complaintBill.theoryPayOutAmount} 元</td>
			<th style="color: orangered;">投诉来源：</th>
			<td>${complaintBill.comeFrom}</td>
			<th>对客赔付理据：</th>
			<td>${complaintBill.payBasis}</td>
			<td><input type="button" class="blue" name="twoBtn" id="twoBtn" value="双方质检" onclick="addQc(${complaintBill.id},3)"></td>
			<td><input type="button"  class="blue"  name="oneBtn" id="oneBtn" value="研发质检" onclick="addQc(${complaintBill.id},2)"></td>
		</tr>
	</table>
</div>
</c:if>
<c:if test="${agencyList!=null&& fn:length(agencyList)>0}">
<div class="common-box">
	<div class="common-box-hd">
		<span class="title2">供应商赔付信息</span>
	</div>
	<table class="listtable" width="100%">
	<tr> 
			<th width="100">供应商ID</th>
			<th width="200">供应商名称</th>
			<th width="120">索赔总额</th>
			<th width="100">确认状态</th>
			<th>索赔理据</th>
	</tr>
	<c:forEach items="${agencyList}" var="v">
	<tr>
		<td>${v.agencyId}</td>
		<td class="left">${v.agencyName}</td>
		<td>${v.localCurrencyAmount} 元</td>
    	<td>${v.confirmState}</td>
    	<td class="left">${v.claimBasis}</td>
	</tr>
	</c:forEach>
	</table>
</div>
</c:if>
<c:if test="${complaintBill.reasonList!=null && fn:length(complaintBill.reasonList)>0}">
<div class="common-box">
	<div class="common-box-hd">
		<span class="title2">投诉事宜</span>
	</div>
	<table class="listtable" width="1000px">
		<tr>
			<th>投诉事宜一级分类</th>
			<th>投诉事宜二级分类</th>
			<th>投诉事宜</th>
			<th>备注</th>
		</tr>
		<c:forEach items="${complaintBill.reasonList}" var="v">
		<tr>
			<td width="120">${v.type}</td>
			<td width="120">${v.secondType}</td>
			<td class="left">${v.typeDescript}</td>
			<td class="left">${v.descript}</td>
		</tr>
		</c:forEach>
	</table>
</div>
</c:if>
<c:if test="${recordList!=null&& fn:length(recordList)>0}">
<div class="common-box">
	<div class="common-box-hd">
		<span class="title2">投诉跟进记录</span>
	</div>
	<table class="listtable" width="100%">
	<tr align="center"> 
			<th width="130">跟进时间</th>
			<th width="60">处理人</th>
			<th>跟进内容</th>
	</tr>
	<c:forEach items="${recordList}" var="v">
	<tr align="center">
		<td>${v.add_time}</td>
		<td>${v.addUserName}</td>
		<td class="shorten200">${v.note}</td>
	</tr>
	</c:forEach>
	</table>
</div>
</c:if>

<c:if test="${cmpImproveList != null && fn:length(cmpImproveList)>0}">
	<div class="common-box">
		<div class="common-box-hd">
			<span class="title2">投诉改进报告</span>
		</div>
		<c:forEach items="${cmpImproveList}" var="cmpImprove">
			<table class="datatable" width="100%">
				<tr>
					<th width="8%" style="color:red">改进报告单号：</th>
					<td width="8%">${cmpImprove.id}</td>
					<th width="8%">责任人：</th>
					<td width="12%">${cmpImprove.impPerson}</td>
					<th width="8%">处理人：</th>
					<td width="12%">${cmpImprove.handlePerson}</td>
					<th width="8%">报告状态：</th>
					<td width="12%">${cmpImprove.stateStr}</td>
					<th width="8%">流程处理到期时间：</th>
			  		<td width="12%"><fmt:formatDate value="${cmpImprove.handleEndTime}" pattern="yyyy-MM-dd"/></td>
			  		
				</tr>
				<tr>
					<th>是否有责：</th>
					<td>
						<c:if test="${cmpImprove.isRespFlag == 0 }">
							有责
						</c:if>
						<c:if test="${cmpImprove.isRespFlag == 1 }">
							无责
						</c:if>
					</td>
					<th>其他责任人：</th>
					<td>${cmpImprove.otherPerson}</td>
					<th>责任供应商：</th>
					<td>${cmpImprove.otherAgencyName}</td>
					<th>预计改进完成时间：</th>
					<td><p><fmt:formatDate value="${cmpImprove.improveFinTime}" pattern="yyyy-MM-dd"/></p></td>
					<th>操作：</th>
					<td>
						<c:if test="${cmpImprove.state == 1 && fn:contains(loginUser_WCS,'IMPROVE_BILL_OPERAT')}">
			  				<input type="button" class="blue" name="Btn" value="改进报告定责"
			  					onclick="openWinow('更新改进报告', 'qs/cmpImprove/${cmpImprove.id}/toUpdate', 820, 520, '20%','30%')">
			  		 	</c:if>
		  		 		<c:if test="${cmpImprove.state == 3 && fn:contains(loginUser_WCS,'IMPROVE_BILL_CREATE')}">
		  		 			<input type="button" class="blue" name="Btn"  value="改进报告发起"
		  		 				onclick="openWinow('发起改进报告','qs/cmpImprove/toAdd', 820, 620, '20%','30%')">
		  		 		</c:if>
		  		 		<c:if test="${cmpImprove.state == 3 && fn:contains(loginUser_WCS,'IMPROVE_BILL_OPERAT')}">
		  		 			<input type="button" class="blue" name="Btn"  value="完结"
		  		 				onclick="finishImprove(${cmpImprove.id}, this)">
			  		 	</c:if>
                	</td>
				</tr>
				<tr>
					<th>需要改进信息：</th>
					<td colspan="9">
						<table class="datatable">
							<tr>
								<th style="text-align:center">投诉事宜</th>
								<th style="text-align:center">改进点</th>
								<th style="text-align:center">附件</th>
							</tr>
							<tr>
								<td width="33%">${cmpImprove.cmpAffair}</td>
								<td width="33%">${cmpImprove.improvePoint}</td>
								<td width="33%">
									<c:forEach items="${cmpImprove.attachList}" var="upload" >
										<a href="${upload.path}" target="_blank">${upload.name}</a>
									</c:forEach>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<th>改进结论</th>
					<td colspan="9">
						<table class="datatable">
							<tr>
								<th style="text-align:center">改进措施</th>
								<th style="text-align:center">预计改进结果</th>
								<th style="text-align:center">备注</th>
							</tr>
							<tr>
								<td width="33%">${cmpImprove.improveMethod}</td>
								<td class="33%">${cmpImprove.improveResult}</td>
								<td class="33%">${cmpImprove.remark}</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<br>
		</c:forEach>
	</div>
</c:if>
</form>
</body>
</html>
