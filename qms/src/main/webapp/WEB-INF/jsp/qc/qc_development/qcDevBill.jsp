<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>质检单[${qcBillId}]</title>
<script type="text/javascript">

$(document).ready(function(){
	
	$(".accordion").each(function() {
		$(this).accordion({
	    	collapsible: true,
	    	heightStyle: "content"
	    });
	});
	if('${loginUser_WCS}'.indexOf('DEV_REPORT') !=-1){
		tabpanel = new TabPanel({
	        renderTo:'tabs', 
	        border:'none',  
	        active : 0,
	        items : [
	            {
	            	id:'qcReport',
	            	title:'质检报告',
	            	closable: false,
	            	html:"<iframe src='qc/resDevQcBill/devReport?qcBillId=${qcBillId}' width='100%' height='100%' frameborder='0'></iframe>"
	            },
	            {
	            	id:'qcNote',
	            	title:'跟进记录('+'${qcNoteCount}'+')',
	            	closable: false,
	            	html:"<iframe src='qc/resDevQcBill/listNote?qcBillId=${qcBillId}' width='100%' height='100%' frameborder='0'></iframe>"
	            }
	        ]
	    });
		
	}else{
		
		tabpanel = new TabPanel({
	        renderTo:'tabs', 
	        border:'none',  
	        active : 0,
	        items : [
	            {
	            	id:'qcReport',
	            	title:'质检报告',
	            	closable: false,
	            	html:"<iframe src='qc/resDevQcBill/devReport?qcBillId=${qcBillId}' width='100%' height='100%' frameborder='0'></iframe>"
	            }
	        ]
	    });
		
	}
	
});

</script>
</head>
<body style="margin: 0px;">
<form name="form" id="searchForm" method="post" action="" >
<input type="hidden" name="qcBillId" id="qcBillId" value="${qcBillId}">
<div id="tabs"></div>

</form>
</body>
</html>
