<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>
<html>
<head>
<link rel="stylesheet" type="text/css" href="res/plugins/KindEditor/css/default.css"/>
<script type="text/javascript" src="res/plugins/KindEditor/kindeditor-min.js"></script>
<script type="text/javascript" src="res/plugins/KindEditor/lang/zh_CN.js"></script>

<script type="text/javascript">
$(document).ready(function(){
	
	$("#qc_form").validate({
		rules:{
			influenceTime:{
				
				        required:true,
				        checkTime:true
		          },
		  //  influenceRange:{
		    	
		    	  //		required:true,
		          //      rangelength:[1,200]
		         //   },
		     qcAffairSummary:{
		        	  
		    	 rangelength:[1,200]
		       }
		       
        },
        messages:{
        	//influenceRange:{required:"请输入影响范围",rangelength:"字数不超过200"},
        	influenceTime:{required:"请输入影响时长",checkTime:"时间格式不正确"},
        	qcAffairSummary:{rangelength:"字数不超过200"}
        
        }
		
	});
	jQuery.validator.addMethod("checkTime", function(value, element) {
		
		if($.trim(value)!=''){
				var isTrue = false;
				var re = new RegExp("^[1-9]*[1-9][0-9]*$");
				if( re.test(value)){
					
					isTrue =true;
				}
				return isTrue;
		}else{
			
			return true;
		}
		}, "");
	
	$("#faultSource").val(${qcBill.faultSource});

});
var editor1;
KindEditor.ready(function(K) {

	editor1 = K.create('#qcAffairDesc', {
		resizeType : 1,
		allowPreviewEmoticons : false,
		allowImageUpload : false,
		items : ['fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', 
				 'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', '|', 'hr', 'fullscreen']
	});
	

});


function doSubmit(input) {
	editor1.sync();
    if($('#qc_form').valid()){
    	disableButton(input);
		$.ajax( {
			url : 'qc/resDevQcBill/updateDev',
			data : 	$('#qc_form').serialize(),
			type : 'post',
			dataType:'json',
			cache : false,
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

function setMaxDate(id){
	WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'});
}

function setMinDate(id){
	WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'});
}
</script>
</head>
<body>
<form name="qc_form" id="qc_form" method="post" action="" >
<form:hidden path="qcBill.id"/>
<table class="datatable" >
<tr>
<th align="right" style="width:120px" height="30">质量问题等级：</th>
	<td style="width:100px">
		<form:select path="qcBill.qualityEventClass" id="qualityEventClass">
					<form:option value="S" label="S级" />
					<form:option value="A" label="A级" />
					<form:option value="B" label="B级" />
					<form:option value="C" label="C级" />
					<form:option value="非研发故障" label="非研发故障" />
			</form:select>
	</td>
	<tr>
<th align="right" style="width:120px" height="30">故障来源：</th>
	<td style="width:100px">
	<form:select path="qcBill.faultSource" id="faultSource">
		<c:forEach items="${falutSourceList}" var="type">
			<option value="${type.key}">${type.content}</option>
		</c:forEach>
	</form:select>
	</td>
</tr>
<tr>
<th align="right" style="width:100px" height="30">影响时长(分钟)：</th>
 <td>
    <form:input path="qcBill.influenceTime"/>
 </td>
</tr>
<tr>
	<th align="right" style="width:100px" height="30">故障发生时间：</th>
	<td>
		<form:input path="qcBill.faultHappenTime" onfocus="setMaxDate('faultFinishTime')"/>
	</td>
</tr>
<tr>
	<th align="right" style="width:100px" height="30">故障完成时间：</th>
	<td>
		<form:input path="qcBill.faultFinishTime" onfocus="setMaxDate('faultFinishTime')"/>
	</td>
</tr>
<tr>
<th align="right" style="width:100px" height="30">影响结果：</th>
 <td>
    <form:input path="qcBill.influenceResult" style="width:700px"/>
 </td>
</tr>
<%-- <tr>
	<th align="right" style="width:100px" height="30">影响范围：</th>
   	<td >
   	     <form:textarea path="qcBill.influenceRange" style="width:700px;height:100px;"/>
	</td>
</tr> --%>
<tr>
		<th align="right" style="width:100px" height="30">质检事宜概述：</th>
		<td>
		     <form:textarea path="qcBill.qcAffairSummary" style="width:700px;height:50px;"/>
		</td>
	</tr>
	<tr>
		<th align="right" width="100" height="30">质检事宜详述：</th>
		<td>
		  <form:textarea path="qcBill.qcAffairDesc" style="width:700px;height:100px;"/>
		</td>
	</tr>
	<tr>
		<th align="right" width="100" height="30">备注：</th>
		<td colspan="3">
		  <form:textarea path="qcBill.remark" style="width:700px;height:50px;"/>
		</td>
	</tr>
	<tr>
		<th align="right" width="100" height="30">操作</th>
		<td colspan="3">
			<input type="button" class="blue" value="提交" name="submitBtn" id="submitBtn" onclick="doSubmit(this)">
		</td>
	</tr>
</table>
</form>
</body>
</html>