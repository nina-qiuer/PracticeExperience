<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp"%>
<style type="text/css">
.listtable td {
	text-align: center;
	background: none
}

.listtable th {
	background-color: #4C68A2;
	color: white;
	font-weight: bold
}

.hidden {
	display: none;
}

img {
	vertical-align: middle;
}

img:hover {
	cursor: pointer;
}
</style>
<script type="text/javascript">
	$(function() {

	})

	//根据部门获取数据
	var getLaunchCountData = function() {
		$.ajax({
			type : "POST",
			url : "complaint_report-getLanchCountReportData",
			data : {
				"finishTimeBgn" : $("#finishTimeBgn").val(),
				"finishTimeEnd" : $("#finishTimeEnd").val(),
				"routeType":$("#routeType").val()
			},
			async : false,
			success : function(data) {
				appendHtml(data.retObj);
			}
		});
	}

	var appendHtml = function(data) {
		var html = [];
		var trString = "<tr><th>部门</th><th>发起量</th><th>占比</th></tr>";
		html.push(trString);
		$.each(data, function(i, n) {
			var firstDep_level=i;
			trString = '<tr dept_level="'+i+'"><td style="text-align:left;background-color:#9EB6E4;">'
					+ (n.childs.length>0?'<img src="${CONFIG.res_url}images/icon/tree/nolines_plus.gif">':'') + n.data.dep_name + '</td>'
					+ '<td style="background-color:#9EB6E4">'
					+ n.data.countNum + '</td>'
					+ '<td style="background-color:#9EB6E4">' + n.data.countPercent
					+ '</td></tr>';
					$.each(n.childs, function(i, n) {
						var secondDep_level=i;
						trString += '<tr style="display:none" dept_level="'+firstDep_level+'-'+i+'"><td style="text-align:left;background-color:#B8C8EE;">'
								+ '&nbsp;&nbsp;&nbsp;&nbsp;'
								+ (n.childs.length>0?'<img src="${CONFIG.res_url}images/icon/tree/nolines_plus.gif">':'') + n.data.dep_name + '</td>'
								+ '<td style="background-color:#B8C8EE">'
								+ n.data.countNum + '</td>'
								+ '<td style="background-color:#B8C8EE">' + n.data.countPercent
								+ '</td></tr>';
								$.each(n.childs, function(i, n) {
									trString += '<tr style="display:none" dept_level="'+firstDep_level+'-'+secondDep_level+'-'+i+'"><td style="text-align:left;background-color:#E6EEFC;">'
											+ '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'
											+ (n.childs.length>0?'<img src="${CONFIG.res_url}images/icon/tree/nolines_plus.gif">':'') + n.data.dep_name + '</td>'
											+ '<td style="background-color:#E6EEFC">'
											+ n.data.countNum + '</td>'
											+ '<td style="background-color:#E6EEFC">' + n.data.countPercent
											+ '</td></tr>';
								})
					})
			html.push(trString);
		})
		$(".listtable").empty().append(html.join(""));
		$(".listtable tr td img").unbind("click").bind("click",showChildHtml);
	}
	
	var showChildHtml=function(){
		var dept_level=$(this).closest("tr").attr("dept_level");
		if($(this).hasClass("active")){
			$(this).removeClass("active");
			$(this).attr("src","${CONFIG.res_url}images/icon/tree/nolines_plus.gif");
			$('tr[dept_level^="'+dept_level+'-'+'"]').hide();
		}else{
			$(this).addClass("active");
			$(this).attr("src","${CONFIG.res_url}images/icon/tree/nolines_minus.gif");
			$('tr[dept_level^="'+dept_level+'-'+'"]').show();
			$.each($('tr[dept_level^="'+dept_level+'-'+'"]'),function(i,n){
				if(!$(n).find("img").hasClass("active")){
					var temp_dept_level=$(this).attr("dept_level");
					$('tr[dept_level^="'+temp_dept_level+'-'+'"]').hide();
				}
			})
		}
	}
</script>
</HEAD>
<BODY>
	<div class="top_crumbs">
		您当前所在的位置：投诉质检系统&gt;&gt;<span class="top_crumbs_txt">发起投诉部门统计报表</span>
	</div>
	<div class="pici_search pd5 mb10">
			<label class="mr10 ">产品品类： <select class="mr10" id="routeType"
				name="routeType">
					<option value="">全部</option>
					<option value="跟团游"
						<c:if test="${'跟团游'.equals(routeType)}">selected</c:if>>跟团游</option>
					<option value="自助游"
						<c:if test="${'自助游'.equals(routeType)}">selected</c:if>>自助游</option>
					<option value="团队游"
						<c:if test="${'团队游'.equals(routeType)}">selected</c:if>>团队游</option>
					<option value="邮轮"
						<c:if test="${'邮轮'.equals(routeType)}">selected</c:if>>邮轮</option>
					<option value="自驾游 "
						<c:if test="${'自驾游'.equals(routeType)}">selected</c:if>>自驾游</option>
					<option value="门票"
						<c:if test="${'门票'.equals(routeType)}">selected</c:if>>门票</option>
					<option value="酒店"
						<c:if test="${'酒店'.equals(routeType)}">selected</c:if>>酒店</option>
					<option value="火车票"
						<c:if test="${'火车票'.equals(routeType)}">selected</c:if>>火车票</option>
					<option value="汽车票"
						<c:if test="${'汽车票'.equals(routeType)}">selected</c:if>>汽车票</option>
					<option value="签证"
						<c:if test="${'签证'.equals(routeType)}">selected</c:if>>签证</option>
					<option value="用车服务"
						<c:if test="${'用车服务'.equals(routeType)}">selected</c:if>>用车服务</option>
					<option value="票务"
						<c:if test="${'票务'.equals(routeType)}">selected</c:if>>票务</option>
					<option value="目的地服务"
						<c:if test="${'目的地服务'.equals(routeType)}">selected</c:if>>目的地服务</option>
					<option value="通信"
						<c:if test="${'通信'.equals(routeType)}">selected</c:if>>通信</option>
			</select>
		<label class="mr10">统计时间： <input id="finishTimeBgn"
			type="text" name="finishTimeBgn" value="${finishTimeBgn }"
			onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'finishTimeEnd\')}'})"
			readOnly="readonly" /> 至 <input id="finishTimeEnd" type="text"
			name="finishTimeEnd" value="${finishTimeEnd }"
			onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'finishTimeBgn\')}'})"
			readOnly="readonly" />
		</label> <label class="mr10"> <input type="submit" size="10"
			value="查询" class="blue" onclick="getLaunchCountData()" />
		</label>
	</div>
	<table width="100%" class="listtable">
		<tr><th>部门</th><th>发起量</th><th>占比</th></tr>
	</table>
</BODY>