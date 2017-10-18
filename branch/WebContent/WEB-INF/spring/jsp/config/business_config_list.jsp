<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xml:lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<script src="http://code.jquery.com/jquery-latest.js"></script>

<script type="text/javascript">
	$(function(){
		buildCheckboxs();
	});
	
//构造集体投诉前置预警负责人配置
function buildCheckboxs(){
	var warningCheckboxs = ${checkboxs};
	var checkboxes = '';
	for(key in warningCheckboxs){
		checkboxes+='<input type="checkbox" name="warningIds" value="'+warningCheckboxs[key].id+'"';
		if(warningCheckboxs[key].checked){
			checkboxes+=' checked ';
		}
		checkboxes+='>'+warningCheckboxs[key].name+'</input>';
	}
	
	$('#content').append(checkboxes);
}

function save(){
	console.log($('#form').serialize());
	$.ajax({
		type:'post',
		url:'update',
		data:$('#form').serialize(),
		success:function(info){
			alert(info.msg);
		}
	});
}


</script>
</HEAD>
<BODY>
	<h1>业务配置</h1>
	<form id="form" action="" method="post">
		<div id="warnings">
			<h2>集体投诉前置预警负责人配置</h2>
			<div id="content">
				
			</div>
		</div>
		<input  type="button" value="保存" onclick="save()">
	</form>

</BODY>
</html>