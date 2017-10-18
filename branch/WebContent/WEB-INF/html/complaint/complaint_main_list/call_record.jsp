<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>

<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>通话记录</title>
<script type="text/javascript">
	function loadMediaBox(url){
		window.open(url,'newwindow','height=150px,width=225px,top=200,left=200,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
	}
	
</script>
</HEAD>
<BODY>
<table class="listtable" width="100%">
	<thead>
		<th>主叫号码</th>
		<th>被叫号码</th>
		<th>拨打时间</th>
		<th>通话时长（秒）</th>
		<th>客服姓名</th>
		<th>录音</th>
	</thead>
	<tbody>
		<c:forEach items="${callRecordList}" var="record">
		<tr align="center" class="trbg">
			<td>${record.callNumber }</td>
			<td>${record.calledNumber }</td>
			<td>${record.callTime }</td>
			<td>${record.holdTimeLength }</td>
			<td>${record.customerName }</td>
			<td>
				<c:if test="${record.holdTimeLength==0 }">未接通</c:if>
				<c:if test="${record.holdTimeLength>0 }">
					<a href="javascript:void(0);"  onclick="loadMediaBox('${record.recordPath}')">录音</a>
				</c:if>
			</td>
			
		</tr>
		</c:forEach>
	</tbody>
</table>

</BODY>
