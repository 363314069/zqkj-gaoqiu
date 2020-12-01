$.extend({
	// 选择单条记录
	getRow : function(rows) {
		if (!rows || rows.length == 0) {
			alert("请选择一条记录");
			return;
		}
		if (rows.length > 1) {
			alert("只能选择一条记录");
			return;
		}
		return rows[0].guid;
	},
	// 选择多条记录
	getRows : function(rows) {
		var guid = new Array();
		if (!rows || rows.length == 0) {
			alert("请选择一条记录");
			return;
		}
		for (var i = 0; i < rows.length; i++) {
			guid.push(rows[i].guid);
		}
		return guid;
	},
	state : '<div>{{d.state == 1?"启用":"禁用"}}</div>'
});

$.fn.extend({
	initForm : function(options) {
		//默认参数  
		var defaults = {
			jsonValue : "",
			isDebug : false
			//是否需要调试，这个用于开发阶段，发布阶段请将设置为false，默认为false,true将会把name value打印出来  
		}
		//设置参数  
		var setting = $.extend({}, defaults, options);
		var form = this;
		jsonValue = setting.jsonValue;
		//如果传入的json字符串，将转为json对象  
		if ($.type(setting.jsonValue) === "string") {
			jsonValue = $.parseJSON(jsonValue);
		}
		//如果传入的json对象为空，则不做任何操作  
		if (!$.isEmptyObject(jsonValue)) {
			var debugInfo = "";
			$.each(jsonValue, function(key, value) {
				//是否开启调试，开启将会把name value打印出来  
				if (setting.isDebug) {
					alert("name:" + key + "; value:" + value);
					debugInfo += "name:" + key + "; value:" + value + " || ";
				}
				var formField = form.find("[name='" + key + "']");
				if ($.type(formField[0]) === "undefined") {
					if (setting.isDebug) {
						alert("can not find name:[" + key + "] in form!!!"); //没找到指定name的表单  
					}
				} else {
					var fieldTagName = formField[0].tagName.toLowerCase();
					if (fieldTagName == "input") {
						if (formField.attr("type") == "radio") {
							$("input:radio[name='" + key + "'][value='"+ value + "']").attr("checked","checked");
						} else if (formField.attr("type") == "checkbox") {
							$("input:checkbox[name='" + key + "'][value='"+ value + "']").attr("checked",true);
						} else{
							formField.val(value);
						}
					} else if (fieldTagName == "select") {
						//do something special  
						formField.val(value);
						formField.attr('val', value);
					} else if (fieldTagName == "textarea") {
						//do something special  
						formField.val(value);
					} else {
						formField.val(value);
					}
				}
			})
			if (setting.isDebug) {
				alert(debugInfo);
			}
		}
		return form; //返回对象，提供链式操作  
	},
	/*
	 * json为数组 
	 * valuekey为value字段名  
	 * namekey为name字段名 
	 * before为前坠可以在前面加空格也可以加图标等
	 * b为true时自动添加 <option value="0">请选择</option>项
	 */
	setOption:function(json, valuekey, namekey, before, b, refreshform){
		var select = $(this);
		if(b)
			select.append('<option value="0">请选择</option>');
		if(!before)
			before = '';
		for(var i = 0; i < json.length; i++)
			select.append('<option value="' + json[i][valuekey] + '">' + before + json[i][namekey] + '</option>');
		for(var i = 0; i < json.length; i++){
			if(json[i].children){
				var optgroup = $('<optgroup label="'+json[i][namekey]+'">');
				optgroup.setOption(json[i].children, valuekey, namekey, '' + before, false, true);
				select.append(optgroup);
			}
		}
		if(!refreshform){
			select.val(select.attr('val'));
			$.refreshform('select');
		}
	}
});
