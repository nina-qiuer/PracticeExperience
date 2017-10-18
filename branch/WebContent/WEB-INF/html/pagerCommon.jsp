<%@page import="tuniu.frm.service.Pager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<div class="pages">
	<c:if test="${((page.currentPage-5<0?page.currentPage+4-page.currentPage+5:page.currentPage+4)>page.pages-1?page.pages-1:(page.currentPage-5<0?page.currentPage+4-page.currentPage+5:page.currentPage+4)) > 0}">
	总计${page.count}条,共${page.pages}页,${page.pageSize}条/页,显示行1-${page.currentPageCount}
	<a href="javascript:;" onclick="commitPage(1,${page.pageSize});"><<</a>
	<c:forEach begin="${page.currentPage-5<0?0:page.currentPage-5}" end="${(page.currentPage-5<0?page.currentPage+4-page.currentPage+5:page.currentPage+4)>page.pages-1?page.pages-1:(page.currentPage-5<0?page.currentPage+4-page.currentPage+5:page.currentPage+4)}" var="v">
		<c:choose>
			<c:when test="${v+1==page.currentPage}"> 
				[${v+1}]
			</c:when>
			<c:otherwise>
				<a href="javascript:;" onclick="commitPage(${v+1},${page.pageSize});">[${v+1}]</a>
			</c:otherwise>
		</c:choose>
	</c:forEach> 
	<a href="javascript:;" onclick="commitPage(${page.pages},${page.pageSize});">>></a>
	</c:if>
	<c:if test="${((page.currentPage-5<0?page.currentPage+4-page.currentPage+5:page.currentPage+4)>page.pages-1?page.pages-1:(page.currentPage-5<0?page.currentPage+4-page.currentPage+5:page.currentPage+4)) <= 0}">
	总计${page.count}条,共${page.pages}页,${page.pageSize}条/页,显示行1-${page.currentPageCount}
	</c:if>
</div>
