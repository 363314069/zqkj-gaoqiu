$(function(){
$('legend').append(guid?'修改':'添加');
var api = {
	list:{url:T.serverurl + "security/vipcard/list", type:"post"},
	info:{url:T.serverurl + "security/vipcard/info", type:"get"},
	save:{url:T.serverurl + "security/vipcard/save", type:"post"},
	update:{url:T.serverurl + "security/vipcard/update", type:"post"},
	uploadFile:{url:T.serverebusiness + "security/vipcard/upload",type:"post"}
}
layui.use('form', function() {
	var form = layui.form,
		laydate = layui.laydate,
		upload = layui.upload,
		layer = layui.layer;
	laydate.render({
		elem: '#startTime' //或 elem: document.getElementById('test')、elem: lay('#test') 等
		,type: 'datetime'
	});
	laydate.render({
		elem: '#endTime' //或 elem: document.getElementById('test')、elem: lay('#test') 等
		,type: 'datetime'
	});
	//执行实例
	var uploadInst = upload.render({
		elem: '#file', //绑定元素
		accept: 'file',
		size: 307200,
		url: api.uploadFile.url, //上传接口
		method: 'POST',
		done: function(res){
			console.log(res);
			//上传完毕回调
			layer.alert("上传成功！", {
				icon: 1,
				title: "提示"
			});
			$("#svgPath").val(res.data);
			$('#upload-normal-img').attr("src", T.serverebusiness + res.data);
			$('#upload-normal-img').css("width","10%").css("height","10%");
		}
		,error: function(res){
			//请求异常回调
			layer.alert("后台出错", {
				icon: 5,
				title: "提示"
			});
		}
	});
	form.on('submit(save)', function(data) {
		var type = api.save.type;
		var url = api.save.url;
		if (guid) {
			ype = api.update.type;
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
	ue = UE.getEditor('content');
});
});