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
	
	tabpanel = new TabPanel({
        renderTo:'tabs', 
        border:'none',  
        active : 0,
        items : [
            {
            	id:'qcReport',
            	title:'质检报告',
            	closable: false,
            	html:"<iframe src='qc/operateQcBill/queryList/${qcBillId}' width='100%' height='100%' frameborder='0'></iframe>"
            },
            {
            	id:'qcNote',
            	title:'质检备忘('+'${qcNoteCount}'+')',
            	closable: false,
            	html:"<iframe src='qc/operateQcBill/listNote?qcBillId=${qcBillId}' width='100%' height='100%' frameborder='0'></iframe>"
            }
          
        ]
    });
});

</script>
</head>
<body style="margin: 0px;">
<form name="form" id="searchForm" method="post" action="" >
<input type="hidden" name="qcBillId" id="qcBillId" value="${qcBillId}">
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
<div id="tabs"></div>

</form>
</body>
</html>
