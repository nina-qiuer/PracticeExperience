<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp"%>

<SCRIPT type="text/javascript">
function onSubmitClicked() {
	var tourTimeNode = $("#tourTimeNode").val();
	var touringGroupType = $("#touringGroupType:checked").val();
	if (tourTimeNode == 2 && typeof(touringGroupType) == "undefined") {
		alert("请为客服配置组别！~");
		return false;
	}
	var str=document.getElementsByName("entity.signCity"); 
	var signCity=""; 
	for(var i=0;i<str.length;i++){ 
	            if(str[i].checked){ 
	            	signCity += str[i].value+","; 
	           } 
	   } 
	var param = $('#form').serialize();
	$.ajax({
	type: "POST",
	async:false,
	url: "${manageUrl}-doModify",
	data: param,
	success: function(data){
		self.parent.tb_remove();
     }
   });
   window.parent.location.reload();
}
</SCRIPT>
<style type="text/css">
	ul.ztree {margin-top: 10px;border: 1px solid #617775;background: #f0f6e4;width:220px;height:360px;overflow-y:scroll;overflow-x:auto;}
</style>
</HEAD>
<BODY>
<h2>投诉处理人配置修改</h2>
<form name="form" id="form" method="post" enctype="multipart/form-data">
	<input type="hidden" name="entity.id" id="id" value="${entity.id}" />
	<input type="hidden" name="entity.tourTimeNode" id="tourTimeNode" value="${entity.tourTimeNode}" />
	<table width="100%" class="datatable">
		<tr>
			<th>部门</th>
			<td>${entity.departmentName }</td>
		</tr>
		<tr>
			<th>人员</th>
			<td>${entity.userName }</td>
		</tr>
		<c:if test="${entity.tourTimeNode != 4 && entity.tourTimeNode != 5 && entity.tourTimeNode != 6 && entity.tourTimeNode != 7}">
		<tr>
			<th>处理岗</th>
			<td>
				<c:if test="${entity.tourTimeNode == 1}">出游前客户服务</c:if>
				<c:if test="${entity.tourTimeNode == 2}">售后组</c:if>
				<c:if test="${entity.tourTimeNode == 3}">资深组</c:if>
			</td>
		</tr>
		</c:if>
		<c:if test="${entity.tourTimeNode == 5}">
		<tr>
			<th>产品品类</th>
			<td>
				<label><input type="checkbox" name="entity.productCategory" id="productCategory" value="国内酒店"  <c:if test="${entity.productCategory.contains('国内酒店')==true}">checked='checked'</c:if>/>国内酒店</label>
				<label><input type="checkbox" name="entity.productCategory" id="productCategory" value="国际酒店"  <c:if test="${entity.productCategory.contains('国际酒店')==true}">checked='checked'</c:if>/>国际酒店</label>
			</td>
		</tr>
		</c:if>
		<c:if test="${entity.tourTimeNode == 2}">
			<tr>
				<th>组别</th>
				<td>
					<label><input type="radio" name="entity.touringGroupType" id="touringGroupType" value="1" <c:if test="${entity.touringGroupType == 1}">checked='checked'</c:if>/>
					呼入组</label>　
					<label><input type="radio" name="entity.touringGroupType" id="touringGroupType" value="2" <c:if test="${entity.touringGroupType == 2}">checked='checked'</c:if>/>
					后处理组</label>　
					<label><input type="radio" name="entity.touringGroupType" id="touringGroupType" value="3" <c:if test="${entity.touringGroupType == 3}">checked='checked'</c:if>/>
					资深坐席组</label>
				</td>
			</tr>
		</c:if>
		<tr>
			<th>投诉级别</th>
			<td>
				<label><input type="checkbox" name="entity.complaintLevel1Flag" id="complaintLevel1Flag" value="1" <c:if test="${entity.complaintLevel1Flag == 1}">checked='checked'</c:if>/>
				一级</label>　　
				<label><input type="checkbox" name="entity.complaintLevel2Flag" id="complaintLevel2Flag" value="1" <c:if test="${entity.complaintLevel2Flag == 1}">checked='checked'</c:if>/>
				二级</label>　　
				<label><input type="checkbox" name="entity.complaintLevel3Flag" id="complaintLevel3Flag" value="1" <c:if test="${entity.complaintLevel3Flag == 1}">checked='checked'</c:if>/>
				三级</label>
			</td>
		</tr>
		<c:if test="${entity.tourTimeNode != 4 && entity.tourTimeNode != 5 && entity.tourTimeNode != 6 && entity.tourTimeNode != 7}">
		<tr>
			<th>产品品类</th>
			<td>
				<label><input type="checkbox" name="entity.productCategory" id="productCategory" value="跟团游"  <c:if test="${entity.productCategory.contains('跟团游')==true}">checked='checked'</c:if>/>跟团游</label>　　
				<label><input type="checkbox" name="entity.productCategory" id="productCategory" value="自助游"  <c:if test="${entity.productCategory.contains('自助游')==true}">checked='checked'</c:if>/>自助游</label>　
				<label><input type="checkbox" name="entity.productCategory" id="productCategory" value="团队游"  <c:if test="${entity.productCategory.contains('团队游')==true}">checked='checked'</c:if>/>团队游</label>　　
				<label><input type="checkbox" name="entity.productCategory" id="productCategory" value="邮轮"  <c:if test="${entity.productCategory.contains('邮轮')==true}">checked='checked'</c:if>/>邮轮</label>
				<br>
				<label><input type="checkbox" name="entity.productCategory" id="productCategory" value="自驾游"  <c:if test="${entity.productCategory.contains('自驾游')==true}">checked='checked'</c:if>/>自驾游</label>　
				<label><input type="checkbox" name="entity.productCategory" id="productCategory" value="门票"  <c:if test="${entity.productCategory.contains('门票')==true}">checked='checked'</c:if>/>门票</label>　
				<label><input type="checkbox" name="entity.productCategory" id="productCategory" value="酒店"  <c:if test="${entity.productCategory.contains('酒店')==true}">checked='checked'</c:if>/>酒店</label>　　
				<label><input type="checkbox" name="entity.productCategory" id="productCategory" value="火车票"  <c:if test="${entity.productCategory.contains('火车票')==true}">checked='checked'</c:if>/>火车票</label>
				<label><input type="checkbox" name="entity.productCategory" id="productCategory" value="汽车票"  <c:if test="${entity.productCategory.contains('汽车票')==true}">checked='checked'</c:if>/>汽车票</label>　　
				<br>
				<label><input type="checkbox" name="entity.productCategory" id="productCategory" value="签证"  <c:if test="${entity.productCategory.contains('签证')==true}">checked='checked'</c:if>/>签证</label>　　
				<label><input type="checkbox" name="entity.productCategory" id="productCategory" value="用车服务"  <c:if test="${entity.productCategory.contains('用车服务')==true}">checked='checked'</c:if>/>用车服务</label>　
				<label><input type="checkbox" name="entity.productCategory" id="productCategory" value="票务"  <c:if test="${entity.productCategory.contains('票务')==true}">checked='checked'</c:if>/>票务</label>　　
				<label><input type="checkbox" name="entity.productCategory" id="productCategory" value="目的地服务"  <c:if test="${entity.productCategory.contains('目的地服务')==true}">checked='checked'</c:if>/>目的地服务</label>
				<br>
				<label><input type="checkbox" name="entity.productCategory" id="productCategory" value="通信"  <c:if test="${entity.productCategory.contains('通信')==true}">checked='checked'</c:if>/>通信</label>
				<label><input type="checkbox" name="entity.productCategory" id="productCategory" value="定制游"  <c:if test="${entity.productCategory.contains('定制游')==true}">checked='checked'</c:if>/>定制游</label>
				<label><input type="checkbox" name="entity.productCategory" id="productCategory" value="衍生品"  <c:if test="${entity.productCategory.contains('衍生品')==true}">checked='checked'</c:if>/>衍生品</label>
				<label><input type="checkbox" name="entity.productCategory" id="productCategory" value="国内机票"  <c:if test="${entity.productCategory.contains('国内机票')==true}">checked='checked'</c:if>/>国内机票</label>
				<br>
				<label><input type="checkbox" name="entity.productCategory" id="productCategory" value="国际机票"  <c:if test="${entity.productCategory.contains('国际机票')==true}">checked='checked'</c:if>/>国际机票</label>
			</td>
		</tr>
		<tr>
			<th>目的地大类</th>
			<td>
				<label><input type="checkbox" name="entity.aroundFlag" id="aroundFlag" value="1" <c:if test="${entity.aroundFlag == 1}">checked='checked'</c:if>/>
				周边</label>　　
				<label><input type="checkbox" name="entity.inlandLongFlag" id="inlandLongFlag" value="1" <c:if test="${entity.inlandLongFlag == 1}">checked='checked'</c:if>/>
				国内长线</label>
				<label><input type="checkbox" name="entity.abroadShortFlag" id="abroadShortFlag" value="1" <c:if test="${entity.abroadShortFlag == 1}">checked='checked'</c:if>/>
				出境短线</label>
				<label><input type="checkbox" name="entity.abroadLongFlag" id="abroadLongFlag" value="1" <c:if test="${entity.abroadLongFlag == 1}">checked='checked'</c:if>/>
				出境长线</label>
				<label><input type="checkbox" name="entity.othersFlag" id="othersFlag" value="1" <c:if test="${entity.othersFlag == 1}">checked='checked'</c:if>/>
				其他</label>
			</td>
		</tr>
		</c:if>
		
		<c:if test="${entity.tourTimeNode == 1}">
			<tr>
			<th>处理赔款单</th>
			<td>
				<label><input type="radio" name="entity.payforOrder" id="payforOrder" value="1" <c:if test="${entity.payforOrder == 1}">checked='checked'</c:if>/>
					是</label>
				<label><input type="radio" name="entity.payforOrder" id="payforOrder" value="0" <c:if test="${entity.payforOrder == 0}">checked='checked'</c:if>/>
					否</label>
				</td>
		</tr>
		</c:if>
		
		<c:if test="${entity.tourTimeNode == 2 || entity.tourTimeNode == 3}">
		<tr>
			<th>签约城市</th>
			<td>
				<label><input type="checkbox" name="entity.signCity" id="signCity" value="北京" <c:if test="${entity.signCity.contains('北京')==true}">checked='checked'</c:if>/>北京</label>　　
				<label><input type="checkbox" name="entity.signCity" id="signCity" value="上海" <c:if test="${entity.signCity.contains('上海')==true}">checked='checked'</c:if>/>上海</label>　　
				<label><input type="checkbox" name="entity.signCity" id="signCity" value="深圳" <c:if test="${entity.signCity.contains('深圳')==true}">checked='checked'</c:if>/>深圳</label>　　
				<label><input type="checkbox" name="entity.signCity" id="signCity" value="广州" <c:if test="${entity.signCity.contains('广州')==true}">checked='checked'</c:if>/>广州</label>
				<label><input type="checkbox" name="entity.signCity" id="signCity" value="其他" <c:if test="${entity.signCity.contains('其他')==true}">checked='checked'</c:if>/>其他</label>
			</td>
		</tr>
		</c:if>
		<!-- 资深-->
		<c:if test="${entity.tourTimeNode == 3}">
		<tr>
		<th>投诉来源</th>
			<td>
				<label><input type="checkbox" name="entity.comeFrom" id="comeFrom" value="点评" <c:if test="${entity.comeFrom.contains('点评')==true}">checked='checked'</c:if> />点评</label>
				<label><input type="checkbox" name="entity.comeFrom" id="comeFrom" value="APP" <c:if test="${entity.comeFrom.contains('APP')==true}">checked='checked'</c:if> />APP</label>　　
				<label><input type="checkbox" name="entity.comeFrom" id="comeFrom" value="来电" <c:if test="${entity.comeFrom.contains('来电')==true}">checked='checked'</c:if> />来电</label>　　
				<label><input type="checkbox" name="entity.comeFrom" id="comeFrom" value="第三方(微博，媒体，旅游局)" <c:if test="${entity.comeFrom.contains('第三方')==true}">checked='checked'</c:if> />第三方(微博，媒体，旅游局)</label>
				<label><input type="checkbox" name="entity.comeFrom" id="comeFrom" value="CS邮箱" <c:if test="${entity.comeFrom.contains('CS邮箱')==true}">checked='checked'</c:if> />CS邮箱</label>　　
				<label><input type="checkbox" name="entity.comeFrom" id="comeFrom" value="其他"  <c:if test="${entity.comeFrom.contains('其他')==true}">checked='checked'</c:if>/>其他</label>　　
			</td>
		</tr>
		
		</c:if>
		
		<c:if test="${entity.tourTimeNode == 1 || entity.tourTimeNode == 2 || entity.tourTimeNode == 3}">
		<tr>
			<th>会员等级</th>
			<td>
				<select name="entity.guestLevel" >
					<option value="0" <c:if test="${entity.guestLevel==0}"> selected </c:if> >所有会员</option>
					<option value="1" <c:if test="${entity.guestLevel==1}"> selected </c:if> >五星以下</option>
					<option value="2" <c:if test="${entity.guestLevel==2}"> selected </c:if> >五星及以上</option>
				</select>
			</td>
		</tr>			
		</c:if>
		
		<tr>
			<th></th>
			<td>
				<input type="button"  class="pd5" value="修改" onclick="return onSubmitClicked();"> 
				<input type="button" class="pd5" value="取消" onclick="self.parent.tb_remove();">
			</td>
		</tr>
	</table>
</form>
</BODY>
