<%@page import="tuniu.frm.service.Pager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="/struts-tags" prefix="s" %>
<script type="text/javascript">
<!--
function commitPage(currentPage){
	$('#currentPage').val(currentPage);
	$('#search_form').submit();
}
//-->
</script>

<div  class="pages">
总计 ${page.count } 条，共 ${page.pages } 页，
<s:select list="{10,20,30,40,50}" name="page.pageSize" onChange="commitPage(1)"></s:select>条/页，
当前第 <span style="color: red">${page.currentPage }</span> 页。&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<c:if test="${page.currentPage <= 1 }">[首页]</c:if>
<c:if test="${page.currentPage > 1 }"><a href="javascript:commitPage(1)">[首页]</a></c:if>
&nbsp;&nbsp;&nbsp;
<c:if test="${page.currentPage <= 1 }">[上一页]</c:if>
<c:if test="${page.currentPage > 1 }"><a href="javascript:commitPage(${page.currentPage - 1 })">[上一页]</a></c:if>
&nbsp;&nbsp;&nbsp;
<c:if test="${page.currentPage < page.pages }"><a href="javascript:commitPage(${page.currentPage + 1 })">[下一页]</a></c:if>
<c:if test="${page.currentPage >= page.pages }">[下一页]</c:if>
&nbsp;&nbsp;&nbsp;
<c:if test="${page.currentPage < page.pages }"><a href="javascript:commitPage(${page.pages })">[尾页]</a></c:if>
<c:if test="${page.currentPage >= page.pages }">[尾页]</c:if>
&nbsp;&nbsp;&nbsp;
到
<select id="currentPage" name="page.currentPage" onchange="commitPage(this.value)">
	<c:forEach begin="1" end="${page.pages }" var="index">
		<option value=${index}  <c:if test="${index==page.currentPage }">selected</c:if>>${index }</option>
	</c:forEach>
	</select>
页
</div>
