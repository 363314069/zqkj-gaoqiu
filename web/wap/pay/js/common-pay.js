/**
 * Created by zqkj on 2019/11/14.
 */

//有订单GUID使用该支付
function wxpay(orderGuid) {
    layer.open({
        type: 2
        ,content: '加载中'
        ,shadeClose: false
    });
    $.ajax({
        type : "POST",
        url : T.serverepay + "/pay/order/jsapipay",
        headers : {token:T.token},
        data : {orderGuid:orderGuid},
        success : function(res) {
            layer.closeAll();
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
                    layer.open({
                        content: res.msg
                        ,skin: 'msg'
                        ,time: 3 //2秒后自动关闭
                    });
                } else {
                    layer.open({
                        content: res.msg
                        ,skin: 'msg'
                        ,time: 3 //2秒后自动关闭
                    });
                }
            }
        }
    });
}

//有订单GUID使用该支付
function payintroductionorder(orderGuid) {
    layer.open({
        type: 2
        ,content: '加载中'
        ,shadeClose: false
    });
    $.ajax({
        type : "POST",
        url : T.serverepay + "/pay/order/payintroductionorder",
        headers : {token:T.token},
        data : {orderGuid:orderGuid},
        success : function(res) {
            layer.closeAll();
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
                    layer.open({
                        content: res.msg
                        ,skin: 'msg'
                        ,time: 3 //2秒后自动关闭
                    });
                } else {
                    layer.open({
                        content: res.msg
                        ,skin: 'msg'
                        ,time: 3 //2秒后自动关闭
                    });
                }
            }
        }
    });
}


function onBridgeReady(json) {
    WeixinJSBridge.invoke('getBrandWCPayRequest', json, function(res) {
        // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。
        if (res.err_msg == "get_brand_wcpay_request:ok") {
            layer.open({
                content: '支付成功'
                ,skin: 'msg'
                ,time: 3 //2秒后自动关闭
            });
            location.href = "/wap/business/success.html?orderGuid="+json.orderGuid;
        } else {
            layer.open({
                content: '支付失败'
                ,skin: 'msg'
                ,time: 3 //2秒后自动关闭
            });
        }
    });
}
/* 微信公众号支付支付 END */


//付款是先进行查询信息是否完善
function queryUserMessage(orderGuid) {
    $.ajax({
        type : "GET",
        url : T.serverepay + "/security/user/selectUserMessage",
        headers : {token:T.token},
        success : function(res) {
            console.log(res);
            if (res.code == 0) {
                var data = res.data;
                if(!isNull(data.phone)){
                    //手机号为空 进入到完善页面
                    $('#dialogBg').fadeIn(100);
                    $('#dialog').removeAttr('class').addClass('animated').fadeIn();
                }else{
                    wxpay(orderGuid);
                }
            } else {
                location.reload();
            }
        }
    });
}

//完善信息
function submitUserOrder(){
    $.ajax({
        type : "POST",
        url : T.serverebusiness + "/security/wxoauth/register",
        headers : {token:T.token},
        /*dataType: "json",
         contentType : 'application/json;charset=utf-8',*/
        async: false,
        data : {name:$("#name").val(),chad:$("#chad").val(),phone:$("#phoe").val(),authCode:$("#code").val()},
        success : function(r) {
            $('#dialogBg').hide();
            $('#dialog').hide();
            if (r.code == 0) {
                alert("保存成功！");
                $('#dialogBg').hide();
                $('#dialog').hide();
            } else {
                alert(r.msg);
            }
        }
    });
}


//验证码
var authCodeValue = "";
//电话号码验证
function isPhoneNo(phone) {
    var pattern = /^1[34578]\d{9}$/;
    return pattern.test(phone);
}
//发送短信
function authCode() {
    var phoe  = $("#phoe").val();
    if(!isNull(phoe)){
        layer.open({
            content: '手机号不能为空',
            style: 'msg',
            time:3
        });
        return false;
    }else if(isPhoneNo(phoe) == false){
        layer.open({
            content: '请输入正确手机号'
            ,skin: 'msg'
            ,time: 3 //2秒后自动关闭
        });
        return false;
    }else {
        $.ajax({
            type : "POST",
            url : T.serverebusiness + "/security/wxoauth/authcode",
            headers : {token:T.token},
            data : {phone:phoe},
            success : function(r) {
                console.log(r);
                if (r.code == 0) {
                    authCodeValue = r.msg;
                    var Time = 60;
                    var TimeDown = setInterval(timedown,1000);
                    //倒计时
                    function timedown(){
                        $("#dynamic").attr("disabled", true);
                        $("#dynamic").val(""+ Time + "s");
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