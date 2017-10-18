<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<link href="${CONFIG.res_url}css/thickbox.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${CONFIG.res_url}script/thickbox-compressed.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}script/complaint/complaint/appoint_manager.js" ></script> 
<script type="text/javascript">

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
		$("#form").submit();
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
			<li class="" onclick="tabFuncdd('#001',this)">
			<s class="rc-l"></s><s class="rc-r"></s><a href="appoint_manager">负责人</a>
			</li>
			<li class="menu_on"><s class="rc-l"></s>
			<s class="rc-r"></s><a href="#">邮件收件人</a>
			</li>
		</ul>
	</div>
	<div id="002" class="tab_part">
		<form name="form" id="form" method="post"
			enctype="multipart/form-data"
			action="${manageUrl}-del" onSubmit="">
			
			<input type="hidden" name="id" id="id" value="" />
			<table class="datatable">
				<tr>
					<th width="130" align="right" colspan="2" >一级投诉说明单：</th>
					<td>
						<table>
							<tr>
								
								<td class="fb" width="120">人员</td>
								<td width="120"><input title="添加人员"
									alt="receiver_email-add?type=1&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=270&width=400&modal=false"
									class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" />
								</td>
							</tr>
							<c:forEach items="${emailList1 }" var="v">
								<tr>
									
									<td>${v.userName }</td>
									<td><input type="button" name="del" onclick="manager_del(${v.id })"
										value="删除" /></td>
								</tr>
							</c:forEach>
						</table>
					</td>
					<td valign="top">
					<table style="padding-top: 5px;">
						<tr valign="top">
							<td class="fb" width="180px;">邮件组</td>
							<td class="fb" width="120px;"></td>
							<td><input title="添加邮件组"
							alt="receiver_email-addGroup?type=1&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=270&width=400&modal=false" 
							class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" /></td>
						</tr>
						<c:forEach items="${emailGroupList1 }" var="v">
							<tr>
								<td>${v.userMail }</td>
								<td>${v.departmentName }</td>
								<td><input type="button" name="del" onclick="manager_del(${v.id })"
									value="删除" /></td>
							</tr>
						</c:forEach>
					</table>
					</td>
				</tr>

				<tr>
					<th width="130" align="right" colspan="2" >二级投诉说明单：</th>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								
								<td class="fb" width="120">人员</td>
								<td width="120"><input title="添加人员"
									alt="receiver_email-add?type=2&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=270&width=400&modal=false"
									class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" />
								</td>
							</tr>
							<c:forEach items="${emailList2 }" var="v">
								<tr>
									
									<td>${v.userName }</td>
									<td><input type="button" name="del" onclick="manager_del(${v.id })"
										value="删除" /></td>
								</tr>
							</c:forEach>
						</table>
					</td>
					<td valign="top">
					<table style="padding-top: 5px;">
						<tr valign="top">
							<td class="fb" width="180px;">邮件组</td>
							<td class="fb" width="120px;"></td>
							<td><input title="添加邮件组"
							 alt="receiver_email-addGroup?type=2&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=270&width=400&modal=false"
							 class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" /></td>
						</tr>
						<c:forEach items="${emailGroupList2 }" var="v">
							<tr>
								<td>${v.userMail }</td>
								<td>${v.departmentName }</td>
								<td><input type="button" name="del" onclick="manager_del(${v.id })"
									value="删除" /></td>
							</tr>
						</c:forEach>
					</table>
					</td>
				</tr>

				<tr>
					<th width="130" align="right" colspan="2" >三级投诉说明单：</th>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								
								<td class="fb" width="120">人员</td>
								<td width="120"><input title="添加人员"
									alt="receiver_email-add?type=3&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=270&width=400&modal=false"
									class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" />
								</td>
							</tr>
							<c:forEach items="${emailList3 }" var="v">
								<tr>
									
									<td>${v.userName }</td>
									<td><input type="button" name="del" onclick="manager_del(${v.id })"
										value="删除" /></td>
								</tr>
							</c:forEach>
						</table>
					</td>
					<td valign="top">
					<table style="padding-top: 5px;">
						<tr valign="top">
							<td class="fb" width="180px;">邮件组</td>
							<td class="fb" width="120px;"></td>
							<td><input title="添加邮件组"
							 alt="receiver_email-addGroup?type=3&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=270&width=400&modal=false"
							 class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" /></td>
						</tr>
						<c:forEach items="${emailGroupList3 }" var="v">
							<tr>
								<td>${v.userMail }</td>
								<td>${v.departmentName }</td>
								<td><input type="button" name="del" onclick="manager_del(${v.id })"
									value="删除" /></td>
							</tr>
						</c:forEach>
					</table>
					</td>
				</tr>
				<tr> <th rowspan="4" width="70">出游前</th> </tr>
				 
				  <tr>
				  	<th align="right" width="60">一级：</th>
				  	<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								
								<td class="fb" width="120">人员</td>
								<td width="120"><input title="添加人员"
									alt="receiver_email-add?type=1&orderState=1&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=270&width=400&modal=false"
									class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" />
								</td>
							</tr>
							<c:forEach items="${emailListBefore1 }" var="v">
								<tr>
									
									<td>${v.userName }</td>
									<td><input type="button" name="del" onclick="manager_del(${v.id })"
										value="删除" /></td>
								</tr>
							</c:forEach>
						</table>
					</td>
				  	<td>
					  	<table style="padding-top: 5px;">
							<tr valign="top">
								<td class="fb" width="180px;">邮件组</td>
								<td class="fb" width="120px;"></td>
								<td><input title="添加邮件组"
								 alt="receiver_email-addGroup?type=1&orderState=1&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=270&width=400&modal=false"
								 class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" /></td>
							</tr>
							<c:forEach items="${emailListBeforeGroup1 }" var="v">
								<tr>
									<td>${v.userMail }</td>
									<td>${v.departmentName }</td>
									<td><input type="button" name="del" onclick="manager_del(${v.id })"
										value="删除" /></td>
								</tr>
							</c:forEach>
						</table>
					</td>
				  </tr>
				  <tr>
				  	<th align="right" width="60">二级：</th>
				  	<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								
								<td class="fb" width="120">人员</td>
								<td width="120"><input title="添加人员"
									alt="receiver_email-add?type=2&orderState=1&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=270&width=400&modal=false"
									class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" />
								</td>
							</tr>
							<c:forEach items="${emailListBefore2 }" var="v">
								<tr>
									
									<td>${v.userName }</td>
									<td><input type="button" name="del" onclick="manager_del(${v.id })"
										value="删除" /></td>
								</tr>
							</c:forEach>
						</table>
					</td>
				  	<td>
					  	<table style="padding-top: 5px;">
							<tr valign="top">
								<td class="fb" width="180px;">邮件组</td>
								<td class="fb" width="120px;"></td>
								<td><input title="添加邮件组"
								 alt="receiver_email-addGroup?type=2&orderState=1&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=270&width=400&modal=false"
								 class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" /></td>
							</tr>
							<c:forEach items="${emailListBeforeGroup2 }" var="v">
								<tr>
									<td>${v.userMail }</td>
									<td>${v.departmentName }</td>
									<td><input type="button" name="del" onclick="manager_del(${v.id })"
										value="删除" /></td>
								</tr>
							</c:forEach>
						</table>
					</td>
				  </tr>
				  <tr>
				  	<th align="right" width="60">三级：</th>
				  	<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								
								<td class="fb" width="120">人员</td>
								<td width="120"><input title="添加人员"
									alt="receiver_email-add?type=3&orderState=1&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=270&width=400&modal=false"
									class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" />
								</td>
							</tr>
							<c:forEach items="${emailListBefore3 }" var="v">
								<tr>
									
									<td>${v.userName }</td>
									<td><input type="button" name="del" onclick="manager_del(${v.id })"
										value="删除" /></td>
								</tr>
							</c:forEach>
						</table>
					</td>
				  	<td>
					  	<table style="padding-top: 5px;">
							<tr valign="top">
								<td class="fb" width="180px;">邮件组</td>
								<td class="fb" width="120px;"></td>
								<td><input title="添加邮件组"
								 alt="receiver_email-addGroup?type=3&orderState=1&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=270&width=400&modal=false"
								 class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" /></td>
							</tr>
							<c:forEach items="${emailListBeforeGroup3 }" var="v">
								<tr>
									<td>${v.userMail }</td>
									<td>${v.departmentName }</td>
									<td><input type="button" name="del" onclick="manager_del(${v.id })"
										value="删除" /></td>
								</tr>
							</c:forEach>
						</table>
					</td>
				  </tr>

				
				  <tr> <th rowspan="4" width="70">出游中</th> </tr>
				 
				  <tr>
				  	<th align="right" width="60">一级：</th>
				  	<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								
								<td class="fb" width="120">人员</td>
								<td width="120"><input title="添加人员"
									alt="receiver_email-add?type=1&orderState=2&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=270&width=400&modal=false"
									class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" />
								</td>
							</tr>
							<c:forEach items="${emailListIn1 }" var="v">
								<tr>
									
									<td>${v.userName }</td>
									<td><input type="button" name="del" onclick="manager_del(${v.id })"
										value="删除" /></td>
								</tr>
							</c:forEach>
						</table>
					</td>
				  	<td>
					  	<table style="padding-top: 5px;">
							<tr valign="top">
								<td class="fb" width="180px;">邮件组</td>
								<td class="fb" width="120px;"></td>
								<td><input title="添加邮件组"
								 alt="receiver_email-addGroup?type=1&orderState=2&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=270&width=400&modal=false"
								 class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" /></td>
							</tr>
							<c:forEach items="${emailListInGroup1 }" var="v">
								<tr>
									<td>${v.userMail }</td>
									<td>${v.departmentName }</td>
									<td><input type="button" name="del" onclick="manager_del(${v.id })"
										value="删除" /></td>
								</tr>
							</c:forEach>
						</table>
					</td>
				  </tr>
				  <tr>
				  	<th align="right" width="60">二级：</th>
				  	<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								
								<td class="fb" width="120">人员</td>
								<td width="120"><input title="添加人员"
									alt="receiver_email-add?type=2&orderState=2&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=270&width=400&modal=false"
									class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" />
								</td>
							</tr>
							<c:forEach items="${emailListIn2 }" var="v">
								<tr>
									
									<td>${v.userName }</td>
									<td><input type="button" name="del" onclick="manager_del(${v.id })"
										value="删除" /></td>
								</tr>
							</c:forEach>
						</table>
					</td>
				  	<td>
					  	<table style="padding-top: 5px;">
							<tr valign="top">
								<td class="fb" width="180px;">邮件组</td>
								<td class="fb" width="120px;"></td>
								<td><input title="添加邮件组"
								 alt="receiver_email-addGroup?type=2&orderState=2&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=270&width=400&modal=false"
								 class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" /></td>
							</tr>
							<c:forEach items="${emailListInGroup2 }" var="v">
								<tr>
									<td>${v.userMail }</td>
									<td>${v.departmentName }</td>
									<td><input type="button" name="del" onclick="manager_del(${v.id })"
										value="删除" /></td>
								</tr>
							</c:forEach>
						</table>
					</td>
				  </tr>
				  <tr>
				  	<th align="right" width="60">三级：</th>
				  	<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								
								<td class="fb" width="120">人员</td>
								<td width="120"><input title="添加人员"
									alt="receiver_email-add?type=3&orderState=2&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=270&width=400&modal=false"
									class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" />
								</td>
							</tr>
							<c:forEach items="${emailListIn3 }" var="v">
								<tr>
									
									<td>${v.userName }</td>
									<td><input type="button" name="del" onclick="manager_del(${v.id })"
										value="删除" /></td>
								</tr>
							</c:forEach>
						</table>
					</td>
				  	<td>
					  	<table style="padding-top: 5px;">
							<tr valign="top">
								<td class="fb" width="180px;">邮件组</td>
								<td class="fb" width="120px;"></td>
								<td><input title="添加邮件组"
								 alt="receiver_email-addGroup?type=3&orderState=2&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=270&width=400&modal=false"
								 class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" /></td>
							</tr>
							<c:forEach items="${emailListInGroup3 }" var="v">
								<tr>
									<td>${v.userMail }</td>
									<td>${v.departmentName }</td>
									<td><input type="button" name="del" onclick="manager_del(${v.id })"
										value="删除" /></td>
								</tr>
							</c:forEach>
						</table>
					</td>
				  </tr>
				  
					
				 <tr> <th rowspan="4" width="70">出游后</th> </tr>
				 
				  <tr>
				  	<th align="right" width="60">一级：</th>
				  	<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								
								<td class="fb" width="120">人员</td>
								<td width="120"><input title="添加人员"
									alt="receiver_email-add?type=1&orderState=3&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=270&width=400&modal=false"
									class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" />
								</td>
							</tr>
							<c:forEach items="${emailListAfter1 }" var="v">
								<tr>
									
									<td>${v.userName }</td>
									<td><input type="button" name="del" onclick="manager_del(${v.id })"
										value="删除" /></td>
								</tr>
							</c:forEach>
						</table>
					</td>
				  	<td>
					  	<table style="padding-top: 5px;">
							<tr valign="top">
								<td class="fb" width="180px;">邮件组</td>
								<td class="fb" width="120px;"></td>
								<td><input title="添加邮件组"
								 alt="receiver_email-addGroup?type=1&orderState=3&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=270&width=400&modal=false"
								 class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" /></td>
							</tr>
							<c:forEach items="${emailListAfterGroup1 }" var="v">
								<tr>
									<td>${v.userMail }</td>
									<td>${v.departmentName }</td>
									<td><input type="button" name="del" onclick="manager_del(${v.id })"
										value="删除" /></td>
								</tr>
							</c:forEach>
						</table>
					</td>
				  </tr>
				  <tr>
				  	<th align="right" width="60">二级：</th>
				  	<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								
								<td class="fb" width="120">人员</td>
								<td width="120"><input title="添加人员"
									alt="receiver_email-add?type=2&orderState=3&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=270&width=400&modal=false"
									class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" />
								</td>
							</tr>
							<c:forEach items="${emailListAfter2 }" var="v">
								<tr>
									
									<td>${v.userName }</td>
									<td><input type="button" name="del" onclick="manager_del(${v.id })"
										value="删除" /></td>
								</tr>
							</c:forEach>
						</table>
					</td>
				  	<td>
					  	<table style="padding-top: 5px;">
							<tr valign="top">
								<td class="fb" width="180px;">邮件组</td>
								<td class="fb" width="120px;"></td>
								<td><input title="添加邮件组"
								 alt="receiver_email-addGroup?type=2&orderState=3&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=270&width=400&modal=false"
								 class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" /></td>
							</tr>
							<c:forEach items="${emailListAfterGroup2 }" var="v">
								<tr>
									<td>${v.userMail }</td>
									<td>${v.departmentName }</td>
									<td><input type="button" name="del" onclick="manager_del(${v.id })"
										value="删除" /></td>
								</tr>
							</c:forEach>
						</table>
					</td>
				  </tr>
				  <tr>
				  	<th align="right" width="60">三级：</th>
				  	<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								
								<td class="fb" width="120">人员</td>
								<td width="120"><input title="添加人员"
									alt="receiver_email-add?type=3&orderState=3&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=270&width=400&modal=false"
									class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" />
								</td>
							</tr>
							<c:forEach items="${emailListAfter3 }" var="v">
								<tr>
									
									<td>${v.userName }</td>
									<td><input type="button" name="del" onclick="manager_del(${v.id })"
										value="删除" /></td>
								</tr>
							</c:forEach>
						</table>
					</td>
				  	<td>
					  	<table style="padding-top: 5px;">
							<tr valign="top">
								<td class="fb" width="180px;">邮件组</td>
								<td class="fb" width="120px;"></td>
								<td><input title="添加邮件组"
								 alt="receiver_email-addGroup?type=3&orderState=3&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=270&width=400&modal=false"
								 class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" /></td>
							</tr>
							<c:forEach items="${emailListAfterGroup3 }" var="v">
								<tr>
									<td>${v.userMail }</td>
									<td>${v.departmentName }</td>
									<td><input type="button" name="del" onclick="manager_del(${v.id })"
										value="删除" /></td>
								</tr>
							</c:forEach>
						</table>
					</td>
				  </tr>
				
				<tr>
					<th width="130" align="right" colspan="2" >质检报告：</th>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								
								<td class="fb" width="120">人员</td>
								<td><input title="添加人员"
									alt="receiver_email-add?type=6&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=270&width=400&modal=false"
									class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" />
								</td>
							</tr>
							<c:forEach items="${emailList6 }" var="v">
								<tr>
									
									<td>${v.userName }</td>
									<td><input type="button" name="del" onclick="manager_del(${v.id })"
										value="删除" /></td>
								</tr>
							</c:forEach>
						</table>
					</td>
					<td valign="top">
					<table style="padding-top: 5px;">
						<tr valign="top">
							<td class="fb" width="180px;">邮件组</td>
							<td class="fb" width="120px;"></td>
							<td><input title="添加邮件组"
							 alt="receiver_email-addGroup?type=6&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=270&width=400&modal=false"
							 class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" /></td>
						</tr>
						<c:forEach items="${emailGroupList6 }" var="v">
							<tr>
								<td>${v.userMail }</td>
								<td>${v.departmentName }</td>
								<td><input type="button" name="del" onclick="manager_del(${v.id })"
									value="删除" /></td>
							</tr>
						</c:forEach>
					</table>
					</td>
				</tr>
				
				<tr>
					<th width="130" align="right" colspan="2" >网络微博负责人：</th>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								
								<td class="fb" width="120">人员</td>
								<td><input title="添加人员"
									alt="receiver_email-add?type=7&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=270&width=400&modal=false"
									class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" />
								</td>
							</tr>
							<c:forEach items="${emailList7 }" var="v">
								<tr>
									
									<td>${v.userName }</td>
									<td><input type="button" name="del" onclick="manager_del(${v.id })"
										value="删除" /></td>
								</tr>
							</c:forEach>
						</table>
					</td>
					<td valign="top">
					<table style="padding-top: 5px;">
						<tr valign="top">
							<td class="fb" width="180px;">邮件组</td>
							<td class="fb" width="120px;"></td>
							<td><input title="添加邮件组"
							 alt="receiver_email-addGroup?type=7&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=270&width=400&modal=false"
							 class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" /></td>
						</tr>
						<c:forEach items="${emailGroupList7 }" var="v">
							<tr>
								<td>${v.userMail }</td>
								<td>${v.departmentName }</td>
								<td><input type="button" name="del" onclick="manager_del(${v.id })"
									value="删除" /></td>
							</tr>
						</c:forEach>
					</table>
					</td>
				</tr>
				
				<tr>
					<th width="130" align="right" colspan="2" >马代采购人员：</th>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								
								<td class="fb" width="120">人员</td>
								<td><input title="添加人员"
									alt="receiver_email-add?type=8&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=270&width=400&modal=false"
									class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" />
								</td>
							</tr>
							<c:forEach items="${madaiPurchase8 }" var="v">
								<tr>
									
									<td>${v.userName }</td>
									<td><input type="button" name="del" onclick="manager_del(${v.id })"
										value="删除" /></td>
								</tr>
							</c:forEach>
						</table>
					</td>
					<td valign="top">
					<table style="padding-top: 5px;">
						<tr valign="top">
							<td class="fb" width="180px;">邮件组</td>
							<td class="fb" width="120px;"></td>
							<td><input title="添加邮件组"
							 alt="receiver_email-addGroup?type=8&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=270&width=400&modal=false"
							 class="thickbox pd5 mr10 blue" name="input" type="button" value="添加" /></td>
						</tr>
						<c:forEach items="${madaiPurchaseGroup8 }" var="v">
							<tr>
								<td>${v.userMail }</td>
								<td>${v.departmentName }</td>
								<td><input type="button" name="del" onclick="manager_del(${v.id })"
									value="删除" /></td>
							</tr>
						</c:forEach>
					</table>
					</td>
				</tr>
				

			</table>
		</form>
	</div>

	<%@include file="/WEB-INF/html/foot.jsp" %>
