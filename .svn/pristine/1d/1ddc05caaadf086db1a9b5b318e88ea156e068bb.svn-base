var userCouponsSum = new Map();
$(function(){
$('legend').append('用户优惠券兑换');
var userGuid = T.p("userGuid");
var api = {
	list:{url:T.serverurl + "business/couponsuser/list", type:"post"},
	info:{url:T.serverurl + "business/couponsuser/info", type:"get"},
	save:{url:T.serverurl + "business/couponsuser/usercouponsexchange", type:"post"},
	couponsList:{url:T.serverurl + "business/coupons/list", type:"post"},
	listByGuids:{url:T.serverurl + "business/coupons/listByGuids", type:"post"},
	update:{url:T.serverurl + "business/couponsuser/update", type:"post"}
}
layui.use('form', function() {
	var form = layui.form, 
	layer = layui.layer;
	form.on('submit(save)', function(data) {

		var type = api.save.type;
		var url = api.save.url;

		data.field.userGuid = userGuid;
		if(data.field.reduceCouponsSum > userCouponsSum.get(data.field.reduceCouponsGuid)){
			alert("减少优惠券数量不能大于现有数量！");
			return false;
		}

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

	//查询自己的优惠券
	$.ajax({
		type:api.list.type,
		url:api.list.url,
		data:{"userGuid":userGuid,"ifUse":0},
		success : function(r) {
			if (r.code == 0) {
				var couponsGuids = new Array();
				$.each(r.data, function(i, obj){
					if(isNull(obj.couponsGuid)){
						couponsGuids.push(obj.couponsGuid);
						if(userCouponsSum.has(obj.couponsGuid)){
							let sum = userCouponsSum.get(obj.couponsGuid);
							userCouponsSum.set(obj.couponsGuid,sum+1);
						}else{
							userCouponsSum.set(obj.couponsGuid,1);
						}
					}
				});
				$.ajax({
					type:api.listByGuids.type,
					traditional: true,
					url:api.listByGuids.url,
					data : {"guids":couponsGuids},
					success : function(res) {
						if (res.code == 0) {
							$.each(res.data, function(i, obj){
								if(i == 0){
									$("#existingSum").text("现有："+userCouponsSum.get(obj.guid)+"张");
								}
								$('#reduceCouponsGuid').append('<option value="'+obj.guid+'">'+obj.name+'</option>');
							});
							form.render();
						} else {
							alertMsg(res);
						}
					}
				});
			} else {
				alertMsg(r);
			}
		}
	});


	//查询所有优惠券
	$.ajax({
		type:api.couponsList.type,
		url:api.couponsList.url,
		success : function(r) {
			if (r.code == 0) {
				$.each(r.data, function(i, obj){
					$('#addCouponsGuid').append('<option value="'+obj.guid+'">'+obj.name+'</option>');
				});
				form.render();
			} else {
				alertMsg(r);
			}
		}
	});
	form.render();


	//切换现有优惠券重新展示数量
	form.on('select(existingSwitch)', function(data){
		$("#existingSum").text("现有："+userCouponsSum.get(data.value)+"张");
	});
});
});