<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<link href="${CONFIG.res_url}css/thickbox.css" rel="stylesheet" type="text/css" />
<link type="text/css" href="${CONFIG.res_url}css/bui.css" rel="stylesheet" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${CONFIG.res_url}/script/thickbox-compressed.js"></script>
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
	<span style="align:center;"><font color="red" size="16">${youaretheone}</font></span>
	<div class="common-box">
		<div class="common-box-hd">
			<span class="title2">modify complaint</span>
		</div>
		<form action="complaint-whoIsYourDaddy" method="post">
		<table width="100%" class="datatable">
			<tr>
				<th align="right">id</th>
				<td><input name="id" type="text" size="10" value="${entity.id}" readonly/></td>
				<th align="right">联系人id：</th>
				<td><input name="contactId" type="text" size="10" value=""/></td>
				<th align="right">订单类型：</th>
				<td><input name="createType" type="text" size="10" value="${entity.createType}"/></td>
			</tr>
			<tr>
				<th align="right">事业部：</th>
				<td><input name="depName" type="text" size="10" value="${entity.depName}"/></td>
				<th align="right">事业部总经理：</th>
				<td><input name="depManager" type="text" size="10" value="${entity.depManager}"/></td>
				<th align="right">产品总监：</th>
				<td><input name="productManager" type="text" size="10" value="${entity.productManager}"/></td>
			</tr>
			<tr>
				<th align="right">处理人id：</th>
				<td><input name="deal" type="text" size="10" value="${entity.deal}"/></td>
				<th align="right">处理人名称：</th>
				<td><input name="dealName" type="text" size="10" value="${entity.dealName}"/></td>
				<th align="right">处理状态：</th>
				<td><input name="state" type="text" size="10" value="${entity.state}"/></td>
			</tr>
			
			<tr>
				<th align="right">who are you：</th>
				<td><input name="key" type="text" size="10" /></td>
				<th align="right"></th>
				<td></td>
				<th align="right"></th>
				<td><input type="submit" size="10" value="submit"/></td>
			</tr>
		</table>
		</form>
	</div>
	
	

<script type="text/javascript">
$(function(){
	/*移除thickbox默认样式*/
	$('.thickbox').click(function(){
		$('#TB_closeAjaxWindow').html("<a href='javascript:void(0)' id='TB_closeWindowButton' title='Close'>×</a>");
		$('#TB_closeWindowButton').live('click',function(){
			tb_remove();
		});
	});
});
</script>
</body>
<%@include file="/WEB-INF/html/foot.jsp"%>
