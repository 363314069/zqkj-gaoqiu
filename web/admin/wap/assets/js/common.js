//工具集合Tools
window.T = {};
var scripts = document.getElementsByTagName('script');
// 获取现在已经加载的所有script
var lastScript = scripts[scripts.length-1];
// 获取最近一个加载的script，即这个js本身
T.basepath = lastScript.src.replace(/wap.*/, "wap/");// 获取此js的路径
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
//T.serverurl = "http://127.0.0.1:5202/";
//T.serverexamurl = "http://127.0.0.1:5203/"
//T.serviceImg = "http://192.168.0.41:8848";
if(/^.+:[0-9]+$/.test(location.host)){
    T.serverurl = window.location.protocol + "//wap.oneagles.com/";
    T.serverebusiness = window.location.protocol + "//" + location.hostname + "/";
    T.serverepay = window.location.protocol + "//" + location.hostname + "/";
} else {
    T.serverurl = window.location.protocol + "//" + location.hostname + "/";
    //T.serverebusiness = window.location.protocol + "//" + location.hostname;
    T.serverebusiness = window.location.protocol + "//" + location.hostname + "/";
    T.serverepay = window.location.protocol + "//" + location.hostname + "/";
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
		//location.href = T.basepath + "login.html?u=" + encodeURI(location.href);
		location.href = T.serverurl + "security/wxoauth/orgtimebind?state=1";
	},
	initMsg:function(){
		var reg = /^.*\/login.html.*$/;
		if(reg.test(location.href))
			return;
	 	//T.token = Cookies.get("token");
		T.token = sessionStorage.getItem("token");
		if(!isNull(T.token)){
			$.login();
			return;
		}
		T.usermsg = JSON.parse(sessionStorage.getItem("usermsg"));
		if(T.usermsg == null || T.usermsg.id == null || T.usermsg.token != T.token){
			$.ajax({
				type : "POST",
				url : T.serverurl + "security/oauth/info",
				success : function(r) {
					if(r.code == 0){
						T.usermsg = r.data;
						T.usermsg.token = T.token;
						sessionStorage.setItem("usermsg", JSON.stringify(T.usermsg));
					} else {
						$.login();
					}
				}
			});
		}
	}
});
$.initMsg();

$(function(){
	// 重写alert
	window.alert = function(msg, callback) {
		if(typeof msg === "object" && msg.code){
			window.alertMsg(msg);
		} else {
			layer.open({
			    content: msg
			    ,btn: '确定'
				,yes: function(index){
					if (typeof (callback) === "function") {
						callback("ok");
					}
					layer.close(index);
				}
			});
		}
	}
	
	// 重写confirm式样框
	window.confirm = function(msg, callback) {
		//询问框
		layer.open({
			content: msg
			,btn: [ '确定', '取消' ]
			,yes: function(index){
				layer.close(index);
				if (typeof (callback) === "function") {
					callback("ok");
				}
			}
		});
	}
	
	window.alertMsg = function(obj) {
		var str = "";
		var msg = obj.msg;
		var data = obj.data;
		var err = obj.error;
		if(!$.isEmptyObject(msg)) {
			if((typeof msg == 'object') && msg.constructor == Object) {
				for(var key in msg) {
					str = str + "<div style='text-align: left;'>" + key + ":" + msg[key] + "</div>";
				}
			} else {
				str = str + "<div style='text-align: left;'>" + msg + "</div>";
			}
		}
		if(!$.isEmptyObject(data)) {
			if((typeof data == 'object') && data.constructor == Object) {
				for(var key in data) {
					str = str + "<div style='text-align: left;'>" + key + ":" + data[key] + "</div>";
				}
			} else {
				str = str + "<div style='text-align: left;'>" + data + "</div>";
			}
		}
		if(!$.isEmptyObject(err)) {
			if((typeof err == 'object') && err.constructor == Object) {
				for(var key in err) {
					str = str + "<div style='text-align: left;'>" + key + ":" + err[key] + "</div>";
				}
			} else {
				str = str + "<div style='text-align: left;'>" + err + "</div>";
			}
		}
		alert(str);
	}
});
//判断是否为空
function isNull(data) {
    return (data == "" || data == undefined || data == null || data == 'null') ? false : true;
}

function bindWXUser() {
	if(!isNull(T.usermsg.openid)){
		//Cookies.set("bindUrl",window.location.pathname + window.location.search);
		location.href = T.serverurl + "security/wxoauth/orgtimebind?userGuid="+T.usermsg.guid+"&state=2";
	}else{
		alert("该账号已经被绑定，请先解绑！");
	}
}