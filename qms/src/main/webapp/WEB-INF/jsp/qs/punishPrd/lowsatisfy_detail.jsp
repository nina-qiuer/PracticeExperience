<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>
<html>
<head>
<style type="text/css">
<!-- tr变黄色 -->
.trbg td{background:#ff9;}
.trbg1 td{background:#87CEFF;}
.listtable tr.yellowbg td{background:#FFFF99}
.listtable tr td.orange{background:orange;}
</style>
</head>
<body>
	<div style="width: 900px; display: block;">
		<div>
			<div  style="width:900px;height:495px">
				<h4>上上周，满意度<span id="detail_last_week_statisfaction">${vo.twoWeeksAgoSasisfaction*100}%</span>，点评详情如下：（<span id="detail_last_week_range">${vo.twoWeeksAgoBgn} 至 ${vo.twoWeeksAgoEnd}</span>）</h4>
				<div id="detail_two_week_ago_remark">
					<table style="width: 850px;border-collapse: collapse;border: 2px solid #99bbe8;margin: auto;table-layout: fixed;">
						<tbody>
							<tr>
								<th style="width:10%;border: 2px solid #99bbe8;text-align: center;">点评时间</th>
								<th style="width:9%;border: 2px solid #99bbe8;text-align: center;">订单号</th>
								<th style="width:9%;border: 2px solid #99bbe8;text-align: center;">线路编号</th>
								<th style="width:9%;border: 2px solid #99bbe8;text-align: center;">出游日期</th>
								<th style="width:7%;border: 2px solid #99bbe8;text-align: center;">满意度</th>
								<th style="width:28%;border: 2px solid #99bbe8;text-align: center;">整体评价</th>
								<th style="width:28%;border: 2px solid #99bbe8;text-align: center;">分项评价</th>
							</tr>
							<c:forEach items="${vo.twoWeeksAgoRemarkList}" var="remark">
							<tr>
								<td style="border: 2px solid #99bbe8;text-align: center;">${remark.remarkTime}</td>
								<td style="border: 2px solid #99bbe8;text-align: center;">${remark.orderId}</td>
								<td style="border: 2px solid #99bbe8;text-align: center;">${remark.routeId}</td>
								<td style="border: 2px solid #99bbe8;text-align: center;">${remark.startTime}</td>
								<td style="border: 2px solid #99bbe8;text-align: center;">${remark.satisfaction}%</td>
								<td style="border: 2px solid #99bbe8;text-align: left;white-space: normal;text-overflow: ellipsis;overflow: hidden;">${remark.compTextContent}</td>
								<td style="border: 2px solid #99bbe8;text-align: left;white-space: normal;text-overflow: ellipsis;overflow: hidden;">${remark.subTextContent}</td>
							</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<br>
				<h4>上周，满意度<span id="detail_statisfaction">${vo.lastWeekSatisfaction*100}%</span>，点评详情如下：（<span id="detail_week_range">${vo.lastWeekBgn} 至 ${vo.lastWeekEnd}</span>）</h4>
				<div id="detail_remark">
					<table style="width: 850px;border-collapse: collapse;border: 2px solid #99bbe8;margin: auto;table-layout: fixed;">
						<tbody>
							<tr>
								<th style="width:10%;border: 2px solid #99bbe8;text-align: center;">点评时间</th>
								<th style="width:9%;border: 2px solid #99bbe8;text-align: center;">订单号</th>
								<th style="width:9%;border: 2px solid #99bbe8;text-align: center;">线路编号</th>
								<th style="width:9%;border: 2px solid #99bbe8;text-align: center;">出游日期</th>
								<th style="width:7%;border: 2px solid #99bbe8;text-align: center;">满意度</th>
								<th style="width:28%;border: 2px solid #99bbe8;text-align: center;">整体评价</th>
								<th style="width:28%;border: 2px solid #99bbe8;text-align: center;">分项评价</th>
							</tr>
							<c:forEach items="${vo.lastWeekRemarkList}" var="remark">
							<tr>
								<td style="border: 2px solid #99bbe8;text-align: center;">${remark.remarkTime}</td>
								<td style="border: 2px solid #99bbe8;text-align: center;">${remark.orderId}</td>
								<td style="border: 2px solid #99bbe8;text-align: center;">${remark.routeId}</td>
								<td style="border: 2px solid #99bbe8;text-align: center;">${remark.startTime}</td>
								<td style="border: 2px solid #99bbe8;text-align: center;">${remark.satisfaction}%</td>
								<td style="border: 2px solid #99bbe8;text-align: left;white-space: normal;text-overflow: ellipsis;overflow: hidden;">${remark.compTextContent}</td>
								<td style="border: 2px solid #99bbe8;text-align: left;white-space: normal;text-overflow: ellipsis;overflow: hidden;">${remark.subTextContent}</td>
							</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<br>
				<!-- 历史记录 -->
				<h4>历史下线记录</h4>
				<div id="detail_remark">
					<table style="width: 850px;border-collapse: collapse;border: 2px solid #99bbe8;margin: auto;table-layout: fixed;">
						<tbody>
							<tr>
								<th style="width:10%;border: 2px solid #99bbe8;text-align: center;">线路编号</th>
								<th style="width:10%;border: 2px solid #99bbe8;text-align: center;">状态</th>
								<th style="width:30%;border: 2px solid #99bbe8;text-align: center;">下线时间</th>
								<th style="width:10%;border: 2px solid #99bbe8;text-align: center;">下线人</th>
								<th style="width:30%;border: 2px solid #99bbe8;text-align: center;">审核时间</th>
								<th style="width:10%;border: 2px solid #99bbe8;text-align: center;">审核人</th>
							</tr>
							<c:forEach items="${vo.offlineHistory}" var="ppe">
							<tr>
								<td style="border: 2px solid #99bbe8;text-align: center;">${ppe.routeId}</td>
								<td style="border: 2px solid #99bbe8;text-align: center;">
									<c:if test="${ppe.status==2}">整改中</c:if>
									<c:if test="${ppe.status==3}">已整改</c:if>
									<c:if test="${ppe.status==4}">永久下线</c:if>
								</td>
								<td style="border: 2px solid #99bbe8;text-align: center;"><fmt:formatDate value="${ppe.offlineOperTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td style="border: 2px solid #99bbe8;text-align: center;">${ppe.offlineOperPerson}</td>
								<td style="border: 2px solid #99bbe8;text-align: center;"><fmt:formatDate value="${ppe.onlineOperTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td style="border: 2px solid #99bbe8;text-align: center;">${ppe.onlineOperPerson}</td>
							</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<br>
				<!-- 未下线记录 -->
				<h4>未下线记录</h4>
				<div id="detail_remark">
						<table style="width: 850px;border-collapse: collapse;border: 2px solid #99bbe8;margin: auto;table-layout: fixed;">
								<tbody>
									<tr>
										<th style="width:10%;border: 2px solid #99bbe8;text-align: center;">线路编号</th>
										<th style="width:10%;border: 2px solid #99bbe8;text-align: center;">状态</th>
										<th style="width:10%;border: 2px solid #99bbe8;text-align: center;">操作人</th>
										<th style="width:10%;border: 2px solid #99bbe8;text-align: center;">操作时间</th>
										<th style="width:28%;border: 2px solid #99bbe8;text-align: center;">情况说明</th>
									</tr>
									<c:forEach items = "${vo.passlineHistory}" var = "ppe">
									<tr>
										<td style="border: 2px solid #99bbe8;text-align: center;">${ppe.routeId}</td>
										<td style="border: 2px solid #99bbe8;text-align: center;"> 未下线  </td>
										<td style="border: 2px solid #99bbe8;text-align: center;">${ppe.passOperPerson} </td>
										<td style="border: 2px solid #99bbe8;text-align: center;"><fmt:formatDate value="${ppe.passOperTime}" pattern="yyyy-MM-dd HH:mm:ss" /> </td>
										<td style="border: 2px solid #99bbe8;text-align: left;white-space: normal;text-overflow: ellipsis;overflow: hidden;">${ppe.remark}</td>
									</tr>
									</c:forEach>
								</tbody>					
						</table>			
				</div>
			</div>
		</div>
</div>
</body>
</html>