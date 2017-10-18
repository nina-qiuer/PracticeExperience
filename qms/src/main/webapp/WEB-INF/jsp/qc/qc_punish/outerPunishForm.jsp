<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>
<html>
<head>
<script  type="text/javascript">
$(document).ready(function() {
	$("#outer_form").validate({
		rules:{
			punishReason:{
                  required:true,
                  rangelength:[1,1000]
			},
		  economicPunish:{
	          number:true,
	          min:0
	      },
		  scorePunish:{
		          digits:true,
		          min:0
	      },
	      agencyName:{
	    	//  agencyExists : true,
	    	 // required:true
	      },
	     /*  economicPunish:{
		 	   required:true,
	                number:true,
	                min:0
	            }, */
	        scorePunish:{
	      	   required:true,
	                digits:true,
	                min:0
	            }
		},
        messages:{
        	
        	punishReason:{required:"请输入处罚事由"},
        	economicPunish:{ number:"请输入数字"},
        	scorePunish:{digits:"请输入整数"},
        	//agencyName:{required:"请输入供应商"},
         	/* economicPunish:{ number:"请输入数字",required:"请输入处罚金额"}, */
        	scorePunish:{digits:"请输入整数",required:"请输入记分值"}
        }
		
	});
}); 
function iFrameHeight(iframe) {   
	var ifm= document.getElementById(iframe.id);   
	var subWeb = document.frames ? document.frames[iframe.id].document : ifm.contentDocument;   
	if(ifm != null && subWeb != null) {
	   ifm.height = subWeb.body.scrollHeight;
	   ifm.width = subWeb.body.scrollWidth;
	}   
} 

$(function(){  
	  
	$('#agencyName').bind('input propertychange', function() {  
		  var agencyName = $("#agencyName").val();
		 if($.trim(agencyName)==''){
			
			 $(".errorMsg").html("请输入供应商");
	    	 $(".errorMsg").css("color","red");
	         $(".errorMsg").addClass("msg");
		}else{
			
			 $(".errorMsg").html("");
			 $(".errorMsg").removeClass("msg");
		}
	});  
}); 
function queryAgency(){
	  
	 $(".errorMsg").html("");
     var agencyName =$('#agencyName').val();	
     if($.trim(agencyName)==''){
    	 $(".errorMsg").html("供应商不能为空!");
    	 $(".errorMsg").css("color","red");
         $(".errorMsg").addClass("msg");
    	 return false;
     }
	  $.ajax( {
			url : 'qc/qualityProblem/getAgencySelf',
			data : 
			{
				agencyName : agencyName,
				qcId: '${outerPunish.qcId}'
			},
			type : 'post',
			dataType:'json',
			cache: false,
			success : function(result) {
				if(result)
				{
			    	if(result.retCode == "0")
					{
			    		var respBill = eval(result.retObj);
			    		$('#agencyId').val( respBill.agencyId);
					 }else{
						
						$('#agencyName').val('');	 
						$('#agencyId').val('');	 
						$(".errorMsg").html("供应商不存在!");
					    $(".errorMsg").css("color","red");
					    $(".errorMsg").addClass("msg");
					}
			     }
			 }
		    });
	  
  }
  

  function doSubmit(input) {
	    var agencyName =  $('#agencyName').val();
		var agencyId =  $('#agencyId').val();
		if($.trim(agencyName)==''||agencyId==''){
		    	 $(".errorMsg").html("请输入供应商");
		    	 $(".errorMsg").css("color","red");
		    	 $(".errorMsg").addClass("msg");
		    	 return false;
		     }
  	if($('#outer_form').valid()){
  		disableButton(input);
  		$.ajax( {
  			url : 'qc/outerPunish/addOuterPunish',
  			data : 	$('#outer_form').serialize(),
  			type : 'post',
  			dataType:'json',
  			cache : false,
  			success : function(result) {
  				enableButton(input);
  				if(result)
  				{
  			    	if(result.retCode == "0")
  					{
  			    		 parent.frames['punishBill'].location.reload();
  			    		// parent.location.reload();
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
	  
	  var frame = document.getElementById('punishOut');
	  frame.src = "qc/outerPunishBasis/"+$('#id').val()+"/"+$('#useFlag').val()+"/toOuterPunishBasis";
  }
</script>
</head>
<body>
<form name="outer_form" id="outer_form" method="post" action="" >
<form:hidden path="outerPunish.qcId"/>
<form:hidden path="outerPunish.id"/>
<input type="hidden" id="useFlag" name ="useFlag" value ="${useFlag}" >
<table class="datatable" >
<tr>
<th align="right" width="100" height="30"><span style="color: red">*</span>供应商名称：</th>
<td>
      <form:input  path="outerPunish.agencyName"  onblur="queryAgency()"/>
   	  <span class="errorMsg"></span>
</td>
</tr>
<tr>
<th align="right" width="100" height="30"><span style="color: red">*</span>供应商编号：</th>
<td>
    <form:input  path="outerPunish.agencyId"  readonly="true"/>
</td>
</tr>
<tr>
		<th align="right" width="100"><span style="color: red">*</span>处罚依据：</th>
		<td>
			<div>
			 <iframe name="punishOut" src="qc/outerPunishBasis/${outerPunish.id}/${useFlag}/toOuterPunishBasis" width="100%"  scrolling="no" frameborder="0" id="punishOut" onload="iFrameHeight(this)"></iframe>
			</div>
		</td>
	</tr>
<tr>
<th align="right" width="100" height="30"><span style="color: red">*</span>记分处罚：</th>
<td>
     <form:input  path="outerPunish.scorePunish"  />
</td>
</tr>
<tr>
<%-- <th align="right" width="100" height="30"><span style="color: red">*</span>经济处罚：</th>
<td>
    <form:input  path="outerPunish.economicPunish"  />
</td> --%>
</tr>
<tr>
	<th align="right" width="100"><span style="color: red">*</span>责任单ID：</th>
	<td>
	    <form:input  path="outerPunish.respId"  />
	</td>
</tr>
<tr>
	<th align="right" width="100"><span style="color: red">*</span>责任单类型：</th>
	<td>
	    <form:select path="outerPunish.respType">
	    	<form:option value="2">外部责任单</form:option>
	    </form:select>
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