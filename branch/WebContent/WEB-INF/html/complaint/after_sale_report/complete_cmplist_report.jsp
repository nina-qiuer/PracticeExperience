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

    var dealDepart;

	$(function(){
		showTab("tab_1");
		//机票组特殊
		airPlane();
	});

	//机票组特殊
	function airPlane(){
		$("#content_4,#content_9").find(".needAdd").show();
		$("#content_4,#content_9").find(".needRemove").remove();
		$.each($("#content_4,#content_9").find(".countAll"),function(i,n){
			var sum=0;
			$.each($(this).siblings("td:gt(0)"),function(j,m){
				var tempcount=parseInt($(m).text())||0;
				sum+=tempcount;
			})
			//总计
			sum>0?$(this).text(sum):"";
		})
		var sumTr='<tr class="countTr"><td width="7.78%" class="nosb">总计</td><td width="7.78%"></td><td width="7.78%">'+
		'</td><td width="7.78%"></td><td width="7.78%"></td><td width="10%"></td></tr>';
		$("#content_4,#content_9").find(".listtable tbody").append(sumTr);
		$.each($("#content_4,#content_9").find(".countTr td:gt(0)").not(".nosb"),function(i,n){
			var count=0
			var countTds;
			if(i%5==0){
				countTds=$(n).closest("table").find("td.jipiao");
			}else if(i%5==1){
				countTds=$(n).closest("table").find("td.huochepiao");
			}else if(i%5==2){
				countTds=$(n).closest("table").find("td.qichepiao");
			}else if(i%5==3){
				countTds=$(n).closest("table").find("td.yongche");
			}
			//合计的总计
			if(i%5==4){
				countTds=$(n).closest("table").find("td.countAll");
				$.each(countTds,function(j,m){
					tempNum=parseInt($(m).text())||0;
					count+=tempNum;
				})
			}else{
				$.each(countTds,function(j,m){
					tempNum=parseInt($(m).find("a").text())||0;
					count+=tempNum;
				})
			}
			if(count>0){
				$(n).text(count);
			}
		})
	}
	
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
		if(contentId=="content_4"||contentId=="content_9"){
			$(".datatable .datatable .datatable").find(".needRemove").hide();
			$(".datatable .datatable .datatable").find(".needAdd").show();
		}else{
			$(".datatable .datatable .datatable").find(".needRemove").show();
			$(".datatable .datatable .datatable").find(".needAdd").hide();
		}
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

	function openBox(dealName,statisticsFieldType,statisticsField){
		var oldDealName = dealName;
		dealName =encodeURI(encodeURI(dealName));
		statisticsField=encodeURI(encodeURI(statisticsField));
		var url = "complaint_report-getOrderList?finishTimeBgn="+$('#finishTimeBgn').val()+"&finishTimeEnd="+$('#finishTimeEnd').val()+"&dealName="+dealName+"&statisticsFieldType="+statisticsFieldType+"&statisticsField="+statisticsField;
		$('#orderListBoxIFrame').attr("src",url);
		$('#title').text(oldDealName+" "+$('#finishTimeBgn').val()+"至"+$('#finishTimeEnd').val());
		easyDialog.open({container : 'orderListBox', overlay : false});
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
<form method="post" action="complaint_report-completeCmpList">
<div class="pici_search pd5">
	<label class="mr10 ">统计时间：</label>
	<input  type="text"  id="finishTimeBgn" name="finishTimeBgn" value="${finishTimeBgn }"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'finishTimeEnd\')}',minDate:'#F{$dp.$D(\'finishTimeEnd\',{M:-15})}'})" readOnly="readonly" class="Wdate">
	&nbsp;&nbsp;--&nbsp;&nbsp;
	<input  type="text"  id="finishTimeEnd" name="finishTimeEnd" value="${finishTimeEnd }"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'finishTimeBgn\')}',maxDate:'#F{$dp.$D(\'finishTimeBgn\',{M:15})}'})" readOnly="readonly" class="Wdate">
	<input type="submit"  value="查询"/>
</div>
</form>
<div id="pici_tab" class="clear" >
                                    <ul  id="tabs">
                                                <li class="menu_on" onclick="showTab(this.id)" id="tab_1" >
                                                            <s class="rc-l" ></s><s class= "rc-r" ></s><a href= "javascript:void(0)">售后服务部</a>
                                                </li>
                                                <li onclick="showTab(this.id)" id= "tab_2">
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
                                                <li onclick="showTab(this.id);" id= "tab_6">
                                                            <s class="rc-l" ></s><s class= "rc-r" ></s><a href= "javascript:void(0)">华东售后服务部</a>
                                                </li>
                                                <li onclick="showTab(this.id);" id= "tab_7">
                                                            <s class="rc-l" ></s><s class= "rc-r" ></s><a href= "javascript:void(0)">华北售后服务部</a>
                                                </li>
                                                <li onclick="showTab(this.id);" id= "tab_8">
                                                            <s class="rc-l" ></s><s class= "rc-r" ></s><a href= "javascript:void(0)">华南售后服务部</a>
                                                </li>
                                                <li onclick="showTab(this.id);" id= "tab_20">
                                                            <s class="rc-l" ></s><s class= "rc-r" ></s><a href= "javascript:void(0)">综合服务部</a>
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
										<th width="7.78%">员工</th>
										<th width="7.78%" class="needRemove">出境长</th>
										<th width="7.78%" class="needRemove">出境短</th>
										<th width="7.78%" class="needRemove">国内长</th>
										<th width="7.78%" class="needRemove">周边</th>
										<th width="7.78%" class="needRemove">门票</th>
										<th width="7.78%" class="needRemove">酒店</th>
										<th width="7.78%" class="needRemove">签证</th>
										<th width="7.78%">机票</th>
										<th width="7.78%">火车票</th>
										<th width="7.78%" class="needAdd" style="display:none;">汽车票</th>
										<th width="7.78%" class="needAdd" style="display:none;">用车</th>
										<th width="10%" class="needRemove">其他</th>
										<th width="10%" class="needAdd" style="display:none;">合计</th>
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
					<c:if test="${dep2.data=='售后服务部' || dep2.data=='资深服务部'||dep2.data=='国内机票运营部' || dep2.data =='综合服务部' 
					|| dep2.data == '华东售后服务部' || dep2.data =='华北售后服务部' || dep2.data =='华南售后服务部' || dep2.data =='交通客服部' || dep2.data =='酒店客服部'}">
					<c:choose>
						<c:when test="${dep2.data=='售后服务部'}"><div id="content_1"></c:when>
						<c:when test="${dep2.data=='资深服务部'}"><div id="content_2"></c:when>
						<c:when test="${dep2.data=='国内机票运营部'}"><div id="content_4"></c:when>
						<c:when test="${dep2.data=='交通客服部'}"><div id="content_9"></c:when>
						<c:when test="${dep2.data=='酒店客服部'}"><div id="content_10"></c:when>
						<c:when test="${dep2.data=='华东售后服务部'}"><div id="content_6"></c:when>
						<c:when test="${dep2.data=='华北售后服务部'}"><div id="content_7"></c:when>
						<c:when test="${dep2.data=='华南售后服务部'}"><div id="content_8"></c:when>
						<c:when test="${dep2.data=='综合服务部'}"><div id="content_20"></c:when>
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
														<td width="7.78%">${user.data }</td>
														<td width="7.78%" class="needRemove">
																<c:if test="${reportData[user.data]['出境长线']!=0}">
																	<a href="javascript:void(0)" onclick="openBox('${user.data}','dest_category_name','出境长线')">
																			${reportData[user.data]['出境长线']}
																	</a>
																</c:if>
														</td>
														<td width="7.78%" class="needRemove">
														<c:if test="${reportData[user.data]['出境短线']!=0}">
															<a href="javascript:void(0)" onclick="openBox('${user.data}','dest_category_name','出境短线')">
																		${reportData[user.data]['出境短线'] }
															</a>
														</c:if>
														</td>
														<td width="7.78%" class="needRemove">
														<c:if test="${reportData[user.data]['国内长线']!=0}">
															<a href="javascript:void(0)" onclick="openBox('${user.data}','dest_category_name','国内长线')">
																		${reportData[user.data]['国内长线'] }
															</a>
														</c:if>
														</td>
														<td width="7.78%" class="needRemove">
														<c:if test="${reportData[user.data]['周边']!=0}">
															<a href="javascript:void(0)" onclick="openBox('${user.data}','dest_category_name','周边')">
																		${reportData[user.data]['周边']}
															</a>
														</c:if>
														</td>
														<td width="7.78%" class="needRemove">
															<c:if test="${reportData[user.data]['门票']!=0}">
																<a href="javascript:void(0)" onclick="openBox('${user.data}','route_type','门票')">
																			${reportData[user.data]['门票']}
																</a>
															</c:if>
														</td>
														<td width="7.78%" class="needRemove">
															<a href="javascript:void(0)" onclick="openBox('${user.data}','route_type','酒店')">
																		${reportData[user.data]['酒店'] }
															</a>
														</td>
														<td width="7.78%" class="needRemove">
															<c:if test="${reportData[user.data]['签证']!=0}">
																<a href="javascript:void(0)" onclick="openBox('${user.data}','route_type','签证')">
																			${reportData[user.data]['签证'] }
																</a>
															</c:if>
														</td>
														<td width="7.78%" class="jipiao">
															<c:if test="${reportData[user.data]['机票']!=0}">
																<a href="javascript:void(0)" onclick="openBox('${user.data}','route_type','机票')">
																			${reportData[user.data]['机票'] }
																</a>
															</c:if>
														</td>
														<td width="7.78%" class="huochepiao">
															<c:if test="${reportData[user.data]['火车票']!=0}">
																<a href="javascript:void(0)" onclick="openBox('${user.data}','route_type','火车票')">
																			${reportData[user.data]['火车票'] }
																</a>
															</c:if>
														</td>
														<td width="7.78%" class="needAdd qichepiao" style="display:none;">
															<c:if test="${reportData[user.data]['汽车票']!=0}">
																<a href="javascript:void(0)" onclick="openBox('${user.data}','route_type','汽车票')">
																			${reportData[user.data]['汽车票'] }
																</a>
															</c:if>
														</td>
														<td width="7.78%" class="needAdd yongche" style="display:none;">
															<c:if test="${reportData[user.data]['用车服务']!=0}">
																<a href="javascript:void(0)" onclick="openBox('${user.data}','route_type','用车服务')">
																			${reportData[user.data]['用车服务'] }
																</a>
															</c:if>
														</td>
														<td width="10%" class="needRemove qita">
															<c:if test="${reportData[user.data]['其他']!=0}">
																<a href="javascript:void(0)" onclick="openBox('${user.data}','other_type','其他')">
																			${reportData[user.data]['其他'] }
																</a>
															</c:if>
														</td>
														<td width="10%" class="needAdd countAll" style="display:none;">
															
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

<div id="orderListBox" style="display: none; width: 1000px;"
			class="easyDialog_wrapper">
			<div class="easyDialog_content">
				<h4 class="easyDialog_title" id="easyDialogTitle">
					<a href="javascript:void(0)" title="关闭窗口" class="close_btn"
						id="closeBtn">×</a><span id="title"></span>
				</h4>
				<div>
					<iframe id="orderListBoxIFrame" src="" frameborder="0" width="1000"
						height="600"></iframe>
				</div>
			</div>
</div>
</body>
</HTML>
