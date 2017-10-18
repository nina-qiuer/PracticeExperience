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
var depArr = new Array();
var userArr = new Array();
var realNameArr = new Array();
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
	$(document).ready(function() {
		userAutoComplete();
		depAutoComplete();
		$("#note_form").validate({
			rules:{
				newContent:{
					  required:true,
	                  rangelength:[1,500]
				},
				dockingDep:{
		    	   
		    	   depExists:true
		       },
			    dockingPeople:{
			    	userExists:true
			     }
	        },
	        messages:{
	        	newContent:{required:"请输入备忘",rangelength:"不超过500个字"},
	        	dockingDep:{depExists:"部门不存在"},
	        	dockingPeople:{userExists:"用户名不正确"}
	        }
			
		});
		jQuery.validator.addMethod("depExists", function(value, element) {
			if($.trim(value)!=''){
			var isExists = false;
			for(var i=0;i<depArr.length;i++){
				
				if(value == depArr[i]){
					
					isExists =true;
					
				}
			}
			return isExists;
			
		}else{
			return true;
			
		}
		}, "");
	
		jQuery.validator.addMethod("userExists", function(value, element) {
			if($.trim(value)!=''){
				var isExists = false;
				for(var i=0;i<realNameArr.length;i++){
					
					if(value == realNameArr[i]){
						
						isExists =true;
					}
				}
				return isExists;
				
			}else{
				
				return true;
			}
			}, "");
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
	function addNote(input)
	{
		var newContent = $('#newContent').val();
		var dockingPeople = $('#dockingPeople').val();
		var dockingDep = $('#dockingDep').val();
		if($('#note_form').valid()){
			disableButton(input);
		$.ajax({
			type : "POST",
			url : "qc/qcBill/addNote",
			data:{newContent:newContent,qcBillId:'${dto.qcBillId}',addPerson:'${loginUser.realName}',dockingPeople:dockingPeople,dockingDep:dockingDep},
			success : function(qcNote) {
				enableButton(input);
				var newLine = "<tr><td>" +new Date(parseInt(qcNote.addTime)).Format("yyyy-MM-dd hh:mm:ss") + "</td><td>" +qcNote.addPerson+ "</td><td>" +qcNote.dockingPeople+ "</td><td>" +qcNote.dockingDep+ "</td><td class='noteContent' id='"+qcNote.id+"' ondblclick='updateNote(this)'><p>" +qcNote.content+ "</p></td><td><a href='javascript:void(0)' onclick='deleteNote(confirm(\"确定删除吗？\"),this,"+qcNote.id+")'>删除</a></td></tr>";
				$('.listtable tr:first').after($(newLine));
				$('#'+qcNote.id).dblclick(
						updateNote
				);
				$('#newContent').val("");
				$('#dockingPeople').val("");
				$('#dockingDep').val("");
			}
		});
		}
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
	function queryUser(){
		
	     var dockingPeople =$('#dockingPeople').val();	
	     if($.trim(dockingPeople)==''){
	    	 $('#dockingDep').val("");
	    	 return false;
	     }
		 $.ajax( {
				url : 'qc/qualityProblem/getUserDetailSelf',
				data : 
				{
				    respPersonName : dockingPeople
				},
				type : 'post',
				dataType:'json',
				//async: false,
				success : function(result) {
					if(result)
					{
				    	if(result.retCode == "0")
						{
				    		var detail = eval(result.retObj);
				    		$('#dockingDep').val( detail.depName);
						 }else{
							 
					        $('#dockingDep').val("");
							
							
						}
				     }
				 }
			    });
		  
	  }
	

	function userAutoComplete() {
		
		<c:forEach items="${userNames}" var="userMap">
			userArr.push({
				label: '${userMap.label}',
				value: '${userMap.realName}'
			});
			realNameArr.push('${userMap.realName}');
		</c:forEach>
		
		$("#dockingPeople").autocomplete({
		    source: userArr,
		    autoFocus : true
		}).data( "ui-autocomplete" )._renderItem = function( ul, item ) {
			return $( "<li>" )
	        .append( "<a>" + item.value + "</a>" )
	        .appendTo( ul );
		};
	}
	function depAutoComplete() {
		
		<c:forEach items="${depNames}" var="depName">
			depArr.push('${depName}');
		</c:forEach>
		
	    $("#dockingDep").autocomplete({
		    source: depArr,
		    autoFocus : true
		});
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
						<th width="10%">对接人</th>
						<th width="20%">对接部门</th>
						<th width="45%">备忘详情</th>
						<th width="5%">操作</th>
					</tr>
					<c:forEach items="${dto.dataList}" var="qcNote">
						<tr>
							<td><fmt:formatDate value="${qcNote.addTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td>${qcNote.addPerson}</td>
							<td>${qcNote.dockingPeople}</td>
							<td>${qcNote.dockingDep}</td>
							<td class="noteContent <c:if test='${qcNote.addPerson==loginUser.realName}'>selfContent</c:if>" id="${qcNote.id}"><p>${qcNote.content}</p></td>
							<td><c:if test="${qcNote.addPerson==loginUser.realName}">
							<a href="javascript:void(0)" onclick="deleteNote(confirm('确定删除吗？'),this,'${qcNote.id}')">删除</a></c:if></td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>

		<form name="note_form" id="note_form" method="post" action="" >
			<div class="right">
			  <div class="accordion">
				<h3>增加备忘</h3>
					<div>
					<table class="datatable">
					<tr>
						<th align="right" width="100" height="30">对接人名字：</th>
						<td >
						   <input name="dockingPeople" id="dockingPeople" type="text" onblur="queryUser()"/>
						 
						</td>
					</tr>
					<tr>
						<th align="right" width="100" height="30">对接人部门：</th>
						<td >
						    <input name="dockingDep" id="dockingDep" type="text" style="width:300px"/>
						</td>
					</tr>
					<tr>
						<th><span style="color: red">*</span>备忘详情:</th>
					<td>
					    <textarea id="newContent" name="newContent" style="height:80px"></textarea>
					</td>
					</tr>
					</table>	
						<input type="button" class="blue" name="note" value="新增备忘" onclick="addNote(this)">
						<input type="button" value="添加跟进提醒" name="addRtxRemindBtn" id="updateLevelBtn" class="blue" style="width:100px" 
						onclick="openWin('添加跟进提醒', 'qc/qcBill/toAddRtxRemind?qcId=${dto.qcBillId}', 480, 235)">
					</div>
				
				</div>
			</div>
		</form>
	</div>
</body>
</html>
