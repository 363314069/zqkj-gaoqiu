$(function(){
$('legend').append(guid?'修改':'添加');
var api = {
	list:{url:T.serverurl + "security/vipcoupons/list", type:"post"},
	info:{url:T.serverurl + "security/vipcoupons/info", type:"get"},
	save:{url:T.serverurl + "security/vipcoupons/save", type:"post"},
	update:{url:T.serverurl + "security/vipcoupons/update", type:"post"}
}
layui.use('form', function() {
    var laydate = layui.laydate;
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
	laydate.render({
		elem: '#startTime' //或 elem: document.getElementById('test')、elem: lay('#test') 等
		,type: 'datetime'
	});
	laydate.render({
		elem: '#endTime' //或 elem: document.getElementById('test')、elem: lay('#test') 等
		,type: 'datetime'
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