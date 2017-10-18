<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
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
		var validate = $("#newEmployeesSalaryAdjustmentForm").validate({
            submitHandler: function(form){    
            	addOrUpdate(parent.mainUrl);
            },
            
            rules:{
            	'entity.customer':{
            		maxlength:20
            	},
                'entity.workNum':{
                    maxlength:6
                },
                'entity.businessUnitName':{
                    maxlength:20
                },
                'entity.departmentName':{
                    maxlength:20
                },
                'entity.groupName':{
                    maxlength:20
                }

            },
            messages:{
            	'entity.customer':{ 
                    maxlength:'超过最大长度'
                },
                'entity.workNum':{ 
                    maxlength:'超过最大长度'
                },
                'entity.businessUnitName':{ 
                    maxlength:'超过最大长度'
                },
                'entity.departmentName':{ 
                    maxlength:'超过最大长度'
                },
                'entity.groupName':{ 
                    maxlength:'超过最大长度'
                }
            }
	});
		
	});
    
    function fillUserRalatedFields(input){
    	$('.toFill').attr('disabled',true);
    	var realName = Trim($(input).val(),'g');
    	$('#customer').val(realName);
    	console.log('realName:'+realName);
    	if(realName!=''){
    		$.ajax({
        		type:"post",
        		url:'newEmployeesSalaryAdjustment-getUserDepartmentVo',
        		data:{realName:realName},
        		success:function(data){
        			 console.log(data);
        			 if(data.success){
        				 $('#workNum').val(data.workNum);
        				 $('#businessUnitName').val(data.businessUnitName);
        				 $('#departmentName').val(data.departmentName);
        				 $('#groupName').val(data.groupName);
        			 }
        			 $('.toFill').removeAttr('disabled');
        		}
        	});
    	}
    }
 	
</script>
    
</head>
<body>
<div id="main">
<form id="newEmployeesSalaryAdjustmentForm" action="" method="post">
<s:hidden name="id" id="id"/>
<table width="100%" class="datatable mb10">
    <tr>
        <th>类别：</th>
        <td>
            <s:select list="{'新员工','新区域'}" name="entity.category"></s:select>
        </td>
    </tr>
    <tr>
        <th>客服人员：</th>
        <td><s:textfield  id="customer" name="entity.customer" onblur="fillUserRalatedFields(this)"/></td>
    </tr>

    <tr>
        <th>工号：</th>
        <td><s:textfield  id="workNum" cssClass="toFill" name="entity.workNum"/></td>
    </tr>
    <tr>
        <th>一级部门：</th>
        <td><s:textfield  id="businessUnitName" cssClass="toFill" name="entity.businessUnitName"/></td>
    </tr>
    <tr>
        <th>二级部门：</th>
        <td><s:textfield  id="departmentName" cssClass="toFill" name="entity.departmentName"/></td>
    </tr>
    <tr>
        <th>三级部门：</th>
        <td><s:textfield id="groupName"  cssClass="toFill" name="entity.groupName"/></td>
    </tr>
    <tr>
        <th>是否为上月漏报员工：</th>
        <td>
        	<s:radio list="#{1:'是',0:'否'}" name="entity.isFilToRprtLstMnth"></s:radio>
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