<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<%@taglib uri="/struts-tags" prefix="s" %>
<script type="text/javascript" src="${CONFIG.res_url}script/jquery/validation/jquery.validate.min.js"></script>
<link href="${CONFIG.res_url}css/easydialog.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${CONFIG.res_url}script/DatePickerNew/WdatePicker.js" ></script>  
<script type="text/javascript" src="${CONFIG.res_url}/script/easydialog.min.js"></script>
<style type="text/css">
	.listtable td{
		text-align:center
	}
	
	.main{
		margin:0 auto;
		width:100%
	}
</style>
<script type="text/javascript">

var modelName='工单';
var mainUrl = 'work_order';

$(function(){
	validate.settings.rules=
		{
			"searchVo.id":{
				digits:true
		}};
	
	validate.settings.messages = 
		{
				'searchVo.id':{
				digits:"只能填写数字"
	    }};
	
	getSecondConfig();
	console.log(${searchVo.sonConfigId});
	$("#secondConfig").val(${searchVo.sonConfigId});

});	

function getSecondConfig(){
	var configId = $("#firstConfig").val();
	$("#secondConfig").empty();
	
	if(configId > 0){
		$.ajax({
			type : "post",
			url : "work_order-getSonConfigList",
			async: false,
			data : {
				"pid": configId
			},
			success : function(data) {
				addSecondConfig(data);
			}
		});
	}
}

function addSecondConfig(data){
	if( data.resultList.length > 0){
		$("#sonConfigTr").show();
		
		var objSelect = document.getElementById("secondConfig");
		objSelect.add(new Option("---", 0));
		
		$.each(data.resultList, function(i, item){  
            var  option = new Option(item.department, item.id);
            objSelect.add(option);
        });  
	}else{
		$("#sonConfigTr").hide();
	}
}
</script>

</head>
<body>
<div class="top_crumbs">您当前所在的位置：投诉质检管理>><span class="top_crumbs_txt">预订工作台</span>>><span class="top_crumbs_txt">工单发起与查询</span></div>
<div  class="main">
<form name="form" id="search_form" method="post" enctype="multipart/form-data" action="work_order">
    <div class="pici_search pd5 mb10">
        <label class="mr10">单号：
        	<s:textfield id="id" name="searchVo.id"></s:textfield>
        </label>
        <label class="mr10">联系电话：
        	<s:textfield id="phone" name="searchVo.phone"></s:textfield>
        </label>
        <label class="mr10">发起时间：
            <input id="addTimeBgn" type="text"  name="searchVo.addTimeBgn" value="${searchVo.addTimeBgn}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'addTimeEnd\')}'})" readOnly="readonly"/>
            至
            <input id="addTimeEnd" type="text"  name="searchVo.addTimeEnd" value="${searchVo.addTimeEnd}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'addTimeBgn\')}'})" readOnly="readonly"/>
        </label>
        <label class="mr10">项目组：
           	  <s:select id="firstConfig" name="searchVo.configId" list="firstConfig" listKey="id" listValue="department" headerKey="" headerValue="---" onchange="getSecondConfig()"/>
        </label>
        <label id="sonConfigTr"  style="display:none;">二级项目组：
			<select id="secondConfig" name="searchVo.sonConfigId" style="width:150px;"></select>
        </label>
        <label class="mr10">业务分类：
        	<s:textfield id="businessClass" name="searchVo.businessClass"></s:textfield>
        </label>
        <label class="mr10">工单状态：
            <s:select name="searchVo.state"  list="#{1:'待分配',2:'处理中',3:'已处理',4:'已撤销'}" headerKey="" headerValue="---"/>
        </label>
        <label class="mr10">处理人
        	<s:textfield name="searchVo.dealPerson"></s:textfield>
        </label>
         <label class="mr10">发起人
        	<s:textfield name="searchVo.addPersonName"></s:textfield>
        </label>
        <label class="mr10">
            <input type="submit" size="10"   class="blue" value="查询"/>
        </label>
        <label class="mr10" style="text-align:right">
            <input type="button" size="10"  value="发起"  class="blue" onclick="toAddOrUpdate(modelName,mainUrl)"/>
        </label>
    </div>
<table width="100%" class="listtable mb10">
    <tr>
        <th>单号</th>
        <th>发起时间</th>
        <th>客人姓名</th>
        <th>联系电话</th>
        <th>来电事由</th>
        <th>完成时间</th>
        <th>项目组</th>
        <th>业务分类</th>
        <th>发起人</th>
        <th>处理人</th>
        <th>工单状态</th>
        <th>操作</th>
    </tr>
    <c:forEach items="${dataList}" var="v">
        <tr>
            <td><a href="javascript:void(0)" onclick="showInfo(modelName,mainUrl,${v.id})">${v.id}</a></td>
            <td>
            	<fmt:formatDate  value="${v.addTime}"  pattern="yyyy-MM-dd HH:mm:ss"/>
           </td>
            <td>${v.customerName}</td>
            <td>${v.phone}</td>
            <td  class="shorten30">${v.phoneMatter}</td>
            <td>
            	<c:if test="${v.state==3 }">
	            	<fmt:formatDate  value="${v.updateTime}"  pattern="yyyy-MM-dd HH:mm:ss"/>
            	</c:if>
           </td>
           
            <td>${v.department}</td>
            <td>${v.businessClass}</td>
            <td>${v.addPerson}</td>
            <td>${v.dealPerson}</td>
            <td>
            	<c:if test="${v.state ==1 }">待分配</c:if>
            	<c:if test="${v.state ==2}">处理中</c:if>
            	<c:if test="${v.state ==3}">已处理</c:if>
            </td>
            <td>
            	<!-- 未已处理可以进行编辑 -->
            	<c:if test="${v.state != 3 }">
            		<input type="button" class="blue"  value="编辑"  onclick="toAddOrUpdate(modelName,mainUrl,${v.id})"></input>
            	</c:if>
            	<!-- 待分配且为发起人、或者处理中且为处理人 可以进行删除 -->
            	<c:if test="${(v.state == 1 && v.addPerson == user.realName) || (v.state == 2 && v.dealPerson == user.realName) }">
            		<input type="button" class="blue"  value="删除"  onclick="del(modelName,mainUrl,${v.id})"></input>
            	</c:if>
            </td>
        </tr>
    </c:forEach>

</table>
<%@include file="/WEB-INF/html/common/pager.jsp" %>
</form>
</div>

</body>
</html>