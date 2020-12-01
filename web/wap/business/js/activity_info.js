var activityGuid = getQueryString("guid");

$(function(){
    var api = {
        info:{url:T.serverebusiness + "/business/activity/info", type:"get"},
        list:{url:T.serverebusiness + "/business/activity/list", type:"post"},
        page:{url:T.serverebusiness + "/business/activity/page", type:"post"},
        activityApply:{url:T.serverebusiness + "/business/apply/activityapply", type:"post"}
    };
    if(isNull(activityGuid)){
        console.log(api.info.url);
        $.ajax({
            type : "POST",
            url : api.info.url,
            method : api.info.type,
            headers : {token:T.token},
            data : {guid:activityGuid},
            success : function(r) {
                if (r.code == 0) {
                    $("#name").html(r.data.name);
                    $("#phone").html(r.data.phone);
                    $(".call").attr('href','tel:'+r.data.phone+'');
                    $("#price").html("￥"+r.data.price/100);
                    $("#startTime").html("开始时间：" + r.data.startTime);
                    $("#endTime").html("结束时间：" + r.data.endTime);
                    $(".con").append(r.data.content);
                    $(".cover").append('<img src="'+T.serverebusiness + r.data.img+'" id="coverimg" style="width: 100%;height: 100%;text-align: center;">');
                    jssdk(T.usermsg.name+"分享",r.data.name,T.serverebusiness + r.data.img);
                } else {
                    alert(r.msg);
                }
            }
        });
    }
});

function apply(){
    //location.href = T.serverebusiness + "/wap/pay/jsapi.html?guid="+activityGuid;
    location.href = "join.html?guid="+activityGuid;
    /*$.ajax({
        type : "post",
        url : T.serverebusiness + "apply/activityapply",
        headers : {token:T.token},
        data : {activityGuid:activityGuid},
        success : function(r) {
            if (r.code == 0) {
                alert("报名成功");
            } else {
                alert(r.msg);
            }
        }
    });*/
}