<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp"%>
<style>
img{
	width: 10px;
	height: 10px;
}
</style>
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
	
	// 根据部门选择人员
	var id = nodes[0].id; // 每次只选一个部门

	$("#departmentId").val(id);

	// 删除原人员列表
	$("#userSel").find("option").remove();	
	
	$.ajax({
		"type":"post",
		"url":"../../frm/ajax/system/user-getUserListByDepartmentId",
		data:{
			"departmentId":id
		},
		success: function(data) {				
			
			var json_data = eval('('+data+')');
			if(json_data.type=="success") {
				for (i in json_data.data) {
					var userId = json_data.data[i].id;
					var userName = json_data.data[i].realName;
			
					$("#userSel").append("<option value='"+userId+"'>"+userName+"</option>");
				}
				
				// 默认选择第一个
				var userName = $("#userSel option:selected").text();
				$("#userName").val(userName);
			} else {
				alert(json_data.data);
			}
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
	
	var str=document.getElementsByName("entity.signCity"); 
	var signCity=""; 
	for(var i=0;i<str.length;i++){ 
	            if(str[i].checked){ 
	            	signCity += str[i].value+","; 
	           } 
	   } 
	
	var str1=document.getElementsByName("entity.productCategory"); 
	var productCategory=""; 
	for(var i=0;i<str1.length;i++){ 
	            if(str1[i].checked){ 
	            	productCategory += str1[i].value+","; 
	           } 
	   } 
	
	var str2=document.getElementsByName("entity.comeFrom"); 
	var comeFrom=""; 
	for(var i=0;i<str2.length;i++){ 
	            if(str2[i].checked){ 
	            	comeFrom += str2[i].value+","; 
	           } 
	   } 
	
	var type = $("#type").val();
	var touringGroupType = $("#touringGroupType:checked").val();
	if (type == 1 && typeof(touringGroupType) == "undefined") {
		alert("请为客服配置组别！~");
		return false;
	}
	
	var param = $('#form').serialize();
	
	
	$.ajax({
	type: "POST",
	async:false,
	url: "${manageUrl}-doAdd",
	data: param,
	success: function(data){
		if(data==1) {
			alert("该用户已经配置过，请先删除再配置");
		}
		self.parent.tb_remove();
    }
  });
   window.parent.location.reload();
}

function onUserSelChanged() {
	var userName = $("#userSel option:selected").text();
	$("#userName").val(userName);
}

function hve_display(t_id,i_id){//显示隐藏程序
	var tr = document.getElementById(t_id);//表格ID
	var img = document.getElementById(i_id);//图片ID
	var on_img='${CONFIG.res_url}images/icon/default/up.png';//打开时图片
	var off_img='${CONFIG.res_url}images/icon/default/down.png';//隐藏时图片
	if (tr.style.display == "none"){
		tr.style.display="";//切换为显示状态
		img.src=off_img;
	}else{
		tr.style.display="none";//切换为隐藏状态
		img.src=on_img;
	}
}

function chooseAll(id,checked)
{
	if(checked)
		$("td.child_"+id+" :checkbox").attr("checked",true); 
	else
		$("td.child_"+id+" :checkbox").attr("checked",false); 
		
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
<table width="100%" class="datatable">
	<tr>
		<th>部门</th>
		<td>
			<input class="w200" name="entity.departmentName" id="departmentSel" value="${entity.departmentName}" onclick="showMenu();" readonly="readonly"/>
			<input type="hidden" name="entity.departmentId" id="departmentId"/>
			<div id="menuContent" class="menuContent w200" style="display:none; position: absolute; z-index:1">
				<ul id="treeDemo" class="ztree" style="margin-top:0;"></ul>
			</div>
		</td>
	</tr>
	<tr>
		<th>人员</th>
		<td> 
			<input type="hidden" name="entity.userName" id="userName"/>
			<select class="w200" name="entity.userId" id="userSel" onchange="onUserSelChanged();">
			</select>
		</td>
	</tr>
	<c:if test="${type == 1}"><!-- 投诉特殊配置 -->
		<tr>
			<!-- 出游中 -->
			<c:if test="${tourTimeNode == 2}">
				<input type="hidden" name="entity.tourTimeNode" id="tourTimeNode" value="2" />
				<th>组别</th>
				<td>
					<label><input type="radio" name="entity.touringGroupType" id="touringGroupType" value="1"/>呼入组</label>　
					<label><input type="radio" name="entity.touringGroupType" id="touringGroupType" value="2"/>后处理组</label>　
					<label><input type="radio" name="entity.touringGroupType" id="touringGroupType" value="3"/>资深座席组</label>
				</td>
			</c:if>
			<!-- 出游前或出游后 -->
			<c:if test="${tourTimeNode == 1 || tourTimeNode == 3 || tourTimeNode==4 || tourTimeNode==5 || tourTimeNode==6 || tourTimeNode==7 || tourTimeNode==8}">	
				<input type="hidden" name="entity.tourTimeNode" id="tourTimeNode" value="${tourTimeNode}">
				<label class='hide'>
					<input type="radio" name="entity.touringGroupType" id="touringGroupType" value="0" checked="checked"/>
				</label>
			</c:if>
		</tr>
		
		<c:if test="${tourTimeNode!=6 && tourTimeNode!=8}">	
			<tr>
				<th>投诉级别</th>
				<td>
					<label><input type="checkbox" name="entity.complaintLevel1Flag" id="complaintLevel1Flag" value="1"/>一级</label>　　
					<label><input type="checkbox" name="entity.complaintLevel2Flag" id="complaintLevel2Flag" value="1"/>二级</label>　　
					<label><input type="checkbox" name="entity.complaintLevel3Flag" id="complaintLevel3Flag" value="1"/>三级</label>
				</td>
			</tr>
		</c:if>
		<c:if test="${tourTimeNode == 5}">
		<tr>
			<th>产品品类</th>
			<td>
				<label><input type="checkbox" name="entity.productCategory" id="productCategory" value="国内酒店"  <c:if test="${entity.productCategory.contains('国内酒店')==true}">checked='checked'</c:if>/>国内酒店</label>
				<label><input type="checkbox" name="entity.productCategory" id="productCategory" value="国际酒店"  <c:if test="${entity.productCategory.contains('国际酒店')==true}">checked='checked'</c:if>/>国际酒店</label>
			</td>
		</tr>
		</c:if>
		<c:if test="${tourTimeNode != 4 && tourTimeNode != 5 && tourTimeNode != 6 && tourTimeNode != 7 && tourTimeNode != 8}">
		<tr>
			<th>产品品类</th>
			<td>
				<label><input type="checkbox" name="entity.productCategory" id="productCategory" value="跟团游"/>跟团游</label>　　
				<label><input type="checkbox" name="entity.productCategory" id="productCategory" value="自助游"/>自助游</label>　
				<label><input type="checkbox" name="entity.productCategory" id="productCategory" value="团队游"/>团队游</label>　　
				<label><input type="checkbox" name="entity.productCategory" id="productCategory" value="邮轮"/>邮轮</label>
				<br>
				<label><input type="checkbox" name="entity.productCategory" id="productCategory" value="自驾游"/>自驾游</label>　
				<label><input type="checkbox" name="entity.productCategory" id="productCategory" value="门票"/>门票</label>　
				<label><input type="checkbox" name="entity.productCategory" id="productCategory" value="酒店"/>酒店</label>　　
				<label><input type="checkbox" name="entity.productCategory" id="productCategory" value="火车票"/>火车票</label>
				<label><input type="checkbox" name="entity.productCategory" id="productCategory" value="汽车票"/>汽车票</label>　　
				<br>
				<label><input type="checkbox" name="entity.productCategory" id="productCategory" value="签证"/>签证</label>　　
				<label><input type="checkbox" name="entity.productCategory" id="productCategory" value="用车服务"/>用车服务</label>　
				<label><input type="checkbox" name="entity.productCategory" id="productCategory" value="票务"/>票务</label>　　
				<label><input type="checkbox" name="entity.productCategory" id="productCategory" value="目的地服务"/>目的地服务</label>
				<br>
				<label><input type="checkbox" name="entity.productCategory" id="productCategory" value="通信"/>通信</label>
				<label><input type="checkbox" name="entity.productCategory" id="productCategory" value="定制游"/>定制游</label>
				<label><input type="checkbox" name="entity.productCategory" id="productCategory" value="衍生品"/>衍生品</label>
				<label><input type="checkbox" name="entity.productCategory" id="productCategory" value="国内机票"/>国内机票</label>
				<label><input type="checkbox" name="entity.productCategory" id="productCategory" value="国际机票"/>国际机票</label>
				
				
			</td>
		</tr>
		<tr>
			<th>目的地大类</th>
			<td>
				<label><input type="checkbox" name="entity.aroundFlag" id="aroundFlag" value="1"/>周边</label>　　
				<label><input type="checkbox" name="entity.inlandLongFlag" id="inlandLongFlag" value="1"/>国内长线</label>
				<label><input type="checkbox" name="entity.abroadShortFlag" id="abroadShortFlag" value="1"/>出境短线</label>
				<label><input type="checkbox" name="entity.abroadLongFlag" id="abroadLongFlag" value="1"/>出境长线</label>
				<label><input type="checkbox" name="entity.othersFlag" id="othersFlag" value="1"/>其他</label>
			</td>
		</tr>
		</c:if>
		<!-- 资深、售后-->
		<c:if test="${tourTimeNode == 2 || tourTimeNode == 3}">
		<tr>
			<th>签约城市</th>
			<td>
				<label><input type="checkbox" name="entity.signCity" id="signCity" value="北京"/>北京</label>　　
				<label><input type="checkbox" name="entity.signCity" id="signCity" value="上海"/>上海</label>　
				<label><input type="checkbox" name="entity.signCity" id="signCity" value="深圳"/>深圳</label>　　
				<label><input type="checkbox" name="entity.signCity" id="signCity" value="广州"/>广州</label>　
				<label><input type="checkbox" name="entity.signCity" id="signCity" value="其他" checked="checked"/>其他</label>
			</td>
		</tr>
		</c:if>
		<!-- 资深-->
		<c:if test="${tourTimeNode == 3}">
		<tr>
			<th>投诉来源</th>
			<td>
				<label><input type="checkbox" name="entity.comeFrom" id="comeFrom" value="点评"/>点评</label>
				<label><input type="checkbox" name="entity.comeFrom" id="comeFrom" value="APP"/>APP</label>　　　　
				<label><input type="checkbox" name="entity.comeFrom" id="comeFrom" value="来电"/>来电</label>　　
				<label><input type="checkbox" name="entity.comeFrom" id="comeFrom" value="第三方(微博，媒体，旅游局)"/>第三方(微博，媒体，旅游局)</label>
				<label><input type="checkbox" name="entity.comeFrom" id="comeFrom" value="CS邮箱"/>CS邮箱</label>　　
				<label><input type="checkbox" name="entity.comeFrom" id="comeFrom" value="其他"  checked="checked"/>其他</label>　　
			</td>
		</tr>
		
		</c:if>
		
		<!-- 出游前 -->
		<c:if test="${tourTimeNode == 1}">
		<tr>
			<th>处理赔款单</th>
			<td>
				<label><input type="radio" name="entity.payforOrder" id="payforOrder" value="1"/>是</label>
				<label><input type="radio" name="entity.payforOrder" id="payforOrder" value="0" checked/>否</label>
			</td>
		</tr>			
		</c:if>
		
		<c:if test="${tourTimeNode == 1 || tourTimeNode == 2 || tourTimeNode == 3}">
		<tr>
			<th>会员等级</th>
			<td>
				<select name="entity.guestLevel" >
					<option value="0">所有会员</option>
					<option value="1">五星以下</option>
					<option value="2">五星及以上</option>
				</select>
			</td>
		</tr>			
		</c:if>
		
	</c:if>
	<c:if test="${type == 2}"><!-- 质检特殊配置 -->
		<tr onclick="hve_display('notr','imgno')">
			<th>普通</th>
			<td>
				<img id="imgno" src="${CONFIG.res_url}images/icon/default/down.png">　非（微博,一级,媒体,旅游局）
			</td>
		</tr>
		<tr id="notr">
			<th>普&nbsp;&nbsp;&nbsp;&nbsp;通</th>
			<td><table width="100%">
					<c:forEach items="${deptList}" var="vv" varStatus="ss">
						<tr>
							<th><input type="checkbox" onclick="chooseAll(${vv.id},this.checked)"/>${vv.depName}</th>
							<td class="child_${vv.id}">
								<table width="100%">
									<tr>
										<td>
											<table width="100%">
												<tr>
													<c:forEach items="${vv.childDept}" var="v"
														varStatus="st">
														<c:if test="${st.count % 5 == 1}">
												</tr>
												<tr>
													</c:if>
													<td style="border: 0px;"><label> <input
															type="checkbox" name="entity.depIds[${ss.index}]"
															value="${vv.depName}@${v.id}" />${v.depName}
													</label></td>
													</c:forEach>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</c:forEach>
				</table></td>
		</tr>
		<tr onclick="hve_display('sptr','imgsp')">
			<th>特殊</th>
			<td>
				<img id="imgsp" src="${CONFIG.res_url}images/icon/default/up.png">　微博,一级,媒体,旅游局
			</td>
		</tr>
		<tr id="sptr" style="display: none">
			<th title="微博,一级,媒体,旅游局">特&nbsp;&nbsp;&nbsp;&nbsp;殊</th>
			<td><table width="100%">
					<c:forEach items="${deptList}" var="vv" varStatus="ss">
						<tr>
							<th>${vv.depName}</th>
							<input type="hidden" name="entity.depNames[${ss.index}]" value="${vv.depName}">
							<td>
								<table width="100%">
									<tr>
										<td>
											<table width="100%">
												<tr>
													<c:forEach items="${vv.childDept}" var="v"
														varStatus="st">
														<c:if test="${st.count % 5 == 1}">
												</tr>
												<tr>
													</c:if>
													<td style="border: 0px;"><label> <input
															type="checkbox" name="entity.spDepIds[${ss.index}]"
															 value="${vv.depName}@${v.id}" />${v.depName}
													</label></td>
													</c:forEach>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</c:forEach>
				</table></td>
		</tr>
		<tr>
			<th>无订单</th>
			<td>
				<input type="radio" name="entity.noOrdFlag" value="0" checked="checked">不处理　　
				<input type="radio" name="entity.noOrdFlag" value="1">处理
			</td>
		</tr>
	</c:if>
	<tr>
		<th></th>
		<td>
			<input type="button"  class="pd5" value="增加" onclick="return onSubmitClicked();"> 
			<input type="button" class="pd5" value="取消" onclick="self.parent.tb_remove();">
		</td>
	</tr>
</table>
</form>
</BODY>
