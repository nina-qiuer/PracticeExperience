<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<%@taglib uri="/struts-tags" prefix="s" %>
<script type="text/javascript" src="${CONFIG.res_url}script/thickbox-compressed.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}script/jquery/validation/jquery.validate.min.js"></script>
<link href="${CONFIG.res_url}css/thickbox.css" rel="stylesheet" type="text/css" />
<link href="${CONFIG.res_url}css/easydialog.css" rel="stylesheet" type="text/css" />
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
	var modelName='改进问题反馈';
	var mainUrl = 'issueImprovementFeedback';
</script>

</head>
<body>
<div class="top_crumbs">您当前所在的位置：投诉质检管理>><span class="top_crumbs_txt">预订中心改进问题反馈表</span></div>
<div  class="main">
<form name="form" id="search_form" method="post" enctype="multipart/form-data" action="issueImprovementFeedback">
    <div class="pici_search pd5 mb10">
        <label class="mr10">一级部门：
        	<s:textfield name="searchVo.customerDepartment"></s:textfield>
        </label>
        <label class="mr10">客服：
        	<s:textfield  name="searchVo.customer"></s:textfield>
        </label>
        <label class="mr10">发起时间：
            <input id="addTimeBgn" type="text"  class="Wdate" name="searchVo.addTimeBgn" value="${searchVo.addTimeBgn}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'addTimeEnd\')}'})" readOnly="readonly"/>
            至
            <input id="addTimeEnd" type="text"  class="Wdate" name="searchVo.addTimeEnd" value="${searchVo.addTimeEnd}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'addTimeBgn\')}'})" readOnly="readonly"/>
        </label>
        <label class="mr10">
            <input type="submit" size="10"   class="blue" value="查询"/>
        </label>
        <label class="mr10" style="text-align:right">
            <input type="button" size="10"  value="新增"  class="blue" onclick="toAddOrUpdate(modelName,mainUrl)"/>
        </label>
        <label class="mr10" style="text-align:right">
            <input id="exportsButton" type="button" size="10"  value="导出"  class="blue" onclick="exports(mainUrl)"/>
        </label>
    </div>
<table width="100%" class="listtable mb10">
    <tr>
        <th>编号</th>
        <th>一级部门</th>
        <th>二级部门</th>
        <th>三级组</th>
        <th>客服专员</th>
        <th>关联订单号</th>
        <th>问题描述</th>
        <th>改进建议</th>
        <th>添加日期</th>
        <th>操作</th>
    </tr>
    <c:forEach items="${dataList}" var="v">
        <tr>
            <td>${v.id}</td>
            <td>${v.customerDepartment}</td>
            <td>${v.customerScndDepartment}</td>
            <td>${v.customerGroup}</td>
            <td>${v.customer}</td>
            <td>${v.orderId}</td>
            <td class="shorten30">${v.issueDescription}</td>
            <td class="shorten30">${v.advice}</td>
            <td>
                <fmt:formatDate value= "${v.addTime}" pattern ="yyyy-MM-dd"/>
           </td>
            <td>
                <input type="button" class="blue"  value="编辑"  onclick="toAddOrUpdate(modelName,mainUrl,${v.id})"></input>
            </td>
        </tr>
    </c:forEach>

</table>
<%@include file="/WEB-INF/html/common/pager.jsp" %>
</form>
</div>

</body>
</html>