<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>组织目标值列表</title>
<script type="text/javascript">
var depArr = new Array();

$(function(){
	$('#accordion').accordion({
    	collapsible: true,
    	heightStyle: "content"
    });
    
});

function search() {
	$("#targetForm").attr("action", "qs/targetConfig/list");
	$("#targetForm").submit();
}

function resetForm() {
	$("#depName").val('');
}

function checkTarget(input){
	
	var targetValue  = input.value;
	if($.trim(targetValue) ==''){
			
			layer.alert("应为0到100整数或两位小数",{icon: 2});	
		    $(input).val(0);
			return false;
		}
		var compar = new RegExp("^(((\\d|[1-9]\\d)(\\.\\d{1,2})?)|100|100.0|100.00)$");
		if(!compar.test(targetValue)){
			
	      layer.alert("应为0到100整数或带两位小数",{icon: 2});	
	      $(input).val(0);
	      return false;
			 
		}
	
}


function saveTarget(node,id){
	
	var tr1 = node.parentNode.parentNode; 
/* 	$(tr1).find('input[type=text]').each(function(){
		alert($(this).val());
	}); */
	var oneTargetValue =$(tr1).find('input[name="oneTargetValue"]').val();
	var twoTargetValue =$(tr1).find('input[name="twoTargetValue"]').val();
	var threeTargetValue =$(tr1).find('input[name="threeTargetValue"]').val();
	var fourTargetValue =$(tr1).find('input[name="fourTargetValue"]').val();
	var businessUnitName =$(tr1).find('input[name="businessUnitName"]').val();
	var prdDepName =$(tr1).find('input[name="prdDepName"]').val();
	
	var flag = 0;
	if(prdDepName == ''){
		flag = 1;
		layer.confirm('你确定批量更新该事业部吗', {
			  btn: ['是','否'] //按钮
			}, function(){
		 		 $.ajax( {
						url : 'qs/targetConfig/updateTarget',
						data :
						{
							id : id,
							flag : 1,
							businessUnitName : businessUnitName,
							oneTargetValue : oneTargetValue,
							twoTargetValue : twoTargetValue,
							threeTargetValue : threeTargetValue,
							fourTargetValue : fourTargetValue
						},
						type : 'post',
						dataType:'json',
						cache: false,
						success : function(result) {
							if(result)
							{
						    	if(result.retCode == "0")
								{
						    		layer.msg('保存成功', {icon: 1});
									search();
						    		
								}else{
									
									layer.alert(result.resMsg, {icon: 2});
								}
						     }
						 }
					    }); 
				
			}, function(){
				
				 $.ajax( {
						url : 'qs/targetConfig/updateTarget',
						data : 
						{
							id : id,
							flag : 0,
							businessUnitName : businessUnitName,
							oneTargetValue : oneTargetValue,
							twoTargetValue : twoTargetValue,
							threeTargetValue : threeTargetValue,
							fourTargetValue : fourTargetValue
						},
						type : 'post',
						dataType:'json',
						cache: false,
						success : function(result) {
							if(result)
							{
						    	if(result.retCode == "0")
								{
						    		layer.msg('保存成功', {icon: 1});
									search();
						    		
								}else{
									
									layer.alert(result.resMsg, {icon: 2});
								}
						     }
						 }
					    });
			});
		
	}else{
	 $.ajax( {
			url : 'qs/targetConfig/updateTarget',
			data : 
			{
				id : id,
				flag : flag,
				businessUnitName : businessUnitName,
				oneTargetValue : oneTargetValue,
				twoTargetValue : twoTargetValue,
				threeTargetValue : threeTargetValue,
				fourTargetValue : fourTargetValue
			},
			type : 'post',
			dataType:'json',
			cache: false,
			success : function(result) {
				if(result)
				{
			    	if(result.retCode == "0")
					{
			    		layer.msg('保存成功', {icon: 1});
						search();
			    		
					}else{
						
						layer.alert(result.resMsg, {icon: 2});
					}
			     }
			 }
		    });
	} 
}
function depAutoComplete() {
	
	if (depArr.length > 0) {
		return;
	} else {
		$.ajax({
			type : "POST",
			url : "qs/targetConfig/getAllDep",
			success : function(data) {
				for (var i = 0; i < data.length; i++) {
					depArr.push(data[i]);
				}
			}
		});
	
	$("#depName").autocomplete({
	    source: depArr,
	    autoFocus : true
	});
 }
}
function depExists(input){
	  
	var isExists = false;
	var depName = input.value;
	if($.trim(depName)!=''){
		for(var i=0;i<depArr.length;i++){
			
			if(depName == depArr[i]){
				
				isExists =true;
			}
		}
		if(isExists==false){
			
		       $(input).val("");
		       return false;
		}
	}
}
</script>
</head>
<body>
<form id="targetForm" name="targetForm" method="post" action="">
<input type="hidden" name="tokenKey" value="${tokenKey}">
<input type="hidden" name="tokenValue" value="${tokenValue}">
<div id="accordion">
<h3>组织目标值列表</h3>
<div align="right">
<table width="95%">
	<tr>
		<td align="right">事业部：</td>
	 	<td><form:input path="dto.depName" style="width:250px" onfocus="depAutoComplete()" onblur="depExists(this)"/>  </td>
	 	<td align="right">年份：</td>
	 	<td>
	 	<form:select path="dto.year" class="year"   style="width:100px" >
	 						<form:option value="${dto.nowYear+1}" label="${dto.nowYear+1}年" />
							<form:option value="${dto.nowYear}" label="${dto.nowYear}年"/>
							<form:option value="${dto.nowYear-1}" label="${dto.nowYear-1}年" />
							<form:option value="${dto.nowYear-2}" label="${dto.nowYear-2}年" />
							</form:select>
		</td>
		<td >
			<input type="button" class="blue" value="查询" onclick="searchResetPage()">
			<input type="button" class="blue" value="重置" onclick="resetForm()">
		</td>
	</tr>
</table>
</div>
</div>
<table class="listtable">
	<tr>
		<th width="200px">组织架构</th>
		<th width="50px">Q1目标值</th>
		<th width="50px">Q2目标值</th>
		<th width="50px">Q3目标值</th>
		<th width="50px">Q4目标值</th>
		<th width="50px" style="display:none">一级部门</th>
		<th width="50px"  style="display:none">二级部门</th>
		<th width="50px">操作</th>
	</tr>
	<c:forEach items="${dto.dataList}" var="target">
	<tr>
		<td  style="text-align:left">${target.depName}</td>
	    <td  style="text-align:left"><input type="text" name="oneTargetValue"  value="${target.oneTargetValue}" onblur="checkTarget(this)"></td>
	    <td  style="text-align:left"><input type="text" name="twoTargetValue"  value="${target.twoTargetValue}"  onblur="checkTarget(this)"></td>
		<td  style="text-align:left"><input type="text" name="threeTargetValue"  value="${target.threeTargetValue}" onblur="checkTarget(this)"></td>
		<td  style="text-align:left"><input type="text" name="fourTargetValue"  value="${target.fourTargetValue}"  onblur="checkTarget(this)"></td>
		<td  style="text-align:left;display:none"><input type="text" name="businessUnitName"  value="${target.businessUnit}"  ></td>
		<td  style="text-align:left;display:none"><input type="text" name="prdDepName"  value="${target.prdDep}" ></td>
		<td  style="text-align:left"><input type="button" class='blue' name="submitBtn" id="submitBtn" value="保存" onclick="saveTarget(this, ${target.id})" ></td>
	</tr>
	</c:forEach>
</table>
<%@ include file="/WEB-INF/jsp/common/pager.jsp" %>
</form>
</body>
</html>
