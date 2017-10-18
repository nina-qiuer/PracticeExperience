<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/html/head.jsp"%>
<!-- HTML head 元素已经在head.jsp中包含 -->

<link type="text/css" href="${CONFIG.res_url}css/bui.css"
	rel="stylesheet" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>质检报告</title>
<style type="text/css">
.datatable td table,.datatable td td {
	border: 0 none;
}

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
</style>
<script type="text/javascript" src="${CONFIG.res_url}script/complaint/qc.js?v=1" ></script>
<script type="text/javascript" src="${CONFIG.res_url}/script/jquery/plugin/jquery.form.js"></script>
<script >
$(document).ready(function(){
	var refill = ${refill};
	if(refill == 1){
		if(!confirm("确定重新填写质检报告？")){
			self.parent.tb_remove();
			return false;
		}
	}
	var checked = ${questionChecked};
	
	for (var i=0; i<checked.length; i++) {
		$("#checkbox_"+checked[i]).attr("checked", true);
	}
	
	var respFirst = $("select[name='qcQuestions[qid].trackers[tracker_id].responsibility']");
	var respSecond = $("select[name='qcQuestions[qid].trackers[tracker_id].respSecond']");
	respFirst.each(function(){
		getDepartmentById($(this),2);
	});
	respSecond.each(function(){
		getDepartmentById($(this),3);
	});
	
	var options = { 
	        beforeSubmit:  checkSubmit,  // pre-submit callback 
	        success:       success_function,  // post-submit callback 
	    }; 
	    $('#report_form').ajaxForm(options); 
	});
	
	function checkSubmit() {
		/*
		if($('#compLevel').val() == '-1'){
			alert("请选择投诉等级鉴定!");
			return false;
		}*/
		
		if($('#hiddenInput').val()==0){
			alert("请先选择问题类型,再进行操作!");
			return false;
		}
		
		var  recipients  = $('#recipients').val();
		var  ccs =$('#ccs').val();
		var  recipientsT = recipients.substr(0,recipients.length-1).split(";");//去除最后一个分号
		var  ccsT = ccs.substr(0,ccs.length-1).split(";");//去除最后一个分号
		var  compar = new RegExp("^(\\w)+([-+.]\\w+)*@tuniu\.com$");//判断tuniu.com邮箱正则表达式
		//对收件人邮箱进去判断
		var recipientsF = $.trim(recipientsT);
		if(recipientsF==''){
			alert("收件人不能为空!");
			return false;
		}
		for(var i=0;i<recipientsT.length;i++){
			if(!compar.test(recipientsT[i])){
				alert("收件人中"+recipientsT[i]+"不符合要求!");
				return false;
				break;
			}
		}
		//对抄送人邮箱进去判断
		var ccsF = $.trim(ccsT);
		if(ccsF==''){
			alert("抄送人不能为空!");
			return false;
		}
		for(var i=0;i<ccsT.length;i++){
			if(!compar.test(ccsT[i])){
				alert("抄送人中"+ccsT[i]+"不符合要求!");
				return false;
				break;
			}
		}
	
		$('#saveInfo').attr('disabled' , 'true');
		$('#submitInfo').attr('disabled' , 'true');
		
		return true;
	}

	function success_function() {
		//self.parent.tb_remove();
		var flag = $('#refill').val();
		if(flag == 1){
			parent.onTabClicked('#003');
		}else if(flag == 0){
			//parent.onTabClicked('#002');
			parent.location.href=$('#curUrl').val();
		}
	}

	/**
	*根据部门id获取部门
	*obj 当前控件  level 2第二个select控件  3第三个select控件
	*/
	function getDepartmentById(obj,level){
		var nextSelect = $(obj).next().next();
		if(level==2){
			nextSelect.empty();
			nextSelect.next().next().empty();
			$("<option/>").attr("value","0").html("-------------").appendTo(nextSelect.next().next());
		}else if(level==3){
			nextSelect.empty();
		}
		$("<option/>").attr("value","0").html("-------------").appendTo(nextSelect);
		//$(obj).next().next().empty();
		if($(obj).val()>=0 && $(obj).val()<15){//表示是老数据，不用读取数据。
			return false;
		}
		$.ajax({
			type:"post",
			url:"qc-getDeparments?depId="+$(obj).val(),
			dataType:"json",
			async:false,
			success:function appendDepartment(data){
				var depId = $(obj).next().val();
				//alert(depId);
				if(data != null){
					var select=$(obj).next().next();
					select.empty();
					$("<option/>").attr("value","0").html("-------------").appendTo(select);
					for(var i=0;i<data.length;i++){
						for(var key in data[i]){ 
							$("<option/>").attr("value",key).html(data[i][key]).appendTo(select);
						}
					}
					if(depId != ""){
						select.attr("value", depId);
					}
				}
			}
		});
	}

function redLineTrToggle(obj) {
	var value = obj.value;
	if ("一级乙等-红线" == value) {
		$(obj).parent().parent().next().show();
	} else {
		$(obj).parent().parent().next().hide();
	}
}
</script>

</head>
<body>
	<div class="top_crumbs">
		您当前所在的位置：<a href="#">投诉质检管理</a>&gt;&gt;<span class="top_crumbs_txt">质检报告</span>
	</div>
		<!-- 投诉相关数据 -->
		<div class="common-box">
			<div class="common-box-hd">
				<span class="title2">投诉情况说明单</span>
			</div>
			<table width="100%" class="datatable">
				<tr>
					<th width="100" align="right">订单号：</th>
					<td>${qc.orderId}</td>
					<th width="100" align="right">客户姓名：</th>
					<td>${qc.complaint.guestName}</td>
					<th width="100" align="right">人数：</th>
					<td>${qc.complaint.guestNum}</td>
				</tr>
				<tr>
					<th align="right">出发地：</th>
					<td>${qc.complaint.startCity}</td>
					<th align="right">出发时间：</th>
					<td colspan="3"><fmt:formatDate
							value="${qc.complaint.startTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
				</tr>
				<tr>
					<th align="right">线路：</th>
					<td colspan="5">${qc.complaint.route}</td>
				</tr>
				<tr>
					<th align="right">满意度：</th>
					<td colspan="5">${qc.complaint.score}%</td>
				</tr>
				<tr>
					<th height="36" align="right">售前客服：</th>
					<td>${qc.complaint.customer}</td>
					<th align="right">客服经理：</th>
					<td>${qc.complaint.customerLeader}</td>
					<th align="right">高级客服经理：</th>
					<td>${qc.complaint.serviceManager}</td>
				</tr>
				<tr>
					<th align="right">产品专员：</th>
					<td>${qc.complaint.producter}</td>
					<th align="right">产品经理：</th>
					<td>${qc.complaint.productLeader}</td>
					<th align="right">高级产品经理：</th>
					<td>${qc.complaint.seniorManager}</td>
				</tr>
				<tr>
					<th align="right">导游编号：</th>
					<td>${qc.complaint.guideId}</td>
					<th align="right">导游姓名：</th>
					<td>${qc.complaint.guideName}</td>
					<th align="right">导游电话：</th>
					<td>${qc.complaint.guideCall}</td>
				</tr>
				<tr>
					<th align="right">投诉级别：</th>
					<td colspan="5">${qc.complaint.level}级</td>
				</tr>
				<tr>
					<th align="right">投诉说明：</th>
					<td colspan="5">${qc.complaint.descript}</td>
				</tr>
				<tr>
					<th align="right">客户要求：</th>
					<td colspan="5">${qc.complaint.requirement}</td>
				</tr>
				<tr>
					<th align="right">受理人：</th>
					<td>${qc.complaint.dealName}</td>
					<th align="right">受理时间：</th>
					<td colspan="3"><fmt:formatDate
							value="${qc.complaint.buildDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
				</tr>
				<tr>
					<th align="right">相关供应商：</th>
					<td colspan="5">${qc.complaint.agencyName}</td>
				</tr>
			</table>
		</div>

		<!-- 质检相关数据 -->
		<div class="common-box">
			<div class="common-box-hd">
				<span class="title2">质检报告</span>
			</div>
			<form name="report_form" id ="report_form" method="post" enctype="multipart/form-data" action="qc-" >
			    <input type="hidden" name="curUrl" id="curUrl" value="${curUrl}"/>
				<input type="hidden" name="qc.id" id="id"  value="${qc.id}" />
				<input type="hidden" name="id" id="id"  value="${id}" />
				<input type="hidden" name="refer_to" id="refer_to" value="" />		
				<table width="100%" class="datatable">
				<tr>
					<th width="100" align="right">问题描述：</th>
					<td>
						<table>
							<c:forEach items="${complaintReasons }" var="reason">
								<tr>
									<td width="130">${reason.type}&nbsp;-&nbsp;${reason.secondType}</td>
									<td>${reason.typeDescript}</td>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
				<tr>
					<th width="100" align="right">核实情况：</th>
					<td><textarea name="qc.verify" id="textarea" cols="80" rows="5">${qc.verify}</textarea></td>
				</tr>
				<tr>
					<th align="right"></th>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<input type="hidden" value="${flag}" id="hiddenInput">
							<c:forEach items="${qcQuestionClasses}" var="bigClass">
								<c:if test="${bigClass.childClasses!=null}">
									<!-- 找对应的子类问题 -->
									<c:forEach items="${bigClass.childClasses}" var="smallClass" varStatus="vt">
										<c:if test="${vt.index % 5 == 0}">
											<!-- 每行输出5个元素 -->
											<tr>
												<c:if test="${vt.index >0}">
													<td>&nbsp;</td>
												</c:if>
										</c:if>

										<c:if test="${vt.index == 0}">
											<!-- 第一行第一个元素为一级分类名称  -->
											<td class="fb"><label> <input type="checkbox"
													name="checkbox_${bigClass.id}" value="${bigClass.id}"
													id="checkbox_${bigClass.id}" onclick="onBigClassClicked(this)"/> ${bigClass.description}
											</label></td>
										</c:if>

										<td><label> <input type="checkbox"
												name="checkbox_${bigClass.id}" value="${smallClass.id}"
												id="checkbox_${smallClass.id}" onclick="onSmallClassClicked(this,{smallClassId:${smallClass.id}, bigClassId:${bigClass.id}, smallClassTitle:'${smallClass.description}', bigClassTitle:'${bigClass.description}' })"/>
												${smallClass.description}
										</label></td>

										<c:if test="${vt.count % 5 == 0}">
											<!-- 每行输出5个元素 -->
											</tr>
										</c:if>
									</c:forEach>
								</c:if>
							</c:forEach>
							
							<!-- 问题跟踪者 -->
							<tr id="tracker_title" style="display:none" rowid="tracker_title\${smallClassId}">
	            				<td colspan="6">
	            					<hr>
	            					<table>
	            						<tr>
	            							<td>
	            								<b>问题类型\${smallClassId}</b>&nbsp;:&nbsp;\${bigClassTitle}-\${smallClassTitle}
	            								<input type="hidden" name="qcQuestions[qid].bigClassId" value="\${bigClassId}" />
				            					<input type="hidden" name="qcQuestions[qid].smallClassId" value="\${smallClassId}"/>
				            					<input type="hidden" name="qcQuestions[qid].bigClassName" value="\${bigClassTitle}" />
				            					<input type="hidden" name="qcQuestions[qid].smallClassName" value="\${smallClassTitle}"/><br>
				            					<b>投诉问题等级：</b>
				            					<select class="mr10" name="qcQuestions[qid].compLevel">
													<option value="-1"></option>
													<option value="0">0级</option>
													<option value="1">1级</option>
													<option value="2">2级</option>
													<option value="3">3级</option>
												</select>
	            							</td>
	            							<td colspan="4"><b>质检结论：</b><textarea name="qcQuestions[qid].conclusion" cols="60" rows="4" style="vertical-align: middle;"></textarea></td>
	            						</tr>
	            						<tr>
	            							<td>
	            								<b>记分等级：</b>
				            					<select class="mr10" name="qcQuestions[qid].scoreLevel" onchange="redLineTrToggle(this)">
													<option value=""></option>
													<option value="一级甲等">一级甲等</option>
													<option value="一级乙等">一级乙等</option>
													<option value="一级丙等">一级丙等</option>
													<option value="二级">二级</option>
													<option value="三级">三级</option>
													<option value="一级乙等-红线">一级乙等-红线</option>
													<option value="一级乙等-非红线">一级乙等-非红线</option>
												</select>
	            							</td>
	            							<td>
	            								<b>记分值：</b>
				            					<select class="mr10" name="qcQuestions[qid].scoreValue">
													<option value="0"></option>
													<option value="10">10</option>
													<option value="5">5</option>
													<option value="3">3</option>
													<option value="2">2</option>
													<option value="1">1</option>
												</select>
	            							</td>
	            							<td>
	            								<b>记分对象1：</b>
	            								<input type="text" name="qcQuestions[qid].scoreTarget1" size="10" class="mr20">
	            							</td>
	            							<td>
	            								<b>记分对象2：</b>
	            								<input type="text" name="qcQuestions[qid].scoreTarget2" size="10" class="mr20">
	            							</td>
	            							<td rowspan="2" valign="bottom"><input type="button" name="add_tracker" id="add_tracker" value="添加责任归属" onclick="onAddTrackerClicked(\${smallClassId}, \${bigClassId})"/></td>
	            						</tr>
	            						<tr style="display: none;">
	            							<td>
	            								<b>　出发地：</b>
				            					<input type="text" name="qcQuestions[qid].startCity" size="10" class="mr20" value="${qc.complaint.startCity}">
	            							</td>
	            							<td>
	            								<b>目的地：</b>
				            					<input type="text" name="qcQuestions[qid].endCity" size="10" class="mr20" value="${qc.complaint.endCity}">
	            							</td>
	            							<td>
	            								<b>机票价格（元）：</b>
	            								<c:if test="${1 == qc.complaint.airFlag}">
	            									<c:if test="${qc.complaint.airfare > 0}">
	            										<input type="text" name="qcQuestions[qid].airfare" size="10" value="${qc.complaint.airfare}">
	            									</c:if>
	            									<c:if test="${qc.complaint.airfare <= 0}">
	            										<input type="text" name="qcQuestions[qid].airfare" size="10" value="打包资源">
	            									</c:if>
	            								</c:if>
	            								<c:if test="${0 == qc.complaint.airFlag}">
	            									<input type="text" name="qcQuestions[qid].airfare" size="10" value="无机票">
	            								</c:if>
	            							</td>
	            							<td>
	            								<b>产品价格（元）：</b>
	            								<input type="text" name="qcQuestions[qid].productPrice" size="8" value="${qc.complaint.productPrice}">
	            							</td>
	            						</tr>
	            					</table>
	            				</td>
				          	</tr>
				          	<tr id="tracker_input"  style="display:none" rowid="tracker_input\${smallClassId}_\${index}">							          		
								<td colspan="6">
									<select class="mr20" name="qcQuestions[qid].trackers[tracker_id].responsibility" onchange="getDepartmentById(this,2)">
										<option value="0">请选择责任归属</option>
										${ui:makeSelect(officerDept,"0",null)}
									</select>
									<input type="hidden" id="hiddenRespSecond" value="0">
									<select class="mr20" name="qcQuestions[qid].trackers[tracker_id].respSecond" onchange="getDepartmentById(this,3)">
										<option value="0"></option>
									</select>
									<input type="hidden" id="hiddenRespSecond" value="0">
									<select class="mr20" name="qcQuestions[qid].trackers[tracker_id].respThird" >
										<option value="0"></option>
									</select>
									<select class="mr20" name="qcQuestions[qid].trackers[tracker_id].position" >
										<option value="0">请选择执行岗位</option>
										${ui:makeSelect(positionMap,"0",null)}
									</select>
									<input class="mr20" name="qcQuestions[qid].trackers[tracker_id].responsiblePerson" 
										type="text" value="填写责任人" size="10" onclick="onInputClicked(this)" onBlur="onInputBlur(this, '填写责任人')"/> 
									<input class="mr20" name="qcQuestions[qid].trackers[tracker_id].improver" 
										type="text" value="填写改进人" size="10" onclick="onInputClicked(this)" onBlur="onInputBlur(this, '填写改进人')"/> 
									<a class="mr20" href="javascript:void(0)" onclick="onDelTrackerClicked(this, \${smallClassId}, \${bigClassId})">删除</a>
								</td>
				          	</tr>
				          	
				          	<c:forEach items="${qcQuestions}" var="question" varStatus="st">
				          		<!-- 根据问题类型，自动勾选 -->
				          		<tr id="tracker_title${question.smallClassId}" class="dynamic_data">
				          		<td colspan="6">
	            					<hr>
	            					<table>
	            						<tr>
	            							<td>问题类型${question.smallClassId}&nbsp;:&nbsp;${question.bigClassName}-${question.smallClassName}          					
				            					<input type="hidden" name="qcQuestions[qid].bigClassId" value="${question.bigClassId}" />
				            					<input type="hidden" name="qcQuestions[qid].smallClassId" value="${question.smallClassId}"/>
				            					<input type="hidden" name="qcQuestions[qid].bigClassName" value="${question.bigClassName}" />
				            					<input type="hidden" name="qcQuestions[qid].smallClassName" value="${question.smallClassName}"/><br>
				            					<b>投诉问题等级：</b>
				            					<select class="mr10" name="qcQuestions[qid].compLevel">
													<option value="-1" <c:if test="${question.compLevel==-1}">selected</c:if>></option>
													<option value="0" <c:if test="${question.compLevel==0}">selected</c:if>>0级</option>
													<option value="1" <c:if test="${question.compLevel==1}">selected</c:if>>1级</option>
													<option value="2" <c:if test="${question.compLevel==2}">selected</c:if>>2级</option>
													<option value="3" <c:if test="${question.compLevel==3}">selected</c:if>>3级</option>
												</select>
				            				</td>
				            				<td colspan="4"><b>质检结论：</b><textarea name="qcQuestions[qid].conclusion" cols="45" rows="4" style="vertical-align: middle;">${question.conclusion}</textarea></td>
	            						</tr>
	            						<tr>
		           							<td>
		           								<b>记分等级：</b>
				            					<select class="mr10" name="qcQuestions[qid].scoreLevel" onchange="redLineTrToggle(this)">
													<option value="" <c:if test="${question.scoreLevel==''}">selected</c:if>></option>
													<option value="一级甲等" <c:if test="${question.scoreLevel=='一级甲等'}">selected</c:if>>一级甲等</option>
													<option value="一级乙等" <c:if test="${question.scoreLevel=='一级乙等'}">selected</c:if>>一级乙等</option>
													<option value="一级丙等" <c:if test="${question.scoreLevel=='一级丙等'}">selected</c:if>>一级丙等</option>
													<option value="二级" <c:if test="${question.scoreLevel=='二级'}">selected</c:if>>二级</option>
													<option value="三级" <c:if test="${question.scoreLevel=='三级'}">selected</c:if>>三级</option>
													<option value="一级乙等-红线" <c:if test="${question.scoreLevel=='一级乙等-红线'}">selected</c:if>>一级乙等-红线</option>
													<option value="一级乙等-非红线" <c:if test="${question.scoreLevel=='一级乙等-非红线'}">selected</c:if>>一级乙等-非红线</option>
												</select>
		           							</td>
		           							<td>
		           								<b>记分值：</b>
				            					<select class="mr10" name="qcQuestions[qid].scoreValue">
													<option value="0" <c:if test="${question.scoreValue==0}">selected</c:if>></option>
													<option value="10" <c:if test="${question.scoreValue==10}">selected</c:if>>10</option>
													<option value="5" <c:if test="${question.scoreValue==5}">selected</c:if>>5</option>
													<option value="3" <c:if test="${question.scoreValue==3}">selected</c:if>>3</option>
													<option value="2" <c:if test="${question.scoreValue==2}">selected</c:if>>2</option>
													<option value="1" <c:if test="${question.scoreValue==1}">selected</c:if>>1</option>
												</select>
		           							</td>
		           							<td>
		           								<b>记分对象1：</b>
		           								<input type="text" name="qcQuestions[qid].scoreTarget1" size="10" class="mr20" value="${question.scoreTarget1 }">
		           							</td>
		           							<td>
		           								<b>记分对象2：</b>
		           								<input type="text" name="qcQuestions[qid].scoreTarget2" size="10" class="mr20" value="${question.scoreTarget2 }">
		           							</td>
		           							<td rowspan="2"><input type="button" name="add_tracker" id="add_tracker" value="添加责任归属" onclick="onAddTrackerClicked(${question.smallClassId}, ${question.bigClassId})"/></td>
		           						</tr>
		           						<tr <c:if test="${question.scoreLevel!='一级乙等-红线'}">style='display: none;'</c:if>>
	            							<td>
	            								<b>　出发地：</b>
	            								<input type="text" name="qcQuestions[qid].startCity" size="10" class="mr20" value="${question.startCity}">
	            							</td>
	            							<td>
	            								<b>目的地：</b>
	            								<input type="text" name="qcQuestions[qid].endCity" size="10" class="mr20" value="${question.endCity}">
	            							</td>
	            							<td>
	            								<b>机票价格（元）：</b>
	            								<input type="text" name="qcQuestions[qid].airfare" size="10" value="${question.airfare}">
	            							</td>
	            							<td>
	            								<b>产品价格（元）：</b>
	            								<input type="text" name="qcQuestions[qid].productPrice" size="10" value="${question.productPrice}">
	            							</td>
	            						</tr>
	            					</table>
	            				</td>
				          		</tr>
				          		<c:forEach items="${question.trackers}" var="tracker" varStatus="vt">
				          			<tr  id="tracker_input${question.smallClassId}_${vt.index}" class="dynamic_data">							          		
										<td colspan="6">
											<select class="mr20" name="qcQuestions[qid].trackers[tracker_id].responsibility" onchange="getDepartmentById(this,2)">
												<option value="0">请选择责任归属</option>
												${ui:makeSelect(officerDept,tracker.responsibility,null)}
											</select>
											<input type="hidden" id="hiddenRespSecond" value="${tracker.respSecond}">
											<select class="mr20" name="qcQuestions[qid].trackers[tracker_id].respSecond" onchange="getDepartmentById(this,3)">
												<option value="0"></option>
											</select>
											<input type="hidden" id="hiddenResprespThird" value="${tracker.respThird}">
											<select class="mr20" name="qcQuestions[qid].trackers[tracker_id].respThird">
												<option value="0"></option>
											</select>
											<select class="mr20" name="qcQuestions[qid].trackers[tracker_id].position">
												<option value="0">请选择执行岗位</option>
												${ui:makeSelect(positionMap,tracker.position,null)}
											</select>
											<input class="mr20" name="qcQuestions[qid].trackers[tracker_id].responsiblePerson" 
												type="text" value="${tracker.responsiblePerson}" size="10"/>
											<input class="mr20" name="qcQuestions[qid].trackers[tracker_id].improver" 
												type="text" value="${tracker.improver}" size="10"/> 
											<a class="mr20" href="javascript:void(0)" onclick="onDelTrackerClicked(this, ${question.smallClassId}, ${question.bigClassId})">删除</a>
										</td>
						          	</tr>
				          		</c:forEach>				          	
				          	</c:forEach>				          			
						</table>
					</td>
				</tr>
				
				<tr>
					<th align="right">收件人：</th>
					<td>
						<textarea name="recipientsEmails" id="recipients" rows="4" cols="60">${qc.recipients}</textarea>
					</td>
				</tr>
				<tr>
					<th align="right">抄送人：</th>
					<td>
						<textarea name="cssEmails" id="ccs" rows="4" cols="60">${qc.ccs}</textarea>
					</td>
				</tr>
				<tr>
					<th align="right">质检人：</th>
					<td>${qc.qcPersonName}</td>
				</tr>
				<tr>
					<th align="right">&nbsp;</th>
					<td>
						
						<!-- <input class="pd5" type="button" value="JS测试" name="save" onclick="resetIndex();" /> -->
						<input class="pd5" type="submit" value="保存" name="save" id="saveInfo" onmouseover="$('#report_form').attr('action', 'qc-doSave');" onclick="resetIndex();"/>
						<input class="pd5" type="submit" value="提交" name="submit" id="submitInfo" onmouseover="$('#report_form').attr('action', 'qc-doSubmit');" onclick="resetIndex();"/>
						<c:if test="${closeQcFlag}">		
		                    <%--  <input title="关闭质检" class="pd5" name="input" type="button" value="关闭质检" onClick="javascript:if(confirm('确定关闭质检流程?')){location.href='qc-doClose?id=${complaintId}' }"  /> --%>
		                    <input class="pd5" type="submit" value="关闭质检" name="submit" id="closeQc" onmouseover="$('#report_form').attr('action', 'qc-doSubmit');" onclick="resetIndex();"/>	
	                    </c:if>
						<input type="hidden" id="refill" name="refill" value="${refill}"/>
					</td>
				</tr>
			</table>
			</form>
		</div>
	</body>
</html>
