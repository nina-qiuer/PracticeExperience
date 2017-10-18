<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript">
<!--
function toPage(pageNo) {
	var num = parseInt(pageNo);
	var totalPages = "${dto.totalPages}";
	if(!isNaN(num)){
		if (num > totalPages) {
			num = totalPages;
		}
		if (num < 1) {
			num = 1;
		}
		$('#pageNo').val(num);
		search();
	} else {
		$('#pageNo').val('${dto.pageNo}');
	}
}
//-->
</script>
<div style="height: 5px"></div>
总计 ${dto.totalRecords } 条，共 ${dto.totalPages } 页，
<form:select path="dto.pageSize" onchange="searchResetPage()" cssStyle="height:20px;width:45px">
	<form:option value="10">10</form:option>
	<form:option value="20">20</form:option>
	<form:option value="30">30</form:option>
	<form:option value="40">40</form:option>
	<form:option value="50">50</form:option>
</form:select>条 / 页，
当前第 <span style="color: red">${dto.pageNo }</span> 页。&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<c:if test="${dto.pageNo <= 1 }">[首页]</c:if>
<c:if test="${dto.pageNo > 1 }"><a href="javascript:toPage(1)">[首页]</a></c:if>
&nbsp;&nbsp;&nbsp;
<c:if test="${dto.pageNo <= 1 }">[上一页]</c:if>
<c:if test="${dto.pageNo > 1 }"><a href="javascript:toPage(${dto.pageNo - 1 })">[上一页]</a></c:if>
&nbsp;&nbsp;&nbsp;
<c:if test="${dto.pageNo < dto.totalPages }"><a href="javascript:toPage(${dto.pageNo + 1 })">[下一页]</a></c:if>
<c:if test="${dto.pageNo >= dto.totalPages }">[下一页]</c:if>
&nbsp;&nbsp;&nbsp;
<c:if test="${dto.pageNo < dto.totalPages }"><a href="javascript:toPage(${dto.totalPages })">[尾页]</a></c:if>
<c:if test="${dto.pageNo >= dto.totalPages }">[尾页]</c:if>
&nbsp;&nbsp;&nbsp;
到 <form:input id="pageNo" path="dto.pageNo" onchange="toPage(this.value)" cssStyle="height:18px;width:26px"/> 页
