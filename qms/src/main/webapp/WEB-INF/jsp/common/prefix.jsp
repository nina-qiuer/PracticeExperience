<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<style type="text/css">
.msg {
	margin-left: 3px;
	background: url("res/plugins/validation/images/unchecked.gif") no-repeat 0px 0px;
	padding-left: 16px;
	color: red
}
.left
{
text-align:left;
}
</style>
<link href="res/plugins/jquery-ui.css" rel="stylesheet" type="text/css">
<link href="res/plugins/layout/layout-default.css" rel="stylesheet" type="text/css">
<link href="res/plugins/wdScrollTab/css/TabPanel.css" rel="stylesheet" type="text/css">
<link href="res/plugins/zTree_v3/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css">
<link href="res/plugins/layer/skin/layer.css" rel="stylesheet" type="text/css">
<link href="res/plugins/validation/jQuery.validate.css" rel="stylesheet" type="text/css">
<link href="res/css/bui.css" rel="stylesheet" type="text/css">

<script src="res/plugins/jquery-2.1.3.js" type="text/javascript"></script>
<script src="res/plugins/jquery-ui.js" type="text/javascript"></script>
<script src="res/plugins/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="res/plugins/layout/jquery.layout.js" type="text/javascript"></script>
<script src="res/plugins/wdScrollTab/src/Plugins/Fader.js" type="text/javascript"></script>
<script src="res/plugins/wdScrollTab/src/Plugins/TabPanel.js" type="text/javascript"></script>
<script src="res/plugins/wdScrollTab/src/Plugins/Math.uuid.js" type="text/javascript"></script>
<script src="res/plugins/zTree_v3/js/jquery.ztree.core-3.5.js" type="text/javascript"></script>
<script src="res/plugins/zTree_v3/js/jquery.ztree.excheck-3.5.js" type="text/javascript"></script>
<script src="res/plugins/zTree_v3/js/jquery.ztree.exedit-3.5.js" type="text/javascript"></script>
<script src="res/plugins/layer/layer.js" type="text/javascript"></script>
<script src="res/plugins/validation/jquery.validate.min.js" type="text/javascript"></script>
<script src="res/plugins/validation/messages_zh.js" type="text/javascript"></script>
<script src="res/plugins/jquery.base64.js" type="text/javascript"></script>

<script type="text/javascript">
var temptr = $();
$(function(){
    $(".listtable").on("click","tr",function(event){
    	if(!$(this).hasClass("avb")){
    		temptr.removeClass("avb");
        	temptr = $(this);
        	$(this).addClass("avb");
    	}
    });
    
    /* 显示指定长度的内容，剩余部分用“...”代替      使用方法：class='shorten数字' 数字为显示的字符长度  如class='shorten30' 则显示30个字符，其余用“...”代替*/
    $('td[class*=shorten]').each(function() {
    	$(this).css("text-align","left");
		var classNames = $(this).attr("class");
		var classNameGroup = classNames.split(/[ ]+/);
		for(var key  in  classNameGroup){
				var className = classNameGroup[key];
				if (className.indexOf('shorten') !=- 1){
					var showLength = className.substring("shorten".length,className.length);
					
					var content = $(this).text();
					if (content.length > showLength) {
						var title = $(this).attr('title');
						if(title == undefined || title==''){
							title = $(this).text();
							$(this).attr('title',title);
						}
						content = content.substring(0, showLength);
						content += "...";
						$(this).text(content);
					}else{
						$(this).removeAttr('title');
					}
				break;
				}
		}
		
	});
    
    
    $('td[class*=orderId]').each(function() {
    		if("0"==$(this).text()||0==$(this).text()){
    			$(this).text("");
    		}else{
    			/* var uid = ${loginUser.id};
    			var object ={
    					"uid": uid,
    					"nickname":"${loginUser.realName}",
    					"orderId":$(this).text()
    			};
    			$.base64.utf8encode = true;
    			var str = $.base64.btoa(JSON.stringify(object));
    			url = "http://public-api.tof.tuniu.org/tof/manage/order/redirect?" + str; 生产
    			
	    		var orderLink = '<a href='+ url +' target="_blank">'+$(this).text()+'</a>'; */
	    		
				var orderLink = '<a href="javascript:void(0);"  onClick="gotopage(' + $(this).text() + ')">' +$(this).text()+ '</a>';
	    		
	    		$(this).text("");
	    		$(this).append(orderLink);
    		}
    } );
    
    $('td[class*=prdId]').each(function() {
    	if("0"==$(this).text()||0==$(this).text()){
			$(this).text("");
		}else{
			var orderLink = '<a href="http://crm.tuniu.com/main.php?do=new_crm_main_tree_quick_order&order_id=l'+$(this).text()+'" target="_blank">'+$(this).text()+'</a>';
			$(this).text("");
			$(this).append(orderLink);
		}
	} );
    
    $('td[class*=cmpId]').each(function() {
    	if("0"==$(this).text()){
			$(this).text("");
		}else{
			var orderLink = '<a href="http://complaint.tuniu.org:12001/ssi/complaint/action/complaint-toBill?id='+$(this).text()+'" target="_blank">'+$(this).text()+'</a>';
			$(this).text("");
			$(this).append(orderLink);
		}
	} );
    
    /*jquery扩展函数escape     作用：将文本中的单双引号转换为转义字符&quot;   &acute;   目的：如果不进行转义，在title使用的地方就会导致文本缺失*/
    $.fn.escape=function () {
    	var reg=new RegExp('"',"g"); 
    	content=$(this).val().replace(reg,"&quot;"); 
    	reg = new RegExp("'","g");
    	content = content.replace(reg,"&acute;");
    	$(this).val(content);
     };
     
});

/**
 * 获得订单详情页面进行跳转
 */
function gotopage(text){
	$.ajax({
		type: "POST",
		url: "common/page/queryOrderPageUrl",
		data: {"orderId" : text},
		dataType: "json",
		success: function(result) {
			if(null != result.data && "" != result.data){
				window.open(result.data);
			}
		}
	});
}

function lineFeed(name){
	
	if(name.length>9){
		
		var beginName = name.substring(0, 9);
		var EndName  = name.substring(9,name.length);
		name = beginName+"\n"+EndName;
		
	}
	return name;
}

function openWin(title, url, width, height) {
	layer.open({
        type: 2,
        shade: false,
        maxmin: true,
        title: title,
        content: url,
        area: [width+'px', height+'px']
    });
}

function openContent(title, key, width, height) {
	var content = $("#" + key).html();
	layer.open({
        type: 1,
        shade: false,
        maxmin: true,
        title: title,
        content: content,
        area: [width+'px', height+'px']
    });
}

//按钮置灰
function disableButton(input)
{
	$("input[name='"+input.name+"']").attr('disabled','disabled');
	$("input[name='"+input.name+"']").removeClass('blue');
	
};
//按钮去掉置灰样式
function enableButton(input)
{
	$("input[name='"+input.name+"']").removeAttr('disabled','disabled');
	$("input[name='"+input.name+"']").addClass('blue');

};

function searchResetPage(){
	   
	  $('#pageNo').val(1);
	  search();
}
</script>
</head>
</html>
