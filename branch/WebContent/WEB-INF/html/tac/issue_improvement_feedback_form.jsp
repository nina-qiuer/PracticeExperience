<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript" src="${CONFIG.res_url}script/thickbox-compressed.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}script/jquery/validation/jquery.validate.min.js"></script>
    <style type="text/css">
    	
    	.datatable{
    		margin:0 auto;
    	}
    
    
        .datatable th{
            text-align: right;
            width: 20%;
        }

        .datatable td{
            text-align: left;
        }
        
        input[type=text]{
        	width:70%;
        }
        
        
        select {
        	width:71.5%;
        }
        
        
    </style>
    
    <script type="text/javascript">
    $(function(){
		var validate = $("#issueImprovementFeedbackForm").validate({
            submitHandler: function(form){    
            	addOrUpdate(parent.mainUrl);
            },
            
            rules:{
                'entity.customerDepartment':{
                    maxlength:20
                },
                'entity.customerScndDepartment':{
                    maxlength:20
                },
                'entity.customerGroup':{
                    maxlength:20
                },
            	'entity.orderId':{
            		maxlength:20
            	},
            	'entity.issueDescription':{
            		maxlength:1000
            	},
                'entity.advice':{
                    maxlength:1000
                }

            },
            messages:{
                'entity.customerDepartment':{
                    maxlength:'超过最大长度'
                },
                'entity.customerScndDepartment':{
                    maxlength:'超过最大长度'
                },
                'entity.customerGroup':{
                    maxlength:'超过最大长度'
                },
                'entity.orderId':{
                    maxlength:'超过最大长度'
                },
                'entity.issueDescription':{
                    maxlength:'超过最大长度'
                },
                'entity.advice':{
                    maxlength:'超过最大长度'
                }
            }
	});
		
	});
 	
</script>
    
</head>
<body>
<div id="main">
<form id="issueImprovementFeedbackForm" action="" method="post">
<s:hidden name="id" id="id"/>
<table width="100%" class="datatable mb10">
    <tr>
        <th>一级部门：</th>
        <td><s:textfield  name="entity.customerDepartment"/></td>
    </tr>
    <tr>
        <th>二级部门：</th>
        <td><s:textfield  name="entity.customerScndDepartment"/></td>
    </tr>
    <tr>
        <th>三级组：</th>
        <td><s:textfield  name="entity.customerGroup"/></td>
    </tr>
    <tr>
        <th>关联订单号：</th>
        <td><s:textfield  name="entity.orderId"/></td>
    </tr>
    <tr>
        <th>问题描述：</th>
        <td>
            <s:textarea name="entity.issueDescription" cols="47" rows="5"></s:textarea>
        </td>
    </tr>
    <tr>
        <th>改进建议：</th>
        <td>
            <s:textarea name="entity.advice" cols="47" rows="5"></s:textarea>
        </td>
    </tr>
    <tr>
    	<td colspan="2" style="text-align: center">
    	</td>
    </tr>

    <tr>
        <td colspan="2" style="text-align: center">
        	<input id="submitButton" type="submit"  class="blue" value="提交" ></input>
        </td>
    </tr>
</table>
</form>
</div>
</body>
</html>