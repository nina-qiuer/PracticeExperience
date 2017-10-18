/*
* 注：该插件提取于动易CMS系统管理后台。  
*
* 该插件只能应用于同域的URL，跨域的URL由于Javascript的安全限制，会出错。
*
* 新建一个标签：PE_FrameTab.AddNew(url)
; 如果url为空则是新开一个空白的标签页。
* 关闭当前标签：PE_FrameTab.CloseCurrentTab()
* 从当前标签切换到其他标签时触发：BeforeSwitch(); 如果该函数存在并返回false则不切换标签.该函数直接写在当前页面中.
* 从其他标签切换到当前标签时触发：SwitchInto(); 如果该函数存在则执行.该函数直接写在要切换到的页面.
* 在关闭当前标签页的时候触发：OnCloseTab(); 如果该函数存在存在则执行.该函数直接写在当前页面中.
*/
var lastFrameTabId = 1; //最后新建的FrameTabId，用于新建FrameTab
var currentFrameTabId = 1; //当前显示的FrameTab
var frameTabCount = 1;
var StyleSheetPath ="";
var  url_map = {}; 
var width_map = {};
var menu_title = '';


var PE_FrameTab = {
    //新建一个标签
    AddNew: function(url) {
        AddNewFrameTab(url);
    },
    //关闭当前标签
    CloseCurrentTab: function(){
        jQuery("#iFrameTab" + currentFrameTabId).find(".closeTab").click();
   }
};


function set_width_map(width) {
	width_map[currentFrameTabId] = width; 
}

///给tab标签注册切换和关闭tab事件
jQuery.fn.iFrameTab = function() {
    jQuery(this).each(function() {
        var cr = jQuery(this);
        var tabId = cr.attr("id").replace("iFrameTab", "");
        cr.click(function() {//切换FrameTab
            SwitchIframe(this);
        }).find(".closeTab").click(function() {//关闭FrameTab
            if (frameTabCount > 1) {
                var mainRightFrame = jQuery("#main_right_frame iframe[tabid='" + tabId + "']");
                var bClose = true; 
               	try {
               		bClose = mainRightFrame[0].contentWindow.OnCloseTab ? mainRightFrame[0].contentWindow.OnCloseTab() : true;
               	} catch(err) {
               		
               	}
               if (bClose) {
                    if (cr.attr("class") == "current") {//如果关闭的标签是当前标签，则切换到前一标签，如果前一标签不存在，则切换到后一标签
                        var nextIframe = cr.prev("li[id^='iFrameTab']");
                        if (nextIframe.length <= 0) { nextIframe = cr.next("li[id^='iFrameTab']"); }
                        SwitchIframe(nextIframe[0]);
                    }
                    //清理
                    cr.remove();
                    jQuery("#frmTitle iframe[tabid='" + tabId + "']").remove();
                    mainRightFrame.remove();
                    frameTabCount--;
                    CheckFramesScroll();
                    delete url_map[tabId];  
                }
            }
        }).end().dblclick(function() {
            jQuery(this).find(".closeTab").click();
        });
    });
    return jQuery(this);
}


///切换tab
function SwitchIframe(iFrameTab,url) {
    var tabId = jQuery(iFrameTab).attr("id").replace("iFrameTab", "");  //鼠标点击的tab的id
    if (currentFrameTabId == tabId) { return false; }
    //判断是否允许切换Tab
    var switchFunc = false;
    try {
    	switchFunc = jQuery("#main_right")[0].contentWindow.window.BeforeSwitch;
    } catch(err) {
    	switchFunc  = false;
    }
    var bSwitch = (switchFunc) ? switchFunc() : true;
    if (!bSwitch) { return false; }

    var currentGuideSrc = jQuery("#frmTitle iframe[tabid='" + currentFrameTabId + "']").attr("src");
    SetCurrentFrameTab(iFrameTab); 
	var mainFrames = jQuery("#main_right_frame > iframe").hide().attr({ "id": "", "name": "" });
	var newMainFrame = jQuery("#main_right_frame iframe[tabid='" + tabId + "']");
    //将iframe的window.name设为空，使<a target="main_right" />只对当前iframe有效
    mainFrames.each(function() { 
    	try {
    		this.contentWindow.window.name = ""; 
    	}catch(err) {
    		
    	}
    }); 
    
    if (newMainFrame.length <= 0) {//是否新建标签
        newMainFrame = jQuery("#main_right_frame").prepend(jQuery("#iframeMainTemplate").html())
				.find("[tabid=0]").attr({ "tabid": tabId, "src": url, "id": "main_right", "name": "main_right" })
				.css("display", "block").load(function(){
//					
					
					var tab_height = $(window.parent.document).find('#FrameTabs').height();
					var head_height = 35; 
					$(this).height($(window).height() - tab_height - head_height); 
				
				});
		//这儿要计算高度;
				//alert(newMainFrame.attr("tabid")); 
    } else {
    	//auto_width(width_map[tabId]);     
        newMainFrame = jQuery("#main_right_frame iframe[tabid='" + tabId + "']")
            .attr("id", "main_right").attr("name", "main_right").show();
    }
    //指定iframe的window.name，使<a target="main_right" />有效
    newMainFrame[0].contentWindow.window.name = "main_right";
    frames["main_right"] = newMainFrame[0].contentWindow.window;
	 
    currentFrameTabId = tabId;

    var switchInto = jQuery("#main_right")[0].contentWindow.window.SwitchInto;
    if(switchInto){ switchInto(); }
}

///初始化新建标签按钮
function InitNewFrameTab() {
    jQuery("#newFrameTab").click(function() {
        AddNewFrameTab();
    });
}

function AddNewFrameTab(url){
	
	//jQuery("#FrameTabs .current").removeClass("current");
	if(url) {
		for(var item in url_map) {
			if(url_map[item] == url) {
				if(item == currentFrameTabId)  {//刷新
					return true;
					
				} else {
					SwitchIframe(jQuery("#iFrameTab" + item)[0],url); //切换
					return false;
				}
			}
		}
	}
	
	url=url||"about:blank";
	var btn=jQuery("#newFrameTab"); 
	jQuery('<li id="iFrameTab' + (++lastFrameTabId) + '" ><a href="javascript:"><span id="frameTabTitle">(无标题)</span><a class="closeTab"><img border="0" src="' + StyleSheetPath +RES_URL+ 'script/jquery/plugin/frame_tab/images/tab-close.gif"/></a></a></li>')
			.insertBefore(btn).iFrameTab();
      frameTabCount++;
      SwitchIframe(jQuery("#iFrameTab" + lastFrameTabId)[0],url);
      if (CheckFramesScroll()) { jQuery("#FrameTabs ul:eq(0)").css("margin-left", cW - fW - 40); }
      
      url_map[lastFrameTabId] = url;
      return false; 
}

function SetCurrentFrameTab(selector) {
    jQuery("#FrameTabs .current").removeClass("current");
    jQuery(selector).addClass("current");
}
///检查是否需要滚动
function CheckFramesScroll() {
    var ft = jQuery("#FrameTabs");
    window.cW = ft.width(); //包含Tabs的容器宽度
    window.fW = ft.find("ul:eq(0)").width();
    ft.unbind("DOMMouseScroll").unbind("mousewheel");
    if (fW > cW) {
        if (jQuery.browser.mozilla) {
            ft.bind("DOMMouseScroll", function(e) {
                ScrollFrames(cW, fW, e);
            });
        } else {
            ft.bind("mousewheel", function(e) {
                ScrollFrames(cW, fW, e);
            });
        }
        jQuery("#FrameTabs .tab-strip-wrap").addClass("tab-strip-margin");
        jQuery("#FrameTabs .tab-right, #FrameTabs .tab-left").css("display", "block");
        $("#frameRefresh").css('right','25px'); 
        return true;
    } else {
        jQuery("#FrameTabs ul:eq(0)").css("margin-left", 0);
        jQuery("#FrameTabs .tab-right, #FrameTabs .tab-left").css("display", "none");
        jQuery("#FrameTabs .tab-strip-wrap").removeClass("tab-strip-margin");
         $("#frameRefresh").css('right','2px'); 
        return false;
    }
}
///Tab滚动。
///cW为包含Tabs的容器宽度；fW为全部Tabs的宽度；y为指定的位移，如果不指定y，则使用event中的位移。
function ScrollFrames(cW, fW, event, y) {
    if (!y) {
        if (event.wheelDelta) {
            y = event.wheelDelta / 5;
        } else if (event.detail) {
            y = -event.detail * 8;
        }
    }
    var jList = jQuery("#FrameTabs ul:eq(0)");
    var ml = jList.css("margin-left");
    ml = Number(ml.toLowerCase().replace("px", ""));
    if ((ml < 0 && y > 0) || (ml - cW > -fW - 40) && y < 0) {
        ml = ml + y;
        if (ml >= 0) { ml = 0; }
        if (ml - cW <= -fW - 40) { ml = cW - fW - 40;}
        jList.css("margin-left", ml);
    }
}
//注册Tab超出范围时左移、右移事件
function RegScrollFramesBtn() {
    jQuery("#FrameTabs .tab-right").click(function() { 
        ScrollFrames(window.cW,window.fW,null,-50);
    });
    jQuery("#FrameTabs .tab-left").click(function() { 
        ScrollFrames(window.cW,window.fW,null,50);
    });
}

//设置标签的标题
//tarFrame参数为目标iframe
function SetTabTitle(tarFrame) {
    var title = "";
    try { title = tarFrame.contentWindow.document.title; } catch (e) { title = menu_title; }
    var subTitle = title = title || "(无标题)";
    if (title.length > 10) { subTitle = title.substr(0, 9) + ".." } 
    jQuery("#iFrameTab" + jQuery(tarFrame).attr("tabid")).find("#frameTabTitle").html(subTitle).attr("title", title);
    
}


jQuery(function() {
    jQuery("#FrameTabs li[id^='iFrameTab']").iFrameTab();
    var menu_index = 0;
    InitNewFrameTab(); //初始化新建标签页按钮
    RegScrollFramesBtn();
    $('#frameRefresh').click(function() { 
    	var url = url_map[currentFrameTabId];
    	$('#main_right').get(0).src = url;   
    }
    );
});

//初始化使用方式
/*
   
 $(function(){
		 	 var first_link_url = 'm=Table,system';
		 	 var opened = false;
		 	 $('#admin-menu a[target="main_right"]').each(function(){      
		    	if(!opened && this.href.indexOf(first_link_url) != -1 ) {
		    		AddNewFrameTab(this.href);
		    		opened = true;    
		    	} 
		    	this.onclick = function() {
		    		menu_title = this.innerHTML;  
		    		return AddNewFrameTab(this.href); 
		    	}
		    });
		 })     
  */