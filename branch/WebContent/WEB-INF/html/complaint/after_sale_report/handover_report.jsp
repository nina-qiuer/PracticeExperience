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
		showTab("tab_2");
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

	function openBox(dealName, handOverState) {
		var oldDealName = dealName;
		dealName = encodeURI(encodeURI(dealName));
		var url = "complaint_report-getHandOverComplaintList?finishTimeBgn="
				+ $('#finishTimeBgn').val() + "&finishTimeEnd="
				+ $('#finishTimeEnd').val() + "&dealName=" + dealName
				+ "&handOverState=" + handOverState;
		$('#cmpDetailListBoxIFrame').attr("src", url);
		openDialog(oldDealName + " " + $('#finishTimeBgn').val() + "至"
				+ $('#finishTimeEnd').val(), url);
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
	<form method="post" action="complaint_report-getHandOverReport">
		<div class="pici_search pd5">
			<label class="mr10 ">完成时间：</label> <input type="text"
				id="finishTimeBgn" name="finishTimeBgn" value="${finishTimeBgn }"
				onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'finishTimeEnd\')}',minDate:'#F{$dp.$D(\'finishTimeEnd\',{M:-24})}'})"
				readOnly="readonly" class="Wdate">
			&nbsp;&nbsp;--&nbsp;&nbsp; <input type="text" id="finishTimeEnd"
				name="finishTimeEnd" value="${finishTimeEnd }"
				onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'finishTimeBgn\')}',maxDate:'#F{$dp.$D(\'finishTimeBgn\',{M:24})}'})"
				readOnly="readonly" class="Wdate"> <input type="submit"
				value="查询" />
		</div>
	</form>
	<div id="pici_tab" class="clear">
		<ul id="tabs">
			<li class="menu_on" onclick="showTab(this.id)" id="tab_2"><s
				class="rc-l"></s><s class="rc-r"></s><a href="javascript:void(0)">售后服务部</a>
			</li>
			<li onclick="showTab(this.id);" id="tab_4"><s class="rc-l"></s><s
				class="rc-r"></s><a href="javascript:void(0)">机票服务部</a></li>
			<li onclick="showTab(this.id);" id="tab_5"><s class="rc-l"></s><s
				class="rc-r"></s><a href="javascript:void(0)">综合服务部</a></li>
			<li onclick="showTab(this.id);" id="tab_7"><s class="rc-l"></s><s
				class="rc-r"></s><a href="javascript:void(0)">华东售后部</a></li>
			<li onclick="showTab(this.id);" id="tab_8"><s class="rc-l"></s><s
				class="rc-r"></s><a href="javascript:void(0)">华南售后部</a></li>
			<li onclick="showTab(this.id);" id="tab_9"><s class="rc-l"></s><s
				class="rc-r"></s><a href="javascript:void(0)">华北售后部</a></li>
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
									<th width="5%">主动交接数</th>
									<th width="5%">被动交接数</th>
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
					test="${dep2.data=='售后服务部'||dep2.data=='华东售后部'||dep2.data=='机票服务部'||dep2.data=='综合服务部'
					||dep2.data=='华南售后部'||dep2.data=='华北售后部'}">
					<c:choose>
						<c:when test="${dep2.data=='售后服务部'}">
							<div id="content_2">
						</c:when>
						<c:when test="${dep2.data=='机票服务部'}">
							<div id="content_4">
						</c:when>
						<c:when test="${dep2.data=='综合服务部'}">
							<div id="content_5">
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
					</c:choose>
					<table width="100%" class="datatable">
						<c:forEach items="${dep2.childs }" var="dep3">
							<c:if test="${dep3.data!='资深服务组'}">
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
															test="${reportData[user.data]['下班交接']!=0}">
															<a href="javascript:void(0)"
																onclick="openBox('${user.data}',true)">
																${reportData[user.data]['下班交接']} </a>
														</c:if></td>
													<td width="5%" class="cmpCountItem"><c:if
															test="${reportData[user.data]['不在班交接']!=0}">
															<a href="javascript:void(0)"
																onclick="openBox('${user.data}',false)">
																${reportData[user.data]['不在班交接']} </a>
														</c:if></td>
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
