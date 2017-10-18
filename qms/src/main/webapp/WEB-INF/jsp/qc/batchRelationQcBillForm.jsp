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
		  /*   influenceRange:{
		    	
		    	  		required:true,
		                rangelength:[1,200]
		            },	  */  
		     influenceResult:{
		    	
		    	  		required:true
		            },
		     qcAffairSummary:{
		        	  
		    	 rangelength:[1,200]
		       }
		       
        },
        messages:{
        //	influenceRange:{required:"请输入影响范围",rangelength:"字数不超过200"},
        	influenceResult:{required:"请输入影响结果"},
        	influenceTime:{required:"请输入影响时长",checkTime:"时间格式不正确"},
        	qcAffairSummary:{rangelength:"字数不超过200"}
        
        }
		
	});
	jQuery.validator.addMethod("checkTime", function(value, element) {
		
		if($.trim(value)!=''){
				var isTrue = false;
				var re = new RegExp("^[0-9]*[1-9][0-9]*$");
				if( re.test(value)){
					
					isTrue =true;
				}
				return isTrue;
		}else{
			
			return true;
		}
		}, "");

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
			url : 'qc/resDevQcBill/addDevRelation',
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

function setMaxDate(id){
	WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss', maxDate:'#F{$dp.$D(\''+id+'\')}'});
}

function setMinDate(id){
	WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss', minDate:'#F{$dp.$D(\''+id+'\')}'});
}
</script>
</head>
<body>
<form name="qc_form" id="qc_form" method="post" action="" >
<input type="hidden" name="relationIds"  id="relationIds" value="${relationIds}">
<table class="datatable" >
<tr>
  <th align="right" >投诉单号</th>
  <td>${cmpIds}</td>
</tr>
<tr>
    <th align="right" style="width:120px" height="30">质量问题等级：</th>
	<td style="width:100px"><select name="qualityEventClass">
			<option value="S" >S级</option>
			<option value="A" >A级</option>
			<option value="B" >B级</option>
			<option value="C" selected="selected" >C级</option>
		</select>
	</td>

</tr>
<tr>
<th align="right" style="width:120px" height="30">故障来源：</th>
	<td style="width:100px">
		<select name="faultSource">
			<c:forEach items="${falutSourceList}" var="type">
				<option value="${type.key}">${type.content}</option>
			</c:forEach>
		</select>
	</td>
</tr>
<tr>
<th align="right" style="width:100px" height="30">影响时长(分钟)：</th>
 <td>
   <input type="text" name="influenceTime" id="influenceTime" value="" />
 </td>
</tr>
<tr>
	<th align="right" style="width:100px" height="30">故障发生时间：</th>
	<td>
		<input id="faultHappenTime" name="faultHappenTime" type="text" onfocus="setMaxDate('faultFinishTime')"/>
	</td>
</tr>
<tr>
	<th align="right" style="width:100px" height="30">故障完成时间：</th>
	<td>
		<input id="faultFinishTime" name="faultFinishTime" type="text"  onfocus="setMinDate('faultHappenTime')"/>
	</td>
</tr>
<!-- <tr>
	<th align="right" style="width:100px" height="30">影响范围：</th>
   	<td colspan="3">
			<textarea id="influenceRange" name="influenceRange" style="width:700px;height:100px;"></textarea>
	</td>
</tr> -->
<tr>
<th align="right" style="width:100px" height="30">影响结果：</th>
 <td>
   <input type="text" name="influenceResult" id="influenceResult" value="" />
 </td>
</tr>
<tr>
		<th align="right" style="width:100px" height="30">故障概述：</th>
		<td colspan="3">
			<textarea id="qcAffairSummary" name="qcAffairSummary" style="width:700px;height:50px;"></textarea>
		</td>
	</tr>
	<tr>
		<th align="right" width="100" height="30">故障详述：</th>
		<td colspan="3">
			<textarea id="qcAffairDesc" name="qcAffairDesc" style="width:700px;height:100px;visibility:hidden;"></textarea>
		</td>
	</tr>
	<tr>
		<th align="right" width="100" height="30">备注：</th>
		<td colspan="3">
			<textarea id="remark" name="remark" style="width:700px;height:50px;"></textarea>
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