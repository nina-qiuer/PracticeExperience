<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>售后部门分单统计报表</title>
<link rel="stylesheet" href="${CONFIG.res_url}css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript"	src="${CONFIG.res_url}script/ztree/jquery.ztree.core-3.1.js"></script>

<script type="text/javascript">

	$(function(){
		showTab("tab_1");

		computePersonTotal();
	});

	function showTab(tabId) {
		changeTabStyle(tabId);
		var contentId = tabId.replace("tab","content");
		showContent(contentId);
	}

	function changeTabStyle(tabId) {
		$("#tabs li").removeClass("menu_on" );
		$("#"+tabId).addClass("menu_on" );
	}

	function showContent(contentId) {
		$("#contents div").hide();
		$("#"+contentId).show();
	}

	function toggleData(img){
		var altValue = img.alt;
		if(altValue=='展开'){
			$(img).parent('td').next("td").children('table').removeClass("hidden");
			$(img).hide();
			$(img).next('img').show();
		}else {
			$(img).parent('td').next("td").children('table').addClass("hidden");
			$(img).hide();
			$(img).prev('img').show();
		}
	}

	function computePersonTotal(){
		$('.totalCount').each(function(){
			var personTotalCount = 0;
			$(this).siblings('.cmpCountItem').each(function(){
				personTotalCount = (personTotalCount-0)+($(this).text()-0);
			});
			if(personTotalCount!=0){
				$(this).text(personTotalCount);
			}
		});
	}

	function openBox(dealName,statisticsFieldType,statisticsField){
		var oldDealName = dealName;
		dealName =encodeURI(encodeURI(dealName));
		statisticsField=encodeURI(encodeURI(statisticsField));
		var url = "after_sale_report-getCmpDetailList?assignTimeBgn="+$('#assignTimeBgn').val()+"&assignTimeEnd="+$('#assignTimeEnd').val()+"&dealName="+dealName+"&statisticsFieldType="+statisticsFieldType+"&statisticsField="+statisticsField;
		$('#cmpDetailListBoxIFrame').attr("src",url);
		openDialog(oldDealName+" "+$('#assignTimeBgn').val()+"至"+$('#assignTimeEnd').val(),url);
	}
</script>

<style type="text/css">
.datatable td{text-align:center;}
 .hidden{	display:none;}

img{
	vertical-align:middle;
}

img:hover{
	cursor: pointer;
}

</style>
</HEAD>
<body>
<form method="post" action="after_sale_report">
<div class="pici_search pd5">
	<label class="mr10 ">分单时间：</label>
	<input  type="text"  id="assignTimeBgn" name="assignTimeBgn" value="${assignTimeBgn }"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'assignTimeEnd\')}',minDate:'#F{$dp.$D(\'assignTimeEnd\',{M:-15})}'})" readOnly="readonly" class="Wdate">
	&nbsp;&nbsp;--&nbsp;&nbsp;
	<input  type="text"  id="assignTimeEnd" name="assignTimeEnd" value="${assignTimeEnd }"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'assignTimeBgn\')}',maxDate:'#F{$dp.$D(\'assignTimeBgn\',{M:15})}'})" readOnly="readonly" class="Wdate">
	<input type="submit"  value="查询"/>
</div>
</form>
<div id="pici_tab" class="clear" >
                                    <ul  id="tabs">
                                                <li class="menu_on" onclick="showTab(this.id)" id="tab_1" >
                                                            <s class="rc-l" ></s><s class= "rc-r" ></s><a href= "javascript:void(0)">售后服务部</a>
                                                </li>
                                                <li onclick="showTab(this.id)" id= "tab_2">
                                                            <s class="rc-l" ></s><s class= "rc-r" ></s><a href= "javascript:void(0)">出游前服务部</a>
                                                </li>
                                                <li onclick="showTab(this.id)" id= "tab_3">
                                                            <s class="rc-l" ></s><s class= "rc-r" ></s><a href= "javascript:void(0)">资深服务部</a>
                                                </li>
                                                <li onclick="showTab(this.id);" id= "tab_4">
                                                            <s class="rc-l" ></s><s class= "rc-r" ></s><a href= "javascript:void(0)">国内机票运营部</a>
                                                </li>
                                                <li onclick="showTab(this.id);" id= "tab_9">
                                                            <s class="rc-l" ></s><s class= "rc-r" ></s><a href= "javascript:void(0)">交通客服部</a>
                                                </li>
                                                <li onclick="showTab(this.id);" id= "tab_10">
                                                            <s class="rc-l" ></s><s class= "rc-r" ></s><a href= "javascript:void(0)">酒店客服部</a>
                                                </li>
												<li onclick="showTab(this.id);" id= "tab_5">
                                                            <s class="rc-l" ></s><s class= "rc-r" ></s><a href= "javascript:void(0)">综合服务部</a>
                                                </li>
                                                <li onclick="showTab(this.id);" id= "tab_6">
                                                            <s class="rc-l" ></s><s class= "rc-r" ></s><a href= "javascript:void(0)">华东售后服务部</a>
                                                </li>
                                                <li onclick="showTab(this.id);" id= "tab_7">
                                                            <s class="rc-l" ></s><s class= "rc-r" ></s><a href= "javascript:void(0)">华北售后服务部</a>
                                                </li>
                                                <li onclick="showTab(this.id);" id= "tab_8">
                                                            <s class="rc-l" ></s><s class= "rc-r" ></s><a href= "javascript:void(0)">华南售后服务部</a>
                                                </li>
                                    </ul>
</div>
<br/>

			<table width="100%" class="datatable">
			<tr>
				<th>
					<table width="100%" class="datatable">
						<tr>
							<th width="10%">三级部门</th>
							<th width="90%">
								<table  width="100%" class="datatable">
									<tr>
										<th width="10%">员工</th>
										<th width="7%">有订单<br/>投诉总量</th>
										<th width="7%">出境长</th>
										<th width="7%">出境短</th>
										<th width="7%">国内长</th>
										<th width="7%">周边</th>
										<th width="7%">门票</th>
										<th width="7%">酒店</th>
										<th width="7%">签证</th>
										<th width="7%">机票</th>
										<th width="7%">火车票</th>
										<th width="7%">其他</th>
										<th width="7%">无订单</th>
										<th width="7%">赔款单</th>
									</tr>
								</table>
							</th>
						</tr>
					</table>
				</th>
			</tr>
		</table>

<div id="contents">
	<c:forEach items = "${depUsersTreeList}" var = "tree">
			<c:forEach items="${tree.root.childs }"  var="dep2">
					<c:if test="${dep2.data=='售后服务部'|| dep2.data=='出游前服务部'||dep2.data=='资深服务部'||dep2.data=='国内机票运营部' || dep2.data == '综合服务部'
					|| dep2.data == '华东售后服务部' || dep2.data =='华北售后服务部' || dep2.data =='华南售后服务部' || dep2.data=='交通客服部' || dep2.data =='酒店客服部'}">
					<c:choose>
						<c:when test="${dep2.data=='售后服务部'}"><div id="content_1"></c:when>
						<c:when test="${dep2.data=='出游前服务部'}"><div id="content_2"></c:when>
						<c:when test="${dep2.data=='资深服务部'}"><div id="content_3"></c:when>
						<c:when test="${dep2.data=='国内机票运营部'}"><div id="content_4"></c:when>
						<c:when test="${dep2.data=='交通客服部'}"><div id="content_9"></c:when>
						<c:when test="${dep2.data=='酒店客服部'}"><div id="content_10"></c:when>
						<c:when test="${dep2.data=='综合服务部'}"><div id="content_5"></c:when>
						<c:when test="${dep2.data=='华东售后服务部'}"><div id="content_6"></c:when>
						<c:when test="${dep2.data=='华北售后服务部'}"><div id="content_7"></c:when>
						<c:when test="${dep2.data=='华南售后服务部'}"><div id="content_8"></c:when>
					</c:choose>
					<table width="100%" class="datatable">
							<c:forEach items="${dep2.childs }"  var="dep3">
									<tr>
										<td width="10%" valign="top" >${dep3.data}
											<img alt="展开" class="expand" src="${CONFIG.res_url}images/icon/tree/nolines_plus.gif"  onclick="toggleData(this)" title="展开">
											<img alt="收缩" class="shrink hidden"  src="${CONFIG.res_url}images/icon/tree/nolines_minus.gif" onclick="toggleData(this)" title="收缩">
										</td>
										<td>
											<table width="100%" class="listtable hidden">
												<c:forEach items="${dep3.childs }"  var="user">
													<tr>
														<td width="10%">${user.data }</td>
														<td width="7%" class="totalCount"></td>
														<td width="7%" class="cmpCountItem">
																<c:if test="${reportData[user.data]['出境长线']!=0}">
																	<a href="javascript:void(0)" onclick="openBox('${user.data}','routeType','出境长线')">
																			${reportData[user.data]['出境长线']}
																	</a>
																</c:if>
														</td>
														<td width="7%" class="cmpCountItem">
														<c:if test="${reportData[user.data]['出境短线']!=0}">
															<a href="javascript:void(0)" onclick="openBox('${user.data}','routeType','出境短线')">
																		${reportData[user.data]['出境短线'] }
															</a>
														</c:if>
														</td>
														<td width="7%" class="cmpCountItem">
														<c:if test="${reportData[user.data]['国内长线']!=0}">
															<a href="javascript:void(0)" onclick="openBox('${user.data}','routeType','国内长线')">
																		${reportData[user.data]['国内长线'] }
															</a>
														</c:if>
														</td>
														<td width="7%" class="cmpCountItem">
														<c:if test="${reportData[user.data]['周边']!=0}">
															<a href="javascript:void(0)" onclick="openBox('${user.data}','routeType','周边')">
																		${reportData[user.data]['周边']}
															</a>
														</c:if>
														</td>
														<td width="7%" class="cmpCountItem">
															<c:if test="${reportData[user.data]['门票']!=0}">
																<a href="javascript:void(0)" onclick="openBox('${user.data}','singleResource','门票')">
																			${reportData[user.data]['门票']}
																</a>
															</c:if>
														</td>
														<td width="7%" class="cmpCountItem">
															<a href="javascript:void(0)" onclick="openBox('${user.data}','singleResource','酒店')">
																		${reportData[user.data]['酒店'] }
															</a>
														</td>
														<td width="7%" class="cmpCountItem">
															<c:if test="${reportData[user.data]['签证']!=0}">
																<a href="javascript:void(0)" onclick="openBox('${user.data}','singleResource','签证')">
																			${reportData[user.data]['签证'] }
																</a>
															</c:if>
														</td>
														<td width="7%" class="cmpCountItem">
															<c:if test="${reportData[user.data]['机票']!=0}">
																<a href="javascript:void(0)" onclick="openBox('${user.data}','singleResource','机票')">
																			${reportData[user.data]['机票'] }
																</a>
															</c:if>
														</td>
														<td width="7%" class="cmpCountItem">
															<c:if test="${reportData[user.data]['火车票']!=0}">
																<a href="javascript:void(0)" onclick="openBox('${user.data}','singleResource','火车票')">
																			${reportData[user.data]['火车票'] }
																</a>
															</c:if>
														</td>
														<td width="7%" class="cmpCountItem">
														<c:if test="${reportData[user.data]['其他']!=0}">
																<a href="javascript:void(0)" onclick="openBox('${user.data}','otherType','其他')">
																			${reportData[user.data]['其他'] }
																</a>
														</c:if>
														</td>
														<td width="7%">
														<c:if test="${reportData[user.data]['无订单']!=0}">
																<a href="javascript:void(0)" onclick="openBox('${user.data}','noneOrder','无订单')">
																			${reportData[user.data]['无订单'] }
																</a>
														</c:if>
														</td>
														<td width="7%">
														<c:if test="${reportData[user.data]['赔款单']!=0}">
																<a href="javascript:void(0)" onclick="openBox('${user.data}','reparations','赔款单')">
																			${reportData[user.data]['赔款单'] }
																</a>
														</c:if>
														</td>
													</tr>
												</c:forEach>
											</table>
										</td>
									</tr>
							</c:forEach>
						</table>
					</div>
					</c:if>
			</c:forEach>
		</c:forEach>
</div>

</body>
</HTML>
