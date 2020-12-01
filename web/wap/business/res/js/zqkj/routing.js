var scripts = document.getElementsByTagName('script');
// 获取现在已经加载的所有script
var lastScript = scripts[scripts.length-1];
// 获取最近一个加载的script，即这个js本身
var jumpbasepath = lastScript.src.replace(/\/res\/js\/zqkj\/.*js.*/, "/");// 获取此js的路径
/*
$.getJSON(jumpbasepath + "res/js/zqkj/url.js?d=" + (new Date()).getTime(), function(json){
	jump.link = json;
});
*/
var loadjump = false;
$.getScript(jumpbasepath + "res/js/zqkj/jump.js", function(){
	loadjump = true;
});
$(function(){
	$('body').on('click', 'a', function(e) {
		if(!loadjump){
			return false;
		}
		var that = $(e.currentTarget);
		var href = that.attr("href");
		try{
			if(href){
				var i = href.indexOf("?");
				if(that){
					var parm = ""
					var key = href;
					if(i > 0){
						key = href.substring(0,i);
						parm = href.substring(i);
					}
					var uri = jump.geturi(key, that.attr("v"));
					if(uri)
						that.attr("href", uri + parm);
				}
			}
		} catch(err) {}
	});
})