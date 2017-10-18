<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>
<html>
<head>

<script type="text/javascript">
$(document).ready(function() {
	$("#kcpType_form").validate({
		
		rules:{
			name:{
                  required:true
			}
        },
        messages:{
        	
        	name:{required:"请输入KCP类型"}
          
        }
		
	});
	
});



  function doSubmit(input) {
	
  	var id = $('#id').val();
	if($('#kcpType_form').valid()){
		disableButton(input);
		    if(id!="" && id!=null){
		    	  		$.ajax( {
		    	  			url : 'qs/kcpType/updateKcp',
		    	  			data : 	$('#kcpType_form').serialize(),
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
		    	  			url : 'qs/kcpType/add',
		    	  			data : 	$('#kcpType_form').serialize(),
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
<form name="kcpType_form" id="kcpType_form" method="post" action="" >
<form:hidden path="kcpType.id"/>
<table class="datatable" >
<tr>
	<th align="right" width="100" height="30">KCP类型：</th>
	<td>
	     <form:input path="kcpType.name"/>
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