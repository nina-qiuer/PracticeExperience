<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>

<script type="text/javascript">

function onSubmitClicked() {
	$("form").attr("action", "complaint-sendEmailByComplaintId");
	$("form").submit();
	return false;
}


</script>

</HEAD>
<BODY>

<form name="form" id ="form" method="post" enctype="multipart/form-data" action="complaint-sendEmailByComplaintId">
   <table style="margin-top:120px;margin-left:500px;margin-bottom:250px;" class="notice2 mb5">
     <tr>
     <th colspan="3" >补发投诉邮件</th>
     </tr>
     <tr>
 	  <th>投诉单号</th>
      <td>
    	<input type="text"  name="complaintId" value="" id="complaintId" />
      </td>
   	  <td>
    	<input type="submit" name="submit" class="pd5" value="发送" id="submit"/>
      </td>
      </tr>
   </table>
</form>


<%@include file="/WEB-INF/html/foot.jsp"%>