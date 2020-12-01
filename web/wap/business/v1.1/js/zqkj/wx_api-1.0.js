//ajax注入权限验证
//jsApiList : [ 'checkJsApi','updateAppMessageShareData', 'updateTimelineShareData','getLocation' ]
var wxapi = {
	//39.908823,116.397470  北京   code 11
	location:{lat:39.908823, lng:116.397470, province:"北京", code:11},
	selectarea: null,	//{province:"北京", code:11}
	getLocation:function(){
		return this.location;
	},
	initstate:false,
	jsApiList:new Array(),
	addApiList:function(jsApiList){
		if(jsApiList)
			this.jsApiList = this.jsApiList.concat(jsApiList);
	},
	share:{
		title:($('title').text() == 'WECHAT_EMPTY_TITLE')?'分享九州高尔夫平台':('分享九州高尔夫平台-' + $('title').text()),
		desc:($('.wrap').text() && $('.wrap').text() != "")?$('.wrap').text():'欢迎加入九州高尔夫平台！',
		link:location.href,
		imgUrl:'http://wap.oneagles.com/wap/common/images/logo.png'
	},
	setShare:function(params){
		this.share = params;
	},
	init:function(url){
		this.initstate = true;
		var res = localStorage.getItem("location");
		if(res){
			this.location = JSON.parse(res);
		}
		res = localStorage.getItem("selectarea");
		if(res){
			this.selectarea = JSON.parse(res);
		}
		if(!url)
			url = decodeURIComponent(location.href.split('#')[0]);
		$.ajax({
			type : "POST",
			url : T.serverurl + "/security/wxoauth/share",
			headers : {token:T.token},
			data : {strUrl:url},
			async: false,
			dataType : 'json',
			success : function(res) {
				wx.config({
					debug : false, //开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
					appId : res.data.appId, //必填，公众号的唯一标识
					timestamp : res.data.timestamp, // 必填，生成签名的时间戳
					nonceStr : res.data.noncestr, //必填，生成签名的随机串
					signature : res.data.signature, // 必填，签名，见附录1
					jsApiList : wxapi.jsApiList //必填，需要使用的JS接口列表，所有JS接口列表 见附录2
				}); // end wx.config
				wx.error(function(res) {
					
					// config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
				});
			} // end success
		}); // end ajax
	}
}
wxapi.addApiList(['checkJsApi','updateAppMessageShareData', 'updateTimelineShareData', 'onMenuShareTimeline', 'onMenuShareAppMessage', 'getLocation', 'openLocation']);
wx.ready(function() {
	wx.checkJsApi({
		jsApiList: ['updateAppMessageShareData', 'updateTimelineShareData', 'onMenuShareTimeline', 'onMenuShareAppMessage'], // 需要检测的JS接口列表，所有JS接口列表见附录2,
		success: function(res) {
			if (res.checkResult.updateAppMessageShareData == true || res.checkResult.updateAppMessageShareData == 'yes') {
				//分享给朋友
				wx.updateAppMessageShareData({
					title: wxapi.share.title, // 分享标题
					desc: wxapi.share.desc, // 分享描述
					link: wxapi.share.link, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
					imgUrl: wxapi.share.imgUrl, // 分享图标
					success: function () {
						// 设置成功
					},
					cancel: function () {
						// 用户取消分享后执行的回调函数
					}
				});
			} else {
				//旧的分享到朋友接口
				wx.onMenuShareAppMessage({
					title: wxapi.share.title, // 分享标题
					desc: wxapi.share.desc, // 分享描述
					link: wxapi.share.link, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
					imgUrl: wxapi.share.imgUrl, // 分享图标
					type: '', // 分享类型,music、video或link，不填默认为link
					dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
					success: function () {
						// 用户点击了分享后执行的回调函数
					}
				});
			}
			//分享给朋友圈
			if (res.checkResult.updateTimelineShareData == true || res.checkResult.updateTimelineShareData == 'yes') {
				//分享给朋友圈
				wx.updateTimelineShareData({
					title: wxapi.share.title, // 分享标题
					link: wxapi.share.link, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
					imgUrl: wxapi.share.imgUrl, // 分享图标
					success: function () {
						// 设置成功
					}
				});
			} else {
				//旧的分享朋友圈接口
				wx.onMenuShareTimeline({
					title: wxapi.share.title, // 分享标题
					link: wxapi.share.link, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
					imgUrl: wxapi.share.imgUrl, // 分享图标
					success: function () {
						// 用户点击了分享后执行的回调函数
					}
				});
			}
		}
	});
	wx.getLocation({
		type: 'gcj02', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'  'wgs84'
		success: function (res) {
			//latitude: res.latitude, // 纬度，浮点数，范围为90 ~ -90
			//longitude: res.longitude ,// 经度，浮点数，范围为180 ~ -180。
			var l = getDisance(wxapi.location.lng, wxapi.location.lat, res.longitude, res.latitude);
			//console.log(l + "公里");
			if(l > 10){
				getAreaInfo(res.latitude, res.longitude);
			}
			wxapi.location.lat = res.latitude;
			wxapi.location.lng = res.longitude;
			localStorage.setItem("location", JSON.stringify(wxapi.location));
			$(function(){
				//wxapi.msg = "(" +wxapi.location.lat + "," + wxapi.location.lng+ ")" + l;
				//alert(wxapi.msg);
			})
		},
		fail: function(e){
			//console.log("失败" + e);
			getAreaInfoByIp();
		}
	});
});
var toRad = function(d) {return d * Math.PI / 180;}
var getDisance = function(lng1, lat1, lng2, lat2) { //#lat为纬度, lng为经度, 一定不要弄错
	var dis = 0;
	var radLat1 = toRad(lat1);
	var radLat2 = toRad(lat2);
	var deltaLat = radLat1 - radLat2;
	var deltaLng = toRad(lng1) - toRad(lng2);
	var dis = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(deltaLat / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(deltaLng / 2), 2)));
	return dis * 6378137;
}
function getAreaInfo(lat, lng){
	var data = {
		location:lat + "," + lng,
		/*换成自己申请的key*/
		key:"E7JBZ-5UO3R-NIJWJ-WXGP5-P2R5V-IKFZY",
		get_poi:0,
		output:"jsonp"
	}
	var url="https://apis.map.qq.com/ws/geocoder/v1/";
	$.ajax({
		type:"get",
		dataType:'jsonp',
		data:data,
		url:url,
		jsonp:"callback",
		jsonpCallback:"QQmap",
		success:function(json){
			wxapi.location.province = json.result.ad_info.province;
			wxapi.location.code = parseInt(json.result.ad_info.adcode / 10000);
			localStorage.setItem("location", JSON.stringify(wxapi.location));
		},
		error : function(err){}
	})
}
function getAreaInfoByIp(){
	var data = {
		key:"E7JBZ-5UO3R-NIJWJ-WXGP5-P2R5V-IKFZY",
		output:"jsonp"
	}
	var url="https://apis.map.qq.com/ws/location/v1/ip";
	$.ajax({
		type:"get",
		dataType:'jsonp',
		data:data,
		url:url,
		jsonp:"callback",
		jsonpCallback:"QQmap",
		success:function(json){
			wxapi.location.province = json.result.ad_info.province;
			wxapi.location.code = parseInt(json.result.ad_info.adcode / 10000);
			wxapi.location.lat = json.result.location.lat;
			wxapi.location.lng = json.result.location.lng;
			localStorage.setItem("location", JSON.stringify(wxapi.location));
		},
		error : function(err){}
	})
}
wxapi.init();
