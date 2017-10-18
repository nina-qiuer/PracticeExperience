<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<style type="text/css">
body{font-family:Arial,sans-serif;padding:0;background:#ffffff;font-size:12px;color:#000000;line-height:1.5;}

</style>
</head>
<body>
<h2>以下组织需要及时配置!</h2>
<h2 style="color:red">
		<#list sList as department>
			<#if department.fullName?? >
			 	${department.fullName} </br>
			</#if>
	    </#list>
 <h2>
</body>
</html>
	