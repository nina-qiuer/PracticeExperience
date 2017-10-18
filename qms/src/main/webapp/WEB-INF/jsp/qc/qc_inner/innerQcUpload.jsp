<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>
<html>
<head>
<link rel="stylesheet" type="text/css" href="res/plugins/uploadify/uploadify.css"/>
<script type="text/javascript" src="res/plugins/uploadify/jquery.uploadify.min.js"></script>
<script type="text/javascript" src="res/js/upload.js"></script>
<script type="text/javascript" >
function  startUpload() {
	
	var filepath = $("#fileInput").val();
	var extStart = filepath.lastIndexOf(".");
	var ext = filepath.substring(extStart, filepath.length).toUpperCase();
	if ($("#a").is(':hidden') || $("#a").html() == '文件上传成功!') {
		return false;
	}

	var paramData = {
		'qcId' : $('#qcId').val(),
		'operType' : 1,
		'templateName' : "",
		'extType' :ext
	};
	$.ajaxFileUpload({
		url : 'qc/upload/innerfileUpload', //需要链接到服务器地址  
		secureuri : false,
		fileElementId : 'fileInput', //文件选择框的id属性  
		dataType : 'json', //服务器返回的格式，可以是json  
		data : paramData,
		success: function (result)            //相当于java中try语句块的用法  
          {       
            	if(result.retCode == "0")
				{
            		 parent.frames['attachMain'].location.reload();
		    		 parent.layer.closeAll();
				}else
				{
					layer.alert(result.retObj, {icon: 2});
				}
          },  
          error: function (result)            //相当于java中catch语句块的用法  
          {  
          	 layer.alert("上传文件失败，请刷新页面重新上传！", {icon: 2});
          }
	});
	$('#fileInput').remove();
	$("#addFileHrefId")
			.html(
					'添加附件<input type="file" id="fileInput" name="fileInput" class="fileInput" onchange="return fileOnChange();"/> ');
};
</script>
</head>
<body>
<div id="" style="padding: 10px 20px; overflow: auto;">
<form id="innerQcForm" name="innerQcForm" action="" method="post" enctype="multipart/form-data">
	<input type="hidden" name="qcId" id="qcId" value="${qcId}"/><br>
	<div id="qualityInfo" class="pici_search pd5">附件信息</div>
	<table class="tb" id="qualityDetail" style="width:100%; overflow: auto;">
		<tr>
			<td>
			<div style="padding: 5px;text-align: center;max-width:350px;">
				<a href="javascript:void(0);" id ="addFileHrefId" class="input uploadify-button">
					添加附件<input type="file" id="fileInput" name="fileInput" class="fileInput" onchange="return fileOnChange()">
				</a>
			</div>
			<div id="a" class="uploadify-queue-item" style="display:none;margin-bottom:10px;text-align: center"></div>
			</td>
			<td align="center">
				<input class="btn-new" id="savebtn" type="button" name="save" onclick="startUpload();" value="上传文件">
				<input class="btn-new" id="savebtn" type="button" name="save" onclick="cancelUpload()" value="取消导入">
			</td>
		</tr>
	</table>
	<div id="download_file"></div>
</form>
</div>
</body>
</html>