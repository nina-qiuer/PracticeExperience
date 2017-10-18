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
		var validate = $("#commissionSummaryForm").validate({
            submitHandler: function(form){    
            	addOrUpdate(parent.mainUrl);
            },
            
            rules:{
            	'entity.remark':{
            		maxlength:500
            	}

            },
            messages:{
            	'entity.remark':{ 
                    maxlength:'超过最大长度'
                }
            }
	});
		
	});
 	
</script>
    
</head>
<body>
<div id="main">
<form id="commissionSummaryForm" action="" method="post">
<s:hidden name="id" id="id"/>
<table width="100%" class="datatable mb10">
    <tr>
        <th>类别：</th>
        <td>
        	<s:select list="categorys" headerKey=""  headerValue="--"  name="entity.category"></s:select>
        </td>
    </tr>
    <tr>
        <th>备注：</th>
        <td>
            <s:textarea name="entity.remark" cols="47" rows="5"></s:textarea>
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