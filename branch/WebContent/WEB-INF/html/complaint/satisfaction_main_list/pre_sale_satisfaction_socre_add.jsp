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
});

function success_function() {
	alert("提交成功!");
	var parent_salerName = $("#parent_salerName").val();
	var parent_deptId = $("#parent_deptId").val();
	parent.parent.frames['right'].location.href = "preSaleSatisfaction-toRight?name="+parent_salerName+"&id="+parent_deptId; 
}

function updateInfoClicked() {
	if(checkValue()){
		$('#form').attr("action", "preSaleSatisfaction-addSocre");
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
<input type="hidden" name="satisId" id="satisId" value="${request.satisId }" />
<input type="hidden" name="total" id="total" value="" />
<input type="hidden" name="preSaleSatisfaction" id="preSaleSatisfaction" value="" />
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
			<label><input type="radio" name="professional" id="professional_1" value="4" onchange="calSocre('professional')">非常满意</input>　</label>
			<label><input type="radio" name="professional" id="professional_2" value="3" onchange ="calSocre('professional')">满意</input>　</label>
			<label><input type="radio" name="professional" id="professional_3" value="2" onchange="calSocre('professional')">一般</input>　</label>
			<label><input type="radio" name="professional" id="professional_4" value="0" onchange="calSocre('professional')">不满意</input></label>
		</td> 
		<td>
			<textarea id="professional_text" name="professional_text"  rows="2" cols="25"></textarea>
		</td>
		<td id="professional_socre"></td> 
	</tr>
	<tr align="center"  class="trbg" >
		<td>解决问题及时性</td> 
		<td align="left">问：您对您的专属客服解决问题的及时性满意吗？<br>
			<label><input type="radio" name="timeliness" id="timeliness_1" value="4" onchange="calSocre('timeliness')">非常满意</input>　</label>
			<label><input type="radio" name="timeliness" id="timeliness_2" value="3" onchange="calSocre('timeliness')">满意</input>　</label>
			<label><input type="radio" name="timeliness" id="timeliness_3" value="2" onchange="calSocre('timeliness')">一般</input>　</label>
			<label><input type="radio" name="timeliness" id="timeliness_4" value="0" onchange="calSocre('timeliness')">不满意</input></label>
		</td> 
		<td>
			<textarea  id="timeliness_text"  name="timeliness_text" rows="2" cols="25"></textarea>
		</td>
		<td id="timeliness_socre"></td> 
	</tr>
	<tr align="center" class="trbg">
		<td rowspan="2">服务态度</td>
		<td>热情、耐心、周到</td> 
		<td align="left">问：您的专属客服在与您沟通时是否做到热情、耐心、周到？<br>
			<label><input type="radio" name="patience" id="patience_1" value="4" onchange="calSocre('patience')">完全做到</input>　</label>
			<label><input type="radio" name="patience" id="patience_2" value="3" onchange="calSocre('patience')">做到</input>　</label>
			<label><input type="radio" name="patience" id="patience_3" value="2" onchange="calSocre('patience')">一般</input>　</label>
			<label><input type="radio" name="patience" id="patience_4" value="0" onchange="calSocre('patience')">没做到</input></label>
		</td> 
		<td>
			<textarea  id="patience_text"  name="patience_text"  rows="2" cols="25"></textarea>
		</td>
		<td id="patience_socre"></td> 
	</tr>
	<tr align="center"  class="trbg" >
		<td>有责任心，是否推脱责任</td> 
		<td align="left">问：您的专属客服在与您的沟通过程中是否做到认真负责？<br>是否出现找借口、推脱责任的事情？<br>
			<label><input type="radio" name="responsibility" id="responsibility_1" value="4" onchange="calSocre('responsibility')">完全做到</input>　</label>
			<label><input type="radio" name="responsibility" id="responsibility_2" value="3" onchange="calSocre('responsibility')">做到</input>　</label>
			<label><input type="radio" name="responsibility" id="responsibility_3" value="2" onchange="calSocre('responsibility')">一般</input>　</label>
			<label><input type="radio" name="responsibility" id="responsibility_4" value="0" onchange="calSocre('responsibility')">没做到</input></label>
		</td>
		<td>
			<textarea id="responsibility_text"  name="responsibility_text" rows="2" cols="25"></textarea>
		</td>
		<td id="responsibility_socre"></td> 
	</tr>
	<tr align="center"  class="trbg" >
		<td>服务改进建议</td>
		<td></td>
		<td align="left">问：请问您还有其他建议吗？<br />
			<textarea id="other_text"  name="other_text" rows="3" cols="40"></textarea>
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
			<label><input type="radio" name="status" id="status_2" value="1">点评完成</input>　</label>
			<label><input type="radio" name="status" id="status_3" value="2">待再次回访</input>　</label>
			<label><input type="radio" name="status" id="status_4" value="3">点评失败</input></label>
		</td>
	</tr>
	<tr>
    	<td colspan="5" align="center">
    		<input class="pd5" type="button" id="updateInfo" value="确定" onclick="updateInfoClicked()">
    	</td>
    </tr>
</tbody>
</table>
</form>
</div>
</body>
	
