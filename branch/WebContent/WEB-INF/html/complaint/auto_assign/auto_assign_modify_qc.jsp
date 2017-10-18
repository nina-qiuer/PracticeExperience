<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp"%>
<style>
img{
	width: 10px;
	height: 10px;
}
</style>
<SCRIPT type="text/javascript">
function onSubmitClicked() {
	var param = $('#form').serialize();
	$.ajax({
	type: "POST",
	async:false,
	url: "${manageUrl}-doModifyQc",
	data: param,
	success: function(data){
		self.parent.tb_remove();
     }
   });
   window.parent.location.reload();
}

function hve_display(t_id,i_id){//显示隐藏程序
	var tr = document.getElementById(t_id);//表格ID
	var img = document.getElementById(i_id);//图片ID
	var on_img='${CONFIG.res_url}images/icon/default/up.png';//打开时图片
	var off_img='${CONFIG.res_url}images/icon/default/down.png';//隐藏时图片
	if (tr.style.display == "none"){
		tr.style.display="";//切换为显示状态
		img.src=off_img;
	}else{
		tr.style.display="none";//切换为隐藏状态
		img.src=on_img;
	}
}

function chooseAll(id,checked)
{
	if(checked)
		$("td.child_"+id+" :checkbox").attr("checked",true); 
	else
		$("td.child_"+id+" :checkbox").attr("checked",false); 
		
}
</SCRIPT>
<style type="text/css">
	ul.ztree {margin-top: 10px;border: 1px solid #617775;background: #f0f6e4;width:220px;height:360px;overflow-y:scroll;overflow-x:auto;}
</style>
</HEAD>
<BODY>
<form name="form" id="form" method="post" enctype="multipart/form-data">
	<input type="hidden" name="entity.id" id="id" value="${entity.id}" />
	<table width="100%" class="datatable">
		<tr>
			<th>部门</th>
			<td>${entity.departmentName }</td>
		</tr>
		<tr>
			<th>人员</th>
			<td>${entity.userName }</td>
		</tr>
		<tr onclick="hve_display('notr','imgno')">
			<th>普通</th>
			<td>
				<img id="imgno" src="${CONFIG.res_url}images/icon/default/down.png">　非（微博,一级,媒体,旅游局）
			</td>
		</tr>
		<tr id="notr">
			<th>普&nbsp;&nbsp;&nbsp;&nbsp;通</th>
			<td><table width="100%">
					<c:forEach items="${deptList}" var="vv" varStatus="ss">
						<tr>
							<th><input type="checkbox" onclick="chooseAll(${vv.id},this.checked)"/>${vv.depName }</th>
							<td class="child_${vv.id}">
								<table width="100%">
									<tr>
										<td>
											<table width="100%">
												<tr>
												<c:forEach items="${vv.childDept }" var="v" varStatus="st">
												<c:if test="${st.count % 5 == 1 }">
												</tr>
												<tr>
												</c:if>
													<td style="border: 0px;">
													<label>
														<c:set var="iscontain" value="false"/>
														<c:forEach items="${entity.cfgQcList }" var="cfgQc" varStatus="st">
															<c:if test="${fn:contains(cfgQc.depIds,v.id)}">
																<c:set var="iscontain" value="true"/>
															</c:if>
														</c:forEach>
														<input type="checkbox" name="entity.depIds[${ss.index}]" value="${vv.depName}@${v.id }" <c:if test="${iscontain}">checked='checked'</c:if>>${v.depName }
													</label>
													</td>
												</c:forEach>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</c:forEach>
				</table></td>
		</tr>
		<tr onclick="hve_display('sptr','imgsp')">
			<th>特殊</th>
			<td>
				<img id="imgsp" src="${CONFIG.res_url}images/icon/default/up.png">　微博,一级,媒体,旅游局
			</td>
		</tr>
		<tr id="sptr" style="display: none">
			<th title="微博,一级,媒体,旅游局">特&nbsp;&nbsp;&nbsp;&nbsp;殊</th>
			<td><table width="100%">
					<c:forEach items="${deptList}" var="vv" varStatus="ss">
						<tr>
							<th>${vv.depName }</th>
							<input type="hidden" name="entity.depNames[${ss.index}]" value="${vv.depName }">
							<td>
								<table width="100%">
									<tr>
										<td>
											<table width="100%">
												<tr>
												<c:forEach items="${vv.childDept }" var="v" varStatus="st">
												<c:if test="${st.count % 5 == 1 }">
												</tr>
												<tr>
												</c:if>
													<td style="border: 0px;">
													<label>
														<c:set var="iscontain" value="false"/>
														<c:forEach items="${entity.cfgQcSpList }" var="cfgQc" varStatus="st">
															<c:if test="${fn:contains(cfgQc.spDepIds,v.id)}">
																<c:set var="iscontain" value="true"/>
															</c:if>
														</c:forEach>
														<input type="checkbox" name="entity.spDepIds[${ss.index}]" value="${vv.depName }@${v.id }" <c:if test="${iscontain}">checked='checked'</c:if>>${v.depName }
													</label>
													</td>
												</c:forEach>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</c:forEach>
				</table></td>
		</tr>
		<tr>
			<th>无订单</th>
			<td>
				<input type="radio" name="entity.noOrdFlag" value="0" <c:if test="${entity.noOrdFlag == 0}">checked='checked'</c:if>>不处理　　
				<input type="radio" name="entity.noOrdFlag" value="1" <c:if test="${entity.noOrdFlag == 1}">checked='checked'</c:if>>处理
			</td>
		</tr>
		<tr>
			<th></th>
			<td>
				<input type="button"  class="pd5" value="修改" onclick="return onSubmitClicked();" /> 
				<input type="button" class="pd5" value="取消" onclick="self.parent.tb_remove();" />
			</td>
		</tr>
	</table>
</form>
</BODY>
