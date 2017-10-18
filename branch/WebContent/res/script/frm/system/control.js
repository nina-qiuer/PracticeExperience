function control_check_form(){
	var valid_obj = {
		need_ds:{
			title:"是否有数据源",
			auto_func:{
				type_func:"is_int"
			}
		}
	};
	return check_form(valid_obj);
}
