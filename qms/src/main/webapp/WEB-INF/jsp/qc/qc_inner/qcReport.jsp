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
	<title>质检报告</title>
</head>
<body>
<div class="main_div">
<table class="datatable"  style="border:1px solid lightblue">
	<tr>
				<th style="width:10%">订单号：</th>
				<td style="width:10%">${qcBill.ordId}</td>
				<th style="width:10%">产品单号：</th>
				<td style="width:7%">${qcBill.prdId}</td>
				<th style="width:7%">申请人：</th>
				<td style="width:7%">${qcBill.addPerson}</td>
				<th style="width:8%">公司损失：</th>
				<td style="width:7%">${qcBill.lossAmount}</td>
				<th style="width:8%">质检类型：</th>
				<td style="width:10%">${qcBill.qcTypeName}</td>
	</tr>
	<tr>
		<th>质检事宜概述：</th>
		<td colspan="9"><p>${qcBill.qcAffairSummary}</p></td>
	</tr>
	<tr>
		<th>质检事宜详述：</th>
		<td colspan="9"><p>${qcBill.qcAffairDesc}</p></td>
	</tr>
	<tr>
		<th>核实情况：</th>
		<td colspan="9"><p>${qcBill.verification}</p></td>
	</tr>
	<tr>
		<th>附件：</th>
		<td colspan="9">
			<table>
				<tr>
					<c:forEach items="${attachList}" var="upload" >
					  <td>
					     <a href="${upload.path}" target="_blank">${upload.name}</a>
					  </td>
				    </c:forEach>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<th>备注：</th>
		<td colspan="9"><p>${qcBill.remark}</p></td>
	</tr>
	<tr>
		<td colspan="10"><div class="subTitle">业务操作日志</div></td>
	</tr>
	<tr>
	<td colspan="10">
	 <table class="listtable">
	 <tr>
  		<th style="text-align:center;width:150px">添加时间</th>
 		<th style="text-align:center;">操作人</th>
		<th style="text-align:center;width:100px">角色</th>
		<th style="text-align:center;">事件</th>
	    <th style="text-align:center;">备注</th>
   </tr>
	<c:forEach items="${operlist}" var="oper" varStatus="st">
	 <tr>
 	         <td class="addTime" ><fmt:formatDate value="${oper.addTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	 	     <td class="dealPeopleName"> ${oper.dealPeopleName}</td>
	  		 <td class="dealDepart">${oper.dealDepart}</td>
	  		 <td class="flowName">${oper.flowName}</td>
	  		 <td  class="shorten30">${oper.content}</td>
 	</tr>
	</c:forEach>
	</table>
	</td>
	</tr>
	<tr>
		<td colspan="10"><div class="subTitle">质检结论</div></td>
	</tr>
	<c:forEach items="${qualityProblemlist}" var="qualityProblem" varStatus="st">
			<tr>
				<td colspan="10">
				<div class="thirdTitle">${qualityProblem.qptName}${st.index+1}
				</div></td>
			</tr>
			<tr>
				<th style="width:12%">问题类型：</th>
				<td colspan="9">${qualityProblem.qptName}</td>
			</tr>
			<tr>
				<th style="width:12%">问题描述：</th>
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
									<th style="text-align:center;">改进人</th>
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
										<td class="center">${innerRespBill.impPersonName}</td>
										<td class="center">${innerRespBill.respManagerName}</td>
										<td class="center">${innerRespBill.managerJobName}</td>
										<td class="center">${innerRespBill.respGeneralName}</td>
										<td class="center">${innerRespBill.generalJobName}</td>
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
	</div>
</body>
</html>
