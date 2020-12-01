var activityGuid = getQueryString("guid");
var inviterGuid = getQueryString("inviterGuid");//邀请人guid
var buyNumber = 0;  //商品限购数量
var vipPrice = 100;//会员价
var originalPrice = 100;//原价

var sigPhone = 0;
$(function(){
    var api = {
        info:{url:T.serverebusiness + "/business/activity/info", type:"get"},
        list:{url:T.serverebusiness + "/business/activity/list", type:"post"},
        page:{url:T.serverebusiness + "/business/activity/page", type:"post"},
        activityApply:{url:T.serverebusiness + "/business/apply/activityapply", type:"post"}
    };
    if(isNull(activityGuid)){
        $.ajax({
            type : "POST",
            url : api.info.url,
            method : api.info.type,
            headers : {token:T.token},
            data : {guid:activityGuid},
            success : function(r) {
                if (r.code == 0) {
                    if(r.data.state == 0){
                        location.href = "H5_activity_list.html";
                    }
                    var currentTime = new Date().getTime().toString();
                    var startTime = new Date(r.data.startTime.replace(/-/g, '/')).getTime().toString();
                    var endTime = new Date(r.data.endTime.replace(/-/g, '/')).getTime().toString();
                    if(currentTime > endTime){
                        //判断是否已结束
                        $("#buy_nav").append('<a class="cur" style="background-color: #B7B2B3" href="#">已结束</a>');
                        $(".sprice").show();
                    }else if(currentTime < startTime){
                        //判断是否未开始
                        $("#buy_nav").append('<a class="cur" style="background-color: #B7B2B3" href="#">未开始</a>');
                        $(".sprice").show();
                    }else{
                        if(isNull(r.data.price) && r.data.price > 0){
                            $("#buy_nav").append('<a class="cur" href="#">立即报名</a>');
                            $(".sprice").show();
                        }else{
                            $("#buy_nav").append('<a class="cur" href="#">立即领取</a>');
                        }
                    }
                    document.title = r.data.name;
                    $("#nameLabel").html(r.data.name);
                    $("#consultphone").html(r.data.phone);
                    buyNumber = r.data.buyNumber;
                    vipPrice = r.data.price;
                    originalPrice = r.data.originalPrice;
                    $(".call").attr('href','tel:'+r.data.phone+'');
                    //$("#data-price").attr("data-price",r.data.originalPrice/100);
                    //$("#data-price").text(r.data.originalPrice/100);
                    $("#data-price").attr("data-price",r.data.price/100);
                    $("#data-price").text(r.data.price/100);
                    $("#originalPrice").html("￥"+r.data.originalPrice/100);
                    $("#price").html("￥"+r.data.price/100);
                    $("#date").html("活动组织时间：" + formatDate(r.data.date));
                    //$("#startTime").html("开始时间：" + r.data.startTime);
                    //$("#endTime").html("结束时间：" + r.data.endTime);
                    $(".con").append(r.data.content);
                    $("#activityImg").attr('src',T.serverebusiness + r.data.img);
                    $('#clock').countdown(r.data.endTime, function(event) {
                        $(this).html(event.strftime('%D 天 %H:%M:%S'));
                    });
                    //分享加载
                    wxapi.setShare({
                        title:(r.data.name),
                        desc:('活动价格：'+r.data.price/100+'元 \n活动时间：'+formatDate(r.data.date)),
                        link:location.href,
                        imgUrl : T.serverebusiness + r.data.img
                    });
                } else {
                    alert(r.msg);
                }
            }
        });
    }
});


//验证码
var authCodeValue = "";
//电话号码验证
function isPhoneNo(phone) {
    var pattern = /^1[34578]\d{9}$/;
    return pattern.test(phone);
}
//发送短信
function authCodeActivity() {
    var phoe  = $("#phoeUser").val();
    if(!isNull(phoe)){
        alert('手机号不能为空');
        return false;
    }else if(isPhoneNo(phoe) == false){
        alert('请输入正确手机号');
        return false;
    }else {
        $.ajax({
            type : "POST",
            url : T.serverebusiness + "/security/wxoauth/authcode",
            headers : {token:T.token},
            data : {phone:phoe},
            success : function(r) {
                if (r.code == 0) {
                    authCodeValue = r.msg;
                    var Time = 60;
                    var TimeDown = setInterval(timedown,1000);
                    //倒计时
                    function timedown(){
                        $("#dynamic").attr("disabled", true);
                        $("#dynamic").text(""+ Time + "s");
                        if(Time == 0){
                            $("#dynamic").val("重新获取").removeAttr("disabled");
                            clearInterval(TimeDown);
                        }
                        Time--;
                    }
                } else {
                    $("#dynamic").val("重新获取").removeAttr("disabled");
                    alert(r.msg);
                }
            }
        });
    }
}

//完善信息
function submitUser(){
    if(sigPhone == 0){
        $.ajax({
            type : "POST",
            url : T.serverebusiness + "/security/wxoauth/register",
            headers : {token:T.token},
            async: false,
            data : {name:$("#nameUser").val(),chad:$("#chadUser").val(),phone:$("#phoeUser").val(),authCode:$("#code").val()},
            success : function(r) {
                if (r.code == 0) {
                    submitOrders();
                } else {
                    alert(r.msg);
                }
            }
        });
    }else{
        submitOrders();
    }
}


//提交订单
function submitOrders() {
    //判断是否是本人打开自己分享的连接，自己下单不加佣金
    /*if(inviterGuid == T.usermsg.guid){
        inviterGuid = null;
    }*/
    var result = true;
    var typeArr = new Array();
    var params = new Array();
    $("#items").find("li").each(function(){
        var type = $(this).find("input:checked").val();
        typeArr.push(type);
        var object = {};
        var name = $(this).find("input[name='name']").val();
        var phone = $(this).find("input[name='phoe']").val();
        var chad = $(this).find("input[name='chad']").val();
        if(!isNull(name)){
            layer.open({
                content: '报名人姓名不可为空'
                ,skin: 'msg'
                ,time: 3 //2秒后自动关闭
            });
            result = false;
            return false;
        }
        /*if(!isNull(phone)){
            layer.open({
                content: '报名人电话不可为空'
                ,skin: 'msg'
                ,time: 3 //2秒后自动关闭
            });
            result = false;
            return false;
        }*/
        if(!isNull(chad)){
            layer.open({
                content: '报名人差点不可为空'
                ,skin: 'msg'
                ,time: 3 //2秒后自动关闭
            });
            result = false;
            return false;
        }
        object['name'] = $(this).find("input[name='name']").val();
        object['phone'] = $(this).find("input[name='phoe']").val();
        object['chad'] = $(this).find("input[name='chad']").val();
        params.push(object)
    })
    if (!result){
        return false;
    }

    var json = JSON.stringify(params);
    layer.closeAll();
    layer.open({
        type: 2,
        shadeClose: false,
        content: '加载中'
    });
    $.ajax({
        type : "POST",
        url : T.serverepay + "/pay/order/addorder",
        headers : {token:T.token},
        traditional : true,
        data : {goodsGuid:activityGuid,/*inviterGuid:inviterGuid,*/sum:$("#items").children().length,typeArr:typeArr,userJson:json,remark:$("#remark").val()},
        success : function(res) {
            layer.closeAll();
            if (res.code == 0) {
                if(isNull(res.data) && res.data.orderMoney == 0){
                    alert("恭喜您，领取成功。请关注“九洲高尔夫”公众号预定使用！");
                    location.href = "alone_order_list.html";
                }else{
                    //location.href = "sure_order2.html?guid="+activityGuid+"&orderGuid="+res.data.guid;
                    if (res.code == 0) {
                        var data = $.parseJSON(res.msg);
                        if (typeof WeixinJSBridge == "undefined") {
                            if (document.addEventListener) {
                                document.addEventListener('WeixinJSBridgeReady',
                                    onBridgeReady(data), false);
                            } else if (document.attachEvent) {
                                document.attachEvent('WeixinJSBridgeReady',
                                    onBridgeReady(data));
                                document.attachEvent('onWeixinJSBridgeReady',
                                    onBridgeReady(data));
                            }
                        } else {
                            onBridgeReady(data);
                        }
                    } else {
                        if (res.code == 2) {
                            layer.alert(res.msg);
                        } else {
                            layer.msg("error：" + res.msg, {
                                shift : 6
                            });
                        }
                    }
                }
            } else if (res.code == 500) {
                alert("提交订单失败！请重试");
            }else{
                alert(res.msg);
            }
        }
    });
}



//判断手机号是否为空
function submitOrdersInfo() {
    $.ajax({
        type : "GET",
        url : T.serverepay + "/security/user/selectUserMessage",
        headers : {token:T.token},
        success : function(res) {
            if (res.code == 0) {
                var data = res.data;
                if(!isNull(data.phone)){
                    sigPhone = 0;//0手机号为空   1手机号不为空
                    //手机号为空 进入到完善页面
                    $("#codeLi").show();
                }else{
                    sigPhone = 1;
                    $("#codeLi").hide();
                    $("#nameUser").val(T.usermsg.name);
                    $("#chadUser").val(T.usermsg.gap);
                    $("#phoeUser").val(T.usermsg.phone);

                    $("#defaultName").val(T.usermsg.name);
                    $("#defaultPhone").val(T.usermsg.phone);
                    $("#defaultChad").val(T.usermsg.gap);
                }
            } else {
                location.reload();
            }
        }
    });
}


//格式化日期字符串
function formatDate(date, format) {
    if (!date) return;
    if (!format) format = "yyyy-MM-dd";
    switch(typeof date) {
        case "string":
            date = new Date(date.replace(/\-/g, '/'));
            break;
        case "number":
            date = new Date(date);
            break;
    }
    if (!date instanceof Date) return;
    var dict = {
        "yyyy": date.getFullYear(),
        "M": date.getMonth() + 1,
        "d": date.getDate(),
        "H": date.getHours(),
        "m": date.getMinutes(),
        "s": date.getSeconds(),
        "MM": ("" + (date.getMonth() + 101)).substr(1),
        "dd": ("" + (date.getDate() + 100)).substr(1),
        "HH": ("" + (date.getHours() + 100)).substr(1),
        "mm": ("" + (date.getMinutes() + 100)).substr(1),
        "ss": ("" + (date.getSeconds() + 100)).substr(1)
    };
    return format.replace(/(yyyy|MM?|dd?|HH?|ss?|mm?)/g, function() {
        return dict[arguments[0]];
    });
}