<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xml:lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<script src="http://code.jquery.com/jquery-latest.js"></script>
<style>
	.listtable { border:1px solid #C1D3EB;border-collapse:collapse; font-size:12px;}
	.listtable th { padding:2px;text-align:center;border:1px solid #C1D3EB;color:#3E649D;background:#DFEAFB;font-weight:normal;}
	.listtable td {	border: 1px solid #C1D3EB;padding:2px; background:#fff;}
	
	.hidden{ display:none }
</style>
<script type="text/javascript">
	var modelName = "配置";
	
	//删除按钮
	function del(id){
		var url ='delete/'+id;
		if(confirm("确定删除"+modelName+"["+id+"]吗?")){
			$.ajax({
				type:"post",
				url:url,
				success:function(){
					alert('删除成功');
					$("#search_form").submit();
				}
			});
		}
	}
	
	function showAdd(){
		$('.hidden').toggle();
	}
	
	function add(){
		console.log(dataId.value+':'+content.value+':'+description.value);
		$.ajax({
			type:"post",
			url:'add',
			data:{'dataId':dataId.value, 'content':$('#content').val(),'description':description.value,'config_level':0},
			success:function(){
				alert('添加成功');
				$("#search_form").submit();
			}
		});
	}
	
	function showUpdate(editButton){
		var names = ['dataId','content','description'];
		$(editButton).parent().siblings().not(':first-child').wrapInner(function(index) {
			  return '<input value="'+$(this).text()+'" name="'+names[index]+'"></input>';
			});
		
		editButton.value='保存';
		editButton.onclick= update;
	}
	
	function update(saveButton){
		var dataId = $('input[name=dataId]').val();
		var description = $('input[name=description]').val();
		var id = $('input[name=dataId]').parent().siblings().first().text();
		
		$.ajax({
			type:"post",
			url:'update',
			data:{id:id,dataId:dataId, content:$('input[name=content]').val(),description:description},
			success:function(){
				alert('保存成功');
				$("#search_form").submit();
			}
		});
		
	}
</script>
</HEAD>
<BODY>
<form name="form" id="search_form" method="post"  action="list">
        配置键：
        	<input name="dataIdPre" value="${dataIdPre }"/>
            <input type="submit" size="10"  value="查询"/>
            <input type="button" size="10"  value="新增"  onclick="showAdd()"/>
</form>
<br />
<table class="listtable" width="100%" style="table-layout:fixed;word-break: break-all; word-wrap: break-word; overflow-x:scroll">
		<thead>
			<th width="15%">id</th>
			<th width="15%">配置键</th>
			<th width="40%">配置值</th>
			<th width="15%">配置描述</th>
			<th width="15%">操作</th>
		</thead>

		<tbody>
				<c:forEach items="${dataList}" var="v"  >
				<tr>
						<td width="15%">${v.id}</td>   
						<td width="15%">${v.dataId}</td>
						<td width="40%">${v.content}</td>
						<td width="15%">${v.description}</td>

						<td width="15%">
							<input type="button"   value="编辑"  onclick="showUpdate(this)"></input>
							<input type="button" size="10"  value="删除"  onclick="del(${v.id})"/>
						</td>

				</tr>
				</c:forEach>
				<tr class="hidden">
					<td></td>
					<td><input  id="dataId"/> </td>
					<td><input  id="content"/></td>
					<td><input  id="description"/></td>
					<td>
							<input type="button"  value="保存"  onclick="add()"/>
					</td>
				</tr>
		</tbody>
</table>
</BODY>
</html>