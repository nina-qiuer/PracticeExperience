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
		var validate = $("#prdProcurementIssuesFeedbackForm").validate({
            submitHandler: function(form){    
            	addOrUpdate(parent.mainUrl);
            },
            
            rules:{
            	'entity.department':{
            		maxlength:50
            	},
            	'entity.area':{
            		maxlength:50
            	},
                'entity.destType':{
                    maxlength:50
                },
                'entity.orderId':{
                    maxlength:30
                },
                'entity.routeId':{
                    maxlength:30
                },
                'entity.issueType':{
                    maxlength:50
                },
                'entity.prdOfficer':{
                    maxlength:50
                },
                'entity.prdManager':{
                    maxlength:50
                },
                'entity.prdMajordomo':{
                    maxlength:50
                },
                'entity.supplier':{
                    maxlength:50
                },
                'entity.description':{
                    maxlength:1000
                }

            },
            messages:{
            	'entity.department':{
                    maxlength:'超过最大长度'
                },
                'entity.area':{
                    maxlength:'超过最大长度'
                },
                'entity.destType':{
                    maxlength:'超过最大长度'
                },
                'entity.orderId':{
                    maxlength:'超过最大长度'
                },
                'entity.routeId':{
                    maxlength:'超过最大长度'
                },
                'entity.issueType':{
                    maxlength:'超过最大长度'
                },
                'entity.prdOfficer':{
                    maxlength:'超过最大长度'
                },
                'entity.prdManager':{
                    maxlength:'超过最大长度'
                },
                'entity.prdMajordomo':{
                    maxlength:'超过最大长度'
                },
                'entity.supplier':{
                    maxlength:'超过最大长度'
                },
                'entity.description':{
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
									url : 'prdProcurementIssuesFeedback-fillRelatedFields',
									data : {
										orderId : orderId
									},
									success : function(data) {
										console.log(data);
										$('#departDate').val(data.departDate);
										$('#supplier').val(data.supplier);

										$('#routeId').val(data.routeId);
										$('#prdOfficer').val(data.prdOfficer);
										$('#prdManager').val(data.prdManager);
										$('#prdMajordomo').val(
												data.prdMajordomo);

										$('.toFill').removeAttr('disabled');
									}
								});
					}
				</script>
    
</head>
<body>
<div id="main">
<form id="prdProcurementIssuesFeedbackForm" action="" method="post">
<s:hidden name="id" id="id"/>
<table width="100%" class="datatable mb10">
    <tr>
        <th>组别：</th>
        <td><s:textfield  name="entity.department" id="department"/></td>
    </tr>
    <tr>
        <th>区域：</th>
        <td><s:textfield  name="entity.area"/></td>
    </tr>
    <tr>
        <th>目的地类型：</th>
        <td><s:textfield  name="entity.destType"/></td>
    </tr>
    <tr>
        <th>订单号：</th>
        <td><s:textfield name="entity.orderId" id="orderId" onblur="fillRalatedFields(this)"/></td>
    </tr>
     <tr>
        <th>线路编号：</th>
        <td><s:textfield name="entity.routeId" id="routeId" cssClass="toFill"/></td>
    </tr>
    <tr>
        <th>出发日期：</th>
        <td>
	        <s:textfield name="entity.departDate" id="departDate" cssClass="Wdate toFill" onfocus="WdatePicker({dateFmt:'yyyy年MM月dd日',isShowClear:false,readOnly:true})"> 
	      		<s:param name="value"><s:date name="entity.departDate" format="yyyy年MM月dd日"/></s:param> 
			</s:textfield>
        </td>
    </tr>
    <tr>
        <th>问题类型：</th>
        <td>
        	<s:select list="issueTypes" headerKey="" headerValue="--"  name="entity.issueType"></s:select>
        </td>
    </tr>
    <tr>
        <th>产品专员：</th>
        <td><s:textfield  name="entity.prdOfficer" id="prdOfficer" cssClass="toFill"/></td>
    </tr> 
    <tr>
        <th>产品经理：</th>
        <td><s:textfield  name="entity.prdManager" id="prdManager" cssClass="toFill"/></td>
    </tr>
    <tr>
        <th>产品总监：</th>
        <td><s:textfield  name="entity.prdMajordomo" id="prdMajordomo" cssClass="toFill"/></td>
    </tr>
    <tr>
        <th>供应商：</th>
        <td><s:textfield  name="entity.supplier" id="supplier" cssClass="toFill"/></td>
    </tr>
    <tr>
        <th>问题描述：</th>
        <td>
            <s:textarea name="entity.description" cols="47" rows="5"></s:textarea>
        </td>
    </tr>
    <tr>
        <th>是否解决：</th>
        <td>
            <s:radio list="#{1:'是',0:'否'}" name="entity.resolveState"></s:radio>

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