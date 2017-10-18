<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/prefix.jsp" %>
    <style type="text/css">
        .datatable th{
            text-align: right;
            /* width: 30%; */
        }

        .datatable td{
            text-align: left;
        }
    </style>
    
 <script type="text/javascript">
 	
 	function executeSql(){
 		
 		console.log(sqlParam.value);
 		
 		$.ajax({
			type:"post",
			url:'ts/tecksupport/execute',
			data:$('#form').serialize(),
			success:function(data){
				if(data.success){
					alert(data.msg);
				}else{
					console.log(data.msg);
				}
			}
		});
 	}
 	

</script>
</head>
<body>
<div>
<form id="form" action="" method="post">
    <table width="100%" class="datatable mb10">
        <tr>
            <th width="10%">技术支持提报：</th>
            <td  width="40%" style="text-align: left">
                <textarea  id="sqlParam" cols="60" rows="10" name="sqlParam"></textarea>
            	<input type="button"  value="提交" onclick="executeSql()"/>
            </td>
        </tr>
    </table>
</form>
</div>

</body>
</html>