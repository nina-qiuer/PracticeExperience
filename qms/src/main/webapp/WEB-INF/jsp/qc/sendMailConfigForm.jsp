<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title></title>
<script type="text/javascript">
$(document).ready(function(){
	$("#mcForm").validate({
		rules:{
			
			respType:{
		      	       required:true
		            },
		       sendAddrs:{
			      	    required:true
			        }
        },
        messages:{
        	
        	respType:{required:"类型不能为空"},
        	sendAddrs:{required:"发送人不能为空"}
        }
	   });
});

function addOrUpdate() {
	var url = "qc/sendMailConfig/add";
	if ("${mc.id}" > 0) {
		url = "qc/sendMailConfig/update";
	}
	var mailType  = $('#mailType').val();
	var sendAddrs  = $('#sendAddrs').val();
	if($.trim(mailType)==''){
		
		layer.alert("配置类型不能为空", {icon: 2});
		return false;
	}
	var  sendAddrsT = sendAddrs.split(";");//去除最后一个分号
	var  compar = new RegExp("^(\\w)+([-+.]\\w+)*@tuniu\.com$");//判断tuniu.com邮箱正则表达式
	//对收件人邮箱进去判断
		for(var i=0;i<sendAddrsT.length;i++){
		if(!compar.test(sendAddrsT[i])){
			layer.alert("发送人中"+sendAddrsT[i]+"不符合要求!", {icon: 2});
			return false;
			break;
		}
	}
	
	$.ajax({
		url: url,
		data: $("#mcForm").serialize(),
		dataType: "json",
		type : 'post',
	    cache : false,
		success : function(result) {
				if(result)
				{
			    	if(result.retCode == "0")
					{
			    		
			    		parent.search();
			    		parent.layer.closeAll();
					 }else{
						
						layer.alert(result.resMsg, {icon: 2});
					}
			     }
			 }
	});
}

</script>
</head>
<body>
<form name="mcForm" id="mcForm" method="post" action="" >
<form:hidden path="mc.id"/>
<table class="datatable">
  	<tr>
  		<th>配置类型：</th>
  		<c:if test="${mc.id>0}">
  		<td><form:input  path="mc.mailType" readonly="true"/></td>
  		</c:if>
  		<c:if test="${mc.id==0}">
  		<td><form:input path="mc.mailType"/></td>
  		</c:if>
  	</tr>
  	<tr>
  		<th>发送人：</th>
  		<td>
  		<p style="color:red">多个邮件用“;”进行分隔</p>
  		<form:textarea path="mc.sendAddrs" cols="40" rows="3"/></td>
  	</tr>
  	<tr>
  		<th>操作：</th>
  		<td><input type="button" class="blue" value="提交" onclick="addOrUpdate()">
  		</td>
  	</tr>
</table>
</form>
</body>
</html>
