function follow_time_check_form(){
	var valid_obj = {
		complaint_id:{
			title:"关联投诉id",
			auto_func:{
				type_func:"is_int"
			}
		},
		user_id:{
			title:"提醒用户",
			auto_func:{
				type_func:"is_int"
			}
		},
		order_id:{
			title:"订单id",
			auto_func:{
				type_func:"is_int"
			}
		}
	};
	return check_form(valid_obj);
}
