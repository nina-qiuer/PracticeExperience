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
var qtArr = new Array();
$(document).ready(function() {
	
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
	//判断字符串是否含有中文字符，含有中文返回true，否则返回false

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
        	jiraId:{digits:"请输入整数"},
        	economicPunishBgn:{ number:"请输入数字"},
        	economicPunishEnd:{ number:"请输入数字"},
        	scorePunishBgn:{digits:"请输入整数"},
        	scorePunishEnd:{digits:"请输入整数"},
        	qcTypeName:{qcTypeExists:"质检类型不存在"}
        }
		
	});
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

function qcTypeAutoComplete() {

	<c:forEach items="${qcTypeNames}" var="qcTypeName">
	qtArr.push('${qcTypeName}');
	</c:forEach>
	
    $("#qcTypeName").autocomplete({
	    source: qtArr,
	    autoFocus : true
	});
}
function search() {
	
	  $('#qcTypeId').val(null);
	  var scorePunishBgn = $("#scorePunishBgn").val();
	  var scorePunishEnd = $("#scorePunishEnd").val();
	  var economicPunishBgn = $("#economicPunishBgn").val();
	  var economicPunishEnd = $("#economicPunishEnd").val();
	  if($.trim(scorePunishBgn)!='' && $.trim(scorePunishEnd)!=''){
		 if(parseInt(scorePunishBgn) > parseInt(scorePunishEnd)){
			 
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
			 if(parseFloat(economicPunishBgn) > parseFloat(economicPunishEnd)){
				 
				 $(".errorEcMsg").html("处罚金额范围非法!");
		    	 $(".errorEcMsg").css("color","red");
		    	 $(".errorEcMsg").addClass("msg");
				 return false;
			 }else{
					
				 $(".errorEcMsg").html("");
				 $(".errorEcMsg").removeClass("msg");
			}
	  }
		
		$("#searchForm").attr("action", "qc/outerPunish/list");
		$("#searchForm").submit();

}
$(function(){  
	  
	$('#scorePunishBgn').bind('input propertychange', function() {  
		  var scorePunishBgn = $("#scorePunishBgn").val();
		  var scorePunishEnd = $("#scorePunishEnd").val();
		  if($.trim(scorePunishBgn)!='' && $.trim(scorePunishEnd)!=''){
			 if(parseInt(scorePunishBgn) > parseInt(scorePunishEnd)){
				 
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
			 if(parseFloat(economicPunishBgn) > parseFloat(economicPunishEnd)){
				 
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
</script>
<title>外部处罚单列表</title>

</head>
<body >
<form name="searchForm" id="searchForm" method="post" action="" >
  <form:hidden path="dto.qcTypeId"/>
	  <div class="accordion">
	   <h3>外部处罚单列表</h3> 
	   <div>
			<table width="100%" class="search">
				<tr>
					<td>处罚单号：</td>
					<td><form:input path="dto.id"/></td>
					<td>供应商ID：</td>
					<td><form:input path="dto.agencyId"/></td>
					<td>供应商名称：</td>
					<td><form:input path="dto.agencyName"/></td>
					<td>添加时间：</td>
					<td>
						<form:input path="dto.addPunishTimeBgn"  onfocus="setMaxDate('addPunishTimeEnd')" />
						 至
						<form:input path="dto.addPunishTimeEnd"  onfocus="setMinDate('addPunishTimeBgn')" />
					</td>
				</tr>
				<tr>
				    <td>添加人：</td>
			    	<td><form:input path="dto.addPerson"/></td>
					<td>投诉单号：</td>
					<td><form:input path="dto.cmpId"/></td>
					<td>质检单号：</td>
					<td><form:input path="dto.qcId"/></td>
					<td>记分处罚(分)：</td>
					<td style="width:300px"><input type="text" name="scorePunishBgn" id="scorePunishBgn"
						value="${dto.scorePunishBgn }" /> 至 <input type="text" id="scorePunishEnd"
						name="scorePunishEnd" value="${dto.scorePunishEnd }" />
						<span class="errorMsg"></span></td>
				</tr>
				<tr>
					<td>JIRA单号：</td>
					<td ><form:input path="dto.jiraNum"/></td>
			    	<td>产品编号：</td>
					<td ><form:input path="dto.prdId"/></td>
					<td>订单号：</td>
					<td ><form:input path="dto.ordId"/></td>
					<td>经济处罚(元)：</td>
					<td style="width:300px"><input type="text" name="economicPunishBgn" id="economicPunishBgn"
						value="${dto.economicPunishBgn }" /> 至 <input type="text" id="economicPunishEnd"
						name="economicPunishEnd" value="${dto.economicPunishEnd }" />
						<span class="errorEcMsg"></span></td>
				</tr>
				<tr>
				<td>质检类型：</td>
					<td >
					  <form:input path="dto.qcTypeName" style="width:250px" /> 
					</td>
					<td>质检状态：</td>
					<td colspan="3">
						<div id="radioset">
							<form:radiobutton path="dto.state" value="-1" label="全部" />
							<form:radiobutton path="dto.state" value="3" label="质检中" />
							<form:radiobutton path="dto.state" value="6" label="已待结" />
							<form:radiobutton path="dto.state" value="7" label="审核中" />
							<form:radiobutton path="dto.state" value="4" label="已完成" />
						</div>
					</td>
					<td style="text-align: center;" colspan="2">
						<input type="button" class="blue" value="查询" onclick="searchResetPage()"/>
						<input type="reset" class="blue" value="重置" />
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
				<th class="jiraNum">JIRA单号</th>
				<th class="agencyId">供应商ID</th>
				<th class="agencyName">供应商名称</th>
				<th class="qcTypeName">质检类型</th>
				<th class="scorePunish">记分处罚(分)</th>
				<th class="economicPunish">经济处罚(元)</th>
				<th class="addPerson">添加人</th>
				<th class="addTime">添加时间</th>
	   		 </tr>
	   		<c:forEach items="${dto.dataList}" var="outerPunishBill">
	   		 <tr>
		   		 <td class="id"><a href="javascript:void(0)" onclick="openWin('外部处罚单详情','qc/outerPunish/${outerPunishBill.id}/getPunishDetail','800','500')">${outerPunishBill.id }</a></td>
		   		 <td class="qcId">${outerPunishBill.qcId }</td>
		   		  <td class="state">
		   		  <c:if test="${outerPunishBill.qcState == 3}">
		   		   	质检中	
		   		  </c:if>
		   		  <c:if test="${outerPunishBill.qcState == 6}">
		   		  	 已待结
		   		 </c:if>
		   		 <c:if test="${outerPunishBill.qcState == 7}">
		   		 	审核中
		   		 </c:if>
		   		 <c:if test="${outerPunishBill.qcState == 4}">
		   		 	已完成
		   		 </c:if>
		   		 <td class="cmpId">${outerPunishBill.cmpId }</td>
		   		 <td class="orderId">${outerPunishBill.ordId }</td>
		   		 <td class="prdId">${outerPunishBill.prdId }</td>
		   		 <td class="jiraNum">${outerPunishBill.jiraNum }</td>
		   		 <td class="agencyId">${outerPunishBill.agencyId }</td>
		   		 <td class="agencyName">${outerPunishBill.agencyName }</td>
		   		 <td class="qcTypeName">${outerPunishBill.qcTypeName }</td>
		   		 <td class="scorePunish">${outerPunishBill.scorePunish }</td>
		   		 <td class="economicPunish">${outerPunishBill.economicPunish }</td>
		   		 <td class="addPerson">${outerPunishBill.addPerson }</td>
		   		 <td class="addTime"><fmt:formatDate value="${outerPunishBill.addTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	   		 </tr>
	        </c:forEach>
	  </table>
 	<%@include file="/WEB-INF/jsp/common/pager.jsp"%>
</form>
</body>
</html>
