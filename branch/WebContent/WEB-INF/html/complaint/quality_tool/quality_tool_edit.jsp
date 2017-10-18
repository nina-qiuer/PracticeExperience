<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<script type="text/javascript" src="${CONFIG.res_url}script/thickbox-compressed.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}script/jquery/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}script/complaint/complaint/quality_tool.js"></script>
<style type="text/css">
	.datatable td table{border-collapse:collapse;}
	.datatable td td{border:0 none;}
</style>
</HEAD>
<BODY>
	<div id="001" class="tab_part">
		<input id="id" type="hidden" value="${e.id}" />
		<table class="datatable">
			<tr>
				<th width="100px;">成本类型级别<font color="red">*</font></th>
				<td width="300px;">
					<select id="level" name="level">
						<option value="1" <c:if test="${e.level==1}">selected</c:if>>公司级</option>
						<option value="2" <c:if test="${e.level==2}">selected</c:if>>部门级</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>成本类型类别<font color="red">*</font></th>
				<td>
					<select id="type" name="type">
						<option value="1" <c:if test="${e.type==1}">selected</c:if>>成本增加类</option>
						<option value="2" <c:if test="${e.type==2}">selected</c:if>>收入扣减类</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>成本类型名称<font color="red">*</font></th>
				<td>
					<input style="width: 250px;" id="name" name="name" value="<c:if test="${e.name!=''}">${e.name}</c:if>" />
				</td>
			</tr>
			<tr>
				<th>状态</th>
				<td>
					<input type="radio" name="useFlag" value="1" <c:if test="${e.useFlag!=0}">checked="checked"</c:if> />启用
					<input type="radio" name="useFlag" value="0" <c:if test="${e.useFlag==0}">checked="checked"</c:if> />废弃
				</td>
			</tr>
			<tr>
				<th>备注</th>
				<td>
					<textarea id="remark" name="remark" style="width: 250px; font-size: 12px;" rows="3"><c:if test="${e.remark!=''}">${e.remark}</c:if></textarea>
				</td>
			</tr>
			<tr align="center">
				<td colspan="2">
					<input type="button" value="保存" class="pd5" onclick="check_quality_tool();" />&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" value="返回" class="pd5" onclick="self.parent.tb_remove();" />
				</td>
			</tr>
		</table>
	</div>
	<font color="red">友情提醒：成本类型名称和备注中请不要含有&和%符号<br /></font>
	<div id="002" class="tab_part">
	</div>
	<%@include file="/WEB-INF/html/foot.jsp" %>
