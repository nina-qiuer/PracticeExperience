function log_check_form(){
	var valid_obj = {
		user_name:{
			title:"用户名",
			max_len:"30",
			auto_func:{
				type_func:"check_length"
			}
		},
		user_id:{
			title:"用户ID",
			auto_func:{
				empty_func:"check_empty",
				type_func:"is_int"
			}
		},
		url:{
			title:"访问地址",
			max_len:"255",
			auto_func:{
				type_func:"check_length"
			}
		}
	};
	return check_form(valid_obj);
}
