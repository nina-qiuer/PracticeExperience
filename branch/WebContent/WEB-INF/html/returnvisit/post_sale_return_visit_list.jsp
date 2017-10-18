<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %> 
<%@include file="/WEB-INF/html/head.jsp" %>
<script type="text/javascript" src="${CONFIG.res_url}script/jquery/validation/jquery.validate.min.js"></script>
<style type="text/css">
<!-- tr变黄色 -->
.trbg td{background:#ff9;}
.trbg1 td{background:#87CEFF;}
.listtable tr.yellowbg td{background:#FFFF99}
.listtable tr td.orange{background:orange;}
</style>
<script>
$(function(){
	
	$("#search_form").validate({
		submitHandler: function(form){
			$('#search_form input[type=text]').each(function(){
				$(this).val($.trim($(this).val()));
			});
            form.submit();
		},
		rules:{
			"vo.complaintId":{
				digits:true
			}
        }
	});
	
});

function onResetClicked() {
    $(':input','#search_form').not(':button, :submit, :reset, :hidden')  
    .val('').removeAttr('checked').removeAttr('selected');
}


</script>
</HEAD>
<BODY>
<div class="top_crumbs">您当前所在的位置：投诉质检系统&gt;&gt;<span class="top_crumbs_txt">售后短信回访列表</span></div>
<form name="form" id="search_form" method="post" action="postSaleReturnVisit-list">
<div class="pici_search pd5">
	<label class="mr10 menu1">投诉单号:<input type="text" id="complaintId" size="10" name="vo.complaintId" value="${vo.complaintId}"/> </label>
	<label class="mr10 menu1">处理岗：
		<s:select list="dealDepartments" headerKey="" headerValue="请选择" name="vo.dealDepart" onchange="submit()"/>
	</label>
	<label class="mr10">客服:<input type="text"size="10"  name="vo.dealName" value="${vo.dealName}"/> </label>
	<label class="mr10 menu1">客服经理：<input type="text" size="10"  name="vo.dealManagerName" value="${vo.dealManagerName}"/> </label>
	<label class="mr10 menu1">售后综合满意度评分：
	<select name="vo.score" onchange="submit()">
		<option value="">全部</option>
		<option value="3" <c:if test="${3 == vo.score}">selected</c:if>>满意（3分）</option>
		<option value="2" <c:if test="${2 == vo.score}">selected</c:if>>一般（2分）</option>
		<option value="0" <c:if test="${0 == vo.score}">selected</c:if>>不满意（0分）</option>
	</select> 
	</label>
	
	<label class="mr10 menu1">不满意原因：
		<select name="vo.unsatisfyReason" onchange="submit()">
			<option value="">全部</option>
			<option value="1" <c:if test="${1 == vo.unsatisfyReason}">selected</c:if>>服务态度</option>
			<option value="2" <c:if test="${2 == vo.unsatisfyReason}">selected</c:if>>跟进不及时</option>
			<option value="3" <c:if test="${3 == vo.unsatisfyReason}">selected</c:if>>处理方案</option>
			<option value="4" <c:if test="${4 == vo.unsatisfyReason}">selected</c:if>>专业程度</option>
		</select> 
	</label>
	
	<label>回访日期：
		<input id ="returnVisitDateBgn" class= "Wdate" type ="text" value= '${vo.returnVisitDateBgn }'  name= "vo.returnVisitDateBgn"
                                 onFocus= "WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'returnVisitDateEnd\')}'})" />
        至 
        <input id ="returnVisitDateEnd" name= "vo.returnVisitDateEnd" class ="Wdate"  type= "text"  value='${vo.returnVisitDateEnd }'
                                 onFocus= "WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'returnVisitDateBgn\')}'})" />
     </label>
	
	
	
	<label class="mr10"><input type="submit" value="查询" class="blue"></label>
	<label class="mr10"><input type="button" value="重置" class="blue" onclick="onResetClicked()" style="cursor: pointer;"> </label>
	</div>
</div>
<table class="listtable" width="100%">
<thead>
	<th>投诉单号</th>
	<th>订单号</th>
	<th>产品名称</th>
	<th>部门</th>
	<th>组别</th>
	<th>客服</th>
	<th>客服经理</th>
	<th>处理岗</th>
	<th>售后综合服务评分</th>
	<th>不满意原因</th>
	<th>回访日期</th>
</thead>
<tbody>
	<c:forEach items="${dataList}" var="v"  varStatus="st"> 
		<tr>
			<td style="text-align:center">${v.complaintId}</td>
			<td style="text-align:center">${v.orderId}</td>
			<td class="shorten30">${v.prdName}</td>
			<td style="text-align:center">${v.departmentName}</td>
			<td style="text-align:center">${v.groupName}</td>
			<td style="text-align:center">${v.dealName}</td>
			<td style="text-align:center">${v.dealManagerName}</td>
			<td style="text-align:center">${v.dealDepart}</td>
			<td style="text-align:center">
				<c:if test="${v.score!=-1 }">
					${v.score}
				</c:if>
			</td>
			<td style="text-align:center">
				<c:if test="${v.unsatisfyReason ==1}">服务态度</c:if>
				<c:if test="${v.unsatisfyReason ==2}">跟进不及时</c:if>
				<c:if test="${v.unsatisfyReason ==3}">处理方案</c:if>
				<c:if test="${v.unsatisfyReason ==4}">专业程度</c:if>
			</td>
			<td style="text-align:center"><fmt:formatDate value="${v.returnVisitDate}" pattern="yyyy-MM-dd" /></td>
	</c:forEach>
</tbody>
</table>
</form>
<%@include file="/WEB-INF/html/pager.jsp" %>
</BODY>