function menu_privilege_check_form(){
	var valid_obj = {
		menu_id:{
			title:"菜单id",
			auto_func:{
				type_func:"is_int"
			}
		},
		privilege_name:{
			title:"权限名称",
			max_len:"30",
			auto_func:{
				type_func:"check_length"
			}
		},
		privilege_url:{
			title:"权限url",
			max_len:"255",
			auto_func:{
				type_func:"check_length"
			}
		}
	};
	return check_form(valid_obj);
}
