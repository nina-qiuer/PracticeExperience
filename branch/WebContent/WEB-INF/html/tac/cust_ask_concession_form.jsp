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
		var validate = $("#custAskConcessionForm").validate({
            submitHandler: function(form){    
            	addOrUpdate(parent.mainUrl);
            },
            
            rules:{
            	'entity.orderId':{
            		maxlength:30
            	},
                'entity.customerDepartment':{
                    maxlength:20
                },
                'entity.customerGroup':{
                    maxlength:20
                },
            	'entity.price':{
            		maxlength:20
            	},
                'entity.concessionalLimit':{
                    maxlength:20
                },
                'entity.personNum':{
                    maxlength:5
                },
                'entity.concessionalBearer':{
                    maxlength:50
                },
                'entity.prdOfficerName':{
                    maxlength:20
                }

            },
            messages:{
            	'entity.orderId':{ 
                    maxlength:'超过最大长度'
                },
                'entity.customerDepartment':{
                    maxlength:'超过最大长度'
                },
                'entity.customerGroup':{
                    maxlength:'超过最大长度'
                },
                'entity.price':{
                    maxlength:'超过最大长度'
                },
                'entity.concessionalLimit':{
                    maxlength:'超过最大长度'
                },
                'entity.personNum':{
                    maxlength:'超过最大长度'
                },
                'entity.concessionalBearer':{
                    maxlength:'超过最大长度'
                },
                'entity.prdOfficerName':{
                    maxlength:'超过最大长度'
                }
            }
	});
		
	});
    
   
					function fillRalatedFields(input) {

						var orderId = Trim($(input).val(), 'g');
						if (orderId == '') {
							return;
						}
						$('.toFill').attr('disabled', true);
						$('#orderId').val(orderId);
						$.ajax({
							type : "post",
							url : 'custAskConcession-fillRelatedFields',
							data : {
								orderId : orderId
							},
							success : function(data) {
								console.log(data);
								$('#personNum').val(data.personNum);
								$('#prdOfficerName').val(data.prdOfficerName);

								$('.toFill').removeAttr('disabled');
							}
						});
					}
				</script>
    
</head>
<body>
<div id="main">
<form id="custAskConcessionForm" action="" method="post">
<s:hidden name="id" id="id"/>
<table width="100%" class="datatable mb10">
    <tr>
        <th>订单号：</th>
        <td><s:textfield  name="entity.orderId" id="orderId" onblur="fillRalatedFields(this)"/></td>
    </tr>
    <tr>
        <th>客服部：</th>
        <td><s:textfield  name="entity.customerDepartment" id="customerDepartment"/></td>
    </tr>
    <tr>
        <th>客服组：</th>
        <td><s:textfield  name="entity.customerGroup" id="customerGroup"/></td>
    </tr>
    <tr>
        <th>产品原价：</th>
        <td><s:textfield  name="entity.price"/></td>
    </tr>
    <tr>
        <th>让价额度：</th>
        <td><s:textfield name="entity.concessionalLimit"/></td>
    </tr>
    <tr>
        <th>订单人数：</th>
        <td><s:textfield  name="entity.personNum" id="personNum" cssClass="toFill"/></td>
    </tr>
    <tr>
        <th>附加赠送：</th>
        <td><s:textfield  name="entity.additionPresentation"/></td>
    </tr>
    <tr>
        <th>让价承担方：</th>
        <td><s:textfield name="entity.concessionalBearer"/></td>
    </tr>
     <tr>
        <th>产品姓名：</th>
        <td><s:textfield name="entity.prdOfficerName" id="prdOfficerName" cssClass="toFill"/></td>
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