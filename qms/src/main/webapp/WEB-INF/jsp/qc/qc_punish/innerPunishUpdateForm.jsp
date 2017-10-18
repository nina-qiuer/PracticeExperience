<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>
<html>
<head>
<script type="text/javascript">
var depArr = new Array();
var jobArr = new Array();
var userArr =new Array();

$(document).ready(function() {
	depAutoComplete();
	jobAutoComplete();
	$("#innerpunish_form").validate({
		rules:{
			  depName:{
				  depExists:true,
		   	   required:true
		      },
		      jobName:{
		    	  jobExists:true,
			   	   required:true
			      },
		      punishPersonName:{
		   	   required:true
				} ,
			  economicPunish:{
		                number:true,
		                required:true,
		                min:0
		            },
	        scorePunish:{
	                digits:true,
	                required:true,
	                min:0
	            }
        },
        messages:{
        	
        	depName:{required:"请输入部门",depExists:"部门不存在"},
        	jobName:{required:"请输入岗位",jobExists:"岗位不存在"},
        	punishPersonName:{required:"请输入责任人"},
        	economicPunish:{ number:"请输入数字",required:"请输入处罚金额"},
        	scorePunish:{digits:"请输入整数",required:"请输入记分值"}
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
	
		$("#respManagerName").autocomplete({
			source : userArr,
			autoFocus : true
		}).data("ui-autocomplete")._renderItem = function(ul, item) {
			return $("<li>").append("<a>" + item.value + "</a>").appendTo(ul);
		};
		
		$("#respGeneralName").autocomplete({
			source : userArr,
			autoFocus : true
		}).data("ui-autocomplete")._renderItem = function(ul, item) {
			return $("<li>").append("<a>" + item.value + "</a>").appendTo(ul);
		};
	}
}
function iFrameHeight(iframe) {   
	var ifm= document.getElementById(iframe.id);   
	var subWeb = document.frames ? document.frames[iframe.id].document : ifm.contentDocument;   
	if(ifm != null && subWeb != null) {
	   ifm.height = subWeb.body.scrollHeight;
	   ifm.width = subWeb.body.scrollWidth;
	}   
} 

  function doSubmit(input) {
	  
	if($('#innerpunish_form').valid()){
		disableButton(input);
   		$.ajax( {
 			url : 'qc/innerPunish/updateInnerPunish',
 			data : 	$('#innerpunish_form').serialize(),
 			type : 'post',
 			dataType:'json',
 			cache : false,
 			success : function(result) {
 				if(result){
 					enableButton(input);
 			    	if(result.retCode == "0"){
 			    		if($("#useFlag").val() == 2){
 			    			parent.search();
  			    		}else{
  			    			parent.frames['punishBill'].location.reload(); 
  			    		}
 			    		parent.layer.closeAll();
 					 }else{
 						
 						layer.alert(result.resMsg, {icon: 2});
 					}
 			     }
 			 }
 		});
		    	  		
	}
  }

  function refresh(){
	  var frame = document.getElementById('punishMain');
	  frame.src = "qc/innerPunishBasis/"+$('#id').val()+"/"+$('#useFlag').val()+"/toInnerPunishBasis";
  }
</script>
</head>
<body>
<form name="innerpunish_form" id="innerpunish_form" method="post" action="" >
<form:hidden path="innerPunish.qcId"/>
<form:hidden path="innerPunish.punishPersonId"/>
<form:hidden path="innerPunish.id"/>
 <input type="hidden" id="useFlag" name ="useFlag" value ="${useFlag}" >
<table class="datatable" >
<c:if test="${useFlag==2}">
	<tr>
		<th align="right" width="100">订单号：</th>
		<td>
		    <form:input  path="innerPunish.ordId" />
			</td>
		</tr>
		<tr>
			<th align="right" width="100">产品单号：</th>
			<td>
			    <form:input  path="innerPunish.prdId" />
			</td>
		</tr>
		<tr>
			<th align="right" width="100">质检类型：</th>
			<td>
			    <form:input  path="innerPunish.qcTypeName" readonly="true" />
			</td>
		</tr>
	<tr>
</c:if>
<tr>
<th align="right" width="100" height="30"><span style="color: red">*</span>处罚人：</th>
<td>
   <form:input path="innerPunish.punishPersonName"  readonly="true" />
</td>
</tr>
<tr>
	<th align="right" width="100">连带责任：</th>
	<td>
		<form:radiobutton path="innerPunish.relatedFlag" value="1" label="是"/>
		<form:radiobutton path="innerPunish.relatedFlag" value="0" label="否"/>　
	
	</td>
</tr>
<tr>
<th align="right" width="100" height="30"><span style="color: red">*</span>关联部门：</th>
<td>
     <form:input path="innerPunish.depName"  style="width:300px" />
</td>
</tr>
<tr>
<th align="right" width="100" height="30"><span style="color: red">*</span>关联岗位：</th>
<td>
    <form:input path="innerPunish.jobName"  /> 
</td>
</tr>
<tr>
	<th align="right" width="100"><span style="color: red">*</span>处罚依据：</th>
	<td>
	<div>
	<iframe name="punishMain" src="qc/innerPunishBasis/${innerPunish.id}/${useFlag}/toInnerPunishBasis" width="100%"  scrolling="yes" frameborder="0" id="punishMain" ></iframe>
	</div>
	</td>
</tr>
<tr>
<th align="right" width="100" height="30">记分处罚：</th>
<td>
    <form:input path="innerPunish.scorePunish"  /> 
</td>
</tr>
<tr>
<th align="right" width="100" height="30">经济处罚：</th>
<td>
    <form:input path="innerPunish.economicPunish"  /> 
</td>
</tr>
<c:if test="${useFlag==1 || useFlag==4}">
	<tr>
		<th align="right" width="100"><span style="color: red">*</span>责任单ID：</th>
		<td>
		    <form:input  path="innerPunish.respId"  />
		</td>
	</tr>
	<tr>
		<th align="right" width="100"><span style="color: red">*</span>责任单类型：</th>
		<td>
		    <form:select path="innerPunish.respType">
		    	<form:option value="1">内部责任单</form:option>
		    	<c:if test="${useFlag == 1}">
		    		<form:option value="2">外部责任单</form:option>
		    	</c:if>
		    </form:select>
		</td>
	</tr>
</c:if>
<c:if test="${useFlag == 2}">
	<tr>
		<th align="right" width="100">责任经理：</th>
		<td>
			<form:input path="innerPunish.respManagerName" onfocus="userAutoComplete()" required="required"/>
		</td>
	</tr>
	<tr>
		<th align="right" width="100">责任总监：</th>
		<td>
			<form:input path="innerPunish.respGeneralName" onfocus="userAutoComplete()" required="required"/> 
		</td>
	</tr>
</c:if>
<c:if test="${useFlag==3 || useFlag==2}">
	<tr>
		<th align="right" width="100" height="30">处罚事由</th>
		<td colspan="3">
			<textarea id="punishReason" name="punishReason" style="width:300px;height:100px;">${innerPunish.punishReason}</textarea>
		</td>
	</tr>
	</c:if>
<tr>
	<th align="right" width="100">操作：</th>
	<td>
		<input type="button" name="submitBtn" class="blue" style="width:100px" value="提交" id="submitBtn" onclick="doSubmit(this)">
	</td>
</tr>
</table>
</form>
</body>
</html>