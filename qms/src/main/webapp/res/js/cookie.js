/*增加cookie*/
function addPerpetualCookie(name, value) {
	var cookieString = name + "=" + escape(value);
	var date = new Date("2020", "01", "01");
	cookieString = cookieString + "; expires=" + date.toGMTString();
	document.cookie = cookieString;
}

/* 根据cookie名取内容 */
function getCookie(c_name) {
	if (document.cookie.length > 0) {
		c_start = document.cookie.indexOf(c_name + "=")
		if (c_start != -1) {
			c_start = c_start + c_name.length + 1
			c_end = document.cookie.indexOf(";", c_start)
			if (c_end == -1)
				c_end = document.cookie.length
			return unescape(document.cookie.substring(c_start, c_end))
		}
	}
	return ""
}

function deleteCookie(name) {
	var date = new Date();
	date.setTime(date.getTime() - 10000);
	document.cookie = name + "=v; expires=" + date.toGMTString();
}
