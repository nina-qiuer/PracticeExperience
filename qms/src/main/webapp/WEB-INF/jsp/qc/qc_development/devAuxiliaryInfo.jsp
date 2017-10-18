<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>质检-辅助信息</title>
<script type="text/javascript">
$(document).ready(function(){
	$(".accordion").each(function() {
		$(this).accordion({
	    	collapsible: true,
	    	heightStyle: "content"
	    });
	});
});

function closeJira(jiraId){
	var msg = "您确定关闭该JIRA单吗？";
	layer.confirm(msg, {icon: 3}, function(index){
	$.ajax( {
		url : 'qc/jiraRelation/closeJira',
		data : 
		{
			jiraId : jiraId
		},
		type : 'post',
		dataType:'json',
		cache: false,
		success : function(result) {
			if(result)
			{
		    	if(result.retCode == "0")
				{
		    		
		    		parent.location.reload();
				}else{
					
					 layer.alert(result.resMsg,{icon: 2});
				}
		     }
		 }
	    });
	});
}

function addDev(cmpId,qcFlag){
	
	var devId="${qcBillId}";
	var msg="";
	if(qcFlag==3){
		
		 msg = "您确定双方质检吗？";
	}else{
		
		 msg = "您确定投诉质检吗？";
	}
	layer.confirm(msg, {icon: 3}, function(index){
		 layer.close(index);
	$.ajax({
		url : 'qc/qcBillRelation/saveDevToCmp',
		data : 
		{
			devId : devId,
			qcFlag : qcFlag,
			cmpId: cmpId
		},
		type : 'post',
		dataType:'json',
		cache: false,
		success : function(result) {
			if(result)
			{
		    	if(result.retCode == "0")
				{
		    		
					if(qcFlag==1){
			    			
			    			//window.parent.parent.opener.search();
				    		//window.top.close(); 
						    parent.location.reload();
						    
			    		}else{
			    			
			    			parent.location.reload();
			    		}
		    		
				 }else{
					
					 layer.alert(result.resMsg,{icon: 2});
				}
		     }
		 }
	    });
	});	
}


function closeRelation(cmpId){
	
	var msg  = "您确定取消关联吗？";
	layer.confirm(msg, {icon: 3}, function(index){
		 layer.close(index);
	$.ajax( {
		url : 'qc/qcBillRelation/closeByCmpId',
		data : 
		{
			cmpId : cmpId
		},
		type : 'post',
		dataType:'json',
		cache: false,
		success : function(result) {
			if(result)
			{
		    	if(result.retCode == "0")
				{
		    		
		    		parent.parent.location.reload();
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
<form name="searchForm" id="searchForm" method="post" action="" >
 <div class="common-box">
	<div class="common-box-hd">
		<span class="title2">关联JIRA单号</span>
	</div>
		<table class="listtable" width="1000px">
		<tr>
	    <th hidden>JIRAID</th>
		<th>JIRA单号</th>
		<th>JIRA单标题</th>
		<th>JIRA单描述</th>
		<th>问题主要原因</th>
		<th>问题原因明细</th>
		<th>解决方案</th>
		<th>严重等级</th>
		<th>研发处理人</th>
		 <c:if test= "${fn:contains(loginUser_WCS,'DEV_REPORT')}">
		<th>操作</th>
		</c:if>
		</tr>
		<c:forEach items="${list}" var="v">
		<tr align="center">
			<td  hidden>${v.id}</td>
			<td  width="70"><a href="javascript:void(0)" onclick="window.open('http://jira.tuniu.org/browse/${v.jiraName }')">${v.jiraName}</a></td>
			<td class="shorten30" width="100" title ="${v.summary}">${v.summary}</td>
			<td class="shorten30" title="${ v.description}" width="150">${v.description}</td>
			<td class="shorten30" title="${ v.mianReason}" width="100">${v.mianReason}</td>
			<td class="shorten30" title="${ v.reasonDetail}" width="150">${v.reasonDetail}</td>
			<td class="shorten30" title="${ v.solution}" width="80">${v.solution}</td>
			<td  width="80">${v.eventClass}</td>
			<td  width="80">${v.devProPeople}</td>
			 <c:if test= "${fn:contains(loginUser_WCS,'DEV_REPORT')}">
			<td width="80"> <input type="button" width="80"  class="blue" value="关闭" onclick="closeJira('${v.id}')"></td>
			</c:if>
		</tr>
		</c:forEach>
		</table>
<c:if test="${cmpList!=null&& fn:length(cmpList)>0}">

	<c:forEach items="${cmpList}" var="complaintBill">
	<c:if test="${complaintBill.id>0}">
	<div class="common-box">
		<div class="common-box-hd">
			<span class="title2">投诉单${complaintBill.id}</span>
		</div>
		<table class="datatable" width="100%">
			<tr> 
				<th width="80">投诉单号：</th>
				<td class="cmpId" width="85">${complaintBill.id}</td>
				<th style="color: orangered;" width="100">投诉级别：</th>
				<td>${complaintBill.cmpLevel}　
					<input  type="button" value="修改投诉等级" name="updateLevelBtn" id="updateLevelBtn" class="blue" style="width:100px" 
						onclick="openWin('修改投诉等级', 'qc/qcBill/toLevel?qcId=${qcBillId}',  300, 117)">
				</td>
				<th width="120">投诉时间：</th>
				<td><fmt:formatDate value="${complaintBill.addTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
				<th width="120">投诉处理完成时间：</th>
				<td><fmt:formatDate value="${complaintBill.finishTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
			<tr>
				<th>处理人：</th>
				<td>${complaintBill.dealPerson}</td>
				<th>对客赔偿总额：</th>
				<td>${complaintBill.indemnifyAmount} 元</td>
				<th>供应商赔付总额：</th>
				<td>${complaintBill.claimAmount} 元</td>
				<th style="color: orangered;">公司损失：</th>
				<td>${complaintBill.companyLose} 元</td>
			</tr>
			<tr>
				<th style="color: orangered;">投诉来源：</th>
				<td>${complaintBill.comeFrom}</td>
				<th>对客赔付理据：</th>
				<td >${complaintBill.payBasis}</td>
				<c:if test="${complaintBill.qcFlag==2}">
				<td><input type="button" class="blue" name="twoBtn" id="twoBtn" value="双方质检" onclick="addDev(${complaintBill.id},3)"></td>
				<td><input type="button"  class="blue"  name="oneBtn" id="oneBtn" value="投诉质检" onclick="addDev(${complaintBill.id},1)"></td>
				<td> <input type="button"  class="blue"  name="cancelBtn" id="cancelBtn" value="取消关联" onclick="closeRelation(${complaintBill.id})"></td>
				<td colspan="2"></td>
				</c:if>
				<c:if test="${complaintBill.qcFlag!=2}">
			    <td colspan="3">
			      <input type="button"  class="blue"  name="cancelBtn" id="cancelBtn" value="取消关联" onclick="closeRelation(${complaintBill.id})">
			    </td>
			    </c:if>
			</tr>
		</table>
	</div>
	<c:if test="${complaintBill.reasonList!=null && fn:length(complaintBill.reasonList)>0}">
		<div class="common-box">
			<div class="common-box-hd">
				<span class="title2">投诉事宜</span>
			</div>
			<table class="listtable" width="1000px">
				<tr>
					<th>投诉事宜一级分类</th>
					<th>投诉事宜二级分类</th>
					<th>投诉事宜</th>
					<th>备注</th>
				</tr>
				<c:forEach items="${complaintBill.reasonList}" var="v">
				<tr>
					<td width="120">${v.type}</td>
					<td width="120">${v.secondType}</td>
					<td class="left">${v.typeDescript}</td>
					<td class="left">${v.descript}</td>
				</tr>
				</c:forEach>
			</table>
		</div>
	</c:if>
	</c:if>
	</c:forEach>
	</c:if>
	</div>
</form>
</body>
</html>
