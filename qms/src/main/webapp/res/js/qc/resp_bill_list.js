$(document).ready(function() {
	$(".accordion").each(function() {
		$(this).accordion({
	    	collapsible: true,
	    	heightStyle: "content"
	    });
	});
		
	$("#list").jqGrid({
        url:"qc/qcBill/queryRespList",
        datatype:"json", //数据来源，本地数据
        mtype:"post",//提交方式
        height:500,//高度，表格高度。可为数值、百分比或'auto'
        autowidth:true,//自动宽
        multiselect: true,
        contentType: 'application/json; charset=utf-8' ,
//      serializeGridData: function () {
//        	 var params = new Object();
//             params.complaintId = "111";
//             params.id = "222";
//             return params;
//        },
        postData:{qpId:$('#qpId').val(),addTimeStart:$('#addTimeStart').val(),addTimeEnd:$('#addTimeEnd').val()},
        colNames:['id','质量问题id','责任人ID', '责任部门ID', '责任岗位ID','添加人','添加时间','操作'],
        colModel:[
            {name:'id',index:'id', width:'20%',align:'center',hidden:true},
            {name:'qpId',index:'qpId', width:'20%',align:'center',sortable:false},
            {name:'respPersonId',index:'respPersonId', width:'20%',align:'center',sortable:false},
            {name:'depId',index:'depId', width:'15%',align:'center',sortable:false},
            {name:'jobId',index:'jobId', width:'20%', align:"center",sortable:false},
            //{name:'scoreValue',index:'scoreValue', width:'10%', align:"left",sortable:false},
           // {name:'fineAmount',index:'fineAmount', width:'10%', align:"center" ,cellattr: addCellAttr,sortable:false,},
            {name:'addPerson',index:'addPerson', width:'10%', align:"left", sortable:false},
            {name:'addTime',index:'addTime', width:'20%',align:"center", sortable:false},
            {name:'modify',index:'modify', width:'15%',align:"center", formatter : operFormatter}
        ],
       // rownumbers:true,//添加左侧行号
       // altRows:true,//设置为交替行表格,默认为false
       // caption: "JSON Example",
        //sortname:'respPersonId',
        viewrecords: true,//是否在浏览导航栏显示记录总数
        rowNum:15,//每页显示记录数
        rowList:[5,10,15],//用于改变显示行数的下拉列表框的元素数组。
        prmNames:  {},
        jsonReader:{
        	root:function (obj) {
                var data = eval("(" + obj + ")");
                return data.records;
                },
        	total: function (obj) {
                var data = eval("(" + obj + ")");
                return data.totalPages;
                },
        	page: function (obj) {
                var data = eval("(" + obj + ")");
                return data.currPage;
                },
        	records: function (obj) {
                var data = eval("(" + obj + ")");
                return data.totalRecords;
                },
            repeatitems: false
            
        },
      
       gridComplete: function () {  
        var ids = jQuery("#list").jqGrid("getDataIDs");  
        for (var i = 0; i < ids.length; i++) {  
            var id = ids[i];  
        	var model = jQuery("#list").jqGrid("getRowData", id);  
        	if(model.fineAmount>20){
        		$("#"+id + " td").css("background-color","pink");	
        	}
        }  
    } ,
        pager:$('#gridPager')
    });
	
	 jQuery("#list").jqGrid('navGrid', '#gridPager', {edit : false,add : false,del : false,search:false,refresh:false});
	 
});

addCellAttr = function (rowId, val, rawObject, cm, rdata) {
	 if (val>8) {
	      return "style='color:red'";
	   }
	} ;


operFormatter = function(cellvalue, options, row) {
	//也可以直接获取row.qpId
	var updateBtn = '<input type="button" class="blue" value="修改" onclick="Modify('
	+ options.rowId+ ')">';
	 
	var deleteBtn = '<input type="button" class="blue" value="删除" onclick="Delete('
	+  options.rowId + ')">';
	 
	return "&nbsp;&nbsp;\t" + updateBtn + "&nbsp;&nbsp;"
	+ deleteBtn;
	};

//修改一条数据
function Modify(id){
	
	var model = jQuery("#list").jqGrid("getRowData", id);  
	alert(model.qpId);
	
}
//删除一条数据
function Delete(id){
	
	var model = jQuery("#list").jqGrid("getRowData", id);  
	var id = model.id;
	var title=confirm("您确定删除该信息吗？点击“取消”则返回！");
    if(title == true){
			$.ajax( {
				url : 'qc/qcBill/deleteResp',
				data : {
					
					id:id
				},
				type : 'post',
				dataType:'json',
				cache : false,
				success : function(result) {
					if(result)
					{
				    	if(result.retCode == "0")
						{
				    		layer.alert("删除成功！", {icon: 1});
				    	    $("#list").trigger("reloadGrid");
						 }else{
							
							 layer.alert(result.resMsg,{icon: 2});
						}
				     }
				 }
			    });
			
		}
}
function deleteRows(){
	var arrayObj = new Array();
	var ids = $('#list').jqGrid('getGridParam','selarrrow');
	for(var i=0;i<ids.length;i++){
		
		var model = jQuery("#list").jqGrid("getRowData", ids[i]);  
		arrayObj.push(model.qpId);
	}
   alert(arrayObj);	
}

function search(){
	
	var reg = new RegExp("^[0-9]*$");
	var qpId = $('#qpId').val();
	if (qpId != "" && !reg.test(qpId)) {
		layer.alert("质检单号应为数字！", {icon: 2});	
		$('#qpId').focus();
		return false;
	}
     $("#list").jqGrid('setGridParam',{ 
            url:"qc/qcBill/queryRespList",
            postData:{qpId:qpId,addTimeStart:$('#addTimeStart').val(),addTimeEnd:$('#addTimeEnd').val()},
            page:1 
        }).trigger("reloadGrid"); //重新载入 
}