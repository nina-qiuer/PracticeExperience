<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<script type="text/javascript">
	function synchSubbankTask(){
		$.ajax({
			type:'post',
			url:'finance_subbank-synchSubbankTask',
			data:$('#form').serialize(),
			success:function(data){
				alert("同步结束");
			}
		});
	}
</script>
</head>
<body>
	<form name="form" method="post" id="form"
		enctype="multipart/form-data" action="finance_subbank-synchSubbankTask">
		<div class="pici_search pd5 mb10">
			<label class="mr10">时间节点： 
				 <s:textfield name="lastUpdateDate"></s:textfield>

			</label> <label class="mr10"> <input type="button" size="10"
				value="同步分行数据" class="blue" onclick="synchSubbankTask()"/>
			</label>
		</div>
	</form>

</body>
</html>