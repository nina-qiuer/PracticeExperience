<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>
<html>
<head>
<script type="text/javascript">
var depArr = new Array();
var jobArr = new Array();
var qtArr = new Array();
$(document).ready(function() {
	depAutoComplete();
	jobAutoComplete();
	userAutoComplete();
	$("#innerpunish_form").validate({
		rules:{
			prdId:{
				
				required:true,
                digits:true,
                min:0
			},
			ordId:{
				
				required:true,
                digits:true,
                min:0
			},
			  depName:{
				   required:true,
				   depExists:true
		      },
		      jobName:{
		    	   required:true,
		    	   jobExists:true
			      },
			      
			     qcTypeName:{
			    	 
			    	 required:true,
			    	 qcTypeExists :true 
			    	 
			     },
			  economicPunish:{
			 	   required:true,
		                number:true,
		                min:0
		            },
		        scorePunish:{
		      	   required:true,
		                digits:true,
		                min:0
		            }
        },
        messages:{
        	
        	ordId:{digits:"请输入整数"},
        	prdId:{digits:"请输入整数"},
        	depName:{required:"请输入部门",depExists:"部门不存在"},
        	jobName:{required:"请输入岗位",jobExists:"岗位不存在"},
        	qcTypeName:{required:"请输入质检类型",qcTypeExists:"质检类型不存在"},
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

$(function(){  
	  
	$('#punishPersonName').bind('input propertychange', function() {  
		  var punishPersonName = $("#punishPersonName").val();
		  if($.trim(punishPersonName)==''){
			
			 $(".errorMsg").html("请输入被处罚人");
	    	 $(".errorMsg").css("color","red");
	    	 $(".errorMsg").addClass("msg");
		}else{
			
			 $(".errorMsg").html("");
			 $(".errorMsg").removeClass("msg");
		}
	});  
	  
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
			url : "qc/qcType/getOperQtNames",
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
	var userArr = new Array();
	<c:forEach items="${userNames}" var="userMap">
		userArr.push({
			label: '${userMap.label}',
			value: '${userMap.realName}'
		});
	</c:forEach>
	
	$("#punishPersonName").autocomplete({
	    source: userArr,
	    autoFocus : true
	}).data( "ui-autocomplete" )._renderItem = function( ul, item ) {
		return $( "<li>" )
        .append( "<a>" + item.value + "</a>" )
        .appendTo( ul );
	};
}
function iFrameHeight(iframe) {   
	var ifm= document.getElementById(iframe.id);   
	var subWeb = document.frames ? document.frames[iframe.id].document : ifm.contentDocument;   
	if(ifm != null && subWeb != null) {
	   ifm.height = subWeb.body.scrollHeight;
	   ifm.width = subWeb.body.scrollWidth;
	}   
} 

function queryUser(){
	
     $(".errorMsg").html("");
     var punishPersonName =$('#punishPersonName').val();	
     if($.trim(punishPersonName)==''){
    	 $(".errorMsg").html("请输入被处罚人");
    	 $(".errorMsg").css("color","red");
    	 $(".errorMsg").addClass("msg");
    	 return false;
     }
	  $.ajax( {
			url : 'qc/qualityProblem/getUserDetailSelf',
			data : 
			{
			    respPersonName : punishPersonName
			},
			type : 'post',
			dataType:'json',
			//async: false,
			success : function(result) {
				if(result)
				{
			    	if(result.retCode == "0")
					{
			    		var detail = eval(result.retObj);
			    		$('#depName').val( detail.depName);
			    		$('#jobName').val( detail.jobName);
			    		$('#punishPersonId').val(detail.id);
			    		
					 }else{
						
						$('#punishPersonName').val("");	 
						$('#depName').val("");
			    		$('#jobName').val("");
			    	    $(".errorMsg").html("此员工不存在");
			    	    $(".errorMsg").css("color","red");
			    	    $(".errorMsg").addClass("msg");
					}
			     }
			 }
		    });
	  
  }
  
  

  function doSubmit(input) {
	  var punishPersonName =  $('#punishPersonName').val();
	  var punishPersonId =  $('punishPersonId').val();
	  if($.trim(punishPersonName)==''||punishPersonId==''){
	    	 $(".errorMsg").html("请输入被处罚人");
	    	 $(".errorMsg").css("color","red");
	    	 $(".errorMsg").addClass("msg");
	    	 return false;
	     }
	if($('#innerpunish_form').valid()){
		disableButton(input);
		    	  		$.ajax( {
		    	  			url : 'qc/innerPunish/addScore',
		    	  			data : 	$('#innerpunish_form').serialize(),
		    	  			type : 'post',
		    	  			dataType:'json',
		    	  			cache : false,
		    	  			success : function(result) {
		    	  				if(result)
		    	  				{
		    	  					enableButton(input);
		    	  			    	if(result.retCode == "0")
		    	  					{
		    	  			    		 parent.search();
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
<table class="datatable">
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
		    <form:input  path="innerPunish.qcTypeName"  style="width:300px" onfocus="qcTypeAutoComplete()"/>
		</td>
	</tr>
	<tr>
		<th align="right" width="100"><span style="color: red">*</span>处罚人：</th>
	<td>
		    <form:input  path="innerPunish.punishPersonName"  onblur="queryUser()"/>
			<span class="errorMsg"></span>
		</td>
	</tr>
	<tr>
		<th align="right" width="100"><span style="color: red">*</span>关联部门：</th>
		<td>
		    <form:input  path="innerPunish.depName" style="width:300px"/>
		</td>
	</tr>
	<tr>
		<th align="right" width="100"><span style="color: red">*</span>关联岗位：</th>
		<td>
		     <form:input  path="innerPunish.jobName"  />
		</td>
	</tr>
	<tr>
		<th align="right" width="100"><span style="color: red">*</span>处罚依据：</th>
		<td>
		<div>
			<iframe name="punishMain" src="qc/innerPunishBasis/${innerPunish.id}/${useFlag}/toInnerPunishBasis" width="100%"  scrolling="no" frameborder="0" id="punishMain" onload="iFrameHeight(this)"></iframe>
		</div>
		</td>
	</tr>
	<tr>
		<th align="right" width="100"><span style="color: red">*</span>记分处罚：</th>
		<td>
		    <form:input  path="innerPunish.scorePunish"  />
		</td>
	</tr>
	<tr>
		<th align="right" width="100"><span style="color: red">*</span>经济处罚：</th>
		<td>
		   <form:input  path="innerPunish.economicPunish"  />
		</td>
	</tr>
	<tr>
		<th align="right" width="100" height="30">处罚事由</th>
		<td colspan="3">
			<textarea id="punishReason" name="punishReason" style="width:300px;height:50px;">${innerPunish.punishReason}</textarea>
		</td>
	</tr>
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