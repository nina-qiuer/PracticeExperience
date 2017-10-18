<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<link href="${CONFIG.res_url}css/thickbox.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${CONFIG.res_url}script/jquery/plugin/auto_complete/jquery.autocomplete.css"/>
<link href="${CONFIG.res_url}css/easydialog.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${CONFIG.res_url}/script/easydialog.min.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}script/thickbox-compressed.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}script/jquery/plugin/auto_complete/jquery.autocomplete.js"></script>  
<script type="text/javascript" src="${CONFIG.BOSS_URL}/js/util.js"></script>
<script type="text/javascript" src="${CONFIG.res_url}script/jquery/jquery.1.4.2.js"></script>
<style type="text/css">
<!-- tr变黄色 -->
.trbg td{background:#ff9;}
.trbg1 td{background:#87CEFF;}

.listtable tr.yellowbg td{background:#FFFF99}

.listtable tr td.orange{background:orange;}
</style>
<script>
//标签转换时更新标签样式
function change_menu(value) {
	$("li[id^='menu_']").each(function(){
		$(this).removeClass("menu_on");
	});
	$('#menu_'+value).addClass("menu_on");
	$('#status').val(value-1);
}

$(function () {
    // chkAll全选事件
    $("#chkAll").bind("click", function () {
        $("[name = call_num]:checkbox").attr("checked", this.checked);
    });

});

function searchTable(menuId){ 
	$('#callloss_form').attr("action", "callloss");
	change_menu(menuId.split('menu_')[1]);
	$('#callloss_form').submit();
}

function onSearchClicked() {
	$('#callloss_form').attr("action", "callloss");
	$('#callloss_form').submit();
}

function onReset(){
	$('#callingId').val("");
}

function checkCall(phoneNum) {
	var param = {"callingId":phoneNum};
	$.ajax({
	type: "POST",
	async:false,
	url: "callloss-checkCall",
	data: param,
	success: function(data){
		if(data.replace("[","").replace("]","")==3){
			$('#flag').val(3);
		}
		if(data.replace("[","").replace("]","")==0){
			$('#flag').val(0);
		}
     }
   });
}


function callloss_call(obj){
	var phoneNum = $(obj).html();
	checkCall(phoneNum);
	if($('#flag').val()==3){
		alert("此用户有客服正在更进");
		onSearchClicked();
		return false;
	}
	CallDispatch(phoneNum, '0', 'call_dispatch');
	$('#initAddCall').attr('href',"callloss-initChangeCallStatus?callingId="+phoneNum+"&status=3");
	$('#initAddCall').get(0).click();
}

function queryDetail(obj){
	$('#queryDetail').attr('alt',"callloss-queryDetail?callingId="+obj.id+"&placeValuesBeforeTB_=savedValues&TB_iframe=true&height=500&width=800&modal=false");
	$("#queryDetail").click();
	
}

function onSubmit(id,callingId){
	if($("input[name='status"+id+"']:checked").val()==2){
		$('#callloss_form_change').attr("action","callloss-changeCallStatus?status="+$('input[name=status'+id+']:checked').val()+"&callingId="+callingId+"&a="+new Date());
	}else{
		$('#callloss_form_change').attr("action","callloss-changeCallStatus?status="+$('input[name=status'+id+']:checked').val()+"&callId="+id+"&a="+new Date());
	}
	if(!confirm('是否确定修改状态')){
		return false;
	}
	$('#callloss_form_change').submit();
}

function checkSubmit(){
	if($("input[name='call_num']:checked").length <= 0){
		alert("至少选择一条记录");
		return false;
	}
	var regMobile = /^0?1[3|4|5|8][0-9]\d{8}$/;
	var cou = 0;
	$("input[name='call_num']:checkbox").each(function(){ 
		if($(this).attr("checked")){
			if(!regMobile.test($(this).val())){
				$("#"+$(this).val()).attr("style","background-color:red");
				cou++;
			}
        }
    });
	if(cou > 0){
		alert("选了 "+cou+" 个非手机号码");
		return false;
	}
	
}

function sendSms4lost(){
	if(checkSubmit()==false){
		return false;
	}
	var str="";
	var cou = 0; 
    $("input[name='call_num']:checkbox").each(function(){ 
        if($(this).attr("checked")){
        	if(cou==$("input[name='call_num']:checked").length - 1){
        		str += $(this).val();
        	}else{
        		str += $(this).val()+",";
        	}
        	cou++;
        }
    });
	$("#sendSms4lostFrame").attr("src","callloss-sendSms4lost?ids="+str);
	easyDialog.open({container : 'sendSms4lostBox', overlay : false});
}
</script>
</HEAD>
<BODY>
<div class="top_crumbs">您当前所在的位置：<a href="#">呼损查询</a>&gt;&gt;<span class="top_crumbs_txt">呼损列表</span></div>
	<a id="initAddCall" target="_blank"></a> 
	<input type="button" id="queryDetail" style="display: none" class="thickbox pd5 mr20" value="查看详情"/> 
	<form name="form" id="callloss_form" method="post">

		<div class="pici_search pd5">

			<div class="pici_search pd5 mb10">
			<label class="mr10">客人主叫号： <input type="text" size="10" id="callingId" name="callingId" value="${callingId}" /> </label>
			<input type="hidden" id="status" name="status" value="${status==null?0:status}"/> 
			<input type="hidden" id="flag"/> 
			<input type="button" id="queryCall" value="查询" class="blue" name="" onclick="onSearchClicked();"/> 
			<input type="button" value="重置" class="blue" onclick="onReset();"/> 
			<input type="button" value="刷新" class="blue" onclick="onSearchClicked();"/> 
			<c:if test="${status == 4}">
				<input type="button" value="发送短信" class="blue" onclick="sendSms4lost();"/> 
			</c:if>
			</div>
		</div>
	</form>
	<div id="pici_tab" class="clear">
			<ul>
				<li onclick="searchTable(this.id)" id="menu_1">
					<s class="rc-l"></s><s class="rc-r"></s><a href="javascript:void(0)">待处理</a>
				</li>
				<li onclick="searchTable(this.id)" id="menu_4">
					<s class="rc-l"></s><s class="rc-r"></s><a href="javascript:void(0)">处理中</a>
				</li>
				<li onclick="searchTable(this.id)" id="menu_2">
					<s class="rc-l"></s><s class="rc-r"></s><a href="javascript:void(0)">已处理</a>
				</li>
				<li onclick="searchTable(this.id)" id="menu_3">
					<s class="rc-l"></s><s class="rc-r"></s><a href="javascript:void(0)">已关闭</a>
				</li>
				<li onclick="searchTable(this.id)" id="menu_5">
					<s class="rc-l"></s><s class="rc-r"></s><a href="javascript:void(0)">发短信</a>
				</li>
			</ul>
		</div>
<table class="listtable" width="98%">
	<thead>
		<c:if test="${status == 4}"><th><input type="checkbox" id="chkAll">全选</th></c:if>
		
		<th>客人主叫号</th>
		<th>来电时间</th>
		<th>排队振铃时间（秒）</th>
		<th>呼损次数</th>
		<th>最后修改人</th>
		<th>最后修改时间</th>
		<th>状态</th>
		<th>回拨次数</th>
		<c:if test="${status != 0 && status !=4}">
			<th>操作</th>
		</c:if>
		<!--  <th>操作</th>-->
	</thead>
	<tbody>
		<a href="callloss-sendSms4lost" target="_blank" id="sendSms4lost"></a>
		<c:forEach items="${callLossEntitys}" var="v"  varStatus="st"> 
		<tr id="mytr" <c:if test="${v.status eq 2 && v.autoClose eq 1}">class="trbg1"</c:if> align="center" class="trbg" >
			<c:if test="${status == 4}">
				<td><input type="checkbox" name="call_num" value="${v.callingId}"></td>
			</c:if>
			<c:if test="${v.status == 0}">
				<td id="${v.callingId}" ><a href="javascript:;"  onclick="callloss_call(this);">${v.callingId}</a><c:if test="${fn:substring(v.statisticDate, 0,10) eq today && (status==0 || status==4)}"><font color='red'>(当天)</font></c:if></td>
			</c:if>
			<c:if test="${v.status != 0}">
				<td id="${v.callingId}">${v.callingId}<c:if test="${fn:substring(v.statisticDate, 0,10) eq today && (status==0 || status==4)}"><font color='red'>(当天)</font></c:if></td>
			</c:if>
			<td>${fn:substring(v.statisticDate, 0,19)}</td>
			<td>${v.answerTime}</td>
			<td>${v.callingCount}</td>
			<c:if test="${v.lastUpdatedBy==0}">
			<td></td>
			</c:if>
			<c:if test="${v.lastUpdatedBy!=0}">
			<td>${v.lastUpdatedNameBy}(${v.lastUpdatedBy})</td>
			</c:if>
			<td><fmt:formatDate value="${v.lastUpdatedTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
			<c:if test="${v.status eq 0}">
			<td>待处理</td>
			</c:if>
			<c:if test="${v.status eq 1}">
			<td>已处理</td>
			</c:if>
			<c:if test="${v.status eq 2}">
			<td>已关闭</td>
			</c:if>
			<c:if test="${v.status eq 3}">
			<td>处理中</td>
			</c:if>
			<td><a href="javascript:;" id="${v.callingId}"onclick="queryDetail(this);">${v.callCount}</a></td>
			<c:if test="${v.status != 0}">
			<td>
			<form name="form" id="callloss_form_change" method="post">
			状态：
				<input type="radio" name="status${v.id}" value="0" <c:if test="${status==1 }">checked="checked"</c:if>>待处理
				<input type="radio" name="status${v.id}" value="1" <c:if test="${status==1 }">disabled="disabled" checked="checked"</c:if>>已处理
				
				<input type="radio" name="status${v.id}" value="2" <c:if test="${status==2 }">disabled="disabled" checked="checked"</c:if>>已关闭
				<c:if test="${user==103 || user==5057 }">
					<input type="button" value="修改状态" class="blue" name="" onclick="onSubmit(${v.id},${v.callingId});"/> 
				</c:if>
				<c:if test="${user!=103 && user!=5057 }">
					<input type="button" value="修改状态" class="blue" <c:if test="${status==3 }">disabled="disabled"</c:if> name="" onclick="onSubmit(${v.id},${v.callingId});"/> 
				</c:if> 
			</form>
			</c:if>
		</tr>
		</c:forEach>
	</tbody>
</table>
<div id="sendSms4lostBox" style="display: none; width: 520px;" class="easyDialog_wrapper">
	<div class="easyDialog_content">
		<h4 class="easyDialog_title" id="easyDialogTitle"><a href="javascript:void(0)" title="关闭窗口" class="close_btn" id="closeBtn">×</a>发起核实请求</h4>
		<div>
			<iframe id="sendSms4lostFrame" src="callloss-sendSms4lost" frameborder="0" width="520" height="330"></iframe>
		</div>
	</div>
</div>
<%@include file="/WEB-INF/html/pager.jsp" %>
<br>
<script type="text/javascript">
$(function(){
	/*移除thickbox默认样式*/
	$('.thickbox').click(function(){
		$('#TB_closeAjaxWindow').html("<a href='javascript:void(0)' id='TB_closeWindowButton' title='Close'>×</a>");
		$('#TB_closeWindowButton').live('click',function(){
			tb_remove();
		});
	});
	$("li[id^='menu_']").each(function(){
		$(this).removeClass("menu_on");
	});
	var v = '${status==null?0:status}';
	$('#menu_'+(parseInt(v)+1)).addClass("menu_on");
	$('#status').val(v);
	if('${status}'==0){
		setTimeout('myrefresh()',30000); //指定30秒刷新一次 
	}
	if('${calling}' == "calling"){
		parent.parent.MDIClose();
	}
});

function myrefresh()
{
	onSearchClicked();
}

</script>
<%@include file="/WEB-INF/html/foot.jsp" %>
