<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>

<link href="${CONFIG.res_url}css/easydialog.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${CONFIG.res_url}/script/easydialog.min.js"></script>

<style type="text/css">
.datatable td table{border-collapse:collapse;}
.datatable td td{border:0 none;}
</style>

<script type="text/javascript">
//标签页控制
function tabFuncdd(showId,navObj){ 
	$(".tab_part").hide();
	$("#pici_tab .menu_on").removeClass("menu_on");
	$(navObj).addClass("menu_on");
	$(showId).show();
	return false;
}

function manager_del(id) {
	$("#id").attr("value",id);
	var result = confirm("确定删除此条数据？");
	if(result == true) {
		$("#form").submit();
	}
}
</script>
</HEAD>
<BODY>
<div id="pici_tab" class="clear">
	<ul>
		<li class="" onclick="tabFuncdd('#001',this)">
		<s class="rc-l"></s><s class="rc-r"></s><a href="appoint_manager">负责人</a>
		</li>
		<li class="menu_on"><s class="rc-l"></s>
		<s class="rc-r"></s><a href="#">邮件抄送人</a>
		</li>
	</ul>
</div>
<div class="tab_part">
<form name="form" id="form" method="post" enctype="multipart/form-data" action="${manageUrl}-delete">
	<input type="hidden" name="entity.id" id="id">
	<table class="datatable">
		<tr>
			<th width="130">邮箱名称</th>
			<th width="130">邮箱地址</th>
			<th>收件条件配置</th>
			<th><input title="添加邮件抄送人" class="pd5 mr10 blue" type="button" value="添加" onclick="easyDialog.open({container : 'ccEmailAddBox', overlay : false})"></th>
		</tr>
		<c:forEach items="${ccEmailList }" var="cc">
		<tr>
			<td>${cc.emailName }</td>
			<td>${cc.emailAddress }</td>
			<td>
			<c:forEach items="${cc.cfgList }" var="cfg">
				<table>
					<tr>
						<td>${cfg.compLevel }级；　</td>
						<td>${cfg.orderStates }；　</td>
						<td>${cfg.comeFroms }；　</td>
						<td>
							<c:if test="${cfg.isMedia == 0}">拒收媒体参与</c:if>
							<c:if test="${cfg.isMedia == 1}">接收媒体参与</c:if>
						</td>
					</tr>
				</table>
			</c:forEach>
			</td>
			<td align="center"">
				<a href="javascript:manager_del(${cc.id })">删除</a>
			</td>
		</tr>
		</c:forEach>
	</table>
</form>
</div>

<div id="easyDialogBox" style="display: none;">
<div id="ccEmailAddBox" style="width: 700px;" class="easyDialog_wrapper">
	<div class="easyDialog_content">
		<h4 class="easyDialog_title" id="easyDialogTitle"><a href="javascript:void(0)" title="关闭窗口" class="close_btn" id="closeBtn">×</a>添加邮件抄送人</h4>
		<div>
			<iframe src="cc_email-toAdd" frameborder="0" width="700" height="400"></iframe>
		</div>
	</div>
</div>
</div>

</BODY>
