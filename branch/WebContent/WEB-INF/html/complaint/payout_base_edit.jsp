<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<style type="text/css">
.datatable td table {
	border:0 none;
}
.datatable td td {
	border:0 none;
}
</style>
<script type="text/javascript" src="${CONFIG.res_url}/script/jquery/plugin/jquery.form.js"></script>
<script type="text/javascript">
$(document).ready(function() { 
    var options = { 
        beforeSubmit:  check_form_submit,  // pre-submit callback 
        success:       success_function,  // post-submit callback 
    }; 
    // bind form using 'ajaxForm' 
    $('#form').ajaxForm(options); 
});

//提交表单
function check_form_submit(formData, jqForm, options){
	var form = jqForm[0];
	if(form.descript.value == '用以填写其他相关与客人的核实情况（可空）'){
		form.descript.value = '';
	}
	
	$('#button2').attr('disabled' , 'true');
	
	return true;
}

function success_function(){
	self.parent.tb_remove();
	parent.location.replace(parent.location.href);
}

//隐藏表单，与投诉信息同时提交
function form_hidden(){

	var display = "";
	var first_flag = false;
	var second_flag = false;
	//获取一级分类被选中的类型
	$("[name='type']").each(function(){
        if($(this).attr("checked")){
           
            first_flag = true;
           
            display += $(this).parent().text().trim();
            display += '-- ';
          	//获取一级分类对应二级分类被选中的类型
          	var index= this.value;
            $("[name='secondType[" + index + "]']").each(function(){
                if($(this).attr("checked")){
                	second_flag = true;
                    display += this.value.trim();
                    display += '&nbsp';
                }
            });
            
            display += '<br/>';
        }
    });
	
	if (!first_flag) {
		alert("请选择一级分类");
		return false;
	}
	if (!second_flag) {
		alert("请选择二级分类");
		return false;
	}
	var pname = ${name};
	pname = pname.toString();
	console.debug('[name="'+pname+'"]');
	//$('#payoutBase', parent.document).html(content);
	$('[name="'+pname+'"]', parent.document).val(display);
	/* self.parent.tb_remove(); */
	//当你在iframe页面关闭自身时
	var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
	parent.layer.close(index); //再执行关闭
}

</script>

</HEAD>
<BODY>
<h2>${request.formTitle}</h2>
<form name="form" id ="form" method="post" enctype="multipart/form-data"  action="">

<table class="datatable" width="100%">

  <tr>
    <th width="100" align="right">一级分类：<span class="cred">*</span></th>

    <td>
    	<table id="complaint_type_1" class="datatable" width="100%">
        	<tr>
				<c:forEach items="${payoutbaseList}" var="v" varStatus="st">
					<td width="16%">
					<label>
					<input type="checkbox" name="type" value="${v.id }" id="${v.id }"/>${v.payoutBase }
					</label>
					</td>
					<c:if test="${st.count%6==0 }"></tr><tr></c:if>
				</c:forEach>
       		 </tr>
    	</table>
    </td>

  </tr>
  <tr>
    <th align="right">二级分类：<span class="cred">*</span></th>
    <td id="complaint_type_2">
    	<c:forEach items="${twoTypeMap }" var="e" varStatus="et">
    		<table class="datatable" style="display:none;" id="tab-${et.count-1 }" width="100%">
        		<tr>
          		<td colspan="6"><span class="fb ml5">${e.key }</span></td>
        		</tr>
        		<tr>
        		<c:forEach items="${e.value }" var="v" varStatus="st">
        			<td width="16%">
        			<label><input type="checkbox" name="secondType[${v.fatherId }]" value="${v.payoutBase }"/>${v.payoutBase }</label>
              		</td>
              		<c:if test="${st.count%1==0 }"></tr><tr></c:if>
        		</c:forEach>
 				</tr>	
	      	</table>
	     </c:forEach>
    </td>

  </tr>

  <tr>
    <th></th>
    <td><input class="pd5" type="button" name="button2" id="button2" value="确定" onclick="form_hidden()"/>
   </td>
  </tr>
</table>
<script type="text/javascript">
//选中一级分类时，控制二级分类显示
$(function(){
	$('#complaint_type_1 input').each(function(i){
		$(this).click(function(){
			if ($(this).attr('checked') == "checked"){
				$('#tab-'+i).show().css({'border-bottom':'#ccc dotted 1px'});
				$('#complaint_type_2 table').hover(function(){
					$(this).find('td').css({'background':'#ffffcc'});													
				},function(){
					$(this).find('td').css({'background':'none'});	
				});
			} else {
				$('#tab-'+i).hide();
			}
		});
	});	   
});

//未保存发起投诉时再次打开投诉事宜，将投诉事宜恢复上一次确定时状态
$(function(){
	$("[name='cr.type']", parent.document).each(function(i){
		type = this.value;
		//选中一级分类
		$('#' + type).attr('checked', 'checked');
		//选中二级分类
		$("[name='cr.secondType[" + type + "]']", parent.document).each(function(i){
			secondType = this.value;
			//alert(secondType);
			$("[name='secondType[" + type + "]']").each(function(i){
				if($(this).parent().text() == secondType){
					$(this).attr('checked', 'checked');
				}
			});
		});
		//改变二级分类css样式
		$('#tab-' + (type-1)).show().css({'border-bottom':'#ccc dotted 1px'});
		$('#complaint_type_2 table').hover(function(){
			$(this).find('td').css({'background':'#ffffcc'});													
		},function(){
			$(this).find('td').css({'background':'none'});	
		});
	});
	
});
</script>
</form>
<%@include file="/WEB-INF/html/foot.jsp" %> 
