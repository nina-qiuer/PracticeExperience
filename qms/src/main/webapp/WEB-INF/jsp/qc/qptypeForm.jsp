<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>
<html>
<head>
<style type="text/css">
</style>
<script type="text/javascript">
$(document).ready(function(){
	qpTypeInit();
});

function qpTypeInit() {
	var setting = {
			view: {
				dblClickExpand: true,
				fontCss: getFont,
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				beforeClick: beforeClick,
				onClick: onClick
			}
		};
	
	var zNodes = new Array();
	
	
	<c:forEach items="${qpTypeList}" var="qpType">
		<c:if test="${0 == qpType.touchRedFlag}">
	    	zNodes.push({id:'${qpType.id}', pId:'${qpType.pid}', name:'${qpType.name}'});
		</c:if>
		<c:if test="${1 == qpType.touchRedFlag}">
			zNodes.push({id:'${qpType.id}', pId:'${qpType.pid}', name:'${qpType.name}', font:{'color':'red'} });
		</c:if>
	 
	</c:forEach>
	
	qpTypeTree = $.fn.zTree.init($("#qpTypeTree"), setting, zNodes);
	
}

function getFont(treeId, node) {
	return node.font ? node.font : {};
}

function beforeClick(treeId, treeNode) {
	var check = (treeNode && !treeNode.isParent);
	if (!check) ;
	return check;
}

function onClick(e, treeId, treeNode) {
    var node ='';
	var zTree = $.fn.zTree.getZTreeObj("qpTypeTree");
	nodes = zTree.getSelectedNodes();
	if (nodes.length > 0) {
	 node = nodes[0].getParentNode();
	}
	v = "";
	var ids="";
	nodes.sort(function compare(a,b){return a.id-b.id;});
	for (var i=0, l=nodes.length; i<l; i++) {
		v += nodes[i].name + ",";
		ids+=nodes[i].id+",";  
	}
	if (v.length > 0 ) v = v.substring(0, v.length-1);
	if (ids.length > 0 ) ids = ids.substring(0, ids.length-1);  
	$("#qptName").attr("value", v);
	$("#qptId").attr("value", ids);
	
	$.ajax( {
		url : 'qc/qualityProblem/getFullName',
		data : {	
			qptId: $("#qptId").val(),
			qcFlag:$("#qcFlag").val()
			},
		type : 'post',
		dataType:'json',
		cache : false,
		success : function(result) {
			if(result)
			{
		    	if(result.retCode == "0")
				{
		    		var qptName = result.retObj;
		    		 parent.$("input[name='qptName']").val(qptName);
		    		 var index = parent.layer.getFrameIndex(window.name); 
		    		 parent.layer.close(index);
				 }else{
					
					layer.alert(result.resMsg, {icon: 2});
				}
		     }
		 }
	    });
	
		
}


</script>
</head>
<body>
<form name="qptype_form" id="qptype_form" method="post" action="" >
	<input id="qptName" name="qptName" type="hidden" value=""   />
	<input id="qptId" name="qptId" type="hidden" value=""   />
	<input id="qcFlag" name="qcFlag" type="hidden" value="${qcFlag}"   />
	<ul id="qpTypeTree" class="ztree" style="overflow:auto;"></ul>
</form>
</body>
</html>