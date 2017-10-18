<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp" %>
<style type="text/css">
.listtable td{text-align:center;background:none}
.listtable th{background-color:#4C68A2;color:white;font-weight:bold}
.hidden{	display:none;} 

img{
	vertical-align:middle;
}


img:hover{
	cursor: pointer;
}
</style>
<script type="text/javascript">
		const shrinkImgSrc= "${CONFIG.res_url}images/icon/tree/nolines_plus.gif";
		const expandImgSrc= "${CONFIG.res_url}images/icon/tree/nolines_minus.gif";
		const deptLevelPre = 'dept_level_';
		
		$(function(){
			markMaxOrMinData();
			$('tr[class^=dept_level_]').addClass('hidden');
			$('.dept_level_1').removeClass('hidden');
			$('img').attr('src',shrinkImgSrc).click(
					function(){
						toggleData(this);
					});
			
		});
		
		function markMaxOrMinData(){
			var totalItems = [{col:2,type:'min'},{col:3,type:'max'},{col:8,type:'max'},{col:9,type:'max'}]; //col：列数    type：统计类型
			for(var deptLevel=1;deptLevel<4;deptLevel++){ //各部门层级分别计算
				$('.'+deptLevelPre+deptLevel).each(function(){
					for(index in totalItems){
						var tdCells = getChildren(this,deptLevel).children('td:nth-child('+totalItems[index].col+')');
						computeMaxOrMinData(tdCells,totalItems[index].type);
					}
				});
			}
			$('.max,.min').css({"background-color":"pink"}); //最大最小项标粉
		}
		
		function toggleData(img){
			//拿到class,确定当前操作类型
			var imgClass = $(img).attr('class');
			var parentTr = $(img).parent().parent();
			//拿到level
			var deptLevel = $(parentTr).attr('class').substr(-1);
			
			if(imgClass=='expand'){ //如果当前为展开状态，则收缩所有子孙节点
				$(img).attr('src',shrinkImgSrc).removeClass('expand'); //改变当前图片
				getDescendant(parentTr,deptLevel).addClass("hidden"); //所有子节点都隐藏
			}else {
				$(img).attr({'src':expandImgSrc}).addClass('expand');
				/* 展示隐藏的下级部门 */
				showHideChild(img);
			}
		}
		
		/* 展示隐藏的下级部门 */
		function showHideChild(img){
			var imgClass = $(img).attr('class');
			var parentTr = $(img).parent().parent();
			var deptLevel = $(parentTr).attr('class').substr(-1);
			getChildren(parentTr,deptLevel).removeClass("hidden");
			
			getChildren(parentTr,deptLevel).find('.expand').each(function(){
				showHideChild(this);
			});
			
		}
			
		/* 获取子孙部门 */
		function getDescendant(ancestorTr,deptLevel){
			var trs = $('tr[id^="'+$(ancestorTr).attr('id')+'_"]');
			return trs;
			
		}

		/* 获取子部门 */
		function getChildren(parentTr,deptLevel){
			var trs = $('tr[id^="'+$(parentTr).attr('id')+'_"][class*=dept_level_'+(deptLevel-0+1)+']');
			return trs;
		}

		function computeMaxOrMinData(tdCells,type){
			if(type=='min'){
				var min = 100;
				$(tdCells).each(function(){
					var data = $.trim($(this).text());
					if(parseFloat(data)<min){
						min = parseFloat(data);
					}
				});
				
				$(tdCells).each(function(index){
					var data = $.trim($(this).text());
					if(min!=100&&parseFloat(data)==min){
						$(this).addClass("min");
					}
				});	
				
			}else{
				var max = 0;
				$(tdCells).each(function(){
					var data = $.trim($(this).text());
					if(parseFloat(data)>max){
						max = parseFloat(data);
					}
				});
				
				$(tdCells).each(function(){
					var data = $.trim($(this).text());
					if(max!=0&&parseFloat(data)==max){
						$(this).addClass("max");
					}
				});	
			}
		}
		
		function getDetail(businessUnitName,departmentName,groupName,dealName,score,unsatisfyReason){
			var oldDealName = dealName;
			businessUnitName=encodeURI(encodeURI(businessUnitName));
			departmentName=encodeURI(encodeURI(departmentName));
			groupName=encodeURI(encodeURI(groupName));
			dealName =encodeURI(encodeURI(dealName));
			var returnVisitDateBgn = $('#addTimeBgn').val();
			var returnVisitDateEnd = $('#addTimeEnd').val();
			
			var url =  "postSaleReturnVisit-getDetail?businessUnitName="+businessUnitName+"&departmentName="+departmentName+"&groupName="+groupName+"&dealName="+dealName+"&score="+score;
			if(addTimeBgn){
				url = url+'&returnVisitDateBgn='+returnVisitDateBgn;
			}
			
			if(addTimeEnd){
				url = url+'&returnVisitDateEnd='+returnVisitDateEnd;
			}
			
			if(unsatisfyReason){
				url = url + "&unsatisfyReason="+unsatisfyReason;
			}
			
			openDialog('数据详情['+oldDealName+']',url);
		}
		
</script>
</HEAD>
<BODY>
<div class="top_crumbs">您当前所在的位置：投诉质检系统&gt;&gt;<span class="top_crumbs_txt">售后短信回访统计报表</span></div>
<form name="form" id="search_form" method="post" enctype="multipart/form-data" action="postSaleReturnVisit-getReport">
	<div class="pici_search pd5 mb10">
		<label class="mr10">统计时间：
            <input id="addTimeBgn" type="text"  name="vo.returnVisitDateBgn" value="${vo.returnVisitDateBgn}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'addTimeEnd\')}'})" readOnly="readonly"/>
            至
            <input id="addTimeEnd" type="text"  name="vo.returnVisitDateEnd" value="${vo.returnVisitDateEnd}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'addTimeBgn\')}'})" readOnly="readonly"/>
        </label>
        <label class="mr10">
            <input type="submit" size="10"  value="查询"  class="blue"/>
        </label>
	</div>
</form>

<br/>
<p>数据说明：</p>
<p>1）服务不满意率：（态度不满数+跟进不满数）/（不满意数）*100%</p>
<p>2）方案不满意率：（方案不满数/不满意数）*100%</p>
<p>3）综合满意率：总得分/（总评次数*3）*100%</p>
<br/>
	<table width="100%" class="listtable">
		<tr>
			<th >部门</th>
			<th >综合<br/>满意率</th>
			<th >服务<br/>不满意率</th>
			<th >方案<br/>不满意率</th>
			<th >满意<br/>（3分）</th>
			<th >一般<br/>（2分）</th>
			<th >不满意<br/>（0分）</th>
			<th >态度<br/>不满意</th>
			<th >方案<br/>不满意</th>
			<th >跟进<br/>不及时</th>
			<th >专业程度<br/>不满意</th>
			<th >无不满意<br/>原因</th>
		</tr>
		<c:forEach items = "${dataList}" var = "businessUnitVo" varStatus="level1Status">
		<tr style="background-color:#9EB6E4;" class="dept_level_1" id="dept_${level1Status.count}">
			<td style="text-align:left">
				<img>
				${businessUnitVo.name}
			</td>
			<td title='综合满意率'>${businessUnitVo.data.comprehensiveSatisfaction }</td>
			<td title='服务不满意率'>${businessUnitVo.data.serviceSatisfaction }</td>
			<td title='方案不满意率'>${businessUnitVo.data.planSatisfaction }</td>
			<td title='满意（3分）'>${businessUnitVo.data.satisfactionCount }</td>
			<td title='一般（2分）'>${businessUnitVo.data.normalCount }</td>
			<td title='不满意（0分）'>${businessUnitVo.data.unsatisfactionCount }</td>
			<td title='态度不满意'>${businessUnitVo.data.attitudeReason }</td>
			<td title='方案不满意'>${businessUnitVo.data.planReason }</td>
			<td title='跟进不及时'>${businessUnitVo.data.followNotInTimeReason }</td>
			<td title='专业程度不满意'>${businessUnitVo.data.notProfessionalReason }</td>
			<td title='无不满意原因'>${businessUnitVo.data.noReason }</td>
		</tr>
		<c:forEach items = "${businessUnitVo.children}" var = "departmentVo" varStatus="level2Status">
		<tr style="background-color:#B8C8EE;" class="dept_level_2" id="dept_${level1Status.count}_${level2Status.count}">
			<td style="text-align:left">&nbsp;&nbsp;&nbsp;&nbsp;
				<img/>
				${departmentVo.name}
			</td>
			<td title='综合满意率'>${departmentVo.data.comprehensiveSatisfaction }</td>
			<td title='服务不满意率'>${departmentVo.data.serviceSatisfaction }</td>
			<td title='方案不满意率'>${departmentVo.data.planSatisfaction }</td>
			<td title='满意（3分）'>${departmentVo.data.satisfactionCount }</td>
			<td title='一般（2分）'>${departmentVo.data.normalCount }</td>
			<td title='不满意（0分）'>${departmentVo.data.unsatisfactionCount }</td>
			<td title='态度不满意'>${departmentVo.data.attitudeReason }</td>
			<td title='方案不满意'>${departmentVo.data.planReason }</td>
			<td title='跟进不及时'>${departmentVo.data.followNotInTimeReason }</td>
			<td title='专业程度不满意'>${departmentVo.data.notProfessionalReason }</td>
			<td title='无不满意原因'>${departmentVo.data.noReason }</td>
		</tr>
		<c:forEach items = "${departmentVo.children}" var = "groupVo" varStatus="level3Status">
		<tr style="background-color:#C6DAF8;" class="dept_level_3" id="dept_${level1Status.count}_${level2Status.count}_${level3Status.count}">
			<td style="text-align:left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<img>
				${groupVo.name}
			</td>
			<td title='综合满意率'>${groupVo.data.comprehensiveSatisfaction }</td>
			<td title='服务不满意率'>${groupVo.data.serviceSatisfaction }</td>
			<td title='方案不满意率'>${groupVo.data.planSatisfaction }</td>
			<td title='满意（3分）'>${groupVo.data.satisfactionCount }</td>
			<td title='一般（2分）'>${groupVo.data.normalCount }</td>
			<td title='不满意（0分）'>${groupVo.data.unsatisfactionCount }</td>
			<td title='态度不满意'>${groupVo.data.attitudeReason }</td>
			<td title='方案不满意'>${groupVo.data.planReason }</td>
			<td title='跟进不及时'>${groupVo.data.followNotInTimeReason }</td>
			<td title='专业程度不满意'>${groupVo.data.notProfessionalReason }</td>
			<td title='无不满意原因'>${groupVo.data.noReason }</td>
		</tr>
		<c:forEach items = "${groupVo.children}" var = "dealPersonVo" varStatus="level4Status">
		<tr id="dept_${level1Status.count}_${level2Status.count}_${level3Status.count}_${level4Status.count}" class="dept_level_4">
			<td style="text-align:left;background-color:#E6EEFC;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				${dealPersonVo.name }
			</td>
			<td title='综合满意率'>${dealPersonVo.data.comprehensiveSatisfaction }</td>
			<td title='服务不满意率'>${dealPersonVo.data.serviceSatisfaction }</td>
			<td title='方案不满意率'>${dealPersonVo.data.planSatisfaction }</td>
			<td title='满意（3分）'>${dealPersonVo.data.satisfactionCount }</td>
			<td title='一般（2分）'>
				<c:if test="${dealPersonVo.data.normalCount!=0 }">
					<a href="javascript:;"  onclick="getDetail('${businessUnitVo.name }','${ departmentVo.name}','${groupVo.name }','${dealPersonVo.name}',2)">${dealPersonVo.data.normalCount }</a>
				</c:if>	
				<c:if test="${dealPersonVo.data.normalCount==0 }">
					${dealPersonVo.data.normalCount }
				</c:if>	
			</td>
			<td title='不满意（0分）'>
				<c:if test="${dealPersonVo.data.unsatisfactionCount!=0 }">
					<a href="javascript:;"  onclick="getDetail('${businessUnitVo.name }','${ departmentVo.name}','${groupVo.name }','${dealPersonVo.name}',0)">${dealPersonVo.data.unsatisfactionCount }</a>
				</c:if>	
				<c:if test="${dealPersonVo.data.unsatisfactionCount==0 }">
					${dealPersonVo.data.unsatisfactionCount }
				</c:if>	
			</td>
			<td title='态度不满意'>
				<c:if test="${dealPersonVo.data.attitudeReason!=0 }">
					<a href="javascript:;"  onclick="getDetail('${businessUnitVo.name }','${ departmentVo.name}','${groupVo.name }','${dealPersonVo.name}',0,1)">${dealPersonVo.data.attitudeReason }</a>
				</c:if>	
				<c:if test="${dealPersonVo.data.attitudeReason==0 }">
					${dealPersonVo.data.attitudeReason }
				</c:if>	
			</td>
			<td title='方案不满意'>
				<c:if test="${dealPersonVo.data.planReason!=0 }">
					<a href="javascript:;"  onclick="getDetail('${businessUnitVo.name }','${ departmentVo.name}','${groupVo.name }','${dealPersonVo.name}',0,3)">${dealPersonVo.data.planReason }</a>
				</c:if>	
				<c:if test="${dealPersonVo.data.planReason==0 }">
					${dealPersonVo.data.planReason }
				</c:if>	
			</td>
			<td title='跟进不及时'>
				<c:if test="${dealPersonVo.data.followNotInTimeReason!=0 }">
					<a href="javascript:;"  onclick="getDetail('${businessUnitVo.name }','${ departmentVo.name}','${groupVo.name }','${dealPersonVo.name}',0,2)">${dealPersonVo.data.followNotInTimeReason }</a>
				</c:if>	
				<c:if test="${dealPersonVo.data.followNotInTimeReason==0 }">
					${dealPersonVo.data.followNotInTimeReason }
				</c:if>	
			</td>
			<td title='专业程度不满意'>
				<c:if test="${dealPersonVo.data.notProfessionalReason!=0 }">
					<a href="javascript:;"  onclick="getDetail('${businessUnitVo.name }','${ departmentVo.name}','${groupVo.name }','${dealPersonVo.name}',0,4)">${dealPersonVo.data.notProfessionalReason }</a>
				</c:if>	
				<c:if test="${dealPersonVo.data.notProfessionalReason==0 }">
					${dealPersonVo.data.notProfessionalReason }
				</c:if>	
			</td>
			<td title='无不满意原因'>
				<c:if test="${dealPersonVo.data.noReason!=0 }">
					<a href="javascript:;"  onclick="getDetail('${businessUnitVo.name }','${ departmentVo.name}','${groupVo.name }','${dealPersonVo.name}',0,0)">${dealPersonVo.data.noReason }</a>
				</c:if>	
				<c:if test="${dealPersonVo.data.noReason==0 }">
					${dealPersonVo.data.noReason }
				</c:if>	
			</td>
		</tr>
		</c:forEach>
		</c:forEach>
		</c:forEach>
		</c:forEach>
	</table>
</BODY>