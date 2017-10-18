function qc_check_form(){

	var valid_obj = {
		complaint_id:{
			title:"投诉单id",
			auto_func:{
				type_func:"is_int"
			}
		},
		leader:{
			title:"负责人",
			auto_func:{
				type_func:"is_int"
			}
		},
		state:{
			title:"状态。0为待处理，1为处理中，2为已处理",
			auto_func:{
				type_func:"is_int"
			}
		},
		qc_person:{
			title:"质检人",
			auto_func:{
				type_func:"is_int"
			}
		},
		order_id:{
			title:"订单id",
			auto_func:{
				type_func:"is_int"
			}
		},
		del_flag:{
			title:"标示位。0为已删除，1为正常",
			auto_func:{
				type_func:"is_int"
			}
		}
	};
	
	return check_form(valid_obj);
}

//重置索引
function resetIndex() {
	// 规整控件序号
	$("tr[id^='tracker_title']:not([rowid])").each(function(qid){
		
		$(this).find("input[name='qcQuestions[qid].bigClassId']").attr("name","qcQuestions["+qid+"].bigClassId");
		$(this).find("input[name='qcQuestions[qid].smallClassId']").attr("name","qcQuestions["+qid+"].smallClassId");
		$(this).find("input[name='qcQuestions[qid].bigClassName']").attr("name","qcQuestions["+qid+"].bigClassName");
		$(this).find("input[name='qcQuestions[qid].smallClassName']").attr("name","qcQuestions["+qid+"].smallClassName");
		$(this).find("select[name='qcQuestions[qid].compLevel']").attr("name","qcQuestions["+qid+"].compLevel");
		$(this).find("textarea[name='qcQuestions[qid].conclusion']").attr("name","qcQuestions["+qid+"].conclusion");
		$(this).find("select[name='qcQuestions[qid].scoreLevel']").attr("name","qcQuestions["+qid+"].scoreLevel");
		$(this).find("select[name='qcQuestions[qid].scoreValue']").attr("name","qcQuestions["+qid+"].scoreValue");
		$(this).find("input[name='qcQuestions[qid].scoreTarget1']").attr("name","qcQuestions["+qid+"].scoreTarget1");
		$(this).find("input[name='qcQuestions[qid].scoreTarget2']").attr("name","qcQuestions["+qid+"].scoreTarget2");
		$(this).find("input[name='qcQuestions[qid].startCity']").attr("name","qcQuestions["+qid+"].startCity");
		$(this).find("input[name='qcQuestions[qid].endCity']").attr("name","qcQuestions["+qid+"].endCity");
		$(this).find("input[name='qcQuestions[qid].airfare']").attr("name","qcQuestions["+qid+"].airfare");
		$(this).find("input[name='qcQuestions[qid].productPrice']").attr("name","qcQuestions["+qid+"].productPrice");

		var smallClassId = $(this).find("input[name='qcQuestions[" + qid +"].smallClassId']").attr("value");
		
		// 将所有子类对应跟踪输入控件序号调整
		$("tr[id^='tracker_input"+smallClassId+"_']:not([rowid])").each(function(tracker_id){
			$(this).find("select[name='qcQuestions[qid].trackers[tracker_id].responsibility']").attr("name", "qcQuestions["+qid+"].trackers["+tracker_id+"].responsibility");
			$(this).find("select[name='qcQuestions[qid].trackers[tracker_id].respSecond']").attr("name", "qcQuestions["+qid+"].trackers["+tracker_id+"].respSecond");
			$(this).find("select[name='qcQuestions[qid].trackers[tracker_id].respThird']").attr("name", "qcQuestions["+qid+"].trackers["+tracker_id+"].respThird");
			$(this).find("select[name='qcQuestions[qid].trackers[tracker_id].position']").attr("name", "qcQuestions["+qid+"].trackers["+tracker_id+"].position");
			$(this).find("input[name='qcQuestions[qid].trackers[tracker_id].responsiblePerson']").attr("name", "qcQuestions["+qid+"].trackers["+tracker_id+"].responsiblePerson");
			$(this).find("input[name='qcQuestions[qid].trackers[tracker_id].improver']").attr("name", "qcQuestions["+qid+"].trackers["+tracker_id+"].improver");
		});
	});
}

// 点击大类
function onBigClassClicked(obj) {
	// 去选大类时，同时去选所有小类及小类对应的跟踪者
	var bigClassName = obj.name;
	if (!obj.checked) {
		$("[name="+bigClassName+"]").each(
				function() {
					if ($(this).attr("checked")) {
						// 删除子类对应的跟踪控件
						$("#tracker_title"+$(this).attr("value")).remove(); 
						$("tr[id^='tracker_input"+$(this).attr("value")+"_']").remove();
						$("#hiddenInput").val($("#hiddenInput").val()-1);
					}
				}
			);

		$("[name="+bigClassName+"]").removeAttr("checked");
	}
}

// 去选小类复选框
function DeSelectSmallClass(smallClassId, bigClassId) {
	$("#checkbox_"+smallClassId).removeAttr("checked");
	$("#tracker_title"+smallClassId).remove(); 
	$("tr[id^='tracker_input"+smallClassId+"_']").remove();
	
	// 判断该大类是否所有小类都取消选择(只有大类1个选中)，如果是，则取消选择该大类
	if ($("[name='checkbox_" + bigClassId + "']:checked").length == 1 ) {
		$("#checkbox_"+bigClassId).removeAttr("checked");
	}
}

//点击小类
function onSmallClassClicked(obj, jsonData) {
	jsonData.index = 0; // tracker_input 默认从0起计数
	jsonData.qid = $("tr[id^='tracker_title").length;	
	
	var pattern = [jsonData];
	var smallClassId = jsonData.smallClassId;
	var bigClassId = jsonData.bigClassId;
	if (obj.checked) {		
		$.tmpl.add_row("tracker_title", pattern);
		$.tmpl.add_row("tracker_input", pattern);
		// 对应大类选中
		$("#checkbox_"+bigClassId).attr("checked", true);
		$("#hiddenInput").val($("#hiddenInput").val()-0+1);
		
		//点击客人咨询、客人误解，自动填写默认值 [BOSSGT-479]4.如果填写质检报告时，点选了其他类，自动填写默认值；
		if(smallClassId ==35 || smallClassId ==36)
		{ //tracker_input35_1
			//alert($("#tracker_title36").find("select[name='qcQuestions[qid].compLevel']").val());
			$("#tracker_title"+smallClassId).find("select[name='qcQuestions[qid].compLevel']").attr("value","3");
			$("#tracker_title"+smallClassId).find("input[name='qcQuestions[qid].scoreTarget1']").attr("value","无");
			$("#tracker_title"+smallClassId).find("input[name='qcQuestions[qid].scoreTarget2']").attr("value","无");
			
			$("#tracker_input"+smallClassId+"_0").find("select[name='qcQuestions[qid].trackers[tracker_id].responsibility']").attr("value","14");
			$("#tracker_input"+smallClassId+"_0").find("input[name='qcQuestions[qid].trackers[tracker_id].responsiblePerson']").attr("value","无");
			$("#tracker_input"+smallClassId+"_0").find("input[name='qcQuestions[qid].trackers[tracker_id].improver']").attr("value","无");
		}
		
	} else {		
		DeSelectSmallClass(smallClassId, bigClassId);
		$("#hiddenInput").val($("#hiddenInput").val()-1);
	}
	
}

// 添加跟踪者
function onAddTrackerClicked(smallClassId, bigClassId) {
	// 规整序号
	var count = 0;
	$("tr[id^='tracker_input"+smallClassId+"_']").each(
			function() {				
				$(this).attr("id", "tracker_input" + smallClassId + "_" +count);
				count++;
			}
			);
	var questionCount = $("tr[id^='tracker_title").length;
	var index = count;//$("tr[id^='tracker_input"+smallClassId+"_']").length;
	var jsonData = {index:index, smallClassId:smallClassId, bigClassId:bigClassId, qid:questionCount};
	
	$.tmpl.add_row("tracker_input", jsonData, "tracker_title"+smallClassId, "*[id^='tracker_title"+smallClassId+"_'");
}

//删除跟踪者
function onDelTrackerClicked(obj, smallClassId, bigClassId) {
	$(obj).parent().parent().remove();

	// 所有跟踪输入都删除，同时去选对应小类勾选
	var count = $("tr[id^='tracker_input"+smallClassId+"_']").length;
	if (count == 0) {
		DeSelectSmallClass(smallClassId, bigClassId);
		$("#hiddenInput").val($("#hiddenInput").val()-1);
	}
}

function onSaveClicked(baseUrl) {
	resetIndex();
	$('#report_form').attr("action", baseUrl + "-doSave");
	//alert(document.report_form.action);
	//self.parent.tb_remove();//点击保存后关闭悬浮窗
	//$('#report_form').attr("action", baseUrl + "-doSave");
	//$('#report_form').submit();
	
}

function onSubmitClicked(baseUrl) {
	resetIndex();
	//alert("submit");
	//self.parent.tb_remove();//点击提交后关闭悬浮窗
	$('#report_form').attr("action", baseUrl + "-doSubmit");
	//$('#report_form').submit();
	//alert(document.report_form.action);
}



function onInputClicked(obj) {
	if (obj.value=='填写责任人' || obj.value=='填写改进人') {
		obj.value="";
	}
} 

function onInputBlur(obj, hint) {
	if (obj.value == "") {
		obj.value = hint;
	}
}
