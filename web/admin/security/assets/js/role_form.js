$(function(){
$('legend').append(id?'修改':'添加');
var api = {
	organizationList:{url:T.serverurl + "security/organization/list", type:"post"},
	info:{url:T.serverurl + "security/role/info", type:"get"},
	save:{url:T.serverurl + "security/role/save", type:"post"},
	update:{url:T.serverurl + "security/role/update", type:"post"}
}
layui.use('form', function() {
	var form = layui.form, 
	layer = layui.layer;
	//vm.initInfo(id);
	form.on('submit(save)', function(data) {
		var type = api.save.type;
		var url = api.save.url;
		if (id) {
			type = api.update.type;
			url = api.update.url;
		}
		if(data.field.state == undefined)
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
	
	$.post(api.organizationList.url, function(r) {
		if(r.code != 0){
			alert(r.msg);
			return;
		}
		var select = $("form").find("select[name=organizationGuid]");
		for (var i = 0; i < r.data.length; i++) {
			var option = $("<option>");
			option.val(r.data[i].guid);
			option.append(r.data[i].name);
			select.append(option);
			//r.data[i]
		}
		var data = {state:1}
		form.val("form", data);
		form.render();
	});
	if(id){
		$.get(api.info.url,
			{t:(new Date()),id:id},
			function(json) {
			form.val("form", json.data);
			form.render();
		});
	}
});
});