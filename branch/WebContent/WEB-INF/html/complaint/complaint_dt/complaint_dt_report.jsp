<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>投诉处理数据报表</title>
<link rel="stylesheet"
	href="${CONFIG.res_url}css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript"
	src="${CONFIG.res_url}script/ztree/jquery.ztree.core-3.1.js"></script>

<script type="text/javascript">
	$(function() {
		showTab("tab_1");

		computePersonTotal();

	});

	function showTab(tabId) {
		changeTabStyle(tabId);
		var contentId = tabId.replace("tab", "content");
		showContent(contentId);
	}

	function changeTabStyle(tabId) {
		$("#tabs li").removeClass("menu_on");
		$("#" + tabId).addClass("menu_on");
	}

	function showContent(contentId) {
		$("#contents div").hide();
		$("#" + contentId).show();
	}

	function toggleData(img) {
		var altValue = img.alt;
		var lastTr = $(img).parent('td').next("td").children('table')
				.find("tr").last();
		var theory_pay = 0;
		var real_pay = 0;
		var suppiler_pay = 0;
		$.each(lastTr.find("td"),
				function(i, n) {
					var count = 0;
					var columnum = i;
					if (i > 0) {
						$.each(lastTr.siblings(), function(j, m) {
							var tempNum = parseFloat($(m).find("td").eq(
									columnum).text().trim()) || 0;
							tempNum = Math.floor(tempNum * 100) / 100;
							if (i == 3) {//理论赔付
								theory_pay += tempNum;
							}
							if (i == 4) {//实际赔付
								real_pay += tempNum;
							}
							if (i == 6) {//供应商承担
								suppiler_pay += tempNum;
							}
							if (i == 10) {//质量成本实际使用额
								if (tempNum < 0) {
									$(m).find("td").eq(columnum).css("color",
											"green");
								}
							}
							if (i == 11) {//实际收益
								if (tempNum < 0) {
									$(m).find("td").eq(columnum).css("color",
											"red");
								}
							}
							if (i == 12) {//理论收益
								if (tempNum < 0) {
									$(m).find("td").eq(columnum).css("color",
											"red");
								}
							}
							/* if (i == 12) {//加百分号
								if (tempNum == 0) {
									tempNum = ""
								} else {
									tempNum = tempNum + "%";
								}
								$(m).find("td").eq(columnum).text(tempNum)
							} */
							count += tempNum;
						})
						if (i==11||i==12){
							if (count < 0) {
								$(n).css("color", "red");
							}
						}
						count = Math.floor(count * 100) / 100;
						if (i != (lastTr.find("td").length - 1)) {
							$(n).text(count);
						} else {
							$(n).text("");
						}
					}
				})
		if (altValue == '展开') {
			$(img).parent('td').next("td").children('table').removeClass(
					"hidden");
			$(img).hide();
			$(img).next('img').show();
		} else {
			$(img).parent('td').next("td").children('table').addClass("hidden");
			$(img).hide();
			$(img).prev('img').show();
		}
	}

	function computePersonTotal() {
		$('.totalCount').each(
				function() {
					var personTotalCount = 0;
					$(this).siblings('.cmpCountItem').each(
							function() {
								personTotalCount = (personTotalCount - 0)
										+ ($(this).text() - 0);
							});
					if (personTotalCount != 0) {
						$(this).text(personTotalCount);
					}
				});
	}

	function openBox(dealName, statisticsFieldType, statisticsField) {
		var oldDealName = dealName;
		dealName = encodeURI(encodeURI(dealName));
		statisticsField = encodeURI(encodeURI(statisticsField));
		var url = "complaint_dt_report-getDealPayoutDetail?assignTimeBgn="
				+ $('#assignTimeBgn').val() + "&assignTimeEnd="
				+ $('#assignTimeEnd').val() + "&real_name=" + dealName;
		$('#cmpDetailListBoxIFrame').attr("src", url);
		openDialog(oldDealName + " " + $('#assignTimeBgn').val() + "至"
				+ $('#assignTimeEnd').val(), url);
	}
	
	function openTotalWindow(){
		var url = "complaint_dt_report-getTotalReport?assignTimeBgn="
			+ $('#assignTimeBgn').val() + "&assignTimeEnd="
			+ $('#assignTimeEnd').val();
		openDialog("二级部统计-" + $('#assignTimeBgn').val() + "至"
				+ $('#assignTimeEnd').val(), url);
	}
</script>

<style type="text/css">
.datatable td {
	text-align: center;
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
</HEAD>
<body>
	<form method="post" action="complaint_dt_report">
		<div class="pici_search pd5">
			<label class="mr10 ">完成时间：</label> <input type="text"
				id="assignTimeBgn" name="assignTimeBgn" value="${assignTimeBgn }"
				onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'assignTimeEnd\')}',minDate:'#F{$dp.$D(\'assignTimeEnd\',{M:-24})}'})"
				readOnly="readonly" class="Wdate">
			&nbsp;&nbsp;--&nbsp;&nbsp; <input type="text" id="assignTimeEnd"
				name="assignTimeEnd" value="${assignTimeEnd }"
				onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'assignTimeBgn\')}',maxDate:'#F{$dp.$D(\'assignTimeBgn\',{M:24})}'})"
				readOnly="readonly" class="Wdate"> <input type="submit"
				value="查询" />
		</div>
	</form>
	<div id="pici_tab" class="clear">
		<ul id="tabs">
			<li class="menu_on" onclick="showTab(this.id)" id="tab_1"><s
				class="rc-l"></s><s class="rc-r"></s><a href="javascript:void(0)">出游前服务部</a>
			</li>
			<li onclick="showTab(this.id)" id="tab_2"><s class="rc-l"></s><s
				class="rc-r"></s><a href="javascript:void(0)">售后服务部</a></li>
			<li onclick="showTab(this.id)" id="tab_3"><s class="rc-l"></s><s
				class="rc-r"></s><a href="javascript:void(0)">资深服务部</a></li>
			<li onclick="showTab(this.id);" id="tab_6"><s class="rc-l"></s><s
				class="rc-r"></s><a href="javascript:void(0)">分销客服部</a></li>
			<li onclick="showTab(this.id);" id="tab_7"><s class="rc-l"></s><s
				class="rc-r"></s><a href="javascript:void(0)">华东售后部</a></li>
			<li onclick="showTab(this.id);" id="tab_8"><s class="rc-l"></s><s
				class="rc-r"></s><a href="javascript:void(0)">华南售后部</a></li>
			<li onclick="showTab(this.id);" id="tab_9"><s class="rc-l"></s><s
				class="rc-r"></s><a href="javascript:void(0)">华北售后部</a></li>
			<li onclick="showTab(this.id);" id="tab_10"><s class="rc-l"></s><s
				class="rc-r"></s><a href="javascript:void(0)">交通客服部</a></li>
			<li onclick="showTab(this.id);" id="tab_11"><s class="rc-l"></s><s
				class="rc-r"></s><a href="javascript:void(0)">国内机票运营部</a></li>
			<li onclick="showTab(this.id);" id="tab_12"><s class="rc-l"></s><s
				class="rc-r"></s><a href="javascript:void(0)">酒店客服部</a></li>
			<li onclick="openTotalWindow()"><s class="rc-l"></s><s
				class="rc-r"></s><a href="javascript:void(0)">二级部统计</a></li>
		</ul>
	</div>
	<br />

	<table width="100%" class="datatable">
		<tr>
			<th>
				<table width="100%" class="datatable">
					<tr>
						<th width="7%">三级部门</th>
						<th width="90%">
							<table width="100%" class="datatable">
								<tr>
									<th width="5%">员工</th>
									<th width="5%">理赔单数</th>
									<th width="5%">填写理论赔付数</th>
									<th width="7%">公司理论赔付</th>
									<th width="7%">实际赔付总额</th>
									<th width="7%">分担总额</th>
									<th width="7%">供应商承担金额</th>
									<th width="7%">公司承担金额</th>
									<th width="7%">质量工具承担金额</th>
									<th width="7%">订单利润承担金额</th>
									<th width="7%">质量成本实际使用额</th>
									<th width="7%">实际收益</th>
									<th width="7%">理论收益</th>
									<!-- <th width="7%">超额赔付率</th> -->
									<th width="4%">订单号</th>
								</tr>
							</table>
						</th>
					</tr>
				</table>
			</th>
		</tr>
	</table>
	<div id="contents">
		<c:forEach items="${depUsersTreeList}" var="tree">
			<c:forEach items="${tree.root.childs }" var="dep2">
				<c:if
					test="${dep2.data=='出游前服务部'|| dep2.data=='售后服务部'||dep2.data=='资深服务部'
					||dep2.data == '分销客服部'||dep2.data=='华东售后部'||dep2.data=='华南售后部'||dep2.data=='华北售后部'
					||dep2.data == '交通客服部'||dep2.data=='国内机票运营部'||dep2.data=='酒店客服部'}">
					<c:choose>
						<c:when test="${dep2.data=='出游前服务部'}">
							<div id="content_1">
						</c:when>
						<c:when test="${dep2.data=='售后服务部'}">
							<div id="content_2">
						</c:when>
						<c:when test="${dep2.data=='资深服务部'}">
							<div id="content_3">
						</c:when>
						<c:when test="${dep2.data=='分销客服部'}">
							<div id="content_6">
						</c:when>
						<c:when test="${dep2.data=='华东售后部'}">
							<div id="content_7">
						</c:when>
						<c:when test="${dep2.data=='华南售后部'}">
							<div id="content_8">
						</c:when>
						<c:when test="${dep2.data=='华北售后部'}">
							<div id="content_9">
						</c:when>
						<c:when test="${dep2.data=='交通客服部'}">
							<div id="content_10">
						</c:when>
						<c:when test="${dep2.data=='国内机票运营部'}">
							<div id="content_11">
						</c:when>
						<c:when test="${dep2.data=='酒店客服部'}">
							<div id="content_12">
						</c:when>
					</c:choose>
					<table width="100%" class="datatable">
						<c:forEach items="${dep2.childs }" var="dep3">
							<c:if test="${dep3.data!='合计'}">
								<tr>
									<td width="7%" valign="top">${dep3.data}<img alt="展开"
										class="expand"
										src="${CONFIG.res_url}images/icon/tree/nolines_plus.gif"
										onclick="toggleData(this)" title="展开"> <img alt="收缩"
										class="shrink hidden"
										src="${CONFIG.res_url}images/icon/tree/nolines_minus.gif"
										onclick="toggleData(this)" title="收缩">
									</td>
									<td>
										<table width="100%" class="listtable hidden">
											<c:forEach items="${dep3.childs }" var="user">
												<tr>
													<td width="5%">${user.data}</td>
													<td width="5%" class="cmpCountItem"><c:if
															test="${reportData[user.data]['payout_num']!=0}">
																			${reportData[user.data]['payout_num']}
																</c:if></td>
													<td width="5%" class="cmpCountItem">${reportData[user.data]['theory_num']}</td>
													<td width="7%" class="cmpCountItem"><c:if
															test="${reportData[user.data]['theoryPayout']!=0}">
																			${reportData[user.data]['theoryPayout']}
																</c:if></td>
													<td width="7%" class="cmpCountItem"><c:if
															test="${reportData[user.data]['realPayout']!=0}">
																		${reportData[user.data]['realPayout']}
														</c:if></td>
													<td width="7%" class="cmpCountItem"><c:if
															test="${reportData[user.data]['share_total']!=0}">
																			${reportData[user.data]['share_total']}
																</c:if></td>
													<td width="7%" class="cmpCountItem"><c:if
															test="${reportData[user.data]['supplier_total']!=0}">
																			${reportData[user.data]['supplier_total']}
																</c:if></td>
													<td width="7%" class="cmpCountItem"><c:if
															test="${reportData[user.data]['company_total']!=0}">
																		${reportData[user.data]['company_total'] }
														</c:if></td>
													<td width="7%" class="cmpCountItem"><c:if
															test="${reportData[user.data]['quality_tool_total']!=0}">
																		${reportData[user.data]['quality_tool_total'] }
														</c:if></td>
													<td width="7%" class="cmpCountItem"><c:if
															test="${reportData[user.data]['order_gains']!=0}">
																		${reportData[user.data]['order_gains'] }
														</c:if></td>
													<td width="7%" class="cmpCountItem"><c:if
															test="${reportData[user.data]['savePayout']!=0}">
																			${reportData[user.data]['savePayout']}
															</c:if></td>
													<td width="7%" class="cmpCountItem"><c:if
															test="${reportData[user.data]['realGain']!=0}">
																			${reportData[user.data]['realGain']}
															</c:if></td>
													<td width="7%" class="cmpCountItem"><c:if
															test="${reportData[user.data]['theoryGain']!=0}">
																			${reportData[user.data]['theoryGain']}
															</c:if></td>		
													<%-- <td width="7%" class="cmpCountItem">
														${reportData[user.data]['above_scale'] }</td> --%>
													<td width="4%" class="cmpCountItem"><a
														href="javascript:void(0)"
														onclick="openBox('${user.data}')"> 查看 </a></td>
												</tr>
											</c:forEach>
										</table>
									</td>
								</tr>
							</c:if>
						</c:forEach>
					</table>
	</div>
	</c:if>
	</c:forEach>
	</c:forEach>
	</div>

</body>
</HTML>
