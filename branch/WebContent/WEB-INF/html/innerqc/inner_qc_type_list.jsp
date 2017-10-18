<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<script language="javascript" src="${CONFIG.res_url}script/jquery/jquery-ui.min.js"></script>
<link rel="stylesheet" href="${CONFIG.res_url}script/jquery/css/jquery-ui.min.css"/>
<style type="text/css">
<!-- tr变黄色 -->
.trbg td{background:#ff9;}
.trbg1 td{background:#87CEFF;}
.listtable tr.yellowbg td{background:#FFFF99}
.listtable tr td.orange{background:orange;}
</style>
<script type="text/javascript">
<!--
$(function(){
	$(".nameTd").dblclick(function() { // 注册双击事件
		bindEdit($(this));
	});
});

function bindEdit($td) {
    // 检测此td是否已经被替换了，如果被替换直接返回
    if ($td.children("input").length > 0) {
        return false;
    }
    var text = $td.text();
    var $input = $("<input type='text'>");
    $input.val(text);
    $td.html("");
    $td.append($input);
    $input.focus();

    $input.click(function(event) {
		event.stopPropagation();
	});

    $input.keyup(function(event) {
        if (event.which == 13) { // 按回车提交，将td中的内容修改成获取的value值
        	var value = $input.val();
        	if (value != text) {
    	    	var idStr = $td.attr("id");
    	    	var id = idStr.substring(7, idStr.length);
    	    	$.ajax({
    	    		type: "POST",
    	    		url: "inner_qc_type-update",
    	    		data: {"entity.id":id, "entity.name":value},
    	    		async: false,
    	    		success: function(data) {
    	    			$td.html(value);
    	    		} 
    	    	});
        	} else {
        		$td.html(value);
        	}
        }
    });

    $input.keyup(function(event) {
        if (event.which == 27) { // 按ESC，将td中的内容还原
        	$td.html(text);
        }
    });
}

function addRow_type(){
	var data_json = [ {} ];
	$.tmpl.add_row("tr_type", data_json);
}

function addType(obj, event) {
	if (event.which == 13) { // 按回车提交，将td中的内容修改成获取的value值
		var value = $(obj).val();
		if (value != '') {
	    	$.ajax({
	    		type: "POST",
	    		url: "inner_qc_type-add",
	    		data: {"entity.name":value},
	    		async: false,
	    		success: function(data) {
	    			var id = jQuery.parseJSON(data);
	        		var $td = $(obj).parent();
	        		$td.html(value);
	        		$td.attr("id", "nameTd_"+id);
	    			$td.dblclick(function() { // 注册双击事件
	        			bindEdit($td);
	        		});
	    			$td.next().html("");
	    		}
	    	});
		}
    }
}
//-->
</script>
</HEAD>
<BODY>
<p style="color: blue;">提示：双击单元格进入编辑状态，按ESC键还原，按回车键提交；提交后的数据不支持删除。</p>
<table class="listtable" width="260">
<tr>
	<th>质检事宜类型</th>
	<th><input type="button" class="blue" value="添加" onclick="addRow_type()"></th>
</tr>
<c:forEach items="${typeList}" var="type">
<tr>
	<td class="nameTd" id="nameTd_${type.id}">${type.name}</td>
	<td></td>
</tr>
</c:forEach>
<tr id="tr_type" style="display: none">
	<td><input type="text" onkeyup="addType(this, event)"></td>
	<td align="center">
		<input type="button" onclick="$(this).parent().parent().remove();" value="删除">
	</td>
</tr>
</table>
</BODY>
