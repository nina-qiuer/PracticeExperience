<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<link type="text/css" href="${CONFIG.res_url}css/bui.css" rel="stylesheet" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${CONFIG.res_url}css/easydialog.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${CONFIG.res_url}/script/easydialog.min.js"></script>
<link href="${CONFIG.res_url}css/thickbox.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${CONFIG.res_url}script/thickbox-compressed.js"></script>
<script type="text/javascript" src="${CONFIG.BOSS_URL}/js/util.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}script/jquery/jquery.1.4.2.js"></script>
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
<script type="text/javascript">

/**
 * 转为已完成
 */
function turnFinish(input){
	
	var complaintId = $('#complaintId').val();
	var roomId = $('#roomId').val();
	 disableButton(input);
	$.ajax( {
		url : 'complaint-turnFinish',
		data : {
			 complaintId:complaintId,
			 roomId:roomId
			},
		type : 'post',
		dataType:'json',
		cache : false,
		success : function(result) {
			if(result)
			{
			    enableButton(input);
		    	if(result.retObj == "success")
				{
		    		alert("状态改变成功");
		    		self.parent.easyDialog.close();
		    		parent.location.replace(parent.location.href);
				 }else{
					
					alert(result.resMsg);
					self.parent.easyDialog.close();
					parent.location.replace(parent.location.href);
				}
		     }
		 },
		 error: function() {
             // 解除禁用
             enableButton(input);
         }
	    });
	
}
</script>
<title>会话列表</title>
</head>
<body>
	<div class="mb10">
	<input title="追加沟通" class="pd5 mr10" type="button" value="追加沟通" onclick="easyDialog.open({container : 'addCommitBox', overlay : false})" <c:if test="${chat.statusNum==4||state!=2}">disabled</c:if>/>
	<input title="修改沟通类型" class="pd5 mr10" type="button" value="修改沟通类型" onclick="easyDialog.open({container : 'updateCommitType', overlay : false})" <c:if test="${chat.statusNum==4||state!=2}">disabled</c:if>/>
	<input title="转为已完成" class="pd5 mr10" type="button" name="tfinish" value="转为已完成" onclick="turnFinish(this)" <c:if test="${chat.statusNum==4||state!=2}">disabled</c:if>/>
	</div>
		<input type="hidden" name="complaintId" id="complaintId" value="${complaintId }" />
		<input type="hidden" name="roomId" id="roomId" value="${roomId }" />
	<div class="common-box">
	<div class="common-box-hd">
		<span class="title2">沟通详情</span>
	</div>
	<table width="100%" class="listtable" style="TABLE-LAYOUT: fixed;">
			<tr align="center">
			<td width="60">房间编号</td>
			<td width="60">沟通时间</td>
			<td width="60">处理人</td>
			<td width="120">沟通内容</td>
		</tr>
		</table>
	<div id="follow_note">
	<c:forEach items="${listAgency}" var="v">
		<table width="100%" class="listtable" style="TABLE-LAYOUT: fixed;">
			<tr align="center">
				<td width="60">${v.roomId}</td>
				<td width="60">${v.commitTime}</td>
				<td width="60">${v.dealName}</td>
			 <c:if test="${v.contentType==0}">
			  <td width="120">${v.descript }</td>
			  </c:if>
			  <c:if test="${v.contentType==1}">
			  <td width="120"><a href="${v.descript}">${v.pictName}</a></td>
			  </c:if>
			</tr>
		</table>
	</c:forEach>
	</div>
</div>
	<div id="addCommitBox" style="display: none; width: 500px;" class="easyDialog_wrapper">
	<div class="easyDialog_content">
		<h4 class="easyDialog_title" id="easyDialogTitle"><a href="javascript:void(0)" title="关闭窗口" class="close_btn" id="closeBtn">×</a>追加沟通</h4>
		<div>
			<iframe src="complaint-addCommit?complaintId=${complaintId}&roomId=${roomId}" frameborder="0" width="500" height="300"></iframe>
		</div>
	</div>
</div>

<div id="updateCommitType" style="display: none; width: 500px;" class="easyDialog_wrapper">
	<div class="easyDialog_content">
		<h4 class="easyDialog_title" id="easyDialogTitle"><a href="javascript:void(0)" title="关闭窗口" class="close_btn" id="closeBtn">×</a>修改沟通类型</h4>
		<div>
			<iframe src="complaint-updateCommitType?complaintId=${complaintId}&roomId=${roomId}" frameborder="0" width="400" height="150"></iframe>
		</div>
	</div>
</div>
</body>
