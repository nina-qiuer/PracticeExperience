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
	$("#searchForm").validate({
		rules:{
			qcId:{
                digits:true,
                min:0
			}
        },
        messages:{
        	qcId:{digits:"请输入整数"}
        }
		
	});
	
});
$(function (){
	
	$('#chkAll').click(function() {
		var flag = this.checked;
		$('.listtable td :checkbox[name = jiraIds]').each(function() {
			this.checked = flag;
		});
	});
	$('#radioset').buttonset();
	$('#radioset').click(function() {
		searchResetPage();
	});
	
	
});

function search() {
	
		$("#searchForm").attr("action", "qc/jiraRelation/list");
		$("#searchForm").submit();
}


function closeJira(jiraId){
	
	
	$.ajax( {
		url : 'qc/jiraRelation/closeJira',
		data : 
		{
			jiraId : jiraId
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
}

function checkJira(){

	if ($(':checkbox[name="jiraIds"]:checked').length <= 0) {
		layer.alert("请至少选择一个JIRA单！", {
			icon : 2
		});
		return false;
	}
	var str = document.getElementsByName("jiraIds");
    var jiraIds="";
	 for(var i=0;i<str.length;i++){
	             if(str[i].checked){
	            	 jiraIds += str[i].value+",";
	            }
	    } 
	openWin('关联质检单号', 'qc/jiraRelation/relationQcId?jiraIds='+jiraIds, 400, 200);
	
}

function batchJira(){

	if ($(':checkbox[name="jiraIds"]:checked').length <= 0) {
		layer.alert("请至少选择一个JIRA单！", {
			icon : 2
		});
		return false;
	}
	var str = document.getElementsByName("jiraIds");
    var jiraIds="";
	 for(var i=0;i<str.length;i++){
	             if(str[i].checked){
	            	 jiraIds += str[i].value+",";
	            }
	    } 
	openWin('发起研发质检', 'qc/jiraRelation/toAddQcBill?jiraIds='+jiraIds, 850, 500);
	
}

function resetSearchTable(){
	
	$('.search :text').val('');
	$('#eventClass').val("");
	$('#mianReason').val("");
}
function setMaxDate(id){
	WdatePicker({maxDate:'#F{$dp.$D(\''+id+'\')}'});
}

function setMinDate(id){
	WdatePicker({minDate:'#F{$dp.$D(\''+id+'\')}'});
}
</script>
<title>JIRA单列表</title>
</head>
<body >
<form name="searchForm" id="searchForm" method="post" action="" >
<div class="accordion">
<h3>JIRA单列表</h3> 
<div>
<table width="100%" class="search">
	<tr>
		<td>JIRA单号：</td>
		<td><form:input path="dto.jiraName"/></td>
		<td>内容关键字：</td>
		<td><form:input path="dto.keyWord" style="width:200px"/></td>
		<td>添加时间：</td>
		<td>
			<form:input path="dto.addTimeFrom"  onfocus="setMaxDate('addTimeTo')" />
			 至
			<form:input path="dto.addTimeTo"  onfocus="setMinDate('addTimeFrom')" />
		</td>
		<td>质检单号：</td>
		<td><form:input path="dto.qcId" /></td>
	</tr>
	<tr>
		<td>需求提出人：</td>
		<td><form:input path="dto.applicationPeople"/></td>
		<td>问题等级：</td>
		<td>
			<form:select path="dto.eventClass" onchange="searchResetPage()">
				<option value="">全部</option>
				<form:options items="${eventClassList}"/>
			</form:select>
		</td>
		<td>创建时间：</td>
		<td>
			<form:input path="dto.createTimeBgn"  onfocus="setMaxDate('createTimeEnd')" />
			 至
			<form:input path="dto.createTimeEnd"  onfocus="setMinDate('createTimeBgn')" />
		</td>
		<td>问题主因：</td>
		<td>
			<form:select path="dto.mianReason" onchange="searchResetPage()">
				<option value="">全部</option>
				<form:options items="${mainReasonList}"/>
			</form:select>
		</td>
	</tr>
	<tr>
		<td>JIRA单处理状态：</td>
		<td colspan="3">
			<div id="radioset">
				<form:radiobutton path="dto.flag" value="-1" label="全部" />
				<form:radiobutton path="dto.flag" value="0" label="待处理" />
				<form:radiobutton path="dto.flag" value="1" label="已关联" />
				<form:radiobutton path="dto.flag" value="2" label="已关闭" />
			</div>
		</td>
		<td></td><td></td>
		<td colspan="2" style="text-align: center;">
			<input type="button" class="blue" value="查询" onclick="searchResetPage()"/>
			<input type="button" class="blue" value="重置" onclick="resetSearchTable()"/>
	        <input type="button" class="blue" value="批量关联" onclick="checkJira()"/>
	        <input type="button" class="blue" value="批量创建" onclick="batchJira()"/>
		</td>
	</tr>
	</table>
</div>
</div>
<table class="listtable">
  	<tr>
  		<th><input type="checkbox" id="chkAll" title="全选"></th>
	<th class="jiraName">JIRA单号</th>
	<th class="statusName">JIRA单状态</th>
	<!-- <th class="typeName" >JIRA单类型</th> -->
	<th class="summary">JIRA单标题</th>
	<th class="description">JIRA单描述</th>
	<th class="mianReason">问题主要原因</th>
	<th class="reasonDetail">问题原因明细</th>
	<th class="solution">解决方案</th>
	<th class="eventClass">问题等级</th>
	<th class="devProPeople">研发处理人</th>
	<th class="applicationPeople">需求提出人</th>
	<th class="created">创建时间</th>
    <th class="qcId">质检单号</th>
	<th>操作</th>
 		 </tr>
 		<c:forEach items="${dto.dataList}" var="jiraBill">
 		 <tr>
 		 	 <td ><input type="checkbox" autocomplete="off"  name="jiraIds" value="${jiraBill.id }" /></td>
  		 <td class="jiraName"><a href="javascript:void(0)" onclick="window.open('http://jira.tuniu.org/browse/${jiraBill.jiraName }')">${jiraBill.jiraName }</a></td>
  		 <td class="statusName">${jiraBill.statusName }</td>
  		<!--   <td class="typeName" >${jiraBill.typeName }</td>-->
  		 <td class="shorten30" title="${jiraBill.summary }">${jiraBill.summary }</td>
  		 <td class="shorten30" title="${jiraBill.description }">${jiraBill.description }</td>
  		 <td class="shorten30" title="${jiraBill.mianReason }">${jiraBill.mianReason }</td>
  		 <td class="shorten30"  title="${jiraBill.reasonDetail }">${jiraBill.reasonDetail }</td>
  		 <td class="shorten30"  title="${jiraBill.solution }">${jiraBill.solution }</td>
  		 <td class="eventClass">${jiraBill.eventClass }</td>
  		 <td class="devProPeople">${jiraBill.devProPeople }</td>
  		 <td class="applicationPeople">${jiraBill.applicationPeople }</td>
  		 <td class="created"><fmt:formatDate value="${jiraBill.created }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
  		 <td class="qcId">${jiraBill.qcId }</td>
  		 <c:if test="${jiraBill.flag ==0 }">
   		 <td> 
   		 	<input type="button" class="blue" value="关联"  style="cursor: pointer;"
			 onclick="openWin('关联质检单号', 'qc/jiraRelation/relationQcId?jiraIds=${jiraBill.id}', 400, 200)">
			 <input type="button" class="blue" value="创建"  style="cursor: pointer;"
			 onclick="openWin('发起研发质检', 'qc/jiraRelation/toAddQcBill?jiraIds=${jiraBill.id}', 850, 500)">
			<input type="button" class="blue" value="关闭" onclick="closeJira('${jiraBill.id}')">
   		  </td>
  		  </c:if>
  		   <c:if test="${jiraBill.flag == 1 }">
   		 <td> 
   		 	<input type="button"  disabled="disabled" value="已关联" >
			<input type="button" class="blue" value="关闭" onclick="closeJira('${jiraBill.id}')">
   		  </td>
  		  </c:if>
  		   <c:if test="${jiraBill.flag == 2 }">
   		 <td> 
   		 	<input type="button" class="blue" value="关联" style="cursor: pointer;"
			 onclick="openWin('关联质检单号', 'qc/jiraRelation/relationQcId?jiraIds=${jiraBill.id}', 400, 200)">
			  <input type="button" class="blue" value="创建"  style="cursor: pointer;"
			 onclick="openWin('发起研发质检', 'qc/jiraRelation/toAddQcBill?jiraIds=${jiraBill.id}', 850, 500)">
			<input type="button"  disabled="disabled" value="已关闭" >
   		  </td>
  		  </c:if>
 		 </tr>
      </c:forEach>
</table>
<%@include file="/WEB-INF/jsp/common/pager.jsp"%>
</form>
</body>
</html>
