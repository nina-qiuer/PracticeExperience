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
	
	depAutoComplete();
	jobAutoComplete();
	qcTypeAutoComplete();
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
			qcId:{
                digits:true,
                min:0
			},
			cmpId:{
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
			jiraId:{
                digits:true,
                min:0
			},
		   economicPunishBgn:{
		                number:true,
		                min:0
		            },
		    economicPunishEnd:{
		                number:true,
		                min:0
		          },
		    scorePunishBgn:{
		                digits:true,
		                min:0
		            },
		    scorePunishEnd:{
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
        	qcId:{digits:"请输入整数"},
        	cmpId:{digits:"请输入整数"},
        	ordId:{digits:"请输入整数"},
        	prdId:{digits:"请输入整数"},
        	economicPunishBgn:{ number:"请输入数字"},
        	economicPunishEnd:{ number:"请输入数字"},
        	scorePunishBgn:{digits:"请输入整数"},
        	scorePunishEnd:{digits:"请输入整数"},
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

	<c:forEach items="${depNames}" var="depName">
		depArr.push('${depName}');
	</c:forEach>
	
    $("#depName").autocomplete({
	    source: depArr,
	    autoFocus : true
	});
}
function jobAutoComplete() {
	
	<c:forEach items="${jobNames}" var="jobName">
	jobArr.push('${jobName}');
	</c:forEach>
	
    $("#jobName").autocomplete({
	    source: jobArr,
	    autoFocus : true
	});
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
	  var scorePunishBgn = $("#scorePunishBgn").val();
	  var scorePunishEnd = $("#scorePunishEnd").val();
	  var economicPunishBgn = $("#economicPunishBgn").val();
	  var economicPunishEnd = $("#economicPunishEnd").val();
	  if($.trim(scorePunishBgn)!='' && $.trim(scorePunishEnd)!=''){
		 if(parseInt(scorePunishBgn)> parseInt(scorePunishEnd)){
			 
			 $(".errorMsg").html("记分范围非法!");
	    	 $(".errorMsg").css("color","red");
	    	 $(".errorMsg").addClass("msg");
			 return false;
		 }else{
				
			 $(".errorMsg").html("");
			 $(".errorMsg").removeClass("msg");
		}
	  }
	  if($.trim(economicPunishBgn)!='' && $.trim(economicPunishEnd)!=''){
			 if(parseFloat(economicPunishBgn)> parseFloat(economicPunishEnd)){
				 
				 $(".errorEcMsg").html("处罚金额范围非法!");
		    	 $(".errorEcMsg").css("color","red");
		    	 $(".errorEcMsg").addClass("msg");
				 return false;
			 }else{
					
				 $(".errorEcMsg").html("");
				 $(".errorEcMsg").removeClass("msg");
			}
	  }
		
		$("#searchForm").attr("action", "qc/innerPunish/list");
		$("#searchForm").submit();

}
$(function(){  
	  
	$('#scorePunishBgn').bind('input propertychange', function() {  
		  var scorePunishBgn = $("#scorePunishBgn").val();
		  var scorePunishEnd = $("#scorePunishEnd").val();
		  if($.trim(scorePunishBgn)!='' && $.trim(scorePunishEnd)!=''){
			 if(parseInt(scorePunishBgn)> parseInt(scorePunishEnd)){
				 
				 $(".errorMsg").html("记分范围非法!");
		    	 $(".errorMsg").css("color","red");
		    	 $(".errorMsg").addClass("msg");
				 
			 }else{
					
				 $(".errorMsg").html("");
				 $(".errorMsg").removeClass("msg");
			}
			
		}else{
			
			 $(".errorMsg").html("");
			 $(".errorMsg").removeClass("msg");
		}
	});  
	$('#scorePunishEnd').bind('input propertychange', function() {  
		  var scorePunishBgn = $("#scorePunishBgn").val();
		  var scorePunishEnd = $("#scorePunishEnd").val();
		  if($.trim(scorePunishBgn)!='' && $.trim(scorePunishEnd)!=''){
			 if(parseInt(scorePunishEnd) < parseInt(scorePunishBgn)){
				 
				 $(".errorMsg").html("记分范围非法!");
		    	 $(".errorMsg").css("color","red");
		    	 $(".errorMsg").addClass("msg");
				 
			 }else{
					
				 $(".errorMsg").html("");
				 $(".errorMsg").removeClass("msg");
			}
			
		}else{
			
			 $(".errorMsg").html("");
			 $(".errorMsg").removeClass("msg");
		}
	});  
	
	$('#economicPunishBgn').bind('input propertychange', function() {  
		  var economicPunishBgn = $("#economicPunishBgn").val();
		  var economicPunishEnd = $("#economicPunishEnd").val();
		  
		  if($.trim(economicPunishBgn)!='' && $.trim(economicPunishEnd)!=''){
			 if(parseFloat(economicPunishBgn)> parseFloat(economicPunishEnd)){
				 
				 $(".errorEcMsg").html("处罚金额范围非法!");
		    	 $(".errorEcMsg").css("color","red");
		    	 $(".errorEcMsg").addClass("msg");
				 
			 }else{
					
				 $(".errorEcMsg").html("");
				 $(".errorEcMsg").removeClass("msg");
			}
			
		}else{
			
			 $(".errorEcMsg").html("");
			 $(".errorEcMsg").removeClass("msg");
		}
	});  
	$('#economicPunishEnd').bind('input propertychange', function() {  
		  var economicPunishBgn = $("#economicPunishBgn").val();
		  var economicPunishEnd = $("#economicPunishEnd").val();
		  if($.trim(economicPunishBgn)!='' && $.trim(economicPunishEnd)!=''){
			 if(parseFloat(economicPunishEnd) < parseFloat(economicPunishBgn)){
				 
				 $(".errorEcMsg").html("处罚金额范围非法!");
		    	 $(".errorEcMsg").css("color","red");
		    	 $(".errorEcMsg").addClass("msg");
				 
			 }else{
					
				 $(".errorEcMsg").html("");
				 $(".errorEcMsg").removeClass("msg");
			}
			
		}else{
			
			 $(".errorEcMsg").html("");
			 $(".errorEcMsg").removeClass("msg");
		}
	}); 
	}); 
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
<title>内部处罚单列表</title>

</head>
<body>
<form name="searchForm" id="searchForm" method="post" action="">
   <form:hidden path="dto.depId"/>
   <form:hidden path="dto.jobId"/>
   <form:hidden path="dto.qcTypeId"/>
	  <div class="accordion">
	   <h3>内部处罚单列表</h3> 
	   <div>
			<table width="100%" class="search">
				<tr>
					<td>处罚单号：</td>
					<td><form:input path="dto.id"/></td>
				    <td>质检单号：</td>
					<td ><form:input path="dto.qcId"/></td>
					<td>添加时间：</td>
					<td>
						<form:input path="dto.addPunishTimeBgn"  onfocus="setMaxDate('addPunishTimeEnd')" />
						 至
						<form:input path="dto.addPunishTimeEnd"  onfocus="setMinDate('addPunishTimeBgn')" />
					</td>
					<td>关联部门 :</td>
					<td>
					   <form:input path="dto.depName" style="width:250px"/> 
					</td>
				</tr>
				<tr>
				    <td>添加人：</td>
			    	<td><form:input path="dto.addPerson"/></td>
					<td>投诉单号：</td>
					<td><form:input path="dto.cmpId"/></td>
					<td>记分处罚(分)：</td>
					<td><input type="text" name="scorePunishBgn" id="scorePunishBgn"
						value="${dto.scorePunishBgn }" /> 至 <input type="text" id="scorePunishEnd"
						name="scorePunishEnd" value="${dto.scorePunishEnd }" />
						<span class="errorMsg"></span></td>
			   		 <td> 关联岗位 :</td>
					<td>
					  <form:input path="dto.jobName" style="width:250px"/> 
					</td>
				</tr>
				<tr>
					<td>订单号：</td>
					<td><form:input path="dto.ordId"/></td>
			    	<td>产品编号：</td>
					<td><form:input path="dto.prdId"/></td>
					<td>经济处罚(元)：</td>
					<td><input type="text" name="economicPunishBgn" id="economicPunishBgn"
						value="${dto.economicPunishBgn }" /> 至 <input type="text" id="economicPunishEnd"
						name="economicPunishEnd" value="${dto.economicPunishEnd }" />
						<span class="errorEcMsg"></span></td>
					<td> 质检类型:</td>
					<td>
					  <form:input path="dto.qcTypeName" style="width:250px" onfocus="qcTypeAutoComplete()"/>
					</td>
				</tr>
				<tr>
					<td>质检状态：</td>
					<td >
						<div id="radioset">
					 	    <form:radiobutton path="dto.state" value="-1" label="全部" />
							<form:radiobutton path="dto.state" value="3" label="质检中" />
							<form:radiobutton path="dto.state" value="6" label="已待结" />
							<form:radiobutton path="dto.state" value="7" label="审核中" />
							<form:radiobutton path="dto.state" value="4" label="已完成" />
						</div>
					</td>
					<td>被处罚人姓名：</td>
					<td><form:input path="dto.punishPersonName"/></td>
					<td>被处罚人工号：</td>
					<td ><form:input path="dto.pubPersonId"/></td>
					<td>是否连带责任：</td>
					<td >
						<form:select path="dto.relatedFlag" style="width: 120px;">
							<form:option value="">全部</form:option>
							<form:option value="0">否</form:option>
							<form:option value="1">是</form:option>
						</form:select>
					</td>
				</tr>
				<tr>
					<td colspan="8" style="text-align: right;">
						<input type="button" class="blue" 	value="查询" onclick="searchResetPage()"/> 
						<input type="button" class="blue" value="重置" onclick="resetSearchTable()" />
					</td>
				</tr>
				</table>
			</div>
	  </div>
	  <table class="listtable">
	    	<tr>
				<th class="id">处罚单号</th>
				<th class="qcId">质检单号</th>
				<th class="state">质检状态</th>
				<th class="cmpId">投诉单号</th>
				<th class="orderId">订单号</th>
				<th class="prdId">产品编号</th>
				<th class="punishPersonName">被处罚人姓名</th>
				<th class="pubPersonId">被处罚人工号</th>
				<th class="depName">关联部门</th>
				<th class="jobName">关联岗位</th>
				<th class="qcTypeName">质检类型</th>
				<th class="scorePunish">记分处罚(分)</th>
				<th class="economicPunish">经济处罚(元)</th>
				<th class="addPerson">添加人</th>
				<th class="addTime">添加时间</th>
				<th class="relatedFlag">是否连带责任</th>
	   		 </tr>
	   		<c:forEach items="${dto.dataList}" var="innerPunishBill">
	   		 <tr>
		   		 <td class="id"><a href="javascript:void(0)" onclick="openWin('内部处罚单详情','qc/innerPunish/${innerPunishBill.id}/getPunishDetail','800','500')">${innerPunishBill.id }</a></td>
		   		 <td class="qcId">${innerPunishBill.qcId }</td>
		   		 <td class="state">
		   		  <c:if test="${innerPunishBill.qcState == 3}">
		   		   	质检中	
		   		  </c:if>
		   		  <c:if test="${innerPunishBill.qcState == 6}">
		   		  	 已待结
		   		 </c:if>
		   		 <c:if test="${innerPunishBill.qcState == 7}">
		   		 	审核中
		   		 </c:if>
		   		 <c:if test="${innerPunishBill.qcState == 4}">
		   		 	已完成
		   		 </c:if>
		   		 </td>
		   		 <td class="cmpId">${innerPunishBill.cmpId }</td>
		   		 <td class="orderId">${innerPunishBill.ordId }</td>
		   		 <td class="prdId">${innerPunishBill.prdId }</td>
		   		 <td class="punishPersonName">${innerPunishBill.punishPersonName }</td>
		   		 <td class="pubPersonId">${innerPunishBill.pubPersonId }</td>
		   		 <td class="shorten10">${innerPunishBill.depName }</td>
		   		 <td class="jobName">${innerPunishBill.jobName }</td>
		   		 <td class="shorten10">${innerPunishBill.qcTypeName }</td>
		   		 <td class="scorePunish">${innerPunishBill.scorePunish }</td>
		   		 <td class="economicPunish">${innerPunishBill.economicPunish }</td>
		   		 <td class="addPerson">${innerPunishBill.addPerson }</td>
		   		 <td class="addTime"><fmt:formatDate value="${innerPunishBill.addTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		   		 <td class="relatedFlag">
		   		 	<c:if test="${innerPunishBill.relatedFlag == 0}">
		   		   		否	
		   		    </c:if>
		   		    <c:if test="${innerPunishBill.relatedFlag == 1}">
		   		  	 	 是
		   		 	</c:if>
		   		 </td>
	   		 </tr>
	        </c:forEach>
	  </table>
 	<%@include file="/WEB-INF/jsp/common/pager.jsp"%>
</form>
</body>
</html>
