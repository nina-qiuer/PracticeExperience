<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/html/head.jsp"%>
<title>投诉点</title>
<link type="text/css" href="${CONFIG.res_url}css/bui.css"
	rel="stylesheet" />
<style type="text/css">


.datatable th{
	text-align:right;
}

 .textarea1{
 	height:80px;
 	width:300px;
 }
 
 .textarea2{
 	height:80px;
 	width:450px;
 }
 
 .textarea_single{
 	width:400px;
 }
 
 .input_long1{
 	width:280px;
 }
 
  .input_long2{
 	width:430px;
 }
 
 #final_conclution{
  	height:120px;
 	width:100%;
 }

 
 .foot{
	margin-top:20px;
	margin-bottom:20px;
}
 
</style>

<script type="text/javascript">
var maxPointIndex = '${fn:length(dataList)}';
var reasonTypes = ${reasonTypes};

$(function(){
	recalculate();
});

function addPoint(){
	var option = '';
	/* 构造一级分类下拉框 */
	for(var index=0;index<reasonTypes.length;index++){
		option+= '<option value="'+reasonTypes[index].id+'">'+reasonTypes[index].name+'</option>';
	}
 	var mainTypeSelect = '<select name="pointList['+
		maxPointIndex +'].detailFirst" onclick="setSecondTypeOptions(this)">'+option+'</select>';
	
	/* 构造二级分类下拉框 */
	option = '<option value="'+reasonTypes[0].child[0].id+'">'+reasonTypes[0].child[0].name+'</option>';
	var secondTypeSelect = '<select id="" name="pointList['+
		maxPointIndex+'].detailSecond" disabled onclick="setThirdTypeOptions(this)">'+option+'</select>'; 	
		
	/* 构造三级分类下拉框 */
	option = '<option value="'+reasonTypes[0].child[0].child[0].id+'">'+reasonTypes[0].child[0].child[0].name+'</option>';
	var thirdTypeSelect = '<select id="" name="pointList['+
		maxPointIndex+'].detailThird" disabled>'+option+'</select>';
	
	/* 新增投诉点HTML */
	var newDataArea = '<table width="100%" class="datatable"><tr><th>投诉事宜：</th><td><textarea name="pointList['
		+maxPointIndex+'].typeDescript" class="textarea1"></textarea></td><th>核实情况：</th><td colspan="3"><textarea name="pointList['
		+maxPointIndex+'].verify"  class="textarea2"></textarea></td><td rowspan="3"><input type="button"  class="blue" value="删除" onclick="deletePoint(this)"></td></tr><tr><th>投诉分类：</th><td>'
		+mainTypeSelect+'&nbsp;&nbsp;'+secondTypeSelect+'&nbsp;&nbsp;'+thirdTypeSelect+'</td><th>赔付理据：</th><td><textarea class="textarea_single" name="pointList['
		+maxPointIndex+'].payoutBase"></textarea>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="选择" onclick="openDialog(\'赔付理据\',\'payout_base?name=pointList['
		+maxPointIndex+'].payoutBase\')"/></td></tr><tr><th>法律法规理论赔付金额：</th><td><input type="text" class="input_long1" name="pointList['
		+maxPointIndex+'].theoryPayoutLaw" onblur="recalculate()"/>元</td><th>质量工具理论赔付金额：</th><td><input type="text" class="input_long2" name="pointList['
		+maxPointIndex+'].theoryPayoutQuality" onblur="recalculate()"/>元</td></tr></table><br/>';

	
	$('.point_area').append(newDataArea);
	
	maxPointIndex++;
}

function setSecondTypeOptions(mainTypeSelect)
{
	var secondTypeSelect = $(mainTypeSelect).next();
	var oldSelect = $(secondTypeSelect).val();
	var thirdTypeSelect = $(secondTypeSelect).next();
	var oldThirdSelect = $(thirdTypeSelect).val();
	$(secondTypeSelect).empty(); //清空旧的二级分类选项
	var secondTypes;
	for(var index=0;index<reasonTypes.length;index++){
		if(reasonTypes[index].id==mainTypeSelect.value){
			secondTypes=reasonTypes[index].child;
		}
	}
	//二级菜单变化
	var option='';
	for(var index=0;index<secondTypes.length;index++){
		option+= '<option value="'+secondTypes[index].id+'"';
		if(oldSelect==secondTypes[index].id){
			option+=' selected';
		}
		option+='>'+secondTypes[index].name+'</option>';
	}
	$(secondTypeSelect).append(option);
   	$(secondTypeSelect).removeAttr("disabled"); //放开禁用按钮
   	//三级菜单变化
   	var thirdTypes;
   	$(thirdTypeSelect).empty(); //清空旧的二级分类选项
   	for(var index=0;index<reasonTypes.length;index++){
		for(var second_idx=0;second_idx<reasonTypes[index].child.length;second_idx++){
			if(reasonTypes[index].child[second_idx].id==$(secondTypeSelect).val()){
				thirdTypes=reasonTypes[index].child[second_idx].child;
			}
		}
	}
   	option='';
   	for(var index=0;index<thirdTypes.length;index++){
		option+= '<option value="'+thirdTypes[index].id+'"';
		if(oldThirdSelect==thirdTypes[index].id){
			option+=' selected';
		}
		option+='>'+thirdTypes[index].name+'</option>';
	}
   	$(thirdTypeSelect).append(option);
   	$(thirdTypeSelect).removeAttr("disabled"); //放开禁用按钮
}

function setThirdTypeOptions(secondTypeSelect){
	var thirdTypeSelect = $(secondTypeSelect).next();
	var oldSelect = $(thirdTypeSelect).val();
	$(thirdTypeSelect).empty(); //清空旧的三级分类选项
	var thirdTypes;
	for(var index=0;index<reasonTypes.length;index++){
		for(var second_idx=0;second_idx<reasonTypes[index].child.length;second_idx++){
			if(reasonTypes[index].child[second_idx].id==secondTypeSelect.value){
				thirdTypes=reasonTypes[index].child[second_idx].child;
			}
		}
	}
	var option;
	for(var index=0;index<thirdTypes.length;index++){
		option+= '<option value="'+thirdTypes[index].id+'"';
		if(oldSelect==thirdTypes[index].id){
			option+=' selected';
		}
		option+='>'+thirdTypes[index].name+'</option>';
	}
	$(thirdTypeSelect).append(option);
   	$(thirdTypeSelect).removeAttr("disabled"); //放开禁用按钮
}	

function deletePoint(deleteButton)
{
		//1.删除所在的dataarea
		$(deleteButton).parents('table').remove();
		//2.重新计算理论赔付金额
		recalculate();
}

function recalculate(){
	//拿到所有的理论赔付金额
	var totalTheoryPayout = 0;
	$('input[name$=theoryPayoutLaw]').each(function(){
		var theoryPayoutLaw = $(this).val();
		if(theoryPayoutLaw){
			totalTheoryPayout += theoryPayoutLaw-0;
		}
	});
	$('input[name$=theoryPayoutQuality]').each(function(){
		var theoryPayoutQuality = $(this).val();
		if(theoryPayoutQuality){
			totalTheoryPayout += theoryPayoutQuality-0;
		}
	});
	$('#totalTheoryPayout').text(totalTheoryPayout);
	
}

function addPoints(){
	var secondTypeSelect = $('select:disabled');
	$(secondTypeSelect).removeAttr('disabled');
	var checkOk = true;
	//校验  所有的理论赔付都不为空且必须为数字
	$('input[name$=theoryPayoutLaw]').each(function(){
		var theoryPayoutLaw = $(this).val();
		if(theoryPayoutLaw=='' || isNaN(theoryPayoutLaw-0)){
			alert('法律法规理论赔付金额为必填项且只能为数字');
			checkOk =  false;
			$(secondTypeSelect).attr('disabled','disabled');
			return false; 
		}
	});
	$('input[name$=theoryPayoutQuality]').each(function(){
		var theoryPayoutQuality = $(this).val();
		if(theoryPayoutQuality=='' || isNaN(theoryPayoutQuality-0)){
			alert('质量工具理论赔付金额为必填项且只能为数字');
			checkOk =  false;
			$(secondTypeSelect).attr('disabled','disabled');
			return false; 
		}
	});
	//校验  所有的分类不能为空
	$('select[name$=detailSecond]').each(function(){
		var detailSecond = this.value;
		if(detailSecond==''){
			alert('请选择投诉分类');
			checkOk =  false;
			$(secondTypeSelect).attr('disabled','disabled');
			return false; 
		}
	});
	if(checkOk){
		alert('保存成功');
	}
	
	return checkOk;
	
}
</script>
</head>
<body>
	<p align="right">实际赔付总额：<font color="red">${actualTotalPayout }</font>元；理论赔付金额（公司）：<span id="totalTheoryPayout" style="color:red"></span>元&nbsp;&nbsp;&nbsp;<input type="button" class="blue" value="新增投诉点"/  onclick="addPoint()"></p>
	<form action="complaint_point-save" method="post" onsubmit="return addPoints()">
	<input type="hidden" name="complaintId" value="${complaintId }"/>
	<div class="point_area">
		<c:forEach items="${dataList }" var = "point" varStatus="status">
			<table width="100%" class="datatable">
				<tr>
					<th>投诉事宜：</th>
					<td><textarea name="pointList[${status.index }].typeDescript" class="textarea1">${point.typeDescript }</textarea></td>
					<th>核实情况：</th>
					<td><textarea name="pointList[${status.index }].verify"  class="textarea2">${point.verify }</textarea></td>
					
					<td rowspan="3"><input type="button"  class="blue" value="删除" onclick="deletePoint(this)"></td>
				</tr>
				<tr>
					<th>投诉分类：</th>
					<td>
 						<select name="pointList[${status.index }].detailFirst" onclick="setSecondTypeOptions(this)">
								<c:forEach items="${reasonTypes}" var="tempReasonType" >
									<option value="${tempReasonType.id }" <c:if test="${point.detailFirst == tempReasonType.id }">selected</c:if>>${tempReasonType.name }</option>
								</c:forEach>
						</select>
						<select name="pointList[${status.index }].detailSecond" onclick="setThirdTypeOptions(this)" disabled>
							<%-- <option value="${point.detailSecond }" selected>${point.detailSecond }</option> --%>
							<option value="${point.detailSecond }">
								<c:choose>
									<c:when test="${point.detailThird == null}">请选择</c:when>
		  							<c:otherwise> 
										<c:forEach items="${reasonTypes}" var="tempReasonType" >
											<c:if test="${point.detailFirst == tempReasonType.id }">
												<c:forEach items="${tempReasonType.child}" var="tempReasonTypeScd" >
													<c:if test="${point.detailSecond == tempReasonTypeScd.id }">${tempReasonTypeScd.name }</c:if>
												</c:forEach>
											</c:if>
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</option>
						</select>
						<select name="pointList[${status.index }].detailThird" disabled>
							<option value="${point.detailThird }">
								<c:choose>
									<c:when test="${point.detailThird == null}">请选择</c:when>
		  							<c:otherwise> 
										<c:forEach items="${reasonTypes}" var="tempReasonType" >
											<c:if test="${point.detailFirst == tempReasonType.id }">
												<c:forEach items="${tempReasonType.child}" var="tempReasonTypeScd" >
													<c:if test="${point.detailSecond == tempReasonTypeScd.id }">
														<c:forEach items="${tempReasonTypeScd.child}" var="tempReasonTypeTrd" >
															<c:if test="${point.detailThird == tempReasonTypeTrd.id }">${tempReasonTypeTrd.name }</c:if>
														</c:forEach>
													</c:if>
												</c:forEach>
											</c:if>
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</option>
						</select>
					</td>
					<th>赔付理据：</th>
					<td><textarea name="pointList[${status.index }].payoutBase" class="textarea_single">${point.payoutBase }</textarea>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" value="选择" onclick="openDialog('赔付理据','payout_base?name=pointList[${status.index }].payoutBase')"/></td>
				</tr>
				<tr>
					<th>法律法规理论赔付金额：</th>
					<td><input type="text" value="${point.theoryPayoutLaw }" class="input_long1" name="pointList[${status.index }].theoryPayoutLaw" onblur="recalculate()"/>元</td>
					<th>质量工具理论赔付金额：</th>
					<td><input type="text" value="${point.theoryPayoutQuality }" class="input_long2" name="pointList[${status.index }].theoryPayoutQuality" onblur="recalculate()"/>元</td>
				</tr>
			</table>
			<br/>
		</c:forEach>
	</div><!-- point_area end -->
	
	<div id="final_conclution_area">
		<table width="100%" class="datatable">
			<tr>
				<th width="10%">最终处理方案：</th>
				<td><textarea id="final_conclution" name="finalConclution">${finalConclution }</textarea>
				</td>
			</tr>
		</table>
	</div>
	
	<div class="foot">
		<table  width="100%">
			<tr>
				<td width="40%"></td>
				<td><input type="button" class="blue" value="查看报告" onclick="openDialog('投诉处理报告预览','complaint_point-preview?complaintId=${complaintId}')">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="submit" class="blue" value="保存"></td>
		</table>
	</div>
	</form>
</body>
</html>
