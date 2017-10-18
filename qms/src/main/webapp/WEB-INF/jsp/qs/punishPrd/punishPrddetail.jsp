<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<style type="text/css">
<!-- tr变黄色 -->
.trbg td{background:#ff9;}
.trbg1 td{background:#87CEFF;}
.listtable tr.yellowbg td{background:#FFFF99}
.listtable tr td.orange{background:orange;}
</style>
<script type="text/javascript">
function iFrameHeight(iframe) {   
	var ifm= document.getElementById(iframe.id);   
	var subWeb = document.frames ? document.frames[iframe.id].document : ifm.contentDocument;   
	if(ifm != null && subWeb != null) {
	   ifm.height = subWeb.body.scrollHeight;
	   ifm.width = subWeb.body.scrollWidth;
	}   
} 
</script>
</head>
<body>
	<c:if test="${attachList != null &&  fn:length(attachList) > 0 }">
		<h4>整改报告附件</h4>
		<div id="detail_remark">
			<table style="width: 850px;border-collapse: collapse;border: 2px solid #99bbe8;margin: auto;">
				<tbody>
					<tr>
						<th style="width:10%;border: 2px solid #99bbe8;text-align: center;">附件名称</th>
						<th style="width:10%;border: 2px solid #99bbe8;text-align: center;">添加人</th>
						<th style="width:10%;border: 2px solid #99bbe8;text-align: center;">添加时间</th>
					</tr>
					<c:forEach items = "${attachList}" var = "attach">
						<tr>
							<td style="border: 2px solid #99bbe8;text-align: center;">
								<a href="${attach.path}" target="_blank">${attach.name}</a>
							</td>
							<td style="border: 2px solid #99bbe8;text-align: center;">${attach.addPerson}</td>
							<td style="border: 2px solid #99bbe8;text-align: center;"><fmt:formatDate value="${attach.addTime}" pattern="yyyy-MM-dd HH:mm:ss" /> </td>
						</tr>
					</c:forEach>
				</tbody>					
			</table>			
		</div>
	</c:if>
	<c:if test="${punishPrd.offlineType==1}">
		 <h3>质检报告</h3>
		 <div>
		 	<iframe name="qcReport" src="qc/qcBill/${punishPrd.qcId}/qcReport" width="90%" id="iframepage" scrolling="no" frameborder="0" onload="iFrameHeight(this)"></iframe>
		 </div>
	</c:if>
	<c:if test="${punishPrd.offlineType==2}">
		<h3>低满意度详情</h3>
		 <div>
		 	<iframe name="lowSatisfyDetail" src="qs/punishPrd/${punishPrd.id}/lowSatisfyDetail" width="90%" id="iframepage" scrolling="no" frameborder="0" onload="iFrameHeight(this)"></iframe>
		 </div>
	</c:if>
	<%-- <c:if test="${punishPrd.offlineType==3}">
		<input type="button" value="查看详情" title="D类产品详情" class="blue" 
			onclick="openWin('D类产品详情','qs/punishPrd/${punishPrd.id}/dPrdDetail',850,520)">
	</c:if> --%>
	<!-- 该条数据上线附件信息 -->
</body>
</html>