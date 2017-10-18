<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>投诉质检单列表</title>

<style type="text/css">
/*basic*/
.clearfix:after {
	content: ".";
	display: block;
	height: 0;
	clear: both;
}

/*覆盖插件默认样式*/
.ui-autocomplete {
	max-height: 100px;
	overflow-y: auto;
	/* prevent horizontal scrollbar */
	overflow-x: hidden;
}

.ui-widget {
	font-family: Microsoft YaHei;
}

/*搜索框表格样式*/
.search {
	font-size: 12px;
	font-family: Microsoft YaHei;
}

.search td:nth-child(even) {
	text-align: left;
}

.search td:nth-child(odd) {
	text-align: right;
}

.search td input[type=text] {
	width: 100px;
}
`
/*字段配置样式*/
#fieldListDiv {
	font-family: Microsoft YaHei;
	font-size: 12px;
	background: ##E1E4E6;
	padding: 20px 0;
}

#fieldListDiv ul {
	list-style: none;
	margin: 0px;
	float: left;
	clear: both
}

#fieldListDiv ul li {
	float: left;
	margin: 0 10px;
	display: block;
	line-height: 28px
}

#fieldListDiv ul li:hover {
	color: red;
}

/*覆盖通用数据表格样式*/
.listtable th {
	display: none;
}

.listtable td {
	display: none;
}

.listtable .orderable {
	background: #DFEAFB url('res/img/line.png') no-repeat right 5px  center;
	text-align: right;
	padding-right: 26px;
}
</style>

<script type="text/javascript">
	var userArr = new Array();//人名自动补全辅助数组

	$(function() {
		$("#accordion").accordion({
			collapsible : true,
			heightStyle : "content"
		});

		var fields =new Array();
		
		<c:forEach items="${dto.fields}" var="field">
			fields.push('${field}');
		</c:forEach>
		for (var i=0;i<fields.length;i++)
		{
			$("#f_" + fields[i]).click();
		}

		$('#fieldListDiv :checkbox').each(function() {
			$(this).click(function() {
				$('.' + this.id.substring(2)).toggle();
			});
			if (this.checked) {
				$('.' + this.id.substring(2)).toggle();
			}
		});

		$('#fieldListDiv').toggle();
		$('.listtable th:first').toggle();
		$('.listtable tr').find('td:first').toggle();
		$('.listtable th:last').toggle();
		$('.listtable tr').find('td:last').toggle();

		$('#chkAll').click(function() {
			var flag = this.checked;
			$('.listtable td :checkbox[name = qcBillIds]').each(function() {
				this.checked = flag;
			});
		});

		$('#radioset').buttonset();
		$('#radioset').click(function() {
			searchResetPage();
		});
		
		$('.orderable').click(function() {
			//取orderField
			var orderField = $('#orderField').val();
			//取orderDirect
			var orderDirect = $('#orderDirect').val();
			//如果orderField和当前点击的id一样，则orderDirect+1%3
			var  clickId = $(this).attr('id');
			if(clickId == orderField) {
				orderDirect = (orderDirect+1)%3;
			}else{//如果orderField和当前点击的id不一样，则orderDirect=1
				orderDirect = 1;
			}
			
			$('#orderField').val(clickId);
			$('#orderDirect').val(orderDirect);
			
			//提交
			searchResetPage();
		});
		
		dealOrderStyle();
		
	});

	//为自动补全控件准备数据
	function userAutoComplete() {
		//如果已经取过，则直接使用
		if (userArr.length > 0) {
			return;
		} else {
			$.ajax({
				type : "POST",
				url : "qc/qcBill/getUserNamesInJSON",
				success : function(data) {
					for (i = 0; i < data.length; i++) {
						userArr.push({
							label : data[i].label,
							value : data[i].realName
						});
					}
				}
			});

			$("#qcPerson").autocomplete({
				minLength : 2,
				source : userArr
			}).data("ui-autocomplete")._renderItem = function(ul, item) {
				return $("<li>").append("<a>" + item.value + "</a>").appendTo(
						ul);
			};

			$("#dealPerson").autocomplete({
				minLength : 2,
				source : userArr
			}).data("ui-autocomplete")._renderItem = function(ul, item) {
				return $("<li>").append("<a>" + item.value + "</a>").appendTo(
						ul);
			};

			$("#assignTo").autocomplete({
				minLength : 2,
				source : userArr
			}).data("ui-autocomplete")._renderItem = function(ul, item) {
				return $("<li>").append("<a>" + item.value + "</a>").appendTo(
						ul);
			};
			
			$("#prdManager").autocomplete({
				minLength : 2,
				source : userArr
			}).data("ui-autocomplete")._renderItem = function(ul, item) {
				return $("<li>").append("<a>" + item.value + "</a>").appendTo(
						ul);
			};
			
			$("#producter").autocomplete({
				minLength : 2,
				source : userArr
			}).data("ui-autocomplete")._renderItem = function(ul, item) {
				return $("<li>").append("<a>" + item.value + "</a>").appendTo(
						ul);
			};
			
			$("#salerManagerName").autocomplete({
				minLength : 2,
				source : userArr
			}).data("ui-autocomplete")._renderItem = function(ul, item) {
				return $("<li>").append("<a>" + item.value + "</a>").appendTo(
						ul);
			};
			
			$("#salerName").autocomplete({
				minLength : 2,
				source : userArr
			}).data("ui-autocomplete")._renderItem = function(ul, item) {
				return $("<li>").append("<a>" + item.value + "</a>").appendTo(
						ul);
			};

		}

		$(this).forcus();
	}

	function search() {
		$("#searchForm").attr("action", "qc/qcBill/qcCancelList");
		$("#searchForm").submit();
	}

	 function refresh(){
		 
		 search();
	 }
	function addTabx(title, url, id) {
		if (url && url != '') {
			var htmlStr = '<iframe src="' + url
					+ '" width="100%" height="100%" frameborder="0"></iframe>';
			window.parent.tabpanel.addTab({
				id : "newtab_qcBill_" + id,
				title : title,
				html : htmlStr
			});
		}
	}

	//重置搜索框内容：直接使用reset会影响所有form中内容
	function resetSearchTable() {
		$('.search :text').val('');
		$('#cmpLevel').val("");
		$('.cmpState').val("4");
	}
	
	function setMaxDate(id){
		WdatePicker({maxDate:'#F{$dp.$D(\''+id+'\')}'});
	}
	
	function setMinDate(id){
		WdatePicker({minDate:'#F{$dp.$D(\''+id+'\')}'});
	}
	
	function dealOrderStyle(){
		var orderFiled = $('#orderField').val();
		var orderDirect = $('#orderDirect').val();
		if(orderDirect==1){
			$('.listtable th#'+orderFiled).css('background',"#DFEAFB url('res/img/down.png') no-repeat right 5px center");
		}else if (orderDirect ==2){
			$('.listtable th#'+orderFiled).css('background',"#DFEAFB url('res/img/up.png') no-repeat right 5px center");
		}
	}
	

	function backToProcessing(id) {
		$("#searchForm").attr("action", "qc/qcBill/" + id + "/backToProcessing");
		$("#searchForm").submit();
	}
</script>
</head>

<body>
<form name="searchForm" id="searchForm" method="post" action="">

<form:hidden path="dto.orderField"/>
<form:hidden path="dto.orderDirect"/>
<div id="accordion">
	<h3>搜索框</h3>
	<table width="100%" class="search">
		<tr>
			<td>质检单号：</td>
			<td><form:input path="dto.id"/></td>
			<td>出 发 地：</td>
			<td><form:input path="dto.departCity"/></td>
			<td>损失金额(元)：</td>
			<td><form:input path="dto.lossAmountBgn"/> 至 <form:input path="dto.lossAmountEnd"/></td>
			<td>出游日期：</td>
			<td>
				<form:input path="dto.departDateBgn" onfocus="setMaxDate('departDateEnd')" />
			至 
			<form:input path="dto.departDateEnd" onfocus="setMinDate('departDateBgn')" />
		</td>
	</tr>

	<tr>
		<td>产品编号：</td>
		<td><form:input path="dto.prdId"/></td>
		<td>产品品类：</td>
		<td><form:input path="dto.cateName"/></td>
		<td>成人途牛价(元)：</td>
		<td><form:input path="dto.prdAdultPriceBgn"/> 至 <form:input path="dto.prdAdultPriceEnd"/></td>
		<td>归来日期：</td>
		<td>
			<form:input path="dto.returnDateBgn" onfocus="setMaxDate('returnDateEnd')" />
			至 
			<form:input path="dto.returnDateEnd" onfocus="setMinDate('returnDateBgn')" />
		</td>
	</tr>

	<tr>

		<td>订 单 号：</td>
		<td><form:input path="dto.orderId"/></td>
		<td>产品子品类：</td>
		<td><form:input path="dto.subCateName"/></td>
		<td>对客赔偿总额(元)：</td>
		<td><form:input path="dto.indemnifyAmountBgn"/> 至 <form:input path="dto.indemnifyAmountEnd"/></td>
		<td>质检添加时间：</td>
		<td>
			<form:input path="dto.addTimeFrom" onfocus="setMaxDate('addTimeTo')" />
			至
			<form:input path="dto.addTimeTo" onfocus="setMinDate('addTimeFrom')" />
		</td>
	</tr>

	<tr>
		<td>投诉单号：</td>
		<td><form:input path="dto.cmpId"/></td>
		<td>产品品牌：</td>
		<td><form:input path="dto.brandName"/></td>
		<td>供应商赔付总额(元)：</td>
		<td><form:input path="dto.claimAmountBgn"/> 至 <form:input path="dto.claimAmountEnd"/></td>
		<td>质检分配时间：</td>
		<td>
			<form:input path="dto.distribTimeBgn" onfocus="setMaxDate('distribTimeEnd')" />
			至 
			<form:input path="dto.distribTimeEnd" onfocus="setMinDate('distribTimeBgn')" />
		</td>
	</tr>
	<tr>
		<td>质 检 员：</td>
		<td>
		<c:choose>
				<c:when test="${loginUser.role.type==1}">
						<form:input path="dto.qcPerson" disabled="true" onfocus="userAutoComplete(this.id)"/>
				</c:when>
				<c:otherwise>
						<form:input path="dto.qcPerson" onfocus="userAutoComplete(this.id)"/>
				</c:otherwise>
		</c:choose>
		</td>
		<td>产品线目的地：</td>
		<td><form:input path="dto.prdLineDestName" /></td>
		<td>投诉时间：</td>
		<td>
			<form:input path="dto.cmpAddTimeBgn" onfocus="setMaxDate('cmpAddTimeEnd')" />
			至 
			<form:input path="dto.cmpAddTimeEnd" onfocus="setMinDate('cmpAddTimeBgn')" />
		</td>
		<td>质检完成时间：</td>
		<td>
			<form:input path="dto.finishTimeBgn" onfocus="setMaxDate('finishTimeEnd')" />
			至 
			<form:input path="dto.finishTimeEnd" onfocus="setMinDate('finishTimeBgn')" />
		</td>
	</tr>


	<tr>
		<td>投诉处理人：</td>
		<td><input type="text" name="dealPerson"
			onfocus="userAutoComplete(this.id)" value="${dto.dealPerson }"
			id="dealPerson"></td>
		<td>投诉级别：</td>
		<td>
			<form:select path="dto.cmpLevel" id="cmpLevel"  onchange="searchResetPage()">
					<form:option value="" label="全部" />
					<form:option value="1" label="一级" />
					<form:option value="2" label="二级" />
					<form:option value="3" label="三级" />
			</form:select>
		</td>
		<td>投诉处理完成时间：</td>
		<td>
			<form:input path="dto.cmpFinishTimeBgn" onfocus="setMaxDate('cmpFinishTimeEnd')" />
			至 
			<form:input path="dto.cmpFinishTimeEnd" onfocus="setMinDate('cmpFinishTimeBgn')" />
		</td>
		<td>投诉状态：</td>
		<td>
			<form:select path="dto.cmpState" class="cmpState"   onchange="searchResetPage()">
					<form:option value="" label="全部" />
					<form:option value="1" label="投诉待处理" />
					<form:option value="2" label="投诉处理中" />
					<form:option value="3" label="投诉已待结" />
					<form:option value="4" label="投诉已完成" />
					<form:option value="5" label="升级售后" />
					<form:option value="6" label="提交售后填写分担方案" />
					<form:option value="7" label="升级售前" />
					<form:option value="10" label="升级客服中心售后" />
			</form:select>
		</td>
	</tr>
	
	<tr>
		<td>产品经理：</td>
		<td><input type="text" name="prdManager"
			onfocus="userAutoComplete(this.id)" value="${dto.prdManager }"
			id="prdManager"></td>
		<td>产品专员：</td>
		<td><input type="text" name="producter"
			onfocus="userAutoComplete(this.id)" value="${dto.producter }"
			id="producter"></td>
		<td>客服经理：</td>
		<td><input type="text" name="salerManagerName"
			onfocus="userAutoComplete(this.id)" value="${dto.salerManagerName }"
			id="salerManagerName"></td>
		<td>客服专员：</td>
		<td><input type="text" name="salerName"
			onfocus="userAutoComplete(this.id)" value="${dto.salerName }"
			id="salerName"></td>
	</tr>
	<tr>
		<td>投诉来源：</td>
		<td>
			<form:select path="dto.comeFrom" class="comeFrom"   onchange="searchResetPage()">
					<form:option value="" label="全部" />
					<form:option value="网站" label="网站" />
					<form:option value="门市" label="门市" />
					<form:option value="当地质检" label="当地质检" />
					<form:option value="来电投诉" label="来电投诉" />
					<form:option value="CS邮箱" label="CS邮箱" />
					<form:option value="回访" label="回访" />
					<form:option value="旅游局" label="旅游局" />
					<form:option value="微博" label="微博" />
					<form:option value="其他" label="其他" />
			</form:select>
		</td>
		<td>一级事业部：</td>
		<td><input type="text" name="businessUnitName"  value="${dto.businessUnitName }" id="businessUnitName"></td>
		<td> </td>
		<td style="text-align: center">
			<input type="button" class="blue" value="查询" onclick="searchResetPage()"/>
			<input type="button" class="blue" value="重置" onclick="resetSearchTable()" />
			<input type="button" class="blue" value="字段选取" onclick="$('#fieldListDiv').toggle()" />
		</td>
	</tr>
	</table>
</div>

<div id="fieldListDiv" class="clearfix">
	<ul>
		<li><strong>质检字段：</strong></li>
		<li><label><input type="checkbox" name="fields"
				value="importantFlag" id="f_importantFlag">重要标记</label></li>
		<li><label><input type="checkbox" name="fields"
				value="userFlag" id="f_userFlag">核实</label></li>
		<li><label><input type="checkbox" name="fields"
				value="cancelFlag" id="f_cancelFlag">撤销</label></li>
		<li><label><input type="checkbox" name="fields"
				value="id" id="f_id">质检单号</label></li>
		<li><label><input type="checkbox" name="fields"
				id="f_state" value="state">质检状态</label></li>
		<li><label><input type="checkbox" name="fields"
				id="f_qcPerson" value="qcPerson">质检员</label></li>
		<li><label><input type="checkbox" name="fields"
				id="f_addTime" value="addTime">添加时间</label></li>
		<li><label><input type="checkbox" name="fields"
				id="f_qcPeriodBgnTime" value="qcPeriodBgnTime">质检开始时间</label></li>
		<li><label><input type="checkbox" name="fields"
				id="f_qcPeriodEndTime" value="qcPeriodEndTime">质检到期时间</label></li>
		<li><label><input type="checkbox" value="distribTime"
				name="fields" id="f_distribTime">分配时间</label></li>
		<li><label><input type="checkbox" name="fields"
				value="finishTime" id="f_finishTime">完成时间</label></li>
		<li><label><input type="checkbox" name="fields"
				value="remark" id="f_remark">备注</label></li>
		<li><label><input type="checkbox" name="fields"
				value="prdId" id="f_prdId">产品编号</label></li>
		<li><label><input type="checkbox" name="fields"
				value="groupDate" id="f_groupDate">团期</label></li>
	</ul>
	<ul>
		<li><strong>产品字段：</strong></li>
		<li><label><input type="checkbox" name="fields"
				value="prdName" id="f_prdName">产品名称</label></li>
		<li><label><input type="checkbox" name="fields"
				value="cateName" id="f_cateName">产品品类</label></li>
		<li><label><input type="checkbox" name="fields"
				value="subCateName" id="f_subCateName">产品子品类</label></li>
		<li><label><input type="checkbox" name="fields"
				value="brandName" id="f_brandName">产品品牌</label></li>
		<li><label><input type="checkbox" name="fields"
				value="prdLineDestName" id="f_prdLineDestName">目的地</label></li>
		<li><label><input type="checkbox" name="fields"
				value="businessUnitName" id="f_businessUnitName">事业部</label></li>
	</ul>
	<ul>
		<li><strong>订单字段：</strong></li>
		<li><label><input type="checkbox" name="fields"
				value="orderId" id="f_orderId">订单号</label></li>
		<li><label><input type="checkbox" name="fields"
				value="departCity" id="f_departCity">出发地</label></li>
		<li><label><input type="checkbox" name="fields"
				value="prdAdultPrice" id="f_prdAdultPrice">成人途牛价</label></li>
		<li><label><input type="checkbox" name="fields"
				value="adultNum" id="f_adultNum">出游成人数</label></li>
		<li><label><input type="checkbox" name="fields"
				value="childNum" id="f_childNum">出游儿童数</label></li>
		<li><label><input type="checkbox" name="fields"
				value="departDate" id="f_departDate">出游日期</label></li>
		<li><label><input type="checkbox" name="fields"
				value="returnDate" id="f_returnDate">归来日期</label></li>
		<li><label><input type="checkbox" name="fields"
				value="prdManager" id="f_prdManager">产品经理</label></li>
		<li><label><input type="checkbox" name="fields"
				value="producter" id="f_producter">产品专员</label></li>
		<li><label><input type="checkbox" name="fields"
				value="salerManagerName" id="f_salerManagerName">客服经理</label></li>
		<li><label><input type="checkbox" name="fields"
				value="salerName" id="f_salerName">客服专员</label></li>
	</ul>
	<ul>
		<li><strong>投诉字段：</strong></li>
		<li><label><input type="checkbox" name="fields"
				value="cmpId" id="f_cmpId">投诉单号</label></li>
		<li><label><input type="checkbox" name="fields"
				value="cmpLevel" id="f_cmpLevel">投诉级别</label></li>
		<li><label><input type="checkbox" name="fields"
			value="cmpAddTime" id="f_cmpAddTime">投诉时间</label></li>
		<li><label><input type="checkbox" name="fields"
				value="comeFrom" id="f_comeFrom">投诉来源</label></li>
		<li><label><input type="checkbox" name="fields"
				value="cmpFinishTime" id="f_cmpFinishTime">投诉处理完成时间</label></li>
		<li><label><input type="checkbox" name="fields"
				value="dealPerson" id="f_dealPerson">处理人</label></li>
		<li><label><input type="checkbox" name="fields"
				value="indemnifyAmount" id="f_indemnifyAmount">对客赔偿总额</label></li>
		<li><label><input type="checkbox" name="fields"
				value="claimAmount" id="f_claimAmount">供应商赔付总额</label></li>
		<li><label><input type="checkbox" name="fields"
				value="companyLose" id="f_companyLose">公司赔付金额</label></li>
	</ul>
</div>

<table class="listtable">
	<tr>
		<th><input type="checkbox" id="chkAll" title="全选"></th>
	
		<th id="importantFlag" class="importantFlag orderable">重要</th>
		<th id="userFlag" class="userFlag orderable">核实</th>
		<th id="cancelFlag" class="cancelFlag orderable">撤销</th>
		<th class="id">质检单号</th>
		<th class="state">质检状态</th>
		<th class="qcPerson">质检员</th>
		<th class="addTime">添加时间</th>
		<th class="qcPeriodBgnTime ">质检开始时间</th>
		<th id="qcPeriodEndTime" class="qcPeriodEndTime orderable">质检到期时间</th>
		<th class="distribTime">分配时间</th>
		<th class="finishTime">完成时间</th>
		<th class="remark">备注</th>
		<th id="prdId" class="prdId orderable">产品编号</th>
		<th id="groupDate" class="groupDate">团期</th>
	
		<th class="prdName">产品名称</th>
		<th class="cateName">产品品类</th>
		<th class="subCateName">产品<br>子品类</th>
		<th class="brandName">产品品牌</th>
		<th class="prdLineDestName">目的地</th>
		<th class="businessUnitName">事业部</th>
	
		<th class="orderId">订单号</th>
		<th class="departCity">出发地</th>
		<th class="prdAdultPrice">成人<br/>途牛价</th>
		<th class="adultNum">出游成人数</th>
		<th class="childNum">出游儿童数</th>
		<th class="departDate">出游日期</th>
		<th class="returnDate">归来日期</th>
		<th class="prdManager">产品经理</th>
		<th class="producter">产品专员</th>
		<th class="salerManagerName">客服经理</th>
		<th class="salerName">客服专员</th>
	
		<th class="cmpId">投诉单号</th>
		<th class="cmpLevel">投诉级别</th>
		<th id="comeFrom" class="comeFrom">投诉来源</th>
		<th id="cmpAddTime" class="cmpAddTime orderable">投诉时间</th>
		<th id="cmpFinishTime" class="cmpFinishTime orderable">投诉处理<br/>完成时间</th>
		<th class="dealPerson">处理人</th>
		<th class="companyLose" >公司<br>赔偿金额</th>
		<th class="operate">操作</th>
	</tr>

	<c:forEach items="${dto.dataList}" var="qcBill">
	<tr>
		<td><input type="checkbox" name="qcBillIds" value="${qcBill.id }"/></td>
		<td class="importantFlag">
			<c:if test="${qcBill.importantFlag ==1}"><img src="res/img/important.png" width="16px" height="16px"/></c:if>
			<c:if test="${qcBill.importantFlag ==0}"><img src="res/img/not_important.png" width="16px" height="16px"/></c:if>
		</td>
		<td class="userFlag">
			<c:if test="${qcBill.userFlag ==1}"><img src="res/img/user_true.png" width="16px" height="16px"/></c:if>
			<c:if test="${qcBill.userFlag ==0}"><img src="res/img/user_false.png" width="16px" height="16px"/></c:if>
		</td>
		<td class="cancelFlag">
			<c:if test="${qcBill.cancelFlag ==1}"><img src="res/img/cancel_true.png" width="16px" height="16px"/></c:if>
			<c:if test="${qcBill.cancelFlag ==0}"><img src="res/img/cancel_false.png" width="16px" height="16px"/></c:if>
		</td>
	    <td class="id" >	
			<a href="javascript:void(0)" onclick="openWin('质检报告','qc/qcBill/${qcBill.id}/qcReport',1000,520)">${qcBill.id} 
			</a>
		</td>
		<td class="state">${qcBill.stateName}</td>
		<td class="qcPerson">${qcBill.qcPerson}</td>
		<td class="addTime" title='<fmt:formatDate value="${qcBill.addTime}" pattern="yyyy-MM-dd HH:mm:ss"/>'><fmt:formatDate value="${qcBill.addTime}" pattern="yyyy-MM-dd"/></td>
		<td class="qcPeriodBgnTime" title='<fmt:formatDate value="${qcBill.qcPeriodBgnTime}" pattern="yyyy-MM-dd HH:mm:ss"/>'><fmt:formatDate value="${qcBill.qcPeriodBgnTime}" pattern="yyyy-MM-dd"/></td>
		<c:if test="${qcBill.state == 4 }">
		<c:choose>
		<c:when test="${qcBill.finishTime.time - qcBill.qcPeriodEndTime.time > 0}">
			<td class="qcPeriodEndTime" style="color:red"   title='<fmt:formatDate value="${qcBill.qcPeriodEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>'><fmt:formatDate value="${qcBill.qcPeriodEndTime}" pattern="yyyy-MM-dd"/></td>
		</c:when>
		<c:otherwise>
			<td class="qcPeriodEndTime"   title='<fmt:formatDate value="${qcBill.qcPeriodEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>'><fmt:formatDate value="${qcBill.qcPeriodEndTime}" pattern="yyyy-MM-dd"/></td>
		</c:otherwise>
		</c:choose>
		</c:if>
		<c:if test="${qcBill.state !=4 }">
		<c:set var="nowDate" value="<%=System.currentTimeMillis()%>"></c:set>
		<c:choose>
		<c:when test="${nowDate - qcBill.qcPeriodEndTime.time > 0}">
			<td class="qcPeriodEndTime" style="color:red"   title='<fmt:formatDate value="${qcBill.qcPeriodEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>'><fmt:formatDate value="${qcBill.qcPeriodEndTime}" pattern="yyyy-MM-dd"/></td>
		</c:when>
		<c:otherwise>
			<td class="qcPeriodEndTime"   title='<fmt:formatDate value="${qcBill.qcPeriodEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/>'><fmt:formatDate value="${qcBill.qcPeriodEndTime}" pattern="yyyy-MM-dd"/></td>
		</c:otherwise>
		</c:choose>
		</c:if>
		<td class="distribTime" title='<fmt:formatDate value="${qcBill.distribTime}" pattern="yyyy-MM-dd HH:mm:ss"/>'><fmt:formatDate value="${qcBill.distribTime}" pattern="yyyy-MM-dd"/></td>
		<td class="finishTime" title='<fmt:formatDate value="${qcBill.finishTime}" pattern="yyyy-MM-dd HH:mm:ss"/>'><fmt:formatDate value="${qcBill.finishTime}" pattern="yyyy-MM-dd"/></td>
		<td class="remark shorten10">${qcBill.remark}</td>
		<td class="prdId">${qcBill.prdId}</td>
		<td class="groupDate"><fmt:formatDate value="${qcBill.groupDate}" pattern="yyyy-MM-dd"/></td>
	
	
		<td class="prdName shorten10">${qcBill.product.prdName}</td>
		<td class="cateName">${qcBill.product.cateName}</td>
		<td class="subCateName">${qcBill.product.subCateName}</td>
		<td class="brandName">${qcBill.product.brandName}</td>
		<td class="prdLineDestName">${qcBill.product.prdLineDestName}</td>
		<td class="businessUnitName">${qcBill.product.businessUnitName}</td>
	
		<td class="orderId">${qcBill.ordId}</td>
		<td class="departCity">${qcBill.orderBill.departCity}</td>
		<td class="prdAdultPrice">${qcBill.orderBill.prdAdultPrice}</td>
		<td class="adultNum">${qcBill.orderBill.adultNum}</td>
		<td class="childNum">${qcBill.orderBill.childNum}</td>
		<td class="departDate">${qcBill.orderBill.departDate}</td>
		<td class="returnDate">${qcBill.orderBill.returnDate}</td>
		<td class="prdManager">${qcBill.orderBill.prdManager}</td>
		<td class="producter">${qcBill.orderBill.producter}</td>
		<td class="salerManagerName">${qcBill.orderBill.salerManagerName}</td>
		<td class="salerName">${qcBill.orderBill.salerName}</td>
	
		<td class="cmpId">${qcBill.cmpId}</td>
		<td class="cmpLevel">${qcBill.complaintBill.cmpLevel}</td>
		<td class="comeFrom">${qcBill.complaintBill.comeFrom}</td>
		<td class="cmpAddTime"  title='<fmt:formatDate value="${qcBill.complaintBill.addTime}" pattern="yyyy-MM-dd  HH:mm:ss"/>'><fmt:formatDate value="${qcBill.complaintBill.addTime}" pattern="yyyy-MM-dd"/></td>
		<td class="cmpFinishTime" title='<fmt:formatDate value="${qcBill.complaintBill.finishTime}" pattern="yyyy-MM-dd HH:mm:ss"/>'><fmt:formatDate value="${qcBill.complaintBill.finishTime}" pattern="yyyy-MM-dd"/></td>
		<td class="dealPerson">${qcBill.complaintBill.dealPerson}</td>
		<td class="companyLose">${qcBill.complaintBill.companyLose}</td>
		<td class="operate">
				<a href="javascript:void(0)" onclick="backToProcessing(${qcBill.id})">返回质检中</a>
		</td>
	</tr>
	</c:forEach>
</table>
<%@include file="/WEB-INF/jsp/common/pager.jsp"%>
</form>
</body>
</html>
