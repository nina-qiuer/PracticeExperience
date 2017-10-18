<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>
<html>
<head>
<script type="text/javascript">
function savePunish(){
	
	var val= $('input:radio[name="basisId"]:checked').val();
	if(val==null||val==''){
		layer.alert("请选择一个执行标准！", {
			icon : 2
		});
		return false;
	}
	$.ajax( {
			url : 'qc/innerPunishBasis/addPunishBasis',
			data : 	$('#punish_basis_form').serialize(),
			type : 'post',
			dataType:'json',
			cache : false,
			success : function(result) {
				if(result)
				{
			    	if(result.retCode == "0")
					{
			    		
					 }else{
						layer.alert(result.resMsg, {icon: 2});
					}
			     }
			 }
	});
	
}

function deleteBasis(id){
	
		var msg = "您确定删除该处罚依据吗？";
		layer.confirm(msg, {icon: 3}, function(index){
		$.ajax( {
				url : 'qc/innerPunishBasis/deleteBasis',
				data : 	{id:id} ,
				type : 'post',
				dataType:'json',
				cache : false,
				success : function(result) {
					if(result)
					{
				    	if(result.retCode == "0")
						{
				    		location.reload();
						 }else{
							layer.alert(result.resMsg, {icon: 2});
						}
				     }
				 }
			    });
		});
}
function openWinow(title, url, width, height) {
	parent.layer.open({
        type: 2,
        shade : [0.5 , '#000' , true],
        maxmin: true,
        title: title,
        content: url,
        area: [width+'px', height+'px']
    });
}
</script>
<body>
<form name="punish_basis_form" id="punish_basis_form" method="post" action="">
<input type="hidden" name="ipbId"  id="ipbId" value="${punishId}">
<table class="listtable">
	<tr>
	<th width="40">执行</th>
	<th width="85">处罚等级</th>
	<th>分级标准描述</th>
	<th width="80">经济处罚</th>
	<th width="70">记分处罚</th>
	<th>
		<input type="button" value="添加" class="blue" 
			onclick="openWinow('添加处罚依据', 'qc/punishStandard/getPunishStandard?punishId=${punishId}&punishObj=1&useFlag=${useFlag}', 700, 400)">
	</th>
	</tr>
	<c:forEach items="${basisList}" var="v">
	<tr align="center">
		<td>
		<c:if test="${v.execFlag ==1 }">
			<input type="radio" name="basisId" value="${v.id}" checked="checked" onclick="savePunish()"/>
		</c:if>
		<c:if test="${v.execFlag == 0 }">
			<input type="radio" name="basisId" value="${v.id}"  onclick="savePunish()"/>
		</c:if>
		</td>
		<td>
			<c:if test="${v.punishStandard.redLineFlag == 1}"><span style="color: red;">${v.punishStandard.level}</span></c:if>
			<c:if test="${v.punishStandard.redLineFlag == 0}">${v.punishStandard.level}</c:if>
		</td>
		<td class="left">${v.punishStandard.description }</td>
		<td>${v.punishStandard.economicPunish } 元</td>
		<td>${v.punishStandard.scorePunish}</td>
		<td>
			<input type="button" value="删除" class="blue" onclick="deleteBasis(${v.id})">
		</td>
	</tr>
	</c:forEach>
</table>
</form>
</body>
</html>