<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8"  pageEncoding="utf-8"%>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="frm" uri="/WEB-INF/tld/frm.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xml:lang="en" xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<title>${pageTitle}</title>
		<meta name="description" content="">
		<meta name="keywords" content="">
		<link href="${CONFIG.res_url}css/frame.css" rel="stylesheet" type="text/css" />
		<script>
		${frm:makeJsVar(gJsMap)}
		</script>
		<script language="javascript" src="${CONFIG.res_url}script/jquery/jquery-1.7.1.min.js" ></script>
		<script language="javascript" src="${CONFIG.res_url}script/global.js" ></script>
		<script language="javascript" src="${CONFIG.res_url}script/jquery/plugin/frame_tab/frame_tab.js"></script> 
		<script language="javascript">
		function menu_hide () { 
			$('#sidebar').hide();
			$('#splitBar1').show();
			$('#splitBar2').hide();
		}
		
		function menu_show () {
			$("#sidebar").css({ display: "table-cell"});
			$('#splitBar1').hide();
			$('#splitBar2').show();
		}	
		
		$(document).ready(function(){
			window.scrollTo(0,0);
		});
		
		
		</script>
	</head>
	<body id="body" style="overflow:hidden"> 
		<div id="header" class="swauto">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="top_table">
				<tr>
					<td width="5">
					</td>
					<td width="50%">
					</td> 
					<td align="right">
						
							欢迎，${user.realName} | <a href="${CONFIG.app_url}frm/action/login-doLogout" target="_top">退出</a>
						
					</td>
					<td width="5"></td>
				</tr>
			</table>
		</div>
	
		<div class="" id="main">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
				<tr>
					<td  id="sidebar" style="width:180px;" valign="top">
						<div class="menu_t">&nbsp;</div>
						<div class="menu_m menu">
							<%@include file="menu.jsp" %>
							<script>
							 $(function(){
								 var first_link_url = '';
							 	 var opened = false;
								
							 	 $('#admin-menu a[target="main_right"]').each(function(){      
							    
							 		if(first_link_url == '') {
							 	
							 			first_link_url = this.href;
							 			if(first_link_url.substr(0,7). toLowerCase() == "http://" || first_link_url.substr(0,8). toLowerCase() == "https://") {
							 				
							 			} else {
							 				first_link_url = WEB_URL+first_link_url;
							 			}
							 			AddNewFrameTab(first_link_url);  
							 		}
							    	this.onclick = function() {
							    		menu_title = this.innerHTML; 
							    		return AddNewFrameTab(this.href); 
							    	}
							    });
							 })  
							</script>
						</div>
					</td>
					<td width="5" valign="top"></td>
					<td valign="top">
						<div class="clear" id="content">
							<!-- 书签浏览 --> 
							<div id="FrameTabs" style="overflow: hidden; position:relative;">
								<div id="frameRefresh" style="float:right; height:23px;position:absolute;right:10px; top:5px;z-index:10;"><img src="${CONFIG.res_url}images/icon/default/refresh.gif" id="frameRefresh" style="cursor:pointer;"/> 
								</div>
								<div class="tab-right" onmouseover="this.className='tab-right tab-right-over'" onmouseout="this.className='tab-right tab-right-disabled'"> </div>
								<div class="tab-left" onmouseover="this.className='tab-left tab-left-over'" onmouseout="this.className='tab-left tab-left-disabled'"> </div>
								<div style="width:100%;padding-right:20px;" class="tab-strip-wrap">
									<ul class="tab-strip clear" >
											<li id="newFrameTab" style="display:none;"><a title="新建标签页" href="javascript:" /></a> </li>
									</ul>
								</div>
							</div>
							<!-- 书签结束 --> 
							<div id="main_right_frame">      
								<iframe scrolling="auto"  id="main_right" name="main_right" src="about:blank" frameborder="0" width="100%"></iframe>
							</div>
		
							<div style="display: none" id="iframeMainTemplate">  
								<iframe tabid="0" frameborder="0" scrolling="auto" src="about:blank" onload="SetTabTitle(this)" style="width: 100%; visibility: inherit; z-index: 2;"> </iframe>
	
							</div> 
						</div>
					</td>
				</tr>            
			</table>
		</div>
	</BODY>
</HTML>

