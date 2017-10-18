<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<script language="javascript" src="${CONFIG.res_url}script/jquery/jquery-ui.min.js"></script>
<link rel="stylesheet" href="${CONFIG.res_url}script/jquery/css/jquery-ui.min.css"/>
<style type="text/css">
<!-- tr变黄色 -->
.trbg td{background:#ff9;}
.trbg1 td{background:#87CEFF;}
.listtable tr.yellowbg td{background:#FFFF99}
.listtable tr td.orange{background:orange;}
</style>
<script>
//输入你希望根据页面高度自动调整高度的iframe的名称的列表
//如果用户的浏览器不支持iframe是否将iframe隐藏 yes 表示隐藏，no表示不隐藏
var iframehide = "yes";
function dyniframesize() {
	$("iframe").each(function() {
		if (document.getElementById) {
			//自动调整iframe高度
			if (!window.opera) {
				this.style.display = "block";
				if (this.Document && this.Document.body.scrollHeight) { //如果用户的浏览器是IE等
					this.height = this.Document.body.scrollHeight + 25;
				} else if (this.contentDocument && this.contentDocument.body.offsetHeight) { //如果用户的浏览器是NetScape等
					this.height = this.contentDocument.body.offsetHeight + 25;
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

$(function(){
	$(".accordion").each(function() {
		$(this).accordion({
	    	collapsible: true,
	    	heightStyle: "content"
	    });
	});
});

function openAddWindow(iqcId) {
	parent.openWindow('添加质量问题单', 'inner_qc_question-toAdd?entity.iqcId='+iqcId, 870, 520);
}

function openEditWindow(id) {
	parent.openWindow('修改质量问题单', 'inner_qc_question-toUpdate?entity.id='+id, 870, 520);
}

function delInnerQcQuestion(id) {
	if (confirm("确定要删除质量问题单[" + id + "]吗？")) {
		$.ajax({
			type: "POST",
			url: "inner_qc_question-delete",
			data: {"entity.id":id},
			async: false,
			success: function(data) {
				location.reload();
			} 
		});
	}
}
</script>
</HEAD>
<BODY>
<div class="pici_search pd5" align="right">
	<input type="button" value="添加质量问题单" title="添加质量问题单" class="blue" style="cursor: pointer;"
		onclick="openAddWindow('${entity.iqcId}')">
</div>
<c:forEach items="${questionList}" var="v" varStatus="st">
<table class="datatable" width="100%">
<tr>
	<th align="right" width="100">问题单号：</th>
	<td>${v.id}</td>
	<!-- <a href="inner_qc_question-toBill?entity.id=${v.id}" target="_blank" title="质量问题单[${v.id}]">${v.id}</a> -->
	<th align="right" width="100">问题等级：</th>
	<td>${v.questionLevel}</td>
	<th align="right" width="100">问题类型：</th>
	<td>${v.bigClassName}&nbsp;—&nbsp;${v.smallClassName}</td>
	<th align="right" width="100">操作：</th>
	<td>　
		<input type="button" class="pd5" value="编辑" title="编辑" style="cursor: pointer;"
			onclick="openEditWindow('${v.id}')">　
		<input type="button" class="pd5" value="删除" onclick="delInnerQcQuestion('${v.id}')">
	</td>
</tr>
<tr>
	<td colspan="8">
		<div class="accordion">
			<h3>责任归属及记分处罚</h3>
			<div>
				<iframe id="ifm_2_${v.id}" src="inner_qc_duty?entity.questionId=${v.id}" width="100%" frameborder="0" scrolling="auto"></iframe>
			</div>
		</div>
	</td>
</tr>
</table>
<c:if test="${st.count < fn:length(questionList)}"><hr></c:if>
</c:forEach>
</BODY>
