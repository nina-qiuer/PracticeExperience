/**
 * json_obj 形式 {id:{msg:''}}
 */
function check_form(json_obj) {
	var ret = true;
	var func_msg_map = {
		'check_length' :'最大只能为%d个字符',
		'check_empty' :'不能为空',
		'is_int' :'只能为整型',
		'is_decimal' :'只能为数值',
		'check_time':'不能为空'
	};

	for ( var obj_id in json_obj) {
		var obj = $('#' + obj_id);
		$('#' + obj_id).val($.trim($('#' + obj_id).val()));
		var val = $('#' + obj_id).val();
		var func = 'valid_' + obj_id;
	
		if (func_exists(func)) {
			func += '(obj_id)';
			eval('ret = ' + func); 
			
		} else {
			if (typeof json_obj[obj_id].auto_func != 'undefined') { // 有检测函数
				for ( var it in json_obj[obj_id].auto_func) {

					var func = json_obj[obj_id].auto_func[it];
					switch (func) { 
					case 'check_length':
						if (!check_length(val, json_obj[obj_id].max_len)) {
							func_msg_map.check_length = func_msg_map.check_length.replace('%d', json_obj[obj_id].max_len); 
							ret = false;
						}
						break;
					default:
						eval('ret = ' + func + '(val);');
					}

					if (!ret) {
						break;
					}
				}
			}

			if (!ret) {
				if (func_msg_map[func]) {
					alert(json_obj[obj_id].title + func_msg_map[func]);
				}
				if ($('#' + obj_id).get(0).type == 'text' || $('#' + obj_id).get(0).type == 'textarea') { 
					$('#' + obj_id).get(0).focus();
				}
			}
		}

		if (!ret) {
			return ret;
		}
	}
	return true;
}

function func_exists(func_name) {

	try {
		if (typeof eval(func_name) == "undefined") {
			return false;
		}
		if (typeof eval(func_name) == "function") {
			return true;
		}
	} catch (e) {
		return false;
	}
	return false;
}

/**
 * 检查时间不能为空
 * @author hudelei 2010-12-13 11:49
 * @return
 */
function check_time(str){
	if (str == '' || str == '0000-00-00') {
		return false;
	} else {
		return true;
	}
}

function check_empty(str) {
	if (str == '') {
		return false;
	} else {
		return true;
	}
}

function check_length(str, max_len) {
	if (str.length > max_len) {
		return false;
	} else {
		return true;
	}
}

function is_int(str) {
	patten = /^-?[0-9]\d*$/;
	if (null == str.match(patten)) { 
		return false;
	} else {
		return true;
	}
}

function is_decimal(str) {
	patten = /^-?\d+(?:\.\d+)?$/;
	if (null == str.match(patten)) {
		return false;
	} else {
		return true;
	}
}

function intval(str) { 
	var type = typeof( str );
	switch (type) {   
		case 'boolean':
			return type?1:0;
		case 'string':
			var ret = parseInt(str);
			return isNaN(ret)?0:ret; 
		case 'number':
			return str;
		default:
			return 0;
	}
}


/**
 *
 * @return
 */
function check_all(field_name,self_obj) {
	$('input[name^="'+field_name+'["][name$="]"]').each(function(){
		this.checked = self_obj.checked;
	});
}

function get_checked_ids(field_name) {
	var ret = [];
	var i = 0;
	$('input[name^="'+field_name+'["][name$="]"]').each(function(){
		if(this.checked) { 
			ret[i] = $(this).val();  
			i++;
		}
	});
	
	return ret.join(',');
}


function auto_match(all_data,data_field_name,field_obj) {
	
	this.field_obj = field_obj;
	this.all_data = {};
	this.data_field_name ="";
	this.match=function(text) {
		var origin_data = '';
		var field_name = this.field_obj.s_field;
		text = $.trim(text);
		$('#'+field_name).empty();
		var i = 1;
		for(var it in this.all_data) {
			eval("origin_data=this.all_data[it]."+this.data_field_name+";");  
			if( (text=="") || (origin_data.indexOf(text) != -1)) { 
				$('#'+field_name).append('<option value="'+it+'">'+origin_data+'</option>');
			}
		}
	}
	
	this.init = function(all_data,data_field_name) {
		this.all_data = all_data;
		this.data_field_name = data_field_name;
		this.match('');
	}
	this.init(all_data,data_field_name);
}


/**
批量操作
**/
function batch_op(field_name,type) { 
	var ids = get_checked_ids(field_name); 
	if(ids) {
		var act = $('#list_form').attr('action');
		var arr = act.split(',');
		if(arr.length>2) {
			arr =  arr.slice(0,2);
		}
		
		arr[2] = type; 
		act = arr.join(',');   
		 
		$('#list_form').attr('action',act);
		$('#list_form').submit(); 
	} else{
		alert('请选择要操作的项');
	}
	return false;
} 


function openWindow(url, nActiveMode){
	try{
		parent.parent.MDIOpen(url, nActiveMode);
		return;
	}catch(e){
	}
	try{
		var win = window.open(url);
		if(!nActiveMode && win)win.blur();
	}catch(e){
	}
}
