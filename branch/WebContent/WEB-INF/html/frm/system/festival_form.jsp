<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<script type="text/javascript" src="${CONFIG.res_url}script/DatePickerNew/WdatePicker.js" ></script>
<script type="text/javascript">
$(document).ready(function(){
	var succFlag = "${succFlag }";
	if ('Success' == succFlag) {
		self.parent.tb_remove();
		window.parent.location.reload();
	}
});
</script>
</HEAD>
<BODY>
<h2>法定假日及补工日期</h2>
<form name="form" id="form" method="post" enctype="multipart/form-data" action="festival-doAddFestival" onsubmit="">
	<table width="100%" class="datatable">
		<tr>
			<th>节日名称</th>
			<td>
			    <select class="mr10" name="vo.fesId" >
				    <c:forEach items="${fesEntList }" var="ent" varStatus="st">
				    	<option value="${ent.id }">${ent.festivalName }</option>
				    </c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<th>放假日期</th>
			<td> 
				<span style="color: green;">格式示例：2014-01-01,2014-01-02,2014-01-03。</span>
				<span style="color: red;">注意：逗号为英文逗号。</span><br>
				<input type="text" name="vo.fesDateStr" id="fesDateStr" class="mr10" style="width: 560px">
			</td>
		</tr>
		<tr>
			<th>补工日期</th>
			<td> 
				<span style="color: green;">格式示例：2014-01-04,2014-01-05。</span>
				<span style="color: red;">注意：逗号为英文逗号。</span><br>
				<input type="text" name="vo.offsetDateStr" id="offsetDateStr" class="mr10" style="width: 560px">
			</td>
		</tr>
		<tr>
			<th></th>
			<td>
				<input type="submit" class="pd5" value="增加" />
				<input type="button" class="pd5" value="取消" onclick="self.parent.tb_remove();" />
			</td>
		</tr>
	</table>
</form>
</BODY>
<%@include file="/WEB-INF/html/foot.jsp" %>
