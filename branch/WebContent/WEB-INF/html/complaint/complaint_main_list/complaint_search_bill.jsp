<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<link type="text/css" href="${CONFIG.res_url}css/bui.css" rel="stylesheet" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${CONFIG.res_url}css/easydialog.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${CONFIG.res_url}/script/easydialog.min.js"></script>
<style type="text/css">
.common-box {
	border: 1px solid #8CBFDE;
	margin:0 0 10px 0;
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
</style>
<title>投诉处理单</title>
</head>
<body>
	<div class="top_crumbs">
		您当前所在的位置：<a href="#">投诉质检管理</a>&gt;&gt;<span class="top_crumbs_txt">投诉处理单</span>
	</div>
<c:if test="${currUserId==complaint.deal || currUserId==complaint.associater }">
	<div class="mb10" <c:if test="${isCharger==1||complaint.state eq 9}">style="display:none;"</c:if> <c:if test="${view_type.equals('qcView')}">style="display:none;"</c:if> <c:if test="${isAssignDealer==0}">style="display:none;"</c:if>>
		<input title="填写投诉事宜" class="pd5 mr10" type="button" value="填写投诉事宜" onclick="easyDialog.open({container : 'reasonBox', overlay : false})"/>
		<input title="发起核实请求" class="pd5 mr10" type="button" value="发起核实请求" onclick="easyDialog.open({container : 'checkEmailBox', overlay : false})"/>
		<input title="填写本次跟进记录" class="pd5 mr10" type="button" value="填写本次跟进记录" onclick="easyDialog.open({container : 'followUpRecordBox', overlay : false})"/>
		<input title="设置下次跟进提醒" class="pd5 mr10" type="button" value="设置下次跟进提醒" onclick="easyDialog.open({container : 'followTimeBox', overlay : false})"/>
		<input title="对客解决方案" class="pd5 mr10" type="button" value="对客解决方案" onclick="easyDialog.open({container : 'solutionBox', overlay : false})"/>
	<!-- 如果是售前处理的投诉分担方案提交到售后填写 -->
	<c:if test="${canAddSoulation==1 }">
		<input title="填写分担方案" class="pd5 mr10" type="button" value="填写分担方案" <c:if test="${buttonNotDisable==0}">disabled</c:if> onclick="easyDialog.open({container : 'shareSolutionBox', overlay : false})"/>
	</c:if>
	<c:if test="${canAddSoulation==0 }">
		<input title="提交售后填写分担方案" class="pd5 mr10" type="button" value="提交售后填写分担方案" 
		onClick="javascript:if(confirm('确定提交售后填写分担方案？')){location.href='complaint-shareSolutionUpgrade?complaintId=${complaint.id }&id=${complaint.id }' }" <c:if test="${complaint.state==4}">disabled</c:if> <c:if test="${buttonNotDisable==0}">disabled</c:if> />
	</c:if>
		<input title="申请礼品" class="pd5 mr10" type="button" value="申请礼品" onclick="easyDialog.open({container : 'giftNoteBox', overlay : false})"/>
		<input title="查看质检报告" class="pd5 mr10" type="button" value="查看质检报告" <c:if test="${qc.status!=2}">disabled</c:if> onclick="easyDialog.open({container : 'qcViewBox', overlay : false})"/>
		<c:if test="${qc.status==1}">
		<input title="一键关闭质检" class="pd5 mr10" type="button" value="一键关闭质检" onclick="javascript:if(confirm('确定关闭质检?')){location.href='qc-doClose?id=${complaint.id}'}">
		</c:if>
		<input title="修改投诉来源" class="pd5 mr10" type="button" value="修改投诉来源" <c:if test="${complaint.state!=2 && complaint.state!=3}">disabled</c:if> onclick="easyDialog.open({container : 'chgComeFromBox', overlay : false})"/>
	</div>
</c:if>
	
	<div class="common-box">
		<div class="common-box-hd">
			<span class="title2">投诉情况说明单</span>
		</div>
		<table width="100%" class="bgnavy">
			<tr>
				<td class="pl10">订单状态：<span class="cred">${complaint.orderState}</span></td>
				<td class="pl10">处理岗：<span class="cred">${complaint.dealDepart}</span></td>
				<td>订单类型：<span class="cred">${complaint.orderType }</span></td>
				<td>线路类型：<span class="cred">${complaint.routeType }</span></td>
				<td>订单来源：<span class="cred">${request.complaint.orderComeFrom}</span></td>
				<td>当前投诉处理状态：<span class="cred"><c:if test="${complaint.state eq 1}">投诉待指定</c:if>
				<c:if test="${complaint.state eq 2}">投诉处理中</c:if>
				<c:if test="${complaint.state eq 3}">投诉已待结</c:if>
				<c:if test="${complaint.state eq 4}">投诉已完成</c:if>
				<c:if test="${complaint.state eq 5}">升级到售后</c:if>
				<c:if test="${complaint.state eq 6}">提交售后填写分担方案</c:if>
				<c:if test="${complaint.state eq 9}">投诉撤消</c:if></span></td>
				<c:if test="${complaint.state eq 10}">升级到客服中心售后</c:if></span></td>
			</tr>
		</table>
		<table width="100%" class="datatable">
			<tr>
				<th width="100" align="right">投诉来源：</th>
				<td>${complaint.comeFrom}
					<c:if test="${complaint.isMedia == 1}"><span style="color: blue;">（媒体参与）</span></c:if>
				</td>
				<th width="100" align="right">投诉级别：</th>
				<td>${complaint.level}级</td>
				<th width="100" align="right">会员等级：</th>
				<td>${complaint.guestLevel}</td>
			</tr>
			<tr>
				<th align="right">订单号：</th>
				<td>
				<c:if test="${complaint.orderId > 0}">
				<a href="#" onclick="showOrder(${user.id},'${user.realName}',${complaint.orderId})">${complaint.orderId}</a>
				</c:if>
				</td>
				<th align="right">出发地：</th>

				<td>${complaint.startCity}</td>
				<th width="100" align="right">线路：</th>
				<td>
				<c:if test="${complaint.routeId > 0}">
				${complaint.routeId}
				</c:if>
				</td>
			</tr>
			<tr>
				<th align="right">客户姓名：</th>
				<td>${complaint.guestName}</td>
				<th align="right">人数：</th>
				<td colspan="">${complaint.guestNum}</td>
				<th align="right">线路名称：</th>
				<td colspan="">${complaint.route}</td>
			</tr>
			<c:if test="${complaint.contactId>0}">
				<tr>
					<th align="right">联系人：</th>
					<td>${contact.name}</td>
					<th align="right">联系人手机：</th>

					<td>${contact.tel}</td>
					<th width="100" align="right">联系人邮箱：</th>
					<td>${contact.email}</td>
				</tr>
			</c:if>
			<c:if test="${complaint.contactId==0}">
				<tr>
					<th align="right">联系人：</th>
					<td>${complaint.contactPerson}</td>
					<th align="right">联系人手机：</th>

					<td>${complaint.contactPhone}</td>
					<th width="100" align="right">联系人邮箱：</th>
					<td>${complaint.contactMail}</td>
				</tr>
			</c:if>

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
				<th align="right">供应商名称：</th>
				<td>${complaint.agencyName}</td>
				<th align="right">供应商电话：</th>
				<td colspan="3">${complaint.agencyTel}</td>
			</tr>
			<!--  
			<tr>
				<th align="right">投诉事宜：</th>
				<td colspan="5"><p>
						<span class="mr20">住宿-周边环境</span>周边有个垃圾站，气味难闻
					</p></td>

			</tr>
			-->
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

	<div class="common-box">
		<div class="common-box-hd">
			<span class="title2">投诉记录<input type="checkbox" id="onlyRecord" onclick="onlyRecord();"/>(勾选只查看跟进记录)</span>
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
					<td width="130" <c:if test="${v.flowName eq '首呼'}">class="cred"</c:if>>${v.flowName}</td>
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
					<td align="center" ><fmt:formatDate value="${v.addTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
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
						</select>
						<br>备注:<input type="text" id="accuracyReMsg${v.id}"/>
						<input type="button" value="提交" class="blue" onclick="updateAccuracy(${v.id});"/></td>
						<td align="left" id="accuracyMsg${v.id}"><c:if test="${v.accuracy==0}">问题分类判断</c:if><c:if test="${v.accuracy==1}">当前状态：<font color="green">正确</font></c:if><c:if test="${v.accuracy==2}">当前状态：<font color="red">错误</font></c:if><br>备注:<span title="${v.accuracyRe}">${v.accuracyRe}</span><input style="margin-left: 10px" type="button" value="编辑" class="blue" onclick="editAccuracy(${v.id});"/></td>
					</c:if>
				</tr>
			</c:forEach>
		</table>
	</div>

	<div class="common-box">
		<div class="common-box-hd">
			<span class="title2">投诉核实记录</span>
		</div>
		<c:forEach items="${request.check_mail_list }" var="v">
			<table class="bgnavy f13 fb" width="100%">
				<tr>
					<td width="90" class="pl10">${v.sender}</td>
					<td><fmt:formatDate value="${v.buildDate}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<c:if test="${v.mark==0}">
						<td>发起核实请求</td>
						<td>发送至：${v.address}</td>
						<td></td>
					</c:if>
					<c:if test="${v.mark==1}">
						<td>反馈核实请求</td>
						<td></td>
						<td></td>
					</c:if>
				</tr>
			</table>

			<table width="100%" class="datatable">
				<tr>
					<th width="100" align="right">内容：</th>
					<!-- <td>${v.content}</td> -->
					<td>${v.content}<br><c:if test="${ v.mark==0}">反馈地址如下：<br><a href="check_email-toReply?id=${v.id}&complaintId=${v.complaintId}" target="_blank">${CONFIG.app_url}complaint/action/check_email-toReply?id=${v.id}&complaintId=${v.complaintId}</a></c:if></td>
				</tr>
			</table>
		</c:forEach>
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
	<input type="hidden" name="orderId" id="orderId" value="${complaint.orderId}"/>

<script type="text/javascript">
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

function onlyRecord(){
	var str= "";
	if($("#onlyRecord:checked").length > 0){
		str="<c:forEach items='${request.follow_note_list }' var='v'><c:if test='${v.isSys == 1}'><table width='100%' class='listtable' style='TABLE-LAYOUT: fixed;'><tr align='center'><td width='130'><fmt:formatDate value='${v.addTime}' pattern='yyyy-MM-dd HH:mm:ss' /></td><td width='60'>${v.peopleName}</td><td width='60'>${v.dealDept}</td><td width='130' <c:if test='${v.flowName eq \"首呼\"}'>class='cred'</c:if>>${v.flowName}</td><td align='left' style='WORD-WRAP: break-word;'>${v.content}</td></tr></table></c:if></c:forEach>";
	}else{
		str="<c:forEach items='${request.follow_note_list }' var='v'><table width='100%' class='listtable' style='TABLE-LAYOUT: fixed;'><tr align='center'><td width='130'><fmt:formatDate value='${v.addTime}' pattern='yyyy-MM-dd HH:mm:ss' /></td><td width='60'>${v.peopleName}</td><td width='60'>${v.dealDept}</td><td width='130' <c:if test='${v.flowName eq \"首呼\"}'>class='cred'</c:if>>${v.flowName}</td><td align='left' style='WORD-WRAP: break-word;'>${v.content}</td></tr></table></c:forEach>";
	}
	$("#follow_note").html(str);	
}

</script>

<div id="easyDialogBox" style="display: none;">
<div id="reasonBox" style="width: 800px;" class="easyDialog_wrapper">
	<div class="easyDialog_content">
		<h4 class="easyDialog_title" id="easyDialogTitle"><a href="javascript:void(0)" title="关闭窗口" class="close_btn" id="closeBtn">×</a>填写投诉事宜</h4>
		<div>
			<iframe src="complaint_reason?complaintId=${complaint.id }&orderId=${complaint.orderId}" frameborder="0" width="800" height="470"></iframe>
		</div>
	</div>
</div>
</div>
<div id="checkEmailBox" style="display: none; width: 520px;" class="easyDialog_wrapper">
	<div class="easyDialog_content">
		<h4 class="easyDialog_title" id="easyDialogTitle"><a href="javascript:void(0)" title="关闭窗口" class="close_btn" id="closeBtn">×</a>发起核实请求</h4>
		<div>
			<iframe src="check_email?complaintId=${complaint.id }&complaintlevel=${complaint.level}&orderId=${complaint.orderId}" frameborder="0" width="520" height="330"></iframe>
		</div>
	</div>
</div>
<div id="followUpRecordBox" style="display: none; width: 550px;" class="easyDialog_wrapper">
	<div class="easyDialog_content">
		<h4 class="easyDialog_title" id="easyDialogTitle"><a href="javascript:void(0)" title="关闭窗口" class="close_btn" id="closeBtn">×</a>填写本次跟进记录</h4>
		<div>
			<iframe src="follow_up_record?complaintId=${complaint.id }&orderId=${complaint.orderId }" frameborder="0" width="550" height="280"></iframe>
		</div>
	</div>
</div>
<div id="followTimeBox" style="display: none; width: 550px;" class="easyDialog_wrapper">
	<div class="easyDialog_content">
		<h4 class="easyDialog_title" id="easyDialogTitle"><a href="javascript:void(0)" title="关闭窗口" class="close_btn" id="closeBtn">×</a>设置下次跟进提醒</h4>
		<div>
			<iframe src="follow_time?complaintId=${complaint.id }&orderId=${complaint.orderId }" frameborder="0" width="550" height="260"></iframe>
		</div>
	</div>
</div>
<div id="solutionBox" style="display: none; width: 670px;" class="easyDialog_wrapper">
	<div class="easyDialog_content">
		<h4 class="easyDialog_title" id="easyDialogTitle"><a href="javascript:void(0)" title="关闭窗口" class="close_btn" id="closeBtn">×</a>对客解决方案</h4>
		<div>
			<iframe src="complaint_solution?complaintId=${complaint.id }&state=${complaint.state }" frameborder="0" width="670" height="450"></iframe>
		</div>
	</div>
</div>
<div id="shareSolutionBox" style="display: none; width: 860px;" class="easyDialog_wrapper">
	<div class="easyDialog_content">
		<h4 class="easyDialog_title" id="easyDialogTitle"><a href="javascript:void(0)" title="关闭窗口" class="close_btn" id="closeBtn">×</a>填写分担方案</h4>
		<div>
			<iframe src="share_solution?complaintId=${complaint.id }" frameborder="0" width="860" height="500"></iframe>
		</div>
	</div>
</div>
<div id="giftNoteBox" style="display: none; width: 620px;" class="easyDialog_wrapper">
	<div class="easyDialog_content">
		<h4 class="easyDialog_title" id="easyDialogTitle"><a href="javascript:void(0)" title="关闭窗口" class="close_btn" id="closeBtn">×</a>申请礼品</h4>
		<div>
			<iframe src="gift_note?complaintId=${complaint.id }&orderId=${complaint.orderId }" frameborder="0" width="620" height="500"></iframe>
		</div>
	</div>
</div>
<div id="qcViewBox" style="display: none; width: 800px;" class="easyDialog_wrapper">
	<div class="easyDialog_content">
		<h4 class="easyDialog_title" id="easyDialogTitle"><a href="javascript:void(0)" title="关闭窗口" class="close_btn" id="closeBtn">×</a>查看质检报告</h4>
		<div>
			<iframe src="qc-view?id=${qc.id}" frameborder="0" width="800" height="400"></iframe>
		</div>
	</div>
</div>
<div id="chgComeFromBox" style="display: none; width: 400px;" class="easyDialog_wrapper">
	<div class="easyDialog_content">
		<h4 class="easyDialog_title" id="easyDialogTitle"><a href="javascript:void(0)" title="关闭窗口" class="close_btn" id="closeBtn">×</a>修改投诉来源</h4>
		<div>
			<iframe src="complaint-changeComplaintComeFrom?id=${complaint.id}" frameborder="0" width="400" height="200"></iframe>
		</div>
	</div>
</div>

</body>
