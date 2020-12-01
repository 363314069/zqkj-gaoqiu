$(function(){
$('legend').append('用户添加优惠券');
var userGuid = T.p("userGuid");
var api = {
	list:{url:T.serverurl + "business/couponsuser/list", type:"post"},
	info:{url:T.serverurl + "business/couponsuser/info", type:"get"},
	save:{url:T.serverurl + "business/couponsuser/addusercoupons", type:"post"},
	couponsList:{url:T.serverurl + "business/coupons/list", type:"post"},
	update:{url:T.serverurl + "business/couponsuser/update", type:"post"}
}
layui.use('form', function() {
	var form = layui.form, 
	layer = layui.layer;
	form.on('submit(save)', function(data) {
		var type = api.save.type;
		var url = api.save.url;

		data.field.userGuid = userGuid;
		data.field.creator = userGuid;
		data.field.state = 0;
		data.field.type = 1;
		data.field.ifUse = 0;

		$.ajax({
			type : type,
			url : url,
			data : data.field,
			success : function(r) {
				if (r.code === 0) {
					alert('操作成功', function(index) {
						$.back();
					});
				} else {
					alertMsg(r);
				}
			}
		});
		return false;
	});
	
	$('#back').on('click', function(data) {
		$.back();
	});

	$.ajax({
		type:api.couponsList.type,
		url:api.couponsList.url,
		success : function(r) {
			if (r.code == 0) {
				$.each(r.data, function(i, obj){
					$('#couponsGuid').append('<option value="'+obj.guid+'">'+obj.name+'</option>');
				});
				form.val("form", r.data);
				form.render();
			} else {
				alertMsg(r);
			}
		}
	});
	form.render();
});
});