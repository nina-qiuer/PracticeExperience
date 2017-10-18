<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp"%>
<script type="text/javascript" src="${CONFIG.BOSS_URL}/js/util.js"></script>
<!-- icc一键呼出js -->
<%-- <script type="text/javascript" src="${CONFIG.ICC_URL}/softPhone/util.js"></script> --%>
<script type="text/javascript"
	src="${CONFIG.res_url}script/jquery/jquery.1.4.2.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}/script/jquery/jquery.base64.js"></script>
<style type="text/css">
.common-box {
	border: 1px solid #8CBFDE;
	margin: 0 0 10px 0;
}

.common-box-hd {
	margin-top: 1px;
	padding-left: 10px;
	background: #C6E3F1;
	color: #005590;
	font-size: 14px;
	font-weight: bold;
	height: 25px;
	line-height: 25px;
	border-bottom: 1px solid #8CBFDE;
	position: relative;
}

.common-box-hd span.title2 {
	cursor: pointer;
	display: inline-block;
	line-height: 25px;
	margin-right: 15px;
}

.mb10 {
	margin-bottom:0px;
}

.mr10{
	margin-bottom:10px;
	margin-right:10px;
}
</style>
<title>投诉处理单</title>
</head>
<body>
	<div class="top_crumbs">
		您当前所在的位置：<a href="#">投诉质检管理</a>&gt;&gt;<span class="top_crumbs_txt">投诉处理单</span>
	</div>
	<div class="mb10" style="
		<c:if test="${isCharger==1 || complaint.state eq 9 || view_type.equals('qcView') || isAssignDealer==0 || authFlag==false}">
			<c:if test="${complaint.state != 3&& complaint.state != 4}">display:none</c:if>
		</c:if>">
		<div style="float: left; width: 100px;
			<c:if test="${complaint.state != 3&& complaint.state != 4}">display:none;</c:if>
			<c:if test="${isCharger==1 || complaint.state eq 9 || view_type.equals('qcView') || isAssignDealer==0 || authFlag==false}">float: none;</c:if>">
			<input class="pd5 mr10" type="button" value="投诉处理报告"
				onclick="openDialog('理论赔付','complaint_point?complaintId=${complaint.id }','L')" />
		</div>
		<div style="
			<c:if test="${isCharger==1 || complaint.state eq 9 || view_type.equals('qcView') || isAssignDealer==0 || authFlag==false}">display:none</c:if>">
			<c:if test="${state==2 && chat==null}">
				<input title="发起供应商沟通" class="pd5 mr10" type="button"
					value="发起供应商沟通"
					onclick="openWinX('新增供应商沟通', 'complaint-supplierCommit?complaintId=${complaint.id }&orderId=${complaint.orderId }&createType=${complaint.createType }', 550, 450)" />
			</c:if>
			<c:if test="${state==2 && chat!=null}">
				<input title="发起供应商沟通" class="pd5 mr10" type="button"
					value="发起供应商沟通"
					onclick="openWinX('新增供应商沟通', 'complaint-supplierCommit?complaintId=${complaint.id }&orderId=${complaint.orderId }&createType=${complaint.createType }', 550, 450)"
					<c:if test="${chat.statusNum!=4}">disabled</c:if> />
			</c:if>
			<input title="填写投诉事宜" class="pd5 mr10" type="button" value="填写投诉事宜"
				onclick="openWinX('填写投诉事宜', 'complaint_reason?complaintId=${complaint.id }&orderId=${complaint.orderId}', 850, 520)" />
			<input title="填写本次跟进记录" class="pd5 mr10" type="button"
				value="填写本次跟进记录"
				onclick="openWinX('填写本次跟进记录', 'follow_up_record?complaintId=${complaint.id }&orderId=${complaint.orderId}&tel=${complaint.contactPhone}&tel1=${contact.tel}&deal=${complaint.deal}&associater=${complaint.associater}', 600, 510)" />
			<input title="设置下次跟进提醒" class="pd5 mr10" type="button"
				value="设置下次跟进提醒"
				onclick="openWinX('设置下次跟进提醒', 'follow_time?complaintId=${complaint.id }&orderId=${complaint.orderId }', 600, 310)" />
			<input title="退款申请" class="pd5 mr10" type="button" value="退款申请"
				onclick="openWinX('退款申请', 'refund_apply?complaintId=${complaint.id }', 670, 350)" />
			<input title="对客解决方案" class="pd5 mr10" type="button" value="对客解决方案"
				onclick="openDialog('对客解决方案','complaint_solution?orderId=${complaint.orderId }&complaintId=${complaint.id }&contactId=${complaint.contactId }','L')" />
			<input title="质量工具赔款" class="pd5 mr10" type="button" value="质量工具赔款"
				onclick="openDialog('质量工具赔款','${CONFIG.CSS_URL}/ind/indemnity/list?relBillType=1&relBillId=${complaint.id }&ordId=${complaint.orderId }&dealPostName='+encodeURI(encodeURI('${complaint.dealDepart }'))+'&contactName='+encodeURI(encodeURI('${complaint.cmpPerson }'))+'&contactPhone=${complaint.cmpPhone }','L')" />
			<!-- 如果是售前处理的投诉分担方案提交到售后填写 -->
			<c:if test="${canAddSoulation==1 }">
				<input title="分担方案" class="pd5 mr10" type="button" value="分担方案"
					onclick="openWinX('分担方案', 'share_solution?complaintId=${complaint.id }&orderId=${complaint.orderId }&createType=${complaint.createType }', 900, 550)">
			</c:if>
			<c:if
				test="${canAddSoulation==0 && (complaintSoulution.cash + complaintSoulution.touristBook) > 0}">
				<input title="提交售后填写分担方案" class="pd5 mr10" type="button"
					value="提交售后填写分担方案"
					onClick="javascript:if(confirm('确定提交售后填写分担方案？')){location.href='complaint-shareSolutionUpgrade?complaintId=${complaint.id }&id=${complaint.id }'}">
			</c:if>
			<input title="上传附件" class="pd5 mr10" type="button" value="上传附件"
				onclick="openWinX('上传附件', 'complaint-queryUpload?complaintId=${complaint.id }&type=act', 670, 520)" />
			<c:if test="${qcPoint==null}">
				<input title="填写质检点" class="pd5 mr10" type="button" value="填写质检点"
					onclick="openWinX('填写质检点', 'complaint-qcPoint?complaintId=${complaint.id}', 850, 520)" />
			</c:if>
			<c:if test="${qcPoint!=null}">
				<input title="修改质检点" class="pd5 mr10" type="button" value="修改质检点"
					onclick="openWinX('填写质检点', 'complaint-qcPoint?complaintId=${complaint.id}', 850, 520)" />
			</c:if>
			<c:if test="${qc.status==1 && deptzj==1}">
				<input title="一键关闭质检" class="pd5 mr10" type="button" id="closeQcBtn"
					value="一键关闭质检" onclick="oneKeyClose(${complaint.id})">
			</c:if>
			<c:if test="${reportFlag == 0}">
				<input title="查看质检报告" class="pd5 mr10" type="button" value="查看质检报告"
					onclick="openWinX('查看质检报告', 'qc-view?id=${qc.id}', 850, 520)" />
			</c:if>
			<c:if test="${reportFlag == 1}">
				<input title="查看质检报告" class="pd5 mr10" type="button" value="查看质检报告"
					onclick="openWinX('质检备忘列表', 'http://qms.tuniu.org/qc/qcBill/${complaint.id}/getReport', 900, 520)" />
			</c:if>
			<input title="填写改进报告" class="pd5 mr10" type="button" value="填写改进报告"
				onclick="openWinX('填写改进报告', 'complaint-improveBill?complaintId=${complaint.id}', 750, 520)"/>
			<input title="查看改进报告" class="pd5 mr10" type="button" value="查看改进报告"
				onclick="openWinX('查看改进报告', 'http://qms.tuniu.org/qs/cmpImprove/${complaint.id}/reportWithCmpId', 850, 520)"/>
			<input title="修改投诉来源" class="pd5 mr10" type="button" value="修改投诉来源"
				<c:if test="${complaint.state!=2 && complaint.state!=3}">disabled</c:if>
				onclick="openWinX('修改投诉来源', 'complaint-changeComplaintComeFrom?id=${complaint.id}', 400, 200)" />
			<c:if test="${complaint.state == 2 && isCtOfficer}">
				<input title="转至已待结" class="pd5 mr10" type="button" value="转至已待结"
					onClick="changeState('${complaint.id}', '${complaint.state}')" />
			</c:if>
			<c:if
				test="${(complaint.state == 3 || complaint.state == 4) && isCtOfficer && (complaintSoulution.cash + complaintSoulution.touristBook + complaintSoulution.gift) == 0}">
				<input title="返回处理中" class="pd5 mr10" type="button" value="返回处理中"
					onClick="changeState('${complaint.id}', '${complaint.state}')" />
			</c:if>
			<c:if test="${isReturnVisitSwitchOpened != null }">
				<input id="closeReturnVisitButton" class="pd5 mr10" type="button"
					value="关闭回访" style="display: none"
					onclick="changeReturnVisitSwitch('${complaint.id}',0)" />
				<input id="openReturnVisitButton" class="pd5 mr10" type="button"
					value="打开回访" style="display: none"
					onclick="changeReturnVisitSwitch('${complaint.id}',1)" />
			</c:if>
			<c:if
				test="${(complaint.contactId>0&&contact.tel!='')||(complaint.contactId==0&&complaint.contactPerson!='')}">
				<input title="通话记录" class="pd5 mr10" type="button" value="通话记录"
					onclick="openDialog('通话记录','complaint-queryCallRecords?id=${complaint.id }&cmpPhone=${complaint.cmpPhone}')" />
			</c:if>

			<input title="处理优先级" class="pd5 mr10" type="button" value="处理优先级"
				onclick="openWinX('处理优先级', 'complaint-getPriority?complaintId=${complaint.id}', 550, 300)" />
			<input title="点评修改申请" class="pd5 mr10" type="button" value="点评修改申请"
				onclick="openWinX('点评修改申请', 'complaint-getReviewApply?complaintId=${complaint.id}', 550, 300)" />
				
		</div>
	</div>

	<c:if test="${isAssignDealer==0}">
		<div class="mb10" align="center"
			<c:if test="${isCharger==1}">style="display:none;"</c:if>>
			<font size="10" color="red">此投诉单未分配处理人，无法操作</font>
		</div>
	</c:if>

	<div class="common-box">
		<div class="common-box-hd">
			<span class="title2">投诉情况说明单 ${complaint.id } <c:if
					test="${complaint.isReparations==1 }">(赔款单)</c:if>
					 <span style="color:red;"><c:if test="${haveThirdPart==true }">&nbsp;&nbsp;(投诉升级至第三方)</c:if></span>
			</span>
		</div>
		<table width="100%" class="bgnavy">
			<tr>
				<td class="pl10">订单状态：<span class="cred">${complaint.orderState}</span></td>
				<td class="pl10">处理岗：<span class="cred">${complaint.dealDepart}</span></td>
				<td>订单类型：<span class="cred">${complaint.orderType }</span></td>
				<td>线路类型：<span class="cred">${complaint.routeType }</span></td>
				<td>订单来源：<span class="cred">${request.complaint.orderComeFrom}</span></td>
				<td>当前投诉处理状态：<span class="cred"><c:if
							test="${complaint.state eq 1}">投诉待指定</c:if> <c:if
							test="${complaint.state eq 2}">投诉处理中</c:if> <c:if
							test="${complaint.state eq 3}">投诉已待结</c:if> <c:if
							test="${complaint.state eq 4}">投诉已完成</c:if> <c:if
							test="${complaint.state eq 5}">升级到售后</c:if> <c:if
							test="${complaint.state eq 6}">提交售后填写分担方案</c:if> <c:if
							test="${complaint.state eq 9}">投诉撤消</c:if></span></td>
				<c:if test="${complaint.state eq 10}">升级到客服中心售后</c:if>
				</span>
				</td>
			</tr>
		</table>
		<table width="100%" class="datatable">
			<tr>
				<th width="100" align="right">投诉来源：</th>
				<td>${complaint.comeFrom}<c:if test="${complaint.isMedia == 1}">
						<span style="color: blue;">（媒体参与）</span>
					</c:if><c:if test="${upgradeThirdPart==true}">&nbsp;&nbsp;
					<input title="投诉升级第三方" class="pd5 mr10 blue" type="button" value='投诉升级第三方' 
						id="upgrade_third_part" complaint_id="${complaint.id }"/>
					</c:if>
				</td>
				<th width="100" align="right">投诉级别：</th>
				<td>${complaint.level}级<c:if
						test="${(isCtOfficer == true||depflag==1) && complaint.state != 4}">&nbsp;&nbsp;
					<input title="修改等级" class="pd5 mr10 blue" type="button"
							value="修改等级"
							onclick="openWinX('修改等级', 'complaint-changeComplaintLevl?complaintId=${complaint.id }&complaintLevl=${complaint.level }', 500, 400)" />
					</c:if>
				</td>
				<th width="100" align="right">会员等级：</th>
				<td><font
					<c:if test="${complaint.guestLevel=='白金会员' || complaint.guestLevel=='钻石会员' || complaint.guestLevel=='五星会员'}">color='red'</c:if>>${complaint.guestLevel}</font>
					（<c:if test="${complaint.custId!=null && complaint.custId!=''}">
					<a href="javascript:void(0)" onclick="openCmpCustView(${user.id},${complaint.custId})">会员视图</a>
					</c:if>）
				</td>
			</tr>
			<tr>
				<th align="right">投诉人：</th>
				<td>${complaint.cmpPerson}</td>
				<th align="right">投诉人电话：</th>
				<td><a href="javascript:callDispatch('${complaint.cmpPhone}')"
					title="点击直呼">${complaint.cmpPhone}</a></td>
				<th align="right">特殊会员：</th>
				<td>
					<span style="color:red;">
					<c:if test="${whiteResult!= null}">${whiteResult }&nbsp;&nbsp;&nbsp;订单中含白名单人员，请严格按照标准接待，确保服务温度和速度。</c:if>
					<c:if test="${blackResult!= null}">${blackResult }&nbsp;&nbsp;&nbsp;${blackName }为我司标注的 ${blackResultType }，可以接待，请注意服务规范。</c:if>
					</span>
				</td>
			</tr>
			<tr>
				<th align="right">订单号：</th>
				<td><c:if test="${complaint.orderId > 0}">
						<a href="javascript:void(0)" onclick="showOrder(${user.id},'${user.realName}',${complaint.orderId})">${complaint.orderId}</a>

					</c:if></td>
				<th align="right">出发地：</th>
				<td>${complaint.startCity}</td>
				<th width="100" align="right">线路：</th>
				<td><c:if test="${complaint.routeId > 0}">${complaint.routeId}&nbsp;
					<c:if test="${complaint.niuLineFlag > 0}">
							<img src="${CONFIG.res_url}images/icon/default/niurenLine.gif"
								style="vertical-align: middle;">
						</c:if>
					</c:if></td>
			</tr>
			<tr>
				<th align="right">客户姓名：</th>
				<td><a
					href="http://crm.tuniu.com/crm/custInfo/cust-info!showInfoForSale.action?customerDto.id=${complaint.custId}"
					target="_blank">${complaint.guestName}</a></td>
				<th align="right">出游人数：</th>
				<td colspan="">${complaint.guestNum}</td>
				<th align="right">线路名称：</th>
				<td colspan="">${complaint.route}</td>
			</tr>
			<tr>
				<th align="right">联系人：</th>
				<td>${complaint.contactPerson}</td>
				<th align="right">联系人手机：</th>
				<td><a
					href="javascript:callDispatch('${complaint.contactPhone}')"
					title="点击直呼">${complaint.contactPhone}</a></td>
				<th width="100" align="right">联系人邮箱：</th>
				<td>${complaint.contactMail}</td>
			</tr>
			<tr>
				<th align="right">售前客服：</th>
				<td>${complaint.customer}</td>
				<th align="right">客服经理：</th>
				<td>${complaint.customerLeader}</td>
				<th align="right">高级客服经理：</th>
				<td>${complaint.serviceManager}</td>
			</tr>
			<tr>
				<th align="right">产品专员：</th>
				<td>${complaint.producter}</td>
				<th align="right">产品经理：</th>
				<td>${complaint.productLeader}</td>

				<th align="right">高级产品经理：</th>
				<td>${complaint.seniorManager}</td>
			</tr>
			<tr>
				<th align="right">导游编号：</th>
				<td>${complaint.guideId}</td>
				<th align="right">导游姓名：</th>
				<td>${complaint.guideName}</td>
				<th align="right">导游电话：</th>
				<td>${complaint.guideCall}</td>
			</tr>
			<tr>
				<th align="right">运营专员：</th>
				<td>${complaint.operateName}</td>
				<th align="right">运营经理：</th>
				<td colspan="3">${complaint.operateManagerName}</td>
			</tr>
			<tr>
				<th align="right">投诉说明：</th>
				<td colspan="5">${complaint.descript}</td>
			</tr>
			<tr>
				<th align="right">客服要求：</th>
				<td colspan="5">${complaint.requirement}</td>
			</tr>
			<tr>
				<th align="right">发起人：</th>
				<td>${complaint.ownerName}</td>
				<th>日期：</th>
				<td colspan="3"><fmt:formatDate value="${complaint.buildDate}"
						pattern="yyyy-MM-dd" /></td>
			</tr>
		</table>
	</div>
	<!-- 待回呼页面 -->
	<c:if test="${taskList != null && taskList.size() > 0}">
		<div class="common-box">
			<div class="common-box-hd">
				<span class="title2">任务提醒列表</span>
			</div>
			<table width="100%" class="listtable" style="TABLE-LAYOUT: fixed;">
				<tr align="center">
					<th>状态</th>
					<th>要求回呼时间</th>
					<th>投诉人姓名</th>
					<th>投诉人电话</th>
					<th>备注</th>
				</tr>
				<c:forEach items="${taskList}" var="v">
					<tr align="center">
						<c:if test="${v.cbState==1}">
							<td>待回呼</td>
						</c:if>
						<c:if test="${v.fcState==1}">
							<td>待首呼</td>
						</c:if>
						<td>${v.callBackTime }</td>
						<td>${v.cmpPerson }</td>
						<td><a href="javascript:callDispatch('${v.cmpPhone }')"
							title="点击直呼">${v.cmpPhone }</a></td>
						<td>${v.content }</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</c:if>
	<!-- 供应商沟通页面 -->
	<c:if test="${chatList != null && chatList.size() > 0}">
		<div class="common-box">
			<div class="common-box-hd">
				<span class="title2">供应商沟通</span>
			</div>
			<table width="100%" class="listtable" style="TABLE-LAYOUT: fixed;">
				<tr align="center">
					<th>会话ID</th>
					<th>沟通状态</th>
					<th>沟通类型</th>
					<th>供应商</th>
					<th>沟通时间</th>
					<th>沟通内容</th>
					<th>投诉单号</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${chatList}" var="v">
					<tr align="center">
						<td>${v.roomId }</td>
						<td>${v.statusName }</td>
						<td>${v.typeName }</td>
						<td>${v.agencyName }</td>
						<td>${v.commitTime }</td>
						<c:if test="${v.contentType==0}">
							<td>${v.descript }</td>
						</c:if>
						<c:if test="${v.contentType==1}">
							<td><a href="${v.descript}">${v.pictName}</a></td>
						</c:if>
						<td>${v.complaintId }</td>
						<td><input title="查看" type="button" value="查看"
							onclick="openWinX('供应商沟通列表', 'complaint-commitList?roomId=${v.roomId}&complaintId=${v.complaintId}&state=${state}', 850, 450)" />
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</c:if>

	<c:if test="${payInfoList != null && payInfoList.size() > 0}">
		<div class="common-box">
			<div class="common-box-hd">
				<c:if test="${complaint.orderId > 0}">
					<span class="title2">订单相关赔付信息</span>
				</c:if>
				<c:if test="${complaint.orderId == 0}">
					<span class="title2">电话相关赔付信息</span>
				</c:if>
			</div>
			<table width="100%" class="listtable" style="TABLE-LAYOUT: fixed;">
				<tr align="center">
					<th width="120">投诉单号</th>
					<th width="120">处理人</th>
					<th>现金（元）</th>
					<th>旅游券（元）</th>
					<th>抵用券（元）</th>
					<th>礼品（元）</th>
					<th>分担总额（元）</th>
				</tr>
				<c:forEach items="${payInfoList }" var="v">
					<tr align="center">
						<td><c:if test="${complaint.id == v.complaint_id}">
					${v.complaint_id}
				</c:if> <c:if test="${complaint.id != v.complaint_id}">
								<a href="complaint-toBill?id=${v.complaint_id}" target="_blank"
									id="td_${v.complaint_id}">${v.complaint_id}</a>
							</c:if></td>
						<td>${v.deal_name }</td>
						<td>${v.cash }</td>
						<td>${v.tourist_book }</td>
						<td>${v.replace_book }</td>
						<td>${v.gift }</td>
						<td>${v.shareTotal}</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</c:if>

	<c:if
		test="${complaint.orderId > 0 && sgOrderList != null && sgOrderList.size() > 1}">
		<div class="common-box">
			<div class="common-box-hd">
				<span class="title2" onclick="javascript:$('#sgOrderListTb').toggle()">同团期订单投诉情况</span>
			</div>
			<table id="sgOrderListTb" width="100%" class="listtable" style="TABLE-LAYOUT: fixed;">
				<tr align="center">
					<c:forEach items="${sgOrderList }" var="sgoMap" varStatus="st">
						<c:if test="${sgoMap.cmpFlag == 0}">
							<td>${sgoMap.orderId }</td>
						</c:if>
						<c:if test="${sgoMap.cmpFlag == 1}">
							<td><a href="javascript:void(0);"
								title="订单[${sgoMap.orderId }]投诉详情"
								onclick="openDialog('订单[${sgoMap.orderId }]投诉详情','complaint-getCompDetail?orderId=${sgoMap.orderId }')">${sgoMap.orderId
									}</a></td>
						</c:if>
						<c:if test="${st.count % 10 == 0}">
				</tr>
				<tr align="center">
					</c:if>
					</c:forEach>
				</tr>
			</table>
		</div>
	</c:if>

	<div class="common-box">
		<div class="common-box-hd">
			<span class="title2"
				onclick="javascript:$('#agencyContacts').toggle()">供应商列表</span>
		</div>
		<table width="100%" id="agencyContacts" class="listtable"
			style="TABLE-LAYOUT: fixed; display: none">

		</table>
	</div>

	<div class="common-box">
		<div class="common-box-hd">
			<span class="title2">跟进记录</span>
		</div>
		<table width="100%" class="listtable" style="TABLE-LAYOUT: fixed;">
			<tr align="center">
				<td width="130">跟进时间</td>
				<td width="60">处理人</td>
				<td align="left" style="WORD-WRAP: break-word;">跟进内容</td>
			</tr>
		</table>
		<div>
			<c:forEach items="${recordList }" var="v">
				<table width="100%" class="listtable" style="TABLE-LAYOUT: fixed;">
					<tr align="center">
						<td width="130"><fmt:formatDate value="${v.addTime}"
								pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td width="60">${v.addUserName}</td>
						<td align="left" style="WORD-WRAP: break-word;"
							<c:if test="${v.addUser == user.id}">class='content'</c:if>
							id="content_${v.id}">${v.note}</td>
					</tr>
				</table>
			</c:forEach>
		</div>
	</div>

	<div class="common-box">
		<div class="common-box-hd">
			<span class="title2">业务操作日志</span>
			<!-- <input type="checkbox" id="onlyRecord" onclick="onlyRecord();"/>(勾选只查看跟进记录) -->
		</div>
		<table width="100%" class="listtable" style="TABLE-LAYOUT: fixed;">
			<tr align="center">
				<td width="130">添加时间</td>
				<td width="60">操作人</td>
				<td width="60">所属岗位</td>
				<td width="130">事件</td>
				<td align="left" style="WORD-WRAP: break-word;">备注</td>
			</tr>
		</table>
		<div id="follow_note">
			<c:forEach items="${request.follow_note_list }" var="v">
				<table width="100%" class="listtable" style="TABLE-LAYOUT: fixed;">
					<tr align="center">
						<td width="130"><fmt:formatDate value="${v.addTime}"
								pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td width="60">${v.peopleName}</td>
						<td width="60">${v.dealDept}</td>
						<td width="130"
							<c:if test="${v.flowName eq '首呼'}">class="cred"</c:if>>${v.flowName}</td>
						<td align="left" style="WORD-WRAP: break-word;">${v.content}</td>
					</tr>
				</table>
			</c:forEach>
		</div>
	</div>

	<div class="common-box">
		<div class="common-box-hd">
			<span class="title2">投诉事宜及质量成本类型</span>
		</div>
		<table width="100%" class="datatable">
			<tr>
				<th align="center" colspan="4">投诉事宜</th>
				<th align="center" colspan="4">质量成本类型</th>
				<c:if test="${deptzj==1}">
					<th align="center" colspan="2"></th>
				</c:if>
			</tr>
			<tr>
				<th width="75">添加时间</th>
				<th width="110">投诉事宜分类</th>
				<th>投诉详情</th>
				<th>备注</th>
				<th width="110">供应商问题</th>
				<th width="110">我司问题</th>
				<th width="110">成本类型</th>
				<th width="46">其他</th>
				<c:if test="${deptzj==1}">
					<th width="120"></th>
				</c:if>
			</tr>
			<c:forEach items="${complaint_reason }" var="v">
				<tr>
					<td align="center"><fmt:formatDate value="${v.addTime}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td align="center">${v.type }-${v.secondType }</td>
					<td align="left">${v.typeDescript}</td>
					<td align="left" style="color: red">${v.descript }</td>
					<td align="center"">${v.agencyProblem }</td>
					<td align="center">${v.companyProblem }</td>
					<td align="center">${v.qualityTool }</td>
					<td align="center">${v.others }</td>
					<c:if test="${deptzj==1}">
						<td align="left" id="accuracyTD${v.id}" style="display: none">是否正确:
							<select id="accuracy${v.id}">
								<option value="1">正确</option>
								<option value="2">错误</option>
						</select> <br>备注:<input type="text" id="accuracyReMsg${v.id}" /> <input
							type="button" value="提交" class="blue"
							onclick="updateAccuracy(${v.id});" />
						</td>
						<td align="left" id="accuracyMsg${v.id}"><c:if
								test="${v.accuracy==0}">问题分类判断</c:if> <c:if
								test="${v.accuracy==1}">当前状态：<font color="green">正确</font>
							</c:if> <c:if test="${v.accuracy==2}">当前状态：<font color="red">错误</font>
							</c:if><br>备注:<span title="${v.accuracyRe}">${v.accuracyRe}</span><input
							style="margin-left: 10px" type="button" value="编辑" class="blue"
							onclick="editAccuracy(${v.id});" /></td>
					</c:if>
				</tr>
			</c:forEach>
		</table>
	</div>


	<div class="common-box">
		<div class="common-box-hd">
			<span class="title2">对客解决方案</span>
		</div>

		<table width="100%" class="datatable">
			<tr>
				<th width="100" align="right">赔偿现金：</th>
				<td>${complaintSoulution.cash }</td>
				<th width="100" align="right">赔偿抵用券：</th>
				<td>${complaintSoulution.replaceBook }</td>
				<th width="100" align="right">赔偿旅游券：</th>
				<td>${complaintSoulution.touristBook }</td>
				<th width="100" align="right">礼品:</th>
				<td>${complaintSoulution.gift }</td>
			</tr>
			<tr>
				<th width="100" align="right">备注:</th>
				<td colspan="7">${complaintSoulution.descript}</td>
			</tr>
		</table>
	</div>
	<div class="common-box">
		<div class="common-box-hd">
			<span class="title2">分担方案</span>
		</div>

		<table width="100%" class="datatable">
			<tr>
				<th width="150" align="right">对客人赔偿总额：</th>
				<td colspan="11">${shareSolutionEntity.total }</td>
			</tr>
			<tr>
				<th width="150" align="right">供应商承担赔偿金额：</th>
				<td>${shareSolutionEntity.supplierTotal }</td>
				<th width="150" align="right">订单利润承担赔偿金额：</th>
				<td>${shareSolutionEntity.orderGains }</td>
				<th width="150" align="right">员工承担赔偿金额：</th>
				<td>${shareSolutionEntity.employeeTotal }</td>
				<th width="150" align="right">公司承担：</th>
				<td>${shareSolutionEntity.special }</td>
				<th width="150" align="right">成本类型：</th>
				<td>${shareSolutionEntity.qualityToolTotal }</td>
				<th width="150" align="right">退转赔：</th>
				<td>${shareSolutionEntity.refundToIndemnity }</td>
			</tr>
		</table>
	</div>
	<input type="hidden" id="buttonTitle" value="" />
	<input type="hidden" name="orderId" id="orderId"
		value="${complaint.orderId}" />

	<script type="text/javascript">
$(function(){
	/*移除thickbox默认样式*/
	$('.thickbox').click(function(){
		$('#TB_closeAjaxWindow').html("<a href='javascript:void(0)' id='TB_closeWindowButton' title='Close'>×</a>");
		$('#TB_closeWindowButton').live('click',function(){
			tb_remove();
		});
	});

	$(".content").dblclick(function() { // 注册双击事件
	    var $td = $(this);
	    // 检测此td是否已经被替换了，如果被替换直接返回
	    if ($td.children("input").length > 0) {
	        return false;
	    }
	    var text = $td.text();
	    var $textarea = $("<textarea style='height: 19px; font-size: 10pt' rows='1' cols='100'></textarea>");
	    $textarea.text(text);
	    $td.html("");
	    $td.append($textarea);
	    $textarea.focus();

	    $textarea.click(function(event) {
			event.stopPropagation();
		});

	    $textarea.blur(function() { // 鼠标点出即提交，将td中的内容修改成获取的value值
	    	var value = $textarea.val();
	    	if (value != text) {
	    		var complaintId = "${complaint.id}";
		    	var idStr = $td.attr("id");
		    	var id = idStr.substring(8, idStr.length);
		    	$.ajax({
		    		type: "POST",
		    		url: "complaint-updateFollowUpRecord",
		    		data: {"id":id, "content":value, "complaintId":complaintId},
		    		async: false,
		    		success: function(data) {
		    			$td.html(value);
		    		} 
		    	});
	    	} else {
	    		$td.html(value);
	    	}
	    });
	    
	    $textarea.keyup(function(event) {
	        if (event.which == 27) { // 按ESC，将td中的内容还原
	        	$td.html(text);
	        }
	    });
	});
	
	//控制回访开关可见性
	var isReturnVisitSwitchOpened = '${isReturnVisitSwitchOpened}';
	if(isReturnVisitSwitchOpened!=''){
		if(isReturnVisitSwitchOpened=='true') {
			$('#closeReturnVisitButton').show();
		}else{
			$('#openReturnVisitButton').show();
		}
	}
	
	
	buildAgencyContactsList();
	
	$("#upgrade_third_part").unbind("click").bind("click",function(){
		layer.open({
	        type: 2,
	        shade: false,
	        maxmin: true,
	        end : refreshWindow,
	        title: "投诉升级第三方",
	        content: 'upgrade_third_part?complaintId='+$(this).attr("complaint_id"),
	        area: ['400px', '250px']
	    });
	})
});

var refreshWindow = function (){
	window.location.reload();
}

function updateAccuracy(id){
	var accuracy = $('#accuracy'+id).val();
	var accuracyReMsg = $('#accuracyReMsg'+id).val();
	if(accuracyReMsg.length>64){
		alert("备注不能超过64个字");
		return false;
	}
	var param = {"reasonId":id,"accuracy":accuracy,"accuracyRe":accuracyReMsg};
	$.ajax({
	type: "POST",
	async:false,
	url: "complaint-checkAccuracy",
	data: param,
	success: function(data){
		if(accuracy==1){
			$('#accuracyMsg'+id).html('当前状态：<font color="green">正确</font><br>备注:<span>'+accuracyReMsg+'</span><input style="margin-left: 10px" type="button" value="编辑" class="blue" onclick="editAccuracy('+id+');"/>');
			$('#accuracyTD'+id).hide();
			$('#accuracyMsg'+id).show();
		}
		if(accuracy==2){
			$('#accuracyMsg'+id).html('当前状态：<font color="red">错误</font><br>备注:<span>'+accuracyReMsg+'</span><input style="margin-left: 10px" type="button" value="编辑" class="blue" onclick="editAccuracy('+id+');"/>');
			$('#accuracyTD'+id).hide();
			$('#accuracyMsg'+id).show();
		}
     }
   });
}

function editAccuracy(id){
	$('#accuracyTD'+id).show();
	$('#accuracyMsg'+id).hide();
}

function oneKeyClose(id) {
	if (confirm('确定关闭质检?')) {
		var param = {"id":id};
		$.ajax({
			type: "POST",
			async:false,
			url: "qc-doClose",
			data: param,
			success: function(data) {
				//$("#closeQcBtn").hide();
				location.href="complaint-toBill?id=" + id;
		    }
	   });
	}
}

function changeState(id, state) {
	var cfmInfo = "";
	if (2 == state) {
		cfmInfo = "确定将此单转至已待结？";
	}
	if (3 == state || 4 == state) {
		cfmInfo = "确定将此单转至处理中？";
	}
	if (confirm(cfmInfo)) {
		var param = {"id":id};
		$.ajax({
			type: "POST",
			async:false,
			url: "complaint-changeState",
			data: param,
			success: function(data) {
				location.href="complaint-toBill?id=" + id;
		    }
	   });
	}
}

function changeReturnVisitSwitch(complaintId,switchState){
	$.ajax({
		type: "POST",
		url: "complaint-changeReturnVisitSwitch",
		data: {'complaintId':complaintId,'switchState':switchState},
		success: function(data) {
			alert('操作成功');
			if(switchState==0){
				$('#openReturnVisitButton').show();
				$('#closeReturnVisitButton').hide();
			}else{
				$('#closeReturnVisitButton').show();
				$('#openReturnVisitButton').hide();
			}
	    }
   });
}

function reflsh(id){
	location.href="complaint-toBill?id=" + id;
}

function callDispatch(phoneNum) {
	CallDispatch(phoneNum, '0', 'call_dispatch');
	//callOut(phoneNum); //icc一键呼出
	
	openWinX('填写本次跟进记录', 'follow_up_record?complaintId=${complaint.id }&orderId=${complaint.orderId}&tel=${complaint.contactPhone}&tel1=${contact.tel}&deal=${complaint.deal}&associater=${complaint.associater}', 600, 450);
}

function buildAgencyContactsList(){
	var agencyContacts = ${agencyContactArray};
	//供应商列表table
	//$('#agencyContacts')
	var agencyDetail;
	var agencyName;
	var contacts;
	var tr;
	var td1;
	var td2;
	for(key in agencyContacts){
		agencyDetail = agencyContacts[key];
		agencyName = agencyDetail.agencyName;
		
		tr = $('<tr/>').appendTo($('#agencyContacts'));
		td1 = $('<td/>',{
			text:agencyName
		}).css('width','40%').appendTo(tr);
		
		td2 = $('<td/>').appendTo(tr);
		
		contacts = agencyDetail.contactDetail;
		var contactItem;
		var p;
		var contactItemIds = new Array(); //根据联系人id去重
		for(key2 in contacts){
			var contact = contacts[key2];
			label:for(key3 in contact){
				contactItem = contact[key3];
				
				for(key4 in contactItemIds){
					if(contactItem.id==contactItemIds[key4]){
						continue label;
					}
				}
				contactItemIds.push(contactItem.id);
				
				p = $('<p/>',{
					text:contactItem.position+'：'+contactItem.name+'('+(contactItem.sex=='0'?'女':'男')+')    '
				}).appendTo(td2);
				
				$('<a/>',{
					href:'javascript:callDispatch('+contactItem.mobileNum+')',
					title:'点击直呼',
					text:contactItem.mobileNum
				}).appendTo(p);
			}
		}
		
	}
}

function openQcToolPay(){
	
}

function openCmpCustView(salerId,custId){
	var url = "http://crm.tuniu.org/nebula-sun/dispatcher/index.htm?crmfrom=search&card=&mobile=";
	url +="&custid=" +custId;
	url +="&salerid=" +salerId;
	openWindow(url);
}
</script>
</body>
