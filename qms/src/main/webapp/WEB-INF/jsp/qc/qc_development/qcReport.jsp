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
<table class="datatable" style="border:1px solid lightblue">
	<tr>
		<th style="width:10%">质量问题等级：</th>
		<td>${qcBill.qualityEventClass}级</td>
		<th style="width:10%">影响时长：</th>
		<td>${qcBill.influenceTime}分钟</td>
		<th style="width:10%">影响结果：</th>
		<td>${qcBill.influenceResult}</td>
		<th style="width:8%">质检单号：</th>
		<td style="width:7%">${qcBill.id}</td>
	</tr>
	<tr>
		<th>故障来源：</th>
		<td> ${qcBill.faultSourceName}</td>
		<th>报告添加时间：</th>
		<td >
			<fmt:formatDate value="${qcBill.addTime}" pattern="yyyy-MM-dd HH:mm:ss" />
		</td>
		<th>报告完成时间：</th>
		<td colspan="3">
			<fmt:formatDate value="${qcBill.finishTime}" pattern="yyyy-MM-dd HH:mm:ss" />
		</td>
	</tr>
	<tr>
		<th>影响系统：</th>
		<td>
		<c:forEach items="${qcBill.influenceSystem}" var="qcInfluenceSystem" >
				${qcInfluenceSystem.influenceSystem};&nbsp;
	    </c:forEach>
	    <th>故障发生时间：</th>
		<td >${qcBill.faultHappenTime}</td>
		<th>故障完成时间：</th>
		<td colspan="3">${qcBill.faultFinishTime}</td>
	</tr>
	<tr>
			<th>故障描述：</th>
			<td colspan="7">${qcBill.qcAffairDesc}</td>
	</tr>
	<tr>
			<td colspan="8"><div class="subTitle">质检结论</div></td>
	</tr>
	
	<c:forEach items="${devFaultList}" var="devFaultBill" varStatus="db">
		<c:if test="${devFaultBill.useFlag == 0  }">
			<tr>
				<td colspan="8"><div class="thirdTitle">开发故障单${db.index+1}-${devFaultBill.qptName}</div></td>
			</tr>
			<tr>
				<th>责任系统：</th>
				<td colspan="7">${devFaultBill.influenceSystem}</td>
			</tr>
			<tr>
				<th>原因分析：</th>
				<td colspan="7">${devFaultBill.causeAnalysis}</td>
			</tr>
		
			<tr>
				<th>处理措施：</th>
				<td colspan="7">${devFaultBill.treMeasures}</td>
			</tr>
			<tr>
				<th>改进措施：</th>
				<td colspan="7">${devFaultBill.impMeasures}</td>
			</tr>
			<tr>
				<th>责任单列表：</th>
				<td colspan="7">
					<c:if test="${devFaultBill.devList!=null && fn:length(devFaultBill.devList)>0}">
						<table class="listtable">
								<tr>
									<th style="text-align:center;">研发责任单号</th>
									<th style="text-align:center;">故障单号</th>
									<th style="text-align:center;">责任人</th>
									<th style="text-align:center;">责任部门</th>
									<th style="text-align:center;">责任岗位</th>
									<th style="text-align:center;">改进人</th>
								</tr>
								<c:forEach items="${devFaultBill.devList}" var="devRespBill">
									<tr>
										<td class="center">${devRespBill.id}</td>
										<td class="center">${devRespBill.devId}</td>
										<td class="center">${devRespBill.respPersonName}</td>
										<td class="left">${devRespBill.depName}</td>
										<td class="center">${devRespBill.jobName}</td>
										<td class="center">${devRespBill.impPersonName}</td>
									</tr>
								</c:forEach>
						</table>
					</c:if>
				</td>
			</tr>
		</c:if>
		<c:if test="${devFaultBill.useFlag == 1  }">
			<tr>
				<td colspan="8"><div class="thirdTitle">测试故障单${db.index+1}-${devFaultBill.qptName}</div></td>
			</tr>
			<tr>
				<th>漏测分析：</th>
				<td colspan="7">${devFaultBill.causeAnalysis}</td>
			</tr>
			<tr>
				<th>改进措施：</th>
				<td colspan="7">${devFaultBill.impMeasures}</td>
			</tr>
			<tr>
				<th>责任单列表：</th>
				<td colspan="7">
					<c:if test="${devFaultBill.devList!=null && fn:length(devFaultBill.devList)>0}">
						<table class="listtable">
								<tr>
									<th style="text-align:center;">研发责任单号</th>
									<th style="text-align:center;">故障单号</th>
									<th style="text-align:center;">责任人</th>
									<th style="text-align:center;">责任部门</th>
									<th style="text-align:center;">责任岗位</th>
									<th style="text-align:center;">改进人</th>
								</tr>
								<c:forEach items="${devFaultBill.devList}" var="devRespBill">
									<tr>
										<td class="center">${devRespBill.id}</td>
										<td class="center">${devRespBill.devId}</td>
										<td class="center">${devRespBill.respPersonName}</td>
										<td class="left">${devRespBill.depName}</td>
										<td class="center">${devRespBill.jobName}</td>
										<td class="center">${devRespBill.impPersonName}</td>
									</tr>
								</c:forEach>
						</table>
					</c:if>
				</td>
			</tr>
		</c:if>
		</c:forEach>
	</table>
	<!-- 处罚单 -->
		<c:if test="${innerPunishList!=null && fn:length(innerPunishList) > 0}">
		<table class="datatable">

			<tr>
				<td colspan="8"><div class="subTitle">处罚单</div></td>
			</tr>
				<c:forEach items="${innerPunishList}" var="innerPunishBill" varStatus="ipb">
				<tr>
					<td colspan="8"><div class="thirdTitle">内部处罚单${ipb.index+1}</div></td>
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
					<td>${innerPunishBill.economicPunish}元</td>
				</tr>
			</c:forEach>
		</table>
		</c:if>
		<table class="datatable">
			<tr>
				<th style="width:12%">关联Jira单号：</th>
				<td>
				<c:if test="${jiraList!=null && fn:length(jiraList) > 0}">
						<table class="listtable">
							<tr>
								<th style="width:10%;text-align:center">单号</th>
								<th style="width:30%;text-align:center">标题</th>
								<th style="text-align:center">描述</th>
							</tr>
						<c:forEach items="${jiraList}" var="jira">
							<tr>
								<td class="center"><a href="javascript:void(0)" onclick="window.open('http://jira.tuniu.org/browse/${jira.jiraName }')">${jira.jiraName}</a></td>
								<td style="text-align:left">${jira.summary}</td>
								<td style="text-align:left">${jira.description}</td>
							</tr>
						</c:forEach>
						</table>
				</c:if>
				</td>
			</tr>
			<tr>
				<th style="width:12%">关联投诉单号：</th>
				<td>
					<c:if test="${complaintBillList != null && fn:length(complaintBillList) > 0}">	
						<table class="listtable">
							<tr>
								<th style="width:10%;text-align:center">投诉单号：</th>
								<th style="text-align:center">投诉事宜</th>
							</tr>
							<c:forEach items="${complaintBillList}" var="complaint">
								<tr>
									<td class="cmpId">${complaint.id}</td>
									<td>
										<c:if test="${complaint.reasonList != null && fn:length(complaint.reasonList) > 0}">
										<table class="listtable">
											<tr>
												<th style="width:20%;text-align:center">一级分类</th>
												<th style="width:20%;text-align:center">二级分类</th>
												<th style="text-align:center">详细事宜</th>
											</tr>
											<c:forEach items="${complaint.reasonList}" var="v">
												<tr>
												 	<td style="text-align:center">${v.type}</td>
													<td style="text-align:center">${v.secondType}</td>
													<td style="text-align:center">${v.typeDescript}</td>
												</tr>
											</c:forEach>
										</table>
										</c:if>
									</td>
								</tr>
							</c:forEach>
						</table>
					</c:if>
				</td>
			</tr>
			
			<tr>
				<th style="width:12%">核实情况：</th>
				<td>
					${qcBill.verification}
				</td>
			</tr>
			
		</table>
	</div>
</body>
</html>
