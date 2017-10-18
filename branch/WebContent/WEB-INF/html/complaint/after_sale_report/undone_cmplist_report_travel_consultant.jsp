<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>售后部门分单统计报表</title>
<script type="text/javascript" src="${CONFIG.res_url}script/DatePickerNew/WdatePicker.js" ></script>  
<link rel="stylesheet" href="${CONFIG.res_url}css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript"	src="${CONFIG.res_url}script/ztree/jquery.ztree.core-3.1.js"></script>
<link href="${CONFIG.res_url}css/easydialog.css" rel="stylesheet"	type="text/css" />
<script type="text/javascript"	src="${CONFIG.res_url}/script/easydialog.min.js"></script>

<script type="text/javascript">

	$(function(){
		showTab("tab_1");
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
		var url = "after_sale_report-getUnDoneCmpDetailList?dealName="+dealName+"&statisticsField="+statisticsField;
		$('#cmpDetailListBoxIFrame').attr("src",url);
		$('#title').text(oldDealName+" "+oldStatisticsField);
		easyDialog.open({container : 'cmpDetailListBox', overlay : false}); 
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
                                                            <s class="rc-l" ></s><s class= "rc-r" ></s><a href= "javascript:void(0)">华东一部</a>
                                                </li>
                                                <li onclick="showTab(this.id)" id= "tab_2">
                                                            <s class="rc-l" ></s><s class= "rc-r" ></s><a href= "javascript:void(0)">华东二部</a>
                                                </li>
                                                <li onclick="showTab(this.id)" id= "tab_3">
                                                            <s class="rc-l" ></s><s class= "rc-r" ></s><a href= "javascript:void(0)">华东三部</a>
                                                </li>
                                                <li onclick="showTab(this.id);" id= "tab_4">
                                                            <s class="rc-l" ></s><s class= "rc-r" ></s><a href= "javascript:void(0)">华东四部</a>
                                                </li>
                                                <li onclick="showTab(this.id);" id= "tab_5">
                                                            <s class="rc-l" ></s><s class= "rc-r" ></s><a href= "javascript:void(0)">华东五部</a>
                                                </li>

                                                <li class="menu_on" onclick="showTab(this.id)" id="tab_6" >
                                                            <s class="rc-l" ></s><s class= "rc-r" ></s><a href= "javascript:void(0)">华北一部</a>
                                                </li>
                                                <li onclick="showTab(this.id)" id= "tab_7">
                                                            <s class="rc-l" ></s><s class= "rc-r" ></s><a href= "javascript:void(0)">华北二部</a>
                                                </li>
                                                <li onclick="showTab(this.id)" id= "tab_8">
                                                            <s class="rc-l" ></s><s class= "rc-r" ></s><a href= "javascript:void(0)">华北三部</a>
                                                </li>
                                                <li onclick="showTab(this.id);" id= "tab_9">
                                                            <s class="rc-l" ></s><s class= "rc-r" ></s><a href= "javascript:void(0)">华北四部</a>
                                                </li>
                                                <li onclick="showTab(this.id);" id= "tab_10">
                                                            <s class="rc-l" ></s><s class= "rc-r" ></s><a href= "javascript:void(0)">华北五部</a>
                                                </li>

                                                <li class="menu_on" onclick="showTab(this.id)" id="tab_11" >
                                                            <s class="rc-l" ></s><s class= "rc-r" ></s><a href= "javascript:void(0)">西南一部</a>
                                                </li>
                                                <li onclick="showTab(this.id)" id= "tab_12">
                                                            <s class="rc-l" ></s><s class= "rc-r" ></s><a href= "javascript:void(0)">西南二部</a>
                                                </li>
                                                <li onclick="showTab(this.id)" id= "tab_13">
                                                            <s class="rc-l" ></s><s class= "rc-r" ></s><a href= "javascript:void(0)">西南三部</a>
                                                </li>
                                                <li onclick="showTab(this.id);" id= "tab_14">
                                                            <s class="rc-l" ></s><s class= "rc-r" ></s><a href= "javascript:void(0)">西南四部</a>
                                                </li>
                                                <li onclick="showTab(this.id);" id= "tab_15">
                                                            <s class="rc-l" ></s><s class= "rc-r" ></s><a href= "javascript:void(0)">西南五部</a>
                                                </li>
                                                
                                                <li onclick="showTab(this.id);" id= "tab_16">
                                                            <s class="rc-l" ></s><s class= "rc-r" ></s><a href= "javascript:void(0)">邮轮客服部</a>
                                                </li>
                                                <li onclick="showTab(this.id);" id= "tab_17">
                                                            <s class="rc-l" ></s><s class= "rc-r" ></s><a href= "javascript:void(0)">华南客服部</a>
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
					<c:if test="${fn:startsWith(dep2.data,'华') || fn:startsWith(dep2.data,'西') || dep2.data == '邮轮客服部'}">
						<c:choose>
							<c:when test="${dep2.data=='华东一部'}"><div id="content_1"></c:when>
							<c:when test="${dep2.data=='华东二部'}"><div id="content_2"></c:when>
							<c:when test="${dep2.data=='华东三部'}"><div id="content_3"></c:when>
							<c:when test="${dep2.data=='华东四部'}"><div id="content_4"></c:when>
							<c:when test="${dep2.data=='华东五部'}"><div id="content_5"></c:when>
	
							<c:when test="${dep2.data=='华北一部'}"><div id="content_6"></c:when>
							<c:when test="${dep2.data=='华北二部'}"><div id="content_7"></c:when>
							<c:when test="${dep2.data=='华北三部'}"><div id="content_8"></c:when>
							<c:when test="${dep2.data=='华北四部'}"><div id="content_9"></c:when>
							<c:when test="${dep2.data=='华北五部'}"><div id="content_10"></c:when>
	
							<c:when test="${dep2.data=='西南一部'}"><div id="content_11"></c:when>
							<c:when test="${dep2.data=='西南二部'}"><div id="content_12"></c:when>
							<c:when test="${dep2.data=='西南三部'}"><div id="content_13"></c:when>
							<c:when test="${dep2.data=='西南四部'}"><div id="content_14"></c:when>
							<c:when test="${dep2.data=='西南五部'}"><div id="content_15"></c:when>
	
							<c:when test="${dep2.data=='邮轮客服部'}"><div id="content_16"></c:when>
							<c:when test="${dep2.data=='华南客服部'}"><div id="content_17"></c:when>
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
	
<div id="cmpDetailListBox" style="display: none; width: 1000px;"
			class="easyDialog_wrapper">
			<div class="easyDialog_content">
				<h4 class="easyDialog_title" id="easyDialogTitle">
					<a href="javascript:void(0)" title="关闭窗口" class="close_btn"
						id="closeBtn">×</a><span id="title"></span>
				</h4>
				<div>
					<iframe id="cmpDetailListBoxIFrame" src="" frameborder="0" width="1000"
						height="600"></iframe>
				</div>
			</div>
</div>
</body>
</HTML>
