 /*common函数库*/
//变量
var validate; 

/*1.按钮相关*/
//1.1按钮置灰
function disableButton(input)
{
	$("input[name='"+input.name+"']").attr('disabled','disabled');
};
//1.2按钮去掉置灰样式
function enableButton(input)
{
	$("input[name='"+input.name+"']").removeAttr('disabled','disabled');
};

//1.3 新增或编辑按钮
function toAddOrUpdate(modolName,mainUrl,id) {
	var title = '';
	var content = '';
	
	if(id == undefined){
		title = '新增' + modolName;
		content = mainUrl+'-toAddOrUpdate';
	}else{
		title = '更新' + modolName;
		content = mainUrl+'-toAddOrUpdate?id='+id;
	}
	
	layer.open({
        type: 2,
        title: title,
        shadeClose: true,
        shade: false,
        maxmin: true, //开启最大化最小化按钮
        area: ['893px', '600px'],
        content: content
    });
	
}

function showInfo(modelName,mainUrl,id){
	var title = modelName+'['+id+']详情';
	var content = mainUrl+'-showInfo?id='+id;
	
	layer.open({
        type: 2,
        title: title,
        shadeClose: true,
        shade: false,
        maxmin: true, //开启最大化最小化按钮
        area: ['893px', '600px'],
        content: content
    });
}

//1.4 新增或更新中的提交按钮
function addOrUpdate(mainUrl){
		var url = "";
		if($('#id').val()==''){
 		url = mainUrl+'-add';
		}else{
			url = mainUrl+'-update';
		}
		
		$('#submitButton').attr('disabled','true');
		
		$.ajax({
		type:"post",
		url:url,
		data:$('#'+mainUrl+'Form').serialize(),
		success:function(data){
			console.log("parent.params:"+$('#search_form',parent.document).serialize());
			layer.alert('操作成功', function(index){ //点击确定后关闭当前iframe并刷新父页面
			    layer.close(index);
			    var frameIndex = parent.layer.getFrameIndex(window.name); //得到当前iframe层的索引
				$("#search_form",parent.document).submit();
				parent.layer.close(frameIndex); // 执行关闭
			});             
		}
	});
	}

//删除按钮
function del(modelName,mainUrl,id){
	var url = mainUrl + '-delete';
	if(confirm("确定删除"+modelName+"["+id+"]吗?")){
		$.ajax({
			type:"post",
			url:url,
			data:{id:id},
			success:function(){
				alert('删除成功');
				$("#search_form",parent.document).submit();
			}
		});
	}
}

//1.5 导出按钮
function exports(mainUrl){
	$('#exportsButton').attr('disabled','true');
	$.ajax({
		type:'post',
		url:mainUrl+'-checkExportsCount',
		data:$('#search_form').serialize(),
		success:function(data){
			if(data.success){
				var oldAction = $("#search_form").attr("action");  
				$("#search_form").attr("action",mainUrl+"-exports"); 
				$("#search_form").submit();
				$("#search_form").attr("action",oldAction); 
			}else{
				layer.alert(data.msg);
			}
			
		}
	});
}

//附件按钮
function toUploadList(modolName,mainUrl,id) {
	var title = modolName + '附件列表';
	var content = mainUrl + '-toUploadList?id='+id;
	layer.open({
        type: 2,
        title: title,
        shadeClose: true,
        shade: false,
        maxmin: true, //开启最大化最小化按钮
        area: ['893px', '600px'],
        content: content
    });
}

function showOrder(userId,userName,orderId){
//	openWindow("http://crm.tuniu.com/main.php?do=new_crm_main_tree_quick_order&order_id="+orderId);
//	var base64url=$.base64.encode('{"uid":'+userId+',"nickname":"'+userName+'","orderId":'+orderId+'}', true);
//	openWindow("http://public-api.tof.tuniu.org/tof/manage/order/redirect?"+base64url);
	$.ajax({
		type : "POST",
		url : "../../config/common/getOrderDetailPageUrl",
		data : {
			"orderId" : orderId
		},
		success : function(data) {
			openWindow(data);
		},
		error : function() {
		}
	});
}

function Trim(str,is_global)

{

    var result;

    result = str.replace(/(^\s+)|(\s+$)/g,"");

    if(is_global.toLowerCase()=="g")

    {

        result = result.replace(/\s/g,"");

     }

    return result;

}

function openDialog(title,url,size){
	var height = "";
	var width = "";
	if(size == undefined){
		height = "500px";
		width = "800px";
	}else if(size == 'L'){
		height = "700px";
		width = "1000px";
	}else if(size == 'M'){
		height = "500px";
		width = "800px";
	}else if(size == 'S'){
		height = "300px";
		width = "600px";
	}
	layer.open({
        type: 2,
        title: title,
        shadeClose: true,
        shade: false,
        maxmin: true, //开启最大化最小化按钮
        area: [width,height],
        content: url
    });
	
}

function openWinX(title, url, width, height) {
	layer.open({
        type: 2,
        shade: false,
        maxmin: true,
        title: title,
        content: url,
        area: [width+'px', height+'px']
    });
}

/*加载页面时触发的操作*/
$(function(){
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

//搜索表单进行trim处理
if($('#search_form').size()>0){
	validate = $("#search_form").validate({
		submitHandler: function(form){
			$('#search_form input').each(function(){
				$(this).val($.trim($(this).val()));
			});
			form.submit();
		}
	});
}

Date.prototype.Format = function(fmt) {
	var o = {
		"M+" : this.getMonth() + 1, //月份 
		"d+" : this.getDate(), //日 
		"h+" : this.getHours(), //小时 
		"m+" : this.getMinutes(), //分 
		"s+" : this.getSeconds(), //秒 
		"q+" : Math.floor((this.getMonth() + 3) / 3), //季度 
		"S" : this.getMilliseconds()
	//毫秒 
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}

String.prototype.replaceAll = function(s1,s2){ 
	return this.replace(new RegExp(s1,"gm"),s2); 
}

});