<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<script  type="text/javascript">
var qcTypeArr = new Array();
$(document).ready(function() {
	WdatePicker('${dto.addTimeBgn}');
	WdatePicker('${dto.addTimeEnd}');
	$(".accordion").each(function() {
		$(this).accordion({
	    	collapsible: true,
//	    	active:false, //默认折叠
	    	heightStyle: "content"
	    });
	});
	
	$("#searchForm").validate({
		rules:{
			qcId:{
                digits:true,
                min:0
			}
        },
        messages:{
        	qcId:{digits:"请输入整数"}
        }
		
	});
});

function qcTypeAutoComplete() {
	
	if (qcTypeArr.length > 0) {
		return;
	} else {
		$.ajax({
			type : "POST",
			url : "qc/qcType/getQtNames",
			success : function(data) {
				for (var i = 0; i < data.length; i++) {
					qcTypeArr.push(data[i]);
				}
			}
		});
	
	$("#qcTypeName").autocomplete({
	    source: qcTypeArr,
	    autoFocus : true
	});
	
	}
}

function qcTypeExists(input){
	  
	var isExists = false;
	var qcTypeName = input.value;
	if($.trim(qcTypeName)!=''){	
		for(var i=0;i<qcTypeArr.length;i++){
			
			if(qcTypeName == qcTypeArr[i]){
				
				isExists =true;
			}
		}
		if(isExists==false){
			
		       $(input).val("");
		       return false;
		}
	}
}
function search() {
	
	  $("#searchForm").attr("action", "qc/auxiliaryBill/list");
	  $("#searchForm").submit();

}
function setMaxDate(id){
	WdatePicker({maxDate:'#F{$dp.$D(\''+id+'\')}'});
}

function setMinDate(id){
	WdatePicker({minDate:'#F{$dp.$D(\''+id+'\')}'});
}



//重置搜索框内容：直接使用reset会影响所有form中内容
function resetSearchTable() {
	$('.search :text').val('');
}

function exportExcel(input){
	
	var qcTypeName = $('#qcTypeName').val();
	if($.trim(qcTypeName)==''){
		
		 layer.alert("质检类型不能为空",{icon: 2});
		 return false;
	}
	disableButton(input);
	$.ajax( {
		url : 'qc/auxiliaryBill/checkExport',
		data : $('#searchForm').serialize(),
		type : 'post',
		dataType:'json',
		cache : false,
		success : function(result) {
			
			if(result)
			{
		    	if(result.retCode == "0")
				{
		    		$("#searchForm").attr("action","qc/auxiliaryBill/exports"); 
		    		$("#searchForm").submit(); 	
				 }else{
					
					 layer.alert(result.resMsg,{icon: 2});
					 enableButton(input);
				}
		     }
		 }
	    }); 
}

</script>
<title>运营质检辅助表列表</title>
<style type="text/css">
/*搜索框表格样式*/
.search {
	font-size: 12px;
	font-family: Microsoft YaHei;
}

.search td:nth-child(even) {
	text-align:  ;
}

.search td:nth-child(odd) {
	text-align: right;
}

.search td input[type=text] {
	width: 100px;
}

</style>
</head>
<body >
<form name="searchForm" id="searchForm" method="post" action="" >
<div class="accordion" style="width:3800px" >
<h3>运营质检辅助表列表</h3> 
<div align="left" >
<table width="100%" class="search">
	<tr>
		<td style="width:2%">质检单号：</td>
		<td style="width:5%">
			 <form:input path="dto.qcId" /> 
		</td>
		<td style="width:3%">创建时间：</td>
		<td style="width:7%">
			<form:input path="dto.addTimeBgn"  onfocus="setMaxDate('addTimeEnd')" />
			至
			<form:input path="dto.addTimeEnd"  onfocus="setMinDate('addTimeBgn')" />
		</td>
		<td style="width:3%">质检类型：</td>
		<td style="width:10%">
			 <form:input path="dto.qcTypeName" style="width:250px" onfocus="qcTypeAutoComplete()"  onblur="qcTypeExists(this)"/> 
		</td>
		<td style="width:5%">
			<input type="button" class="blue" 	value="查询" onclick="searchResetPage()"/>
			<input type="button" class="blue" id ="exports" name="exports" value="导出" onclick="exportExcel(this)" />
		    <input type="button" class="blue" value="重置" onclick="resetSearchTable()"/>
		</td>
		<td  style="width:65%">
		</td>
	</tr>
</table>
</div>
</div>
<div style="overflow-x:auto;overflow-y:hidden;width:3800px"  >
<table class="listtable" style="border-collapse:collapse;" cellpadding="0" cellspacing="0" width="3800px" >
  	<tr>
		 <th class="qcId" >质检单号</th>
		 <th class="qcTypeName">质检类型</th>
		 <th class="addTime">添加时间</th>
 		 <th class="field1">field1</th>
  		 <th class="field2">field2</th>
  		 <th class="field3">field3</th>
  		 <th class="field4">field4</th>
  		 <th class="field5">field5</th>
  		 <th class="field6">field6</th>
  		 <th class="field7">field7</th>
  		 <th class="field8">field8</th>
  		 <th class="field9">field9</th>
  		 <th class="field10">field10</th>
  	     <th class="field11">field11</th>
  		 <th class="field12">field12</th>
  		 <th class="field13">field13</th>
  		 <th class="field14">field14</th>
  		 <th class="field15">field15</th>
  		 <th class="field16">field16</th>
  		 <th class="field17">field17</th>
  		 <th class="field18">field18</th>
  		 <th class="field19">field19</th>
  		 <th class="field20">field20</th>
  		 <th class="field21">field21</th>
  		 <th class="field22">field22</th>
  		 <th class="field23">field23</th>
  		 <th class="field24">field24</th>
  		 <th class="field25">field25</th>
  		 <th class="field26">field26</th>
  		 <th class="field27">field27</th>
  		 <th class="field28">field28</th>
  		 <th class="field29">field29</th>
  		 <th class="field30">field30</th>	
  </tr>
 <c:forEach items="${dto.dataList}" var="auxBill">
 		 <tr>
  		 <td class="qcId" width="120px">
  		 	<a href="javascript:void(0)" onclick="openWin('质检单','qc/operateQcBill/${auxBill.qcId}/qcReport',1000,450)" >${auxBill.qcId}</a>
  		 </td>
  		 <td class="qcTypeName"  width="250px">${auxBill.qcTypeName }</td>
  		 <td class="addTime" width="150px"><fmt:formatDate value="${auxBill.addTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
  	 	 <td class="shorten10 field1"  width="180px">${auxBill.field1 }</td>
  		 <td class="shorten10 field2"  width="180px">${auxBill.field2 }</td>
  		 <td class="shorten10 field3"  width="180px">${auxBill.field3 }</td>
  		 <td class="shorten10 field4"  width="180px">${auxBill.field4 }</td>
  		 <td class="shorten10 field5"  width="180px">${auxBill.field5 }</td>
  		 <td class="shorten10 field6" width="180px">${auxBill.field6 }</td>
  		 <td class="shorten10 field7" width="180px">${auxBill.field7 }</td>
  		 <td class="shorten10 field8" width="180px">${auxBill.field8 }</td>
  		 <td class="shorten10 field9" width="180px">${auxBill.field9 }</td>
  		 <td class="shorten10 field10" width="180px">${auxBill.field10 }</td>
  		 <td class="shorten10 field11" width="180px">${auxBill.field11 }</td>
  		 <td class="shorten10 field12" width="180px">${auxBill.field12 }</td>
  		 <td class="shorten10 field13" width="180px">${auxBill.field13 }</td>
  		 <td class="shorten10 field14" width="180px">${auxBill.field14 }</td>
  		 <td class="shorten10 field15" width="180px">${auxBill.field15 }</td>
  		 <td class="shorten10 field16" width="180px">${auxBill.field16 }</td>
  		 <td class="shorten10 field17" width="180px">${auxBill.field17 }</td>
  		 <td class="shorten10 field18" width="180px">${auxBill.field18 }</td>
  		 <td class="shorten10 field19" width="180px">${auxBill.field19 }</td>
  		 <td class="shorten10 field20" width="180px">${auxBill.field20 }</td>
  		 <td class="shorten10 field21" width="180px">${auxBill.field21 }</td>
  		 <td class="shorten10 field22" width="180px">${auxBill.field22 }</td>
  		 <td class="shorten10 field23" width="180px">${auxBill.field23 }</td>
  		 <td class="shorten10 field24" width="180px">${auxBill.field24 }</td>
  		 <td class="shorten10 field25" width="180px">${auxBill.field25 }</td>
  		 <td class="shorten10 field26" width="180px">${auxBill.field26 }</td>
  		 <td class="shorten10 field27" width="180px">${auxBill.field27 }</td>
  		 <td class="shorten10 field28" width="180px">${auxBill.field28 }</td>
  		 <td class="shorten10 field29" width="180px">${auxBill.field29 }</td>
  		 <td class="shorten10 field30" width="180px">${auxBill.field30 }</td>
 	 </tr>
   </c:forEach>
</table>
</div>
<%@include file="/WEB-INF/jsp/common/pager.jsp"%>
</form>
</body>
</html>
