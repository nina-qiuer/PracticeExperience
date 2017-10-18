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
var depArr = new Array();
var jobArr = new Array();
var qtArr = new Array();

$(document).ready(function() {
	$(".accordion").each(function() {
		$(this).accordion({
	    	collapsible: true,
//	    	active:false, //默认折叠
	    	heightStyle: "content"
	    });
	});
	$('#radioset').buttonset();
	$('#radioset').click(function() {
		searchResetPage();
	});
	$("#searchForm").validate({
		rules:{
			id:{
                digits:true,
                min:0
			},
			prdId:{
                digits:true,
                min:0
			},
			ordId:{
                digits:true,
                min:0
			},
		     depName:{
						   depExists:true
				      },
			  jobName:{
				    	   jobExists:true
					      },
		      qcTypeName:{
		    	  qcTypeExists:true
						   }
        },
        messages:{
        	id:{digits:"请输入整数"},
        	ordId:{digits:"请输入整数"},
        	prdId:{digits:"请输入整数"},
        	depName:{depExists:"部门不存在"},
        	jobName:{jobExists:"岗位不存在"},
        	qcTypeName:{qcTypeExists:"质检类型不存在"}
        }
		
	});
	jQuery.validator.addMethod("depExists", function(value, element) {
		if($.trim(value)!=''){
		var isExists = false;
		for(var i=0;i<depArr.length;i++){
			
			if(value == depArr[i]){
				
				isExists =true;
			}
		}
		return isExists;
		
	}else{
		return true;
		
		}
	}, "");
	jQuery.validator.addMethod("jobExists", function(value, element) {
		if($.trim(value)!=''){
			var isExists = false;
			for(var i=0;i<jobArr.length;i++){
				
				if(value == jobArr[i]){
					
					isExists =true;
				}
			}
			return isExists;
			
		}else{
			
			return true;
		}
		}, "");
	jQuery.validator.addMethod("qcTypeExists", function(value, element) {
		if($.trim(value)!=''){
			var isExists = false;
			for(var i=0;i<qtArr.length;i++){
				
				if(value == qtArr[i]){
					
					isExists =true;
				}
			}
			return isExists;
			
		}else{
			
			return true;
		}
		}, "");
});

function depAutoComplete() {

	if (depArr.length > 0) {
		return;
	} else {
		$.ajax({
			type : "POST",
			url : "qc/qcBill/getDepNames",
			success : function(data) {
				for (var i = 0; i < data.length; i++) {
					depArr.push(data[i]);
				}
			}
		});
	
	$("#depName").autocomplete({
	    source: depArr,
	    autoFocus : true
	});
	
	}
}
function jobAutoComplete() {
	
	if (jobArr.length > 0) {
		return;
	} else {
		$.ajax({
			type : "POST",
			url : "qc/qcBill/getJobNames",
			success : function(data) {
				for (var i = 0; i < data.length; i++) {
					jobArr.push(data[i]);
				}
			}
		});
	
	$("#jobName").autocomplete({
	    source: jobArr,
	    autoFocus : true
	});
	
	}
}
function qcTypeAutoComplete() {

	if (qtArr.length > 0) {
		return;
	} else {
		$.ajax({
			type : "POST",
			url : "qc/qcType/getQtNames",
			success : function(data) {
				for (var i = 0; i < data.length; i++) {
					qtArr.push(data[i]);
				}
			}
		});
	
	$("#qcTypeName").autocomplete({
	    source: qtArr,
	    autoFocus : true
	});
	
	}
}
function search() {
    $('#depId').val(null);
    $('#jobId').val(null);
    $('#qcTypeId').val(null);
	$("#searchForm").attr("action", "qc/scoreRecord/myList");
	$("#searchForm").submit();
}

function setMaxDate(id){
	WdatePicker({maxDate:'#F{$dp.$D(\''+id+'\')}'});
}

function setMinDate(id){
	WdatePicker({minDate:'#F{$dp.$D(\''+id+'\')}'});
}

function resetSearchTable(){
	$('.search :text').val('');
}

</script>
<title>处罚单管理列表</title>

</head>
<body>
<form name="searchForm" id="searchForm" method="post" action="">
   <form:hidden path="dto.depId"/>
   <form:hidden path="dto.jobId"/>
   <form:hidden path="dto.qcTypeId"/>
	  <div class="accordion">
	   <h3>处罚单管理列表</h3> 
	   <div>
			<table width="100%" class="search">
				<tr>
					<td>订单号：</td>
					<td><form:input path="dto.ordId"/></td>
					<td>质检单号：</td>
					<td><form:input path="dto.qcId"/></td>
					<td>质检完成时间：</td>
					<td>
						<form:input path="dto.finishTimeBgn"  onfocus="setMaxDate('finishTimeEnd')" />
						 至
						<form:input path="dto.finishTimeEnd"  onfocus="setMinDate('finishTimeBgn')" />
					</td>
					<td>关联部门 :</td>
					<td>
					   <form:input path="dto.depName" style="width:250px" onfocus="depAutoComplete()"/> 
					</td>
				</tr>
				<tr>
					<td>产品编号：</td>
					<td><form:input path="dto.prdId"/></td>
					<td>处罚时责任岗位：</td>
					<td>
						<form:select path="dto.punishJobType">
							<form:option value="-1" label="全部"/>
							<form:option value="0" label="责任专员"/>
							<form:option value="1" label="责任经理"/>
							<form:option value="2" label="责任总监"/>
						</form:select>
					</td>
					<td> 质检类型：</td>
					<td>
					  <form:input path="dto.qcTypeName" style="width:250px" onfocus="qcTypeAutoComplete()"/>
					</td>
			   		<td> 关联岗位 :</td>
					<td>
					  <form:input path="dto.jobName" style="width:250px" onfocus="jobAutoComplete()"/> 
					</td>
				</tr>
				<tr>
				 	<td>质检组：</td>
					<td colspan="3">
						<div id="radioset">
					 	    <form:radiobutton path="dto.groupFlag" value="-1" label="全部" />
							<form:radiobutton path="dto.groupFlag" value="1" label="投诉质检" />
							<form:radiobutton path="dto.groupFlag" value="2" label="运营质检" />
							<form:radiobutton path="dto.groupFlag" value="3" label="研发质检" />
							<form:radiobutton path="dto.groupFlag" value="4" label="内部质检" />
						</div>
					</td>
					<td colspan="4" >
						<input type="button" class="blue" 	value="查询" onclick="searchResetPage()"/> 
						<input type="button" class="blue" value="重置" onclick="resetSearchTable()" />
					</td>
				</tr>
				</table>
			</div>
	  </div>
	  <table class="listtable">
	    	<tr>
				<!-- <th class="id">处罚单号</th> -->
				<th class="qcId">质检单号</th>
				<th class="orderId">订单号</th>
				<th class="prdId">产品编号</th>
				<th class="punishPersonName">被处罚人姓名</th>
				<th class="pubPersonId">被处罚人工号</th>
				<th class="depName">关联部门</th>
				<th class="jobName">关联岗位</th>
				<th class="qcTypeName">质检类型</th>
				<th class="scorePunish">记分处罚(分)</th>
				<th class="qcPerson">质检人</th>
				<th class="finishTime">质检完成时间</th>
	   		 </tr>
	   		<c:forEach items="${dto.dataList}" var="innerPunishBill">
	   		 <tr>
		   		<%--  <td class="id"><a href="javascript:void(0)" onclick="openWin('内部处罚单详情','qc/innerPunish/${innerPunishBill.id}/getPunishDetail','800','500')">${innerPunishBill.id }</a></td> --%>
		   		 <td class="qcId">${innerPunishBill.qcId }</td>
		   		 <td class="orderId">${innerPunishBill.ordId }</td>
		   		 <td class="prdId">${innerPunishBill.prdId }</td>
		   		 <td class="punishPersonName">${innerPunishBill.punishPersonName }</td>
		   		 <td class="pubPersonId">${innerPunishBill.pubPersonId }</td>
		   		 <td  style="text-align:left">${innerPunishBill.depName }</td>
		   		 <td class="jobName">${innerPunishBill.jobName }</td>
		   		 <td  style="text-align:left">${innerPunishBill.qcTypeName }</td>
		   		 <td class="scorePunish">${innerPunishBill.scorePunish }</td>
		   		 <td class="qcPerson">${innerPunishBill.qcPerson }</td>
		   		 <td class="finishTime"><fmt:formatDate value="${innerPunishBill.qcFinishTime }" pattern="yyyy-MM-dd"/></td>
	   		 </tr>
	        </c:forEach>
	  </table>
 	<%@include file="/WEB-INF/jsp/common/pager.jsp"%>
 	<div style="float:right;">当前总分为：${dto.totalScores}</div>
</form>
</body>
</html>
