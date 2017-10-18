/*
function complaint_add_check_form(){
	var valid_obj = {
			
			come_from:{
				title:"投诉来源",
				auto_func:{
					type_func:"check_empty"						
				}
			},
			order_id:{
				title:"订单号",
				auto_func:{
					type_func:"check_empty,is_int"						
				}
			},
			level:{
				title:"投诉等级",
				auto_func:{
					type_func:"check_empty,is_int"						
				}
			}
	}
	return check_form(valid_obj);
}
*/

function complaint_add_check_form(){
	var comeFrom = $('#come_from').val();
	var level = $('#level').val();
	var orderId = $('#order_id').val();
	if(comeFrom == 0){
		alert('请选择投诉来源');
		return false;
	}
	if(level == ''){
		alert('请选择投诉等级');
		return false;
	}
	if(orderId == ''){
		alert('请输入订单号');
		return false;
	}
	if(isNaN(orderId)) {
		alert('订单号必须为数字');
		return false;
	}
	return true;
}

