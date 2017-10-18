<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>

<html>
<head>
<script type="text/javascript">
var projectArr = new Array();
$(document).ready(function(){
	projectAutoComplete();
	
	$("#system_form").validate({
		rules:{
			influenceSystem:{
                  required:true,
                  projectExists:true
			}
        },
        messages:{
        	
        	influenceSystem:{required:"请输入影响系统",projectExists:"影响系统不存在"},
        }
	   });
	jQuery.validator.addMethod("projectExists", function(value, element) {
		if($.trim(value)!=''){
			var isExists = false;
			for(var i=0;i<projectArr.length;i++){
				
				if(value == projectArr[i]){
					
					isExists =true;
					
				}
			}
			
			return isExists;
			
		}else{
			
			return true;
		}
		}, "");

});
	
function projectAutoComplete() {
	   <c:forEach items="${projectList}" var="project">
		projectArr.push('${project.projectName}');
	   </c:forEach>

		$("#influenceSystem").autocomplete({
		    source: projectArr,
		    autoFocus : true
		});
	}


function doSubmit(input) {
	
	if($('#system_form').valid()){
		disableButton(input);
		$.ajax({
			type: "post",
			url: "qc/resDevQcBill/addSystem",
			data: {
				qcId : $('#qcId').val(),
				influenceSystem : $('#influenceSystem').val()
			},
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
<form name="system_form" id="system_form" method="post" action="" >
<input type="hidden" name="qcId" id="qcId" value="${qcId}">
<table class="datatable">
	<tr>
		<th align="right" width="85"><span style="color: red">*</span>影响系统：</th>
		<td><input type="text" id="influenceSystem" name="influenceSystem" style="width:200px"/>
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
