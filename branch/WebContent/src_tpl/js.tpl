function {?table_name}_check_form(){
	var valid_obj = {{?json_str}
	};
	return check_form(valid_obj);
}