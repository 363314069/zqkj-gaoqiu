$(function(){
$('legend').append(guid?'修改':'添加');
var api = {
	list:{url:T.serverurl + "business/couponsactmap/list", type:"post"},
	info:{url:T.serverurl + "business/couponsactmap/info", type:"get"},
	save:{url:T.serverurl + "business/couponsactmap/save", type:"post"},
	update:{url:T.serverurl + "business/couponsactmap/update", type:"post"}
}
layui.use('form', function() {
	var form = layui.form, 
	layer = layui.layer;
	form.on('submit(save)', function(data) {
		var type = api.save.type;
		var url = api.save.url;
		if (guid) {
			ype = api.update.type;
			url = api.update.url;
		}
		if(data.field.state == "undefield")
			data.field.state = 0;
		else 
			data.field.state = 1;
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
	
	if(guid){
		$.ajax({
			type : api.info.type,
			url : api.info.url,
			data : {t:(new Date()), guid:guid},
			success : function(r) {
				if(r.code == 0 && r.data){
					form.val("form", r.data);
					form.render();
				} else {
					alertMsg(r);
				}
			}
		});
	}
	form.render();
});
});