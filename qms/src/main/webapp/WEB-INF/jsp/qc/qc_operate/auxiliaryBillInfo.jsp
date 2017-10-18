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
$(function (){
	
	$('#chkAll').click(function() {
		var flag = this.checked;
		$('.listtable td :checkbox[name = billIds]').each(function() {
			this.checked = flag;
		});
	});
	
});

function delAuxBill(){
	
	if ($(':checkbox[name="billIds"]:checked').length <= 0) {
		layer.alert("请至少选择一个辅助单！", {
			icon : 2
		});
		return false;
	}
	var msg = "您确定删除该辅助单吗？";
	layer.confirm(msg, {icon: 3}, function(index){
	$.ajax( {
		url : 'qc/operateQcBill/delAuxBill',
		data : $('#aux_form').serialize(),
		type : 'post',
		dataType:'json',
		cache : false,
		success : function(result) {
			if(result)
			{
		    	if(result.retCode == "0")
				{
		    		 parent.location.reload();
		    	
				 }else{
					
					 layer.alert(result.resMsg,{icon: 2});
				}
		     }
		 }
	    });
	});
	
}

function updateAuxBill(){
	
	if ($(':checkbox[name="billIds"]:checked').length <= 0) {
		layer.alert("请至少选择一个辅助单！", {
			icon : 2
		});
		return false;
	}
	if ($(':checkbox[name="billIds"]:checked').length >=2) {
		layer.alert("编辑只能选择一条数据！", {
			icon : 2
		});
		return false;
	}
	 var str=document.getElementsByName("billIds");
	 var billId="";
	 for(var i=0;i<str.length;i++){
	             if(str[i].checked){
	            	 billId = str[i].value;
	            }
	    } 
	 parent.openWin('编辑辅助单', 'qc/operateQcBill/'+billId+'/toUpdateAuxBill', 600, 600);
	
}

</script>
</head>
<body>
<form name="aux_form" id="aux_form" method="post" action="" >
<div class="pici_search pd5" align="left">
	<input type="button" class="blue" value="新增" 
			 onclick="parent.openWin('新增辅助单', 'qc/operateQcBill/${qcTemplate.id}/${qcId}/toAddAuxBill', 600, 600)">
		<input type="button" class="blue" value="编辑" 
			 onclick="updateAuxBill()">
			<input type="button" class="blue" value="删除" onclick="delAuxBill()">
</div>
<div style="width:3000px;  overflow-x:auto;overflow-y:hidden;">
<table class="listtable"  width="3000px">
 <tr>
  <th width="30px"><input type="checkbox" id="chkAll" title="全选"></th>
     <c:if test="${qcTemplate.fieldName1!=''}">
       <th width="120px">${qcTemplate.fieldName1}</th>
    </c:if>
     <c:if test="${qcTemplate.fieldName2!=''}">
       <th width="120px">${qcTemplate.fieldName2}</th>
    </c:if>
    <c:if test="${qcTemplate.fieldName3!=''}">
       <th width="120px">${qcTemplate.fieldName3}</th>
    </c:if>
    <c:if test="${qcTemplate.fieldName4!=''}">
       <th width="120px">${qcTemplate.fieldName4}</th>
    </c:if>
    <c:if test="${qcTemplate.fieldName5!=''}">
       <th width="120px">${qcTemplate.fieldName5}</th>
    </c:if>
     <c:if test="${qcTemplate.fieldName6!=''}">
       <th width="120px">${qcTemplate.fieldName6}</th>
    </c:if> 
    <c:if test="${qcTemplate.fieldName7!=''}">
       <th width="120px">${qcTemplate.fieldName7}</th>
    </c:if>
    <c:if test="${qcTemplate.fieldName8!=''}">
       <th width="120px">${qcTemplate.fieldName8}</th>
    </c:if>
    <c:if test="${qcTemplate.fieldName9!=''}">
       <th width="120px">${qcTemplate.fieldName9}</th>
    </c:if> 
 	<c:if test="${qcTemplate.fieldName10!=''}">
       <th width="120px">${qcTemplate.fieldName10}</th>
    </c:if>
    <c:if test="${qcTemplate.fieldName11!=''}">
       <th width="120px">${qcTemplate.fieldName11}</th>
    </c:if> 
   <c:if test="${qcTemplate.fieldName12!=''}">
       <th width="120px">${qcTemplate.fieldName12}</th>
    </c:if>
       <c:if test="${qcTemplate.fieldName13!=''}">
       <th width="120px">${qcTemplate.fieldName13}</th>
    </c:if>
    <c:if test="${qcTemplate.fieldName14!=''}">
       <th width="120px">${qcTemplate.fieldName14}</th>
    </c:if>
    <c:if test="${qcTemplate.fieldName15!=''}">
       <th width="120px">${qcTemplate.fieldName15}</th>
    </c:if>
    <c:if test="${qcTemplate.fieldName16!=''}">
       <th width="120px">${qcTemplate.fieldName16}</th>
    </c:if>
    <c:if test="${qcTemplate.fieldName17!=''}">
       <th width="120px">${qcTemplate.fieldName17}</th>
    </c:if>
    <c:if test="${qcTemplate.fieldName18!=''}">
       <th width="120px">${qcTemplate.fieldName18}</th>
    </c:if>
    <c:if test="${qcTemplate.fieldName19!=''}">
       <th width="120px">${qcTemplate.fieldName19}</th>
    </c:if> 
 	<c:if test="${qcTemplate.fieldName20!=''}">
       <th width="120px">${qcTemplate.fieldName20}</th>
    </c:if>
    <c:if test="${qcTemplate.fieldName21!=''}">
       <th width="120px">${qcTemplate.fieldName21}</th>
    </c:if> 
    <c:if test="${qcTemplate.fieldName22!=''}">
       <th width="120px">${qcTemplate.fieldName22}</th>
    </c:if>
       <c:if test="${qcTemplate.fieldName23!=''}">
       <th width="120px">${qcTemplate.fieldName23}</th>
    </c:if>
    <c:if test="${qcTemplate.fieldName24!=''}">
       <th width="120px">${qcTemplate.fieldName24}</th>
    </c:if>
    <c:if test="${qcTemplate.fieldName25!=''}">
       <th width="120px">${qcTemplate.fieldName25}</th>
    </c:if>
    <c:if test="${qcTemplate.fieldName26!=''}">
       <th width="120px">${qcTemplate.fieldName26}</th>
    </c:if>
    <c:if test="${qcTemplate.fieldName27!=''}">
       <th width="120px">${qcTemplate.fieldName27}</th>
    </c:if>
    <c:if test="${qcTemplate.fieldName28!=''}">
       <th width="120px">${qcTemplate.fieldName28}</th>
    </c:if>
 	<c:if test="${qcTemplate.fieldName29!=''}">
       <th width="120px">${qcTemplate.fieldName29}</th>
    </c:if>
    <c:if test="${qcTemplate.fieldName30!=''}">
       <th width="120px">${qcTemplate.fieldName30}</th>
    </c:if> 
    </tr>
   <c:forEach items="${list}" var="bill" varStatus="st"> 
	<tr>
	 <td style="width:30px"><input type="checkbox" autocomplete="off"  name="billIds" value="${bill.id }" /></td>
	<c:if test="${qcTemplate.fieldName1!=''}">
       <td class="shorten15">${bill.field1}</td>
    </c:if>
     <c:if test="${qcTemplate.fieldName2!=''}">
       <td class="shorten15">${bill.field2}</td>
    </c:if>
    <c:if test="${qcTemplate.fieldName3!=''}">
       <td class="shorten15">${bill.field3}</td>
    </c:if>
    <c:if test="${qcTemplate.fieldName4!=''}">
       <td class="shorten15">${bill.field4}</td>
    </c:if>
    <c:if test="${qcTemplate.fieldName5!=''}">
       <td class="shorten15">${bill.field5}</td>
    </c:if>
     <c:if test="${qcTemplate.fieldName6!=''}">
       <td class="shorten15">${bill.field6}</td>
    </c:if> 
    <c:if test="${qcTemplate.fieldName7!=''}">
       <td class="shorten15">${bill.field7}</td>
    </c:if>
    <c:if test="${qcTemplate.fieldName8!=''}">
       <td class="shorten15">${bill.field8}</td>
    </c:if>
    <c:if test="${qcTemplate.fieldName9!=''}">
       <td class="shorten15">${bill.field9}</td>
    </c:if> 
 	<c:if test="${qcTemplate.fieldName10!=''}">
       <td class="shorten15">${bill.field10}</td>
    </c:if>
    <c:if test="${qcTemplate.fieldName11!=''}">
       <td class="shorten15">${bill.field11}</td>
    </c:if> 
   <c:if test="${qcTemplate.fieldName12!=''}">
       <td class="shorten15">${bill.field12}</td>
    </c:if>
    <c:if test="${qcTemplate.fieldName13!=''}">
       <td class="shorten15">${bill.field13}</td>
    </c:if>
    <c:if test="${qcTemplate.fieldName14!=''}">
       <td class="shorten15">${bill.field14}</td>
    </c:if>
    <c:if test="${qcTemplate.fieldName15!=''}">
       <td class="shorten15">${bill.field15}</td>
    </c:if>
    <c:if test="${qcTemplate.fieldName16!=''}">
       <td class="shorten15">${bill.field16}</td>
    </c:if>
    <c:if test="${qcTemplate.fieldName17!=''}">
       <td class="shorten15">${bill.field17}</td>
    </c:if>
    <c:if test="${qcTemplate.fieldName18!=''}">
       <td class="shorten15">${bill.field18}</td>
    </c:if>
    <c:if test="${qcTemplate.fieldName19!=''}">
       <td class="shorten15">${bill.field19}</td>
    </c:if> 
 	<c:if test="${qcTemplate.fieldName20!=''}">
       <td class="shorten15">${bill.field20}</td>
    </c:if>
    <c:if test="${qcTemplate.fieldName21!=''}">
       <td class="shorten15">${bill.field21}</td>
    </c:if> 
    <c:if test="${qcTemplate.fieldName22!=''}">
       <td class="shorten15">${bill.field22}</td>
    </c:if>
    <c:if test="${qcTemplate.fieldName23!=''}">
       <td class="shorten15">${bill.field23}</td>
    </c:if>
    <c:if test="${qcTemplate.fieldName24!=''}">
       <td class="shorten15">${bill.field24}</td>
    </c:if>
    <c:if test="${qcTemplate.fieldName25!=''}">
       <td class="shorten15">${bill.field25}</td>
    </c:if>
    <c:if test="${qcTemplate.fieldName26!=''}">
       <td class="shorten15">${bill.field26}</td>
    </c:if>
    <c:if test="${qcTemplate.fieldName27!=''}">
       <td class="shorten15">${bill.field27}</td>
    </c:if>
    <c:if test="${qcTemplate.fieldName28!=''}">
       <td class="shorten15">${bill.field28}</td>
    </c:if>
 	<c:if test="${qcTemplate.fieldName29!=''}">
       <td class="shorten15">${bill.field29}</td>
    </c:if>
    <c:if test="${qcTemplate.fieldName30!=''}">
       <td class="shorten15">${bill.field30}</td>
    </c:if> 
	</tr>
	</c:forEach>
 </table>
 </div>
</form>
</body>
</html>