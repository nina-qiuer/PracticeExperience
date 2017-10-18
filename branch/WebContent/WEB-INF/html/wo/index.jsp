<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
    
    <style type="text/css">
    
        div.main
        {
            position:absolute;
            top:30%;
            left:40%;
            width:500px;
            height:400px;
            margin-top:-50px;
            margin-left:-100px;
            background: #EBEBEB;
            font-size:16px;
            text-align:center;
            border-radius: 20px;
        }

        div ul li{ margin:30px auto;}

	
        a{
        	display:block;
            width:150px;
            height:40px;
        	margin:0 auto;
        	padding:10px 5px;
            border-radius: 10px;
            border:1px solid #D4DBED;
            background: #0197D1;
            line-height: 35px;
            color:white;
        }
        
        
       a:hover{
        	text-decoration: none;
            background:green;
        }
        
        
       a:active {
			position: relative;
			top: 2px;
			left: 2px;
		}
		
		.white_font{
			color:white;
			font-weight:bold;
		}

    </style>
</head>
<body>
    <div class="main">
        <ul>
            <li>
            		<a href="work_order" target="_blank"><span class="white_font">工单发起与查询</font></a>
            </li>
            <li>
            	<a href="work_order-list" target="_blank"><span class="white_font">工单处理</font></a>
            </li>
            <li>
            	<a href="config" target="_blank"><span class="white_font">管理员配置</font></a>
            </li>
            <li>
            	<a href="work_order-workOrderReport" target="_blank"><span class="white_font">工单报表</font></a>
            </li>
        </ul>
    </div>
</body>
</html>