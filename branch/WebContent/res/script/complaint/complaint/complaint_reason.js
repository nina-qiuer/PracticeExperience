function complaint_reason_check_form(){
	var valid_obj = {
		complaint_id:{
			title:"关联投诉id",
			auto_func:{
				type_func:"is_int"
			}
		},
		order_id:{
			title:"关联订单id",
			auto_func:{
				type_func:"is_int"
			}
		},
		type:{
			title:"投诉类别",
			max_len:"100",
			auto_func:{
				type_func:"check_length"
			}
		}
	};
	return check_form(valid_obj);
}
