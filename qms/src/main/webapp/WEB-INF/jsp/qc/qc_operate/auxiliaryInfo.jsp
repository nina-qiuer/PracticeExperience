<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>质检-辅助信息</title>
</head>
<body>
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
			<th>品牌：</th>
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
	</table>
</div>
</c:if>
</body>
</html>
