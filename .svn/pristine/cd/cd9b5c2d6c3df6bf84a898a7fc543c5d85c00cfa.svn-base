$(function(){
$('legend').append(id?'修改':'添加');
layui.use('form', function() {
	var form = layui.form, 
	layer = layui.layer;
	//vm.initInfo(id);
	form.on('submit(save)', function(data) {
		var type = "POST";
		var url = T.serverurl + "jwtauth/province/save";
		if (id) {
			type = "POST";
			url = T.serverurl + "jwtauth/province/update";
		}
		if(data.field.state == undefined)
			data.field.state = 0;
		else 
			data.field.state = 1;
		$.ajax({
			type : type,
			url : url,
			data : JSON.stringify(data.field),
			success : function(r) {
				if (r.code === 0) {
					alert('操作成功', function(index) {
						$.back();
					});
				} else {
					alert(r.msg);
					$("form div[name='namePrompt']").empty();
					
					if(r.name)
						$("form div[name='namePrompt']").append("<font color='#FF0000'>"+r.name+"</font>");
				}
			}
		});
		return false;
	});
	$('#back').on('click', function(data) {
		$.back();
	});
	$.post(T.serverurl + "jwtauth/province/list?parentId=0",function(json) {
		for(var i= 0;i<json.data.length;i++){
			//获取全部数据
			$("form select[name='parentId']").append("<option value="+json.data[i].id+">"+json.data[i].name+"</option>");
		}
		if(T.p("parentId"))
			$("form select[name='parentId']").val(T.p("parentId"));
		form.render();
	});
	if(id)
		$.get(T.serverurl + "jwtauth/province/info/" + id + "?t=" + (new Date()),function(json) {
			$("form").initForm({jsonValue:json.data});
			form.render();
		});
});
});