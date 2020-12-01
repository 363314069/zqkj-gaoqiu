$(function(){
var api = {
	save:{url:T.serverebusiness + "security/wxpushed/beingpushed", type:"post"}
}
layui.use('form', function() {
	var form = layui.form;
	var laydate = layui.laydate;

	/*form.on('submit(save)', function(data) {
		$.ajax({
			type : api.save.type,
			url : api.save.url,
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
	});*/
	
	$('#back').on('click', function(data) {
		$.back();
	});

	laydate.render({
		elem: '#date' //或 elem: document.getElementById('test')、elem: lay('#test') 等
		,type: 'datetime'
	});

	form.render();
});
});