$(function(){
var parentId = T.p("parentId");
$('legend').append(id?'修改':'添加');
var api={
	provinceList:{url:T.serverurl + "security/param/list", type:"get"},
	provinceInfo:{url:T.serverurl + "security/param/info", type:"get"},
	provinceByGuidList:{url:T.serverurl + "security/param/listByGuids", type:"post"},
	info:{url:T.serverurl + "security/param/info",type:"get"},
	save:{url:T.serverurl + "security/param/save",type:"post"},
	update:{url:T.serverurl + "security/param/update",type:"post"}
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
					//alert(r.msg);
					alert(r);
					$("form div[name='namePrompt']").empty();
					if(r.name)
						$("form div[name='namePrompt']").append("<font color='#FF0000'>"+r.name+"</font>");
				}
			}
		});
		return false;
	});
	$('[lay-filter="back"]').on('click', function(data) {
		$.back();
	});
	if(parentId && parentId > 0){
		$.get(api.info.url, {id:parentId}, function(r) {
			form.val("form", {parentId:parentId, parentName:r.data.name});
			form.render();
		});
	} else {
		form.val("form", {parentId:parentId, parentName:"无"});
		form.render();
	}
	
	if(id){
		$.get(api.info.url, {id:id}, function(r) {
			form.val("form", r.data);
			form.render();
		});
	}
});
});