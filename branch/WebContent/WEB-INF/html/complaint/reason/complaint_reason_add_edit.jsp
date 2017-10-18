<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<style type="text/css">
.common-box {
	border: 1px solid #8CBFDE;
	margin:0 0 10px 0;
}
.common-box-hd {
	margin-top: 1px;
	padding-left: 10px;
	background: #C6E3F1;
	color: #005590;
	font-size: 14px;
	font-weight: bold;
	height: 25px;
	line-height: 25px;
	border-bottom: 1px solid #8CBFDE;
	position: relative;
}
.common-box-hd span.title2 {
	cursor: pointer;
	display: inline-block;
	line-height: 25px;
	margin-right: 15px;
}
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
    
  //提交表单
    function check_form_submit(formData, jqForm, options){
    	var first_flag = false;
    	var second_flag = false;
    	//获取一级分类被选中的类型
    	$("[name='type']").each(function(){
            if($(this).attr("checked")){
                first_flag = true;
              	//获取一级分类对应二级分类被选中的类型
              	var index= this.value;
                $("[name='secondType[" + index + "]']").each(function(){
                    if($(this).attr("checked")){
                    	second_flag = true;
                    } 
                });
            }
        });
    	
    	if(checkTypeDescript()){
    		alert("请填写投诉详情");
    		return false;
    	}
    	
    	if (!first_flag) {
    		alert("请选择一级分类");
    		return false;
    	}
    	if (!second_flag) {
    		alert("请选择二级分类");
    		return false;
    	}

    	var form = jqForm[0];
    	if(form.descript.value == '用以填写其他相关与客人的核实情况（可空）'){
    		form.descript.value = '';
    	}
    	
    	$('#button2').attr('disabled' , 'true');
    	
    	return true;
    }

    function success_function(){
    	self.parent.easyDialog.close();
    	parent.location.replace(parent.location.href);
    }

    
    
    /*选中一级分类时，控制二级分类显示*/
    $('.complaint_type_1 input').each(function(i){
		$(this).click(function(){
			if ($(this).attr('checked') == "checked"){
				if(checkTypeDescript()){
					alert("请先填写投诉详情再选择分类");
					return false;
				}
				$('table[id^="tab-"]').hide();
				$('#tab-'+i).show().css({'border-top':'#ccc solid 1px'});
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
    
    /*未保存发起投诉时再次打开投诉事宜，将投诉事宜恢复上一次确定时状态*/
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
		$('#tab-' + type).show().css({'border-bottom':'#ccc dotted 1px'});
		$('#complaint_type_2 table').hover(function(){
			$(this).find('td').css({'background':'#ffffcc'});													
		},function(){
			$(this).find('td').css({'background':'none'});	
		});
	});
	
	//投诉详情
	$('#textarea').val($("[name='cr.typeDescript']", parent.document).val());
	//备注
	$('#descript').val($("[name='cr.descript']", parent.document).val());
	
});

//隐藏表单，与投诉信息同时提交
function form_hidden(){
	var thirdPartyFlag = $("#thirdPartyFlag").attr("checked");
	var compCity = $("#compCity").val();
	if (thirdPartyFlag) {
		if (compCity=='') {
			alert("请填写第三方城市！");
			$("#compCity").focus();
			return false;
		}
	} else {
		compCity = '';
	}
	
	var typeDescript = $("#textarea").val();
	var descript = $("#descript").val();
	var content = "";
	var display = "<br/>";
	if(descript == '用以填写其他相关与客人的核实情况（可空）'){
		descript = '';
	}

	if (compCity != "") {
		display += "投诉来源第三方，城市：" + compCity 
			+ "<input type='hidden' name='entity.compCity' value='" + compCity + "'/><br>";
	}
	
	var first_flag = false;
	var second_flag = false;
	//获取一级分类被选中的类型
	$("[name='type']").each(function(){
        if($(this).attr("checked")){
            temp = "<input type='hidden' name='cr.type' value='" + this.value + "'/>";
            first_flag = true;
            content += temp; 
            display += $(this).parent().text();
            display += '-- ';
          	//获取一级分类对应二级分类被选中的类型
          	var index= this.value;
            $("[name='secondType[" + index + "]']").each(function(){
                if($(this).attr("checked")){
                	second_flag = true;
                    temp = "<input type='hidden' name='cr.secondType[" + index + "]' value='" + this.value + "'/>";
                    content += temp; 
                    display += this.value;
                    display += ',';
                }
            });
            
            display += '<br/>';
        }
    });
	content += "<input type='hidden' name='cr.typeDescript' value='" + typeDescript + "'/>";
	content += "<input type='hidden' name='cr.descript' value='" + descript + "'/>";
	display += '投诉详情:' + typeDescript;
	display += '<br/>备注:' + descript;
	
	if(checkTypeDescript()){
		alert("请填写投诉详情");
		return false;
	}
	
	if (!first_flag) {
		alert("请选择一级分类");
		return false;
	}
	if (!second_flag) {
		alert("请选择二级分类");
		return false;
	}

	$('#reason_div', parent.document).html(content);
	$('#reason_display', parent.document).html(display);
	self.parent.easyDialog.close();
}

function checkTypeDescript(){
	var isEmpty = false;
	if($('#textarea').val()==''){
		isEmpty = true;
	}
	return isEmpty;
}

function checkSpecial(){
	var s = $("#specialEventFlag").attr("checked");
	if(s == undefined){
		$("#specialEventFlag").val(0);
	}else{
		$("#specialEventFlag").val(1);
	}
}

</script>

</HEAD>
<BODY>
<c:if test="${null != complaintReason}">
<span style="color: red;">订单号[${orderId }]有未处理完成的投诉单，请直接添加投诉事宜</span><br>
<span style="color: green;">投诉单号：${complaintId }，处理人：${dealName }，交接人：${associaterName }</span>
<div class="common-box">
	<div class="common-box-hd">
		<span class="title2">投诉事宜记录</span>
	</div>
	<table width="100%" class="listtable">
		<c:forEach items="${complaintReason }" var="v">
			<tr align="center">
				<td width="130"><fmt:formatDate value="${v.addTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
				<td align="left">${v.type }-${v.secondType }</td>
				<td align="left">${v.typeDescript}</td>
				<td align="left">${v.descript }</td>
			</tr>
		</c:forEach>
	</table>
</div>
</c:if>
<form name="form" id ="form" method="post" enctype="multipart/form-data"  action="complaint_reason-saveType?complaintId=${complaintId}&orderId=${orderId}">
<input type="hidden" name="entity.complaintId" value="${complaintId}"/>
<input type="hidden" name="entity.orderId" value="${orderId}"/>
<c:if test="${null != complaintReason}">
<label><input type="checkbox" name="specialEventFlag" 
	id="specialEventFlag" onclick="checkSpecial()" value="0" <c:if test="${specialEventFlag==1}">checked</c:if>>&nbsp;&nbsp;特殊事件</label></c:if>
<label><input type="checkbox" id="thirdPartyFlag" onchange="$('#ccSpan').toggle()">&nbsp;投诉来源于第三方</label>
<span id="ccSpan" style="display: none;">，请输入第三方城市&nbsp;<input type="text" id="compCity" name="compCity"></span>
<div style="height: 5px"></div>
<div class="common-box">
	<div class="common-box-hd">
		<span class="title2">添加投诉事宜</span>
	</div>
<table class="datatable" width="100%">
  <c:forEach items="${firstTypeMap}" var="m" varStatus="st">
  	<tr>
    	<th width="18%" align="right">
	 		<c:choose>
	 			<c:when test="${m.key==1001 }">[旅行前]</c:when>
	 			<c:when test="${m.key==1002 }">[旅行中]</c:when>
	 			<c:when test="${m.key==1003 }">[旅行后]</c:when>
	 			<c:when test="${m.key==1004 }">[机票]</c:when>
	 			<c:when test="${m.key==1005 }">[交通]</c:when>
	 			<c:when test="${m.key==1133 }">[酒店]</c:when>
	 			<c:otherwise>
	 				
	 			</c:otherwise>
	 		</c:choose>一级分类：<span class="cred">*</span>
	 	</th>
 		
 		<td>
	    	<table  class="datatable complaint_type_1" width="100%">
	  			<tr>
			  		<c:forEach items="${m.value}" var="v" varStatus="st">
			  			<td width="16%">
							<label>
								<input type="radio" name="type" value="${v.id }" id="${v.id }"/>${v.name }
							</label>
						</td>
						<c:if test="${st.count%6==0 }"></tr><tr></c:if>
					</c:forEach>
				</tr>
	 		 </table>
	 	 </td>
  	</tr>
 </c:forEach>
  
  <tr>
    <th align="right">二级分类：<span class="cred">*</span></th>
    <td id="complaint_type_2">
    	<c:forEach items="${twoTypeMap }" var="e" varStatus="et">
    		<table class="datatable" style="display:none;" id="tab-${et.count-1 }" width="100%">
        		<tr>
        		<c:forEach items="${e.value }" var="v" varStatus="st">
        			<td width="16%">
        			<label><input type="radio" name="secondType[${v.fatherId }]" value="${v.name }"/>${v.name }</label>
              		</td>
              		<c:if test="${st.count%6==0 }"></tr><tr></c:if>
        		</c:forEach>
 				</tr>	
	      	</table>
	     </c:forEach>
    </td>

  </tr>
  <tr>
    <th align="right">投诉详情：<span class="cred">*</span> </th>
    <td><table>
        <tr>
          <td><textarea name="entity.typeDescript" id="textarea" cols="45" rows="2"></textarea>&nbsp;
  			<span style="color: red;">【提示】若为出游前变更，请填写变更情况说明。</span>
          </td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <th align="right">备注：</th>
    <td><textarea name="entity.descript" id="descript" cols="45" rows="4" onfocus="if(this.value=='用以填写其他相关与客人的核实情况（可空）') {this.value='';}">用以填写其他相关与客人的核实情况（可空）</textarea>
    &nbsp;<span style="color: red;">【提示】若为出游前变更，请填写处理方案及反馈时限。</span>
    </td>
  </tr>
  <tr>
    <th></th>
    <td><c:if test="${complaintId==''}"><input class="pd5" type="button" name="button2" id="button2" value="确定" onclick="form_hidden()"/></c:if>
    <c:if test="${complaintId!=''}"><input class="pd5" type="submit" name="button2" id="button2" value="确定"/></c:if></td>
  </tr>
</table>
</div>
</form>
<span style="color:red;font-weight:bold">“旅行前”“旅行中”“旅行后”为客人反映内容发生的时间点状态，请在勾时注意。
例：客人出游归来后，反映导游在出游过程中无讲解。虽然客人是旅游后来电，但反映的问题发生时间点是旅游中的，故择大类为”旅游中“。</span>
</BODY>
