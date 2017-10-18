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
        
      input[type="text"]:disabled, textarea:disabled,select:disabled{
      		color:black;
      }
        
        
    </style>
    
    <script type="text/javascript">
 	
 	function dealWorkOrder(){
 		if(validateData()){
	 		var url = "";
	 		url = "work_order-update";
	 		$('#submitButton').attr('disabled','true');
	 		 $.ajax({
				type:"post",
				url:url,
				data:{id:$('#id').val(),'entity.configId':$('#configId').val(),'entity.solveResult':$('#solveResult').val(),'entity.remark':$('#remark').val()},
				success:function(data){
					layer.alert('提交成功·',function(index){
						layer.close(index);
						$('#search_form',parent.document).submit();
					}); 
				}
			}); 
 		}
 	}
 	
 	function validateData(){
 		var solveResult = $('#solveResult').val();
 		if(solveResult.length>2000){
 			alert("请控制在2000字以内");
 			return false;
 		}
 		
 		var remark = $('#remark').val();
 		if(remark.length>3000){
 			alert("请控制在3000字以内");
 			return false;
 		}
 		return true;
 	}
 	

</script>
    
</head>
<body>
<div id="main">
<form id="workOrderForm" action="" method="post">
<s:hidden name="id" id="id"/>
<table width="100%" class="datatable mb10">
    <tr>
        <th>项目组：</th>
        <td>
           	  <s:select id="configId" name="entity.configId" list="firstConfig" listKey="id" listValue="department" disabled="true" headerKey="" headerValue="---"/>
        </td>
    </tr>
    <c:if test="${entity.parentConfigId != null && entity.parentConfigId > 0}">
		<tr>
			<th>二级项目组：</th>
			<td> <s:textfield name="entity.department" disabled="true"/></td>
		</tr>
	</c:if>
    <tr>
        <th>业务分类：</th>
        <td><s:textfield name="entity.businessClass" disabled="true"/></td>
    </tr>
    <tr>
        <th>订单号：</th>
        <td><s:textfield name="entity.orderId" disabled="true"/></td>
    </tr>
    <tr>
        <th>用户姓名：</th>
        <td><s:textfield name="entity.customerName" disabled="true"/></td>
    </tr>
    <tr>
        <th>联系电话：</th>
        <td><s:textfield name="entity.phone" disabled="true"/></td>
    </tr>
    <tr>
        <th>来电事由：</th>
        <td>
        	<s:textarea name="entity.phoneMatter" cols="47"  rows="5" disabled="true"></s:textarea>
        </td>
    </tr>
    <tr>
        <th>回复/解决时间:</th>
        <td><s:textfield name="entity.answerTime" disabled="true"/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" class="blue" value="跟进提醒" onclick="openDialog('跟进提醒','follow_time?woId=${id}','S')"> </td>
    </tr>
    <tr>
        <th>备注：</th>
        <td>
        	<s:textarea id="remark"  name="entity.remark" cols="47"  rows="5" disabled="true"></s:textarea>
        </td>
    </tr>
    <tr>
        <th>处理结果：</th>
        <td>
        	<s:textarea id="solveResult"  name="entity.solveResult" cols="47"  rows="5"></s:textarea>
        </td>
    </tr>
    <tr>
    	<td colspan="2" style="text-align: center">
    	</td>
    </tr>

    <tr>
        <td colspan="2" style="text-align: center">
        	<input id="submitButton" type="button"  class="blue" value="提交"  onclick="dealWorkOrder()"></input>
        </td>
    </tr>
</table>
</form>
</div>
</body>
</html>
