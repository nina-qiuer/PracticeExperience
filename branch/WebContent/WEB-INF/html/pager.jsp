<%@page import="tuniu.frm.service.Pager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<div class="pages">
	总计${pager.totalCount}条,共${pager.pageCount}页,${pager.perPage}条/页,显示行${pager.currStart}-${pager.currEnd}
	<a href="${pager.url}1&${pager.param}"><<</a>
	<c:forEach items="${pager.pagesSet}" var="v">
		<c:choose>
			<c:when test="${v==pager.currPage}"> 
				[${v}]
			</c:when>
			<c:otherwise>
				<a href="${pager.url}${v}&${pager.param}">[${v}]</a>
			</c:otherwise>
		</c:choose>
		
	</c:forEach> 
	<a href="${pager.url}${pager.pageCount}&${pager.param}">>></a>
</div>
<!-- 
<div class="nextprepage clear rf">
		<ul>
			<span class="page_wd">共${pager.totalCount}条记录，当前第${pager.currStart}-${pager.currEnd}条</span>
			<c:if test="${pager.currPage > 1}">
			<li><a href="${pager.url}1&${pager.param}">上一页&gt;</a></li>
			</c:if>
			<c:forEach items="${pager.pagesSet}" var="v">
				<c:choose>
					<c:when test="${v==pager.currPage}">				 
						${v}
					</c:when>
					<c:otherwise>
						<a href="${pager.url}${v}&${pager.param}">[${v}]</a>
						<li><a href="${pager.url}${v}&${pager.param}">${v}</a></li>
					</c:otherwise>
				</c:choose>	
			</c:forEach> 
			<c:if test="${pager.currPage < pager.pageCount}">
			<li><a href="${pager.url}${pager.pageCount}&${pager.param}">下一页&gt;</a></li>
			</c:if>
			
	</ul>
</div> 
-->