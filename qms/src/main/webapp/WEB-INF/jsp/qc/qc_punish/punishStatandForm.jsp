<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/prefix.jsp" %>
<html>
<head>
<style type="text/css">

* {font-size:13px;font-family:Arial;}
#nav li {list-style:none; width:120px; margin-top:0;padding:4px 6px;}
#nav li a {text-decoration:none; color:#666;}
#nav li a:hover {color:#333;}
.aa {border-right:1px solid #AAC7E9; background:#E8F5FE;cursor:hand;border-top:3px solid #AAC7E9}
.bb {border:1px solid #AAC7E9;background:#FFFFDD;cursor:hand;border-top:3px solid #ff9900;}
.cc {border-top:4px solid #ff66ff; background:#fcf;cursor:hand;}
.list2  { font-size:13px; line-height:20px; padding:3px;text-align:left;background:#FFFFFF;}
.list2   li{ color:#555;font-size:13px; line-height:24px; padding:0 0 0 10px;}
.list2    a{ text-decoration: underline;}
.lfloat {float:left;}
.rfloat {float:right;}
.ctt{padding:0;clear:both;border-top:1px solid #AAC7E9;border-right:1px solid #AAC7E9;border-bottom:1px solid #AAC7E9;text-align:left;height:307px;}
.dis{display:block;}
.undis{display:none;}
li{list-style: none;}
form, ul { padding:0; margin:0;}

</style>
<script type="text/javascript">
$(function() {
	
	$('#chkAll').click(function() {
		var flag = this.checked;
		$('.listtable td :checkbox[name = standardIds]').each(function() {
			this.checked = flag;
		});
	});
});

function openWinow(title, url, width, height) {
	parent.layer.open({
        type: 2,
        shade : [0.5 , '#000' , true],
        maxmin: true,
        title: title,
        content: url,
        area: [width+'px', height+'px']
    });
}

function savePunish(input){
	
	if ($(':checkbox[name="standardIds"]:checked').length <= 0) {
		layer.alert("请至少选择一个处罚依据！", {
			icon : 2
		});
		return false;
	}
	//var punishId =$('#punishId').val();
	var punishObj =$('#punishObj').val();
	disableButton(input);
	$.ajax( {
			url : 'qc/punishStandard/addPunish',
			data : 	$('#punish_form').serialize(),
			type : 'post',
			dataType:'json',
			cache : false,
			success : function(result) {
				enableButton(input);
				if(result)
				{
			    	if(result.retCode == "0")
					{
			    	     if(punishObj==1){
			    	    	 
			    	    	 parent.refresh();
		 			    	 parent.layer.closeAll();
			    	    	 
			    	     }else if(punishObj==2){
			    	    	 
			    	    	 parent.refresh();
		 			    	 parent.layer.closeAll();
			    	     }
			    		
			    		
					 }else{
						layer.alert(result.resMsg, {icon: 2});
					}
			     }
			 }
		    });
	
}

function g(o){
	return document.getElementById(o);
	}
function hoverli(n){
	var size =  $('#levelSize').val();
	
   for(var i=0;i<size;i++){
	   g('tb_'+i).className='aa';
	   g('tbc_0'+i).className='undis';
	   }
   g('tbc_0'+n).className='list2';
   g('tb_'+n).className='bb';
}
function fun(){
	hoverli(0);
}

</script>
<body>
<form name="punish_form" id="punish_form" method="post" action="" >
<input type="hidden" name="punishId"  id="punishId" value="${punishId}">
<input type="hidden" name="punishObj"  id="punishObj" value="${punishObj}">
<input type="hidden" name="levelSize"  id="levelSize" value="${levelSize}">
<input type="button" style="width:100px;height:50px"   name ="selectBtn" id="selectBtn" value="提交" class="blue" onclick="savePunish(this)">
	
<div style="width:600px">
 <div class="lfloat" style="width:126px">
 <ul id="nav">
 <c:forEach items="${levelList}" var="inner" varStatus="st"> 
 <c:if test="${st.index==0}">
 <li class="bb" id="tb_${st.index}" onClick="i:hoverli(${st.index});">${inner}</li>
 </c:if>
 <c:if test="${st.index!=0}">
 <li class="aa" id="tb_${st.index}" onClick="x:hoverli(${st.index});">${inner}</li>
 </c:if>

 </c:forEach>
 </ul>
 </div>
 <div class="lfloat" style="width:450px;">
  <div id="newinfo">
    <div class="ctt list2">
    <c:forEach items="${psList}" var="inner" varStatus="inst"> 
	   <c:if test="${inst.index ==0 }">
	    <div class="dis" id="tbc_0${inst.index}">
	      <table width="100%" class="listtable">
			<tr>
			<th><input type="checkbox" id="chkAll" title="全选"></th>
			<th>处罚等级</th>
			<th>分级标准描述</th>
			<th>经济处罚</th>
			<th>记分处罚</th>
			</tr>
			<c:forEach items="${inner}" var="v">
			<tr align="center">
				<td width="40"><input type="checkbox" name="standardIds" value="${v.id }"/></td>
				<c:if test="${v.redLineFlag == 1}">
				  <td width="85" style="color: red;">${v.level}(红线)</td>
				</c:if>
				<c:if test="${v.redLineFlag == 0}">
				  <td width="85">${v.level}(非红线)</td>
				</c:if>
				<td class="left">${v.description}</td>
				<td width="80">${v.economicPunish }</td>
				<td width="70">${v.scorePunish}</td>
			</tr>
			</c:forEach>
		</table>
	    </div>
	    </c:if>
	    <c:if test="${inst.index !=0 }">
		  <div class="undis" id="tbc_0${inst.index}">
	      <table class="listtable">
			<tr>
			<th><input type="checkbox" id="chkAll" title="全选"></th>
			<th>处罚等级</th>
			<th>分级标准描述</th>
			<th>经济处罚</th>
			<th>记分处罚</th>
			</tr>
			<c:forEach items="${inner}" var="v">
			<tr align="center">
				<td width="40"><input type="checkbox" name="standardIds" value="${v.id }"/></td>
				<c:if test="${v.redLineFlag == 1}">
				  <td width="85" style="color: red;">${v.level}(红线)</td>
				</c:if>
				<c:if test="${v.redLineFlag == 0}">
				  <td width="85">${v.level}(非红线)</td>
				</c:if>
				<td  width="200" >${v.description}</td>
				<td  width="85">${v.economicPunish }</td>
				<td  width="85">${v.scorePunish}</td>
			</tr>
			</c:forEach>
		</table>
	    </div>
	    </c:if>
   </c:forEach>
    </div>
    </div>
 </div>
</div>
	 
	
</form>
</body>
</html>