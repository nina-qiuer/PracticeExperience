<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>

<html>
<head>
<script type="text/javascript">
var userArr = new Array();
var realNameArr = new Array();
var flag = 0;
$(document).ready(function(){
	<c:forEach items="${userNames}" var="userMap">
		userArr.push({
			label: '${userMap.label}',
			value: '${userMap.realName}'
		});
		realNameArr.push('${userMap.realName}');
	</c:forEach>
	userAutoComplete();
	
	$("#ac_form").validate({
		rules:{
			qcPersonName:{
                //  required:true,
                  userExists:true
			}
        },
        messages:{
        	
        	qcPersonName:{ userExists:"用户不存在!"}
        }
	   });
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
	  
	$('#qcPersonName').bind('input propertychange', function() {  
		  var qcPersonName = $("#qcPersonName").val();
		 if($.trim(qcPersonName)==''){
			
			 $(".errorMsg").html("请输入质检员");
	    	 $(".errorMsg").css("color","red");
	    	 $(".errorMsg").addClass("msg");
		}else{
			
			 $(".errorMsg").html("");
			 $(".errorMsg").removeClass("msg");
		}
	});  
	}); 
	
function userAutoComplete() {
	$("#qcPersonName").autocomplete({
		autoFocus: true,
	    source: userArr
	}).data( "ui-autocomplete" )._renderItem = function( ul, item ) {
		return $( "<li>" )
        .append( "<a>" + item.value + "</a>" )
        .appendTo( ul );
	};
}

function checkUser(){
	
     $(".errorMsg").html("");
     var qcPersonName  =$('#qcPersonName').val();	
     if($.trim(qcPersonName)==''){
    	 $(".errorMsg").html("请输入质检员");
    	 $(".errorMsg").css("color","red");
    	 $(".errorMsg").addClass("msg");
    	 return false;
     }
	  $.ajax( {
			url : 'qc/assignConfigDev/checkUser',
			data : 
			{
				qcPersonName : qcPersonName
			},
			type : 'post',
			dataType:'json',
			async: false,
			success : function(result) {
				if(result)
				{
			    	if(result.retCode == "0")
					{
			    		 flag = 0;
			    		
					 }else{
						
						$('#qcPersonName').val("");	 
			    	    $(".errorMsg").html(result.resMsg);
			    	    $(".errorMsg").css("color","red");
			    	    $(".errorMsg").addClass("msg");
			    	    flag =1;
					}
			     }
			 }
		    });
	  
  }
function doSubmit(input) {
	if(flag==1){
		
		return false;
	}
	if($('#ac_form').valid()){
		disableButton(input);
		$.ajax({
			type: "post",
			url: "qc/assignConfigDev/addQcPerson",
			data: $("#ac_form").serialize(),
			dataType: "json",
			async: false,
			success : function(result) {
				enableButton(input);
				if(result)
				{
			    	if(result.retCode == "0")
					{
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
</script>
</head>
<body>
<form name="ac_form" id="ac_form" method="post" action="" >
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
<table class="datatable">
	<tr>
		<th align="right" width="85"><span style="color: red">*</span>质检员：</th>
		<td><input type="text" id="qcPersonName" name="qcPersonName" onblur="checkUser()"/>
		 <span class="errorMsg"></span>
		</td>
	</tr>
	<tr>
		<th align="right">操作：</th>
		<td><input type="button" class="blue" value="提交" id="submitBtn" id="submitBtn" onclick="doSubmit(this)"></td>
	</tr>
</table>
</form>
</body>
</html>
