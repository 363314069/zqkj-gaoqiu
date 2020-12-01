//微信分析公用，页面加载完后调用此方法，必须页面中设置 wxTtitle   wxDesc   wxImgUrl
function jssdk(wxTtitle,wxDesc,wxImgUrl,urlJump) {
    var url = decodeURIComponent(location.href.split('#')[0]);
    //ajax注入权限验证
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
                jsApiList : [ 'checkJsApi','updateAppMessageShareData', 'updateTimelineShareData','getLocation' ] //必填，需要使用的JS接口列表，所有JS接口列表 见附录2
            }); // end wx.config
            wx.ready(function() {
                wx.checkJsApi({
                    jsApiList: ['chooseImage'], // 需要检测的JS接口列表，所有JS接口列表见附录2,
                    success: function(res) {
                        console.log(res)
                        // 以键值对的形式返回，可用的api值true，不可用为false
                        // 如：{"checkResult":{"chooseImage":true},"errMsg":"checkJsApi:ok"}
                    }
                });
                // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
                //分享给朋友
                wx.updateAppMessageShareData({
                    title: wxTtitle, // 分享标题
                    desc: wxDesc, // 分享描述
                    link: urlJump, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
                    imgUrl: wxImgUrl, // 分享图标
                    success: function () {
                        // 设置成功
                    },
                    cancel: function () {
                        // 用户取消分享后执行的回调函数
                    }
                });

                //分享给朋友圈
                wx.updateTimelineShareData({
                    title: wxTtitle, // 分享标题
                    link: urlJump, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
                    imgUrl: wxImgUrl, // 分享图标
                    success: function () {
                        // 设置成功
                    }
                });

            }); // end ready
            wx.error(function(res) {
                console.log("------------------"+res)
                // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
            });
        } // end success
    }); // end ajax
}
