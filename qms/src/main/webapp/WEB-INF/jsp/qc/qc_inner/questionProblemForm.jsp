<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>
<html>
<head>
<link rel="stylesheet" type="text/css" href="res/plugins/KindEditor/css/default.css"/>
<script type="text/javascript" src="res/plugins/KindEditor/kindeditor-min.js"></script>
<script type="text/javascript" src="res/plugins/KindEditor/lang/zh_CN.js"></script>

<script type="text/javascript">
var qpArr = new Array();
$(document).ready(function(){
	qpTypeAutoComplete();
	isNotCheck();
	$("#question_form").validate({
		rules:{
	
			qptName:{
		    	   required:true,
		    	   qptNameExists:true
			      }
		   
        },
        messages:{
        	
        	qptName:{required:"请输入质量问题类型",qptNameExists:"质量问题类型不存在"}
        }
	   });
	jQuery.validator.addMethod("qptNameExists", function(value, element) {
		if($.trim(value)!=''){
		var isExists = false;
		for(var i=0;i<qpArr.length;i++){
			
			if(value == qpArr[i]){
				
				isExists =true;
			}
		}
		return isExists;
		
	}else{
		return true;
		
		}
	}, "");
});
var editor1, editor2;
KindEditor.ready(function(K) {
	editor1 = K.create('#description', {
		resizeType : 1,
		allowPreviewEmoticons : false,
		allowImageUpload : false,
		items : ['fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', 
				 'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', '|', 'hr', 'fullscreen']
	});
	editor2 = K.create('#impAdvice', {
		resizeType : 1,
		allowPreviewEmoticons : false,
		allowImageUpload : false,
		items : ['fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', 
				 'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', '|', 'hr', 'fullscreen']
	});
});

function isNotCheck(){
	
    var id = "${qualityProblem.lowSatisDegree}";
    var lowSatisDegree = document.getElementsByName("lowSatisDegree");
    for(var i = 0; i < lowSatisDegree.length;i++){
        if(id == lowSatisDegree[i].value){
        	lowSatisDegree[i].checked = true;
        }
    }
}
function qpTypeAutoComplete() {
	
	<c:forEach items="${qpTypeNames}" var="qpTypeName">
		qpArr.push('${qpTypeName}');
	</c:forEach>
    $("#qptName").autocomplete({
	    source: qpArr,
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
	var qpId = $('#id').val();
	editor1.sync();
	editor2.sync();
	var qptName =$('#qptName').val();
	if(editor1.text()==""){
		
		layer.alert("问题判定不能为空!", {icon: 5});
		return false;
	}
	if($('#question_form').valid()){
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
		    		$('#qptLv1Id').attr("value",qcType.qptLv1Id);
		    		if(qpId!="" && qpId!=null){
		    			
		    			$.ajax( {
		    				url : 'qc/qualityProblem/updateQp',
		    				data : 	$('#question_form').serialize(),
		    				type : 'post',
		    				dataType:'json',
		    				cache : false,
		    				success : function(result) {
		    					enableButton(input);
		    					if(result)
		    					{
		    				    	if(result.retCode == "0")
		    						{
		    				    		 parent.frames['qualityProblem'].location.reload();
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
		    				url : 'qc/qualityProblem/addNewQp',
		    				data : 	$('#question_form').serialize(),
		    				type : 'post',
		    				dataType:'json',
		    				cache : false,
		    				success : function(result) {
		    					enableButton(input);
		    					if(result)
		    					{
		    				    	if(result.retCode == "0")
		    						{
		    				    	
		    				    		 parent.frames['qualityProblem'].location.reload();
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
</script>
</head>
<body>
<form name="question_form" id="question_form" method="post" action="" >
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
<input type="hidden" name="qcFlag" id="qcFlag" value="${qcFlag}">
<form:hidden path="qualityProblem.qcId"/>
<form:hidden path="qualityProblem.id"/>
<table class="datatable" >
<tr>
<th align="right" width="100" height="30">问题类型：</th>
<td>
	<input id="qptName" name="qptName" style="width:350px" value="${qualityProblem.qptName}"   />
    <input id="qptId" type="hidden" name="qptId" value="${qualityProblem.qptId}">
    <input id="qptLv1Id" type="hidden" name="qptLv1Id" value="${qualityProblem.qptLv1Id}">
	<input id="layerId" type="hidden" name="layerId" value="">
	<a id="menuBtn" href="javascript:void(0)" onclick="openTopWinow('问题类型', 'qc/qualityProblem/getQpType?qcFlag=${qcFlag}',  600, 400)">选择</a>
</td>
</tr>
<tr>
		<th align="right" width="100" height="30">问题描述：</th>
		<td colspan="3">
			<textarea id="description" name="description" style="width:700px;height:200px;visibility:hidden;">${qualityProblem.description}</textarea>
		</td>
	</tr>
	<tr>
		<th align="right" width="100" height="30">问题判定：</th>
		<td colspan="3">
			<textarea id="impAdvice" name="impAdvice" style="width:700px;height:200px;visibility:hidden;">${ qualityProblem.impAdvice}</textarea>
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