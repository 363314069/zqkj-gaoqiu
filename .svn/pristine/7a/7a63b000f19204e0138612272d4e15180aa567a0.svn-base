var api = {
    info:{url:T.serverebusiness + "/business/coupons/info", type:"get"},
    list:{url:T.serverebusiness + "/business/coupons/list", type:"post"},
    getcoupons:{url:T.serverebusiness + "/business/coupons/getcoupons", type:"post"},
    page:{url:T.serverebusiness + "/business/coupons/page", type:"post"}
};

$(function(){
    $.ajax({
        type : "POST",
        url : api.list.url,
        method : api.list.type,
        headers : {token:T.token},
        async : false,
        data : {"orderBy":"createTime desc",state:1},
        success : function(r) {
        	console.log(r);
            if (r.code == 0) {
                $.each(r.data, function(i, obj){
                    //1、全品类 2、指定商品 3、指定商家全品类 4、指定商家指定商品
                    var div1 = $('<div class="tab-item-list">');
                    var div2 = $('<div class="aui-flex">');
                    var div3 = $('<div class="aui-show-box" id="'+obj.guid+'"><p>'+obj.useRules+'</p></div>');

                    var div2_1 = $('<div class="aui-left-change">');
                    var div2_2 = $('<div class="aui-flex-box aui-flex-box-two">');
                    div2_1.append('<h2><em>￥</em>'+obj.faceValue/100+'</h2><p>满'+obj.moneyConform/100+'元可用</p><div class="aui-icon-circle"></div>');
                    if(obj.scope == 1){
                        div1 = $('<div class="tab-item-list tab-item-list-green">');
                        div2_2.append('<h3><em>券票</em>全场通用</h3>');
                    }else if(obj.scope == 2){
                        div2_2.append('<h3><em>券票</em>指定商品</h3>');
                    }else if(obj.scope == 3){
                        div2_2.append('<h3><em>券票</em>指定商家全品类</h3>');
                    }else if(obj.scope == 4){
                        div2_2.append('<h3><em>券票</em>指定商家指定商品</h3>');
                    }
                    var div2_2_1 = $('<div class="aui-flex aui-flex-bor-bom">');
                    var div2_2_2 = $('<div class="aui-flex aui-flex-show">');

                    if(obj.termType == 1){
                        div2_2_1.append('<div class="aui-flex-box aui-flex-box-flow">过期时间：'+obj.endTime+'</div>');
                    }else{
                        div2_2_1.append('<div class="aui-flex-box aui-flex-box-flow">过期时间：'+obj.endTime+'</div>');
                    }
                    div2_2_1.append('<div class="aui-btn-use" guid="'+obj.guid+'">立即领取</div>');

                    div2_2_2.append('<div class="aui-flex-box">详细信息</div><div class="aui-show-btn" onclick="detailed(\''+obj.guid+'\')"><i class="icon fa fa-angle-right"></i></div>');

                    div2_2.append(div2_2_1).append(div2_2_2);
                    div2.append(div2_1).append(div2_2);
                    div1.append(div2);
                    div1.append(div3);
                    $(".tab-item").append(div1);
                });
            } else {
                alert(r.msg);
            }
        }
    });

    $('.aui-btn-use').click(function(){
        $.ajax({
            type : "POST",
            url : api.getcoupons.url,
            method : api.getcoupons.type,
            headers : {token:T.token},
            async : false,
            data : {guid : $(this).attr("guid")},
            success : function(r) {
                if (r.code == 0) {
                    alert("恭喜您，领取成功。请关注“九洲高尔夫”公众号使用！");
                } else {
                    alert(r.msg);
                }
            }
        });
    });
});


//详细信息
function detailed(guid) {
    if($("#"+guid).is(':hidden')){
        $("#"+guid).show();
    }else{
        $("#"+guid).hide();
    }
}