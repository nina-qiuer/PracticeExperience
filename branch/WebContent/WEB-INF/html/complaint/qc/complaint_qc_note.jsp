<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp"%>
<link type="text/css" href="${CONFIG.res_url}css/bui.css"
	rel="stylesheet" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript"
	src="${CONFIG.res_url}/script/jquery/plugin/jquery.form.js"></script>
<link href="${CONFIG.res_url}css/easydialog.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="${CONFIG.res_url}/script/easydialog.min.js"></script>
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

.common-box-hd button {
	float: right;
	margin-right: 15px;
	background: #C6E3F1 repeat-x;
	padding-top: 3px;
	border-top: 1px solid #708090;
	border-right: 1px solid #708090;
	border-bottom: 1px solid #708090;
	border-left: 1px solid #708090;
	width: 80px;
	height: auto;
	font-size: 10pt;
	cursor: hand;
	border: 0 none;
}

.common-box-hd button:hover {
	background: #EEE repeat-x;
	text-align: center;
	text-decoration: none;
	text-shadow: 1px 1px 1px rgba(255, 255, 255, .22);
	-webkit-border-radius: 30px;
	-moz-border-radius: 30px;
	border-radius: 30px;
	-webkit-box-shadow: 1px 1px 1px rgba(0, 0, 0, .29), inset 1px 1px 1px
		rgba(255, 255, 255, .44);
	-moz-box-shadow: 1px 1px 1px rgba(0, 0, 0, .29), inset 1px 1px 1px
		rgba(255, 255, 255, .44);
	box-shadow: 1px 1px 1px rgba(0, 0, 0, .29), inset 1px 1px 1px
		rgba(255, 255, 255, .44);
	-webkit-transition: all 0.15s ease;
	-moz-transition: all 0.15s ease;
	-o-transition: all 0.15s ease;
	-ms-transition: all 0.15s ease;
	transition: all 0.15s ease;
}
</style>
<script type="text/javascript">
var editor;
KindEditor.ready(function(K) {
	editor = K.create('#remark', {
		resizeType : 1,
		allowPreviewEmoticons : false,
		allowImageUpload : false,
		afterBlur: function(){this.sync();addNote();} ,
		items : ['fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', 
				 'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', '|', 'hr', 'fullscreen']
	});
});




	$(function() {
		$(".content")
				.dblclick(
						function() { // 注册双击事件
							var $td = $(this);
							// 检测此td是否已经被替换了，如果被替换直接返回
							if ($td.children("input").length > 0) {
								return false;
							}
							var text = $td.text();
							var $textarea = $("<textarea style='height: 19px; font-size: 10pt;text-align:left' rows='1' cols='100' ></textarea>");
							$textarea.text(text);
							$td.html("");
							$td.append($textarea);
							$textarea.focus();

							$textarea.click(function(event) {
								event.stopPropagation();
							});

							$textarea.blur(function() { // 鼠标点出即提交，将td中的内容修改成获取的value值
								var value = $textarea.val();
								if (value != text) {
									var idStr = $td.attr("id");
									var id = idStr.substring(7, idStr.length);
									$.ajax({
										type : "POST",
										url : "qc_note-update",
										data : {
											"id" : id,
											"remark" : value
										},
										async : false,
										success : function(data) {
											$td.html(value);
										}
									});
								} else {
									$td.html(value);
								}

							});

							$textarea.keyup(function(event) {
								if (event.which == 27) { // 按ESC，将td中的内容还原
									$td.html(text);
								}
							});
						});
	});
	
	

	function fadeToggle() {
		$("form").fadeToggle();
		$("form textarea").focus();
	}

	function toggleNoteList() {
		$("table:.note").toggle();
		$(".pages").toggle();
	}

	function toggleAttachList() {
		$("table:.attach").toggle();
	}

	function addNote() {
		if( $("#remark").val().length>0){
			$("form").submit();
		}
	}
</script>

<title>质检备忘列表</title>
</head>
<body>
	<div class="common-box">
		<div class="common-box-hd">
			<span class="title2" onclick="toggleNoteList()">备忘列表</span>
			<button
				onclick="easyDialog.open({container : 'uploadInfoBox', overlay : false})">上传附件</button>
			<button onclick="fadeToggle()">添加备忘</button>
		</div>

		<form action="qc_note-insert" method="post" style="display: none">
			<input type="hidden" name="qcId" value="${qcId }"/>
			<textarea id="remark" name="remark" style="width:100%;height:200px;"></textarea>
		</form>
		<table width="100%" class="listtable note"
			style="TABLE-LAYOUT: fixed;">
			<tr align="center">
				<th width="15%">添加时间</th>
				<th width="15%">添加人</th>
				<th>备忘详情</th>
			</tr>
			<c:forEach items="${dataList }" var="v">
				<tr align="center">
					<td style="padding: 0px;"><fmt:formatDate value="${v.addTime}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td>${v.addPerson }</td>
					<td style="padding: 0px;">
						<table width="100%" style="border-collapse: collapse;">
							<tr>
								<td width="95%" height="50" style="text-align: left"
									<c:if test="${v.addPerson == user.realName}">class='content'</c:if>
									id="remark_${v.id}">${v.remark}</td>
								<td><c:if test="${v.addPerson == user.realName}">
										<a href="qc_note-delete?id=${v.id}&qcId=${qcId }">删除</a>
									</c:if></td>
							</tr>
						</table>
					</td>
				</tr>
			</c:forEach>
		</table>
		<%@include file="/WEB-INF/html/pager.jsp"%>

	</div>


	<div class="common-box">
		<div class="common-box-hd">
			<span class="title2" onclick="toggleAttachList()">附件列表</span>
		</div>
		<table width="100%" class="listtable attach"
			style="TABLE-LAYOUT: fixed;">
			<tr>
				<th width="15%">上传时间</th>
				<th width="15%">上传人</th>
				<th>附件名称</th>
			</tr>
			<c:forEach items="${request.attachList}" var="v" varStatus="st">
				<tr align="center">
					<td style="padding: 0px;"><fmt:formatDate
							value="${v.updateTime}" pattern="yyyy-MM-dd hh:mm:ss" /></td>
					<td>${v.addPerson}</td>
					<td style="padding: 0px;">
						<table width="100%" style="border-collapse: collapse;">
							<tr>
								<td width="95%" style="text-align: center"><a
									href="${v.path}">${v.name}</a></td>
								<td><c:if test="${v.addPerson == user.realName}">
										<a
											href="qc_note-delNoteFile?attachId=${v.id}&pageFrom=notePage&qcId=${qcId}"
											onclick="return confirm('本操作不可恢复,确认删除?')">删除</a>
									</c:if></td>
							</tr>
						</table>
					</td>
				</tr>
			</c:forEach>
		</table>

	</div>

	<div id="uploadInfoBox" style="display: none; width: 620px;"
		class="easyDialog_wrapper">
		<div class="easyDialog_content">
			<h4 class="easyDialog_title" id="easyDialogTitle">
				<a href="javascript:window.location.href='qc_note?qcId=${qcId }';" title="关闭窗口"
					class="close_btn" id="closeBtn">×</a>上传附件
			</h4>
			<div>
				<iframe src="qc_note-queryAttachList?qcId=${qcId }" frameborder="0"
					width="620" height="500"></iframe>
			</div>
		</div>
	</div>
</body>
