/**
 * 检验数字长度
 */
function checkIsNumber(obj,len){	
	if(isNaN(obj.value)){
		alert("请输入数字");
		obj.value = "";
		return false;
	}
	if(len){
		if(obj.value.length != len){
			alert("请输入"+len+"位数字");
			return false;
		}
	}
	return true;
}

function isDigit(s) {
	var r = new RegExp("^-?\d+\.{0,}\d{0,"+2+"}$");
	alert(r.exec(s));
	var patrn = /^-?\d+\.{0,}\d{0,}$/;
	alert(patrn.exec(s));
	if (!patrn.exec(s)) {
		alert("error");
		return false;
	} else {
		alert("ok");
		return true;
	}
}

function roundNum(dight,len){  
	dight = Math.round(dight*Math.pow(10,len))/Math.pow(10,len);  
    return dight;  
} 

function interceptNum(dight,len){
	var s = dight + "";
	var str = s.substring(0,s.indexOf(".") + len+1);
	return str;
}


function formatNumber(num,len){
	var re=/(\d{1,3})(?=(\d{3})+(?:$|\.))/g;
		if(isNaN(num)){
			alert("请输入数字");
			return "";
		}
		return Number(num).toFixed(len).replace(re,"$1,");
}
function parseNumber(num){
	return num.replace(/,/g,"");
}

if (typeof(PrimeFaces) == "undefined"){
}else{
	PrimeFaces.locales['zh_CN'] = {
			closeText: '关闭',
			prevText: '上个月',
			nextText: '下个月',
			currentText: '今天',
			monthNames: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],
			monthNamesShort: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],
			dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],
			dayNamesShort: ['日','一','二','三','四','五','六'],
			dayNamesMin: ['日','一','二','三','四','五','六'],
			weekHeader: '周',
			firstDay: 0,
			isRTL: false,
			showMonthAfterYear: true,
			yearSuffix: '', // 年    
			timeOnlyTitle: '时间',
			timeText: '时间',
			hourText: '时',
			minuteText: '分',
			secondText: '秒',
			ampm: false,
			month: '月', 
			week: '周',
			day: '日',
			allDayText : '全天'
			}; 
}