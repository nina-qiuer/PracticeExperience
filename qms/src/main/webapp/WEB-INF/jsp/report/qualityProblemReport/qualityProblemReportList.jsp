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
.listtable .orderable {
	background: #DFEAFB url('res/img/line.png') no-repeat right 5px  center;
	text-align: right;
	padding-right: 26px;
}
</style>
<script  type="text/javascript">
var depArr = new Array();
var qptArr = new Array();
var jobArr = new Array();
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


function search() {
	
	  $("#searchForm").attr("action", "report/qualityProblemReport/list");
	  $("#searchForm").submit();

}
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
function depExists(input){
	  
	var isExists = false;
	var depName = input.value;
	if($.trim(depName)!=''){	
		for(var i=0;i<depArr.length;i++){
			
			if(depName == depArr[i]){
				
				isExists =true;
			}
		}
		if(isExists==false){
			
			  // layer.alert("责任部门不存在",{icon: 2});	
		       $(input).val("");
		       return false;
		}
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
function jobExists(input){
	  
	var isExists = false;
	var jobName = input.value;
	if($.trim(jobName)!=''){	
		for(var i=0;i<jobArr.length;i++){
			
			if(jobName == jobArr[i]){
				
				isExists =true;
			}
		}
		if(isExists==false){
			
			  // layer.alert("责任岗位不存在",{icon: 2});	
		       $(input).val("");
		       return false;
		}
	}
}


function qptAutoComplete() {
	
	if (qptArr.length > 0) {
		return;
	} else {
		$.ajax({
			type : "POST",
			url : "qc/qualityProblemType/getCmpQpTyppNames",
			success : function(data) {
				for (var i = 0; i < data.length; i++) {
					qptArr.push(data[i]);
				}
			}
		});
	
	$("#qptName").autocomplete({
	    source: qptArr,
	    autoFocus : true
	});
 }
}
function qptExists(input){
	  
	var isExists = false;
	var qptName = input.value;
	if($.trim(qptName)!=''){	
		for(var i=0;i<qptArr.length;i++){
			
			if(qptName == qptArr[i]){
				
				isExists =true;
			}
		}
		if(isExists==false){
			
			 //  layer.alert("问题类型不存在",{icon: 2});	
		       $(input).val("");
		       return false;
		}
	}
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
	
	$("#respPersonName").autocomplete({
	    source: userArr,
	    autoFocus : true
	}).data( "ui-autocomplete" )._renderItem = function( ul, item ) {
		return $( "<li>" )
        .append( "<a>" + item.value + "</a>" )
        .appendTo( ul );
	};

	}
}
function respExists(input){
	  
	var isExists = false;
	var respName = input.value;
	if($.trim(respName)!=''){	
		for(var i=0;i<userArr.length;i++){
			
			if(respName == userArr[i].value){
				
				isExists = true;
			}
		}
		if(isExists==false){
			
			 //  layer.alert("责任人不存在",{icon: 2});	
		       $(input).val("");
		       return false;
		}
	}
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
<title>质量问题列表</title>
</head>
<body>
<form name="searchForm" id="searchForm" method="post" action="">
	  <div class="accordion">
	   <h3>质量问题列表</h3> 
	   <div>
			<table width="100%" class="search">
				<tr>
				    <td>质检单号：</td>
					<td><form:input path="dto.qcId"/></td>
					<td>责任部门 :</td>
					<td>
					  <form:input path="dto.depName" style="width:250px" onfocus="depAutoComplete()"  onblur="depExists(this)"/> 
					</td>
					<td>责任岗位 :</td>
					<td>
					 <form:input path="dto.jobName" onfocus="jobAutoComplete()"  onblur="jobExists(this)"/>　
					</td>
				</tr>
				<tr>
					<td>供应商名称：</td>
					<td>
					   <form:input path="dto.agencyName"  /> 
					</td>
					<td>问题类型：</td>
					<td>
					   <form:input path="dto.qptName" style="width:250px" onfocus="qptAutoComplete()"  onblur="qptExists(this)"/> 
					</td>
						<td>责任人：</td>
			    	<td><form:input path="dto.respPersonName"  onfocus="userAutoComplete()"  onblur="respExists(this)"/></td>
					<td>
						<input type="button" class="blue" 	value="查询" onclick="searchResetPage()"/> 
						<input type="button" class="blue" value="重置" onclick="resetSearchTable()" />
					</td>
				</tr>
				</table>
			</div>
	  </div>
	  <table class="listtable">
	    	<tr>
				<th class="qcId">质检单号</th>
			    <th class="firstQpTypeName">一级问题</th>
				<th class="secondQpTypeName">二级问题</th>
		   	    <th class="thirdQpTypeName">三级问题</th>
				<th class="firstDepName">一级责任部门</th>
				<th class="twoDepName">二级责任部门</th>
				<th class="threeDepName">三级责任部门</th>
				<th class="jobName">责任岗位</th>
				<th class="respPersonName">责任人</th>
				<th class="agencyName">供应商</th>
				<th class="addTime">添加时间</th>
	   		 </tr>
	   		<c:forEach items="${dto.dataList}" var="qualityProblem">
	   		 <tr>
		   		 <td class="qcId">
		   		 	<a href="javascript:void(0)" onclick="openWin('质检报告','qc/qcBill/${qualityProblem.qcId}/qcReport',1000,520)">${qualityProblem.qcId}</a>
		   		 </td>
		   		 <td class="firstQpTypeName">${qualityProblem.firstQpTypeName }</td>
		   		 <td class="secondQpTypeName">${qualityProblem.secondQpTypeName }</td>
		   		 <td class="thirdQpTypeName">${qualityProblem.thirdQpTypeName }</td>
		   		 <td class="firstDepName">${qualityProblem.firstDepName }</td>
		   		 <td class="twoDepName">${qualityProblem.twoDepName }</td>
		   		 <td class="threeDepName">${qualityProblem.threeDepName }</td>
		   		 <td class="jobName">${qualityProblem.jobName }</td>
		   		 <td class="respPersonName">${qualityProblem.respPersonName }</td>
		   		 <td class="agencyName">${qualityProblem.agencyName }</td>
		   		 <td class="addTime"><fmt:formatDate value="${qualityProblem.addTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	   		 </tr>
	        </c:forEach>
	  </table>
 	<%@include file="/WEB-INF/jsp/common/pager.jsp"%>
</form>
</body>
</html>
