<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>研发质检报告</title>
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
			width:80%;
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
</head>
<body>
	<div class="main_div">
		<table class="datatable" style="border:1px solid lightblue">
			<tr>
				<th style="width:10%">质量问题等级：</th>
				<td>${qcBill.qualityEventClass!}级</td>
				<th style="width:10%">影响时长：</th>
				<td>${(qcBill.influenceTime!0)?c}分钟</td>
				<th style="width:10%">影响结果：</th>
				<td>${qcBill.influenceResult!}</td>
				<th style="width:10%">质检单号：</th>
				<td >${(qcBill.id!0)?c}</td>
			</tr>
			<tr>
				<th >故障发生时间：</th>
				<td >${qcBill.faultHappenTime!}</td>
				<th>故障完成时间：</th>
				<td >${qcBill.faultFinishTime!}</td>
				<th>报告添加时间：</th>
				<td>${qcBill.addTime?string("yyyy-MM-dd")}</td>
				<th>报告完成时间：</th>
				<td>${qcBill.finishTime?string("yyyy-MM-dd")}</td>
			</tr>
			<tr>
				<th>故障来源：</th>
				<td> ${qcBill.faultSourceName!}</td>
				<th>影响系统：</th>
					<td colspan="5">
						<#list qcBill.influenceSystem as qcInfluenceSystem>
							${qcInfluenceSystem.influenceSystem!};&nbsp;
						</#list>
					</td>
			</tr>
			<tr>
				<th>故障描述：</th>
				<td colspan="7">
					${qcBill.qcAffairDesc!}
				</td>
			</tr>
			<tr>
				<td colspan="8"><div class="subTitle">质检结论</div></td>
			</tr>

			<#list devFaultList as devFaultBill>
			<#if devFaultBill.useFlag == 0 >
			<tr>
				<td colspan="8"><div class="thirdTitle">开发故障单${devFaultBill_index+1}-${devFaultBill.qptName!}</div></td>
			</tr>
			<tr>
				<th>责任系统：</th>
				<td colspan="7">${devFaultBill.influenceSystem!}</td>
			</tr>
			<tr>
				<th>原因分析：</th>
				<td colspan="7">${devFaultBill.causeAnalysis!}</td>
			</tr>
			<tr>
				<th>处理措施：</th>
				<td colspan="7">${devFaultBill.treMeasures!}</td>
			</tr>
			<tr>
				<th>改进措施：</th>
				<td colspan="7">${devFaultBill.impMeasures!}</td>
			</tr>
			<tr>
				<th>责任单列表：</th>
				<td colspan="7">
					<#if devFaultBill.devList?? && (devFaultBill.devList?size>0) >
						<table class="listtable">
								<tr>
									<th style="text-align:center;">研发责任单号</th>
									<th style="text-align:center;">故障单号</th>
									<th style="text-align:center;">责任人</th>
									<th style="text-align:center;">责任部门</th>
									<th style="text-align:center;">责任岗位</th>
									<th style="text-align:center;">改进人</th>
								</tr>
								<#list devFaultBill.devList as devRespBill>
									<tr>
										<td class="center">${(devRespBill.id!0)?c}</td>
										<td class="center">${(devRespBill.devId!0)?c}</td>
										<td class="center">${devRespBill.respPersonName!}</td>
										<td class="left">${devRespBill.depName!}</td>
										<td class="center">${devRespBill.jobName!}</td>
										<td class="center">${devRespBill.impPersonName!}</td>
									</tr>
								</#list>
						</table>
					</#if>
				</td>
			</tr>
			</#if>
			<#if devFaultBill.useFlag == 1 >
			<tr>
				<td colspan="8"><div class="thirdTitle">测试故障单${devFaultBill_index+1}-${devFaultBill.qptName!}</div></td>
			</tr>
			<tr>
				<th>漏测分析：</th>
				<td colspan="7">${devFaultBill.causeAnalysis!}</td>
			</tr>
			<tr>
				<th>改进措施：</th>
				<td colspan="7">${devFaultBill.impMeasures!}</td>
			</tr>
			<tr>
				<th>责任单列表：</th>
				<td colspan="7">
					<#if devFaultBill.devList?? && (devFaultBill.devList?size>0) >
						<table class="listtable">
								<tr>
									<th style="text-align:center;">研发责任单号</th>
									<th style="text-align:center;">故障单号</th>
									<th style="text-align:center;">责任人</th>
									<th style="text-align:center;">责任部门</th>
									<th style="text-align:center;">责任岗位</th>
									<th style="text-align:center;">改进人</th>
								</tr>
								<#list devFaultBill.devList as devRespBill>
									<tr>
										<td class="center">${(devRespBill.id!0)?c}</td>
										<td class="center">${(devRespBill.devId!0)?c}</td>
										<td class="center">${devRespBill.respPersonName!}</td>
										<td class="left">${devRespBill.depName!}</td>
										<td class="center">${devRespBill.jobName!}</td>
										<td class="center">${devRespBill.impPersonName!}</td>
									</tr>
								</#list>
						</table>
					</#if>
				</td>
			</tr>
			</#if>
			</#list>
			
		</table>

		<#-- 处罚单 -->
		<#if innerPunishList??>
		<table class="datatable">

			<tr>
				<td colspan="8"><div class="subTitle">处罚单</div></td>
			</tr>
			<#list innerPunishList as innerPunishBill>
				<tr>
					<td colspan="8"><div class="thirdTitle">内部处罚单${innerPunishBill_index+1}</div></td>
				</tr>
				<tr>
					<th style="width:7%">被处罚人：</th>
					<td>${innerPunishBill.punishPersonName!}</td>
					<th style="width:10%">被处罚人工号：</th>
					<td>${innerPunishBill.pubPersonId}</td>
					<th style="width:7%">关联部门：</th>
					<td>${innerPunishBill.depName!}</td>
					<th style="width:7%">关联岗位：</th>
					<td>${innerPunishBill.jobName!}</td>
				</tr>
				<tr>
					<th>连带责任：</th>
					<td>
						<#if innerPunishBill.relatedFlag==0>
							否
						<#else>
							是
						</#if>
					</td>
					<th>记分处罚：</th>
					<td>${(innerPunishBill.scorePunish!0)?c}分</td>
					<th>经济处罚：</th>
					<td>${(innerPunishBill.economicPunish!0)?string('0.00')}元</td>
				</tr>
			</#list>
		</table>
		</#if>
		<table class="datatable">
			<tr>
				<th style="width:12%">关联Jira单号：</th>
				<td>
					<#if jiraList??>
						<table class="listtable">
							<tr>
								<th style="width:10%;text-align:center">单号</th>
								<th style="width:30%;text-align:center">标题</th>
								<th style="text-align:center">描述</th>
							</tr>
						<#list jiraList as jira>
							<tr>
								<td class="center"><a href='http://jira.tuniu.org/browse/${jira.jiraName}'>${jira.jiraName!}</a></td>
								<td style="text-align:left">${jira.summary!}</td>
								<td style="text-align:left">${jira.description!}</td>
							</tr>
						</#list>
						</table>
					</#if>
				</td>
			</tr>
			
			<tr>
				<th style="width:12%">关联投诉单号：</th>
				<td>
					 <#if complaintBillList?? && (complaintBillList?size>0) > 	
						<table class="listtable">
							<tr>
								<th style="width:10%; text-align:center">投诉单号：</th>
								<th style="text-align:center">投诉事宜</th>
							</tr>
							<#list complaintBillList as complaint>
								<tr>
									<td class="cmpId">${(complaint.id!)?c}</td>
									<td>
										<#if complaint.reasonList?? && (complaint.reasonList?size>0)> 
											<table class="listtable">
												<tr>
													<th style="width:130;text-align:center">一级分类</th>
													<th style="width:130;text-align:center">二级分类</th>
													<th style="width:500;text-align:center">详细事宜</th>
												</tr>
												<#list complaint.reasonList as v>
													<tr>
													 	<td style="text-align:center">${v.type}</td>
														<td style="text-align:center">${v.secondType}</td>
														<td style="text-align:center">${v.typeDescript}</td>
													</tr>
												</#list>
											</table>
										</#if>
									</td>
								</tr>
							</#list>
						</table>
					</#if>
				</td>
			</tr>
			
			<tr>
				<th style="width:12%">核实情况：</th>
				<td>
					${qcBill.verification!}
				</td>
			</tr>
		</table>

		<div class="footer">
			<span><strong>质检员：</strong>${qcPerson}</span>&nbsp;&nbsp;&nbsp;&nbsp;
			<span><strong>日期：</strong>${dateTime?date}</span>
		</div>
	</div>
</body>
</html>