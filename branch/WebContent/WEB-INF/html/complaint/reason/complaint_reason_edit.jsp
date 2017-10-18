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

.datatable .cmpPerson, .callBackTime {
	width: 160px;
	font-size: 12px;
	font-family: Arial, "微软雅黑";
}

.datatable .cmpPhone {
	width: 160px;
}
</style>
<script type="text/javascript" src="${CONFIG.res_url}/script/jquery/plugin/jquery.form.js"></script>
<script type="text/javascript">

var callBackTime;//回呼时间

$(document).ready(function() { 
    var orginalTime = '${orginalTime}';
	$(".callBackTime").val(orginalTime);
	orginalTime = orginalTime.replaceAll("-","/");
	callBackTime = new Date(orginalTime);

	var addtimer = setInterval(dateAddBySecond, 1000);
	$(".callBackTime").one("focus", function() {
		clearInterval(addtimer);
	});
	
	$(".submitBtn").unbind("click").bind("click",submitReason);
});

function dateAddBySecond() {
	callBackTime.setSeconds(callBackTime.getSeconds() + 1);
	var dateStr = callBackTime.Format("yyyy-MM-dd hh:mm:ss");
	$(".callBackTime").val(dateStr);
}


function submitReason(){
	//验证提交信息
	if(check_form_submit()){
		$('#button2').attr('disabled' , 'true');
		$("#isSubmit").val(1);
		
		var url = $("#form").attr("action");
		$.ajax({
			url : url,
			data : $('#form').serialize(),
			type : 'post',
			async : true,
			success : function(data) {
				//关闭弹出框
		    	top.$(".layui-layer").hide();
		    	try{
					//调用新增列表页面的查询方法
			    	parent.parent.onSearchClicked();
				}catch(e){//调用失败则刷新父页面
					parent.location.replace(parent.location.href);
				}
			}
		});
	}
}

//检验提交信息
function check_form_submit() {
	var thirdPartyFlag = $("#thirdPartyFlag").attr("checked");
	var compCity = $("#compCity").val();
	if (thirdPartyFlag) {
		if (compCity=='') {
			layer.alert("请填写第三方城市！", {
				icon : 2
			});
			$("#compCity").focus();
			return false;
		}
	} else {
		$("#compCity").val('');
	}
	
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
		layer.alert("请填写投诉详情", {
			icon : 2
		});
		return false;
	}
	
	if (!first_flag) {
		layer.alert("请选择一级分类", {
			icon : 2
		});
		return false;
	}
	if (!second_flag) {
		layer.alert("请选择二级分类", {
			icon : 2
		});
		return false;
	}
	if($(".cmpPerson").size()>0){
		var cmpPerson = $('.cmpPerson').val();
		var cmpPhone = $('.cmpPhone').val();
		var callBackTime = $('.callBackTime').val();
		if ($.trim(cmpPerson) == '') {
			layer.alert("请填写投诉人姓名", {
				icon : 2
			});
			return false;
		}
		if ($.trim(cmpPhone) == '') {
			layer.alert("请填写投诉人电话", {
				icon : 2
			});
			return false;
		}
		if ($.trim(callBackTime) == '') {
			layer.alert("请选择回呼时间", {
				icon : 2
			});
			return false;
		}
		var originalUrl =$("#form").attr("action");
		var newUrl=originalUrl.replace("saveType","saveTypeAndAndTask")
		$("#form").attr("action",newUrl);
	}
	return true;
}

/*检查投诉详情是否填写*/
function checkTypeDescript(){
	var isEmpty = false;
	if($('#textarea').val()==''){
		isEmpty = true;
	}
	return isEmpty;
}

function success_function(){
	parent.location.replace(parent.location.href);
}

//隐藏表单，与投诉信息同时提交
function form_hidden(){
	var typeDescript = $("#textarea").val();
	var descript = $("#descrip").val();
	var content = "";
	var display = "<br/>";
	if(descript == '用以填写其他相关与客人的核实情况（可空）'){
		descript = '';
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
	
	if (!first_flag) {
		layer.alert("请选择一级分类", {
			icon : 2
		});
		return false;
	}
	if (!second_flag) {
		layer.alert("请选择二级分类", {
			icon : 2
		});
		return false;
	}
	
	$('#reason_div', parent.document).html(content);
	$('#reason_display', parent.document).html(display);
	self.parent.tb_remove();
}
//选中一级分类时，控制二级分类显示
$(function(){
	$('.complaint_type_1 input').each(function(i){
		$(this).click(function(){
			if ($(this).attr('checked') == "checked"){
				$(this).attr('checked','checked'); 
				if(checkTypeDescript()){
					layer.alert("请先填写投诉详情再选择分类", {
						icon : 2
					});
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
	$('#complaint_type_2 input').each(function(){
		$(this).click(function(){
			if ($(this).attr('checked') == "checked"){
				$(this).attr('checked','checked'); 
			} 
		});
	});	   
});

function deleteButton(index){
	var id = $("#forDid"+index).val();
	var flag = $("#flag"+index).val();
	if(flag!=0){
		layer.alert("请先保存修改记录！", {
			icon : 2
		});
		return;
	}
	confirm_ = confirm("确定删除?");
    if(confirm_){
        $.ajax({
            type:"POST",
            url:"complaint_reason-toEditDeleteHis?id=" + id + "&flag="+flag,
            success:function(msg){
                $("#tr_"+id).remove();
            }
        });
    }
}
function editButton(index){
	var id = $("#forDid"+index).val();
	var flag = $("#flag"+index).val();
	if(flag==0){//可编辑
		$("#page_type"+index).attr("disabled",false);
		$("#page_secondType"+index).attr("disabled",false);
		/* $("#typeDescript"+index).attr("disabled",false); */
		/* $("#descript"+index).attr("disabled",false); */
		$("#flag"+index).val(1);
		$("#editButton"+index).val("保存");
	}else if(flag==1){
		//todo 保存ajax
		var type = $("#page_type"+index ).find("option:selected").text();
		var secondType = $("#page_secondType"+index ).find("option:selected").text();
		var typeDescript = $("#typeDescript"+index).val();
		var descript = $("#descript"+index).val();
            $.ajax({
                type:"POST",
                url:"complaint_reason-toEditDeleteHis",
                data:{"id":id,"flag":flag,"type":type,"secondType":secondType,"typeDescript":typeDescript,"descript":descript},
                success:function(data){
                	alert("保存成功！");
        			$("#flag"+index).val(0);
        			$("#editButton"+index).val("编辑");
        			$("#page_type"+index).attr("disabled",true);
        			$("#page_secondType"+index).attr("disabled",true);
        			/* $("#typeDescript"+index).attr("disabled",true); */
        			/* $("#descript"+index).attr("disabled",true); */
                }
            });
        //}
	}
}

function showSecondType(index){
	var type = "page_type"+index;
	var secType = "page_secondType"+index;
	var id = $("#"+type).val();

	$.ajax({
        type:"POST",
        url:"complaint_reason-toSearchHis",
        data:"id=" + id,
        success:function(json){
        	var data=jQuery.parseJSON(json);
        	$("#"+secType).empty();
        	for(var i=0;i<data.length;i++){
        		var name = data[i].name;
        		var fatherId = data[i].fatherId;
        		$("#"+secType).append("<option value='"+fatherId+"'>"+name+"</option>");  
        	}
        }
    });
}
</script>

</HEAD>
<BODY>
<span style="color: green;">订单号：${orderId }，投诉单号：${complaintId }，处理人：${dealName }，交接人：${associaterName }</span>
<div class="common-box">
	<div class="common-box-hd">
		<span class="title2">投诉事宜记录</span>
	</div>
	<table width="100%" class="listtable">
		<c:forEach items="${complaintReason }" var="v" varStatus="status">
			<tr align="center" id="tr_${v.id }">
				<td width="130"><fmt:formatDate value="${v.addTime}" pattern="yyyy-MM-dd HH:mm:ss" />
				<input type="hidden" id="forDid${status.index + 1}" name="forDid${status.index + 1}" value="${v.id }"/>
				<input type="hidden" id="flag${status.index + 1}" name="flag${status.index + 1}" value="0"/>
				</td>
				
				
				<td align="left">
				<select id="page_type${status.index + 1}" name="page.type" disabled onchange="showSecondType(${status.index + 1});">
			    	<option value=""></option>
					<c:forEach items="${allFirstReasonTypeList}" var="vv" varStatus="st">
						<option value="${vv.id }" <c:if test="${v.type  == vv.name }">selected</c:if>>${vv.name }</option>
					</c:forEach>
			    </select>
			    <select id="page_secondType${status.index + 1}" name="page.secondType" disabled>
			    	<option value=""></option>
					<c:forEach items="${twoTypeMap }" var="e" varStatus="et">
       					<c:forEach items="${e.value }" var="vv" varStatus="st">
       						<option value="${vv.fatherId }" <c:if test="${v.secondType  == vv.name }">selected</c:if>>${vv.name }</option>
       					</c:forEach>
    				</c:forEach>
			    </select></td>
			    
				<td align="left"><textarea id="typeDescript${status.index + 1}" style="font-size:14px" name="typeDescript${status.index + 1}" disabled  rows="2" title="${v.typeDescript}">${v.typeDescript}</textarea></td>
				<td align="left"><textarea id="descript${status.index + 1}" style="font-size:14px" name="descript${status.index + 1}" disabled  rows="2" title="${v.descript}">${v.descript}</textarea></td>
				<td width="130"><input class="pd5" type="button" name="editButton${status.index + 1}" id="editButton${status.index + 1}" value="编辑" onclick="editButton(${status.index + 1});"/>
								<input class="pd5" type="button" name="deleteButton${status.index + 1}" id="deleteButton${status.index + 1}" value="删除" onclick="deleteButton(${status.index + 1});"/></td>
			</tr>
		</c:forEach>
	</table>
</div>
<h2>${request.formTitle}</h2>
<form name="form" id ="form" method="post" enctype="multipart/form-data"  action="complaint_reason-saveType?complaintId=${complaintId}&orderId=${orderId}">
<input type="hidden"  id="isSubmit" value="0" />
<input type="hidden" name="entity.complaintId" value="${complaintId}"/>
<input type="hidden" name="entity.orderId" value="${orderId}"/>
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
			<table class="datatable complaint_type_1" width="100%">
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
       							<input type="radio" name="secondType[${v.fatherId }]" value="${v.name }" />${v.name }
             					</td>
             					<c:if test="${st.count%6==0 }"></tr><tr></c:if>
       					</c:forEach>
						</tr>	
     				</table>
    			</c:forEach>
  			</td>
	</tr>
	<c:if test="${needWriteCallBack!=null}">
	<tr>
			<th align="right">投诉人姓名：<span class="cred">*</span></th>
			<td><input type="text" name="cmpPerson" class="cmpPerson"
			maxlength="30" value="${cmpPerson }" /></td>
	</tr>
	<tr>
			<th align="right">投诉人电话：<span class="cred">*</span></th>
			<td><input type="text" name="cmpPhone" class="cmpPhone"
			maxlength="20" value="${cmpPhone}" /></td>
	</tr>
	<tr>
			<th align="right">要求回呼时间：<span class="cred">*</span></th>
			<td><input type="text"
				class="MyWdate datePiker callBackTime" name="callBackTime"
				onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"><span
				style="color: red; margin-left: 6px;">若未指定回呼时间，默认为30分钟</span></td>
	</tr>
	</c:if>
	<tr>
  			<th align="right">投诉详情：<span class="cred">*</span> </th>
  			<td ><textarea name="entity.typeDescript" id="textarea" cols="45" rows="2"></textarea></td>
	</tr>
	<tr>
  			<th align="right">备注：</th>
  			<td><textarea name="entity.descript" id="descrip" cols="45" rows="4"></textarea></td>
	</tr>
	 <tr>
  			<th>&nbsp;</th>
  			<td><c:if test="${complaintId=='' }"><input class="pd5" type="button" name="button1" id="button1" value="确定" onclick="form_hidden()"/></c:if>
  			<c:if test="${complaintId!='' }"><input class="pd5 submitBtn" type="button" name="button2" id="button2" value="确定"/></c:if></td>
	 </tr>
</table>
</div>
</form>
<span style="color:red;font-weight:bold">“旅行前”“旅行中”“旅行后”为客人反映内容发生的时间点状态，请在勾时注意。
例：客人出游归来后，反映导游在出游过程中无讲解。虽然客人是旅游后来电，但反映的问题发生时间点是旅游中的，故择大类为”旅游中“。</span>
</BODY>
