<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<%@taglib uri="/struts-tags" prefix="s" %>
<style type="text/css">
	.listtable td{
		text-align:center
	}
</style>
<script type="text/javascript">

$(function(){
	if($('#bankName').val()==''){
		$('#bankName').val($('#bigBank',parent.document).val());
	}
	if($('#subbankProvince').val()==''){
		$('#subbankProvince').val($('#bankProvince',parent.document).val());
	}
	
	if($('#subbankCity').val()==''){
		$('#subbankCity').val($('#bankCity',parent.document).val());
	}
});

function chooseSubbank(obj){

	var  bank = "";
	var bankTd  =  $(obj).parent().prev();
	var big_bank_td = $(bankTd).prev();
	
	var bank = $(bankTd).text();
	var big_bank = $(big_bank_td).text();

	$('#bank',parent.document).val(bank);
	$('#bank',parent.document).click();
	$('#bank',parent.document).blur();
	
	$('#bigBank',parent.document).val(big_bank);
	
	 var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
	 parent.layer.close(index); //再执行关闭   
}
	
</script>

</head>
<body>
<form name="form" id="search_form" method="post" enctype="multipart/form-data" action="finance_subbank">
    <div class="pici_search pd5 mb10">
        <label class="mr10">银行名称：
        	<s:textfield id="bankName" name="searchVo.bankName"></s:textfield>
        </label>
        <label class="mr10">省份：
        	<s:textfield id="subbankProvince" name="searchVo.subbankProvince"></s:textfield>
        </label>
        <label class="mr10">城市：
        	<s:textfield id="subbankCity" name="searchVo.subbankCity"></s:textfield>
        </label>
        
        <label class="mr10">
            <input type="submit" size="10"  value="查询"  class="blue"/>
        </label>
    </div>
    
<table width="100%" class="listtable mb10">
    <tr>
        <th>编号</th>
        <th>分行省份</th>
        <th>分行城市</th>
        <th>银行名称</th>
        <th>分行名称</th>
        <th>选择</th>
    </tr>
    <c:forEach items="${dataList}" var="v">
        <tr>
            <td>${v.id}</td>
            <td>${v.subbankProvince}</td>
            <td >${v.subbankCity}</td>
            <td>${v.bankName}</td>
           <td>${v.subbankName}</td>
           <td><input type="radio" onclick="chooseSubbank(this)"/></td>
        </tr>
    </c:forEach>

</table>
</form>

</body>
</html>