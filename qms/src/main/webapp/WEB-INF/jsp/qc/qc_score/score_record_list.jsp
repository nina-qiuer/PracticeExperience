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
var userArr =new Array();

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
				}
			}
		});
	
		$("#punishPersonName").autocomplete({
		    source: userArr,
		    autoFocus : true
		}).data( "ui-autocomplete" )._renderItem = function( ul, item ) {
			return $( "<li>" ).append( "<a>" + item.value + "</a>" ).appendTo( ul );
		};
		$("#respManagerName").autocomplete({
		    source: userArr,
		    autoFocus : true
		}).data( "ui-autocomplete" )._renderItem = function( ul, item ) {
			return $( "<li>" ).append( "<a>" + item.value + "</a>" ).appendTo( ul );
		};
		$("#respGeneralName").autocomplete({
		    source: userArr,
		    autoFocus : true
		}).data( "ui-autocomplete" )._renderItem = function( ul, item ) {
			return $( "<li>" ).append( "<a>" + item.value + "</a>" ).appendTo( ul );
		};
	}
}

function search() {
	$('#depId').val(null);
	$('#jobId').val(null);
	$('#qcTypeId').val(null);
	$("#searchForm").attr("action", "qc/scoreRecord/list");
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

function exportExcel(input){
	disableButton(input);
	$.ajax( {
		url : 'qc/scoreRecord/checkExport',
		data : $('#searchForm').serialize(),
		type : 'post',
		dataType:'json',
		cache : false,
		success : function(result) {
			if(result){
		    	if(result.retCode == "0"){
		    		$("#searchForm").attr("action","qc/scoreRecord/exports"); 
		    		$("#searchForm").submit(); 	
				 }else{
					 layer.alert(result.resMsg,{icon: 2});
					 enableButton(input);
				}
		     }
		 }
	    }); 
}

function delInnerPunish(innerId){
	var msg = "您确定删除编号["+innerId+"]的内部处罚单吗？";
	layer.confirm(msg, {icon: 3}, function(index){
	$.ajax( {
			url : 'qc/innerPunish/deleteInnerPunish',
			data : 	{innerId:innerId} ,
			type : 'post',
			dataType:'json',
			cache : false,
			success : function(result) {
				if(result)
				{
			    	if(result.retCode == "0")
					{
			    		searchResetPage();
					 }else{
						layer.alert(result.resMsg, {icon: 2});
					}
			     }
			 }
		    });
	  });
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
					<td>处罚单号：</td>
					<td><form:input path="dto.id"/></td>
					<td>质检单号：</td>
					<td><form:input path="dto.qcId"/></td>
					<td>订单号：</td>
					<td><form:input path="dto.ordId"/></td>
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
					<td>被处罚人姓名：</td>
					<td><form:input path="dto.punishPersonName" onfocus="userAutoComplete()"/></td>
					<td>被处罚人工号：</td>
					<td ><form:input path="dto.pubPersonId"/></td>
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
					<td>是否连带责任：</td>
					<td >
						<form:select path="dto.relatedFlag" style="width: 100px;">
							<form:option value="">全部</form:option>
							<form:option value="0">否</form:option>
							<form:option value="1">是</form:option>
						</form:select>
					</td>
					<td>责任经理：</td>
					<td><form:input path="dto.respManagerName" onfocus="userAutoComplete()"/></td>
					<td>责任总监：</td>
					<td><form:input path="dto.respGeneralName" onfocus="userAutoComplete()"/></td>
				</tr>
				<tr>
					<td colspan="10" style="text-align: right;">
						<input type="button" class="blue" 	value="查询" onclick="searchResetPage()"/> 
						<input type="button" class="blue" value="重置" onclick="resetSearchTable()" />
						<c:if test="${fn:contains(loginUser_WCS,'addPunish')}">
							<input type="button" value="新增"  class="blue" onclick="openWin('新增处罚单', 'qc/innerPunish/0/2/toAdd', 800, 490)">
							<input type="button" value="导入" title="处罚单导入" class="blue" onclick="openWin('处罚单导入', 'qc/scoreRecord/toImport',  870, 300)">
			            </c:if>
			           	<input type="button" class="blue" id ="exports" name="exports" value="导出" onclick="exportExcel(this)" />
					</td>
				</tr>
				</table>
			</div>
	  </div>
	  <table class="listtable">
	    	<tr>
				<th class="id">处罚单号</th>
				<th class="qcId">质检单号</th>
				<th class="orderId">订单号</th>
				<th class="prdId">产品编号</th>
				<th class="punishPersonName">被处罚人姓名</th>
				<th class="pubPersonId">被处罚人工号</th>
				<th class="depName">关联部门</th>
				<th class="jobName">关联岗位</th>
				<th class="qcTypeName">质检类型</th>
				<th class="scorePunish">记分处罚(分)</th>
				<th class="economicPunish">经济处罚(元)</th>
				<th class="qcPerson">质检人</th>
				<th class="relatedFlag">是否连带责任</th>
				<th >质检完成时间</th>
				<th >操作</th>
	   		 </tr>
	   		<c:forEach items="${dto.dataList}" var="innerPunishBill">
	   		 <tr>
		   		 <td class="id"><a href="javascript:void(0)" onclick="openWin('内部处罚单详情','qc/innerPunish/${innerPunishBill.id}/getPunishDetail','800','500')">${innerPunishBill.id }</a></td>
		   		 <td class="qcId">${innerPunishBill.qcId }</td>
		   		 <td class="orderId">${innerPunishBill.ordId }</td>
		   		 <td class="prdId">${innerPunishBill.prdId }</td>
		   		 <td class="punishPersonName">${innerPunishBill.punishPersonName }</td>
		   		 <td class="pubPersonId">${innerPunishBill.pubPersonId }</td>
		   		 <td  style="text-align:left" >${innerPunishBill.depName }</td>
		   		 <td class="jobName">${innerPunishBill.jobName }</td>
		   		 <td style="text-align:left">${innerPunishBill.qcTypeName }</td>
		   		 <td class="scorePunish">${innerPunishBill.scorePunish }</td>
		   		 <td class="economicPunish">${innerPunishBill.economicPunish }</td>
		   		 <td class="qcPerson">${innerPunishBill.qcPerson }</td>
		   		 <td class="relatedFlag">
		   		 	<c:if test="${innerPunishBill.relatedFlag == 0}">
		   		   		否	
		   		    </c:if>
		   		    <c:if test="${innerPunishBill.relatedFlag == 1}">
		   		  	 	 是
		   		 	</c:if>
		   		 </td>
		   		 <td class="finishTime"><fmt:formatDate value="${innerPunishBill.qcFinishTime }" pattern="yyyy-MM-dd"/></td>
		   		 <td style="text-align: center;" width="130">
		   		 <c:if test="${ innerPunishBill.groupFlag ==2}">
					<input type="button" class="blue" value="修改" onclick="openWin('修改记分单', 'qc/innerPunish/${innerPunishBill.id}/2/updateInner', 800, 490)">
					 </c:if>
					<c:if test="${fn:contains(loginUser_WCS,'deletePunish') && innerPunishBill.groupFlag ==2}">
						<input type="button" class="blue" value="删除" onclick="delInnerPunish('${innerPunishBill.id}')">
					</c:if>
				</td>
	   		 </tr>
	        </c:forEach>
	  </table>
 	<%@include file="/WEB-INF/jsp/common/pager.jsp"%>
</form>
</body>
</html>
