<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp"%>
<script type="text/javascript"
	src="${CONFIG.res_url}script/complaint/complaint/appoint_manager.js"></script>
		
<link rel="stylesheet" href="${CONFIG.res_url}css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${CONFIG.res_url}script/ztree/jquery.ztree.core-3.1.js"></script>
	
<SCRIPT type="text/javascript">
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

var zNodes = ${departmentTree};
function selectDept(id) {
	var fid = id.split(":")[0];
	var cid = id.split(":")[1];
	var isDel = "del";
	if($('input[name=usedDept'+cid+']:checked').length > 0){
		isDel = "add";
	}
	$.ajax({
		"type":"post",
		"url":"${manageUrl}-doAddDept",
		data:{
			"fid":fid,
			"cid":cid,
			"isDel":isDel
		},
		success: function(data) {
		},
		error:function() {
			alert("error");
		}
		
		
	});
	
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
	var userName = $("#userName").val();
	if (userName == "") {
		alert("您还没有选择客服！~");
		return false;
	}
	
	var type = $("#type").val();
	var tourTimeNode = $("#tourTimeNode:checked").val();
	if (type == 1 && typeof(tourTimeNode) == "undefined") {
		alert("请为客服配置时间节点！~");
		return false;
	}
	
	var param = $('#form').serialize();
	$.ajax({
	type: "POST",
	async:false,
	url: "${manageUrl}-doAdd",
	data: param,
	success: function(data){
		if(data!=0) {
			self.parent.tb_remove();
		}
     }
   });
   window.parent.location.reload();
}

function onUserSelChanged() {
	var userName = $("#userSel option:selected").text();
	$("#userName").val(userName);
}

function chooseAll(checked)
{
	if($(":checkbox:unchecked").length==0)//如果已经处于全选状态，则屏蔽此次后台交互
		return false;
	
	var isDel = "del";
	if(checked){
		$(":checkbox").attr("checked",true); 
		isDel = "add";
	}
	else
	{
		$(":checkbox").attr("checked",false); 
	}
	
	$.ajax({
		"type":"post",
		"url":"${manageUrl}-batchAddDept",
		data:{
			"isDel":isDel
		},
		success: function(data) {
		},
		error:function() {
			alert("error");
		}
		
	});

}

</SCRIPT>
<style type="text/css">
	ul.ztree {margin-top: 10px;border: 1px solid #617775;background: #f0f6e4;width:220px;height:360px;overflow-y:scroll;overflow-x:auto;}
</style>
</HEAD>
<BODY>
<form name="form" id="form" method="post" enctype="multipart/form-data">
<!--<input type="hidden" name="entity.id" id="id" value="${entity.id}" />-->
<input type="hidden" name="type" id="type" value="${type}" />
<table width="100%" class="datatable" id="deptTable">
		<tr>
			<th>事业部   <input type="checkbox" name="all" onclick="chooseAll(this.checked)"/></th>
			<th>二级部门</th>
		</tr>
	<c:forEach items="${deptList }" var="v" varStatus="st">
		<tr>
			<th>${v.depName}</th>
			<td>
			    <table width="100%">
			        <tr>
			            <td>
			                <table width="100%">
			                    <tr>
						    	    <c:forEach items="${v.childDept }" var="vv" varStatus="st">
						    	        <c:if test="${st.count % 5 == 1 }">
						    	        	</tr><tr>
						    	        </c:if>
							    	    <td style="border: 0px;">
							    	    <label>
							    	    	<input type="checkbox" name="usedDept${vv.id}" id="${v.id}:${vv.id}" value="${vv.id }" onclick="selectDept(this.id)" <c:if test="${vv.useDept==1}">checked</c:if>/>${vv.depName }
							    	    </label>
							    	    </td>
						    	    </c:forEach>
						    	</tr>
			                </table>
			            </td>
			        </tr>
			    	
			    </table>
			</td>
		</tr>
	</c:forEach>
</table>
</form>
</BODY>
<%@include file="/WEB-INF/html/foot.jsp"%>