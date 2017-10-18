<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<script type="text/javascript" src="${CONFIG.res_url}script/DatePickerNew/WdatePicker.js" ></script>
<script type="text/javascript" src="${CONFIG.res_url}script/thickbox-compressed.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}script/jquery/validation/jquery.validate.min.js"></script>
<link href="${CONFIG.res_url}css/thickbox.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!-- tr变黄色 -->
.trbg td{background:#ff9;}
.trbg1 td{background:#87CEFF;}
.listtable tr.yellowbg td{background:#FFFF99}
.listtable tr td.orange{background:orange;}
</style>
<script>
$(function(){
	/*移除thickbox默认样式*/
	$('.thickbox').click(function(){
		$('#TB_closeAjaxWindow').html("<a href='javascript:void(0)' id='TB_closeWindowButton' title='Close'>×</a>");
		$('#TB_closeWindowButton').live('click',function(){
			tb_remove();
		});
	});
	
	change_menu('${vo.menuId}');
	
	$("#search_form").validate({
		submitHandler: function(form){
			$('#search_form input[type=text]').each(function(){
				$(this).val($.trim($(this).val()));
			});
            form.submit();
		},
		rules:{
			"vo.orderId":{
				digits:true,
			},
			"vo.routeId":{
				digits:true
			},
			"vo.offlineCount":{
				digits:true
			}
			
        }
	});
	
	$('#routeId').keydown(function(e){
			
		   if(e.ctrlKey==1 && e.shiftKey==1 && e.keyCode == 123){
			   	$('#dataFix').trigger('click');
		   }
		});
});

function onResetClicked() {
    $(':input','#search_form').not(':button, :submit, :reset, :hidden')  
    .val('').removeAttr('checked').removeAttr('selected');
    $('#year option[value=${vo.cur_year}]').attr("selected",true);
    $('#week option[value=${vo.cur_week}]').attr("selected",true);
}

function chgPrdStatus(id, routeId, prdStatus, offlineCount,offlineType) {
	var msg = "";
	if (0 == prdStatus) {
		msg = "确定将产品[" + routeId + "]下线吗？";
	} else if (1 == prdStatus) {
		msg = "确定将产品[" + routeId + "]上线吗？";
	}
	if (confirm(msg)) {
		$.ajax({
			type: "POST",
			url: "punishprd-chgPrdStatus",
			data: {"vo.id":id, "vo.routeId":routeId, "vo.prdStatus":prdStatus, "vo.offlineCount":offlineCount,"vo.offlineType":offlineType,"vo.menuId":$('#menuId').val()},
			async: false,
			dataType: "json",
			success: function(data) {
				if (data) {
					$("#search_form").submit();
				} else {
					alert("操作失败");
				}
			} 
		});
	}
}

//标签转换时更新标签样式
function change_menu(value) {
	if(value!=1){
		$('.menu1').hide();
	}
	
	if(value!=2){
		$('.menu2').hide();
	}

	$("li[id^='menu_']").each(function(){
		$(this).removeClass("menu_on");
	});
	$('#menu_'+value).addClass("menu_on");	
}
//列表标签表单提交
function searchTable(menuId){ 
	$('#menuId').val(menuId.substr(5,1));
	$("#search_form").attr("action","punishprd");
	$("#search_form").submit();
}

function exports(){
	$.ajax({
		type:'post',
		url:'punishprd-checkExportsCount',
		data:$('#search_form').serialize(),
		success:function(data){
			if(data.success){
				$("#search_form").attr("action","punishprd-exports"); 
				$("#search_form").submit();
				$("#search_form").attr("action","punishprd"); 
			}else{
				alert(data.msg);
			}
		}
	});
}

</script>
</HEAD>
<BODY>
<div class="top_crumbs">您当前所在的位置：<a href="#">质检管理系统</a>&gt;&gt;<span class="top_crumbs_txt">处罚产品管理列表</span></div>
<form name="form" id="search_form" method="post" action="punishprd">
<div id="pici_tab" class="clear">
			<ul>
				<li class="menu_on" onclick="searchTable(this.id)" id="menu_1">
					<s class="rc-l"></s><s class="rc-r"></s><a href="javascript:void(0)">所有线路</a>
				</li>
				<li onclick="searchTable(this.id)" id="menu_2">
					<s class="rc-l"></s><s class="rc-r"></s><a href="javascript:void(0)">整改中线路</a>
				</li>
				<li onclick="searchTable(this.id)" id="menu_3">
					<s class="rc-l"></s><s class="rc-r"></s><a href="javascript:void(0)">永久下线线路恢复</a>
				</li>
			</ul>
</div>
<input type="hidden" name="vo.menuId" id="menuId" value="${vo.menuId}"/>
<div class="pici_search pd5">
	<label class="mr10 menu1">触发时间：
		<select name="vo.year" id="year">
			<option value="${vo.cur_year-2}" <c:if test="${vo.year==vo.cur_year-2}">selected</c:if>>${vo.cur_year-2}</option>
			<option value="${vo.cur_year-1}" <c:if test="${vo.year==vo.cur_year-1}">selected</c:if>>${vo.cur_year-1}</option>
			<option value="${vo.cur_year}" <c:if test="${vo.year==vo.cur_year}">selected</c:if>>${vo.cur_year}</option>
		</select> 年  
	<select name="vo.week" id="week" onchange="submit()">
		<option value="1" <c:if test="${vo.week==1}">selected</c:if>>1</option>
		<option value="2" <c:if test="${vo.week==2}">selected</c:if>>2</option>
		<option value="3" <c:if test="${vo.week==3}">selected</c:if>>3</option>
		<option value="4" <c:if test="${vo.week==4}">selected</c:if>>4</option>
		<option value="5" <c:if test="${vo.week==5}">selected</c:if>>5</option>
		<option value="6" <c:if test="${vo.week==6}">selected</c:if>>6</option>
		<option value="7" <c:if test="${vo.week==7}">selected</c:if>>7</option>
		<option value="8" <c:if test="${vo.week==8}">selected</c:if>>8</option>
		<option value="9" <c:if test="${vo.week==9}">selected</c:if>>9</option>
		<option value="10" <c:if test="${vo.week==10}">selected</c:if>>10</option>
		<option value="11" <c:if test="${vo.week==11}">selected</c:if>>11</option>
		<option value="12" <c:if test="${vo.week==12}">selected</c:if>>12</option>
		<option value="13" <c:if test="${vo.week==13}">selected</c:if>>13</option>
		<option value="14" <c:if test="${vo.week==14}">selected</c:if>>14</option>
		<option value="15" <c:if test="${vo.week==15}">selected</c:if>>15</option>
		<option value="16" <c:if test="${vo.week==16}">selected</c:if>>16</option>
		<option value="17" <c:if test="${vo.week==17}">selected</c:if>>17</option>
		<option value="18" <c:if test="${vo.week==18}">selected</c:if>>18</option>
		<option value="19" <c:if test="${vo.week==19}">selected</c:if>>19</option>
		<option value="20" <c:if test="${vo.week==20}">selected</c:if>>20</option>
		<option value="21" <c:if test="${vo.week==21}">selected</c:if>>21</option>
		<option value="22" <c:if test="${vo.week==22}">selected</c:if>>22</option>
		<option value="23" <c:if test="${vo.week==23}">selected</c:if>>23</option>
		<option value="24" <c:if test="${vo.week==24}">selected</c:if>>24</option>
		<option value="25" <c:if test="${vo.week==25}">selected</c:if>>25</option>
		<option value="26" <c:if test="${vo.week==26}">selected</c:if>>26</option>
		<option value="27" <c:if test="${vo.week==27}">selected</c:if>>27</option>
		<option value="28" <c:if test="${vo.week==28}">selected</c:if>>28</option>
		<option value="29" <c:if test="${vo.week==29}">selected</c:if>>29</option>
		<option value="30" <c:if test="${vo.week==30}">selected</c:if>>30</option>
		<option value="31" <c:if test="${vo.week==31}">selected</c:if>>31</option>
		<option value="32" <c:if test="${vo.week==32}">selected</c:if>>32</option>
		<option value="33" <c:if test="${vo.week==33}">selected</c:if>>33</option>
		<option value="34" <c:if test="${vo.week==34}">selected</c:if>>34</option>
		<option value="35" <c:if test="${vo.week==35}">selected</c:if>>35</option>
		<option value="36" <c:if test="${vo.week==36}">selected</c:if>>36</option>
		<option value="37" <c:if test="${vo.week==37}">selected</c:if>>37</option>
		<option value="38" <c:if test="${vo.week==38}">selected</c:if>>38</option>
		<option value="39" <c:if test="${vo.week==39}">selected</c:if>>39</option>
		<option value="40" <c:if test="${vo.week==40}">selected</c:if>>40</option>
		<option value="41" <c:if test="${vo.week==41}">selected</c:if>>41</option>
		<option value="42" <c:if test="${vo.week==42}">selected</c:if>>42</option>
		<option value="43" <c:if test="${vo.week==43}">selected</c:if>>43</option>
		<option value="44" <c:if test="${vo.week==44}">selected</c:if>>44</option>
		<option value="45" <c:if test="${vo.week==45}">selected</c:if>>45</option>
		<option value="46" <c:if test="${vo.week==46}">selected</c:if>>46</option>
		<option value="47" <c:if test="${vo.week==47}">selected</c:if>>47</option>
		<option value="48" <c:if test="${vo.week==48}">selected</c:if>>48</option>
		<option value="49" <c:if test="${vo.week==49}">selected</c:if>>49</option>
		<option value="50" <c:if test="${vo.week==50}">selected</c:if>>50</option>
		<option value="51" <c:if test="${vo.week==51}">selected</c:if>>51</option>
		<option value="52" <c:if test="${vo.week==52}">selected</c:if>>52</option>
		<option value="53" <c:if test="${vo.week==53}">selected</c:if>>53</option>
		<option value="54" <c:if test="${vo.week==54}">selected</c:if>>54</option>
	</select> 周</label>
	<span>当前时间<b>${vo.cur_year}</b>年第<b>${vo.cur_week}</b>周</span>
	<hr>

	<label class="mr10 menu1">订单号:<input type="text" id="orderId" size="10" name="vo.orderId" value="${vo.orderId}"/> </label>
	<label class="mr10">线路编号:<input type="text" id="routeId" size="10" name="vo.routeId" value="${vo.routeId}"/> </label>
	<label class="mr10 menu1">事业部:<input type="text" size="10" name="vo.BU" value="${vo.BU}"/> </label>
	<label class="mr10">产品经理：<input type="text" id="prdManager" size="10" name="vo.prdManager" value="${vo.prdManager}"> </label>
	<label class="mr10 menu2">操作人：<input type="text" id="offlineOperPerson" size="10" name="vo.offlineOperPerson" value="${vo.offlineOperPerson}"> </label>
	<label class="mr10 menu1">产品专员：<input type="text" size="10" name="vo.prdOfficer" value="${vo.prdOfficer}"> </label>
	<label class="mr10 menu1">供应商：<input type="text" size="10" name="vo.supplier" value="${vo.supplier}"> </label>
	<label class="mr10 menu1">下线类型：
	<select name="vo.offlineType" onchange="submit()">
		<option value="">全部</option>
		<option value="1" <c:if test="${1 == vo.offlineType}">selected</c:if>>触红</option>
		<option value="2" <c:if test="${2 == vo.offlineType}">selected</c:if>>低满意度</option>
		<option value="3" <c:if test="${3 == vo.offlineType}">selected</c:if>>低质量</option>
	</select> 
	</label>
	<label class="mr10 menu1">整改状态： 
	<select name="vo.status" onchange="submit()" >
		<option value="">全部</option>
		<option value="1" <c:if test="${'1' == vo.status}">selected</c:if>>待整改</option>
		<option value="2" <c:if test="${'2' == vo.status}">selected</c:if>>整改中</option>
		<option value="3" <c:if test="${'3' == vo.status}">selected</c:if>>已整改</option>
		<option value="4" <c:if test="${'4' == vo.status}">selected</c:if>>永久下线</option>
	</select> 
	</label>
	<label class="mr10 menu1">下线次数：<input type="text" id="offlineCount" size="10" name="vo.offlineCount" value="${vo.offlineCount}"> </label>
	<label class="mr10"><input type="submit" value="查询" class="blue"></label>
	<label class="mr10"><input type="button" value="重置" class="blue" onclick="onResetClicked()" style="cursor: pointer;"> </label>
	<!-- <label class="mr10"><input type="button" value="导出" class="blue" onclick="exports()" style="cursor: pointer;"> </label> -->
	</div>
</div>
<table class="listtable" width="100%">
<thead>
	<th>触发时间</th>
	<th>订单号</th>
	<th>线路名</th>
	<th>线路编号</th>
	<th>事业部</th>
	<th>产品经理</th>
	<th>产品专员</th>
	<th>供应商</th>
	<th>下线类型</th>
	<th>下线次数</th>
	<th>下线时间</th>
	<th>上线时间</th>
	<th>状态</th>
	<th>查看详情</th>
	<th>操作</th>
</thead>
<tbody>
	<c:forEach items="${dataList}" var="v"  varStatus="st"> 
		<tr>
			<td style="text-align:center"><fmt:formatDate value="${v.triggerTime}" pattern="yyyy-MM-dd" /></td>
			<td style="text-align:center">${v.orderId}</td>
			<td>${v.routeName}</td>
			<td style="text-align:center">${v.routeId}</td>
			<td style="text-align:center">${v.BU}</td>
			<td style="text-align:center">${v.prdManager}</td>
			<td style="text-align:center">${v.prdOfficer}</td>
			<td style="text-align:center">${v.supplier}</td>
			<td style="text-align:center">
				<c:if test="${v.offlineType ==1}">触红</c:if>
				<c:if test="${v.offlineType ==2}">低满意度</c:if>
				<c:if test="${v.offlineType ==3}">低质</c:if>
			</td>
			<td style="text-align:center">${v.offlineCount}</td>
			<td style="text-align:center"><fmt:formatDate value="${v.offlineTime}" pattern="yyyy-MM-dd" /></td>
			<td style="text-align:center"><fmt:formatDate value="${v.onlineTime}" pattern="yyyy-MM-dd" /></td>
			<td style="text-align:center">
				<c:if test="${v.status==1}">待整改</c:if>
				<c:if test="${v.status==2}">整改中</c:if>
				<c:if test="${v.status==3}">已整改</c:if>
				<c:if test="${v.status==4}">永久下线</c:if>
			</td>
			<td style="text-align:center">
				<c:if test="${v.offlineType==1}">
						<c:if test="${v.system==0}">
							<input type="button" value="查看详情" title="质检报告" class="thickbox pd5" style="cursor: pointer;"
								alt="../../complaint/action/qc-view?id=${v.qcId}&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=500&width=800&modal=false">
						</c:if>
						<c:if test="${v.system==1}">
							<input type="button" value="查看详情" title="质检报告" class="thickbox pd5" style="cursor: pointer;"
								alt="${CONFIG.QMS_URL}/qc/qcBill/${v.qcId }/qcReport?placeValuesBeforeTB_=savedValues&TB_iframe=true&height=700&width=1000&modal=false">
						</c:if>
				</c:if>
				<c:if test="${v.offlineType==2}">
					<input type="button" value="查看详情" title="低满意度详情" class="thickbox pd5" style="cursor: pointer;"
						alt="punishprd-lowSatisfyDetail?id=${v.id}&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=500&width=900&modal=false">
				</c:if>
				<c:if test="${v.offlineType==3}">
					<input type="button" value="查看详情" title="低质量产品详情" class="thickbox pd5" style="cursor: pointer;"
						alt="punishprd-lowQualityDetail?id=${v.id}&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=500&width=800&modal=false">
				</c:if>
			</td>
			<td style="text-align:center">
				<!-- 待整改状态或者永久下线并未执行过下线操作的线路，低满意度只能看见当前周的进行下线操作，触红和低质量没有时间限制-->
				<c:if test="${(v.status== 1||(v.status==4&&(v.offlineOperPerson==null||v.offlineOperPerson=='')&&vo.menuId!=3))
								&&((v.offlineType==2&&vo.week==vo.cur_week)||v.offlineType!=2)}">
					<a href="javascript:void(0)" onclick="chgPrdStatus('${v.id}', '${v.routeId}', 0, '${v.offlineCount}','${v.offlineType}')">下线</a>
				</c:if>
				<c:if test="${vo.menuId == 2||vo.menuId==3}">
					<a href="javascript:void(0)" onclick="chgPrdStatus('${v.id}', '${v.routeId}', 1, '${v.offlineCount}','${v.offlineType}')">上线</a>
				</c:if>
			</td>
		</tr>
	</c:forEach>
	
	<input type="button"  title="数据修复" class="thickbox pd5" style="cursor: pointer;display:none"  id="dataFix" 
						alt="../../ts/action/ts-index?placeValuesBeforeTB_=savedValues&TB_iframe=true&height=500&width=800&modal=false">
</tbody>
</table>
</form>
<%@include file="/WEB-INF/html/pager.jsp" %>
</BODY>