/* Simplified Chinese translation for the jQuery Timepicker Addon /
/ Written by Will Lu */
(function($) {
	$.timepicker.regional['zh-CN'] = {
		timeOnlyTitle: '选择时间',
		timeText: '时间',
		hourText: '小时',
		minuteText: '分钟',
		secondText: '秒钟',
		millisecText: '微秒',
		timezoneText: '时区',
		currentText: '现在时间',
		closeText: '关闭',
		prevText: "向前", // Display text for previous month link
		nextText: "向后", // Display text for next month link
		monthNames: ["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"], // Names of months for drop-down and formatting
		monthNamesShort: ["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月", "十一", "十二"], // For formatting
		dayNames: ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"], // For formatting
		dayNamesShort: ["日", "一", "二", "三", "四", "五", "六"], // For formatting
		dayNamesMin: ["日", "一", "二", "三", "四", "五", "六"], // Column headings for days starting at Sunday
		timeFormat: 'HH:mm',
		amNames: ['AM', 'A'],
		pmNames: ['PM', 'P'],
		isRTL: false
	};
	$.timepicker.setDefaults($.timepicker.regional['zh-CN']);
	
	$.datepicker.regional['zh-CN'] = {
		timeOnlyTitle: '选择时间',
		timeText: '时间',
		hourText: '小时',
		minuteText: '分钟',
		secondText: '秒钟',
		millisecText: '微秒',
		timezoneText: '时区',
		currentText: '现在时间',
		closeText: '关闭',
		prevText: "向前", // Display text for previous month link
		nextText: "向后", // Display text for next month link
		monthNames: ["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"], // Names of months for drop-down and formatting
		monthNamesShort: ["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月", "十一", "十二"], // For formatting
		dayNames: ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"], // For formatting
		dayNamesShort: ["日", "一", "二", "三", "四", "五", "六"], // For formatting
		dayNamesMin: ["日", "一", "二", "三", "四", "五", "六"], // Column headings for days starting at Sunday
		timeFormat: 'HH:mm',
		amNames: ['AM', 'A'],
		pmNames: ['PM', 'P'],
		isRTL: false
	};
	$.datepicker.setDefaults($.datepicker.regional['zh-CN']);
})(jQuery);


