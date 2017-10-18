function check_quality_tool(){
	var name = $.trim($('#name').val());
	var remark = $.trim($('#remark').val());
	if (name == '') {
		alert('质量工具名称必录');
		return false;
	} else if (name.length > 50) {
		alert('质量工具名称最多可录入50字符');
		return false;
	} else if (name.indexOf('&') > 0 || name.indexOf('%') > 0) {
		alert('质量工具名称中请不要含有&和%符号');
		return false;
	}
	if (remark != '' && remark.length > 500) {
		alert('备注最多可录入500字符');
		return false;
	} else if (remark != '' && (remark.indexOf('&') > 0 || remark.indexOf('%') > 0)) {
		alert('备注中请不要含有&和%符号');
		return false;
	}
	
	var param = 'name=' + name;
	param += '&level=' + $('#level').val();
	param += '&type=' + $('#type').val();
	param += '&remark=' + remark;
	param += '&id=' + $('#id').val();
	param += '&useFlag=' + $('input:radio[name="useFlag"]:checked').val();
	$.post('quality_tool-doAdd', param, function(data){
		if (data == 1) {
			alert('保存成功');
			self.parent.location.reload();
		} else {
			alert('请存在相同质量工具 ，请确认！');
			return false;
		}
	});
	
}