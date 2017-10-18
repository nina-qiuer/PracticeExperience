<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<style type="text/css">
body{font-family:Arial,sans-serif;padding:0;background:#ffffff;font-size:12px;color:#000000;line-height:1.5;}

</style>
</head>
<body>
<h2>以下责任部门需要及时更新!</h2>
<h2 style="color:red">
		<#list sList as auxAccount>
		  <#if auxAccount.threeDepId == 0 && auxAccount.twoDepId ==0 >
		  ${auxAccount.firstDepName} </br>
		  </#if>
		  <#if auxAccount.threeDepId == 0 >
		  ${auxAccount.firstDepName}-${auxAccount.twoDepName} </br>
		  </#if>
		   <#if auxAccount.threeDepId != 0 && auxAccount.twoDepId !=0 >
		  ${auxAccount.firstDepName}-${auxAccount.twoDepName}-${auxAccount.threeDepName}</br>
		  </#if>
	    </#list>
 <h2>
</body>
</html>
	