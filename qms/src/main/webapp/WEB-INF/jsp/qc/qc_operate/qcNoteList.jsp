<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>质检列表</title>
<script type="text/javascript" src="res/js/DateFormatUtil.js"></script>
<style type="text/css">
a:hover{
	color:blue;
}
.left {
	width: 65%;
}

.right {
	width: 30%;
	position: absolute;
	top: 4px;
	right: 15px;
}

.right textarea {
	display: block;
	margin: 5px 0;
	width: 100%;
	height: 150px;
	resize: none;
}

.listtable  .noteContent {
	text-align: left;
}

.noteContent p {
	text-indent: 2em;
}

#editArea {
	width: 100%;
}
</style>


<script type="text/javascript">
var textArea="";
	$(function() {
		$(".accordion").each(function() {
			$(this).accordion({
				collapsible : true,
				heightStyle : "content"
			});
		});

		$('.selfContent')
				.dblclick(
						updateNote
						);

	});
	
	//修改质检备忘
	function updateNote(){
		
		var content ="";
		//原始td对象
		var originTd = this;
		var noteId = this.id;
		//1.1取原始html
		var origin = $(this).html();
		//1.2取原始内容
		if(origin.indexOf("textarea")!=-1){
			
			content = textArea;
		}else{
			
	       content = $(origin).text();
	       textArea = content;
		}
		//1.3用textarea置换原html（）
		var editArea = '<textarea name="" id="editArea" cols="30" rows="1"></textarea>';
		$(this).html(editArea);
		//1.4先focus再赋值确保光标定位到文本末尾
		$('#editArea').focus().val(content);

		//2 定义失去焦点事件
		$('#editArea')
				.blur(
						function() {
							//2.1 将新编辑的内容赋值给原td
							newEditContent=$('#editArea').val();
							if(newEditContent==""){
								layer.alert("请填写备忘内容");
								return false;
							}
							
							if(newEditContent.length>500){
								layer.alert("质检备忘内容应小于500字");
								return false;
							}
							var newContent = '<p>'
									+ newEditContent
									+ "</p>";
							$(originTd).html(newContent);
							//2.2判断内容是否发生改变
							if (origin != newContent) {
								//2.2.1如果内容变化则调用后台ajax更新数据
								$.ajax({
									type : "POST",
									url : "qc/qcBill/" + noteId + "/updateNote",
									data:{newContent:newEditContent},
								});
							}
						});
	}

	/*
	*新增备忘
	*/
	function addNote()
	{
		var newContent = $('#newContent').val();
		if(newContent==""){
			layer.alert("请填写备忘内容");
			return false;
		}
		
		if(newContent.length>500){
			layer.alert("质检备忘内容应小于500字");
			return false;
		}
		
		$.ajax({
			type : "POST",
			url : "qc/qcBill/addNote",
			data:{newContent:newContent,qcBillId:'${dto.qcBillId}',addPerson:'${loginUser.realName}'},
			success : function(qcNote) {
				var newLine = "<tr><td>" +new Date(parseInt(qcNote.addTime)).Format("yyyy-MM-dd hh:mm:ss") + "</td><td>" +qcNote.addPerson+ "</td><td class='noteContent' id='"+qcNote.id+"' ondblclick='updateNote(this)'><p>" +qcNote.content+ "</p></td><td><a href='javascript:void(0)' onclick='deleteNote(confirm(\"确定删除吗？\"),this,"+qcNote.id+")'>删除</a></td></tr>";
				$('.listtable tr:first').after($(newLine));
				$('#'+qcNote.id).dblclick(
						updateNote
				);
				$('#newContent').val("");
			}
		});
	}
	
	/*
	 *删除质检备忘
	 */
	function deleteNote(flag, node, noteId) {
		if(flag){
			//调用ajax删除质备忘
			$.ajax({
				type : "POST",
				url : "qc/qcBill/" + noteId + "/deleteNote",
				success : function() {
					//删除所在行
					$(node).parents('tr').remove();
				}
			});
		}
	}
	
</script>

</head>

<body>
	<div>
		<div class="left">
			<div class="accordion">
				<h3>质检备忘列表</h3>
				<table class="listtable">
					<tr>
						<th width="10%">添加时间</th>
						<th width="10%">添加人</th>
						<th width="75%">备忘详情</th>
						<th>操作</th>
					</tr>
					<c:forEach items="${dto.dataList}" var="qcNote">
						<tr>
							<td><fmt:formatDate value="${qcNote.addTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td>${qcNote.addPerson}</td>
							<td class="noteContent <c:if test='${qcNote.addPerson==loginUser.realName}'>selfContent</c:if>" id="${qcNote.id}"><p>${qcNote.content}</p></td>
							<td><c:if test="${qcNote.addPerson==loginUser.realName}">
							<a href="javascript:void(0)" onclick="deleteNote(confirm('确定删除吗？'),this,'${qcNote.id}')">删除</a></c:if></td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>

		<div class="right">
			<div class="accordion">
				<h3>增加备忘</h3>
					<div>
						<textarea id="newContent" resizable="none"></textarea>
						<input type="button" class="blue" value="新增备忘" onclick="addNote()">
						<input type="button" value="添加跟进提醒" name="addRtxRemindBtn" id="updateLevelBtn" class="blue" style="width:100px" 
						onclick="openWin('添加跟进提醒', 'qc/qcBill/toAddRtxRemind?qcId=${dto.qcBillId}', 480, 235)">
					</div>
			</div>
		</div>
	</div>
</body>
</html>
