function department_check_form(){
	var valid_obj = {
		tree_father_id:{
			title:"树形结点父id",
			max_len:"200",
			auto_func:{
				type_func:"check_length"
			}
		},
		dep_name:{
			title:"部门名称",
			max_len:"150",
			auto_func:{
				type_func:"check_length"
			}
		},
		father_id:{
			title:"直接上级ID",
			auto_func:{
				type_func:"is_int"
			}
		},
		depth:{
			title:"深度",
			auto_func:{
				type_func:"is_int"
			}
		},
		tree_id:{
			title:"树形结点id",
			max_len:"10",
			auto_func:{
				type_func:"check_length"
			}
		}
	};
	return check_form(valid_obj);
}
