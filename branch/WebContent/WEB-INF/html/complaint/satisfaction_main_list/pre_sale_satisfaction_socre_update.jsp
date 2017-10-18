<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${CONFIG.res_url}/script/jquery/plugin/jquery.form.js"></script>
<style type="text/css">
.common-box {
	border: 1px solid #8CBFDE;
	margin: 0 0 10px 0;
}
</style>

<script type="text/javascript">
function calSocre(tag){
	var value =  getRadioValue(tag);
	$('#'+tag+"_socre").html(value) ;
	calTotal();
}

function calTotal(){
	var professionalValue = getRadioValue("professional");
	var timelinessValue = getRadioValue("timeliness");
	var patienceValue = getRadioValue("patience");
	var responsibilityValue = getRadioValue("responsibility");
	var value=0;
	if(professionalValue!=""&&professionalValue!=null){
		value = value + parseFloat(professionalValue) ;
	}
	if(timelinessValue!=""&&timelinessValue!=null){
		value = value + parseFloat(timelinessValue) ;
	}
	if(patienceValue!=""&&patienceValue!=null){
		value = value + parseFloat(patienceValue) ;
	}
	if(responsibilityValue!=""&&responsibilityValue!=null){
		value = value + parseFloat(responsibilityValue) ;
	}
	$("#total_score").html(value) ; 
	$("#total").val(value) ;
	var satisPercent = value/16*100+"%";
	$("#satisfaction_percent").html(satisPercent) ;
	$("#preSaleSatisfaction").val(satisPercent) ;
	
}

function getRadioValue(RadioName){
    var obj;   
    obj=document.getElementsByName(RadioName);
    if(obj!=null){
        var i;
        for(i=0;i<obj.length;i++){
            if(obj[i].checked){
                return obj[i].value;           
            }
        }
    }
    return null;
}

$(document).ready(function() { 
    var options = { 
        success:       success_function,  // post-submit callback 
    }; 
 
    $('#form').ajaxForm(options); 
    $("#professional_socre").html("${entity.professional }");
    $("#timeliness_socre").html("${entity.timeliness }");
    $("#patience_socre").html("${entity.patience }");
    $("#responsibility_socre").html("${entity.responsibility }");
    $("#total_score").html("${entity.total }");
    $("#satisfaction_percent").html("${entity.preSaleSatisfaction }");
});

function success_function() {
	alert("提交成功!");
	var parent_salerName = $("#parent_salerName").val();
	var parent_deptId = $("#parent_deptId").val();
	parent.parent.frames['right'].location.href = "preSaleSatisfaction-toRight?name="+parent_salerName+"&id="+parent_deptId; 
}

function onSaveInfoClicked() {
	if(checkValue()){
		$('#form').attr("action", "preSaleSatisfaction-updateSocre");
		$('#form').submit();
	}
}

function checkValue(){
	var professionalValue = getRadioValue("professional");
	if(professionalValue==null||professionalValue==""){
		alert("请选择业务熟练度、专业性！");
		return false;
	}
	if($("#professional_text").val().length>100){
		alert("业务熟练度、专业性不满意问题记录过长！");
		return false;
	}
	var timelinessValue = getRadioValue("timeliness");
	if(timelinessValue==null||timelinessValue==""){
		alert("请选择解决问题及时性！");
		return false;
	}
	if($("#timeliness_text").val().length>100){
		alert("解决问题及时性不满意问题记录过长！");
		return false;
	}
	var patienceValue = getRadioValue("patience");
	if(patienceValue==null||patienceValue==""){
		alert("请选择热情、耐心、周到！");
		return false;
	}
	if($("#patience_text").val().length>100){
		alert("热情、耐心、周到不满意问题记录过长！");
		return false;
	}
	var responsibilityValue = getRadioValue("responsibility");
	if(responsibilityValue==null||responsibilityValue==""){
		alert("请选择有责任心，是否推脱责任！");
		return false;
	}
	if($("#responsibility_text").val().length>100){
		alert("有责任心，是否推脱责任不满意问题记录过长！");
		return false;
	}
	return true;
}
</script>
</head>
<body>
<div class="common-box">
<form name="form" id ="form" method="post" enctype="multipart/form-data">
<input type="hidden" name="satisId" id="satisId" value="${entity.preSaleSatisId }" />
<input type="hidden" name="total" id="total" value="${entity.total }" />
<input type="hidden" name="preSaleSatisfaction" id="preSaleSatisfaction" value="${entity.preSaleSatisfaction } "/>
	<input type="hidden" name="parent_salerName" id="parent_salerName" value="${request.parent_salerName }" />
	<input type="hidden" name="parent_deptId" id="parent_deptId" value="${request.parent_deptId }" />
	<table class="listtable" width="100%">
<thead>
	<th>满意度打分项大类</th>
	<th>满意度打分项小类</th>
	<th>回访问题设置</th>
	<th>不满意问题记录</th>
	<th>得分</th>
</thead>
<tbody>
	<tr align="center" class="trbg">
		<td rowspan="2">业务技能</td>
		<td>业务熟练度、专业性</td> 
		<td align="left">问：您对您的专属客服业务熟练度及专业性满意吗？<br>
			<input type="radio" name="professional" id="professional_1"  value="4" onchange="calSocre('professional')" <c:if test="${entity.professional==4 }">checked="checked"</c:if>>非常满意</input>
			<input type="radio" name="professional" id="professional_2" value="3" onchange="calSocre('professional')" <c:if test="${entity.professional==3 }">checked="checked"</c:if>>满意</input>
			<input type="radio" name="professional" id="professional_3" value="2"  onchange="calSocre('professional')" <c:if test="${entity.professional==2 }">checked="checked"</c:if>>一般</input>
			<input type="radio" name="professional" id="professional_4" value="0"  onchange="calSocre('professional')" <c:if test="${entity.professional==0 }">checked="checked"</c:if>>不满意</input>
		</td> 
		<td>
			<textarea id="professional_text" name="professional_text"  rows="2" cols="25">${entity.professionalComment }</textarea>
		</td>
		<td id="professional_socre"></td> 
	</tr>
	<tr align="center"  class="trbg" >
		<td>解决问题及时性</td> 
		<td align="left">问：您对您的专属客服解决问题的及时性满意吗？<br>
			<input type="radio" name="timeliness" id="timeliness_1" value="4"   onchange="calSocre('timeliness')" <c:if test="${entity.timeliness==4 }">checked="checked"</c:if>>非常满意</input>
			<input type="radio" name="timeliness" id="timeliness_2" value="3"   onchange="calSocre('timeliness')" <c:if test="${entity.timeliness==3 }">checked="checked"</c:if>>满意</input>
			<input type="radio" name="timeliness" id="timeliness_3" value="2"   onchange="calSocre('timeliness')" <c:if test="${entity.timeliness==2 }">checked="checked"</c:if>>一般</input>
			<input type="radio" name="timeliness" id="timeliness_4" value="0"   onchange="calSocre('timeliness')" <c:if test="${entity.timeliness==0 }">checked="checked"</c:if>>不满意</input>
		</td> 
		<td>
			<textarea  id="timeliness_text"  name="timeliness_text" rows="2" cols="25">${entity.timelinessComment }</textarea>
		</td>
		<td id="timeliness_socre"></td> 
	</tr>
	<tr align="center" class="trbg">
		<td rowspan="2">服务态度</td>
		<td>热情、耐心、周到</td> 
		<td align="left">问：您的专属客服在与您沟通时是否做到热情、耐心、周到？<br>
			<input type="radio" name="patience" id="patience_1" value="4"   onchange="calSocre('patience')" <c:if test="${entity.patience==4 }">checked="checked"</c:if>>完全做到</input>
			<input type="radio" name="patience" id="patience_2" value="3"   onchange="calSocre('patience')" <c:if test="${entity.patience==3 }">checked="checked"</c:if>>做到</input>
			<input type="radio" name="patience" id="patience_3" value="2"   onchange="calSocre('patience')" <c:if test="${entity.patience==2 }">checked="checked"</c:if>>一般</input>
			<input type="radio" name="patience" id="patience_4" value="0"   onchange="calSocre('patience')" <c:if test="${entity.patience==0 }">checked="checked"</c:if>>没做到</input>
		</td> 
		<td>
			<textarea  id="patience_text"  name="patience_text"  rows="2" cols="25">${entity.patienceComment }</textarea>
		</td>
		<td id="patience_socre"></td> 
	</tr>
	<tr align="center"  class="trbg" >
		<td>有责任心，是否推脱责任</td> 
		<td align="left">问：您的专属客服在与您的沟通过程中是否做到认真负责？<br />是否出现找借口、推脱责任的事情？<br />
			<input type="radio" name="responsibility" id="responsibility_1" value="4"  onchange="calSocre('responsibility')" <c:if test="${entity.responsibility==4 }">checked="checked"</c:if>>完全做到</input>
			<input type="radio" name="responsibility" id="responsibility_2" value="3"  onchange="calSocre('responsibility')" <c:if test="${entity.responsibility==3 }">checked="checked"</c:if>>做到</input>
			<input type="radio" name="responsibility" id="responsibility_3" value="2"  onchange="calSocre('responsibility')" <c:if test="${entity.responsibility==2 }">checked="checked"</c:if>>一般</input>
			<input type="radio" name="responsibility" id="responsibility_4" value="0"  onchange="calSocre('responsibility')" <c:if test="${entity.responsibility==0 }">checked="checked"</c:if>>没做到</input>
		</td> 
		<td>
			<textarea id="responsibility_text"  name="responsibility_text" rows="2" cols="25">${entity.responsibilityComment }</textarea>
		</td>
		<td id="responsibility_socre"></td> 
	</tr>
	<tr align="center"  class="trbg" >
		<td>服务改进建议</td>
		<td></td>
		<td align="left">问：请问您还有其他建议吗？<br />
			<textarea id="other_text"  name="other_text" rows="3" cols="40">${entity.suggestion }</textarea>
		</td> 
		<td align="right">总计：</td>
		<td id="total_score"></td>
	</tr>
	<tr align="center" class="trbg">
		<td>客服服务满意度</td>
		<td colspan="3"></td>
		<td id="satisfaction_percent"></td> 
	</tr>
	<tr align="center"  class="trbg" >
		<td>点评状态</td> 
		<td colspan="4">
			<input type="radio" name="status" id="status_2" value="1"  <c:if test="${entity.status==1 }">checked="checked"</c:if>>点评完成</input>
			<input type="radio" name="status" id="status_3" value="2"  <c:if test="${entity.status==2 }">checked="checked"</c:if>>待再次回访</input>
			<input type="radio" name="status" id="status_4" value="3"  <c:if test="${entity.status==3 }">checked="checked"</c:if>>点评失败</input>
		</td>
	</tr>
	<tr>
    	<td colspan="5" align="center">
		<input  class="pd5" type="button" id="saveInfo" value="确定" onclick="onSaveInfoClicked()">
	</td>
</tr>
</tbody>
</table>
</form>
</div>
</body>
	
