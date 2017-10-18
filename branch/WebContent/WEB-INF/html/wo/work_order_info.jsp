<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<link type="text/css" href="${CONFIG.res_url}css/bui.css"
	rel="stylesheet" />
    <style type="text/css">

 		.datatable th{
 			width:15%;
            text-align: right;
        }      
        
        .datatable td{
        	width:35%;
            text-align: left;
        }    
        
        .listtable td{
        	text-align:center;
        }
      
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
    
</head>
<body>
<div id="main">
<div class="common-box">
		<div class="common-box-hd">
			<span class="title2">工单详情</span>
		</div>
			<table width="100%" class="datatable mb10">
			    <tr>
			        <th>项目组：</th>
			        <td>
			        	${entity.department } 
			        	<c:if test='${entity.businessClass != ""}'>
			        		(${entity.businessClass })
			        	</c:if>
			        </td>
			        <th>订单号：</th>
			        <td>${ entity.orderId}</td>
			    </tr>
			    <tr>
			        <th>用户姓名：</th>
			        <td>${entity.customerName}</td>
			         <th>联系电话：</th>
			        <td>${entity.phone }</td>
			    </tr>
			    <tr>
			        <th>来电事由：</th>
			        <td colspan="3" class="shorten50">${entity.phoneMatter }</td>
			    </tr>
			    <tr>
			        <th>回复/解决时间:</th>
			        <td colspan="3">${entity.answerTime }分钟</td>
			    </tr>
			    <tr>
			        <th>备注：</th>
			        <td colspan="3" class="shorten50">${ entity.remark}</td>
			    </tr>
			    <tr>
			        <th>处理结果：</th>
			        <td colspan="3" class="shorten50">${ entity.solveResult}</td>
			    </tr>
			</table>
</div>

<div class="common-box">
		<div class="common-box-hd">
			<span class="title2">业务操作日志</span>
		</div>
		<table width="100%" class="listtable">
			<tr>
				<th width="130">操作时间</th>
				<th width="60">操作人</th>
				<th width="60">事件</th>
				<th align="left">内容</th>
			</tr>
		</table>
		<div id="follow_note">
			<c:forEach items="${operationLogList }" var="operationLog">
				<table width="100%" class="listtable" >
					<tr>
						<td width="130"><fmt:formatDate value="${operationLog.operateTime}"
								pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td width="60">${operationLog.operatePerson}</td>
						<td width="60">${operationLog.event}</td>
						<td align="left" class="shorten80">${operationLog.content}</td>
					</tr>
				</table>
			</c:forEach>
		</div>
</div>
</div>
</body>
</html>