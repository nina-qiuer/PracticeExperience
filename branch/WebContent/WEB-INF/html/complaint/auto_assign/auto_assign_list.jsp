<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<link href="${CONFIG.res_url}css/thickbox.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${CONFIG.res_url}script/thickbox-compressed.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}script/complaint/complaint/appoint_manager.js" ></script> 
<script type="text/javascript">
function tabFuncdd(showId,navObj){ 
	$(".tab_part").hide();
	$("#pici_tab .menu_on").removeClass("menu_on");
	$(navObj).addClass("menu_on");
	$(showId).show();
	return false;
}

//thinkbox弹出框控制js
$(function(){
	/*移除thickbox默认样式*/
	$('.thickbox').click(function(){
		$('#TB_closeAjaxWindow').html("<a href='javascript:void(0)' id='TB_closeWindowButton' title='Close'>×</a>");
		$('#TB_closeWindowButton').live('click',function(){
			tb_remove();
		});
	});
});

//开启或关闭一条配置数据
function manager_del(id,delFlag){
	$("#id").attr("value",id);
	$("#delFlag").attr("value",delFlag);
	
	var text=""
	if(delFlag==0){
		text="确定关闭此人分单？";
	}else{
		text="确定开启此人分单？";
	}
	var result=confirm(text);
	if(result==true){
		var param = $('#form').serialize();
		$.ajax({
		type: "POST",
		url: "${manageUrl}-del",
		data: param,
		success: function(data){
			if(data!=0) {
				window.location.reload();
			}
	  }
 	});
	}
}

function manager_delete(id){
	$("#id").attr("value",id);
	var text="确定删除此人分单？";
	var result=confirm(text);
	if(result==true){
		var param = $('#form').serialize();
		$.ajax({
		type: "POST",
		url: "${manageUrl}-delete",
		data: param,
		success: function(data){
			if(data!=0) {
				window.location.reload();
			}
	  }
 	});
	}
}
</script>
<style type="text/css">
	.datatable td table{border-collapse:collapse;}
	.datatable td td{border:0 none;}
</style>

</HEAD>
<BODY>
<c:if test="${!isOQ}">
	<div id="pici_tab" class="clear">
		<ul>
			<li class="menu_on" onclick="tabFuncdd('#001',this)">
			<s class="rc-l"></s><s class="rc-r"></s><a href="${manageUrl}">自动分单配置</a>
			</li>
			<li onclick="tabFuncdd('#002',this)"><s class="rc-l"></s>
			<s class="rc-r"></s><a href="${manageUrl}-queryDetail">当前分单统计</a>
			</li>
		</ul>
	</div>
</c:if>
<div id="001" class="tab_part">
<form name="form" id="form" method="post" enctype="multipart/form-data" action="${manageUrl}-del" onSubmit="">
<input type="hidden" name="id" id="id" value="" />
<input type="hidden" name="delFlag" id="delFlag" value="" />
<table width="100%" class="datatable">
	<c:if test="${isBefore || isDev || isComp}">
	<tr>
		<th width="160" align="right">出游前分单配置：</th>
		<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="fb" width="120">部门</td>
					<td class="fb" width="120">人员 </td>
					<td class="fb" width="120">处理岗</td>
					<td class="fb" width="125">投诉级别</td>
					<td class="fb" width="135">产品品类</td>
					<td class="fb" width="235">目的地大类</td>
					<td class="fb" width="120">处理赔款单</td>
					<td class="fb" width="120">会员等级</td>
					<td><input title="添加"
						alt="auto_assign-add?type=1&tourTimeNode=1&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=290&width=400&modal=false"
						class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" />
					</td>
				</tr>
				<c:forEach items="${complaintList}" var="v">
					<c:if test="${v.tourTimeNode==1}">
						<tr <c:if test="${v.delFlag==1}">style='color: #666666'</c:if>>
							<td>${v.departmentName}</td>
							<td>${v.userName}</td>
							<td>
								<c:if test="${v.tourTimeNode == 1}">出游前客户服务</c:if>
								<c:if test="${v.tourTimeNode == 2}">售后组</c:if>
								<c:if test="${v.tourTimeNode == 3}">资深组</c:if>
							</td>
							<td>
								<c:if test="${v.complaintLevel1Flag == 1}">一级；</c:if>
								<c:if test="${v.complaintLevel2Flag == 1}">二级；</c:if>
								<c:if test="${v.complaintLevel3Flag == 1}">三级；</c:if>
							</td>
							<td align="center">${v.productCategory}</td>
							<td>
								<c:if test="${v.aroundFlag == 1}">周边；</c:if>
								<c:if test="${v.inlandLongFlag == 1}">国内长线；</c:if>
								<c:if test="${v.abroadShortFlag == 1}">出境短线；</c:if>
								<c:if test="${v.abroadLongFlag == 1}">出境长线；</c:if>
								<c:if test="${v.othersFlag == 1}">其他</c:if>
							</td>
							<td>
								<c:if test="${v.payforOrder == 1}">是</c:if>
								<c:if test="${v.payforOrder == 0}">否</c:if>
							</td>
							<td>
								<c:if test="${v.guestLevel == 0}">所有会员</c:if>
								<c:if test="${v.guestLevel == 1}">五星以下</c:if>
								<c:if test="${v.guestLevel == 2}">五星及以上</c:if>
							</td>
							<td>
								<input type="button" name="del" onclick="manager_del(${v.id},${v.delFlag})" <c:if test="${v.delFlag==1}"> style="color:gray;"</c:if> 
								value="<c:if test="${v.delFlag==0}">关闭</c:if><c:if test="${v.delFlag==1}">开启</c:if>" " />
								<input alt="auto_assign-modify?entity.id=${v.id}&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=290&width=400&modal=false"
						           class="thickbox" name="modify" type="button" value="修改" title="修改"/>
								<input type="button" name="del" onclick="manager_delete(${v.id})" value="删除" />
							</td>
						</tr>
					</c:if>
				</c:forEach>
			</table>
		</td>
	</tr>
	</c:if>
	
	<c:if test="${isMiddle || isDev || isComp}">
	<tr>
		<th width="160" align="right">售后组分单配置：</th>
		<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="fb" width="120">部门</td>
					<td class="fb" width="120">人员 </td>
					<td class="fb" width="120">处理岗</td>
					<td class="fb" width="120">组别</td>
					<td class="fb" width="125">投诉级别</td>
					<td class="fb" width="135">产品品类</td>
					<td class="fb" width="235">目的地大类</td>
					<td class="fb" width="120">签约城市</td>
					<td class="fb" width="120">会员等级</td>
					<td><input title="添加"
						alt="auto_assign-add?type=1&tourTimeNode=2&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=290&width=400&modal=false"
						class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" />
					</td>
				</tr>
				<c:forEach items="${complaintList}" var="v">
					<c:if test="${v.tourTimeNode==2}">
						<tr <c:if test="${v.delFlag==1}">style='color: #666666'</c:if>>
							<td>${v.departmentName}</td>
							<td>${v.userName}</td>
							<td>
								<c:if test="${v.tourTimeNode == 1}">出游前客户服务</c:if>
								<c:if test="${v.tourTimeNode == 2}">售后组</c:if>
								<c:if test="${v.tourTimeNode == 3}">资深组</c:if>
							</td>
							<td>
								<c:if test="${v.touringGroupType == 1}">呼入组</c:if>
								<c:if test="${v.touringGroupType == 2}">后处理组</c:if>
								<c:if test="${v.touringGroupType == 3}">资深坐席组</c:if>
							</td>
							<td>
								<c:if test="${v.complaintLevel1Flag == 1}">一级；</c:if>
								<c:if test="${v.complaintLevel2Flag == 1}">二级；</c:if>
								<c:if test="${v.complaintLevel3Flag == 1}">三级；</c:if>
							</td>
							<td align="center">${v.productCategory}</td>
							<td>
								<c:if test="${v.aroundFlag == 1}">周边；</c:if>
								<c:if test="${v.inlandLongFlag == 1}">国内长线；</c:if>
								<c:if test="${v.abroadShortFlag == 1}">出境短线；</c:if>
								<c:if test="${v.abroadLongFlag == 1}">出境长线；</c:if>
								<c:if test="${v.othersFlag == 1}">其他</c:if>
							</td>
							<td align="left">${v.signCity}</td>
							<td>
								<c:if test="${v.guestLevel == 0}">所有会员</c:if>
								<c:if test="${v.guestLevel == 1}">五星以下</c:if>
								<c:if test="${v.guestLevel == 2}">五星及以上</c:if>
							</td>
							<td>
								<input type="button" name="del" onclick="manager_del(${v.id},${v.delFlag})" <c:if test="${v.delFlag==1}"> style="color:gray;"</c:if> 
								value="<c:if test="${v.delFlag==0}">关闭</c:if><c:if test="${v.delFlag==1}">开启</c:if>" " />
								<input alt="auto_assign-modify?entity.id=${v.id}&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=290&width=400&modal=false"
						           class="thickbox" name="modify" type="button" value="修改" title="修改"/>
								<input type="button" name="del" onclick="manager_delete(${v.id})" value="删除" />
							</td>
						</tr>
					</c:if>
				</c:forEach>
			</table>
		</td>
	</tr>
	</c:if>
	
	<c:if test="${isAfter || isDev || isComp}">
	<tr>
		<th width="160" align="right">资深组自动分单配置：</th>
		<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="fb" width="120">人员 </td>
					<td class="fb" width="120">处理岗</td>
					<td class="fb" width="125">投诉级别</td>
					<td class="fb" width="135">产品品类</td>
					<td class="fb" width="235">目的地大类</td>
					<td class="fb" width="120">签约城市</td>
					<td class="fb" width="120">投诉来源</td>
					<td class="fb" width="120">会员等级</td>
					<td><input title="添加"
						alt="auto_assign-add?type=1&tourTimeNode=3&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=290&width=400&modal=false"
						class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" />
					</td>
				</tr>
				<c:forEach items="${complaintList}" var="v">
					<c:if test="${v.tourTimeNode==3}">
					<tr <c:if test="${v.delFlag==1}">style='color: #666666'</c:if>>
						<td>${v.userName}</td>
						<td>
							<c:if test="${v.tourTimeNode == 1}">出游前客户服务</c:if>
							<c:if test="${v.tourTimeNode == 2}">售后组</c:if>
							<c:if test="${v.tourTimeNode == 3}">资深组</c:if>
						</td>
						<td>
							<c:if test="${v.complaintLevel1Flag == 1}">一级；</c:if>
							<c:if test="${v.complaintLevel2Flag == 1}">二级；</c:if>
							<c:if test="${v.complaintLevel3Flag == 1}">三级；</c:if>
						</td>
						<td align="center">${v.productCategory}</td>
						<td>
							<c:if test="${v.aroundFlag == 1}">周边；</c:if>
							<c:if test="${v.inlandLongFlag == 1}">国内长线；</c:if>
							<c:if test="${v.abroadShortFlag == 1}">出境短线；</c:if>
							<c:if test="${v.abroadLongFlag == 1}">出境长线；</c:if>
							<c:if test="${v.othersFlag == 1}">其他</c:if>
						</td>
						<td align="center">${v.signCity}</td>
						<td >${v.comeFrom}</td>
						<td>
								<c:if test="${v.guestLevel == 0}">所有会员</c:if>
								<c:if test="${v.guestLevel == 1}">五星以下</c:if>
								<c:if test="${v.guestLevel == 2}">五星及以上</c:if>
							</td>
						<td><input type="button" name="del" onclick="manager_del(${v.id},${v.delFlag})" <c:if test="${v.delFlag==1}"> style="color:gray;"</c:if> 
							value="<c:if test="${v.delFlag==0}">关闭</c:if><c:if test="${v.delFlag==1}">开启</c:if>" " />
							<input alt="auto_assign-modify?entity.id=${v.id}&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=290&width=400&modal=false"
						           class="thickbox" name="modify" type="button" value="修改" title="修改"/>
							<input type="button" name="del" onclick="manager_delete(${v.id})" value="删除" />
						</td>
					</tr>
					</c:if>
				</c:forEach>
			</table>
		</td>
	</tr>
	</c:if>
	
	<!-- 机票组自动分单配置 -->
<c:if test="${isAir || isDev || isComp}">
	<tr>
		<th width="160" align="right">机票组自动分单配置：</th>
		<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="fb" width="120">人员 </td>
					<td class="fb" width="125">投诉级别</td>
					
					<td><input title="添加"
						alt="auto_assign-add?type=1&tourTimeNode=4&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=290&width=400&modal=false"
						class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" />
					</td>
				</tr>
				<c:forEach items="${complaintList}" var="v">
					<c:if test="${v.tourTimeNode==4}">
					<tr <c:if test="${v.delFlag==1}">style='color: #666666'</c:if>>
						<td>${v.userName}</td>
						<td>
							<c:if test="${v.complaintLevel1Flag == 1}">一级；</c:if>
							<c:if test="${v.complaintLevel2Flag == 1}">二级；</c:if>
							<c:if test="${v.complaintLevel3Flag == 1}">三级；</c:if>
						</td>
						<td><input type="button" name="del" onclick="manager_del(${v.id},${v.delFlag})" <c:if test="${v.delFlag==1}"> style="color:gray;"</c:if> 
							value="<c:if test="${v.delFlag==0}">关闭</c:if><c:if test="${v.delFlag==1}">开启</c:if>" " />
							<input alt="auto_assign-modify?entity.id=${v.id}&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=290&width=400&modal=false"
						           class="thickbox" name="modify" type="button" value="修改" title="修改"/>
							<input type="button" name="del" onclick="manager_delete(${v.id})" value="删除" />
						</td>
					</tr>
					</c:if>
				</c:forEach>
			</table>
		</td>
	</tr>
	</c:if>
	
	
<!-- 交通组自动分单配置 -->
<c:if test="${isTraffic || isDev || isComp}">
	<tr>
		<th width="160" align="right">交通组自动分单配置：</th>
		<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="fb" width="120">人员 </td>
					<td><input title="添加"
						alt="auto_assign-add?type=1&tourTimeNode=6&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=290&width=400&modal=false"
						class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" />
					</td>
				</tr>
				<c:forEach items="${complaintList}" var="v">
					<c:if test="${v.tourTimeNode==6}">
					<tr <c:if test="${v.delFlag==1}">style='color: #666666'</c:if>>
						<td>${v.userName}</td>
						<td><input type="button" name="del" onclick="manager_del(${v.id},${v.delFlag})" <c:if test="${v.delFlag==1}"> style="color:gray;"</c:if> 
							value="<c:if test="${v.delFlag==0}">关闭</c:if><c:if test="${v.delFlag==1}">开启</c:if>" " />
							<input type="button" name="del" onclick="manager_delete(${v.id})" value="删除" />
						</td>
					</tr>
					</c:if>
				</c:forEach>
			</table>
		</td>
	</tr>
</c:if>
	
	<!-- 酒店组自动分单配置 -->
<c:if test="${isHotel || isDev || isComp}">
	<tr>
		<th width="160" align="right">酒店组自动分单配置：</th>
		<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="fb" width="120">人员 </td>
					<td class="fb" width="125">产品品类</td>
					<td class="fb" width="125">投诉级别</td>
					
					<td><input title="添加"
						alt="auto_assign-add?type=1&tourTimeNode=5&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=290&width=400&modal=false"
						class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" />
					</td>
				</tr>
				<c:forEach items="${complaintList}" var="v">
					<c:if test="${v.tourTimeNode==5}">
					<tr <c:if test="${v.delFlag==1}">style='color: #666666'</c:if>>
						<td>${v.userName}</td>
						<td align="left">${v.productCategory}</td>
						<td>
							<c:if test="${v.complaintLevel1Flag == 1}">一级；</c:if>
							<c:if test="${v.complaintLevel2Flag == 1}">二级；</c:if>
							<c:if test="${v.complaintLevel3Flag == 1}">三级；</c:if>
						</td>
						<td><input type="button" name="del" onclick="manager_del(${v.id},${v.delFlag})" <c:if test="${v.delFlag==1}"> style="color:gray;"</c:if> 
							value="<c:if test="${v.delFlag==0}">关闭</c:if><c:if test="${v.delFlag==1}">开启</c:if>" " />
							<input alt="auto_assign-modify?entity.id=${v.id}&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=290&width=400&modal=false"
						           class="thickbox" name="modify" type="button" value="修改" title="修改"/>
							<input type="button" name="del" onclick="manager_delete(${v.id})" value="删除" />
						</td>
					</tr>
					</c:if>
				</c:forEach>
			</table>
		</td>
	</tr>
	</c:if>
	
	<!-- 分销机票组自动分单配置 -->
	<c:if test="${isDistribute || isDev || isComp}">
	<tr>
		<th width="160" align="right">分销机票组自动分单配置：</th>
		<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="fb" width="120">人员 </td>
					<td class="fb" width="125">投诉级别</td>
					
					<td><input title="添加"
						alt="auto_assign-add?type=1&tourTimeNode=7&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=290&width=400&modal=false"
						class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" />
					</td>
				</tr>
				<c:forEach items="${complaintList}" var="v">
					<c:if test="${v.tourTimeNode==7}">
					<tr <c:if test="${v.delFlag==1}">style='color: #666666'</c:if>>
						<td>${v.userName}</td>
						<td>
							<c:if test="${v.complaintLevel1Flag == 1}">一级；</c:if>
							<c:if test="${v.complaintLevel2Flag == 1}">二级；</c:if>
							<c:if test="${v.complaintLevel3Flag == 1}">三级；</c:if>
						</td>
						<td><input type="button" name="del" onclick="manager_del(${v.id},${v.delFlag})" <c:if test="${v.delFlag==1}"> style="color:gray;"</c:if> 
							value="<c:if test="${v.delFlag==0}">关闭</c:if><c:if test="${v.delFlag==1}">开启</c:if>" " />
							<input alt="auto_assign-modify?entity.id=${v.id}&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=290&width=400&modal=false"
						           class="thickbox" name="modify" type="button" value="修改" title="修改"/>
							<input type="button" name="del" onclick="manager_delete(${v.id})" value="删除" />
						</td>
					</tr>
					</c:if>
				</c:forEach>
			</table>
		</td>
	</tr>
	</c:if>
	
       <c:if test="${isQc || isDev}">
    <tr>
		<th width="160" align="right">质检自动分单配置：</th>
		<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="fb" width="100">部门</td>
					<td class="fb" width="100">人员</td>
					<td class="fb">配置详情</td>
					<c:if test="${isQc || isDev}">
						<td><input title="配置事业部"
							alt="auto_assign-configureDept?type=2&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=450&height=450&width=650&modal=false"
							class="thickbox pd5 mr10 blue" name="input" type="button" value="配置事业部" />
						</td>
					</c:if>
					<td><input title="添加"
						alt="auto_assign-add?type=2&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=450&width=800&modal=false"
						class="thickbox pd5 mr10 blue" name="input" type="button" value="添加人员" />
					</td>
				</tr>
				<c:forEach items="${qcList}" var="v">
					<tr>
						<td>${v.departmentName}</td>
						<td>${v.userName}</td>
						<td>
							<table>
								<c:if test="${v.cfgQcList.size() > 0}">
								<tr><th width="70">普&nbsp;&nbsp;&nbsp;&nbsp;通</th>
								<td>
							    <table>
							        <c:forEach items="${v.cfgQcList}" var="qc">
							        <tr>
							            <th width="100px">${qc.depName}</th>
							            <td width="700px">
							               <table>
							                   <tr>
							                       <td>${qc.depNames}</td>
							                   </tr>
							               </table> 
							            </td>
							        </tr>
							        </c:forEach>
							        
							     </table>
							     </td>
							    </tr>
							    </c:if>
							    <c:if test="${v.cfgQcSpList.size() > 0}">
							    <tr title="微博,一级,媒体,旅游局"><th width="70">特&nbsp;&nbsp;&nbsp;&nbsp;殊</th>
								<td>
							     <table>
							        <c:forEach items="${v.cfgQcSpList}" var="qc">
							        <tr>
							            <th width="100px">${qc.depName}</th>
							            <td width="700px">
							               <table>
							                   <tr>
							                       <td>${qc.depNames}</td>
							                   </tr>
							               </table> 
							            </td>
							        </tr>
							        </c:forEach>
							        
							    </table>
							    </td>
							    </tr>
							    </c:if>
							    <tr>
							    	<th width="70">无订单</th>
									<td>
										<c:if test="${v.noOrdFlag == 0}">不处理</c:if>
										<c:if test="${v.noOrdFlag == 1}">处理</c:if>
									</td>
								</tr>
						    </table>
						</td>
						<td colspan="2" align="center">
						    <input type="button" name="del" onclick="manager_del(${v.id},${v.delFlag})" <c:if test="${v.delFlag==1}"> style="color:gray;"</c:if>
							value="<c:if test="${v.delFlag==0}">关闭</c:if><c:if test="${v.delFlag==1}">开启</c:if>" " />
							<input alt="auto_assign-modifyQc?entity.id=${v.id}&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=500&width=750&modal=false"
						           class="thickbox" name="modify" type="button" value="修改" title="修改"/>
							<input type="button" name="del" onclick="manager_delete(${v.id})" value="删除" />
						</td>
					</tr>
				</c:forEach>
			</table></td>
	</tr>
	</c:if>
	<c:if test="${ isDev|| isOQ}">
     <tr>
		<th width="160" align="right">在线问答分单配置：</th>
		<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="fb" width="120">部门</td>
					<td class="fb" width="120">人员</td>
					<td><input title="添加"
						alt="auto_assign-add?type=5&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=270&width=400&modal=false"
						class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" />
					</td>
				</tr>
				<c:forEach items="${knowledgeList}" var="v">
					<tr>
						<td>${v.departmentName}</td>
						<td>${v.userName}</td>
						<td><input type="button" name="del" onclick="manager_del(${v.id},${v.delFlag})" <c:if test="${v.delFlag==1}"> style="color:gray;"</c:if>
							value="<c:if test="${v.delFlag==0}">关闭</c:if><c:if test="${v.delFlag==1}">开启</c:if>" " />
							<input type="button" name="del" onclick="manager_delete(${v.id})" value="删除" />
							</td>
					</tr>
				</c:forEach>
			</table></td>
	</tr>
    </c:if>
    <!-- 会员顾问组自动分单配置 -->
<c:if test="${isVipDepart || isDev || isComp}">
	<tr>
		<th width="160" align="right">会员顾问组自动分单配置：</th>
		<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="fb" width="120">人员 </td>
					<td><input title="添加"
						alt="auto_assign-add?type=1&tourTimeNode=8&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=290&width=400&modal=false"
						class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" />
					</td>
				</tr>
				<c:forEach items="${complaintList}" var="v">
					<c:if test="${v.tourTimeNode==8}">
					<tr <c:if test="${v.delFlag==1}">style='color: #666666'</c:if>>
						<td>${v.userName}</td>
						<td><input type="button" name="del" onclick="manager_del(${v.id},${v.delFlag})" <c:if test="${v.delFlag==1}"> style="color:gray;"</c:if> 
							value="<c:if test="${v.delFlag==0}">关闭</c:if><c:if test="${v.delFlag==1}">开启</c:if>" " />
							<input type="button" name="del" onclick="manager_delete(${v.id})" value="删除" />
						</td>
					</tr>
					</c:if>
				</c:forEach>
			</table>
		</td>
	</tr>
</c:if>
</table>
</form>
</div>
<div id="002" class="tab_part">
</div>
