<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/html/head.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript"
	src="${CONFIG.res_url}script/thickbox-compressed.js"></script>
<script type="text/javascript"
	src="${CONFIG.res_url}script/jquery/validation/jquery.validate.min.js"></script>
<style type="text/css">
.datatable {
	margin: 0 auto;
}

.datatable th {
	text-align: right;
	width: 20%;
}

.datatable td {
	text-align: left;
}

input[type=text] {
	width: 70%;
}

select {
	width: 71.5%;
}

.assign_self {
	background-color: #FFF;
	padding: 8px 4px;
	border-radius: 7px;
	display: inline-block;
	position: relative;
	margin-right: 30px;
	background: #F7836D;
	width: 55px;
	height: 10px;
	box-shadow: inset 0 0 20px rgba(0, 0, 0, 0.1), 0 0 10px
		rgba(245, 146, 146, 0.4);
}

.assign_self.active {
	background: #67A5DF;
	box-shadow: inset 0 0 20px rgba(0, 0, 0, 0.1), 0 0 10px
		rgba(146, 196, 245, 0.4);
}

.assign_self .seebutton {
	position: absolute;
	background: #FFF;
	top: 0px;
	z-index: 99999;
	left: 0px;
	width: 16px;
	color: #FFF;
	height: 26px;
	border-radius: 7px;
	box-shadow: 0 0 1px rgba(0, 0, 0, 0.6);
}

.assign_self .seecontent {
	position: absolute;
	top: 0px;
	z-index: 99999;
	left: 26px;
	height: 26px;
	line-height: 26px;
	font-size: 16px;
	font-family: '微软雅黑';
}
</style>

<script type="text/javascript">
	var oldPhone = '';
	$(function() {
		oldPhone = $('#entity_phone').val();

		var validate = $("#workOrderForm").validate({
			submitHandler : function(form) {
				addOrUpdateWorkOrder();
			},

			rules : {
				'entity.orderId' : {
					digits : true
				},
				'entity.businessClass' : {
					maxlength : 20
				},
				'entity.customerName' : {
					required : true
				},
				'entity.phone' : {
					required : true
				},
				'entity.phoneMatter' : {
					required : true,
					maxlength : 2000
				},
				'entity.answerTime' : {
					digits : true
				},
				'entity.remark' : {
					maxlength : 3000
				}
			},
			messages : {
				'entity.orderId' : {
					digits : "只能是数字"
				},
				'entity.businessClass' : {
					maxlength : "超过20字符"
				},
				'entity.customerName' : {
					required : "必填"
				},
				'entity.phone' : {
					required : "必填"
				},
				'entity.phoneMatter' : {
					required : "必填",
					maxlength : "超过2000字符"
				},
				'entity.answerTime' : {
					digits : "只能填数字"
				},
				'entity.remark' : {
					maxlength : '超过3000字符'
				}
			}
		});

		$(".assign_self").unbind("click").bind("click", function() {
			AssginToSelf();
		});
		
		$("#firstConfig").val(${entity.configId});
		$("#secondConfig").val(${entity.sonConfigId});

	});

	function AssginToSelf() {
		var timeOutTime = 100;
		if ($(".seebutton").hasClass("active")) {
			$(".seecontent").animate({
				left : "26px"
			}, timeOutTime);
			$(".seebutton").animate({
				left : "0px"
			}, timeOutTime);
			$(".seebutton").removeClass("active");
			$(".assign_self").removeClass("active");
			$(".seecontent").text("否");
			$("#dealBySelf").attr("value", 0);
		} else {
			$(".seecontent").animate({
				left : "20px"
			}, timeOutTime);
			$(".seebutton").animate({
				left : "47px"
			}, timeOutTime);
			$(".assign_self").addClass("active");
			$(".seecontent").text("是");
			$(".seebutton").addClass("active");
			$("#dealBySelf").attr("value", 1);
		}

	}

	function addOrUpdateWorkOrder() {
		var url = "";
		if ($('#id').val() == '') {
			url = "work_order-add";
		} else {
			url = "work_order-update";
		}

		$('#submitButton').attr('disabled', 'true');

		console.log(oldPhone + ":" + $('#entity_phone').val());
		if ($('#id').val() == '' || (oldPhone != $('#entity_phone').val())) { //新增时校验电话的唯一性，更新时当修改了电话后校验唯一性
			$.ajax({
				type : "post",
				url : 'work_order-isPhoneUnique',
				data : $('#workOrderForm').serialize(),
				success : function(data) {
					if (!data.unique) { // 不唯一询问
						layer.confirm('该号码已存在，工单号[' + data.id + ']，是否仍然提交',
								function(index) { //点击确定的回调函数
									layer.close(index);
									submitWorkOrder(url);
								}, function(index) { //点击取消的回调函数
									layer.close(index);
									$('#submitButton').removeAttr('disabled');
									return false;
								});
					} else {
						submitWorkOrder(url);
					}
				}
			});

		} else {
			submitWorkOrder(url);
		}

	}

	function submitWorkOrder(url) {
		$.ajax({
			type : "post",
			url : url,
			data : $('#workOrderForm').serialize(),
			success : function(data) {
				layer.alert('操作成功', function(index) { //点击确定后关闭当前iframe并刷新父页面
					layer.close(index);
					var frameIndex = parent.layer.getFrameIndex(window.name); //得到当前iframe层的索引
					$('#search_form', parent.document).submit(); //刷新父页面
					parent.layer.close(frameIndex); // 执行关闭
				});
			}
		});
	}
	
	function getSecondConfig(){
		var configId = $("#firstConfig").val();
		$("#secondConfig").empty();
		
		if(configId > 0){
			$.ajax({
				type : "post",
				url : "work_order-getSonConfigList",
				data : {
					"pid": configId
				},
				success : function(data) {
					addSecondConfig(data);
				}
			});
		}
	}
	
	function addSecondConfig(data){
		if( data.resultList.length > 0){
			$("#sonConfigTr").show();
			
			var objSelect = document.getElementById("secondConfig");
			$.each(data.resultList, function(i, item){  
	            var  option = new Option(item.department, item.id);
	            objSelect.add(option);
	        });  
		}else{
			$("#sonConfigTr").hide();
		}
	}
	
</script>

</head>
<body>
	<div id="main">
		<!-- 页面类型 -->
		<input id="pageType" type="hidden" value="${pageType }" />
		<form id="workOrderForm" action="" method="post">
			<s:hidden name="id" id="id" />
			<s:hidden name="dealBySelf" id="dealBySelf" value="0" />
			<table width="100%" class="datatable mb10">
				<tr>
					<th>项目组：</th>
					<td>
						<select id="firstConfig" name="entity.configId" onchange="getSecondConfig()">  
						 	<c:forEach items="${firstConfigList}" var="item">
						 		<option value="${item.id}">${item.department}</option>
						 	</c:forEach>
						 </select>
					</td>
				</tr>
				<c:if test="${entity.sonConfigId != null && entity.sonConfigId >0 }">
					<tr id="sonConfigTr">
				</c:if>
				<c:if test="${entity.sonConfigId == null || entity.sonConfigId == 0 }">
					<tr id="sonConfigTr" style="display:none;">
				</c:if>
					<th>二级项目组：</th>
					<td> 
						 <select id="secondConfig" name="entity.sonConfigId">  
						 	<c:forEach items="${secondConfigList}" var="item">
						 		<option value="${item.id}">${item.department}</option>
						 	</c:forEach>
						 </select>
					</td>
				</tr>
				<tr>
					<th>订单号：</th>
					<td><s:textfield cssClass="element edit" name="entity.orderId" /></td>
				</tr>
				<tr>
					<th>业务分类：</th>
					<td><s:textfield cssClass="element edit"
							name="entity.businessClass" /></td>
				</tr>
				<tr>
					<th>客人姓名：</th>
					<td><s:textfield cssClass="element edit"
							name="entity.customerName" /></td>
				</tr>
				<tr>
					<th>联系电话：</th>
					<td><s:textfield cssClass="element edit" name="entity.phone" /></td>
				</tr>
				<tr>
					<th>来电事由：</th>
					<td><s:textarea cssClass="element edit"
							name="entity.phoneMatter" cols="47" rows="5"></s:textarea></td>
				</tr>
				<tr>
					<th>回复/解决时间:</th>
					<td><s:textfield cssClass="element edit"
							name="entity.answerTime" />(分钟)</td>
				</tr>
				<tr>
					<th>备注：</th>
					<td><s:textarea cssClass="element  remark"
							name="entity.remark" cols="47" rows="5"></s:textarea></td>
				</tr>
				<c:if test="${id==null}">
				<tr>
					<th>分配给自己：</th>
					<td><label class="assign_self"><div class="seebutton"></div>
							<div class="seecontent">否</div></label></td>
				</tr>
				<tr>
					<td colspan="2" style="text-align: center"></td>
				</tr>
				</c:if>

				<tr>
					<td colspan="2" style="text-align: center"><input
						id="submitButton" type="submit" class="blue" value="提交"></input></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>