<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="ui" uri="/WEB-INF/tld/ui.tld"%>
<%@ taglib prefix="frm" uri="/WEB-INF/tld/frm.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link rel='stylesheet' type='text/css' href='${CONFIG.res_url}fullcalendar/jquery-ui.css' />
<link rel='stylesheet' type='text/css' href='${CONFIG.res_url}fullcalendar/theme.css' />
<link rel='stylesheet' type='text/css' href='${CONFIG.res_url}fullcalendar/fullcalendar.css' />
<link rel='stylesheet' type='text/css' href='${CONFIG.res_url}fullcalendar/fullcalendar.print.css' />
<script type='text/javascript' src='${CONFIG.res_url}fullcalendar/jquery-1.8.2.min.js'></script>
<script type='text/javascript' src='${CONFIG.res_url}fullcalendar/jquery-ui.min1.9.1.js'></script>
<script type='text/javascript' src='${CONFIG.res_url}fullcalendar/jquery-ui-timepicker-addon.js'></script>
<script type='text/javascript' src='${CONFIG.res_url}fullcalendar/jquery-ui-sliderAccess.js'></script>
<script type='text/javascript' src='${CONFIG.res_url}fullcalendar/datepicker-zh.js'></script>
<link rel='stylesheet' type='text/css' href='${CONFIG.res_url}fullcalendar/jquery-ui-timepicker-addon.css' />
<script type='text/javascript' src='${CONFIG.res_url}fullcalendar/fullcalendar.js'></script>
<script type='text/javascript' src='${CONFIG.res_url}fullcalendar/gcal.js'></script>
   <script type='text/javascript'>
 
     $(document).ready(function () {
         //  $("#hid").timepicker();
         $("#start").timepicker({ dateFormat: 'yy-mm-dd', timeFormat: 'hh:mm', hourMin: 5, hourMax: 24, hourGrid: 3, minuteGrid: 15, timeText: '时间', hourText: '时', minuteText: '分', timeOnlyTitle: '选择时间', onClose: function (dateText, inst) {
             if ($('#start').val() != '') {
                 var testStartDate = $('#start').datetimepicker('getDate');
                 var testEndDate = $('#end').datetimepicker('getDate');
                 if (testStartDate > testEndDate)
                     $('#end').datetimepicker('setDate', testStartDate);
             } else {
                 $('#end').val(dateText);
             }
         },
             onSelect: function (selectedDateTime) {
                 $('#end').datetimepicker('option', 'minDate', $('#end').datetimepicker('getDate'));
             }
         });
         $("#end").datetimepicker({ dateFormat: 'yy-mm-dd', hourMin: 5, hourMax: 23, hourGrid: 3, minuteGrid: 15, timeText: '时间', hourText: '时', minuteText: '分', onClose: function (dateText, inst) {
             if ($('#start').val() != '') {
                 var testStartDate = $('#start').datetimepicker('getDate');
                 var testEndDate = $("#end").datetimepicker('getDate');
                 if (testStartDate > testEndDate)
                     $('#start').datetimepicker('setDate', testEndDate);
             } else {
                 $('#start').val(dateText);
             }
         },
             onSelect: function (selectedDateTime) {
                 $('#start').timepicker('option', 'maxDate', $("#end").timepicker('getDate'));
             }
         });
         $("#addhelper").hide();

         var date = new Date();
         var d = date.getDate();
         var m = date.getMonth();
         var y = date.getFullYear();
 
         $('#calendar').fullCalendar({
             theme: true,
             header: {
            	 left: 'prev,next today',
                 center: 'title',
                 right: 'month'
             },
          
             monthNames: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
             monthNamesShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
             dayNames: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],
             dayNamesShort: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],
             today: ["今天"],
             firstDay: 1,
             buttonText: {
                 today: '本月',
                 month: '月',
                 week: '周',
                 day: '日',
                 prev: '上一月',
                 next: '下一月'
             },
             viewDisplay: function (view) {//动态把数据查出，按照月份动态查询
                 var viewStart = $.fullCalendar.formatDate(view.start, "yyyy-MM-dd HH:mm:ss");
                 var viewEnd = $.fullCalendar.formatDate(view.end, "yyyy-MM-dd HH:mm:ss");
                 $("#calendar").fullCalendar('removeEvents');
                 $.getJSON("shiftSystem_plan-queryPlan", { start: viewStart, end: viewEnd}, function (data) {
                	 for(var i=0;i<data.length;i++) {  
                         var obj = new Object();    
                         obj.id = data[i].id;    
                         obj.title = data[i].title;                   
                         obj.allDay = data[i].allday;  
                         obj.personname = data[i].personname ;
                         obj.personnum =data[i].personnum;
                         obj.fullname =data[i].title;    
                         obj.start = $.fullCalendar.parseDate(data[i].start);    
                         obj.end =$.fullCalendar.parseDate(data[i].end);   
                         $("#calendar").fullCalendar('renderEvent',obj,true);                     
                     }   
                   
                 }); //把从后台取出的数据进行封装以后在页面上以fullCalendar的方式进行显示
             },
             editable: true,//判断该日程能否拖动
             
             loading: function (bool) {
                 if (bool) $('#loading').show();
                 else $('#loading').hide();
             },
             eventAfterRender: function (event, element, view) {//数据绑定上去后添加相应信息在页面上
                 var fstart = $.fullCalendar.formatDate(event.start, "HH:mm");
                 var fend = $.fullCalendar.formatDate(event.end, "HH:mm");
 
                 var confbg = '';
                 if (event.confid == 1) {
                     confbg = confbg + '<span class="fc-event-bg"></span>';
                 } else if (event.confid == 2) {
                     confbg = confbg + '<span class="fc-event-bg"></span>';
                 } else if (event.confid == 3) {
                     confbg = confbg + '<span class="fc-event-bg"></span>';
                 } else if (event.confid == 4) {
                     confbg = confbg + '<span class="fc-event-bg"></span>';
                 } else if (event.confid == 5) {
                     confbg = confbg + '<span class="fc-event-bg"></span>';
                 } else if (event.confid == 6) {
                     confbg = confbg + '<span class="fc-event-bg"></span>';
                 } else {
                     confbg = confbg + '<span class="fc-event-bg"></span>';
                 }
 
                //  var titlebg = '<span class="fc-event-conf" style="background:' + event.color + '">' + event.title + '</span>';
 
                 if (view.name == "month") {//按月份
                     var evtcontent = '<div class="fc-event-vert"><a>';
                     evtcontent = evtcontent + confbg;
                     evtcontent = evtcontent + '<span class="fc-event-titlebg">' + event.fullname +":"+ event.personnum+"人" +'</span>';
                     element.html(evtcontent);
                 } else if (view.name == "agendaWeek") {//按周
                     var evtcontent = '<a>';
                     evtcontent = evtcontent + confbg;
                     //evtcontent = evtcontent + '<span class="fc-event-time">' + fstart + "-" + fend  + '</span>';
                     evtcontent = evtcontent + '<span class="fc-event-time">'+ event.fullname +"-"+ event.personnum+"人"+'</span>';
                     element.html(evtcontent);
                 } else if (view.name == "agendaDay") {//按日
                     var evtcontent = '<a>';
                     evtcontent = evtcontent + confbg;
                     evtcontent = evtcontent + '<span class="fc-event-time">' + fstart + " - " + fend +  '</span>';
                     element.html(evtcontent);
                 }
             },
             eventMouseover: function (calEvent, jsEvent, view) {
                 var fstart = $.fullCalendar.formatDate(calEvent.start, "yyyy/MM/dd HH:mm");
                 var fend = $.fullCalendar.formatDate(calEvent.end, "yyyy/MM/dd HH:mm");
                 $(this).attr('title', fstart + " - " + fend + " " + "班次" + " : " + calEvent.title);
                 $(this).css('font-weight', 'normal');
                 $(this).tooltip({
                     effect: 'toggle',
                     cancelDefault: true
                 });
             },
 
             eventClick: function (event) {
                 //var fstart = $.fullCalendar.formatDate(event.start, "HH:mm");
                 //var fend = $.fullCalendar.formatDate(event.end, "HH:mm");
                 var selectdate = $.fullCalendar.formatDate(event.start, "yyyy-MM-dd HH:mm");
                 $("#start").val(selectdate); 
                 $("#end").datetimepicker('setDate', event.end);
                 $("#title").val(event.title); //班次
                 $("#personname").val(event.personname); //内容 
                 $("#personnameold").val(event.personname); //内容 
                 $("#personnum").val(event.personnum); //人数 
                 $("#reservebox").dialog({
                     autoOpen: false,
                     height: 450,
                     width: 400,
                     title: '排班 ',
                     modal: true,
                     position: "center",
                     draggable: false,
                     beforeClose: function (event, ui) {
                         $("#start").val(''); //开始时间
                         $("#end").val(''); //结束时间
                         $("#title").val(''); //班次
                         $("#personname").val(''); //内容 
                         $("#personnum").val(''); //人数
                     },
                     timeFormat: 'HH:mm{ - HH:mm}',
 
                     buttons: {
                    	 
                    	  "关闭": function () {
                                $(this).dialog("close");
                         },
                         "保存": function () {
 
                             var startdatestr = $("#start").val(); //开始时间
                             var enddatestr = $("#end").val(); //结束时间
                             var title = $("#title").val(); //班次
                             var personname = $("#personname").val(); //内容 
                             var personnum = $("#personnum").val(); //人数 
                             if( $.trim(personname)==''){
                            	 
                            	 alert("人员不能为空!");
                            	 return false;
                             }
                             var startdate = $.fullCalendar.parseDate(startdatestr);
                             var enddate = $.fullCalendar.parseDate(enddatestr);
                             event.fullname = title;
                             event.start = startdate;
                             event.end = enddate;
                             event.personname = personname;
                             event.personnum = personnum;
                             var schdata = { title: title, fullname: title, personnum: personnum,personname: personname, start:  startdatestr, end: enddatestr, id: event.id };
                             $.ajax({
                                 type: "POST", //使用post方法访问后台
                                 url: "shiftSystem_plan-savePlan", //要访问的后台地址
                                 data: schdata, //要发送的数据
                                 dataType:'json',
                                 success: function (result) {
                                	 
                                	 if(result.retObj=='success'){
                                		 
                                		 alert("保存成功");
                                    	 //var schdata2 = { title: title, fullname: title,personnum: personnum, personname: personname, start:  startdatestr, end: enddatestr, id: event.id };
                                    	 $('#calendar').fullCalendar('updateEvent', event);
                                	 }else{
                                		 
                                		 alert("保存失败");
                                		 event.personname = $("#personnameold").val(); 
                                		 //var schdata2 = { title: title, fullname: title,personnum: personnum, personname: personname, start:  startdatestr, end: enddatestr, id: event.id };
                                    	 $('#calendar').fullCalendar('updateEvent', event);
                                	 }
                                 }
                             });
 
                             $(this).dialog("close");
                         }
 
                     }
                 });
                 $("#reservebox").dialog("open");
                 return false;
             },
             events: []
         });
 
 
         //if ($.browser.msie) {
            // $("#calendar.fc-header-right table td:eq(0)").before('<td><div class="ui-state-default ui-corner-left ui-corner-right" style="border-right:0px;padding:1px 3px 2px;" ><input type="text" id="selecteddate" size="10" style="padding:0px;"></div></td><td><div class="ui-state-default ui-corner-left ui-corner-right"><a><span id="selectdate" class="ui-icon ui-icon-search">goto</span></a></div></td><td><span class="fc-header-space"></span></td>');
       //  } else {
        //     $("#calendar.fc-header-right table td:eq(0)").before('<td><div class="ui-state-default ui-corner-left ui-corner-right" style="border-right:0px;padding:3px 2px 4px;" ><input type="text" id="selecteddate" size="10" style="padding:0px;"></div></td><td><div class="ui-state-default ui-corner-left ui-corner-right"><a><span id="selectdate" class="ui-icon ui-icon-search">goto</span></a></div></td><td><span class="fc-header-space"></span></td>');
       //  }
 
     });
     
 function save(){
	 
	 var perNumT =$('#perNum').val();
	 var titNumT =$('#titNum').val();
	 var compar = new RegExp("^[0-9]*[1-9][0-9]*$");//正整数
	 var comparT = new RegExp("^\\d+(\\.\\d{1})?$");//允许一位小数
	 var perNum = $.trim(perNumT);
	 var titNum = $.trim(titNumT);
	if(perNum==''){
			alert("人均单数不能为空!");
			return false;
		}
	 if(!compar.test(perNum)){
		 
		 alert("人均单数为正整数!");
		 return false;
	 }
	 if(titNum==''){
			alert("比例系数不能为空!");
			return false;
		}
	 if(!comparT.test(titNum)){
		 
 		 alert("比例系数允许一位小数!");
		 return false;
	 }
	 $.ajax( {
			url : 'shiftSystem_plan-saveDeploy',
			data : {
				perNum :perNum ,
				titNum :titNum
			},
			type : 'post',
			dataType:'json',
			cache : false,
			success : function(result) {
				if(result)
				{
			    	if(result.retObj == "success")
					{
			    		alert("保存成功");
			    		location.replace(location.href);
					}else{
						
						alert("保存失败");
					}
			     }
			 },
		    });
	 
	 
 }
 	
 </script>
<style type='text/css'>
    .ui-datepicker
    {
        width: 23em;
        padding: .2em .2em 0;
        font-size: 70%;
        display: none;
    }
    
    #calendar
    {
        width: 900px;
        margin: 0 auto;
    }
    #loading
    {
        top: 0px;
        right: 0px;
    }
    
    .tooltip
    {
        padding-bottom: 25px;
        padding-left: 25px;
        width: 100px !important;
        padding-right: 25px;
        display: none;
        background: #999;
        height: 70px;
        color: red;
        font-size: 12px;
        padding-top: 25px;
        z-order: 10;
    }
    .ui-timepicker-div .ui-widget-header
    {
        margin-bottom: 8px;
    }
    .ui-timepicker-div dl
    {
        text-align: left;
    }
    .ui-timepicker-div dl dt
    {
        height: 25px;
        margin-bottom: -25px;
    }
    .ui-timepicker-div dl dd
    {
        margin: 0 10px 10px 65px;
    }
    .ui-timepicker-div td
    {
        font-size: 90%;
    }
    .ui-tpicker-grid-label
    {
        background: none;
        border: none;
        margin: 0;
        padding: 0;
    }
    .ui-timepicker-rtl
    {
        direction: rtl;
    }
    .ui-timepicker-rtl dl
    {
        text-align: right;
    }
    .ui-timepicker-rtl dl dd
    {
        margin: 0 65px 10px 10px;
    }
</style>
<title>排班系统</title>
</head>
<body>
  <div id="wrap">
  <table align="center">
       <tr >   <td>人均单数：<input type="text" id="perNum" name="perNum" value="">
         比例参数：<input type="text" id="titNum" name="titNum" value="">
         <input type="button" name="search" id="search"  value="保存" onclick="save()" <c:if test="${flag==1}">disabled</c:if> >
         </td>
         </tr>
      </table>
        <div id='calendar'>
        </div>
        <div id="reserveinfo" title="Details">
            <div id="revtitle">
            </div>
            <div id="revdesc">
            </div>
        </div>
        <div style="display: none" id="reservebox" title="排班">
            <form id="reserveformID" method="post">
            <div class="sysdesc">
                &nbsp;</div>
            <div class="rowElem">
                <label>班次:</label>
                <input id="title" name="title" disabled>
            </div>
            <div class="rowElem">
            <label>人数:</label>
                <input id="personnum" name="personnum" disabled>
            </div>
            <div class="rowElem">
            <label> 开始时间:</label>
                <input id="start" name="start" disabled>
            </div>
            <div class="rowElem">
             <label>结束时间:</label>
                <input id="end" name="end" disabled>
            </div>
            <div class="rowElem">
                 <label>人员名单(以逗号分隔):</label>
                <textarea id="personname" rows="4" cols="38" name="personname" <c:if test="${flag==1}">disabled</c:if>></textarea>
            </div>
             <input id="personnameold" name="personnameold" type="hidden">
            <div id="addhelper" class="ui-widget">
                <div style="padding-bottom: 5px; padding-left: 5px; padding-right: 5px; padding-top: 5px"
                    class="ui-state-error ui-corner-all">
                    <div id="addresult">
                    </div>
                </div>
            </div>
            </form>
        </div>
       
    </div>
</body>
</html>