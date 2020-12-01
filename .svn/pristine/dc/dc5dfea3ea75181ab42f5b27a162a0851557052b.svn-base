var activityGuid = getQueryString("guid");

$(function(){
    var api = {
        info:{url:T.serverebusiness + "/activity/info", type:"get"},
        list:{url:T.serverebusiness + "/activity/list", type:"post"},
        page:{url:T.serverebusiness + "/activity/page", type:"post"},
        activityApply:{url:T.serverebusiness + "/apply/activityapply", type:"post"}
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
                    $("#price").html("￥"+r.data.price);
                    $(".con").append(r.data.content);
                    console.log(T.serverebusiness + r.data.img);
                    $(".cover").append('<img src="'+T.serverebusiness + r.data.img+'" id="coverimg" style="width: 100%;height: 100%;text-align: center;">');
                } else {
                    alert(r.msg);
                }
            }
        });
    }
});

function apply(){
    location.href = T.serverebusiness + "/wap/pay/jsapi.html?guid="+activityGuid;
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