$(function(){
	var api = {
		info:{url:T.serverebusiness + "/business/activity/info", type:"get"},
		list:{url:T.serverebusiness + "/business/activity/selectlist", type:"post"},
		page:{url:T.serverebusiness + "/business/activity/page", type:"post"}
	};

    $.ajax({
        type : "POST",
        url : api.list.url,
        method : api.list.type,
        data : JSON.stringify({type:0,selectVal:0,state:1}),
        headers : {token:T.token},
        contentType : 'application/json;charset=utf-8',
        success : function(r) {
        	console.log(r);
            if (r.code == 0) {
                var list = '<ul>';
                $.each(r.data, function(i, obj){
                    list += '<li class="view">'+
                        '<div class="pic">'+
                        '<a title="" class="button" href="snapUp_info.html?guid='+obj.guid+'">'+
                        '<img src="'+T.serverebusiness + obj.img+'">'+
                        '<div class="cont">'+
                        '<div class="num">'+
                        /*'<p><span>20/30</span>人报名</p>'+
                        '<p>出发时间<span>2017/10/02</span></p>'+*/
                        '<p>￥<span>'+obj.price/100+'</span></p>'+
                        '<p>开始时间：<span>'+obj.startTime+'</span></p>'+
                        '</div></div>'+
                        '<div class="clear"></div>'+
                        '</div>'+
                        '<div class="con">'+
                        '<span>'+obj.name+'</span>'+
                        '</a>'+
                        '</div>'+
                        '</li>';
                });
                list += '</ul>';
                $("#trip_list").append(list);
            } else {
                alertMsg(r);
            }
        }
    });
});