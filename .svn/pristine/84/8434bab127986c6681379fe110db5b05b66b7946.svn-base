var util = {
	isEmpty:function(data) {
		if (data == "" || data == undefined || data == null || data == 'null') {
			return true;
		} else {
			if(data.match(/^\s+$/)){
				return true;
			}
		}
		return false;
	},
	getDate:function (date, format){
		var weeks = new Array("日", "一", "二", "三", "四", "五", "六");
		var yyyy = date.getFullYear();
		var MM = date.getMonth()  + 1;         //getMonth()返回的是0-11，则需要加1
		var dd = date.getDate();
		var day = date.getDay();
		var week = weeks[day];
		if(MM <=9){                                     //如果小于9的话，则需要加上0
			MM = "0" + MM;
		}
		if(dd <=9){                                     //如果小于9的话，则需要加上0
			dd = "0" + dd;
		}
		if(format){
			if(format.indexOf("yyyy") != -1){
				format = format.replace(/yyyy/, yyyy);
			}
			if(format.indexOf("MM") != -1){
				format = format.replace(/MM/, MM);
			}
			if(format.indexOf("dd") != -1){
				format = format.replace(/dd/, dd);
			}
			if(format.indexOf("week") != -1){
				format = format.replace(/week/, week);
			}
			return format;
		}
		return yyyy + "-" +mon + "-" + day;
	}
}