<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>
<html>
<head>
<style type="text/css">

		.subTitle {
		    margin-top: 10px;
		    padding-left: 10px;
		    background: #C6E3F1 none repeat scroll 0% 0%;
		    color: #005590;
		    font-size: 14px;
		    font-weight: bold;
		    height: 30px;
		    line-height: 25px;
		    border-bottom: 1px solid #8CBFDE;
		    position: relative;
		}

	</style>
<script type="text/javascript">
var depArr = new Array();
function depAutoComplete(len) {
	if (depArr.length > 0) {
		
	}else{
		$.ajax({
				type : "POST",
				url : "qc/qcBill/getDepNames",
				success : function(data) {
					for (var i = 0; i < data.length; i++) {
						depArr.push(data[i]);
					}
				}
			});
	}	
	
	$("#depName"+len).autocomplete({
		    source: depArr,
		    autoFocus : true
		});
	  
	}
	
	
  $(function(){   

		$("#input1").click(function(){
		  var trLen =  $("#depTable").find("tr").length ;
		    $("#depTable").append("<tr><td width='30px'>"+trLen+"</td><td width='100px'><input type='text'  name='depName"+trLen+"' id='depName"+trLen+"' value='' style='width:250px' onfocus='depAutoComplete("+trLen+")' onblur='depExists(this)'/></td><td width='50px'><input type='text' name='respRate"+trLen+"'   id='respRate"+trLen+"' value='0' onblur='checkAccount(this)' />%</td></tr>") ;
		});

		$("#input2").click(function(){
			
		    var trLen =  $("#depTable").find("tr").length ;
		    if(trLen>1){
		    $("#depTable").find("tr:eq("+(trLen-1)+")").remove() ;
		    }
		});
});
  function checkAccount(input){
		
		var account = input.value;
		if($.trim(account) ==''){
			
			 layer.alert("应为0到100整数",{icon: 2});	
			 $(input).val(0);
			 return false;
		}
		var compar = new RegExp("^(((\\d|[1-9]\\d)(\\.\\d{1,2})?)|100|100.0|100.00)$");//^((\d|[1-9]\d)?|100)$
		if(!compar.test(account)){
			
	       layer.alert("应为0到100整数或带两位小数",{icon: 2});	
	       $(input).val(0);
	       return false;
			 
		}
		
	}
  function depExists(input){
	  
		var isExists = false;
		var depName = input.value;
		for(var i=0;i<depArr.length;i++){
			
			if(depName == depArr[i]){
				
				isExists =true;
			}
		}
		if(isExists==false){
			
			   layer.alert("部门不存在",{icon: 2});	
		       $(input).val("");
		      return false;
		}
	}
function saveDep(id){
	
	var result = getBasicDepTemplate(); //封装数据操作 
	if(result){ 
		
		if(basic_dep_template.length<1){
			
		 layer.alert("请添加数据",{icon: 2});	
	  	 return false; 
	 } 
	
	for(var i=0;i<basic_dep_template.length;i++){ 
		
		basic_dep_template[i]=JSON.stringify(basic_dep_template[i]); 
		
	} 	
	$.ajax( {
		
			url : 'qs/respDep/toAdd',
			data : 	
			  {  
				"tempLateList":"["+basic_dep_template+"]"
				}, 
			type : 'post',
			dataType:'json',
			cache : false,
			success : function(result) {
				if(result)
				{
			    	if(result.retCode == "0")
					{
			    	    parent.search();
			    		parent.layer.closeAll();
			    		
					 }else{
						
						layer.alert(result.resMsg, {icon: 2});
					}
			     }
			 }
	  });
	}
}

var basic_dep_template = {}; 
var did = "depTable"; 	
//获取模板值 
function getBasicDepTemplate(){ 
	var id = $("#id").val();
	basic_dep_template=[]; 
	var trs = eval("$(\"#"+did + " tbody tr\")"); 
	var result = true; 
	var rate = 0;
	var rateD = 0;
	$.each(trs,function(i,tr){ 
		if(i>0){
			var depName = $(tr).find("input[name='depName"+i+"']").val(); 
			var respRate = $(tr).find("input[name='respRate"+i+"']").val(); 
			
			if($.trim(depName)==''){ 
				
				layer.alert("责任部门不能为空",{icon: 2});	
				result = false;
				return false; 
				
			} 
			rate += Number(respRate);
			var basic_dep_templateBean=
			{ 
			 "depName":depName,"respRate":respRate,id:id
			};
			basic_dep_template.push(basic_dep_templateBean); 
		}
	}) ;
	rateD  = rate;
	if(rate!=100 && rateD!=0){
		
		layer.alert("占比必须100%",{icon: 2});	
		return false; 
	}
	return result; 
	} 
</script>
</head>
<body>
<form name="dep_form" id="dep_form" method="post" action="" >
<form:hidden path="dep.id"/>
<table class="datatable" >
<tr>
	<th align="right" width="100" height="30">一级责任部门：</th>
	<td>
	     <form:input path="dep.firstDepName"  readonly="true"/>
	</td>
	<th align="right" width="100" height="30">二级责任部门：</th>
	<td>
	     <form:input path="dep.twoDepName" readonly="true"/>
	</td>
	<th align="right" width="100" height="30">三级责任部门：</th>
	<td>
	     <form:input path="dep.threeDepName" readonly="true"/>
	</td>
</tr>
 <tr>
		<td colspan="8"><div class="subTitle">更新责任部门 
		<input type="button" class="blue"  id="input1" name="input1" value="添加一行"></input>
		<input type="button" class="blue" id="input2" name="input2" value="删除一行"></input>
		<input type="button" class="blue" id="savebtn" name="savebtn" value="保存" onclick="saveDep('depTable')"></input>
		</div>
		</td>
 </tr>
 </table>
 <table class="listtable" id="depTable">
    <tr>
     <th width="30px">序号</th><th width="100px">责任部门</th><th width="50px">责任占比</th>
    </tr>
 </table>
</form>
</body>
</html>