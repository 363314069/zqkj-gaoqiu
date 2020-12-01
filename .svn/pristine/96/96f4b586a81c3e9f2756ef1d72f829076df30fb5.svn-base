//工具集合Tools
window.T = {};
var scripts = document.getElementsByTagName('script');
// 获取现在已经加载的所有script
var lastScript = scripts[scripts.length-1];
// 获取最近一个加载的script，即这个js本身
T.basepath = lastScript.src.replace(/js\/common[^\/]*.js.*/, "");// 获取此js的路径
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
	T.serverurl = window.location.protocol + "//" + location.hostname + "/";
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
		if(!isNull(T.token))
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
        sessionStorage.setItem("forwardUrl",window.location.pathname + window.location.search);
		location.href = T.serverurl + "/security/wxoauth/wxlogin";
	},
	initMsg:function(){
	 	T.token = sessionStorage.getItem("token");
		if(!isNull(T.token)){
			$.login();
			return;
		}
		T.usermsg =  JSON.parse(sessionStorage.getItem("usermsg"));
		if(T.usermsg == null || T.usermsg.id == null || T.usermsg.token != T.token){
			$.login();
			/*$.ajax({
				type : "POST",
				url : T.serverurl + "/security/oauth/info",
				success : function(r) {
					if(r.code == 0){
						T.usermsg = r.data;
						T.usermsg.token = T.token;
                        sessionStorage.setItem("usermsg", JSON.stringify(T.usermsg));
					} else {
						$.login();
					}
				}
			});*/
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

/**
 * footer.hide();	//隐藏
 * footer.show();	//显示
 * footer.select(3);	选中索引值从0开始
 * 回调方法，第一个参数为当前li对像，i为索引值
 * footer.callback(function(that, i){});
 * 
 */
var footer = {
	state:true, 	//是否显示
	initState:false,
	index:0,
	fun:null,
	callback: function(fun){
		this.fun = fun;
	},
	dom: $("<ul class='nav'>"),
	init:function (){
		var li1 = $("<li>");
		li1.append('<i class="iconfont">&#xe6b2;</i>');
		li1.append('<a>抢购</a>');
		var li2 = $("<li>");
		li2.append('<i class="iconfont">&#xe6b3;</i>');
		li2.append('<a>订场</a>');
		var li3 = $("<li>");
		li3.append('<i class="iconfont">&#xe6b0;</i>');
		li3.append('<a>优惠券</a>');
		var li4 = $("<li>");
		li4.append('<i class="iconfont">&#xe642;</i>');
		li4.append('<a>活动</a>');
		//var li5 = $("<li>");
		//li5.append('<i class="iconfont">&#xe639;</i>');
		//li5.append('<a>团购</a>');
		var li6 = $("<li>");
		li6.append('<i class="iconfont">&#xe693;</i>');
		li6.append('<a>我的</a>');
		//this.dom.append(li1).append(li2).append(li3).append(li4).append(li5).append(li6);
		this.dom.append(li1).append(li2).append(li3).append(li4).append(li6);
		this.initState = true;
		this.dom.find('li').click(function(){
			if (typeof (footer.fun) === "function") {
				footer.fun(this, $(this).index());
			}
		});
		this.index = T.p('footer');
		this.select();
		this.load();
	},
	hide:function(){
		this.state = false;
		this.load();
	},
	show:function(){
		this.state = true;
		this.load();
	},
	load:function(){
		if(this.initState){
			if(!this.state){
				this.dom.hide();
			} else {
				$(".footer").append(this.dom);
				this.dom.show();
			}
		}
	},
	select:function(i){
		if(i){
			this.index = i;
		} 
		if(this.index == -1){
			this.dom.find('li').removeClass('select');
		} else {
			this.dom.find('li').removeClass('select');
			this.dom.find('li').eq(this.index).addClass('select');
		}
	}
}
footer.callback(function(that, i){
	switch(i) {
	     case 1:
	        location.href = T.basepath + 'H5_reservation_list.html?footer=1';
	        break;
	     case 2:
			location.href = T.basepath + 'coupons_list.html?footer=2';
	        break;
		case 3:
			location.href = T.basepath + 'H5_activity_list.html?footer=3';
			break;
		//case 4:
			//location.href = T.basepath + 'H5_groupBuying_list.html?footer=4';
			//break;
		case 4:
			location.href = T.basepath + 'my_info.html?footer=5';
			break;
	     default:
	        location.href = T.basepath + 'panicBuying_list.html?footer=0';
	} 
});
$(function(){
	footer.init();
});