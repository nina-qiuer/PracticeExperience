function user_role_check_form(){
	var valid_obj = {
		role_id:{
			title:"角色ID",
			auto_func:{
				type_func:"is_int"
			}
		},
		user_id:{
			title:"人员ID",
			auto_func:{
				type_func:"is_int"
			}
		},
		del_flag:{
			title:"表示数据是否有效",
			auto_func:{
				type_func:"is_int"
			}
		}
	};
	return check_form(valid_obj);
}
