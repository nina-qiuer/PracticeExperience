<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/html/head.jsp"%>
<link rel="stylesheet" type="text/css" href="${CONFIG.res_url}script/jquery/plugin/auto_complete/jquery.autocomplete.css"/>
<script type="text/javascript" src="${CONFIG.res_url}script/jquery/plugin/auto_complete/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}script/jquery/plugin/KindEditor/kindeditor-min.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}script/jquery/plugin/KindEditor/lang/zh_CN.js"></script>
<link rel="stylesheet" type="text/css" href="${CONFIG.res_url}script/jquery/plugin/KindEditor/css/default.css"/>
<title>投诉改进报告</title>
<style type="text/css">
.common-box {
	border: 1px solid #8CBFDE;
	margin: 0 0 10px 0;
}
.common-box-hd {
	margin-top: 1px;
	padding-left: 10px;
	background: #C6E3F1;
	color: #005590;
	font-size: 14px;
	font-weight: bold;
	height: 25px;
	line-height: 25px;
	border-bottom: 1px solid #8CBFDE;
	position: relative;
}

.common-box-hd span.title2 {
	cursor: pointer;
	display: inline-block;
	line-height: 25px;
	margin-right: 15px;
}
</style>
<script type="text/javascript">

$(document).ready(function(){
	/* $(document).keydown(function(event) {  //屏蔽回车提交
	  if (event.keyCode == 13) {
	    $('form').each(function() {
	      event.preventDefault();
	    });
	  }
	});  */
	
	userAutoComplete();
});

/**
 * 点击复选框，改进点自动带出
 */
function addImprovePoint(){
	var ss = checkBoxIsChoose();
	
   	$("#improvePoint").val(ss);
}

/**
 * 单选按钮切换, 文本框是否置灰操作
 */
function improveRadioClick(){
	//获得单选按钮列
	var radios = document.getElementsByName("impPerson");
	
    for(var i=0; i<radios.length; i++)  
    {   
        if(radios[i].checked && i == 0){  
             $("#impPersonName").removeAttr("disabled");//取消不可用
        }else if(radios[i].checked && i == 1){
        	$("#impPersonName").val("");
        	$("#impPersonName").attr("disabled","disabled");//不可用
        }   
    }   
}


/**
 * 人员信息自动匹配
 */
function userAutoComplete() {
	var selectDelPeopleArray = new Array();
	<c:forEach items="${allUsers}" var="userItem">
		selectDelPeopleArray.push({'id' : '${userItem.id}', 'realName' : '${userItem.realName}', 'userName' : '${userItem.userName}', 'name' : '${userItem.realName}${userItem.userName}'});
	</c:forEach>

	$('#impPersonName').autocomplete(selectDelPeopleArray, {
		max : 500, //列表里的条目数 
		minChars : 0, //自动完成激活之前填入的最小字符 
		width : 120, //提示的宽度，溢出隐藏 
		scrollHeight : 300, //提示的高度，溢出显示滚动条 
		matchContains : true, //包含匹配，就是data参数里的数据，是否只要包含文本框里的数据就显示 
		autoFill : false, //自动填充 
		mustMatch : false,
		dataType : 'json',
		parse: function(data) {
			var rows = [];
			var list = data;
	        for(var i=0; i<list.length; i++){
	        	rows[rows.length] = {
	            data: list[i],
	            value: list[i].name,
	            result: list[i].name
	            };
	        }
	        return rows;
	    },   
		formatItem : function(row, i, max) {
			return row.realName;
		},
		formatMatch : function(row, i, max) {
			return row.name;
		},
		formatResult : function(row) {
			return row.realName;
		}

	}).result(function(event, row, formatted) {
		$('#impPersonId').val(row.id);
	});
}

/**
 * 提交投诉改进报告
 */
function saveImproveBill(input){
	var cmpAffair = checkBoxIsChoose();
    if(cmpAffair == ""){
    	alert("请选择投诉事宜！");
    	return;
    }
  	var improvePoint = $("#improvePoint").val();
  	if(improvePoint == "" || improvePoint == "\n"){
  		alert("请填写改进点！");
    	return;
  	}
    
	var radios = document.getElementsByName("impPerson");//获得单选按钮列
	var impPerson = $("#impPersonName").val();
    if(radios[0].checked && impPerson == ""){  
    	alert("请填写改进人！");
    	return;
    }else if(radios[1].checked){
    	impPerson = "系统";
    } 
    
  	//获取表单提交按钮
	var btnSubmit = document.getElementById("improveSubmit");
	//将表单提交按钮设置为不可用，这样就可以避免用户再次点击提交按钮
	btnSubmit.disabled= "disabled";
    var complaintId = $("#complaintId").val();
    $.ajax( {
		url : 'complaint-saveImproveBill',
		data : {
			"complaintId" : complaintId,
			"cmpAffair" : cmpAffair,
			"improvePoint" : improvePoint,
			"improvePerson" : impPerson
		},
		type : 'post',
		dataType:'json',
		success : function(result) {
			if(result){
		    	if(result.retObj == "success"){
		    		alert("提交成功");
		    		var index = parent.layer.getFrameIndex(window.name);//获取窗口索引
                    parent.layer.close(index);  		
				}else{
					enableButton(input);
					alert(result.resMsg);
				}
		     }
		 },
		 error: function() {
			 enableButton(input);
			 alert("发送失败");
		 }
	 });
}

/**
 * 复选框选择判断
 */
function checkBoxIsChoose(){
	var aa = document.getElementsByName("cmpReason");
    var ss = "";
    var index = 1;
    for (var i = 0; i < aa.length; i++) {
    	if (aa[i].checked) {
            ss += index + ". " + aa[i].value + "\n";
            index++;
        }
    }
    
    return ss;
}
</script>
</head>
<body>
	<div class="common-box">
		<form name="form" id="form" method="post"  enctype="multipart/form-data" action="">
			<input type="hidden" name="complaintId" id="complaintId" value="${complaintId}" />
			<table width="100%" style="TABLE-LAYOUT: fixed;" class="datatable">
				<tr>
					<th width="100">投诉事宜：</th>
					<td>
						<table>
							<c:forEach items="${reasonList}" var="reason" >
								<tr><td>
									<label><input name="cmpReason" type="checkbox" value="${reason}" onclick="addImprovePoint()"/>&nbsp&nbsp&nbsp&nbsp ${reason}</label> 
							    </td></tr>
				 			</c:forEach>
			 			</table>
					</td>
				</tr>
				<tr>
					<th width="100">改进点：</th>
					<td>
					    <textarea id="improvePoint" name="improvePoint" style="width:615px;height:150px"></textarea>
					</td>
				</tr>
				<tr>
					<th width="100">责任人：</th>
					<td>
					    <input type="radio" name="impPerson" value="" onclick="improveRadioClick()" checked/>&nbsp&nbsp
					    <input type="text" id="impPersonName" name="impPersonName" style="width:90px"/>
					    <input type="hidden" name="impPersonId" id="impPersonId"/>&nbsp&nbsp&nbsp&nbsp
						<input type="radio" name="impPerson" onclick="improveRadioClick()" value="系统"/>&nbsp&nbsp&nbsp系统
					</td>
				</tr>
				<tr>
					<td>
						<iframe src="complaint-improveUpload?complaintId=${complaintId }&type=act" frameborder="0" width="750" height="150"></iframe>
					</td>
				</tr>
				<tr>
					<th></th>
					<td><input class="pd5" type="button" id="improveSubmit" name="save" value="提交" onclick="saveImproveBill(this)" /></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>