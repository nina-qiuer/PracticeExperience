<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp"%>
<link href="${CONFIG.res_url}css/thickbox.css" rel="stylesheet"
	type="text/css" />
<link type="text/css" href="${CONFIG.res_url}css/bui.css"
	rel="stylesheet" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript"
	src="${CONFIG.res_url}/script/thickbox-compressed.js"></script>
<script type="text/javascript"
	src="${CONFIG.res_url}/script/jquery/plugin/jquery.form.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}script/jquery/plugin/KindEditor/kindeditor-min.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}script/jquery/plugin/KindEditor/lang/zh_CN.js"></script>
<link rel="stylesheet" type="text/css" href="${CONFIG.res_url}script/jquery/plugin/KindEditor/css/default.css"/>
<style type="text/css">
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


<script type="text/javascript">

var editor1;
KindEditor.ready(function(K) {
	editor1 = K.create('#qcPoint', {
		resizeType : 1,
		allowPreviewEmoticons : false,
		allowImageUpload : false,
		items : ['fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', 
				 'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', '|', 'hr', 'fullscreen']
	});
	
});


	function saveQcPonit(input) {
		editor1.sync();
		var qcPoint = $('#qcPoint').val();
		var evidence = $('#evidence').val();
		var complaintId = $('#complaintId').val();
		var qcPointId = $('#qcPointId').val();
		if (editor1.text() == "") {
			alert('请填写质检点');
			return false;
		}
		if (editor1.text().length < 6) {

			alert("质检点内容不少于6个字");
			return false;
		}
		// 禁用提交按钮
		disableButton(input);
		
		var url, systemProblemFlag = 0;
		if (qcPointId != "" && qcPointId != null) {
			url = 'complaint-updateQcPoint';
		} else {
			url = 'complaint-saveQcPoint';
			systemProblemFlag = $('input[name="systemProblemFlag"]:checked').val();
		}
		$.ajax({
			url : url,
			data : {
				qcPointId : qcPointId,
				complaintId : complaintId,
				qcPoint : qcPoint,
				evidence : evidence,
				systemProblemFlag : systemProblemFlag
			},
			type : 'post',
			dataType :'json',
			cache : false,
			success : function(result) {
				if (result) {
					if (result.retObj == "success") {
						enableButton(input);
						alert("发送成功");
						parent.location.replace(parent.location.href);
					} else {
						enableButton(input);
						alert(result.resMsg);
					}
				}
			},
			error : function() {
				enableButton(input);
			}
		});
	}

	function revokeQcPonit(input) {
		var qcPointId = $('#qcPointId').val();
		var complaintId = $('#complaintId').val();
		// 禁用提交按钮
		disableButton(input);
		$.ajax({
			url : 'complaint-revokeQcPoint',
			data : {
				qcPointId : qcPointId,
				complaintId : complaintId
			},
			type : 'post',
			dataType : 'json',
			cache : false,
			success : function(result) {
				if (result) {
					if (result.retObj == "success") {
						enableButton(input);
						alert("撤销成功");
						parent.location.replace(parent.location.href);
					} else {
						enableButton(input);
						alert(result.resMsg);
					}
				}
			},
			error : function() {
				// 解除禁用
				enableButton(input);
			}
		});

	}
</script>
</head>
<body>
	<div class="common-box">
		<form name="form" id="form" method="post"  enctype="multipart/form-data" action="">
			<input type="hidden" name="complaintId" id="complaintId" value="${complaintId }" />
			<input type="hidden" name="qcPointId" id="qcPointId" value="${qcPoint.id }" />
			<table width="100%" style="TABLE-LAYOUT: fixed;" class="datatable">
			
				<tr>
					<th width="100" rows="4">质检点：</th>
					<td>
						<textarea id="qcPoint" name="qcPoint" style="width:650px;height:180px">${qcPoint.qcPoint }</textarea>
					</td>
				</tr>
				<tr>
					<th width="100" rows="4">证据：</th>
					<td>
					    <textarea id="evidence" name="evidence" style="width:650px;height:50px">${qcPoint.evidence }</textarea>
					</td>
				</tr>
				<c:if test="${qcPoint == null}">
					<tr>
						<th width="100" rows="4">是否系统问题：</th>
						<td>
						    <input name="systemProblemFlag" type="radio" value="0" checked/>否&nbsp&nbsp&nbsp&nbsp
							<input name="systemProblemFlag" type="radio" value="1"/>是
						</td>
					</tr>
				</c:if>
				<tr>
				<td>
					<iframe src="complaint-queryQcUpload?complaintId=${complaintId }&type=act" frameborder="0" width="750" height="150"></iframe>
				</td>
				</tr>
				<tr>
					<th width="100" ></th>
					<td>
						<input class="pd5" type="button" name="save" value="确认" onclick="saveQcPonit(this)" /> &nbsp&nbsp&nbsp&nbsp
						<c:if test="${qcPoint!=null && (userId == qcPoint.updatePersonId || userId == reportUserId)}">
							<input class="pd5" type="button" name="revoke" value="撤销" onclick="revokeQcPonit(this)" />&nbsp&nbsp&nbsp&nbsp
						</c:if>
				    </td>
				    <td></td>
				</tr>
			</table>
		</form>
	</div>
	