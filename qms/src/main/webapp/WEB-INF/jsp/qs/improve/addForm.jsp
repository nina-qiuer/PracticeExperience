<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>
<html>
<head>
<script type="text/javascript">
var userArr = new Array();
$(document).ready(function(){
	$("#improve_form").validate({
		rules:{
			cmpAffair:{
		    	 required:true,
		    	 rangelength:[1,200]
		       },
		       impPerson:{
		    	   required:true,
		    	   rangelength:[1,20]
				},
				improvePoint:{
					 required:true,
			    	 rangelength:[1,200]
				},
				cmpId:{
	                digits:true,
	                min:0
				}
        },
        messages:{
        	cmpId:{digits:"请输入整数"},
        	cmpAffair:{required:"请输入投诉事宜",rangelength:"字数不超过200"},
        	improvePoint:{required:"请输入改进点",rangelength:"字数不超过200"},
        	impPerson:{required:"请输入责任人",rangelength:"字数不超过20"}
        
        }
	});
	
	if( !${addFlag}){//是否是更新操作
		textIsDisable(true);
	}
});

function textIsDisable(flag){
	var btnSubmit1 = document.getElementById("cmpId");
	var btnSubmit2 = document.getElementById("cmpAffair");
	var btnSubmit3 = document.getElementById("improvePoint");
	if(flag){
		btnSubmit1.disabled= "disabled";
		btnSubmit2.disabled= "disabled";
		btnSubmit3.disabled= "disabled";
	}else{
		$("#cmpId").removeAttr("disabled");
		$("#cmpAffair").removeAttr("disabled");
		$("#improvePoint").removeAttr("disabled");
	}
	
}

function doSubmit(input, flag) {
	var url = "";
	if(flag == 1){
		textIsDisable(false);
		url ="qs/cmpImprove/submitImproveBill";
	}
    if($('#improve_form').valid()){
    	disableButton(input);
		$.ajax({
			url : url,
			data : $('#improve_form').serialize(),
			type : 'post',
			dataType:'json',
			success : function(result) {
		    	if(result.retCode == "0"){
		    		alert("操作成功！", {icon: 1});
		    		parent.searchReaload();
		    		parent.layer.closeAll();
				 }else{
					enableButton(input);
					var flag = $("#addFlag").val();
					if("false" == flag){
						textIsDisable(true);
					}
					layer.alert(result.resMsg, {icon: 2});
				}
			}
		});
	 }else{
		var flag = $("#addFlag").val();
		if("false" == flag){
			textIsDisable(true);
		}
	 }    			
}

function userAutoComplete() {
	if (userArr.length > 0) {
		return;
	} else {
		$.ajax({
			type : "POST",
			url : "common/user/getUserNamesInJSON",
			success : function(data) {
				for (var i = 0; i < data.length; i++) {
					userArr.push({
						label : data[i].label,
						value : data[i].realName
					});
				}
			}
		});
	$("#impPerson").autocomplete({
	    source: userArr,
	    autoFocus : true
	}).data( "ui-autocomplete" )._renderItem = function( ul, item ) {
		return $( "<li>" )
        .append( "<a>" + item.value + "</a>" )
        .appendTo( ul );
	};
	}
}
</script>
</head>
<body>
<form name="improve_form" id="improve_form" method="post" action="" >
<form:hidden path="cmpImprove.id"/>
<input type="hidden" name="addFlag" id="addFlag" value="${addFlag}"/>
<table class="datatable" >
	<tr>
		<th align="right" style="width:80px" >投诉单号：</th>
		<td colspan="3">
		   <input type="text" name="cmpId" id="cmpId" value="${cmpImprove.cmpId}"/>
		</td>
	</tr>
	<tr>
		<th align="right" style="width:80px" height="25">投诉事宜：</th>
		<td colspan="3">
			<textarea id="cmpAffair" name="cmpAffair" style="width:700px;height:150px;">${cmpImprove.cmpAffair}</textarea>
		</td>
	</tr>
	<tr>
		<th align="right" width="80" height="25">改进点：</th>
		<td colspan="3">
			<textarea id="improvePoint" name="improvePoint" style="width:700px;height:150px;">${cmpImprove.improvePoint}</textarea>
		</td>
	</tr>
	<tr>
		<th align="right" style="width:80px" >责任人：</th>
		<td colspan="3">
		   <input type="text" name="impPerson" id="impPerson" value="${cmpImprove.impPerson}" onfocus="userAutoComplete()"/>
		</td>
	</tr>
	<c:if test="${!addFlag}">
		<tr>
			<th>附件：</th>
			<td colspan="3">
				<table>
					<tr>
						<c:forEach items="${attachList}" var="upload" >
						  <td>
						     <a href="${upload.path}" target="_blank">${upload.name}</a>
						  </td>
					    </c:forEach>
					</tr>
				</table>
			</td>
		</tr>
	</c:if>
	<c:if test="${addFlag}">
		<tr>
			<th align="right" width="80">附件：</th>
			<td colspan="3">
			<div>
				<iframe name="attachMain" src="qs/cmpImprove/${cmpImprove.id}/toShowAttach" width="100%"  scrolling="no" frameborder="0" id="attachMain" onload="iFrameHeight(this)"></iframe>
			</div>
			</td>
		</tr>
	</c:if>
	<tr>
		<th align="right" width="100" height="30">操作</th>
		<td colspan="3">
			<%-- <c:if  test="${cmpImprove.id == null || cmpImprove.id == 0}">
				<input type="button" class="blue" value="保存" name="saveBtn" id="saveBtn" onclick="doSubmit(this,0)">
			</c:if> --%>
			<input type="button" class="blue" value="提交" name="submitBtn" id="submitBtn" onclick="doSubmit(this,1)">
		</td>
	</tr>
</table>
</form>
</body>
</html>