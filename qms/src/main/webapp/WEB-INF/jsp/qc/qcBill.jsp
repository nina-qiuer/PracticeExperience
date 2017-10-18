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
	if($("#userRole").val()==2 || $("#userRole").val() ==3){
		
		if( $("#cmpId").val() != 0 ){
			tabpanel = new TabPanel({
		        renderTo:'tabs', 
		        border:'none',  
		        active : 0,
		        items : [
		            {
		            	id:'qcReport',
		            	title:'质检报告',
		            	closable: false,
		            	html:"<iframe src='qc/qcBill/queryList/${qcBillId}' width='100%' height='100%' frameborder='0'></iframe>"
		            },
		            {
		            	id:'qcNote',
		            	title:'质检备忘('+'${qcNoteCount}'+')',
		            	closable: false,
		            	html:"<iframe src='qc/qcBill/listNote?qcBillId=${qcBillId}' width='100%' height='100%' frameborder='0'></iframe>"
		            },
		            {
		            	id:'sameGroup',
		            	title:'同团期投诉订单',
		            	closable: false,
		            	html:"<iframe src='qc/qcBill/listSameGroup?qcBillId=${qcBillId}' width='100%' height='100%' frameborder='0'></iframe>"
		            } ,
		            {
		            	id:'operLog',
		            	title:'业务操作日志',
		            	closable: false,
		            	html:"<iframe src='qc/qcBill/listOperLog?qcBillId=${qcBillId}' width='100%' height='100%' frameborder='0'></iframe>"
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
		            	html:"<iframe src='qc/qcBill/queryList/${qcBillId}' width='100%' height='100%' frameborder='0'></iframe>"
		            },
		            {
		            	id:'qcNote',
		            	title:'质检备忘('+'${qcNoteCount}'+')',
		            	closable: false,
		            	html:"<iframe src='qc/qcBill/listNote?qcBillId=${qcBillId}' width='100%' height='100%' frameborder='0'></iframe>"
		            },
		            {
		            	id:'operLog',
		            	title:'业务操作日志',
		            	closable: false,
		            	html:"<iframe src='qc/qcBill/listOperLog?qcBillId=${qcBillId}' width='100%' height='100%' frameborder='0'></iframe>"
		            } 
		        ]
		    });
		}
		
	}else{
		if( $("#cmpId").val() != 0 ){
			tabpanel = new TabPanel({
		        renderTo:'tabs', 
		        border:'none',  
		        active : 0,
		        items : [
		            {
		            	id:'qcReport',
		            	title:'质检报告',
		            	closable: false,
		            	html:"<iframe src='qc/qcBill/queryList/${qcBillId}' width='100%' height='100%' frameborder='0'></iframe>"
		            },
		            {
		            	id:'qcNote',
		            	title:'质检备忘('+'${qcNoteCount}'+')',
		            	closable: false,
		            	html:"<iframe src='qc/qcBill/listNote?qcBillId=${qcBillId}' width='100%' height='100%' frameborder='0'></iframe>"
		            },
		            {
		            	id:'sameGroup',
		            	title:'同团期投诉订单',
		            	closable: false,
		            	html:"<iframe src='qc/qcBill/listSameGroup?qcBillId=${qcBillId}' width='100%' height='100%' frameborder='0'></iframe>"
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
		            	html:"<iframe src='qc/qcBill/queryList/${qcBillId}' width='100%' height='100%' frameborder='0'></iframe>"
		            },
		            {
		            	id:'qcNote',
		            	title:'质检备忘('+'${qcNoteCount}'+')',
		            	closable: false,
		            	html:"<iframe src='qc/qcBill/listNote?qcBillId=${qcBillId}' width='100%' height='100%' frameborder='0'></iframe>"
		            }
		        ]
		    });
		}
	}

});

function on_list(){
	
	var qcBillId = $('#qcBillId').val();
	$.ajax( {
		url : 'qc/qcBill/queryList/'+qcBillId,
		type : 'post',
		dataType:'json',
		cache : false,
		success : function(result) {
			if(result)
			{
		    	if(result.retObj == "success")
				{
		    		window.location.reload();
		    		
				 }else{
					
				}
		     }
		 }
	    });
}
</script>
</head>
<body style="margin: 0px;">
<form name="form" id="searchForm" method="post" action="" >
<input type="hidden" name="qcBillId" id="qcBillId" value="${qcBillId}">
<input type="hidden" name="userRole" id="userRole" value="${userRole}">
<input type="hidden" name="cmpId" id="cmpId" value="${qcBill.cmpId}">
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
<div id="tabs"></div>

</form>
</body>
</html>
