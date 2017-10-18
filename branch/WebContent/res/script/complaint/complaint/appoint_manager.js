function appoint_manager_check_form(){
	var valid_obj = {
		user_name:{
			title:"姓名",
			max_len:"11",
			auto_func:{
				type_func:"check_length"
			}
		},
		department_id:{
			title:"所在部门",
			auto_func:{
				type_func:"is_int"
			}
		},
		user_id:{
			title:"用户id",
			auto_func:{
				type_func:"is_int"
			}
		},
		type:{
			title:"类别",
			auto_func:{
				type_func:"is_int"
			}
		},
		department_name:{
			title:"部门名称",
			max_len:"50",
			auto_func:{
				type_func:"check_length"
			}
		},
		del_falg:{
			title:"删除标示未",
			auto_func:{
				type_func:"is_int"
			}
		}
	};
	return check_form(valid_obj);
}