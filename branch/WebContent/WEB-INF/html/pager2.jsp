<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<script type="text/javascript">
<!--
function toPage(pageNo) {
	var num = parseInt(pageNo);
	if(!isNaN(num)){
		$('#pageNo').val(num);
		search();
	} else {
		$('#pageNo').val('${page.pageNo}');
	}
}
//-->
</script>
<div style="height: 5px"></div>
总计 ${page.totalRecords } 条，共 ${page.totalPages } 页，${page.pageSize }条 / 页，
当前第 <span style="color: red">${page.pageNo }</span> 页。&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<c:if test="${page.pageNo <= 1 }">[首页]</c:if>
<c:if test="${page.pageNo > 1 }"><a href="javascript:toPage(1)">[首页]</a></c:if>
&nbsp;&nbsp;&nbsp;
<c:if test="${page.pageNo <= 1 }">[上一页]</c:if>
<c:if test="${page.pageNo > 1 }"><a href="javascript:toPage(${page.pageNo - 1 })">[上一页]</a></c:if>
&nbsp;&nbsp;&nbsp;
<c:if test="${page.pageNo < page.totalPages }"><a href="javascript:toPage(${page.pageNo + 1 })">[下一页]</a></c:if>
<c:if test="${page.pageNo >= page.totalPages }">[下一页]</c:if>
&nbsp;&nbsp;&nbsp;
<c:if test="${page.pageNo < page.totalPages }"><a href="javascript:toPage(${page.totalPages })">[尾页]</a></c:if>
<c:if test="${page.pageNo >= page.totalPages }">[尾页]</c:if>
&nbsp;&nbsp;&nbsp;
到 <input type="text" name="page.pageNo" id="pageNo" value="${page.pageNo }" style="height:12px;width:18px" onchange="toPage(this.value)"> 页
