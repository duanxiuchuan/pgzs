function number_format(number, decimals, dec_point, thousands_sep) {
	/*
	 * 参数说明：
	 * number：要格式化的数字
	 * decimals：保留几位小数
	 * dec_point：小数点符号
	 * thousands_sep：千分位符号
	 * */
	number = (number + '').replace(/[^0-9+-Ee.]/g, '');
	var n = !isFinite(+number) ? 0 : +number,

	prec = !isFinite(+decimals) ? 0 : Math.abs(decimals), sep = (typeof thousands_sep === 'undefined') ? ','
			: thousands_sep, dec = (typeof dec_point === 'undefined') ? '.'
			: dec_point, s = '', toFixedFix = function(n, prec) {
		var k = Math.pow(10, prec);
		return '' + Math.floor(n * k) / k;
	};
	s = (prec ? toFixedFix(n, prec) : '' + Math.floor(n)).split('.');
	var re = /(-?\d+)(\d{3})/;
	while (re.test(s[0])) {
		s[0] = s[0].replace(re, "$1" + sep + "$2");
	}

	if ((s[1] || '').length < prec) {
		s[1] = s[1] || '';
		s[1] += new Array(prec - s[1].length + 1).join('0');
	}
	return s.join(dec);
}

/**************************************时间格式化处理************************************/
function dateFtt(fmt, date) { //author: meizz   
	var o = {
		"M+" : date.getMonth() + 1, //月份   
		"d+" : date.getDate(), //日   
		"h+" : date.getHours(), //小时   
		"m+" : date.getMinutes(), //分   
		"s+" : date.getSeconds(), //秒   
		"q+" : Math.floor((date.getMonth() + 3) / 3), //季度   
		"S" : date.getMilliseconds()
	//毫秒   
	};
	if (/(y+)/.test(fmt)) {
		fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
	}
	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(fmt)) {
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		}
	}
		
	return fmt;
}

/**************************************常用正则表达式************************************/
var match_pass = /^[a-zA-Z\d_]{8,}$/;
var match_cash = /^\d{6}$/;
var match_phone = /^1[3-9]\d{9}$/;
var match_yzm = /^\d{4}|\d{6}$/;
var match_name = /^[\u4E00-\u9FA5\uf900-\ufa2d]{2,6}$/;
var match_money = /(^[0-9]{1,6}$)|(^[0-9]{1,6}[\.]{1}[0-9]{1,6}$)/;