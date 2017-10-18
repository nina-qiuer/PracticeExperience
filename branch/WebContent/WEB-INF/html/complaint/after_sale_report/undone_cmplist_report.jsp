<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>售后部门分单统计报表</title>

<script type="text/javascript">

	$(function(){
		showTab("tab_1");
		
		fillTotalNums();
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
	
	function openBox(dealName,statisticsField){
		var oldDealName = dealName;
		dealName =encodeURI(encodeURI(dealName));
		var oldStatisticsField = statisticsField;
		statisticsField=encodeURI(encodeURI(statisticsField));
		var title = oldDealName+" "+oldStatisticsField;
		var url = "after_sale_report-getUnDoneCmpDetailList?dealName="+dealName+"&statisticsField="+statisticsField;
		openDialog(title,url);
	}
	
	/* 填充小组统计 */
	function fillTotalNums(){
		$('td[class*=total]').each(function(){
			var className = $(this).attr("class");
			var colNumber = className.substring("total".length,className.length); //需要统计的列号
			var siblings = $(this).parent().siblings(); //当前table中兄弟行
			var tds = $('td:eq('+colNumber+')',siblings); //拿到所有需要计算的单元格
			var total = 0;
			$(tds).each(function(){
				var data = $.trim($(this).text());
				if(data!=''){
					total+=parseInt(data);
				}
			});

			$(this).text(total);
		});
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
                                                            <s class="rc-l" ></s><s class= "rc-r" ></s><a href= "javascript:void(0)">票务服务部</a>
                                                </li>
                                                <li onclick="showTab(this.id);" id= "tab_5">
                                                            <s class="rc-l" ></s><s class= "rc-r" ></s><a href= "javascript:void(0)">酒店BU客服部</a>
                                                </li>
                                                <li onclick="showTab(this.id);" id= "tab_6">
                                                            <s class="rc-l" ></s><s class= "rc-r" ></s><a href= "javascript:void(0)">华东大区-售后部</a>
                                                </li>
                                                <li onclick="showTab(this.id);" id= "tab_7">
                                                            <s class="rc-l" ></s><s class= "rc-r" ></s><a href= "javascript:void(0)">华北大区-售后部</a>
                                                </li>
                                                <li onclick="showTab(this.id);" id= "tab_8">
                                                            <s class="rc-l" ></s><s class= "rc-r" ></s><a href= "javascript:void(0)">华南大区-客户服务部</a>
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
										<th width="10%">处理中</th>
										<th width="10%">处理中对客已达成</th>
										<th width="10%">处理中首呼超时</th>
										<th width="10%">处理中超时国内</th>
										<th width="10%">处理中超时出境</th>
										<th width="10%">已待结</th>
										<th width="10%">已待结超时国内</th>
										<th width="10%">已待结超时出境</th>
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
					<c:if test="${dep2.data=='售后服务部'|| dep2.data=='出游前服务部'||dep2.data=='资深服务部'||dep2.data=='票务服务部' || dep2.data == '客服部' || dep2.data == '售后部' || dep2.data =='客户服务部'}">
					<c:choose>
						<c:when test="${dep2.data=='售后服务部'}"><div id="content_1"></c:when>
						<c:when test="${dep2.data=='出游前服务部'}"><div id="content_2"></c:when>
						<c:when test="${dep2.data=='资深服务部'}"><div id="content_3"></c:when>
						<c:when test="${dep2.data=='票务服务部'}"><div id="content_4"></c:when>
						<c:when test="${dep2.data=='客服部' && tree.root.data=='酒店事业部'}"><div id="content_5"></c:when>
						<c:when test="${dep2.data=='售后部' && tree.root.data=='华东大区'}"><div id="content_6"></c:when>
						<c:when test="${dep2.data=='售后部' && tree.root.data=='华北大区'}"><div id="content_7"></c:when>
						<c:when test="${dep2.data=='客户服务部' &&  tree.root.data=='华南大区'}"><div id="content_8"></c:when>
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
														<td width="10%" class="cmpCountItem">
																<c:if test="${reportData[user.data]['处理中']!=0}">
																	<a href="javascript:void(0)" onclick="openBox('${user.data}','处理中')">
																			${reportData[user.data]['处理中']}
																	</a>
																</c:if>
														</td>
														<td width="10%" class="cmpCountItem">
														<c:if test="${reportData[user.data]['处理中对客已达成']!=0}">
															<a href="javascript:void(0)" onclick="openBox('${user.data}','处理中对客已达成')">
																		${reportData[user.data]['处理中对客已达成'] }
															</a>
														</c:if>
														</td>
														<td width="10%" class="cmpCountItem">
														<c:if test="${reportData[user.data]['处理中首呼超时']!=0}">
															<a href="javascript:void(0)" onclick="openBox('${user.data}','处理中首呼超时')">
																		${reportData[user.data]['处理中首呼超时'] }
															</a>
														</c:if>
														</td>
														<td width="10%" class="cmpCountItem">
														<c:if test="${reportData[user.data]['处理中超时国内']!=0}">
															<a href="javascript:void(0)" onclick="openBox('${user.data}','处理中超时国内')">
																		${reportData[user.data]['处理中超时国内']}
															</a>
														</c:if>
														</td>
														<td width="10%" class="cmpCountItem">
															<c:if test="${reportData[user.data]['处理中超时出境']!=0}">
																<a href="javascript:void(0)" onclick="openBox('${user.data}','处理中超时出境')">
																			${reportData[user.data]['处理中超时出境']}
																</a>
															</c:if>
														</td>
														<td width="10%" class="cmpCountItem">
															<c:if test="${reportData[user.data]['已待结']!=0}">
																<a href="javascript:void(0)" onclick="openBox('${user.data}','已待结')">
																			${reportData[user.data]['已待结'] }
																</a>
															</c:if>
														</td>
														<td width="10%" class="cmpCountItem">
															<c:if test="${reportData[user.data]['已待结超时国内']!=0}">
																<a href="javascript:void(0)" onclick="openBox('${user.data}','已待结超时国内')">
																			${reportData[user.data]['已待结超时国内'] }
																</a>
															</c:if>
														</td>
														<td width="10%" class="cmpCountItem">
															<c:if test="${reportData[user.data]['已待结超时出境']!=0}">
																<a href="javascript:void(0)" onclick="openBox('${user.data}','已待结超时出境')">
																			${reportData[user.data]['已待结超时出境'] }
																</a>
															</c:if>
														</td>
													</tr>
												</c:forEach>
													<tr>
														<td width="10%"><font color='green' ><strong>总计</strong></font></td>
														<td class="total1"></td>
														<td class="total2"></td>
														<td class="total3"></td>
														<td class="total4"></td>
														<td class="total5"></td>
														<td class="total6"></td>
														<td class="total7"></td>
														<td class="total8"></td>
													</tr>
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
