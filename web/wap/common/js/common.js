//工具集合Tools
window.T = {};
var scripts = document.getElementsByTagName('script');
// 获取现在已经加载的所有script
var lastScript = scripts[scripts.length-1];
// 获取最近一个加载的script，即这个js本身
T.basepath = lastScript.src.replace(/common\/js\/common.js.*/, "");// 获取此js的路径
// 获取请求参数
// 使用示例
// location.href = http://localhost:8080/index.html?id=123
// T.p('id') --> 123;
var url = function(name) {
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if(r!=null)return  unescape(r[2]); return null;
};
T.p = url;
if(/^.+:[0-9]+$/.test(location.host)){
	T.serverurl = window.location.protocol + "//" + location.hostname + ":8769/admin-Service/";
    T.serverebusiness = window.location.protocol + "//" + location.hostname + ":8769/business-Service/";
    T.serverepay = window.location.protocol + "//" + location.hostname + ":8769/pay-Service/";
} else {
	T.serverurl = window.location.protocol + "//" + location.hostname + "/admin-Service/";
    //T.serverebusiness = window.location.protocol + "//" + location.hostname;
    T.serverebusiness = window.location.protocol + "//" + location.hostname + "/business-Service/";
    T.serverepay = window.location.protocol + "//" + location.hostname + "/pay-Service/";
}
var id = T.p("id");
var guid = T.p("guid");

//全局配置
//TODO 如果不采用json作为数据传输，需要修改contentType
$.ajaxSetup({
	dataType: "json",
	cache: false,
	beforeSend: function(xhr) {
		if(T.token != null)
			xhr.setRequestHeader("token", T.token);
	},
	dataFilter: function(data, type){
	    //alert('page not found 401');
	    var tokenreg = /^.*msg.*token验证失败.*401.*/;
	    if(tokenreg.test(data)){
		    layer.open({
				content: "帐号验证失败请重新登录！"
				,btn: ['登录']
				,yes: function(index){
					//history.back(-1);
					$.login();
				}
			});
			return "{}";
		}
	  return data;
	},
	statusCode: {
		404: function() {
	    	alert('page not found');
		},
		400: function() {
	    	alert('400错误，参数异常！');
		},
		401: function(msg) {
	    	alert('401错误，' + msg.responseText);
		},
		402: function(msg) {
	    	alert('402错误，' + msg.responseText);
		},
		500: function() {
	    	alert('请求失败！');
		}
	}
});


//全局统一返回
$.extend({
	back:function(){
		window.history.back(); 
	},
	login:function(){
        Cookies.set("forwardUrl",window.location.pathname + window.location.search);
		location.href = T.serverurl + "/wxoauth/wxlogin";
	},
	initMsg:function(){
	 	T.token = Cookies.get("token");
		if(T.token == null){
			$.login();
			return;
		}
		T.usermsg = JSON.parse(localStorage.getItem("usermsg"));
		console.log(T.usermsg);
		if(T.usermsg == null || T.usermsg.id == null || T.usermsg.token != T.token){
			$.ajax({
				type : "POST",
				url : T.serverurl + "/security/oauth/info",
				success : function(r) {
					if(r.code == 0){
						T.usermsg = r.data;
						T.usermsg.token = T.token;
						localStorage.setItem("usermsg", JSON.stringify(T.usermsg));
					} else {
						$.login();
					}
				}
			});
		}
	}
});
$.initMsg();

//判断是否为空
function isNull(data) {
    return (data == "" || data == undefined || data == null || data == 'null') ? false : true;
}
/*=====获取URL上的参数====*/
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
};
