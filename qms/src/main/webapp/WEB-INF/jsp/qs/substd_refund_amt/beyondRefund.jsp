<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>订单超额退款监控</title>
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
            	id:'beyondRefundList',
            	title:'列表',
            	closable: false,
            	html:"<iframe src='qs/substdRefundAmt/beyondRefundList' width='100%' height='100%' frameborder='0'></iframe>"
            },
            {
            	id:'beyondRefundDepGraph',
            	title:'图表-组织架构维度',
            	closable: false,
            	html:"<iframe src='qs/substdRefundAmt/beyondRefundDepGraph' width='100%' height='100%' frameborder='0'></iframe>"
            },
            {
            	id:'beyondRefundDateGraph',
            	title:'图表-添加日期维度',
            	closable: false,
            	html:"<iframe src='qs/substdRefundAmt/beyondRefundDateGraph' width='100%' height='100%' frameborder='0'></iframe>"
            }
        ]
    });
});
</script>
</head>
<body style="margin: 0px;">
<div id="tabs"></div>
</body>
</html>
