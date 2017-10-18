<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>
<html>
<head>

<script type="text/javascript">
var depArr = new Array();
var jobArr = new Array();
var userArr = new Array();
var realNameArr = new Array();
$(document).ready(function() {
	jobAutoComplete();
	userAutoComplete();
	depAutoComplete();
	$("#resp_form").validate({
		rules:{
			respReason:{
                  rangelength:[1,1000]
			},
	       depName:{
	    	   required:true,
	    	   depExists:true
	       },
 			jobName:{
 				required:true,
 				jobExists:true
	       },
		  respPersonName:{
			  // userExists : true
	    	   //required:true
			},
			impPersonName:{
				 required:true,
				 userExists : true
			}
        },
        messages:{
        	
        	depName:{required:"请输入部门",depExists:"部门不存在"},
        	jobName:{required:"请输入岗位",jobExists:"岗位不存在"},
            impPersonName:{required:"请输入改进人",userExists:"改进人不存在"}
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
	jQuery.validator.addMethod("userExists", function(value, element) {
		if($.trim(value)!=''){
			var isExists = false;
			for(var i=0;i<realNameArr.length;i++){
				
				if(value == realNameArr[i]){
					
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
	  
	$('#respPersonName').bind('input propertychange', function() {  
		  var respPersonName = $("#respPersonName").val();
		 if($.trim(respPersonName)==''){
			
			 $(".errorMsg").html("请输入责任人");
	    	 $(".errorMsg").css("color","red");
	    	 $(".errorMsg").addClass("msg");
		}else{
			
			 $(".errorMsg").html("");
			 $(".errorMsg").removeClass("msg");
		}
	});  
	});  



function userAutoComplete() {
	
	<c:forEach items="${userNames}" var="userMap">
		userArr.push({
			label: '${userMap.label}',
			value: '${userMap.realName}'
		});
		realNameArr.push('${userMap.realName}');
	</c:forEach>
	
	$("#respPersonName").autocomplete({
	    source: userArr,
	    autoFocus : true
	}).data( "ui-autocomplete" )._renderItem = function( ul, item ) {
		return $( "<li>" )
        .append( "<a>" + item.value + "</a>" )
        .appendTo( ul );
	};
	$("#impPersonName").autocomplete({
	    source: userArr,
	    autoFocus : true
	}).data( "ui-autocomplete" )._renderItem = function( ul, item ) {
		return $( "<li>" )
        .append( "<a>" + item.value + "</a>" )
        .appendTo( ul );
	};
}
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


function queryUser(){
	
	 $(".errorMsg").html("");
     var respPersonName =$('#respPersonName').val();	
     if($.trim(respPersonName)==''){
    	 $(".errorMsg").html("请输入责任人");
    	 $(".errorMsg").css("color","red");
         $(".errorMsg").addClass("msg");
    	 return false;
     }
	  $.ajax( {
			url : 'qc/qualityProblem/getUserDetail',
			data : 
			{
			    respPersonName : respPersonName
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
			    		$('#depName').val( detail.impDepName);
			    		$('#depId').val( detail.depId);
			    		$('#jobName').val( detail.jobName);
			    		$('#jobId').val(detail.jobId);	
			    		$('#respPersonId').val(detail.id);
			    		$('#impPersonName').val(detail.impPersonName);
					 }else{
						    $('#respPersonName').val("");
						    $('#respPersonId').val("");
						    $('#depName').val("");
				    		$('#depId').val("");
				    		$('#jobName').val("");
				    		$('#jobId').val("");	
				    	    $('#impPersonName').val("");
						    $(".errorMsg").html("此员工不存在");
				    	    $(".errorMsg").css("color","red");
				    	    $(".errorMsg").addClass("msg");
						
					}
			     }
			 }
		    });
	  
  }
  

  function doSubmit(input) {
	  var respPersonName =  $('#respPersonName').val();
	  var respPersonId =  $('#respPersonId').val();
	  if($.trim(respPersonName)==''||respPersonId==''){
	    	 $(".errorMsg").html("请输入责任人");
	    	 $(".errorMsg").css("color","red");
	    	 $(".errorMsg").addClass("msg");
	    	 return false;
	     }
  	var respId = $('#id').val();
	if($('#resp_form').valid()){
		disableButton(input);
		    if(respId!="" && respId!=null){
		    	  		$.ajax( {
		    	  			url : 'qc/devResp/updateDevResp',
		    	  			data : 	$('#resp_form').serialize(),
		    	  			type : 'post',
		    	  			dataType:'json',
		    	  			cache : false,
		    	  			success : function(result) {
		    	  			
		    	  				if(result)
		    	  				{
		    	  					enableButton(input);
		    	  			    	if(result.retCode == "0")
		    	  					{
		    	  			    		 parent.frames['respBill'].location.reload();
		    	  			    		 parent.layer.closeAll();
		    	  					 }else{
		    	  						
		    	  						layer.alert(result.resMsg, {icon: 2});
		    	  					}
		    	  			     }
		    	  			 }
		    	  		    });
		    	  		
		    	  	}else{
		    	  		
		    	  		$.ajax( {
		    	  			url : 'qc/devResp/addDevResp',
		    	  			data : 	$('#resp_form').serialize(),
		    	  			type : 'post',
		    	  			dataType:'json',
		    	  			cache : false,
		    	  			success : function(result) {
		    	  				if(result)
		    	  				{
		    	  					enableButton(input);
		    	  			    	if(result.retCode == "0")
		    	  					{
		    	  			    		 //parent.frames['respBill'].location.reload();
		    	  			    		parent.location.reload();
		    	  			    		parent.layer.closeAll();
		    	  					 }else{
		    	  						
		    	  						layer.alert(result.resMsg, {icon: 2});
		    	  					}
		    	  			     }
		    	  			 }
		    	  		    });
		    	  	}
	
	}
  }

</script>
</head>
<body>
<form name="resp_form" id="resp_form" method="post" action="" >
<form:hidden path="devResp.devId"/>
<form:hidden path="devResp.id"/>
<form:hidden path="devResp.jobId"/>
<form:hidden path="devResp.respPersonId"/>
<form:hidden path="devResp.depId" />
<table class="datatable" >
<tr>
<th align="right" width="100" height="30"><span style="color: red">*</span>责任人名字：</th>
<td >
 <c:if test="${devResp.id ==null }">
    <form:input path="devResp.respPersonName" onblur="queryUser()"/>
  </c:if>
  <c:if test="${devResp.id !=null }">
     <form:input path="devResp.respPersonName" readonly="true"/>
  </c:if>
  <span class="errorMsg"></span>
</td>
</tr>
<tr>
<th align="right" width="100" height="30">责任人部门：</th>
<td >
    <form:input path="devResp.depName"  style="width:300px"/>
</td>
</tr>
<tr>
	<th align="right" width="100" height="30">责任人岗位：</th>
	<td >
	   <form:input path="devResp.jobName"/>
	</td>
</tr>
<tr>
	<th align="right" width="100" height="30">改进人姓名：</th>
	<td>
	   <form:input path="devResp.impPersonName"/>
	</td>
</tr>
<tr>
		<th align="right" width="100" height="30">责任事由：</th>
		<td colspan="3">
		     <form:textarea path="devResp.respReason" style="width:300px;height:100px;"/>
		</td>
	</tr>
	<tr>
		<th align="right" width="100" height="30"></th>
		<td colspan="3">
			<input type="button" name="submitBtn" class="blue"  value="提交" id="submitBtn" onclick="doSubmit(this)">
		</td>
	</tr>
</table>
</form>
</body>
</html>