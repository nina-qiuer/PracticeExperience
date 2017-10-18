<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp"%>
<link href="${CONFIG.res_url}css/thickbox.css" rel="stylesheet"
	type="text/css" />
<link type="text/css" href="${CONFIG.res_url}css/bui.css"
	rel="stylesheet" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript"
	src="${CONFIG.res_url}/script/thickbox-compressed.js"></script>
<style type="text/css">
.common-box {
	border: 1px solid #8CBFDE;
	margin: 0 0 10px 0;
}

.common-box-hd {
	margin-top: 1px;
	padding-left: 10px;
	background: #C6E3F1;
	color: #005590;
	font-size: 14px;
	font-weight: bold;
	height: 25px;
	line-height: 25px;
	border-bottom: 1px solid #8CBFDE;
	position: relative;
}

.common-box-hd span.title2 {
	cursor: pointer;
	display: inline-block;
	line-height: 25px;
	margin-right: 15px;
}
</style>
<title>增加备注</title>
</head>
<body>
<h3 align="right"><a href="#2">到页尾</a></h3>
<div class="common-box">
		<div class="common-box-hd">
			<span class="title2">投诉跟进记录</span>
		</div>
		
		<c:forEach items="${request.follow_note_list }" var="v">
			<table width="100%" class="listtable" style="TABLE-LAYOUT: fixed;">
				<tr align="center">
					<td width="130"><fmt:formatDate value="${v.addTime}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td width="60">${v.peopleName}</td>

					<td align="left" style="WORD-WRAP: break-word;">${v.content}</td>
				</tr>
			</table>
		</c:forEach>
	
		<div class="common-box-hd" style="margin-top:30px;">
			<span class="title2">增加备注</span>
		</div>
		<form name="form" id ="add_note" method="post" enctype="multipart/form-data"
		action="complaint-addNote?id=${request.complaint_id }">
			<div class="common-box" >		
				<table width="100%" style="TABLE-LAYOUT: fixed;" class="datatable">
					<tr>
			            <th width="130" rows="4" >备注：</th>
			            <td ><textarea name="remark" id="remark" cols="45" rows="4" ></textarea></td>
		          	</tr>
		          	<tr>
		          	    <th width="130"></th>
		          	    <td><input class="pd5" type="submit" name="save" value="备注" id="saveInfo" /></td>
		          	</tr>
		        </table>
	        </div>
        </form>
	</div>
	<a name="2">
	
<%@include file="/WEB-INF/html/foot.jsp" %> 
