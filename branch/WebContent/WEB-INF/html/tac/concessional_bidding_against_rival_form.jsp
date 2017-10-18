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
		var validate = $("#concessionalBiddingAgainstRivalForm").validate({
            submitHandler: function(form){    
            	addOrUpdate(parent.mainUrl);
            },
            
            rules:{
            	'entity.orderId':{
            		maxlength:20
            	},
            	'entity.rival':{
            		maxlength:50
            	},
            	'entity.rivalPrice':{
            		maxlength:20
            	},
                'entity.routeHref':{
                    maxlength:100
                },
                'entity.rivalPriceProof':{
                    maxlength:300
                },
                'entity.tuniuPrice':{
                    maxlength:20
                },
                'entity.concessionalLimit':{
                    maxlength:20
                },
                'entity.additionPresentation':{
                    maxlength:50
                },
                'entity.concessionalSide':{
                    maxlength:50
                },
                'entity.concessionalName':{
                    maxlength:50
                },
                'entity.unConcessionalReason':{
                    maxlength:100
                },
                'entity.customerDepartment':{
                    maxlength:20
                },
                'entity.customerGroup':{
                    maxlength:20
                },
                'entity.productDepartment':{
                    maxlength:20
                },
                'entity.productGroup':{
                    maxlength:20
                }

            },
            messages:{
            	'entity.orderId':{
            		maxlength:'超过最大长度'
            	},
            	'entity.rival':{
                    maxlength:'超过最大长度'
                },
                'entity.rivalPrice':{
                    maxlength:'超过最大长度'
                },
                'entity.routeHref':{
                    maxlength:'超过最大长度'
                },
                'entity.rivalPriceProof':{
                    maxlength:'超过最大长度'
                },
                'entity.tuniuPrice':{
                    maxlength:'超过最大长度'
                },
                'entity.concessionalLimit':{
                    maxlength:'超过最大长度'
                },
                'entity.additionPresentation':{
                    maxlength:'超过最大长度'
                },
                'entity.concessionalSide':{
                    maxlength:'超过最大长度'
                },
                'entity.concessionalName':{
                    maxlength:'超过最大长度'
                },
                'entity.unConcessionalReason':{
                    maxlength:'超过最大长度'
                },
                'entity.customerDepartment':{
                    maxlength:'超过最大长度'
                },
                'entity.customerGroup':{
                    maxlength:'超过最大长度'
                },
                'entity.productDepartment':{
                    maxlength:'超过最大长度'
                },
                'entity.productGroup':{
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
						console.log('orderId:' + orderId);
						$
								.ajax({
									type : "post",
									url : 'concessionalBiddingAgainstRival-fillRelatedFields',
									data : {
										orderId : orderId
									},
									success : function(data) {
										console.log(data);
										$('#productDepartment')
												.val(
														data.prdOfficerDepartment.departmentName);
										$('#productGroup')
												.val(
														data.prdOfficerDepartment.groupName);

										$('.toFill').removeAttr('disabled');
									}
								});
					}
				</script>
    
</head>
<body>
<div id="main">
<form id="concessionalBiddingAgainstRivalForm" action="" method="post">
<s:hidden name="id" id="id"/>
<table width="100%" class="datatable mb10">
	<tr>
        <th>订单号：</th>
        <td><s:textfield  name="entity.orderId" onblur="fillRalatedFields(this)"/></td>
    </tr>
    <tr>
        <th>竞争对手：</th>
        <td><s:textfield  name="entity.rival"/></td>
    </tr>
    <tr>
        <th>对手价格：</th>
        <td><s:textfield  name="entity.rivalPrice"/></td>
    </tr>
    <tr>
        <th>对手线路链接：</th>
        <td><s:textfield  name="entity.routeHref"/></td>
    </tr>
    <tr>
        <th>对手价格证据：</th>
        <td><s:textfield name="entity.rivalPriceProof"/></td>
    </tr>
     <tr>
        <th>我司价格：</th>
        <td><s:textfield name="entity.tuniuPrice"/></td>
    </tr>
    <tr>
        <th>让价额度：</th>
        <td><s:textfield name="entity.concessionalLimit"/></td>
    </tr>
    <tr>
        <th>附加赠送：</th>
        <td><s:textfield  name="entity.additionPresentation"/></td>
    </tr>
    <tr>
        <th>让价方：</th>
        <td><s:textfield  name="entity.concessionalSide"/></td>
    </tr> 
    <tr>
        <th>让价人名：</th>
        <td><s:textfield  name="entity.concessionalName"/></td>
    </tr>
    <tr>
        <th>产品不予让价原因：</th>
        <td><s:textfield  name="entity.unConcessionalReason"/></td>
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
        <th>产品部：</th>
        <td><s:textfield  name="entity.productDepartment" id="productDepartment" cssClass="toFill"/></td>
    </tr>
    <tr>
        <th>产品组：</th>
        <td><s:textfield  name="entity.productGroup" id="productGroup" cssClass="toFill"/></td>
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