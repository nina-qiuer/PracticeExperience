(function($) {
	// Code goes here
	$.tmpl.html = function(tpl_id) {
		var tmp_obj = $('#'+ tpl_id).clone(true).removeAttr("id").show().addClass("dynamic_data");
		if(tmp_obj.attr("rowid") != 'undefined') {
			tmp_obj.attr("id",tmp_obj.attr("rowid"));
			tmp_obj.removeAttr("rowid");
		}
		return tmp_obj.outer();
	}
	
	$.tmpl.list = function(tpl_id,json_data,aftid,addCond) {
		var tpl = this.html(tpl_id);
		if(typeof aftid == 'undefined') {
			aftid = tpl_id;
		}
		if(typeof addCond == 'undefined') {
			addCond = ".dynamic_data";
		} 
		$('#'+aftid).nextAll().remove(addCond); 
		$.tmpl(tpl, json_data ).insertAfter('#'+aftid); 
	}
	
	$.tmpl.add_row = function(tpl_id,json_data,aftid,addCond) {
		if(typeof json_data.length == 'undefined') {
			json_data = [json_data];
		}
		var tpl = this.html(tpl_id);
		if(typeof aftid == 'undefined') {
			aftid = tpl_id;
		}
		
		if(typeof addCond == 'undefined') {
			addCond = ".dynamic_data";
		}
		var obj = $('#'+aftid).nextAll(addCond).last();
		
		if(obj.length == 0) {
			obj = $('#'+aftid); 
		}
		$.tmpl( tpl, json_data).insertAfter(obj);  
	}

})(jQuery);