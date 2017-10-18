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
		        <th style="width:8%">订单号：</th>
				<td>${qcBill.ordId}</td>
				<th>团期：</th>
				<td>${qcBill.groupDate}</td>
				<th>产品名称：</th>
				<td>${product.prdName}</td>
				<th>产品经理：</th>
				<td>${orderBill.prdManager}</td>
	</tr>
	<tr>
				<th>产品编号：</th>
				<td>${qcBill.prdId}</td>
				<th>产品品牌：</th>
				<td>${product.brandName}</td>
				<th>出发地：</th>
				<td>${orderBill.departCity}</td>
				<th>产品线目的地：</th>
				<td >${product.prdLineDestName}</td>
	</tr>
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
				<div class="thirdTitle">操作问题-质量问题${st.index+1}
				</div></td>
			</tr>
			<tr>
				<th>问题类型：</th>
				<td colspan="9">${qualityProblem.qptName}</td>
			</tr>
			<tr>
				<th>异常原因：</th>
				<td colspan="9">${qualityProblem.description}</td>
			</tr>
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
								</tr>
								<c:forEach items="${ qualityProblem.innerList}" var="innerRespBill" >
									<tr>
										<td class="center">${innerRespBill.respPersonName}</td>
										<td>${innerRespBill.depName}</td>
										<td class="center">${innerRespBill.jobName}</td>
										<td class="center">${innerRespBill.impPersonName}</td>
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
				<tr>
					<th>处罚依据：</th>
					<td colspan="5">
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
