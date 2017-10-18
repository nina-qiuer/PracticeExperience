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
	
	/* if(editor1.text()==""){
		
		layer.alert("核实情况不能为空", {icon: 2});
		return false;
	} */
	
	var id = '${qcBill.id}';
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
	var str = document.getElementById("qcState");
	var qcState = 0;
	if(str.checked){
		
		qcState = str.value;
	 }
	var msg = "您确定发送该质检报告吗？";
	layer.confirm(msg, {icon: 3}, function(index){
		 disableButton(input);
		 layer.close(index);
	$.ajax({
		type:'post',
		url:'qc/qcBill/'+id+'/sendEmail',
		data:{'title':title.value,'reEmails':reEmails.value,'ccEmails':ccEmails.value,qcState:qcState},
		cache : false,
		success:function(result){
			enableButton(input);
			if(result)
			{
		    	if(result.retCode == "0")
				{
		    		layer.alert(result.resMsg,{icon: 6,closeBtn: 0},function(){
						
						 window.parent.parent.opener.search();
						//window.parent.opener.location.href=window.parent.opener.location.href;
			    		 window.top.close();  
					});
		    		
				 }else if(result.retCode == "2"){
					 
					     layer.alert(result.resMsg,{icon:2,closeBtn: 0},function(){
						 window.parent.parent.opener.search();
			    		 window.top.close();  
					});
					
				}else{
					
					 layer.alert(result.resMsg,{icon: 2});
				}
		     }
			
			
		}
		});
	});
} 

function returnBill(input){
	
	var id = '${qcBill.id}';
	var msg = "您确定退回该质检单吗？";
	layer.confirm(msg, {icon: 3}, function(index){
		 disableButton(input);
		 layer.close(index);
	$.ajax({
		type:'post',
		url:'qc/qcBill/'+id+'/returnBill',
		data:{},
		cache : false,
		success:function(result){
			enableButton(input);
			if(result)
			{
		    	if(result.retCode == "0")
				{
		    		layer.alert('退回成功',{icon: 6,closeBtn: 0},function(){
						
						 window.parent.parent.opener.search();
			    		 window.top.close();  
					});
		    		
				 }else{
					
					 layer.alert(result.resMsg,{icon: 2});
				}
		     }
			
			
		}
		});
	});
}

function checkAccount(id,input){
	
	var account = input.value;
	if($.trim(account) ==''){
		
		 layer.alert("应为0到100整数",{icon: 2});	
		 $(input).val(0);
		 return false;
	}
	var compar = new RegExp("^(((\\d|[1-9]\\d)(\\.\\d{1,2})?)|100|100.0|100.00)$");//^((\d|[1-9]\d)?|100)$
	if(!compar.test(account)){
		
       layer.alert("应为0到100整数或带两位小数",{icon: 2});	
       $(input).val(0);
       return false;
		 
	}
	$.ajax({
		url:'qc/innerResp/updateRespAccount',
		data:{
			innerId : id,
			account : account
		},
		type : 'post',
		dataType:'json',
		success:function(result){
			if(result)
			{
		    	if(result.retCode == "0")
				{
		    		layer.alert('占比保存成功',{icon: 6});
		    		
				 }else{
					 
					 layer.alert(result.resMsg,{icon: 2});
				}
		     }
		}
		});
}

</script>
</head>
<body>
<div class="main_div">
<form name="emailForm" id="emailForm" method="post" action="">
		<table class="datatable"  style="border:1px solid lightblue">
		<tr>
		<td colspan="10" style="height:50px">
		  ${talkConfig.content}
		</td>
		</tr>
		<tr>
		<td colspan="10"><div class="subTitle">邮件信息</div></td>
		</tr>
		<c:if test="${qcBill.state!=7}">
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
			<td align="center"><input type="button" class="blue"  value="选择收件人和抄送人" onclick="openWin('选择质检邮件模板', 'qc/mailConfig/${qcBill.addPersonId}/toChoose', 850, 400)">
			<input type="button" value="发送" id='sendButton'  name='sendButton' class="blue" onclick="sendEmail(this)">
			<input type="checkbox" name="qcState" id="qcState" value="6" <c:if test="${qcBill.state ==6}">checked="checked"</c:if>>待结</td>
		</tr>
		</c:if>
		<c:if test="${qcBill.state==7}">
		<tr>
		<th style="width:10%">邮件主题：</th>
			<td colspan="4"><input style="font-size: 14px;width:900px;height:30px" type="text" name="title" id="title" value="${qcBill.subject}"/></td>
		</tr>
		<tr>
			<th style="width:10%">收件人：</th>
			<td>
				<textarea name="reEmails" id="reEmails" style="font-size: 14px;" rows="3" cols="40">${qcBill.reAddrs }</textarea>
			</td>
			<th style="width:10%">抄送人：</th>
			<td>
				<textarea name="ccEmails" id="ccEmails" style="font-size: 14px;" rows="3" cols="40">${qcBill.ccAddrs}</textarea>
			</td>
			<td align="center">
			<input type="button" value="审核通过" id='auditButton'  name='auditButton' class="blue" onclick="sendEmail(this)">
			<input type="button" value="退回" id='returnButton'  name='returnButton' class="blue" onclick="returnBill(this)">
			<input type="checkbox" name="qcState" id="qcState" value="" style="display: none">
			</td>
		</tr>
		</c:if>
		</table>
</form>
<table class="datatable"  style="border:1px solid lightblue">
	<tr>
		<td colspan="10"><div class="subTitle">辅助信息</div></td>
	</tr>
	<!-- 有投诉质检单排版 -->
	<c:if test="${qcBill.cmpId > 0}">
		<tr>
			<th style="width:10%">投诉等级：</th>
			<td>${complaintBill.cmpLevel}</td>
			<th style="width:8%">订单号：</th>
			<td>${qcBill.ordId}</td>
			<th style="width:10%">投诉单号：</th>
			<td>${qcBill.cmpId}</td>
			<th style="width:10%">会员星级：</th>
			<td style="width:10%">${orderBill.custLevel}</td>
		</tr>
		<tr>
			<th>产品编号：</th>
			<td>${qcBill.prdId}</td>
			<th>团期：</th>
			<td>${qcBill.groupDate}</td>
			<th>产品名称：</th>
			<td>${product.prdName}</td>
			<th>产品经理：</th>
			<td>${orderBill.prdManager}</td>
		</tr>
		<tr>
	  	    <th>售卖价：</th>
		    <td>${orderBill.prdAdultPrice}</td>
		     <th>出行天数：</th>
		    <td>${orderBill.departDay}</td>
		    <th>地接成本价：</th>
		    <td>${orderBill.groundPrice}</td>
		    <th>日均地接成本价：</th>
		    <td>${orderBill.avlGroundPrice}
		</tr>
		<tr>
			<th>产品品牌：</th>
			<td>${product.brandName}</td>
			<th>出发地：</th>
			<td>${orderBill.departCity}</td>
			<th>产品线目的地：</th>
			<td colspan="3">${product.prdLineDestName}</td>
		</tr>
		<tr>
		    <th>导游编号：</th>
		    <td>${orderBill.guideId}</td>
		    <th>导游姓名：</th>
		    <td>${orderBill.guideName}</td>
		    <th>导游电话：</th>
		    <td colspan="3">${orderBill.guideCall}</td>
		</tr>
	</c:if>
	<!-- 有投诉质检单排版 end -->
	<!-- 无投诉质检单排版  start -->
	<c:if test="${qcBill.cmpId == 0}">
		<tr>
			<th style="width:10%">订单号：</th>
			<td>${qcBill.ordId}</td>
			<th style="width:8%">会员星级：</th>
			<td style="width:10%">${orderBill.custLevel}</td>
			<th style="width:10%">产品编号：</th>
			<td>${qcBill.prdId}</td>
			<th style="width:12%">团期：</th>
			<td style="width:10%">${qcBill.groupDate}</td> 
		</tr>
		<tr>
			<th >产品经理：</th>
			<td >${orderBill.prdManager}</td>
			<th>产品品牌：</th>
			<td>${product.brandName}</td>
			<th >产品名称：</th>
			<td>${product.prdName}</td>
			<th>出发地：</th>
			<td>${orderBill.departCity}</td>
		</tr>
		<tr>
	  	    <th>售卖价：</th>
		    <td>${orderBill.prdAdultPrice}</td>
		     <th>出行天数：</th>
		    <td>${orderBill.departDay}</td>
		    <th>地接成本价：</th>
		    <td>${orderBill.groundPrice}</td>
		    <th>日均地接成本价：</th>
		    <td>${orderBill.avlGroundPrice}
		</tr>
		<tr>
			<th>产品线目的地：</th>
			<td>${product.prdLineDestName}</td>
			<th>导游编号：</th>
		    <td>${orderBill.guideId}</td>
		    <th>导游姓名：</th>
		    <td>${orderBill.guideName}</td>
		    <th>导游电话：</th>
		    <td>${orderBill.guideCall}</td>
		</tr>
	</c:if>
	<!-- 无投诉质检单排版  END -->
	<tr>
		<th>供应商：</th>
		<td colspan="9">
			<table class="listtable" style="width:100%">
				<tr>
					<th style="width:20%;text-align:center;">供应商ID</th>
					<th style="width:20%;text-align:center;">供应商名称</th>
					<th style="width:20%;text-align:center">供应商owner</th>
					<th style="width:20%;text-align:center;">签约公司</th>
					<th style="width:20%;text-align:center;">供应商准入时间</th>
				</tr>
				<c:forEach items="${orderBill.angencies}" var="agency">
				<tr>
					<td>${agency.agencyId}</td>
					<td>${agency.agencyName}</td>
					<td>${agency.agencyOwner}</td>
					<td>${agency.signCompany}</td>
					<td>${agency.agencyAccessTime}</td>
				</tr>
				</c:forEach>
			</table>
		</td>
	</tr>
	<c:if test="${qcBill.cmpId > 0}">
		<tr>
			<th>投诉问题描述：</th>
			<td colspan="9">
				<table class="listtable" style="width:100%">
					<tr>
						<th style="width:10%;text-align:center;">一级分类</th>
						<th style="width:10%;text-align:center;">二级分类</th>
						<th style="width:60%;text-align:center">投诉事宜</th>
						<th style="width:20%;text-align:center;">备注</th>
					</tr>
					<c:forEach items="${complaintBill.reasonList}" var="cmpReason">
					<tr>
						<td>${cmpReason.type}</td>
						<td>${cmpReason.secondType}</td>
						<td>${cmpReason.typeDescript}</td>
						<td>${cmpReason.descript}</td>
					</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
	</c:if>
	<c:if test="${qcBill.cmpId == 0}">
		<tr>
			<th>质检事宜：</th>
			<td colspan="9"><p>${qcBill.qcAffairDesc}</p></td>
		</tr>
	</c:if>
	<tr>
		<th>核实情况：</th>
		<td colspan="9"><p>${qcBill.verification}</p></td>
	</tr>
	<tr>
		<th>备注：</th>
		<td colspan="9"><p>${qcBill.remark}</p></td>
	</tr>
	<tr>
		<td colspan="10"><div class="subTitle">质检结论</div></td>
	</tr>
	<c:forEach items="${executeQpList}" var="qualityProblem" varStatus="st">
			<tr>
				<td colspan="10">
				<div class="thirdTitle">执行问题-质量问题${st.index+1}
					<c:if test="${qualityProblem.lowSatisDegree == 1}">	
								【低满意度（满意度小于等于50%）】
				</c:if>
				</div></td>
			</tr>
			<tr>
				<th>问题类型：</th>
				<td colspan="9">${qualityProblem.qptName}</td>
			</tr>
			<tr>
				<th>问题描述：</th>
				<td colspan="9">${qualityProblem.description}</td>
			</tr>
		<c:if test="${qualityProblem.impAdvice!=''}">
				<tr>
					<th>问题判定：</th>
					<td colspan="9">${qualityProblem.impAdvice}</td>
				</tr>
		</c:if>
			<tr>
				<th>责任单列表：</th>
				<td colspan="9">
				<c:if test="${qualityProblem.innerList!=null && fn:length(qualityProblem.innerList)>0}">
						<table class="listtable">
								<tr>
									<th style="text-align:center;">责任人</th>
									<th style="text-align:center;">责任部门</th>
									<th style="text-align:center;">责任岗位</th>
									<th style="text-align:center;color:red">责任占比</th>
									<th style="text-align:center;">改进人</th>
									<th style="text-align:center;">改进人岗位</th>
									<th style="text-align:center;">异议提出人</th>
									<th style="text-align:center;">异议提出人岗位</th>
									<th style="text-align:center;">责任经理</th>
									<th style="text-align:center;">责任经理岗位</th>
									<th style="text-align:center;">责任总监</th>
									<th style="text-align:center;">责任总监岗位</th>
								</tr>
								<c:forEach items="${ qualityProblem.innerList}" var="innerRespBill" >
									<tr>
										<td class="center">${innerRespBill.respPersonName}</td>
										<td>${innerRespBill.depName}</td>
										<td class="center">${innerRespBill.jobName}</td>
										<td class="center"><input type="text" style="width:50px"  onblur="checkAccount(${innerRespBill.id},this)" name="respRate" id="respRate" value="${innerRespBill.respRate}">%</td>
										<td class="center">${innerRespBill.impPersonName}</td>
										<td class="center">${innerRespBill.impJobName}</td>
										<td class="center">${innerRespBill.appealPersonName}</td>
										<td class="center">${innerRespBill.appealJobName}</td>
										<td class="center">${innerRespBill.respManagerName}</td>
										<td class="center">${innerRespBill.managerJobName}</td>
										<td class="center">${innerRespBill.respGeneralName}</td>
										<td class="center">${innerRespBill.generalJobName}</td>
									</tr>
								</c:forEach>
						</table>
					</c:if>
				<c:if test="${qualityProblem.outerList!=null && fn:length(qualityProblem.outerList)>0}">
						<table class="listtable">
							<tr>
								<th style="text-align:center;">供应商名称</th>
								<th style="text-align:center;">改进人</th>
								<th style="text-align:center;">异议提出人</th>
								<th style="text-align:center;">责任经理</th>
								<th style="text-align:center;">责任经理岗位</th>
								<th style="text-align:center;">责任总监</th>
								<th style="text-align:center;">责任总监岗位</th>
							</tr>
								<c:forEach items="${ qualityProblem.outerList}" var="outerRespBill" >
								<tr>
									<td>${outerRespBill.agencyName}</td>
									<td class="center">${outerRespBill.impPersonName}</td>
									<td class="center">${outerRespBill.appealPersonName}</td>
									<td class="center">${outerRespBill.respManagerName}</td>
									<td class="center">${outerRespBill.managerJobName}</td>
									<td class="center">${outerRespBill.respGeneralName}</td>
									<td class="center">${outerRespBill.generalJobName}</td>
								</tr>
								</c:forEach>
						</table>
				</c:if>
				</td>
			</tr>
		</c:forEach>
		
		<c:forEach items="${ supplierQpList}" var="qualityProblem"  varStatus="qp">
			<tr>
				<td colspan="10"><div class="thirdTitle">供应商问题-质量问题 ${qp.index+1}
				<c:if test="${qualityProblem.lowSatisDegree == 1}">	
						【低满意度（满意度小于等于50%）】
				</c:if>
				</div></td>
			</tr>
			<tr>
				<th>问题类型：</th>
				<td colspan="9">${qualityProblem.qptName}</td>
			</tr>
			<tr>
				<th>问题描述：</th>
				<td colspan="9">${qualityProblem.description}</td>
			</tr>
			<c:if test="${qualityProblem.impAdvice!=''}">
				<tr>
					<th>问题判定：</th>
					<td colspan="9">${qualityProblem.impAdvice}</td>
				</tr>
			</c:if>
			<tr>
				<th>责任单列表：</th>
				<td colspan="9">
					<c:if test="${qualityProblem.innerList!=null && fn:length(qualityProblem.innerList)>0}">
					<table class="listtable">
						<tr>
							<th style="text-align:center;">责任人</th>
							<th style="text-align:center;">责任部门</th>
							<th style="text-align:center;">责任岗位</th>
							<th style="text-align:center;color:red">责任占比</th>
							<th style="text-align:center;">改进人</th>
							<th style="text-align:center;">改进人岗位</th>
							<th style="text-align:center;">异议提出人</th>
							<th style="text-align:center;">异议提出人岗位</th>
							<th style="text-align:center;">责任经理</th>
							<th style="text-align:center;">责任经理岗位</th>
							<th style="text-align:center;">责任总监</th>
							<th style="text-align:center;">责任总监岗位</th>
						</tr>
						<c:forEach items="${ qualityProblem.innerList}" var="innerRespBill" >
							<tr>
								<td class="center">${innerRespBill.respPersonName}</td>
								<td>${innerRespBill.depName}</td>
								<td class="center">${innerRespBill.jobName}</td>
								<td class="center"><input type="text" style="width:50px" name="respRate" id="respRate"   onblur="checkAccount(${innerRespBill.id},this)" value="${innerRespBill.respRate}">%</td>
								<td class="center">${innerRespBill.impPersonName}</td>
								<td class="center">${innerRespBill.impJobName}</td>
								<td class="center">${innerRespBill.appealPersonName}</td>
								<td class="center">${innerRespBill.appealJobName}</td>
								<td class="center">${innerRespBill.respManagerName}</td>
								<td class="center">${innerRespBill.managerJobName}</td>
								<td class="center">${innerRespBill.respGeneralName}</td>
								<td class="center">${innerRespBill.generalJobName}</td>
							</tr>
						</c:forEach>
					</table>
				</c:if>
				<c:if test="${qualityProblem.outerList!=null && fn:length(qualityProblem.outerList)>0}">
					<table class="listtable">
						<tr>
							<th style="text-align:center;">供应商名称</th>
							<th style="text-align:center;">改进人</th>
							<th style="text-align:center;">异议提出人</th>
							<th style="text-align:center;">责任经理</th>
							<th style="text-align:center;">责任经理岗位</th>
							<th style="text-align:center;">责任总监</th>
							<th style="text-align:center;">责任总监岗位</th>
						</tr>
					<c:forEach items="${ qualityProblem.outerList}" var="outerRespBill" >
						<tr>
							<td>${outerRespBill.agencyName}</td>
							<td class="center">${outerRespBill.impPersonName}</td>
							<td class="center">${outerRespBill.appealPersonName}</td>
							<td class="center">${outerRespBill.respManagerName}</td>
							<td class="center">${outerRespBill.managerJobName}</td>
							<td class="center">${outerRespBill.respGeneralName}</td>
							<td class="center">${outerRespBill.generalJobName}</td>
						</tr>
					</c:forEach>
					</table>
				</c:if>
				</td>
			</tr>
		</c:forEach>
		<c:forEach items="${flowsQpList}" var="qualityProblem" varStatus="st">
			<tr>
				<td colspan="10">
				<div class="thirdTitle">流程系统问题-质量问题${st.index+1}
					<c:if test="${qualityProblem.lowSatisDegree == 1}">	
								【低满意度（满意度小于等于50%）】
				</c:if>
				</div></td>
			</tr>
			<tr>
				<th>问题类型：</th>
				<td colspan="9">${qualityProblem.qptName}</td>
			</tr>
			<tr>
				<th>问题描述：</th>
				<td colspan="9">${qualityProblem.description}</td>
			</tr>
		<c:if test="${qualityProblem.impAdvice!=''}">
				<tr>
					<th>问题判定：</th>
					<td colspan="9">${qualityProblem.impAdvice}</td>
				</tr>
		</c:if>
			<tr>
				<th>责任单列表：</th>
				<td colspan="9">
				<c:if test="${qualityProblem.innerList!=null && fn:length(qualityProblem.innerList)>0}">
						<table class="listtable">
								<tr>
									<th style="text-align:center;">责任人</th>
									<th style="text-align:center;">责任部门</th>
									<th style="text-align:center;">责任岗位</th>
									<th style="text-align:center;color:red">责任占比</th>
									<th style="text-align:center;">改进人</th>
									<th style="text-align:center;">改进人岗位</th>
									<th style="text-align:center;">异议提出人</th>
									<th style="text-align:center;">异议提出人岗位</th>
									<th style="text-align:center;">责任经理</th>
									<th style="text-align:center;">责任经理岗位</th>
									<th style="text-align:center;">责任总监</th>
									<th style="text-align:center;">责任总监岗位</th>
								</tr>
								<c:forEach items="${ qualityProblem.innerList}" var="innerRespBill" >
									<tr>
										<td class="center">${innerRespBill.respPersonName}</td>
										<td>${innerRespBill.depName}</td>
										<td class="center">${innerRespBill.jobName}</td>
										<td class="center"><input type="text" style="width:50px" name="respRate"  onblur="checkAccount(${innerRespBill.id},this)" id="respRate" value="${innerRespBill.respRate}">%</td>
										<td class="center">${innerRespBill.impPersonName}</td>
										<td class="center">${innerRespBill.impJobName}</td>
										<td class="center">${innerRespBill.appealPersonName}</td>
										<td class="center">${innerRespBill.appealJobName}</td>
										<td class="center">${innerRespBill.respManagerName}</td>
										<td class="center">${innerRespBill.managerJobName}</td>
										<td class="center">${innerRespBill.respGeneralName}</td>
										<td class="center">${innerRespBill.generalJobName}</td>
									</tr>
								</c:forEach>
						</table>
					</c:if>
				<c:if test="${qualityProblem.outerList!=null && fn:length(qualityProblem.outerList)>0}">
						<table class="listtable">
							<tr>
								<th style="text-align:center;">供应商名称</th>
								<th style="text-align:center;">改进人</th>
								<th style="text-align:center;">异议提出人</th>
								<th style="text-align:center;">责任经理</th>
								<th style="text-align:center;">责任经理岗位</th>
								<th style="text-align:center;">责任总监</th>
								<th style="text-align:center;">责任总监岗位</th>
							</tr>
								<c:forEach items="${ qualityProblem.outerList}" var="outerRespBill" >
								<tr>
									<td>${outerRespBill.agencyName}</td>
									<td class="center">${outerRespBill.impPersonName}</td>
									<td class="center">${outerRespBill.appealPersonName}</td>
									<td class="center">${outerRespBill.respManagerName}</td>
									<td class="center">${outerRespBill.managerJobName}</td>
									<td class="center">${outerRespBill.respGeneralName}</td>
									<td class="center">${outerRespBill.generalJobName}</td>
								</tr>
								</c:forEach>
						</table>
				</c:if>
				</td>
			</tr>
		</c:forEach>
		<c:forEach items="${improveQpList}" var="qualityProblem" varStatus="st">
			<tr>
				<td colspan="10">
				<div class="thirdTitle">改进问题-质量问题${st.index+1}
					<c:if test="${qualityProblem.lowSatisDegree == 1}">	
								【低满意度（满意度小于等于50%）】
				</c:if>
				</div></td>
			</tr>
			<tr>
				<th>问题类型：</th>
				<td colspan="9">${qualityProblem.qptName}</td>
			</tr>
			<tr>
				<th>问题描述：</th>
				<td colspan="9">${qualityProblem.description}</td>
			</tr>
		<c:if test="${qualityProblem.impAdvice!=''}">
				<tr>
					<th>问题判定：</th>
					<td colspan="9">${qualityProblem.impAdvice}</td>
				</tr>
		</c:if>
			<tr>
				<th>责任单列表：</th>
				<td colspan="9">
				<c:if test="${qualityProblem.innerList!=null && fn:length(qualityProblem.innerList)>0}">
						<table class="listtable">
								<tr>
									<th style="text-align:center;">责任人</th>
									<th style="text-align:center;">责任部门</th>
									<th style="text-align:center;">责任岗位</th>
									<th style="text-align:center;color:red">责任占比</th>
									<th style="text-align:center;">改进人</th>
									<th style="text-align:center;">改进人岗位</th>
									<th style="text-align:center;">异议提出人</th>
									<th style="text-align:center;">异议提出人岗位</th>
									<th style="text-align:center;">责任经理</th>
									<th style="text-align:center;">责任经理岗位</th>
									<th style="text-align:center;">责任总监</th>
									<th style="text-align:center;">责任总监岗位</th>
								</tr>
								<c:forEach items="${ qualityProblem.innerList}" var="innerRespBill" >
									<tr>
										<td class="center">${innerRespBill.respPersonName}</td>
										<td>${innerRespBill.depName}</td>
										<td class="center">${innerRespBill.jobName}</td>
										<td class="center"><input type="text" style="width:50px"  onblur="checkAccount(${innerRespBill.id},this)" name="respRate" id="respRate" value="${innerRespBill.respRate}">%</td>
										<td class="center">${innerRespBill.impPersonName}</td>
										<td class="center">${innerRespBill.impJobName}</td>
										<td class="center">${innerRespBill.appealPersonName}</td>
										<td class="center">${innerRespBill.appealJobName}</td>
										<td class="center">${innerRespBill.respManagerName}</td>
										<td class="center">${innerRespBill.managerJobName}</td>
										<td class="center">${innerRespBill.respGeneralName}</td>
										<td class="center">${innerRespBill.generalJobName}</td>
									</tr>
								</c:forEach>
						</table>
					</c:if>
				<c:if test="${qualityProblem.outerList!=null && fn:length(qualityProblem.outerList)>0}">
						<table class="listtable">
							<tr>
								<th style="text-align:center;">供应商名称</th>
								<th style="text-align:center;">改进人</th>
								<th style="text-align:center;">异议提出人</th>
								<th style="text-align:center;">责任经理</th>
								<th style="text-align:center;">责任经理岗位</th>
								<th style="text-align:center;">责任总监</th>
								<th style="text-align:center;">责任总监岗位</th>
							</tr>
								<c:forEach items="${ qualityProblem.outerList}" var="outerRespBill" >
								<tr>
									<td>${outerRespBill.agencyName}</td>
									<td class="center">${outerRespBill.impPersonName}</td>
									<td class="center">${outerRespBill.appealPersonName}</td>
									<td class="center">${outerRespBill.respManagerName}</td>
									<td class="center">${outerRespBill.managerJobName}</td>
									<td class="center">${outerRespBill.respGeneralName}</td>
									<td class="center">${outerRespBill.generalJobName}</td>
								</tr>
								</c:forEach>
						</table>
				</c:if>
				</td>
			</tr>
		</c:forEach>
   </table>
	<!-- 处罚单 -->
	<c:if test="${innerPunishList!=null && fn:length(innerPunishList) > 0}">
		<table class="datatable">
			<tr>
				<td colspan="10"><div class="subTitle">处罚单</div></td>
			</tr>
			<c:forEach items="${ innerPunishList}" var="innerPunishBill" varStatus="ipst"> 
				<tr>
					<td colspan="10"><div class="thirdTitle">内部处罚单${ipst.index+1}</div></td>
				</tr>
				<tr>
					<th style="width:7%">被处罚人：</th>
					<td>${innerPunishBill.punishPersonName}</td>
					<th style="width:10%">被处罚人工号：</th>
					<td>${innerPunishBill.pubPersonId}</td>
					<th style="width:7%">关联部门：</th>
					<td>${innerPunishBill.depName}</td>
					<th style="width:7%">关联岗位：</th>
					<td>${innerPunishBill.jobName}</td>
				</tr>
				<tr>
					<th>连带责任：</th>
					<td>
					<c:if test="${innerPunishBill.relatedFlag==0 }">
							否
				    </c:if>
				    <c:if test="${innerPunishBill.relatedFlag==1 }">
							是
					</c:if>
					</td>
					<th>记分处罚：</th>
					<td>${innerPunishBill.scorePunish}分</td>
					<th>经济处罚：</th>
					<td colspan="3">${innerPunishBill.economicPunish}元</td>
				</tr>
				<tr>
					<th>处罚依据：</th>
					<td colspan="7">
						<table class="listtable">
							<tr>
							    <th style="width:15%;text-align:center;">处罚等级</th>
								<th style="text-align:center;">分级标准</th>
							</tr>
						<c:forEach items="${ innerPunishBill.ipbList}" var="innerPunishBasis" > 
								<tr>
									<td class="center" <c:if test="${innerPunishBasis.punishStandard.redLineFlag==1}"> style="color:red" </c:if>>
										${innerPunishBasis.punishStandard.level}
									</td>
									<td  class="left">${innerPunishBasis.punishStandard.description}</td>
								</tr>
						</c:forEach>
						</table>
					</td>
				</tr>
			</c:forEach>
		</table>
		</c:if>
		<c:if test="${outerPunishList!=null && fn:length(outerPunishList) > 0}">
		<table class="datatable">
		<c:forEach items="${ outerPunishList}" var="outerPunishBill" varStatus="opst"> 
				<tr>
					<td colspan="4"><div class="thirdTitle">外部处罚单${opst.index+1}</div></td>
				</tr>
				<tr>
					<%-- <th style="width:7%">供应商id：</th>
					<td>${outerPunishBill.id}</td> --%>
					<th style="width:10%">供应商名称：</th>
					<td>${outerPunishBill.agencyName}</td>
					<th style="width:7%">记分处罚：</th>
					<td>${outerPunishBill.scorePunish}分</td>
					<%-- <th style="width:7%">经济处罚：</th>
					<td>${outerPunishBill.economicPunish}元</td> --%>
				</tr>
				<c:if test="${outerPunishBill.opbList!=null && fn:length(outerPunishBill.opbList) > 0}">
					<tr>
						<th>处罚依据：</th>
						<td colspan="3">
							<table class="listtable">
								<tr>
									<th style="width:15%;text-align:center;">处罚等级</th>
									<th style="text-align:center;">分级标准</th>
									
								</tr>
								<c:forEach items="${ outerPunishBill.opbList}" var="outerPunishBasis" > 
									<tr>
										<td class="center"  <c:if test="${outerPunishBasis.punishStandard.redLineFlag==1}"> style="color:red" </c:if>>
											${outerPunishBasis.punishStandard.level}
										</td>
										<td  class="left">${outerPunishBasis.punishStandard.description}</td>
									
									</tr>
								</c:forEach>
							</table>
						</td>
					</tr>
				</c:if>
			</c:forEach>
		</table>
		</c:if>
	</div>
</body>
</html>
