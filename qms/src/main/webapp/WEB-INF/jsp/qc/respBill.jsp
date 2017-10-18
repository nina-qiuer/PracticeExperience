<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<link rel="stylesheet" type="text/css" media="screen" href="res/plugins/jqGrid/src/css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" media="screen" href="res/plugins/jqGrid/plugins/searchFilter.css" />
<script src="res/plugins/jqGrid/js/i18n/grid.locale-cn.js" type="text/javascript"></script>
<script src="res/plugins/jqGrid/js/jquery.jqGrid.min.js" type="text/javascript"></script>
<script src="res/js/qc/resp_bill_list.js" type="text/javascript"></script>
<title>责任单列表</title>

</head>
<body >
<form name="searchForm" id="searchForm" method="post" action="qc/qcBill/queryRespBill" >
<input type="hidden" name="addTimeStart" id="addTimeStart" value="${addTimeStart}" />
<input type="hidden" name="addTimeEnd" id="addTimeEnd" value="${addTimeEnd}" />
<div class="accordion">
	  <h3>责任单列表</h3> 
	  <div>
			<table width="100%" class="search">
				<tr>
					<td>质量问题ID：<input type="text" id="qpId" name="qpId" value=""/></td>
					<td>添加时间：<input id="addTimeStart" name="addTimeStart" class="Wdate" type="text"
						value='<fmt:formatDate value="${addTimeStart }" pattern="yyyy-MM-dd"/>'
						name="addTimeStart"
						onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'addTimeEnd\')}'})" />
						至 <input id="addTimeEnd" name="addTimeEnd" class="Wdate"
						type="text"
						value='<fmt:formatDate value="${addTimeEnd }" pattern="yyyy-MM-dd"/>'
						onFocus="WdatePicker({minDate:'#F{$dp.$D(\'addTimeStart\')}'})" /></td>
					<td style="text-align: left"><input type="button" class="blue" 
						value="查询" onclick="search()"/> <input type="reset" class="blue" value="重置" />
						<input type="button" class="blue" value="删除"  onclick="deleteRows()" /></td>
				</tr>
				</table>
	  </div>
</div>
	 <table id="list"></table>
	<div id="gridPager" ></div>
</form>
</body>
</html>
