<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<%@taglib uri="/struts-tags" prefix="s" %>
<script type="text/javascript" src="${CONFIG.res_url}script/thickbox-compressed.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}script/jquery/validation/jquery.validate.min.js"></script>
<link href="${CONFIG.res_url}css/thickbox.css" rel="stylesheet" type="text/css" />
<link href="${CONFIG.res_url}css/easydialog.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${CONFIG.res_url}script/DatePickerNew/WdatePicker.js" ></script>  
<script type="text/javascript" src="${CONFIG.res_url}/script/easydialog.min.js"></script>
<style type="text/css">
	.listtable td{
		text-align:center
	}
</style>
<script type="text/javascript">
var modelName='工单';
var mainUrl = 'work_order';
var isAssigner;
$(function(){

		isAssigner = ${isAssigner};	
	
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
	
	$('#checkAll').click(function(){
		if($(this).attr("checked")) {
			$(this).parents('table').find(":checkbox[name='ids']").attr("checked", true);
		}else{
			$(this).parents('table').find(":checkbox[name='ids']").attr("checked", false);
		}
	});
	   
	change_menu('${searchVo.menuId}');
});


	
	function assign(){
		var c = $(":checkbox[name='ids']:checked").length;
		if(c == 0){
			alert("请至少选择一条工单!");
			return false;
		}
		
		var assignTo = $('#members').val();
		
		if(assignTo==''){
			alert('请填写要分配的处理人');
			return false;
		}
		
 		$.ajax({
			type:"post",
			url:'work_order-assign',
			data:$('#search_form').serialize(),
			success:function(data){
				alert(data.msg);
				if(data.success){
					$('#members option:first').attr('selected','selected');
					$("#search_form").submit();
				}
			}
		});
		
	}
	
	
	//标签转换时更新标签样式
	function change_menu(value) {
		$("li[id^='menu_']").each(function(){
			$(this).removeClass("menu_on");
		});
		$('#menu_'+value).addClass("menu_on");	
	}
	
	//列表标签表单提交
	function searchTable(menuId){
		$('#menuId').val(menuId.substr(5,1));
		$("#search_form").submit();
	}

	function changeDealPersonData() {
			if(isAssigner){
				var configId = $('#department option:selected').val();
				var userArr = new Array();
				$.ajax({
					type : "POST",
					url : "work_order-getMembersByDepartment",
					data:{"configId":configId},
					success : function(data) {
						userArr = data.memberList;
						$('#members').empty();
						$('#members').append('<option value="">---</option>');
						for(var index in userArr){
							$('#members').append('<option value="'+userArr[index]+'">'+userArr[index]+'</option>');
						}
					}
				});
			}
	}
	
	function cancelWorkOrder(id){
		if(confirm('确定撤销吗？')){
			$.ajax({
				type:"post",
				data:{
					"id":id
				},
				url:"work_order-cancel",
				success:function(data){
					window.location.reload();
				}
			});
		}
	}
</script>

</head>
<body>
<div class="top_crumbs">您当前所在的位置：投诉质检管理>><span class="top_crumbs_txt">预订工作台</span>>><span class="top_crumbs_txt">工单处理</span></div>
<form name="form" id="search_form" method="post" enctype="multipart/form-data" action="work_order-list">
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
           	  <s:select id="department" name="searchVo.configId" list="responsibleConfig" listKey="id" listValue="department" headerKey="" headerValue="---"  onchange="changeDealPersonData()"/>
        </label>
        <label class="mr10">业务分类：
        	<s:textfield id="businessClass" name="searchVo.businessClass"></s:textfield>
        </label>
        <c:if test="${isAssigner }">
	        <label class="mr10">处理人：
	        	<s:select id="members" list="members"  name="searchVo.dealPerson" headerKey="" headerValue="--"></s:select>
	        </label>
        </c:if>
        <c:if test="${searchVo.menuId!=3&&isAssigner}">
	        <label class="mr10" style="text-align:right">
	            <input type="button" size="10"  value="分配" class="blue" onclick="assign()"/>
	        </label>
        </c:if>
        <label class="mr10">
            <input type="submit" size="10"  value="查询"  class="blue"/>
        </label>
        <!-- <label class="mr10" style="text-align:right">
            <input id="exportsButton" type="button" size="10"  value="导出"  class="blue" onclick="exports(mainUrl)"/>
        </label> -->
    </div>
    
    <div id="pici_tab" class="clear">
			<ul>
				<c:if test="${isAssigner}">
					<li onclick="searchTable(this.id)" id="menu_1">
						<s class="rc-l"></s><s class="rc-r"></s><a href="javascript:void(0)">待分配</a>
					</li>
				</c:if>
				<li  onclick="searchTable(this.id)" id="menu_2">
					<s class="rc-l"></s><s class="rc-r"></s><a href="javascript:void(0)">处理中</a>
				</li>
				<li onclick="searchTable(this.id)" id="menu_3">
					<s class="rc-l"></s><s class="rc-r"></s><a href="javascript:void(0)">已处理</a>
				</li>
			</ul>
	</div>
<input type="hidden" name="searchVo.menuId" id="menuId" value="${searchVo.menuId}"/>
<table width="100%" class="listtable mb10">
    <tr>
    	<c:if test="${ searchVo.menuId!=3 and isAssigner}">
    		<th><input type="checkbox" id="checkAll"/></th>
    	</c:if>
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
        	<c:choose>
        		<c:when test="${ searchVo.menuId!=3 && isAssigner && fn:contains(responsibledeps,v.department)}">
        			<td><input type="checkbox"  name="ids" value="${v.id }"/></td>
        		</c:when>
        		<c:when test="${ searchVo.menuId!=3 && isAssigner }">
        			<td></td>
        		</c:when>
        		<c:otherwise>
        			
        		</c:otherwise>
        	</c:choose>
            <td><a href="javascript:void(0)" onclick="showInfo(modelName,mainUrl,${v.id})">${v.id}</a></td>
            <td>
            	<fmt:formatDate  value="${v.addTime}"  pattern="yyyy-MM-dd HH:mm:ss"/>
           </td>
            <td>${v.customerName}</td>
            <td>${v.phone}</td>
            <td class="shorten30">${v.phoneMatter}</td>
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
            	<c:if test="${v.state == 3}">已处理</c:if>
            </td>
            <td>
	            <c:if test="${ searchVo.menuId != 3}">
						<input type="button" class="blue"  value="编辑"  onclick="toAddOrUpdate(modelName,mainUrl,${v.id})"></input>
						<input type="button" class="blue"  value="删除"  onclick="del(modelName,mainUrl,${v.id})"></input>
	            		<!-- 处理人是自己的可以进行处理 -->
	            		<c:if test="${user.realName==v.dealPerson}" >
					        <input type="button" class="blue"  value="处理"  onclick="openDialog('工单处理','work_order-toDeal?id='+${v.id})"></input>
	            		</c:if>
	            </c:if>
            </td>
        </tr>
    </c:forEach>

</table>
<%@include file="/WEB-INF/html/common/pager.jsp" %>
</form>

</body>
</html>