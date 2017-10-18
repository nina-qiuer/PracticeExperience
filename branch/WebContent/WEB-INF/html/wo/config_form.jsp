<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript" src="${CONFIG.res_url}script/thickbox-compressed.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}script/jquery/validation/jquery.validate.min.js"></script>
<link href="${CONFIG.res_url}css/thickbox.css" rel="stylesheet" type="text/css" />
    <style type="text/css">
        .datatable th{
            text-align: right;
            width: 20%;
        }

        .datatable td{
            text-align: left;
        }
    </style>
    
 <script type="text/javascript">
 
 $(function(){
		$("#addOrUpdateConfig").validate({
         submitHandler: function(form){    
        	 addOrUpdateConfig();
         },
         
         rules:{
         	'config.department':{
         		required:true,
         		maxlength:11
         	},
         	'config.principals':{
         		required:true,
         		maxlength:300
         	},
         	'config.assigners':{
         		required:true,
         		maxlength:300
         	},
         	'config.members':{
         		maxlength:1000
         	}
         },
         messages:{
         	'config.department':{
                 required:"必填",
                 maxlength:'输入过长'
             },
             'config.principals':{
                 required:"必填",
                 maxlength:'输入过长'
             },
             'config.assigners':{
                 required:"必填",
                 maxlength:'输入过长'
             },
             'config.members':{
            	 maxlength:'输入过长'
          	}
         }
	});
	});
 	
 	function addOrUpdateConfig(){
 		if(validateData()){
 			var url = "";
 	 		url = "config-addOrUpdate";
 	 		$.ajax({
 				type:"post",
 				url:url,
 				data:$('#addOrUpdateConfig').serialize(),
 				success:function(data){
 					alert(data.msg);
 					if(data.success){
 						self.parent.easyDialog.close();
 						parent.location.replace(parent.location.href);
 					}
 				}
 			});
 		}
 	}
 	
 	function validateData(){
 		var department = $('#config\\.department').val();
 		var principals = $('#config\\.principals').val();
 		var assigners = $('#config\\.assigners').val();
 		var members = $('#config\\.members').val();
 		if(principals.indexOf("，")!=-1){
 			alert("负责人配置有误，只能用英文逗号分割");
 			return false;
 		}
 		
 		if(principals[principals.length-1]==','){
 			alert("不能以逗号结尾");
 			return false;
 		}
 		
 		if(assigners.indexOf("，")!=-1){
 			alert("分配人配置有误，只能用英文逗号分割");
 			return false;
 		}
 		
 		if(assigners[assigners.length-1]==','){
 			alert("不能以逗号结尾");
 			return false;
 		}
 		
 		if(members&&members.indexOf("，")!=-1){
 			alert("组员配置有误，只能用英文逗号分割");
 			return false;
 		}
 		
 		if(members[members.length-1]==','){
 			alert("不能以逗号结尾");
 			return false;
 		}
 		
 		$('#config\\.principals').val(Trim(principals,'g'));
 		$('#config\\.assigners').val(Trim(assigners,'g'));
 		$('#config\\.department').val(Trim(department,'g'));
 		if(members!=''){
 			$('#config\\.members').val(Trim(members,'g'));
 		}
 		
 		return true;
 	}
 	
 	function checkParentName(){
 		 $(".errorMsg").html("");
 	     var parentName =$('#config\\.parentName').val();	
 	     
 	     if($.trim(parentName)==''){
 	    	 return ;
 	     }
 	     
	  	 $.ajax( {
			url : 'config-checkDepartmentNameExist',
			data : 
			{
			    "departmentName" : parentName
			},
			type : 'post',
			dataType:'json',
			success : function(result) {
				if(!result.success){
					$(".errorMsg").html("父项目组不存在，请重新输入");
			     }
			 }
		  });
 	}

</script>
</head>
<body>
<div>
<form id="addOrUpdateConfig" action="" method="post">
	<s:hidden id="id" name="config.id"/>
	<span><font color="red">*项目负责人配置加说明：“如配置多个人，请使用英文逗号间隔；如：张三,李四”
并加“组员”配置，支持配置多个人。同一个人可以配置在负责人和组员中，也可以配置在不同的项目组中。没有父项目组请不要填写</font>
	</span>
    <table width="100%" class="datatable mb10">
        <tr>
            <th>项目组：</th>
            <td>
                <s:textfield id="config.department" name="config.department" cssStyle="width:80%"/>
            </td>
        </tr>
        <tr>
            <th>父项目组：</th>
            <td>
                <s:textfield id="config.parentName" name="config.parentName" cssStyle="width:80%" onblur="checkParentName()"/>
                <span class="errorMsg"></span>
            </td>
        </tr>
        <tr>
            <th>负责人：</th>
            <td>
           		 <s:textfield id="config.principals" name = "config.principals" cssStyle="width:80%"/>
			</td>
        </tr>
        <tr>
            <th>分配人：</th>
            <td>
           		 <s:textfield id="config.assigners" name = "config.assigners" cssStyle="width:80%"/>
			</td>
        </tr>
        <tr>
            <th>处理人：</th>
            <td>
           		 <s:textfield id="config.members" name = "config.members" cssStyle="width:80%"/>
			</td>
        </tr>
        <tr>
            <td colspan="2" style="text-align: center"><input type="submit" value="提交"/></td>
        </tr>
    </table>
</form>
</div>

</body>
</html>