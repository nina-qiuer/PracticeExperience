function gift_check_form(){
	var valid_obj = {
		name:{
			title:"礼品名称",
			max_len:"50",
			auto_func:{
				type_func:"check_length"
			}
		},
		complaint_id:{
			title:"关联投诉id",
			auto_func:{
				type_func:"is_int"
			}
		},
		gift_id:{
			title:"礼品id",
			auto_func:{
				type_func:"is_int"
			}
		},
		number:{
			title:"礼品数量",
			auto_func:{
				type_func:"is_int"
			}
		},
		solution_id:{
			title:"关联解决方案id",
			auto_func:{
				type_func:"is_int"
			}
		}
	};
	return check_form(valid_obj);
}
