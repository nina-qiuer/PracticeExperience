<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp"%>
<link type="text/css" href="${CONFIG.res_url}css/bui.css" rel="stylesheet" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${CONFIG.res_url}css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${CONFIG.res_url}script/ztree/jquery.ztree.core-3.1.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}script/ztree/jquery.ztree.excheck-3.1.js"></script>
</HEAD>	
<BODY>
<div class="top_crumbs">请选择需回访客服组</div>
<div class="left_crumbs" style="float:left;height:700px;width:100%;">
<div style="display:none"><p>${dep_construts}</p></div>
<div align="center">
<input type="button" value="提交" class="blue" id="submitBtn" onclick="onSelectedConstruts();">　
<input type="button" value="关闭" class="blue" onclick="onClose();">
</div>
<div class="zTreeDemoBackground left">
	<ul id="treeDemo" class="ztree"></ul>
</div>
<div class="right" style="display:none">
	被勾选时：<input type="checkbox" id="py" class="checkbox first" checked /><span>关联父</span>
	<input type="checkbox" id="sy" class="checkbox first" checked /><span>关联子</span><br/>
	取消勾选时：<input type="checkbox" id="pn" class="checkbox first" checked /><span>关联父</span>
	<input type="checkbox" id="sn" class="checkbox first" checked /><span>关联子</span><br/>
</div>
</div>

<script>
function onSelectedConstruts() {
	$("#submitBtn").attr("disabled", "disabled");
	var treeObj=$.fn.zTree.getZTreeObj("treeDemo");
    nodes=treeObj.getCheckedNodes(true);
    var ids="";var out = "";
    if(nodes!=null&&nodes!=""){
    	for(var i=0;i<nodes.length;i++){
    		var s = nodes[i].name;
    		var obj = nodes[i];
    		if(obj!=null&&obj.children!=null&&obj.children.length!=null&&obj.children.length>0){
    		}else{
    			while(nodes[i]=nodes[i].getParentNode()) {
    				s=nodes[i].name+'-'+s;
    			}
    			s = s+"#"+obj.id+"#"+obj.name+"#"+obj.pId+"^";
    			out += s;
    		}
        }
        ids=out.substring(0,out.length-1);//获取选中节点的值
    		var param ={"ids":ids};
    		$.ajax({
    		type: "POST",
    		url: "preSaleSatisfaction-doBackConstruts",
    		data: param,
    		success: function(data){
    			//window.parent.location.href="preSaleSatisfaction";
    			window.parent.frames['left'].location.href = "preSaleSatisfaction-toLeft"; 
    				
    	   }
     	 });
    }else{
    	window.parent.location.href="preSaleSatisfaction";
    }
    
}
function onClose(){
	window.parent.location.href="preSaleSatisfaction";
}
//treeConstruts
var setting = {
	check: {
		enable: true
	},
	data: {
		simpleData: {
			enable: true
		}
	}
};
var dep_construts=${dep_construts}; 
var code;

function setCheck() {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
	py = $("#py").attr("checked")? "p":"",
	sy = $("#sy").attr("checked")? "s":"",
	pn = $("#pn").attr("checked")? "p":"",
	sn = $("#sn").attr("checked")? "s":"",
	type = { "Y":py + sy, "N":pn + sn};
	zTree.setting.check.chkboxType = type;
	//showCode('setting.check.chkboxType = { "Y" : "' + type.Y + '", "N" : "' + type.N + '" };');
}
$(document).ready(function(){
	$.fn.zTree.init($("#treeDemo"), setting, dep_construts);
	setCheck();
	$("#py").bind("change", setCheck);
	$("#sy").bind("change", setCheck);
	$("#pn").bind("change", setCheck);
	$("#sn").bind("change", setCheck);
});
</script>
