<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>
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
var realNameArr = new Array();
var userArr = new Array();
$(document).ready(function() {
	$(".accordion").each(function() {
		$(this).accordion({
	    	collapsible: true,
	    	heightStyle: "content"
	    });
	});
	
	$("#searchForm").validate({
		rules:{
			orderId:{
                digits:true,
                min:0
			},
			routeId:{
                digits:true,
                min:0
			},
			realOffLineCount:{
				  digits:true,
	              min:0,
	              range:[0,5]
			}
        },
        messages:{
        	orderId:{digits:"请输入整数"},
        	routeId:{digits:"请输入整数"},
        	offlineCount:{digits:"请输入整数"}
        }
		
	});
	
});
$(function (){
	
	$('#radioset').buttonset();
	$('#radioset').click(function() {
		searchResetPage();
	});
	
});


function search() {
	
	  $("#searchForm").attr("action", "qs/punishPrd/list");
	  $("#searchForm").submit();

}
function setMaxDate(id){
	WdatePicker({maxDate:'#F{$dp.$D(\''+id+'\')}'});
}

function setMinDate(id){
	WdatePicker({minDate:'#F{$dp.$D(\''+id+'\')}'});
}


function userAutoComplete() {
	
	if (userArr.length > 0) {
		return;
	} else {
		$.ajax({
			type : "POST",
			url : "qc/qcBill/getUserNamesInJSON",
			success : function(data) {
				for (var i = 0; i < data.length; i++) {
					userArr.push({
						label : data[i].label,
						value : data[i].realName
					});
					realNameArr.push(data[i].realName);
				}
			}
		});
	
	$("#assignTo").autocomplete({
	    source: userArr,
	    autoFocus : true
	}).data( "ui-autocomplete" )._renderItem = function( ul, item ) {
		return $( "<li>" )
        .append( "<a>" + item.value + "</a>" )
        .appendTo( ul );
	};
	}
}


function exportExcel(input){
	
	disableButton(input);
	$.ajax( {
		url : 'qs/punishPrd/checkExport',
		data : $('#searchForm').serialize(),
		type : 'post',
		dataType:'json',
		cache : false,
		success : function(result) {
			
			if(result)
			{
		    	if(result.retCode == "0")
				{
		    		$("#searchForm").attr("action","qs/punishPrd/exports"); 
		    		$("#searchForm").submit(); 	
				 }else{
					
					 layer.alert(result.resMsg,{icon: 2});
					 enableButton(input);
				}
		     }
		 }
	    }); 
}

//重置搜索框内容：直接使用reset会影响所有form中内容
function resetSearchTable() {
	$('.search :text').val('');
	$('#status').val("-1");
	$('#offlineType').val("-1");
	
}
function chgPrdStatus(id, routeId, prdStatus, realOffLineCount, offlineType) {
	var msg = "";
	if (0 == prdStatus) {
		msg = "确定将产品[" + routeId + "]下线吗？";
	} else if (1 == prdStatus) {
		msg = "确定将产品[" + routeId + "]上线吗？";
	}
	var lineType = "${dto.lineType}";
	layer.confirm(msg, {icon: 3}, function(index){
		 layer.close(index);
		$.ajax( {
			url : 'qs/punishPrd/chgPrdStatus',
			data: {  id : id,
					 routeId : routeId, 
					 prdStatus : prdStatus,
					 realOffLineCount : realOffLineCount,
					 offlineType :offlineType,
					 lineType : lineType
				},
			type : 'post',
			dataType:'json',
			cache : false,
			success : function(result) {
				if(result)
				{
			    	if(result.retCode == "0")
					{
			    		layer.alert(result.resMsg,{icon: 6,closeBtn: 0},function(){
							
							 search();
				    		
						});
			    		
					 }else{
						
						layer.alert(result.resMsg, {icon: 2});
					}
			     }
			 }
		    });
		});
}

</script>
<title>处罚产品管理列表</title>
</head>
<body >
<form name="searchForm" id="searchForm" method="post" action="" >
<div class="accordion">
<h3>处罚产品管理列表</h3> 
<div align="right">
<table width="100%" class="search">
	<tr>
	<c:if test="${dto.lineType==1}">
	<td>触发时间：</td>
	<td>
		<form:select path="dto.year" class="year"  style="width:100px" >
			<form:option value="${dto.nowYear}" label="${dto.nowYear}" />
			<form:option value="${dto.nowYear-1}" label="${dto.nowYear-1}" />
			<form:option value="${dto.nowYear-2}" label="${dto.nowYear-2}" />
			</form:select> 年-
			  <form:select path="dto.week" onchange="searchResetPage()">
			<form:options items="${weekList}"/>
		</form:select> 周
	 </td>
	 <td>整改状态：</td>
	 <td>
		<form:select path="dto.status" id="status" onchange="searchResetPage()"  style="width:100px">
			<form:option value="-1" label="全部" />
			<form:option value="1" label="待整改" />
			<form:option value="2" label="整改中" />
			<form:option value="3" label="已整改" />
			<form:option value="4" label="永久下线" />
			<form:option value="5" label="已放过" />
		</form:select>
	 </td>
	 <td>下线类型：</td>
		<td>
		<form:select path="dto.offlineType" id="offlineType" onchange="searchResetPage()"  style="width:100px">
			<form:option value="-1" label="全部" />
			<form:option value="1" label="触红" />
			<form:option value="2" label="低满意度" />
			<form:option value="3" label="D类" />
		</form:select>
	 </td>
	 <td>实际下线次数：</td>
     <td><form:input path="dto.realOffLineCount"/></td>
	 </c:if>
	 <span>当前时间<b>${dto.nowYear}</b>年第<b>${dto.nowWeek}</b>周</span>
	</tr>
	<c:if test="${dto.lineType==1}">
	<tr>
	<td>供应商：</td>
    <td><form:input path="dto.supplier"/></td>
    <td>产品专员：</td>
    <td><form:input path="dto.prdOfficer"/></td>
     <td>事业部：</td>
    	<td><form:input path="dto.businessUnit"/></td>
   </tr>
   </c:if>
   <tr>
		<td>线路编号：</td>
    	<td><form:input path="dto.routeId"/></td>
    	<td>产品经理：</td>
    	<td><form:input path="dto.prdManager"/></td>
    	<c:if test="${dto.lineType==2}">
    	<td>操作人：</td>
        <td><form:input path="dto.offlineOperPerson" /></td>
        </c:if>
        <c:if test="${dto.lineType==1}">
        <td>订单号：</td>
    	<td><form:input path="dto.orderId"/></td>
    	
    	</c:if>
		
	</tr>
	<tr>
	 <td >线路：</td>
	   <td colspan="6">
			<div id="radioset" onclick="searchResetPage()">
				<form:radiobutton path="dto.lineType" value="1" label="所有线路" />
				<form:radiobutton path="dto.lineType" value="2" label="整改中线路" />
				<form:radiobutton path="dto.lineType" value="3" label="永久下线线路恢复" />
			</div>
		</td>
		<td>
			<input type="button" class="blue" 	value="查询" onclick="searchResetPage()"/>
		    <input type="button" class="blue" value="重置" onclick="resetSearchTable()"/>
		    <input type="button" class="blue" id ="exports" name="exports" value="导出" onclick="exportExcel(this)" />
		</td>
	</tr>
</table>
</div>
</div>
<table class="listtable">
  <tr>
	<th>触发时间</th>
	<th style="width:60px">订单号</th>
	<th>线路名</th>
	<th>线路编号</th>
	<th>事业部</th>
	<th>产品经理</th>
	<th>产品专员</th>
	<!-- <th>供应商</th> -->
	<th style="width:60px">下线类型</th>
	<th>应下线次数</th>
	<th>实际下线次数</th>
	<th>下线时间</th>
	<th>上线时间</th>
	<th style="width:50px">状态</th>
	<th>查看详情</th>
	<th>情况说明</th>
	<th>操作</th>
  </tr>
 		<c:forEach items="${dto.dataList}" var="punishPrd">
 		 <tr>
  		 	<td style="text-align:center"><fmt:formatDate value="${punishPrd.triggerTime}" pattern="yyyy-MM-dd" /></td>
  			<td class="orderId" style="text-align:center">${punishPrd.orderId}</td>
  		    <td class="shorten45" >${punishPrd.routeName}</td>
  		    <td style="text-align:center">${punishPrd.routeId}</td>
			<td style="text-align:center">${punishPrd.businessUnit}</td>
			<td style="text-align:center">${punishPrd.prdManager}</td>
			<td style="text-align:center">${punishPrd.prdOfficer}</td>
			<%-- <td style="text-align:center">${punishPrd.supplier}</td> --%>
			<td style="text-align:center">
				<c:if test="${punishPrd.offlineType ==1}">触红</c:if>
				<c:if test="${punishPrd.offlineType ==2}">低满意度</c:if>
				<c:if test="${punishPrd.offlineType ==3}">低质</c:if>
			</td>
  			<td style="text-align:center">${punishPrd.offlineCount}</td>
  			<td style="text-align:center">${punishPrd.realOffLineCount}</td>
			<td style="text-align:center"><fmt:formatDate value="${punishPrd.offlineTime}" pattern="yyyy-MM-dd" /></td>
			<td style="text-align:center"><fmt:formatDate value="${punishPrd.onlineTime}" pattern="yyyy-MM-dd" /></td>
			<td style="text-align:center">
				<c:if test="${punishPrd.status==1}">待整改</c:if>
				<c:if test="${punishPrd.status==2}">整改中</c:if>
				<c:if test="${punishPrd.status==3}">已整改</c:if>
				<c:if test="${punishPrd.status==4}">永久下线</c:if>
				<c:if test="${punishPrd.status==5}">已放过</c:if>
			</td>
  		    <td style="text-align:center">
  		    	<input type="button" value="查看详情" title="查看详情" class="blue" 
						onclick="openWin('查看详情','qs/punishPrd/${punishPrd.id}/punishPrdDetail',950,520)">
			</td>
			<td class="shorten25">${punishPrd.remark}</td>
			<td style="text-align:center;width:100px">
				<!-- 待整改状态或者永久下线并未执行过下线操作的线路，低满意度只能看见当前周的进行下线操作，触红和D类没有时间限制-->
				<c:if test="${(punishPrd.status== 1||(punishPrd.status==4&&(punishPrd.passOperPerson == null || punishPrd.passOperPerson == '')&&(punishPrd.offlineOperPerson==null||punishPrd.offlineOperPerson=='')&&dto.lineType!=3))
								&&((punishPrd.offlineType==2&&dto.week==dto.nowWeek)||punishPrd.offlineType!=2)}">
					<a href="javascript:void(0)" onclick="chgPrdStatus('${punishPrd.id}', '${punishPrd.routeId}', 0, '${punishPrd.realOffLineCount}','${punishPrd.offlineType}')">下线</a>
					<c:if test="${(punishPrd.status == 1)&&(punishPrd.offlineType == 2) && (fn:contains(loginUser_WCS,'PRD_PASS_PERMISSION'))}">
						<a href="javascript:void(0)" title="情况说明" class="blue" onclick="openWin('情况说明','qs/punishPrd/${punishPrd.id}/qcRemark',800,500)">放过</a>
					</c:if>
				</c:if>
				<c:if test="${dto.lineType == 2||dto.lineType==3}">
					<%-- <a href="javascript:void(0)" onclick="chgPrdStatus('${punishPrd.id}', '${punishPrd.routeId}', 1, '${punishPrd.realOffLineCount}','${punishPrd.offlineType}')">上线</a> --%>
					<a href="javascript:void(0)" title="情况说明" class="blue" onclick="openWin('情况说明','qs/punishPrd/${punishPrd.id}/qcRemark',800,500)">上线</a>
				</c:if>
			</td>
 		 </tr>
      </c:forEach>
</table>
<%@include file="/WEB-INF/jsp/common/pager.jsp"%>
</form>
</body>
</html>
