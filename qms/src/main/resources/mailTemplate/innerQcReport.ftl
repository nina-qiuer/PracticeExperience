<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>内部质检报告</title>
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
		    margin-top: 40px;
		    background: #E5E6CD none repeat scroll 0% 0%;
			color:#33312C;
			font-size:20px;
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
			<!-- touchRedTitle -->
			<tr>
				<th style="width:12%">订单号：</th>
				<td style="width:10%">${(qcBill.ordId!0)?c}</td>
				<th style="width:8%">产品单号：</th>
				<td>${(qcBill.prdId!0)?c}</td>
				<th style="width:10%">申请人：</th>
				<td>${qcBill.addPerson!}</td>
				<th style="width:10%">公司损失：</th>
				<td>${(qcBill.lossAmount!0)?c}</td>
				<th style="width:10%">质检类型：</th>
				<td>${qcBill.qcTypeName!}</td>
			</tr>
			<tr>
				<th style="width:12%">质检事宜概述：</th>
				<td colspan="9"><p>${qcBill.qcAffairSummary!}</p></td>
			</tr>
			<tr>
				<th style="width:12%">质检事宜详述：</th>
				<td colspan="9"><p>${qcBill.qcAffairDesc!}</p></td>
			</tr>
			<tr>
				<th>核实情况：</th>
				<td colspan="9"><p>${qcBill.verification!}</p></td>
			</tr>
			<tr>
				<td colspan="10"><div class="subTitle">质检结论</div></td>
			</tr>
			<#list qualityProblemlist as qualityProblem>
			<tr>
				<td colspan="10">
					<div class="thirdTitle">
						${qualityProblem.qptName!}${qualityProblem_index+1}
					</div>
				</td>
			</tr>
			<tr>
				<th>问题类型：</th>
				<td colspan="9">${qualityProblem.qptName!}</td>
			</tr>
			<tr>
				<th>问题描述：</th>
				<td colspan="9">${qualityProblem.description!}</td>
			</tr>
			<#if (qualityProblem.impAdvice!"")?length gt 0>
				<tr>
					<th>问题判定：</th>
					<td colspan="9">${qualityProblem.impAdvice!}</td>
				</tr>
			</#if>
			
			<tr>
				<th>责任单列表：</th>
				<td colspan="9">
					<#if qualityProblem.innerList?? && (qualityProblem.innerList?size>0) >
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
						<#list qualityProblem.innerList as innerRespBill>
							<tr>
								<td class="center">${innerRespBill.respPersonName!}</td>
								<td class="center">${innerRespBill.depName!}</td>
								<td class="center">${innerRespBill.jobName!}</td>
								<td class="center">${innerRespBill.impPersonName!}</td>
								<td class="center">${innerRespBill.respManagerName!}</td>
								<td class="center">${innerRespBill.managerJobName!}</td>
								<td class="center">${innerRespBill.respGeneralName!}</td>
								<td class="center">${innerRespBill.generalJobName!}</td>
							</tr>
						</#list>
					</table>
					</#if>
					<#if qualityProblem.outerList?? && (qualityProblem.outerList?size>0) >
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
							<#list qualityProblem.outerList as outerRespBill>
								<tr>
									<td class="center">${outerRespBill.agencyName!}</td>
									<td class="center">${outerRespBill.impPersonName!}</td>
									<td class="center">${outerRespBill.appealPersonName!}</td>
									<td class="center">${outerRespBill.respManagerName!}</td>
									<td class="center">${outerRespBill.managerJobName!}</td>
									<td class="center">${outerRespBill.respGeneralName!}</td>
									<td class="center">${outerRespBill.generalJobName!}</td>
								</tr>
							</#list>
						</table>
					</#if>
				</td>
			</tr>
			</#list>
		</table>
		<#-- 处罚单 -->
		<#if innerPunishList?? && (innerPunishList?size>0)>
		<table class="datatable">
			<tr>
				<td colspan="10"><div class="subTitle">处罚单</div></td>
			</tr>
			<#list innerPunishList as innerPunishBill>
				<tr>
					<td colspan="10"><div class="thirdTitle">内部处罚单${innerPunishBill_index+1}</div></td>
				</tr>
				<tr>
					<th style="width:7%">被处罚人：</th>
					<td>${innerPunishBill.punishPersonName!}</td>
					<th style="width:10%">被处罚人工号：</th>
					<td>${innerPunishBill.pubPersonId!}</td>
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
					<td colspan="3">${(innerPunishBill.economicPunish!0)?string('0.00')}元</td>
				</tr>
				<tr>
					<th>处罚依据：</th>
					<td colspan="7">
						<table class="listtable">
							<tr>
								<th style="width:15%;text-align:center;">处罚等级</th>
								<th style="text-align:center;">分级标准</th>
							</tr>
							<#list innerPunishBill.ipbList as innerPunishBasis>
								<tr>
									<td class="center" <#if innerPunishBasis.punishStandard.redLineFlag==1>style="color:red"</#if>>
										${innerPunishBasis.punishStandard.level!}
									</td>
									<td>${innerPunishBasis.punishStandard.description!}</td>
								</tr>
							</#list>
						</table>
					</td>
				</tr>
			</#list>
		</table>
		</#if>
		<#if outerPunishList??>
		<table class="datatable">
			<#list outerPunishList as outerPunishBill>
				<tr>
					<td colspan="10"><div class="thirdTitle">外部处罚单${outerPunishBill_index+1}</div></td>
				</tr>
				<tr>
					<#-- <th style="width:7%">供应商id：</th> -->
					<#-- <td>${outerPunishBill.id}</td> -->
					<th style="width:10%">供应商名称：</th>
					<td>${outerPunishBill.agencyName!}</td>
					<th style="width:7%">记分处罚：</th>
					<td>${(outerPunishBill.scorePunish!0)?c}分</td>
					<#-- <th style="width:7%">经济处罚：</th>  -->
					<#-- <td>${(outerPunishBill.economicPunish!0)?string('0.00')}元</td> -->
				</tr>
				<#if outerPunishBill.opbList??>
					<tr>
						<th>处罚依据：</th>
						<td colspan="3">
							<table class="listtable">
								<tr>
									<th style="width:15%;text-align:center;">处罚等级</th>
									<th style="text-align:center;">分级标准</th>
								</tr>
								<#list outerPunishBill.opbList as outerPunishBasis>
									<tr>
										<td class="center"  <#if outerPunishBasis.punishStandard.redLineFlag==1>style="color:red"</#if>>
											${outerPunishBasis.punishStandard.level!}
										</td>
										<td >${outerPunishBasis.punishStandard.description!}</td>
									</tr>
								</#list>
							</table>
						</td>
					</tr>
				</#if>
			</#list>
		</table>
		</#if>
		<div class="footer">
			<span><strong>质检员：</strong>${qcPerson}</span>&nbsp;&nbsp;&nbsp;&nbsp;
			<span><strong>日期：</strong>${dateTime?date}</span>
		</div>
	</div>
</body>
</html>