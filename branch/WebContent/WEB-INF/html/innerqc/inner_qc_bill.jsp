<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp"%>
<script language="javascript" src="${CONFIG.res_url}script/jquery/jquery-ui.min.js"></script>
<link rel="stylesheet" href="${CONFIG.res_url}script/jquery/css/jquery-ui.min.css"/>
<script type="text/javascript" src="${CONFIG.res_url}/script/easydialog.min.js"></script>
<link href="${CONFIG.res_url}css/easydialog.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
<!--
//输入你希望根据页面高度自动调整高度的iframe的名称的列表
//如果用户的浏览器不支持iframe是否将iframe隐藏 yes 表示隐藏，no表示不隐藏
var iframehide = "yes";
function dyniframesize() {
	$(".ifmAutoHeight").each(function() {
		if (document.getElementById) {
			//自动调整iframe高度
			if (!window.opera) {
				this.style.display = "block";
				if (this.Document && this.Document.body.scrollHeight) { //如果用户的浏览器是IE等
					var sh = this.Document.body.scrollHeight + 25;
					if (sh < 263) {
						sh = 263;
					}
					this.height = sh;
				} else if (this.contentDocument && this.contentDocument.body.offsetHeight) { //如果用户的浏览器是NetScape等
					var oh = this.contentDocument.body.offsetHeight + 25;
					if (oh < 263) {
						oh = 263;
					}
					this.height = oh;
				}
			}
		}
		//根据设定的参数来处理不支持iframe的浏览器的显示问题
		if ((document.all || document.getElementById) && iframehide == "no") {
			this.style.display = "block";
		}
	});
}

if (window.addEventListener) {
	window.addEventListener("load", dyniframesize, false);
} else if (window.attachEvent) {
	window.attachEvent("onload", dyniframesize);
} else {
	window.onload = dyniframesize;
}

$(function() {
	var resultInfo = '${resultInfo}';
	if ("Success" == resultInfo) {
		alert("发送成功！");
		window.opener.search();
		window.close();
	}
	
	$(".accordion").each(function() {
		$(this).accordion({
	    	collapsible: true,
	    	heightStyle: "content"
	    });
	});
});

function openWindow(title, url, width, height) {
	$("#openBox").width(width);
	var divStr = 
		"<div class='easyDialog_content'>" + 
		"<h4 class='easyDialog_title' id='easyDialogTitle'><a href='javascript:void(0)' title='关闭窗口' class='close_btn' id='closeBtn'>×</a>" + title + "</h4>" + 
		"<div>" + 
			"<iframe src='" + url + "' frameborder='0' width='" + width + "' height='" + height + "'></iframe>" + 
		"</div>" + 
		"</div>";
	$("#openBox").html(divStr);
	easyDialog.open({container : 'openBox', overlay : false})
}

function refreshIfm2() {
	var iqcId = "${id}";
	$("#ifm2").attr("src", "inner_qc_question?entity.iqcId=" + iqcId);
}

function refreshIfm2_x(questionId) {
	$("#ifm2").contents().find("#ifm_2_" + questionId).attr("src", "inner_qc_duty?entity.questionId=" + questionId);
}

function doSubmit() {
	if(preSubmit()){
		$("#emailForm").submit();
	}
}

function preSubmit(){
	var  recipients  = $('#reEmails').val();
	var  ccs =$('#ccEmails').val();
	var  recipientsT = recipients.substr(0,recipients.length-1).split(";");//去除最后一个分号
	var  ccsT = ccs.substr(0,ccs.length-1).split(";");//去除最后一个分号
	var  compar = new RegExp("^(\\w)+([-+.]\\w+)*@tuniu\.com$");//判断tuniu.com邮箱正则表达式
	//对收件人邮箱进去判断
	var recipientsF = $.trim(recipientsT);
	if(recipientsF==''){
		alert("收件人不能为空!");
		return false;
	}
	for(var i=0;i<recipientsT.length;i++){
		if(!compar.test(recipientsT[i])){
			alert("收件人中"+recipientsT[i]+"不符合要求!");
			return false;
			break;
		}
	}
	//对抄送人邮箱进去判断
	var ccsF = $.trim(ccsT);
	if(ccsF==''){
		alert("抄送人不能为空!");
		return false;
	}
	for(var i=0;i<ccsT.length;i++){
		if(!compar.test(ccsT[i])){
			alert("抄送人中"+ccsT[i]+"不符合要求!");
			return false;
			break;
		}
	}

	$('.sendButton').attr('disabled' , 'true');
	return true;
}
//-->
</script>
</HEAD>
<BODY>
<div class="top_crumbs">您当前所在的位置：<a href="#">质检管理系统</a>&gt;&gt;<span class="top_crumbs_txt">内部质检单</span></div>

<div class="accordion">
	<h3>质检申请单信息</h3>
	<div>
		<iframe class="ifmAutoHeight" id="ifm1" src="inner_qc-toInfo?entity.id=${id}" width="100%" frameborder="0" scrolling="auto"></iframe>
	</div>
</div>

<div class="accordion">
	<h3>质量问题</h3>
	<div>
		<iframe class="ifmAutoHeight" id="ifm2" src="inner_qc_question?entity.iqcId=${id}" width="100%" frameborder="0" scrolling="auto"></iframe>
	</div>
</div>

<div class="accordion">
<h3>发送质检报告</h3>
<div>
<form name="form" id="emailForm" method="post" action="inner_qc-sendEmail" enctype="multipart/form-data">
<input type="hidden" name="entity.id" value="${id}">
<table width="100%" class="datatable">
<tr>
<th align="right" width="80">邮件主题：</th>
	<td colspan=4><input style="font-size: 14px;width:700px;height:30px" type="text" name="title" name ="title"  value="${emailTitle}"/></td>
</tr>
<tr>
	<th align="right" width="80">收件人：</th>
	<td>
		<textarea name="reEmails" id="reEmails" style="font-size: 14px;" rows="3" cols="60">${reEmails }</textarea>
	</td>
	<th align="right" width="80">抄送人：</th>
	<td>
		<textarea name="ccEmails" id="ccEmails" style="font-size: 14px;" rows="3" cols="60">${ccEmails}</textarea>
	</td>
	<td align="center"><input type="button" value="发送" id='sendButton' class="blue" onclick="doSubmit()"></td>
</tr>
</table>
</form>
</div>
</div>

<div id="openBox" style="display: none;" class="easyDialog_wrapper"></div>

</BODY>
