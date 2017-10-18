<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<script type="text/javascript" src="${CONFIG.res_url}script/thickbox-compressed.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}script/jquery/validation/jquery.validate.min.js"></script>
<link href="${CONFIG.res_url}css/thickbox.css" rel="stylesheet" type="text/css" />
<link href="${CONFIG.res_url}css/easydialog.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${CONFIG.res_url}/script/easydialog.min.js"></script>
<style type="text/css">
        .main{
            text-align:center;
            margin: 0 auto;
        }
</style>

<script type="text/javascript">
	function delConfig(id){
		if(confirm('确定删除吗？')){
			$.ajax({
				type:"post",
				url:"config-delete?id="+id,
				success:function(data){
					window.location.reload();
				}
			});
		}
	}
	
	function toAddOrUpdate(id){
		$('iframe').attr("src","config-toAddOrUpdate?id="+id);
		easyDialog.open({container : 'addConfigBox', overlay : false});
	}
</script>
</head>
<body>
<div class="main">
    <table width="100%" class="listtable mb10">
        <tr>
        	<th>项目组</th>
            <th>父项目组</th>
            <th>负责人</th>
            <th>分配人</th>
            <th>处理人</th>
            <th>操作</th>
        </tr>
        <c:forEach items="${dataList}" var="config">
        	<tr>
        		<td>${config.department}</td>
        		<td>${config.parentName}</td>
            	<td>${config.principals}</td>
            	<td>${config.assigners}</td>
            	<td>${config.members}</td>
            	<td>
					<input type="button" value="修改" onclick="toAddOrUpdate(${config.id})" class="blue"/>
					<input type="button" value="删除" onclick="delConfig(${config.id})"  class="blue"/>
          		 </td>
            </tr>
        </c:forEach>
    </table>
    <input type="button" value="新增" class="blue" style="text-align: left" onclick="toAddOrUpdate(0)"/>
</div>

<div id="addConfigBox" style="width: 560px;height:280px" class="easyDialog_wrapper">
	<div class="easyDialog_content">
		<h4 class="easyDialog_title" id="easyDialogTitle"><a href="javascript:void(0)" title="关闭窗口" class="close_btn" id="closeBtn">×</a>工单配置</h4>
		<div>
			<iframe src="" frameborder="0" width="560"  height="250"></iframe>
		</div>
	</div>
</div>

</body>
</html>