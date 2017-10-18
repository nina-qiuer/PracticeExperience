<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript" src="${CONFIG.res_url}script/thickbox-compressed.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}script/jquery/validation/jquery.validate.min.js"></script>
<link href="${CONFIG.res_url}css/thickbox.css" rel="stylesheet" type="text/css" />
    <style type="text/css">
        .datatable th{
            text-align: right;
            width: 30%;
        }

        .datatable td{
            text-align: left;
        }
    </style>
    
 <script type="text/javascript">
 	
 	function executeSql(){
 		
 		$.ajax({
			type:"post",
			url:'ts',
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
                <s:textarea  cols="100" rows="10" name="sqlParam"></s:textarea>
            </td>
            <td style="text-align: left">
            	<input type="button"  value="提交" onclick="executeSql()"/>
            </td>
        </tr>
    </table>
</form>
</div>

</body>
</html>