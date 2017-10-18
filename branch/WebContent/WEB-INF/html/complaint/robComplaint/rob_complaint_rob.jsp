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
            text-align:center;
            border-radius: 20px;
        }

        div ul li{list-style:none;float: left;display:block;margin:0 13%;}

        div.message{
            display:block;
            width:80%;
            height:65%;
            margin:10px auto;
        }

        p{
            font-size: 16px;
            color:green;
            margin: 2.5em auto;
        }
	   
       div.operate{
            display:block;
            width:60%;
            height:10%;
            margin:10px auto;
            font-size: 16px;
       }

    </style>
    <script type="text/javascript">
		function robComplaint(){
			$('#submitButton').attr('disabled',true);
			var amount = $('#amount').val();
			$('p:first').text('抢单中......');
			$('p:eq(1)').text('');
			$('p:last').text('');
			
			$.ajax({
				type:"post",
				url:'robComplaint-rob',
				data:{robAmount : amount},
				success:function(data){
					console.log(data);
					$('p:first').text('抢单结束');
					if(data.success){
						$('p:eq(1)').text('成功抢到'+data.robedAmount+'单');
						$('p:last').text('剩余可抢单数：'+ data.restAmount);
					}else{
						$('p:eq(1)').text(data.errorMsg);
					}
					$('#submitButton').removeAttr('disabled');
				}
			});
		}
	</script>
</head>
<body>
    <div class="main">
        <div class="message">
            <p>抢单系统</p>
            <p></p>
            <p></p>
        </div>
        <div class="operate">
            <ul>
                <li>抢&nbsp;
                    <select name="amount" id="amount">
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                    </select>&nbsp;单
                </li>
                <li><input id="submitButton" type="button" class="blue" value="提交" onclick="robComplaint()"></input></li>
            </ul>
        </div>
    </div>

</body></html>