<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<style type="text/css">
    
        div.main
        {
            position:absolute;
            top:30%;
            left:40%;
            width:500px;
            height:300px;
            margin-top:-50px;
            margin-left:-100px;
            background: #EBEBEB;
            font-size:16px;
            text-align:center;
            border-radius: 20px;
        }

        div ul li{ margin:60px auto;}

	
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
            		<a target="_blank" href="robComplaint-toRob"><span class="white_font">抢单</span></a>
            </li>
            <li>
            	<a target="_blank" href="robComplaint-toStatistics"><span class="white_font">抢单统计</span></a>
            </li>
        </ul>
    </div>

</body></html>