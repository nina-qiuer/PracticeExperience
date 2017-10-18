<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>

<html>
<head>
<style type="text/css">
		.listtable { border:1px solid #C1D3EB;border-collapse:collapse; font-size:12px;width: 100%;}
		.listtable th { padding:1px;text-align:center;border:1px solid #C1D3EB;color:#3E649D;background:#DFEAFB;font-weight:normal;}
		.listtable td {	border: 1px solid #C1D3EB;padding:2px; background:#fff;}
		.listtable tr:hover { background-color:#FFC;}
		.listtable tr:hover td { background-color:#FFC;}
		.listtable .zrow td { background:#F3F5F8;}
		.datatable { border:1px solid #fff;border-collapse:collapse;font-size:12px;width: 100%;}
		.datatable th{border:1px solid #fff;text-align:right;color:#355586;background:#DFEAFB; padding:2px;}
		.datatable td{padding:2px;background-color: #F9F5F2;border: 1px solid #fff;}
		.datatable .zrow td { background:#F3F5F8;}
		.listtable a,.datatable a { text-decoration: underline;}
		
		.main_div{
			width:100%;
			margin: 0 auto;
			font-size: 12px;
		}

		.subTitle {
		    margin-top: 10px;
		    padding-left: 10px;
		    background: #C6E3F1 none repeat scroll 0% 0%;
		    color: #005590;
		    font-size: 14px;
		    font-weight: bold;
		    height: 25px;
		    line-height: 25px;
		    border-bottom: 1px solid #8CBFDE;
		    position: relative;
		}

		.thirdTitle{
			margin-top: 5px;
		    background: #C6E3F1 none repeat scroll 0% 0%;
		    color: #005590;
		    font-size: 12px;
		    font-weight: bold;
		    height: 25px;
		    line-height: 25px;
		}

		.footer{
			text-align: right;
			margin-top: 10px;
		}

		.left{
			text-align: left;
		}

		.center{
			text-align: center;
		}
	</style>
<title>邮件预览</title>
<script type="text/javascript">
function sendEmail(input){
	
	var reEmail  = $('#reEmails').val();
	var ccEmail  = $('#ccEmails').val();
	if($.trim(reEmail)==''||$.trim(ccEmail)==''){
		
		layer.alert("收件人、抄送人不能为空", {icon: 2});
		return false;
	}
	var  reEmailT = reEmail.split(";");//去除最后一个分号
	var  ccEmailT = ccEmail.split(";");//去除最后一个分号
	var  compar = new RegExp("^(\\w)+([-+.]\\w+)*@tuniu\.com$");//判断tuniu.com邮箱正则表达式
	//对收件人邮箱进去判断
		for(var i=0;i<reEmailT.length;i++){
		if(!compar.test(reEmailT[i])){
			layer.alert("收件人中"+reEmailT[i]+"不符合要求!", {icon: 2});
			return false;
			break;
		}
	}
	//对抄送人邮箱进去判断
	for(var i=0;i<ccEmailT.length;i++){
		if(!compar.test(ccEmailT[i])){
			layer.alert("抄送人中"+ccEmailT[i]+"不符合要求!",{icon: 2});
			return false;
			break;
		}
	}
	var msg = "您确定发送该预警邮件吗？";
	layer.confirm(msg, {icon: 3}, function(index){
		 disableButton(input);
		 layer.close(index);
	$.ajax({
		type:'post',
		url:'qs/compSatisRate/sendEmail',
		data:{  title:title.value,
				reEmails:reEmails.value,
				ccEmails:ccEmails.value,
			    targetId:'${dto.targetId}',
			    searchQuarter :'${dto.searchQuarter}',
			    searchMonth :'${dto.searchMonth}',
			    timeType :'${dto.timeType}'
		 },
		cache : false,
		success:function(result){
			enableButton(input);
			if(result)
			{
		    	if(result.retCode == "0")
				{
		    		layer.alert(result.resMsg,{icon: 6,closeBtn: 0},function(){
						
		    			parent.layer.closeAll(); 
					});
		    		
		    		
				}else{
					
					 layer.alert(result.resMsg,{icon: 2});
				}
		     }
			
			
		}
		});
	});
} 
</script>
</head>
<body>
<div class="main_div">
<form name="emailForm" id="emailForm" method="post" action="">
		<table class="datatable"  style="border:1px solid lightblue">
		<tr>
		<td colspan="10"><div class="subTitle">邮件信息</div></td>
		</tr>
		<tr>
		<th style="width:10%">邮件主题：</th>
			<td colspan="4"><input style="font-size: 14px;width:900px;height:30px" type="text" name="title" id="title" value="${emailTitle}"/></td>
		</tr>
		<tr>
			<th style="width:10%">收件人：</th>
			<td>
				<textarea name="reEmails" id="reEmails" style="font-size: 14px;" rows="3" cols="40">${reEmails }</textarea>
			</td>
			<th style="width:10%">抄送人：</th>
			<td>
				<textarea name="ccEmails" id="ccEmails" style="font-size: 14px;" rows="3" cols="40">${ccEmails}</textarea>
			</td>
			<td>
			<input type="button" value="发送" id='sendButton'  name='sendButton' class="blue" onclick="sendEmail(this)">
			</td>
		</tr>
		</table>
</form>

	</div>
</body>
</html>
