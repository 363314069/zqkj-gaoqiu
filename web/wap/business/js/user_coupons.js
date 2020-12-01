var api = {
    usercouponslist:{url:T.serverebusiness + "/business/couponsuser/list", type:"post"},
    overdueunused:{url:T.serverebusiness + "/business/couponsuser/overdueunused", type:"post"}
};

$(function(){
    unused();
    /*hasBeenUsed();
    overdue();*/
});

//未使用优惠券
function unused() {
    $.ajax({
        type : "POST",
        url : api.usercouponslist.url,
        method : api.usercouponslist.type,
        data : {"userGuid":T.usermsg.guid,ifUse:0},
        headers : {token:T.token},
        success : function(r) {
            if (r.code == 0) {
                console.log(r.data);
                /*$.each(r.data, function(i, obj){
                    var list = '';
                    if(obj.couponsEntity.type != 4){
                        list += '<div class="tab-item-list"><div class="aui-flex"><div class="aui-left-change"><h2><em>￥</em>'+obj.couponsEntity.faceValue/100+'</h2><p>满'+obj.couponsEntity.moneyConform/100+'元可用</p>'+
                            '<div class="aui-icon-circle"></div></div><div class="aui-flex-box aui-flex-box-two"><h3><em>券票</em>'+obj.couponsEntity.name+'</h3><div class="aui-flex aui-flex-bor-bom">'+
                            '<div class="aui-flex-box aui-flex-box-flow">过期时间：'+obj.couponsEntity.endTime+'</div></div>'+
                            '<div class="aui-flex aui-flex-show"><div class="aui-flex-box">详细信息</div><div class="aui-show-btn"><i class="icon fa fa-angle-right"></i>'+
                            '</div></div></div></div><div class="aui-show-box"><p>默认隐藏内容</p></div></div></div>';
                    }else{
                        list += '<div class="tab-item-list tab-item-list-green"><div class="aui-flex"><div class="aui-left-change"><h2><em>￥</em>'+obj.couponsEntity.faceValue/100+'</h2><p>特殊商品除外</p>'+
                            '<div class="aui-icon-circle"></div></div><div class="aui-flex-box aui-flex-box-two"><h3><em>券票</em>'+obj.couponsEntity.name+'</h3><div class="aui-flex aui-flex-bor-bom">'+
                            '<div class="aui-flex-box aui-flex-box-flow">过期时间：'+obj.couponsEntity.endTime+'</div></div>'+
                            '<div class="aui-flex"><div class="aui-flex-box">详细信息</div><div class="aui-show-btn"><i class="icon fa fa-angle-right"></i>'+
                            '</div></div></div></div><div class="aui-show-box"><p>默认隐藏内容</p></div></div>';
                    }
                    $("#unused").append(list);
                });*/
            } else {
                alert(r.msg);
            }
        }
    });
}


//已过期优惠券
function overdue() {
    $.ajax({
        type : "POST",
        url : api.overdueunused.url,
        method : api.overdueunused.type,
        headers : {token:T.token},
        success : function(r) {
            if (r.code == 0) {
                $.each(r.data, function(i, obj){
                    var list = '';
                    if(obj.couponsEntity.type != 4){
                        list += '<div class="tab-item-list tab-item-list-default"><div class="aui-flex"><div class="aui-left-change"><h2><em>￥</em>'+obj.couponsEntity.faceValue/100+'</h2><p>满'+obj.couponsEntity.moneyConform/100+'元可用</p>'+
                            '<div class="aui-icon-circle"></div></div><div class="aui-flex-box aui-flex-box-two"><h3><em>券票</em>'+obj.couponsEntity.name+'</h3>'+
                            '<div class="aui-flex aui-flex-bor-bom"><div class="aui-flex-box aui-flex-box-flow">过期时间：'+obj.couponsEntity.endTime+'</div></div>'+
                            '<div class="aui-flex aui-flex-show"><div class="aui-flex-box">详细信息</div><div class="aui-show-btn"><i class="icon fa fa-angle-right"></i>'+
                            '</div></div></div></div><div class="aui-show-box"><p>默认隐藏内容</p></div></div>';
                    }else{
                        list += '<div class="tab-item-list tab-item-list-default"><div class="aui-flex"><div class="aui-left-change"><h2><em>￥</em>'+obj.couponsEntity.faceValue/100+'</h2><p>特殊商品除外</p>'+
                            '<div class="aui-icon-circle"></div></div><div class="aui-flex-box aui-flex-box-two"><h3><em>券票</em>'+obj.couponsEntity.name+'</h3>'+
                            '<div class="aui-flex aui-flex-bor-bom"><div class="aui-flex-box aui-flex-box-flow">过期时间：'+obj.couponsEntity.endTime+'</div></div>'+
                            '<div class="aui-flex aui-flex-show"><div class="aui-flex-box">详细信息</div><div class="aui-show-btn"><i class="icon fa fa-angle-right"></i>'+
                            '</div></div></div></div><div class="aui-show-box"><p>默认隐藏内容</p></div></div>';
                    }
                    $("#overdue").append(list);
                });
            } else {
                alert(r.msg);
            }
        }
    });
}


//已使用优惠券
function hasBeenUsed() {
    $.ajax({
        type : "POST",
        url : api.usercouponslist.url,
        method : api.usercouponslist.type,
        data : JSON.stringify({ifUse:1}),
        contentType : 'application/json;charset=utf-8',
        headers : {token:T.token},
        success : function(r) {
            if (r.code == 0) {
                $.each(r.data, function(i, obj){
                    var list = '';
                    if(obj.couponsEntity.type != 4){
                        list += '<div class="tab-item-list tab-item-list-default"><div class="aui-flex"><div class="aui-left-change"><h2><em>￥</em>'+obj.couponsEntity.faceValue/100+'</h2>'+
                            '<p>满'+obj.couponsEntity.moneyConform/100+'元可用</p><div class="aui-icon-circle"></div></div><div class="aui-flex-box aui-flex-box-two"><span class="aui-icon-used">'+
                            '<img src="images/icon-used.png" alt=""></span><h3><em>券票</em>'+obj.couponsEntity.name+'</h3><div class="aui-flex aui-flex-bor-bom">'+
                            '<div class="aui-flex-box aui-flex-box-flow">过期时间：'+obj.couponsEntity.endTime+'</div></div><div class="aui-flex aui-flex-show"><div class="aui-flex-box">详细信息</div>'+
                            '<div class="aui-show-btn"><i class="icon fa fa-angle-right"></i></div></div></div></div><div class="aui-show-box"><p>默认隐藏内容</p></div></div>';
                    }else{
                        list += '<div class="tab-item-list tab-item-list-default"><div class="aui-flex"><div class="aui-left-change"><h2><em>￥</em>'+obj.couponsEntity.faceValue/100+'</h2><p>特殊商品除外</p>'+
                            '<div class="aui-icon-circle"></div></div><div class="aui-flex-box aui-flex-box-two"><span class="aui-icon-used">'+
                            '<img src="images/icon-used.png" alt=""></span><h3><em>券票</em>'+obj.couponsEntity.name+'</h3><div class="aui-flex aui-flex-bor-bom">'+
                            '<div class="aui-flex-box aui-flex-box-flow">过期时间：'+obj.couponsEntity.endTime+'</div></div><div class="aui-flex aui-flex-show"><div class="aui-flex-box">详细信息</div>'+
                            '<div class="aui-show-btn"><i class="icon fa fa-angle-right"></i></div></div></div></div><div class="aui-show-box"><p>默认隐藏内容</p></div></div>';
                    }
                    $("#hasBeenUsed").append(list);
                });
            } else {
                alert(r.msg);
            }
        }
    });
}