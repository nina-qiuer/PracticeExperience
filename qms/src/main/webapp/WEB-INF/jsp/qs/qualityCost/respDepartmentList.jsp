<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<script  type="text/javascript">
$(document).ready(function() {
	
	$(".accordion").each(function() {
		$(this).accordion({
	    	collapsible: true,
	    	heightStyle: "content"
	    });
	});
	
});

function search() {
	
	  $("#searchForm").attr("action", "qs/respDep/list");
	  $("#searchForm").submit();

}
function openWinow(title, url, width, height) {
	layer.open({
        type: 2,
        shade : [0.5 , '#000' , true],
        maxmin: true,
        title: title,
        content: url,
        area: [width+'px', height+'px']
    });
}
</script>
<title>责任部门列表</title>
</head>
<body>
<form name="searchForm" id="searchForm" method="post" action="">
	  <div class="accordion">
	   <h3>责任部门列表</h3> 
	   <div>
			<table width="100%" class="search">
				<tr>
					<td  style="text-align: right;">
						<input type="button" class="blue" 	value="查询" onclick="searchResetPage()"/> 
					</td>
				</tr>
				</table>
			</div>
	  </div>
	  <table class="listtable">
	    	<tr>
				<th class="id">单号</th>
				<th class="firstDepId">一级责任部门编号</th>
				<th class="firstDepName">一级责任部门</th>
				<th class="twoDepId">二级责任部门编号</th>
				<th class="twoDepName">二级责任部门</th>
				<th class="threeDepId">三级责任部门编号</th>
				<th class="threeDepName">三级责任部门</th>
				<th >操作</th>
	   		 </tr>
	   		<c:forEach items="${dto.dataList}" var="respDep">
	   		 <tr>
		   		 <td class="id">${respDep.id}</td>
		   		 <td class="firstDepId">${respDep.firstDepId }</td>
		   		 <td class="firstDepName">${respDep.firstDepName }</td>
		   		 <td class="twoDepId">${respDep.twoDepId }</td>
		   		 <td class="twoDepName">${respDep.twoDepName }</td>
		   		 <td class="threeDepId">${respDep.threeDepId }</td>
		   		 <td class="threeDepName">${respDep.threeDepName }</td>
           		 <td><input type="button" class="blue"   value="处理" onclick="openWinow('处理', 'qs/respDep/${respDep.id}/dealDep',  870, 520)"></td>
	   		 </tr>
	        </c:forEach>
	  </table>
 	<%@include file="/WEB-INF/jsp/common/pager.jsp"%>
</form>
</body>
</html>
