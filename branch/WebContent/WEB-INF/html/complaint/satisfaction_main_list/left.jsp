<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="${CONFIG.res_url}script/DatePickerNew/WdatePicker.js" ></script>  
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
$(function(){
	/*移除thickbox默认样式*/
	$('.thickbox').click(function(){
		$('#TB_closeAjaxWindow').html("<a href='javascript:void(0)' id='TB_closeWindowButton' title='Close'>×</a>");
		$('#TB_closeWindowButton').live('click',function(){
			tb_remove();
		});
	});
	
});
function selectAndShow(id,depth){
	/* var cnt = parseInt('${fn:length(UcDepartmentDelList)}'); */
	$('.clickShowColor').each(function() {
		$(this).css("color", "");
		});
	
	if(depth==1){
		$("#deptId_"+id).css("color","green");
	}else{
		$("#deptId_"+id).css("color","gray");
	}
	
	var time = $("#selectedM").val();
	window.parent.frames['right'].location.href = "preSaleSatisfaction-toRight?search.depId="+id+"&search.yearMonth="+time; 
}
function showHidden(id){
	if($("#show_"+id).val()==0){
		$("#personId_"+id).hide();
		$("#plusAndM_"+id).text("＋");
		$("#show_"+id).val(1);
	}else if($("#show_"+id).val()==1){
		$("#personId_"+id).show();
		$("#plusAndM_"+id).text("－");
		$("#show_"+id).val(0);
	}
}
function personShow(personId){
	var time = $("#selectedM").val();
	$("#month_" + personId).val(time);
	$("#personForm_" + personId).submit();
}
function showSelectedMInfo(dp){
	var onChangeM = function(dp){
		/* if(!confirm('日期框原来的值为: '+dp.cal.getDateStr()+', 要用新选择的值:' + dp.cal.getNewDateStr() + '覆盖吗?')) return true; */
		if($("#selectedM").val()==dp.cal.getNewDateStr()){
		}else{
			doShowSelectedMInfo(dp.cal.getNewDateStr());
		}
		
	};
	WdatePicker({dateFmt:'yyyy-MM',onpicking: onChangeM });
}
function doShowSelectedMInfo(obj){//根据选中的日期跟新组织架构树
	window.location.href = "preSaleSatisfaction-toLeft?yearMonth="+obj; 
}
</script>
</head>
<body>
<table>
	<tr><td align="center"><a href="preSaleSatisfaction-doAddConstruts?width=500&dep_construts=${dep_construts }" class="pd5 mr10">客服组配置</a></td></tr>
	<tr><td>
		月份：<input type="text" size="13" name="selectedM" id="selectedM" value="${selectedM }" onclick="showSelectedMInfo(this);" readOnly="readonly"/>
		<c:forEach items="${UcDepartmentDelList}" var="v" varStatus="ss">
		<div>
			<ul><li>
				<a id="plusAndM_${v.currentId }" href="javascript:showHidden('${v.currentId }');">＋</a>&nbsp;
				<a id="deptId_${v.currentId }" class="clickShowColor"
				<c:if test="${v.depth==1}">
						style="color:green"
				</c:if>
				 href="javascript:selectAndShow('${v.currentId }','${v.depth }');">${v.selectedDeptName}</a>
				<input type="hidden" id="show_${v.currentId }" value="1"/>
			</li></ul> 
			<div id="personId_${v.currentId }" style="display:none">
				<c:forEach items="${v.personList}" var="person" varStatus="st">
				<ul><li>　　　　<a 
				<c:if test="${person.approvalNumber>=5}">
					style="color:green"
				</c:if> href="javascript:personShow('${person.id}');">${person.realName}(${person.workNum})[<span id="showCount">${person.approvalNumber}</span>]</a>
				<form action="preSaleSatisfaction-toRight" id="personForm_${person.id}" method="post" target="right">
					<input type="hidden" name="search.salerName" value="${person.realName}">
					<input type="hidden" name="search.yearMonth" id="month_${person.id}">
				</form>
				</li></ul>
				</c:forEach>
			</div>
		</div>
		</c:forEach>
	</td></tr>
</table>


<script type="text/javascript">
$(function(){
	/*移除thickbox默认样式*/
	$('.thickbox').click(function(){
		$('#TB_closeAjaxWindow').html("<a href='javascript:void(0)' id='TB_closeWindowButton' title='Close'>×</a>");
		$('#TB_closeWindowButton').live('click',function(){
			tb_remove();
		});
	});
	
});
</script>
</body>
</html>