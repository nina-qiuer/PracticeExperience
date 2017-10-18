function gift_type_check_form(){
	var valid_obj = {
		price:{
			title:"价格",
			auto_func:{
				type_func:"is_int"
			}
		},
		name:{
			title:"名字",
			max_len:"50",
			auto_func:{
				type_func:"check_length"
			}
		},
		cost:{
			title:"成本",
			auto_func:{
				type_func:"is_int"
			}
		}
	};
	return check_form(valid_obj);
}
