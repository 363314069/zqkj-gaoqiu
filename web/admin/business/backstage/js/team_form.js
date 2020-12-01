$(function(){
$('legend').append(guid?'修改':'添加');
var api = {
	list:{url:T.serverurl + "business/team/list", type:"post"},
	info:{url:T.serverurl + "business/team/info", type:"get"},
	save:{url:T.serverurl + "business/team/save", type:"post"},
	update:{url:T.serverurl + "business/team/update", type:"post"},
	userList:{url:T.serverurl + "security/user/list", type:"post"},
	uploadFile:{url:T.serverebusiness + "/business/activity/uploadimage",type:"post"}
}
layui.use('form', function() {
	var form = layui.form, 
	layer = layui.layer, upload = layui.upload;

	//执行实例
	var uploadInst = upload.render({
		elem: '#imgs', //绑定元素
		accept: 'file',
		size: 307200,
		url: api.uploadFile.url, //上传接口
		method: 'POST',
		done: function(res){
			//上传完毕回调
			layer.alert("上传成功！", {
				icon: 1,
				title: "提示"
			});
			$("#logo").val(res.data);
			$('#upload-normal-img').attr("src", T.serverebusiness + res.data);
		}
		,error: function(res){
			//请求异常回调
			layer.alert("后台出错", {
				icon: 5,
				title: "提示"
			});
		}
	});
	selectUser();
	form.on('submit(save)', function(data) {
		var type = api.save.type;
		var url = api.save.url;
		if (guid) {
			type = api.update.type;
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
					$('#upload-normal-img').attr("src", T.serverebusiness + r.data.logo);
					form.val("form", r.data);
					form.render();
				} else {
					alertMsg(r);
				}
			}
		});
	}
	function selectUser(){
		$.ajax({
			type : api.userList.type,
			url : api.userList.url,
			async: false,
			success : function(r) {
				if(r.code == 0 && r.data){
					$.each(r.data,function (i,object) {
						$("#userGuid").append('<option value="'+object.guid+'">'+object.name+'</option>');
					});
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