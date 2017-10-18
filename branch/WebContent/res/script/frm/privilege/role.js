function role_check_form(){
	var valid_obj = {
		manage_ids:{
			title:"普通管理员ids",
			auto_func:{
				empty_func:"check_empty"
			}
		},
		role_name:{
			title:"角色名",
			max_len:"60",
			auto_func:{
				empty_func:"check_empty",
				type_func:"check_length"
			}
		},
		super_manage_id:{
			title:"超级管理员id",
			auto_func:{
				empty_func:"check_empty",
				type_func:"is_int"
			}
		}
	};
	return check_form(valid_obj);
}

    
function ajax_do_system_allocate_role_privilege() { 
	var opt = {
		success: function(data) {
			var json_data = eval('('+data+')');
			if(json_data.type=="success") {
				alert("ok");
			}
		} 
	};
	$('#role_privilege_form').ajaxForm(opt);
}


function ajax_do_super_admin_allocate_role_privilege() {  
	var opt = {
		success: function(data) {
			var json_data = eval('('+data+')');
			if(json_data.type=="success") {
				alert("ok");
			}
		} 
	};
	$('#role_privilege_form').ajaxForm(opt);
}


function ajax_do_admin_allocate_role_privilege() {  
	var opt = {
		success: function(data) {
			var json_data = eval('('+data+')');
			if(json_data.type=="success") {
				alert("ok");
			}
		} 
	};
	$('#role_privilege_form').ajaxForm(opt); 
}


function ajax_do_add_manage() {
	var role_id = $("#id").val(); 
	var new_id =  $("#new_manage_id").val();
	var new_manage_name = $("#new_manage_name").val(); 
	
	if(parseInt(new_id) < 1 ) {
		alert("请选择需要添加的管理员");
		return false;
	}
	
	$.ajax({
		"type":"post",
		"url":APP_URL+"frm/ajax/privilege/role-doAddManage",
		data:{
			"role_id":role_id, 
			"new_id":new_id,
			"new_manage_name":new_manage_name
		},
		success: function(data) {
			var json_data = eval('('+data+')');
			if(json_data.type=="success") {
				$.tmpl.add_row("user_info_tpl",json_data.data);
				$("#new_manage_id").val(0);
				 $("#new_manage_name").val(""); 
			} else {
				alert(json_data.data);
			}
		},
		error:function() {
			alert("error");
		}
		
	});
}


function ajax_do_del_manage(obj,manage_id) {
	var role_id = $("#id").val(); ;
	$.ajax({
		"type":"post",
		"url":APP_URL+"frm/ajax/privilege/role-doDelManage",
		data:{
			"role_id":role_id, 
			"manage_id":manage_id,
		},
		success: function(data) {
			var json_data = eval('('+data+')');
			if(json_data.type=="success") {
				$(obj).parent().remove();
			} else {
				alert(json_data.data);
			}
		},
		error:function() {
			alert("error");
		}
		
	});
	
	return false;
}


function ajax_role_user_attr_do_add() {
	var opt = {
			success: function(data) {
				var json_data = eval('('+data+')');
				if(json_data.type=="success") {
					alert("ok");
				}
			} 
		};
		$('#role_user_attr_form').ajaxForm(opt); 
}