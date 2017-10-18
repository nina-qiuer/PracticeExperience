function menu_check_form(){
	var valid_obj = {
		sequence:{
			title:"sequence",
			auto_func:{
				type_func:"is_int"
			}
		},
		father_id:{
			title:"father_id",
			auto_func:{
				empty_func:"check_empty",
				type_func:"is_int"
			}
		},
		menu_url:{
			title:"menu_url",
			max_len:"100",
			auto_func:{
				empty_func:"check_empty",
				type_func:"check_length"
			}
		},
		is_menu:{
			title:"is_menu",
			auto_func:{
				type_func:"is_int"
			}
		},
		menu_name:{
			title:"menu_name",
			max_len:"30",
			auto_func:{
				empty_func:"check_empty",
				type_func:"check_length"
			}
		},
		is_external:{
			title:"is_external",
			auto_func:{
				type_func:"is_int"
			}
		}
	};
	return check_form(valid_obj);
}


function ajax_edit_menu_privilege(id) {
	$.ajax({
		url:APP_URL+"frm/ajax/privilege/menu_privilege-edit",
		type:"post",
		data:{"id":id},
		success:function(data) {
		
			var json_data = eval('('+data+')');
			if(json_data.type=="success") {
				$('#privilege_name').val(json_data.data.privilegeName);
				$('#privilege_url').val(json_data.data.privilegeUrl); 
				$('#entity.id').val(json_data.data.id);  
			} else {
				alert("获取数据失败:"+ json_data.data); 
			}
		}
	});
}



function ajax_do_edit_menu_privilege() {
	$('#menu_privilege_form').ajaxForm(opt);
}


function ajax_do_del_menu_privileg(id) {
	$.ajax({
		url:APP_URL+"frm/ajax/privilege/menu_privilege-doDel",
		type:"post",
		data:{"id":id},
		success:function(data) {
			$('#privilege_row_'+id).remove();
		}
	});
}

function ajax_do_add_menu_privilege() {
	var opt = {
		success: function(data) {
			var json_data = eval('('+data+')');
			if(json_data.type=="success") {
				$.tmpl.add_row("privilege_row_tpl",json_data.data); 
			} else {
				alert("添加失败");
			}
		}
	};
	$('#menu_privilege_form').ajaxForm(opt);
}
