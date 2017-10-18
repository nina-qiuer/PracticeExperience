<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>
<html>
<head>
<link rel="stylesheet" type="text/css" href="res/plugins/KindEditor/css/default.css"/>
<script type="text/javascript" src="res/plugins/KindEditor/kindeditor-min.js"></script>
<script type="text/javascript" src="res/plugins/KindEditor/lang/zh_CN.js"></script>

<script type="text/javascript">
var projectArr = new Array();
$(document).ready(function(){
	qpTypeAutoComplete();
	projectAutoComplete();
	$("#fault_form").validate({
		rules:{
			influenceSystem:{
				required:true,
				projectExists:true
			},
			qptName:{
				
				required:true
			}
        },
        messages:{
        	influenceSystem:{required:"请输入责任系统",projectExists:"责任系统不存在"},
        	qptName:{required:"请输入故障类型"}
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
var editor1, editor2,editor3;
KindEditor.ready(function(K) {
	editor1 = K.create('#causeAnalysis', {
		resizeType : 1,
		allowPreviewEmoticons : false,
		allowImageUpload : false,
		items : ['fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', 
				 'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', '|', 'hr', 'fullscreen']
	});
	editor2 = K.create('#treMeasures', {
		resizeType : 1,
		allowPreviewEmoticons : false,
		allowImageUpload : false,
		items : ['fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', 
				 'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', '|', 'hr', 'fullscreen']
	});
	editor3 = K.create('#impMeasures', {
		resizeType : 1,
		allowPreviewEmoticons : false,
		allowImageUpload : false,
		items : ['fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', 
				 'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', '|', 'hr', 'fullscreen']
	});
});


function qpTypeAutoComplete() {
	var qpArr = new Array();
	<c:forEach items="${qpTypeNames}" var="qpTypeName">
		qpArr.push('${qpTypeName}');
	</c:forEach>
	
    $("#qptName").autocomplete({
	    source: qpArr,
	    autoFocus : true
	});
}

function projectAutoComplete() {
   <c:forEach items="${projectList}" var="project">
	projectArr.push('${project.projectName}');
   </c:forEach>

	$("#influenceSystem").autocomplete({
	    source: projectArr,
	    autoFocus : true
	});
}
function openTopWinow(title, url, width, height) {
	layer.open({
        type: 2,
        shade : [0.5 , '#000' , true],
        maxmin: true,
        title: title,
        content: url,
        area: [width+'px', height+'px']
	   
    });
}

function doSubmit(input) {
	
	var onLine = document.getElementById('onLine').value;
	var jiraAddress = $('#jiraAddress').val();
	var jira  = $.trim(jiraAddress);
	if(onLine == 0){
		if(jira==''){
			layer.alert("JIRA地址或单号不能为空!", {icon: 5});
			return false;
		}
		
	}
	
	var qpId = $('#id').val();
	editor1.sync();
	editor2.sync();
	editor3.sync();
	var qptName =$('#qptName').val();
	if($('#fault_form').valid()){
	disableButton(input);
	$.ajax( {
		url : 'qc/qualityProblem/getRealType',
		data : 	{
			qptName:qptName
		},
		type : 'post',
		dataType:'json',
		cache : false,
		success : function(result) {
			if(result)
			{
		    	if(result.retCode == "0")
				{
		    		var qcType = eval(result.retObj);
		    		$('#qptId').attr("value",qcType.id);
		    		if(qpId!="" && qpId!=null){
		    			
		    			$.ajax( {
		    				url : 'qc/devFault/updateFault',
		    				data : 	$('#fault_form').serialize(),
		    				type : 'post',
		    				dataType:'json',
		    				cache : false,
		    				success : function(result) {
		    					enableButton(input);
		    					if(result)
		    					{
		    				    	if(result.retCode == "0")
		    						{
		    				    		 parent.frames['devFault'].location.reload();
		    				    		//parent.location.reload();
		    				    		 parent.layer.closeAll();
		    						 }else{
		    							
		    							layer.alert(result.resMsg, {icon: 2});
		    						}
		    				     }
		    				 }
		    			    });
		    			
		    		}else{
		    		   
		    			$.ajax( {
		    				url : 'qc/devFault/addNewFault',
		    				data : 	$('#fault_form').serialize(),
		    				type : 'post',
		    				dataType:'json',
		    				cache : false,
		    				success : function(result) {
		    					enableButton(input);
		    					if(result)
		    					{
		    				    	if(result.retCode == "0")
		    						{
		    				    	
		    				    		 parent.frames['devFault'].location.reload();
		    				    		 parent.layer.closeAll();
		    						 }else{
		    							
		    							layer.alert(result.resMsg, {icon: 2});
		    						}
		    				     }
		    				 }
		    			    });
		    			}
				 }else{
					
					layer.alert(result.resMsg, {icon: 2});
					enableButton(input);
				}
		     }
		 },error:function(result){
			 
			 enableButton(input);
		 }
	    });
	}
}
function checkOnline(){
	
    $("#jiraTd").toggle();
	
}
</script>
</head>
<body>
<form name="fault_form" id="fault_form" method="post" action="" >
<input type="hidden" name="qcFlag" id="qcFlag" value="${qcFlag}">
<form:hidden path="dev.useFlag"/>
<form:hidden path="dev.qcId"/>
<form:hidden path="dev.id"/>
<table class="datatable" >
<tr>
<th align="right" width="150" height="30">故障类型：</th>
<td>
	<input id="qptName" name="qptName" style="width:350px" value="${dev.qptName}"   />
    <input id="qptId" type="hidden" name="qptId" value="${dev.qptId}">
	<input id="layerId" type="hidden" name="layerId" value="">
	<a id="menuBtn" href="javascript:void(0)" onclick="openTopWinow('故障类型', 'qc/qualityProblem/getQpType?qcFlag=${qcFlag}',  600, 400)">选择</a>
</td>
</tr>
<tr>
  <th align="right" width="100" height="30">责任系统：</th>
	<td>
	<input id="influenceSystem" name="influenceSystem" style="width:350px" value="${dev.influenceSystem}">
	</td>
	</tr>
<tr>
<tr>
  <th align="right" width="100" height="30">是否为上线(含变更)引起：</th>
    <td style="width:100px">
        <select name="onLine" id="onLine" onchange="checkOnline()" style="width:100px">
			<option value="0" selected="selected" >是</option>
			<option value="1" >否</option>
		</select>
	</td>
</tr>
<tr id="jiraTd">
  <th align="right" width="100" height="30">JIRA地址或单号：</th>
	<td>
	  <input type="text" style="width:350px"  name="jiraAddress" id="jiraAddress" value="${dev.jiraAddress }">
	</td>
	</tr>
<tr>
		<th align="right" width="100" height="30">原因分析：</th>
		<td colspan="3">
			<textarea id="causeAnalysis" name="causeAnalysis" style="width:700px;height:200px;visibility:hidden;">${dev.causeAnalysis}</textarea>
		</td>
	</tr>
	<tr>
		<th align="right" width="100" height="30">处理措施：</th>
		<td colspan="3">
			<textarea id="treMeasures" name="treMeasures" style="width:700px;height:200px;visibility:hidden;">${ dev.treMeasures}</textarea>
		</td>
	</tr>
	<tr>
		<th align="right" width="100" height="30">改进措施：</th>
		<td colspan="3">
			<textarea id="impMeasures" name="impMeasures" style="width:700px;height:200px;visibility:hidden;">${ dev.impMeasures}</textarea>
		</td>
	</tr>
	<tr>
		<th align="right" width="100" height="30"></th>
		<td colspan="3">
			<input type="button" class="blue" value="提交" name="submitBtn" id="submitBtn" onclick="doSubmit(this)">
		</td>
	</tr>
</table>
</form>
</body>
</html>