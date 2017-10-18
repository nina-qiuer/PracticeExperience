<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<link href="${CONFIG.res_url}css/thickbox.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${CONFIG.res_url}script/thickbox-compressed.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}script/complaint/complaint/appoint_manager.js" ></script> 
<script type="text/javascript">
//var varib = '${beforeList}'.split("");
//var abc =[];
//alert(typeof(varib));
//标签页控制
function tabFuncdd(showId,navObj){ 
	$(".tab_part").hide();
	$("#pici_tab .menu_on").removeClass("menu_on");
	$(navObj).addClass("menu_on");
	$(showId).show();
	return false;
}
</script>
	<script type="text/javascript">
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
	
	//删除一条负责人数据
	function manager_del(id){
		$("#id").attr("value",id);
		var result=confirm("确定删除此条数据？");
		if(result==true){
			var param = $('#form').serialize();
			$.ajax({
			type: "POST",
			url: "${manageUrl}-del",
			data: param,
			success: function(data){
				if(data.success) {
					window.location.reload();
				}
		}
		})
		}
	}
	</script>
	<style type="text/css">
		.datatable td table{border-collapse:collapse;}
		.datatable td td{border:0 none;}
	</style>

</HEAD>
<BODY>
	
	<div id="pici_tab" class="clear">
		<ul>
			<li class="menu_on" onclick="tabFuncdd('#001',this)">
			<s class="rc-l"></s><s class="rc-r"></s><a href="#">负责人</a>
			</li>
			<li onclick=""><s class="rc-l"></s>
			<s class="rc-r"></s><a href="email_config">邮件配置</a>
			</li>
		</ul>
	</div>
	<div id="001" class="tab_part">
		<form name="form" id="form" method="post"
			enctype="multipart/form-data"
			action="${manageUrl}-del" onSubmit="">
			
			<input type="hidden" name="id" id="id" value="" />
			<table width="100%" class="datatable">
			<c:if test="${showbeforeList eq '1' }">
				<tr>
					<th width="150" align="right">售前组客服负责人：</th>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td class="fb" width="120"> 部门</td>
								<td class="fb" width="120">人员 </td>
								<td><input title="添加"
									alt="appoint_manager-add?type=1&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=270&width=400&modal=false"
									class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" />
								</td>
							</tr>
							<c:forEach items="${beforeList }" var="v">
								<tr>
									<td>${v.departmentName }</td>
									<td>${v.userName }</td>
									<td><input type="button" name="del" onclick="manager_del(${v.id })"
										value="删除" /></td>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
				</c:if>
				<tr>
					<th width="150" align="right">售后组客服负责人：</th>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td class="fb" width="120">部门</td>
								<td class="fb" width="120">人员</td>
								<td><input title="添加"
									alt="appoint_manager-add?type=2&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=270&width=400&modal=false"
									class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" />
								</td>
							</tr>
							<c:forEach items="${middleList}" var="v">
								<tr>
									<td>${v.departmentName}</td>
									<td>${v.userName }</td>
									<td><input type="button" name="del" onclick="manager_del(${v.id })"
										value="删除" /></td>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>

				<tr>
					<th width="150" align="right">售后组借调人员：</th>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td class="fb" width="120">部门</td>
								<td class="fb" width="120">人员</td>
								<td><input title="添加"
									alt="appoint_manager-add?type=11&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=270&width=400&modal=false"
									class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" />
								</td>
							</tr>
							<c:forEach items="${middleTransferList}" var="v">
								<tr>
									<td>${v.departmentName}</td>
									<td>${v.userName }</td>
									<td><input type="button" name="del" onclick="manager_del(${v.id })" value="删除" /></td>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
				
				<tr>
					<th width="150" align="right">资深组借调人员：</th>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td class="fb" width="120">部门</td>
								<td class="fb" width="120">人员</td>
								<td><input title="添加"
									alt="appoint_manager-add?type=13&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=270&width=400&modal=false"
									class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" />
								</td>
							</tr>
							<c:forEach items="${aftTransferList}" var="v">
								<tr>
									<td>${v.departmentName}</td>
									<td>${v.userName }</td>
									<td><input type="button" name="del" onclick="manager_del(${v.id })" value="删除" /></td>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>

				<tr>
					<th width="150" align="right">资深组客服负责人：</th>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td class="fb" width="120">部门</td>
								<td class="fb" width="120">人员</td>
								<td><input title="添加"
									alt="appoint_manager-add?type=3&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=270&width=400&modal=false"
									class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" />
								</td>
							</tr>
							<c:forEach items="${afterList }" var="v">
								<tr>
									<td>${v.departmentName }</td>
									<td>${v.userName }</td>
									<td><input type="button" name="del" onclick="manager_del(${v.id })"
										value="删除" /></td>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>

				<tr>
					<th width="150" align="right">出游前客户服务负责人：</th>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td class="fb" width="120">部门</td>
								<td class="fb" width="120">人员</td>
								<td><input title="添加"
									alt="appoint_manager-add?type=5&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=270&width=400&modal=false"
									class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" />
								</td>
							</tr>
							<c:forEach items="${specialBeforeList }" var="v">
								<tr>
									<td>${v.departmentName }</td>
									<td>${v.userName }</td>
									<td><input type="button" name="del" onclick="manager_del(${v.id })"
										value="删除" /></td>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
				
				<tr>
					<th width="150" align="right">机票组负责人：</th>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td class="fb" width="120">部门</td>
								<td class="fb" width="120">人员</td>
								<td><input title="添加"
									alt="appoint_manager-add?type=6&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=270&width=400&modal=false"
									class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" />
								</td>
							</tr>
							<c:forEach items="${airTicketList }" var="v">
								<tr>
									<td>${v.departmentName }</td>
									<td>${v.userName }</td>
									<td><input type="button" name="del" onclick="manager_del(${v.id })"
										value="删除" /></td>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
				
				<tr>
					<th width="150" align="right">酒店组负责人：</th>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td class="fb" width="120">部门</td>
								<td class="fb" width="120">人员</td>
								<td><input title="添加"
									alt="appoint_manager-add?type=7&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=270&width=400&modal=false"
									class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" />
								</td>
							</tr>
							<c:forEach items="${hotelList }" var="v">
								<tr>
									<td>${v.departmentName }</td>
									<td>${v.userName }</td>
									<td><input type="button" name="del" onclick="manager_del(${v.id })"
										value="删除" /></td>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
				
				<tr>
					<th width="150" align="right">分销组负责人：</th>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td class="fb" width="120">部门</td>
								<td class="fb" width="120">人员</td>
								<td><input title="添加"
									alt="appoint_manager-add?type=8&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=270&width=400&modal=false"
									class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" />
								</td>
							</tr>
							<c:forEach items="${distributeList }" var="v">
								<tr>
									<td>${v.departmentName }</td>
									<td>${v.userName }</td>
									<td><input type="button" name="del" onclick="manager_del(${v.id })"
										value="删除" /></td>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
				
				<tr>
					<th width="150" align="right">交通组负责人：</th>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td class="fb" width="120">部门</td>
								<td class="fb" width="120">人员</td>
								<td><input title="添加"
									alt="appoint_manager-add?type=15&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=270&width=400&modal=false"
									class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" />
								</td>
							</tr>
							<c:forEach items="${trafficList }" var="v">
								<tr>
									<td>${v.departmentName }</td>
									<td>${v.userName }</td>
									<td><input type="button" name="del" onclick="manager_del(${v.id })"
										value="删除" /></td>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
				
				<c:if test="${showVipDepart == true}">
				<tr>
					<th width="150" align="right">会员顾问组负责人：</th>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td class="fb" width="120">部门</td>
								<td class="fb" width="120">人员</td>
								<td><input title="添加"
									alt="appoint_manager-add?type=17&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=270&width=400&modal=false"
									class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" />
								</td>
							</tr>
							<c:forEach items="${vipDepartOffierList }" var="v">
								<tr>
									<td>${v.departmentName }</td>
									<td>${v.userName }</td>
									<td><input type="button" name="del" onclick="manager_del(${v.id })"
										value="删除" /></td>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
				<tr>
					<th width="150" align="right">会员维护人三级组配置：</th>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td class="fb" width="240">部门</td>
								<td class="fb" width="60">部门ID</td>
								<td><input title="添加"
									alt="appoint_manager-add?type=16&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=270&width=400&modal=false"
									class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" />
								</td>
							</tr>
							<c:forEach items="${vipDepartGroupList }" var="v">
								<tr>
									<td>${v.departmentName }</td>
									<td>${v.departmentId }</td>
									<td><input type="button" name="del" onclick="manager_del(${v.id })"
										value="删除" /></td>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
				</c:if>
				
				<c:if test="${showbeforeList eq '1' }">
				<tr>
					<th width="150" align="right">短信回访开关负责人：</th>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td class="fb" width="120">部门</td>
								<td class="fb" width="120">人员</td>
								<td><input title="添加"
									alt="appoint_manager-add?type=14&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=270&width=400&modal=false"
									class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" />
								</td>
							</tr>
							<c:forEach items="${returnVisitList }" var="v">
								<tr>
									<td>${v.departmentName }</td>
									<td>${v.userName }</td>
									<td><input type="button" name="del" onclick="manager_del(${v.id })"
										value="删除" /></td>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
				<tr>
					<th width="150" align="right">质检负责人：</th>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td class="fb" width="120">部门</td>
								<td class="fb" width="120">人员</td>
								<td><input title="添加"
									alt="appoint_manager-add?type=4&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=270&width=400&modal=false"
									class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" />
								</td>
							</tr>
							<c:forEach items="${qtList}" var="v">
								<tr>
									<td>${v.departmentName}</td>
									<td>${v.userName }</td>
									<td><input type="button" name="del" onclick="manager_del(${v.id })"
										value="删除" /></td>
								</tr>
							</c:forEach>
						</table></td>
				</tr>
				<tr>
					<th width="150" align="right">发起投诉事业部：</th>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td class="fb" width="120"> 部门</td>
								<td class="fb" width="120">部门ID </td>
								<td><input title="添加"
									alt="appoint_manager-add?type=9&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=270&width=400&modal=false"
									class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" />
								</td>
							</tr>
							<c:forEach items="${departmentList }" var="v">
								<tr>
									<td>${v.departmentName }</td>
									<td>${v.departmentId }</td>
									<td><input type="button" name="del" onclick="manager_del(${v.id })"
										value="删除" /></td>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
				<tr>
					<th width="150" align="right">部门信息：</th>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td class="fb" width="120"> 部门</td>
								<td class="fb" width="120">部门ID </td>
								<td><input title="添加"
									alt="appoint_manager-addDepartment?type=10&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=270&width=400&modal=false"
									class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" />
								</td>
							</tr>
							<c:forEach items="${complaintDepList }" var="v">
								<tr>
									<td>${v.departmentName }</td>
									<td>${v.departmentId }</td>
									<td><input type="button" name="del" onclick="manager_del(${v.id })"
										value="删除" /></td>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
				<tr>
					<th width="150" align="right">研发负责人：</th>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td class="fb" width="120">部门</td>
								<td class="fb" width="120">人员</td>
								<td><input title="添加"
									alt="appoint_manager-add?type=12&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=270&width=400&modal=false"
									class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" />
								</td>
							</tr>
							<c:forEach items="${devList}" var="v">
								<tr>
									<td>${v.departmentName}</td>
									<td>${v.userName }</td>
									<td><input type="button" name="del" onclick="manager_del(${v.id })"
										value="删除" /></td>
								</tr>
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
	<%@include file="/WEB-INF/html/foot.jsp" %>
