<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>情况说明</title>
	<script type="text/javascript">
	$(function(){
		$('#accordion').accordion({
	    	collapsible: true,
	    	heightStyle: "content"
	    });
		
	});
	
	function passSubmit() {
		var remark = $("#remark").val();
		if(remark == null || remark == ''){
			layer.alert("情况说明不能为空！");
			return;
		}else if(remark.length > 499){
			layer.alert("情况说明不能大于500字符！");
			return;
		}
		
		var status = $("#status").val();
		var routeId = $("#routeId").val();
		var url, prdStatus, lineType, msg;
		if(status == 2 || status == 4){
			url = "qs/punishPrd/chgPrdStatus";
			prdStatus = 1;
			lineType = (status == 4) ? 3 : 2; //是否永久下线
			msg = "确定将产品[" + routeId + "]上线吗？";
		}else{
			url = 'qs/punishPrd/updatePassStatus';
			prdStatus = 0;
			lineType = 1;
			msg = "确定将产品[" + routeId + "]放过吗？";
		}
		layer.confirm(msg , {icon: 3}, function(index){
			layer.close(index);
			
			$.ajax( {
				url : url,
				data:{  
					"id": $("#prdId").val(),
					"routeId" : routeId, 
					"prdStatus" : prdStatus,
					"realOffLineCount" : $("#realOffLineCount").val(),
					"offlineType" : $("#offlineType").val(),
					"lineType" : lineType,
					"remark" : remark 
				},
				type : 'post',
				dataType:'json',
				cache : false,
				success : function(result) {
					if(result){
				    	if(result.retCode == "0"){
				    		layer.alert(result.resMsg,{icon: 6,closeBtn: 0},function(){
				    			parent.search();
						    	parent.layer.closeAll();
							});
				    	}else{
							layer.alert(result.resMsg, {icon: 2});
						}
				   }else{
					   layer.alert("操作失败", {icon: 2}); 
				   }
				 }
			});
		});
	}

</script>
</head>
<body>
	<form name="searchForm" id="qs_form" method="post" action="" >
		<input id="prdId" type="hidden" value="${prd.id}"/>
		<input id="routeId" type="hidden" value="${prd.routeId}"/>
		<input id="status" type="hidden" value="${prd.status}"/>
		<input id="realOffLineCount" type="hidden" value="${prd.realOffLineCount}"/>
		<input id="offlineType" type="hidden" value="${prd.offlineType}"/>
		<div class="accordion">
			<div align="right">
				<table width="100%" class="search">
					<tr>
						<td>情况说明：</td>
					</tr>
					<tr>
						<td><textarea id="remark" rows="8" cols="85">${prd.remark}</textarea></td>
					</tr>
					
					<c:if test="${prd.status == 2 || prd.status == 4}">
						<td>
							<div>
								<iframe name="attachMain" src="qs/punishPrd/${prd.id}/toShowAttach" width="100%"  scrolling="yes" frameborder="0" id="attachMain" onload="iFrameHeight(this)"></iframe>
							</div>
						</td>
					</c:if>
					<tr >
						<td style="text-align: right">
							<input type="button" class="blue" value="提交" onclick="passSubmit()">
						</td>
					</tr>
				</table>
			</div>
		</div>
	</form>
</body>
</html>