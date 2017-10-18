<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp"%>
<script type="text/javascript"
	src="${CONFIG.res_url}script/complaint/complaint/appoint_manager.js"></script>
		
	<link rel="stylesheet" href="${CONFIG.res_url}css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="${CONFIG.res_url}script/ztree/jquery.ztree.core-3.1.js"></script>
		
	<SCRIPT type="text/javascript">
		<!--
		var setting = {
			view: {
				dblClickExpand: false
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onClick: onClick
			}
		};
	
		var zNodes = ${strDepartmentTree};
			
		function onClick(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
			nodes = zTree.getSelectedNodes(),
			v = "";
			nodes.sort(function compare(a,b){return a.id-b.id;});
			for (var i=0, l=nodes.length; i<l; i++) {
				v += nodes[i].name + ",";
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			var departmentObj = $("#departmentSel");
			departmentObj.attr("value", v);
			// 部门id
			var id = nodes[0].id;
			$("#departmentId").val(id);
			
			hideMenu();
		}
		
	
		function showMenu() {
			var departmentObj = $("#departmentSel");
			var departmentOffset = $("#departmentSel").offset();
			$("#menuContent").css({left:departmentOffset.left + "px", top:departmentOffset.top + departmentObj.outerHeight() + "px"}).slideDown("fast");
	
			$("body").bind("mousedown", onBodyDown);
		}
		function hideMenu() {
			$("#menuContent").fadeOut("fast");
			$("body").unbind("mousedown", onBodyDown);
		}
		function onBodyDown(event) {
			if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
				hideMenu();
			}
		}
	
		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		});
		
		function onSubmitClicked() {
			var param = $('#form').serialize();
			$.ajax({
			type: "POST",
			async:false,
			url: "appoint_manager-doAddDepartment",
			data: param,
			success: function(data){
				if(data==0) {
					
				} else {
					self.parent.tb_remove();
			}
		}
		})
		window.parent.location.reload();
		}
		
		//-->
	</SCRIPT>
	<style type="text/css">
		ul.ztree {margin-top: 10px;border: 1px solid #617775;background: #f0f6e4;width:220px;height:360px;overflow-y:scroll;overflow-x:auto;}
	</style>
</HEAD>
<BODY>
	<h2>增加部门</h2>

	<form name="form" id="form" method="post" enctype="multipart/form-data">
		<input type="hidden" name="entity.id" id="id" value="${entity.id}" />
		<input type="hidden" name="type" id="type" value="${type}" />
		<table width="100%" class="datatable">
			<tr>
				<th>部门</th>
				<td>
					<input class="w200" name="entity.departmentName" id="departmentSel" value="${entity.departmentName}" onclick="showMenu();"/>
					<input type="hidden" name="entity.departmentId" id="departmentId"/>
					<div id="menuContent" class="menuContent w200" style="display:none; position: absolute; z-index:1">
						<ul id="treeDemo" class="ztree" style="margin-top:0;"></ul>
					</div>
				</td>
			
			<tr>
				<th></th>
				<td>
					<input type="button"  class="pd5" value="增加" onclick="onSubmitClicked();self.parent.location.reload();" /> 
					<input type="button" class="pd5" value="取消" onclick="self.parent.tb_remove();" />
				</td>
			</tr>
		</table>
	</form>


	<%@include file="/WEB-INF/html/foot.jsp"%>