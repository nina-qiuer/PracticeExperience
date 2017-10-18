<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>
<html>
<head>

<script type="text/javascript">
$(document).ready(function() {
	$("#kcpSource_form").validate({
		
		rules:{
			name:{
                  required:true
			}
        },
        messages:{
        	
        	name:{required:"请输入KCP来源"}
          
        }
		
	});
	
});



  function doSubmit(input) {
	
  	var id = $('#id').val();
	if($('#kcpSource_form').valid()){
		disableButton(input);
		    if(id!="" && id!=null){
		    	  		$.ajax( {
		    	  			url : 'qs/kcpSource/updateKcp',
		    	  			data : 	$('#kcpSource_form').serialize(),
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
		    	  		
		    	  	}else{
		    	  		
		    	  		$.ajax( {
		    	  			url : 'qs/kcpSource/add',
		    	  			data : 	$('#kcpSource_form').serialize(),
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
  }

</script>
</head>
<body>
<form name="kcpSource_form" id="kcpSource_form" method="post" action="" >
<form:hidden path="kcpSource.id"/>
<table class="datatable" >
<tr>
	<th align="right" width="100" height="30">KCP来源：</th>
	<td >
	   <form:input path="kcpSource.name"/>
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