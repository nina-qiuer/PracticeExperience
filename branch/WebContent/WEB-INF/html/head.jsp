<%@ page   contentType="text/html; charset=UTF-8"  pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="ui" uri="/WEB-INF/tld/ui.tld"%>
<%@ taglib prefix="frm" uri="/WEB-INF/tld/frm.tld"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xml:lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<title>${pageTitle}</title>
<style>
label.error {
	background:url("${CONFIG.res_url}script/jquery/validation/images/unchecked.gif") no-repeat 0px 0px;
	padding-left: 16px;
}
label.success {
	background:url("${CONFIG.res_url}script/jquery/validation/images/checked.gif") no-repeat 0px 0px;
	padding-left: 16px;
}
</style>
<script>
${frm:makeJsVar(gJsMap)}
</script>
<script language="javascript" src="${CONFIG.res_url}script/jquery/jquery-1.7.1.min.js"></script> 
<script language="javascript" src="${CONFIG.res_url}script/jquery/plugin/jquery.outer.js"></script>  
<script language="javascript" src="${CONFIG.res_url}script/jquery/plugin/jquery.tmpl.min.js"></script> 
<script language="javascript" src="${CONFIG.res_url}script/jquery/plugin/jquery.tmpl.ext.js"></script>  
<script language="javascript" src="${CONFIG.res_url}script/global.js" ></script>
<script language="javascript" src="${CONFIG.res_url}script/jquery/validation/jquery.validate.min.js"></script>
<script language="javascript" src="${CONFIG.res_url}script/jquery/validation/messages_zh.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}script/DatePickerNew/WdatePicker.js" ></script>
<script type="text/javascript" src="${CONFIG.res_url}script/jquery/plugin/layer/layer.js"></script>

<!-- 通用js函数，放在最后加载 -->
<script type="text/javascript" src="${CONFIG.res_url}script/common/common.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}/script/jquery/jquery.base64.js"></script>
<link rel="stylesheet" href="${CONFIG.res_url}script/jquery/validation/css/screen.css" />
<link type="text/css" rel="stylesheet" href="${CONFIG.res_url}css/bui.css" />
<link type="text/css" rel="stylesheet" href="${CONFIG.res_url}css/base.css" />
