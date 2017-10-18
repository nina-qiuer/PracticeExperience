<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<style type="text/css">
/*搜索框表格样式*/
.search {
	font-size: 12px;
	font-family: Microsoft YaHei;
}

.search td:nth-child(even) {
	text-align: left;
}

.search td:nth-child(odd) {
	text-align: right;
}

.search td input[type=text] {
	width: 100px;
}
</style>
<script  type="text/javascript">
$(document).ready(function() {
	$(".accordion").each(function() {
		$(this).accordion({
	    	collapsible: true,
//	    	active:false, //默认折叠
	    	heightStyle: "content"
	    });
	});
	
});

function search() {
	
		$("#searchForm").attr("action", "qs/kcpType/list");
		$("#searchForm").submit();
}


function deleteKcp(id){
	
	var msg = "您确定删除吗？";
	layer.confirm(msg, {icon: 3}, function(index){
	$.ajax( {
		url : 'qs/kcpType/deleteKcp',
		data : 
		{
			id : id
		},
		type : 'post',
		dataType:'json',
		cache: false,
		success : function(result) {
			if(result)
			{
		    	if(result.retCode == "0")
				{
		    		search();
		    		
				}else{
					
					 layer.alert(result.resMsg,{icon: 2});
				}
		     }
		 }
	    });
	});
}



function reset(){
	
	$('.search :text').val('');
}

</script>
<title>KCP类型配置</title>
</head>
<body >
<form name="searchForm" id="searchForm" method="post" action="" >
<div class="accordion">
<h3>KCP类型配置</h3> 
	
<div align="right">
	<input type="button" class="blue" value="查询" onclick="search()">
	<input type="button" class="blue" value="添加" onclick="openWin('添加KCP类型', 'qs/kcpType/toAdd', 480, 225)">
</div>
</div>
<table class="listtable">
 <tr>
	<th class="id">编号</th>
	<th class="name">KCP类型名称</th>
	<th class="addPerson">添加人</th>
	<th class="addTime">添加时间</th>
	<th>操作</th>
 </tr>
 	<c:forEach items="${dto.dataList}" var="kcpType">
 	 <tr>
  		 <td class="id">${kcpType.id }</td>
  		 <td class="name">${kcpType.name }</td>
  		 <td class="addPerson">${kcpType.addPerson }</td>
  		 <td class="addTime"><fmt:formatDate value="${kcpType.addTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
  		<td> 
   		 	<input type="button" class="blue" value="修改" style="cursor: pointer;"
			 onclick="openWin('修改KCP类型', 'qs/kcpType/${kcpType.id}/toUpdate', 400, 200)">
			<input type="button" class="blue"  onclick="deleteKcp(${kcpType.id})" value="删除" >
   		  </td>
 		 </tr>
      </c:forEach>
</table>
</form>
</body>
</html>
