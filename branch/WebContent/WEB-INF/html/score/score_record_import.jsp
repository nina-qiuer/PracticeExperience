<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<script type="text/javascript" src="${CONFIG.res_url}/script/jquery/plugin/jquery.form.js"></script>
<script type="text/javascript">
<!--
$(document).ready(function(){
	var options = {
	    beforeSubmit:  checkSubmit,  // pre-submit callback 
	    success:       success_function}; 
    $('#form').ajaxForm(options);
});

function checkSubmit() {
	
}

function success_function(data) {
	var succFlag = data.replace("<pre>","").replace("</pre>","");
	if (1 == succFlag) {
		self.parent.closeWindow(0);
	} else {
		alert("导入失败！");
	}
}

//-->
</script>
</HEAD>
<BODY>
<form name="form" id="form" method="post" action="score_record-doImport" enctype="multipart/form-data">
<table width="100%" class="datatable">
	<tr>
		<th align="right" width="80" height="30">模板下载：</th>
		<td>
			<a href="${CONFIG.res_url}images/icon/default/score_record_template.xlsx">score_record_template.xlsx</a>
		</td>
	</tr>
	<tr>
		<th align="right" width="80" height="30">选择文件：</th>
		<td>
			<input type="file" name="excelFile">　
			<input type="submit" value="导入" class="blue">
		</td>
	</tr>
</table>
</form>
</BODY>
